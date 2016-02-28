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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Priyesh on 20-02-2016.
 */
public class ResultActivity extends AppCompatActivity {
    private ArrayList<String>sections;
    private LayoutInflater mInflater;
    private PrayerSetAdapater mAdapter;
    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Results");
        setContentView(R.layout.result_sections);
        mListView = (ListView) findViewById(R.id.lv_sections);
        mInflater = LayoutInflater.from(ResultActivity.this);
        sections = new ArrayList<>();
        sections.add("Item Results");
        sections.add("Individual LeaderBoard");
        sections.add("College LeaderBoard");
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


                    if(position == 0) {
                        Intent intent = new Intent(ResultActivity.this,ResultEvents.class);
                        intent.putExtra("items", "1");
                        startActivity(intent);
                    }
                    else if(position ==1) {
                        Intent intent = new Intent(ResultActivity.this,ItemsByIndividualLeader.class);
                        intent.putExtra("items", "2");
                        startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(ResultActivity.this,ItemRsultActivity.class);
                        intent.putExtra("items", "3");
                        startActivity(intent);
                    }

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
