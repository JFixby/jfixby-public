package com.jfixby.util.patch18.red.materials;

public class MaterialName {
	static int id = -1;
	{
		systemName = "unknownMaterial" + newID();
	}

	public static int getId() {
		return id;
	}

	public static void setId(int id) {
		MaterialName.id = id;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	private String systemName;

	private static synchronized String newID() {
		id++;
		return id + "";
	}

	public void set(final MaterialName name) {
		this.systemName = name.getSystemName();
	}

	public boolean is(String name) {
		return this.systemName.equals(name);
	}
}
