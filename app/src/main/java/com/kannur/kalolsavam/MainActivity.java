package com.kannur.kalolsavam;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.analytics.tracking.android.EasyTracker;
import com.kannur.kalolsavam.app.AppController;
import com.kannur.kalolsavam.app.Constants;
import com.kannur.kalolsavam.app.Utilities;

public class MainActivity extends ActionBarActivity implements
		SearchView.OnQueryTextListener {

	private ImageView img;
	private String newpath = "pic1.jpg";
	private int i = 2;
	private Button btnevent, btnstatus;
	private ImageButton more;
	private LinearLayout hsgen;
	private LinearLayout nritham, music, drama, draw, sahitham;
	private TextView leaddist, leadscore, leaddist2, leaddist3, leadscore2,
			leadscore3;
	private ScrollView scrl;
	private String dist1, dist2, dist3;
	private int points1, points2, points3;
	private Handler hand = new Handler();
	private Handler hands = new Handler();

	// private ShowcaseView showcaseView;
	private static final int REFRESH_INTERVAL = 60000;
	int counter;

	DrawerLayout mDrawerLayout;
	ActionBarDrawerToggle mDrawerToggle;
	ListView mListView;
	String titles[];
	private SearchView mSearchView;
	MenuItem searchItem;
	SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drawer_layout);
		initialise();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

		mListView = (ListView) findViewById(R.id.drawerList);
		titles = new String[] { "Standings",
				"Venue","Results","Colleges","Gallery","Train Timings",  "Follow Us"
				, "About Us","Locate Us", "Exit" };
		mListView
				.setAdapter(new DrawerListAdapter(this, Arrays.asList(titles)));
		LayoutInflater inflater = getLayoutInflater();

		View listHeaderView = inflater.inflate(R.layout.header_list,null, false);

		mListView.addHeaderView(listHeaderView);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.string.app_name, R.string.app_name){

			public void onDrawerClosed(View view) {
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
				if(getCurrentFocus()!=null) {
					InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
					inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
				}
			}

		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mListView.setOnItemClickListener(mListener);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		mDrawerToggle.syncState();
		prefs = getSharedPreferences("Kalolsavam", 0);
		getSupportActionBar().setTitle(
				getResources().getString(R.string.app_name));
		scrl = (ScrollView) findViewById(R.id.sv_home);
		/*scrl.postDelayed(new Runnable() {

			@Override
			public void run() {
				scrl.smoothScrollTo(0, 100);
			}
		}, 300);*/
		setScore();
		try {
			ViewConfiguration config = ViewConfiguration.get(MainActivity.this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception e) {

		}

		hand.postDelayed(runnableSlider, 1000);
		nritham.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (Utilities.isNetworkAvailable(MainActivity.this)) {

					Intent genint = new Intent(MainActivity.this,
							CategoryResults.class);
					genint.putExtra("category", "NRITHAM");
					startActivity(genint);
					overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
				} else {
					Toast.makeText(MainActivity.this,
							"Please check your internet connection",
							Toast.LENGTH_SHORT).show();
				}

			}
		});
		drama.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (Utilities.isNetworkAvailable(MainActivity.this)) {

					Intent genint = new Intent(MainActivity.this,
							CategoryResults.class);
					genint.putExtra("category", "DRISHYAM");
					startActivity(genint);
					overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
				} else {
					Toast.makeText(MainActivity.this,
							"Please check your internet connection",
							Toast.LENGTH_SHORT).show();
				}

			}
		});
		music.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (Utilities.isNetworkAvailable(MainActivity.this)) {
					Intent genint = new Intent(MainActivity.this,
							CategoryResults.class);
					genint.putExtra("category", "SANGEETHAM");
					startActivity(genint);
					overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
				} else {
					Toast.makeText(MainActivity.this,
							"Please check your internet connection",
							Toast.LENGTH_SHORT).show();

				}
			}
		});

		draw.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (Utilities.isNetworkAvailable(MainActivity.this)) {
					Intent genint = new Intent(MainActivity.this,
							CategoryResults.class);
					genint.putExtra("category", "CHITHRAM");
					startActivity(genint);
					overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
				} else {
					Toast.makeText(MainActivity.this,
							"Please check your internet connection",
							Toast.LENGTH_SHORT).show();

				}
			}
		});

		sahitham.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (Utilities.isNetworkAvailable(MainActivity.this)) {
					Intent genint = new Intent(MainActivity.this,
							CategoryResults.class);
					genint.putExtra("category", "SAHITHYAM");
					startActivity(genint);
					overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
				} else {
					Toast.makeText(MainActivity.this,
							"Please check your internet connection",
							Toast.LENGTH_SHORT).show();

				}
			}
		});

	}

	Runnable runnableSlider = new Runnable() {
		@Override
		public void run() {
			updateTimes();

		}
	};
	Runnable runnableLoadScore = new Runnable() {
		@Override
		public void run() {
			if (Utilities.isNetworkAvailable(MainActivity.this)) {
//				loadScore();
			}

		}
	};



	public void updateTimes() {

		Bitmap localBitmap2 = getBitmapFromAsset(newpath);
		img.setImageBitmap(localBitmap2);
		newpath = "pic" + i + ".jpg";
		i++;
		if (i > 6)
			i = 2;
		if (i > 6) {
			i = 2;
			hand.postDelayed(runnableSlider, 4000);

		} else {
			hand.postDelayed(runnableSlider, 4000);
		}

	}

	private Bitmap getBitmapFromAsset(String strName) {
		AssetManager assetManager = getAssets();
		InputStream istr = null;
		try {
			istr = assetManager.open(strName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Bitmap bitmap = BitmapFactory.decodeStream(istr);
		return bitmap;
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.activity_main, menu);

		searchItem = menu.findItem(R.id.action_search);
		mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
		mSearchView.setOnQueryTextListener(this);
		mSearchView.setQueryHint("Participant Id");
		EditText searchPlate = (EditText) mSearchView.findViewById(R.id.search_src_text);
		searchPlate.setTextColor(getResources().getColor(R.color.bg_leading_dist));
		MenuItemCompat.setOnActionExpandListener(searchItem,
				new OnActionExpandListener() {

					@Override
					public boolean onMenuItemActionExpand(MenuItem arg0) {
						Toast.makeText(MainActivity.this, "Expand",
								Toast.LENGTH_SHORT).show();
						if (mDrawerLayout.isDrawerOpen(mListView)) {
							mDrawerLayout.closeDrawer(mListView);
						}
						return true;
					}

					@Override
					public boolean onMenuItemActionCollapse(MenuItem arg0) {
						// TODO Auto-generated method stub
						return false;
					}
				});
		return true;
	}

	@Override
	public boolean onQueryTextChange(String arg0) {
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String s) {
		mSearchView.setQuery("", false);
		mSearchView.clearFocus();
		mSearchView.onActionViewCollapsed();
		if (Utilities.isNetworkAvailable(MainActivity.this)) {
			Intent ints = new Intent(MainActivity.this, Search.class);
			ints.putExtra("id", s);
			startActivity(ints);
			overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
		} else {
			Toast.makeText(MainActivity.this,
					"Please check your internet connection", Toast.LENGTH_SHORT)
					.show();
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);

	}

	public void initialise() {
		img = (ImageView) findViewById(R.id.scrollimage);
		leaddist = (TextView) findViewById(R.id.leaddist1);

		leadscore = (TextView) findViewById(R.id.leadscore1);
		leaddist2 = (TextView) findViewById(R.id.leaddist2);
		leadscore2 = (TextView) findViewById(R.id.leadscore2);
		leaddist3 = (TextView) findViewById(R.id.leaddist3);
		leadscore3 = (TextView) findViewById(R.id.leadscore3);
		more = (ImageButton) findViewById(R.id.more);

		hsgen = (LinearLayout) findViewById(R.id.hsgeneral);
		nritham = (LinearLayout) findViewById(R.id.nirtham);
		drama = (LinearLayout) findViewById(R.id.drama);
		music = (LinearLayout) findViewById(R.id.music);
		draw = (LinearLayout) findViewById(R.id.draw);
		sahitham = (LinearLayout) findViewById(R.id.sahitham);

		btnevent = (Button) findViewById(R.id.btnevent);
		btnstatus = (Button) findViewById(R.id.btnstatus);

	}

	@Override
	public void onBackPressed() {
		exit();
	}

	private void loadScore() {

		String tag = "tag_req_gold";
		StringRequest req = new StringRequest(Method.POST, Constants.webSurl,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						if (response != null) {
							String d1, d2, d3;
							int s1, s2, s3;
							try {
								JSONArray result = new JSONArray(response);
								JSONObject jo = result.getJSONObject(0);
								d1 = jo.getString("District");
								s1 = Integer.parseInt(jo.getString("Point"));
								leaddist.setText(d1);
								leadscore.setText(jo.getString("Point"));
								JSONObject jo1 = result.getJSONObject(1);
								d2 = jo1.getString("District");
								s2 = Integer.parseInt(jo1.getString("Point"));
								leaddist2.setText(d2);
								leadscore2.setText(jo1.getString("Point"));
								JSONObject jo2 = result.getJSONObject(2);
								d3 = jo2.getString("District");
								s3 = Integer.parseInt(jo2.getString("Point"));
								leaddist3.setText(d3);
								leadscore3.setText(jo2.getString("Point"));

								setScore(d1, s1, d2, s2, d3, s3);
							} catch (JSONException e) {
							} catch (Exception e) {
							}
						}
						hands.postDelayed(runnableLoadScore, REFRESH_INTERVAL);

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						hands.postDelayed(runnableLoadScore, REFRESH_INTERVAL);

					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("type", "gold");
				return params;
			}
		};
		AppController.getInstance().addToRequestQueue(req, tag);

	}

	public void exit() {
		AlertDialog.Builder al = new AlertDialog.Builder(MainActivity.this);
		al.setTitle(MainActivity.this.getResources().getString(
				R.string.app_name));
		al.setMessage("Sure to exit?");
		al.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				finish();
			}

		});
		al.setNegativeButton("No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();

			}
		});
		AlertDialog dg = al.create();
		dg.show();
	}

	// set score to the home screen
	public void setScore() {
		points1 = prefs.getInt("Point1", 0);
		dist1 = prefs.getString("District1", "No Result");
		points2 = prefs.getInt("Point2", 0);
		dist2 = prefs.getString("District2", "No Result");
		points3 = prefs.getInt("Point3", 0);
		dist3 = prefs.getString("District3", "No Result");
		leaddist.setText(dist1);
		leadscore.setText(points1 + "");
		leaddist2.setText(dist2);
		leadscore2.setText(points2 + "");
		leaddist3.setText(dist3);
		leadscore3.setText(points3 + "");

	}

	// save score to preference
	public void setScore(String distname1, int score1, String distname2,
			int score2, String distname3, int score3) {
		// load preferences
		Editor edit = prefs.edit();
		edit.putInt("Point1", score1);
		edit.putString("District1", distname1);
		edit.putInt("Point2", score2);
		edit.putString("District2", distname2);
		edit.putInt("Point3", score3);
		edit.putString("District3", distname3);
		edit.commit();
	}

	@Override
	public void onStart() {
		super.onStart();

		EasyTracker.getInstance(this).activityStart(this);
	}

	@Override
	public void onStop() {
		super.onStop();

		EasyTracker.getInstance(this).activityStop(this);
	}

	OnItemClickListener mListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position,
				long arg3) {
			// selectItem(position);
			mDrawerLayout.closeDrawer(mListView);
			switch (position) {

			case 1:
				if (Utilities.isNetworkAvailable(MainActivity.this)) {
					Intent golds = new Intent(MainActivity.this,
							StandingsActivity.class);
					startActivity(golds);
					overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
				} else {
					Toast.makeText(MainActivity.this,
							"Please check your internet connection",
							Toast.LENGTH_SHORT).show();
				}
				break;
				case 2:
					if (Utilities.isNetworkAvailable(MainActivity.this)) {
//						Intent webint = new Intent(MainActivity.this,
//								WebViewKalol.class);
//						webint.putExtra("url", Constants.venue);
//						webint.putExtra("title", "Venue Map");
//						startActivity(webint);
//						overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
//						overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
					} else {
						Toast.makeText(MainActivity.this,
								"Please check your internet connection",
								Toast.LENGTH_SHORT).show();
					}
					break;
				case 3:
					Intent intent = new Intent(MainActivity.this,ResultActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
					break;
				case 4:
					Intent college = new Intent(MainActivity.this, ParticipatingCollege.class);
					startActivity(college);
					overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
					break;
				case 5:
					Intent train = new Intent(MainActivity.this, GalleryActivity.class);
					startActivity(train);
					overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
					break;
				case 6:
					Intent gallery = new Intent(MainActivity.this, TrainTimings.class);
					startActivity(gallery);
					overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
					break;
				case 7:
					if (Utilities.isNetworkAvailable(MainActivity.this)) {
					/*Intent gold = new Intent(MainActivity.this, Links.class);
					startActivity(gold);*/
						Intent facebook = getOpenFacebookIntent(MainActivity.this);
						startActivity(facebook);
						overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
					} else {
						Toast.makeText(MainActivity.this,
								"Please check your internet connection",
								Toast.LENGTH_SHORT).show();
						return;
					}

					break;
				case 8:
					Intent about = new Intent(MainActivity.this, About.class);
					startActivity(about);
					overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
					break;
				case 9:
					Intent locate = new Intent(MainActivity.this, LocateUsActivity.class);
					startActivity(locate);
					overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
					break;

				case 10:
					exit();
				break;
			default:
				break;
			}
		}
	};

	public static Intent getOpenFacebookIntent(Context context) {

		try {
			context.getPackageManager()
					.getPackageInfo("com.facebook.katana", 0); //Checks if FB is even installed.
			return new Intent(Intent.ACTION_VIEW,
					Uri.parse("fb://profile/254175194653125")); //Trys to make intent with FB's URI
		} catch (Exception e) {
			return new Intent(Intent.ACTION_VIEW,
					Uri.parse("https://www.facebook.com/kannuruniversitykalotsavam2015kanhangad/")); //catches and opens a url to the desired page
		}
	}
	private void onClickleadingDistrict() {
		if (Utilities.isNetworkAvailable(MainActivity.this)) {

			Intent golds = new Intent(MainActivity.this, StandingsActivity.class);
			golds.putExtra("Check", "gold");
			startActivity(golds);
		} else {
			Toast.makeText(MainActivity.this,
					"Please check your internet connection", Toast.LENGTH_SHORT)
					.show();
		}
	}
}
