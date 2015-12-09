package com.jfixby.r3.ext.api.patch18;

import com.jfixby.cmns.api.ComponentInstaller;

public class P18 {

	static private ComponentInstaller<P18Component> componentInstaller = new ComponentInstaller<P18Component>(
			"P18");

	public static final void installComponent(P18Component component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final P18Component invoke() {
		return componentInstaller.invokeComponent();
	}

	public static final P18Component component() {
		return componentInstaller.getComponent();
	}

	public static GridFactory getGridFactory() {
		return invoke().getGridFactory();
	}
}
