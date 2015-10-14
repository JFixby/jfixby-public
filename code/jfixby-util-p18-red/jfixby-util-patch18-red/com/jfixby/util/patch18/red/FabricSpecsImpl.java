package com.jfixby.util.patch18.red;

import com.jfixby.r3.ext.api.patch18.palette.FabricSpecs;

class FabricSpecsImpl implements FabricSpecs {

	public String getFabricName() {
		return fabricName;
	}

	public void setFabricName(String fabricName) {
		this.fabricName = fabricName;
	}

	private String fabricName;
}
