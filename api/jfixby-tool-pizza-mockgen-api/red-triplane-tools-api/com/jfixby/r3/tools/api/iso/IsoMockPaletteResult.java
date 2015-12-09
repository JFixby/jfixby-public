package com.jfixby.r3.tools.api.iso;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.file.File;
import com.jfixby.r3.ext.api.scene2d.srlz.Scene2DPackage;

public interface IsoMockPaletteResult {

	void print();

	File getRasterOutputFolder();

	AssetID getNamespace();

	Scene2DPackage getScene2DPackage();

	Collection<AssetID> getAssetsUsed();

}
