/**
 * 
 */
package com.revfrosh.eventscalendar;


import java.net.URI;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListFragment;

/**
 * @author admin
 *
 */
public class pageListFragment extends SherlockListFragment {
	
	private String[] clubName = {"clubA", "clubB", "clubC"};
	private String[] date = {"12/12/2013", "13/12/2013", "14/12/2013"};
	private int tabNo;

	  @Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	  
		  super.onActivityCreated(savedInstanceState);
	    
		  tabNo = getArguments().getInt("pageIndex");
		  new DownloadEvents().execute("http://cal-backend.appspot.com/query/category/all");
	  }
	  
	  private class DownloadEvents extends AsyncTask<String, Void, ArrayList<Event>> {
		    /** The system calls this to perform work in a worker thread and
		      * delivers it the parameters given to AsyncTask.execute() */
		    protected ArrayList<Event> doInBackground(String... urls) {
		        return loadEvents(urls);
		    }
		    
		    /** The system calls this to perform work in the UI thread and delivers
		      * the result from doInBackground() */
		    protected void onPostExecute(ArrayList<Event> result) {
		       
		    	final ArrayList<Event> details = result;
		    	getListView().setSelector(R.drawable.selector_list);
			    setListAdapter(new customAdapter(details , getActivity()));
		    	
		    }
		    
		    public ArrayList<Event> loadEvents(String... urls){
		        final ArrayList<Event> events = new ArrayList<Event>();
		        try {
		        	URI url= new URI(urls[0]);
		            HttpClient hc = new DefaultHttpClient();
		            HttpGet get = new HttpGet(url);
		            HttpResponse rp = hc.execute(get);
		            if(rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
		            {
		            	String result = EntityUtils.toString(rp.getEntity());
		                JSONObject root = new JSONObject(result);
		                        
		                JSONArray sessions = root.getJSONArray("results");
		                for (int i = 0; i < sessions.length(); i++) {
		                	
		                	JSONObject session = sessions.getJSONObject(i);
		                    Event Detail = new Event();
		                                
		                    Detail.name = session.getString("name");
		                    Detail.clubName = clubName[tabNo];
		                    Detail.date = date[tabNo];
		                    events.add(Detail);
		                  
		                }
		            }
		        } catch (Exception e) {
		                Log.e("EventFeedActivity", "Error loading JSON", e);
		        	}
		        return events;
		}
	}
	  
	  @Override
	  public void onListItemClick(ListView l, View v, int position, long id) {
	    // Do something with the data
		Toast.makeText(getActivity(), " :: "+ (position+1)+ " :: ", Toast.LENGTH_SHORT).show();  
	  }
}
