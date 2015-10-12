package com.jfixby.r3.ext.api.patch18;

import com.jfixby.r3.ext.api.patch18.palette.Fabric;
import com.jfixby.r3.ext.api.patch18.palette.FabricRelationSpecs;
import com.jfixby.r3.ext.api.patch18.palette.FabricSpecs;
import com.jfixby.r3.ext.api.patch18.palette.FabricsRelation;


public interface P18PaletteFactory {

	public FabricSpecs newFabricSpecs();

	public Fabric newFabric(FabricSpecs fabric_specs);

	public FabricRelationSpecs newFacricRelationSpecs();

	public FabricsRelation newFacricRelation(FabricRelationSpecs relation_specs);

	public PaletteSpecs newPaletteSpecs();

	public P18Palette newPalette(PaletteSpecs palette_specs);

	
}
