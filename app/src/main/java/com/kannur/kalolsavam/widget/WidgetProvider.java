package com.kannur.kalolsavam.widget;

import com.kannur.kalolsavam.R;
import com.kannur.kalolsavam.app.AppController;
import com.kannur.kalolsavam.app.Constants;
import com.kannur.kalolsavam.app.Utilities;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class WidgetProvider extends AppWidgetProvider {

	private static final String REFRESH_CLICKED = "refreshButtonClick";

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {

		RemoteViews views = new RemoteViews(context.getPackageName(),
				R.layout.widget_layout);
		views.setOnClickPendingIntent(R.id.btn_refresh,
				getPendingSelfIntent(context, REFRESH_CLICKED));
		ComponentName cn = new ComponentName(context, WidgetProvider.class);
		appWidgetManager.updateAppWidget(cn, views);
		context.startService(new Intent(context, WidgetService.class));
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		final String action = intent.getAction();
		if (AppWidgetManager.ACTION_APPWIDGET_DELETED.equals(action)) {
			final int appWidgetId = intent.getIntExtra(
					AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
			if (appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
				this.onDeleted(context, new int[] { appWidgetId });
			}
		} else if (REFRESH_CLICKED.equals(action)) {
			context.startService(new Intent(context, RefreshService.class));

		} else {
			super.onReceive(context, intent);
		}
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
		context.stopService(new Intent(context, WidgetService.class));
	}

	protected PendingIntent getPendingSelfIntent(Context context, String action) {
		Intent intent = new Intent(context, getClass());
		intent.setAction(action);
		return PendingIntent.getBroadcast(context, 0, intent, 0);
	}
}
