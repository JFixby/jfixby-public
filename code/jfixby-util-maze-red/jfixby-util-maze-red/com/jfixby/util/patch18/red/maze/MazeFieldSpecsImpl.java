package com.jfixby.util.patch18.red.maze;

import com.jfixby.r3.ext.api.maze.MazeFieldSpecs;

public class MazeFieldSpecsImpl implements MazeFieldSpecs {

	public int getWidth() {
		return Width;
	}

	public void setWidth(int width) {
		Width = width;
	}

	public int getHeight() {
		return Height;
	}

	public void setHeight(int height) {
		Height = height;
	}

	public long getRandomizationSeed() {
		return randomizationSeed;
	}

	public void setRandomizationSeed(long randomizationSeed) {
		this.randomizationSeed = randomizationSeed;
	}

	int Width;
	int Height;
	long randomizationSeed = DEFAULT;
}
