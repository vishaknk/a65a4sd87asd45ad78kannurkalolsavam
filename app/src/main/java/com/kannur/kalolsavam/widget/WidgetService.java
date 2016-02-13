package com.kannur.kalolsavam.widget;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;import com.kannur.kalolsavam.R;
import com.kannur.kalolsavam.app.AppController;
import com.kannur.kalolsavam.app.Constants;
import com.kannur.kalolsavam.app.Utilities;

public class WidgetService extends Service {

	private final String tag = "serivce_req_gold";
	Handler mHandler;
	private final long DELAY = 10000;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		AppController.getInstance().cancelPendingRequests(tag);
		mHandler.removeCallbacks(mRunnable);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		mHandler = new Handler();
		mHandler.post(mRunnable);
		return super.onStartCommand(intent, flags, startId);
	}

	Runnable mRunnable = new Runnable() {

		@Override
		public void run() {
			loadScore();

		}
	};

	private void loadScore() {

		// final String webSurl =
		// "http://www.itschoolekmap.in/APPS/KALOL/router.php";
		final String webSurl = "http://192.168.199.1/test/getjson.php";
		StringRequest req = new StringRequest(Method.POST, webSurl,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {

						if (response != null) {
							RemoteViews views = new RemoteViews(
									getApplicationContext().getPackageName(),
									R.layout.widget_layout);
							String d1, d2, d3, s1, s2, s3;
							try {

								JSONArray result = new JSONArray(response);

								JSONObject jo = result.getJSONObject(0);
								d1 = jo.getString("sub_district_name");
								s1 = jo.getString("spoint");

								JSONObject jo1 = result.getJSONObject(1);
								d2 = jo1.getString("sub_district_name");
								s2 = jo1.getString("spoint");

								JSONObject jo2 = result.getJSONObject(2);
								d3 = jo2.getString("sub_district_name");
								s3 = jo2.getString("spoint");

								views.setTextViewText(R.id.leaddist1, d1);
								views.setTextViewText(R.id.leadscore1, s1);

								views.setTextViewText(R.id.leaddist2, d2);
								views.setTextViewText(R.id.leadscore2, s2);

								views.setTextViewText(R.id.leaddist3, d3);
								views.setTextViewText(R.id.leadscore3, s3);
								ComponentName cn = new ComponentName(
										getApplicationContext(),
										WidgetProvider.class);
								AppWidgetManager.getInstance(
										getApplicationContext())
										.updateAppWidget(cn, views);

							} catch (JSONException e) {
							} catch (Exception e) {
							}
						}
						mHandler.postDelayed(mRunnable, DELAY);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						mHandler.postDelayed(mRunnable, DELAY);
					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("type", "gold");
				return params;
			}
		};
		AppController.getInstance().addToRequestQueue(req, tag);
	}
}
