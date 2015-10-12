package com.jfixby.gsem.run;

import java.io.IOException;

import com.jfixby.cmns.api.collections.JUtils;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.filesystem.File;
import com.jfixby.cmns.api.filesystem.LocalFileSystem;
import com.jfixby.cmns.api.log.L;
import com.jfixby.cmns.api.net.http.Http;
import com.jfixby.cmns.api.net.http.HttpCall;
import com.jfixby.cmns.api.net.http.HttpCallExecutor;
import com.jfixby.cmns.api.net.http.HttpCallProgress;
import com.jfixby.cmns.api.net.http.HttpCallSpecs;
import com.jfixby.cmns.api.net.http.HttpURL;
import com.jfixby.cmns.api.net.http.METHOD;

public class S000_DownloadFiles {

	public static void main(String[] args) throws IOException {
		Setup.setup();

		File chars_file = LocalFileSystem.ApplicationHome().child(
				"exclude-chars.txt");

		File sources = LocalFileSystem.ApplicationHome().child(
				"word-sources.txt");
		File raw_folder = LocalFileSystem.ApplicationHome().child("raw");
		raw_folder.makeFolder();
		String list = sources.readToString();
		List<String> list_of_sources = JUtils.split(list, "\n");

		for (int i = 0; i < list_of_sources.size(); i++) {
			String url = list_of_sources.getElementAt(i).replaceAll("\r", "")
					.replaceAll("\n", "");

			String data = null;
			L.d("downloading", url);
			data = readURL(url);
			String child_name = url.replaceAll("https", "")
					.replaceAll("http", "").replaceAll("://", "")
					.replaceAll("/", "_")
					+ ".html";
			L.d("          ", child_name);
			File file = raw_folder.child(child_name);
			file.writeString(data);

		}

	}

	private static String readURL(String url_string) throws IOException {
		HttpURL url = Http.newURL(url_string);

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
