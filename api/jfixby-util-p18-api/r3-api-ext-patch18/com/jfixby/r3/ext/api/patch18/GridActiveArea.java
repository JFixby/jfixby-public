package com.jfixby.r3.ext.api.patch18;

public interface GridActiveArea {

	boolean isBorder(long cell_x, long cell_y);

	boolean isInside(long cell_x, long cell_y);

	boolean isOutside(long cell_x, long cell_y);

}
