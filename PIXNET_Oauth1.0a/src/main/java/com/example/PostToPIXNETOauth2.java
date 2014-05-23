import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * PIXNET API Oauth2.0
 * 
 * @author Koi
 * @version 1.0.8
 */
public class PostToPIXNETOauth2 {
	private String access_token = "";
	private String client_id = "";
	private String client_secret = "";
	private String redirect_uri = "";

	/**
	 * Constructor
	 * 
	 * @param client_id
	 *            your client id
	 * @param client_secret
	 *            your client secret
	 * @param redirect_uri
	 *            your redirect url
	 * @throws IOException
	 * @throws JSONException
	 */
	public PostToPIXNETOauth2(String client_id, String client_secret,
			String redirect_uri) throws IOException, JSONException {
		this.client_id = client_id;
		this.client_secret = client_secret;
		this.redirect_uri = redirect_uri;
	}

	public static void main(String args[]) throws IOException, JSONException {

	}

	/**
	 * The url that can auth and return the code for accesstoken
	 * @return RequestUrl
	 */
	public String getRequestUrl() {
		String url = "https://emma.pixnet.cc/oauth2/authorize?client_id="
				+ client_id + "&redirect_uri=" + redirect_uri
				+ "&response_type=code";
		return url;
	}

	private String post(String access_token) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in,
				"big5"));
		System.out.print("Title:");
		String title = in.readLine();
		System.out.print("Body:");
		String body = in.readLine();
		URL urlPost = new URL(
				"https://emma.pixnet.cc/blog/articles?access_token="
						+ access_token + "&body="
						+ URLEncoder.encode(body, "UTF-8") + "&title="
						+ URLEncoder.encode(title, "UTF-8")); //
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
		return br.readLine();
	}

	/**
	 * 
	 * @param code
	 * @return Set code and Get AccessToken
	 * @throws IOException
	 * @throws JSONException
	 */
	public String getAccessToken(String code) throws IOException, JSONException {
		String urlgrant = "https://emma.pixnet.cc/oauth2/grant?grant_type=authorization_code&code="
				+ code
				+ "&redirect_uri="
				+ redirect_uri
				+ "&client_id="
				+ client_id + "&client_secret=" + client_secret;
		URL url = new URL(urlgrant);
		URLConnection conn = url.openConnection();
		BufferedReader br = new BufferedReader(new InputStreamReader(
				conn.getInputStream(), "UTF-8"));
		String json = br.readLine();
		JSONObject obj = new JSONObject(json);
		access_token = (String) obj.get("access_token");
		return access_token;
	}
}
