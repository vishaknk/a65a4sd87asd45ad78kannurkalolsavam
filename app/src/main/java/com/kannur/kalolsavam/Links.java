package com.kannur.kalolsavam;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kannur.kalolsavam.app.AppController;
import com.kannur.kalolsavam.app.Constants;
import com.kannur.kalolsavam.app.Utilities;

public class Links extends ActionBarActivity {
	private String[] title;
	private String[] urls;
	private ListView lv;
	private String tag = "tag_req_links";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.link_main);
		getSupportActionBar().setTitle("Links");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		lv = (ListView) findViewById(R.id.listViewss);

		if (Utilities.isNetworkAvailable(Links.this)) {
			getData();
		}
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				Intent webint = new Intent(Links.this, WebViewKalol.class);
				webint.putExtra("url", urls[arg2]);
				startActivity(webint);

			}
		});
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
	private void getData() {
		final ProgressDialog pd = new ProgressDialog(this);
		pd.setMessage(getResources().getString(R.string.loading_message));
		pd.show();
		StringRequest req = new StringRequest(Constants.linkurl,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String result) {
						if (pd.isShowing())
							pd.dismiss();
						if (result != null) {
							try {
								if (result.equals("")) {
									lv.setEmptyView(findViewById(android.R.id.empty));
								} else {
									JSONArray ar = new JSONArray(result);
									System.out.println(ar.length());
									title = new String[ar.length()];
									urls = new String[ar.length()];
									for (int i = 0; i < ar.length(); i++) {
										JSONObject jo = ar.getJSONObject(i);
										title[i] = jo.getString("title");
										urls[i] = jo.getString("url");

									}
									lv.setAdapter(new LinkAdapter(Links.this, title, urls));
								}

							} catch (JSONException e) {

								e.printStackTrace();
							} catch (Exception e) {

								e.printStackTrace();
							}
						} else {
							Toast.makeText(Links.this,
									"Cannot connect to the server. Please try again",
									Toast.LENGTH_SHORT).show();
						}

						lv.setEmptyView(findViewById(android.R.id.empty));

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						if (pd.isShowing())
							pd.dismiss();
						lv.setEmptyView(findViewById(android.R.id.empty));

					}
				});
		AppController.getInstance().addToRequestQueue(req, tag);
	}
}
