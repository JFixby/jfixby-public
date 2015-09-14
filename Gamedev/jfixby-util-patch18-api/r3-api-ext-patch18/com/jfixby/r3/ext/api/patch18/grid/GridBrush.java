package com.jfixby.r3.ext.api.patch18.grid;

import com.jfixby.r3.ext.api.patch18.palette.Fabric;

public interface GridBrush {

	void setFabric(Fabric fabric);

	public void setPaintAtNode(int grid_node_x, int grid_node_y);

	void clearHistory();

	void revertBack();

	void begin();

	BrushApplicationResult end();

	void setSize(int brush_size);

	void refresh();

	void applyPaintAtCell(int cell_x, int cell_y);

}
