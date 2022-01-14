package com.musicslayer.physicstutor;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

public class PhysicsHelpActivity extends PhysicsActivity {
    private CustomLinearLayout L1AA, L1AB, L1AC;
    
	@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            MAIN.setOrientation(0);
            L1AA.setOrientation(1);
            L1AB.setOrientation(1);
            L1AC.setOrientation(1);
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            MAIN.setOrientation(1);
            L1AA.setOrientation(0);
            L1AB.setOrientation(0);
            L1AC.setOrientation(0);
        }
    }

    public void myOnBackPressed()
    {
        finish();
		startActivity(new Intent(this, PhysicsIntroActivity.class));
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        MAIN=makeHelp();
        setContentView(MAIN);
        setTitle("Physics Tutor - Help");

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            MAIN.setOrientation(0);
            L1AA.setOrientation(1);
            L1AB.setOrientation(1);
            L1AC.setOrientation(1);
        }
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            MAIN.setOrientation(1);
            L1AA.setOrientation(0);
            L1AB.setOrientation(0);
            L1AC.setOrientation(0);
        }
    }

    private CustomLinearLayout makeHelp () {
        final CustomScrollView S1;
        final CustomLinearLayout L1, L1A;
        final CustomButton b1, b2, b3, b4, b5, b6;
        final CustomTextView help;

        L1=new CustomLinearLayout(this,-1,-1,1);
        L1A=new CustomLinearLayout(this,-2,-2,1);
        L1AA=new CustomLinearLayout(this,-2,-2,1);

        b1=new CustomButton(this,150,50,"PROBLEMS",14);
        b2=new CustomButton(this,150,50,"CONSTANTS",14);

        L1AA.addView(b1);
        L1AA.addView(b2);

        L1AB=new CustomLinearLayout(this,-2,-2,1);

        b3=new CustomButton(this,150,50,"SETTINGS",14);
        b4=new CustomButton(this,150,50,"EMAIL DEVELOPER",14);

        L1AB.addView(b3);
        L1AB.addView(b4);

        L1AC=new CustomLinearLayout(this,-2,-2,1);

        b5=new CustomButton(this,150,50,"ADS",14);
        b6=new CustomButton(this,150,50,"GENERAL TIPS",14);

        L1AC.addView(b5);
        L1AC.addView(b6);

        L1A.addView(L1AA);
        L1A.addView(L1AB);
        L1A.addView(L1AC);

        S1=new CustomScrollView(this,-2,-2);

        help=new CustomTextView(this,14);

        fillHelp(help,b1,b2,b3,b4,b5,b6);

        S1.addView(help);
        L1.addView(L1A);
        L1.addView(S1);

        return L1;
    }

    private void fillHelp(final CustomTextView help, CustomButton b1, CustomButton b2, CustomButton b3, CustomButton b4, CustomButton b5, CustomButton b6)
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
                    "\nIn addition to the following settings, you can choose the default units here." +
                    "\n\nAnswer Layout: How the answer to a problem is displayed." +
                    "\n--\"Split\" separates the illustrated explanation from the answer fields. This creates less clutter, but requires switching between screens for all the information." +
                    "\n--\"Combined\" puts everything on one screen, which allows the user to see everything at once, but also may feel cramped if the user has a small screen." +
                    "\n\nKeyboard Choice: Which keyboard pops up for data entry." +
                    "\n--\"Numbers\" gives a number keyboard. This makes sense most of the time, but may prevent using certain abbreviations such as 'e-10' which need letters." +
                    "\n--\"Default\" gives a standard keyboard.");
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

        b5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                help.setText("Ads:" +
                    "\n\nThis app is currently ad free." +
                    "\nI have used advertising in the past, and may use it again in the future.");
                /*
                help.setText("Ads:" +
                    "\n\nI have switched to a new system of ads." +
                    "\nWhen you first install the app, hit 'GET DAYS' to get 3 free days to start." +
                    "\nWhen you complete an offer (there should be plenty of free and easy ones), you will get the promised amount of days (hit 'GET DAYS' to get them added to the timer)." +
                    "\nUnlike before, now you can have more than one week on your timer (If you have more than 10000 days, you cannot earn more, but it is highly doubtful anyone will reach anywhere near that)." +
                    "\n\nSome things to keep in mind:" +
                    "\n--To the best of my knowledge, updating the app should preserve the time you've earned, but CLEARING APP DATA or UNINSTALLING/REMOVING THE APP will make you LOSE YOUR TIME." +
                    "\n--The timer does not stop, even if the app is not running (or indeed even if your device is off)." +
                    "\n--Once an offer is completed, hit 'GET DAYS' to get your earned time added onto the timer. You should see a confirmation message that time has been granted." +
                    "\n\nIf there are any issues with not getting the promised time, feel free to send an email.");
                */
            }
        });

        b6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                help.setText("General Tips:" +
                    "\n\nThe solver only gives sensible answers if you give it sensible inputs." +
                    "\nFor example, if you input a negative mass, then all bets are off, and any answers given may be nonsensical." +
                    "\n\nThe solver can only be used if there really is enough information to solve the problem completely (no input field can be left as a variable)." +
                    "\n\nTap on most pictures to zoom in on them if you are having trouble seeing them.");
            }
        });
    }
}