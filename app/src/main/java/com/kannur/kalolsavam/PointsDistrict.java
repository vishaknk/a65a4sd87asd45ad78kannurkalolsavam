package com.kannur.kalolsavam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kannur.kalolsavam.app.AppController;
import com.kannur.kalolsavam.app.Constants;
import com.kannur.kalolsavam.app.Utilities;

public class PointsDistrict extends Fragment {
	private String tag = "tag_req_pointdistrict";
	
	private ListView listView;
	private ArrayList<Points> points;
	private PointsAdapter adapter;
	private Context context;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		View v = inflater.inflate(R.layout.listview_table, container, false);
		TextView tv = (TextView) v.findViewById(R.id.txtDistrict); 
		tv.setText("DISTRICT");
		listView = (ListView) v.findViewById(R.id.listview);
		context = getActivity();
		listView.setEmptyView(v.findViewById(android.R.id.empty));
		if (Utilities.isNetworkAvailable(getActivity())) {
			points = new ArrayList<Points>();
			loadData();
		} else {
			Toast.makeText(getActivity(),
					"No Network Connection\n Try again later!",
					Toast.LENGTH_SHORT).show();
		}
		return v;
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		AppController.getInstance().cancelPendingRequests(tag);
	}
	private void loadData() {
		final ProgressDialog pd = new ProgressDialog(getActivity());
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
								JSONArray ar = new JSONArray(result);
								for (int i = 0; i < ar.length(); i++) {
									JSONObject jo = ar.getJSONObject(i);
									points.add(new Points(i + 1, jo
											.getString("District"), jo
											.getString("Point")));
								}
								adapter = new PointsAdapter(context, points);
								listView.setAdapter(adapter);

							} catch (JSONException e) {
								e.printStackTrace();
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							Toast.makeText(
									getActivity(),
									"Cannot connect to the server. Please try again",
									Toast.LENGTH_SHORT).show();
						}

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						if (pd.isShowing())
							pd.dismiss();

					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("type", GeneralTab.chchk);
				params.put("meth", "District");
				return params;
			}
		};
		AppController.getInstance().addToRequestQueue(req, tag);
	}
}
