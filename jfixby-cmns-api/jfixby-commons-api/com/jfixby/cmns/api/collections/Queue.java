package com.jfixby.cmns.api.collections;

public interface Queue<T> extends Collection<T> {

	void put(T element);

	void putAll(Collection<T> elements);

	boolean hasMore();

	T get();

	void clear();

}
