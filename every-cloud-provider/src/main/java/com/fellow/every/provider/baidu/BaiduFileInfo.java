package com.fellow.every.provider.baidu;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.base.AbstractFileInfo;
import com.fellow.every.disk.FileType;

public class BaiduFileInfo extends AbstractFileInfo{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public BaiduFileInfo(JSONObject json, String root){
		try {
			this.setId(json.getString("fs_id"));

			String path = json.getString("path");
			if(path == null){
				this.setPath(null);
			} else if(path.indexOf(root) == 0){
				this.setPath(path.substring(root.length()));
			} else {
				this.setPath(path);
			}
			int i = (this.getPath() == null ? -1 :this.getPath().lastIndexOf("/"));
			if(i < 0){
				this.setName("");
			} else {
				this.setName(this.getPath().substring(i+1));
			}
			
			this.setName(json.has("name") ? json.getString("name") : null);
			this.setSize(json.has("size") ? json.getLong("size") : -1);
			this.setCreateTime(json.has("ctime") ? json.getLong("ctime") : null);
			this.setLastModifiedTime(json.has("mtime") ? json.getLong("mtime"): null);
			this.setDeleted(false);
			
			String type = (json.has("isdir") ? json.getString("isdir") : null);
			if("0".equalsIgnoreCase(type)){
				this.setType(FileType.FILE);
			} else if("1".equalsIgnoreCase(type)){
				this.setType(FileType.FOLDER);
			} else {
				this.setType(FileType.NULL);
			}
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
