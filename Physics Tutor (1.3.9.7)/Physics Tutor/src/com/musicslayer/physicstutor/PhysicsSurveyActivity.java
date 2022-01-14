package com.musicslayer.physicstutor;

import android.content.res.Configuration;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PhysicsSurveyActivity extends PhysicsActivity {
    private int currentSurvey=0;

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

    public void myOnBackPressed()
    {
        finish();
        startActivity(new Intent(this, PhysicsIntroActivity.class));
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MAIN=makeSurvey();
        setContentView(MAIN);
        setTitle("Physics Tutor - Survey");

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            MAIN.setOrientation(0);
        }
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            MAIN.setOrientation(1);
        }
    }

    private CustomLinearLayout makeSurvey()
    {
        final String ss[]=new String[100];

        try
        {
            BufferedReader file = new BufferedReader(new InputStreamReader(this.getResources().openRawResource(R.raw.survey)));
            for (int c=0;(ss[c] = file.readLine()) != null;c++);
        }
        catch (IOException e)
        {
        }

        final CustomButton C[][]=new CustomButton[Integer.parseInt(ss[0].substring(1))][], B_PrevSurvey, B_NextSurvey, Reset[]=new CustomButton[C.length];
        final CustomLinearLayout L1, L11, LSurveyList, LA[]=new CustomLinearLayout[C.length], L_Choice[]=new CustomLinearLayout[C.length];
        final CustomTextView T_Prompt[]=new CustomTextView[C.length], message[]=new CustomTextView[C.length], T_SurveyNumber;

        int q=1;

        L1=new CustomLinearLayout(this,-1,-1,1);
        L11=new CustomLinearLayout(this,350,250,1);
        LSurveyList=new CustomLinearLayout(this,-2,-2,0);

        B_PrevSurvey=new CustomButton(this,120,50,"PREVIOUS",20,R.color.myBlue);
        B_PrevSurvey.setEnabled(false);

        T_SurveyNumber=new CustomTextView(this,-2,-2,22,"Survey #1");

        B_NextSurvey=new CustomButton(this,120,50,"NEXT",20,R.color.myBlue);
        if (C.length==1){B_NextSurvey.setEnabled(false);}

        B_PrevSurvey.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (currentSurvey>0)
                {
                    currentSurvey--;
                    if (currentSurvey==0){B_PrevSurvey.setEnabled(false);}
                    B_NextSurvey.setEnabled(true);
                    T_SurveyNumber.setText("Survey #"+Integer.toString(currentSurvey+1));
                    LA[currentSurvey].setVisibility(0);
                    L_Choice[currentSurvey].setVisibility(0);
                    LA[currentSurvey+1].setVisibility(8);
                    L_Choice[currentSurvey+1].setVisibility(8);
                }
            }
        });

        B_NextSurvey.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (currentSurvey<C.length-1)
                {
                    currentSurvey++;
                    if (currentSurvey==C.length-1){B_NextSurvey.setEnabled(false);}
                    B_PrevSurvey.setEnabled(true);
                    T_SurveyNumber.setText("Survey #"+Integer.toString(currentSurvey+1));
                    LA[currentSurvey].setVisibility(0);
                    L_Choice[currentSurvey].setVisibility(0);
                    LA[currentSurvey-1].setVisibility(8);
                    L_Choice[currentSurvey-1].setVisibility(8);
                }
            }
        });

        LSurveyList.addView(B_PrevSurvey);
        LSurveyList.addView(T_SurveyNumber);
        LSurveyList.addView(B_NextSurvey);
        L11.addView(LSurveyList);
        L1.addView(L11);

        for(int c=0;c<C.length;c++)
        {
            final int i=c;
            C[i]=new CustomButton[Integer.parseInt(ss[q].substring(1,ss[q].indexOf("-")))];

            LA[i]=new CustomLinearLayout(this,-1,-1,1);

            if (i==0){LA[i].setVisibility(0);}
            else{LA[i].setVisibility(8);}

            T_Prompt[i] = new CustomTextView(this,-2,-2,22,ss[q].substring(ss[q].indexOf("-")+1));

            q++;

            Reset[i]=new CustomButton(this,120,50,"RESET",22,R.color.myRed);
            Reset[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    localyticsSession.tagEvent("choice_"+Integer.toString(i)+"_"+Integer.toString(surveyChoice[i])+"_cancel");
                    enableDisableView(L_Choice[i],true);
                    //C[i][surveyChoice[i]].invalidate();
                    C[i][surveyChoice[i]].setColorFilter(R.color.myYellow);
                    message[i].setVisibility(4);
                    Reset[i].setVisibility(4);
                    surveyChoice[i]=-1;
                }
            });

            message[i]=new CustomTextView(this,-2,-2,14,"Thank you for your feedback.\nClick RESET to change your response.");

            L_Choice[i]=new CustomLinearLayout(this,-1,-1,1);

            for(int d=0;d<C[i].length;d++)
            {
                final int j=d;
                if (surveyChoice[i]==j){C[i][j]=new CustomButton(this,290,50,ss[q++],20,R.color.myGreen);}
                else{C[i][j]=new CustomButton(this,290,50,ss[q++],20,R.color.myYellow);}
                C[i][j].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        localyticsSession.tagEvent("choice_"+Integer.toString(i)+"_"+Integer.toString(j));
                        enableDisableView(L_Choice[i],false);
                        message[i].setVisibility(0);
                        Reset[i].setVisibility(0);
                        //C[i][j].invalidate();
                        C[i][j].setColorFilter(R.color.myGreen);
                        surveyChoice[i]=j;
                    }
                });

                L_Choice[i].addView(C[i][j]);
            }

            if (surveyChoice[i]==-1)
            {
                message[i].setVisibility(4);
                Reset[i].setVisibility(4);
            }
            else
            {
                message[i].setVisibility(0);
                Reset[i].setVisibility(0);
                enableDisableView(L_Choice[i],false);
            }

            LA[i].addView(T_Prompt[i]);
            LA[i].addView(message[i]);
            LA[i].addView(Reset[i]);
            L11.addView(LA[i]);
            L1.addView(L_Choice[i]);
        }

        return L1;
    }
}