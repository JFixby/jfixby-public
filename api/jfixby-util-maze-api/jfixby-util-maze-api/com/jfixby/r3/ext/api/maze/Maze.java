package com.jfixby.r3.ext.api.maze;


public class Maze {

	static private ComponentInstaller<MazeComponent> componentInstaller = new ComponentInstaller<MazeComponent>(
			"Maze");

	public static final void installComponent(MazeComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final MazeComponent invoke() {
		return componentInstaller.invokeComponent();
	}

	public static final MazeComponent component() {
		return componentInstaller.getComponent();
	}

	public static MazeFactory getMazeFactory() {
		return invoke().getMazeFactory();
	}

}
