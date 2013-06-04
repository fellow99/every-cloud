package com.fellow.every.status;

import com.fellow.every.user.UserInfo;

public interface StatusInfo {
	String getId();
	String getContent();
	String getSource();
	long getDatetime();
	
	UserInfo getUser();
}
