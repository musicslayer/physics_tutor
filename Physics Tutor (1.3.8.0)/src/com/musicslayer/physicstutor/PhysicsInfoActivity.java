package com.musicslayer.physicstutor;

import android.widget.LinearLayout;
import com.localytics.android.*;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PhysicsInfoActivity extends PhysicsActivity {
	private LocalyticsSession localyticsSession;
	private Button proceed;
	private TextView description, constants, notes;
	private ImageView helper;
	
	@Override
    public void onPause() 
    {
		this.localyticsSession.close();
    	super.onPause();
    	savePrefs();
    }

    @Override
	public void onBackPressed() {
        startActivity(new Intent(PhysicsInfoActivity.this, PhysicsProblemsActivity.class));
	}
	
	@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
        	((LinearLayout) findViewById(R.id.LinearLayout01)).setOrientation(0);
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
        	((LinearLayout) findViewById(R.id.LinearLayout01)).setOrientation(1);
        }
    }
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        this.localyticsSession = new LocalyticsSession(this.getApplicationContext(),LOCALYTICS);
		this.localyticsSession.open();
		this.localyticsSession.upload();
        loadPrefs();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
        	((LinearLayout) findViewById(R.id.LinearLayout01)).setOrientation(0);
        }
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
        	((LinearLayout) findViewById(R.id.LinearLayout01)).setOrientation(1);
        }
        
        helper = (ImageView) findViewById(R.id.ImageView_Helper);
        proceed = (Button) findViewById(R.id.Button_Proceed);
        description = (TextView) findViewById(R.id.TextView_Description);
        constants = (TextView) findViewById(R.id.TextView_Constants);
        notes = (TextView) findViewById(R.id.TextView_Notes);
        
        if ("PROJECTILE".equals(type))
        {
        	setTitle("Physics Tutor - Projectile Info");
        	helper.setImageResource(R.raw.info_projectile);
        	description.setText("A simple projectile motion problem. An object is launched and is under constant gravitational pull downward. There are no drag forces.");
        	constants.setText("\nConstants used in this problem:\nConstant g = "+String.format("%.4f", constant_g)+" m/s^2");
        	notes.setText("\nNotes:\nThe object is assumed to follow the flight path for all time. You must check to see if the object would crash into something yourself.");
        }
        else if ("INCLINE".equals(type))
        {
        	setTitle("Physics Tutor - Incline Info");
        	helper.setImageResource(R.raw.info_incline);
        	description.setText("An object slides along an incline under the influence of gravity, the normal force, and friction.");
        	constants.setText("\nConstants used in this problem:\nConstant g = "+String.format("%.4f", constant_g)+" m/s^2");
        	notes.setText("\nNotes:\nThe incline is assumed to extend forever, and the object never leaves the surface of the incline. Also, currently it is explicitly assumed that friction is small enough to allow motion. If this is not the case, then the solver may give wrong answers.");
        }
        else if ("INCLINE_SIMPLE".equals(type))
        {
        	setTitle("Physics Tutor - Simple Incline Info");
        	helper.setImageResource(R.raw.info_incline);
        	description.setText("An object slides along an incline under the influence of gravity, the normal force, and friction.");
        	constants.setText("\nConstants used in this problem:\nConstant g = "+String.format("%.4f", constant_g)+" m/s^2");
        	notes.setText("\nNotes:\nThe incline is assumed to extend forever, and the object never leaves the surface of the incline.");
        }
        else if ("SPRING_SIMPLE".equals(type))
        {
        	setTitle("Physics Tutor - Simple Spring Info");
        	helper.setImageResource(R.raw.info_spring_simple);
        	description.setText("A mass is attached to a spring. The mass oscillates back and forth only under the influence of the spring's elastic force.");
        	constants.setText("\nConstants used in this problem:\nNone");
        	notes.setText("\nNotes:\nGravity and friction on the mass are neglected. Also, the spring is ideal (it has no mass, can stretch and compress without limit, never breaks, and obeys Hooke's Law).");
        }
        else if ("SPRING".equals(type))
        {
        	setTitle("Physics Tutor - Spring Info");
        	helper.setImageResource(R.raw.info_spring);
        	description.setText("A mass is attached to a spring. The mass oscillates back and forth only under the influence of the spring's elastic force.");
        	constants.setText("\nConstants used in this problem:\nNone");
        	notes.setText("\nNotes:\nThe equilibrium position is assumed to be the origin.\nGravity and friction on the mass are neglected. Also, the spring is ideal (it has no mass, can stretch and compress without limit, never breaks, and obeys Hooke's Law).");
        }

        proceed.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		startActivity(new Intent(PhysicsInfoActivity.this, PhysicsInputActivity.class));
            }
        });
	}
}
