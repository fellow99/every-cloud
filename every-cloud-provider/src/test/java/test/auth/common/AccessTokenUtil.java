package test.auth.common;

import java.io.*;
import java.util.*;

import org.scribe.model.Token;

public class AccessTokenUtil {

	public static Token loadAppToken(String fileName, String api){
		try {
	        File file=new File(fileName);
	        if(!file.exists()) return null;
	        
			Properties prop = new Properties();
			prop.load(new FileInputStream(file));


			String key = prop.getProperty(api + ".key");
			String secret = prop.getProperty(api + ".secret");

			if (key == null || secret == null) return null;
			
			return new Token(key, secret);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void saveAuthFile(String fileName, String raw) {
		FileWriter f = null;		
		try {
			f = new FileWriter(fileName, false);
			f.write(raw);
			f.flush();
			f.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public static String loadAuthFile(String fileName){
		try {
	        File file=new File(fileName);
	        if(!file.exists()) return null;
	        
		    BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		    StringBuffer buffer = new StringBuffer();
		    String line = null;
		    while ((line = in.readLine()) != null){
		      buffer.append(line);
		    }
		    in.close();
		    return buffer.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}
}
