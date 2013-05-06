package test.disk.common;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.fellow.every.disk.FileInfo;

public abstract class AbstractTestOpsFolder extends AbstractTestAPI{

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void normal() throws Exception{
		FileInfo file = null;
		//创建目录
		assertTrue(getDiskAPI().mkdir(getAccessToken(), "/test_dir"));
		assertTrue(getDiskAPI().mkdir(getAccessToken(), "/test_dir/sub_dir"));

		file = getDiskAPI().metadata(getAccessToken(), "/test_dir");
		assertNotNull(file);
		assertEquals(file.getPath(), "/test_dir");
		
		file = getDiskAPI().metadata(getAccessToken(), "/test_dir/sub_dir");
		assertNotNull(file);
		assertEquals(file.getPath(), "/test_dir/sub_dir");
		
		//重命名目录
		assertTrue(getDiskAPI().mv(getAccessToken(), "/test_dir/sub_dir", "/test_dir/sub_dir2"));
		assertTrue(getDiskAPI().mv(getAccessToken(), "/test_dir", "/test_dir2"));

		file = getDiskAPI().metadata(getAccessToken(), "/test_dir2");
		assertNotNull(file);
		assertEquals(file.getPath(), "/test_dir2");
		
		file = getDiskAPI().metadata(getAccessToken(), "/test_dir2/sub_dir2");
		assertNotNull(file);
		assertEquals(file.getPath(), "/test_dir2/sub_dir2");

		//复制目录
		assertTrue(getDiskAPI().cp(getAccessToken(), "/test_dir2/sub_dir2", "/test_dir2/sub_dir3"));
		assertTrue(getDiskAPI().cp(getAccessToken(), "/test_dir2", "/test_dir3"));

		file = getDiskAPI().metadata(getAccessToken(), "/test_dir3");
		assertNotNull(file);
		assertEquals(file.getPath(), "/test_dir3");
		
		file = getDiskAPI().metadata(getAccessToken(), "/test_dir3/sub_dir3");
		assertNotNull(file);
		assertEquals(file.getPath(), "/test_dir3/sub_dir3");

		//删除目录
		assertTrue(getDiskAPI().rm(getAccessToken(), "/test_dir2/sub_dir2", false));
		assertTrue(getDiskAPI().rm(getAccessToken(), "/test_dir2", false));
		assertTrue(getDiskAPI().rm(getAccessToken(), "/test_dir3/sub_dir3", false));
		assertTrue(getDiskAPI().rm(getAccessToken(), "/test_dir3", false));
		
		file = getDiskAPI().metadata(getAccessToken(), "/test_dir2");
		assertTrue(file == null || file.isDeleted());
		
		file = getDiskAPI().metadata(getAccessToken(), "/test_dir2/sub_dir2");
		assertTrue(file == null || file.isDeleted());
	}

	@Test
	public void blank() throws Exception{
		FileInfo file = null;
		//创建目录
		assertTrue(getDiskAPI().mkdir(getAccessToken(), "/test dir"));
		assertTrue(getDiskAPI().mkdir(getAccessToken(), "/test dir/sub dir"));

		file = getDiskAPI().metadata(getAccessToken(), "/test dir");
		assertNotNull(file);
		assertEquals(file.getPath(), "/test dir");
		
		file = getDiskAPI().metadata(getAccessToken(), "/test dir/sub dir");
		assertNotNull(file);
		assertEquals(file.getPath(), "/test dir/sub dir");
		
		//重命名目录
		assertTrue(getDiskAPI().mv(getAccessToken(), "/test dir/sub dir", "/test dir/sub dir2"));
		assertTrue(getDiskAPI().mv(getAccessToken(), "/test dir", "/test dir2"));

		file = getDiskAPI().metadata(getAccessToken(), "/test dir2");
		assertNotNull(file);
		assertEquals(file.getPath(), "/test dir2");
		
		file = getDiskAPI().metadata(getAccessToken(), "/test dir2/sub dir2");
		assertNotNull(file);
		assertEquals(file.getPath(), "/test dir2/sub dir2");

		//复制目录
		assertTrue(getDiskAPI().cp(getAccessToken(), "/test dir2/sub dir2", "/test dir2/sub dir3"));
		assertTrue(getDiskAPI().cp(getAccessToken(), "/test dir2", "/test dir3"));

		file = getDiskAPI().metadata(getAccessToken(), "/test dir3");
		assertNotNull(file);
		assertEquals(file.getPath(), "/test dir3");
		
		file = getDiskAPI().metadata(getAccessToken(), "/test dir3/sub dir3");
		assertNotNull(file);
		assertEquals(file.getPath(), "/test dir3/sub dir3");

		//删除目录
		assertTrue(getDiskAPI().rm(getAccessToken(), "/test dir2/sub dir2", false));
		assertTrue(getDiskAPI().rm(getAccessToken(), "/test dir2", false));
		assertTrue(getDiskAPI().rm(getAccessToken(), "/test dir3/sub dir3", false));
		assertTrue(getDiskAPI().rm(getAccessToken(), "/test dir3", false));
		
		file = getDiskAPI().metadata(getAccessToken(), "/test dir2");
		assertTrue(file == null || file.isDeleted());
		
		file = getDiskAPI().metadata(getAccessToken(), "/test dir2/sub dir2");
		assertTrue(file == null || file.isDeleted());
	}

	@Test
	public void cn() throws Exception{
		FileInfo file = null;
		//创建目录
		assertTrue(getDiskAPI().mkdir(getAccessToken(), "/test_目录"));
		assertTrue(getDiskAPI().mkdir(getAccessToken(), "/test_目录/sub_目录"));

		file = getDiskAPI().metadata(getAccessToken(), "/test_目录");
		assertNotNull(file);
		assertEquals(file.getPath(), "/test_目录");
		
		file = getDiskAPI().metadata(getAccessToken(), "/test_目录/sub_目录");
		assertNotNull(file);
		assertEquals(file.getPath(), "/test_目录/sub_目录");
		
		//重命名目录
		assertTrue(getDiskAPI().mv(getAccessToken(), "/test_目录/sub_目录", "/test_目录/sub_目录2"));
		assertTrue(getDiskAPI().mv(getAccessToken(), "/test_目录", "/test_目录2"));

		file = getDiskAPI().metadata(getAccessToken(), "/test_目录2");
		assertNotNull(file);
		assertEquals(file.getPath(), "/test_目录2");
		
		file = getDiskAPI().metadata(getAccessToken(), "/test_目录2/sub_目录2");
		assertNotNull(file);
		assertEquals(file.getPath(), "/test_目录2/sub_目录2");

		//复制目录
		assertTrue(getDiskAPI().cp(getAccessToken(), "/test_目录2/sub_目录2", "/test_目录2/sub_目录3"));
		assertTrue(getDiskAPI().cp(getAccessToken(), "/test_目录2", "/test_目录3"));

		file = getDiskAPI().metadata(getAccessToken(), "/test_目录3");
		assertNotNull(file);
		assertEquals(file.getPath(), "/test_目录3");
		
		file = getDiskAPI().metadata(getAccessToken(), "/test_目录3/sub_目录3");
		assertNotNull(file);
		assertEquals(file.getPath(), "/test_目录3/sub_目录3");

		//删除目录
		assertTrue(getDiskAPI().rm(getAccessToken(), "/test_目录2/sub_目录2", false));
		assertTrue(getDiskAPI().rm(getAccessToken(), "/test_目录2", false));
		assertTrue(getDiskAPI().rm(getAccessToken(), "/test_目录3/sub_目录3", false));
		assertTrue(getDiskAPI().rm(getAccessToken(), "/test_目录3", false));
		
		file = getDiskAPI().metadata(getAccessToken(), "/test_目录2");
		assertTrue(file == null || file.isDeleted());
		
		file = getDiskAPI().metadata(getAccessToken(), "/test_目录2/sub_目录2");
		assertTrue(file == null || file.isDeleted());
	}

}
