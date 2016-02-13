package com.kannur.kalolsavam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kannur.kalolsavam.app.AppController;
import com.kannur.kalolsavam.app.Constants;
import com.kannur.kalolsavam.app.Utilities;


public class LeadingDist extends ActionBarActivity{
	private ListView listView;
	private ArrayList<Points> points;
	private CustomAdapter adapter;
	private final String tag = "tag_req_gold";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		getSupportActionBar().setTitle("Leading Districts");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.gold_main);
		listView = (ListView) findViewById(R.id.listview);
		new ArrayList<Points>();
		points = new ArrayList<Points>();
		listView.setEmptyView(findViewById(android.R.id.empty));
		if (Utilities.isNetworkAvailable(LeadingDist.this)) {
			loadData();
		} else {
			Toast.makeText(LeadingDist.this,
					"No Network Connection\n Try again later!",
					Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		AppController.getInstance().cancelPendingRequests(tag);
	}
	private void loadData() {
		final ProgressDialog pd = new ProgressDialog(LeadingDist.this);
		pd.setMessage(getResources().getString(R.string.loading_message));
		pd.show();		
		StringRequest req = new StringRequest(Request.Method.POST, Constants.webSurl,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String result) {
						if (pd.isShowing())
							pd.dismiss();
						if (result != null) {
							try {
								JSONArray ar = new JSONArray(result);
								for (int i = 0; i < ar.length(); i++) {
									JSONObject jo = ar.getJSONObject(i);
									points.add(new Points(i + 1, jo
											.getString("District"), jo
											.getString("Point")));

								}
								points.size();
								adapter = new CustomAdapter(LeadingDist.this,
										points);
								listView.setAdapter(adapter);

							} catch (JSONException e) {

								e.printStackTrace();
							} catch (Exception e) {

								e.printStackTrace();
							}
						} else {
							Toast.makeText(
									LeadingDist.this,
									"Cannot connect to the server. Please try again",
									Toast.LENGTH_SHORT).show();
						}

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						if (pd.isShowing())
							pd.dismiss();
						Toast.makeText(
								LeadingDist.this,
								"Cannot connect to the server. Please try again",
								Toast.LENGTH_SHORT).show();
					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("type", "gold");
				return params;
			}
		};
		AppController.getInstance().addToRequestQueue(req, tag);
	}
}
