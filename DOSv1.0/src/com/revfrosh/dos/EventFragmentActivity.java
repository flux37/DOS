package com.revfrosh.dos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class EventFragmentActivity extends FragmentActivity {
	public static final String EXTRA_EVENT = "com.revfrosh.dos.SELECTEVENT";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_fragment);
		
/*
		 // Get the message from the intent
	    Intent intent = getIntent();
	    String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

	    // Create the text view
	    TextView textView = new TextView(this);
	    textView.setTextSize(40);
	    textView.setText(message);

	    // Set the text view as the activity layout
	    setContentView(textView);

		
		
	    Bundle extras = getIntent().getExtras();
	    if (extras != null) {
	      Event e = extras.
	      //TextView view = (TextView) findViewById(R.id.detailsText);
	      //view.setText(s);
	    }
	    */
		Intent i = getIntent();
		Event myObject = (Event) i.getParcelableExtra(EXTRA_EVENT);
		
		EventDetailFragment fragment = (EventDetailFragment) getSupportFragmentManager().findFragmentById(R.id.eventDetailFragment);
		fragment.setData(myObject);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event, menu);
		return true;
	}

}
