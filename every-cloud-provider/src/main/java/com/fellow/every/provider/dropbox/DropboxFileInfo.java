package com.fellow.every.provider.dropbox;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.disk.FileInfo;
import com.fellow.every.disk.FileType;

public class DropboxFileInfo implements FileInfo{
	private JSONObject json;
	public DropboxFileInfo(JSONObject json){
		this.json = json;
	}

	@Override
	public String toString(){
		return this.getClass().getName() + "-" + json;
	}
	
	@Override
	public String getId() {
		try {
			return json.getString("rev");
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public String getName() {
		String path = getPath();
		int i = (path == null ? -1 :path.lastIndexOf("/"));
		if(i < 0){
			return "";
		} else {
			return path.substring(i+1);
		}
	}
	@Override
	public String getPath() {
		try {
			return json.getString("path");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public FileType getType() {
		try {
			String type = json.getString("is_dir");
			if("false".equalsIgnoreCase(type)){
				return FileType.FILE;
			} else if("true".equalsIgnoreCase(type)){
				return FileType.FOLDER;
			} else {
				return FileType.NULL;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public long getSize() {
		try {
			return json.getLong("bytes");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public long getCreateTime() {
		try {
			return 0;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public long getLastModifiedTime() {
		try {
			return 0;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public boolean isDeleted() {
		try {
			return "true".equalsIgnoreCase(json.getString("is_deleted"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
