import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;
import org.json.JSONObject;

public class PostToPIXNETOauth2 {
	String url = "https://emma.pixnet.cc/oauth2/authorize?client_id=46857444204aa2ce0cb8c9666a8eadae&redirect_uri=http://oob&response_type=code";
	String code = "37e66182143c34c357a388b1f56556b961e6512f";
	String access_token = "";

	PostToPIXNETOauth2() throws IOException, JSONException {
		grant();
	}

	public static void main(String args[]) throws IOException, JSONException {
		new PostToPIXNETOauth2();
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
