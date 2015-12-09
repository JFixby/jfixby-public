package com.jfixby.r3.tools.api.iso;

import java.io.IOException;

import com.jfixby.cmns.api.ComponentInstaller;

public class IsoMockPaletteGenerator {

	static private ComponentInstaller<IsoMockPaletteGeneratorComponent> componentInstaller = new ComponentInstaller<IsoMockPaletteGeneratorComponent>(
			"IsoMockPaletteGenerator");

	public static final void installComponent(
			IsoMockPaletteGeneratorComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final IsoMockPaletteGeneratorComponent invoke() {
		return componentInstaller.invokeComponent();
	}

	public static final IsoMockPaletteGeneratorComponent component() {
		return componentInstaller.getComponent();
	}

	public static GeneratorParams newIsoMockPaletteGeneratorParams() {
		return invoke().newIsoMockPaletteGeneratorParams();
	}

	public static IsoMockPaletteResult generate(GeneratorParams specs)
			throws IOException {
		return invoke().generate(specs);
	}

}
