import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

public class PostToPIXNETOauth2 {
	String url = "https://emma.pixnet.cc/oauth2/authorize?client_id=46857444204aa2ce0cb8c9666a8eadae&redirect_uri=http://oob&response_type=code";
	String code = "";
	String access_token = "";

	PostToPIXNETOauth2() throws IOException, JSONException {
		grant();
		post();
	}

	public static void main(String args[]) throws IOException, JSONException {
		new PostToPIXNETOauth2();
	}

	void post() throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in,
				"big5"));
		System.out.print("請輸入標題:");
		String title = in.readLine();
		System.out.print("請輸入內容:");
		String body = in.readLine();
		// System.out.println();
		// System.out.println(title + body);

		URL urlPost = new URL(
				"https://emma.pixnet.cc/blog/articles?access_token="
						+ access_token + "&body="
						+ URLEncoder.encode(body, "UTF-8") + "&title="
						+ URLEncoder.encode(title, "UTF-8")); //
		System.out.println(urlPost.toString());
		HttpURLConnection con = (HttpURLConnection) urlPost.openConnection();
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		con.setRequestMethod("POST");
		con.connect();
		BufferedReader br = new BufferedReader(new InputStreamReader(
				con.getInputStream(), "UTF-8"));
		System.out.println(br.readLine());
	}

	void grant() throws IOException, JSONException {
		System.out.println("取得Code:" + url);
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		code = in.readLine();
		String urlgrant = "https://emma.pixnet.cc/oauth2/grant?grant_type=authorization_code&code="
				+ code
				+ "&redirect_uri=http://oob&client_id=46857444204aa2ce0cb8c9666a8eadae&client_secret=dfa46d6dc4acfc8a25046fffcc5d9b14";
		URL url = new URL(urlgrant);
		URLConnection conn = url.openConnection();
		BufferedReader br = new BufferedReader(new InputStreamReader(
				conn.getInputStream(), "UTF-8"));
		String json = br.readLine();
		JSONObject obj = new JSONObject(json);
		access_token = (String) obj.get("access_token");
	}
}
