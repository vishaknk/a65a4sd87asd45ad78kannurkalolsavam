package com.kannur.kalolsavam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;

/**
 * Created by Priyesh on 27-02-2016.
 */
public class ItemShedule extends AppCompatActivity{
    private ImageView mItem;
    private int position = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_shedule);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Kannur University Kalolsavam 2016");
        mItem = (ImageView) findViewById(R.id.iv_train);
        position = getIntent().getIntExtra("items",0);
        switch (position){
            case 0 :
                mItem.setImageResource(R.drawable.shedule_01);
                break;
            case 1 :
                mItem.setImageResource(R.drawable.shedule_03);
                break;

            case 2 :
                mItem.setImageResource(R.drawable.shedule_02);
                break;
            case 3 :
                mItem.setImageResource(R.drawable.shedule_04);
                break;
            case 4 :
                mItem.setImageResource(R.drawable.shedule_05);
                break;


        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
