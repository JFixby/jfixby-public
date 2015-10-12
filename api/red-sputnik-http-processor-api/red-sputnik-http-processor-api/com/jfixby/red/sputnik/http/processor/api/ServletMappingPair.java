package com.jfixby.red.sputnik.http.processor.api;

public class ServletMappingPair {
	String ServletClassName;
	String PathPattern;

	public String getServletClassName() {
		return ServletClassName;
	}

	public void setServletClassName(String servletClassName) {
		ServletClassName = servletClassName;
	}

	public String getPathPattern() {
		return PathPattern;
	}

	public void setPathPattern(String pathPattern) {
		PathPattern = pathPattern;
	}

}
