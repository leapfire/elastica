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

import java.util.Map;

import com.leapfire.elastica.util.Json;

public class SearchHit
{
    String _index;
    String _type;
    String _id;
    float _score;
    Map<String,Object>_source;
    String[] sort;
    String _routing;
    String _parent;

    @com.fasterxml.jackson.annotation.JsonIgnore
    public String getSourceAsString()
    {
        return Json.json(_source);
    }

    public String sourceAsString()
    {
        return Json.json(_source);
    }
    
    public SearchHitField field(String key)
    {
        SearchHitField f = null;
        Object obj = _source.get(key);
        if (obj != null) {
            f = new SearchHitField();
            f.setValue(obj);
        }
        return f;
    }

    public String get_index()
    {
        return _index;
    }
    public void set_index(String _index)
    {
        this._index = _index;
    }
    public String get_type()
    {
        return _type;
    }
    public void set_type(String _type)
    {
        this._type = _type;
    }
    public String get_id()
    {
        return _id;
    }
    public void set_id(String _id)
    {
        this._id = _id;
    }
    public float get_score()
    {
        return _score;
    }
    public void set_score(float _score)
    {
        this._score = _score;
    }
    public Map<String, Object> get_source()
    {
        return _source;
    }
    public void set_source(Map<String, Object> _source)
    {
        this._source = _source;
    }

    public String[] getSort()
    {
        return sort;
    }

    public void setSort(String[] sort)
    {
        this.sort = sort;
    }

    public String get_routing()
    {
        return _routing;
    }

    public void set_routing(String _routing)
    {
        this._routing = _routing;
    }

    public String get_parent()
    {
        return _parent;
    }

    public void set_parent(String _parent)
    {
        this._parent = _parent;
    }
}
