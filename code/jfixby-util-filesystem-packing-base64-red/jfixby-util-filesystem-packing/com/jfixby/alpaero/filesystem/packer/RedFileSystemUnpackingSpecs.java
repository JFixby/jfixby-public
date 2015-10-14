package com.jfixby.alpaero.filesystem.packer;

import com.jfixby.cmns.api.filesystem.File;
import com.jfixby.cmns.api.filesystem.packing.FileSystemUnpackingSpecs;
import com.jfixby.cmns.api.io.InputStream;

public class RedFileSystemUnpackingSpecs implements FileSystemUnpackingSpecs {

	private InputStream inputStream;
	private File target_folder;

	public void setTargetFolder(File target_folder) {
		this.target_folder = target_folder;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public File getTargetFolder() {
		return target_folder;
	}

	public InputStream getInputStream() {
		return inputStream;
	}


}
