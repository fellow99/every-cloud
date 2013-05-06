package test.disk.dropbox;

import org.junit.BeforeClass;

import test.disk.common.AbstractTestOpsFile;


public class TestOpsFile extends AbstractTestOpsFile {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		setDiskAPI(DropboxDiskTests.getDiskAPI());
	}
}
