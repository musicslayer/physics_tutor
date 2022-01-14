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
	Button b1, b2, b3;
	
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
        ;
    }
	
	@Override
	public void onBackPressed() {
		startActivity(new Intent(PhysicsHelpActivity.this, PhysicsIntroActivity.class));
	return;
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
        
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	help.setText("Problems:" +
                		"\n\nUse this section to choose a type of physics problem." +
                		"\nEach problem will have one or more categories of information." +
                		"\nEach category contains one or more pages – groups of values that are to be provided by the user." +
                		"\nThe user must choose exactly one page in each category, based on what information the user already knows." +
                		"\nThe pages are designed so that only one page is necessary to provide enough information for that category." +
                		"\n\nAfter filling out one page in each category, the user must hit ‘TEST’ to ensure that all inputs are valid, and that each category has a page chosen and completely filled out." +
                		"\nIf everything is OK, then the user can hit ‘SOLVE’ to proceed to the solution." +
                		"\nNote that, if the user changes any of the values, or switches a category’s page, those changes will not be counted unless the user hits ‘TEST’ again." +
                		"\n\nThe solution caters to both those who want a quick answer, and those who want an explanation of how to reach the answer." +
                		"\nIf the user wants answers, then enter the value of one variable into the answer fields, and hit ‘Solve’, and the other variables will be computed." +
                		"\nIf the user wants an explanation, then there are instructions and helper illustrations that will guide the user through the problem, step by step." +
                		"\nNote that the step by step instructions end by finding all the terms to write all the equations for that problem." +
                		"\nThe user must still plug in information and/or solve for what they want (At this point, the answer fields can be used).");
            }
        });  
        
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	help.setText("Constants:" +
            			"\n\nPhysics problems often rely on certain constants." +
                		"\nFor example, the projectile problem depends on the value of ‘g’, the (magnitude of the) gravitational acceleration of an object." +
                		"\nThese constants are set to a default value (for example ‘g’=9.8 m/s^2)." +
                		"\nHowever, the user can change these values in this section.");
            }
        });  
        
        b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	help.setText("Settings:" +
            			"\n\nThis section allows users to make certain choices about the program." +
            			"\n\nAnswer Layout: The user can decide how the answer to a problem is displayed." +
            			"Choice A separates the illustrated explanation from the answer fields. This creates less clutter, but requires switching between two screens for all the information." +
            			"Choice B puts everything on one screen, which allows the user to see everything at once, but also may feel cramped if the user has a small screen.");
            }
        });  
    }
}