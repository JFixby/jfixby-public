package com.jfixby.util.patch18.red;

import com.jfixby.r3.ext.api.patch18.palette.Fabric;
import com.jfixby.r3.ext.api.patch18.palette.FabricRelationSpecs;

class FacricRelationSpecsImpl implements FabricRelationSpecs {

	public Fabric getUpperFabric() {
		return upperFabric;
	}

	public void setUpperFabric(Fabric upperFabric) {
		this.upperFabric = upperFabric;
	}

	public Fabric getLowerFabric() {
		return lowerFabric;
	}

	public void setLowerFabric(Fabric lowerFabric) {
		this.lowerFabric = lowerFabric;
	}

	Fabric upperFabric;
	Fabric lowerFabric;
}
