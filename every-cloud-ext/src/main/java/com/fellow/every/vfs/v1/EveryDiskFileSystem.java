package com.fellow.every.vfs.v1;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.vfs.FileName;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemOptions;
import org.apache.commons.vfs.provider.AbstractFileSystem;

import com.fellow.every.auth.AccessToken;
import com.fellow.every.disk.DiskAPI;

public class EveryDiskFileSystem extends AbstractFileSystem {
	private static final Log LOG = LogFactory.getLog(EveryDiskFileSystem.class);
	
	private final DiskAPI diskAPI;
    private final AtomicReference<AccessToken> idleClient = new AtomicReference<AccessToken>();

    public EveryDiskFileSystem(final FileName rootName, final FileSystemOptions fileSystemOptions,
    		final DiskAPI diskAPI, final AccessToken accessToken){
        super(rootName, null, fileSystemOptions);

        this.diskAPI = diskAPI;
        this.idleClient.set(accessToken);
    }

    @Override
    protected void doCloseCommunicationLink(){
    	AccessToken accessToken = idleClient.getAndSet(null);
        // Clean up the connection
        if (accessToken != null){
            closeConnection(accessToken);
        }
    }

    /**
     * Adds the capabilities of this file system.
     */
    @Override
    protected void addCapabilities(final Collection caps){
        caps.addAll(EveryDiskProvider.capabilities);
    }

    /**
     * Cleans up the connection to the server.
     * @param client The FtpClient.
     */
    private void closeConnection(final AccessToken accessToken){
    }

    /**
     * Creates a file object.
     */
    @Override
    protected FileObject createFile(final FileName name)
        throws Exception{
        return new EveryDiskFileObject(name, this, getRootName());
    }
    
    public DiskAPI getDiskAPI(){
    	return diskAPI;
    }
    
    public AccessToken getAccessToken(){
    	return idleClient.get();
    }
}
