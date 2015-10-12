package com.jfixby.util.terain.test.api.landscape;

public interface LandscapeFactory {

	LandscapeSpecs newLandscapeSpecs();

	<T> Landscape<T> newLandscape(LandscapeSpecs landscape_specs);

}
