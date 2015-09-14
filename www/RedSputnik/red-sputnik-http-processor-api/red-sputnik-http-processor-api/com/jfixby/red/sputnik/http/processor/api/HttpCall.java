package com.jfixby.red.sputnik.http.processor.api;

public interface HttpCall {

	boolean toAnswer();

	void logEnd();

	void logIgnored();

	void beginCall(String original_path_info);

	void setServerDebugMode(boolean is_debug_server);

	void setIsDebugCall(boolean b);

	void logBegin();

	void forward(String new_path_info);

}
