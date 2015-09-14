package com.jfixby.r3.tools.api.iso;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.filesystem.File;
import com.jfixby.r3.ext.api.patch18.Patch18Palette;
import com.jfixby.r3.ext.terrain.api.TerrainPalette;
import com.jfixby.util.iso.api.IsoTransform;

public interface GeneratorParams {

	void setOutputFolder(File mock_palette_folder);

	File getOutputFolder();

	void setNamespace(AssetID newAssetID);

	AssetID getNamespace();

	void setTerrainPalette(TerrainPalette palette);

	TerrainPalette getTerrainPalette();

	void setPixelsToTileMeter(double pixels_to_tile_meter);

	public double getPixelsToTileMeter();

	void setPatch18Palette(Patch18Palette patch18_palette);

	Patch18Palette getPatch18Palette();

	void setIsoTransform(IsoTransform isometry);

	IsoTransform getIsoTransform();

	void setPadding(int pixels);

	int getPadding();

}
