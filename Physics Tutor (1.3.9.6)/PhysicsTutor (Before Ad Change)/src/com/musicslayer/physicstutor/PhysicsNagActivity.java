package com.musicslayer.physicstutor;

import java.util.Date;

import android.view.ViewGroup;
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
    private LinearLayout L, L1, LA, LB;
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
            L.setOrientation(0);
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            L.setOrientation(1);
        }
    }

	@Override
	public void onBackPressed() {
        startActivity(new Intent(PhysicsNagActivity.this, PhysicsInputActivity.class));
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(null);
		this.localyticsSession = new LocalyticsSession(this.getApplicationContext(),LOCALYTICS);
		this.localyticsSession.open();
		this.localyticsSession.upload();
        L=makeNag();
        setContentView(L);
        setTitle("Physics Tutor - Nag Screen");
		loadPrefs();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            L.setOrientation(0);
        }
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            L.setOrientation(1);
        }
    }

    LinearLayout makeNag()
    {
        L1=new LinearLayout(this);
        L1.setOrientation(1);
        L1.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));

        LA=new LinearLayout(this);
        LA.setOrientation(1);
        LA.setLayoutParams(new ViewGroup.LayoutParams(dpToPx((int)(300*scale)), -2));

        message=new TextView(this);
        message.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        message.setTextSize(1,22*scale);
        message.setText("You must wait "+Integer.toString(secOfNag)+ " seconds to proceed.\n\nTo bypass this screen for a week, click the ad on the menu screen.\n");

        LA.addView(message);

        LB=new LinearLayout(this);
        LB.setOrientation(1);
        LB.setLayoutParams(new ViewGroup.LayoutParams(dpToPx((int)(300*scale)), -2));

        diff=new TextView(this);
        diff.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        diff.setTextSize(1,22*scale);

        proceed=new Button(this);
        proceed.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        proceed.setEnabled(false);
        proceed.setText("PROCEED");
        proceed.setTextSize(1,22*scale);
        proceed.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                localyticsSession.tagEvent(PhysicsNagActivity.PROCEED_CLICK);
                startActivity(new Intent(PhysicsNagActivity.this, PhysicsSolverActivity.class));
            }
        });

        LB.addView(diff);
        LB.addView(proceed);
        L1.addView(LA);
        L1.addView(LB);

        nag1=new Date();
        tHandler.removeCallbacks(updateTask);
        tHandler.postDelayed(updateTask, 0);

        return L1;
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