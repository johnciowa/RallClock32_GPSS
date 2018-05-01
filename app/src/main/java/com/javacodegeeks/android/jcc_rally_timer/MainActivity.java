package com.javacodegeeks.android.jcc_rally_timer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity {

	private Button startButton;
	private Button pauseButton;
	private Button newCASTButton;
	
	private Button resetOdoButton;

	private TextView timerValue;
	private TextView deccoValue;
	private TextView odoValue;
	private TextView curCASTValue;
	private TextView pauseTimeValue;

	TextView CAST_Value;
	
	private Button setCFButton;
	
	TextView CF_Value;
	EditText CF_Text;

	String CF_Text2;
	double odo_CF=1;
	
	private long startTime = 0L;
	
	private Handler customHandler = new Handler();
	
	EditText CAST_Text;
	String CAST_Text2;
	
	String odo_string;
	
	long timeInMilliseconds = 0L;
	long timeSwapBuff = 0L;
	long updatedTime = 0L;

	double decmins;

	double good_odo;
	
	long PtimeInMilliseconds = 0L;
	long updatedPauseTime = 0L;
	long pauseStartTime = 0L;
	
	long PausetimeSwapBuff = 0L;	
	long PupdatedTime= 0L;
		
	double secs_keep;
	double pause_secs;
	double cast_chg_odo = 0;
	double pause_time = 0;
	
	double deco = .5/30; //.01667; 
	
	double CAST;

    TextView lt, ln;
    TextView calcDist;
    TextView dist_Value;
    Integer FirstRun = 1;
    LocationManager locationManager;
    TextView curr_Speed;
    TextView Avg_Speed;
    TextView curr_Secs;

    double lastLat;
    double lastLon;
    double totalDistance_mile;
    double totalDistance = 0;
    long start_Time = 0;

    long timeInMilliseconds2 = 0L;
    long updatedTime2;
    double  secs_keep2;
    double decmins2;
    double avg_speed = 0;
    double totalSpeed = 0;

    TextView exp_time_Value;
    TextView actual_time_Value;
    TextView time_vary_Value;
    double ms_to_mph = 2.2369362920544;

	private Button cp_button;
    private TextView legA_time_value;
    TextView legA_avg_spd_Value, Cumul_time_Value;
    double actual_time;
    double legA_time;
    double time_vary;
    String strLegA_time, str_AvgSpeed, strCumul_time;
    double cumul_time;

    private Handler legHandler = new Handler();

    @Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);

		timerValue = (TextView) findViewById(R.id.timerValue);
		
		deccoValue = (TextView) findViewById(R.id.deccoValue);

		startButton = (Button) findViewById(R.id.startButton);
		
		newCASTButton = (Button) findViewById(R.id.newCASTButton);	
		
		odoValue = (TextView) findViewById(R.id.odoValue);

		curCASTValue = (TextView) findViewById(R.id.curCASTValue);
		
		resetOdoButton = (Button) findViewById(R.id.resetOdoButton);
		
		pauseTimeValue = (TextView) findViewById(R.id.pauseTimeValue);
		
		setCFButton = (Button) findViewById(R.id.setCFButton);
		
		CF_Value = (TextView) findViewById(R.id.CF_Value);	
		
		CAST_Value = (TextView) findViewById(R.id.CAST_Value);

        ln=(TextView)findViewById(R.id.lng);

        lt=(TextView)findViewById(R.id.lat);

        dist_Value =(TextView)findViewById(R.id.dist_Value);

        curr_Speed =(TextView)findViewById(R.id.curr_Speed);

        Avg_Speed = (TextView)findViewById(R.id.Avg_Speed);

        curr_Secs =(TextView)findViewById(R.id.curr_Secs);

        pauseButton = (Button) findViewById(R.id.pauseButton);

        actual_time_Value = (TextView)findViewById(R.id.actual_time_Value);

        exp_time_Value = (TextView)findViewById(R.id.exp_time_Value);

        time_vary_Value =(TextView)findViewById(R.id.time_vary_Value);

        cp_button = (Button) findViewById(R.id.Ck_point_Button);

        legA_time_value = (TextView) findViewById(R.id.legA_time_Vary);

		legA_avg_spd_Value = (TextView) findViewById(R.id.leg_avg_spdA);

        Cumul_time_Value = (TextView) findViewById(R.id.Cumul_time_view);

        setCFButton = (Button) findViewById(R.id.setCFButton);

        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
                0, locationListener);

        Location locationManagerLastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);



// next cast button
		newCASTButton = (Button) findViewById(R.id.newCASTButton);
		
		newCASTButton.setOnClickListener(new View.OnClickListener() {
		
			public void onClick(View view) {

//                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, locationListener);
				
			    CAST_Text = (EditText) findViewById (R.id.CAST_Value);
			    
			    CAST_Text2 = CAST_Text.getText().toString();
			    		    
			    if (CAST_Text.getText().toString().equals("")) { 

			    	 return; 
			    }
				CAST_Value.setText("");

				CAST =  Double.parseDouble(CAST_Text2);
				
				startTime = SystemClock.uptimeMillis();  // start time
				
				timeSwapBuff = 0L;
						
				cast_chg_odo = good_odo;

                totalDistance = 0;

                FirstRun = 1;
				
				customHandler.postDelayed(updateTimerThread, 0);

//				customHandler.removeCallbacks(updatePauseTimerThread);

            }
		});

		
// reset odo button
		resetOdoButton = (Button) findViewById(R.id.resetOdoButton);
		
		resetOdoButton.setOnClickListener(new View.OnClickListener() {
		
			public void onClick(View view) {

				// reset time
				
				customHandler.removeCallbacks(updateTimerThread);			// stops timer	

//				SystemClock.sleep(300); 
				
				good_odo = 0;
				cast_chg_odo = 0;
				timeSwapBuff = 0L;
				
				startTime = SystemClock.uptimeMillis();  // start time				
				
			customHandler.postDelayed(updateTimerThread, 0);  // starts run time
				
//				customHandler.removeCallbacks(updatePauseTimerThread);		// stops pause timer
        	
			}
		});

// START Button

		startButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {

				CAST_Text = (EditText) findViewById (R.id.CAST_Value);

				CAST_Text2 = CAST_Text.getText().toString();

				if (CAST_Text.getText().toString().equals("")) {

					return;
				}

				CAST =  Double.parseDouble(CAST_Text2);

				startTime = SystemClock.uptimeMillis();  // start time

				customHandler.postDelayed(updateTimerThread, 0);

//				customHandler.removeCallbacks(updatePauseTimerThread);

			}

		});

// PAUSE Button
//
		pauseButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {

				/// count up accum pause time

				pauseStartTime = SystemClock.uptimeMillis();
				// stop counter
				timeSwapBuff += timeInMilliseconds;

				customHandler.postDelayed(updatePauseTimerThread, 0);	// starts pause timer

				customHandler.removeCallbacks(updateTimerThread);   // stops timer

			}
		});



// LEG Button  /////////////////////////

          cp_button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {


                cumul_time = legA_time + time_vary;

             legA_time = time_vary;  // secs  //double

                strLegA_time = String.format( "%.3f", legA_time );

                strCumul_time = String.format( "%.3f", cumul_time );


                legA_time_value.setText( strLegA_time );

                legA_avg_spd_Value.setText( str_AvgSpeed );

                Cumul_time_Value.setText(strCumul_time);


				legHandler.postDelayed(LegInfoThread, 0);

            }
        });  /// LEG TIME

// set correction factor

        setCFButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                CF_Text = (EditText) findViewById (R.id.CF_Value);

                CF_Text2 = CF_Text.getText().toString();

                odo_CF =  Double.parseDouble(CF_Text2);

            }
        });

	}  // end of public on create
////////////////////////////////////////////////////////////////////////////////////////////


	public Runnable updateTimerThread = new Runnable() {

		public void run() {
			
//			double deco = .01667;  // add more decimal places?30/(0.5); //
			
		// read in correction factor	
		    CF_Text = (EditText) findViewById (R.id.CF_Value);
	        
		    CF_Text2 = CF_Text.getText().toString();
		    
			odo_CF =  Double.parseDouble(CF_Text2);	
					
// time past			
			timeInMilliseconds = SystemClock.uptimeMillis() - startTime;  // what about cast set?
			
			updatedTime = timeSwapBuff + timeInMilliseconds;  // 
			
			secs_keep = (updatedTime / 1000);

			int secs = (int) (updatedTime / 1000);
			
			int mins = secs / 60;

			secs = secs % 60;

			int milliseconds = (int) (updatedTime % 1000);
			
			timerValue.setText("" + mins + ":"
					+ String.format("%02d", secs) + ":"
					+ String.format("%03d", milliseconds));
	        	        
// Decimal Minutes			
			decmins = (updatedTime / 1000) * deco;

	        String str = String.format("%.3f", decmins); 
	               
			deccoValue.setText(str); 
			
// Odometer Reading	   // BIG TIME IMPORTANT
			
			good_odo = (updatedTime/1000) * ((CAST*odo_CF)/3600) + cast_chg_odo;
			
			// secs since last cast reset

			if (good_odo < 10) {
				odo_string = String.format("0" +"%.2f", good_odo);  }

			 else {
		        odo_string = String.format("%.2f", good_odo);  }        
	 
	        odoValue.setText(odo_string); 
	        
	        // keep odo reading
	        
// CAST	Readout        
	        String curcast = String.format("%.1f", CAST);  

			curCASTValue.setText(curcast); 
	        
			customHandler.postDelayed(this, 0);		
					
		}
	};
	

// PAUSE TIMER COUNT
	public Runnable updatePauseTimerThread = new Runnable() {	

			public void run() {
				
					PtimeInMilliseconds = SystemClock.uptimeMillis() - pauseStartTime;  // e
	
						updatedPauseTime = PtimeInMilliseconds;  // ge

						int psecs = (int) (updatedPauseTime / 1000);
						
						int pmins = psecs / 60;
						psecs = psecs % 60;
	
						pauseTimeValue.setText(pmins + ":"	+ String.format("%02d", psecs));						

						customHandler.postDelayed(this, 0);		        
			}

		};

    public Runnable LegInfoThread = new Runnable() {

        public void run() {

            legA_time_value.setText(strLegA_time);

        }

    };


//	// LOCATION LISTENER a listener that responds to location updates
    LocationListener locationListener = new LocationListener() {  /// ALWAYS ON  EVERY SECOND

            public void onLocationChanged(Location location) {

                Location loc_2 = new Location( "Two" );
                loc_2.setLatitude( lastLat );
                loc_2.setLongitude( lastLon );

                double lat = location.getLatitude();
                double lng = location.getLongitude();

                String curLat = String.format( "%.4f", lat );
                String curLng = String.format( "%.4f", lng );

                ln.setText( curLng );
                lt.setText( curLat );

                Location loc_1 = new Location( "One" );
                loc_1.setLatitude( lat );
                loc_1.setLongitude( lng );

                double distance_Trvl = loc_2.distanceTo( loc_1 );

                if (FirstRun < 2) {
                    distance_Trvl = 0;
                    long Start_time = location.getTime();
                    avg_speed = 0;
                    totalSpeed = 0;
                    FirstRun = 2;
                }

                totalDistance = totalDistance + distance_Trvl; // meters

                lastLat = lat;
                lastLon = lng;

                totalDistance_mile = totalDistance * 0.000621371192; // meters to miles

                String strDist = String.format( "%.3f", totalDistance_mile ); //

                dist_Value.setText( strDist + " mile" );


                double speed = (double) (location.getSpeed()); // m/s

                String strSpeed = String.format( "%.2f", speed * ms_to_mph );

                curr_Speed.setText( strSpeed + " mph" );

                totalSpeed = avg_speed + speed;

                avg_speed = totalSpeed / 2;

                str_AvgSpeed = String.format( "%.2f", avg_speed * ms_to_mph );

                Avg_Speed.setText( str_AvgSpeed + " av mph" );

/// TIME
//                timeInMilliseconds2 = SystemClock.uptimeMillis() - startTime;  // what about cast set?
//
//                updatedTime2 = timeSwapBuff + timeInMilliseconds2;  //
//
//                secs_keep2 = (updatedTime2 / 1000);
//
//                decmins2 = (updatedTime2 / 1000) * deco;

                String strSecs = String.format( "%.3f", decmins );

                curr_Secs.setText( strSecs + " mins" );

// actual vs expected time
//                double exp_time = (totalDistance_mile / CAST) * 60*60; //secs

                double CAST_ms = CAST / ms_to_mph;

                double exp_time = (totalDistance / CAST_ms); //sec

                String str_exp_time = String.format( "%.3f", exp_time );

                exp_time_Value.setText( str_exp_time + " exp" );


                actual_time = (totalDistance / avg_speed); //secs

                String str_act_time = String.format( "%.3f", actual_time );

                actual_time_Value.setText( str_act_time + " act" );


                time_vary = exp_time - actual_time; // sec ///IMPORTANT

                String str_time_vary = String.format( "%.3f", time_vary );

                if (time_vary > 0) {
                    time_vary_Value.setTextColor( Color.BLUE );
                }  // too fast
                else {

                    time_vary_Value.setTextColor( Color.GREEN );
                } // too slow

                time_vary_Value.setText( str_time_vary + " secs" );


//                String str_time_vary = String.format( "%.3f", mins2 +":"+ String.format("%02d", secs2));

//                String str_time_vary = String.format("" + mins2 + ":"+ String.format("%02d", secs2) + ":"
//                        + String.format("%03d", milliseconds2));

            }// on location change



    public void onStatusChanged(String provider, int status,
                                    Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }


        public void onProviderDisabled(String provider) {
        }


    };  // location listener



} // end of activity
