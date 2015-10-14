package com.jfixby.util.patch18.red.fields;

import com.jfixby.util.patch18.red.fields.afield.WallFunction;
import com.jfixby.util.patch18.red.fields.afield.WallsFieldSchematic;

public class FieldsFactory {

	public static WallsFieldSchematic newWallsFieldProperties() {
		return new WallsFieldSchematic();
	}

	public static WallFunction newWallsField(
			WallsFieldSchematic field_properties) {
		// return new WallsField(field_properties);
		return new WallFunction();

	}

}
