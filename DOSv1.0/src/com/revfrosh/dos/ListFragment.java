package com.revfrosh.dos;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class ListFragment extends Fragment {
	//private OnItemSelectedListener listener;
	private OnListItemSelectedListener listener;
	
	//private Spinner monthSpinner;
	//static final String[] EVENTS = new String[] { "List" };
	
	//private ListView mainListView ;
	//static final String[] months = new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
	  
	
	  @Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
	      Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.fragment_event_list,
	        container, false);
	    
	    //addListenerOnSpinnerItemSelection();
	    //dynamicList(0);
	    //
	    Spinner monthSpinner = (Spinner) view.findViewById(R.id.spinnerMonth);
		//monthSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
		
		monthSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
	        @Override
	        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
	            // TODO Auto-generated method stub

	        	Toast.makeText(parent.getContext(), ""+ (pos+1)+ ": "+ parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();

	        	dynamicList(pos);

	        }

	        @Override
	        public void onNothingSelected(AdapterView<?> arg0) {
	        }
	    });
	    //
	    
	    return view;
	  }
	  
	  public void addListenerOnSpinnerItemSelection() {
			
			Spinner monthSpinner = (Spinner) getView().findViewById(R.id.spinnerMonth);
			//monthSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
			
			monthSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
		        @Override
		        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		            // TODO Auto-generated method stub

		        	Toast.makeText(parent.getContext(), ""+ (pos+1)+ ": "+ parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();

		        	dynamicList(pos);

		        }

		        @Override
		        public void onNothingSelected(AdapterView<?> arg0) {
		        }
		    });
			
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
	  
	  public void dynamicList(int position){
			
		  	ListView mainListView = (ListView) getView().findViewById(R.id.listView);
	        
	        final ArrayList<Event> details = new ArrayList<Event>();
	        
	        for(int i=0; i<5; i++)
	        {
	            Event Detail;
	            Detail = new Event();
	            Detail.name = "Bob";
	            Detail.clubName = "Dinner";
	            Detail.date = "12/12/2013";
	            details.add(Detail);
	            
	            Detail = new Event();
	            Detail.name = "Rob";
	            Detail.clubName = "Party";
	            Detail.date = "13/12/2012";
	            details.add(Detail);
	            
	            Detail = new Event();
	            Detail.name = "Mike";
	            Detail.clubName = "Mail";
	            Detail.date = "13/12/2012";
	            details.add(Detail);
	        }
	            

	        
	        mainListView.setOnItemClickListener(new OnItemClickListener() {
	        
	        	public void onItemClick (AdapterView<?> parent, View v, int position, long id) {
	        		    
	        		Context c = parent.getContext();
	        		listener.onListItemSelected(c, details.get(position));
	        		/*
	        		    Intent intent = new Intent(parent.getContext(), EventDetailActivity.class);
	        		   
	                    String s = (String) details.get(position).name;
	                    Toast.makeText(parent.getContext(), ""+ (position+1)+ ": "+ s, Toast.LENGTH_SHORT).show();
	                    
	                    intent.putExtra(EXTRA_EVENT, details.get(position));
	                    startActivity(intent);
	                    */
	                    }
	                }); 
	        mainListView.setAdapter(new customAdapter(details , getActivity()));
			
		}

	  
	  
}
