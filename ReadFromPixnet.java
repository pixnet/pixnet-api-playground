import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class ReadFromPixnet {
	public static void main(String args[]) {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try {
			// get json from url
			String url = in.readLine();
			String[] token = url.split(",");
			url = "http://emma.pixnet.cc/blog/articles/" + token[0] + "?user="
					+ token[1] + "&format=json";
			URL pageurl = new URL(url);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					pageurl.openStream(), "UTF-8"));
			String json = "";
			json = br.readLine();
			// parse json
			JSONObject obj = new JSONObject(json);
			obj = new JSONObject(obj.get("article").toString());
			System.out.println(obj.get("body"));
		} catch (IOException e) {
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
