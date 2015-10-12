package com.jfixby.r3.ext.api.patch18.palette;

public interface FabricsList {

	int size();

	Fabric getFabric(int i);

	int indexOf(Fabric material_a);

	Fabric findFabricByName(String string);

	Fabric getLast();

	void print(String tag);

	boolean contains(Fabric fabric);

}
