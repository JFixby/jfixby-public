package com.jfixby.texture.slicer.api;

import java.io.IOException;

public interface TextureSlicerComponent {

	TextureSlicerSpecs newDecompositionSpecs();

	TextureSlicingResult decompose(TextureSlicerSpecs specs)
			throws IOException;

}
