package com.revfrosh.eventscalendar;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class SearchableActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());
    }
    
    public void onNewIntent(Intent intent) { 
	      setIntent(intent); 
	      handleIntent(intent); 
	   } 

    public void onListItemClick(ListView l, View v, int position, long id) { 
	      // call detail activity for clicked entry 
	   } 

    private void handleIntent(Intent intent) { 
	      if (Intent.ACTION_SEARCH.equals(intent.getAction())) { 
		         String query = intent.getStringExtra(SearchManager.QUERY); 
		         doSearch(query); 
		      } 
		   }    

	   
	   private void doSearch(String queryStr) { 
		   
		   
		   Toast.makeText(getBaseContext(), "Search Requested: " + queryStr, Toast.LENGTH_SHORT)
			.show(); // Create a simple toast message
	
	   // get a Cursor, prepare the ListAdapter
	   // and set it
	   }
	   
	   /*
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.searchable, menu);
	        return true;
	    }
	    */
	    
}
