package com.jfixby.r3.ext.terrain.api;

import com.jfixby.cmns.api.assets.AssetID;

public interface TerrainBlockSpecs {

	void setName(AssetID name);

	AssetID getName();

	void setZHeight(TerrainMeasurement block_z_height);

	TerrainMeasurement getZHeight();

	TerrainMeasurement getXWidth();

	void setXWidth(TerrainMeasurement block_x_width);

	void setYWidth(TerrainMeasurement block_y_width);

	TerrainMeasurement getYWidth();

	void setZAltitude(TerrainMeasurement block_z_altitude);

	TerrainMeasurement getZAltitude();

}
