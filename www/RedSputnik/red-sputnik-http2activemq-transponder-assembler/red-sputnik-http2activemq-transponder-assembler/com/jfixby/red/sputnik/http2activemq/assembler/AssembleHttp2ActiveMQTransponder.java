package com.jfixby.red.sputnik.http2activemq.assembler;

import java.io.IOException;

import com.jfixby.cmns.api.collections.JUtils;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.filesystem.File;
import com.jfixby.cmns.api.filesystem.FileSystem;
import com.jfixby.cmns.api.filesystem.LocalFileSystem;
import com.jfixby.cmns.api.log.L;
import com.jfixby.cmns.api.path.AbsolutePath;
import com.jfixby.cmns.api.path.RelativePath;
import com.jfixby.red.desktop.filesystem.win.WinFile;

public class AssembleHttp2ActiveMQTransponder {

	public static void main(String[] args) throws Exception {

		Setup.setup();

		AbsolutePath<FileSystem> nbProjectPath = LocalFileSystem
				.newFile(
						"D:\\[DEV]\\[CODE]\\[NetBeans]\\red-sputnik-http2activemq-transponder")
				.getAbsoluteFilePath();

		AbsolutePath<FileSystem> java_code_output_path = nbProjectPath.child(
				"src").child("java-refs");

		// \\src\\java-refs

		// src\java-refs
		AbsolutePath<FileSystem> workspace_path = LocalFileSystem
				.WorkspaceFolder().getAbsoluteFilePath();

		L.d("workspace_path", workspace_path);

		File output_folder = LocalFileSystem.newFile(java_code_output_path);
		{
			output_folder.makeFolder();
		}
		output_folder.clearFolder();

		List<RelativePath> settings = JUtils.newList();

		settings.add(newPair("jfixby-commons-api"));
		settings.add(newPair("jfixby-commons-red"));
		settings.add(newPair("jfixby-filesystem-unix"));
		settings.add(newPair("jfixby-jutils-desktop"));
		settings.add(newPair("jfixby-log-mq"));
		settings.add(newPair("jfixby-log-desktop"));
		settings.add(newPair("jfixby-math-desktop"));
		settings.add(newPair("jfixby-sys-desktop"));
		settings.add(newPair("red-triplane-fokker-gdx", "tools"));
		settings.add(newPair("red-sputnik-to-activemq-adaptor"));

		for (int i = 0; i < settings.size(); i++) {
			RelativePath element = settings.getElementAt(i);
			String project_name = element.steps().getElementAt(0);
			String source_folder_name = element.steps().getLast();
			L.d("processing", project_name + "/" + source_folder_name);

			AbsolutePath<FileSystem> input_path = workspace_path.child(
					project_name).child(source_folder_name);
			copy_content(input_path, java_code_output_path);
		}

	}

	private static RelativePath newPair(String project_name,
			String source_folder_name) {
		RelativePath pair = JUtils.newRelativePath().child(project_name)
				.child(source_folder_name);
		return pair;
	}

	private static RelativePath newPair(String project_name) {
		return newPair(project_name, project_name);
	}

	private static void copy_content(AbsolutePath<FileSystem> input_path,
			AbsolutePath<FileSystem> output_path) throws IOException {

		File input_folder = LocalFileSystem.newFile(input_path);
		{
			input_folder.listChildren().print();
		}

		File output_folder = LocalFileSystem.newFile(output_path);
		{
			output_folder.makeFolder();
			L.d("Listing",
					((WinFile) output_folder).getAbsoluteWindowsPathString());
			output_folder.listChildren().print();
		}
		// output_folder.clearFolder();

		LocalFileSystem.copyFolderContentsToFolder(input_folder, output_folder);

	}

}
