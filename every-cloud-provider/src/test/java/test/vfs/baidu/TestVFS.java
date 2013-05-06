package test.vfs.baidu;

import org.apache.commons.vfs.FileSystemOptions;
import org.apache.commons.vfs.impl.StandardFileSystemManager;
import org.junit.BeforeClass;

import com.fellow.every.vfs.v1.EveryDiskFileObject;
import com.fellow.every.vfs.v1.EveryDiskFileSystemConfigBuilder;
import com.fellow.every.vfs.v1.EveryDiskProvider;

import test.disk.baidu.BaiduDiskTests;
import test.vfs.common.AbstractTestVFS;

public class TestVFS extends AbstractTestVFS {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
        
        FileSystemOptions opts = new FileSystemOptions();
        EveryDiskFileSystemConfigBuilder.getInstance().setDiskAPI(opts, BaiduVFSTests.getDiskAPI());
        EveryDiskFileSystemConfigBuilder.getInstance().setAuthenticator(opts, BaiduVFSTests.getAuthenticator());
        
        ((StandardFileSystemManager)getFileSystemManager()).addProvider(SCHEMA, new EveryDiskProvider());
        
        EveryDiskFileObject root = (EveryDiskFileObject)getFileSystemManager().resolveFile(SCHEMA_ROOT, opts);
        
		setDiskAPI(root.getDiskAPI());
		
		setAccessToken(BaiduDiskTests.getAuthenticator().requestAuthentication());
	}
}
