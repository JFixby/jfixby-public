package com.jfixby.util.iso.api;

import com.jfixby.cmns.api.ComponentInstaller;

public class Isometry {

	static private ComponentInstaller<ISOComponent> componentInstaller = new ComponentInstaller<ISOComponent>(
			"Isometry");

	public static final void installComponent(ISOComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final ISOComponent invoke() {
		return componentInstaller.invokeComponent();
	}

	public static final ISOComponent component() {
		return componentInstaller.getComponent();
	}

	public static IsoTransformSpecs newTransformSpecs() {
		return invoke().newTransformSpecs();
	}

	public static IsoTransform newTransform(IsoTransformSpecs specs) {
		return invoke().newTransform(specs);
	}

	public static IsoTransform getFallout(double pixels_to_game_meter) {
		return invoke().getFallout(pixels_to_game_meter);
	}

}
