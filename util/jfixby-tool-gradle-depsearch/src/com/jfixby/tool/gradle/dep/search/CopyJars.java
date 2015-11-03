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

public class CopyJars {

	private static final String OUTPUT_HOME_STRING = "D:\\[DEV]\\[GIT-2]\\libs\\libs\\gdx\\1.7.0";
	private static File output_jars_folder;

	public static void main(String[] args) throws IOException {
		DesktopAssembler.setup();

		String root_path_string = "D:\\[DEV]\\[TOOLS]\\gdx\\1.7.0";
		// D:\[DEV]\[TOOLS]\gdx\1.7.0\desktop
		File root = LocalFileSystem.newFile(root_path_string);
		List<File> result = JUtils.newList();
		searchFolderFor(root, "JCode", result);

		output_jars_folder = LocalFileSystem.newFile(OUTPUT_HOME_STRING);
		output_jars_folder.makeFolder();
		output_jars_folder.clearFolder();

		File bin = result.getLast();

		String data = bin.readToString();
		List<String> split = JUtils.split(data, "JCode");
		// split.print("split");
		List<Set<File>> groups = JUtils.newList();
		Set<File> jar_homes = JUtils.newSet();
		Set<File> collected = JUtils.newSet();

		for (String line : split) {
			if (line.startsWith("\\.gradle")) {
				line = line.substring(0, line.indexOf(".jar") + 4);
				// line = line.replaceFirst(::, replacement)
				line = "C:\\Users\\JCode" + line;
				// L.d("line", line);
				File jar_file = LocalFileSystem.newFile(line);
				File jar_home = jar_file.parent().parent();
				// L.d("jar_home", jar_home);
				if (collected.contains(jar_home)) {
					groups.add(jar_homes);
					jar_homes = JUtils.newSet();
				}
				jar_homes.add(jar_home);
				collected.add(jar_home);
			}
		}
		// L.d(data);
		// groups.print("groups");
		// scanGroups(groups);
		scanJarHomes(collected);
	}

	private static void scanGroups(List<Set<File>> groups) throws IOException {
		for (Set<File> jar_homes : groups) {
			L.d("-----------------------------");
			scanJarHomes(jar_homes);
		}

	}

	private static void scanJarHomes(Collection<File> jar_homes)
			throws IOException {
		for (File jar_home : jar_homes) {
			scan_jar_home(jar_home);
		}
	}

	private static void scan_jar_home(File jar_home) throws IOException {
		// L.d("scanning", jar_home);
		ChildrenList children = jar_home.listChildren();
		// children.print("children");
		for (File child : children) {
			File candidate = child.listChildren().getLast();
			if (candidate.getName().endsWith(".jar")) {
				L.d("jar_found", candidate);
				// processJar(candidate);
			}
		}
	}

	private static void processJar(File candidate) throws IOException {

		output_jars_folder.getFileSystem().copyFileToFolder(candidate,
				output_jars_folder);

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
		String string = file.readToString();
		if (string.contains(search_terms)) {
			result.add(file);
			L.d("found", file);
		}

	}
}
