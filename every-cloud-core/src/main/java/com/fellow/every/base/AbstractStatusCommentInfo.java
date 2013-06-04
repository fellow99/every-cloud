package com.fellow.every.base;

import com.fellow.every.status.StatusCommentInfo;
import com.fellow.every.status.StatusInfo;

public class AbstractStatusCommentInfo extends AbstractStatusInfo implements StatusCommentInfo, java.io.Serializable{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	private StatusInfo status;

	public StatusInfo getStatus() {
		return status;
	}

	public void setStatus(StatusInfo status) {
		this.status = status;
	}
}
