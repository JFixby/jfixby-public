package com.jfixby.r3.ext.api.maze;

public interface MazeField {
	public abstract int getCellsHeight();

	public abstract int getCellsWidth();

	public abstract BrickValue getCellValue(int x, int y);

	public abstract BrickValue getCellWall(int x, int y, Direction d);

	public abstract void print();

	public abstract int getBricksWidth();

	public abstract int getBricksHeight();

	public abstract BrickValue getBrickValue(int x, int y);
}
