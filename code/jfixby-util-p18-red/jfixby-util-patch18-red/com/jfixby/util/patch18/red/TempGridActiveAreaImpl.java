package com.jfixby.util.patch18.red;

import com.jfixby.cmns.api.math.FloatMath;
import com.jfixby.r3.ext.api.patch18.GridActiveArea;

public class TempGridActiveAreaImpl implements GridActiveArea {

	public TempGridActiveAreaImpl(int width, int height) {

	}

	@Override
	public boolean isInside(long cell_x, long cell_y) {
		int points = FloatMath.INDEX(this.isDotInside(cell_x, cell_y))
				+ FloatMath.INDEX(this.isDotInside(cell_x + 1, cell_y))
				+ FloatMath.INDEX(this.isDotInside(cell_x, cell_y + 1))
				+ FloatMath.INDEX(this.isDotInside(cell_x + 1, cell_y + 1));

		return (points == 4);
	}

	public boolean isBorder(long cell_x, long cell_y) {
		int points = FloatMath.INDEX(this.isDotInside(cell_x, cell_y))
				+ FloatMath.INDEX(this.isDotInside(cell_x + 1, cell_y))
				+ FloatMath.INDEX(this.isDotInside(cell_x, cell_y + 1))
				+ FloatMath.INDEX(this.isDotInside(cell_x + 1, cell_y + 1));

		return (points != 0) && (points != 4);

	}

	boolean isDotInside(double cell_x, double cell_y) {

		// if (MathTools.pointLiesInsideTriangle(cell_x, cell_y, x0, y0, x1, y1,
		// x2, y2)) {
		// return true;
		// }
		// if (MathTools.pointLiesInsideTriangle(cell_x, cell_y, x0, y0, x3, y3,
		// x2, y2)) {
		// return true;
		// }
		return false;
	}

	@Override
	public boolean isOutside(long cell_x, long cell_y) {
		int points = FloatMath.INDEX(this.isDotInside(cell_x, cell_y))
				+ FloatMath.INDEX(this.isDotInside(cell_x + 1, cell_y))
				+ FloatMath.INDEX(this.isDotInside(cell_x, cell_y + 1))
				+ FloatMath.INDEX(this.isDotInside(cell_x + 1, cell_y + 1));

		return (points == 0);
	}

}
