package com.fellow.every.auth;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class OAuth10Util {	
	
	private final static String NONCE_SAMPLE = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private final static String ENCODE = "UTF-8";
	private final static String HASH_METHOD = "HmacSHA1";
	
	private final static char JOIN_AND = '&';
	private final static char JOIN_EQUAL = '=';

	private String appKey;
	private String appSecret;

	public OAuth10Util(String appKey, String appSecret){
		this.appKey = appKey;
		this.appSecret = appSecret;
	}
	
	public String buildURL(AccessToken accessToken, String method, String host, String location, 
			Map<String, String> params, boolean isSecure) {
		
		TreeMap<String, String> signed_params;
		location = urlEncode(location);
		if (params != null)
			signed_params = new TreeMap<String, String>(params);
		else
			signed_params = new TreeMap<String, String>();
		
		signed_params.put("oauth_nonce", generateNonce());
		signed_params.put("oauth_timestamp", Long.toString((System.currentTimeMillis()/1000)));
		signed_params.put("oauth_version", "1.0");
		signed_params.put("oauth_signature_method", "HMAC-SHA1");
		
		String signature;
		
		try {
			signature = generateSignature(accessToken, method, host, location, signed_params, isSecure);
		} catch (NoSuchAlgorithmException e) {
			return null;
		} catch (InvalidKeyException e) {
			return null;
		}
		
		signed_params.put("oauth_signature", signature);
		signed_params.put("oauth_consumer_key", appKey);
		signed_params.put("oauth_token", accessToken.getAccessToken());

		String query = encodeParameters(signed_params);
		return (isSecure ? "https://" : "http://") + host + location + "?" + query;
	}
	
	/**
	 * prepare parameters for HTTP request.
	 * @param signed_params
	 * @return
	 */
	public String encodeParameters(Map<String, String> params) {
		StringBuffer buf = new StringBuffer();
		for (Iterator<String> iter = params.keySet().iterator(); iter.hasNext();) {
			String key = iter.next();
			buf.append(key);
			buf.append(JOIN_EQUAL);
			try {
				buf.append(URLEncoder.encode(params.get(key), ENCODE));
			} catch (Exception e) {
				// never come here
			}
			if (iter.hasNext())
				buf.append(JOIN_AND);			
		}
		return buf.toString();
	}
	
	private String generateNonce() {
		return generateNonce(10);
	}
	
	private String generateNonce(int length) {
		Random random = new Random(System.currentTimeMillis());
		if (length < 10)
			length = 10;
		
		int MAX_LEN = NONCE_SAMPLE.length();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < length; i++) {
			buf.append(NONCE_SAMPLE.charAt(random.nextInt(MAX_LEN)));
		}
		return buf.toString();
	}
	
	private String generateSignature(AccessToken accessToken, String method, String host, String location, 
			TreeMap<String, String> params, boolean isSecure
			) throws NoSuchAlgorithmException, InvalidKeyException{
		
		String msg = generateBaseString(accessToken, method, host, location, params, isSecure);
		String key = generateSecret(accessToken);
		
		Mac mac = Mac.getInstance(HASH_METHOD);
		SecretKeySpec skey = new SecretKeySpec(key.getBytes(), HASH_METHOD);
		
		mac.init(skey);
		byte[] data = mac.doFinal(msg.toString().getBytes());
		return encodeBase64(data);
	}
	
	private String generateSecret(AccessToken accessToken) {
		StringBuffer buf = new StringBuffer();
		buf.append(appSecret);
		buf.append(JOIN_AND);
		buf.append(accessToken.getAccessTokenSecret());
		
		return buf.toString();
	}
	
	/**
	 * 
	 * @param method GET or POST.
	 * @param host like 'openapi.kuaipan.cn', without scheme.
	 * @param location starts with '/', no non-ASCII characters.
	 * @param params without tokens.
	 * @param consumer
	 * @param token
	 * @param isSecure whether uses HTTPS or not.
	 * @return basestring defined in RFC 5849 3.4.1. Signature Base String
	 */
	private String generateBaseString(AccessToken accessToken, String method, String host, String location, 
			TreeMap<String, String> params, boolean isSecure) {
		
		StringBuffer buf = new StringBuffer();
		buf.append(method);
		buf.append(JOIN_AND);
		buf.append(oauthEncode((isSecure ? "https://" : "http://") + host + location));
		
		buf.append(JOIN_AND);
		buf.append(oauthEncode(normalizeParameters(accessToken, params)));
		
		return buf.toString();
	}
	
	/**
	 * 
	 * @param params
	 * @param consumer
	 * @param token
	 * @return the third part of basestring
	 */
	private String normalizeParameters(AccessToken accessToken, TreeMap<String, String> params) {
		StringBuffer buf = new StringBuffer();
		TreeMap<String, String> tm = new TreeMap<String, String>(params);
		
		tm.put("oauth_consumer_key", appKey);	
		tm.put("oauth_token", accessToken.getAccessToken());

		
		for (Iterator<String> iter = tm.keySet().iterator(); iter.hasNext(); ) {
			String k = iter.next();			
			buf.append(k);
			buf.append(JOIN_EQUAL);
			buf.append(oauthEncode(tm.get(k)));
			if (iter.hasNext())
				buf.append(JOIN_AND);
		}
		return buf.toString();
	}
	
	/**
	 * unreserved characters (ALPHA / DIGIT / "-" / "." / "_" / "~") MUST not be encoded, 
	 * others MUST be encoded.
	 * @param str
	 * @return percent encoding defined in RFC 5849 3.6. Percent Encoding
	 */
	public static String oauthEncode(String str) {
		try {
			return URLEncoder.encode(str, ENCODE)
					.replace("*", "%2A")
					.replace("%7E", "~")
					.replace("+", "%20");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	
	public static String urlEncode(String str) {
		try {
			return URLEncoder.encode(str, ENCODE).replace("%2F", "/");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}


	public static String encodeBase64(byte[] from) {
		final char last2byte = (char) Integer
				.parseInt("00000011", 2);
		final char last4byte = (char) Integer
				.parseInt("00001111", 2);
		final char last6byte = (char) Integer
				.parseInt("00111111", 2);
		final char lead6byte = (char) Integer
				.parseInt("11111100", 2);
		final char lead4byte = (char) Integer
				.parseInt("11110000", 2);
		final char lead2byte = (char) Integer
				.parseInt("11000000", 2);
		final char[] encodeTable = new char[] { 'A', 'B', 'C', 'D',
				'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
				'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
				'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
				'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
				'4', '5', '6', '7', '8', '9', '+', '/' };
		
		StringBuilder to = new StringBuilder((int) (from.length * 1.34) + 3);
		int num = 0;
		char currentByte = 0;
		for (int i = 0; i < from.length; i++) {
			num = num % 8;
			while (num < 8) {
				switch (num) {
				case 0:
					currentByte = (char) (from[i] & lead6byte);
					currentByte = (char) (currentByte >>> 2);
					break;
				case 2:
					currentByte = (char) (from[i] & last6byte);
					break;
				case 4:
					currentByte = (char) (from[i] & last4byte);
					currentByte = (char) (currentByte << 2);
					if ((i + 1) < from.length) {
						currentByte |= (from[i + 1] & lead2byte) >>> 6;
					}
					break;
				case 6:
					currentByte = (char) (from[i] & last2byte);
					currentByte = (char) (currentByte << 4);
					if ((i + 1) < from.length) {
						currentByte |= (from[i + 1] & lead4byte) >>> 4;
					}
					break;
				}
				to.append(encodeTable[currentByte]);
				num += 6;
			}
		}
		if (to.length() % 4 != 0) {
			for (int i = 4 - to.length() % 4; i > 0; i--) {
				to.append("=");
			}
		}
		return to.toString();
	}


	public static String escape(String src) {  
        int i;  
        char j;  
        StringBuffer tmp = new StringBuffer();  
        tmp.ensureCapacity(src.length() * 6);  
        for (i = 0; i < src.length(); i++) {  
            j = src.charAt(i);  
            if (Character.isDigit(j) || Character.isLowerCase(j)  
                    || Character.isUpperCase(j))  
                tmp.append(j);  
            else if (j < 256) {  
                tmp.append("%");  
                if (j < 16)  
                    tmp.append("0");  
                tmp.append(Integer.toString(j, 16));  
            } else {  
                tmp.append("%u");  
                tmp.append(Integer.toString(j, 16));  
            }  
        }  
        return tmp.toString();  
    }  
 
	public static String unescape(String src) {  
        StringBuffer tmp = new StringBuffer();  
        tmp.ensureCapacity(src.length());  
        int lastPos = 0, pos = 0;  
        char ch;
        while (lastPos < src.length()) {  
            pos = src.indexOf("%", lastPos);  
            if (pos == lastPos) {  
                if (src.charAt(pos + 1) == 'u') {  
                    ch = (char) Integer.parseInt(src  
                            .substring(pos + 2, pos + 6), 16);  
                    tmp.append(ch);  
                    lastPos = pos + 6;  
                } else {  
                    ch = (char) Integer.parseInt(src  
                            .substring(pos + 1, pos + 3), 16);  
                    tmp.append(ch);  
                    lastPos = pos + 3;  
                }
            } else {  
                if (pos == -1) {  
                    tmp.append(src.substring(lastPos));  
                    lastPos = src.length();  
                } else {  
                    tmp.append(src.substring(lastPos, pos));  
                    lastPos = pos;  
                }  
            }  
        }  
        return tmp.toString();  
    }  
}