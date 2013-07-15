/**
 * 
 */
package com.revfrosh.eventscalendar;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListFragment;

/**
 * @author admin
 *
 */
public class pageListFragment extends SherlockListFragment implements LoaderCallbacks<RESTLoader.RESTResponse>{
	
	ArrayList<Event> events;
	public final static String SELECT_EVENT = "com.revfrosh.eventcalendar.SELECTEVENT";
	private static String[] clubName = {"clubA", "clubB", "clubC"};
	private static String[] date = {"12/12/2013", "13/12/2013", "14/12/2013"};
	
	// private int tabNo;
	
	private static final String TAG = pageListFragment.class.getName();
	private static final int LOADER_EVENTS = 0x1;
	    
	private static final String ARGS_URI    = "com.revfrosh.eventscalendar.ARGS_URI";
    private static final String ARGS_PARAMS = "com.revfrosh.eventscalendar.ARGS_PARAMS";
	

    @Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	  
		  super.onActivityCreated(savedInstanceState);
	      
		//  tabNo = getArguments().getInt("pageIndex");	  
		  Uri eventSearchUri = Uri.parse("http://cal-backend.appspot.com/query/category/all");
	        
	        // Here we are going to place our REST call parameters. Note that
	        // we could have just used Uri.Builder and appendQueryParameter()
	        // here, but I wanted to illustrate how to use the Bundle params.
	        Bundle params = new Bundle();
	        params.putString("Events", "android");
	        
	        // These are the loader arguments. They are stored in a Bundle because
	        // LoaderManager will maintain the state of our Loaders for us and
	        // reload the Loader if necessary. This is the whole reason why
	        // we have even bothered to implement RESTLoader.
	        Bundle args = new Bundle();
	        args.putParcelable(ARGS_URI, eventSearchUri);
	        args.putParcelable(ARGS_PARAMS, params);
	        
	        // Initialize the Loader.
	        getLoaderManager().initLoader(LOADER_EVENTS, args, this);
	  }
	  
	  @Override
	    public Loader<RESTLoader.RESTResponse> onCreateLoader(int id, Bundle args) {
	        if (args != null && args.containsKey(ARGS_URI) && args.containsKey(ARGS_PARAMS)) {
	            Uri    action = args.getParcelable(ARGS_URI);
	            Bundle params = args.getParcelable(ARGS_PARAMS);
	            
	            return new RESTLoader(getActivity(), RESTLoader.HTTPVerb.GET, action, params);
	        }
	        return null;
	    }

	  @Override
	    public void onLoadFinished(Loader<RESTLoader.RESTResponse> loader, RESTLoader.RESTResponse data) {
	        int    code = data.getCode();
	        String json = data.getData();
	        
	        // Check to see if we got an HTTP 200 code and have some data.
	        if (code == 200 && !json.equals("")) {
	            
	            // For really complicated JSON decoding I usually do my heavy lifting
	            // Gson and proper model classes, but for now let's keep it simple
	            // and use a utility method that relies on some of the built in
	            // JSON utilities on Android.
	            events = getEventsFromJson(json);
	            
	            // Load our list adapter with our Tweets.
	            getListView().setSelector(R.drawable.selector_list);
			    setListAdapter(new customAdapter(events , getActivity()));
		    		
	        }
	        else {
	        	Log.e("OnLoadFinished()", "Failed to Load JSON data");
	        }
	    }

	    @Override
	    public void onLoaderReset(Loader<RESTLoader.RESTResponse> loader) {
	    }
	    
	    private static ArrayList<Event> getEventsFromJson(String json) {
	        ArrayList<Event> eventList = new ArrayList<Event>();
	        
	        try {
	            JSONObject eventsWrapper = (JSONObject) new JSONTokener(json).nextValue();
	            JSONArray  events        = eventsWrapper.getJSONArray("results");
	            
	            for (int i = 0; i < events.length(); i++) {
	                JSONObject event = events.getJSONObject(i);
	                
	                Event eventDetails = new Event();
                    
	                eventDetails.name = event.getString("name");
	                eventDetails.clubName = clubName[0];
	                eventDetails.date = date[0];
                    eventList.add(eventDetails);
	            }
	        }
	        catch (JSONException e) {
	            Log.e(TAG, "Failed to parse JSON.", e);
	        }
	        
	        return eventList;
	    }
	  
	  @Override
	  public void onListItemClick(ListView l, View v, int position, long id) {
	    // Do something with the data
		
		Context context = getActivity();
		Event event = events.get(position);
		
		Toast.makeText(context, " :: "+ (position+1)+ " :: ", Toast.LENGTH_SHORT).show();
				
		Intent intent = new Intent(context, EventDetailFragment.class);
		intent.putExtra(SELECT_EVENT, event);
        
        startActivity(intent);
        		
	  }
}
