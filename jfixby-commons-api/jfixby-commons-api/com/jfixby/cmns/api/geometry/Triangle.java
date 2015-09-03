package com.jfixby.cmns.api.geometry;

public interface Triangle {

	public Vertex A();

	public Vertex B();

	public Vertex C();

	public boolean containsPoint(double worldx, double worldy);

	public boolean containsPoint(FixedFloat2 point);

}
