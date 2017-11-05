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

package com.leapfire.elastica.response;


public class SearchResponse
{
    Shards _shards;
    SearchHits hits;
    
    int failedShards;
    long took;
    boolean timed_out;
    boolean terminated_early;
    String _scroll_id;
    
    
    @com.fasterxml.jackson.annotation.JsonIgnore
    public int getFailedShards()
    {
        return _shards.getFailed();
    }

    @com.fasterxml.jackson.annotation.JsonIgnore
    public long getTookInMillis() { return getTook(); }

    public Shards get_shards()
    {
        return _shards;
    }
    public void set_shards(Shards _shards)
    {
        this._shards = _shards;
    }
    public SearchHits getHits()
    {
        return hits;
    }
    public void setHits(SearchHits hits)
    {
        this.hits = hits;
    }

    public long getTook()
    {
        return took;
    }

    public void setTook(long took)
    {
        this.took = took;
    }

    public boolean isTimed_out()
    {
        return timed_out;
    }

    public void setTimed_out(boolean timed_out)
    {
        this.timed_out = timed_out;
    }

    public String get_scroll_id()
    {
        return _scroll_id;
    }

    public void set_scroll_id(String _scroll_id)
    {
        this._scroll_id = _scroll_id;
    }

    public boolean isTerminated_early()
    {
        return terminated_early;
    }

    public void setTerminated_early(boolean terminated_early)
    {
        this.terminated_early = terminated_early;
    }
}
