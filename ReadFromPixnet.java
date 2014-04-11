import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.json.JSONException;
import org.json.JSONObject;

public class ReadFromPixnet{
	public static void main(String args[]) {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try {
			// get json from url
			String url = in.readLine();
			String[] token = url.split("/");
			String[] arc = token[5].split("-");
			String[] user = token[2].split("pixnet");
			url = "http://emma.pixnet.cc/blog/articles/" + arc[0] + "?user="
					+ user[0].substring(0, user[0].length() - 1)
					+ "&format=json";
			URL pageurl = new URL(url);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					pageurl.openStream(), "UTF-8"));
			String json = "";
			json = br.readLine();
			// parse json
			JSONObject obj = new JSONObject(json);
			obj = new JSONObject(obj.get("article").toString());
			HtmlCleaner cleaner = new HtmlCleaner();
			TagNode node = cleaner.clean(obj.get("body").toString()
					.replaceAll("&nbsp;", "").replaceAll("&ndash;", "-"));
			// Document doc = Jsoup.parse(obj.get("body").toString());
			System.out.println(node.getText());
		} catch (IOException e) {
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
