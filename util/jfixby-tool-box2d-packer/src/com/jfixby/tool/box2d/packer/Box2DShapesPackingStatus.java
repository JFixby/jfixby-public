package com.jfixby.tool.box2d.packer;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.filesystem.File;

public class Box2DShapesPackingStatus {
	
	private List<File> related_folders;

	public void setRelatedFolders(List<File> related_folders) {
		this.related_folders = related_folders;
	}

	public Box2DShapesPackingStatus() {

	}

	public Collection<File> getRelatedFolders() {
		return related_folders;
	}

}