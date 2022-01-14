package com.musicslayer.physicstutor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PhysicsSurveyActivity extends PhysicsActivity {
    private int currentSurvey=0;

    public void myOnBackPressed()
    {
        finish();
        startActivity(new Intent(this, PhysicsIntroActivity.class));
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        MAIN=makeSurvey();
        setContentView(MAIN);
        setTitle("Physics Tutor - Survey");
        super.onCreate(savedInstanceState);
    }

    private CustomLinearLayout makeSurvey()
    {
        final String qq[]=new String[100];
        final BufferedReader file = new BufferedReader(new InputStreamReader(this.getResources().openRawResource(R.raw.survey)));
        try
        {
            for (int c=0;(qq[c] = file.readLine()) != null;c++);
            file.close();
        }
        catch (IOException e){}

        final int numSurveys=Integer.parseInt(qq[0].substring(1));
        final String prompts[]=new String[numSurveys];
        int q=1;

        final CustomLinearLayout L=new CustomLinearLayout(this,-1,-1,1);
        final CustomLinearLayout LA=new CustomLinearLayout(this,350,250,1);
        final CustomLinearLayout LSurveyRow=new CustomLinearLayout(this,-2,-2,0);
        final CustomButton B_PrevSurvey=new CustomButton(this,120,50,"PREVIOUS",20,R.color.myBlue);
        final CustomTextView T_SurveyNumber=new CustomTextView(this,-2,-2,22,"Survey #1");
        final CustomButton B_NextSurvey=new CustomButton(this,120,50,"NEXT",20,R.color.myBlue);
        final CustomTextView T_Prompt=new CustomTextView(this,-2,-2,22,"Prompt");
        final CustomTextView message=new CustomTextView(this,-2,-2,14,"Thank you for your feedback.\nClick RESET to change your response.");
        final CustomButton Reset=new CustomButton(this,120,50,"RESET",22,R.color.myRed);
        final CustomLinearLayout L_Choice[]=new CustomLinearLayout[numSurveys];
        final CustomButton C[][]=new CustomButton[numSurveys][];

        B_PrevSurvey.setEnabled(false);
        B_PrevSurvey.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (currentSurvey>0)
                {
                    currentSurvey--;
                    if (currentSurvey==0){B_PrevSurvey.setEnabled(false);}
                    B_NextSurvey.setEnabled(true);
                    T_SurveyNumber.setText("Survey #"+Integer.toString(currentSurvey+1));
                    T_Prompt.setText(prompts[currentSurvey]);
                    L_Choice[currentSurvey].setVisibility(0);
                    L_Choice[currentSurvey+1].setVisibility(8);
                    if (surveyChoice[currentSurvey]==-1)
                    {
                        message.setVisibility(4);
                        Reset.setVisibility(4);
                    }
                    else
                    {
                        message.setVisibility(0);
                        Reset.setVisibility(0);
                    }
                }
            }
        });

        if (numSurveys==1){B_NextSurvey.setEnabled(false);}
        B_NextSurvey.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (currentSurvey<numSurveys-1)
                {
                    currentSurvey++;
                    if (currentSurvey==numSurveys-1){B_NextSurvey.setEnabled(false);}
                    B_PrevSurvey.setEnabled(true);
                    T_SurveyNumber.setText("Survey #" + Integer.toString(currentSurvey + 1));
                    T_Prompt.setText(prompts[currentSurvey]);
                    L_Choice[currentSurvey].setVisibility(0);
                    L_Choice[currentSurvey-1].setVisibility(8);
                    if (surveyChoice[currentSurvey]==-1)
                    {
                        message.setVisibility(4);
                        Reset.setVisibility(4);
                    }
                    else
                    {
                        message.setVisibility(0);
                        Reset.setVisibility(0);
                    }
                }
            }
        });

        message.setVisibility(surveyChoice[currentSurvey]==-1?4:0);

        Reset.setVisibility(surveyChoice[currentSurvey]==-1?4:0);
        Reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                localyticsSession.tagEvent("choice_"+Integer.toString(currentSurvey)+"_"+Integer.toString(surveyChoice[currentSurvey])+"_cancel");
                enableDisableView(L_Choice[currentSurvey],true);
                C[currentSurvey][surveyChoice[currentSurvey]].setColorFilter(R.color.myYellow);
                message.setVisibility(4);
                Reset.setVisibility(4);
                surveyChoice[currentSurvey]=-1;
            }
        });

        LSurveyRow.addView(B_PrevSurvey);
        LSurveyRow.addView(T_SurveyNumber);
        LSurveyRow.addView(B_NextSurvey);
        LA.addView(LSurveyRow);
        LA.addView(T_Prompt);
        LA.addView(message);
        LA.addView(Reset);
        L.addView(LA);

        for(int c=0;c<numSurveys;c++)
        {
            final int i=c;

            C[i]=new CustomButton[Integer.parseInt(qq[q].substring(1,qq[q].indexOf("-")))];
            prompts[i] = qq[q].substring(qq[q].indexOf("-")+1);

            q++;

            L_Choice[i]=new CustomLinearLayout(this,-1,-1,1);
            L_Choice[i].setVisibility(i==0?0:8);

            for(int d=0;d<C[i].length;d++)
            {
                final int j=d;
                if (surveyChoice[i]==j){C[i][j]=new CustomButton(this,290,50,qq[q++],20,R.color.myGreen);}
                else{C[i][j]=new CustomButton(this,290,50,qq[q++],20,R.color.myYellow);}
                C[i][j].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        localyticsSession.tagEvent("choice_"+Integer.toString(i)+"_"+Integer.toString(j));
                        enableDisableView(L_Choice[i],false);
                        message.setVisibility(0);
                        Reset.setVisibility(0);
                        C[i][j].setColorFilter(R.color.myGreen);
                        surveyChoice[i]=j;
                    }
                });

                L_Choice[i].addView(C[i][j]);
            }

            if (surveyChoice[i]>-1){enableDisableView(L_Choice[i],false);}
            L.addView(L_Choice[i]);
        }
        T_Prompt.setText(prompts[0]);

        return L;
    }
}