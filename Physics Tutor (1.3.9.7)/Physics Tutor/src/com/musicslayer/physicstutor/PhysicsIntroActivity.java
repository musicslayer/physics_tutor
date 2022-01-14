package com.musicslayer.physicstutor;

import android.net.Uri;
import com.crittercism.app.Crittercism;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class PhysicsIntroActivity extends PhysicsActivity {
    private Toast toast1, toast2;
    private CustomLinearLayout L1A;
	
    @Override
    public void onPause() 
    {
    	super.onPause();
    }

    @Override
    public void onResume()
    {
        super.onResume();
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
        MAIN=makeIntro();
        setContentView(MAIN);
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
        final CustomLinearLayout L1, L2A, L2B, L2AA, L2AB, L2BA;
        final CustomButton problems, constants, settings, help, survey, email;
        final CustomTextView message;

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

        message=new CustomTextView(this,-2,-2,14,"Welcome to Physics Tutor!" +
            "\n\nPROBLEMS: Choose the problem you would like help with." +
            "\nCONSTANTS: Set the values of fundamental physical constants." +
            "\nSETTINGS: Set various preferences, including default units." +
            "\nHELP: See detailed help regarding this app." +
            "\nFEEDBACK SURVEY: Answer some polls to aid the developer." +
            "\nEMAIL DEVELOPER: Send the developer an email.");

        L2B.addView(L2BA);
        L2B.addView(message);
        L1A.addView(L2A);
        L1A.addView(L2B);
        L1.addView(L1A);

        toast1=Toast.makeText(getApplicationContext(), "Press HOME to exit this application.", 0);
        toast2=Toast.makeText(getApplicationContext(), "Your device does not have an email application properly installed and set up.", 0);

        return L1;
    }
}