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

/**
 * Created by Priyesh on 27-02-2016.
 */
public class ShedulePrograms extends AppCompatActivity {
    private ArrayList<String>sections;
    private LayoutInflater mInflater;
    private PrayerSetAdapater mAdapter;
    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Shedules");
        setContentView(R.layout.result_sections);
        mListView = (ListView) findViewById(R.id.lv_sections);
        mInflater = LayoutInflater.from(ShedulePrograms.this);
        sections = new ArrayList<>();
        sections.add("Day 1 (28-02-2016)");
        sections.add("Day 2 (29-02-2016)");
        sections.add("Day 3 (01-03-2016)");
        sections.add("Day 4 (02-03-2016)");
        sections.add("Day 5 (03-03-2016)");
        mAdapter = new PrayerSetAdapater(this,R.id.lv_sections,sections);
        mListView.setAdapter(mAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    class PrayerSetAdapater extends ArrayAdapter {
        Context context;
        ArrayList<String> prayers;

        public PrayerSetAdapater(Context context, int resource, ArrayList<String> list) {
            super(context, resource, list);
            this.context = context;
            this.prayers = new ArrayList<>();
            this.prayers = list;
        }
        public class ViewHolder {
            TextView prayerSet;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView;
            final ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                view = mInflater.inflate(R.layout.list_item_sections, parent, false);
                holder.prayerSet = (TextView) view.findViewById(R.id.tv_prayer_sets);
                view.setTag(R.integer.tag_prayer_set_listing_view_holder, holder);
            } else
                holder = (ViewHolder) view.getTag(R.integer.tag_prayer_set_listing_view_holder);
            holder.prayerSet.setText(prayers.get(position));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(ShedulePrograms.this,ItemShedule.class);
                    intent.putExtra("items",position);
                    startActivity(intent);
                    overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                }
            });
            return view;
        }
        @Override
        public int getCount() {
            return prayers.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return prayers.get(position);
        }

    }

}
