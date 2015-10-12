package com.jfixby.utl.pizza.api;

import com.jfixby.cmns.api.floatn.FixedFloat2;

public interface LandscapeActiveArea {

	FixedFloat2 getTopLeftCorner();

	FixedFloat2 getTopRightCorner();

	FixedFloat2 getBottomLeftCorner();

	FixedFloat2 getBottomRightCorner();

}
