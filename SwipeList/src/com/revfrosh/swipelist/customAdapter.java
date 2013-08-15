/**
 * 
 */
package com.revfrosh.swipelist;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.fortysevendeg.android.swipelistview.SwipeListView;

/**
 * @author admin
 *
 */
public class customAdapter extends BaseAdapter implements ListAdapter {

	private ArrayList<Event> customData;
    Context customContext;
	
    public customAdapter(ArrayList<Event> data, Context c){
        customData = data;
        customContext = c;
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return customData.size();
	}

	@Override
	public Event getItem(int position) {
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
		//final Event item = getItem(position);
        ViewHolder holder;
        
		//View  customView = convertView;
        if ( convertView == null)
        {
           LayoutInflater vi = (LayoutInflater) customContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           convertView = vi.inflate(R.layout.package_row, null);
           holder = new ViewHolder();
           holder.eventDate = (TextView)convertView.findViewById(R.id.DateSeparator);
           holder.eventName = (TextView)convertView.findViewById(R.id.EventName);
           //holder.clubName = (TextView)convertView.findViewById(R.id.ClubName);
           holder.bAction1 = (ImageButton) convertView.findViewById(R.id.example_row_b_action_1);
           holder.bAction2 = (ImageButton) convertView.findViewById(R.id.example_row_b_action_2);
           holder.bAction3 = (ImageButton) convertView.findViewById(R.id.example_row_b_action_3);
           convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        ((SwipeListView)parent).recycle(convertView, position);
        
        Event event = (Event) customData.get(position);
        holder.eventDate.setText(event.date);
        //SET IF VISIBLE
        holder.eventDate.setVisibility(View.GONE);// or View.VISIBLE
        //
        holder.eventName.setText(event.name);
        //holder.clubName.setText(event.clubName);
        
        holder.bAction1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	Toast.makeText(customContext, ":: SHARE ::", Toast.LENGTH_SHORT).show();
            }
        });

        holder.bAction2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	Toast.makeText(customContext, ":: STAR ::", Toast.LENGTH_SHORT).show();
            }
        });

        holder.bAction3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	Toast.makeText(customContext, ":: DELETE? LOL ::", Toast.LENGTH_SHORT).show();
            }
        });
                      
       return convertView;
	}
	
	static class ViewHolder {
		TextView eventDate;
		TextView eventName;
        TextView clubName;
        ImageButton bAction1;
        ImageButton bAction2;
        ImageButton bAction3;
    }

}
