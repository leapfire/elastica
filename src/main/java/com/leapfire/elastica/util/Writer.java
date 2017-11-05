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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer
{
    FileWriter fw = null;
    BufferedWriter bw = null;

    public Writer(File file) throws IOException
    {
        fw = new FileWriter(file);
        bw = new BufferedWriter(fw);
    }

    public Writer(File file, boolean append) throws IOException
    {
        fw = new FileWriter(file, append);
        bw = new BufferedWriter(fw);
    }

    public static void main(String[] args) throws IOException
    {
        System.out.println("( Start U");

        String[] lines = { "one","two","three","four","five" };
        String fileName = "test2.txt";
        File file = new File(fileName);
        
        Writer w = new Writer(file);
        for (String s:lines) {
            w.writeLine(s);
        }
        
        System.out.println("    End U)");
    }

    public void write(String s) throws IOException
    {
        bw.write(s);
    }

    public void newLine() throws IOException
    {
        bw.newLine();
    }

    public void writeLine(String s) throws IOException
    {
        bw.write(s);
        bw.newLine();
    }

    public void close() throws IOException
    {
        if (bw != null) { bw.close(); }
        if (fw != null) { fw.close(); }
    }
}
