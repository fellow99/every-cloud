package com.fellow.every.provider.kuaipan;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.disk.FileInfo;
import com.fellow.every.disk.FileType;

public class KuaipanFileInfo implements FileInfo{
	private String parent;
	private JSONObject json;
	public KuaipanFileInfo(JSONObject json, String parent){
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
			return json.getString("file_id");
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public String getName() {
		try {
			return json.getString("name");
		} catch (JSONException e) {
			throw new RuntimeException(e);
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
		try {
			return "true".equalsIgnoreCase(json.getString("is_deleted"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
