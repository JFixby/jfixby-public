package com.jfixby.util.patch18.red;

import com.jfixby.r3.ext.api.patch18.palette.Fabric;
import com.jfixby.r3.ext.api.patch18.palette.FabricRelationSpecs;
import com.jfixby.r3.ext.api.patch18.palette.FabricsRelation;

class FacricRelationImpl implements FabricsRelation {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((lowerFabric == null) ? 0 : lowerFabric.hashCode());
		result = prime * result
				+ ((upperFabric == null) ? 0 : upperFabric.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FacricRelationImpl other = (FacricRelationImpl) obj;
		if (lowerFabric == null) {
			if (other.lowerFabric != null)
				return false;
		} else if (!lowerFabric.equals(other.lowerFabric))
			return false;
		if (upperFabric == null) {
			if (other.upperFabric != null)
				return false;
		} else if (!upperFabric.equals(other.upperFabric))
			return false;
		return true;
	}

	public Fabric getUpperFabric() {
		return upperFabric;
	}

	public void setUpperFabric(Fabric upperFabric) {
		this.upperFabric = upperFabric;
	}

	public Fabric getLowerFabric() {
		return lowerFabric;
	}

	public void setLowerFabric(Fabric lowerFabric) {
		this.lowerFabric = lowerFabric;
	}

	@Override
	public String toString() {
		return "" + upperFabric.getName() + "-vs-" + lowerFabric.getName() + "";
	}

	Fabric upperFabric;
	Fabric lowerFabric;

	public FacricRelationImpl(FabricRelationSpecs relation_specs) {
		upperFabric = relation_specs.getUpperFabric();
		lowerFabric = relation_specs.getLowerFabric();
	}

	@Override
	public String getIDString() {
		return upperFabric.getName().toLowerCase() + "-on-"
				+ lowerFabric.getName().toLowerCase();
	}

	@Override
	public boolean isAbout(Fabric fabric_a, Fabric fabric_b) {
		return (this.upperFabric == fabric_a && this.lowerFabric == fabric_b) || //
				(this.upperFabric == fabric_b && this.lowerFabric == fabric_a);
	}
}
