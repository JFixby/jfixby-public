package com.jfixby.tool.git.eclipse.name;

import java.io.IOException;

import com.jfixby.cmns.api.filesystem.File;
import com.jfixby.cmns.api.filesystem.LocalFileSystem;
import com.jfixby.cmns.api.log.L;
import com.jfixby.cmns.api.path.ChildrenList;

public class ScanEclipseProjects {

	static boolean WRITE_MODE = true;

	public static void main(String[] args) throws IOException {
		Setup.setup();

		String java_path = "D:\\[DEV]\\[GIT-2]";
		File git_folder = LocalFileSystem.newFile(java_path);
		scanFolder(git_folder);

	}

	private static void scanFolder(File folder) throws IOException {
		ChildrenList children = folder.listChildren();
		File project_file = children.findChild(".project");
		if (project_file != null) {
			// L.d("found", project_file);
			check(folder, project_file);
			return;
		}

		for (int i = 0; i < children.size(); i++) {
			File child = children.getElementAt(i);
			if (child.isFolder()) {
				scanFolder(child);
				continue;
			}
			if (child.isFile()) {
				continue;
			}
		}
	}

	private static void check(File folder, File project_file)
			throws IOException {
		String folder_name = folder.getName();
		String data = project_file.readToString();
		String NAME_OPEN = "<name>";
		String NAME_CLOSE = "</name>";
		int open = data.indexOf(NAME_OPEN) + NAME_OPEN.length();
		int close = data.indexOf(NAME_CLOSE);
		String project_name = data.substring(open, close);
		if (!project_name.equals(folder_name)) {
			L.d("renaming", folder);
			L.d("    from", folder_name);
			L.d("      to", project_name);
			if (WRITE_MODE) {
				folder.rename(project_name);
			}
			L.d();
		}
	}
}
