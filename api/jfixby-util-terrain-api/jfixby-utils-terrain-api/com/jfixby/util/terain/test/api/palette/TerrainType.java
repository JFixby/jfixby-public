package com.jfixby.util.terain.test.api.palette;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.gamedev.GameMeterFixed;

public interface TerrainType {

	public AssetID getID();

	public GameMeterFixed getHeight();

	public GameMeterFixed getAltitude();

	public GameMeterFixed getXWidth();

	public GameMeterFixed getYWidth();

}
