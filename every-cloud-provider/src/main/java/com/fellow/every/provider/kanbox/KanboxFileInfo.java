package com.fellow.every.provider.kanbox;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.base.AbstractFileInfo;
import com.fellow.every.disk.FileType;

public class KanboxFileInfo extends AbstractFileInfo{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public KanboxFileInfo(JSONObject json){
		try {
			this.setId(json.getString("fullPath"));
			this.setPath(json.getString("fullPath"));
			int i = (getPath() == null ? -1 :getPath().lastIndexOf("/"));
			if(i < 0){
				this.setName("");
			} else {
				this.setName(getPath().substring(i+1));
			}
			this.setSize(json.getLong("fileSize"));
			this.setDeleted(false);
			String type = json.getString("isFolder");
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
