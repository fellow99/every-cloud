package test.auth.common;

import java.io.InputStream;
import java.util.Scanner;

import org.scribe.model.Token;
import org.scribe.model.Verifier;

import com.fellow.every.auth.AccessToken;

public abstract class AbstractShellAuthenticatorV10 extends AbstractAuthenticator{
	
	private InputStream in;

	public AbstractShellAuthenticatorV10(InputStream in){
		this.in = in;
	}
	
	@Override
	public AccessToken requestAuthentication() throws Exception {
		final String FILE_NAME = getProvider().getName() + ".log";
		
        Scanner in = new Scanner(this.in);
        
        AccessToken accessToken = this.createAccessToken();

        String raw = AccessTokenUtil.loadAuthFile(FILE_NAME);
		if(raw != null && raw.length() >= 0){
	        System.out.print("已检查到本地保存了认证信息，是否继续使用（y/n）？");
			String a1 = in.nextLine();
	        System.out.println();
	        
			if("y".equalsIgnoreCase(a1)){
		        accessToken.load(raw);
				return accessToken;
			}
		}

        System.out.println("=== 开始OAuth认证：" + getProvider().getName() + " ===");
        System.out.println();
  
        // Grab a request token.  
        System.out.println("正在获取requestToken...");
        Token requestToken = getService().getRequestToken();  
        System.out.println("成功获取：" + requestToken.getToken());  
        System.out.println();
  
        // Obtain the Authorization URL  
        System.out.println("正在获取认证链接URL...");  
        String authorizationUrl = getService().getAuthorizationUrl(requestToken);  
        System.out.println("成功获取认证链接，现在请访问该链接进行登录认证:");  
        System.out.println(authorizationUrl);  
        System.out.print("认证完成后把授权码粘贴到此处：");
        Verifier verifier = new Verifier(in.nextLine());  
        System.out.println();  
  
        // Trade the Request Token and Verfier for the Access Token  
        System.out.println("正在获取accessToken...");  
        Token aToken = getService().getAccessToken(requestToken, verifier);

        System.out.println("成功获取："  + accessToken);  
        System.out.println("accessToken的响应 信息: " + aToken.getRawResponse());
        System.out.println();

        System.out.println("正在保存accessToken：" + FILE_NAME);  

        raw = aToken.getRawResponse();
        AccessTokenUtil.saveAuthFile(FILE_NAME, raw);
        
        accessToken.load(raw);
		return accessToken;
	}
}
