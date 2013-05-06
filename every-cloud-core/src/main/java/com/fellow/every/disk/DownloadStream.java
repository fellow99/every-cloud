package com.fellow.every.disk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import com.fellow.every.auth.AccessToken;
import com.fellow.every.disk.ProgressListener;
import com.fellow.every.exception.ApiException;
import com.fellow.every.exception.ServerException;

public class DownloadStream extends InputStream{
	
	private DiskAPI api;
	private FileInputStream tmpFileInputStream;
	private ProgressListener listener;

	public DownloadStream(DiskAPI api, AccessToken accessToken, String path, ProgressListener listener) throws IOException, ServerException, ApiException{
		this.api = api;
		this.listener = listener;

		File tmpFile = File.createTempFile(UUID.randomUUID().toString(), "");
		FileOutputStream os = new FileOutputStream(tmpFile);
		this.api.download(accessToken, path, os, this.listener);
		os.close();
		
		tmpFileInputStream = new FileInputStream(tmpFile);
	}
	
	@Override
	public int read() throws IOException {
		return tmpFileInputStream.read();
	}

	@Override
	public int read(byte b[], int off, int len) throws IOException{
		return tmpFileInputStream.read(b, off, len);
	}
	
	@Override
    public void close() throws IOException {
		tmpFileInputStream.close();
    }
}
