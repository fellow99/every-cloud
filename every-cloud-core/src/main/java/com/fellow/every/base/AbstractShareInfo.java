package com.fellow.every.base;

import com.fellow.every.disk.ShareInfo;

public class AbstractShareInfo implements ShareInfo, java.io.Serializable{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	private String url;
	private String accessCode;
	private long expireTime;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAccessCode() {
		return accessCode;
	}
	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}
	public long getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}
}
