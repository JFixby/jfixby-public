package com.jfixby.util.patch18.red.fields.afield;

import com.jfixby.r3.ext.api.patch18.Patch18;
import com.jfixby.util.patch18.red.fields.BadCell;

public class Patch18Cell implements BadCell {

	public Patch18 value;

	@Override
	public BadCell replicate() {

		return new Patch18Cell().set(value);
	}

	private BadCell set(Patch18 value2) {
		this.value = value2;
		return this;
	}

	@Override
	public BadCell inverse() {

		return new Patch18Cell().set(value.inverse());
	}

	@Override
	public BadCell set(BadCell other) {

		return new Patch18Cell().set(((Patch18Cell) other).value);

	}

}
