package com.jfixby.util.patch18.red;


import com.jfixby.cmns.api.collections.JUtils;
import com.jfixby.cmns.api.collections.Set;
import com.jfixby.r3.ext.api.patch18.P18PaletteFactory;
import com.jfixby.r3.ext.api.patch18.PaletteSpecs;
import com.jfixby.r3.ext.api.patch18.palette.Fabric;
import com.jfixby.r3.ext.api.patch18.palette.FabricRelationSpecs;
import com.jfixby.r3.ext.api.patch18.palette.FabricsRelation;

class PaletteSpecsImpl implements PaletteSpecs {

	final Set<Fabric> fabrics = JUtils.newSet();
	final Set<FabricsRelation> relations = JUtils.newSet();
	private String name;
	private final P18PaletteFactory patch18Factory;

	public PaletteSpecsImpl(P18PaletteFactory patch18Factory) {
		super();
		this.patch18Factory = patch18Factory;
	}

	@Override
	public void addFabric(Fabric fabric) {
		fabrics.add(fabric);
	}

	@Override
	public FabricsRelation defineRelation(Fabric upper_fabric,
			Fabric lower_fabric) {
		if (!fabrics.contains(upper_fabric)) {
			throw new Error(
					"Unknown fabric: "
							+ upper_fabric
							+ ". Use addFabric() method to add the fabric before defining it's relations.");
		}
		if (!fabrics.contains(lower_fabric)) {
			throw new Error(
					"Unknown fabric: "
							+ lower_fabric
							+ ". Use addFabric() method to add the fabric before defining it's relations.");
		}

		FabricRelationSpecs relation_specs = patch18Factory
				.newFacricRelationSpecs();
		relation_specs.setUpperFabric(upper_fabric);
		relation_specs.setLowerFabric(lower_fabric);
		FabricsRelation relation = patch18Factory
				.newFacricRelation(relation_specs);
		relations.add(relation);
		return relation;
	}

	@Override
	public void setPaletteName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public Set<Fabric> getFabricsList() {
		return this.fabrics;
	}

	public Set<FabricsRelation> getRelationsList() {
		return this.relations;
	}

}
