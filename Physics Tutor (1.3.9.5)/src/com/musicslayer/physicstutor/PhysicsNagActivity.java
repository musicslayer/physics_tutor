package com.musicslayer.physicstutor;

import java.util.Date;
import android.widget.LinearLayout;
import com.localytics.android.*;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PhysicsNagActivity extends PhysicsActivity {
    final private int secOfNag=40;

	final private static String PROCEED_CLICK = "proceed_click";
    private LocalyticsSession localyticsSession;
    private Handler tHandler = new Handler();
	private Date nag1, nag2;
	private TextView diff, message;
	private Button proceed;
	private Long timer;
	
	@Override
    public void onDestroy() 
    {
        this.localyticsSession.upload();
        super.onDestroy();
    }
	
	@Override 
	public void onResume() {
	    super.onResume();
        this.localyticsSession.open();
        tHandler.postDelayed(updateTask, 0);
	} 
	
    @Override
    public void onPause() 
    {
    	this.localyticsSession.close();
        this.localyticsSession.upload();
    	super.onPause();
    	savePrefs();
    	tHandler.removeCallbacks(updateTask);
    }
	
	@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
        	((LinearLayout) findViewById(R.id.LinearLayout01)).setOrientation(0);
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
        	((LinearLayout) findViewById(R.id.LinearLayout01)).setOrientation(1);
        }
    }

	@Override
	public void onBackPressed() {
        startActivity(new Intent(PhysicsNagActivity.this, PhysicsInputActivity.class));
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(null);
		setContentView(R.layout.nag);
		setTitle("Physics Tutor - Nag Screen");
		this.localyticsSession = new LocalyticsSession(this.getApplicationContext(),LOCALYTICS);
		this.localyticsSession.open();
		this.localyticsSession.upload();
		loadPrefs();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
        	((LinearLayout) findViewById(R.id.LinearLayout01)).setOrientation(0);
        }
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
        	((LinearLayout) findViewById(R.id.LinearLayout01)).setOrientation(1);
        }

        diff=(TextView)findViewById(R.id.diff);
        message=(TextView)findViewById(R.id.message);
        proceed=(Button)findViewById(R.id.Button_proceed);

        nag1=new Date();
        message.setText("You must wait "+Integer.toString(secOfNag)+ " seconds to proceed.\n\nTo bypass this screen for a week, click the ad on the menu screen.\n");
        tHandler.removeCallbacks(updateTask);
        tHandler.postDelayed(updateTask, 0);
	    
	    proceed.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
                localyticsSession.tagEvent(PhysicsNagActivity.PROCEED_CLICK);
                startActivity(new Intent(PhysicsNagActivity.this, PhysicsSolverActivity.class));
	        }
	    });
    }
	
	private Runnable updateTask = new Runnable() {
 	   public void run() {
           nag2=new Date();
           timer=(nag2.getTime()-nag1.getTime())/1000;
           if (secOfNag-timer<=0)
           {
               diff.setText("You may proceed.");
               proceed.setEnabled(true);
               tHandler.removeCallbacks(updateTask);
           }
           else
           {
               diff.setText(Long.toString(secOfNag-timer)+" seconds remaining.");
               tHandler.postDelayed(updateTask, 1000);
           }
 	   }
 	};
}