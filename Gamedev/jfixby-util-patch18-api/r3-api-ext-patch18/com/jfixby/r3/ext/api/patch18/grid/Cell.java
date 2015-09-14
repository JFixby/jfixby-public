package com.jfixby.r3.ext.api.patch18.grid;

import com.jfixby.r3.ext.api.patch18.P18;
import com.jfixby.r3.ext.api.patch18.palette.Fabric;

public interface Cell {

	P18 getShape();

	Fabric getUpperFabric();

	Fabric getLowerFabric();
	
	public long getX();
	
	public long getY();

}
