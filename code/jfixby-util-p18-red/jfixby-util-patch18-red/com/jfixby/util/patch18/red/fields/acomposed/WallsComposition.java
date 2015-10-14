package com.jfixby.util.patch18.red.fields.acomposed;

import com.jfixby.cmns.api.collections.List;
import com.jfixby.r3.ext.api.patch18.GridActiveArea;
import com.jfixby.r3.ext.api.patch18.Patch18;
import com.jfixby.r3.ext.api.patch18.palette.Fabric;
import com.jfixby.util.patch18.red.materials.JointLayer;

public class WallsComposition {

	final private List<JointLayer> layers;
	private GridActiveArea active_area;

	public WallsComposition(List<JointLayer> jointLayers,
			Fabric default_fabric, GridActiveArea active_area) {

		this.layers = jointLayers;

		this.active_area = active_area;

	}

	public ComposedTile getValue(long cell_x, long cell_y) {
		long grid_x = toGridX(cell_x);
		long grid_y = toGridY(cell_y);

		Patch18 shape = null;
		JointLayer definitive_layer = extract(grid_x, grid_y, layers);
		if (definitive_layer == null) {
			if (this.active_area.isInside(cell_x, cell_y)) {
				shape = Patch18.ERR;
			} else {
				shape = Patch18.Irrelevant;
			}
			definitive_layer = layers.getLast();
			definitive_layer = layers.getElementAt(0);
		} else {
			shape = definitive_layer.getWallShape(grid_x, grid_y);
		}

		// if (this.active_area.isBorder(cell_x, cell_y)) {
		// shape = P18.Irrelevant;
		// } else {
		// if (definitive_layer == null) {
		// shape = P18.ERR;
		// definitive_layer = layers.getLast();
		// definitive_layer = layers.get(0);
		// } else {
		// shape = definitive_layer.getWallShape(grid_x, grid_y);
		// }
		// }

		Fabric materialForeground = definitive_layer.getForegroundMaterial();
		Fabric materialBackground = definitive_layer.getBackgroundMaterial();
		ComposedTile value = new ComposedTile();
		value.setForegroundMaterial(materialForeground);
		value.setBackgroundMaterial(materialBackground);
		value.setShape(shape);
		return value;

	}

	private long toGridY(long cell_y) {
		return cell_y;
	}

	private long toGridX(long cell_x) {
		return cell_x;
	}

	private JointLayer extract(long grid_x, long grid_y, List<JointLayer> layers) {
		for (int i = 0; i < layers.size(); i++) {
			JointLayer layer = layers.getElementAt(i);
			Patch18 shape = layer.getWallShape(grid_x, grid_y);
			if (!shape.isIrrelevant()) {
				return layer;
			}
		}
		return null;
	}

}
