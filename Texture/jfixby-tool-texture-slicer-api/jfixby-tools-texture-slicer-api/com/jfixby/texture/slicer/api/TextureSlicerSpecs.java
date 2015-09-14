package com.jfixby.texture.slicer.api;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.filesystem.FileSystem;
import com.jfixby.cmns.api.path.AbsolutePath;

public interface TextureSlicerSpecs {

	public static final int MIN_TILE_SIZE = 32;

	public static final String TILE_MAP_FILE_EXTENSION = "r3-trs";

	void setInputFilePath(AbsolutePath<FileSystem> png_file);

	void setTileWidth(int tile_width);

	void setTileHeight(int tile_height);

	void setNameSpacePrefix(AssetID newAssetID);

	void setOutputFolderPath(AbsolutePath<FileSystem> parent);

	void setMargin(int margin);

	public AbsolutePath<FileSystem> getInputFilePath();

	public int getTileWidth();

	public int getTileHeight();

	public AssetID getNameSpacePrefix();

	public AbsolutePath<FileSystem> getOutputFolderPath();

	public int getMargin();

}
