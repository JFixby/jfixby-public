package com.jfixby.util.patch18.red.fields.bool;

import com.jfixby.r3.ext.api.patch18.palette.Fabric;
import com.jfixby.util.patch18.red.FabricsLevel;

public class BoolFunction {

	private Fabric falseColor;
	private Fabric trueColor;
	private FabricsLevel fabric_function;

	public BoolFunction(FabricsLevel fabric_function, Fabric trueColor,
			Fabric falseColor) {
		super();
		this.fabric_function = fabric_function;
		this.falseColor = falseColor;
		this.trueColor = trueColor;
		if (this.trueColor == null) {
			throw new Error();
		}
		if (this.falseColor == null) {
			throw new Error();
		}
	}

	public BOOL getValue(long node_x, long node_y) {

		Fabric color_value = fabric_function.getValueAt(node_x, node_y);
		if (color_value == trueColor) {
			return BOOL.TRUE;
		} else if (color_value == falseColor) {
			return BOOL.FALSE;
		} else {
			return BOOL.UNKNOWN;
		}
	}

}
