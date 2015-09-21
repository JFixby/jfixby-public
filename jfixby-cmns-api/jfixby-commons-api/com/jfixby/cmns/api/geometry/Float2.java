package com.jfixby.cmns.api.geometry;

public interface Float2 extends FixedFloat2 {

	Float2 set(double x, double y);

	Float2 setX(double x);

	Float2 setY(double y);

	Float2 set(FixedFloat2 other);

	Float2 setXY();

	Float2 setXY(double x, double y);

	Float2 add(FixedFloat2 offset);

	Float2 addX(double delta);

	Float2 addY(double delta);

	Float2 add(double deltaX, double deltaY);

	void multiply(double factor);

	void multiply(double x, double y);

}
