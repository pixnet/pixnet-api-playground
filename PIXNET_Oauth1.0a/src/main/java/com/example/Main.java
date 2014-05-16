package com.example;

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
 * PostToPIXNETOauth2.0
 */
public class Main {
	String url = "https://emma.pixnet.cc/oauth2/authorize?client_id=46857444204aa2ce0cb8c9666a8eadae&redirect_uri=http://oob&response_type=code";
	String code = "";
	String access_token = "";
	/**
	 * Main
	 * @throws IOException
	 * @throws JSONException
	 */
	public Main() {
		try{
		grant();
		post();
		}catch(Exception e){
		}
	}
	/**
	 * Start of Program
	 * @param args
	 * @throws IOException
	 * @throws JSONException
	 */
	public static void main(String args[]) {
		new Main();
	}
	/**
	 * Function for Post
	 * @throws IOException
	 */
	public void post() throws IOException {
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
	/**
	 * Function for get AccessToken
	 * @throws IOException
	 * @throws JSONException
	 */
	public void grant() throws IOException, JSONException {
		System.out.println("Code:" + url);
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
	public int test(int a){
		return 10;
	}
}
