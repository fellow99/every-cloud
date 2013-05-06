package com.fellow.every.disk;

public interface FileInfo {

	public String getId();
	public String getName();
	public String getPath();
	public FileType getType();
	public long getSize();
	public long getCreateTime();
	public long getLastModifiedTime();
	public boolean isDeleted();
}
