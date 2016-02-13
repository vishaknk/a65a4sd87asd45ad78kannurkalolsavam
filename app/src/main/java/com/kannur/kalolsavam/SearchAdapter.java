package com.kannur.kalolsavam;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.kannur.kalolsavam.app.AppController;
import com.kannur.kalolsavam.app.Constants;
import com.kannur.kalolsavam.app.Utilities;

public class SearchAdapter extends BaseAdapter {

	private ViewHolder holder;
	ArrayList<Search_const> points;
	private LayoutInflater mInflater;
	String ranks, grade, point;

	public SearchAdapter(Context context, ArrayList<Search_const> points) {

		this.points = points;
		mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.search_custom, null);
			holder.txtSlNo = (TextView) convertView.findViewById(R.id.txtSlNo);
			holder.txtname = (TextView) convertView.findViewById(R.id.txtName);
			holder.txtclas = (TextView) convertView.findViewById(R.id.txtClass);
			holder.txtsname = (TextView) convertView
					.findViewById(R.id.txtSchool);
			holder.txtiname = (TextView) convertView.findViewById(R.id.txtItem);
			holder.txtrank = (TextView) convertView.findViewById(R.id.txtR);
			holder.txtgrade = (TextView) convertView.findViewById(R.id.txtG);
			holder.txtpoint = (TextView) convertView.findViewById(R.id.txtP);
			holder.layout = (RelativeLayout) convertView
					.findViewById(R.id.layout);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.txtSlNo.setText("" + points.get(position).participant_id);
		holder.txtname.setText(points.get(position).participant_name);
		holder.txtclas.setText("Class-" + points.get(position).classs);
		holder.txtsname.setText("" + points.get(position).school_name);
		System.out.println("School " + points.get(position).school_name);
		holder.txtiname.setText("" + points.get(position).item_name);
		ranks = points.get(position).rank;
		grade = points.get(position).grade;
		point = points.get(position).point;
		if (ranks.equals("") || ranks == null) {
			ranks = "?";
		}
		if (grade.equals("") || ranks == null) {
			grade = "?";
		}
		if (point.equals("") || ranks == null) {
			point = "?";
		}
		holder.txtrank.setText("R-" + ranks);
		holder.txtgrade.setText("  G-" + grade);
		holder.txtpoint.setText("  P-" + point);
		if (position % 2 != 0)
			holder.layout.setBackgroundResource(R.drawable.bg_even);
		else {
			holder.layout.setBackgroundResource(R.drawable.bg);
		}
		return convertView;
	}

	static class ViewHolder {
		RelativeLayout layout;
		TextView txtSlNo;
		TextView txtname;
		TextView txtclas;
		TextView txtsname;
		TextView txtiname;
		TextView txtrank;
		TextView txtgrade;
		TextView txtpoint;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return points.size();
	}

	@Override
	public Object getItem(int index) {
		// TODO Auto-generated method stub
		return points.get(index);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

}