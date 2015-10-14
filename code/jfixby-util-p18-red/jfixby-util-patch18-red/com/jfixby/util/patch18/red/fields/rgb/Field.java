package com.jfixby.util.patch18.red.fields.rgb;

import com.jfixby.util.patch18.red.fields.BadCell;

public abstract class Field<T extends BadCell> {

	final private BadCell[][] values;
	final private int w;
	final private int h;

	final private T default_outside_value;

	public Field(int w, int h, T default_inside_value, T default_outside_value) {
		this.w = w;
		this.h = h;
		this.default_outside_value = default_outside_value;
		values = new BadCell[w][h];
		for (int i = 0; i < this.w; i++) {
			for (int j = 0; j < this.h; j++) {
				this.values[i][j] = default_inside_value.replicate();
			}
		}
	}

	public void setValue(int x, int y, BadCell val) {
		if (x < 0 || x >= w || y < 0 || y >= h) {
			return;
		}
		values[x][y] = values[x][y].set(val);
	}

	public T getValue(int x, int y) {
		if (x < 0 || x >= w || y < 0 || y >= h) {
			return default_outside_value;
		}
		return (T) this.values[x][y];
	}

	public int getWidth() {
		return this.w;
	}

	public int getHeight() {
		return this.h;
	}

	public void inverse() {
		for (int i = 0; i < this.getWidth(); i++) {
			for (int j = 0; j < this.getHeight(); j++) {
				this.setValue(i, j, this.getValue(i, j).inverse());
			}
		}
	}

}
