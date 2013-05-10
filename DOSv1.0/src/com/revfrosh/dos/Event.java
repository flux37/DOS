package com.revfrosh.dos;

import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable {

	public String name;
	public String clubName;
	//public String category;
	//public String venue;
	public String date;
	//public String time;
	//public String deadline;
	//public String description;
	public String availability;
	//public String link;
	//The variable for favorites (star)
	public boolean starred;
	
	
	//Constructor - for any non parcelable object
	public Event(){
		//Since it should not be in favorites by default.
		starred=false;
	}
	
	public Event(Parcel in){
		readFromParcel(in);
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(name);
		dest.writeString(clubName);
		dest.writeString(date);
		dest.writeString(availability);
		dest.writeString(clubName);
		
		//Type casting from boolean to int
		dest.writeInt(starred?1:0);
			
	}
	
	private void readFromParcel(Parcel in) {
		 
		// We just need to read back each
		// field in the order that it was
		// written to the parcel
		name = in.readString();
		clubName = in.readString();
		date = in.readString();
		availability = in.readString();
		
		//Type casting back from int to boolean
		starred = (in.readInt() == 1)?true:false;
	}
	
	/**
    *
    * This field is needed for Android to be able to
    * create new objects, individually or as arrays.
    *
    * This also means that you can use use the default
    * constructor to create the object and use another
    * method to hyrdate it as necessary.
    *
    * I just find it easier to use the constructor.
    * It makes sense for the way my brain thinks ;-)
    *
    */
   public static final Parcelable.Creator<Event> CREATOR =
   	new Parcelable.Creator<Event>() {
           public Event createFromParcel(Parcel in) {
               return new Event(in);
           }

           public Event[] newArray(int size) {
               return new Event[size];
           }
       };
}

