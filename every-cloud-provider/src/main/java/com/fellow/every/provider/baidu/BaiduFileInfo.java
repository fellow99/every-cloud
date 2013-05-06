package com.fellow.every.provider.baidu;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.disk.FileInfo;
import com.fellow.every.disk.FileType;

public class BaiduFileInfo implements FileInfo{
	private String root;
	private JSONObject json;
	
	public BaiduFileInfo(JSONObject json, String root){
		this.root = root;
		this.json = json;
	}

	@Override
	public String toString(){
		return this.getClass().getName() + "-" + json;
	}
	
	@Override
	public String getId() {
		try {
			return json.getString("fs_id");
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
			String path = json.getString("path");
			if(path == null){
				return null;
			} else if(path.indexOf(root) == 0){
				return path.substring(root.length());
			} else {
				return path;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public FileType getType() {
		try {
			String isdir = json.getString("isdir");
			if("0".equalsIgnoreCase(isdir)){
				return FileType.FILE;
			} else if("1".equalsIgnoreCase(isdir)){
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
			return json.getLong("size");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public long getCreateTime() {
		try {
			return json.getLong("ctime");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public long getLastModifiedTime() {
		try {
			return json.getLong("mtime");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public boolean isDeleted() {
		return false;
	}
}
