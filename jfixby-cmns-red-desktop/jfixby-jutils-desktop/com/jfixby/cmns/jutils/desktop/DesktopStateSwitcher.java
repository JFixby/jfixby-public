package com.jfixby.cmns.jutils.desktop;

import com.jfixby.cmns.api.collections.JUtils;
import com.jfixby.cmns.api.collections.StateSwitcher;
import com.jfixby.cmns.api.log.L;

public class DesktopStateSwitcher<T> implements StateSwitcher<T> {
	T state;
	private String debug_name;

	public DesktopStateSwitcher(T default_state) {
		debug_name = "StateSwitcher<?>";
		this.switchState(default_state);
	}

	@Override
	public void expectState(T expected_state) {
		if (!this.state.equals(expected_state)) {
			throw new Error("Wrong state=" + this.state + ", expected: "
					+ expected_state);
		}
	}

	@Override
	public void switchState(T next_state) {
		if (next_state == null) {
			JUtils.checkNull("next_state", next_state);
		}
		if (debug) {
			L.d(this.debug_name + ": " + this.state + " -", next_state);
		}
		this.state = next_state;
	}

	@Override
	public T currentState() {
		return this.state;
	}

	@Override
	public void setDebugName(String string) {
		this.debug_name = string;
	}

	boolean debug = false;

	@Override
	public void setDebugFlag(boolean b) {
		debug = b;
	}

	@Override
	public String toString() {
		return "<" + state + ">";
	}

}
