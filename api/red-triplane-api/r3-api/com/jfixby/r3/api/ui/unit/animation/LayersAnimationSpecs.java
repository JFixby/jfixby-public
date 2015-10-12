package com.jfixby.r3.api.ui.unit.animation;

import com.jfixby.cmns.api.collections.List;
import com.jfixby.r3.api.ui.unit.layer.VisibleComponent;

public interface LayersAnimationSpecs extends AnimationSpecs {

	void addFrame(VisibleComponent child);

	List<VisibleComponent> getFrames();

	

	// void setIsSimple(boolean is_simple_animation);

	// boolean isSimple();

	long getFrameTime();

	void setFrameTime(long single_frame_time);

	// void setIsPositionModifyer(boolean is_positions_modifyer_animation);

	// boolean isPositionModifyer();

	// Anchor addAnchor(long parseLong, double position_x, double position_y);

	// List<Anchor> getAnchors();

}