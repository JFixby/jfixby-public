package com.jfixby.util.terain.test.api.landscape;

import com.jfixby.util.terain.test.api.palette.TerrainType;

public interface TerrainBlock<T> {

	TerrainType getType();

	T getKeyPoint();



}
