package com.kannur.kalolsavam;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DrawerListAdapter extends BaseAdapter {

	private List<String> items;
	private Context context;

	public DrawerListAdapter(Context context, List<String> items) {
		this.items = items;
		this.context = context;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int index) {
		return items.get(index);
	}

	@Override
	public long getItemId(int id) {
		return id;
	}

	@Override
	public View getView(int pos, View view, ViewGroup parent) {
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.row_drawer_list, null);

		}
		ImageView icon = (ImageView) view.findViewById(R.id.icon);
		TextView item = (TextView) view.findViewById(R.id.listItems);
		item.setText(items.get(pos));
		switch (pos) {
		case 0:
			icon.setImageResource(R.drawable.ic_gold_cup);
			break;
		case 1:
			icon.setImageResource(R.drawable.ic_program_chart);
			break;
		case 2:
			icon.setImageResource(R.drawable.ic_venue_map);
			break;
		case 3:
			icon.setImageResource(R.drawable.ic_video);
			break;
		case 4:
			icon.setImageResource(R.drawable.ic_links);
			break;
		case 5:
			icon.setImageResource(R.drawable.ic_history);
			break;
		case 6:
			icon.setImageResource(R.drawable.ic_about);
			break;
		case 7:
			icon.setImageResource(R.drawable.ic_exit);
			break;
		default:
			break;
		}
		return view;
	}

}
