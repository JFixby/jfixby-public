package com.jfixby.red.math;

import com.jfixby.cmns.api.math.Int2;

public class RedInt2 implements Int2 {
	long x;
	long y;

	public RedInt2(long cell_x, long cell_y) {
		x = cell_x;
		y = cell_y;
	}

	public RedInt2() {
		this(0, 0);
	}

	@Override
	public long x_index() {
		return this.x;
	}

	@Override
	public long y_index() {
		return this.y;
	}

}
