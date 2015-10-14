package com.jfixby.util.patch18.red;

import com.jfixby.cmns.api.collections.JUtils;
import com.jfixby.cmns.api.collections.Set;
import com.jfixby.r3.ext.api.patch18.palette.Fabric;
import com.jfixby.r3.ext.api.patch18.palette.FabricsRelation;
import com.jfixby.r3.ext.api.patch18.palette.RelationsList;

public class FabricsRelationListImpl implements RelationsList {

	private Set<FabricsRelation> relations;

	public void setup(Set<FabricsRelation> relationsList) {
		relations = JUtils.checkNull("relationsList", relationsList);
	}

	public FabricsRelation getElementAt(int i) {
		return relations.getElementAt(i);
	}

	@Override
	public FabricsRelation findRelationFor(Fabric fabric_a, Fabric fabric_b) {
		if (fabric_a == null) {
			throw new Error("Fabric A is null");
		}
		if (fabric_b == null) {
			throw new Error("Fabric B is null");
		}

		for (int i = 0; i < relations.size(); i++) {
			FabricsRelation relation = relations.getElementAt(i);
			if (relation.isAbout(fabric_a, fabric_b)) {
				return relation;
			}
		}
		return null;
	}

	@Override
	public int size() {
		return relations.size();
	}

	@Override
	public FabricsRelation getRelation(int i) {
		return relations.getElementAt(i);
	}

	@Override
	public int indexOf(FabricsRelation relation) {
		return relations.indexOf(relation);
	}

	@Override
	public void print(String tag) {
		relations.print(tag);
	}

}
