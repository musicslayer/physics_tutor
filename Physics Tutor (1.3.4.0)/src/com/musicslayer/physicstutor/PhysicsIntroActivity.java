package com.musicslayer.physicstutor;

import android.graphics.LightingColorFilter;
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
	Date nag1;
	LinearLayout L1;
	Button seeAd, problems, constants, settings, help, email;
	AdView adr;
    LightingColorFilter fPurple;
    Intent emailIntent;
	
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
    }
	
	@Override
	public void onBackPressed() {
		toast.show();
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
        
        toast=Toast.makeText(getApplicationContext(), "Press HOME to exit this application.", 0);
        L1=(LinearLayout) findViewById(R.id.LinearLayout01);
        seeAd=(Button) findViewById(R.id.Button_Display);
        adFreeTimer=(TextView) findViewById(R.id.AdFreeTimer);
        problems = (Button) findViewById(R.id.Button_Problems);
        constants = (Button) findViewById(R.id.Button_Constants);
        settings = (Button) findViewById(R.id.Button_Settings);
        help = (Button) findViewById(R.id.Button_Help);
        email = (Button) findViewById(R.id.Button_Email);
        fPurple = new LightingColorFilter(0xFFFFFF, 0xFF00FF);
        emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"musicslayer@gmail.com", ""});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Physics Tutor - Bug Report");

        tHandler.removeCallbacks(updateTask);
        tHandler.postDelayed(updateTask, 0);
        email.invalidate();
        email.getBackground().setColorFilter(fPurple);

        if (test)
        {
            seeAd.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    localyticsSession.tagEvent(PhysicsIntroActivity.FAKE_AD_REQUEST);
                    PhysicsActivity.adTimer=new Date();
                    startActivity(new Intent(PhysicsIntroActivity.this, PhysicsIntroActivity.class));
            }});
        }
        else
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
                        }});
            }});
        }
        
        problems.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startActivity(new Intent(PhysicsIntroActivity.this, PhysicsProblemsActivity.class));    
            }
        });
            
        constants.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startActivity(new Intent(PhysicsIntroActivity.this, PhysicsConstantsActivity.class));
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

        email.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(emailIntent);
            }
        });
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