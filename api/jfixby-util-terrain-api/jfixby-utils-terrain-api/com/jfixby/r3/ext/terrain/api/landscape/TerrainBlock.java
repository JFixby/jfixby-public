package com.jfixby.r3.ext.terrain.api.landscape;

import com.jfixby.r3.ext.terrain.api.palette.TerrainType;

public interface TerrainBlock<T> {

	TerrainType getType();

	T getKeyPoint();



}
