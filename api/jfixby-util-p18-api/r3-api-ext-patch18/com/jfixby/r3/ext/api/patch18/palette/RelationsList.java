package com.jfixby.r3.ext.api.patch18.palette;

public interface RelationsList {

	int size();

	FabricsRelation getRelation(int i);

	FabricsRelation findRelationFor(Fabric fabric_a, Fabric fabric_b);

	int indexOf(FabricsRelation relation);
	
	void print(String tag);

}
