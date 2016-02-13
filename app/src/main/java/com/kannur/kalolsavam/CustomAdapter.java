package com.kannur.kalolsavam;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

	private ViewHolder holder;
	ArrayList<Points> points;
	private LayoutInflater mInflater;

	public CustomAdapter(Context context, ArrayList<Points> points) {
		this.points = points;
		mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.gold_custom, null);
			holder.txtSlNo = (TextView) convertView.findViewById(R.id.txtSlNo);
			holder.txtDistrict = (TextView) convertView
					.findViewById(R.id.txtdistr);
			holder.txtPoint = (TextView) convertView
					.findViewById(R.id.txtPoint);
			holder.layout = (LinearLayout) convertView
					.findViewById(R.id.layout);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.txtSlNo.setText("" + points.get(position).sl_no);
		holder.txtDistrict.setText(points.get(position).district);
		holder.txtPoint.setText("" + points.get(position).point);
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
		TextView txtDistrict;
		TextView txtPoint;

	}

	@Override
	public int getCount() {
		  
		return points.size();
	}

	@Override
	public Object getItem(int index) {
		  
		return points.get(index);
	}

	@Override
	public long getItemId(int position) {
		  
		return position;
	}

}