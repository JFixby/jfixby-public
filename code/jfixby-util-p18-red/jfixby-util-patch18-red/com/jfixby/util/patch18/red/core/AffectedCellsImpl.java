package com.jfixby.util.patch18.red.core;

import com.jfixby.cmns.api.collections.JUtils;
import com.jfixby.cmns.api.collections.Set;
import com.jfixby.cmns.api.math.Int2;
import com.jfixby.cmns.api.math.IntegerMath;
import com.jfixby.r3.ext.api.patch18.grid.AffectedCells;
import com.jfixby.r3.ext.api.patch18.grid.Cell;
import com.jfixby.util.patch18.red.fields.acomposed.WallsComposition;

public class AffectedCellsImpl implements AffectedCells {
	final Set<Int2> list = JUtils.newSet();
	private WallsComposition wallsComposition;

	@Override
	public int numberOfElements() {
		return list.size();
	}

	@Override
	public Cell getCell(int index) {
		Int2 position = list.getElementAt(index);
		CellMark mark = new CellMark(position);
		mark.setBase(wallsComposition);
		return mark;
	}

	public void clear() {
		list.clear();
	}

	public void markCell(long cell_x, long cell_y,
			WallsComposition wallsComposition) {
		this.wallsComposition = wallsComposition;
		Int2 position = IntegerMath.newInt2(cell_x, cell_y);

		list.add(position);
	}

}
