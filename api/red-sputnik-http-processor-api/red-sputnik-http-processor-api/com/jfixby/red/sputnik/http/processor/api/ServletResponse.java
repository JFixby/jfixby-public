package com.jfixby.red.sputnik.http.processor.api;

import com.jfixby.cmns.api.collections.Map;
import com.jfixby.cmns.api.io.OutputStream;

public interface ServletResponse {

	OutputStream getServerToClientStream();

	Map<String, String> getResponseHeaders();

}
