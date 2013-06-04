package com.fellow.every.base;

import com.fellow.every.disk.QuotaInfo;

public class AbstractQuotaInfo implements QuotaInfo, java.io.Serializable{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	private long quota;
	private long used;
	public long getQuota() {
		return quota;
	}
	public void setQuota(long quota) {
		this.quota = quota;
	}
	public long getUsed() {
		return used;
	}
	public void setUsed(long used) {
		this.used = used;
	}
}
