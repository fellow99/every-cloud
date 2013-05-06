package test.microblog.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import test.auth.shell.AuthShell;
import test.microblog.qq.QQMicroBlogTests;
import test.microblog.weibo.WeiboMicroBlogTests;

import com.fellow.every.auth.AccessToken;
import com.fellow.every.auth.OAuthTokenAuthenticator;
import com.fellow.every.exception.ServerException;
import com.fellow.every.http.HTTPEngine;
import com.fellow.every.http.impl.HTTPURLConnection;
import com.fellow.every.mircoblog.MicroBlogAPI;
import com.fellow.every.mircoblog.MicroBlogCommentInfo;
import com.fellow.every.mircoblog.MicroBlogInfo;
import com.fellow.every.provider.qq.QQMicroBlogAPI;
import com.fellow.every.provider.weibo.WeiboMicroBlogAPI;
import com.fellow.every.provider.weibo.WeiboUserAPI;

public class MicroBlogShell {

    private final static String PKG = AuthShell.class.getPackage().getName();
    
	public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        System.out.println("请输入OAuthTokenAuthenticator类：");
        String className = in.nextLine();
        
		MicroBlogAPI api = api(className);
		AccessToken accessToken = accessToken(className);
		MicroBlogShell shell = new MicroBlogShell(System.in, api, accessToken);
		shell.go();
	}

	public static AccessToken accessToken(String className) throws Exception{
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
        	throw new Exception("AccessToken is null");
        }
        return accessToken;
	}

    public static MicroBlogAPI api(String className) throws Exception{
        MicroBlogAPI api = null;
        if(className.toLowerCase().indexOf("weibo") >= 0){
    		api = WeiboMicroBlogTests.getMicroBlogAPI();
        } else if(className.toLowerCase().indexOf("qq") >= 0){
        	api = QQMicroBlogTests.getMicroBlogAPI();
        }
        return api;
	}

	private AccessToken accessToken;
	private MicroBlogAPI api;
    private BufferedReader reader;

    public MicroBlogShell(InputStream in, MicroBlogAPI api, AccessToken accessToken){
    	this.api = api;
    	this.accessToken = accessToken;
        this.reader = new BufferedReader(new InputStreamReader(in));
    }

	protected void go() throws Exception {
		while (true) {
			final String[] cmd = nextCommand();
			if (cmd == null) {
				return;
			}
			if (cmd.length == 0) {
				continue;
			}
			final String cmdName = cmd[0];
			if (cmdName.equalsIgnoreCase("exit")
					|| cmdName.equalsIgnoreCase("quit")) {
				return;
			}

			try {
				handleCommand(cmd);
			} catch (final Exception e) {
				Throwable cause = e;
				while (true) {
					if (cause == null)
						break;
					if (cause instanceof ServerException) {
						ServerException se = (ServerException) cause;
						System.err.println("Server exception:");
						System.err.println("CODE: " + se.getCode() + " "
								+ se.getMessage());
						System.err.println("URL: " + se.getUrl());
						System.err.println("RESPONSE: " + se.getInfo());
						break;
					}
					cause = cause.getCause();
				}
				System.err.println("Command failed:");
				e.printStackTrace(System.err);
			}
		}
	}

	/**
	 * Returns the next command, split into tokens.
	 */
	private String[] nextCommand() throws IOException {
		System.out.print("> ");
		final String line = reader.readLine();
		if (line == null) {
			return null;
		}
		final ArrayList cmd = new ArrayList();
		final StringTokenizer tokens = new StringTokenizer(line);
		while (tokens.hasMoreTokens()) {
			cmd.add(tokens.nextToken());
		}
		return (String[]) cmd.toArray(new String[cmd.size()]);
	}
    /**
     * Handles a command.
     */
    private void handleCommand(final String[] cmd) throws Exception
    {
        final String cmdName = cmd[0];
		if (cmdName.equalsIgnoreCase("add")) {
			add(cmd);
		} else if (cmdName.equalsIgnoreCase("delete")) {
			delete(cmd);
		} else if (cmdName.equalsIgnoreCase("repost")) {
			repost(cmd);
		} else if (cmdName.equalsIgnoreCase("reply")) {
			reply(cmd);
		} else if (cmdName.equalsIgnoreCase("comment")) {
			comment(cmd);
		} else {
			System.err.println("Unknown command \"" + cmdName + "\".");
		}
    }

    /**
     * Does a 'help' command.
     */
    private void help() {
        System.out.println("Commands:");
        System.out.println("help               Shows this message.");
        System.out.println("add <content>      Add a micro blog.");
        System.out.println("delete <id>      Delete a micro blog.");
        System.out.println("repost <id> <content>      Repost a micro blog.");
        System.out.println("reply <id> <content>      Reply a micro blog.");
        System.out.println("comment <id> <content>      Comment a micro blog.");
        System.out.println("exit       Exits this program.");
        System.out.println("quit       Exits this program.");
    }

    
    
	private void add(final String[] cmd) throws Exception {
		if (cmd.length < 2) {
			throw new Exception("USAGE: add <content>");
		}
		MicroBlogInfo info = api.add(accessToken, cmd[1]);
		
		if(info == null){
	        System.out.println("Fail.");
		} else {
	        System.out.println("Success. ID: " + info.getId());
		}
	}
    
	private void delete(final String[] cmd) throws Exception {
		if (cmd.length < 2) {
			throw new Exception("USAGE: delete <id>");
		}
		api.delete(accessToken, cmd[1]);
		
        System.out.println("Success.");
	}
    
	private void repost(final String[] cmd) throws Exception {
		if (cmd.length < 3) {
			throw new Exception("USAGE: repost <id> <content>");
		}
		MicroBlogInfo info = api.repost(accessToken, cmd[1], cmd[2]);
		
		if(info == null){
	        System.out.println("Fail.");
		} else {
	        System.out.println("Success. ID: " + info.getId());
		}
	}
    
	private void reply(final String[] cmd) throws Exception {
		if (cmd.length < 3) {
			throw new Exception("USAGE: reply <id> <content>");
		}
		MicroBlogInfo info = api.reply(accessToken, cmd[1], cmd[2]);
		
		if(info == null){
	        System.out.println("Fail.");
		} else {
	        System.out.println("Success. ID: " + info.getId());
		}
	}
    
	private void comment(final String[] cmd) throws Exception {
		if (cmd.length < 3) {
			throw new Exception("USAGE: comment <id> <content>");
		}
		MicroBlogCommentInfo comment = api.comment(accessToken, cmd[1], cmd[2]);
		
		if(comment == null){
	        System.out.println("Fail.");
		} else {
	        System.out.println("Success. ID: " + comment.getId());
		}
	}
}
