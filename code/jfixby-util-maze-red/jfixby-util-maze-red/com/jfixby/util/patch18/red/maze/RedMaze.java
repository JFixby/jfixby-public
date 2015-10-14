package com.jfixby.util.patch18.red.maze;

import com.jfixby.r3.ext.api.maze.MazeComponent;
import com.jfixby.r3.ext.api.maze.MazeFactory;
import com.jfixby.r3.ext.api.maze.MazeField;
import com.jfixby.r3.ext.api.maze.MazeFieldSpecs;

public class RedMaze implements MazeComponent, MazeFactory {

	@Override
	public MazeFactory getMazeFactory() {
		return this;
	}

	@Override
	public MazeFieldSpecs newMazeFieldSpecs() {
		return new MazeFieldSpecsImpl();
	}

	@Override
	public MazeField newMaze(MazeFieldSpecs maze_field_specs) {
		return new FokkerMazeField(maze_field_specs);
	}

}
