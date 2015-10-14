package com.jfixby.util.patch18.red;

import com.jfixby.cmns.api.collections.JUtils;
import com.jfixby.cmns.api.collections.Set;
import com.jfixby.r3.ext.api.patch18.palette.Fabric;
import com.jfixby.r3.ext.api.patch18.palette.FabricsList;

public class FabricsListImpl implements FabricsList {

	private Set<Fabric> fabricsList;

	public void setup(Set<Fabric> fabricsList) {
		this.fabricsList = JUtils.checkNull("fabricsList", fabricsList);
	}

	public int size() {
		return fabricsList.size();
	}

	public Fabric getElementAt(int i) {
		return fabricsList.getElementAt(i);
	}

	@Override
	public Fabric findFabricByName(String string) {
		for (int i = 0; i < this.fabricsList.size(); i++) {
			Fabric fabric_i = fabricsList.getElementAt(i);
			if (fabric_i.getName().equals(string)) {
				return fabric_i;
			}
		}

		return null;
	}

	@Override
	public Fabric getLast() {
		return this.fabricsList.getLast();
	}

	@Override
	public Fabric getFabric(int i) {
		return fabricsList.getElementAt(i);
	}

	@Override
	public int indexOf(Fabric material_a) {
		return fabricsList.indexOf(material_a);
	}

	@Override
	public void print(String tag) {
		fabricsList.print(tag);
	}

	@Override
	public boolean contains(Fabric fabric) {
		return fabricsList.contains(fabric);
	}

}
