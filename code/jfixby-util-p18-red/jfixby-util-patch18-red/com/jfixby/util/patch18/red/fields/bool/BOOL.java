package com.jfixby.util.patch18.red.fields.bool;

import com.jfixby.util.patch18.red.fields.BadCell;

public enum BOOL implements BadCell {
	FALSE("[ ]"), TRUE("[■]"), UNKNOWN("[?]"),
//	OTHER("[x]"),
	// OUTSIDE_TRUE("[■]"), OUTSIDE_FALSE("[□]");
	;
	private String char_value;

	public String getChar() {
		return char_value;
	}

	BOOL(String char_value) {
		this.char_value = char_value;
	}

	public BOOL inverse() {
		if (this == TRUE)
			return FALSE;
		if (this == FALSE)
			return TRUE;
		return UNKNOWN;
	}

	public boolean isUnknown() {
		return this == UNKNOWN;
	}

	public boolean isFalse() {
		return this == FALSE;
	}

	public boolean isTrue() {
		return this == TRUE;
	}

	@Override
	public BadCell replicate() {
		return this;
	}

	@Override
	public BadCell set(BadCell other) {
		return other;
	}

//	public boolean isOther() {
//		return this == OTHER;
//	}

}
