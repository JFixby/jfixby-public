package com.jfixby.util.patch18.red.core;

import com.jfixby.cmns.api.math.FixedInt2;
import com.jfixby.cmns.api.math.Int2;
import com.jfixby.cmns.api.math.IntegerMath;
import com.jfixby.r3.ext.api.patch18.Patch18;
import com.jfixby.r3.ext.api.patch18.grid.Cell;
import com.jfixby.r3.ext.api.patch18.palette.Fabric;
import com.jfixby.util.patch18.red.fields.acomposed.WallsComposition;

public class CellMark implements Cell {
	final Int2 cell_xy = IntegerMath.newInt2();

	public CellMark(long x, long y) {
		cell_xy.setXY(x, y);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cell_xy == null) ? 0 : cell_xy.hashCode());
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
		CellMark other = (CellMark) obj;
		if (cell_xy == null) {
			if (other.cell_xy != null)
				return false;
		} else if (!cell_xy.equals(other.cell_xy))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return cell_xy.toString();
	}

	public CellMark(Int2 position) {
		this(position.getX(), position.getY());
	}

	private WallsComposition composed;

	@Override
	public Patch18 getShape() {
		return composed.getValue(cell_xy.getX(), cell_xy.getY()).getShape();
	}

	@Override
	public Fabric getUpperFabric() {
		return composed.getValue(cell_xy.getX(), cell_xy.getY())
				.getUpperFabric();
	}

	@Override
	public Fabric getLowerFabric() {
		return composed.getValue(cell_xy.getX(), cell_xy.getY())
				.getLowerFabric();
	}

	public void setBase(WallsComposition composed) {
		this.composed = composed;
	}

	@Override
	public FixedInt2 getPosition() {
		return this.cell_xy;
	}

}
