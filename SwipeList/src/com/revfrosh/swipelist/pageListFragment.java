/**
 * 
 */
package com.revfrosh.swipelist;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.SherlockFragment;
import com.fortysevendeg.android.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.android.swipelistview.SwipeListView;

/**
 * @author admin
 *
 */
public class pageListFragment extends SherlockFragment implements LoaderCallbacks<RESTLoader.RESTResponse>{
	
	private ArrayList<Event> events;
	public final static String SELECT_EVENT = "com.revfrosh.eventcalendar.SELECTEVENT";
	//private static String[] clubName = {"CLUB A", "CLUB B", "CLUB C"};
	private static String[] date = {"12/12/2013", "13/12/2013", "14/12/2013"};

	private OnListItemSelectedListener listener;
	
	private static final String TAG = pageListFragment.class.getName();
	private static final int LOADER_EVENTS = 0x1;
	    
	private static final String ARGS_URI    = "com.revfrosh.eventscalendar.ARGS_URI";
    private static final String ARGS_PARAMS = "com.revfrosh.eventscalendar.ARGS_PARAMS";
    
    private SwipeListView swipeListView;
    private customAdapter adapter;
    private LinearLayout Progressbar;
    //private ArrayList<Event> data;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.swipe_list_view_activity,
	    		container, false);
	    
	    events = new ArrayList<Event>();
        adapter = new customAdapter(events, getActivity());
        Progressbar = (LinearLayout) view.findViewById(R.id.ProgressLayout);
        Progressbar.setVisibility(View.VISIBLE);
        
	    swipeListView = (SwipeListView) view.findViewById(R.id.example_lv_list);
	    
	    swipeListView.setSwipeListViewListener(new BaseSwipeListViewListener() {
            @Override
            public void onOpened(int position, boolean toRight) {
            }

            @Override
            public void onClosed(int position, boolean fromRight) {
            }

            @Override
            public void onListChanged() {
            }

            @Override
            public void onMove(int position, float x) {
            }

            @Override
            public void onStartOpen(int position, int action, boolean right) {
                Log.d("swipe", String.format("onStartOpen %d - action %d", position, action));
            }

            @Override
            public void onStartClose(int position, boolean right) {
                Log.d("swipe", String.format("onStartClose %d", position));
            }

            @Override
            public void onClickFrontView(int position) {
                Log.d("swipe", String.format("onClickFrontView %d", position));
                listener.onListItemSelected(getActivity(), events.get(position));
            }

            @Override
            public void onClickBackView(int position) {
                Log.d("swipe", String.format("onClickBackView %d", position));
            }

            @Override
            public void onDismiss(int[] reverseSortedPositions) {
                //for (int position : reverseSortedPositions) {
                //    events.remove(position);
                //}
                //adapter.notifyDataSetChanged();
            }

        });
	    
	    swipeListView.setAdapter(adapter);
        reload();
        
	    return view;
    }

    
    @Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	  
		  super.onActivityCreated(savedInstanceState);
		  
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
	    	  events.clear();  //Empty data of ArrayList
	          events.addAll(getEventsFromJson(json));
	          Log.d(TAG, "Load Complete.");

	          adapter.notifyDataSetChanged();
	      }
	      else {
	      	Log.e("OnLoadFinished()", "Failed to Load JSON data");
	      }
	      Progressbar.setVisibility(View.GONE);
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
                  
	              eventDetails.name = event.getString("name").toUpperCase();
	              //eventDetails.clubName = clubName[1];
	              eventDetails.date = date[0];
                  eventList.add(eventDetails);
	          }
	      }
	      catch (JSONException e) {
	          Log.e(TAG, "Failed to parse JSON.", e);
	      }
	        
	      return eventList;
	  }
	  
	  private void reload() {
	    	swipeListView.setSwipeMode(SwipeListView.SWIPE_MODE_LEFT);
	        swipeListView.setSwipeActionLeft(SwipeListView.SWIPE_ACTION_REVEAL);
	        swipeListView.setSwipeActionRight(SwipeListView.SWIPE_ACTION_REVEAL);
	        swipeListView.setOffsetLeft(convertDpToPixel(0));
	        swipeListView.setOffsetRight(convertDpToPixel(0));
	        swipeListView.setAnimationTime(0);
	        swipeListView.setSwipeOpenOnLongPress(false);
	  }

	  public int convertDpToPixel(float dp) {
	      DisplayMetrics metrics = getResources().getDisplayMetrics();
	      float px = dp * (metrics.densityDpi / 160f);
	      return (int) px;
	  }
	  
	  public interface OnListItemSelectedListener {
		  public void onListItemSelected(Context c, Event selected);
	  }
	    	
	  @Override
	  public void onAttach(Activity activity) {
		  super.onAttach(activity);
		  if (activity instanceof OnListItemSelectedListener) {
			  listener = (OnListItemSelectedListener) activity;
		  } else {
			throw new ClassCastException(activity.toString()
				+ " must implement ListFragment.OnListItemSelectedListener");
		  }
	  }

}
