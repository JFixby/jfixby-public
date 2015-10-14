package com.jfixby.alpaero.filesystem.packer;

import com.jfixby.cmns.api.filesystem.File;
import com.jfixby.cmns.api.filesystem.packing.FileSystemPackingSpecs;
import com.jfixby.cmns.api.io.OutputStream;

public class RedFileSystemPackingSpecs implements FileSystemPackingSpecs {

	private OutputStream outputStream;
	private File target_folder;

	public void setTargetFolder(File target_folder) {
		this.target_folder = target_folder;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public File getTargetFolder() {
		return target_folder;
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}

	
}
