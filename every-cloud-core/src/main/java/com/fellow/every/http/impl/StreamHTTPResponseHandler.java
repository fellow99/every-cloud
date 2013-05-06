package com.fellow.every.http.impl;

import java.io.InputStream;
import java.io.OutputStream;

import com.fellow.every.disk.ProgressListener;
import com.fellow.every.exception.ApiException;
import com.fellow.every.exception.ServerException;
import com.fellow.every.http.HTTPRequest;
import com.fellow.every.http.HTTPResponse;
import com.fellow.every.http.HTTPResponseHandler;
import com.fellow.every.http.HTTPStreamUtil;

public class StreamHTTPResponseHandler implements HTTPResponseHandler{
	private OutputStream os;
	private ProgressListener listener;
	public StreamHTTPResponseHandler(final OutputStream os, final ProgressListener listener){
		this.os = os;
		this.listener = listener;
	}
	public void handle(HTTPRequest request, HTTPResponse response, InputStream is) throws ServerException, ApiException{
		if(response.getCode() == 200){
			long total = response.getContentLength();
			HTTPStreamUtil.bufferedWriting(os, is, total, listener);
		} else {
			String url = request.getUrl();
			String code = new Integer(response.getCode()).toString();
			String message = response.getMessage();
			String info = HTTPStreamUtil.toString(is);
			throw new ServerException(url, code, message, info);
		}
	}
}