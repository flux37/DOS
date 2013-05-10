package com.revfrosh.dos;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class EventDetailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_event_detail);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event_detail, menu);
		return true;
	}

	 public void star_on(View v) {
	      // TODO Auto-generated method stub
	       ImageView newStarOn = (ImageView) findViewById(R.id.star);
	       newStarOn.setImageResource(R.drawable.star_on);
	       newStarOn.setVisibility(View.VISIBLE);

	 }
}

