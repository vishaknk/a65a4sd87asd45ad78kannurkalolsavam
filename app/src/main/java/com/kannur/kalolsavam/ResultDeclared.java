package com.kannur.kalolsavam;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TableRow;
import android.widget.TextView;

import com.kannur.kalolsavam.app.AppController;
import com.kannur.kalolsavam.app.Constants;
import com.kannur.kalolsavam.app.Utilities;

public class ResultDeclared extends Fragment {
	TableRow rowItems;
	TableRow rowTitle;
	TableRow.LayoutParams params;
	WebView webView;
	String url = "";
	View v;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		v = inflater.inflate(R.layout.tabless, container, false);
		webView = (WebView) v.findViewById(R.id.webView1);
		if (GeneralTab.chchk.equalsIgnoreCase("hsgeneral")) {
			url = Constants.hsgen;
		} else if (GeneralTab.chchk.equalsIgnoreCase("hsarabic")) {
			url = Constants.hsara;
		} else if (GeneralTab.chchk.equalsIgnoreCase("hssan")) {
			url = Constants.hssan;
		} else {
			url = Constants.hssgen;
		}
		if (Utilities.isNetworkAvailable(getActivity())) {
			gotoPage();
		} else {
			TextView err = (TextView) v.findViewById(R.id.empty);
			err.setText("Error Occured! Please try again");
			err.setVisibility(View.VISIBLE);

		}
		return v;

	}

	@SuppressLint("SetJavaScriptEnabled")
	private void gotoPage() {

		WebSettings webSettings = webView.getSettings();
		webSettings.setBuiltInZoomControls(true);
		webSettings.setJavaScriptEnabled(true);

		webView.setWebViewClient(new Callback());

		webView.loadUrl(url);

	}

	private class Callback extends WebViewClient {

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			v.findViewById(R.id.empty).setVisibility(View.VISIBLE);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			v.findViewById(R.id.empty).setVisibility(View.INVISIBLE);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {

			super.onReceivedError(view, errorCode, description, failingUrl);
			webView.loadUrl("file:///android_asset/error.html");
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return (false);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		webView.stopLoading();
	}
}
