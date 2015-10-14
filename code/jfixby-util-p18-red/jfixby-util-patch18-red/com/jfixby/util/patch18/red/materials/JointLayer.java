package com.jfixby.util.patch18.red.materials;

import com.jfixby.r3.ext.api.patch18.Patch18;
import com.jfixby.r3.ext.api.patch18.palette.Fabric;
import com.jfixby.util.patch18.red.fields.afield.WallFunction;

public class JointLayer {

	final private Fabric materialF;
	final private Fabric materialB;;
	private WallFunction walls;

	public Fabric getForegroundMaterial() {
		return materialF;
	}

	public Fabric getBackgroundMaterial() {
		return materialB;
	}

	public JointLayer(JointLayerSchematic material_layer_schematic) {
		materialF = material_layer_schematic.getForegroundMaterial();
		materialB = material_layer_schematic.getBackgroundMaterial();
		walls = material_layer_schematic.getWalls();
	}

	public Patch18 getWallShape(long grid_x, long grid_y) {
		return walls.getValue(grid_x, grid_y).value;
	}

}
