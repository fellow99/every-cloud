package com.fellow.every.provider.box;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.disk.FileInfo;
import com.fellow.every.disk.FileType;

public class BoxFileInfo implements FileInfo{
	private String parent;
	private JSONObject json;
	public BoxFileInfo(JSONObject json, String parent){
		this.json = json;
		this.parent = parent;
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
			if(json.has("path")){
				return json.getString("path");
			} else if(parent != null){
				return parent + "/" + this.getName();
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public FileType getType() {
		try {
			String type = json.getString("type");
			if("file".equalsIgnoreCase(type)){
				return FileType.FILE;
			} else if("folder".equalsIgnoreCase(type)){
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
