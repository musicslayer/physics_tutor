package com.musicslayer.physicstutor;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

public class PhysicsNagActivity extends PhysicsActivity {
	final private static String NAG_CLICK = "nag_click";
    private CustomTextView diff;
	private CustomButton proceed;

    final private static int secOfNag=40;
    final private long startTime=System.currentTimeMillis();
	
	@Override 
	public void onResume() {
	    super.onResume();
        MAIN.postDelayed(nagTask, 0);
	} 
	
    @Override
    public void onPause() 
    {
    	super.onPause();
        MAIN.removeCallbacks(nagTask);
    }
	
	@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            MAIN.setOrientation(0);
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            MAIN.setOrientation(1);
        }
    }

    public void myOnBackPressed()
    {
        finish();
        startActivity(new Intent(this, PhysicsInputActivity.class));
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(null);
        MAIN=makeNag();
        setContentView(MAIN);
        MAIN.removeCallbacks(nagTask);
        MAIN.postDelayed(nagTask, 0);
        setTitle("Physics Tutor - Nag Screen");

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            MAIN.setOrientation(0);
        }
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            MAIN.setOrientation(1);
        }
    }

    private CustomLinearLayout makeNag()
    {
        final CustomLinearLayout L1, LA, LB;
        final CustomTextView message;

        L1=new CustomLinearLayout(this,-1,-1,1);
        LA=new CustomLinearLayout(this,300,-2,1);

        message=new CustomTextView(this,-2,-2,22,"You must wait "+Integer.toString(secOfNag)+ " seconds to proceed.\n\nTo bypass this screen and to support the developer, hit 'DISPLAY AD' on the menu screen to earn some \"nag free time\".\n");

        LA.addView(message);

        LB=new CustomLinearLayout(this,300,-2,1);

        diff=new CustomTextView(this,22);

        proceed=new CustomButton(this,120,50,"PROCEED",22);
        proceed.setEnabled(false);
        proceed.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                localyticsSession.tagEvent(PhysicsNagActivity.NAG_CLICK);
                finish();
                startActivity(new Intent(PhysicsNagActivity.this, PhysicsSolverActivity.class));
            }
        });

        LB.addView(diff);
        LB.addView(proceed);
        L1.addView(LA);
        L1.addView(LB);

        return L1;
    }
	
	final private Runnable nagTask = new Runnable() {
 	   public void run() {
           final Long timer=(System.currentTimeMillis()-startTime)/1000;

           if (secOfNag<=timer)
           {
               diff.setText("You may proceed.");
               proceed.setEnabled(true);
               MAIN.removeCallbacks(nagTask);
           }
           else
           {
               diff.setText(Long.toString(secOfNag-timer)+" seconds remaining.");
               MAIN.postDelayed(nagTask, 1000);
           }
 	   }
 	};
}