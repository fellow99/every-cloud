package com.fellow.every.http.impl;

import java.io.*;

import com.fellow.every.disk.ProgressListener;
import com.fellow.every.exception.ApiException;
import com.fellow.every.exception.ServerException;
import com.fellow.every.http.HTTPRequest;
import com.fellow.every.http.HTTPResponse;
import com.fellow.every.http.HTTPResponseHandler;
import com.fellow.every.http.HTTPStreamUtil;

public class ByteArrayHTTPResponseHandler implements HTTPResponseHandler{
	private byte[] buffer;
	private ProgressListener listener;
	public ByteArrayHTTPResponseHandler(final ProgressListener listener){
		this.listener = listener;
	}
	public void handle(HTTPRequest request, HTTPResponse response, InputStream is) throws ServerException, ApiException{
		if(response.getCode() == 200){
			long total = response.getContentLength();
			ByteArrayOutputStream os = new ByteArrayOutputStream((int)total);
			HTTPStreamUtil.bufferedWriting(os, is, total, listener);
			buffer = os.toByteArray();
		} else {
			String url = request.getUrl();
			String code = new Integer(response.getCode()).toString();
			String message = response.getMessage();
			String info = HTTPStreamUtil.toString(is);
			throw new ServerException(url, code, message, info);
		}
	}
	
	public byte[] getByteArray(){
		return buffer;
	}
}