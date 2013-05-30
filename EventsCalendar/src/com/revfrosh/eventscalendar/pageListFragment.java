/**
 * 
 */
package com.revfrosh.eventscalendar;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

/**
 * @author admin
 *
 */
public class pageListFragment extends ListFragment {
	
	private String[] eventName = {"Bob", "Rob", "Mike"};
	private String[] clubName = {"clubA", "clubB", "clubC"};
	private String[] date = {"12/12/2013", "13/12/2013", "14/12/2013"};
	private int tabNo;

	  @Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);

	    tabNo = getArguments().getInt("pageIndex");
	    final ArrayList<Event> details = new ArrayList<Event>();
	    for(int i=0; i<5; i++) {
            Event Detail;
            Detail = new Event();
            Detail.name = eventName[tabNo];
            Detail.clubName = clubName[tabNo];
            Detail.date = date[tabNo];
            details.add(Detail);
        }
	    getListView().setSelector(R.drawable.selector_list);
	    setListAdapter(new customAdapter(details , getActivity()));
	  }

	  @Override
	  public void onListItemClick(ListView l, View v, int position, long id) {
	    // Do something with the data
		Toast.makeText(getActivity(), " :: "+ (position+1)+ " :: ", Toast.LENGTH_SHORT).show();  
	  }
}
