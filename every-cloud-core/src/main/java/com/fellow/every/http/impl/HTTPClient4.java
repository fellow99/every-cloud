package com.fellow.every.http.impl;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.fellow.every.exception.ApiException;
import com.fellow.every.exception.ServerException;
import com.fellow.every.http.HTTPEngine;
import com.fellow.every.http.HTTPRequest;
import com.fellow.every.http.HTTPResponse;
import com.fellow.every.http.HTTPResponseHandler;

public class HTTPClient4 implements HTTPEngine {
	private HttpClient httpClient;

	public HTTPClient4(){
		this.httpClient = new DefaultHttpClient();
	}
	
	
	@Override
	public void get(HTTPRequest request, HTTPResponseHandler handler) throws ServerException, ApiException {
		try {
			HttpGet resq = new HttpGet(request.getUrl());
			for(Map.Entry<String, String> entry : request.getHeaders().entrySet()){
				resq.addHeader(entry.getKey(), entry.getValue());
			}
			for(Map.Entry<String, String> entry : request.getBodyParameters().entrySet()){
				resq.getParams().setParameter(entry.getKey(), entry.getValue());
			}
			
			HttpResponse resp = httpClient.execute(resq);
	
			StatusLine status = resp.getStatusLine();
			HttpEntity entity = resp.getEntity();
			Header charset = entity.getContentEncoding();
			
			HTTPResponse response = new HTTPResponse(
					status.getStatusCode(),
					status.getReasonPhrase(),
					entity.getContentLength(),
					(charset == null ? null : charset.getValue()));
	
			handler.handle(request, response, entity.getContent());
	
			EntityUtils.consume(entity);
		} catch (IOException e) {
			throw new ApiException(e);
		}
	}


	@Override
	public void post(HTTPRequest request, HTTPResponseHandler handler) throws ServerException, ApiException {
		try {
			HttpPost resq = new HttpPost(request.getUrl());
			for(Map.Entry<String, String> entry : request.getHeaders().entrySet()){
				resq.addHeader(entry.getKey(), entry.getValue());
			}
			for(Map.Entry<String, String> entry : request.getBodyParameters().entrySet()){
				resq.getParams().setParameter(entry.getKey(), entry.getValue());
			}
			
			HttpResponse resp = httpClient.execute(resq);
	
			StatusLine status = resp.getStatusLine();
			HttpEntity entity = resp.getEntity();
			Header charset = entity.getContentEncoding();
			
			HTTPResponse response = new HTTPResponse(
					status.getStatusCode(),
					status.getReasonPhrase(),
					entity.getContentLength(),
					(charset == null ? null : charset.getValue()));
			InputStream bodyStream = entity.getContent();
			
			handler.handle(request, response, bodyStream);
	
			bodyStream.close();
			EntityUtils.consume(entity);
		} catch (IOException e) {
			throw new ApiException(e);
		}
	}

	@Override
	public void postUpload(HTTPRequest request, HTTPResponseHandler handler,
			InputStream datastream, long size) throws ServerException, ApiException {
		try {
			HttpPost resq = new HttpPost(request.getUrl());
			for(Map.Entry<String, String> entry : request.getHeaders().entrySet()){
				resq.addHeader(entry.getKey(), entry.getValue());
			}
			
			InputStreamEntity postEntity = new InputStreamEntity(datastream, size);
			resq.setEntity(postEntity);
			HttpResponse resp = httpClient.execute(resq);
	
			StatusLine status = resp.getStatusLine();
			HttpEntity entity = resp.getEntity();
			Header charset = entity.getContentEncoding();
			
			HTTPResponse response = new HTTPResponse(
					status.getStatusCode(),
					status.getReasonPhrase(),
					entity.getContentLength(),
					(charset == null ? null : charset.getValue()));
			InputStream bodyStream = entity.getContent();
			
			handler.handle(request, response, bodyStream);
	
			bodyStream.close();
			EntityUtils.consume(entity);
		} catch (IOException e) {
			throw new ApiException(e);
		}
	}


	@Override
	public void upload(HTTPRequest request, HTTPResponseHandler handler,
			InputStream datastream, final long size) throws ServerException, ApiException {
		try {
	        HttpPost resq = new HttpPost(request.getUrl());
			MultipartEntity multipartEntity = new MultipartEntity();

			InputStreamBody bin = new InputStreamBody(datastream, "application/octet-stream", "file"){
				public long getContentLength() {
			        return size;
			    }
			};
			multipartEntity.addPart("file", bin);

			resq.setEntity(multipartEntity);
			
			HttpResponse resp = httpClient.execute(resq);
	
			StatusLine status = resp.getStatusLine();
			HttpEntity entity = resp.getEntity();
			Header charset = entity.getContentEncoding();
			
			HTTPResponse response = new HTTPResponse(
					status.getStatusCode(),
					status.getReasonPhrase(),
					entity.getContentLength(),
					(charset == null ? null : charset.getValue()));
			InputStream bodyStream = entity.getContent();
			
			handler.handle(request, response, bodyStream);
	
			bodyStream.close();
			EntityUtils.consume(entity);
		} catch (IOException e) {
			throw new ApiException(e);
		}
	}

}
