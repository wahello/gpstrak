package com.sec7.gpstrak;

import java.util.Vector;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


public class MainActivity extends Activity implements LocationListener {

	private LocationManager locationManager;
	private String provider;
	private LinearLayout ll;
	private ScrollView sv;
	private Vector<Double> latitude, longitude;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ll = (LinearLayout)findViewById(R.id.child);
		sv = (ScrollView)findViewById(R.id.scrollView1);
		
		latitude = new Vector<Double>();
		longitude = new Vector<Double>();
		
		locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(provider);
		
		TextView tv = new TextView(this);
		tv.setText(provider);
		ll.addView(tv);
		
		if (location != null)
			onLocationChanged(location);	
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		locationManager.requestLocationUpdates(provider, 0, 0, this);
	}
	
	@Override
	public void onLocationChanged(Location location)
	{
		location = locationManager.getLastKnownLocation(provider);
		double lat = (double)Math.round(location.getLatitude() * 1000000) / 1000000;
		double lng = (double)Math.round(location.getLongitude() * 1000000) / 1000000;

		TextView tv = new TextView(this);
		tv.setText("Lat: " + Double.toString(lat) + " Long: " + Double.toString(lng));
		ll.addView(tv);
		
		latitude.add(lat);
		longitude.add(lng);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
	public void onProviderDisabled(String arg0) {
		TextView tv = new TextView(this);
		tv.setText("Provider disabled: " + arg0);
		ll.addView(tv);
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		TextView tv = new TextView(this);
		tv.setText("Provider enabled: " + arg0);
		ll.addView(tv);
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
}
