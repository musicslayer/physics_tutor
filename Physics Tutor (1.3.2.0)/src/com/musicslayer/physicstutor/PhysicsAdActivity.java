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

//import com.google.ads.*;
//import com.admob.android.ads.AdManager;
//import com.admob.android.ads.AdView;
public class PhysicsAdActivity extends PhysicsActivity {
	private Handler tHandler = new Handler();
	//private final static String AD_CLICK = "ad_click";
	private final static String PROCEED_CLICK = "proceed_click";
	//private final static String AD_BYPASS = "ad_bypass";
	private LocalyticsSession localyticsSession;
	Date nag1, nag2;
	TextView diff, message;
	//AdView adr;
	Button proceed;
	Long timer;
	
	int i, secOfNag, adType;
	
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
    	//tHandler.removeCallbacks(checkAd);
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
	return;
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
		
		/*
		AdManager.setTestDevices(new String[]{  
			AdManager.TEST_EMULATOR,
	    	"242B06F88823F5C9643AA6E6F27CFE8A", 
	    	});
	    */
			nag1=new Date();
			diff=(TextView)findViewById(R.id.diff);
			message=(TextView)findViewById(R.id.message);
			//adr = (AdView)findViewById(R.id.adr);
			proceed=(Button)findViewById(R.id.Button_proceed);
			secOfNag=40;
			message.setText("You must wait 40 seconds to proceed.\n\nTo bypass this screen for a week, click the ad on the menu screen.\n"); 
			/*
			adType=0;
	    	if (adType==0)
	    	{
	    		adr.setVisibility(0);
	    	}
	    	else if (adType==-1)
	    	{
	    		PhysicsActivity.adTimer=new Date();
	    		if (answerChoice==0)
				{
				startActivity(new Intent(PhysicsAdActivity.this, PhysicsSolverSplitActivity.class));
				}
				else
				{
				startActivity(new Intent(PhysicsAdActivity.this, PhysicsSolverActivity.class));	
				}
	    	}
	    	*/
	    	tHandler.removeCallbacks(updateTask);
            tHandler.postDelayed(updateTask, 0);
			//tHandler.removeCallbacks(checkAd);
			//tHandler.postDelayed(checkAd, 5000);
        /*    
	    adr.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	        	localyticsSession.tagEvent(PhysicsAdActivity.AD_CLICK);
	        	PhysicsActivity.adTimer=new Date();
	        	//
	        	if (bypasses<=90)
	        	{
	        		bypasses+=10;
	        	}
	        	else
	        	{
	        		bypasses=100;
	        	}
	        	//
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
	    );
	    */
	    /*
	    adrFox.setOnClickListener(new View.OnClickListener() {
	    		public void onClick(View v) {
	        	PhysicsActivity.adTimer=new Date();
	        	startActivity(new Intent(PhysicsAdActivity.this, PhysicsSolverActivity.class)); 
	        }    	
	        }
	    );
	    */
	    
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
	        }
	    );
	    
    
    
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
 	/*
 	private Runnable checkAd = new Runnable() {
  	   public void run() {
  		 if (adr.hasAd()==true)
		   {
  		   message.setText("You must click the ad to proceed or wait 40 seconds."); 
  		   tHandler.removeCallbacks(checkAd);
  		   //diff.setText("You must click the ad to proceed.");
  		   //tHandler.removeCallbacks(updateTask);
  		   //tHandler.postDelayed(updateTask, 0);
		   }
  		 
  		else if (adr.hasAd()==false && bypasses>0)
		{
  			bypasses--;
  			localyticsSession.tagEvent(PhysicsAdActivity.AD_BYPASS);
  			tHandler.removeCallbacks(checkAd);
  			if (answerChoice==0)
			{
			startActivity(new Intent(PhysicsAdActivity.this, PhysicsSolverSplitActivity.class));
			}
			else
			{
			startActivity(new Intent(PhysicsAdActivity.this, PhysicsSolverActivity.class));	
			}
  			//Toast.makeText(getApplicationContext(), "Bypass used.\n"+Integer.toString(bypasses)+" bypasses remaining.", 0).show();
		}
		
  		else
  		{
  			message.setText("You are out of bypasses. You must wait 40 seconds."); 
   		    tHandler.removeCallbacks(checkAd);
   		    //tHandler.removeCallbacks(updateTask);
            //tHandler.postDelayed(updateTask, 0);
  		}
  	   }   
  	};
  	*/
}