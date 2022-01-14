package com.musicslayer.physicstutor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PhysicsInputActivity extends PhysicsActivity {
    private int visCat=0;
    private boolean isReady=true;

    public void myOnBackPressed()
    {
        finish();
		startActivity(new Intent(this, PhysicsProblemsActivity.class));
	}
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        else if ("CIRCUIT_LRC".equals(type))
        {
            MAIN=makeInput(R.raw.input_circuit_lrc);
            setTitle("Physics Tutor - LRC Circuit Problem");
        }
        setContentView(MAIN);
        super.onCreate(savedInstanceState);
    }

    private CustomLinearLayout makeInput (int filename) {
        final String qq[]=new String[100];
        final BufferedReader file = new BufferedReader(new InputStreamReader(this.getResources().openRawResource(filename)));
        try
        {
            for (int c=0;(qq[c] = file.readLine()) != null;c++);
            file.close();
        }
        catch (IOException e){}
        final int numCat=Integer.parseInt(qq[0].substring(1));
        final int numPage[]=new int[numCat];
        final int numField[][]=new int[numCat][];
        int q=1;

        final CustomLinearLayout L=new CustomLinearLayout(this,-1,-1,1);
        final CustomLinearLayout LA=new CustomLinearLayout(this,330,-2,1);
        final CustomLinearLayout L_Row=new CustomLinearLayout(this,-2,-2,0);
        final CustomButton B_Test=new CustomButton(this,120,50,"TEST",20,R.color.myYellow);
        final CustomButton B_Solve=new CustomButton(this,120,50,"SOLVE",20,R.color.myYellow);
        final CustomTextView T_Message=new CustomTextView(this,-2,-2,14,"Complete one page in every category and hit 'TEST'.\nMissing/invalid input will be marked red.");
        final CustomLinearLayout LB=new CustomLinearLayout(this,-1,-2,1);
        final CustomLinearLayout LCatList=new CustomLinearLayout(this,-1,-2,0);
        final CustomButton B_PrevCat=new CustomButton(this,50,50,"<=",20);
        final CustomTextView T_Cat[]=new CustomTextView[numCat];
        final CustomButton B_NextCat=new CustomButton(this,50,50,"=>",20);
        final CustomLinearLayout LPageList=new CustomLinearLayout(this,-1,-2);
        final CustomButton B_PrevPage=new CustomButton(this,50,50,"<=",20);
        final CustomTextView T_PageText=new CustomTextView(this,50,50,14,"PAGE 1");
        final CustomButton B_NextPage=new CustomButton(this,50,50,"=>",20);
        final CustomScrollView S_P[][]=new CustomScrollView[numCat][];
        final CustomEditText EEE[][][]=new CustomEditText[numCat][][];

        final double factor[][][] = new double[numCat][][];
        final int visPage[]=new int[numCat];

        L.setFocusable(true);
        L.setFocusableInTouchMode(true);

        B_Test.setOnClickListener(new View.OnClickListener() {
            public void onClick(View vv) {
                int v=0;
                if (isReady)
                {
                    isReady=false;
                    for (int r=0;r<numCat;r++)
                    {
                        boolean isCatError=false;
                        for (int t=0;t<numField[r][option[r]-1];t++)
                        {
                            boolean isFieldError=false;
                            try
                            {
                                var[v]=Double.valueOf(EEE[r][option[r]-1][t].getText().toString());
                                varUnit[v]=factor[r][option[r]-1][t]*var[v];
                                Sunit[v]=SunitChoice[r][option[r]-1][t];
                                v++;
                            }
                            catch (NumberFormatException e){isReady=isCatError=isFieldError=true;}
                            EEE[r][option[r]-1][t].invalidate();
                            EEE[r][option[r]-1][t].setColorFilter(isFieldError?R.color.myRed:R.color.myGreen);
                        }
                        T_Cat[r].setTextColor(isCatError?-65536:-16711936);
                    }
                    while (v<var.length) {var[v++]=0;}
                    if (!isReady)
                    {
                        B_Test.setText("EDIT");
                        T_Message.setText("You may proceed by clicking 'SOLVE'.\n\n"+getHumanReadableText(Sunit));
                        B_Solve.setEnabled(true);
                        enableDisableView(LB,false);
                        L.requestFocus();
                    }
                }
                else
                {
                    isReady=true;
                    B_Test.setText("TEST");
                    T_Message.setText("Complete one page in every category and hit 'TEST'.\nMissing/invalid input will be marked red.");
                    B_Solve.setEnabled(false);
                    enableDisableView(LB,true);
                    B_PrevCat.setEnabled(visCat!=0);
                    B_NextCat.setEnabled(visCat!=numCat-1);
                    B_PrevPage.setEnabled(visPage[visCat]!=0);
                    B_NextPage.setEnabled(visPage[visCat]!=numPage[visCat]-1);
                }
            }
        });

        B_Solve.setEnabled(false);
        B_Solve.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                localyticsSession.tagEvent("solve_"+type.toLowerCase()+"_"+prefOptions[0][prefs[0]].toLowerCase());
                finish();
                startActivity(new Intent(PhysicsInputActivity.this, PhysicsSolverActivity.class));
            }
        });

        L_Row.addView(B_Test);
        L_Row.addView(B_Solve);

        LA.addView(L_Row);
        LA.addView(T_Message);

        B_PrevCat.setEnabled(false);
        B_PrevCat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (visCat>0)
                {
                    if (--visCat==0){B_PrevCat.setEnabled(false);}
                    B_NextCat.setEnabled(true);
                    T_Cat[visCat].setVisibility(0);
                    T_Cat[visCat+1].setVisibility(8);
                    T_PageText.setText("PAGE "+Integer.toString(visPage[visCat]+1));
                    S_P[visCat][visPage[visCat]].setVisibility(0);
                    S_P[visCat+1][visPage[visCat+1]].setVisibility(8);
                    B_PrevPage.setEnabled(visPage[visCat]!=0);
                    B_NextPage.setEnabled(visPage[visCat]!=numPage[visCat]-1);
                }
            }
        });

        LCatList.addView(B_PrevCat);

        LB.addView(LCatList);
        L.addView(LA);
        L.addView(LB);

        B_PrevPage.setEnabled(false);
        B_PrevPage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (visPage[visCat]>0)
                {
                    if (--visPage[visCat]==0){B_PrevPage.setEnabled(false);}
                    B_NextPage.setEnabled(true);
                    T_PageText.setText("PAGE "+Integer.toString(visPage[visCat]+1));
                    S_P[visCat][visPage[visCat]].setVisibility(0);
                    S_P[visCat][visPage[visCat]+1].setVisibility(8);
                    option[visCat]=visPage[visCat]+1;
                }
            }
        });

        T_PageText.setGravity(17);

        LPageList.addView(B_PrevPage);
        LPageList.addView(T_PageText);
        LPageList.addView(B_NextPage);
        LB.addView(LPageList);

        for (int i=0;i<numCat;i++)
        {
            numPage[i]=Integer.parseInt(qq[q].substring(1,qq[q].indexOf("-")));
            numField[i]=new int[numPage[i]];

            T_Cat[i]=new CustomTextView(this,150,50,Integer.parseInt(qq[q].substring(qq[q].indexOf("-")+1, qq[q].indexOf(":"))),qq[q].substring(qq[q].indexOf(":")+1));
            T_Cat[i].setVisibility(i==0?0:8);
            T_Cat[i].setGravity(17);

            LCatList.addView(T_Cat[i]);

            S_P[i]=new CustomScrollView[numPage[i]];
            EEE[i]=new CustomEditText[numPage[i]][];
            factor[i]=new double[numPage[i]][];

            q++;

            for (int j=0;j<numPage[i];j++)
            {
                numField[i][j]=Integer.parseInt(qq[q].substring(1,qq[q].indexOf("-")));

                S_P[i][j]=new CustomScrollView(this,-1,-1);
                S_P[i][j].setVisibility(i+j==0?0:8);

                final CustomLinearLayout page=new CustomLinearLayout(this,-1,-2,1);
                final CustomTextView pageTitle=new CustomTextView(this,-2,-2,14,qq[q].substring(qq[q].indexOf("-")+1));

                page.addView(pageTitle);
                S_P[i][j].addView(page);
                LB.addView(S_P[i][j]);

                EEE[i][j]=new CustomEditText[numField[i][j]];
                factor[i][j]=new double[numField[i][j]];

                q++;

                for (int k=0;k<numField[i][j];k++)
                {
                    final CustomLinearLayout field=new CustomLinearLayout(this,-1,-2,0);

                    EEE[i][j][k]=new CustomEditText(this);
                    EEE[i][j][k].setColorFilter(R.color.myRed);
                    field.addView(EEE[i][j][k]);

                    q++;

                    if ("unitless".equals(qq[q])){factor[i][j][k]=1;}
                    else{field.addView(makeUnitSelector(k,qq[q],factor[i][j],SunitChoice[i][j]));}
                    page.addView(new CustomTextView(this,-2,-2,14,qq[(q++-1)]));
                    page.addView(field);
                }
            }
        }

        if (numPage[visCat]==1){B_NextPage.setEnabled(false);}
        B_NextPage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (visPage[visCat]<numPage[visCat]-1)
                {
                    if (++visPage[visCat]==numPage[visCat]-1){B_NextPage.setEnabled(false);}
                    B_PrevPage.setEnabled(true);
                    T_PageText.setText("PAGE "+Integer.toString(visPage[visCat]+1));
                    S_P[visCat][visPage[visCat]].setVisibility(0);
                    S_P[visCat][visPage[visCat]-1].setVisibility(8);
                    option[visCat]=visPage[visCat]+1;
                }
            }
        });

        if (numCat==1){B_NextCat.setEnabled(false);}
        B_NextCat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (visCat<numCat-1)
                {
                    if (++visCat==numCat-1){B_NextCat.setEnabled(false);}
                    B_PrevCat.setEnabled(true);
                    T_Cat[visCat].setVisibility(0);
                    T_Cat[visCat-1].setVisibility(8);
                    T_PageText.setText("PAGE "+Integer.toString(visPage[visCat]+1));
                    S_P[visCat][visPage[visCat]].setVisibility(0);
                    S_P[visCat-1][visPage[visCat-1]].setVisibility(8);
                    B_PrevPage.setEnabled(visPage[visCat]!=0);
                    B_NextPage.setEnabled(visPage[visCat]!=numPage[visCat]-1);
                }
            }
        });

        LCatList.addView(B_NextCat);

        return L;
    }
}