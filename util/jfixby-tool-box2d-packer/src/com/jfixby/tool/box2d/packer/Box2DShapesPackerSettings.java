package com.jfixby.tool.box2d.packer;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.filesystem.File;

public class Box2DShapesPackerSettings {

	private File i_file;
	private File output_folder;
	private AssetID package_name;

	public void setInputFile(File i_file) {
		this.i_file = i_file;
	}

	public void setOutputFolder(File output_folder) {
		this.output_folder = output_folder;
	}

	public File getOutputFolder() {
		return this.output_folder;
	}

	public void setPackageName(AssetID package_name) {
		this.package_name = package_name;
	}

	public AssetID getPackageName() {
		return this.package_name;
	}

	public File getInputFile() {
		return i_file;
	}

}
