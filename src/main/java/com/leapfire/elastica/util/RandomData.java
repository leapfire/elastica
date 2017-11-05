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

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RandomData
{
	public static String[] words;
	public static String[] names;
	public static String[] places;

	public static final char[] letters = {
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
	};
	public static final char[] lowercase = {
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
	};
	public static final char[] uppercase = {
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
	};
	public static final char[] numbers = {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
	};
	public static final char[] alphanumeric = {
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
	};

	public static final String[] states = { "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID",
			"IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ",
			"NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV",
			"WI", "WY" };

	public static final String[] top_states = { "CA", "TX", "FL", "NY", "IL", "PA", "OH", "GA", "NC", "MI" };

	public static final String[] languages = { "EN", "JP", "GE", "FR", "IT", "NL", "SL", "IN", "CS" };

	public static final String[] streets = { "Street", "St", "Way", "Road", "Blvd", "Avenue", "Ave", "Road", "Rd", "Street" };

	public static final String[] emailProviders = { "yahoo.com", "hotmail.com", "comcast.net", "gmail.com", "pobox.org", "att.net", "aol.com" };

	public static void loadWords(File f) throws IOException {
		List<String> lines = U.readLines(f);
		words = new String[lines.size()];
		lines.toArray(words);
	}
	public static void loadPlaces(File f) throws IOException {
		List<String> lines = U.readLines(f);
		places = new String[lines.size()];
		lines.toArray(places);
	}
	public static void loadNames(File f) throws IOException {
		List<String> lines = U.readLines(f);
		names = new String[lines.size()];
		lines.toArray(names);
	}
}
