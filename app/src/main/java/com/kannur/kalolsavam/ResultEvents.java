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
 * Created by Priyesh on 28-02-2016.
 */
public class ResultEvents  extends AppCompatActivity {
    private Spinner spinner;
    private int mPositon =0;
    private List<String> list, codes;
    String[] titl,urls;
    private  TextView mFirstColege, mSecondColege, mThirdColege, mFirstStudents, mSecondStudents, mThirdStudents;
    private String tag = "result_by_item";
    ArrayList<String> firstname = new ArrayList<>();
    ArrayList<String> secondname = new ArrayList<>();
    ArrayList<String> thirdname = new ArrayList<>();
    ArrayList<String> firstcollege = new ArrayList<>();
    ArrayList<String> secondcollege = new ArrayList<>();
    ArrayList<String> thirdcollege= new ArrayList<>();
    ArrayList<ResultModelClass> mExcelModel = new ArrayList<ResultModelClass>();
    ArrayList<FirstMC> first = new ArrayList<FirstMC>();
    ArrayList<SecondMc> second = new ArrayList<SecondMc>();
    ArrayList<ThirdMc> third = new ArrayList<ThirdMc>();
    ArrayList<String> FirstFullData =  new ArrayList<>();
    ArrayList<String> SecondFullData =  new ArrayList<>();
    ArrayList<String> ThirdFullData =  new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Item Results");
        setContentView(R.layout.result_by_items);
        spinner= (Spinner) findViewById(R.id.sv_bible_books);
        list = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.itemNames)));
        codes = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.itemcodes)));
        getSupportActionBar().setTitle("Item Results");
        mFirstColege = (TextView) findViewById(R.id.tv_firstcollege);
        mSecondColege = (TextView) findViewById(R.id.tv_secondcollege);
        mThirdColege = (TextView) findViewById(R.id.tv_thirdcollege);
        mFirstStudents = (TextView) findViewById(R.id.tv_firststudents);
        mSecondStudents = (TextView) findViewById(R.id.tv_secondStudents);
        mThirdStudents = (TextView) findViewById(R.id.tv_thirdStudent);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ResultEvents.this
                ,R.layout.spinner_item,list);

        // Set the Adapter
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPositon = position;
                if (Utilities.isNetworkAvailable(ResultEvents.this)) {
                    loadData(codes.get(position));
                }else {
                    Toast.makeText(ResultEvents.this,
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

        String url = Constants.kannurbaseurl + "showeventresult/" + item;
        Log.v("url", "-" + url);
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage(getResources().getString(R.string.loading_message));
        pd.show();
        pd.setCanceledOnTouchOutside(false);
        StringRequest req = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String result) {

                        firstname.clear();
                        secondname.clear();
                        thirdname.clear();
                        firstcollege.clear();
                        secondcollege.clear();
                        thirdcollege.clear();
                        if (result != null) {
                            try {
                                if (result.equals("") ) {
                                } else {
                                    FirstMC firstMc = new FirstMC();
                                    SecondMc secondMc = new SecondMc();
                                    ThirdMc thirdMc = new ThirdMc();
                                    JSONObject object = new JSONObject(result);
                                    if(object.get("message").equals("Result not announced")){
                                        Toast.makeText(ResultEvents.this,
                                                "Result not announced",
                                                Toast.LENGTH_SHORT).show();
                                        if (pd.isShowing())
                                            pd.dismiss();
                                        return;
                                    }
                                    JSONObject objec = new JSONObject();
                                    try {

                                        objec = (JSONObject) object.get("results");
                                    }catch (ClassCastException e){
                                        if (pd.isShowing())
                                            pd.dismiss();
                                        e.printStackTrace();
                                        Toast.makeText(ResultEvents.this,
                                                "Please try again",
                                                Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    JSONArray firstArray = objec.getJSONArray("first");
                                    JSONArray secondArray = objec.getJSONArray("second");
                                    JSONArray thirdArray = objec.getJSONArray("third");
                                    Log.v("firs","-"+firstArray);
                                    Log.v("secondArray", "-" + secondArray);
                                    Log.v("thirdArray", "-" + thirdArray);

                                    setFirstView(firstArray, secondArray, thirdArray);
                                    if (pd.isShowing())
                                        pd.dismiss();
                                }
                            } catch (JSONException e) {

                            }
                        }else {
                            Toast.makeText(ResultEvents.this,
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

    private void setFirstView(JSONArray firstArray, JSONArray secondArray, JSONArray thirdArray) throws JSONException {
        firstcollege.clear();
        FirstFullData.clear();
        for(int i = 0 ; i < firstArray.length(); i++){
            JSONObject ob = new JSONObject();
            ob = firstArray.getJSONObject(i);
            firstcollege.add(ob.getString("college_name"));
            JSONArray arrayName = ob.getJSONArray("students");
            firstname.clear();
            for(int j= 0 ; j< arrayName.length(); j++){
                JSONObject names = new JSONObject();
                names = arrayName.getJSONObject(j);
                firstname.add("\n" + names.getString("student_name")+"\n");
            }
            FirstFullData.add(firstcollege.get(i) + " : " + firstname.toString().replace("[", "").replace("]", ""));
        }
        Log.v("FirstFullData", "-" + FirstFullData);
        mFirstStudents.setText("");
        for (int j = 0; j < FirstFullData.size(); j++){
            mFirstStudents.append( FirstFullData.get(j) + "\n");
        }
        setSecondView(secondArray, thirdArray);
    }

    private void setSecondView(JSONArray secondArray, JSONArray thirdArray) throws JSONException {
        firstcollege.clear();
        SecondFullData.clear();
        for(int i = 0 ; i < secondArray.length(); i++){
            JSONObject ob = new JSONObject();
            ob = secondArray.getJSONObject(i);
            secondcollege.add(ob.getString("college_name"));
            JSONArray arrayName = ob.getJSONArray("students");
            secondname.clear();
            for(int j= 0 ; j< arrayName.length(); j++){
                JSONObject names = new JSONObject();
                names = arrayName.getJSONObject(j);
                secondname.add("\n" + names.getString("student_name")+"\n");
            }
            SecondFullData.add(secondcollege.get(i) + " : " + secondname.toString().replace("[", "").replace("]", ""));
        }
        mSecondStudents.setText("");
        for (int j = 0; j < SecondFullData.size(); j++){
            mSecondStudents.append(SecondFullData.get(j) + "\n");
        }
        setThirdView(thirdArray);
    }

    private void setThirdView(JSONArray thirdArray) throws JSONException {
        thirdcollege.clear();
        ThirdFullData.clear();
        for(int i = 0 ; i < thirdArray.length(); i++){
            JSONObject ob = new JSONObject();
            ob = thirdArray.getJSONObject(i);
            thirdcollege.add(ob.getString("college_name"));
            JSONArray arrayName = ob.getJSONArray("students");
            thirdname.clear();
            for(int j= 0 ; j< arrayName.length(); j++){
                JSONObject names = new JSONObject();
                names = arrayName.getJSONObject(j);
                thirdname.add("\n" + names.getString("student_name")+"\n");
            }
            ThirdFullData.add(thirdcollege.get(i) + " : " + thirdname.toString().replace("[", "").replace("]", ""));
        }
        mThirdStudents.setText("");
        for (int j = 0; j < ThirdFullData.size(); j++){
            mThirdStudents.append( ThirdFullData.get(j) + "\n");
        }
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
                convertView = vi.inflate(R.layout.list_item_college_leader, null);

                holder = new ViewHolder();
                holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
                holder.college = (TextView) convertView.findViewById(R.id.tv_college_name);
                holder.tvId = (TextView) convertView.findViewById(R.id.tv_id);
                holder.mMainLayout = (LinearLayout) convertView.findViewById(R.id.main_layout);
                convertView.setTag(holder);

            } else
                holder = (ViewHolder) convertView.getTag();
            if(position% 2 == 0)
                holder.mMainLayout.setBackgroundColor(getResources().getColor(R.color.bg_outer_grey));
            else
                holder.mMainLayout.setBackgroundColor(getResources().getColor(R.color.bg_leading_dist));


            holder.tvName.setText("Rank : " + dataList.get(position).getRank());
            holder.college.setText("College : " + dataList.get(position).getCollegeName());
            holder.tvId.setText("Points : " + dataList.get(position).getPoints());
            //display the values

            return convertView;
        }
    }
}
