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
import android.os.IBinder;
import android.view.View;
import android.widget.RemoteViews;
import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kannur.kalolsavam.R;
import com.kannur.kalolsavam.app.AppController;
import com.kannur.kalolsavam.app.Constants;
import com.kannur.kalolsavam.app.Utilities;

public class RefreshService extends Service {

	private RemoteViews views;
	private ComponentName cn;
	private final String tag = "refresh_req_gold";
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		views = new RemoteViews(getApplicationContext().getPackageName(),
				R.layout.widget_layout);
		cn = new ComponentName(getApplicationContext(), WidgetProvider.class);
		views.setViewVisibility(R.id.btn_refresh, View.GONE);
		views.setViewVisibility(R.id.progress_bar, View.VISIBLE);
		AppWidgetManager.getInstance(getApplicationContext()).updateAppWidget(
				cn, views);
		loadScore();
		
		return super.onStartCommand(intent, flags, startId);
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		AppController.getInstance().cancelPendingRequests(tag);
	}
	private void loadScore() {
		
		// final String webSurl =
		// "http://www.itschoolekmap.in/APPS/KALOL/router.php";
		final String webSurl = "http://192.168.199.1/test/getjson.php";
		StringRequest req = new StringRequest(Method.POST, webSurl,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						views.setViewVisibility(R.id.progress_bar, View.GONE);
						views.setViewVisibility(R.id.btn_refresh, View.VISIBLE);
						if (response != null) {

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

							} catch (JSONException e) {
							} catch (Exception e) {
							}
						}
						AppWidgetManager.getInstance(
								getApplicationContext())
								.updateAppWidget(cn, views);
						stopSelf();
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						views.setViewVisibility(R.id.progress_bar, View.GONE);
						views.setViewVisibility(R.id.btn_refresh, View.VISIBLE);
						AppWidgetManager.getInstance(
								getApplicationContext())
								.updateAppWidget(cn, views);
						stopSelf();
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
