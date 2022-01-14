package com.musicslayer.physicstutor;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.crittercism.app.Crittercism;

public class PhysicsIntroActivity extends PhysicsActivity {
    private Toast toastExit, toastSMS, toastEmail, toastMarket;
	
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

    public void myOnBackPressed()
    {
        toastExit.show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Crittercism.init(getApplicationContext(), CRITTERCISM);
        MAIN=makeIntro();
        setContentView(MAIN);
        setTitle("Physics Tutor");
        super.onCreate(savedInstanceState);

        toastExit=Toast.makeText(this, "Press HOME to exit this application.", 0);
        toastSMS=Toast.makeText(this, "Your device does not have a text messaging application.", 0);
        toastEmail=Toast.makeText(this, "Your device does not have an email application.", 0);
        toastMarket=Toast.makeText(this,"Sorry! The app store could not be launched.", 0);
    }

    private CustomLinearLayout makeIntro () {
        final CustomLinearLayout L=new CustomLinearLayout(this,-1,-2,1);
        final CustomLinearLayout LA=new CustomLinearLayout(this,-2,-2,0);
        final CustomLinearLayout L_Group1=new CustomLinearLayout(this,-2,-2,1);
        final CustomButton problems=new CustomButton(this,150,100,"PROBLEMS",22,R.color.myBlue);
        final CustomButton settings=new CustomButton(this,150,100,"SETTINGS",22,R.color.myRed);
        final CustomButton survey=new CustomButton(this,150,100,"FEEDBACK SURVEY",22,R.color.myPurple);
        final CustomLinearLayout L_Group2=new CustomLinearLayout(this,-2,-2,1);
        final CustomButton constants=new CustomButton(this,150,100,"CONSTANTS",22,R.color.myRed);
        final CustomButton help=new CustomButton(this,150,100,"HELP",22,R.color.myRed);
        final CustomButton email=new CustomButton(this,150,100,"EMAIL DEVELOPER",22,R.color.myPurple);
        final CustomLinearLayout LB=new CustomLinearLayout(this,-1,-1,1);
        final CustomTextView message1=new CustomTextView(this,-2,30,23,"WELCOME TO PHYSICS TUTOR!");
        final CustomTextView message2=new CustomTextView(this,-2,-2,14,
            "PROBLEMS: Choose a problem to solve." +
            "\nCONSTANTS: Set values of physical constants." +
            "\nSETTINGS: Set preferences, including default units." +
            "\nHELP: See detailed help regarding this app." +
            "\nFEEDBACK SURVEY: Give feedback to the developer." +
            "\nEMAIL DEVELOPER: Send the developer an email.");
        final CustomLinearLayout L_Group3=new CustomLinearLayout(this,-1,-1,0);
        final CustomButton recommend=new CustomButton(this,150,100,"RECOMMEND THIS APP",22,R.color.myGreen);
        final CustomButton rate=new CustomButton(this,150,100,"RATE THIS APP",22,R.color.myGreen);

        problems.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                startActivity(new Intent(PhysicsIntroActivity.this, PhysicsProblemsActivity.class));
                localyticsSession.tagEvent("intro_problems");
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                startActivity(new Intent(PhysicsIntroActivity.this, PhysicsSettingsActivity.class));
                localyticsSession.tagEvent("intro_settings");
            }
        });

        survey.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                startActivity(new Intent(PhysicsIntroActivity.this, PhysicsSurveyActivity.class));
                localyticsSession.tagEvent("intro_survey");
            }
        });

        L_Group1.addView(problems);
        L_Group1.addView(settings);
        L_Group1.addView(survey);

        constants.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                startActivity(new Intent(PhysicsIntroActivity.this, PhysicsConstantsActivity.class));
                localyticsSession.tagEvent("intro_constants");
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                startActivity(new Intent(PhysicsIntroActivity.this, PhysicsHelpActivity.class));
                localyticsSession.tagEvent("intro_help");
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","musicslayer@gmail.com", null));
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Physics Tutor - Bug Report/Feedback");
                try
                {
                    startActivity(emailIntent);
                    localyticsSession.tagEvent("intro_email_success");
                }
                catch (ActivityNotFoundException e)
                {
                    toastEmail.show();
                    localyticsSession.tagEvent("intro_email_fail");
                }
            }
        });

        L_Group2.addView(constants);
        L_Group2.addView(help);
        L_Group2.addView(email);

        LA.addView(L_Group1);
        LA.addView(L_Group2);

        L_Group3.setGravity(3|80);

        recommend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(PhysicsIntroActivity.this);

                alert.setTitle("Recommend This App to a Friend!");
                alert.setMessage("Choose a contact method:");
                alert.setPositiveButton("SMS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        final Intent shareSMSIntent = new Intent(Intent.ACTION_VIEW);

                        shareSMSIntent.putExtra("sms_body", "Hey! Physics Tutor saves me so much time solving physics problems, and it really helps me understand them! You should try it!\n\nhttp://play.google.com/store/apps/details?id=com.musicslayer.physicstutor\n\nhttp://slideme.org/application/physics-tutor");
                        shareSMSIntent.setType("vnd.android-dir/mms-sms");
                        try
                        {
                            startActivity(shareSMSIntent);
                            localyticsSession.tagEvent("recommend_sms_success");
                        }
                        catch (ActivityNotFoundException e)
                        {
                            toastSMS.show();
                            localyticsSession.tagEvent("recommend_sms_fail");
                        }
                    }
                });
                alert.setNeutralButton("EMAIL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        final Intent shareEmailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","", null));

                        shareEmailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Physics Tutor - Your friend wants to recommend this app for you!");
                        shareEmailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hey! Physics Tutor saves me so much time solving physics problems, and it really helps me understand them! You should try it!\n\nhttp://play.google.com/store/apps/details?id=com.musicslayer.physicstutor\n\nhttp://slideme.org/application/physics-tutor");
                        try
                        {
                            startActivity(shareEmailIntent);
                            localyticsSession.tagEvent("recommend_email_success");
                        }
                        catch (ActivityNotFoundException e)
                        {
                            toastEmail.show();
                            localyticsSession.tagEvent("recommend_email_fail");
                        }
                    }
                });
                alert.setNegativeButton("(Cancel)", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });
                alert.create().show();
            }
        });

        rate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent marketActivity=new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=com.musicslayer.physicstutor"));

                try
                {
                    startActivity(marketActivity);
                    localyticsSession.tagEvent("rate_success");
                }
                catch (ActivityNotFoundException e)
                {
                    toastMarket.show();
                    localyticsSession.tagEvent("rate_fail");
                }
                /*
                final AlertDialog.Builder alert = new AlertDialog.Builder(PhysicsIntroActivity.this);

                alert.setTitle("Rate This App!");
                alert.setMessage("Would you like to rate this app on Google Play?");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        final Intent marketActivity=new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=com.musicslayer.physicstutor"));

                        try
                        {
                            startActivity(marketActivity);
                            localyticsSession.tagEvent("rate_success");
                        }
                        catch (ActivityNotFoundException e)
                        {
                            toastMarket.show();
                            localyticsSession.tagEvent("rate_fail");
                        }
                    }
                });
                alert.setNeutralButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });
                alert.create().show();
                */
            }
        });

        L_Group3.addView(recommend);
        L_Group3.addView(rate);
        LB.addView(message1);
        LB.addView(message2);
        LB.addView(L_Group3);
        L.addView(LA);
        L.addView(LB);

        return L;
    }
}