package com.kannur.kalolsavam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
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
public class StatusFest extends ActionBarActivity {


	private List<Fest> festList;
	private BaseAdapter adapter;
	private ListView listView;
	private String tag = "tag_req_statusfest";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.status_fest);
		listView = (ListView) findViewById(R.id.listView);
		listView.setEmptyView(findViewById(android.R.id.empty));
		festList = new ArrayList<Fest>();
		adapter = new StatAdapter();
		listView.setAdapter(adapter);
		loadData();
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
	class Fest {

		String fest_name;
		int total;
		int completed;
		int remaining;

		public Fest(String fest_name, int total, int completed) {
			this.fest_name = fest_name;
			this.total = total;
			this.completed = completed;
			this.remaining = total - completed;
		}
	}

	class StatAdapter extends BaseAdapter {

		ViewHolder holder;
		int colors_title[];
		int colors_sub[];

		public StatAdapter() {
			colors_title = new int[]{R.color.head1,R.color.head,R.color.head2};
			colors_sub = new int[]{R.color.sub1,R.color.sub,R.color.sub2};
		}
		@Override
		public int getCount() {
			return festList.size();
		}

		@Override
		public Object getItem(int index) {
			return festList.get(index);
		}

		@Override
		public long getItemId(int id) {
			return id;
		}

		@Override
		public View getView(int pos, View mView, ViewGroup arg2) {

			if (mView == null) {
				LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
				mView = inflater.inflate(R.layout.row_status_fest, null);
				holder = new ViewHolder();
				holder.title = (TextView) mView
						.findViewById(R.id.status_fest_title);
				holder.total = (TextView) mView
						.findViewById(R.id.txt_item_total);
				holder.completed = (TextView) mView
						.findViewById(R.id.txt_item_completed);
				holder.remaining = (TextView) mView
						.findViewById(R.id.txt_item_to_complete); 
				holder.bg = (LinearLayout) mView.findViewById(R.id.status_fest_sub);
				mView.setTag(holder);

			} else {
				holder = (ViewHolder) mView.getTag();
			}
			holder.title.setBackgroundResource(colors_title[pos%3]);
			holder.title.setText(festList.get(pos).fest_name);
			holder.total.setText("" + festList.get(pos).total);
			holder.completed.setText("" + festList.get(pos).completed);
			holder.remaining.setText("" + festList.get(pos).remaining);
			return mView;
		}

		class ViewHolder {
			TextView title, total, completed, remaining;
			LinearLayout bg;
		}
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
								JSONArray ar = new JSONArray(result);
								JSONArray totals = ar.getJSONArray(0);
								JSONArray completed = ar.getJSONArray(1);
								for (int i = 0; i < totals.length(); i++) {
									JSONObject t = totals.getJSONObject(i);
									JSONObject c = completed.getJSONObject(i);
									Fest fest = new Fest(t.getString("fest_name"),
											t.getInt("cnt"), c.getInt("pcode"));
									festList.add(fest);
								}
								adapter.notifyDataSetChanged();

							} catch (JSONException e) {
								e.printStackTrace();
							} catch (Exception e) {
								e.printStackTrace();
							} 
						} else {
							Toast.makeText(StatusFest.this,
									"Cannot connect to the server. Please try again", Toast.LENGTH_SHORT)
									.show();
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
				params.put("type", "status_fest");
				return params;
			}
		};
		AppController.getInstance().addToRequestQueue(req, tag);
	}
}
