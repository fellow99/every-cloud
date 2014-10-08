package com.fellow.every.provider.qq;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.base.AbstractFileInfo;

public class QQFileInfo extends AbstractFileInfo{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public QQFileInfo(JSONObject json){
		try {
			this.setId(json.getString("file_id"));

			this.setPath(json.getString("name"));
			this.setName(json.getString("name"));
			this.setSize(json.getLong("size"));
			this.setCreateTime(json.getLong("ctime"));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
