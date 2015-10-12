package com.jfixby.r3.tools.grid.test;

import com.jfixby.examples.wdgs.WDGS_P18_Palette;
import com.jfixby.r3.ext.api.patch18.Grid;
import com.jfixby.r3.ext.api.patch18.GridSpecs;
import com.jfixby.r3.ext.api.patch18.grid.GridBrush;
import com.jfixby.r3.ext.api.patch18.grid.GridBrushApplicationResult;
import com.jfixby.util.patch18.red.RedGridFactory;

public class GridTest {

	public static void main(String[] args) {
		Setup.setup();

		RedGridFactory factory = new RedGridFactory();

		GridSpecs grid_specs = factory.newGridSpecs();
		grid_specs.setPalette(WDGS_P18_Palette.PALETTE);
		grid_specs.setActiveGridArea(null);

		Grid grid = factory.newGrid(grid_specs);

		GridBrush brush = grid.getBrush();
		brush.begin();
		brush.applyPaintAtCell(5, 5);
		brush.applyPaintAtCell(8, 8);
		brush.applyPaintAtCell(8, 5);

		GridBrushApplicationResult result = brush.end();
		grid.print(0, 0, 30, 20);
	}

}
