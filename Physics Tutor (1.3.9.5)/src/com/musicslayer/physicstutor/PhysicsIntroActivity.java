package com.musicslayer.physicstutor;

import android.graphics.PorterDuff;
import com.localytics.android.*;
import android.content.ActivityNotFoundException;
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

public class PhysicsIntroActivity extends PhysicsActivity implements AdListener {
	final private static String AD_CLICK = "ad_click";
	final private static String AD_REQUEST = "ad_request";
    final private static String POTENTIAL_AD = "potential_ad";
	Handler tHandler = new Handler();
	private LocalyticsSession localyticsSession;
	Toast toast1, toast2;
	TextView adFreeTimer;
	Date nag1;
	LinearLayout L1;
	Button seeAd, problems, constants, settings, help, survey, email;
	AdView adV;
    AdRequest adR;
    Intent emailIntent;

	long sec, min, hours, days, secSinceAd;
	
	@Override
    public void onDestroy() 
    {
        this.localyticsSession.upload();
        if (adV!=null)
        {
            adV.destroy();
        }
        super.onDestroy();
    }
	
    @Override
    public void onPause() 
    {
    	this.localyticsSession.close();
        this.localyticsSession.upload();
    	super.onPause();
    	tHandler.removeCallbacks(updateTask);
    	savePrefs();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        this.localyticsSession.open();
        tHandler.postDelayed(updateTask, 0);
    }

	@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
        	((LinearLayout) findViewById(R.id.LinearLayoutA)).setOrientation(0);
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
        	((LinearLayout) findViewById(R.id.LinearLayoutA)).setOrientation(1);
        }
    }

    @Override
	public void onBackPressed() {
        toast1.show();
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

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
        	((LinearLayout) findViewById(R.id.LinearLayoutA)).setOrientation(0);
        }
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
        	((LinearLayout) findViewById(R.id.LinearLayoutA)).setOrientation(1);
        }

        adR=new AdRequest();
        toast1=Toast.makeText(getApplicationContext(), "Press HOME to exit this application.", 0);
        toast2=Toast.makeText(getApplicationContext(), "Your device does not have an email application.", 0);
        L1=(LinearLayout) findViewById(R.id.LinearLayout01);
        seeAd=(Button) findViewById(R.id.Button_Display);
        adFreeTimer=(TextView) findViewById(R.id.AdFreeTimer);
        adFreeTimer.setTextSize(1,14*scale);
        problems = (Button) findViewById(R.id.Button_Problems);
        constants = (Button) findViewById(R.id.Button_Constants);
        settings = (Button) findViewById(R.id.Button_Settings);
        help = (Button) findViewById(R.id.Button_Help);
        survey = (Button) findViewById(R.id.Button_Survey);
        email = (Button) findViewById(R.id.Button_Email);
        emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"musicslayer@gmail.com", ""});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Physics Tutor - Bug Report");

        tHandler.removeCallbacks(updateTask);
        tHandler.postDelayed(updateTask, 0);
        problems.invalidate();
        problems.getBackground().setColorFilter(0xFF0A85FF, PorterDuff.Mode.MULTIPLY);
        constants.invalidate();
        constants.getBackground().setColorFilter(0xFFFF4747, PorterDuff.Mode.MULTIPLY);
        settings.invalidate();
        settings.getBackground().setColorFilter(0xFFFF4747, PorterDuff.Mode.MULTIPLY);
        help.invalidate();
        help.getBackground().setColorFilter(0xFFFF4747, PorterDuff.Mode.MULTIPLY);
        survey.invalidate();
        survey.getBackground().setColorFilter(0xFFFF0AFF, PorterDuff.Mode.MULTIPLY);
        email.invalidate();
        email.getBackground().setColorFilter(0xFFFF0AFF, PorterDuff.Mode.MULTIPLY);
        localyticsSession.tagEvent(PhysicsIntroActivity.POTENTIAL_AD);

        if (alwaysShowAds)
        {
            adV=new AdView(PhysicsIntroActivity.this, AdSize.BANNER, "a14debddae9593b");
            seeAd.setVisibility(8);
            L1.addView(adV);
            if (test)
            {
                adR.addTestDevice("242B06F88823F5C9643AA6E6F27CFE8A");
                adR.addTestDevice("6DC545172C8C1895787C12C21307D865");
                adR.addTestDevice("A49B8C97551DABED06523BA42E3E9CAC");
            }
            adV.loadAd(adR);
            adV.setAdListener(PhysicsIntroActivity.this);

        }
        else
        {
        seeAd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                localyticsSession.tagEvent(PhysicsIntroActivity.AD_REQUEST);
                adV=new AdView(PhysicsIntroActivity.this, AdSize.BANNER, "a14debddae9593b");
                seeAd.setVisibility(8);
                L1.addView(adV);
                if (test)
                {
                    adR.addTestDevice("242B06F88823F5C9643AA6E6F27CFE8A");
                    adR.addTestDevice("6DC545172C8C1895787C12C21307D865");
                }
                adV.loadAd(adR);
                adV.setAdListener(PhysicsIntroActivity.this);
            }
        });
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

        survey.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startActivity(new Intent(PhysicsIntroActivity.this, PhysicsSurveyActivity.class));
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try
                {
                    startActivity(emailIntent);
                }
                catch (ActivityNotFoundException e)
                {
                    toast2.show();
                }
            }
        });
    }
        
    private Runnable updateTask = new Runnable() {
        public void run() {
            nag1=new Date();
            secSinceAd=(nag1.getTime()-adTimer.getTime())/1000;
            if (adFreeTimeSec-secSinceAd>0)
            {
                days=(adFreeTimeSec-secSinceAd)/86400;
                hours=((adFreeTimeSec-secSinceAd)/3600)%24;
                min=((adFreeTimeSec-secSinceAd)/60)%60;
                sec=(adFreeTimeSec-secSinceAd)%60;
                adFreeTimer.setText("Nag free time:\n"+Long.toString(days)+" days, "+Long.toString(hours)+" hours, "+Long.toString(min)+" minutes, "+Long.toString(sec)+" seconds\n\n"+"Thank you for supporting this app.\nClick the ad again to reset the timer.");
                tHandler.postDelayed(updateTask, 1000);
            }
            else
            {
                adFreeTimer.setText("Nag free time:\nNone\n\nClick the ad to remove the nag screen for a week.\n");
                tHandler.removeCallbacks(updateTask);
            }
       }
    };

    public void onReceiveAd(Ad ad) {
    }

    public void onFailedToReceiveAd(Ad ad, AdRequest.ErrorCode errorCode) {
    }

    public void onPresentScreen(Ad ad) {
    }

    public void onDismissScreen(Ad ad) {
    }

    public void onLeaveApplication(Ad ad) {
        localyticsSession.tagEvent(PhysicsIntroActivity.AD_CLICK);
        adTimer=new Date();
        savePrefs();
        //startActivity(new Intent(PhysicsIntroActivity.this, PhysicsIntroActivity.class));
    }
}