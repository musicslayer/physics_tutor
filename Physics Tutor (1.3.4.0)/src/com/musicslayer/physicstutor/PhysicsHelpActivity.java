package com.musicslayer.physicstutor;

import com.Localytics.android.*;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PhysicsHelpActivity extends PhysicsActivity {
	private LocalyticsSession localyticsSession;
	TextView help;
	Button b1, b2, b3, b4;
	
	int i;
	
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
    	savePrefs();
    }
    
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
	
	@Override
	public void onBackPressed() {
		startActivity(new Intent(PhysicsHelpActivity.this, PhysicsIntroActivity.class));
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        setTitle("Physics Tutor - Help");
        this.localyticsSession = new LocalyticsSession(this.getApplicationContext(),LOCALYTICS);
		this.localyticsSession.open();
		this.localyticsSession.upload();
        loadPrefs();
        
        help=(TextView) findViewById(R.id.help);
        b1=(Button) findViewById(R.id.problems);
        b2=(Button) findViewById(R.id.assumptions);
        b3=(Button) findViewById(R.id.settings);
        b4=(Button) findViewById(R.id.email);
        
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
                		"\nThe user must still plug in information and/or solve for what they want (At this point, the answer fields can be used).");
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
            			"\nChoice A separates the illustrated explanation from the answer fields. This creates less clutter, but requires switching between two screens for all the information." +
            			"\nChoice B puts everything on one screen, which allows the user to see everything at once, but also may feel cramped if the user has a small screen.");
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	help.setText("Email Developer:" +
            			"\n\nThis button will allow you to send an email to the developer." +
            			"\nYou can use this to give feedback about the program, or to report bugs (for example, programming bugs or physics mistakes)." +
            			"\n\nIf you wish to report a bug relating to a specific physics problem, it is recommended that you send the email while viewing the solution to that problem (there is a menu option)." +
            			"\nThis way, specific information about the problem will automatically be inserted into the email for convenience.");
            }
        });
    }
}