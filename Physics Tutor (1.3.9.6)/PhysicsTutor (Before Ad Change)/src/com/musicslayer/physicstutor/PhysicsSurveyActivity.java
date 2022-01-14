package com.musicslayer.physicstutor;

import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.localytics.android.*;
import android.content.Intent;
import android.os.Bundle;

public class PhysicsSurveyActivity extends PhysicsActivity {
    private LocalyticsSession localyticsSession;

    LinearLayout L, L1, L11, LA[]=new LinearLayout[4], L_Choice[]=new LinearLayout[4], LSurveyList;
    ScrollView S_Choice[]=new ScrollView[4];
    Button R[]=new Button[4], C[][]=new Button[4][6], B_PrevSurvey, B_NextSurvey;
    TextView T_Prompt[]=new TextView[4], message[]=new TextView[4], T_SurveyNumber;
    String question[]=new String[4], choice[][]=new String[4][6];

    int numSurveys=4, currentSurvey;
	
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
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            L.setOrientation(1);
        }
    }

    @Override
	public void onBackPressed() {
        startActivity(new Intent(PhysicsSurveyActivity.this, PhysicsIntroActivity.class));
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.localyticsSession = new LocalyticsSession(this.getApplicationContext(),LOCALYTICS);
		this.localyticsSession.open();
		this.localyticsSession.upload();
        L=makeSurvey();
        setContentView(L);
        setTitle("Physics Tutor - Survey");
        loadPrefs();

        currentSurvey=0;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            L.setOrientation(0);
        }
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            L.setOrientation(1);
        }
    }

    LinearLayout makeSurvey()
    {
        setQuestionsAndChoices();

        L1=new LinearLayout(this);
        L1.setOrientation(1);
        L1.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));

        L11=new LinearLayout(this);
        L11.setOrientation(1);
        L11.setLayoutParams(new ViewGroup.LayoutParams(dpToPx((int)(350*scale)),dpToPx((int)(250*scale))));

        LSurveyList=new LinearLayout(this);
        LSurveyList.setOrientation(0);
        LSurveyList.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        B_PrevSurvey=makePrevSurveyButton();
        B_PrevSurvey.setEnabled(false);

        T_SurveyNumber=new TextView(this);
        T_SurveyNumber.setText("Survey #1");
        T_SurveyNumber.setTextSize(1,22*scale);

        B_NextSurvey=makeNextSurveyButton();

        LSurveyList.addView(B_PrevSurvey);
        LSurveyList.addView(T_SurveyNumber);
        LSurveyList.addView(B_NextSurvey);
        L11.addView(LSurveyList);
        L1.addView(L11);

        for(int i=0;i<numSurveys;i++)
        {
            LA[i]=new LinearLayout(this);
            LA[i].setOrientation(1);
            LA[i].setLayoutParams(new ViewGroup.LayoutParams(-1, -1));

            if (i==0)
            {
                LA[i].setVisibility(0);
            }
            else
            {
                LA[i].setVisibility(8);
            }

            R[i]=makeResetButton(i);

            T_Prompt[i] = new TextView(this);
            T_Prompt[i].setTextSize(1,22*scale);
            T_Prompt[i].setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
            T_Prompt[i].setText(question[i]);

            LA[i].addView(T_Prompt[i]);

            message[i]=new TextView(this);
            message[i].setTextSize(1,14*scale);
            message[i].setText("Thank you for your feedback.\nClick RESET to change your response.");
            message[i].setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
            message[i].setVisibility(4);

            S_Choice[i]=new ScrollView(this);
            S_Choice[i].setLayoutParams(new ViewGroup.LayoutParams(-1, -1));

            L_Choice[i]=new LinearLayout(this);
            L_Choice[i].setOrientation(1);
            L_Choice[i].setLayoutParams(new ViewGroup.LayoutParams(-1, -1));

            for(int j=0;j<6;j++)
            {
                C[i][j]=makeChoiceButton(i,j);
                L_Choice[i].addView(C[i][j]);
            }

            S_Choice[i].addView(L_Choice[i]);
            LA[i].addView(message[i]);
            LA[i].addView(R[i]);
            L11.addView(LA[i]);
            L1.addView(S_Choice[i]);
        }

        return L1;
    }

    Button makeResetButton(final int r)
    {
        final Button b=new Button(this);
        b.getBackground().setColorFilter(0xFFFF4747, PorterDuff.Mode.MULTIPLY);
        b.setText("RESET");
        b.setTextSize(1,22*scale);
        b.setVisibility(4);
        b.setLayoutParams(new ViewGroup.LayoutParams(-2, dpToPx((int)(50*scale))));
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                localyticsSession.tagEvent("choice_"+Integer.toString(r)+"_"+Integer.toString(surveyChoice[r]-1)+"cancel");
                for (int k=0;k<6;k++)
                {
                    C[r][k].setEnabled(true);
                    C[r][k].setClickable(true);
                    C[r][k].invalidate();
                    C[r][k].getBackground().setColorFilter(0xFFFFFF66, PorterDuff.Mode.MULTIPLY);
                }
                message[r].setVisibility(4);
                R[r].setVisibility(4);
                surveyChoice[r] = 0;
            }
        });
        return b;
    }

    Button makeChoiceButton(final int r, final int s)
    {
        final Button b=new Button(this);
        b.getBackground().setColorFilter(0xFFFFFF66, PorterDuff.Mode.MULTIPLY);
        b.setText(choice[r][s]);
        b.setTextSize(1,20*scale);
        b.setLayoutParams(new ViewGroup.LayoutParams(-2, dpToPx((int)(50*scale))));
        b.setEnabled(false);
        if (surveyChoice[r]==0)
        {
            b.setEnabled(true);
        }
        else
        {
            b.invalidate();
            if (surveyChoice[r]-1==s)
            {
                b.getBackground().setColorFilter(0xFF70FF94, PorterDuff.Mode.MULTIPLY);
            }
            message[r].setVisibility(0);
            R[r].setVisibility(0);
        }
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                localyticsSession.tagEvent("choice_"+Integer.toString(r)+"_"+Integer.toString(s));
                for (int k=0;k<6;k++)
                {
                    C[r][k].setEnabled(false);
                }
                message[r].setVisibility(0);
                R[r].setVisibility(0);
                b.invalidate();
                b.getBackground().setColorFilter(0xFF70FF94, PorterDuff.Mode.MULTIPLY);
                surveyChoice[r]=s+1;
            }
        });
        return b;
    }

    public Button makePrevSurveyButton() {
        final Button b=new Button(this);
        b.setTextSize(1,20*scale);
        b.setText("PREVIOUS");
        b.setLayoutParams(new ViewGroup.LayoutParams(dpToPx((int)(120*scale)), dpToPx((int)(50*scale))));
        b.getBackground().setColorFilter(0xFF0A85FF, PorterDuff.Mode.MULTIPLY);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (currentSurvey>0)
                {
                    currentSurvey--;
                    T_SurveyNumber.setText("Survey #"+Integer.toString(currentSurvey+1));
                    B_NextSurvey.setEnabled(true);
                    if (currentSurvey==0)
                    {
                        B_PrevSurvey.setEnabled(false);
                    }
                    for (int k=0;k<numSurveys;k++)
                    {
                        if (k==currentSurvey)
                        {
                            LA[k].setVisibility(0);
                            S_Choice[k].setVisibility(0);
                        }
                        else
                        {
                            LA[k].setVisibility(8);
                            S_Choice[k].setVisibility(8);
                        }
                    }
                }
            }
        });
        return b;
    }

    public Button makeNextSurveyButton() {
        final Button b=new Button(this);
        b.setTextSize(1,20*scale);
        b.setText("NEXT");
        b.setLayoutParams(new ViewGroup.LayoutParams(dpToPx((int)(120*scale)), dpToPx((int)(50*scale))));
        b.getBackground().setColorFilter(0xFF0A85FF, PorterDuff.Mode.MULTIPLY);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (currentSurvey<numSurveys-1)
                {
                    currentSurvey++;
                    T_SurveyNumber.setText("Survey #"+Integer.toString(currentSurvey+1));
                    B_PrevSurvey.setEnabled(true);
                    if (currentSurvey==numSurveys-1)
                    {
                        B_NextSurvey.setEnabled(false);
                    }
                    for (int k=0;k<numSurveys;k++)
                    {
                        if (k==currentSurvey)
                        {
                            LA[k].setVisibility(0);
                            S_Choice[k].setVisibility(0);
                        }
                        else
                        {
                            LA[k].setVisibility(8);
                            S_Choice[k].setVisibility(8);
                        }
                    }
                }
            }
        });
        return b;
    }

    void setQuestionsAndChoices()
    {
        question[0]="Which solver do you feel\nis MOST useful?";
        choice[0][0]="Projectile";
        choice[0][1]="Resultant Vectors";
        choice[0][2]="Incline (Simple)";
        choice[0][3]="Incline";
        choice[0][4]="Spring (Simple)";
        choice[0][5]="Spring";
        question[1]="Which solver do you feel\nis LEAST useful?";
        choice[1][0]="6Projectile";
        choice[1][1]="7Resultant Vectors";
        choice[1][2]="7Incline (Simple)";
        choice[1][3]="7Incline";
        choice[1][4]="7Spring (Simple)";
        choice[1][5]="6Spring";
        question[2]="Which choice about this app do you agree with the most?";
        choice[2][0]="100% PURE AWESOMENESS!";
        choice[2][1]="Useful and nifty.";
        choice[2][2]="I don't need a tutor.";
        choice[2][3]="Helpful but confusing.";
        choice[2][4]="It doesn't address my needs.";
        choice[2][5]="TOTALLY STUPID IDEA!";
        question[3]="Regarding the new ads...";
        choice[3][0]="Rock on! I enjoy a free app.";
        choice[3][1]="Seems OK.";
        choice[3][2]="Eh...";
        choice[3][3]="Those new permissions are scary :(";
        choice[3][4]="They annoy me greatly.";
        choice[3][5]="WTF! I HATE THEM!";
    }
}