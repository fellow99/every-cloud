package test.vfs.dropbox;

import org.apache.commons.vfs.FileSystemOptions;
import org.apache.commons.vfs.impl.StandardFileSystemManager;
import org.junit.BeforeClass;

import com.fellow.every.vfs.v1.EveryDiskFileObject;
import com.fellow.every.vfs.v1.EveryDiskFileSystemConfigBuilder;
import com.fellow.every.vfs.v1.EveryDiskProvider;

import test.vfs.common.AbstractTestVFS;

public class TestVFS extends AbstractTestVFS {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
        
        FileSystemOptions opts = new FileSystemOptions();
        EveryDiskFileSystemConfigBuilder.getInstance().setDiskAPI(opts, DropboxVFSTests.getDiskAPI());
        EveryDiskFileSystemConfigBuilder.getInstance().setAuthenticator(opts, DropboxVFSTests.getAuthenticator());
        
        ((StandardFileSystemManager)getFileSystemManager()).addProvider(SCHEMA, new EveryDiskProvider());
        
        EveryDiskFileObject root = (EveryDiskFileObject)getFileSystemManager().resolveFile(SCHEMA_ROOT, opts);
        
		setDiskAPI(root.getDiskAPI());
		
		setAccessToken(DropboxVFSTests.getAuthenticator().requestAuthentication());
	}
}
