package com.jfixby.util.patch18.red;

import com.jfixby.cmns.api.collections.JUtils;
import com.jfixby.cmns.api.collections.ZxZ_Functuion;
import com.jfixby.r3.ext.api.patch18.palette.Fabric;

public class FabricsLevel {

	// final Map<IndexedPosition, FabricPoint> function_set =
	// RedTriplane.newMap();

	final ZxZ_Functuion<FabricPoint> function_set = JUtils.newZxZ_Function();
	private GridImpl grid;

	public FabricsLevel(GridImpl gridImpl) {
		this.grid = gridImpl;

	}

	public boolean set(long grid_node_x, long grid_node_y, Fabric current_fabric) {
		boolean changed = false;
		FabricPoint value = this.function_set.getValueAt(grid_node_x,
				grid_node_y);

		if (value == null) {
			value = new FabricPoint(current_fabric, grid_node_x, grid_node_y);
			this.function_set.setValueAt(grid_node_x, grid_node_y, value);
			changed = true;
		} else {
			changed = value.setFabricValue(current_fabric);
		}

		return changed;

	}

	public Fabric getValueAt(final long grid_node_x, final long grid_node_y) {
		// final IndexedPosition position = newIndexedPosition(grid_node_x,
		// grid_node_y);
		final FabricPoint value = this.function_set.getValueAt(grid_node_x,
				grid_node_y);
		if (value != null) {
			return value.fabricValue;
		}
		return null;
	}
}
