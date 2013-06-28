package com.fellow.every.provider.renren;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.base.AbstractStatusCommentInfo;

public class RenrenStatusCommentInfo extends AbstractStatusCommentInfo{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public RenrenStatusCommentInfo(JSONObject json){
		try{
			this.setId(json.getString("comment_id"));
			this.setContent(json.has("text") ?json.getString("text") : null);

		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
