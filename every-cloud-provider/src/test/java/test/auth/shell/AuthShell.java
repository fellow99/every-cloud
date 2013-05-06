package test.auth.shell;

import java.util.Scanner;

import com.fellow.every.auth.AccessToken;
import com.fellow.every.auth.OAuthTokenAuthenticator;

public class AuthShell {

    private final static String PKG = AuthShell.class.getPackage().getName();

	/**
	 * @param args
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
        
        Scanner in = new Scanner(System.in);
        System.out.println("请输入OAuthTokenAuthenticator类：");
        String className = in.nextLine();

        Class<? extends OAuthTokenAuthenticator> clazz = null;
        
        if(clazz == null){
            try {
            	clazz = (Class<? extends OAuthTokenAuthenticator>)Class.forName(PKG + "." + className + "ShellAuthenticator");
			} catch (ClassNotFoundException e) {
			}
        }
        
        if(clazz == null){
            try {
            	clazz = (Class<? extends OAuthTokenAuthenticator>)Class.forName(PKG + "." + className);
			} catch (ClassNotFoundException e) {
			}
        }
        
        if(clazz == null){
            try {
            	clazz = (Class<? extends OAuthTokenAuthenticator>)Class.forName(className);
			} catch (ClassNotFoundException e) {
			}
        }

        if(clazz == null){
        	throw new Exception("Class not found: " + className);
        }
        
        OAuthTokenAuthenticator authenticator = clazz.newInstance();
        AccessToken accessToken = authenticator.requestAuthentication();
        
        
        if(accessToken != null){
            System.out.println("认证完成。");
        } else {
        	throw new Exception("Session is null");
        }
	}

}
