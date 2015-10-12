package com.jfixby.r3.api.ui.unit.raster;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.color.Color;

public interface Raster extends ProjectableComponent {
	public AssetID getAssetId();
	
	public void setOpacity(double alpha);

	public double getOpacity();

	void setDebugRenderFlag(boolean b);

	boolean getDebugRenderFlag();

	public void setDebugColor(Color debug_render_color);

	public Color getDebugColor();

	
}