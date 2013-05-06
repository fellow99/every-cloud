package test.vfs.shell;

import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;

import com.fellow.every.auth.OAuthTokenAuthenticator;
import com.fellow.every.vfs.v2.EveryDiskFileSystemConfigBuilder;
import com.fellow.every.vfs.v2.EveryDiskProvider;

import test.auth.shell.KanboxShellAuthenticator;
import test.vfs.common.AbstractShellVFS2;
import test.vfs.kanbox.KanboxVFSTests;

public class KanboxShellVFS2 extends AbstractShellVFS2 {

	private static final String SCHEMA = "disk";

	public final static OAuthTokenAuthenticator AUTHENTICATOR = new KanboxShellAuthenticator(System.in);

	public KanboxShellVFS2() throws FileSystemException{
		super(System.in, VFS.getManager());
	}
	
	public static void main(final String[] args) {
		try {
			KanboxShellVFS2 shell = new KanboxShellVFS2();

			
			FileSystemManager mgr = shell.getFileSystemManager();
			
	        FileSystemOptions opts = new FileSystemOptions();
	        EveryDiskFileSystemConfigBuilder.getInstance().setDiskAPI(opts, KanboxVFSTests.getDiskAPI());
	        EveryDiskFileSystemConfigBuilder.getInstance().setAuthenticator(opts, AUTHENTICATOR);
	        
	        ((StandardFileSystemManager)mgr).addProvider(SCHEMA, new EveryDiskProvider());
	        
	        mgr.resolveFile(SCHEMA + "://", opts);
	        
			shell.go();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.exit(0);
	}

}
