package test.disk.common;

import static org.junit.Assert.*;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import org.junit.Before;
import org.junit.Test;

import com.fellow.every.disk.FileInfo;

public class AbstractTestOpsFile extends AbstractTestAPI{

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void empty() throws Exception{
		String local = "src/test/resources/file0.txt";
		String path = "/test-file0.txt";
		
		assertFile(local, path);
	}

	@Test
	public void text() throws Exception{
		String local = "src/test/resources/file1.txt";
		String path = "/test-file1.txt";
		
		assertFile(local, path);
	}
	

	@Test
	public void binary() throws Exception{
		String local = "src/test/resources/file2.jpg";
		String path = "/test-file2.jpg";
		
		assertFile(local, path);
	}

	@Test
	public void big() throws Exception {
		String local = "file3.big";
		String path = "/test-file3.big";
		
		final long SIZE = 1024 * 1024 * 1;
		final int BUFFER_SIZE = 1024 * 10;
		
		File file = new File(local);
		if(file.exists())file.delete();
		
		FileOutputStream os = new FileOutputStream(file);
		BufferedOutputStream bos = new BufferedOutputStream(os, BUFFER_SIZE);
		
		Random random = new Random(new Date().getTime());
		int seed = random.nextInt(Byte.MAX_VALUE);
		for(long i = 0; i < SIZE; i++){
			bos.write(seed);
			seed ++;
			if(seed >= Byte.MAX_VALUE) seed = Byte.MIN_VALUE;
		}
		bos.close();

		assertFile(local, path);
	}

	public void assertFile(String local, String path) throws Exception{
		//clear file on disk
		FileInfo fileInfo = getDiskAPI().metadata(getAccessToken(), path);
		if(!(fileInfo == null || fileInfo.isDeleted())){
			getDiskAPI().rm(getAccessToken(), path, false);
			fileInfo = getDiskAPI().metadata(getAccessToken(), path);
		}
		assertTrue(fileInfo == null || fileInfo.isDeleted());
		
		File file = new File(local);
		assertTrue(file.exists() && file.canRead());

		long size = file.length();
		FileInputStream is = new FileInputStream(file);
		getDiskAPI().upload(getAccessToken(), path, is, size, null);
		is.close();
		
		fileInfo = getDiskAPI().metadata(getAccessToken(), path);
		assertTrue(fileInfo != null && !fileInfo.isDeleted() && fileInfo.getSize() == size);
		
		File tmp = File.createTempFile(UUID.randomUUID().toString(), null);
		FileOutputStream os = new FileOutputStream(tmp);
		getDiskAPI().download(getAccessToken(), path, os, null);
		os.close();
		
		//compare the uploaded file with the local file
		assertTrue(compareMD5(file, tmp));
		
		//clear tmp file
		tmp.delete();
	}
	
	public static boolean compareMD5(File f1 ,File f2) throws IOException{
		String m1 = MD5Util.getFileMD5String(f1);
		String m2 = MD5Util.getFileMD5String(f2);
		if(m1 == null || m2 == null){
			return false;
		} else if(m1.equals(m2)){
			return true;
		} else {
			return false;
		}
	}
		
	public static boolean compareFile(File f1 ,File f2) throws IOException{
		boolean result = false;
        FileInputStream is1 = new FileInputStream(f1);
        FileInputStream is2 = new FileInputStream(f2);
        int b = 0;
        int c = 0;
        while(true){
            b = is1.read();
            c = is2.read();
            if(b == -1 && c == -1){
            	//完全一样，一起结束
            	result = true;
            	break;
            } else if(b == -1 || c == -1){
            	//文件长度不一样
            	break;
            } else if (b != c){
            	//文件信息不一样
            	break;
            }
        }
        is1.close();
        is2.close();
        return result;
	}
	
	static class MD5Util {
		protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
				'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		protected static MessageDigest messagedigest = null;
		static {
			try {
				messagedigest = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				System.err.println(MD5Util.class.getName() + "初始化失败，MessageDigest不支持MD5Util。");
				e.printStackTrace();
			}
		}

		public static String getFileMD5String(File file) throws IOException {
			FileInputStream in = new FileInputStream(file);
			FileChannel ch = in.getChannel();
			MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0,
					file.length());
			messagedigest.update(byteBuffer);
			String md5 = bufferToHex(messagedigest.digest());
			in.close();
			return md5;
		}

		public static String getMD5String(String s) {
			return getMD5String(s.getBytes());
		}

		public static String getMD5String(byte[] bytes) {
			messagedigest.update(bytes);
			return bufferToHex(messagedigest.digest());
		}

		private static String bufferToHex(byte bytes[]) {
			return bufferToHex(bytes, 0, bytes.length);
		}

		private static String bufferToHex(byte bytes[], int m, int n) {
			StringBuffer stringbuffer = new StringBuffer(2 * n);
			int k = m + n;
			for (int l = m; l < k; l++) {
				appendHexPair(bytes[l], stringbuffer);
			}
			return stringbuffer.toString();
		}

		private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
			char c0 = hexDigits[(bt & 0xf0) >> 4];
			char c1 = hexDigits[bt & 0xf];
			stringbuffer.append(c0);
			stringbuffer.append(c1);
		}

		public static boolean checkPassword(String password, String md5PwdStr) {
			String s = getMD5String(password);
			return s.equals(md5PwdStr);
		}
	}
}
