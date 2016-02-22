package com.kannur.kalolsavam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.kannur.kalolsavam.app.Utilities;

/**
 * Created by Priyesh on 20-02-2016.
 */
public class CategoryResults extends AppCompatActivity {
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_cateogry_result);
        listView = (ListView) findViewById(R.id.listview);
        listView.setEmptyView(findViewById(android.R.id.empty));

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

        }

        if (Utilities.isNetworkAvailable(this)) {
            loadData();
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

    private void loadData() {

    }
}
