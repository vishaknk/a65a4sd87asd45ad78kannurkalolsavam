package com.kannur.kalolsavam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.kannur.kalolsavam.app.AppController;
import com.kannur.kalolsavam.app.Constants;
import com.kannur.kalolsavam.app.Utilities;

public class Search extends ActionBarActivity {
	private ListView listView;
	private ArrayList<Search_const> points;
	private SearchAdapter adapter;
	private String id = "";
	private String tag = "tag_req_search";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_main);
		listView = (ListView) findViewById(R.id.listview);
		id = getIntent().getStringExtra("id");
		points = new ArrayList<Search_const>();
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		if (Utilities.isNetworkAvailable(Search.this)) {
//			loadData();
			listView.setEmptyView(findViewById(android.R.id.empty));
		} else {
			Toast.makeText(Search.this,
					"No Network Connection\n Try again later!",
					Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
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
		final ProgressDialog pd = new ProgressDialog(this);
		pd.setMessage(getResources().getString(R.string.loading_message));
		pd.show();
		StringRequest req = new StringRequest(Method.POST, Constants.webSurl,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String result) {
						if (pd.isShowing())
							pd.dismiss();
						if (result != null) {
							try {
								if (result.contains("\"resp\"")) {
									listView.setEmptyView(findViewById(android.R.id.empty));
								} else {
									JSONArray ar = new JSONArray(result);
									for (int i = 0; i < ar.length(); i++) {
										JSONObject jo = ar.getJSONObject(i);
										points.add(new Search_const(jo.getString("pid"), jo
												.getString("Pname"), jo.getString("class"),
												jo.getString("sname"), jo
														.getString("iname"), jo
														.getString("rank"), jo
														.getString("grade"), jo
														.getString("point")));
									}
									adapter = new SearchAdapter(Search.this, points);
									listView.setAdapter(adapter);

								}

							} catch (JSONException e) {
								e.printStackTrace();
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {

							Toast.makeText(Search.this,
									"Cannot connect to the server. Please try again",
									Toast.LENGTH_SHORT).show();
						}
						listView.setEmptyView(findViewById(android.R.id.empty));
				}}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						if (pd.isShowing())
							pd.dismiss();
						listView.setEmptyView(findViewById(android.R.id.empty));

					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("type", "search");
				params.put("id", id);
				return params;
			}
		};
		AppController.getInstance().addToRequestQueue(req, tag);
	}
}
