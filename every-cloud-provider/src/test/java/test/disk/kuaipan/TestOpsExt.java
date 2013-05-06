package test.disk.kuaipan;

import org.junit.BeforeClass;

import test.disk.common.AbstractTestOpsExt;

public class TestOpsExt extends AbstractTestOpsExt {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		setDiskAPI(KuaipanDiskTests.getDiskAPI());
	}
}
