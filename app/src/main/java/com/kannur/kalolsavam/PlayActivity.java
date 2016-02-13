package com.kannur.kalolsavam;

import java.lang.reflect.InvocationTargetException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;

public class PlayActivity extends Activity {

	private VideoEnabledWebView webView;
	private VideoEnabledWebChromeClient webChromeClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Set layout
		setContentView(R.layout.activity_play);

		// Save the web view
		webView = (VideoEnabledWebView) findViewById(R.id.webView);

		// Initialize the VideoEnabledWebChromeClient and set event handlers
		View nonVideoLayout = findViewById(R.id.nonVideoLayout); // Your own
																	// view,
																	// read
																	// class
																	// comments
		ViewGroup videoLayout = (ViewGroup) findViewById(R.id.videoLayout); // Your
																			// own
																			// view,
																			// read
																			// class
																			// comments
		View loadingView = getLayoutInflater().inflate(
				R.layout.video_loading_progress, null); // Your own view, read
														// class comments
		webChromeClient = new VideoEnabledWebChromeClient(nonVideoLayout,
				videoLayout, loadingView, webView) // See all available
													// constructors...
		{
			// Subscribe to standard events, such as onProgressChanged()...
			@Override
			public void onProgressChanged(WebView view, int progress) {
				// Your code...
			}
		};
		webChromeClient
				.setOnToggledFullscreen(new VideoEnabledWebChromeClient.ToggledFullscreenCallback() {
					@SuppressLint("NewApi")
					@Override
					public void toggledFullscreen(boolean fullscreen) {
						// Your code to handle the full-screen change, for
						// example showing and hiding the title bar. Example:
						if (fullscreen) {
							WindowManager.LayoutParams attrs = getWindow()
									.getAttributes();
							attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
							attrs.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
							getWindow().setAttributes(attrs);
							if (android.os.Build.VERSION.SDK_INT >= 14) {
								getWindow()
										.getDecorView()
										.setSystemUiVisibility(
												View.SYSTEM_UI_FLAG_LOW_PROFILE);
							}
						} else {
							WindowManager.LayoutParams attrs = getWindow()
									.getAttributes();
							attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
							attrs.flags &= ~WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
							getWindow().setAttributes(attrs);
							if (android.os.Build.VERSION.SDK_INT >= 14) {
								getWindow().getDecorView()
										.setSystemUiVisibility(
												View.SYSTEM_UI_FLAG_VISIBLE);
							}
						}

					}
				});
		webView.setWebChromeClient(webChromeClient);

		// Navigate everywhere you want, this classes have only been tested on
		// YouTube's mobile site
		// webView.loadUrl("http://go8pm.com/video/8491_Kutty-Pattalam-12-05-2014--Kutty-Pattalam-12th-May-2014--Kutty-Pattalam-120514.html");
		// webView.loadUrl("http://go8pm.com/android_video.php?vid=8340");
		webView.loadUrl(getIntent().getStringExtra("url"));
		
	}

	@Override
	public void onBackPressed() {
		// Notify the VideoEnabledWebChromeClient, and handle it ourselves if it
		// doesn't handle it
		if (!webChromeClient.onBackPressed()) {
			if (webView.canGoBack()) {
				webView.goBack();

			} else {
				// Close app (presumably)
				try {
					Class.forName("android.webkit.WebView")
							.getMethod("onPause", (Class[]) null)
							.invoke(webView, (Object[]) null);

				} catch (ClassNotFoundException cnfe) {

				} catch (NoSuchMethodException nsme) {

				} catch (InvocationTargetException ite) {

				} catch (IllegalAccessException iae) {

				}
				super.onBackPressed();

			}
		}

	}

}
