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

import java.util.Properties;

public class Opt
{
    protected Properties options = new Properties();

    public static Opt make(String[] args)
    {
        return Opt.make(null, args);
    }
    
    public static Opt make(String[] defaultArgs, String[] args)
    {
        Opt opt = new Opt();
        if (defaultArgs != null) { opt.processArgs(defaultArgs); }
        opt.processArgs(args);
        return opt;
    }

    protected void processArgs(String[] args)
    {
        String key = "";
        String arg;
        boolean expectOption = true;
        boolean reported = false;
        
        for (int x = 0; x < args.length; x++) {
            arg = args[x];
            
            if (expectOption) {
                if (arg.startsWith("-")) {
                    key = arg.substring(1);
                    expectOption = false;
                    reported = false;
                }
                else {
                    if (!reported) {
                        System.out.format("? option with leading dash expected; found \"%s\"\n", arg);
                        reported = true;
                    }
                }
            }
            else {
                options.setProperty(key, arg);
                expectOption = true;
            }
        }
    }
    
    public String option(String key)
    {
        return options.getProperty(key);
    }

    public String option(String key, String defaultValue)
    {
        String value = options.getProperty(key);
        return value==null ? defaultValue : value;
    }

    public boolean optionBool(String key)
    {
        String value = options.getProperty(key);
        return value==null ? false : value.equals("1");
    }

    public int optionInt(String key)
    {
        return optionInt(key, 0);
    }
    
    public int optionInt(String key, int defaultValue)
    {
        String value = options.getProperty(key);
        return (int) (value==null ? defaultValue : Integer.valueOf(normalize(value)));
    }

    public long optionLong(String key)
    {
        return optionLong(key, 0L);
    }

    public long optionLong(String key, long defaultValue)
    {
        String value = options.getProperty(key);
        return (long) (value==null ? defaultValue : Long.valueOf(normalize(value)));
    }

    protected String normalize(String key)
    {
        String arg;
        if (key != null && key.indexOf(',') >= 0) {
            String[] parts = key.split(",");
            StringBuilder sb = new StringBuilder();
            for (String p:parts) {
                sb.append(p);
            }
            arg = sb.toString();
        }
        else {
            arg = key;
        }
        return arg;
    }

    public String[] optionList(String key)
    {
        String[] list;
        String value = key==null || key.isEmpty() ? null : options.getProperty(key);
        if (value == null) {
            list = null;
        }
        else {
            list = value.split(",");
        }
        return list;
    }

    public Properties getOptions()
    {
        return options;
    }
}
