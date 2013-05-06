package com.fellow.every.vfs.v2;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.vfs2.FileName;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileType;
import org.apache.commons.vfs2.provider.AbstractFileName;
import org.apache.commons.vfs2.provider.AbstractFileObject;

import com.fellow.every.disk.DiskAPI;
import com.fellow.every.disk.FileInfo;
import com.fellow.every.disk.SimpleProgressListener;


public class EveryDiskFileObject extends AbstractFileObject{

    private final Log log = LogFactory.getLog(EveryDiskFileObject.class);
    
    private final EveryDiskFileSystem fileSystem;
	

    protected EveryDiskFileObject(final AbstractFileName name,
                            final EveryDiskFileSystem fileSystem,
                            final FileName rootName)
        throws FileSystemException{
        super(name, fileSystem);
        this.fileSystem = fileSystem;
    }

    public DiskAPI getDiskAPI(){
    	return fileSystem.getDiskAPI();
    }
    
	@Override
	protected FileType doGetType() throws Exception {
		String path = this.getName().getPath();
		FileInfo file = fileSystem.getDiskAPI().metadata(fileSystem.getAccessToken(), path);
		
		if(file == null){
			return FileType.IMAGINARY;
		} else if(file.getType() == com.fellow.every.disk.FileType.FILE){
			return FileType.FILE;
		} else if(file.getType() == com.fellow.every.disk.FileType.FOLDER){
			return FileType.FOLDER;
		} else {
			return FileType.IMAGINARY;
		}
	}

	@Override
	protected String[] doListChildren() throws Exception {
		String path = this.getName().getPath();
		FileInfo file = fileSystem.getDiskAPI().metadata(fileSystem.getAccessToken(), path);
		
		if(file != null && file.getType() == com.fellow.every.disk.FileType.FOLDER){
			FileInfo[] files = fileSystem.getDiskAPI().list(fileSystem.getAccessToken(), path);
			
			String[] children = null;
			if(files != null){
				children = new String[files.length];
				for(int i = 0; i < files.length; i++){
					children[i] = files[i].getName();
				}
			}
			return children;
		} else {
	        throw new FileSystemException("vfs.provider/list-children-not-folder.error");
		}
	}

	@Override
	protected long doGetContentSize() throws Exception {
		String path = this.getName().getPath();
		FileInfo file = fileSystem.getDiskAPI().metadata(fileSystem.getAccessToken(), path);

		if(file != null){
			return file.getSize();
		} else {
	        throw new FileSystemException("vfs.provider/get-size.error");
		}
	}


	@Override
	protected InputStream doGetInputStream() throws Exception {
		String path = this.getName().getPath();
		return fileSystem.getDiskAPI().downloadStream(fileSystem.getAccessToken(), path, new SimpleProgressListener());
	}
	

	@Override
    protected OutputStream doGetOutputStream(boolean append) throws Exception{
		String path = this.getName().getPath();
		return fileSystem.getDiskAPI().uploadStream(fileSystem.getAccessToken(), path, new SimpleProgressListener());
    }

	@Override
	protected void doDelete() throws Exception {
		String path = this.getName().getPath();
		FileInfo file = fileSystem.getDiskAPI().metadata(fileSystem.getAccessToken(), path);

		if(file != null){
			fileSystem.getDiskAPI().rm(fileSystem.getAccessToken(), path, true);
		} else {
	        throw new FileSystemException("vfs.provider/delete.error");
		}
    }

	@Override
    protected void doRename(FileObject newfile) throws Exception {
		String path = this.getName().getPath();
		FileInfo file = fileSystem.getDiskAPI().metadata(fileSystem.getAccessToken(), path);

		if(file != null){
			fileSystem.getDiskAPI().mv(fileSystem.getAccessToken(), path, newfile.getName().getPath());
		} else {
	        throw new FileSystemException("vfs.provider/rename.error");
		}
    }

	@Override
    protected void doCreateFolder() throws Exception {
		String path = this.getName().getPath();
		fileSystem.getDiskAPI().mkdir(fileSystem.getAccessToken(), path);
    }

	@Override
    protected long doGetLastModifiedTime() throws Exception {
		String path = this.getName().getPath();
		FileInfo file = fileSystem.getDiskAPI().metadata(fileSystem.getAccessToken(), path);

		if(file != null){
			return file.getLastModifiedTime();
		} else {
	        throw new FileSystemException("vfs.provider/get-last-modified.error");
		}
    }
}
