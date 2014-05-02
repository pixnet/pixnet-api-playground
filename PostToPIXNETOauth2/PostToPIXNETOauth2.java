import java.beans.Encoder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

public class PostToPIXNETOauth2 {
	String url = "https://emma.pixnet.cc/oauth2/authorize?client_id=46857444204aa2ce0cb8c9666a8eadae&redirect_uri=http://oob&response_type=code";
	String code = "37e66182143c34c357a388b1f56556b961e6512f";
	String access_token = "";

	PostToPIXNETOauth2() throws IOException, JSONException {
		grant();

		post();
	}

	public static void main(String args[]) throws IOException, JSONException {
		new PostToPIXNETOauth2();
	}

	void post() throws IOException {
		Scanner in = new Scanner(System.in, "UTF-8");
		System.out.print("請輸入標題:");
		String title = "安安";
		System.out.print("請輸入內容:");
		String body = "掰掰";
		// System.out.println();
		// System.out.println(title);
		URL urlPost = new URL(
				"https://emma.pixnet.cc/blog/articles?access_token="
						+ access_token + "&body="
						+ URLEncoder.encode(body, "UTF-8") + "&title="
						+ URLEncoder.encode(title, "UTF-8"));
		// System.out.println(urlPost.toString());
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
