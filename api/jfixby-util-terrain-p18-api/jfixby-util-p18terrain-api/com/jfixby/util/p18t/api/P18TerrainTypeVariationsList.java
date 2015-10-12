package com.jfixby.util.p18t.api;

import com.jfixby.r3.ext.api.patch18.Patch18;
import com.jfixby.r3.ext.api.patch18.palette.FabricsRelation;

public interface P18TerrainTypeVariationsList {

	P18TerrainTypeVariation getRandomElement();

	FabricsRelation getRelation();

	Patch18 getShape();

	void print(String tag);

	int size();

	P18TerrainTypeVariation getVariation(int k);

}
