package com.jfixby.util.patch18.red.fields;

public interface BadCell {

	abstract public BadCell replicate();

	abstract public BadCell inverse();

	abstract public BadCell set(BadCell other);

}
