package com.jfixby.cmns.api.geometry;

public interface Vertex extends GeometryFigure, FixedFloat2 {

	Edge getRightEdge();

	Edge getLeftEdge();

	public FixedFloat2 world();

	public Float2 relative();

}
