package com.jfixby.r3.tools.api.iso;

import java.io.IOException;

public interface IsoMockPaletteGeneratorComponent {

	GeneratorParams newIsoMockPaletteGeneratorParams();

	IsoMockPaletteResult generate(GeneratorParams specs) throws IOException;

}
