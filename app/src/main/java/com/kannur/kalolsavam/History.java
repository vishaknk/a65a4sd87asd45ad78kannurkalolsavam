package com.kannur.kalolsavam;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class History extends ActionBarActivity {
	WebView webView;
	String url = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.history);
		getSupportActionBar().setTitle("History");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		webView = (WebView) findViewById(R.id.webview2);
		url = "file:///android_asset/history.html";
		gotoPage();

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void gotoPage() {

		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);

		webView.setWebViewClient(new Callback());
		try {
			webView.loadUrl(url);
		} catch (Exception e) {

		}

	}

	private class Callback extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return (false);
		}

	}

}
