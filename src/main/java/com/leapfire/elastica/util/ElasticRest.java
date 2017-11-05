/*
Copyright (c) 2017, Dr. Geena Louise Rollins

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package com.leapfire.elastica.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseException;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

import com.leapfire.elastica.response.GetResponse;
import com.leapfire.elastica.response.SearchResponse;

public class ElasticRest
{
    public static final String MATCH_ALL = "{ \"query\" : { \"match_all\" : { } } }";
    public static final String[] docSort = { "_doc" };
    
    protected String clusterName;
    protected RestClient restClient;

    public static ElasticRest make(Properties properties)
    {
        String clusterName = properties.getProperty("elasticsearch.cluster.name");
        String dataNodes = properties.getProperty("elasticsearch.data.nodes");
        return make(clusterName, dataNodes);
    }

    public static ElasticRest make(String clusterName, String dataNodes)
    {
        ElasticRest er = new ElasticRest();
        RestClientBuilder b = RestClient.builder(hostList(dataNodes))
                .setMaxRetryTimeoutMillis(240000);
        er.restClient = b.build();
        er.clusterName = clusterName;
        return er;
    }

    public void close() throws IOException
    {
        if (restClient != null) restClient.close();
    }
    
    public boolean exists(String index, String type)
    {
        boolean success = false;
        try {
            Response response = restClient.performRequest("GET", String.format("/%s/_mappings/%s", index, type), U.emptyMap);
            success = response.getStatusLine().getStatusCode() < 300 && (Json.read(response.getEntity().getContent()).size() > 0);
        }
        catch (Exception e) {
        }
        
        return success;
    }

    public String hello(boolean pretty) throws IOException
    {
        Response res = restClient.performRequest("GET", "/");
        HashMap<String, Object> map = Json.read(res.getEntity().getContent());
        return pretty ? Json.pretty(map) : Json.json(map);
    }

    public String index(String index, String type, Object document, String id) throws IOException
    {
        return index(index, type, Json.json(document), id, null);
    }

    public String index(String index, String type, Object document) throws IOException
    {
        return index(index, type, Json.json(document), null, null);
    }

    public String index(String index, String type, String json) throws IOException
    {
        return index(index, type, json, null, null);
    }

    @SuppressWarnings("unchecked")
    public String index(String index, String type, String json, String id, String parent) throws IOException
    {
//        System.out.format("ElasticRest.index: index=%s,  type=%s,  json=%s,  id=%s,  parent=%s\n", index,  type,  json.length() <= 32 ? json : json.substring(0, 32),  id,  parent);
        Response response;
        HttpEntity body = new StringEntity(json, ContentType.APPLICATION_JSON);
        
        String method, path;
        if (id==null) {
            method = "POST";
            path = String.format("/%s/%s/", index, type);
        }
        else {
            method = "PUT";
            path = String.format("/%s/%s/%s", index, type, id);
        }
        Map<String, String> params;
        if (parent == null) {
            params = U.emptyMap;
        }
        else {
            params = new HashMap<String, String>();
            params.put("parent", parent);
        }
        
        response = performRequest(method, path, params, body);
        
        Map<String, Object> map = Json.read(response.getEntity().getContent());
        int failed = (int) ((Map<String, Object>) map.get("_shards")).get("failed");
        if (failed > 0) { throw new RuntimeException("? Single Indexing failure"); }
        
        return (String) map.get("_id");
    }

    protected Response performRequest(String method, String path, Map<String, String> params, HttpEntity body) throws IOException
    {
        int attempts = 5;
        long delay = 10000;
        Response response = null;
        
        while (response == null && attempts > 0) {
            try {
                attempts--;
                response = restClient.performRequest(method, path, params, body);
            }
            catch (Exception e) {
                System.out.format("? Exception: %s\n", e.getMessage());
                System.out.format("  Delay: %d  Attempts Left: %d\n", delay, attempts);
                try {
                    Thread.sleep(delay);
                    delay = delay * 2;
                }
                catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return response;
    }

    public boolean bulkIndexChildren(String index, String type, List<String> documents, List<String> parents) throws IOException
    {
        if (documents==null || documents.isEmpty()) { return false; }
        
        HttpEntity body = bulkBody(index, type, documents, parents);
        Response response = performRequest("POST", "_bulk", U.emptyMap, body);
        
        Map<String, Object> map = Json.read(response.getEntity().getContent());
        boolean errors = (boolean) map.get("errors");
        if (errors) {
            System.out.printf("bulkIndex.response %s\n", Json.pretty(map));
            throw new RuntimeException("? Bulk Indexing errors");
        }
        return errors;
    }

    public boolean bulkIndex(String index, String type, List<String> documents) throws IOException
    {
        if (documents==null || documents.isEmpty()) { return false; }
        
        HttpEntity body = bulkBody(index, type, documents);
        Response response = performRequest("POST", "_bulk", U.emptyMap, body);
        
        Map<String, Object> map = Json.read(response.getEntity().getContent());
        boolean errors = (boolean) map.get("errors");
        if (errors) {
            System.out.printf("bulkIndex.response %s\n", Json.pretty(map));
            throw new RuntimeException("? Bulk Indexing errors");
        }
        return errors;
    }

    public HttpEntity bulkBody(String index, String type, List<String> documents)
    {
        String action = actionJson(index, type, null);
        StringBuilder b = new StringBuilder();
        for (String doc:documents) {
            b.append(action);
            b.append('\n');
            b.append(doc);
            b.append('\n');
        }
//        System.out.format("<body>\n%s</body>\n", b.toString());
        
        HttpEntity body = new StringEntity(b.toString(), ContentType.APPLICATION_JSON);
        return body;
    }

    public HttpEntity bulkBody(String index, String type, List<String> documents, List<String> parents)
    {
        StringBuilder b = new StringBuilder();
        int i = 0;
        for (String doc:documents) {
            String action = actionJson(index, type, parents.get(i++));
            b.append(action);
            b.append('\n');
            b.append(doc);
            b.append('\n');
        }
//        System.out.format("<body>\n%s</body>\n", b.toString());
        
        HttpEntity body = new StringEntity(b.toString(), ContentType.APPLICATION_JSON);
        return body;
    }

    public static String actionJson(String index, String type, String parent)
    {
        Map<String, Object> settingsMap = new HashMap<String, Object>();
        settingsMap.put("_index", index);
        settingsMap.put("_type", type);
        if (parent != null) { settingsMap.put("parent", parent); }
        
        Map<String, Object> actionMap = new HashMap<String, Object>();
        actionMap.put("index", settingsMap);
        return Json.json(actionMap);
    }

    public SearchResponse searchTest(String index, String type, String rawQuery, int start, int size, String... fields) throws IOException
    {
        Map<String, String> params = parameters(index, type, start, size);
        String query = addFields(false, rawQuery, fields);
        HttpEntity se = new StringEntity(query, ContentType.APPLICATION_JSON);
        Response response = restClient.performRequest("GET", "/_search", params, se);
        
        Map<String, Object> map = Json.read(response.getEntity().getContent());
        System.out.format("map=%s\n", Json.pretty(map));
        return null;
    }

    public static String addFields(boolean scroll, String rawQuery, String... fields)
    {
        HashMap<String, Object> q = Json.read(rawQuery);
        q.put("_source", fields == null || fields.length==0 ? true : fields);
        if (scroll) q.put("sort", docSort);
        return Json.json(q);
    }

    public static String addScroll(String scrollTime, String scrollId)
    {
        HashMap<String, Object> q = new HashMap<String, Object>();
        q.put("scroll", scrollTime);
        q.put("scroll_id", scrollId);
        return Json.json(q);
    }

    public SearchResponse search(String index, String type, String query, String... fields) throws IOException
    {
        return search(query, parameters(index, type, -1, -1), fields);
    }

    public SearchResponse search(String index, String type, String query, int size, String... fields) throws IOException
    {
        return search(query, parameters(index, type, -1, size), fields);
    }

    public SearchResponse search(String index, String type, String query, int start, int size, String... fields) throws IOException
    {
        return search(query, parameters(index, type, start, size), fields);
    }

    public static Map<String, String> parameters(String index, String type, int start, int size)
    {
        Map<String, String> params = new HashMap<String, String>();
        if (! U.isEmpty(index)) params.put("index", index);
        if (! U.isEmpty(type)) params.put("type", type);
        if (size >= 0) params.put("size", Integer.toString(size));
        if (start >= 0) params.put("from", Integer.toString(start));
        
        return params;
    }

    public SearchResponse search(String rawQuery, Map<String, String> params, String... fields) throws IOException
    {
        String query = addFields(false, rawQuery, fields);
        HttpEntity se = new StringEntity(query, ContentType.APPLICATION_JSON);
        Response response = restClient.performRequest("GET", "/_search", params, se);

        InputStream content = response.getEntity().getContent();
        return Json.read(content, SearchResponse.class);
    }

    public GetResponse get(String index, String type, String docId) throws IOException
    {
        String path = String.format("/%s/%s/%s", index, type, docId);
        System.out.format("    get.path=%s\n", path);
        
        GetResponse result = null;
        try {
            Response response = restClient.performRequest("GET", path);
            result = Json.read(response.getEntity().getContent(), GetResponse.class);
        }
        catch (ResponseException e) {
        }

        return result;
    }

    public long count(String index, String type, String query) throws IOException
    {
        SearchResponse res = search(index, type, query, 0, 0);
        return res.getHits().totalHits();
    }

    public interface ScrollOp { long handleHits(SearchResponse res, long elapsed); }
    
    public void scroll(String label, ScrollOp scrollOp, String index, String type, String rawQuery, int size, long limit, String scroll, String... fields) throws IOException 
    {
        String scrollTime = scroll==null || scroll.isEmpty() ? "20s" : scroll;
        long start, now;
        Response response;
        SearchResponse res;
        Map<String, String> params = parameters(index, type, 0, size);
        params.remove("from");
        params.put("scroll", scrollTime);
        String query = addFields(true, rawQuery, fields);
        HttpEntity se = new StringEntity(query, ContentType.APPLICATION_JSON);
        
        start = System.currentTimeMillis();
        response = restClient.performRequest("GET", "/_search", params, se);

        res = Json.read(response.getEntity().getContent(), SearchResponse.class);
        if (res.getFailedShards() > 0) { throw new RuntimeException("? Failed shards in scroll"); }

        String scrollId = res.get_scroll_id();
        String banner = String.format("Total Hits=%,11d  Took=%,11d ms", res.getHits().getTotalHits(), res.getTookInMillis());
        System.out.format("\n%s ScrollId=%s\n", banner, scrollId);
        
        if (limit <= 0) { limit = Integer.MAX_VALUE; }
        long handled = scrollOp.handleHits(res, (now=System.currentTimeMillis()) - start);
        long count = handled;

        se = new StringEntity(addScroll(scrollTime, scrollId), ContentType.APPLICATION_JSON);
        while (0 < handled && count < limit) {
            start = now;
            response = restClient.performRequest("GET", "/_search/scroll", U.emptyMap, se);

            res = Json.read(response.getEntity().getContent(), SearchResponse.class);
            if (res.getFailedShards() > 0) { throw new RuntimeException("? Failed shards in scroll"); }

            handled = scrollOp.handleHits(res, (now=System.currentTimeMillis()) - start);
            count += handled;
        }
        System.out.format("+++ %8s %16s %s\n", index, label, banner);
    }
    
    protected static HttpHost[] hostList(String dataNodes)
    {
        System.out.format("dataNodes=%s\n", dataNodes);
        
        List<HttpHost> hostList = new ArrayList<HttpHost>();
        String[] pairs = dataNodes.split(",");
        for (String pair : pairs) {
            String[] parts = pair.split(":");
            hostList.add(new HttpHost(parts[0], Integer.valueOf(parts[1])));
        }
        HttpHost[] hosts = new HttpHost[hostList.size()];
        hostList.toArray(hosts);
        System.out.format("hosts=%s\n", Json.json(hostList));
        
        return hosts;
    }
    
    public String getClusterName()
    {
        return clusterName;
    }

    public static String expandQueryTemplate(String rawQuery, Map<String, String> values)
    {
        String result;

        if (rawQuery.indexOf("${") >= 0) {
            StringBuilder query = new StringBuilder();
//            System.out.format("expandQueryTemplate.rawQuery=%s\n", rawQuery);

            int start = 0;
            int previous = 0;
            int end = 0;
            boolean live = true;

            while (live) {
                start = rawQuery.indexOf("${", previous);
                if (start >= 0) {
                    end = rawQuery.indexOf("}", start);
                    String key = rawQuery.substring(start + 2, end);
                    String value = values.get(key);
                    query.append(rawQuery.substring(previous, start));
                    query.append(value);
                    previous = end + 1;
                }
                else {
                    live = false;
                    query.append(rawQuery.substring(previous));
                }
            }
            result = Json.json(Json.read(query.toString()));
        }
        else {
            result = rawQuery;
        }
        System.out.format("expandQueryTemplate.result=%s\n", result);
        
        return result;
    }
}
