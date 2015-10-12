package com.jfixby.util.p18t.api;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.collections.Mapping;
import com.jfixby.cmns.api.floatn.Float2;
import com.jfixby.r3.ext.api.patch18.P18Palette;
import com.jfixby.r3.ext.api.patch18.palette.FabricsRelation;

public interface P18TerrainPaletteSpecs {

	AssetID getPaletteNamespace();

	double getBlockXWidth();

	double getBlockYWidth();

	P18Palette getP18Palette();

	double getBlockZHeight(FabricsRelation fabric_relation);

	// TerrainPaletteFactory getTerrainPaletteFactory();

	//

	void setPaletteNamespace(AssetID paletteNamespace);

	void setBlockXWidth(double x_width);

	void setBlockYWidth(double y_width);

	void setP18Palette(P18Palette palette);

	void setBlockZHeight(FabricsRelation relation, double z_height);

	void setRelationRelativeCenterXY(FabricsRelation relation,
			float centerRelativeX, float centerRelativeY);

	public float getRelationRelativeCenterX(FabricsRelation relation);

	public float getRelationRelativeCenterY(FabricsRelation relation);

	Mapping<FabricsRelation, Float2> getRelationRelativeCenters();

	// void setTerrainPaletteFactory(TerrainPaletteFactory factory);

}
