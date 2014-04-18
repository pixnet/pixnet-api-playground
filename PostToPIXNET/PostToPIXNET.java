import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class PostToPIXNET {
	static String[][] oauth = { { "oauth_callback", "oob" },
			{ "oauth_consumer_key", "46857444204aa2ce0cb8c9666a8eadae" },
			{ "oauth_nonce", "" }, { "oauth_signature_method", "HMAC-SHA1" },
			{ "oauth_timestamp", "" }, { "oauth_version", "1.0" },
			{ "oauth_signature", "" } };

	public static void main(String[] args) throws Exception {
		// OAuthConsumer consumer = new DefaultOAuthConsumer(
		// "46857444204aa2ce0cb8c9666a8eadae",
		// "dfa46d6dc4acfc8a25046fffcc5d9b14");
		// OAuthProvider provider = new DefaultOAuthProvider(
		// "http://emma.pixnet.cc/oauth/request_token",
		// "http://emma.pixnet.cc/oauth/access_token",
		// "http://emma.pixnet.cc/authorize");
		// OAuthService service = new ServiceBuilder().provider(PIXNETApi.class)
		// .apiKey("46857444204aa2ce0cb8c9666a8eadae")
		// .apiSecret("dfa46d6dc4acfc8a25046fffcc5d9b14").build();
		// Token requestToken = service.getRequestToken();
		// System.out.println("(if your curious it looks like this: " +
		// requestToken + " )");
		// String authUrl = provider.retrieveRequestToken(consumer,
		// OAuth.OUT_OF_BAND);

		// System.out.println("Request token: " + consumer.getToken());
		// System.out.println("Token secret: " + consumer.getTokenSecret());
		oauth[2][1] = set_nonce();
		oauth[4][1] = set_timestamp();
		oauth[6][1] = hmacsha1(set_basestring(),
				"dfa46d6dc4acfc8a25046fffcc5d9b14&");
		// String urlstr = "oauth_callback=\"oob\"" + ",oauth_consumer_key=\""
		// + oauth[1][1] + "\",oauth_nonce=\"" + oauth[2][1]
		// + "\",oauth_signature_method=\"HMAC-SHA1\""
		// + ",oauth_signature=\""
		// + hmacsha1(ex(), "dfa46d6dc4acfc8a25046fffcc5d9b14")
		// + "\",oauth_timestamp=\"" + oauth[4][1]
		// + "\",oauth_version=\"1.0\"";
		String header = "http://emma.pixnet.cc/oauth/request_token?"+ oauth[0][0] + "=" + oauth[0][1] + "&"
				+ oauth[1][0] + "=" + oauth[1][1] + "&" + oauth[2][0] + "="
				+ oauth[2][1] + "&" + oauth[3][0] + "=" + oauth[3][1] + "&"
				+ oauth[4][0] + "=" + oauth[4][1] + "&" + oauth[5][0] + "="
				+ oauth[5][1] + "&" + oauth[6][0] + "=" + oauth[6][1];
		System.out.println(header);
		System.out.println(set_basestring());
		//header = oauth[1][0] + "=\"" + oauth[1][1] + "\"," + oauth[2][0]
		//		+ "=\"" + oauth[2][1] + "\"," + oauth[3][0] + "=\"" + oauth[3][1]
		//		+ "\"," + oauth[4][0] + "=\"" + oauth[4][1] + "\"," + oauth[5][0]
		//		+ "=\"" + oauth[5][1] + "\"," + oauth[6][0] + "=\"" + oauth[6][1]+"\"";
		//URL url = new URL("http://emma.pixnet.cc/oauth/request_token");
		//HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		//conn.setDoInput(true); // 設定為可從伺服器讀取資料
		//conn.setDoOutput(true); // 設定為可寫入資料至伺服器
		//conn.setRequestMethod("GET");
		//conn.setRequestProperty("Authorization", "OAuth " + header);
		//conn.connect();
		//String inputLine = "";
		//BufferedReader br = new BufferedReader(new InputStreamReader(
		//		conn.getInputStream(), "UTF-8"));
		//while ((inputLine = br.readLine()) != null) {
		//	System.out.println(inputLine); // 印出結果
		//}
		// System.out.println(urlstr);

	}

	public static String set_basestring() throws UnsupportedEncodingException {
		String bss;
		bss = "GET"
				+ "&"
				+ URLEncoder.encode(
						"http://emma.pixnet.cc/oauth/request_token", "utf-8")
				+ "&";
		String bsss = "oauth_callback="
				+ URLEncoder.encode(oauth[0][1], "utf-8")
				+ "&oauth_consumer_key=" + oauth[1][1] + "&oauth_nonce="
				+ oauth[2][1] + "&oauth_signature_method=" + oauth[3][1]
				+ "&oauth_timestamp=" + oauth[4][1] + "&oauth_version="
				+ oauth[5][1];
		bsss = URLEncoder.encode(bsss, "utf-8");
		return bss + bsss;
	}

	public static String hmacsha1(String data, String key) {
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