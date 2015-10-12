package com.jfixby.r3.ext.terrain.api.landscape;

public interface LandscapeFactory {

	LandscapeSpecs newLandscapeSpecs();

	<T> Landscape<T> newLandscape(LandscapeSpecs landscape_specs);

}
