package com.jfixby.util.patch18.red;

import com.jfixby.cmns.api.math.FixedInt2;
import com.jfixby.cmns.api.math.Int2;
import com.jfixby.cmns.api.math.IntegerMath;
import com.jfixby.r3.ext.api.patch18.grid.AffectedCells;
import com.jfixby.r3.ext.api.patch18.grid.GridBrushApplicationResult;
import com.jfixby.util.patch18.red.core.AffectedCellsImpl;

public class BrushApplicationResultImpl implements GridBrushApplicationResult {

	private final AffectedCellsImpl affected = new AffectedCellsImpl();
	private final AffectedCellsImpl changed = new AffectedCellsImpl();
	// private final Int2 applicationPoint = IntegerMath.newInt2();
	private final GridBrushImpl brush;

	public BrushApplicationResultImpl(GridBrushImpl gridBrushImpl) {
		this.brush = gridBrushImpl;
	}

	@Override
	public AffectedCells getAffectedCells() {
		return affected;
	}

	@Override
	public AffectedCells getChangedCells() {
		return changed;
	}

	public void clear() {
		changed.clear();
		affected.clear();
	}

	public void markAffected(long cell_x, long cell_y) {
		affected.markCell(cell_x, cell_y, this.brush.getGrid().getComposed());
	}

	public void markChanged(long cell_x, long cell_y) {
		changed.markCell(cell_x, cell_y, this.brush.getGrid().getComposed());
	}

	// @Override
	// public FixedInt2 applicationPoint() {
	// return applicationPoint;
	// }
}
