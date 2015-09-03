package com.jfixby.cmns.api.geometry;

import com.jfixby.cmns.api.collections.Collection;

public interface ClosedPolygonalChain extends GeometryFigure {

	int size();

	Vertex getVertex(int index);

	public void clear();

//	 public void setup(Collection<? extends Dot> vertices);

	//	 public void addVertices(Collection<? extends Dot> vertices);

	// public void addVertex(Dot vertex);

	public void setSize(int n);

	public PolyTriangulation getTriangulation();

	public Collection<Vertex> listVertices();

	void addVertex(FixedFloat2 point);

}
