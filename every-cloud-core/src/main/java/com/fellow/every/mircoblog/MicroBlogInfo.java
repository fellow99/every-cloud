package com.fellow.every.mircoblog;

import com.fellow.every.user.UserInfo;

public interface MicroBlogInfo {
	String getId();
	String getContent();
	String getSource();
	
	UserInfo getUser();
}
