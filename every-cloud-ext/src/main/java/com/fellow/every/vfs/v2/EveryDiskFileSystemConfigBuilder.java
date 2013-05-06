package com.fellow.every.vfs.v2;

import org.apache.commons.vfs2.FileSystem;
import org.apache.commons.vfs2.FileSystemConfigBuilder;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;

import com.fellow.every.auth.OAuthTokenAuthenticator;
import com.fellow.every.disk.DiskAPI;

public class EveryDiskFileSystemConfigBuilder extends FileSystemConfigBuilder{
	private static final EveryDiskFileSystemConfigBuilder BUILDER = new EveryDiskFileSystemConfigBuilder();

	private EveryDiskFileSystemConfigBuilder() {
		super("every.");
	}

	public static EveryDiskFileSystemConfigBuilder getInstance() {
		return BUILDER;
	}
	public void setDiskAPI(FileSystemOptions opts, DiskAPI diskAPI) throws FileSystemException{
        setParam(opts, "diskAPI", diskAPI);
    }
	
	protected DiskAPI getDiskAPI(FileSystemOptions opts){
		return (DiskAPI)getParam(opts, "diskAPI");
	}

	public void setAuthenticator(FileSystemOptions opts, OAuthTokenAuthenticator authenticator) throws FileSystemException{
        setParam(opts, "authenticator", authenticator);
    }
	
	protected OAuthTokenAuthenticator getAuthenticator(FileSystemOptions opts){
		return (OAuthTokenAuthenticator)getParam(opts, "authenticator");
	}
	
	@Override
	protected Class<? extends FileSystem> getConfigClass() {
		return EveryDiskFileSystem.class;
	}

}
