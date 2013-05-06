package com.fellow.every.provider.qq;

import org.json.JSONObject;

import com.fellow.every.mircoblog.MicroBlogCommentInfo;
import com.fellow.every.mircoblog.MicroBlogInfo;

public class QQMicroBlogCommentInfo extends QQMicroBlogInfo implements MicroBlogCommentInfo{
	private JSONObject json;
	
	public QQMicroBlogCommentInfo(JSONObject json){
		super(json);
		this.json = this.getJson();
	}

	@Override
	public String toString(){
		return this.getClass().getName() + "-" + json;
	}

	@Override
	public MicroBlogInfo getMicroBlog() {
		return null;
	}


}
