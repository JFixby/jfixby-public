package com.jfixby.texture.slicer.red;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.filesystem.FileSystem;
import com.jfixby.cmns.api.path.AbsolutePath;
import com.jfixby.texture.slicer.api.TextureSlicerSpecs;

public class RedSlicerSpecs implements TextureSlicerSpecs {

	@Override
	public String toString() {
		return "[inputFilePath=" + inputFilePath
				+ ", tileWidth=" + tileWidth + ", tileHeight=" + tileHeight
				+ ", nameSpacePrefix=" + nameSpacePrefix
				+ ", outputFolderPath=" + outputFolderPath
				+ ", compositionStructureOutputFileName="
				+ compositionStructureOutputFileName + ", margin=" + margin
				+ "]";
	}

	AbsolutePath<FileSystem> inputFilePath;
	int tileWidth = 128;
	int tileHeight = 128;
	AssetID nameSpacePrefix;
	AbsolutePath<FileSystem> outputFolderPath;
	String compositionStructureOutputFileName;
	int margin = 1;

	public AbsolutePath<FileSystem> getInputFilePath() {
		return inputFilePath;
	}

	public void setInputFilePath(
			AbsolutePath<FileSystem> inputFilePath) {
		this.inputFilePath = inputFilePath;
	}

	public int getTileWidth() {
		return tileWidth;
	}

	public void setTileWidth(int tileWidth) {
		this.tileWidth = tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}

	public void setTileHeight(int tileHeight) {
		this.tileHeight = tileHeight;
	}

	public AssetID getNameSpacePrefix() {
		return nameSpacePrefix;
	}

	public void setNameSpacePrefix(AssetID nameSpacePrefix) {
		this.nameSpacePrefix = nameSpacePrefix;
	}

	public AbsolutePath<FileSystem> getOutputFolderPath() {
		return outputFolderPath;
	}

	public void setOutputFolderPath(
			AbsolutePath<FileSystem> outputFolderPath) {
		this.outputFolderPath = outputFolderPath;
	}

	public String getCompositionStructureOutputFileName() {
		return compositionStructureOutputFileName;
	}

	public void setCompositionStructureOutputFileName(
			String compositionStructureOutputFileName) {
		this.compositionStructureOutputFileName = compositionStructureOutputFileName;
	}

	public int getMargin() {
		return margin;
	}

	public void setMargin(int margin) {
		this.margin = margin;
	}

}
