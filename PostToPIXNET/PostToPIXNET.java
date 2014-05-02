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

public class PostToPIXNET {
	String oauth_callback = "oob";
	String oauth_consumer_key = "46857444204aa2ce0cb8c9666a8eadae";
	String oauth_nonce = "";
	String oauth_signature_method = "HMAC-SHA1";
	String oauth_timestamp = "";
	String oauth_token = "6f8fd46805b50912ec7e93734f269a47";
	String oauth_verifier = "";
	String oauth_version = "1.0";
	String oauth_signature = "";
	String oauth_token_secret = "c98a9526ba72302b7293843d7a3a5131";

	PostToPIXNET() throws Exception {
		request();
		access();
		// readUser();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("請輸入文章標題:");
		String title = in.readLine();
		System.out.print("請輸入文章內容:");
		String body = in.readLine();
		postArticle(title, body);
		// Thread.sleep(100);
		// readUser();
	}

	public void postArticle(String title, String body) throws Exception {
		try {
			oauth_nonce = set_nonce();
			oauth_timestamp = set_timestamp();
			oauth_signature = get_signature(
					set_baseForPostArticle(title, body),
					"dfa46d6dc4acfc8a25046fffcc5d9b14&"
							+ oauth_token_secret.replace("oauth_token_secret=",
									""));
			URL url = new URL("http://emma.pixnet.cc/blog/articles");
			// URLConnection conn = url.openConnection();
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestMethod("POST");
			String header = "oauth_consumer_key=\""
					+ URLEncoder.encode(oauth_consumer_key, "utf-8")
					+ "\","
					+ "oauth_nonce=\""
					+ URLEncoder.encode(oauth_nonce, "utf-8")
					+ "\","
					+ "oauth_signature_method=\""
					+ URLEncoder.encode(oauth_signature_method, "utf-8")
					+ "\","
					+ "oauth_timestamp=\""
					+ URLEncoder.encode(oauth_timestamp, "utf-8")
					+ "\","
					+ "oauth_token=\""
					+ URLEncoder.encode(oauth_token, "utf-8")
					+ "\","
					+ "oauth_version=\""
					+ URLEncoder.encode(oauth_version, "utf-8")
					+ "\","
					+ "oauth_signature=\""
					+ oauth_signature
							.substring(0, oauth_signature.length() - 2) + "\"";
			// System.out.println(header);
			// System.out.println(set_baseForPostArticle(title, body));
			con.setRequestProperty("Authorization", "OAuth " + header);
			// con.setRequestProperty("body", body);
			// con.setRequestProperty("title", title);
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
			// postArticle(title, body);
		}

	}

	public void readUser() throws Exception {
		URLConnection con = null;
		try {
			oauth_nonce = set_nonce();
			oauth_timestamp = set_timestamp();
			// System.out.println(set_baseForUserInfo());
			oauth_signature = get_signature(set_baseForUserInfo(),
					"dfa46d6dc4acfc8a25046fffcc5d9b14&" + oauth_token_secret);
			String header = "oauth_consumer_key=\""
					+ URLEncoder.encode(oauth_consumer_key, "utf-8")
					+ "\","
					+ "oauth_nonce=\""
					+ URLEncoder.encode(oauth_nonce, "utf-8")
					+ "\","
					+ "oauth_signature_method=\""
					+ URLEncoder.encode(oauth_signature_method, "utf-8")
					+ "\","
					+ "oauth_timestamp=\""
					+ URLEncoder.encode(oauth_timestamp, "utf-8")
					+ "\","
					+ "oauth_token=\""
					+ URLEncoder.encode(oauth_token, "utf-8")
					+ "\","
					+ "oauth_version=\""
					+ URLEncoder.encode(oauth_version, "utf-8")
					+ "\","
					+ "oauth_signature=\""
					+ oauth_signature
							.substring(0, oauth_signature.length() - 2) + "\"";
			// System.out.println("\""+oauth_signature.substring(0,
			// oauth_signature.length()-2)+"\"");
			// System.out.println(header);
			// System.out.println(set_baseForUserInfo());
			URL url = new URL("http://emma.pixnet.cc/account");
			con = url.openConnection();
			// con.setRequestMethod("GET");
			con.setRequestProperty("Authorization", "OAuth " + header);
			// con.setRequestProperty("Expect", "");
			con.setDoOutput(true);
			// System.out.println(con.getHeaderField("Authorization"));
			con.connect();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream(), "UTF-8"));
			System.out.println(br.readLine());
			/* URLConnection conn = url.openConnection(); */

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void access() throws Exception {
		try {
			oauth_nonce = set_nonce();
			oauth_timestamp = set_timestamp();
			oauth_signature = get_signature(set_baseForAccess(),
					"dfa46d6dc4acfc8a25046fffcc5d9b14&" + oauth_token_secret);
			// System.out.println(oauth_token_secret);
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
			// System.out.println(accessUrl);
			String[] accToken = accessUrl.split("&");
			oauth_token = accToken[0].replace("oauth_token=", "");
			oauth_token_secret = accToken[1].replace("oauth_token_secret=", "");
			// System.out.println("oauth_token:" + oauth_token
			// + "\noauth_token_secret:" + oauth_token_secret);
		} catch (IOException e) {
			access();
		}
	}

	public void request() throws Exception {
		try {
			oauth_nonce = set_nonce();
			oauth_timestamp = set_timestamp();
			oauth_signature = get_signature(set_baseForRequest(),
					"dfa46d6dc4acfc8a25046fffcc5d9b14&");
			String header = "http://emma.pixnet.cc/oauth/request_token?"
					+ "oauth_callback=" + oauth_callback + "&"
					+ "oauth_consumer_key=" + oauth_consumer_key + "&"
					+ "oauth_nonce=" + oauth_nonce + "&"
					+ "oauth_signature_method=" + oauth_signature_method + "&"
					+ "oauth_timestamp=" + oauth_timestamp + "&"
					+ "oauth_version=" + oauth_version + "&"
					+ "oauth_signature=" + oauth_signature;
			// System.out.println(header);
			URL url = new URL(header);
			//
			URLConnection conn = url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String authUrl = br.readLine();
			// System.out.println(authUrl);
			String[] reqToken = authUrl.split("&");
			oauth_token = reqToken[0].replace("oauth_token=", "");
			oauth_token_secret = reqToken[1].replace("oauth_token_secret=", "");
			reqToken[4] = URLDecoder.decode(reqToken[4], "utf-8");
			String[] token = reqToken[4].split("request_auth_url=");
			System.out.println("取得授權碼；" + token[1]);
			BufferedReader uin = new BufferedReader(new InputStreamReader(
					System.in));
			System.out.print("輸入授權碼；");
			oauth_verifier = uin.readLine();
		} catch (IOException e) {
			request();
			// e.printStackTrace();
		}

	}

	public static void main(String[] args) throws Exception {
		new PostToPIXNET();
	}

	public String set_baseForPostArticle(String title, String body)
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

	public String set_baseForUserInfo() throws UnsupportedEncodingException {
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

	public String set_baseForAccess() throws UnsupportedEncodingException {
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

	public String set_baseForRequest() throws UnsupportedEncodingException {
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
		return oauth;
	}

	public static String set_timestamp() {
		Date date = new Date();
		long time = date.getTime();
		return (time + "").substring(0, 10);
	}

	public static String set_nonce() {
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