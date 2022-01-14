package com.musicslayer.physicstutor;

import android.graphics.PorterDuff;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import com.localytics.android.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
//TODO A Problem should really be a class of its own to give to other programs
public class PhysicsInput2Activity extends PhysicsActivity {
    final private static String SOLVE_PROJECTILE_CLICK = "solve_projectile_click";
    final private static String SOLVE_INCLINE_CLICK = "solve_incline_click";
    final private static String SOLVE_SIMPLE_INCLINE_CLICK = "solve_simple_incline_click";
    final private static String SOLVE_SIMPLE_SPRING_CLICK = "solve_simple_spring_click";
    final private static String NAG_SKIP = "nag_skip";
    private LocalyticsSession localyticsSession;
    private LinearLayout L, L1, LA, LB, LAA, LCatList, LPageList[]=new LinearLayout[3], L_P[][]=new LinearLayout[3][6], L_C[]=new LinearLayout[3];
    private Button B_Test, B_Solve, B_PrevCat, B_NextCat, B_PrevPage[]=new Button[3], B_NextPage[]=new Button[3];
    private TextView T_Test, T_Human, B_Cat[]=new TextView[3], B_P[][]=new TextView[3][6];
    private ScrollView S_P[][]=new ScrollView[3][6];
    private HorizontalScrollView H;
    private EditText EEE[][][]=new EditText[3][6][12];
    private Spinner SSS[][][]=new Spinner[3][6][12];
    private String ss[]=new String[100];

    private int i, v, r, s, t, isError, isReady=0, numC, numP[]=new int[3], numF[][]=new int[3][6], visCat=0, visPage[]={0,0,0};
    private double factor[][][] = new double[3][6][12];

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
        startActivity(new Intent(PhysicsInput2Activity.this, PhysicsInfoActivity.class));
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
            L=makeProblem(R.raw.input_projectile);
            setTitle("Physics Tutor - Projectile Problem");
        }
        else if ("VECTORS".equals(type))
        {
            L=makeProblem(R.raw.input_vectors);
            setTitle("Physics Tutor - Vectors Problem");
        }
        else if ("INCLINE_SIMPLE".equals(type))
        {
            L=makeProblem(R.raw.input_incline_simple);
            setTitle("Physics Tutor - Simple Incline Problem");
        }
        else if ("INCLINE".equals(type))
        {
            L=makeProblem(R.raw.input_incline);
            setTitle("Physics Tutor - Incline Problem");
        }
        else if ("SPRING_SIMPLE".equals(type))
        {
            L=makeProblem(R.raw.input_spring_simple);
            setTitle("Physics Tutor - Simple Spring Problem");
        }
        else if ("SPRING".equals(type))
        {
            L=makeProblem(R.raw.input_spring);
            setTitle("Physics Tutor - Spring Problem");
        }
        setContentView(L);
        this.localyticsSession = new LocalyticsSession(this.getApplicationContext(),LOCALYTICS);
        this.localyticsSession.open();
        this.localyticsSession.upload();
        loadPrefs();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            L.setOrientation(0);
        }
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            L.setOrientation(1);
        }
    }

    private LinearLayout makeProblem (int filename) {
        //TODO The number of digits is fixed in the file for the problem for many fields (what if you have 100 categories?).
        //TODO Array Limits too big? or perhaps too small?
        try
        {
            BufferedReader file = new BufferedReader(new InputStreamReader(this.getResources().openRawResource(filename)));
            for (i=0;(ss[i] = file.readLine()) != null;i++);
        }
        catch (IOException e)
        {
        }
        numC=Integer.parseInt(ss[0].substring(1,2));

        L1=new LinearLayout(this);
        L1.setOrientation(1);
        L1.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));

        LA=new LinearLayout(this);
        LA.setOrientation(1);
        //LA.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        LA.setLayoutParams(new ViewGroup.LayoutParams(dpToPx((int)(350*scale)), -2));

        LAA=new LinearLayout(this);
        LAA.setOrientation(0);
        LAA.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        B_Test=makeTestButton();
        B_Solve=makeSolveButton();

        LAA.addView(B_Test);
        LAA.addView(B_Solve);

        T_Test=new TextView(this);
        T_Test.setTextSize(1,14*scale);
        T_Test.setText("Complete one page in every category.");
        T_Test.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        T_Human=new TextView(this);
        T_Human.setTextSize(1,14*scale);
        T_Human.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        T_Human.setVisibility(8);

        LA.addView(LAA);
        LA.addView(T_Test);
        LA.addView(T_Human);

        LB=new LinearLayout(this);
        LB.setOrientation(1);
        LB.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        H=new HorizontalScrollView(this);
        H.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        LCatList=new LinearLayout(this);
        LCatList.setOrientation(0);
        LCatList.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        B_PrevCat=makePrevCatButton();
        B_PrevCat.setEnabled(false);
        LCatList.addView(B_PrevCat);

        H.addView(LCatList);
        LB.addView(H);
        L1.addView(LA);
        L1.addView(LB);

        i=1;
        for (r=0;r<numC;r++)
        {
            makeCategory();
        }

        B_NextCat=makeNextCatButton();
        if (numC==1)
        {
            B_NextCat.setEnabled(false);
        }
        LCatList.addView(B_NextCat);

        return L1;
    }

    private void makeCategory() {
        numP[r]=Integer.parseInt(ss[i].substring(1,2));

        B_Cat[r]=makeCatButton();
        LCatList.addView(B_Cat[r]);

        L_C[r]=new LinearLayout(this);
        L_C[r].setOrientation(1);

        if (r==0)
        {
            B_Cat[r].setVisibility(0);
            L_C[r].setVisibility(0);
        }
        else
        {
            B_Cat[r].setVisibility(8);
            L_C[r].setVisibility(8);
        }

        LPageList[r]=new LinearLayout(this);

        B_PrevPage[r]=makePrevPageButton(r);
        B_PrevPage[r].setEnabled(false);
        LPageList[r].addView(B_PrevPage[r]);

        L_C[r].addView(LPageList[r]);
        LB.addView(L_C[r]);

        i++;
        for (s=0;s<numP[r];s++)
        {
            makePage();
        }

        B_NextPage[r]=makeNextPageButton(r);
        if (numP[r]==1)
        {
            B_NextPage[r].setEnabled(false);
        }
        LPageList[r].addView(B_NextPage[r]);
    }

    private void makePage() {
        //numF[r][s]=Integer.parseInt(ss[i].substring(1,2));
        numF[r][s]=Integer.parseInt(ss[i].substring(1,ss[i].indexOf("-")));

        B_P[r][s]=makePageButton(s);
        LPageList[r].addView(B_P[r][s]);

        S_P[r][s]=new ScrollView(this);

        if (s==0)
        {
            B_P[r][s].setVisibility(0);
            S_P[r][s].setVisibility(0);
        }
        else
        {
            B_P[r][s].setVisibility(8);
            S_P[r][s].setVisibility(8);
        }

        L_P[r][s]=new LinearLayout(this);
        L_P[r][s].setVisibility(0);
        L_P[r][s].setOrientation(1);

        TextView pageTitle=new TextView(this);
        //pageTitle.setText(ss[i].substring(3));
        pageTitle.setTextSize(1,14*scale);
        pageTitle.setText(ss[i].substring(ss[i].indexOf("-")+1));

        L_P[r][s].addView(pageTitle);
        S_P[r][s].addView(L_P[r][s]);
        L_C[r].addView(S_P[r][s]);

        i++;
        for (t=0;t<numF[r][s];t++)
        {
            makeField();
        }
    }

    private void makeField() {
        TextView fieldTitle=new TextView(this);
        fieldTitle.setTextSize(1,14*scale);
        fieldTitle.setText(ss[i]);

        i++;

        HorizontalScrollView SField=new HorizontalScrollView(this);
        LinearLayout LField=new LinearLayout(this);
        LField.setOrientation(0);

        EEE[r][s][t]=new EditText(this);
        EEE[r][s][t].setTextSize(1,14*scale);
        EEE[r][s][t].setWidth(dpToPx((int)(130*scale)));
        EEE[r][s][t].setHeight(-2);

        TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(dpToPx((int)(30*scale)), 0, 0, 0);
        fieldTitle.setLayoutParams(params);
        EEE[r][s][t].setLayoutParams(params);

        LField.addView(EEE[r][s][t]);
        if ("unitless".equals(ss[i]))
        {
            factor[r][s][t]=1;

        }
        else
        {
            SSS[r][s][t]=makeSpinner(r,s,t,ss[i]);
            LField.addView(SSS[r][s][t]);
        }

        L_P[r][s].addView(fieldTitle);
        SField.addView(LField);
        L_P[r][s].addView(SField);

        i++;
    }

    private TextView makeCatButton() {
        final TextView t=new TextView(this);
        t.setTextSize((int)(Integer.parseInt(ss[i].substring(3, 5))*scale));
        t.setText(ss[i].substring(18));
        t.setWidth(dpToPx((int)(150*scale)));
        t.setHeight(dpToPx((int)(50*scale)));
        t.setGravity(17);
        return t;
    }

    private TextView makePageButton(final int s) {
        final TextView T=new TextView(this);
        T.setTextSize(1,14*scale);
        T.setText("PAGE "+Integer.toString(s+1));
        T.setWidth(dpToPx((int)(50*scale)));
        T.setHeight(dpToPx((int)(50*scale)));
        T.setGravity(17);
        return T;
    }

    private Button makeTestButton() {
        final Button b=new Button(this);
        b.setTextSize(1,20*scale);
        b.setWidth(dpToPx((int)(120*scale)));
        b.setHeight(dpToPx((int)(50*scale)));
        b.setText("TEST");
        b.getBackground().setColorFilter(0xFFFFFF66, PorterDuff.Mode.MULTIPLY);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View vv) {
                int r, s, t;
                v=0;
                if (isReady==0)
                {
                    isError=0;
                    for (r=0;r<numC;r++)
                    {
                        B_Cat[r].invalidate();
                        B_Cat[r].setTextColor(-65536);
                        s=option[r]-1;
                        try
                        {
                            for (t=0;t<numF[r][s];t++)
                            {
                                T_Test.setText(Double.valueOf(EEE[r][s][t].getText().toString()).toString());
                                var[v++]=factor[r][s][t]*Double.valueOf(EEE[r][s][t].getText().toString());
                            }
                            B_Cat[r].invalidate();
                            B_Cat[r].setTextColor(-16711936);
                        }
                        catch (NumberFormatException e)
                        {
                            isError=1;
                            B_Solve.setEnabled(false);
                        }
                    }
                    while (v<12) {var[v++]=0;}
                    T_Test.setText("Missing/invalid input in red categories.");
                    if (isError==0)
                    {
                        isReady=1;
                        b.setText("EDIT");
                        T_Test.setText("You may proceed by clicking 'SOLVE'.");
                        T_Human.setText(getHumanReadableText());
                        T_Human.setVisibility(0);
                        enableDisableView(LB,false);
                        B_Solve.setEnabled(true);
                    }
                }
                else
                {
                    isReady=0;
                    B_Solve.setEnabled(false);
                    b.setText("TEST");
                    T_Test.setText("Complete one page in every category.");
                    T_Human.setVisibility(8);
                    enableDisableView(LB,true);
                    if (visCat==0)
                    {
                        B_PrevCat.setEnabled(false);
                    }
                    if (visCat==numC-1)
                    {
                        B_NextCat.setEnabled(false);
                    }
                    for (r=0;r<numC;r++)
                    {
                        if (visPage[r]==0)
                        {
                            B_PrevPage[r].setEnabled(false);
                        }
                        if (visPage[r]==numP[r]-1)
                        {
                            B_NextPage[r].setEnabled(false);
                        }
                    }
                }
            }
        });
        return b;
    }

    private Button makeSolveButton() {
        final Button b=new Button(this);
        b.setTextSize(1,20*scale);
        b.setWidth(dpToPx((int)(120*scale)));
        b.setHeight(dpToPx((int)(50*scale)));
        b.setText("SOLVE");
        b.setEnabled(false);
        b.getBackground().setColorFilter(0xFFFFFF66, PorterDuff.Mode.MULTIPLY);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if ("PROJECTILE".equals(type))
                {
                    localyticsSession.tagEvent(PhysicsInput2Activity.SOLVE_PROJECTILE_CLICK);
                }
                else if ("INCLINE".equals(type))
                {
                    localyticsSession.tagEvent(PhysicsInput2Activity.SOLVE_INCLINE_CLICK);
                }
                else if ("INCLINE_SIMPLE".equals(type))
                {
                    localyticsSession.tagEvent(PhysicsInput2Activity.SOLVE_SIMPLE_INCLINE_CLICK);
                }
                else if ("SPRING_SIMPLE".equals(type))
                {
                    localyticsSession.tagEvent(PhysicsInput2Activity.SOLVE_SIMPLE_SPRING_CLICK);
                }
                savePrefs();
                try
                {
                    //if ((new Date().getTime()-adTimer.getTime())/1000<adFreeTimeSec)
                    //if (adFreeSeconds>0)
                    if ((new Date().getTime()-adTimer.getTime())/1000<adFreeSeconds)
                    {
                        localyticsSession.tagEvent(PhysicsInput2Activity.NAG_SKIP);
                        startActivity(new Intent(PhysicsInput2Activity.this, PhysicsSolverActivity.class));
                    }
                    else
                    {
                        startActivity(new Intent(PhysicsInput2Activity.this, PhysicsNagActivity.class));
                    }
                }
                catch(RuntimeException e)
                {
                    startActivity(new Intent(PhysicsInput2Activity.this, PhysicsNagActivity.class));
                }
            }
        });
        return b;
    }

    private Button makePrevCatButton() {
        final Button b=new Button(this);
        b.setTextSize(1,20*scale);
        b.setText("<=");
        b.setWidth(dpToPx((int)(50*scale)));
        b.setHeight(dpToPx((int)(50*scale)));
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (visCat>0)
                {
                    visCat--;
                    B_NextCat.setEnabled(true);
                    if (visCat==0)
                    {
                        B_PrevCat.setEnabled(false);
                    }
                    for (int c=0;c<numC;c++)
                    {
                        if (c==visCat)
                        {
                            B_Cat[c].setVisibility(0);
                            L_C[c].setVisibility(0);
                        }
                        else
                        {
                            B_Cat[c].setVisibility(8);
                            L_C[c].setVisibility(8);
                        }
                    }
                }
            }
        });
        return b;
    }

    private Button makeNextCatButton() {
        final Button b=new Button(this);
        b.setTextSize(1,20*scale);
        b.setText("=>");
        b.setWidth(dpToPx((int)(50*scale)));
        b.setHeight(dpToPx((int)(50*scale)));
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (visCat<numC-1)
                {
                    visCat++;
                    B_PrevCat.setEnabled(true);
                    if (visCat==numC-1)
                    {
                        B_NextCat.setEnabled(false);
                    }
                    for (int c=0;c<numC;c++)
                    {
                        if (c==visCat)
                        {
                            B_Cat[c].setVisibility(0);
                            L_C[c].setVisibility(0);
                        }
                        else
                        {
                            B_Cat[c].setVisibility(8);
                            L_C[c].setVisibility(8);
                        }
                    }
                }
            }
        });
        return b;
    }

    private Button makePrevPageButton(final int r) {
        final Button b=new Button(this);
        b.setTextSize(1,20*scale);
        b.setText("<=");
        b.setWidth(dpToPx((int)(50*scale)));
        b.setHeight(dpToPx((int)(50*scale)));
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (visPage[r]>0)
                {
                    visPage[r]--;
                    B_NextPage[r].setEnabled(true);
                    if (visPage[r]==0)
                    {
                        B_PrevPage[r].setEnabled(false);
                    }
                    option[r]=visPage[r]+1;
                    for (int c=0;c<numP[r];c++)
                    {
                        if (c==visPage[r])
                        {
                            B_P[r][c].setVisibility(0);
                            S_P[r][c].setVisibility(0);
                        }
                        else
                        {
                            B_P[r][c].setVisibility(8);
                            S_P[r][c].setVisibility(8);
                        }
                    }
                }
            }
        });
        return b;
    }

    private Button makeNextPageButton(final int r) {
        final Button b=new Button(this);
        b.setTextSize(1,20*scale);
        b.setText("=>");
        b.setWidth(dpToPx((int)(50*scale)));
        b.setHeight(dpToPx((int)(50*scale)));
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (visPage[r]<numP[r]-1)
                {
                    visPage[r]++;
                    B_PrevPage[r].setEnabled(true);
                    if (visPage[r]==numP[r]-1)
                    {
                        B_NextPage[r].setEnabled(false);
                    }
                    option[r]=visPage[r]+1;
                    for (int c=0;c<numP[r];c++)
                    {
                        if (c==visPage[r])
                        {
                            B_P[r][c].setVisibility(0);
                            S_P[r][c].setVisibility(0);
                        }
                        else
                        {
                            B_P[r][c].setVisibility(8);
                            S_P[r][c].setVisibility(8);
                        }
                    }
                }
            }
        });
        return b;
    }

    private Spinner makeSpinner(final int r, final int s, final int t, final String unit) {
        final Spinner S=new Spinner(this);
        final CustomAdapter<CharSequence> A = new CustomAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, this.getResources().getTextArray(getResources().getIdentifier("unit_"+unit, "array", getPackageName())));
        A.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        S.setPrompt("Unit ("+unit+"):");
        S.setAdapter(A);
        if ("length".equals(unit)){
            S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    switch (pos) {
                        case 0:
                            factor[r][s][t] = 1;
                            break;
                        case 1:
                            factor[r][s][t] = 0.01;
                            break;
                        case 2:
                            factor[r][s][t] = 0.001;
                            break;
                        case 3:
                            factor[r][s][t] = 1000;
                            break;
                        case 4:
                            factor[r][s][t] = 0.0254;
                            break;
                        case 5:
                            factor[r][s][t] = 0.3048;
                            break;
                    }
                }

                public void onNothingSelected(AdapterView<?> parent) {
                    factor[r][s][t] = 1;
                }
            });
            S.setSelection(defaultLength);
        }
        else if ("time".equals(unit)){
            S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    switch (pos) {
                        case 0:
                            factor[r][s][t] = 1;
                            break;
                        case 1:
                            factor[r][s][t] = 0.001;
                            break;
                    }
                }

                public void onNothingSelected(AdapterView<?> parent) {
                    factor[r][s][t] = 1;
                }
            });
            S.setSelection(defaultTime);
        }
        else if ("velocity".equals(unit)){
            S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    switch (pos) {
                        case 0:
                            factor[r][s][t] = 1;
                            break;
                        case 1:
                            factor[r][s][t] = 0.01;
                            break;
                        case 2:
                            factor[r][s][t] = 0.001;
                            break;
                        case 3:
                            factor[r][s][t] = 1000;
                            break;
                        case 4:
                            factor[r][s][t] = 0.0254;
                            break;
                        case 5:
                            factor[r][s][t] = 0.3048;
                            break;
                    }
                }

                public void onNothingSelected(AdapterView<?> parent) {
                    factor[r][s][t] = 1;
                }
            });
            S.setSelection(defaultVelocity);
        }
        else if ("angle".equals(unit)){
            S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    switch (pos) {
                        case 0:
                            factor[r][s][t] = 1;
                            break;
                        case 1:
                            factor[r][s][t] = 0.017453292519943295769236907684886;
                            break;
                    }
                }

                public void onNothingSelected(AdapterView<?> parent) {
                    factor[r][s][t] = 1;
                }
            });
            S.setSelection(defaultAngle);
        }
        else if ("mass".equals(unit)){
            S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    switch (pos) {
                        case 0:
                            factor[r][s][t] = 1;
                            break;
                        case 1:
                            factor[r][s][t] = 0.001;
                            break;
                    }
                }

                public void onNothingSelected(AdapterView<?> parent) {
                    factor[r][s][t] = 1;
                }
            });
            S.setSelection(defaultMass);
        }
        else if ("force".equals(unit)){
            S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    switch (pos) {
                        case 0:
                            factor[r][s][t] = 1;
                            break;
                        case 1:
                            factor[r][s][t] = 4.44822162;
                            break;
                    }
                }

                public void onNothingSelected(AdapterView<?> parent) {
                    factor[r][s][t] = 1;
                }
            });
            S.setSelection(defaultForce);
        }
        else if ("energy".equals(unit)){
            S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    switch (pos) {
                        case 0:
                            factor[r][s][t] = 1;
                            break;
                    }
                }

                public void onNothingSelected(AdapterView<?> parent) {
                    factor[r][s][t] = 1;
                }
            });
            S.setSelection(defaultEnergy);
        }
        else if ("frequency".equals(unit)){
            S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    switch (pos) {
                        case 0:
                            factor[r][s][t] = 1;
                            break;
                    }
                }

                public void onNothingSelected(AdapterView<?> parent) {
                    factor[r][s][t] = 1;
                }
            });
            S.setSelection(defaultFrequency);
        }
        else if ("acceleration".equals(unit)){
            S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    switch (pos) {
                        case 0:
                            factor[r][s][t] = 1;
                            break;
                    }
                }

                public void onNothingSelected(AdapterView<?> parent) {
                    factor[r][s][t] = 1;
                }
            });
            S.setSelection(defaultAcceleration);
        }
        return S;
    }

    private String getHumanReadableText()
    {
        String piece1="", piece2="", piece3="", piece4="";
        if ("PROJECTILE".equals(type))
        {
            piece1="An object undergoes projectile motion.\nIt is known that at t="+Double.toString(var[1])+" s, x="+Double.toString(var[0])+" m, and at t="+Double.toString(var[3])+" s, y="+Double.toString(var[2])+" m.";
            if (option[1]==1)
            {
                piece2="\nFurthermore, at t="+Double.toString(var[6])+" s, the magnitude of the velocity ="+Double.toString(var[4])+" m/s and the angle ="+Double.toString(var[5])+" radians.";
            }
            else if (option[1]==2)
            {
                piece2="\nFurthermore, X-velocity is constant and known to be ="+Double.toString(var[4])+" m/s, and at t="+Double.toString(var[6])+" s, the angle ="+Double.toString(var[5])+" radians.";
            }
            else if (option[1]==3)
            {
                piece2="\nFurthermore, at t="+Double.toString(var[5])+" s, the Y-velocity ="+Double.toString(var[4])+" m/s and at t="+Double.toString(var[7])+" s, the angle ="+Double.toString(var[6])+" radians.";
            }
            else if (option[1]==4)
            {
                piece2="\nFurthermore, X-velocity is constant and known to be ="+Double.toString(var[4])+" m/s, and at t="+Double.toString(var[6])+" s, the Y-velocity ="+Double.toString(var[5])+" m/s.";
            }
            else if (option[1]==5)
            {
                piece2="\nFurthermore, at t="+Double.toString(var[5])+" s, x="+Double.toString(var[4])+" m, and at t="+Double.toString(var[7])+" s, y="+Double.toString(var[6])+" m.";
            }
        }
        else if ("VECTORS".equals(type))
        {
            if (option[0]==1)
            {
                piece1="We wish to resolve a vector with magnitude "+Double.toString(var[0])+" N and angle "+Double.toString(var[1])+" radians.";
            }
            else if (option[0]==2)
            {
                piece1="We have "+Integer.toString(option[0])+" vectors that we wish to resolve.";
                piece2="\n\nFirst: Magnitude "+Double.toString(var[0])+" N and angle "+Double.toString(var[1])+" radians." +
                        "\nSecond: Magnitude "+Double.toString(var[2])+" N and angle "+Double.toString(var[3])+" radians.";
            }
            else if (option[0]==3)
            {
                piece1="We have "+Integer.toString(option[0])+" vectors that we wish to resolve.";
                piece2="\n\nFirst: Magnitude "+Double.toString(var[0])+" N and angle "+Double.toString(var[1])+" radians." +
                        "\nSecond: Magnitude "+Double.toString(var[2])+" N and angle "+Double.toString(var[3])+" radians." +
                        "\nThird: Magnitude "+Double.toString(var[4])+" N and angle "+Double.toString(var[5])+" radians.";
            }
            else if (option[0]==4)
            {
                piece1="We have "+Integer.toString(option[0])+" vectors that we wish to resolve.";
                piece2="\n\nFirst: Magnitude "+Double.toString(var[0])+" N and angle "+Double.toString(var[1])+" radians." +
                        "\nSecond: Magnitude "+Double.toString(var[2])+" N and angle "+Double.toString(var[3])+" radians." +
                        "\nThird: Magnitude "+Double.toString(var[4])+" N and angle "+Double.toString(var[5])+" radians." +
                        "\nFourth: Magnitude "+Double.toString(var[6])+" N and angle "+Double.toString(var[7])+" radians.";
            }
            else if (option[0]==5)
            {
                piece1="We have "+Integer.toString(option[0])+" vectors that we wish to resolve.";
                piece2="\n\nFirst: Magnitude "+Double.toString(var[0])+" N and angle "+Double.toString(var[1])+" radians." +
                        "\nSecond: Magnitude "+Double.toString(var[2])+" N and angle "+Double.toString(var[3])+" radians." +
                        "\nThird: Magnitude "+Double.toString(var[4])+" N and angle "+Double.toString(var[5])+" radians." +
                        "\nFourth: Magnitude "+Double.toString(var[6])+" N and angle "+Double.toString(var[7])+" radians." +
                        "\nFifth: Magnitude "+Double.toString(var[8])+" N and angle "+Double.toString(var[9])+" radians.";
            }
            else if (option[0]==6)
            {
                piece1="We have "+Integer.toString(option[0])+" vectors that we wish to resolve.";
                piece2="\n\nFirst: Magnitude "+Double.toString(var[0])+" N and angle "+Double.toString(var[1])+" radians." +
                        "\nSecond: Magnitude "+Double.toString(var[2])+" N and angle "+Double.toString(var[3])+" radians." +
                        "\nThird: Magnitude "+Double.toString(var[4])+" N and angle "+Double.toString(var[5])+" radians." +
                        "\nFourth: Magnitude "+Double.toString(var[6])+" N and angle "+Double.toString(var[7])+" radians." +
                        "\nFifth: Magnitude "+Double.toString(var[8])+" N and angle "+Double.toString(var[9])+" radians." +
                        "\nSixth: Magnitude "+Double.toString(var[10])+" N and angle "+Double.toString(var[11])+" radians.";
            }
        }
        else if ("INCLINE_SIMPLE".equals(type))
        {
            piece1="An object is on an incline. It either sits still or is sliding downward (not upward).";
            if (option[0]==1)
            {
                piece2="\nThe angle of the incline ="+Double.toString(var[0])+" radians, and the mass of the object ="+Double.toString(var[1])+" kg.";
            }
            else if (option[0]==2)
            {
                piece2="\nThe angle of the incline ="+Double.toString(var[0])+" radians, and the force parallel to the incline ="+Double.toString(var[1])+" N.";
            }
            else if (option[0]==3)
            {
                piece2="\nThe angle of the incline ="+Double.toString(var[0])+" radians, and the force normal to the incline ="+Double.toString(var[1])+" N.";
            }
            else if (option[0]==4)
            {
                piece2="\nThe mass of the object ="+Double.toString(var[0])+" kg, and the force parallel to the incline ="+Double.toString(var[1])+" N.";
            }
            else if (option[0]==5)
            {
                piece2="\nThe mass of the object ="+Double.toString(var[0])+" kg, and the force normal to the incline ="+Double.toString(var[1])+" N.";
            }
            else if (option[0]==6)
            {
                piece2="\nThe force parallel to the incline ="+Double.toString(var[0])+" N, and the force normal to the incline ="+Double.toString(var[1])+" N.";
            }
            if (option[1]==1)
            {
                piece3="\nThe static coefficient of friction is "+Double.toString(var[2])+".";
            }
            else if (option[1]==2)
            {
                piece3="\nA force of "+Double.toString(var[2])+" N is required to start moving the object if it sits still.";
            }
            if (option[2]==1)
            {
                piece4="\nThe kinetic coefficient of friction is "+Double.toString(var[3])+".";
            }
            else if (option[2]==2)
            {
                piece4="\nA force of "+Double.toString(var[3])+" N is required to keep moving the object if it is already moving.";
            }
        }
        else if ("INCLINE".equals(type))
        {
            piece1="An object is on an incline.\nThe kinetic coefficient of friction is "+Double.toString(var[0])+".";
            if (option[1]==1)
            {
                piece2="\nThe angle of the incline ="+Double.toString(var[3])+" radians, and at t="+Double.toString(var[2])+" s, the position along the incline ="+Double.toString(var[1])+" m.";
            }
            else if (option[1]==2)
            {
                piece2="\nThe angle of the incline ="+Double.toString(var[3])+" radians, and at t="+Double.toString(var[2])+" s, x="+Double.toString(var[1])+" m.";
            }
            else if (option[1]==3)
            {
                piece2="\nThe angle of the incline ="+Double.toString(var[3])+" radians, and at t="+Double.toString(var[2])+" s, y="+Double.toString(var[1])+" m.";
            }
            else if (option[1]==4)
            {
                piece2="\nAt t="+Double.toString(var[3])+" s, x="+Double.toString(var[1])+" m, and y="+Double.toString(var[2])+" m.";
            }
            piece3="\nAt t="+Double.toString(var[5])+" s, the velocity along the incline ="+Double.toString(var[4])+" m/s.";
        }
        else if ("SPRING_SIMPLE".equals(type))
        {
            piece1="A mass on a frictionless table is attached to an ideal spring.";
            if (option[0]==1)
            {
                piece2="\nThe spring constant k="+Double.toString(var[0])+" N/m.";
                piece3="\nThe equilibrium position of the mass ="+Double.toString(var[1])+" m.";
            }
            else if (option[0]==2)
            {
                piece2="\nThe mass sits at x="+Double.toString(var[0])+" m, and experiences a force with magnitude ="+Double.toString(var[1])+" N.";
                piece3="\nThe equilibrium position of the mass ="+Double.toString(var[2])+" m.";
            }
        }
        else if ("SPRING".equals(type))
        {
            piece1="A mass on a frictionless table is attached to an ideal spring.";
            if (option[0]==1)
            {
                piece2="\nThe mass m="+Double.toString(var[0])+" kg, and the spring constant k="+Double.toString(var[1])+" N/m.";
            }
            else if (option[0]==2)
            {
                piece2="\nThe mass m="+Double.toString(var[0])+" kg, and the angular frequency of oscillation ="+Double.toString(var[1])+" Hz.";
            }
            else if (option[0]==3)
            {
                piece2="\nThe spring constant k="+Double.toString(var[0])+" N/m, and the angular frequency of oscillation ="+Double.toString(var[1])+" Hz.";
            }
            if (option[2]==1)
            {
                piece3="\nThe mass has initial position x="+Double.toString(var[2])+" m and initial velocity v="+Double.toString(var[3])+" m/s.";
            }
            else if (option[2]==2)
            {
                piece3="\nThe amplitude of oscillation is A="+Double.toString(var[2])+" m, and the time offset ="+Double.toString(var[3])+" s.";
            }
            else if (option[2]==3)
            {
                piece3="\nThe amplitude of oscillation is A="+Double.toString(var[2])+" m, and the phase offset ="+Double.toString(var[3])+" radians.";
            }
            else if (option[2]==4)
            {
                piece3="\nThe total energy of the system is E="+Double.toString(var[2])+" J, and the time offset ="+Double.toString(var[3])+" s.";
            }
            else if (option[2]==5)
            {
                piece3="\nThe total energy of the system is E="+Double.toString(var[2])+" m, and the phase offset ="+Double.toString(var[3])+" radians.";
            }
            piece4="\nExpress the position of the mass using the cosine function.";
        }
        return "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"+piece1+piece2+piece3+piece4+"\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n";
    }
}