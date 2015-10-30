package com.jfixby.tool.psd2scene2d;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.collections.Map;
import com.jfixby.psd.unpacker.api.PSDLayer;

public class PsdRepackerNameResolver implements ChildAssetsNameResolver {

	private AssetID package_name;
	private Map<PSDLayer, AssetID> raster_names;

	public PsdRepackerNameResolver(AssetID package_name,
			Map<PSDLayer, AssetID> raster_names) {
		this.package_name = package_name;
		this.raster_names = raster_names;
	}

	@Override
	public AssetID getPSDLayerName(PSDLayer input) {
		return raster_names.get(input);
	}

	@Override
	public AssetID childScene(String child_id) {
		return package_name.child(child_id);
	}

	@Override
	public AssetID childAnimation(String child_id) {
		return package_name.child(child_id);
	}

	@Override
	public AssetID childEvent(String child_id) {
		return package_name.child(child_id);
	}

	@Override
	public AssetID childInput(String child_id) {
		return package_name.child(child_id);
	}

	@Override
	public AssetID childText(String child_id) {
		return package_name.child("text").child(child_id);
	}

}
