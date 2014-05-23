package com.pixnet;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * PIXNET API Oauth1.0a
 * 
 * @author Koi
 * @version 1.0.8
 */
public class PostToPIXNET {
	private String oauth_callback = "oob";
	private String oauth_consumer_key = "";
	private String oauth_consumer_secret = "";
	private String oauth_nonce = "";
	private String oauth_signature_method = "HMAC-SHA1";
	private String oauth_timestamp = "";
	private String oauth_token = "";
	private String oauth_verifier = "";
	private String oauth_version = "1.0";
	private String oauth_signature = "";
	private String oauth_token_secret = "";

	/**
	 * Constructor
	 * 
	 * @param oauth_consumer_key
	 *            your consumer key
	 * @param oauth_consumer_secret
	 *            your consumer secret
	 * @throws Exception
	 */
	public PostToPIXNET(String oauth_consumer_key, String oauth_consumer_secret) {
		this.oauth_consumer_key = oauth_consumer_key;
		this.oauth_consumer_secret = oauth_consumer_secret;
	}

	/**
	 * Get OauthTokenSecret
	 * 
	 * @return oauth_token_secret after access
	 */
	public String getOauthTokenSecret() {
		return oauth_token_secret;
	}

	/**
	 * Get OauthToken
	 * 
	 * @return oauth_token after access
	 */
	public String getOauthToken() {
		return oauth_token;
	}

	private void postArticle(String title, String body) throws Exception {
		try {
			oauth_nonce = set_nonce();
			oauth_timestamp = set_timestamp();
			oauth_signature = get_signature(
					set_baseForPostArticle(title, body), oauth_consumer_secret
							+ "&" + oauth_token_secret);
			URL url = new URL("http://emma.pixnet.cc/blog/articles");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestMethod("POST");
			String header = "oauth_consumer_key=\""
					+ URLEncoder.encode(oauth_consumer_key, "utf-8") + "\","
					+ "oauth_nonce=\""
					+ URLEncoder.encode(oauth_nonce, "utf-8") + "\","
					+ "oauth_signature_method=\""
					+ URLEncoder.encode(oauth_signature_method, "utf-8")
					+ "\"," + "oauth_timestamp=\""
					+ URLEncoder.encode(oauth_timestamp, "utf-8") + "\","
					+ "oauth_token=\""
					+ URLEncoder.encode(oauth_token, "utf-8") + "\","
					+ "oauth_version=\""
					+ URLEncoder.encode(oauth_version, "utf-8") + "\","
					+ "oauth_signature=\"" + oauth_signature + "\"";
			con.setRequestProperty("Authorization", "OAuth " + header);
			con.setDoOutput(true);
			BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(
					con.getOutputStream()));
			wr.write("body=" + URLEncoder.encode(body, "UTF-8") + "&title="
					+ URLEncoder.encode(title, "UTF-8"));
			wr.flush();
			wr.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream(), "UTF-8"));
			System.out.println(br.readLine());
		} catch (Exception e) {
			System.out.println("Post Fail");
		}

	}

	private void readUser() throws Exception {
		URLConnection con = null;
		try {
			oauth_nonce = set_nonce();
			oauth_timestamp = set_timestamp();
			oauth_signature = get_signature(set_baseForUserInfo(),
					oauth_consumer_secret + "&" + oauth_token_secret);
			String header = "oauth_consumer_key=\""
					+ URLEncoder.encode(oauth_consumer_key, "utf-8") + "\","
					+ "oauth_nonce=\""
					+ URLEncoder.encode(oauth_nonce, "utf-8") + "\","
					+ "oauth_signature_method=\""
					+ URLEncoder.encode(oauth_signature_method, "utf-8")
					+ "\"," + "oauth_timestamp=\""
					+ URLEncoder.encode(oauth_timestamp, "utf-8") + "\","
					+ "oauth_token=\""
					+ URLEncoder.encode(oauth_token, "utf-8") + "\","
					+ "oauth_version=\""
					+ URLEncoder.encode(oauth_version, "utf-8") + "\","
					+ "oauth_signature=\"" + oauth_signature + "\"";
			URL url = new URL("http://emma.pixnet.cc/account");
			con = url.openConnection();
			con.setRequestProperty("Authorization", "OAuth " + header);
			con.setDoOutput(true);
			con.connect();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream(), "UTF-8"));
			System.out.println(br.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Set Oauth_Verifier and Get AccessToken
	 * 
	 * @param oauth_verifier
	 *            Oauth_Verifier from Authorize Url
	 */
	public void access(String oauth_verifier) {
		try {
			this.oauth_verifier = oauth_verifier;
			oauth_nonce = set_nonce();
			oauth_timestamp = set_timestamp();
			oauth_signature = get_signature(set_baseForAccess(),
					oauth_consumer_secret + "&" + oauth_token_secret);
			String header = "http://emma.pixnet.cc/oauth/access_token?"
					+ "oauth_callback="
					+ oauth_callback
					+ "&"
					+ "oauth_consumer_key="
					+ oauth_consumer_key
					+ "&"
					+ "oauth_nonce="
					+ oauth_nonce
					+ "&"
					+ "oauth_signature_method="
					+ oauth_signature_method
					+ "&"
					+ "oauth_timestamp="
					+ oauth_timestamp
					+ "&"
					+ "oauth_token="
					+ oauth_token
					+ "&"
					+ "oauth_verifier="
					+ oauth_verifier
					+ "&"
					+ "oauth_version="
					+ oauth_version
					+ "&" + "oauth_signature=" + oauth_signature;
			URL url = new URL(header);
			URLConnection conn = url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String accessUrl = br.readLine();
			String[] accToken = accessUrl.split("&");
			oauth_token = accToken[0].replace("oauth_token=", "");
			oauth_token_secret = accToken[1].replace("oauth_token_secret=", "");
		} catch (IOException e) {
			access(oauth_verifier);
		}
	}

	/**
	 * Get Authorize Url
	 * 
	 * @return Authorize Url
	 */
	public String request() {
		try {
			oauth_nonce = set_nonce();
			oauth_timestamp = set_timestamp();
			oauth_signature = get_signature(set_baseForRequest(),
					oauth_consumer_secret + "&");
			String header = "http://emma.pixnet.cc/oauth/request_token?"
					+ "oauth_callback=" + oauth_callback + "&"
					+ "oauth_consumer_key=" + oauth_consumer_key + "&"
					+ "oauth_nonce=" + oauth_nonce + "&"
					+ "oauth_signature_method=" + oauth_signature_method + "&"
					+ "oauth_timestamp=" + oauth_timestamp + "&"
					+ "oauth_version=" + oauth_version + "&"
					+ "oauth_signature=" + oauth_signature;
			URL url = new URL(header);
			URLConnection conn = url.openConnection();
			conn.connect();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String authUrl = br.readLine();
			String[] reqToken = authUrl.split("&");
			oauth_token = reqToken[0].replace("oauth_token=", "");
			oauth_token_secret = reqToken[1].replace("oauth_token_secret=", "");
			reqToken[4] = URLDecoder.decode(reqToken[4], "utf-8");
			String[] token = reqToken[4].split("request_auth_url=");
			return token[1];
		} catch (IOException e) {
			return request();
		}
	}

	private String set_baseForPostArticle(String title, String body)
			throws UnsupportedEncodingException {
		String bss;
		bss = "POST"
				+ "&"
				+ URLEncoder.encode("http://emma.pixnet.cc/blog/articles",
						"utf-8") + "&";
		String bsss = "body=" + body + "&" + "oauth_consumer_key="
				+ oauth_consumer_key + "&" + "oauth_nonce=" + oauth_nonce + "&"
				+ "oauth_signature_method=" + oauth_signature_method + "&"
				+ "oauth_timestamp=" + oauth_timestamp + "&" + "oauth_token="
				+ oauth_token + "&" + "oauth_version=" + oauth_version + "&"
				+ "title=" + title;
		bsss = URLEncoder.encode(bsss, "utf-8");
		return bss + bsss;
	}

	private String set_baseForUserInfo() throws UnsupportedEncodingException {
		String bss;
		bss = "GET" + "&"
				+ URLEncoder.encode("http://emma.pixnet.cc/account", "utf-8")
				+ "&";
		String bsss = "oauth_consumer_key=" + oauth_consumer_key + "&"
				+ "oauth_nonce=" + oauth_nonce + "&"
				+ "oauth_signature_method=" + oauth_signature_method + "&"
				+ "oauth_timestamp=" + oauth_timestamp + "&" + "oauth_token="
				+ oauth_token + "&" + "oauth_version=" + oauth_version;
		bsss = URLEncoder.encode(bsss, "utf-8");
		return bss + bsss;
	}

	private String set_baseForAccess() throws UnsupportedEncodingException {
		String bss;
		bss = "GET"
				+ "&"
				+ URLEncoder.encode("http://emma.pixnet.cc/oauth/access_token",
						"utf-8") + "&";
		String bsss = "oauth_callback=" + URLEncoder.encode("oob", "utf-8")
				+ "&" + "oauth_consumer_key=" + oauth_consumer_key + "&"
				+ "oauth_nonce=" + oauth_nonce + "&"
				+ "oauth_signature_method=" + oauth_signature_method + "&"
				+ "oauth_timestamp=" + oauth_timestamp + "&" + "oauth_token="
				+ oauth_token + "&" + "oauth_verifier=" + oauth_verifier + "&"
				+ "oauth_version=" + oauth_version;
		bsss = URLEncoder.encode(bsss, "utf-8");
		return bss + bsss;
	}

	private String set_baseForRequest() throws UnsupportedEncodingException {
		String bss;
		bss = "GET"
				+ "&"
				+ URLEncoder.encode(
						"http://emma.pixnet.cc/oauth/request_token", "utf-8")
				+ "&";
		String bsss = "oauth_callback=" + URLEncoder.encode("oob", "utf-8")
				+ "&" + "oauth_consumer_key=" + oauth_consumer_key + "&"
				+ "oauth_nonce=" + oauth_nonce + "&"
				+ "oauth_signature_method=" + oauth_signature_method + "&"
				+ "oauth_timestamp=" + oauth_timestamp + "&" + "oauth_version="
				+ oauth_version;
		bsss = URLEncoder.encode(bsss, "utf-8");
		return bss + bsss;
	}

	/**
	 * Create signature
	 * 
	 * @param data
	 *            BaseString
	 * @param key
	 *            ConsumerSecret&OauthSecret
	 * @return String Signature
	 */
	public String get_signature(String data, String key) {
		byte[] byteHMAC = null;
		try {
			Mac mac = Mac.getInstance("HmacSHA1");
			SecretKeySpec spec = new SecretKeySpec(key.getBytes(), "HmacSHA1");
			mac.init(spec);
			byteHMAC = mac.doFinal(data.getBytes());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException ignore) {
		}
		new Base64();
		String oauth = Base64.encodeBase64String(byteHMAC);
		oauth = oauth.replace("\r\n", "");
		return oauth;
	}

	/**
	 * Create the timestamp
	 * 
	 * @return String timestamp
	 */
	public String set_timestamp() {
		Date date = new Date();
		long time = date.getTime();
		return (time + "").substring(0, 10);
	}

	/**
	 * Create a random nonce
	 * 
	 * @return String nonce
	 */
	public String set_nonce() {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 18; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
}