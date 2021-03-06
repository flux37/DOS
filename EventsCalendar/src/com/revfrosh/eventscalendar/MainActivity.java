package com.revfrosh.eventscalendar;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;



public class MainActivity extends SherlockFragmentActivity {

	public final static String SELECT_EVENT = "com.revfrosh.eventcalendar.SELECTEVENT";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        PagerTabStrip pagerTabStrip = (PagerTabStrip) findViewById(R.id.pager_tab_strip);
        MyFragmentPagerAdapter pagerAdapter = new  MyFragmentPagerAdapter(getSupportFragmentManager());
 
        viewPager.setOffscreenPageLimit(3);
        
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

		menu.add("Settings") // Add a new Menu Button
		.setOnMenuItemClickListener(this.SettingsButtonClickListener) 
		.setIcon(R.drawable.settings_button) // Set the button icon
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM); 

		
		return super.onCreateOptionsMenu(menu);
	}
	
	OnMenuItemClickListener SearchButtonClickListener = new OnMenuItemClickListener() {

		public boolean onMenuItemClick(MenuItem item) {

			Toast.makeText(getBaseContext(), ":: Search ::", Toast.LENGTH_SHORT)
					.show(); // Create a simple toast message

			onSearchRequested();
			
			return false;
		}
	};
		
	OnMenuItemClickListener SettingsButtonClickListener = new OnMenuItemClickListener() {

			public boolean onMenuItemClick(MenuItem item) {

				Toast.makeText(getBaseContext(), ":: Settings ::", Toast.LENGTH_SHORT)
						.show(); // Create a simple toast message

				return false;
			}
	};
	
}

	
