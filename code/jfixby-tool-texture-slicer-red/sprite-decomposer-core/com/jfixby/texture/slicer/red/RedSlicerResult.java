package com.jfixby.texture.slicer.red;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.JUtils;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.texture.slicer.api.SlicesCompositionInfo;
import com.jfixby.texture.slicer.api.TextureSlicingResult;

public class RedSlicerResult implements TextureSlicingResult {

	private AssetID namespace;
	private List<AssetID> list = JUtils.newList();
	private SlicesCompositionInfo structure;

	public void setAssetID(AssetID namespace) {
		this.namespace = namespace;
	}

	@Override
	public AssetID getRasterID() {
		return namespace;
	}

	@Override
	public Collection<AssetID> listProducedTiles() {
		return list;
	}

	public void addTile(AssetID newAssetID) {
		list.add(newAssetID);
	}

	public void setStructure(SlicesCompositionInfo structure) {
		this.structure = structure;
	}

	@Override
	public SlicesCompositionInfo getTilesComposition() {
		return structure;
	}

}
