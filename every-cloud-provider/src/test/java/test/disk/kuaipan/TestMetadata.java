package test.disk.kuaipan;

import org.junit.BeforeClass;

import test.disk.common.AbstractTestMetadata;

public class TestMetadata extends AbstractTestMetadata{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		setDiskAPI(KuaipanDiskTests.getDiskAPI());
	}
}
