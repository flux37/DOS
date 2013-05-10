package com.revfrosh.dos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class MainFragmentActivity extends FragmentActivity implements ListFragment.OnListItemSelectedListener{

	public final static String EXTRA_EVENT = "com.revfrosh.dos.SELECTEVENT";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_activity_main);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	  public void onListItemSelected(Context c, Event event) {
	    EventDetailFragment fragment = (EventDetailFragment) getSupportFragmentManager().findFragmentById(R.id.eventDetailFragment);
	    if (fragment != null && fragment.isInLayout()) {
	      fragment.setData(event);
	    } else {
	    	//Intent intent = new Intent(c, EventFragmentActivity.class);
	    	Intent intent = new Intent(getApplicationContext(), EventFragmentActivity.class);
            //String s = (String) event.name;
            //Toast.makeText(c, ""+ (position+1)+ ": "+ s, Toast.LENGTH_SHORT).show();
            
            intent.putExtra(EXTRA_EVENT, event);
            startActivity(intent);
           
	    }
	  }

}
