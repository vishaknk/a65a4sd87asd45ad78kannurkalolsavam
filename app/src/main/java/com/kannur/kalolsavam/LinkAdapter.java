package com.kannur.kalolsavam;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


public class LinkAdapter extends BaseAdapter{
	

	private ViewHolder holder;
	String[] titles;
	String[] urls;
	private LayoutInflater mInflater;
	public LinkAdapter(Context context,String[] tit,String[] ur)
	{
		titles=tit;
		urls=ur;
		mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView == null)
		{
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.link_custom, null);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			
		
			holder.layout = (LinearLayout)convertView.findViewById(R.id.linearlay);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setText(""+titles[position]);		
		return convertView;
	}
	static class ViewHolder
	{		
			LinearLayout layout;
			TextView title;
			
			
			
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return titles.length;
	}
	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

}