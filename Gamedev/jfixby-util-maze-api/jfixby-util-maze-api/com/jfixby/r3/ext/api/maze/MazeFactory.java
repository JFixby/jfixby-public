package com.jfixby.r3.ext.api.maze;


public interface MazeFactory {

	MazeFieldSpecs newMazeFieldSpecs();

	MazeField newMaze(MazeFieldSpecs maze_field_specs);

}
