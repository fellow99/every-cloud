package com.fellow.every.base;

import com.fellow.every.status.StatusInfo;
import com.fellow.every.user.UserInfo;

public class AbstractStatusInfo implements StatusInfo, java.io.Serializable{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String content;
	private String source;
	private long datetime;
	
	private UserInfo user;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

	public long getDatetime() {
		return datetime;
	}

	public void setDatetime(long datetime) {
		this.datetime = datetime;
	}
}
