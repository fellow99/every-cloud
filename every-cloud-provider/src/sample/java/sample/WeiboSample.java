package sample;

import java.util.Scanner;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import com.fellow.every.auth.AccessToken;
import com.fellow.every.http.HTTPEngine;
import com.fellow.every.http.impl.HTTPURLConnection;
import com.fellow.every.provider.weibo.WeiboAccessToken;
import com.fellow.every.provider.weibo.WeiboOAuth2API;
import com.fellow.every.provider.weibo.WeiboUserAPI;
import com.fellow.every.user.AccountInfo;
import com.fellow.every.user.UserInfo;

public class WeiboSample {

    // Replace these with your own api key and secret
	private static final String API_KEY = "your_api_key";
	private static final String API_SECRET = "your_api_secret";
	
	private static final Token EMPTY_TOKEN = null;
	private static final HTTPEngine HTTP_ENGINE = new HTTPURLConnection();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		WeiboSample sample = new WeiboSample();

		AccessToken accessToken = sample.login();
		
		sample.user(accessToken);
	}

	/**
	 * Login and get a Session object.
	 * @return a scribe Token
	 */
	public AccessToken login() throws Exception {
	    OAuthService service = new ServiceBuilder()
	        .provider(WeiboOAuth2API.class)
	        .apiKey(API_KEY)
	        .apiSecret(API_SECRET)
	        .callback("http://127.0.0.1")
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


	    WeiboAccessToken accessToken = new WeiboAccessToken();
		accessToken.load(token.getRawResponse());
		
        return accessToken;
	}
	

	public void user(AccessToken accessToken) throws Exception {
		WeiboUserAPI api = new WeiboUserAPI();
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

}
