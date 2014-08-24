/**
 * MainActivity.java 2014-07-16
 * Copyright Patrik Evertsson
 */

package com.example.parksmart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.*;

import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;


/**
 * @author Patrik Evertsson
 *
 *
 * ParkingService is connecting to an URL and gets the JSON data
 * and returns an array of JSON objects
 * 
 */
public class ParkingService {
	
	private static final String TAG = "ParkingService";
	
	/**
	 * Final variables for URL from JSON source
	 * These URL can be customized. In this early version I choose not to.
	 * Just to keep it simple. The second row of this URL or String is the APPID
	 * from data.goteborg.se. The other variables of the URL (lat,long,radius,format) is optional.
	 *
	 */
	
	
	static final String privateTollParkings =
			"http://data.goteborg.se/ParkingService/v2.1/PrivateTollParkings" +
					"/%7B57217002-37bb-43ce-8867-69a9b01cf6e9%7D?" +
					"latitude={LATITUDE}&" +
					"longitude={LONGITUDE}&" +
					"radius={RADIUS}&" +
					"format=json";
	
	
	/**
	 * Download JSON data and put in a String.
	 * Pull JSON objects from the string to put in an array
	 * @return Array
	 */
	public Parking[] getParkingArray() {

		String data = null;
		JSONArray jsonarray = new JSONArray();


		try{
			//Create url object from the http url
			URL url = new URL(privateTollParkings);
			
			try{
				//Connect and read the data
				URLConnection connection = new URL(url.toString()).openConnection();
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						connection.getInputStream()), 1024 * 16);

				//Put the data in a string
				data = reader.readLine();
				reader.close();
				
				try {
					//Read the jsondata from the string
					jsonarray = new JSONArray(data);
					
					//Create a list on how long the JSONArray is
					Parking[] parkings = new Parking[jsonarray.length()];

					//Put in the JSON objects (Parkings) in the array
					for (int i = 0; i < jsonarray.length(); i++) {
						parkings[i] = new Parking(jsonarray.getJSONObject(i));
					}
					
					return parkings;
					
				} catch (JSONException e) {
					Log.i(TAG, "JSONException");
					e.printStackTrace();
				}//JSON
				

			}catch (IOException e){
				Log.i(TAG, "IOException");
				e.printStackTrace();
			}//IO
			
		}catch (MalformedURLException e){
			Log.i(TAG, "MalformedURLException");
			e.printStackTrace();
		}//URL
		
		return new Parking[0];


	}
}
