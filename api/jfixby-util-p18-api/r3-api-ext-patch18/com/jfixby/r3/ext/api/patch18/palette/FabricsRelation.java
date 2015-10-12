package com.jfixby.r3.ext.api.patch18.palette;

public interface FabricsRelation {

	Fabric getUpperFabric();

	Fabric getLowerFabric();

	public String getIDString();

	boolean isAbout(Fabric fabric_a, Fabric fabric_b);

}
