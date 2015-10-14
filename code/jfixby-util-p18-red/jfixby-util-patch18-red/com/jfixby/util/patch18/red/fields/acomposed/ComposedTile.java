package com.jfixby.util.patch18.red.fields.acomposed;

import com.jfixby.cmns.api.log.L;
import com.jfixby.r3.ext.api.patch18.Patch18;
import com.jfixby.r3.ext.api.patch18.palette.Fabric;
import com.jfixby.util.patch18.red.fields.BadCell;

public class ComposedTile implements BadCell {

	public ComposedTile() {
		super();
	}

	public int getxIndex() {
		return xIndex;
	}

	public void setxIndex(int xIndex) {
		this.xIndex = xIndex;
	}

	public int getyIndex() {
		return yIndex;
	}

	public void setyIndex(int yIndex) {
		this.yIndex = yIndex;
	}

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

	public Patch18 getShape() {
		return shape;
	}

	public void setShape(Patch18 shape) {
		this.shape = shape;
	}

	int xIndex;
	int yIndex;
	Fabric ForegroundMaterial;
	Fabric BackgroundMaterial;
	Patch18 shape;

	@Override
	public ComposedTile replicate() {
		return new ComposedTile().set(this);
	}

	@Override
	public ComposedTile inverse() {
		L.e(this.toString(), "Operation is not supported.");
		return this;
	}

	@Override
	public ComposedTile set(BadCell other) {
		return (ComposedTile) other;
	}

	public Fabric getUpperFabric() {
		return ForegroundMaterial;
	}

	public Fabric getLowerFabric() {
		return BackgroundMaterial;
	}

}
