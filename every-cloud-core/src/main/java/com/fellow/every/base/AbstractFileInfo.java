package com.fellow.every.base;

import com.fellow.every.disk.FileInfo;
import com.fellow.every.disk.FileType;

public class AbstractFileInfo implements FileInfo, java.io.Serializable{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String path;
	private FileType type;
	private long size;
	private long createTime;
	private long lastModifiedTime;
	private boolean deleted;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public FileType getType() {
		return type;
	}
	public void setType(FileType type) {
		this.type = type;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public long getLastModifiedTime() {
		return lastModifiedTime;
	}
	public void setLastModifiedTime(long lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
