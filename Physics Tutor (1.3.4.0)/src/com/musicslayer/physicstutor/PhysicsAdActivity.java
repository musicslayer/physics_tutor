package com.musicslayer.physicstutor;

import java.util.Date;
import com.Localytics.android.*;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PhysicsAdActivity extends PhysicsActivity {
	private final static String PROCEED_CLICK = "proceed_click";
    private LocalyticsSession localyticsSession;
    private Handler tHandler = new Handler();

	Date nag1, nag2;
	TextView diff, message;
	Button proceed;
	Long timer;
	
	int i, secOfNag;
	
	@Override
    public void onDestroy() 
    {
        this.localyticsSession.upload();
        super.onDestroy();
    }
	
	@Override 
	protected void onResume() { 
	  super.onResume(); 
	} 
	
    @Override
    public void onPause() 
    {
    	this.localyticsSession.close();
    	super.onPause();
    	savePrefs();
    	tHandler.removeCallbacks(updateTask);
    }
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
	
	@Override
	public void onBackPressed() {
		if ("PROJECTILE".equals(type))
        {
		    startActivity(new Intent(PhysicsAdActivity.this, PhysicsProjectileActivity.class));
        }
		else if ("INCLINE".equals(type))
        {
		    startActivity(new Intent(PhysicsAdActivity.this, PhysicsInclineActivity.class));
        }
        else if ("INCLINE_SIMPLE".equals(type))
        {
		    startActivity(new Intent(PhysicsAdActivity.this, PhysicsInclineSimpleActivity.class));
        }
        else
        {
        //TODO Throw an error and/or deal with "null" type.
        }
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(null);
		setContentView(R.layout.ad);
		setTitle("Physics Tutor - Ad Screen");
		this.localyticsSession = new LocalyticsSession(this.getApplicationContext(),LOCALYTICS);
		this.localyticsSession.open();
		this.localyticsSession.upload();
		loadPrefs();

        diff=(TextView)findViewById(R.id.diff);
        message=(TextView)findViewById(R.id.message);
        proceed=(Button)findViewById(R.id.Button_proceed);

        nag1=new Date();
        secOfNag=40;
        message.setText("You must wait 40 seconds to proceed.\n\nTo bypass this screen for a week, click the ad on the menu screen.\n");
        tHandler.removeCallbacks(updateTask);
        tHandler.postDelayed(updateTask, 0);
	    
	    proceed.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	        	nag2=new Date();
	        	timer=(nag2.getTime()-nag1.getTime())/1000;  	
	        	if (timer>=secOfNag)
	        	{
	        		localyticsSession.tagEvent(PhysicsAdActivity.PROCEED_CLICK);
	        		if (answerChoice==0)
	    			{
	    			    startActivity(new Intent(PhysicsAdActivity.this, PhysicsSolverSplitActivity.class));
	    			}
	    			else
	    			{
	    			    startActivity(new Intent(PhysicsAdActivity.this, PhysicsSolverActivity.class));
	    			}
	        	}
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
	      		   tHandler.removeCallbacks(updateTask);
	      		   tHandler.postDelayed(updateTask, 1000);
	      	   }
 	   }
 	};
}