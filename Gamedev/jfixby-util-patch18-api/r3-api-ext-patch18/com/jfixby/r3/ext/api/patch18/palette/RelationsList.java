package com.jfixby.r3.ext.api.patch18.palette;

public interface RelationsList {

	int numberOfRelations();

	FabricsRelation getRelation(int i);

	FabricsRelation findRelationFor(Fabric fabric_a, Fabric fabric_b);

}
