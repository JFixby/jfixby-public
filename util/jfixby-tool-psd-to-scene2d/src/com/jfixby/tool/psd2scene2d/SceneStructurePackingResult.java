package com.jfixby.tool.psd2scene2d;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.collections.Set;
import com.jfixby.cmns.api.debug.Debug;
import com.jfixby.psd.unpacker.api.PSDLayer;
import com.jfixby.r3.ext.api.scene2d.srlz.SceneStructure;

public class SceneStructurePackingResult {

	List<AssetID> lit = Collections.newList();
	private double scale_factor;
	private SceneStructure structure;
	private Set<PSDLayer> ancestors = Collections.newSet();

	public SceneStructurePackingResult(SceneStructure structure) {
		this.structure = structure;
	}

	public void addRequiredAsset(AssetID child_scene_asset_id,
			List<PSDLayer> list) {
		Debug.checkNull(child_scene_asset_id);
		lit.add(child_scene_asset_id);
		ancestors.addAll(list);
	}

	public List<AssetID> listRequiredAssets() {
		return lit;
	}

	public void setScaleFactor(double scale_factor) {
		this.scale_factor = scale_factor;
	}

	public double getScaleFactor() {
		return scale_factor;
	}

	public Collection<PSDLayer> getAncestors() {
		return ancestors;
	}

	public SceneStructure getStructure() {
		return structure;
	}

}
