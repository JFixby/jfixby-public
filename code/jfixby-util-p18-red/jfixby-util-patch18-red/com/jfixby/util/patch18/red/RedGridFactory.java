package com.jfixby.util.patch18.red;

import com.jfixby.r3.ext.api.patch18.Grid;
import com.jfixby.r3.ext.api.patch18.GridFactory;
import com.jfixby.r3.ext.api.patch18.GridSpecs;

public class RedGridFactory implements GridFactory {

	public GridSpecs newGridSpecs() {
		return new GridSpecsImpl();
	}

	public Grid newGrid(GridSpecs grid_specs) {
		return new GridImpl( grid_specs);
	}

}
