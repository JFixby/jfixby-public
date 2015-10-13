package com.jfixby.r3.ext.api.patch18.grid;

import com.jfixby.cmns.api.math.FixedInt2;
import com.jfixby.r3.ext.api.patch18.palette.Fabric;

public interface GridBrush {

	void setFabric(Fabric fabric);

	public void setPaintAtNode(long grid_node_x, long grid_node_y);

	void clearHistory();

	void revertBack();

	void begin();

	GridBrushApplicationResult end();

	void setSize(int brush_size);

	void refresh();

	void applyPaintAtCell(long cell_x, long cell_y);

	void applyPaintAtCell(FixedInt2 cell_xy);

}
