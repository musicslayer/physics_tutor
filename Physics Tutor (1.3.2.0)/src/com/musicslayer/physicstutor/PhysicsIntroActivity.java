package com.musicslayer.physicstutor;

import com.Localytics.android.*;
import com.google.ads.*;
import java.util.Date;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PhysicsIntroActivity extends PhysicsActivity {
	private final static String AD_CLICK = "ad_click";
	private final static String AD_REQUEST = "ad_request";
	private final static String FAKE_AD_REQUEST = "fake_ad_request";
	private Handler tHandler = new Handler();
	private LocalyticsSession localyticsSession;
	Toast toast;
	TextView adFreeTimer;
	Date nag1, nag2;	
	LinearLayout L1;
	Button seeAd, problems, constants, settings, help;
	AdRequest manager;
	AdView adr;
	
	int i;
	long sec, min, hours, days, secSinceAd;
	
	@Override
    public void onDestroy() 
    {
        this.localyticsSession.upload();
        super.onDestroy();
    }
	
    @Override
    public void onPause() 
    {
    	this.localyticsSession.close();
    	super.onPause();
    	tHandler.removeCallbacks(updateTask);
    	savePrefs();
    }
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ;
    }
	
	@Override
	public void onBackPressed() {
		toast.show();
	return;
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro); 
        setTitle("Physics Tutor");
        this.localyticsSession = new LocalyticsSession(this.getApplicationContext(),LOCALYTICS);
		this.localyticsSession.open();
		this.localyticsSession.upload();
        loadPrefs();
        
        tHandler.removeCallbacks(updateTask);
        tHandler.postDelayed(updateTask, 0); 
        
        toast=Toast.makeText(getApplicationContext(), "Press HOME to exit this application.", 0);
        L1=(LinearLayout) findViewById(R.id.LinearLayout01);
        seeAd=(Button) findViewById(R.id.Button_Display);
        adFreeTimer=(TextView) findViewById(R.id.AdFreeTimer);
        problems = (Button) findViewById(R.id.Button_Problems);
        constants = (Button) findViewById(R.id.Button_Constants);
        settings = (Button) findViewById(R.id.Button_Settings);
        help = (Button) findViewById(R.id.Button_Help);
        
        problems.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startActivity(new Intent(PhysicsIntroActivity.this, PhysicsProblemsActivity.class));    
            }
        });
            
        constants.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startActivity(new Intent(PhysicsIntroActivity.this, PhysicsAssumptionsActivity.class));    
            }
        });
        
        settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startActivity(new Intent(PhysicsIntroActivity.this, PhysicsSettingsActivity.class));    
            }
        });
        
        help.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startActivity(new Intent(PhysicsIntroActivity.this, PhysicsHelpActivity.class));    
            }
        });
        if (test==false)
        {
        seeAd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	localyticsSession.tagEvent(PhysicsIntroActivity.AD_REQUEST);
            	adr=new AdView(PhysicsIntroActivity.this, AdSize.BANNER, "a14debddae9593b");
            	seeAd.setVisibility(8);  
            	L1.addView(adr);
            	adr.loadAd(new AdRequest());
            	adr.setOnClickListener(new View.OnClickListener() {
        	        public void onClick(View v) {
        	        	localyticsSession.tagEvent(PhysicsIntroActivity.AD_CLICK);
        	        	PhysicsActivity.adTimer=new Date();
        	        	startActivity(new Intent(PhysicsIntroActivity.this, PhysicsIntroActivity.class));
        	        }    	
        	        }
        	    );
            }
        });
        }
        else if (test==true)
        {
        
        seeAd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	localyticsSession.tagEvent(PhysicsIntroActivity.FAKE_AD_REQUEST);
        	    PhysicsActivity.adTimer=new Date();
        	    startActivity(new Intent(PhysicsIntroActivity.this, PhysicsIntroActivity.class));
        	        
            }
        });
        }
    }
        
        private Runnable updateTask = new Runnable() {
      	   public void run() {
      		 nag1=new Date();
             secSinceAd=(nag1.getTime()-adTimer.getTime())/1000;
             days=(adFreeTimeSec-secSinceAd)/86400;
             hours=((adFreeTimeSec-secSinceAd)/3600)%24;
             min=((adFreeTimeSec-secSinceAd)/60)%60;
             sec=(adFreeTimeSec-secSinceAd)%60;
             if (adFreeTimeSec-secSinceAd>0)
             {
             adFreeTimer.setText("Nag free time:\n"+Long.toString(days)+" days, "+Long.toString(hours)+" hours, "+Long.toString(min)+" minutes, and "+Long.toString(sec)+" seconds\n\n"+"Thank you for supporting this app.\nYou may click the ad again to reset the timer.");
             tHandler.postDelayed(updateTask, 1000);
             }
             else
             {
             adFreeTimer.setText("Nag free time:\nNone\n\nClick the ad to remove the nag screen for a week.\n");
             tHandler.removeCallbacks(updateTask);
             }
      	   }
      	};

}