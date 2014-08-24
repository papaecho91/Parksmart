/**
 * MainActivity.java 2014-08-18
 * Copyright Patrik Evertsson
 */

package com.example.parksmart;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;

import android.app.Activity;
import android.app.Dialog;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * 
 * @author 
 * @version 0.0.7
 * 
 * This is the MainActivity class with the map fragment
 * It connects to google play and sets up the google map.
 */

public class MapActivity extends Activity {



	public GoogleMap googleMap;
	
	private static final String TAG = "MainActivity";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Draw google map only if connected to google play
        if(connectionToGooglePlay()){
        	
        	setContentView(R.layout.activity_map);
        	
        	Log.i(TAG, "--------OnCreate-------");
        	
        	setUpMapIfNeeded();   
        	getMyLocation();
        	
        	//This if-statement makes sure I can run network code in the UI thread
        	if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = 
                        new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                }
        	
        	//Place out all the markers from parkingobjects
        	drawParkingMarkers();
        	
        	//Create a customized infowindow for each marker
        	infowindow();
        }

        
    }
    
    /**
     * Test connection between the app and Google Play
     * @return boolean
     */
    private boolean connectionToGooglePlay(){
    	int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
    	if(status == ConnectionResult.SUCCESS){
    		return true;
    	}else{
    		//Displays a dialog what went wrong
    		((Dialog) GooglePlayServicesUtil.getErrorDialog(status,this,10)).show();
    	}return false;
    		
    		
    }
     
    /**
     * Make sure we can interact and customize the map
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            
            /** 
             * Check if we were successful in obtaining the map.
             * If it works, we can start using the map
             */
            if (googleMap != null) {

            }
        }
    }
    
    /**
     * Get my position and zoom to it on map
     */
    private void getMyLocation(){
    	
    	// Get my current location
    	googleMap.setMyLocationEnabled(true);
 
    	LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
    	String provider = lm.getBestProvider(new Criteria(), true);
    	Location pos = lm.getLastKnownLocation(provider);
    	
    	LatLng mypos = new LatLng(pos.getLatitude(), pos.getLongitude());
		
    	googleMap.moveCamera(CameraUpdateFactory.newLatLng(mypos));
		googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));	
    }

    /**
     * Generate markers from parkingobjects
     */
    private void drawParkingMarkers (){
    	
    	
    	//Create a LatLng object
     	LatLng parkpos = new LatLng(0.0,0.0);
     	
     	ParkingService ps = new ParkingService();
        Parking[] parking = new Parking[0];    
        
        //Insert a list of objects to Parking.java
        parking = ps.getParkingArray();
        
        
        //Loop through the list and create a marker for each object
        //Print the needed attributes to the infowindow on the marker
        for(int i = 0; i < parking.length; i++){
        	parkpos = new LatLng(parking[i].getLat(), parking[i].getLng());
         	
        	googleMap.addMarker(new MarkerOptions()
         	.title(parking[i].getItems())
         	.position(parkpos));         	
        }
     }
    
    /**
     *Create a customized infowindow for a marker 
     */
    private void infowindow(){
     	googleMap.setInfoWindowAdapter(new InfoWindowAdapter(){
     		
     		//Change the content in infowindow
			@Override
			public View getInfoContents(Marker marker) {

				View v = getLayoutInflater().inflate(R.layout.infowindow,null);
				
				TextView title = (TextView) v.findViewById(R.id.title);

				//Use the defualt marker title from Marker class
				title.setText(marker.getTitle());
				
				return v;
			}

			//Use default design for infowindow
			@Override
			public View getInfoWindow(Marker marker) {
				
				return null;
			}
			
     	});
    }
   
}