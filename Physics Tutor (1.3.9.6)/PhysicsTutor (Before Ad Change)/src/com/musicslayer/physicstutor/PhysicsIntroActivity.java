package com.musicslayer.physicstutor;

import com.crittercism.app.Crittercism;
import com.tapjoy.*;
import android.graphics.PorterDuff;
import android.view.ViewGroup;
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

public class PhysicsIntroActivity extends PhysicsActivity implements AdListener, TapjoyNotifier, TapjoyEarnedPointsNotifier, TapjoySpendPointsNotifier, TapjoyAwardPointsNotifier {
	final private static String AD_CLICK = "ad_click";
	final private static String AD_REQUEST_AUTO = "ad_request_auto";
    final private static String AD_REQUEST_BY_BUTTON = "ad_request_by_button";
    final private static String POTENTIAL_AD = "potential_ad";

    private LocalyticsSession localyticsSession;
	private Handler tHandler = new Handler();
    private Toast toast1, toast2;
    private AdRequest adR;
    private Intent emailIntent;
    private Date nag1;
    private LinearLayout L, L1, L1A, L2A, L2B, L2AA, L2AB, L2BA;
    private TextView adFreeTimer;
    private Button seeAd, problems, constants, settings, help, survey, email;
    private AdView adV;

	long sec, min, hours, days, secSinceAd;

    boolean earnedPoints = false;
    int point_total;
    String currency_name;
	
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
        TapjoyConnect.getTapjoyConnectInstance().getTapPoints(this);
        if (point_total>0)
        {
            adFreeTimeSec+=point_total*86400;
            adTimer=new Date();
            TapjoyConnect.getTapjoyConnectInstance().spendTapPoints(point_total, this);
        }
    }

	@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            L1A.setOrientation(0);
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            L1A.setOrientation(1);
        }
    }

    @Override
	public void onBackPressed() {
        toast1.show();
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Crittercism.init(getApplicationContext(), CRITTERCISM);
        TapjoyConnect.requestTapjoyConnect(this, "bb205083-1045-43dd-bb6c-142831f9c675", "obvnQcnjQYzj0lf1ei4o");
        TapjoyConnect.getTapjoyConnectInstance().cacheVideos();
        TapjoyConnect.getTapjoyConnectInstance().setEarnedPointsNotifier(this);
        this.localyticsSession = new LocalyticsSession(this.getApplicationContext(),LOCALYTICS);
		this.localyticsSession.open();
		this.localyticsSession.upload();
        L=makeIntro();
        setContentView(L);
        setTitle("Physics Tutor");
        loadPrefs();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            L1A.setOrientation(0);
        }
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            L1A.setOrientation(1);
        }
    }

    LinearLayout makeIntro () {
        L1=new LinearLayout(this);
        L1.setOrientation(1);
        L1.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));

        L1A=new LinearLayout(this);
        L1A.setOrientation(0);
        L1A.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));

        L2A=new LinearLayout(this);
        L2A.setOrientation(1);
        L2A.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        L2AA=new LinearLayout(this);
        L2AA.setOrientation(0);
        L2AA.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        problems=new Button(this);
        problems.setText("PROBLEMS");
        problems.setTextSize(1,22*scale);
        problems.setLayoutParams(new ViewGroup.LayoutParams(dpToPx((int)(150*scale)), dpToPx((int)(100*scale))));
        problems.invalidate();
        problems.getBackground().setColorFilter(0xFF0A85FF, PorterDuff.Mode.MULTIPLY);
        problems.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(PhysicsIntroActivity.this, PhysicsProblemsActivity.class));
            }
        });

        constants=new Button(this);
        constants.setText("CONSTANTS");
        constants.setTextSize(1,22*scale);
        constants.setLayoutParams(new ViewGroup.LayoutParams(dpToPx((int)(150*scale)), dpToPx((int)(100*scale))));
        constants.invalidate();
        constants.getBackground().setColorFilter(0xFFFF4747, PorterDuff.Mode.MULTIPLY);
        constants.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(PhysicsIntroActivity.this, PhysicsConstantsActivity.class));
            }
        });

        L2AA.addView(problems);
        L2AA.addView(constants);

        L2AB=new LinearLayout(this);
        L2AB.setOrientation(0);
        L2AB.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        settings=new Button(this);
        settings.setText("SETTINGS");
        settings.setTextSize(1,22*scale);
        settings.setLayoutParams(new ViewGroup.LayoutParams(dpToPx((int)(150*scale)), dpToPx((int)(100*scale))));
        settings.invalidate();
        settings.getBackground().setColorFilter(0xFFFF4747, PorterDuff.Mode.MULTIPLY);
        settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(PhysicsIntroActivity.this, PhysicsSettingsActivity.class));
            }
        });

        help=new Button(this);
        help.setText("HELP");
        help.setTextSize(1,22*scale);
        help.setLayoutParams(new ViewGroup.LayoutParams(dpToPx((int)(150*scale)), dpToPx((int)(100*scale))));
        help.invalidate();
        help.getBackground().setColorFilter(0xFFFF4747, PorterDuff.Mode.MULTIPLY);
        help.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(PhysicsIntroActivity.this, PhysicsHelpActivity.class));
            }
        });

        L2AB.addView(settings);
        L2AB.addView(help);
        L2A.addView(L2AA);
        L2A.addView(L2AB);

        L2B=new LinearLayout(this);
        L2B.setOrientation(1);
        L2B.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));

        L2BA=new LinearLayout(this);
        L2BA.setOrientation(0);
        L2BA.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        survey=new Button(this);
        survey.setText("FEEDBACK SURVEY");
        survey.setTextSize(1,22*scale);
        survey.setLayoutParams(new ViewGroup.LayoutParams(dpToPx((int)(150*scale)), dpToPx((int)(100*scale))));
        survey.invalidate();
        survey.getBackground().setColorFilter(0xFFFF0AFF, PorterDuff.Mode.MULTIPLY);
        survey.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(PhysicsIntroActivity.this, PhysicsSurveyActivity.class));
            }
        });

        email=new Button(this);
        email.setText("EMAIL DEVELOPER");
        email.setTextSize(1,22*scale);
        email.setLayoutParams(new ViewGroup.LayoutParams(dpToPx((int)(150*scale)), dpToPx((int)(100*scale))));
        email.getBackground().setColorFilter(0xFF0A85FF, PorterDuff.Mode.MULTIPLY);
        email.invalidate();
        email.getBackground().setColorFilter(0xFFFF0AFF, PorterDuff.Mode.MULTIPLY);
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

        L2BA.addView(survey);
        L2BA.addView(email);

        adFreeTimer=new TextView(this);
        adFreeTimer.setTextSize(1,14*scale);
        adFreeTimer.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        L2B.addView(L2BA);
        L2B.addView(adFreeTimer);
        L1A.addView(L2A);
        L1A.addView(L2B);

        seeAd=new Button(this);
        seeAd.setText("DISPLAY AD");
        seeAd.setTextSize(1,22*scale);
        seeAd.setLayoutParams(new ViewGroup.LayoutParams(dpToPx((int)(150*scale)), dpToPx((int)(70*scale))));
        seeAd.getBackground().setColorFilter(0xFF0A85FF, PorterDuff.Mode.MULTIPLY);

        adV=new AdView(PhysicsIntroActivity.this, AdSize.BANNER, "a14debddae9593b");
        adV.setLayoutParams(new ViewGroup.LayoutParams(dpToPx(AdSize.BANNER.getWidth()), dpToPx(AdSize.BANNER.getHeight())));

        L1.addView(L1A);
        L1.addView(seeAd);
        L1.addView(adV);

        if (alwaysShowAds)
        {
            adR=new AdRequest();
            localyticsSession.tagEvent(PhysicsIntroActivity.AD_REQUEST_AUTO);
            seeAd.setVisibility(8);
            if (test)
            {
                adR.addTestDevice(AdRequest.TEST_EMULATOR);
                adR.addTestDevice("242B06F88823F5C9643AA6E6F27CFE8A");
                adR.addTestDevice("6DC545172C8C1895787C12C21307D865");
                adR.addTestDevice("A49B8C97551DABED06523BA42E3E9CAC");
                adR.addTestDevice("6752DD3B3E8C4169AC08C9BF2DEE4755");
            }
            adV.loadAd(adR);
            adV.setAdListener(PhysicsIntroActivity.this);
        }
        else
        {
            seeAd.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    /*
                    adR=new AdRequest();
                    localyticsSession.tagEvent(PhysicsIntroActivity.AD_REQUEST_BY_BUTTON);
                    seeAd.setVisibility(8);
                    if (test)
                    {
                        adR.addTestDevice("242B06F88823F5C9643AA6E6F27CFE8A");
                        adR.addTestDevice("6DC545172C8C1895787C12C21307D865");
                        adR.addTestDevice("A49B8C97551DABED06523BA42E3E9CAC");
                        adR.addTestDevice("6752DD3B3E8C4169AC08C9BF2DEE4755");
                    }
                    adV.loadAd(adR);
                    adV.setAdListener(PhysicsIntroActivity.this);
                    */
                    TapjoyConnect.getTapjoyConnectInstance().showOffers();
                    //TapjoyConnect.getTapjoyConnectInstance().getDisplayAd(TapjoyDisplayAdNotifier notifier);
                    //setDisplayAdSize(TapjoyDisplayAdSize.TJC_DISPLAY_AD_SIZE_768X90 );
                }
            });
        }

        toast1=Toast.makeText(getApplicationContext(), "Press HOME to exit this application.", 0);
        toast2=Toast.makeText(getApplicationContext(), "Your device does not have an email application.", 0);
        emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"musicslayer@gmail.com", ""});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Physics Tutor - Bug Report");
        tHandler.removeCallbacks(updateTask);
        tHandler.postDelayed(updateTask, 0);
        localyticsSession.tagEvent(PhysicsIntroActivity.POTENTIAL_AD);

        return L1;
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
    }

    public void getUpdatePoints(String currencyName, int pointTotal)
    {
        currency_name = currencyName;
        point_total = pointTotal;
        if (earnedPoints)
        {
            earnedPoints = false;
        }
        else
        {
        }

    }
    public void earnedTapPoints(int amount)
    {
        earnedPoints = true;
    }
    public void getSpendPointsResponseFailed(String error)
    {
    }
    public void getAwardPointsResponse(String currencyName, int pointTotal)
    {
    }
    public void getAwardPointsResponseFailed(String error)
    {
    }
    public void getSpendPointsResponse(String currencyName, int pointTotal)
    {
    }
    public void getUpdatePointsFailed(String error)
    {
    }
}