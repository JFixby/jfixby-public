package com.jfixby.util.patch18.red;

import com.jfixby.r3.ext.api.patch18.GridActiveArea;
import com.jfixby.r3.ext.api.patch18.GridSpecs;
import com.jfixby.r3.ext.api.patch18.P18Palette;

class GridSpecsImpl implements GridSpecs {
	P18Palette palette;

	GridActiveArea activeGridArea;

	public P18Palette getPalette() {
		return palette;
	}

	public void setPalette(P18Palette palette) {
		this.palette = palette;
	}

	public GridActiveArea getActiveGridArea() {
		return activeGridArea;
	}

	public void setActiveGridArea(GridActiveArea activeGridArea) {
		this.activeGridArea = activeGridArea;
	}

}
