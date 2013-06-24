package com.revfrosh.dos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class EventDetailFragment extends Fragment {
	
	//private View fragView;
	
	@Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
	      Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.fragment_event_detail,
	        container, false);
	    //fragView = view;
	    return view;
	  }
	
	public void setData(Event event) {
		
	    TextView eventName = (TextView) getView().findViewById(R.id.eventName);
	    eventName.setText(event.name);
	    
	    ImageView star = (ImageView) getView().findViewById(R.id.star);
	    if(event.starred == true)
	    	star.setImageResource(R.drawable.star_on);
	    else
	    	star.setImageResource(R.drawable.star_off);
	    
	    TextView clubName = (TextView) getView().findViewById(R.id.clubName);
	    clubName.setText(event.clubName);
	    
	    TextView date = (TextView) getView().findViewById(R.id.eventDate);
	    date.setText(event.date);	    
	    
	  }
		
	public void star_on(View v) {
	      // TODO Auto-generated method stub
	       ImageView newStarOn = (ImageView) getView().findViewById(R.id.star);
	       newStarOn.setImageResource(R.drawable.star_on);
	       newStarOn.setVisibility(View.VISIBLE);

	 }

}
