package com.jfixby.util.patch18.red.maze;

public class OriginalGameField {

	final boolean[][] values;
	int w;
	int h;

	public OriginalGameField(int w, int h) {
		values = new boolean[w][h];
		this.w = w;
		this.h = h;
	}

	public int getWidth() {
		// TODO Auto-generated method stub
		return this.w;
	}

	public void setGround(int i, int j, boolean b) {
		// TODO Auto-generated method stub
		if (i < 0 || i >= w || j < 0 || j >= h) {
			return;
		}
		values[i][j] = b;
	}

	public boolean getValue(int i, int j) {
		if (i < 0 || i >= w || j < 0 || j >= h) {
			return use_wall;
		}
		return this.values[i][j];
	}

	public void inverse() {
		// TODO Auto-generated method stub
		for (int i = 0; i < this.w; i++) {
			for (int j = 0; j < this.h; j++) {
				this.values[i][j] = !this.values[i][j];
			}
		}
	}

	public int getHeight() {
		return h;
	}

	boolean use_wall = true;

	public void useWall(boolean b) {
		use_wall = b;
	}

}
