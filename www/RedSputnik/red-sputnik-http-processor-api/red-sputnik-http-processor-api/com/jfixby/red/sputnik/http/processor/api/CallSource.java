package com.jfixby.red.sputnik.http.processor.api;


public interface CallSource {

	void process_message(CallData mapMessage) throws Exception;

	void connectToMyMailBox(HTTPProxyCallProcessor http_processor);

	void init(String mq_server_host, int mq_server_port,
			String web_proxy_to_server, String web_server_to_proxy,
			String mq_server_login, String mq_server_password,
			String mq_file_server_host);

}
