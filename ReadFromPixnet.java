import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.json.JSONException;
import org.json.JSONObject;

public class ReadFromPixnet extends JApplet {
	JLabel jl = new JLabel("文章來源網址:");
	JLabel jl2 = new JLabel("文章內容:");
	JTextField jtf = new JTextField();
	JButton run = new JButton("取得!");
	JButton copy = new JButton("複製!");
	JTextArea jta = new JTextArea();

	public void init() {
		setVisible(true);
		setSize(600, 600);
		setLayout(null);
		int width = getWidth();
		int height = getHeight();
		jl.setBounds(0 * width, 0 * height / 10, 1 * width, height / 10);
		jl2.setBounds(0 * width, 2 * height / 10, 1 * width, height / 10);
		jtf.setBounds(0 * width, 1 * height / 10, 7 * width / 10, height / 10);
		run.setBounds(7 * width / 10, 1 * height / 10, 3 * width / 10,
				height / 10);
		jta.setBounds(0 * width, 3 * height / 10, 1 * width, 7 * height / 10);
		run.addActionListener(get);
		add(jl);
		add(jl2);
		add(jtf);
		add(run);
		add(jta);
	}

	private ActionListener get = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
		}

	};
	/*
	 * public static void main(String args[]) { BufferedReader in = new
	 * BufferedReader(new InputStreamReader(System.in)); try { // get json from
	 * url String url = in.readLine(); String[] token = url.split("/"); String[]
	 * arc = token[5].split("-"); String[] user = token[2].split("pixnet"); url
	 * = "http://emma.pixnet.cc/blog/articles/" + arc[0] + "?user=" +
	 * user[0].substring(0, user[0].length() - 1) + "&format=json"; URL pageurl
	 * = new URL(url); BufferedReader br = new BufferedReader(new
	 * InputStreamReader( pageurl.openStream(), "UTF-8")); String json = "";
	 * json = br.readLine(); // parse json JSONObject obj = new
	 * JSONObject(json); obj = new JSONObject(obj.get("article").toString());
	 * HtmlCleaner cleaner = new HtmlCleaner(); TagNode node =
	 * cleaner.clean(obj.get("body").toString() .replaceAll("&nbsp;",
	 * "").replaceAll("&ndash;", "-")); // Document doc =
	 * Jsoup.parse(obj.get("body").toString());
	 * System.out.println(node.getText()); } catch (IOException e) { } catch
	 * (JSONException e) { e.printStackTrace(); } }
	 */
}
