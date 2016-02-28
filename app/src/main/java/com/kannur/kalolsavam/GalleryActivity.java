package com.kannur.kalolsavam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.kannur.kalolsavam.app.AppController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iTraveller on 2/13/2016.
 */
public class GalleryActivity extends AppCompatActivity{
    private ListView mList;
    private LayoutInflater mInflater;
    private ArrayList<Integer> mImages;
    private galleryAdapter mAdapter;
    private ImageLoader mLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Gallery");
        setContentView(R.layout.layout_gallery);
        mList= (ListView) findViewById(R.id.lv_gallery);
        mInflater = LayoutInflater.from(this);
        mImages = new ArrayList<>();
        mImages.add(R.drawable.committee_office);
        mImages.add(R.drawable.inuagral);
        mImages.add(R.drawable.logo_inau);
        mAdapter = new galleryAdapter(this,R.id.lv_gallery,mImages);
        mList.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    class galleryAdapter extends ArrayAdapter {
        Context context;
        ArrayList<Integer> images;

        public galleryAdapter(Context context, int resource, ArrayList<Integer> list) {
            super(context, resource, list);
            this.context = context;
            this.images = new ArrayList<>();
            this.images = list;
        }
        public class ViewHolder {
            ImageView image;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView;
            final ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                view = mInflater.inflate(R.layout.list_item_images, parent, false);
                holder.image = (ImageView) view.findViewById(R.id.iv_feed_image);
                view.setTag(R.integer.tag_prayer_set_listing_view_holder, holder);
            } else
                holder = (ViewHolder) view.getTag(R.integer.tag_prayer_set_listing_view_holder);
            // to set the image posted
            if (mLoader == null) {
                mLoader = AppController.getInstance().getImageLoader();
            }
            Glide.with(GalleryActivity.this).load(images.get(position)).into(holder.image);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            return view;
        }
        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return images.get(position);
        }

    }
}
