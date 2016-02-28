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


public class StandingsActivity extends ActionBarActivity{
	private ListView listView;
	private CustomAdapter adapter;
	private final String tag = "tag_req_gold";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		getSupportActionBar().setTitle("Standings");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.activity_standings);
		listView = (ListView) findViewById(R.id.listview);
		listView.setEmptyView(findViewById(android.R.id.empty));
		if (Utilities.isNetworkAvailable(StandingsActivity.this)) {
			loadData();
		} else {
			Toast.makeText(StandingsActivity.this,
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
		final ProgressDialog pd = new ProgressDialog(StandingsActivity.this);
		pd.setMessage(getResources().getString(R.string.loading_message));
		pd.show();		
		StringRequest req = new StringRequest(Request.Method.GET, Constants.kannurbaseurl+"getpoints/",
				new Response.Listener<String>() {

					@Override
					public void onResponse(String result) {
						if (pd.isShowing())
							pd.dismiss();
						if (result != null) {
							try {
								JSONObject object = new JSONObject(result);
								JSONArray feedArray = object.getJSONArray("data");
								for (int i = 0; i < feedArray.length(); i++) {
									JSONObject jo = feedArray.getJSONObject(i);

								}
								listView.setAdapter(adapter);

							} catch (JSONException e) {

								e.printStackTrace();
							} catch (Exception e) {

								e.printStackTrace();
							}
						} else {
							Toast.makeText(
									StandingsActivity.this,
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
								StandingsActivity.this,
								"Cannot connect to the server. Please try again",
								Toast.LENGTH_SHORT).show();
					}
				}) {
		};
		AppController.getInstance().addToRequestQueue(req, tag);
	}
}
