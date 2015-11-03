package com.jfixby.tool.gradle.dep.search;

import java.io.IOException;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.JUtils;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.collections.Set;
import com.jfixby.cmns.api.filesystem.File;
import com.jfixby.cmns.api.filesystem.LocalFileSystem;
import com.jfixby.cmns.api.log.L;
import com.jfixby.cmns.api.path.ChildrenList;
import com.jfixby.cmns.desktop.DesktopAssembler;

public class CollectGdxJars {

	private static final String OUTPUT_HOME_STRING = "D:\\[DEV]\\[GIT-2]\\libs\\libs\\gdx\\1.7.0";
	private static File output_jars_folder;

	public static final String PREFIX = "JCode";

	public static void main(String[] args) throws IOException {
		DesktopAssembler.setup();

		// String root_path_string = "D:\\[DEV]\\[TOOLS]\\gdx\\1.7.0";
		String root_path_string = "D:\\[DEV]\\[CODE]\\[WS-17.x]";
		// D:\[DEV]\[TOOLS]\gdx\1.7.0\desktop
		File root = LocalFileSystem.newFile(root_path_string);
		List<File> result = JUtils.newList();
		searchFolderFor(root, "gdx-1.7.0.jar", result);

		output_jars_folder = LocalFileSystem.newFile(OUTPUT_HOME_STRING);
		output_jars_folder.makeFolder();
		output_jars_folder.clearFolder();
		// result.print("result");

		for (File bin : result) {
			if (bin.getName().equals("variablesAndContainers.dat")) {
				processBin(bin);
			}
		}
	}

	private static void processBin(File bin) throws IOException {
		L.d("----------------------------------------------------------------");
		L.d("File", bin);
		String data = new String(bin.readBytes(), "utf-8");
		List<String> sections = JUtils.split(data, "my-gdx-game");
		// sections.print("sections");
		for (String section : sections) {
			processSection(section);
		}
	}

	private static void processSection(String section) throws IOException {
		// L.d("section", section);

		List<String> split = JUtils.split(section, PREFIX);
		// split.print("sections");

		if (split.getElementAt(0).startsWith(CORE)) {
			extractSections(split, CORE);
		} else if (split.getElementAt(0).startsWith(DESKTOP)) {
			extractSections(split, DESKTOP);
		} else if (split.getElementAt(0).startsWith(HTML)) {
			extractSections(split, HTML);
		} else if (split.getElementAt(0).startsWith(IOS)) {
			extractSections(split, IOS);
		}

		// L.d(data);
		// collected.print("collected");
		// scanGroups(groups);
		// scanJarHomes(collected);
	}

	static final String CORE = "-core";
	static final String DESKTOP = "-desktop";
	static final String HTML = "-html";
	static final String IOS = "-ios";

	private static void extractSections(List<String> split, String postfix)
			throws IOException {
		split.removeElementAt(0);
		// split.print(postfix);
		if (split.size() == 0) {
			return;
		}

		Set<File> collected = JUtils.newSet();
		postfix = postfix.substring(1);
		// L.d("section", postfix);

		for (String line : split) {
			if (line.startsWith("/.gradle/")) {
				line = line.substring(0, line.indexOf(".jar") + 4);
				// line = line.replaceFirst(::, replacement)
				line = "C:/Users/JCode" + line;
				// L.d("line", line);
				File jar_file = LocalFileSystem.newFile(line);
				File jar_home = jar_file.parent().parent();
				// L.d("jar_home", jar_home);

				collected.add(jar_home);
			}
		}

		scanJarHomes(collected, postfix);
	}

	private static void scanJarHomes(Collection<File> jar_homes, String postfix)
			throws IOException {
		for (File jar_home : jar_homes) {
			scan_jar_home(jar_home, postfix);
		}
	}

	private static void scan_jar_home(File jar_home, String postfix)
			throws IOException {
		// L.d("scanning", jar_home);
		ChildrenList children = jar_home.listChildren();
		// children.print("children");
		for (File child : children) {
			File candidate = child.listChildren().getLast();
			if (candidate.getName().endsWith(".jar")) {
				L.d("jar_found", candidate);
				processJar(candidate, postfix);
			}
		}
	}

	private static void processJar(File candidate, String postfix)
			throws IOException {
		File output = output_jars_folder.child(postfix);
		output.makeFolder();
		output_jars_folder.getFileSystem().copyFileToFolder(candidate, output);
	}

	private static void searchFolderFor(File folder, String search_terms,
			List<File> result) throws IOException {
		// L.d("scan filder", folder);
		ChildrenList list = folder.listChildren();
		for (int i = 0; i < list.size(); i++) {
			File file = list.getElementAt(i);
			if (file.isFolder()) {
				searchFolderFor(file, search_terms, result);

			} else if (file.isFile()) {
				scanContent(file, search_terms, result);
			}
		}
	}

	private static void scanContent(File file, String search_terms,
			List<File> result) throws IOException {
		// L.d("scan file", file);
		try {
			long BIG = 1024 * 1024;
			if (file.getSize() > BIG) {
				return;
			}
			String string = file.readToString().toLowerCase();
			if (string.contains(search_terms.toLowerCase())) {
				result.add(file);
				L.d("found", file);
				// L.d(string);
			}
		} catch (Exception e) {
			L.d(e);
		}

	}
}
