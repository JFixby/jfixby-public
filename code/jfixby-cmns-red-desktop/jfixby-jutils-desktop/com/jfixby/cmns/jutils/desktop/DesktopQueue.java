package com.jfixby.cmns.jutils.desktop;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.Queue;

public class DesktopQueue<T> extends DesktopList<T> implements Queue<T> {

	@Override
	public void put(T element) {
		this.add(element);
	}

	@Override
	public boolean hasMore() {
		return this.size() > 0;
	}

	@Override
	public T get() {
		return this.removeElementAt(0);
	}

	@Override
	public void putAll(Collection<T> elements) {
		super.addAll(elements);
	}
}
