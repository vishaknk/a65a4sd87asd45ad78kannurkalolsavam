package com.kannur.kalolsavam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Priyesh on 01-03-2016.
 */
public class VenueActivity extends AppCompatActivity {
    private ImageView mImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Venue");
        setContentView(R.layout.activity_venue);
        mImage = (ImageView) findViewById(R.id.iv_feed_image);
        Glide.with(VenueActivity.this).load(R.drawable.venue).into(mImage);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
