package test.disk.kanbox;

import org.junit.BeforeClass;

import test.disk.common.AbstractTestOpsStream;


public class TestOpsStream extends AbstractTestOpsStream {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		setDiskAPI(KanboxDiskTests.getDiskAPI());
	}
}
