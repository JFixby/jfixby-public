package com.jfixby.util.patch18.red;

import com.jfixby.r3.ext.api.patch18.GridFactory;
import com.jfixby.r3.ext.api.patch18.P18Component;
import com.jfixby.r3.ext.api.patch18.P18PaletteFactory;

public class RedP18 implements P18Component {

	final RedP18PaletteFactory palette_fac = new RedP18PaletteFactory();
	final RedGridFactory grid_fac = new RedGridFactory();

	@Override
	public P18PaletteFactory getPaletteFactory() {
		return palette_fac;
	}

	@Override
	public GridFactory getGridFactory() {
		return grid_fac;
	}

}
