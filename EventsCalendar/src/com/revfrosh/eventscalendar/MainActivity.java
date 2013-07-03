package com.revfrosh.eventscalendar;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;


public class MainActivity extends SherlockFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        PagerTabStrip pagerTabStrip = (PagerTabStrip) findViewById(R.id.pager_tab_strip);
        FragmentPagerAdapter pagerAdapter = new  MyFragmentPagerAdapter(getSupportFragmentManager());
 
        viewPager.setAdapter(pagerAdapter);
        pagerTabStrip.setDrawFullUnderline(true);
        pagerTabStrip.setTabIndicatorColor(Color.RED);
        
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add("Search") // Add a new Menu Button
				.setOnMenuItemClickListener(this.SearchButtonClickListener) 
				.setIcon(R.drawable.search_button) // Set the button icon
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM); 
		
		return super.onCreateOptionsMenu(menu);
	}
	
	OnMenuItemClickListener SearchButtonClickListener = new OnMenuItemClickListener() {

		public boolean onMenuItemClick(MenuItem item) {

			Toast.makeText(getBaseContext(), ":: Search ::", Toast.LENGTH_LONG)
					.show(); // Create a simple toast message

			return false;
		}
	};

	
}