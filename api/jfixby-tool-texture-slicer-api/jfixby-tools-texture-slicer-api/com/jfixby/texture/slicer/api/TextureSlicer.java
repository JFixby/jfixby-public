package com.jfixby.texture.slicer.api;

import java.io.IOException;

import com.jfixby.cmns.api.components.ComponentInstaller;

public class TextureSlicer {

	static private ComponentInstaller<TextureSlicerComponent> componentInstaller = new ComponentInstaller<TextureSlicerComponent>(
			"TextureSlicer");

	public static final void installComponent(
			TextureSlicerComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final TextureSlicerComponent invoke() {
		return componentInstaller.invokeComponent();
	}

	public static final TextureSlicerComponent component() {
		return componentInstaller.getComponent();
	}

	public static TextureSlicerSpecs newDecompositionSpecs() {
		return componentInstaller.invokeComponent().newDecompositionSpecs();
	}

	public static TextureSlicingResult decompose(TextureSlicerSpecs specs)
			throws IOException {
		return componentInstaller.invokeComponent().decompose(specs);
	}
}
