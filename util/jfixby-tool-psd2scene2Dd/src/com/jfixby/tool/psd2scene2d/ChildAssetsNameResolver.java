package com.jfixby.tool.psd2scene2d;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.psd.unpacker.api.PSDLayer;

public interface ChildAssetsNameResolver {

	AssetID getPSDLayerName(PSDLayer input);

	AssetID childScene(String child_id);

	AssetID childAnimation(String child_id);

	AssetID childEvent(String child_id);

	AssetID childInput(String child_id);

	AssetID childText(String child_id);

}
