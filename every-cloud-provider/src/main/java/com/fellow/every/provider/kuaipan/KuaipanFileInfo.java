package com.fellow.every.provider.kuaipan;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.base.AbstractFileInfo;
import com.fellow.every.disk.FileType;

public class KuaipanFileInfo extends AbstractFileInfo{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public KuaipanFileInfo(JSONObject json, String parent){
		try {
			this.setId(json.getString("file_id"));
			this.setName(json.getString("name"));
			this.setSize(json.getLong("size"));
			this.setDeleted("true".equalsIgnoreCase(json.getString("is_deleted")));
			if(json.has("path")){
				this.setPath(json.getString("path"));
			} else if(parent != null){
				this.setPath(parent + "/" + this.getName());
			}
			String type = json.getString("type");
			if("file".equalsIgnoreCase(type)){
				this.setType(FileType.FILE);
			} else if("folder".equalsIgnoreCase(type)){
				this.setType(FileType.FOLDER);
			} else {
				this.setType(FileType.NULL);
			}
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
