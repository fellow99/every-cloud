package com.fellow.every.http.impl;

import java.io.*;
import java.util.*;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.PartSource;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.fellow.every.exception.ApiException;
import com.fellow.every.exception.ServerException;
import com.fellow.every.http.HTTPEngine;
import com.fellow.every.http.HTTPRequest;
import com.fellow.every.http.HTTPResponse;
import com.fellow.every.http.HTTPResponseHandler;

public class HTTPClient3 implements HTTPEngine {
	private HttpClient httpClient;

	public HTTPClient3(){
		this.httpClient = new HttpClient();
	}
	
	@Override
	public void get(HTTPRequest request, HTTPResponseHandler handler) throws ServerException, ApiException {
		try {
			GetMethod method = new GetMethod(request.getUrl());
	
			for(Map.Entry<String, String> entry : request.getHeaders().entrySet()){
				method.addRequestHeader(entry.getKey(), entry.getValue());
			}
			
			httpClient.executeMethod(method);
	
			HTTPResponse response = new HTTPResponse(
					method.getStatusCode(),
					method.getStatusText(),
					method.getResponseContentLength(),
					method.getResponseCharSet());
			InputStream bodyStream = method.getResponseBodyAsStream();
			
			handler.handle(request, response, bodyStream);
			
			bodyStream.close();
			method.releaseConnection();
		} catch (IOException e) {
			throw new ApiException(e);
		}
	}

	@Override
	public void post(HTTPRequest request, HTTPResponseHandler handler) throws ServerException, ApiException {
		try {
			PostMethod method = new PostMethod(request.getUrl());
	
			for(Map.Entry<String, String> entry : request.getHeaders().entrySet()){
				method.addRequestHeader(entry.getKey(), entry.getValue());
			}
			for(Map.Entry<String, String> entry : request.getBodyParameters().entrySet()){
				method.getParams().setParameter(entry.getKey(), entry.getValue());
			}
			
			httpClient.executeMethod(method);
	
			HTTPResponse response = new HTTPResponse(
					method.getStatusCode(),
					method.getStatusText(),
					method.getResponseContentLength(),
					method.getResponseCharSet());
			InputStream bodyStream = method.getResponseBodyAsStream();
			
			handler.handle(request, response, bodyStream);
			
			bodyStream.close();
			method.releaseConnection();
		} catch (IOException e) {
			throw new ApiException(e);
		}
	}

	@Override
	public void postUpload(HTTPRequest request, HTTPResponseHandler handler,
			InputStream datastream, long size) throws ServerException,
			ApiException {
		try {
			PostMethod method = new PostMethod(request.getUrl());
	
			for(Map.Entry<String, String> entry : request.getHeaders().entrySet()){
				method.addRequestHeader(entry.getKey(), entry.getValue());
			}
			
			InputStreamRequestEntity entity = new InputStreamRequestEntity(datastream, size);
			method.setRequestEntity(entity);
			httpClient.executeMethod(method);
	
			HTTPResponse response = new HTTPResponse(
					method.getStatusCode(),
					method.getStatusText(),
					method.getResponseContentLength(),
					method.getResponseCharSet());
			InputStream bodyStream = method.getResponseBodyAsStream();
			
			handler.handle(request, response, bodyStream);
			
			bodyStream.close();
			method.releaseConnection();
		} catch (IOException e) {
			throw new ApiException(e);
		}
	}

	@Override
	public void upload(HTTPRequest request, HTTPResponseHandler handler,
			final InputStream datastream, final long size) throws ServerException,
			ApiException {
		try {
			PostMethod method = new PostMethod(request.getUrl());
	
			for(Map.Entry<String, String> entry : request.getHeaders().entrySet()){
				method.addRequestHeader(entry.getKey(), entry.getValue());
			}
			for(Map.Entry<String, String> entry : request.getBodyParameters().entrySet()){
				method.getParams().setParameter(entry.getKey(), entry.getValue());
			}
			
			PartSource source = new PartSource(){
				public InputStream createInputStream() throws IOException {
					return datastream;
				}
				public String getFileName() {
					return "file";
				}
				public long getLength() {
					return size;
				}
			};
			FilePart part = new FilePart("file",source);
			MultipartRequestEntity entity = new MultipartRequestEntity(new Part[]{part}, new HttpMethodParams());
			method.setRequestEntity(entity);
			
			httpClient.executeMethod(method);
	
			HTTPResponse response = new HTTPResponse(
					method.getStatusCode(),
					method.getStatusText(),
					method.getResponseContentLength(),
					method.getResponseCharSet());
			InputStream bodyStream = method.getResponseBodyAsStream();
			
			handler.handle(request, response, bodyStream);
			
			bodyStream.close();
			method.releaseConnection();
		} catch (IOException e) {
			throw new ApiException(e);
		}
	}

}
