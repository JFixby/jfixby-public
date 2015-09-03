package com.jfixby.cmns.red.name;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.assets.AssetsNamespaceComponent;

public class RedAssetsNamespace implements AssetsNamespaceComponent {

	@Override
	public AssetID newAssetId(String asset_id_string) {
		return new RedAssetID(asset_id_string);
	}

	@Override
	public String SEPARATOR() {
		return ".";
	}

}
