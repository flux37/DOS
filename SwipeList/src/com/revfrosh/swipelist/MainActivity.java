package com.revfrosh.swipelist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;



public class MainActivity extends SherlockFragmentActivity implements pageListFragment.OnListItemSelectedListener {

	public final static String SELECT_EVENT = "com.revfrosh.eventcalendar.SELECTEVENT";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        
	}
	
	@Override
	public void onListItemSelected(Context context, Event event) {		
		//Toast.makeText(context, " :: "+ (position+1)+ " :: ", Toast.LENGTH_SHORT).show();
				
		Intent intent = new Intent(context, EventDetailFragment.class);
		intent.putExtra(SELECT_EVENT, event);
        
        startActivity(intent);
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

	
