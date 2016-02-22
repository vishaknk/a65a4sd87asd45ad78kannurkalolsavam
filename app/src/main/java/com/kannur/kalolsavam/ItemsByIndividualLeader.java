package com.kannur.kalolsavam;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kannur.kalolsavam.app.AppController;
import com.kannur.kalolsavam.app.Constants;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Item Results");
        setContentView(R.layout.activity_item_result);
        spinner= (Spinner) findViewById(R.id.sv_bible_books);
        mList= (ListView) findViewById(R.id.lv_bible_list);
        list = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.itemNames)));
        codes = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.itemcodes)));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ItemsByIndividualLeader.this
                ,R.layout.spinner_item,list);

        // Set the Adapter
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPositon = position;
                getResultFromWebService(codes.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    // call WS
    private void getResultFromWebService(String code) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage(getResources().getString(R.string.loading_message));
        pd.show();
        StringRequest req = new StringRequest(Constants.kannurbaseurl,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String result) {
                        if (pd.isShowing())
                            pd.dismiss();
                        if (result != null) {
                            try {
                                if (result.equals("")) {
                                    mList.setEmptyView(findViewById(android.R.id.empty));
                                } else {
                                    JSONObject ar = new JSONObject(result);
                                    setDataToList(ar);
                                }

                            } catch (JSONException e) {

                                e.printStackTrace();
                            } catch (Exception e) {

                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(ItemsByIndividualLeader.this,
                                    "Cannot connect to the server. Please try again",
                                    Toast.LENGTH_SHORT).show();
                        }

                        mList.setEmptyView(findViewById(android.R.id.empty));

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                if (pd.isShowing())
                    pd.dismiss();
                mList.setEmptyView(findViewById(android.R.id.empty));

            }
        });
        AppController.getInstance().addToRequestQueue(req, tag);

    }

    private void setDataToList(JSONObject response) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
