package com.jfixby.tools.gdx.texturepacker.api;

import com.jfixby.cmns.api.filesystem.File;

public interface TexturePackingSpecs {

	void setInputRasterFolder(File input_raster_folder);

	void setOutputAtlasFolder(File output_atlas_folder);

	File getInputRasterFolder();

	File getOutputAtlasFolder();

	String getAtlasFileName();

	void setOutputAtlasFileName(String file_name);

	void setDebugMode(boolean b);

	boolean getDebugMode();

}
