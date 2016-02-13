package com.kannur.kalolsavam;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.kannur.kalolsavam.app.AppController;
import com.kannur.kalolsavam.app.Constants;
import com.kannur.kalolsavam.app.Utilities;

public class WebViewKalol extends ActionBarActivity {
	WebView webView;
	String url = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webviewlayout);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		String title;
		if ((title = getIntent().getStringExtra("title")) == null) {
			title = getResources().getString(R.string.app_name);
		}
		getSupportActionBar().setTitle(title);
		webView = (WebView) findViewById(R.id.webview1);
		url = getIntent().getStringExtra("url");
		if (url.equalsIgnoreCase("http://www.reelax.in/webtv/")) {
			final Dialog dialog = new Dialog(WebViewKalol.this);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.custdialog);

			final Button ok = (Button) dialog.findViewById(R.id.okbtn);

			ok.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			WindowManager.LayoutParams wmlp = dialog.getWindow()
					.getAttributes();
			wmlp.gravity = Gravity.CENTER;

			dialog.getWindow().setBackgroundDrawable(
					new ColorDrawable(Color.parseColor("#fffff3")));
			dialog.setCanceledOnTouchOutside(true);
			dialog.show();

		}
		gotoPage();

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void gotoPage() {

		WebSettings webSettings = webView.getSettings();
		webSettings.setBuiltInZoomControls(true);
		webSettings.setJavaScriptEnabled(true);

		webView.setWebViewClient(new Callback()); // HERE IS THE MAIN CHANGE
		if (Utilities.isNetworkAvailable(WebViewKalol.this)) {
			webView.loadUrl(url);
		} else {

			TextView err = (TextView) findViewById(R.id.error);
			err.setVisibility(View.VISIBLE);

		}

	}

	private class Callback extends WebViewClient { // HERE IS THE MAIN CHANGE.

		ProgressDialog pd;

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			pd = new ProgressDialog(WebViewKalol.this);
			pd.setMessage("Loading... Please Wait");
			pd.show();
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			if (pd.isShowing())
				pd.dismiss();
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			if (pd.isShowing())
				pd.dismiss();
			webView.loadUrl("file:///android_asset/error.html");
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return (false);
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		webView.stopLoading();
	}
}
