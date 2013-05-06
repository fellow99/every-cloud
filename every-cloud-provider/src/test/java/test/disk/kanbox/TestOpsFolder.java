package test.disk.kanbox;

import org.junit.BeforeClass;

import test.disk.common.AbstractTestOpsFolder;


public class TestOpsFolder extends AbstractTestOpsFolder {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		setDiskAPI(KanboxDiskTests.getDiskAPI());
	}
}
