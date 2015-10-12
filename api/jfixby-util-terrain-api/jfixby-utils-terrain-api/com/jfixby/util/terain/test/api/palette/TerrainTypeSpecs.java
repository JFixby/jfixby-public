package com.jfixby.util.terain.test.api.palette;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.gamedev.GameMeter;

public interface TerrainTypeSpecs {

	void setName(AssetID name);

	AssetID getName();

	void setZHeight(GameMeter block_z_height);

	GameMeter getZHeight();

	GameMeter getXWidth();

	void setXWidth(GameMeter block_x_width);

	void setYWidth(GameMeter block_y_width);

	GameMeter getYWidth();

	void setZAltitude(GameMeter block_z_altitude);

	GameMeter getZAltitude();

}
