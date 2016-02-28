package com.kannur.kalolsavam;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
	ResultAdapter mExcelAdapter;
	TextView name, total ;
	private String tag = "tag_req_search";
	ArrayList<SearchMc> mExcelModel = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_main);
		listView = (ListView) findViewById(R.id.listview);
		name = (TextView) findViewById(R.id.tv_name);
		total = (TextView) findViewById(R.id.tv_college_name);
		mExcelAdapter = new ResultAdapter(Search.this, mExcelModel);
		id = getIntent().getStringExtra("id");
		points = new ArrayList<Search_const>();
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		if (Utilities.isNetworkAvailable(Search.this)) {
			loadData(id);
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
	private void loadData(String item) {

		String url = Constants.kannurbaseurl + "getstudentresult/" + item.replace(" ","").trim().toString();
		Log.v("url", "-" + url);
		final ProgressDialog pd = new ProgressDialog(this);
		pd.setMessage(getResources().getString(R.string.loading_message));
		pd.show();
		StringRequest req = new StringRequest(url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String result) {
						if (pd.isShowing())
							pd.dismiss();
						if (result != null) {
							try {
								if (result.equals("") ) {

								} else {

									JSONObject object = new JSONObject(result);
									JSONArray resulArray = object.getJSONArray("results");
									Log.v("ar", "-" + resulArray);
									if(resulArray.length() <= 0){
										Toast.makeText(Search.this,
												"No details",
												Toast.LENGTH_SHORT).show();
										listView.setEmptyView(findViewById(android.R.id.empty));
										mExcelAdapter.notifyDataSetChanged();
										return;
									}
									name.setText("Name : " + object.getString("name"));
									total.setText("Total Points : " + object.getString("total_points"));
									for(int i = 0 ; i< resulArray.length(); i++){
										SearchMc resultModel = new SearchMc();
										JSONObject ob = new JSONObject();
										ob = resulArray.getJSONObject(i);

										resultModel.setName(ob.getString("event_name"));
										resultModel.setgrade(ob.getString("grade"));
										resultModel.setposition(ob.getString("position"));
										mExcelModel.add(resultModel);
									}
									if(resulArray.length() == 0){
										listView.setEmptyView(findViewById(android.R.id.empty));
									}
								}
							} catch (JSONException e) {

							}

							listView.setAdapter(mExcelAdapter);
							mExcelAdapter.notifyDataSetChanged();
						}else {
							Toast.makeText(Search.this,
									"Cannot connect to the server. Please try again",
									Toast.LENGTH_SHORT).show();
						}

					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				if (pd.isShowing())
					pd.dismiss();
			}
		});
		AppController.getInstance().addToRequestQueue(req, tag);
	}

	//adapter for populating the ListView
	private class ResultAdapter extends BaseAdapter {

		private List<SearchMc> originalList;
		private List<SearchMc> dataList;   // Values to be displayed

		public ResultAdapter(Context context, ArrayList<SearchMc> list) {
			super();
			this.dataList = new ArrayList<SearchMc>();
			this.dataList = list;
		}

		private class ViewHolder {
			TextView tvName,college;
			TextView tvId,slno;
			LinearLayout mMainLayout;
		}

		@Override
		public int getCount() {
			return dataList.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;
			Log.v("ConvertView", String.valueOf(position));
			if (convertView == null) {

				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = vi.inflate(R.layout.list_item_college_leader, null);

				holder = new ViewHolder();
				holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
				holder.college = (TextView) convertView.findViewById(R.id.tv_college_name);
				holder.tvId = (TextView) convertView.findViewById(R.id.tv_id);
				holder.mMainLayout = (LinearLayout) convertView.findViewById(R.id.main_layout);
				convertView.setTag(holder);

			} else
				holder = (ViewHolder) convertView.getTag();
			holder.mMainLayout.setBackground(getResources().getDrawable(R.drawable.btn_drawable));


			holder.tvName.setText("Event Name : " + dataList.get(position).getName());
			holder.college.setText("Position : " + dataList.get(position).getposition());
			holder.tvId.setText("Grade : " + dataList.get(position).getGrade());
			//display the values

			return convertView;
		}
	}
}
