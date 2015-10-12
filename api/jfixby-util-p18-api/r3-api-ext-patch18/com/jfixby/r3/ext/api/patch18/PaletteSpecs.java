package com.jfixby.r3.ext.api.patch18;

import com.jfixby.r3.ext.api.patch18.palette.Fabric;
import com.jfixby.r3.ext.api.patch18.palette.FabricsRelation;

public interface PaletteSpecs {

	void addFabric(Fabric fabric);

	FabricsRelation defineRelation(Fabric upper_fabric, Fabric lower_fabric);

	void setPaletteName(String name);

	String getName();

}
