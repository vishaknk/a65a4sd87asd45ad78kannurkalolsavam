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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
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
import java.util.Arrays;
import java.util.List;

/**
 * Created by Priyesh on 20-02-2016.
 */
public class ItemsByIndividualLeader extends AppCompatActivity {

    private Spinner spinner;
    private int mPositon =0;
    private List<String> list, codes;
    private ListView mList;
    String[] titl,urls;
    private String tag = "result_by_item";
    ResultAdapter mExcelAdapter;
    ArrayList<ResultModelClass> mExcelModel = new ArrayList<ResultModelClass>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Item Results");
        setContentView(R.layout.activity_item_result);
        spinner= (Spinner) findViewById(R.id.sv_bible_books);
        mList= (ListView) findViewById(R.id.lv_bible_list);
        list = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.studentWisenames)));
        codes = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.studentwise)));
        getSupportActionBar().setTitle("Individual LeaderBoard");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ItemsByIndividualLeader.this
                ,R.layout.spinner_item,list);
         mExcelAdapter = new ResultAdapter(ItemsByIndividualLeader.this, mExcelModel);

        // Set the Adapter
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPositon = position;
                if (Utilities.isNetworkAvailable(ItemsByIndividualLeader.this)) {
                    mExcelModel.clear();
                    loadData(codes.get(position));
                }else {
                    Toast.makeText(ItemsByIndividualLeader.this,
                            "Please check your internet connection",
                            Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void loadData(String item) {

        String url = Constants.kannurbaseurl + "getprathibha/" + item;
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
                                    mList.setEmptyView(findViewById(android.R.id.empty));
                                } else {

                                    JSONObject object = new JSONObject(result);
                                    JSONArray resulArray = object.getJSONArray("data");
                                    Log.v("ar","-"+resulArray);
                                    if(resulArray.length() <= 0){
                                        Toast.makeText(ItemsByIndividualLeader.this,
                                                "No Results",
                                                Toast.LENGTH_SHORT).show();
                                        mList.setEmptyView(findViewById(android.R.id.empty));
                                        mExcelAdapter.notifyDataSetChanged();
                                        return;
                                    }
                                    mExcelModel.clear();
                                    for(int i = 0 ; i< resulArray.length(); i++){
                                        ResultModelClass resultModel = new ResultModelClass();
                                        JSONObject ob = new JSONObject();
                                        ob = resulArray.getJSONObject(i);

                                        resultModel.setCollegename(ob.getString("college_name"));
                                        resultModel.setName(ob.getString("name"));
                                        resultModel.setPoints(String.valueOf(ob.getString("points")));
                                        mExcelModel.add(resultModel);
                                    }
                                    if(resulArray.length() == 0){
                                        mList.setEmptyView(findViewById(android.R.id.empty));
                                    }
                                }
                            } catch (JSONException e) {

                            }

                            mList.setAdapter(mExcelAdapter);
                            mExcelAdapter.notifyDataSetChanged();
                        }else {
                            Toast.makeText(ItemsByIndividualLeader.this,
                                    "Cannot connect to the server. Please try again",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (pd.isShowing())
                    pd.dismiss();
                mList.setEmptyView(findViewById(android.R.id.empty));
            }
        });
        AppController.getInstance().addToRequestQueue(req, tag);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
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
                convertView = vi.inflate(R.layout.individual_borad_item, null);

                holder = new ViewHolder();
                holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
                holder.college = (TextView) convertView.findViewById(R.id.tv_college_name);
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


            holder.tvName.setText("Name : " + dataList.get(position).getName());
            holder.college.setText("College : " + dataList.get(position).getCollegeName());
            holder.tvId.setText("Points : " + dataList.get(position).getPoints());
            holder.slno.setText(String.valueOf(position + 1));
            //display the values

            return convertView;
        }
    }
}
