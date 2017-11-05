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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class U
{
    public static final Map<String, String> emptyMap = Collections.<String, String>emptyMap();

    public static void main(String[] args) 
    {
        System.out.println("( Start U");

        String[] lines = { "one","two","three","four","five" };
        String fileName = "test.txt";
        File file = new File(fileName);
        
        writeLines(file, lines);
        System.out.println("    End U)");
    }

    public static void writeLines(File file, String[] lines)
    {
        FileWriter fw = null;
        BufferedWriter bw = null;

        try {
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            
            for (String line:lines) {
                bw.write(line);
                bw.newLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (bw != null) { bw.close(); }
                if (fw != null) { fw.close(); }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static BufferedWriter makeWriter(File file)
    {
        FileWriter fw = null;
        BufferedWriter bw = null;

        try {
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return bw;
    }
    
    public static Properties readProperties() throws FileNotFoundException, IOException
    {
        String propFileName = System.getProperty("properties", "config/local.properties");
        File propFile = new File(propFileName);

        Properties result = new Properties();
        BufferedInputStream bs = null;
        try {
            bs = new BufferedInputStream(new FileInputStream(propFile));
            result.load(bs);
        }
        finally {
            if (bs != null) {
                bs.close();
            }
        }
        result.put("_config.dir", propFile.getParent());
        return result;
    }

    public static List<String> readLines(String filename) throws IOException
    {
    	return readLines(new File(filename));
    }

    public static List<String> readLines(File f) throws IOException
    {
//    	http://www.java-samples.com/showtutorial.php?tutorialid=392
        List<String> result = new ArrayList<String>();
        BufferedReader r = null;
        
        try {
            FileReader fr = new FileReader(f);
            r = new BufferedReader(fr);
            String line;
            while ((line = r.readLine()) != null) {
            	result.add(line);
            }
        }
        finally {
            if (r != null) { r.close(); }
        }
        
        return result;
    }

    public static String read(File f) throws IOException
    {
    	StringBuilder b = new StringBuilder();
        BufferedReader r = null;
        
        try {
            FileReader fr = new FileReader(f);
            r = new BufferedReader(fr);
            String line;
            while ((line = r.readLine()) != null) {
            	b.append(line);
            	b.append(' ');
            }
        }
        finally {
            if (r != null) { r.close(); }
        }
        
        return b.toString();
    }

    public static boolean isEmpty(String s) { return s==null || s.isEmpty(); }
}
