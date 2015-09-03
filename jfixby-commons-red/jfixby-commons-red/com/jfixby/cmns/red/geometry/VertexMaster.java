package com.jfixby.cmns.red.geometry;

import com.jfixby.cmns.api.geometry.CanvasTransform;
import com.jfixby.cmns.api.geometry.GeometryFigure;

public class VertexMaster implements GeometryFigure {

	final private RedTransform transform = new RedTransform();

	@Override
	public CanvasTransform getTransform() {

		return transform;
	}

}
