package com.jfixby.red.sputnik.http.processor.api;

import com.jfixby.cmns.api.collections.Map;
import com.jfixby.cmns.api.io.InputStream;

public interface ServletRequest {

	String getSessionID();

	Map<String, String> getRequestHeaders();

	InputStream getClientToServerStream();



}
