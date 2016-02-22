package com.kannur.kalolsavam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Priyesh on 14-02-2016.
 */
public class TrainTimings extends AppCompatActivity {
    private TextView mKannur, mKozhikode;
    private ImageView mKannurTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Train Timings");
        setContentView(R.layout.activity_train);
        mKannurTime = (ImageView) findViewById(R.id.iv_train);
        mKozhikode = (TextView) findViewById(R.id.tv_toclt);
        mKannur = (TextView) findViewById(R.id.tv_toKannur);
        mKannurTime.setImageResource(R.drawable.finalknr);
        mKannur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mKannurTime.setVisibility(View.VISIBLE);
                mKannurTime.setImageResource(R.drawable.finalknr);
                mKannur.setTextColor(getResources().getColor(R.color.bg_leading_dist));
                mKozhikode.setTextColor(getResources().getColor(R.color.text_primary));
                mKannur.setBackgroundColor(getResources().getColor(R.color.lightor));
                mKozhikode.setBackgroundColor(getResources().getColor(R.color.bg_leading_dist));
            }
        });

        mKozhikode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mKannurTime.setVisibility(View.VISIBLE);
                mKannurTime.setImageResource(R.drawable.finalclt);
                mKozhikode.setTextColor(getResources().getColor(R.color.bg_leading_dist));
                mKannur.setTextColor(getResources().getColor(R.color.text_primary));
                mKozhikode.setBackgroundColor(getResources().getColor(R.color.lightor));
                mKannur.setBackgroundColor(getResources().getColor(R.color.bg_leading_dist));
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
}
