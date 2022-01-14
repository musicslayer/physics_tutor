package com.musicslayer.physicstutor;

import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import com.localytics.android.*;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PhysicsHelpActivity extends PhysicsActivity {
	private LocalyticsSession localyticsSession;
    private LinearLayout L, L1, L1A, L1AA, L1AB;
    private ScrollView S1;
	private TextView help;
	private Button b1, b2, b3, b4;
	
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
        this.localyticsSession.upload();
    	super.onPause();
    	savePrefs();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        this.localyticsSession.open();
    }
    
	@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
        	L.setOrientation(0);
            L1AA.setOrientation(1);
            L1AB.setOrientation(1);
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
        	L.setOrientation(1);
            L1AA.setOrientation(0);
            L1AB.setOrientation(0);
        }
    }

	@Override
	public void onBackPressed() {
		startActivity(new Intent(PhysicsHelpActivity.this, PhysicsIntroActivity.class));
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.localyticsSession = new LocalyticsSession(this.getApplicationContext(),LOCALYTICS);
		this.localyticsSession.open();
		this.localyticsSession.upload();
        L=makeHelp();
        setContentView(L);
        setTitle("Physics Tutor - Help");
        loadPrefs();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
        	L.setOrientation(0);
            L1AA.setOrientation(1);
            L1AB.setOrientation(1);
        }
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
        	L.setOrientation(1);
            L1AA.setOrientation(0);
            L1AB.setOrientation(0);
        }
    }

    LinearLayout makeHelp () {
        L1=new LinearLayout(this);
        L1.setOrientation(1);
        L1.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));

        L1A=new LinearLayout(this);
        L1A.setOrientation(1);
        L1A.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        L1AA=new LinearLayout(this);
        L1AA.setOrientation(1);
        L1AA.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        b1=new Button(this);
        b1.setText("PROBLEMS");
        b1.setTextSize(1,14*scale);
        b1.setLayoutParams(new ViewGroup.LayoutParams(dpToPx((int)(150*scale)), dpToPx((int)(50*scale))));

        b2=new Button(this);
        b2.setText("CONSTANTS");
        b2.setTextSize(1,14*scale);
        b2.setLayoutParams(new ViewGroup.LayoutParams(dpToPx((int)(150*scale)), dpToPx((int)(50*scale))));

        L1AA.addView(b1);
        L1AA.addView(b2);

        L1AB=new LinearLayout(this);
        L1AB.setOrientation(1);
        L1AB.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        b3=new Button(this);
        b3.setText("SETTINGS");
        b3.setTextSize(1,14*scale);
        b3.setLayoutParams(new ViewGroup.LayoutParams(dpToPx((int)(150*scale)), dpToPx((int)(50*scale))));

        b4=new Button(this);
        b4.setText("EMAIL DEVELOPER");
        b4.setTextSize(1,14*scale);
        b4.setLayoutParams(new ViewGroup.LayoutParams(dpToPx((int)(150*scale)), dpToPx((int)(50*scale))));

        L1AB.addView(b3);
        L1AB.addView(b4);

        L1A.addView(L1AA);
        L1A.addView(L1AB);

        S1=new ScrollView(this);
        S1.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        help=new TextView(this);
        help.setTextSize(1,14*scale);

        fillHelp();

        S1.addView(help);
        L1.addView(L1A);
        L1.addView(S1);

        return L1;
    }

    private void fillHelp()
    {
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                help.setText("Problems:" +
                        "\n\nUse this section to choose a type of physics problem." +
                        "\nAfter a quick guide screen with some basic information about the problem, the user will be able to input the problem information." +
                        "\nEach problem will have one or more categories of information." +
                        "\nWithin each category, there are one or more pages, or groups of values that are to be provided by the user." +
                        "\nThe user must choose and completely fill out exactly one page in each category, based on what information the user already knows." +
                        "\nThe pages are designed so that only one page is necessary to provide enough information for that category." +
                        "\n\nAfter filling out one page in each category, the user must hit 'TEST' to ensure that all inputs are valid, and that each category has a page chosen and completely filled out." +
                        "\nIf there is a problem, any category with incorrect or insufficient input will be shaded red." +
                        "\nIf everything is OK, then the user can hit 'SOLVE' to proceed to the solution, or 'EDIT INPUTS' to change something." +
                        "\n\nThe solution caters to both those who want a quick answer, and those who want an explanation of how to reach the answer." +
                        "\nIf the user wants answers, then enter the value of one variable into the answer fields, and hit 'SOLVE', and the other variables will be computed." +
                        "\nIf the user wants an explanation, then there are instructions and helper illustrations that will guide the user through the problem, step by step." +
                        "\nNote that the step by step instructions end by finding all the terms to write all the equations for that problem." +
                        "\nThe user must still plug in information and/or solve for what they want (At this point, the answer fields can be used)." +
                        "\n\nNote that there may be more than one possible value for some variables in the answer fields." +
                        "\nIn this case, you should read valid answers down a column and find the column that matches what you want." +
                        "\nMixing values in different columns may give wrong answers.");
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                help.setText("Constants:" +
                        "\n\nPhysics problems often rely on certain constants." +
                        "\nFor example, the projectile problem depends on the value of 'g', the (magnitude of the) gravitational acceleration of an object." +
                        "\nThese constants are set to a default value (for example 'g'=9.8 m/s^2)." +
                        "\nHowever, the user can change these values in this section.");
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                help.setText("Settings:" +
                        "\n\nThis section allows users to make certain choices about the program." +
                        "\n\nAnswer Layout: The user can decide how the answer to a problem is displayed." +
                        "\n\"Split\" separates the illustrated explanation from the answer fields. This creates less clutter, but requires switching between two screens for all the information." +
                        "\n\"Combined\" puts everything on one screen, which allows the user to see everything at once, but also may feel cramped if the user has a small screen.");
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                help.setText("Email Developer:" +
                        "\n\nThis button will allow you to send an email to the developer." +
                        "\nYou can use this to give feedback about the program, or to report bugs (for example, programming bugs or physics mistakes)." +
                        "\n\nIf you wish to report a bug relating to a specific physics problem, it is recommended that you send the email while viewing the solution to that problem (there is a menu option)." +
                        "\nThis way, specific information about the problem will automatically be inserted into the email for convenience." +
                        "\n\nNote: Your device must have an email application installed to use this feature.");
            }
        });
    }
}