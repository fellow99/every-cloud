package com.fellow.every.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.fellow.every.disk.ProgressListener;
import com.fellow.every.exception.ApiException;
import com.fellow.every.exception.ServerException;

public class HTTPStreamUtil {

	public static String toString(InputStream is) throws ServerException, ApiException{
		try {
		    BufferedReader in = new BufferedReader(new InputStreamReader(is));
		    StringBuffer buffer = new StringBuffer();
		    String line = "";
		    while ((line = in.readLine()) != null){
		      buffer.append(line);
		    }
		    return buffer.toString();
		} catch (IOException e) {
			throw new ApiException(e);
		}
	}
	

	
	public static void bufferedWriting(OutputStream os, InputStream is, long total, ProgressListener listener)
			 throws ServerException, ApiException {
		try {
			final int BUFFER_SIZE = 4048;
			long last_triggered_time = 0L;
			int len = 0;
			int count = 0;
			byte[] buf = new byte[BUFFER_SIZE];
	
			if (listener != null) listener.started();
			
	        while((len = is.read(buf)) != -1) {
	        	os.write(buf, 0, len);
	        	count += len;
	        	long current_time = System.currentTimeMillis();
	        	if (listener != null && 
	        			(current_time-last_triggered_time) > listener.getUpdateInterval()) {
	        		listener.processing(count, total);
					last_triggered_time = current_time;
				}
	        }
	        
			if (listener != null) listener.completed();
		} catch (IOException e) {
			throw new ApiException(e);
		}
	}
}
