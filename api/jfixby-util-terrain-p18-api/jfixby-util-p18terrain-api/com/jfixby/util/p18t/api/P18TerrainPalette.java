package com.jfixby.util.p18t.api;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.Mapping;
import com.jfixby.cmns.api.floatn.Float2;
import com.jfixby.cmns.api.gamedev.GameMeter;
import com.jfixby.r3.ext.api.patch18.P18Palette;
import com.jfixby.r3.ext.api.patch18.Patch18;
import com.jfixby.r3.ext.api.patch18.palette.Fabric;
import com.jfixby.r3.ext.api.patch18.palette.FabricsList;
import com.jfixby.r3.ext.api.patch18.palette.FabricsRelation;
import com.jfixby.r3.ext.api.patch18.palette.RelationsList;
import com.jfixby.util.terain.test.api.palette.TerrainPalette;

public interface P18TerrainPalette {
	AssetID getPaletteNamespace();

	GameMeter getBlockXWidth();

	GameMeter getBlockYWidth();

	P18Palette getP18Palette();

	//

	TerrainPalette getTerrainPalette();

	void print();

	Mapping<FabricsRelation, Float2> getRelationRelativeCenters();

	AssetID getNamespace();

	P18TerrainTypeVariationsList listVariationsFor(Patch18 shape,
			Fabric upperFabric, Fabric lowerFabric);

	FabricsList listFabrics();

	RelationsList listRelations();

	P18TerrainTypeVariationsList listVariationsFor(FabricsRelation relation,
			Patch18 p18val);

	Collection<P18TerrainTypeVariationsList> listVariationsAll();

}
