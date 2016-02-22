package com.kannur.kalolsavam;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Priyesh on 14-02-2016.
 */
public class CollegeAdapter extends BaseAdapter {

    List<TreeMap<String,String>>colleges;
    private LayoutInflater mInflater;
    private ViewHolder holder;

    public CollegeAdapter(Context context, List<TreeMap<String,String>>colleges) {
        this.colleges = colleges;
        mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return 0;
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
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.college_listitem, null);
            holder.txtSlNo = (TextView) convertView.findViewById(R.id.txtSlNo);
            holder.collegename = (TextView) convertView
                    .findViewById(R.id.txtdistr);
            holder.layout = (LinearLayout) convertView
                    .findViewById(R.id.layout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txtSlNo.setText("" + colleges.get(position).get("slno"));
        holder.collegename.setText(colleges.get(position).get("name"));
        if (position % 2 != 0)
            holder.layout.setBackgroundResource(R.drawable.bg_even);
        else {
            holder.layout.setBackgroundResource(R.drawable.bg);
        }
        return convertView;
    }

    static class ViewHolder {
        LinearLayout layout;
        TextView txtSlNo;
        TextView collegename;

    }
}
