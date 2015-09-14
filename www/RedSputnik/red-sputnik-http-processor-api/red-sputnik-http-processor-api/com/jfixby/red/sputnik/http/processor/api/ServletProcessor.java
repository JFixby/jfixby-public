package com.jfixby.red.sputnik.http.processor.api;

import java.io.IOException;

public interface ServletProcessor {

	Session findSessionByCookie(String cookie);

	void forward(ServletRequest request, ServletResponse response,
			String new_path_info) throws IOException;

	Session createSession(String cookie);

	HTMLTemplate getTemplate(String string) throws IOException;

	void redirect(ServletRequest request, ServletResponse response,
			String string);

	String getServerName();

}
