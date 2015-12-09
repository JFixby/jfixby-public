package com.jfixby.tool.psd2scene2d;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.file.File;

public class PSDRepackSettings {

	private File psd_file;
	private AssetID package_name;
	private File output_folder;
	private int max_texture_size;
	private int margin;
	private boolean ignore_atlas;
	

	public void setPSDFile(File psd_file) {
		this.psd_file = psd_file;
	}

	public void setPackageName(AssetID package_name) {
		this.package_name = package_name;
	}

	public void setOutputFolder(File output_folder) {
		this.output_folder = output_folder;
	}

	public void setMaxTextureSize(int max_texture_size) {
		this.max_texture_size = max_texture_size;
	}

	public void setMargin(int margin) {
		this.margin = margin;
	}

	public void setIgonreAtlasFlag(boolean ignore_atlas) {
		this.ignore_atlas = ignore_atlas;
	}

	public AssetID getPackageName() {
		return this.package_name;
	}

	public File getPSDFile() {
		return this.psd_file;
	}

	public boolean getIgnoreAtlasFlag() {
		return this.ignore_atlas;
	}

	public File getOutputFolder() {
		return this.output_folder;
	}

	public int getMaxTextureSize() {
		return this.max_texture_size;
	}

	public int getMargin() {
		return this.margin;
	}


}
