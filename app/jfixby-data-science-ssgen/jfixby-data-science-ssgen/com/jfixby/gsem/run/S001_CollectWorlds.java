package com.jfixby.gsem.run;

import java.io.IOException;

import com.jfixby.cmns.api.collections.JUtils;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.filesystem.File;
import com.jfixby.cmns.api.filesystem.LocalFileSystem;
import com.jfixby.cmns.api.log.L;
import com.jfixby.cmns.api.math.IntegerMath;
import com.jfixby.cmns.api.net.http.Http;
import com.jfixby.cmns.api.net.http.HttpCall;
import com.jfixby.cmns.api.net.http.HttpCallExecutor;
import com.jfixby.cmns.api.net.http.HttpCallProgress;
import com.jfixby.cmns.api.net.http.HttpCallSpecs;
import com.jfixby.cmns.api.net.http.HttpURL;
import com.jfixby.cmns.api.net.http.METHOD;
import com.jfixby.cmns.api.path.ChildrenList;

public class S001_CollectWorlds {

	public static void main(String[] args) throws IOException {
		Setup.setup();

		File chars_file = LocalFileSystem.ApplicationHome().child(
				"exclude-chars.txt");
		String chars = chars_file.readToString();
		int K = 0;
		WordsSorter sorter = new WordsSorter();

		File raw_folder = LocalFileSystem.ApplicationHome().child("raw");
		ChildrenList list = raw_folder.listChildren();

		for (int i = 0; i < list.size(); i++) {
			File file = list.getElementAt(i);
			if (file.getName().startsWith("#")) {
				continue;
			}

			K++;
			String data = null;
			L.d("reading", file);
			data = file.readToString();
			data = data.replaceAll("\\<[^>]*>", "");

			int begin = data.indexOf("Contents");
			begin = (int) IntegerMath.max(begin, 0);
			int end = data.indexOf("References");
			int max = data.length();
			end = (int) IntegerMath.min(end, max);
			end = data.indexOf("References", end + 1);
			end = (int) IntegerMath.min(end, max);
			if (begin > end) {
				begin = 0;
				end = data.length();
			}
			data = data.substring(begin, end);
			data = data.replaceAll("\n", " ");
			data = data.replaceAll("\r", " ");

			data = exclude_chars(data, chars);
			data = data.replaceAll("  ", " ");

			List<String> split = JUtils.newList(data.split(" "));
			// split.print(url_string);
//			split.print("words++");
			sorter.addAll(split);

		}

		 K = (K / 8);
		// L.d("cutting", K)
//		K = 2;
		sorter.filter(K);
		sorter.sort();
		sorter.print();
		sorter.saveTo("words.json");

	}

	private static String readURL(String url_string) throws IOException {
		HttpURL url = Http.newURL(url_string);
		L.d("calling", url);

		HttpCallSpecs call_scecs = Http.newCallSpecs();
		call_scecs.setURL(url);
		call_scecs.setMethod(METHOD.GET);
		call_scecs.setUseAgent(true);
		call_scecs.setUseSSL(false);

		HttpCall call = Http.newCall(call_scecs);

		HttpCallExecutor exec = Http.newCallExecutor();

		HttpCallProgress result = exec.execute(call);

		// String data = result.readResultAsString("windows-1251");
		String data = result.readResultAsString("utf-8");

		return data;
	}

	private static String exclude_chars(String data, String chars) {

		for (int i = 0; i < chars.length(); i++) {
			char oldChar = chars.charAt(i);
			data = data.replace(oldChar, ' ');
			// data = data.replaceAll(e, " ");
		}
		return data;
	}
}
