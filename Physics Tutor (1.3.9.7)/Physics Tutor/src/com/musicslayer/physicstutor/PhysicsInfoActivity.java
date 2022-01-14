package com.musicslayer.physicstutor;

import android.widget.*;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

public class PhysicsInfoActivity extends PhysicsActivity {
    private boolean seeImage=false;

    public void myOnBackPressed()
    {
        if (seeImage)
        {
            setContentView(MAIN);
            seeImage=false;
        }
        else
        {
            finish();
            startActivity(new Intent(this, PhysicsProblemsActivity.class));
        }
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
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MAIN=makeInfo();
        setContentView(MAIN);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            MAIN.setOrientation(0);
        }
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            MAIN.setOrientation(1);
        }
	}

    private CustomLinearLayout makeInfo () {
        final CustomTextView description, variables, constants, notes;
        final ZoomCustomImageView helper;
        final CustomButton proceed;
        final CustomLinearLayout L1, LA, LAA;
        final CustomScrollView S_Info;

        L1=new CustomLinearLayout(this,-1,-1,1);
        LA=new CustomLinearLayout(this,300,250,1);

        proceed=new CustomButton(this,120,50,"PROCEED",22,R.color.myBlue);
        proceed.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
                finish();
        		startActivity(new Intent(PhysicsInfoActivity.this, PhysicsInputActivity.class));
            }
        });

        S_Info=new CustomScrollView(this,-1,-1);

        LAA=new CustomLinearLayout(this,-1,-1,1);

        description=new CustomTextView(this,14);
        variables=new CustomTextView(this,14);
        constants=new CustomTextView(this,14);
        notes=new CustomTextView(this,14);

        LAA.addView(description);
        LAA.addView(variables);
        LAA.addView(constants);
        LAA.addView(notes);
        S_Info.addView(LAA);

        LA.addView(proceed);
        LA.addView(S_Info);

        helper=new ZoomCustomImageView(this,-2,-2);
        helper.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(helper.fullScreen());
                seeImage=true;
            }
        });

        fillInfo(description,variables,constants,notes,helper);

        L1.addView(LA);
        L1.addView(helper);

        return L1;
    }

    private void fillInfo(CustomTextView description, CustomTextView variables, CustomTextView constants, CustomTextView notes, ImageView helper)
    {
        if ("PROJECTILE".equals(type))
        {
        	setTitle("Physics Tutor - Projectile Info");
        	helper.setImageResource(R.raw.info_projectile);
            description.setText("A simple projectile motion problem. An object is launched and is under constant gravitational pull downward. There are no drag forces.");
            variables.setText("\nVariables used in this problem:\nTime\nX-position\nY-position\nY-velocity\nOverall Velocity\nAngle");
        	constants.setText("\nConstants used in this problem:\nConstant g = "+String.format("%.4f", constant_g)+" m/s^2");
        	notes.setText("\nNotes:\nThe object is assumed to follow the flight path for all time. You must check to see if the object would crash into something yourself.");
        }
        else if ("VECTORS".equals(type))
        {
        	setTitle("Physics Tutor - Vectors Info");
        	helper.setImageResource(R.raw.info_vectors);
        	description.setText("Vectors of various magnitude and direction are given, and we wish to resolve them into one resultant vector. Also, an equilibrant vector that cancels the resultant vector can be found.");
            variables.setText("\nVariables used in this problem:\nNone");
            constants.setText("\nConstants used in this problem:\nNone");
        	notes.setText("\nNotes:\nAlthough the solver uses force vectors, this problem can apply to other vectors that obey superposition.");
        }
        else if ("INCLINE_SIMPLE".equals(type))
        {
            setTitle("Physics Tutor - Simple Incline Info");
            helper.setImageResource(R.raw.info_incline);
            description.setText("An object is on an incline under the influence of gravity, the normal force, and friction.\n\nThis problem deals with finding the angle of the incline, the mass, and the forces on the object, NOT the position of the object on the incline.");
            variables.setText("\nVariables used in this problem:\nNone");
            constants.setText("\nConstants used in this problem:\nConstant g = "+String.format("%.4f", constant_g)+" m/s^2");
            notes.setText("\nNotes:\nThe incline is assumed to extend forever, and the object never leaves the surface of the incline.");
        }
        else if ("INCLINE".equals(type))
        {
        	setTitle("Physics Tutor - Incline Info");
        	helper.setImageResource(R.raw.info_incline);
        	description.setText("An object slides along an incline under the influence of gravity, the normal force, and friction.\n\nThis problem deals with the position and velocity of the object on the incline.");
            variables.setText("\nVariables used in this problem:\nTime\nPosition along incline\nVelocity along incline");
            constants.setText("\nConstants used in this problem:\nConstant g = "+String.format("%.4f", constant_g)+" m/s^2");
        	notes.setText("\nNotes:\nThe incline is assumed to extend forever, and the object never leaves the surface of the incline. Also, currently it is explicitly assumed that friction is small enough to allow motion. If this is not the case, then the solver may give wrong answers.");
        }
        else if ("SPRING_SIMPLE".equals(type))
        {
        	setTitle("Physics Tutor - Simple Spring Info");
        	helper.setImageResource(R.raw.info_spring_simple);
        	description.setText("A mass is attached to a spring, only affected by the spring's elastic force.\n\nThis problem deals with the position and restoring force of the mass, NOT the overall sinusoidal motion.");
            variables.setText("\nVariables used in this problem:\nPosition\nForce");
            constants.setText("\nConstants used in this problem:\nNone");
        	notes.setText("\nNotes:\nGravity and friction on the mass are neglected. Also, the spring is ideal (it has no mass, can stretch and compress without limit, never breaks, and obeys Hooke's Law).");
        }
        else if ("SPRING".equals(type))
        {
        	setTitle("Physics Tutor - Spring Info");
        	helper.setImageResource(R.raw.info_spring);
        	description.setText("A mass is attached to a spring. The mass oscillates back and forth only under the influence of the spring's elastic force.\n\nThis problem deals with the sinusoidal motion of the mass.");
            variables.setText("\nVariables used in this problem:\nTime\nPosition\nVelocity\nAcceleration\nForce\nKinetic Energy\nPotential Energy");
            constants.setText("\nConstants used in this problem:\nNone");
        	notes.setText("\nNotes:\nGravity and friction on the mass are neglected. Also, the spring is ideal (it has no mass, can stretch and compress without limit, never breaks, and obeys Hooke's Law).");
        }
    }
}