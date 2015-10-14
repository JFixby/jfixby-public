package com.jfixby.util.patch18.red;

import com.jfixby.r3.ext.api.patch18.palette.Fabric;

public class FabricPoint {
	long X;
	long Y;
	Fabric fabricValue;
	boolean affected = false;

	public FabricPoint(Fabric value, long i, long j) {
		this.fabricValue = value;
		this.X = i;
		this.Y = j;
	}

	public boolean setFabricValue(Fabric current_fabric) {
		this.affected = (fabricValue != current_fabric);
		this.fabricValue = current_fabric;
		return affected;
	}

	public Fabric getFabricValue() {
		return fabricValue;
	}

}
