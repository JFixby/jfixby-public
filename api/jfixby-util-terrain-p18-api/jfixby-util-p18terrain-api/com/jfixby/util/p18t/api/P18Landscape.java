package com.jfixby.util.p18t.api;

public interface P18Landscape {

	void print();

	P18LandscapeBrush getBrush();

	public void setListener(P18LandscapeListener listener);

	public P18LandscapeListener getListener();

}
