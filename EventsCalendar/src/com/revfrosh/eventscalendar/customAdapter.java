/**
 * 
 */
package com.revfrosh.eventscalendar;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

/**
 * @author admin
 *
 */
public class customAdapter extends BaseAdapter implements ListAdapter {

	private ArrayList<?> customData;
    Context customContext;
	
	customAdapter(ArrayList<?> data, Context c){
        customData = data;
        customContext = c;
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return customData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return customData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View  customView = convertView;
        if ( customView == null)
        {
           LayoutInflater vi = (LayoutInflater) customContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           customView = vi.inflate(R.layout.list_events, null);
        }

        TextView eventDate = (TextView)customView.findViewById(R.id.DateSeparator);
        TextView eventName = (TextView)customView.findViewById(R.id.EventName);
        TextView clubName = (TextView)customView.findViewById(R.id.ClubName);

        Event event = (Event) customData.get(position);
        eventDate.setText(event.date);
        //SET IF VISIBLE
        //eventDate.setVisibility(View.GONE);// or View.VISIBLE
        //
        eventName.setText(event.name);
        clubName.setText(event.clubName);                           
                      
       return customView;
	}

}
