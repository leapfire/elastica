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

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class Json
{
    public static final String dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final SimpleDateFormat df = new SimpleDateFormat(dateFormat);
    public static final TypeReference<HashMap<String, Object>> DOC_MAP = new TypeReference<HashMap<String, Object>>() {};

    public static final ObjectMapper om = new ObjectMapper().setDateFormat(df);
    public static final ObjectWriter pp = om.writerWithDefaultPrettyPrinter();

    public static String json(Object obj)
    {
        String result = null;
        try {
            result = om.writeValueAsString(obj);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public static String pretty(Object obj)
    {
        String result = null;
        try {
            result = pp.writeValueAsString(obj);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String clean(String query)
    {
        return Json.json(Json.read(query));
    }

    public static HashMap<String, Object> read(String json)
    {
        HashMap<String, Object> result = null;
        try {
            result = om.readValue(json, DOC_MAP);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static HashMap<String, Object> read(InputStream is)
    {
        HashMap<String, Object> result = null;
        try {
            result = om.readValue(is, DOC_MAP);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static <T> T read(String json, Class<T> valueType)
    {
        T result = null;
        try {
            result = om.readValue(json, valueType);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static <T> T read(InputStream is, Class<T> valueType)
    {
        T result = null;
        try {
            result = om.readValue(is, valueType);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
