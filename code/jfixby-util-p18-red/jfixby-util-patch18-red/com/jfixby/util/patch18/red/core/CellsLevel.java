package com.jfixby.util.patch18.red.core;

import com.jfixby.r3.ext.api.patch18.grid.Cell;



public class CellsLevel {

	final private int W;
	final private int H;
	final private CellMark[][] board;

	// private Cell irrelevant = irrelevant();

	public CellsLevel(int W, int H) {
		this.W = W;
		this.H = H;
		board = new CellMark[W][H];

		for (int i = 0; i < W; i++) {
			for (int j = 0; j < H; j++) {
				// board[i][j] = new CellImpl(Patch18.ERR, i, j);
			}
		}
	}

	// private Cell irrelevant() {
	// // CellImpl irr = new CellImpl(Patch18.Irrelevant, -2, -2);
	// // return irr;
	// }

	public Cell getCell(int x, int y) {
		if (withinRange(x, y)) {
			return board[x][y];
		}
		return null;
	}

	private boolean withinRange(int x, int y) {
		if (x < 0 || x >= W) {
			return false;
		}
		if (y < 0 || y >= H) {
			return false;
		}
		return true;
	}

	public int toCellX(int grid_x) {
		return grid_x;
	}

	public int toCellY(int grid_y) {
		return grid_y;
	}

}
