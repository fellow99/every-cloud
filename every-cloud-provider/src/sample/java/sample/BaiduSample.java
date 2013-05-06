package sample;

import java.io.*;
import java.util.*;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import com.fellow.every.auth.AccessToken;
import com.fellow.every.disk.FileInfo;
import com.fellow.every.disk.FileType;
import com.fellow.every.disk.QuotaInfo;
import com.fellow.every.http.HTTPEngine;
import com.fellow.every.http.impl.HTTPURLConnection;
import com.fellow.every.provider.baidu.BaiduAccessToken;
import com.fellow.every.provider.baidu.BaiduDiskAPI;
import com.fellow.every.provider.baidu.BaiduOAuth2API;
import com.fellow.every.provider.baidu.BaiduUserAPI;
import com.fellow.every.user.AccountInfo;
import com.fellow.every.user.UserInfo;

public class BaiduSample {

    // Replace these with your own api key and secret
	private static final String API_KEY = "your_api_key";
	private static final String API_SECRET = "your_api_secret";
	
	private static final Token EMPTY_TOKEN = null;
	private static final HTTPEngine HTTP_ENGINE = new HTTPURLConnection();
	private final static String ROOT = "/apps/EveryDisk";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		BaiduSample sample = new BaiduSample();
		
		AccessToken accessToken = sample.login();
		
		sample.user(accessToken);
		
		sample.disk(accessToken);
	}

	/**
	 * Login and get a accessToken object.
	 * @return a scribe Token
	 */
	public AccessToken login() throws Exception {
	    OAuthService service = new ServiceBuilder()
	        .provider(BaiduOAuth2API.class)
	        .apiKey(API_KEY)
	        .apiSecret(API_SECRET)
	        .build();
	    Scanner in = new Scanner(System.in);

	    System.out.println("=== " + this.getClass().getName() + "'s OAuth Workflow ===");
	    System.out.println();

	    // Obtain the Authorization URL
	    System.out.println("Fetching the Authorization URL...");
	    String authorizationUrl = service.getAuthorizationUrl(EMPTY_TOKEN);
	    System.out.println("Got the Authorization URL!");
	    System.out.println("Now go and authorize Scribe here:");
	    System.out.println(authorizationUrl);
	    System.out.println("And paste the authorization code here");
	    System.out.print(">>");
	    Verifier verifier = new Verifier(in.nextLine());
	    System.out.println();

	    // Trade the Request Token and Verifier for the Access Token
	    System.out.println("Trading the Request Token for an Access Token...");
	    Token token = service.getAccessToken(EMPTY_TOKEN, verifier);
	    System.out.println("Got the Access Token!");
	    System.out.println("(if your curious it looks like this: " + token + " )");
	    System.out.println();


		BaiduAccessToken accessToken = new BaiduAccessToken();
		accessToken.load(token.getRawResponse());
        
        return accessToken;
	}
	

	public void user(AccessToken accessToken) throws Exception {
		BaiduUserAPI api = new BaiduUserAPI();
		api.setAppKey(API_KEY);
		api.setAppSecret(API_SECRET);
		api.setHttpEngine(HTTP_ENGINE);

	    System.out.println("==acount info==");
	    AccountInfo account = api.myAccount(accessToken);
	    System.out.println(account);
	    System.out.println();

	    System.out.println("==user info==");
	    UserInfo user = api.myInfo(accessToken);
	    System.out.println(user);
	    System.out.println();
	}
	

	public void disk(AccessToken accessToken) throws Exception {
		BaiduDiskAPI api = new BaiduDiskAPI();
		api.setAppKey(API_KEY);
		api.setAppSecret(API_SECRET);
		api.setHttpEngine(HTTP_ENGINE);
		api.setProperty(BaiduDiskAPI.PROPERTY_APP_ROOT, ROOT);

	    System.out.println("==quota info==");
		QuotaInfo quota = api.quota(accessToken);
	    System.out.println(quota);
	    System.out.println();

	    System.out.println("==Root dir metadata==");
	    FileInfo root = api.metadata(accessToken, "/");
	    System.out.println(root);
	    System.out.println();

	    System.out.println("==Root file list==");
	    FileInfo[] list = api.list(accessToken, "/");
	    System.out.println("File count: " + list.length);
	    System.out.println();
	    
	    
	    System.out.println("==sample dir==");
	    FileInfo dirInfo = api.metadata(accessToken, "/sample-dir");
	    if(dirInfo == null || dirInfo.getType() == FileType.NULL){
	    	//create dir
	    	api.mkdir(accessToken, "/sample-dir");
	    	dirInfo = api.metadata(accessToken, "/sample-dir");
		    System.out.println(dirInfo);
		    System.out.println();
	    }
	    //move/rename
	    api.mv(accessToken, "/sample-dir", "/sample-dir2");
	    //copy
	    api.cp(accessToken, "/sample-dir2", "/sample-dir3");
	    //delete 
	    api.rm(accessToken, "/sample-dir2", false);
	    api.rm(accessToken, "/sample-dir3", false);
	    
	    System.out.println("==sample-file==");
	    //delete
	    FileInfo fileInfo = api.metadata(accessToken, "/sample-file.txt");
	    if(fileInfo != null && fileInfo.getType() == FileType.FILE){
	    	api.rm(accessToken, "/sample-file.txt", false);
	    }
	    //upload
	    File file = new File("LICENSE");
	    FileInputStream is = new FileInputStream(file);
	    api.upload(accessToken, "/sample-file.txt", is, file.length(), null);
	    is.close();
	    fileInfo = api.metadata(accessToken, "/sample-file.txt");
	    System.out.println(fileInfo);
	    System.out.println();
	    
	    //download
	    File tmpFile = File.createTempFile(UUID.randomUUID().toString(), ".txt");
	    FileOutputStream os = new FileOutputStream(tmpFile);
	    api.download(accessToken, "/sample-file.txt", os, null);
	    os.close();
	    System.out.println(tmpFile);
	    System.out.println();
	}
}
