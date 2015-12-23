package com.jfixby.utl.pizza.test;

import com.jfixby.cmns.desktop.DesktopAssembler;
import com.jfixby.examples.wdgs.WDGS_Pizza_Palette;
import com.jfixby.r3.ext.api.patch18.palette.Fabric;
import com.jfixby.utl.pizza.api.Pizza;
import com.jfixby.utl.pizza.api.PizzaBrush;
import com.jfixby.utl.pizza.api.PizzaBrushPointer;
import com.jfixby.utl.pizza.api.PizzaLandscape;
import com.jfixby.utl.pizza.api.PizzaLandscapeFactory;
import com.jfixby.utl.pizza.api.PizzaLandscapeListener;
import com.jfixby.utl.pizza.api.PizzaLandscapeSpecs;
import com.jfixby.utl.pizza.api.PizzaPalette;
import com.jfixby.utl.pizza.api.PizzaTile;

public class TestPizza {

	public static void main(String[] args) {
		DesktopAssembler.setup();

		PizzaPalette wdgs_pizza_palette = WDGS_Pizza_Palette.PALETTE;

		PizzaLandscapeFactory landscape_fac = Pizza.invoke()
				.getPizzaLandscapeFactory();

		PizzaLandscapeSpecs landscape_specs = landscape_fac.newLandscapeSpecs();
		landscape_specs.setActiveArea(0, 0, 1024 * 4, 1024 * 3);
		landscape_specs.setPalette(wdgs_pizza_palette);

		PizzaLandscape pizza_scape = landscape_fac
				.newPizzaLandscape(landscape_specs);

		PizzaLandscapeListener listener = new PizzaLandscapeListener() {

			@Override
			public void onBlockRemove(PizzaTile block) {
			}

			@Override
			public void onBlockAdd(PizzaTile block) {
			}

			@Override
			public void onBlockFocus(PizzaTile block) {
			}

		};

		pizza_scape.setLandscapeListener(listener);

		PizzaBrush brush = pizza_scape.getBrush();
		Fabric default_fabric = wdgs_pizza_palette.getP18Palette()
				.listFabrics().getFabric(0);// size>0
		brush.setFabric(default_fabric);
		{
			PizzaBrushPointer pointer = brush.pointAtCanvas(0, 0);
			// FixedFloat3 pointer_position = pointer.getTerrainPosition();
			brush.applyPaint();
		}

		pizza_scape.print();

	}
}
