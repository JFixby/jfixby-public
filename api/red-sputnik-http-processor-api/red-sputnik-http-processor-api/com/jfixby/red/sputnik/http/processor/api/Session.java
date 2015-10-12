package com.jfixby.red.sputnik.http.processor.api;

import java.util.Date;

public interface Session {

	Date getLoggedInAt();

	void storeObject(String name, Object object);

	Object getStoredObject(String name);

	void invalidate();

}
