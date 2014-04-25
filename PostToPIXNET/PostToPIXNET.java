import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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

public class PostToPIXNET {
	String oauth_callback = "oauth_callback=oob";
	String oauth_consumer_key = "oauth_consumer_key=46857444204aa2ce0cb8c9666a8eadae";
	String oauth_nonce = "oauth_nonce=";
	String oauth_signature_method = "oauth_signature_method=HMAC-SHA1";
	String oauth_timestamp = "oauth_timestamp=";
	String oauth_token = "oauth_token=";
	String oauth_verifier = "oauth_verifier=";
	String oauth_version = "oauth_version=1.0";
	String oauth_signature = "oauth_signature=";
	String oauth_token_secret = "";
	static String[][] oauth = { { "oauth_callback", "oob" },
			{ "oauth_consumer_key", "46857444204aa2ce0cb8c9666a8eadae" },
			{ "oauth_nonce", "" }, { "oauth_signature_method", "HMAC-SHA1" },
			{ "oauth_timestamp", "" }, { "oauth_token", "" },
			{ "oauth_verifier", "" }, { "oauth_version", "1.0" },
			{ "oauth_signature", "" } };

	PostToPIXNET() throws Exception {
		request();
		
	}

	public void request() throws Exception {
		oauth_nonce = set_nonce();
		oauth_timestamp = set_timestamp();
		oauth_signature = get_signature(set_basestring(),
				"dfa46d6dc4acfc8a25046fffcc5d9b14&");
		String header = "http://emma.pixnet.cc/oauth/request_token?"
				+ oauth_callback + "&" + oauth_consumer_key + "&" + oauth_nonce
				+ "&" + oauth_signature_method + "&" + oauth_timestamp + "&"
				+ oauth_version + "&" + oauth_signature;
		URL url = new URL(header);
		//System.out.println(header);
		URLConnection conn = url.openConnection();
		BufferedReader br = new BufferedReader(new InputStreamReader(
				conn.getInputStream(), "UTF-8"));
		String authUrl = br.readLine();
		String[] reqToken = authUrl.split("&");
		oauth_token = reqToken[0];
		oauth_token_secret = reqToken[1];
		reqToken[4] = URLDecoder.decode(reqToken[4], "utf-8");
		String[] token = reqToken[4].split("request_auth_url=");
		System.out.println(token[1]);
	}

	public static void main(String[] args) throws Exception {
		new PostToPIXNET();
		// request
		/*
		 * // auth BufferedReader uin = new BufferedReader( new
		 * InputStreamReader(System.in)); oauth[2][1] = set_nonce(); oauth[4][1]
		 * = set_timestamp(); oauth[6][1] = uin.readLine(); oauth[8][1] =
		 * hmacsha1(set_basestring2(), "dfa46d6dc4acfc8a25046fffcc5d9b14&" +
		 * oauth_token_secret); header =
		 * "http://emma.pixnet.cc/oauth/access_token?" + oauth[0][0] + "=" +
		 * oauth[0][1] + "&" + oauth[1][0] + "=" + oauth[1][1] + "&" +
		 * oauth[2][0] + "=" + oauth[2][1] + "&" + oauth[3][0] + "=" +
		 * oauth[3][1] + "&" + oauth[4][0] + "=" + oauth[4][1] + "&" +
		 * oauth[5][0] + "=" + oauth[5][1] + "&" + oauth[6][0] + "=" +
		 * oauth[6][1] + "&" + oauth[7][0] + "=" + oauth[7][1] + "&" +
		 * oauth[8][0] + "=" + oauth[8][1]; url = new URL(header); conn =
		 * url.openConnection(); br = new BufferedReader(new
		 * InputStreamReader(conn.getInputStream(), "UTF-8")); String accessUrl
		 * = br.readLine(); String[] accToken = accessUrl.split("&");
		 * oauth[5][1] = accToken[0].replace("oauth_token=", "");
		 * oauth_token_secret = accToken[1].replace("oauth_token_secret=", "");
		 * // access oauth[2][1] = set_nonce(); oauth[4][1] = set_timestamp();
		 * oauth[8][1] = hmacsha1(set_basestring3(),
		 * "dfa46d6dc4acfc8a25046fffcc5d9b14&" + oauth_token_secret); header =
		 * "http://emma.pixnet.cc/account?" + oauth[0][0] + "=" + oauth[0][1] +
		 * "&" + oauth[1][0] + "=" + oauth[1][1] + "&" + oauth[2][0] + "=" +
		 * oauth[2][1] + "&" + oauth[3][0] + "=" + oauth[3][1] + "&" +
		 * oauth[4][0] + "=" + oauth[4][1] + "&" + oauth[5][0] + "=" +
		 * oauth[5][1] + "&" + oauth[7][0] + "=" + oauth[7][1] + "&" +
		 * oauth[8][0] + "=" + oauth[8][1]; System.out.println(header);
		 */
	}

	public static String set_basestring3() throws UnsupportedEncodingException {
		String bss;
		bss = "GET" + "&"
				+ URLEncoder.encode("http://emma.pixnet.cc/account", "utf-8")
				+ "&";
		String bsss = "oauth_callback="
				+ URLEncoder.encode(oauth[0][1], "utf-8")
				+ "&oauth_consumer_key=" + oauth[1][1] + "&oauth_nonce="
				+ oauth[2][1] + "&oauth_signature_method=" + oauth[3][1]
				+ "&oauth_timestamp=" + oauth[4][1] + "&oauth_token="
				+ oauth[5][1] + "&oauth_version=" + oauth[7][1];
		bsss = URLEncoder.encode(bsss, "utf-8");
		return bss + bsss;
	}

	public static String set_basestring2() throws UnsupportedEncodingException {
		String bss;
		bss = "GET"
				+ "&"
				+ URLEncoder.encode("http://emma.pixnet.cc/oauth/access_token",
						"utf-8") + "&";
		String bsss = "oauth_callback="
				+ URLEncoder.encode(oauth[0][1], "utf-8")
				+ "&oauth_consumer_key=" + oauth[1][1] + "&oauth_nonce="
				+ oauth[2][1] + "&oauth_signature_method=" + oauth[3][1]
				+ "&oauth_timestamp=" + oauth[4][1] + "&oauth_token="
				+ oauth[5][1] + "&oauth_verifier=" + oauth[6][1]
				+ "&oauth_version=" + oauth[7][1];
		bsss = URLEncoder.encode(bsss, "utf-8");
		return bss + bsss;
	}

	public String set_basestring() throws UnsupportedEncodingException {
		String bss;
		bss = "GET"
				+ "&"
				+ URLEncoder.encode(
						"http://emma.pixnet.cc/oauth/request_token", "utf-8")
				+ "&";
		String bsss = "oauth_callback=" + URLEncoder.encode("oob", "utf-8")
				+ "&" + oauth_consumer_key + "&" + oauth_nonce + "&"
				+ oauth_signature_method + "&" + oauth_timestamp + "&"
				+ oauth_version;
		bsss = URLEncoder.encode(bsss, "utf-8");
		return bss + bsss;
	}

	public static String get_signature(String data, String key) {
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
		return "oauth_signature=" + oauth;
	}

	public static String set_timestamp() {
		Date date = new Date();
		long time = date.getTime();
		return "oauth_timestamp=" + (time + "").substring(0, 10);
	}

	public static String set_nonce() {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 18; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return "oauth_nonce=" + sb.toString();
	}
}