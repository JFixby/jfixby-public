package com.jfixby.tool.psd2scene2d;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.collections.Map;
import com.jfixby.cmns.api.log.L;
import com.jfixby.psd.unpacker.api.PSDLayer;
import com.jfixby.r3.ext.api.scene2d.srlz.SceneStructure;

public class ConversionResult {

	Map<SceneStructure, SceneStructurePackingResult> requred_raster = Collections
			.newMap();

	Map<PSDLayer, SceneStructurePackingResult> ancestors = Collections.newMap();

	public Collection<AssetID> listAllRequredAssets() {
		List<AssetID> list = Collections.newList();

		for (int i = 0; i < this.requred_raster.size(); i++) {
			SceneStructurePackingResult result_i = this.requred_raster
					.getValueAt(i);
			List<AssetID> required = result_i.listRequiredAssets();
			list.addAll(required);
		}
		return list;
	}

	public void putResult(SceneStructure structure,
			SceneStructurePackingResult result_i) {
		this.requred_raster.put(structure, result_i);
		Collection<PSDLayer> anc = result_i.getAncestors();
		for (int i = 0; i < anc.size(); i++) {
			this.ancestors.put(anc.getElementAt(i), result_i);
		}

	}

	public SceneStructurePackingResult getStrucutreResultByLayer(PSDLayer layer) {

		SceneStructurePackingResult result = this.ancestors.get(layer);
		if (result == null) {
			this.ancestors.print("ancestors");

			L.d("layer", layer);

			throw new Error("Layer not found");

		}
		return result;
	}

}
