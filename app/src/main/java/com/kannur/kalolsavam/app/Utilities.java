package com.kannur.kalolsavam.app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.Gravity;
import android.widget.Toast;

public class Utilities {

	public static boolean isNetworkAvailable(Context context) {

		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
	}


	public static boolean isGinger() {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
			return true;
		return false;
	}

	//method for displaying short toast
	public static void showToast(Context context, String msg) {
		float scale = context.getResources().getDisplayMetrics().density;
		Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		int offsetY = (int) (-100 * scale);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, offsetY);
		toast.show();
	}

	//method for displaying long toast
	public static void showLongToast(Context context, String toastMessage) {
		float scale = context.getResources().getDisplayMetrics().density;
		Toast toast = Toast.makeText(context, toastMessage, Toast.LENGTH_LONG);
		int offsetY = (int) (-100 * scale);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, offsetY);
		toast.show();
	}
}
