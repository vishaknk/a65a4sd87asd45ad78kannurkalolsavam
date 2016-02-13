package com.kannur.kalolsavam;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

@SuppressWarnings("deprecation")
public class GeneralTab extends ActionBarActivity {

	private ActionBar mActionBar;
	private ViewPager mPager;
	private Tab tab;
	static String chchk = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewpager);
		chchk = getIntent().getStringExtra("Check");

		mActionBar = getSupportActionBar();
		mActionBar.setTitle(getResources().getString(R.string.app_name)+getIntent().getStringExtra("title"));
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mPager = (ViewPager) findViewById(R.id.pager);

		FragmentManager fm = getSupportFragmentManager();
		final ViewPagerAdapter viewpageradapter = new ViewPagerAdapter(fm);

		ViewPager.SimpleOnPageChangeListener ViewPagerListener = new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				super.onPageSelected(position);

				mActionBar.setSelectedNavigationItem(position);
				viewpageradapter.notifyDataSetChanged();
			}
		};

		mPager.setOnPageChangeListener(ViewPagerListener);

		mPager.setAdapter(viewpageradapter);

		ActionBar.TabListener tabListener = new ActionBar.TabListener() {

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {

				mPager.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// ft.remove(viewpageradapter.getItem(tab.getPosition()));
				// viewpageradapter.notifyDataSetChanged();
			}

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {

			}
		};

		tab = mActionBar.newTab().setText("Points-\nDistrict")
				.setTabListener(tabListener);
		mActionBar.addTab(tab);

		tab = mActionBar.newTab().setText("Points-\nSchool")
				.setTabListener(tabListener);
		mActionBar.addTab(tab);

		tab = mActionBar.newTab().setText("Result-\nDeclared")
				.setTabListener(tabListener);
		mActionBar.addTab(tab);

	}

	public class ViewPagerAdapter extends FragmentPagerAdapter {

		final int PAGE_COUNT = 3;

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			switch (arg0) {

			case 0:
				PointsDistrict fragmenttab1 = new PointsDistrict();
				return fragmenttab1;

			case 1:
				PointsSchool fragmenttab2 = new PointsSchool();
				return fragmenttab2;

			case 2:
				ResultDeclared fragmenttab3 = new ResultDeclared();
				return fragmenttab3;
			}
			return null;
		}

		@Override
		public int getCount() {

			return PAGE_COUNT;
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home)
			finish();
		return super.onOptionsItemSelected(item);
	}
}
