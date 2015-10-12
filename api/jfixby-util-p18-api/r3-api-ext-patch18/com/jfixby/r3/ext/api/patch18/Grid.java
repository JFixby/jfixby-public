package com.jfixby.r3.ext.api.patch18;

import com.jfixby.r3.ext.api.patch18.grid.GridBrush;

public interface Grid {

	// Cell getCellAt(int x, int y);

	P18Palette getPalette();

	GridBrush getBrush();

	void print(long offset_x, long offset_y, long width, long height);

}
