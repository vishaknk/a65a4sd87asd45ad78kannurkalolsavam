package com.kannur.kalolsavam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Priyesh on 02-03-2016.
 */
public class ImgaeViewMain extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Gallery");
        setContentView(R.layout.image_activity);
        int image = getIntent().getIntExtra("image",R.drawable.img_default);
        ImageView img = (ImageView) findViewById(R.id.vp_image);
        Glide.with(ImgaeViewMain.this).load(image).into(img);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
