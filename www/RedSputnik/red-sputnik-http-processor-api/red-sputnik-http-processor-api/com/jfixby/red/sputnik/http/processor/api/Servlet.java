package com.jfixby.red.sputnik.http.processor.api;

import java.io.IOException;

public interface Servlet {

	void doProcess(ServletRequest request, ServletResponse response, ServletProcessor servlet_processor)
			throws IOException;

}
