package com.fellow.every.exception;

public class ServerException extends Exception{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	private String code;
	private String info;
	private String url;

	protected ServerException() {
        super();
    }
	
	public ServerException(String url, String code, String message, String info){
		super(message);
		this.url = url;
		this.code = code;
		this.info = info;
	}

	public ServerException(String url, String code, String message, String info, Throwable cause){
		super(message, cause);
		this.url = url;
		this.code = code;
		this.info = info;
	}

	public String getCode() {
		return code;
	}

	public String getInfo() {
		return info;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public String toString(){
        System.err.println("Server exception:");
    	System.err.println("CODE: " + this.getCode() + " " + this.getMessage());
    	System.err.println("URL: " + this.getUrl());
    	System.err.println("RESPONSE: " + this.getInfo());
    	
    	return super.toString();
	}
}
