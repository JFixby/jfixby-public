package com.jfixby.util.patch18.red.maze;

import java.util.Random;
import java.util.Vector;

import com.jfixby.cmns.api.log.L;
import com.jfixby.r3.ext.api.maze.BrickValue;
import com.jfixby.r3.ext.api.maze.Direction;
import com.jfixby.r3.ext.api.maze.MazeField;
import com.jfixby.r3.ext.api.maze.MazeFieldSpecs;

class FokkerMazeField implements MazeField {

	final BrickValue[][] f;
	final int cell_height;
	final int cell_width;
	final int blocks_heighth;
	final int blocks_width;
	private long seed;
	private Random random;
	private int VARIATOR_2;
	private int VARIATOR_1;

	public FokkerMazeField(int W, int H) {
		super();
		this.blocks_heighth = H * 2 + 1;
		this.blocks_width = W * 2 + 1;
		this.f = new BrickValue[blocks_width][blocks_heighth];
		this.cell_height = H;
		this.cell_width = W;
		VARIATOR_2 = cell_width + cell_height;
		VARIATOR_1 = cell_width + cell_height;

		for (int x = 0; x < W; x++) {
			int ax = x * 2 + 1;
			for (int y = 0; y < H; y++) {
				int ay = y * 2 + 1;
				f[ax][ay] = BrickValue.NOT_VISITED;
				f[ax + 1][ay] = BrickValue.WallV;
				f[ax - 1][ay] = BrickValue.WallV;
				f[ax][ay + 1] = BrickValue.WallH;
				f[ax][ay - 1] = BrickValue.WallH;
				f[ax + 1][ay + 1] = BrickValue.BrickP;
				f[ax - 1][ay - 1] = BrickValue.BrickP;
				f[ax + 1][ay - 1] = BrickValue.BrickP;
				f[ax - 1][ay + 1] = BrickValue.BrickP;

				if (x == 0) {
					f[ax - 1][ay - 1] = BrickValue.BrickTR;
					f[ax - 1][ay + 1] = BrickValue.BrickTR;
				}

				if (x + 1 == W) {
					f[ax + 1][ay - 1] = BrickValue.BrickTL;
					f[ax + 1][ay + 1] = BrickValue.BrickTL;
				}

				if (y == 0) {
					f[ax - 1][ay - 1] = BrickValue.BrickTD;
					f[ax + 1][ay - 1] = BrickValue.BrickTD;
				}

				if (y + 1 == H) {
					f[ax - 1][ay + 1] = BrickValue.BrickTU;
					f[ax + 1][ay + 1] = BrickValue.BrickTU;
				}

				if (x == 0 && y == 0) {
					f[ax - 1][ay - 1] = BrickValue.BrickDR;
				}

				if (x == 0 && y + 1 == H) {
					f[ax - 1][ay + 1] = BrickValue.BrickUR;
				}

				if (x + 1 == W && y == 0) {
					f[ax + 1][ay - 1] = BrickValue.BrickDL;
				}

				if (x + 1 == W && y + 1 == H) {
					f[ax + 1][ay + 1] = BrickValue.BrickUL;
				}

			}
		}

	}

	public FokkerMazeField(MazeFieldSpecs maze_field_specs) {
		this(maze_field_specs.getWidth(), maze_field_specs.getHeight());
		seed = maze_field_specs.getRandomizationSeed();
		if (seed == MazeFieldSpecs.DEFAULT) {
			seed = System.nanoTime();
		}
		this.random = new Random(seed);
		L.d("seed", seed);
		buildMaze(cell_width / 2, cell_height / 2, 0.9f);
	}

	@Override
	public int getCellsHeight() {
		// TODO Auto-generated method stub
		return cell_height;
	}

	@Override
	public int getCellsWidth() {
		// TODO Auto-generated method stub
		return cell_width;
	}

	@Override
	public BrickValue getCellValue(int x, int y) {
		if (x < 0 || x >= cell_width || y < 0 || y >= cell_height) {
			return BrickValue.LIMBO;
		}
		return this.f[x * 2 + 1][y * 2 + 1];
	}

	@Override
	public BrickValue getCellWall(int x, int y, Direction d) {

		return null;
	}

	@Override
	public void print() {
		// TODO Auto-generated method stub
		L.d(cell_width + "x" + cell_height + " Seed: " + this.seed);
		L.d("       " + blocks_width + "x" + blocks_heighth);
		for (int y = 0; y < blocks_heighth; y++) {
			for (int x = 0; x < blocks_width; x++) {
				L.d_addChars(f[x][y].getSimbol() + "");
			}
			L.d();
		}

	}

	//
	// public void setStartEnd(int sx, int sy, int ex, int ey) {
	// // TODO Auto-generated method stub
	// this.f[sx * 2 + 1][sy * 2 + 1] = CellValue.START;
	// this.f[ex * 2 + 1][ey * 2 + 1] = CellValue.FINISH;
	// }

	public void buildMaze(int sx, int sy, float p_direct) {

		XY marker = new XY(sx, sy);
		this.f[sx * 2 + 1][sy * 2 + 1] = BrickValue.VISITED;
		expandMazeFrom(marker, marker, p_direct, 0);
		this.f[sx * 2 + 1][sy * 2 + 1] = BrickValue.START;

		try_to_place_exit(this.f);

		for (int x = 0; x < cell_width; x++) {
			int ax = x * 2 + 1;
			for (int y = 0; y < cell_height; y++) {
				int ay = y * 2 + 1;

				filterBrick(ax + 1, ay + 1);
				filterBrick(ax - 1, ay + 1);
				filterBrick(ax + 1, ay - 1);
				filterBrick(ax - 1, ay - 1);

			}
		}
	}

	private void try_to_place_exit(BrickValue[][] f) {
		// if (cell_width == 1 && cell_height == 1) {
		// return;
		// }
		long max_tries = 1000;

		while (max_tries > 0) {
			int ex = (int) (cell_width * random.nextFloat());
			int ey = (int) (cell_height * random.nextFloat());

			if (this.f[ex * 2 + 1][ey * 2 + 1] == BrickValue.VISITED) {
				this.f[ex * 2 + 1][ey * 2 + 1] = BrickValue.EXIT;
				return;
			}
			max_tries--;

		}
	}

	private void filterBrick(int x, int y) {

		BrickValue U = getWall(x, y - 1);
		BrickValue D = getWall(x, y + 1);
		BrickValue R = getWall(x + 1, y);
		BrickValue L = getWall(x - 1, y);

		if (U == BrickValue.NO_WALL_V || U == BrickValue.LIMBO) {
			if (D == BrickValue.NO_WALL_V || D == BrickValue.LIMBO) {
				if (R == BrickValue.NO_WALL_H || R == BrickValue.LIMBO) {
					if (L == BrickValue.NO_WALL_H || L == BrickValue.LIMBO) {
						f[x][y] = BrickValue.BrickE;
					} else {
						f[x][y] = BrickValue.BrickL;
					}
				} else {
					if (L == BrickValue.NO_WALL_H || L == BrickValue.LIMBO) {
						f[x][y] = BrickValue.BrickR;
					} else {
						f[x][y] = BrickValue.BrickH;
					}
				}
			} else {
				if (R == BrickValue.NO_WALL_H || R == BrickValue.LIMBO) {
					if (L == BrickValue.NO_WALL_H || L == BrickValue.LIMBO) {
						f[x][y] = BrickValue.BrickD;
					} else {
						f[x][y] = BrickValue.BrickDL;
					}
				} else {
					if (L == BrickValue.NO_WALL_H || L == BrickValue.LIMBO) {
						f[x][y] = BrickValue.BrickDR;
					} else {
						f[x][y] = BrickValue.BrickTD;
					}
				}
			}
		} else {
			if (D == BrickValue.NO_WALL_V || D == BrickValue.LIMBO) {
				if (R == BrickValue.NO_WALL_H || R == BrickValue.LIMBO) {
					if (L == BrickValue.NO_WALL_H || L == BrickValue.LIMBO) {
						f[x][y] = BrickValue.BrickU;
					} else {
						f[x][y] = BrickValue.BrickUL;
					}
				} else {
					if (L == BrickValue.NO_WALL_H || L == BrickValue.LIMBO) {
						f[x][y] = BrickValue.BrickUR;
					} else {
						f[x][y] = BrickValue.BrickTU;
					}
				}
			} else {
				if (R == BrickValue.NO_WALL_H || R == BrickValue.LIMBO) {
					if (L == BrickValue.NO_WALL_H || L == BrickValue.LIMBO) {
						f[x][y] = BrickValue.BrickV;
					} else {
						f[x][y] = BrickValue.BrickTL;
					}
				} else {
					if (L == BrickValue.NO_WALL_H || L == BrickValue.LIMBO) {
						f[x][y] = BrickValue.BrickTR;
					} else {
						f[x][y] = BrickValue.BrickP;
					}
				}
			}
		}

		if (x == 0 && y == 0) {
			f[x][y] = BrickValue.BrickDR;
		}

		if (x == 0 && y + 1 == blocks_heighth) {
			f[x][y] = BrickValue.BrickUR;
		}

		if (x + 1 == blocks_width && y == 0) {
			f[x][y] = BrickValue.BrickDL;
		}

		if (x + 1 == blocks_width && y + 1 == blocks_heighth) {
			f[x][y] = BrickValue.BrickUL;
		}
	}

	private BrickValue getWall(int x, int y) {
		if (x < 0 || x >= blocks_width || y < 0 || y >= blocks_heighth) {
			return BrickValue.LIMBO;
		}
		return f[x][y];
	}

	private void expandMazeFrom(XY previous, XY marker, float p_direct,
			int depth) {
		{
			Vector<XY> options = new Vector<XY>();

			addCellIfValid(options, marker.x + 1, marker.y);
			addCellIfValid(options, marker.x - 1, marker.y);
			addCellIfValid(options, marker.x, marker.y + 1);
			addCellIfValid(options, marker.x, marker.y - 1);
			int roulette = this.random.nextInt(VARIATOR_1);

			if (options.size() == 0 || roulette < depth) {
				this.setCellValue(marker.x, marker.y, BrickValue.Treasure);
				return;
			}
			float step = 1f / options.size();
			final XY next = options.remove((int) (random.nextFloat() / step));
			this.setWall(marker.x, marker.y,
					resolve(marker.x, marker.y, next.x, next.y),
					noWall(resolve(marker.x, marker.y, next.x, next.y)));
			this.setCellValue(next.x, next.y, BrickValue.VISITED);
			// print();
			expandMazeFrom(marker, next, p_direct, depth + 1);

			//
		}
		{
			Vector<XY> options = new Vector<XY>();

			addCellIfValid(options, marker.x + 1, marker.y);
			addCellIfValid(options, marker.x - 1, marker.y);
			addCellIfValid(options, marker.x + 1, marker.y);
			addCellIfValid(options, marker.x - 1, marker.y);
			addCellIfValid(options, marker.x + 1, marker.y);
			addCellIfValid(options, marker.x - 1, marker.y);
			addCellIfValid(options, marker.x, marker.y + 1);
			addCellIfValid(options, marker.x, marker.y - 1);
			int roulette = this.random.nextInt(VARIATOR_2);

			if (options.size() == 0 || roulette < depth) {
				return;
			}
			float step = 1f / options.size();
			final XY next = options.remove((int) (random.nextFloat() / step));
			this.setWall(marker.x, marker.y,
					resolve(marker.x, marker.y, next.x, next.y),
					noWall(resolve(marker.x, marker.y, next.x, next.y)));
			this.setCellValue(next.x, next.y, BrickValue.MONSTER);
			// print();
			expandMazeFrom(marker, next, p_direct, depth + 1);

			//
		}

	}

	private BrickValue noWall(Direction resolve) {
		if (resolve == Direction.Down) {
			return BrickValue.NO_WALL_H;
		}
		if (resolve == Direction.Up) {
			return BrickValue.NO_WALL_H;
		}

		return BrickValue.NO_WALL_V;
	}

	private void setWall(int x, int y, Direction resolve, BrickValue no) {
		if (resolve == Direction.Down) {
			this.f[x * 2 + 1][y * 2 + 1 + 1] = no;
			return;
		}
		if (resolve == Direction.Up) {
			this.f[x * 2 + 1][y * 2 + 1 - 1] = no;
			return;
		}
		if (resolve == Direction.DownRight) {
			this.f[x * 2 + 1 + 1][y * 2 + 1 + 1] = no;
			return;
		}
		if (resolve == Direction.UpRight) {
			this.f[x * 2 + 1 + 1][y * 2 + 1 - 1] = no;
			return;
		}

		if (resolve == Direction.DownLeft) {
			this.f[x * 2 + 1 - 1][y * 2 + 1 + 1] = no;
			return;
		}
		if (resolve == Direction.UpLeft) {
			this.f[x * 2 + 1 - 1][y * 2 + 1 - 1] = no;
			return;
		}

		if (resolve == Direction.Right) {
			this.f[x * 2 + 1 + 1][y * 2 + 1] = no;
			return;
		}
		if (resolve == Direction.Left) {
			this.f[x * 2 + 1 - 1][y * 2 + 1] = no;
			return;
		}

	}

	private Direction resolve(int x, int y, int x2, int y2) {
		if (x < x2) {
			if (y < y2) {
				return Direction.DownRight;
			}
			if (y > y2) {
				return Direction.UpRight;
			}
			return Direction.Right;
		}
		if (x > x2) {
			if (y < y2) {
				return Direction.DownLeft;
			}
			if (y > y2) {
				return Direction.UpLeft;
			}
			return Direction.Left;
		}

		if (y < y2) {
			return Direction.Down;
		}
		if (y > y2) {
			return Direction.Up;
		}
		if (y < y2) {
			return Direction.DownLeft;
		}
		if (y > y2) {
			return Direction.UpLeft;
		}
		return Direction.NO;
	}

	public void setCellValue(int sx, int sy, BrickValue visited) {
		this.f[sx * 2 + 1][sy * 2 + 1] = visited;
	}

	private void addCellIfValid(Vector<XY> options, int x, int y) {
		final BrickValue v = this.getCellValue(x, y);
		if (v == BrickValue.NOT_VISITED) {
			options.add(new XY(x, y));
		}

	}

	class XY {
		int x;
		int y;

		public XY(int sx, int sy) {
			x = sx;
			y = sy;
		}

		public XY set(int sx, int sy) {
			x = sx;
			y = sy;
			return this;
		}

		XY copy() {
			return new XY(x, y);
		}

	}

	public OriginalGameField build() {
		// OriginalGameField
		OriginalGameField of = new OriginalGameField(this.blocks_width,
				blocks_heighth);
		of.useWall(false);
		for (int i = 0; i < blocks_width; i++) {
			for (int j = 0; j < blocks_heighth; j++) {
				// of.setGround(i, j, this.f[i][j].isEmpty());
				of.setGround(i, j, this.f[i][j].isConcrete());
			}
		}
		return of;
	}

	@Override
	public int getBricksWidth() {
		return this.blocks_width;
	}

	@Override
	public int getBricksHeight() {
		return this.blocks_heighth;
	}

	@Override
	public BrickValue getBrickValue(int x, int y) {
		return this.f[x][y];
	}

}
