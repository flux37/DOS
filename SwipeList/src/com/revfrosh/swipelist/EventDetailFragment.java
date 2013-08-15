package com.revfrosh.swipelist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;


public class EventDetailFragment extends SherlockFragmentActivity {

	public final static String SELECT_EVENT = "com.revfrosh.eventcalendar.SELECTEVENT";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_detail_fragment);
		
		Intent i = getIntent();
		Event myObject = (Event) i.getParcelableExtra(SELECT_EVENT);
		
		setData(myObject);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		// Show the Up button in the action bar.
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
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

	//getMenuInflater().inflate(R.menu.event_detail, menu);
	//return true;
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void setData(Event event) {
		
	    TextView eventName = (TextView) findViewById(R.id.eventName);
	    eventName.setText(event.name);
	    
	    TextView clubName = (TextView) findViewById(R.id.clubName);
	    clubName.setText(event.clubName);
	    
	    TextView date = (TextView) findViewById(R.id.eventDate);
	    date.setText(event.date);	    
	    
	}
}