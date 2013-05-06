package test.vfs.common;

import static org.junit.Assert.*;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileType;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.VFS;
import org.junit.Before;
import org.junit.Test;

public class AbstractTestVFS2 extends AbstractTestAPI{

	public final static String SCHEMA = "disk";
	public final static String SCHEMA_ROOT = SCHEMA + "://";

	public static FileSystemManager getFileSystemManager() throws Exception {
		return VFS.getManager();
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void normal() throws Exception{
		FileObject fileFrom = null;
		FileObject fileTo = null;
		FileObject root = getFileSystemManager().resolveFile(SCHEMA_ROOT);
		assertTrue(root.getType() == FileType.FOLDER);
		
		//创建目录
		fileFrom = root.resolveFile("/test_dir");
		fileFrom.createFolder();
		assertTrue(fileFrom.getType() == FileType.FOLDER);
		
		fileFrom = root.resolveFile("/test_dir/sub_dir");
		fileFrom.createFolder();
		assertTrue(fileFrom.getType() == FileType.FOLDER);
		
		//重命名目录
		fileFrom = root.resolveFile("/test_dir/sub_dir");
		fileTo = root.resolveFile("/test_dir/sub_dir2");
		fileFrom.moveTo(fileTo);
		assertTrue(fileFrom.getType() == FileType.IMAGINARY);
		assertTrue(fileTo.getType() == FileType.FOLDER);

		fileFrom = root.resolveFile("/test_dir");
		fileTo = root.resolveFile("/test_dir2");
		fileFrom.moveTo(fileTo);
		assertTrue(fileFrom.getType() == FileType.IMAGINARY);
		assertTrue(fileTo.getType() == FileType.FOLDER);
		

		//复制目录
		fileFrom = root.resolveFile("/test_dir2/sub_dir2");
		fileTo = root.resolveFile("/test_dir2/sub_dir3");
		fileTo.copyFrom(fileFrom, Selectors.SELECT_ALL);
		assertTrue(fileFrom.getType() == FileType.FOLDER);
		assertTrue(fileTo.getType() == FileType.FOLDER);
		
		fileFrom = root.resolveFile("/test_dir2");
		fileTo = root.resolveFile("/test_dir3");
		fileTo.copyFrom(fileFrom, Selectors.SELECT_ALL);
		assertTrue(fileFrom.getType() == FileType.FOLDER);
		assertTrue(fileTo.getType() == FileType.FOLDER);

		//删除目录
		fileFrom = root.resolveFile("/test_dir3/sub_dir3");
		fileFrom.delete(Selectors.SELECT_ALL);
		assertTrue(fileFrom.getType() == FileType.IMAGINARY);

		fileFrom = root.resolveFile("/test_dir3");
		fileFrom.delete(Selectors.SELECT_ALL);
		assertTrue(fileFrom.getType() == FileType.IMAGINARY);

		fileFrom = root.resolveFile("/test_dir2/sub_dir2");
		fileFrom.delete(Selectors.SELECT_ALL);
		assertTrue(fileFrom.getType() == FileType.IMAGINARY);

		fileFrom = root.resolveFile("/test_dir2");
		fileFrom.delete(Selectors.SELECT_ALL);
		assertTrue(fileFrom.getType() == FileType.IMAGINARY);
	}

	@Test
	public void blank() throws Exception{
		FileObject fileFrom = null;
		FileObject fileTo = null;
		FileObject root = getFileSystemManager().resolveFile(SCHEMA_ROOT);
		assertTrue(root.getType() == FileType.FOLDER);
		
		//创建目录
		fileFrom = root.resolveFile("/test dir");
		fileFrom.createFolder();
		assertTrue(fileFrom.getType() == FileType.FOLDER);
		
		fileFrom = root.resolveFile("/test dir/sub dir");
		fileFrom.createFolder();
		assertTrue(fileFrom.getType() == FileType.FOLDER);
		
		//重命名目录
		fileFrom = root.resolveFile("/test dir/sub dir");
		fileTo = root.resolveFile("/test dir/sub dir2");
		fileFrom.moveTo(fileTo);
		assertTrue(fileFrom.getType() == FileType.IMAGINARY);
		assertTrue(fileTo.getType() == FileType.FOLDER);

		fileFrom = root.resolveFile("/test dir");
		fileTo = root.resolveFile("/test dir2");
		fileFrom.moveTo(fileTo);
		assertTrue(fileFrom.getType() == FileType.IMAGINARY);
		assertTrue(fileTo.getType() == FileType.FOLDER);
		

		//复制目录
		fileFrom = root.resolveFile("/test dir2/sub dir2");
		fileTo = root.resolveFile("/test dir2/sub dir3");
		fileTo.copyFrom(fileFrom, Selectors.SELECT_ALL);
		assertTrue(fileFrom.getType() == FileType.FOLDER);
		assertTrue(fileTo.getType() == FileType.FOLDER);
		
		fileFrom = root.resolveFile("/test dir2");
		fileTo = root.resolveFile("/test dir3");
		fileTo.copyFrom(fileFrom, Selectors.SELECT_ALL);
		assertTrue(fileFrom.getType() == FileType.FOLDER);
		assertTrue(fileTo.getType() == FileType.FOLDER);

		//删除目录
		fileFrom = root.resolveFile("/test dir3/sub dir3");
		fileFrom.delete(Selectors.SELECT_ALL);
		assertTrue(fileFrom.getType() == FileType.IMAGINARY);

		fileFrom = root.resolveFile("/test dir3");
		fileFrom.delete(Selectors.SELECT_ALL);
		assertTrue(fileFrom.getType() == FileType.IMAGINARY);

		fileFrom = root.resolveFile("/test dir2/sub dir2");
		fileFrom.delete(Selectors.SELECT_ALL);
		assertTrue(fileFrom.getType() == FileType.IMAGINARY);

		fileFrom = root.resolveFile("/test dir2");
		fileFrom.delete(Selectors.SELECT_ALL);
		assertTrue(fileFrom.getType() == FileType.IMAGINARY);
	}

	@Test
	public void cn() throws Exception{
		FileObject fileFrom = null;
		FileObject fileTo = null;
		FileObject root = getFileSystemManager().resolveFile(SCHEMA_ROOT);
		assertTrue(root.getType() == FileType.FOLDER);
		
		//创建目录
		fileFrom = root.resolveFile("/test_目录");
		fileFrom.createFolder();
		assertTrue(fileFrom.getType() == FileType.FOLDER);
		
		fileFrom = root.resolveFile("/test_目录/sub_目录");
		fileFrom.createFolder();
		assertTrue(fileFrom.getType() == FileType.FOLDER);
		
		//重命名目录
		fileFrom = root.resolveFile("/test_目录/sub_目录");
		fileTo = root.resolveFile("/test_目录/sub_目录2");
		fileFrom.moveTo(fileTo);
		assertTrue(fileFrom.getType() == FileType.IMAGINARY);
		assertTrue(fileTo.getType() == FileType.FOLDER);

		fileFrom = root.resolveFile("/test_目录");
		fileTo = root.resolveFile("/test_目录2");
		fileFrom.moveTo(fileTo);
		assertTrue(fileFrom.getType() == FileType.IMAGINARY);
		assertTrue(fileTo.getType() == FileType.FOLDER);
		

		//复制目录
		fileFrom = root.resolveFile("/test_目录2/sub_目录2");
		fileTo = root.resolveFile("/test_目录2/sub_目录3");
		fileTo.copyFrom(fileFrom, Selectors.SELECT_ALL);
		assertTrue(fileFrom.getType() == FileType.FOLDER);
		assertTrue(fileTo.getType() == FileType.FOLDER);
		
		fileFrom = root.resolveFile("/test_目录2");
		fileTo = root.resolveFile("/test_目录3");
		fileTo.copyFrom(fileFrom, Selectors.SELECT_ALL);
		assertTrue(fileFrom.getType() == FileType.FOLDER);
		assertTrue(fileTo.getType() == FileType.FOLDER);

		//删除目录
		fileFrom = root.resolveFile("/test_目录3/sub_目录3");
		fileFrom.delete(Selectors.SELECT_ALL);
		assertTrue(fileFrom.getType() == FileType.IMAGINARY);

		fileFrom = root.resolveFile("/test_目录3");
		fileFrom.delete(Selectors.SELECT_ALL);
		assertTrue(fileFrom.getType() == FileType.IMAGINARY);

		fileFrom = root.resolveFile("/test_目录2/sub_目录2");
		fileFrom.delete(Selectors.SELECT_ALL);
		assertTrue(fileFrom.getType() == FileType.IMAGINARY);

		fileFrom = root.resolveFile("/test_目录2");
		fileFrom.delete(Selectors.SELECT_ALL);
		assertTrue(fileFrom.getType() == FileType.IMAGINARY);
	}

}
