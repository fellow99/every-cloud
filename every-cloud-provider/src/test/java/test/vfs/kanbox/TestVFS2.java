package test.vfs.kanbox;

import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.junit.BeforeClass;

import com.fellow.every.vfs.v2.EveryDiskFileObject;
import com.fellow.every.vfs.v2.EveryDiskFileSystemConfigBuilder;
import com.fellow.every.vfs.v2.EveryDiskProvider;

import test.vfs.common.AbstractTestVFS2;

public class TestVFS2 extends AbstractTestVFS2 {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
        
        FileSystemOptions opts = new FileSystemOptions();
        EveryDiskFileSystemConfigBuilder.getInstance().setDiskAPI(opts, KanboxVFSTests.getDiskAPI());
        EveryDiskFileSystemConfigBuilder.getInstance().setAuthenticator(opts, KanboxVFSTests.getAuthenticator());
        
        ((StandardFileSystemManager)getFileSystemManager()).addProvider(SCHEMA, new EveryDiskProvider());
        
        EveryDiskFileObject root = (EveryDiskFileObject)getFileSystemManager().resolveFile(SCHEMA_ROOT, opts);
        
		setDiskAPI(root.getDiskAPI());
		
		setAccessToken(KanboxVFSTests.getAuthenticator().requestAuthentication());
	}
}
