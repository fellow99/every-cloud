package com.fellow.every.disk;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.fellow.every.auth.AccessToken;
import com.fellow.every.disk.ProgressListener;

public class UploadStream extends OutputStream{

    protected byte buf[];
    private int size;
    protected int count;
    
    protected boolean append;

    private String path;
	private AccessToken accessToken;
	private DiskAPI api;
	private ProgressListener listener;
	
	public UploadStream(DiskAPI api, AccessToken accessToken, String path, ProgressListener listener){
		this.accessToken = accessToken;
		this.api = api;
		this.path = path;
		this.listener = listener;
		
		this.size = 1024 * 1024 * 10;
		this.buf = new byte[size];
		this.count = 0;
	}

	@Override
	public void write(int b) throws IOException {
		if(count == size){
			flush();
		}
		
		buf[count] = (byte)b;
		count++;
	}

	@Override
    public void flush() throws IOException {
    	if(count == 0)return;
		
		InputStream is = new ByteArrayInputStream(buf, 0, count);
		
    	try {
			api.upload(accessToken, path, is, count, listener);
		} catch (Exception e) {
			throw new IOException(e);
		}
    	
    	count = 0;
    	append = true;
    }

	@Override
    public void close() throws IOException {
    	flush();
    }
}
