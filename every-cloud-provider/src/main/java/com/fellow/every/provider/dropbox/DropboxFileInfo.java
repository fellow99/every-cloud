package com.fellow.every.provider.dropbox;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.base.AbstractFileInfo;
import com.fellow.every.disk.FileType;

public class DropboxFileInfo extends AbstractFileInfo{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public DropboxFileInfo(JSONObject json){
		try {
			this.setId(json.getString("rev"));
			this.setPath(json.getString("path"));
			int i = (getPath() == null ? -1 :getPath().lastIndexOf("/"));
			if(i < 0){
				this.setName("");
			} else {
				this.setName(getPath().substring(i+1));
			}
			this.setSize(json.getLong("bytes"));
			this.setDeleted("true".equalsIgnoreCase(json.getString("is_deleted")));
			String type = json.getString("is_dir");
			if("false".equalsIgnoreCase(type)){
				this.setType(FileType.FILE);
			} else if("true".equalsIgnoreCase(type)){
				this.setType(FileType.FOLDER);
			} else {
				this.setType(FileType.NULL);
			}
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
