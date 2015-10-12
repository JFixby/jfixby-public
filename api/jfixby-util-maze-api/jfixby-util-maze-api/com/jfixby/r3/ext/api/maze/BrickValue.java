package com.jfixby.r3.ext.api.maze;

public enum BrickValue {
	EXIT(" ♀ "), START(" ♂ "), NOT_VISITED(" # "), LIMBO(""), VISITED("   "), SECRET(
			" S "), MONSTER(" Ѫ "), NO_WALL_V(" "), NO_WALL_H("   "), WallV("║"), WallH(
			"═══"), BrickU("║"), BrickD("║"), BrickR("═"), BrickL("═"), BrickDR(
			"╔"), BrickUR("╚"), BrickDL("╗"), BrickUL("╝"), BrickTR("╠"), BrickTL(
			"╣"), BrickTU("╩"), BrickTD("╦"), BrickV("║"), BrickH("═"), BrickP(
			"╬"), BrickE("x"), Treasure(" ◌ ");

	public boolean isWallV() {
		return this == WallV;
	}
	
	public boolean isBrickTL() {
		return this == BrickTL;
	}
	
	public boolean isBrickP() {
		return this == BrickP;
	}
	
	public boolean isWallH() {
		return this == WallH;
	}
	
	public boolean isBrickV() {
		return this == BrickV;
	}
	
	public boolean isBrickH() {
		return this == BrickH;
	}
	
	public boolean isBrickUL() {
		return this == BrickUL;
	}

	public boolean isBrickDL() {
		return this == BrickDL;
	}
	
	public boolean isBrickDR() {
		return this == BrickDR;
	}

	public boolean isBrickUR() {
		return this == BrickUR;
	}

	public boolean isBrickTU() {
		return this == BrickTU;
	}

	public boolean isBrickTR() {
		return this == BrickTR;
	}

	public boolean isBrickR() {
		return this == BrickR;
	}

	public boolean isBrickL() {
		return this == BrickL;
	}

	public boolean isBrickTD() {
		return this == BrickTD;
	}

	public boolean isBrickU() {
		return this == BrickU;
	}

	public boolean isBrickD() {
		return this == BrickD;
	}

	private String s;
	int code;

	private BrickValue(String simbol) {
		s = simbol;
		// this.code = getCode();
	}

	public String getSimbol() {
		return s;
	}

	public boolean isConcrete() {
		// TODO Auto-generated method stub
		return this == WallV || this == WallH || this == BrickU
				|| this == BrickD || this == BrickR || this == BrickL

				|| this == BrickDL || this == BrickUL || this == BrickTR
				|| this == BrickTL || this == BrickTU || this == BrickTD
				|| this == BrickV || this == BrickH || this == BrickP

				|| this == BrickDR || this == BrickUR;
	}

	// public int getCode() {
	// return code;
	// }

	public boolean isEmptiness() {
		// TODO Auto-generated method stub
		return !(this == VISITED || this == NO_WALL_V || this == NO_WALL_H);
	}

	public boolean isNotVisited() {
		return this == NOT_VISITED;
	}

}
