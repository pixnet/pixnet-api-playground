package com.pixnet.pixnetapi;

import java.io.IOException;

import org.json.JSONException;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.pixnet.*;

public class MainActivity extends Activity {
	PostToPIXNETOauth2 ptp;
	TextView show;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		try {
			ptp = new PostToPIXNETOauth2("e6a0fa232cc4da68ae21b727b772229e",
					"fac5c7f719feef5eea37449b1fc6b2ad", "http://oob");
			sendMessage();
		} catch (IOException e) {

		} catch (JSONException e) {

		}

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	private void sendMessage() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				show = (TextView) findViewById(R.id.show);
				System.out.println(ptp.getRequestUrl());
				Message msg = new Message();
				Bundle se = new Bundle();
				se.putString("in", ptp.getRequestUrl());
				msg.setData(se);
				mHandler.sendMessage(msg);
			}
		}).start();
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			show.setText(msg.getData().getString("in"));

		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
