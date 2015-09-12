package com.jfixby.red.geometry;

import com.jfixby.cmns.api.geometry.Float3;

public class RedPoint3 implements Float3 {

	double x, y, z;

	public RedPoint3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public double getZ() {
		return z;
	}

	@Override
	public void setXYZ(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

}
