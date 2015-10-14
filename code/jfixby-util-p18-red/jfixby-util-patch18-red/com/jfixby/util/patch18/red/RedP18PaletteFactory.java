package com.jfixby.util.patch18.red;

import com.jfixby.r3.ext.api.patch18.P18Palette;
import com.jfixby.r3.ext.api.patch18.P18PaletteFactory;
import com.jfixby.r3.ext.api.patch18.PaletteSpecs;
import com.jfixby.r3.ext.api.patch18.palette.Fabric;
import com.jfixby.r3.ext.api.patch18.palette.FabricRelationSpecs;
import com.jfixby.r3.ext.api.patch18.palette.FabricSpecs;
import com.jfixby.r3.ext.api.patch18.palette.FabricsRelation;

public class RedP18PaletteFactory implements P18PaletteFactory {

	public FabricSpecs newFabricSpecs() {
		return new FabricSpecsImpl();
	}

	public Fabric newFabric(FabricSpecs fabric_specs) {
		return new FabricImpl(fabric_specs);
	}

	public FabricRelationSpecs newFacricRelationSpecs() {
		return new FacricRelationSpecsImpl();
	}

	public FabricsRelation newFacricRelation(FabricRelationSpecs relation_specs) {
		return new FacricRelationImpl(relation_specs);
	}

	public PaletteSpecs newPaletteSpecs() {
		return new PaletteSpecsImpl(this);
	}

	public P18Palette newPalette(PaletteSpecs palette_specs) {
		return new PaletteImpl((PaletteSpecsImpl) palette_specs);
	}

}
