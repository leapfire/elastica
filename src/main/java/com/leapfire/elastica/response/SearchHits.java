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

public class SearchHits
{
    long total;
    float max_score;
    SearchHit[] hits;
    
    public long getTotalHits()
    {
        return total;
    }
    public long totalHits()
    {
        return getTotalHits();
    }
    public long getTotal()
    {
        return total;
    }
    public void setTotal(long total)
    {
        this.total = total;
    }
    public float getMax_score()
    {
        return max_score;
    }
    public void setMax_score(float max_score)
    {
        this.max_score = max_score;
    }
    public SearchHit[] getHits()
    {
        return hits;
    }
    public void setHits(SearchHit[] hits)
    {
        this.hits = hits;
    }
}
