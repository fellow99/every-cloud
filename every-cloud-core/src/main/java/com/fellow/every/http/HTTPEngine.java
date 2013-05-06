package com.fellow.every.http;

import java.io.InputStream;

import com.fellow.every.exception.ApiException;
import com.fellow.every.exception.ServerException;

public interface HTTPEngine {
	
	void get(HTTPRequest request, HTTPResponseHandler handler) throws ServerException, ApiException;
	
	void post(HTTPRequest request, HTTPResponseHandler handler) throws ServerException, ApiException;
	
	void postUpload(HTTPRequest request, HTTPResponseHandler handler,
			InputStream datastream, long size) throws ServerException, ApiException;
	
	void upload(HTTPRequest request, HTTPResponseHandler handler,
			InputStream datastream, long size) throws ServerException, ApiException;
}
