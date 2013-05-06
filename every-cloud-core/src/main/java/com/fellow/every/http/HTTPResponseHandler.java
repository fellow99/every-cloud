package com.fellow.every.http;

import java.io.*;

import com.fellow.every.exception.ApiException;
import com.fellow.every.exception.ServerException;

public interface HTTPResponseHandler {
	void handle(HTTPRequest request, HTTPResponse response, InputStream is) throws ServerException, ApiException;
}
