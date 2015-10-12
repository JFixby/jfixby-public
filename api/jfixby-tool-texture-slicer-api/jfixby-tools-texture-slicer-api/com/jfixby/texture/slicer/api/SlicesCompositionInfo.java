package com.jfixby.texture.slicer.api;

import java.util.Vector;

public class SlicesCompositionInfo {

	public String composition_asset_id_string;
	public int cell_width = 0;
	public int cell_height = 0;
	public int cell_margin = 0;

	public Vector<SliceInfo> tiles = new Vector<SliceInfo>();

	public void addTile(int i, int j, String postfix, boolean is_empty,
			double width, double height) {
		SliceInfo t = new SliceInfo();
		t.index_x = i;
		t.index_y = j;
		t.tile_width = width;
		t.tile_height = height;
		t.postfix = postfix;
		t.is_empty = is_empty;
		tiles.add(t);
	}

	public String getAssetID() {
		return composition_asset_id_string;
	}
}
