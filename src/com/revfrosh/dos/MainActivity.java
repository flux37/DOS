package com.revfrosh.dos;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
public class MainActivity extends Activity {

	private Spinner monthSpinner;
	static final String[] EVENTS = new String[] { "List" };
	
	private ListView mainListView ;  
	private ArrayAdapter<String> listAdapter ;
	static final String[] months = new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
	public final static String EXTRA_EVENT = "com.revfrosh.dos.SELECTEVENT";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_activity_main);

		addListenerOnSpinnerItemSelection();
		/*
		 * dynamicList(0);
		 */
		
		
	}
	public void addListenerOnSpinnerItemSelection() {
		
		monthSpinner = (Spinner) findViewById(R.id.spinnerMonth);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void staticList(int position){
	
		mainListView = (ListView) findViewById(R.id.listView);
		ArrayList<String> listData = new ArrayList<String>();
		listData.addAll(Arrays.asList(EVENTS));
				
		listAdapter = new ArrayAdapter<String>(this, R.layout.list_elements, listData);
		
		//Finding the months which have 31 days
		if((position<7 && position%2==0) || (position>6 && position%2==1)) {
			for(int i=1; i<=31;i++){
				listAdapter.add(""+months[position]+" "+i+", 2013");
			}
		}
		else{
			if(position==1){
				//February with position 1 has only 28 days
				for(int i=1; i<=28;i++){
					listAdapter.add(""+months[position]+" "+i+", 2013");
				}
			}
			else{
				for(int i=1; i<=30;i++){
					listAdapter.add(""+months[position]+" "+i+", 2013");
				}
			}
		}
			
		mainListView.setAdapter( listAdapter );
	}
	
	
	public void dynamicList(int position){
		mainListView = (ListView) findViewById(R.id.listView);
        
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
        		    
        		    Intent intent = new Intent(parent.getContext(), EventDetailActivity.class);
        		   
                    String s = (String) details.get(position).name;
                    Toast.makeText(parent.getContext(), ""+ (position+1)+ ": "+ s, Toast.LENGTH_SHORT).show();
                    
                    intent.putExtra(EXTRA_EVENT, details.get(position));
                    startActivity(intent);
                    }
                }); 
        mainListView.setAdapter(new customAdapter(details , this));
		
	}
	
	@Override
	public void onPause() {
	    super.onPause();  // Always call the superclass method first
	    //state = mainListView.onSaveInstanceState();
	    
		}
	
	@Override
	public void onResume() {
	    super.onResume();  // Always call the superclass method first
	    //mainListView.onRestoreInstanceState(state);
	    
	    }
}