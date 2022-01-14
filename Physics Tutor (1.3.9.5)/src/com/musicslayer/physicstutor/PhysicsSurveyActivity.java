package com.musicslayer.physicstutor;

import android.graphics.PorterDuff;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.localytics.android.*;
import android.content.Intent;
import android.os.Bundle;

public class PhysicsSurveyActivity extends PhysicsActivity {
	final private static String CHOICE_1 = "choice_1";
	final private static String CHOICE_2 = "choice_2";
    final private static String CHOICE_3 = "choice_3";
    final private static String CHOICE_4 = "choice_4";
    final private static String CHOICE_5 = "choice_5";
    final private static String CHOICE_6 = "choice_6";
    final private static String CHOICE_1_CANCEL = "choice_1_cancel";
    final private static String CHOICE_2_CANCEL = "choice_2_cancel";
    final private static String CHOICE_3_CANCEL = "choice_3_cancel";
    final private static String CHOICE_4_CANCEL = "choice_4_cancel";
    final private static String CHOICE_5_CANCEL = "choice_5_cancel";
    final private static String CHOICE_6_CANCEL = "choice_6_cancel";
	private LocalyticsSession localyticsSession;
    Button reset, c1, c2, c3, c4, c5, c6;
    TextView message;
	
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
	public void onBackPressed() {
        startActivity(new Intent(PhysicsSurveyActivity.this, PhysicsIntroActivity.class));
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey);
        setTitle("Physics Tutor - Survey");
        this.localyticsSession = new LocalyticsSession(this.getApplicationContext(),LOCALYTICS);
		this.localyticsSession.open();
		this.localyticsSession.upload();
        loadPrefs();

        reset = (Button) findViewById(R.id.Button_Reset);
        message = (TextView) findViewById(R.id.TextView_Message);
        c1 = (Button) findViewById(R.id.Button_Choice1);
        c2 = (Button) findViewById(R.id.Button_Choice2);
        c3 = (Button) findViewById(R.id.Button_Choice3);
        c4 = (Button) findViewById(R.id.Button_Choice4);
        c5 = (Button) findViewById(R.id.Button_Choice5);
        c6 = (Button) findViewById(R.id.Button_Choice6);

        reset.getBackground().setColorFilter(0xFFFF4747, PorterDuff.Mode.MULTIPLY);
        c1.getBackground().setColorFilter(0xFFFFFF66, PorterDuff.Mode.MULTIPLY);
        c2.getBackground().setColorFilter(0xFFFFFF66, PorterDuff.Mode.MULTIPLY);
        c3.getBackground().setColorFilter(0xFFFFFF66, PorterDuff.Mode.MULTIPLY);
        c4.getBackground().setColorFilter(0xFFFFFF66, PorterDuff.Mode.MULTIPLY);
        c5.getBackground().setColorFilter(0xFFFFFF66, PorterDuff.Mode.MULTIPLY);
        c6.getBackground().setColorFilter(0xFFFFFF66, PorterDuff.Mode.MULTIPLY);

        c1.setEnabled(false);
        c2.setEnabled(false);
        c3.setEnabled(false);
        c4.setEnabled(false);
        c5.setEnabled(false);
        c6.setEnabled(false);

        switch (surveyChoice)
        {

            case 0:
                c1.setEnabled(true);
                c2.setEnabled(true);
                c3.setEnabled(true);
                c4.setEnabled(true);
                c5.setEnabled(true);
                c6.setEnabled(true);
                break;
            case 1:
                c1.invalidate();
                c1.getBackground().setColorFilter(0xFF70FF94, PorterDuff.Mode.MULTIPLY);
                reset.setEnabled(true);
                message.setVisibility(0);
                break;
            case 2:
                c2.invalidate();
                c2.getBackground().setColorFilter(0xFF70FF94, PorterDuff.Mode.MULTIPLY);
                reset.setEnabled(true);
                message.setVisibility(0);
                break;
            case 3:
                c3.invalidate();
                c3.getBackground().setColorFilter(0xFF70FF94, PorterDuff.Mode.MULTIPLY);
                reset.setEnabled(true);
                message.setVisibility(0);
                break;
            case 4:
                c4.invalidate();
                c4.getBackground().setColorFilter(0xFF70FF94, PorterDuff.Mode.MULTIPLY);
                reset.setEnabled(true);
                message.setVisibility(0);
                break;
            case 5:
                c5.invalidate();
                c5.getBackground().setColorFilter(0xFF70FF94, PorterDuff.Mode.MULTIPLY);
                reset.setEnabled(true);
                message.setVisibility(0);
                break;
            case 6:
                c6.invalidate();
                c6.getBackground().setColorFilter(0xFF70FF94, PorterDuff.Mode.MULTIPLY);
                reset.setEnabled(true);
                message.setVisibility(0);
                break;
        }

        reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switch (surveyChoice)
                {
                    case 1:
                        localyticsSession.tagEvent(PhysicsSurveyActivity.CHOICE_1_CANCEL);
                        break;
                    case 2:
                        localyticsSession.tagEvent(PhysicsSurveyActivity.CHOICE_2_CANCEL);
                        break;
                    case 3:
                        localyticsSession.tagEvent(PhysicsSurveyActivity.CHOICE_3_CANCEL);
                        break;
                    case 4:
                        localyticsSession.tagEvent(PhysicsSurveyActivity.CHOICE_4_CANCEL);
                        break;
                    case 5:
                        localyticsSession.tagEvent(PhysicsSurveyActivity.CHOICE_5_CANCEL);
                        break;
                    case 6:
                        localyticsSession.tagEvent(PhysicsSurveyActivity.CHOICE_6_CANCEL);
                        break;
                }
                reset.setEnabled(false);
                c1.setEnabled(true);
                c2.setEnabled(true);
                c3.setEnabled(true);
                c4.setEnabled(true);
                c5.setEnabled(true);
                c6.setEnabled(true);
                c1.setClickable(true);
                c2.setClickable(true);
                c3.setClickable(true);
                c4.setClickable(true);
                c5.setClickable(true);
                c6.setClickable(true);
                message.setVisibility(4);
                c1.invalidate();
                //c1.getBackground().clearColorFilter();
                c1.getBackground().setColorFilter(0xFFFFFF66, PorterDuff.Mode.MULTIPLY);
                c2.invalidate();
                //c2.getBackground().clearColorFilter();
                c2.getBackground().setColorFilter(0xFFFFFF66, PorterDuff.Mode.MULTIPLY);
                c3.invalidate();
                //c3.getBackground().clearColorFilter();
                c3.getBackground().setColorFilter(0xFFFFFF66, PorterDuff.Mode.MULTIPLY);
                c4.invalidate();
                //c4.getBackground().clearColorFilter();
                c4.getBackground().setColorFilter(0xFFFFFF66, PorterDuff.Mode.MULTIPLY);
                c5.invalidate();
                //c5.getBackground().clearColorFilter();
                c5.getBackground().setColorFilter(0xFFFFFF66, PorterDuff.Mode.MULTIPLY);
                c6.invalidate();
                //c6.getBackground().clearColorFilter();
                c6.getBackground().setColorFilter(0xFFFFFF66, PorterDuff.Mode.MULTIPLY);
                surveyChoice=0;
            }
        });

        c1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                localyticsSession.tagEvent(PhysicsSurveyActivity.CHOICE_1);
                reset.setEnabled(true);
                c1.setEnabled(false);
                c2.setEnabled(false);
                c3.setEnabled(false);
                c4.setEnabled(false);
                c5.setEnabled(false);
                c6.setEnabled(false);
                message.setVisibility(0);
                c1.invalidate();
                c1.getBackground().setColorFilter(0xFF70FF94, PorterDuff.Mode.MULTIPLY);
                surveyChoice=1;
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                localyticsSession.tagEvent(PhysicsSurveyActivity.CHOICE_2);
                reset.setEnabled(true);
                c1.setEnabled(false);
                c2.setEnabled(false);
                c3.setEnabled(false);
                c4.setEnabled(false);
                c5.setEnabled(false);
                c6.setEnabled(false);
                message.setVisibility(0);
                c2.invalidate();
                c2.getBackground().setColorFilter(0xFF70FF94, PorterDuff.Mode.MULTIPLY);
                surveyChoice=2;
            }
        });

        c3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                localyticsSession.tagEvent(PhysicsSurveyActivity.CHOICE_3);
                reset.setEnabled(true);
                c1.setEnabled(false);
                c2.setEnabled(false);
                c3.setEnabled(false);
                c4.setEnabled(false);
                c5.setEnabled(false);
                c6.setEnabled(false);
                message.setVisibility(0);
                c3.invalidate();
                c3.getBackground().setColorFilter(0xFF70FF94, PorterDuff.Mode.MULTIPLY);
                surveyChoice=3;
            }
        });
        c4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                localyticsSession.tagEvent(PhysicsSurveyActivity.CHOICE_4);
                reset.setEnabled(true);
                c1.setEnabled(false);
                c2.setEnabled(false);
                c3.setEnabled(false);
                c4.setEnabled(false);
                c5.setEnabled(false);
                c6.setEnabled(false);
                message.setVisibility(0);
                c4.invalidate();
                c4.getBackground().setColorFilter(0xFF70FF94, PorterDuff.Mode.MULTIPLY);
                surveyChoice=4;
            }
        });
        c5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                localyticsSession.tagEvent(PhysicsSurveyActivity.CHOICE_5);
                reset.setEnabled(true);
                c1.setEnabled(false);
                c2.setEnabled(false);
                c3.setEnabled(false);
                c4.setEnabled(false);
                c5.setEnabled(false);
                c6.setEnabled(false);
                message.setVisibility(0);
                c5.invalidate();
                c5.getBackground().setColorFilter(0xFF70FF94, PorterDuff.Mode.MULTIPLY);
                surveyChoice=5;
            }
        });
        c6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                localyticsSession.tagEvent(PhysicsSurveyActivity.CHOICE_6);
                reset.setEnabled(true);
                c1.setEnabled(false);
                c2.setEnabled(false);
                c3.setEnabled(false);
                c4.setEnabled(false);
                c5.setEnabled(false);
                c6.setEnabled(false);
                message.setVisibility(0);
                c6.invalidate();
                c6.getBackground().setColorFilter(0xFF70FF94, PorterDuff.Mode.MULTIPLY);
                surveyChoice=6;
            }
        });
    }
}
