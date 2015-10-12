package com.jfixby.red.sputnik.http.processor.api;

import java.io.IOException;

import com.jfixby.cmns.api.io.OutputStream;

public interface HTMLTemplate {

	void setParameterValue(String name, String value);

	void flushTo(OutputStream server_to_client_stream) throws IOException;

	public void print();

	String wrapURL(String url_href, String caption);

}
