package com.jfixby.util.patch18.red.materials;

import com.jfixby.r3.ext.api.patch18.palette.Fabric;
import com.jfixby.util.patch18.red.fields.afield.WallFunction;

public class JointLayerSchematic {
	Fabric ForegroundMaterial;
	Fabric BackgroundMaterial;
	WallFunction walls;

	public Fabric getForegroundMaterial() {
		return ForegroundMaterial;
	}

	public void setForegroundMaterial(Fabric foregroundMaterial) {
		ForegroundMaterial = foregroundMaterial;
	}

	public Fabric getBackgroundMaterial() {
		return BackgroundMaterial;
	}

	public void setBackgroundMaterial(Fabric backgroundMaterial) {
		BackgroundMaterial = backgroundMaterial;
	}

	public WallFunction getWalls() {
		return walls;
	}

	public void setWalls(WallFunction walls) {
		this.walls = walls;
	}

}
