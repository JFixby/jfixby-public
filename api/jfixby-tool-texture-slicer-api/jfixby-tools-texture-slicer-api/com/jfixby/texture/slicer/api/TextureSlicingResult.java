package com.jfixby.texture.slicer.api;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.collections.Collection;

public interface TextureSlicingResult {

	AssetID getRasterID();

	Collection<AssetID> listProducedTiles();

	SlicesCompositionInfo getTilesComposition();

}
