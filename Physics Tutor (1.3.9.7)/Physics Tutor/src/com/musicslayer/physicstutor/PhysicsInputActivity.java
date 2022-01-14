package com.musicslayer.physicstutor;

import android.graphics.Color;
import android.view.View;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PhysicsInputActivity extends PhysicsActivity {
    //final private static String NAG_SKIP = "nag_skip";
    //final private static String NAG_START = "nag_start";

    private int visCat=0;
    private boolean isReady=true;

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
		startActivity(new Intent(this, PhysicsInfoActivity.class));
	}
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        option[0]=1;
        option[1]=1;
        option[2]=1;
        option[3]=1;
        if ("PROJECTILE".equals(type))
        {
            MAIN=makeInput(R.raw.input_projectile);
            setTitle("Physics Tutor - Projectile Problem");
        }
        else if ("VECTORS".equals(type))
        {
            MAIN=makeInput(R.raw.input_vectors);
            setTitle("Physics Tutor - Vectors Problem");
        }
        else if ("INCLINE_SIMPLE".equals(type))
        {
            MAIN=makeInput(R.raw.input_incline_simple);
            setTitle("Physics Tutor - Simple Incline Problem");
        }
        else if ("INCLINE".equals(type))
        {
            MAIN=makeInput(R.raw.input_incline);
            setTitle("Physics Tutor - Incline Problem");
        }
        else if ("SPRING_SIMPLE".equals(type))
        {
            MAIN=makeInput(R.raw.input_spring_simple);
            setTitle("Physics Tutor - Simple Spring Problem");
        }
        else if ("SPRING".equals(type))
        {
            MAIN=makeInput(R.raw.input_spring);
            setTitle("Physics Tutor - Spring Problem");
        }
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

    private CustomLinearLayout makeInput (int filename) {
        final String ss[]=new String[100];

        try
        {
            BufferedReader file = new BufferedReader(new InputStreamReader(this.getResources().openRawResource(filename)));
            for (int c=0;(ss[c] = file.readLine()) != null;c++);
        }
        catch (IOException e)
        {
        }

        final CustomEditText EEE[][][]=new CustomEditText[Integer.parseInt(ss[0].substring(1))][][];
        final CustomLinearLayout L1, LA, LAA;
        final CustomButton B_Test;
        final CustomLinearLayout LB=new CustomLinearLayout(this,-1,-2,1), LCatList, LPageList[]=new CustomLinearLayout[EEE.length], L_P[][]=new CustomLinearLayout[EEE.length][], L_C[]=new CustomLinearLayout[EEE.length];
        final CustomButton B_Solve=new CustomButton(this,120,50,"SOLVE",20,R.color.myYellow), B_PrevCat=new CustomButton(this,50,50,"<=",20), B_NextCat=new CustomButton(this,50,50,"=>",20), B_PrevPage[]=new CustomButton[EEE.length], B_NextPage[]=new CustomButton[EEE.length];
        final CustomTextView T_Test=new CustomTextView(this,-2,-2,14,"Complete one page in every category.\n"), T_Human=new CustomTextView(this,-2,-2,14), T_Cat[]=new CustomTextView[EEE.length], T_P[][]=new CustomTextView[EEE.length][];
        final CustomScrollView S_P[][]=new CustomScrollView[EEE.length][];

        final double factor[][][] = new double[EEE.length][][];
        final int visPage[]=new int[EEE.length];
        int i=1;

        L1=new CustomLinearLayout(this,-1,-1,1);
        LA=new CustomLinearLayout(this,330,-2,1);
        LAA=new CustomLinearLayout(this,-2,-2,0);

        B_Test=new CustomButton(this,120,50,"TEST",20,R.color.myYellow);
        B_Test.setOnClickListener(new View.OnClickListener() {
            public void onClick(View vv) {
                int v=0;
                if (isReady)
                {
                    isReady=false;
                    for (int r=0;r<EEE.length;r++)
                    {
                        boolean isCatError=false;
                        for (int t=0;t<EEE[r][option[r]-1].length;t++)
                        {
                            boolean isFieldError=false;
                            try
                            {
                                //var[v++]=factor[r][option[r]-1][t]*Double.valueOf(EEE[r][option[r]-1][t].getText().toString());
                                var[v]=Double.valueOf(EEE[r][option[r]-1][t].getText().toString());
                                varUnit[v]=factor[r][option[r]-1][t]*Double.valueOf(EEE[r][option[r]-1][t].getText().toString());
                                Sunit[v]=SunitChoice[r][option[r]-1][t];
                                v++;
                            }
                            catch (NumberFormatException e){isReady=isCatError=isFieldError=true;}
                            EEE[r][option[r]-1][t].setBackgroundColor(isFieldError?-65536:-16711936);
                        }
                        T_Cat[r].setTextColor(isCatError?-65536:-16711936);
                    }
                    while (v<var.length) {var[v++]=0;}
                    if (!isReady)
                    {
                        B_Test.setText("EDIT");
                        T_Test.setText("You may proceed by clicking 'SOLVE'.\n");
                        T_Human.setText(getHumanReadableText(Sunit));
                        T_Human.setVisibility(0);
                        B_Solve.setEnabled(true);
                        enableDisableView(LB,false);
                    }
                }
                else
                {
                    isReady=true;
                    B_Test.setText("TEST");
                    T_Test.setText("Complete one page in every category and hit 'TEST'.\nMissing/invalid input will be marked red.");
                    T_Human.setVisibility(8);
                    B_Solve.setEnabled(false);
                    enableDisableView(LB,true);
                    if (visCat==0){B_PrevCat.setEnabled(false);}
                    if (visCat==EEE.length-1){B_NextCat.setEnabled(false);}
                    for (int r=0;r<EEE.length;r++)
                    {
                        if (visPage[r]==0){B_PrevPage[r].setEnabled(false);}
                        if (visPage[r]==EEE[r].length-1){B_NextPage[r].setEnabled(false);}
                    }
                }
            }
        });

        //B_Solve=new CustomButton(this,120,50,"SOLVE",20,R.color.myYellow);
        B_Solve.setEnabled(false);
        B_Solve.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                localyticsSession.tagEvent("solve_"+type.toLowerCase()+"_click");
                finish();
                startActivity(new Intent(PhysicsInputActivity.this, PhysicsSolverActivity.class));
                /*
                if ((System.currentTimeMillis()-adTimer)/1000<adFreeSeconds)
                {
                    localyticsSession.tagEvent(PhysicsInputActivity.NAG_SKIP);
                    finish();
                    startActivity(new Intent(PhysicsInputActivity.this, PhysicsSolverActivity.class));
                }
                else
                {
                    localyticsSession.tagEvent(PhysicsInputActivity.NAG_START);
                    finish();
                    startActivity(new Intent(PhysicsInputActivity.this, PhysicsNagActivity.class));
                }
                */
            }
        });

        LAA.addView(B_Test);
        LAA.addView(B_Solve);

        //T_Test=new CustomTextView(this,-2,-2,14,"Complete one page in every category.\n");
        T_Test.setText("Complete one page in every category and hit 'TEST'.\nMissing/invalid input will be marked red.");

        //T_Human=new CustomTextView(this,-2,-2,14,getHumanReadableText());
        T_Human.setVisibility(8);

        LA.addView(LAA);
        LA.addView(T_Test);
        LA.addView(T_Human);

        //LB=new CustomLinearLayout(this,-1,-2,1);
        LCatList=new CustomLinearLayout(this,-1,-2,0);

        //B_PrevCat=new CustomButton(this,50,50,"<=",20);
        B_PrevCat.setEnabled(false);
        B_PrevCat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                {
                    if (visCat>0)
                    {
                        if (--visCat==0){B_PrevCat.setEnabled(false);}
                        B_NextCat.setEnabled(true);
                        T_Cat[visCat].setVisibility(0);
                        L_C[visCat].setVisibility(0);
                        T_Cat[visCat+1].setVisibility(8);
                        L_C[visCat+1].setVisibility(8);
                    }
                }
            }
        });

        LCatList.addView(B_PrevCat);

        LB.addView(LCatList);
        L1.addView(LA);
        L1.addView(LB);


        for (int c=0;c<EEE.length;c++)
        {
///////////////////////////////////////////////////////////////////////////////////////////////////////
            final int r=c;
            EEE[r]=new CustomEditText[Integer.parseInt(ss[i].substring(1,ss[i].indexOf("-")))][];
            factor[r]=new double[EEE[r].length][];
            S_P[r]=new CustomScrollView[EEE[r].length];
            L_P[r]=new CustomLinearLayout[EEE[r].length];
            T_P[r]=new CustomTextView[EEE[r].length];

            T_Cat[r]=new CustomTextView(this,150,50,Integer.parseInt(ss[i].substring(ss[i].indexOf("-")+1, ss[i].indexOf("-",ss[i].indexOf("-")+1))),ss[i].substring(ss[i].indexOf(":")+2));
            T_Cat[r].setGravity(17);

            LCatList.addView(T_Cat[r]);

            L_C[r]=new CustomLinearLayout(this,-1,-2,1);

            T_Cat[r].setVisibility(r==0?0:8);
            L_C[r].setVisibility(r==0?0:8);

            LPageList[r]=new CustomLinearLayout(this,-1,-2);

            B_PrevPage[r]=new CustomButton(this,50,50,"<=",20);
            B_PrevPage[r].setEnabled(false);
            B_PrevPage[r].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (visPage[r]>0)
                    {
                        if (--visPage[r]==0){B_PrevPage[r].setEnabled(false);}
                        B_NextPage[r].setEnabled(true);
                        T_P[r][visPage[r]].setVisibility(0);
                        S_P[r][visPage[r]].setVisibility(0);
                        T_P[r][visPage[r]+1].setVisibility(8);
                        S_P[r][visPage[r]+1].setVisibility(8);
                        option[r]=visPage[r]+1;
                        for (int t=0;t<EEE[r][visPage[r]+1].length;t++)
                        {
                            EEE[r][visPage[r]+1][t].setBackgroundColor(Color.WHITE);
                        }
                    }
                }
            });

            LPageList[r].addView(B_PrevPage[r]);

            L_C[r].addView(LPageList[r]);
            LB.addView(L_C[r]);

            i++;
            for (int s=0;s<EEE[r].length;s++)
            {
///////////////////////////////////////////////////////////////////////////////////////////////////////
                EEE[r][s]=new CustomEditText[Integer.parseInt(ss[i].substring(1,ss[i].indexOf("-")))];
                factor[r][s]=new double[EEE[r][s].length];
                T_P[r][s]=new CustomTextView(this,50,50,14,"PAGE "+Integer.toString(s+1));
                T_P[r][s].setVisibility(s==0?0:8);
                T_P[r][s].setGravity(17);

                LPageList[r].addView(T_P[r][s]);

                S_P[r][s]=new CustomScrollView(this,-1,-1);
                S_P[r][s].setVisibility(s==0?0:8);

                L_P[r][s]=new CustomLinearLayout(this,-1,-2,1);

                CustomTextView pageTitle=new CustomTextView(this,-2,-2,14,ss[i].substring(ss[i].indexOf("-")+1));

                L_P[r][s].addView(pageTitle);
                S_P[r][s].addView(L_P[r][s]);
                L_C[r].addView(S_P[r][s]);

                i++;
                for (int t=0;t<EEE[r][s].length;t++)
                {
///////////////////////////////////////////////////////////////////////////////////////////////////////
                    final CustomLinearLayout LField=new CustomLinearLayout(this,-1,-2,0);

                    i++;

                    EEE[r][s][t]=new CustomEditText(this);

                    LField.addView(EEE[r][s][t]);

                    if ("unitless".equals(ss[i])){factor[r][s][t]=1;}
                    else{LField.addView(makeSpinner(t,ss[i],factor[r][s],SunitChoice[r][s]));}

                    L_P[r][s].addView(new CustomTextView(this,-2,-2,14,ss[(i++-1)]));
                    L_P[r][s].addView(LField);
///////////////////////////////////////////////////////////////////////////////////////////////////////
                }
///////////////////////////////////////////////////////////////////////////////////////////////////////
            }

            B_NextPage[r]=new CustomButton(this,50,50,"=>",20);
            if (EEE[r].length==1){B_NextPage[r].setEnabled(false);}
            B_NextPage[r].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (visPage[r]<EEE[r].length-1)
                    {
                        if (++visPage[r]==EEE[r].length-1){B_NextPage[r].setEnabled(false);}
                        B_PrevPage[r].setEnabled(true);
                        T_P[r][visPage[r]].setVisibility(0);
                        S_P[r][visPage[r]].setVisibility(0);
                        T_P[r][visPage[r]-1].setVisibility(8);
                        S_P[r][visPage[r]-1].setVisibility(8);
                        option[r]=visPage[r]+1;
                        for (int t=0;t<EEE[r][visPage[r]-1].length;t++)
                        {
                            EEE[r][visPage[r]-1][t].setBackgroundColor(Color.WHITE);
                        }
                    }
                }
            });

            LPageList[r].addView(B_NextPage[r]);
///////////////////////////////////////////////////////////////////////////////////////////////////////
        }

        //B_NextCat=new CustomButton(this,50,50,"=>",20);
        if (EEE.length==1){B_NextCat.setEnabled(false);}
        B_NextCat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (visCat<EEE.length-1)
                {
                    if (++visCat==EEE.length-1){B_NextCat.setEnabled(false);}
                    B_PrevCat.setEnabled(true);
                    T_Cat[visCat].setVisibility(0);
                    L_C[visCat].setVisibility(0);
                    T_Cat[visCat-1].setVisibility(8);
                    L_C[visCat-1].setVisibility(8);
                }
            }
        });

        LCatList.addView(B_NextCat);

        return L1;
    }
}