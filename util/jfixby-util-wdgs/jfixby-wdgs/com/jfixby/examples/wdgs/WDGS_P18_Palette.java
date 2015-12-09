package com.jfixby.examples.wdgs;

import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.r3.ext.api.patch18.P18;
import com.jfixby.r3.ext.api.patch18.P18Palette;
import com.jfixby.r3.ext.api.patch18.P18PaletteFactory;
import com.jfixby.r3.ext.api.patch18.PaletteSpecs;
import com.jfixby.r3.ext.api.patch18.palette.Fabric;
import com.jfixby.r3.ext.api.patch18.palette.FabricSpecs;
import com.jfixby.r3.ext.api.patch18.palette.FabricsRelation;

public class WDGS_P18_Palette {
	public static final String FABRIC_NAME_WATER = "water";
	public static final String FABRIC_NAME_DIRT = "dirt";
	public static final String FABRIC_NAME_GRASS = "grass";
	public static final String FABRIC_NAME_SNOW = "snow";

	public static final Fabric WATER;
	public static final Fabric DIRT;
	public static final Fabric GRASS;
	public static final Fabric SNOW;
	public static final List<Fabric> fabrics = Collections.newList();

	public static final FabricsRelation DIRT_N_WATER;
	public static final FabricsRelation GRASS_N_DIRT;
	public static final FabricsRelation SNOW_N_GRASS;
	public static final List<FabricsRelation> fabric_relations = Collections
			.newList();

	public static final P18Palette PALETTE;
	static {

		P18PaletteFactory palette_factory = P18.invoke().getPaletteFactory();
		{
			FabricSpecs fabric_specs = palette_factory.newFabricSpecs();
			fabric_specs.setFabricName(FABRIC_NAME_WATER);
			Fabric fabric = palette_factory.newFabric(fabric_specs);
			WATER = fabric;
		}
		{
			FabricSpecs fabric_specs = palette_factory.newFabricSpecs();
			fabric_specs.setFabricName(FABRIC_NAME_DIRT);
			Fabric fabric = palette_factory.newFabric(fabric_specs);
			DIRT = fabric;
		}
		{
			FabricSpecs fabric_specs = palette_factory.newFabricSpecs();
			fabric_specs.setFabricName(FABRIC_NAME_GRASS);
			Fabric fabric = palette_factory.newFabric(fabric_specs);
			GRASS = fabric;
		}
		{
			FabricSpecs fabric_specs = palette_factory.newFabricSpecs();
			fabric_specs.setFabricName(FABRIC_NAME_SNOW);
			Fabric fabric = palette_factory.newFabric(fabric_specs);
			SNOW = fabric;
		}
		fabrics.add(WATER);
		fabrics.add(DIRT);
		fabrics.add(GRASS);
		fabrics.add(SNOW);

		PaletteSpecs palette_specs = palette_factory.newPaletteSpecs();

		palette_specs.setPaletteName("WDGS");

		palette_specs.addFabric(WATER);
		palette_specs.addFabric(DIRT);
		palette_specs.addFabric(GRASS);
		palette_specs.addFabric(SNOW);

		DIRT_N_WATER = palette_specs.defineRelation(DIRT, WATER);
		GRASS_N_DIRT = palette_specs.defineRelation(GRASS, DIRT);
		SNOW_N_GRASS = palette_specs.defineRelation(SNOW, GRASS);

		fabric_relations.add(DIRT_N_WATER);
		fabric_relations.add(GRASS_N_DIRT);
		fabric_relations.add(SNOW_N_GRASS);

		P18Palette palette = palette_factory.newPalette(palette_specs);
		PALETTE = palette;
	}

}
