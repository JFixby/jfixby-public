package com.jfixby.util.p18t.api;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.r3.ext.api.patch18.Patch18;
import com.jfixby.r3.ext.api.patch18.palette.FabricsRelation;
import com.jfixby.util.terain.test.api.palette.TerrainType;

public interface P18TerrainTypeVariation {

	public TerrainType getProperties();

	public String getName();

	public AssetID getID();

	public Patch18 getShape();

	public FabricsRelation getRelation();

}
