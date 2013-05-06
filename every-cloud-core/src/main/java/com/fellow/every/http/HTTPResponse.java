package com.fellow.every.http;

public class HTTPResponse {
	private int code;
	private String message;
	private long contentLength;
	private String charset;

	public HTTPResponse(int code, String message, long contentLength, String charset){
		this.code = code;
		this.message = message;
		this.contentLength = contentLength;
		this.charset = charset;
	}
	
	public int getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	public long getContentLength() {
		return contentLength;
	}
	public String getCharset() {
		return charset;
	}
}
