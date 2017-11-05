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

public class GetResponse
{
    String _index;
    String _type;
    String _id;
    String _version;
    String found;
    Map<String,Object> _source;
    
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
    public String get_version()
    {
        return _version;
    }
    public void set_version(String _version)
    {
        this._version = _version;
    }
    public String getFound()
    {
        return found;
    }
    public void setFound(String found)
    {
        this.found = found;
    }
    public Map<String, Object> get_source()
    {
        return _source;
    }
    public void set_source(Map<String, Object> _source)
    {
        this._source = _source;
    }
    
}
