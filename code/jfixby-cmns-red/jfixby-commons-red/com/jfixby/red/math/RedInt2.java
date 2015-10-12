package com.jfixby.red.math;

import com.jfixby.cmns.api.math.Int2;

public class RedInt2 implements Int2 {
	long x;
	long y;

	public RedInt2(long cell_x, long cell_y) {
		x = cell_x;
		y = cell_y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (x ^ (x >>> 32));
		result = prime * result + (int) (y ^ (y >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RedInt2 other = (RedInt2) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	public RedInt2() {
		this(0, 0);
	}

	@Override
	public long getX() {
		return this.x;
	}

	@Override
	public long getY() {
		return this.y;
	}

	@Override
	public void setX(long x) {
		this.x = x;
	}

	@Override
	public void setY(long y) {
		this.y = y;
	}

}
