package com.fellow.every.http.impl;

import java.io.*;
import java.util.*;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.fellow.every.exception.ApiException;
import com.fellow.every.exception.ServerException;
import com.fellow.every.http.HTTPEngine;
import com.fellow.every.http.HTTPRequest;
import com.fellow.every.http.HTTPResponse;
import com.fellow.every.http.HTTPResponseHandler;
import com.fellow.every.http.HTTPStreamUtil;

public class HTTPURLConnection implements HTTPEngine {
	
	public HTTPURLConnection(){
		this(false);
	}
	
	public HTTPURLConnection(boolean acceptAllCookie){
		if(acceptAllCookie){
			CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
		}
	}

	@Override
	public void get(HTTPRequest request, HTTPResponseHandler handler) throws ServerException, ApiException {
		try {
			URL u = new URL(request.getUrl());
			HttpURLConnection conn = (HttpURLConnection)u.openConnection();
			conn.setRequestMethod("GET");
	
			for(Map.Entry<String, String> entry : request.getHeaders().entrySet()){
				conn.addRequestProperty(entry.getKey(), entry.getValue());
			}
			
			int code = conn.getResponseCode();
			if(code == HttpURLConnection.HTTP_MOVED_TEMP){
				String location = conn.getHeaderField("Location");
				
				HTTPRequest req = new HTTPRequest(location);
				this.get(req, handler);
				
				return;
			}
			
			InputStream bodyStream = null;
			try {
				bodyStream = conn.getInputStream();
			} catch (IOException e) {
				bodyStream = conn.getErrorStream();
			}
			if(bodyStream == null)bodyStream = new ByteArrayInputStream(new byte[0]);
			HTTPResponse response = new HTTPResponse(
					conn.getResponseCode(),
					conn.getResponseMessage(),
					conn.getContentLength(),
					conn.getContentEncoding());

			handler.handle(request, response, bodyStream);
			bodyStream.close();
			conn.disconnect();
		} catch (java.io.FileNotFoundException e) {
			HTTPResponse response = new HTTPResponse(404, e.getMessage(), -1, null);
			
			handler.handle(request, response, new ByteArrayInputStream(new byte[0]));
		} catch (MalformedURLException e) {
			throw new ApiException(e);
		} catch (IOException e) {
			throw new ApiException(e);
		}
	}

	@Override
	public void post(HTTPRequest request, HTTPResponseHandler handler) throws ServerException, ApiException {
		try {
			URL u = new URL(request.getUrl());
			HttpURLConnection conn = (HttpURLConnection)u.openConnection();
			conn.setRequestMethod("POST");
	
			for(Map.Entry<String, String> entry : request.getHeaders().entrySet()){
				conn.addRequestProperty(entry.getKey(), entry.getValue());
			}

			conn.setDoOutput(true);
			byte[] body = HTTPRequest.encodeParameters(request.getBodyParameters()).getBytes();
			conn.getOutputStream().write(body);
			
			InputStream bodyStream = null;
			try {
				bodyStream = conn.getInputStream();
			} catch (IOException e) {
				bodyStream = conn.getErrorStream();
			}
			if(bodyStream == null)bodyStream = new ByteArrayInputStream(new byte[0]);
			HTTPResponse response = new HTTPResponse(
					conn.getResponseCode(),
					conn.getResponseMessage(),
					conn.getContentLength(),
					conn.getContentEncoding());

			handler.handle(request, response, bodyStream);
			bodyStream.close();
			
			conn.disconnect();
		} catch (MalformedURLException e) {
			throw new ApiException(e);
		} catch (IOException e) {
			throw new ApiException(e);
		}
	}

	@Override
	public void postUpload(HTTPRequest request, HTTPResponseHandler handler,
			InputStream datastream, long size) throws ServerException, ApiException {
		try {
			URL u = new URL(request.getUrl());
			HttpURLConnection conn = (HttpURLConnection)u.openConnection();
			conn.setRequestMethod("POST");
	
			for(Map.Entry<String, String> entry : request.getHeaders().entrySet()){
				conn.addRequestProperty(entry.getKey(), entry.getValue());
			}

			conn.setDoOutput(true);

			OutputStream os = conn.getOutputStream();
			int len = -1;
			byte[] buf = new byte[1024];
			while((len = datastream.read(buf)) != -1){
				os.write(buf, 0, len);
			}
			os.close();
			
			InputStream bodyStream = null;
			try {
				bodyStream = conn.getInputStream();
			} catch (IOException e) {
				bodyStream = conn.getErrorStream();
			}
			if(bodyStream == null)bodyStream = new ByteArrayInputStream(new byte[0]);
			HTTPResponse response = new HTTPResponse(
					conn.getResponseCode(),
					conn.getResponseMessage(),
					conn.getContentLength(),
					conn.getContentEncoding());

			handler.handle(request, response, bodyStream);
			bodyStream.close();
			
			conn.disconnect();
		} catch (MalformedURLException e) {
			throw new ApiException(e);
		} catch (IOException e) {
			throw new ApiException(e);
		}
	}

	@Override
	public void upload(HTTPRequest request, HTTPResponseHandler handler,
			InputStream datastream, long size) throws ServerException, ApiException {
        try {
			URL u = new URL(request.getUrl());
			HttpURLConnection conn = (HttpURLConnection)u.openConnection();
			conn.setRequestMethod("POST");

			conn.setDoOutput(true);
			String boundary = new Long(System.currentTimeMillis()).toString();

			conn.setRequestProperty("connection", "keep-alive");
	        conn.setRequestProperty("Cache-Control", "no-cache");
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            
            OutputStream os = conn.getOutputStream();
            os.write(("--" + boundary + "\r\n").getBytes());
            os.write(("Content-Disposition: form-data; name=\"file\"; filename=\"file\"\r\n").getBytes());
            os.write(("Content-Type: application/octet-stream\r\n\r\n").getBytes());
            
            HTTPStreamUtil.bufferedWriting(os, datastream, size, null);

            os.write(("\r\n").getBytes());
            os.write(("--" + boundary + "\r\n").getBytes());

			InputStream bodyStream = null;
			try {
				bodyStream = conn.getInputStream();
			} catch (IOException e) {
				bodyStream = conn.getErrorStream();
			}
			if(bodyStream == null)bodyStream = new ByteArrayInputStream(new byte[0]);
			HTTPResponse response = new HTTPResponse(
					conn.getResponseCode(),
					conn.getResponseMessage(),
					conn.getContentLength(),
					conn.getContentEncoding());

			handler.handle(request, response, bodyStream);
			bodyStream.close();
			
			conn.disconnect();
        } catch (IOException e) {
        	throw new ApiException(e);
        }
	}
}
