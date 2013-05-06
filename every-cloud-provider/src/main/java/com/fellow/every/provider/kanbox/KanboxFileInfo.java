package com.fellow.every.provider.kanbox;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.disk.FileInfo;
import com.fellow.every.disk.FileType;

public class KanboxFileInfo implements FileInfo{
	private JSONObject json;
	public KanboxFileInfo(JSONObject json){
		this.json = json;
	}

	@Override
	public String toString(){
		return this.getClass().getName() + "-" + json;
	}
	
	@Override
	public String getId() {
		try {
			return json.getString("fullPath");
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
			return json.getString("fullPath");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public FileType getType() {
		try {
			String type = json.getString("isFolder");
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
			return (json.has("fileSize") ? json.getLong("fileSize") : 0);
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
		return false;
	}
}
