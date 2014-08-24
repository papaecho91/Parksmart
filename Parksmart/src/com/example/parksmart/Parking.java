/**
 * MainActivity.java 2014-07-16
 * Copyright Patrik Evertsson
 */

package com.example.parksmart;


import org.json.JSONObject;

/*
 * Represents a parking lot
 */
public class Parking{

	
	private String id;
	private String name;
	private String owner;
	private int phoneParkingCode;
	private int freeSpaces;	
	private double lng;
	private double lat;
	private int parkingSpaces;
	private String extraInfo;	
	private int currentParkingCost;
	private String parkingCost;
	private String residentialParking;	
	private int parkingSpaceCount;
	private int parkableLength;
	private String maxParking;

	
	public Parking() {

	}

	/**
	 * Insert JSONObject and save the values of the 
	 * values in variables
	 * @param json
	 */
	public Parking(JSONObject json) {
		
		id = json.optString("Id");
		name = json.optString("Name");
		owner = json.optString("Owner");
		phoneParkingCode = json.optInt("PhoneParkingCode");
		freeSpaces = json.optInt("FreeSpaces");
		lat = json.optDouble("Lat");
		lng = json.optDouble("Long");
		parkingSpaces = json.optInt("ParkingSpaces");
		extraInfo = json.optString("ExtraInfo");
		currentParkingCost = json.optInt("CurrentParkingCost");
		parkingCost = json.optString("ParkingCost");
		residentialParking = json.optString("ResidentialParkingArea");
		parkingSpaceCount = json.optInt("ParkingSpaceCount");
		parkableLength = json.optInt("ParkableLength");
		maxParking = json.optString("MaxParkingTime");
		
	}


	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public String getOwner(){
		return owner;
	}
	
	public int getPhoneParkingCode(){
		return phoneParkingCode;
	}
	
	public int getFreeSpaces() {
		return freeSpaces;
	}

	public int getParkableLength() {
		return parkableLength;
	}
	
	public double getLat() {
		return lat;
	}
	
	public double getLng() {
		return lng;
	}

	public int getParkingSpaces() {

		return parkingSpaces;
	}
	
	public String getExtraInfo() {
		return extraInfo;
	}
	
	public int getCurrentParkingCost() {
		return currentParkingCost;
	}
	
	public String getParkingCost() {

		return parkingCost;
	}
	
	public int getParkingSpaceCount() {

		return parkingSpaceCount;
	}

	public String getResidentialParking() {
		return residentialParking;
	}
	
	public String getMaxParking() {
		return maxParking;
	}
	
	
	public void setLng(float lng) {
		this.lng = lng;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	/**
	 * Returns short information about the parkingspace
	 * If there is no freespaces on the specific parkingspace
	 * it does not return anything about freespaces
	 * @return String
	 */
	public String getItems(){
		if(getFreeSpaces() == 0){
			return getName()
					+"\nBolag: "+getOwner()
					+ "\nAntal platser: "+getParkingSpaces() 
					+ "\nTelefonkod: "+getPhoneParkingCode()
					+ "\nKostnad: "+getCurrentParkingCost() +"kr/tim"
					;
		}else {
			return getName()
					+"\nBolag: "+getOwner()
					+"\nAntal platser: "+getParkingSpaces()
					+"\nLediga platser: "+getFreeSpaces()
					+"\nTelefonkod: "+getPhoneParkingCode()
					+"\nKostnad: "+getCurrentParkingCost() +"kr/tim"
					;
		}
	}
	
}

