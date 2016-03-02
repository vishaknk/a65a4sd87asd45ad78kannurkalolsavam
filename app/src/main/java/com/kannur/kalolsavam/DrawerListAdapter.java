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
			icon.setImageResource(R.drawable.icon_stand);
			item.setTextColor(context.getResources().getColor(R.color.orange));
			break;
		case 1:
			icon.setImageResource(R.drawable.icon_shedule);
			item.setTextColor(context.getResources().getColor(R.color.blues));
			break;
		case 2:
			icon.setImageResource(R.drawable.icon_result);
			item.setTextColor(context.getResources().getColor(R.color.bg_leading_dist));
			break;
		case 3:
			icon.setImageResource(R.drawable.icon_college);
			item.setTextColor(context.getResources().getColor(R.color.green));
			break;
		case 4:
			icon.setImageResource(R.drawable.icon_venue);
			item.setTextColor(context.getResources().getColor(R.color.violet));
			break;


		case 5:
			icon.setImageResource(R.drawable.icon_gallery);
			item.setTextColor(context.getResources().getColor(R.color.redish));
			break;
		case 6:
			icon.setImageResource(R.drawable.icon_traintiming);
			item.setTextColor(context.getResources().getColor(R.color.blues));
			break;
			case 7:
				icon.setImageResource(R.drawable.qr);
				item.setTextColor(context.getResources().getColor(R.color.violet));
				break;
		case 8:
			icon.setImageResource(R.drawable.icon_follow);
			item.setTextColor(context.getResources().getColor(R.color.orange));
			break;
		case 9:
			icon.setImageResource(R.drawable.icon_about);
			item.setTextColor(context.getResources().getColor(R.color.green));
			break;
		case 10:
			icon.setImageResource(R.drawable.icon_locate);
			item.setTextColor(context.getResources().getColor(R.color.bg_leading_dist));
			break;
		case 11:
			icon.setImageResource(R.drawable.icon_close);
			item.setTextColor(context.getResources().getColor(R.color.orange));
			break;
		default:
			break;
		}
		return view;
	}

}
