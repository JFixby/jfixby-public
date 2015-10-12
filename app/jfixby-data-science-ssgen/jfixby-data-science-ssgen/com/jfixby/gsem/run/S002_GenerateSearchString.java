package com.jfixby.gsem.run;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.JUtils;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.collections.Set;
import com.jfixby.cmns.api.filesystem.File;
import com.jfixby.cmns.api.filesystem.LocalFileSystem;
import com.jfixby.cmns.api.json.Json;
import com.jfixby.cmns.api.log.L;
import com.jfixby.cmns.api.math.FloatMath;
import com.jfixby.cmns.api.math.IntegerMath;
import com.jfixby.cmns.api.net.http.HttpCallExecutor;
import com.jfixby.cmns.api.path.ChildrenList;
import com.jfixby.cmns.api.sys.Sys;

public class S002_GenerateSearchString {
	static String template = "https://www.google.com/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#q=";

	public static void main(String[] args) throws IOException,
			URISyntaxException {
		Setup.setup();
		File words_folder = LocalFileSystem.ApplicationHome().child("words");
		ChildrenList words_files = words_folder.listChildren();
		WordsSorter sorter = new WordsSorter(false);
		for (int i = 0; i < words_files.size(); i++) {
			File file = words_files.getElementAt(i);
			String file_name = file.getName();
			if (file_name.startsWith("#")) {
				continue;
			}
			L.d("reading", file_name);
			String data = file.readToString();
			L.d("parsing", file_name);
			WordCollectorFile content = Json.deserializeFromString(
					WordCollectorFile.class, data);

			List<WordCollector> split = JUtils.newList(content.values);
			L.d("   adding", split.size());
			sorter.addOthers(split);
		}
		sorter.filter(1);
		sorter.sort();

		// sorter.print();

		Collection<WordCollector> terms_list = sorter.list();
		terms_list.print("terms_list", 0, 500);
		Random random = new Random();

		int EXTRACTIONS = 100;
		int NUMBER_OF_TERMS = 8;

		for (int i = 0;; i++) {
			List<String> batch = JUtils.newList();
			for (int k = 0; k < EXTRACTIONS; k++) {
				String request = generateRequest(NUMBER_OF_TERMS, terms_list,
						random);
				batch.add(request);

			}
			batch.print("batch generated");
			for (int k = 0; k < batch.size(); k++) {
				String request = batch.getElementAt(k);
				String request_url = template + request.replaceAll(" ", "+");
				L.d(request, request_url);
				openUrl(request_url);
				Sys.sleep(10 * 1000);
			}
		}

	}

	private static void call(HttpCallExecutor exec, String request_url,
			String request) throws IOException, URISyntaxException {
		// HttpURL url = Http.newURL(request_url);
		// HttpCallSpecs specs = Http.newCallSpecs();
		// specs.setURL(url);
		// HttpCall call = Http.newCall(specs);
		// HttpCallProgress result = exec.execute(call);
		// String data_string = result.readResultAsString("utf-8");
		//
		// data_string = data_string.replaceAll("\\<[^>]*>", "");
		// String file_name = request.replaceAll(" ", "_") + ".html";
		// File result_file = LocalFileSystem.ApplicationHome().child("results")
		// .child(file_name);
		// result_file.writeString(data_string);
		openUrl(request_url);

		// L.d("       ", data_string);
	}

	public static void openUrl(String url) throws IOException,
			URISyntaxException {
		if (java.awt.Desktop.isDesktopSupported()) {
			java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

			if (desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
				java.net.URI uri = new java.net.URI(url);
				desktop.browse(uri);
			}
		}
	}

	private static String generateRequest(int n,
			Collection<WordCollector> terms_list, Random random) {
		String result = "";

		Set<String> stack = JUtils.newSet();
		for (; stack.size() < n;) {
			double d1 = random.nextDouble();
			d1 = FloatMath.pow(d1, 1.0);
			int index = (int) (terms_list.size() * d1);
			index = (int) IntegerMath.limit(0, index, terms_list.size());
			String term = terms_list.getElementAt(index).getWord();
			stack.addAll(JUtils.split(term, " "));
		}
		// stack.print("");
		for (int k = 0; k < stack.size(); k++) {
			result = result + " " + stack.getElementAt(k);
		}

		return result;
	}
}
