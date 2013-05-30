package com.revfrosh.eventscalendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
	
	private String[] pageTitle = {"MY EVENTS", "ALL EVENTS" , "STARRED"};

	public MyFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		//Fragment fragment = new PageFragment();
		Fragment fragment = new pageListFragment();
		Bundle arguments = new Bundle();
        arguments.putInt("pageIndex", position);
        fragment.setArguments(arguments);
        return fragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pageTitle.length;
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
        return pageTitle[position];
    }

}
