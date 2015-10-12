package com.jfixby.r3.ext.api.scene2d.srlz;

import java.util.Vector;

public class AnimationSettings {

	public static final long MAX = 1000 * 60 * 60 * 24 * 365 * 365;

	public boolean is_positions_modifyer_animation = false;
	public boolean is_simple_animation = false;

	public boolean is_looped = false;
	public boolean autostart = false;
	public Vector<Anchor> anchors = null;
	public String single_frame_time = MAX + "";

	public long single_frame_time() {
		if (single_frame_time == null) {
			return MAX;
		}
		if ("".equals(single_frame_time)) {
			return MAX;
		}
		return Long.parseLong(single_frame_time);
	}

}
