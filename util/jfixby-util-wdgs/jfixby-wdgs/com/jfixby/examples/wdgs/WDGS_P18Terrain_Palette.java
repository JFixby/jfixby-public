package com.jfixby.examples.wdgs;

import com.jfixby.cmns.api.assets.Names;
import com.jfixby.r3.ext.api.patch18.P18Palette;
import com.jfixby.util.p18t.api.P18Terrain;
import com.jfixby.util.p18t.api.P18TerrainPalette;
import com.jfixby.util.p18t.api.P18TerrainPaletteFactory;
import com.jfixby.util.p18t.api.P18TerrainPaletteSpecs;

public class WDGS_P18Terrain_Palette {
	public static final P18Palette P18_PALETTE;
	static {
		P18TerrainPaletteFactory factory = P18Terrain.invoke()
				.getP18TerrainPaletteFactory();

		P18_PALETTE = WDGS_P18_Palette.PALETTE;

		P18TerrainPaletteSpecs specs = factory.newP18TerrainPaletteSpecs();
		specs.setBlockXWidth(1);
		specs.setBlockYWidth(1);
		specs.setBlockZHeight(P18_PALETTE.listRelations().getRelation(0), 0.4);
		specs.setBlockZHeight(P18_PALETTE.listRelations().getRelation(1), 0.4);
		specs.setBlockZHeight(P18_PALETTE.listRelations().getRelation(2), 0.4);

		specs.setRelationRelativeCenterXY(P18_PALETTE.listRelations()
				.getRelation(0), 0.4f, 0.3f);
		specs.setRelationRelativeCenterXY(P18_PALETTE.listRelations()
				.getRelation(1), 0.6f, 0.4f);
		specs.setRelationRelativeCenterXY(P18_PALETTE.listRelations()
				.getRelation(2), 0.3f, 0.8f);

		specs.setPaletteNamespace(Names
				.newAssetID("com.jfixby.r3.iso.test.wdgs"));
		specs.setP18Palette(P18_PALETTE);
		P18_TERRAIN_PALETTE = factory.newP18TerrainPalette(specs);
	}

	public static final P18TerrainPalette P18_TERRAIN_PALETTE;

}
