package com.jfixby.r3.ext.api.patch18;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.Collections;

public enum Patch18 {

	RightBottom(" ╔┬"), RightLeft("┬╤┬"), BottomLeft("┬╗ "), //
	TopBottom(" ╠┼"), Blocked("┼╪┼"), BottomTop("┼╣ "), //
	TopRight(" ╚┴"), LeftRight("┴╩┴"), LeftTop("┴╝ "), //

	BottomRight("┼┌┴"), LeftBottom("┴┐┼"), //
	Free(" ▫ "), //
	RightTop("┼└┬"), TopLeft("┬┘┼"), //
	BridgeLeft("-\\-"), BridgeRight("-/-"), //

	ERR("█?█"), Irrelevant("███"), //
	;

	public static final Collection<Patch18> ALL_VALUES = collect();

	public String shapeSignature() {
		return this.toString();
	}

	final private static Collection<Patch18> collect() {
		return Collections.newList(Patch18.values());
	}

	final private String char_value;

	public String getChar() {
		return char_value;
	}

	Patch18(String char_value) {
		this.char_value = char_value;

	}

	public Patch18 inverse() {
		if (this == RightBottom) {
			return BottomRight;
		}
		if (this == BottomRight) {
			return RightBottom;
		}
		if (this == RightLeft) {
			return LeftRight;
		}
		if (this == LeftRight) {
			return RightLeft;
		}
		if (this == BottomLeft) {
			return LeftBottom;
		}
		if (this == LeftBottom) {
			return BottomLeft;
		}
		if (this == TopBottom) {
			return BottomTop;
		}
		if (this == BottomTop) {
			return TopBottom;
		}
		if (this == Blocked) {
			return Free;
		}
		if (this == Free) {
			return Blocked;
		}
		if (this == TopRight) {
			return RightTop;
		}
		if (this == RightTop) {
			return TopRight;
		}
		if (this == LeftTop) {
			return TopLeft;
		}
		if (this == TopLeft) {
			return LeftTop;
		}
		return null;
	}

	public boolean isDefinitive() {
		return !(this == ERR || this == Irrelevant);
	}

	public boolean isJoint() {
		if (!this.isDefinitive()) {
			return false;
		}
		return !(this == Free || this == Blocked);
	}

	public boolean isLookingUp() {
		if (!this.isJoint()) {
			return false;
		}
		return (this == Patch18.RightLeft);
	}

	public boolean isLookingDown() {
		if (!this.isJoint()) {
			return false;
		}
		return (this == Patch18.LeftRight);
	}

	public boolean isLookingLeft() {
		if (!this.isJoint()) {
			return false;
		}
		return (this == Patch18.TopBottom);
	}

	public boolean isLookingRight() {
		if (!this.isJoint()) {
			return false;
		}
		return (this == Patch18.BottomTop);
	}

	public boolean isTopLeftCorner() {
		return this == BottomRight;
	}

	public boolean isTopRightCorner() {
		return this == LeftBottom;
	}

	public boolean isBottomRightCorner() {
		return this == TopLeft;
	}

	public boolean isBottomLeftCorner() {
		return this == RightTop;
	}

	public boolean isRightBridge() {
		return this == BridgeRight;
	}

	public boolean isLeftBridge() {
		return this == BridgeLeft;
	}

	public boolean isLookingUpRight() {
		return this == BottomLeft;
	}

	public boolean isLookingUpLeft() {
		return this == RightBottom;
	}

	public boolean isLookingDownRight() {
		return this == LeftTop;
	}

	public boolean isLookingDownLeft() {
		return this == TopRight;
	}

	public String toIDString() {
		return this.toString().toLowerCase();
	}

	public boolean isFree() {
		return this == Free;
	}

	public boolean isBlocked() {
		return this == Blocked;
	}

	public boolean isIrrelevant() {
		return this == Irrelevant;
	}

	public boolean isErr() {
		return this == ERR;
	}

}
