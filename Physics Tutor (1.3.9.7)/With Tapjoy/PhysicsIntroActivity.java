package com.musicslayer.physicstutor;

import android.net.Uri;
import com.crittercism.app.Crittercism;
import com.tapjoy.*;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class PhysicsIntroActivity extends PhysicsActivity implements TapjoyNotifier, TapjoySpendPointsNotifier {
	final private static String AD_CLICK = "ad_click";
    final private static String AD_OFFER_COMPLETE = "ad_offer_complete";

    private Toast toast1, toast2, toast3, toast4, toast5;
    private CustomLinearLayout L1A;
    private CustomTextView adFreeTimer;
    private CustomButton seeAd, earnPoints;

    private long secSinceAd;
    private int point_total;
    private boolean noOffers=true;
	
    @Override
    public void onPause() 
    {
    	super.onPause();
        MAIN.removeCallbacks(updateTask);
        MAIN.removeCallbacks(taptap);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        MAIN.postDelayed(updateTask, 0);
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

    public void myOnBackPressed()
    {
        toast1.show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Crittercism.init(getApplicationContext(), CRITTERCISM);
        TapjoyConnect.requestTapjoyConnect(this, "bb205083-1045-43dd-bb6c-142831f9c675", "obvnQcnjQYzj0lf1ei4o");
        TapjoyConnect.getTapjoyConnectInstance().cacheVideos();
        MAIN=makeIntro();
        setContentView(MAIN);
        MAIN.removeCallbacks(updateTask);
        MAIN.postDelayed(updateTask, 0);
        setTitle("Physics Tutor");

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            L1A.setOrientation(0);
        }
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            L1A.setOrientation(1);
        }
    }

    private CustomLinearLayout makeIntro () {
        final CustomLinearLayout L1, L2A, L2B, L2AA, L2AB, L2BA, L2BB;
        final CustomButton problems, constants, settings, help, survey, email;

        L1=new CustomLinearLayout(this,-1,-1,1);
        L1A=new CustomLinearLayout(this,-1,-2,0);
        L2A=new CustomLinearLayout(this,-2,-2,1);
        L2AA=new CustomLinearLayout(this,-2,-2,0);

        problems=new CustomButton(this,150,100,"PROBLEMS",22,R.color.myBlue);
        problems.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                startActivity(new Intent(PhysicsIntroActivity.this, PhysicsProblemsActivity.class));
            }
        });

        constants=new CustomButton(this,150,100,"CONSTANTS",22,R.color.myRed);
        constants.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                startActivity(new Intent(PhysicsIntroActivity.this, PhysicsConstantsActivity.class));
            }
        });

        L2AA.addView(problems);
        L2AA.addView(constants);

        L2AB=new CustomLinearLayout(this,-2,-2,0);

        settings=new CustomButton(this,150,100,"SETTINGS",22,R.color.myRed);
        settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                startActivity(new Intent(PhysicsIntroActivity.this, PhysicsSettingsActivity.class));
            }
        });

        help=new CustomButton(this,150,100,"HELP",22,R.color.myRed);
        help.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                startActivity(new Intent(PhysicsIntroActivity.this, PhysicsHelpActivity.class));
            }
        });

        L2AB.addView(settings);
        L2AB.addView(help);
        L2A.addView(L2AA);
        L2A.addView(L2AB);

        L2B=new CustomLinearLayout(this,-1,-2,1);
        L2BA=new CustomLinearLayout(this,-2,-2,0);

        survey=new CustomButton(this,150,100,"FEEDBACK SURVEY",22,R.color.myPurple);
        survey.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                startActivity(new Intent(PhysicsIntroActivity.this, PhysicsSurveyActivity.class));
            }
        });

        email=new CustomButton(this,150,100,"EMAIL DEVELOPER",22,R.color.myPurple);
        email.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","musicslayer@gmail.com", null));
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Physics Tutor - Bug Report/Feedback");
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

        adFreeTimer=new CustomTextView(this,14);

        L2B.addView(L2BA);
        L2B.addView(adFreeTimer);
        L1A.addView(L2A);
        L1A.addView(L2B);

        L2BB=new CustomLinearLayout(this,-2,-2,0);

        seeAd=new CustomButton(this,150,70,"DISPLAY AD",22,R.color.myGreen);
        seeAd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                localyticsSession.tagEvent(PhysicsIntroActivity.AD_CLICK);
                earnPoints.setEnabled(true);
                TapjoyConnect.getTapjoyConnectInstance().showOffers();
            }
        });

        earnPoints=new CustomButton(this,150,70,"GET DAYS",22,R.color.myGreen);
        earnPoints.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                earnPoints.setEnabled(false);
                toast3.show();
                MAIN.postDelayed(taptap,0);
            }
        });

        L2BB.addView(seeAd);
        L2BB.addView(earnPoints);
        L1.addView(L1A);
        L1.addView(L2BB);

        toast1=Toast.makeText(getApplicationContext(), "Press HOME to exit this application.", 0);
        toast2=Toast.makeText(getApplicationContext(), "Your device does not have an email application properly installed and set up.", 0);
        toast3=Toast.makeText(getApplicationContext(), "Searching for completed offers......", 0);
        toast4=Toast.makeText(getApplicationContext(), "Nag Free Days Rewarded.", 0);
        toast5=Toast.makeText(getApplicationContext(), "No days have been earned. If you have recently completed an offer, make sure you have internet access, wait a few seconds, and try again.", 1);

        return L1;
    }

    final private Runnable taptap = new Runnable() {
        private int i=0;
        public void run() {
            if (i++<5)
            {
                TapjoyConnect.getTapjoyConnectInstance().getTapPoints(PhysicsIntroActivity.this);
                if (point_total>0)
                {
                    TapjoyConnect.getTapjoyConnectInstance().spendTapPoints(point_total, PhysicsIntroActivity.this);
                }
                MAIN.postDelayed(taptap, 2000);
            }
            else
            {
                i=0;
                earnPoints.setEnabled(true);
                if (noOffers)
                {
                    toast5.show();
                }
                noOffers=true;
            }
        }
    };

    final private Runnable updateTask = new Runnable() {
        public void run() {
            secSinceAd=(System.currentTimeMillis()-adTimer)/1000;
            if (adFreeSeconds-secSinceAd<864000000)
            {
                seeAd.setEnabled(true);
            }
            else
            {
                seeAd.setEnabled(false);
            }
            if (adFreeSeconds-secSinceAd>0)
            {
                final long days=(adFreeSeconds-secSinceAd)/86400;
                final long hours=((adFreeSeconds-secSinceAd)/3600)%24;
                final long min=((adFreeSeconds-secSinceAd)/60)%60;
                final long sec=(adFreeSeconds-secSinceAd)%60;
                adFreeTimer.setText("Nag free time:\n"+Long.toString(days)+" days, "+Long.toString(hours)+" hours, "+Long.toString(min)+" minutes, "+Long.toString(sec)+" seconds\n\n"+"Thank you for supporting this app.\nHit 'DISPLAY AD' to earn even more nag free time.\n\n*These new ads are an experiment. Feedback is welcome.*");
                MAIN.postDelayed(updateTask, 1000);
            }
            else
            {
                adFreeTimer.setText("Nag free time:\nNone\n\nHit 'DISPLAY AD' to earn some nag free time.\n\n*FIRST TIME USERS: Hit 'GET DAYS' now to get 3 free days!*");
                MAIN.removeCallbacks(updateTask);
            }
        }
    };

    public void getUpdatePoints(String currencyName, int pointTotal){point_total = pointTotal;}
    public void getUpdatePointsFailed(String error){}
    public void getSpendPointsResponseFailed(String error){}
    public void getSpendPointsResponse(String currencyName, int pointTotal)
    {
        localyticsSession.tagEvent(PhysicsIntroActivity.AD_OFFER_COMPLETE);
        noOffers=false;
        secSinceAd=Math.min((System.currentTimeMillis()-adTimer)/1000,adFreeSeconds);
        adFreeSeconds+=point_total*86400-secSinceAd;
        point_total=0;
        adTimer=System.currentTimeMillis();
        toast4.show();
        MAIN.postDelayed(updateTask, 0);
        savePrefs();
    }
}