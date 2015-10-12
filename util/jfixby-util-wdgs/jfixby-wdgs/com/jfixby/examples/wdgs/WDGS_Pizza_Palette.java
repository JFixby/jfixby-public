package com.jfixby.examples.wdgs;

import com.jfixby.util.iso.api.IsoTransform;
import com.jfixby.util.iso.api.Isometry;
import com.jfixby.utl.pizza.api.Pizza;
import com.jfixby.utl.pizza.api.PizzaPalette;
import com.jfixby.utl.pizza.api.PizzaPaletteFactory;
import com.jfixby.utl.pizza.api.PizzaPaletteSpecs;

public class WDGS_Pizza_Palette {

	static {
		PizzaPaletteFactory palette_factory = Pizza.invoke()
				.getPizzaPaletteFactory();

		PizzaPaletteSpecs specs = palette_factory.newPizzaPaletteSpecs();
		specs.setP18TerrainPalette(WDGS_P18Terrain_Palette.P18_TERRAIN_PALETTE);

		IsoTransform fallout_iso = Isometry.getFallout(256);
		specs.setIsoTransform(fallout_iso);

		PizzaPalette pizza_palette = palette_factory.newPizzaPalette(specs);
		PALETTE = pizza_palette;
	}

	public static final PizzaPalette PALETTE;

}
