package test.disk.kanbox;

import org.junit.BeforeClass;

import com.fellow.every.http.impl.HTTPClient3;
import com.fellow.every.http.impl.HTTPClient4;
import com.fellow.every.http.impl.HTTPURLConnection;

import test.disk.common.AbstractTestHTTPEngine;

public class TestHTTPEngine extends AbstractTestHTTPEngine{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		apiMap.put(HTTPClient4.class, KanboxDiskTests.getDiskAPI(new HTTPClient4()));
		apiMap.put(HTTPClient3.class, KanboxDiskTests.getDiskAPI(new HTTPClient3()));
		apiMap.put(HTTPURLConnection.class, KanboxDiskTests.getDiskAPI(new HTTPURLConnection()));
	}
}
