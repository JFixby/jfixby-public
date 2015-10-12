package com.jfixby.red.sputnik.http.processor.api;

import java.io.IOException;

import com.jfixby.cmns.api.collections.Map;
import com.jfixby.cmns.api.io.InputStream;
import com.jfixby.cmns.api.io.OutputStream;

public interface HTTPProxyCallProcessor {

	void beginCall(String session_id, InputStream client_to_server_stream,
			Map<String, String> request_headers,
			OutputStream server_to_client_stream,
			Map<String, String> response_headers) throws IOException;

	void process_forwarded(String session_id,
			InputStream client_to_server_stream,
			Map<String, String> request_headers,
			OutputStream server_to_client_stream,
			Map<String, String> response_headers) throws IOException;

	HttpCall newHttpCall(CallData mapMessage, CallSource proxyConnector);

}
