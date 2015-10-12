package com.jfixby.r3.ext.api.patch18.grid;

import com.jfixby.r3.ext.api.patch18.Patch18;
import com.jfixby.r3.ext.api.patch18.palette.Fabric;

public interface Cell {

	public Patch18 getShape();

	public Fabric getUpperFabric();

	public Fabric getLowerFabric();

	public CellPosition getPosition();

}
