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

import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

public class Rand
{
    Random r = new Random(System.currentTimeMillis());
    
    public boolean nextBoolean() { return r.nextBoolean(); }
    public int nextInt() { return r.nextInt(); }
    public int nextInt(int min, int max) { return min + r.nextInt(max - min); }
    public int nextInt(int limit) { return r.nextInt(limit); }
    public long nextLong() { return r.nextLong(); }
    public long nextNonNegativeLong() { long result = r.nextLong(); return result<0 ? -result : result; }
    public String nextUUID() { return UUID.randomUUID().toString(); }
    public String nextIp() { return String.format("%s.%s.%s.%s", r.nextInt(255), r.nextInt(255), r.nextInt(255), r.nextInt(255)); }

    public char nextWordChar() { return RandomData.letters[r.nextInt(RandomData.letters.length)]; }
    public char nextUpperChar() { return RandomData.uppercase[r.nextInt(RandomData.uppercase.length)]; }
    public char nextAlphanumeric() { return RandomData.alphanumeric[r.nextInt(RandomData.alphanumeric.length)]; }

    public String nextWord() { return RandomData.words[r.nextInt(RandomData.words.length)]; }
    public String nextState() { return RandomData.states[r.nextInt(RandomData.states.length)]; }
    public String nextName() { return RandomData.names[r.nextInt(RandomData.names.length)]; }
    public String nextTopState() { return RandomData.states[r.nextInt(RandomData.top_states.length)]; }
    public String nextPlace() { return RandomData.places[r.nextInt(RandomData.places.length)]; }
    public String nextStreet() { return RandomData.streets[r.nextInt(RandomData.streets.length)]; }
    public String nextEmailProvider() { return RandomData.emailProviders[r.nextInt(RandomData.emailProviders.length)]; }

    public String nextShortWord()
    {
        String w = nextWord();
        for (int i=0; i < 10  && w.length() > 10; i++) {
            w = nextWord();
        }
        return w;
    }
    
    public String nextEmail(String first, String last)
    {
        String sep = nextBoolean() ? "." : nextBoolean() ? "_" :  "";
        return String.format("%s%s%s@%s", first.toLowerCase(), sep, last.toLowerCase(), nextEmailProvider());
    }
    
	public String nextLanguage()
	{
		return r.nextInt(100) < 70 ? RandomData.languages[0] : RandomData.languages[r.nextInt(RandomData.languages.length)];
	}

    public String nextString(int minLength, int maxLength)
    {
    	int len = r.nextInt(maxLength-minLength+1) + minLength;
    	StringBuilder b = new StringBuilder();
    	for (int i=0; i < len; i++) {
    		char ch = nextWordChar();
    		b.append(ch);
    	}
    	return b.toString();
    }

    public String nextId(int len) { return nextId(len,len); }
    public String nextUpper(int len) { return nextUpper(len,len); }
    public String nextDigits(int len) { return nextDigits(len,len); }
    
    public String nextId(int minLength, int maxLength)
    {
        int len = r.nextInt(maxLength-minLength+1) + minLength;
        StringBuilder b = new StringBuilder();
        for (int i=0; i < len; i++) {
            char ch = nextAlphanumeric();
            b.append(ch);
        }
        return b.toString();
    }

    public String nextUpper(int minLength, int maxLength)
    {
        int len = r.nextInt(maxLength-minLength+1) + minLength;
        StringBuilder b = new StringBuilder();
        for (int i=0; i < len; i++) {
            char ch = nextUpperChar();
            b.append(ch);
        }
        return b.toString();
    }

    public String nextDigits(int minLength, int maxLength)
    {
        int len = r.nextInt(maxLength-minLength+1) + minLength;
        StringBuilder b = new StringBuilder();
        for (int i=0; i < len; i++) {
            char ch = (char) (r.nextInt(10) + '0');
            b.append(ch);
        }
        return b.toString();
    }

    public String nextShortText(int len)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(StringUtils.capitalize(nextShortWord()));
        
        for (int i=0; i < len; i++) {
            sb.append(' ');
            sb.append(nextShortWord()); 
        }
        sb.append(' ');
        sb.append(nextShortWord()); 
        sb.append('.');
        
        return sb.toString();
    }
    public String nextText(int len)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(StringUtils.capitalize(nextWord()));
        
        for (int i=0; i < len; i++) {
           sb.append(nextWord()); 
           sb.append(' ');
        }
        sb.append(nextWord()); 
        sb.append('.');
        
        return sb.toString();
    }
    public String nextIntString(int x)
    {
        return Integer.toString(nextInt(x));
    }
    public String nextIntString(int x, int y)
    {
        return Integer.toString(nextInt(x,y));
    }
    public String next2Power()
    {
        return Integer.toString(2 ^ nextInt(9));
    }
    public String nextBit()
    {
        return nextBoolean() ? "0" : "1";
    }
    public String lang()
    {
        return nextBoolean() ? "EN" : nextLanguage();
    }
}
