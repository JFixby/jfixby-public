package com.jfixby.r3.ext.api.maze;

public interface MazeFieldSpecs {

	static final public long DEFAULT = -1;

	void setWidth(int w);

	void setHeight(int h);

	int getWidth();

	int getHeight();

	void setRandomizationSeed(long i);

	long getRandomizationSeed();

}
