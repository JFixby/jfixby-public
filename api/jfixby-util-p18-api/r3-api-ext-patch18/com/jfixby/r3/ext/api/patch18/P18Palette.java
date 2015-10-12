package com.jfixby.r3.ext.api.patch18;

import com.jfixby.r3.ext.api.patch18.palette.Fabric;
import com.jfixby.r3.ext.api.patch18.palette.FabricsList;
import com.jfixby.r3.ext.api.patch18.palette.RelationsList;

public interface P18Palette {

	void print();

	FabricsList listFabrics();

	RelationsList listRelations();

	Fabric findClosestFabric(Fabric from_fabric, Fabric direction);

}
