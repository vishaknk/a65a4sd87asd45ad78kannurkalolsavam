package com.kannur.kalolsavam;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Priyesh on 20-02-2016.
 */
public class CategoryResults extends AppCompatActivity {
    ListView listView;
    private String tag = "result_by_item";
    ResultAdapter mExcelAdapter;
    ArrayList<ResultModelClass> mExcelModel = new ArrayList<ResultModelClass>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_cateogry_result);
        listView = (ListView) findViewById(R.id.listview);
        listView.setEmptyView(findViewById(android.R.id.empty));
        View header = getLayoutInflater().inflate(R.layout.hesader, null);
        listView.addHeaderView(header);
        mExcelAdapter = new ResultAdapter(CategoryResults.this, mExcelModel);

        if(getIntent().getStringExtra("category").equals("NRITHAM")){
            getSupportActionBar().setTitle("നൃത്തോത്സവം ");

        }else if(getIntent().getStringExtra("category").equals("SAHITHYAM")){
            getSupportActionBar().setTitle("സാഹിത്യോത്സവം");

        }else if(getIntent().getStringExtra("category").equals("SANGEETHAM")){
            getSupportActionBar().setTitle("സംഗീതോത്സവം ");

        }else if(getIntent().getStringExtra("category").equals("DRISHYAM")){
            getSupportActionBar().setTitle("ദൃശ്യ-നാടകോത്സവം ");

        }else if(getIntent().getStringExtra("category").equals("CHITHRAM")){
            getSupportActionBar().setTitle("ചിത്രോത്സവം");

        }else{
            getSupportActionBar().setTitle("Standings");
        }

        if (Utilities.isNetworkAvailable(this)) {
            loadData(getIntent().getStringExtra("category"));
        } else {
           Utilities.showToast(this,
                   "No Network Connection\n Try again later!");
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadData(String item) {
        String itemCode = "";
        if(item.equals("NRITHAM")){
            itemCode = "NRITHAM";

        }else if(item.equals("SAHITHYAM")){
            itemCode = "SAHITHYAM";

        }else if(item.equals("SANGEETHAM")){
            itemCode = "SANGEETHAM";

        }else if(item.equals("DRISHYAM")){
            itemCode = "DRISHYAM";

        }else if(item.equals("CHITHRAM")){
            itemCode = "CHITHRAM";

        }else
            itemCode = "";
        String url = Constants.kannurbaseurl + "getpoints/" + itemCode;
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
                                if (result.equals("")) {
                                    listView.setEmptyView(findViewById(android.R.id.empty));
                                } else {

                                    JSONObject object = new JSONObject(result);
                                    JSONArray resulArray = object.getJSONArray("data");
                                    Log.v("ar","-"+resulArray);
                                    if(resulArray.length() <= 0){
                                        Toast.makeText(CategoryResults.this,
                                                "No Results",
                                                Toast.LENGTH_SHORT).show();
                                        listView.setEmptyView(findViewById(android.R.id.empty));
                                        mExcelAdapter.notifyDataSetChanged();
                                        return;
                                    }
                                    mExcelModel.clear();
                                    for(int i = 0 ; i< resulArray.length(); i++){
                                        ResultModelClass resultModel = new ResultModelClass();
                                        JSONObject ob = new JSONObject();
                                        ob = resulArray.getJSONObject(i);

                                        resultModel.setCollegename(ob.getString("college_name"));
                                        resultModel.setPoints(String.valueOf(ob.getString("points")));
                                        resultModel.setRank(String.valueOf(ob.getInt("rank")));
                                        mExcelModel.add(resultModel);
                                    }
                                }
                            } catch (JSONException e) {

                            }

                            listView.setAdapter(mExcelAdapter);
                            mExcelAdapter.notifyDataSetChanged();
                        }else {
                            Toast.makeText(CategoryResults.this,
                                    "Cannot connect to the server. Please try again",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (pd.isShowing())
                    pd.dismiss();
                listView.setEmptyView(findViewById(android.R.id.empty));
            }
        });
        AppController.getInstance().addToRequestQueue(req, tag);
    }

    //adapter for populating the ListView
    private class ResultAdapter extends BaseAdapter {

        private List<ResultModelClass> originalList;
        private List<ResultModelClass> dataList;   // Values to be displayed

        public ResultAdapter(Context context, ArrayList<ResultModelClass> list) {
            super();
            this.dataList = new ArrayList<ResultModelClass>();
            this.dataList = list;
        }

        private class ViewHolder {
            TextView tvName;
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
                convertView = vi.inflate(R.layout.list_item_result_category, null);

                holder = new ViewHolder();
                holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
                holder.tvId = (TextView) convertView.findViewById(R.id.tv_id);
                holder.slno = (TextView) convertView.findViewById(R.id.slno);
                holder.mMainLayout = (LinearLayout) convertView.findViewById(R.id.main_layout);
                convertView.setTag(holder);

            } else
                holder = (ViewHolder) convertView.getTag();
            if(position% 2 == 0)
                holder.mMainLayout.setBackgroundColor(getResources().getColor(R.color.bg_outer_grey));
            else
                holder.mMainLayout.setBackgroundColor(getResources().getColor(R.color.bg_leading_dist));


            holder.tvName.setText("" + dataList.get(position).getCollegeName());
            holder.tvId.setText("" + dataList.get(position).getPoints());
            holder.slno.setText("" + dataList.get(position).getRank());
            //display the values

            return convertView;
        }
    }
}
