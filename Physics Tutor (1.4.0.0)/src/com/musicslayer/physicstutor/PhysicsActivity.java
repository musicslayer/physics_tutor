package com.musicslayer.physicstutor;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TableRow;
import com.localytics.android.LocalyticsSession;

class PhysicsActivity extends Activity{
    final static String SunitChoice[][][]=new String[4][12][12];
    final static String Sunit[]={"","","","","","","","","","","",""};
    final static String SunitA1[]={"","","","","","","","","","","",""};
    final static String SunitA2[]={"","","","","","","","","","","",""};
    final static String SunitA3[]={"","","","","","","","","","","",""};

    final static String CRITTERCISM="512a5eaf4f633a0cc0001255";//test
    final static private String LOCALYTICS="7189950503a4221d2ffde5d-fbbf5348-c32c-11e2-33e9-00a426b17dd8";//test
    //final static String CRITTERCISM="512a91ccf716963ade0011d4";//real
    //final static private String LOCALYTICS = "28f315436b14eb32f74145e-af72d1fe-c1cc-11e2-33a6-00a426b17dd8";//real

    static LocalyticsSession localyticsSession;
    static String type="?";
    static CustomLinearLayout MAIN;

    final static int option[]={1,1,1,1};
    static double u1=0, u2=0, u3=0, u4=0, u5=0, u6=0, u7=0, u8=0, u9=0, u10=0, u11=0, u12=0;

    final static double var[]={0,0,0,0,0,0,0,0,0,0,0,0};
    final static double varUnit[]={0,0,0,0,0,0,0,0,0,0,0,0};

    static int selection_g=0;
    static double constant_g=9.8;
    final static int prefs[]={0,0,0};
    final static int defaultUnitPrefix[]={4,4,4,4,3,4,4,4,4,4,4,4,4,4,4,4,4};
    final static int defaultUnit[]={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    final static int surveyChoice[]={-1,-1,-1,-1,-1,-1};

    static float scale=1;

    final static String gravity[]={"9.8","10","Custom"};
    final String prefName[]={"Answer Layout","Keyboard Choice","Variable Text"};
    final String prefOptions[][]={{"Split","Combined"},{"Numbers","Default"},{"Names","Symbols"}};
    final String prefString[][]={{"The explanation will be on a separate screen than the answer fields.",
        "The explanation will be on the same screen as the answer fields."},
        {"The popup keyboard will start with numbers.",
            "The popup keyboard will start as normal."},
        {"Display full variable names throughout the program.",
            "Display variable symbols throughout the program."}};
    final static String unitType[]={"Length","Time","Velocity","Angle","Mass","Force","Energy","Frequency","Acceleration","Spring Constant","Voltage","Resistance","Capacitance","Inductance","Charge","Current","Current Slope"};
    final static String unitPrefix[]={"T","G","M","k","","c","m","\u03BC"};
    final static double unitFactorPrefix[]={1000000000000f,1000000000,1000000,1000,1,0.01,0.001,0.000001};
    final static String unitText[][]={{"m","in","ft"},{"s"},{"m/s","in/s","ft/s"},{"radians","degrees"},{"g"},{"N","lb"},{"J"},{"Hz"},{"m/s^2"},{"N/m"},{"V"},{"Ohm"},{"F"},{"H"},{"C"},{"A"},{"A/s"}};
    final static double unitFactor[][]={{1,0.0254,0.3048},{1},{1,0.0254,0.3048},{1,0.01745329},{0.001},{1,4.44822162},{1},{1},{1},{1},{1},{1},{1},{1},{1},{1},{1}};

    @Override
    public void onPause() 
    {
        savePrefs();
    	localyticsSession.close();
        localyticsSession.upload();
    	super.onPause();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        localyticsSession.open();
    }

    @Override
    public void onBackPressed()
    {
        this.myOnBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        setScale();
        customRedrawView(MAIN);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            MAIN.setOrientation(0);
        }
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            MAIN.setOrientation(1);
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event)
    {
        switch (event.getKeyCode())
        {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    scale+=0.1f;
                    savePrefs();
                }
                customRedrawView(MAIN);
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    scale-=0.1f;
                    savePrefs();
                }
                customRedrawView(MAIN);
                return true;
            case KeyEvent.KEYCODE_BACK:
                if(Build.VERSION.SDK_INT<5)
                {
                    if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount()==0)
                    {
                        this.myOnBackPressed();
                    }
                    return true;
                }
            default:
                return super.dispatchKeyEvent(event);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        localyticsSession = new LocalyticsSession(getApplicationContext(),LOCALYTICS);
		localyticsSession.open();
		localyticsSession.upload();
		loadPrefs();
        setScale();
        customRedrawView(MAIN);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            MAIN.setOrientation(0);
        }
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            MAIN.setOrientation(1);
        }
    }

    void myOnBackPressed(){}

    void savePrefs()
    {
    	SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putFloat("scale", scale);
        editor.putString("type", type);

        editor.putInt("sc1", surveyChoice[0]);
        editor.putInt("sc2", surveyChoice[1]);
        editor.putInt("sc3", surveyChoice[2]);
        editor.putInt("sc4", surveyChoice[3]);
        editor.putInt("sc5", surveyChoice[4]);
        editor.putInt("sc6", surveyChoice[5]);

        editor.putInt("op1", option[0]);
        editor.putInt("op2", option[1]);
        editor.putInt("op3", option[2]);
        editor.putInt("op4", option[3]);

        editor.putFloat("in1", (float)u1);
        editor.putFloat("in2", (float)u2);
        editor.putFloat("in3", (float)u3);
        editor.putFloat("in4", (float)u4);
        editor.putFloat("in5", (float)u5);
        editor.putFloat("in6", (float)u6);
        editor.putFloat("in7", (float)u7);
        editor.putFloat("in8", (float)u8);
        editor.putFloat("in9", (float)u9);
        editor.putFloat("in10", (float)u10);
        editor.putFloat("in11", (float)u11);
        editor.putFloat("in12", (float)u12);

        editor.putFloat("inn0", (float)var[0]);
        editor.putFloat("inn1", (float)var[1]);
        editor.putFloat("inn2", (float)var[2]);
        editor.putFloat("inn3", (float)var[3]);
        editor.putFloat("inn4", (float)var[4]);
        editor.putFloat("inn5", (float)var[5]);
        editor.putFloat("inn6", (float)var[6]);
        editor.putFloat("inn7", (float)var[7]);
        editor.putFloat("inn8", (float)var[8]);
        editor.putFloat("inn9", (float)var[9]);
        editor.putFloat("inn10", (float)var[10]);
        editor.putFloat("inn11", (float)var[11]);

        editor.putFloat("cv1", (float)constant_g);
        editor.putInt("cs1", selection_g);

        //TODO these should be in loops
        editor.putInt("s0", prefs[0]);
        editor.putInt("s1", prefs[1]);
        editor.putInt("s2", prefs[2]);

        editor.putInt("un0", defaultUnit[0]);
        editor.putInt("un1", defaultUnit[1]);
        editor.putInt("un2", defaultUnit[2]);
        editor.putInt("un3", defaultUnit[3]);
        editor.putInt("un4", defaultUnit[4]);
        editor.putInt("un5", defaultUnit[5]);
        editor.putInt("un6", defaultUnit[6]);
        editor.putInt("un7", defaultUnit[7]);
        editor.putInt("un8", defaultUnit[8]);
        editor.putInt("un9", defaultUnit[9]);
        editor.putInt("un10", defaultUnit[10]);
        editor.putInt("un11", defaultUnit[11]);
        editor.putInt("un12", defaultUnit[12]);
        editor.putInt("un13", defaultUnit[13]);
        editor.putInt("un14", defaultUnit[14]);
        editor.putInt("un15", defaultUnit[15]);
        editor.putInt("un16", defaultUnit[16]);

        editor.putInt("unp0", defaultUnitPrefix[0]);
        editor.putInt("unp1", defaultUnitPrefix[1]);
        editor.putInt("unp2", defaultUnitPrefix[2]);
        editor.putInt("unp3", defaultUnitPrefix[3]);
        editor.putInt("unp4", defaultUnitPrefix[4]);
        editor.putInt("unp5", defaultUnitPrefix[5]);
        editor.putInt("unp6", defaultUnitPrefix[6]);
        editor.putInt("unp7", defaultUnitPrefix[7]);
        editor.putInt("unp8", defaultUnitPrefix[8]);
        editor.putInt("unp9", defaultUnitPrefix[9]);
        editor.putInt("unp10", defaultUnitPrefix[10]);
        editor.putInt("unp11", defaultUnitPrefix[11]);
        editor.putInt("unp12", defaultUnitPrefix[12]);
        editor.putInt("unp13", defaultUnitPrefix[13]);
        editor.putInt("unp14", defaultUnitPrefix[14]);
        editor.putInt("unp15", defaultUnitPrefix[15]);
        editor.putInt("unp16", defaultUnitPrefix[16]);
        editor.commit();
    }

    private void loadPrefs()
    {
    	SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);
        if(typeStoN(type)==-1)
        {
            scale=preferences.getFloat("scale", scale);
		    type=preferences.getString("type", "?");

            surveyChoice[0]=preferences.getInt("sc1", surveyChoice[0]);
            surveyChoice[1]=preferences.getInt("sc2", surveyChoice[1]);
            surveyChoice[2]=preferences.getInt("sc3", surveyChoice[2]);
            surveyChoice[3]=preferences.getInt("sc4", surveyChoice[3]);
            surveyChoice[4]=preferences.getInt("sc5", surveyChoice[4]);
            surveyChoice[5]=preferences.getInt("sc6", surveyChoice[5]);

            option[0]=preferences.getInt("op1", option[0]);
		    option[1]=preferences.getInt("op2", option[1]);
		    option[2]=preferences.getInt("op3", option[2]);
		    option[3]=preferences.getInt("op4", option[3]);

		    u1=preferences.getFloat("in1", (float)u1);
		    u2=preferences.getFloat("in2", (float)u2);
		    u3=preferences.getFloat("in3", (float)u3);
		    u4=preferences.getFloat("in4", (float)u4);
		    u5=preferences.getFloat("in5", (float)u5);
		    u6=preferences.getFloat("in6", (float)u6);
		    u7=preferences.getFloat("in7", (float)u7);
		    u8=preferences.getFloat("in8", (float)u8);
		    u9=preferences.getFloat("in9", (float)u9);
		    u10=preferences.getFloat("in10", (float)u10);
            u11=preferences.getFloat("in11", (float)u11);
            u12=preferences.getFloat("in12", (float)u12);

            var[0]=preferences.getFloat("inn0", (float)var[0]);
            var[1]=preferences.getFloat("inn1", (float)var[1]);
            var[2]=preferences.getFloat("inn2", (float)var[2]);
            var[3]=preferences.getFloat("inn3", (float)var[3]);
            var[4]=preferences.getFloat("inn4", (float)var[4]);
            var[5]=preferences.getFloat("inn5", (float)var[5]);
            var[6]=preferences.getFloat("inn6", (float)var[6]);
            var[7]=preferences.getFloat("inn7", (float)var[7]);
            var[8]=preferences.getFloat("inn8", (float)var[8]);
            var[9]=preferences.getFloat("inn9", (float)var[9]);
            var[10]=preferences.getFloat("inn10", (float)var[10]);
            var[11]=preferences.getFloat("inn11", (float)var[11]);

            constant_g=preferences.getFloat("cv1", (float)constant_g);
		    selection_g=preferences.getInt("cs1", selection_g);

            prefs[0]=preferences.getInt("s0", prefs[0]);
            prefs[1]=preferences.getInt("s1", prefs[1]);
            prefs[2]=preferences.getInt("s2", prefs[2]);

            defaultUnit[0]=preferences.getInt("un0", defaultUnit[0]);
            defaultUnit[1]=preferences.getInt("un1", defaultUnit[1]);
            defaultUnit[2]=preferences.getInt("un2", defaultUnit[2]);
            defaultUnit[3]=preferences.getInt("un3", defaultUnit[3]);
            defaultUnit[4]=preferences.getInt("un4", defaultUnit[4]);
            defaultUnit[5]=preferences.getInt("un5", defaultUnit[5]);
            defaultUnit[6]=preferences.getInt("un6", defaultUnit[6]);
            defaultUnit[7]=preferences.getInt("un7", defaultUnit[7]);
            defaultUnit[8]=preferences.getInt("un8", defaultUnit[8]);
            defaultUnit[9]=preferences.getInt("un9", defaultUnit[9]);
            defaultUnit[10]=preferences.getInt("un10", defaultUnit[10]);
            defaultUnit[11]=preferences.getInt("un11", defaultUnit[11]);
            defaultUnit[12]=preferences.getInt("un12", defaultUnit[12]);
            defaultUnit[13]=preferences.getInt("un13", defaultUnit[13]);
            defaultUnit[14]=preferences.getInt("un14", defaultUnit[14]);
            defaultUnit[15]=preferences.getInt("un15", defaultUnit[15]);
            defaultUnit[16]=preferences.getInt("un16", defaultUnit[16]);

            defaultUnitPrefix[0]=preferences.getInt("unp0", defaultUnitPrefix[0]);
            defaultUnitPrefix[1]=preferences.getInt("unp1", defaultUnitPrefix[1]);
            defaultUnitPrefix[2]=preferences.getInt("unp2", defaultUnitPrefix[2]);
            defaultUnitPrefix[3]=preferences.getInt("unp3", defaultUnitPrefix[3]);
            defaultUnitPrefix[4]=preferences.getInt("unp4", defaultUnitPrefix[4]);
            defaultUnitPrefix[5]=preferences.getInt("unp5", defaultUnitPrefix[5]);
            defaultUnitPrefix[6]=preferences.getInt("unp6", defaultUnitPrefix[6]);
            defaultUnitPrefix[7]=preferences.getInt("unp7", defaultUnitPrefix[7]);
            defaultUnitPrefix[8]=preferences.getInt("unp8", defaultUnitPrefix[8]);
            defaultUnitPrefix[9]=preferences.getInt("unp9", defaultUnitPrefix[9]);
            defaultUnitPrefix[10]=preferences.getInt("unp10", defaultUnitPrefix[10]);
            defaultUnitPrefix[11]=preferences.getInt("unp11", defaultUnitPrefix[11]);
            defaultUnitPrefix[12]=preferences.getInt("unp12", defaultUnitPrefix[12]);
            defaultUnitPrefix[13]=preferences.getInt("unp13", defaultUnitPrefix[13]);
            defaultUnitPrefix[14]=preferences.getInt("unp14", defaultUnitPrefix[14]);
            defaultUnitPrefix[15]=preferences.getInt("unp15", defaultUnitPrefix[15]);
            defaultUnitPrefix[16]=preferences.getInt("unp16", defaultUnitPrefix[16]);
        }
    }

    double mod(final double x,final double y)
    {
        final double result=x%y;
        return result<0?result+y:result;
    }

    int dpToPx(int dp)
    {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }

    private int pxToDp(int px)
    {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        return Math.round((float)px / density);
    }

    private void setScale()
    {
        float widthRatio, heightRatio;
        int statusHeight = pxToDp(getResources().getDimensionPixelSize(getResources().getIdentifier("status_bar_height", "dimen", "android")));
        if (statusHeight==48) {statusHeight=0;}
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            widthRatio = pxToDp(getWindowManager().getDefaultDisplay().getWidth())/640f;
            heightRatio = (pxToDp(getWindowManager().getDefaultDisplay().getHeight())-25-statusHeight)/310f;
            scale=Math.min(widthRatio,heightRatio);
            savePrefs();
        }
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            widthRatio = pxToDp(getWindowManager().getDefaultDisplay().getWidth())/360f;
            heightRatio = (pxToDp(getWindowManager().getDefaultDisplay().getHeight())-25-statusHeight)/590f;
            scale=Math.min(widthRatio,heightRatio);
            savePrefs();
        }
    }

    void enableDisableView(View view, boolean enabled)
    {
        view.setEnabled(enabled);
        if (view instanceof ViewGroup)
        {
            ViewGroup group=(ViewGroup) view;
            for (int idx=0;idx<group.getChildCount();idx++)
            {
                enableDisableView(group.getChildAt(idx), enabled);
            }
        }
    }

    private void customRedrawView(View view)
    {
        if (CustomButton.class.equals(view.getClass())) {
            ((CustomButton) view).update();
        }
        else if (CustomTextView.class.equals(view.getClass())) {
            ((CustomTextView) view).update();
        }
        else if (CustomEditText.class.equals(view.getClass())) {
            ((CustomEditText) view).update();
        }
        else if (CustomLinearLayout.class.equals(view.getClass())) {
            ((CustomLinearLayout) view).update();
            ViewGroup group=(ViewGroup) view;
            View c;
            for (int idx=0;idx<group.getChildCount();idx++) {
                c = group.getChildAt(idx);
                group.removeView(c);
                customRedrawView(c);
                group.addView(c,idx);
            }
        }
        else if (CustomSpinner.class.equals(view.getClass())) {
            ((CustomSpinner) view).update();
        }
        else if (CustomImageView.class.equals(view.getClass())) {
            ((CustomImageView) view).update();
        }
        else if (ZoomCustomImageView.class.equals(view.getClass())) {
            ((ZoomCustomImageView) view).update();
        }
        else if (TableRow.class.equals(view.getClass())) {
            ViewGroup group=(ViewGroup) view;
            View c;
            for (int idx=0;idx<group.getChildCount();idx++) {
                c = group.getChildAt(idx);
                customRedrawView(c);
            }
        }
        else if (CustomTableRow.class.equals(view.getClass())) {
            ViewGroup group=(ViewGroup) view;
            View c;
            for (int idx=0;idx<group.getChildCount();idx++) {
                c = group.getChildAt(idx);
                customRedrawView(c);
            }
        }
        else if (view instanceof ViewGroup) {
            ViewGroup group=(ViewGroup) view;
            View c;
            for (int idx=0;idx<group.getChildCount();idx++) {
                c = group.getChildAt(idx);
                group.removeView(c);
                customRedrawView(c);
                group.addView(c,idx);
            }
        }
    }

    String getHumanReadableText(final String[] Sunit)
    {
        final String[] varName;
        String piece1="", piece2="", piece3="", piece4="";
        if ("PROJECTILE".equals(type))
        {
            if(prefs[2]==0){varName=new String[]{"Time","X-position","Y-position","Angle","X-velocity","Y-velocity","Overall Velocity"};}
            else{varName=new String[]{"t","x(t)","y(t)","\u03B8(t)","Vx","Vy(t)","V(t)"};}

            piece1="An object undergoes projectile motion.\nIt is known that at "+varName[0]+"="+Double.toString(var[1])+" "+Sunit[1]+", "+varName[1]+"="+Double.toString(var[0])+" "+Sunit[0]+", and at "+varName[0]+"="+Double.toString(var[3])+" "+Sunit[3]+", "+varName[2]+"="+Double.toString(var[2])+" "+Sunit[2]+".";
            if (option[1]==1)
            {
                piece2="\nFurthermore, at "+varName[0]+"="+Double.toString(var[6])+" "+Sunit[6]+", "+varName[6]+"="+Double.toString(var[4])+" "+Sunit[4]+" and "+varName[3]+"="+Double.toString(var[5])+" "+Sunit[5]+".";
            }
            else if (option[1]==2)
            {
                piece2="\nFurthermore, "+varName[4]+" is constant and known to be ="+Double.toString(var[4])+" "+Sunit[4]+", and at "+varName[0]+"="+Double.toString(var[6])+" "+Sunit[6]+", "+varName[3]+"="+Double.toString(var[5])+" "+Sunit[5]+".";
            }
            else if (option[1]==3)
            {
                piece2="\nFurthermore, at "+varName[0]+"="+Double.toString(var[5])+" "+Sunit[5]+", "+varName[5]+"="+Double.toString(var[4])+" "+Sunit[4]+" and at "+varName[0]+"="+Double.toString(var[7])+" "+Sunit[7]+", "+varName[3]+"="+Double.toString(var[6])+" "+Sunit[6]+".";
            }
            else if (option[1]==4)
            {
                piece2="\nFurthermore, "+varName[4]+" is constant and known to be ="+Double.toString(var[4])+" "+Sunit[4]+", and at "+varName[0]+"="+Double.toString(var[6])+" "+Sunit[6]+", the "+varName[5]+"="+Double.toString(var[5])+" "+Sunit[5]+".";
            }
            else if (option[1]==5)
            {
                piece2="\nFurthermore, at "+varName[0]+"="+Double.toString(var[5])+" "+Sunit[5]+", "+varName[1]+"="+Double.toString(var[4])+" "+Sunit[4]+", and at "+varName[0]+"="+Double.toString(var[7])+" "+Sunit[7]+", "+varName[2]+"="+Double.toString(var[6])+" "+Sunit[6]+".";
            }
        }
        else if ("VECTORS".equals(type))
        {
            if(prefs[2]==0){varName=new String[]{"Magnitude","Angle"};}
            else{varName=new String[]{"Magnitude","\u03B8"};}

            if (option[0]==1)
            {
                piece1="We wish to resolve a vector with "+varName[0]+"="+Double.toString(var[0])+" "+Sunit[0]+" and "+varName[1]+"="+Double.toString(var[1])+" "+Sunit[1]+".";
            }
            else if (option[0]==2)
            {
                piece1="We have "+Integer.toString(option[0])+" vectors that we wish to resolve.";
                piece2="\n\nFirst: "+varName[0]+"="+Double.toString(var[0])+" "+Sunit[0]+" and "+varName[1]+"="+Double.toString(var[1])+" "+Sunit[1]+"." +
                    "\nSecond: "+varName[0]+"="+Double.toString(var[2])+" "+Sunit[2]+" and "+varName[1]+"="+Double.toString(var[3])+" "+Sunit[3]+".";
            }
            else if (option[0]==3)
            {
                piece1="We have "+Integer.toString(option[0])+" vectors that we wish to resolve.";
                piece2="\n\nFirst: "+varName[0]+"="+Double.toString(var[0])+" "+Sunit[0]+" and "+varName[1]+"="+Double.toString(var[1])+" "+Sunit[1]+"." +
                    "\nSecond: "+varName[0]+"="+Double.toString(var[2])+" "+Sunit[2]+" and "+varName[1]+"="+Double.toString(var[3])+" "+Sunit[3]+"." +
                    "\nThird: "+varName[0]+"="+Double.toString(var[4])+" "+Sunit[4]+" and "+varName[1]+"="+Double.toString(var[5])+" "+Sunit[5]+".";
            }
            else if (option[0]==4)
            {
                piece1="We have "+Integer.toString(option[0])+" vectors that we wish to resolve.";
                piece2="\n\nFirst: "+varName[0]+"="+Double.toString(var[0])+" "+Sunit[0]+" and "+varName[1]+"="+Double.toString(var[1])+" "+Sunit[1]+"." +
                    "\nSecond: "+varName[0]+"="+Double.toString(var[2])+" "+Sunit[2]+" and "+varName[1]+"="+Double.toString(var[3])+" "+Sunit[3]+"." +
                    "\nThird: "+varName[0]+"="+Double.toString(var[4])+" "+Sunit[4]+" and "+varName[1]+"="+Double.toString(var[5])+" "+Sunit[5]+"." +
                    "\nFourth: "+varName[0]+"="+Double.toString(var[6])+" "+Sunit[6]+" and "+varName[1]+"="+Double.toString(var[7])+" "+Sunit[7]+".";
            }
            else if (option[0]==5)
            {
                piece1="We have "+Integer.toString(option[0])+" vectors that we wish to resolve.";
                piece2="\n\nFirst: "+varName[0]+"="+Double.toString(var[0])+" "+Sunit[0]+" and "+varName[1]+"="+Double.toString(var[1])+" "+Sunit[1]+"." +
                    "\nSecond: "+varName[0]+"="+Double.toString(var[2])+" "+Sunit[2]+" and "+varName[1]+"="+Double.toString(var[3])+" "+Sunit[3]+"." +
                    "\nThird: "+varName[0]+"="+Double.toString(var[4])+" "+Sunit[4]+" and "+varName[1]+"="+Double.toString(var[5])+" "+Sunit[5]+"." +
                    "\nFourth: "+varName[0]+"="+Double.toString(var[6])+" "+Sunit[6]+" and "+varName[1]+"="+Double.toString(var[7])+" "+Sunit[7]+"." +
                    "\nFifth: "+varName[0]+"="+Double.toString(var[8])+" "+Sunit[8]+" and "+varName[1]+"="+Double.toString(var[9])+" "+Sunit[9]+".";
            }
            else if (option[0]==6)
            {
                piece1="We have "+Integer.toString(option[0])+" vectors that we wish to resolve.";
                piece2="\n\nFirst: "+varName[0]+"="+Double.toString(var[0])+" "+Sunit[0]+" and "+varName[1]+"="+Double.toString(var[1])+" "+Sunit[1]+"." +
                    "\nSecond: "+varName[0]+"="+Double.toString(var[2])+" "+Sunit[2]+" and "+varName[1]+"="+Double.toString(var[3])+" "+Sunit[3]+"." +
                    "\nThird: "+varName[0]+"="+Double.toString(var[4])+" "+Sunit[4]+" and "+varName[1]+"="+Double.toString(var[5])+" "+Sunit[5]+"." +
                    "\nFourth: "+varName[0]+"="+Double.toString(var[6])+" "+Sunit[6]+" and "+varName[1]+"="+Double.toString(var[7])+" "+Sunit[7]+"." +
                    "\nFifth: "+varName[0]+"="+Double.toString(var[8])+" "+Sunit[8]+" and "+varName[1]+"="+Double.toString(var[9])+" "+Sunit[9]+"." +
                    "\nSixth: "+varName[0]+"="+Double.toString(var[10])+" "+Sunit[10]+" and "+varName[1]+"="+Double.toString(var[11])+" "+Sunit[11]+".";
            }
        }
        else if ("INCLINE_SIMPLE".equals(type))
        {
            if(prefs[2]==0){varName=new String[]{"The Angle of the Incline","The Mass of the Object","The Force Parallel to the Incline","The Force Normal to the Incline","The Static Coefficient of Friction","The Kinetic Coefficient of Friction"};}
            else{varName=new String[]{"\u03B8","M","F-Parallel","F-Normal","\u03BCs","\u03BCk"};}

            piece1="An object is on an incline. It either sits still or is sliding downward (not upward).";
            if (option[0]==1)
            {
                piece2="\n"+varName[0]+"="+Double.toString(var[0])+" "+Sunit[0]+", and "+varName[1]+"="+Double.toString(var[1])+" "+Sunit[1]+".";
            }
            else if (option[0]==2)
            {
                piece2="\n"+varName[0]+"="+Double.toString(var[0])+" "+Sunit[0]+", and "+varName[2]+"="+Double.toString(var[1])+" "+Sunit[1]+".";
            }
            else if (option[0]==3)
            {
                piece2="\n"+varName[0]+"="+Double.toString(var[0])+" "+Sunit[0]+", and "+varName[3]+"="+Double.toString(var[1])+" "+Sunit[1]+".";
            }
            else if (option[0]==4)
            {
                piece2="\n"+varName[1]+"="+Double.toString(var[0])+" "+Sunit[0]+", and "+varName[2]+"="+Double.toString(var[1])+" "+Sunit[1]+".";
            }
            else if (option[0]==5)
            {
                piece2="\n"+varName[1]+"="+Double.toString(var[0])+" "+Sunit[0]+", and "+varName[3]+"="+Double.toString(var[1])+" "+Sunit[1]+".";
            }
            else if (option[0]==6)
            {
                piece2="\n"+varName[2]+"="+Double.toString(var[0])+" "+Sunit[0]+", and "+varName[3]+"="+Double.toString(var[1])+" "+Sunit[1]+".";
            }
            if (option[1]==1)
            {
                piece3="\n"+varName[4]+"="+Double.toString(var[2])+".";
            }
            else if (option[1]==2)
            {
                piece3="\nA force of "+Double.toString(var[2])+" "+Sunit[2]+" is required to start moving the object if it sits still.";
            }
            if (option[2]==1)
            {
                piece4="\n"+varName[5]+"="+Double.toString(var[3])+".";
            }
            else if (option[2]==2)
            {
                piece4="\nA force of "+Double.toString(var[3])+" "+Sunit[3]+" is required to keep moving the object if it is already moving.";
            }
        }
        else if ("INCLINE".equals(type))
        {
            if(prefs[2]==0){varName=new String[]{"The Kinetic Coefficient of Friction","The Angle of the Incline","Time","X-position","Y-position","The Position Along the Incline","The Velocity Along the Incline"};}
            else{varName=new String[]{"\u03BCk","\u03B8","t","x(t)","y(t)","s(t)","Vs(t)"};}

            piece1="An object is on an incline.\n"+varName[0]+"="+Double.toString(var[0])+".";
            if (option[1]==1)
            {
                piece2="\n"+varName[1]+"="+Double.toString(var[3])+" "+Sunit[3]+", and at "+varName[2]+"="+Double.toString(var[2])+" "+Sunit[2]+", "+varName[5]+"="+Double.toString(var[1])+" "+Sunit[1]+".";
            }
            else if (option[1]==2)
            {
                piece2="\n"+varName[1]+"="+Double.toString(var[3])+" "+Sunit[3]+", and at "+varName[2]+"="+Double.toString(var[2])+" "+Sunit[2]+", "+varName[3]+"="+Double.toString(var[1])+" "+Sunit[1]+".";
            }
            else if (option[1]==3)
            {
                piece2="\n"+varName[1]+"="+Double.toString(var[3])+" "+Sunit[3]+", and at "+varName[2]+"="+Double.toString(var[2])+" "+Sunit[2]+", "+varName[4]+"="+Double.toString(var[1])+" "+Sunit[1]+".";
            }
            else if (option[1]==4)
            {
                piece2="\nAt "+varName[2]+"="+Double.toString(var[3])+" "+Sunit[3]+", "+varName[3]+"="+Double.toString(var[1])+" "+Sunit[1]+", and "+varName[4]+"="+Double.toString(var[2])+" "+Sunit[2]+".";
            }
            piece3="\nAt "+varName[2]+"="+Double.toString(var[5])+" "+Sunit[5]+", "+varName[6]+"="+Double.toString(var[4])+" "+Sunit[4]+".";
        }
        else if ("SPRING_SIMPLE".equals(type))
        {
            if(prefs[2]==0){varName=new String[]{"The Spring Constant","The Equilibrium Position","X-position"};}
            else{varName=new String[]{"k","x-eq","x"};}

            piece1="A mass on a frictionless table is attached to an ideal spring.";
            if (option[0]==1)
            {
                piece2="\n"+varName[0]+"="+Double.toString(var[0])+" "+Sunit[0]+".";
                piece3="\n"+varName[1]+"="+Double.toString(var[1])+" "+Sunit[1]+".";
            }
            else if (option[0]==2)
            {
                piece2="\nThe mass sits at "+varName[2]+"="+Double.toString(var[0])+" "+Sunit[0]+", and experiences a force with magnitude ="+Double.toString(var[1])+" "+Sunit[1]+".";
                piece3="\n"+varName[1]+"="+Double.toString(var[2])+" "+Sunit[2]+".";
            }
        }
        else if ("SPRING".equals(type))
        {
            if(prefs[2]==0){varName=new String[]{"The Mass","The Spring Constant","The Angular Frequency of Oscillation","Position","Velocity","The Amplitude of Oscillation","The Time Offset","The Phase Shift","The Total Energy"};}
            else{varName=new String[]{"M","k","\u03C9","x","Vx","A","ts","\u03A6","E"};}

            piece1="A mass on a frictionless table is attached to an ideal spring.";
            if (option[0]==1)
            {
                piece2="\n"+varName[0]+"="+Double.toString(var[0])+" "+Sunit[0]+", and "+varName[1]+"="+Double.toString(var[1])+" "+Sunit[1]+".";
            }
            else if (option[0]==2)
            {
                piece2="\n"+varName[0]+"="+Double.toString(var[0])+" "+Sunit[0]+", and "+varName[2]+"="+Double.toString(var[1])+" "+Sunit[1]+".";
            }
            else if (option[0]==3)
            {
                piece2="\n"+varName[1]+"="+Double.toString(var[0])+" "+Sunit[0]+", and "+varName[2]+"="+Double.toString(var[1])+" "+Sunit[1]+".";
            }
            if (option[2]==1)
            {
                piece3="\nThe mass has Initial "+varName[3]+"="+Double.toString(var[2])+" "+Sunit[2]+" and Initial "+varName[4]+"="+Double.toString(var[3])+" "+Sunit[3]+".";
            }
            else if (option[2]==2)
            {
                piece3="\n"+varName[5]+"="+Double.toString(var[2])+" "+Sunit[2]+", and "+varName[6]+"="+Double.toString(var[3])+" "+Sunit[3]+".";
            }
            else if (option[2]==3)
            {
                piece3="\n"+varName[5]+"="+Double.toString(var[2])+" "+Sunit[2]+", and "+varName[7]+"="+Double.toString(var[3])+" "+Sunit[3]+".";
            }
            else if (option[2]==4)
            {
                piece3="\n"+varName[8]+"="+Double.toString(var[2])+" "+Sunit[2]+", and "+varName[6]+"="+Double.toString(var[3])+" "+Sunit[3]+".";
            }
            else if (option[2]==5)
            {
                piece3="\n"+varName[8]+"="+Double.toString(var[2])+" "+Sunit[2]+", and "+varName[7]+"="+Double.toString(var[3])+" "+Sunit[3]+".";
            }
            if (option[1]==1)
            {
                piece4="\nExpress the position of the mass using the cosine function.";
            }
            else if (option[1]==2)
            {
                piece4="\nExpress the position of the mass using the sine function.";
            }
        }
        else if ("CIRCUIT_LRC".equals(type))
        {
            if(prefs[2]==0){varName=new String[]{"Voltage","Time","Resistance","Capacitance","Inductance","Charge","Capacitor Voltage","Current"};}
            else{varName=new String[]{"V","t","R","C","L","Q","VC","I"};}

            int offset=0;
            piece1="A circuit has a DC ideal voltage source with "+varName[0]+"="+Double.toString(var[0])+" "+Sunit[0]+", which acts from "+varName[1]+"=0 s onward.";
            if(option[1]==2)
            {
                piece2="\nThere is an ideal resistor with "+varName[2]+"="+Double.toString(var[1+offset])+" "+Sunit[1+offset]+" attached in series.";
                offset++;
            }
            else
            {
                piece2="\nThere is no resistor.";
            }
            if(option[2]==3)
            {
                piece3="\nThere is an ideal capacitor with "+varName[3]+"="+Double.toString(var[1+offset])+" "+Sunit[1+offset]+" and Initial "+varName[6]+"="+Double.toString(var[2+offset])+" "+Sunit[2+offset]+" attached in series.";
                offset+=2;
            }
            else if(option[2]==2)
            {
                piece3="\nThere is an ideal capacitor with "+varName[3]+"="+Double.toString(var[1+offset])+" "+Sunit[1+offset]+" and Initial "+varName[5]+"="+Double.toString(var[2+offset])+" "+Sunit[2+offset]+" attached in series.";
                offset+=2;
            }
            else
            {
                piece3="\nThere is no capacitor.";
            }
            if(option[3]==2)
            {
                piece4="\nThere is an ideal inductor with "+varName[4]+"="+Double.toString(var[1+offset])+" "+Sunit[1+offset]+" and Initial "+varName[7]+"="+Double.toString(var[2+offset])+" "+Sunit[2+offset]+" attached in series.";
            }
            else
            {
                piece4="\nThere is no inductor.";
            }
        }
        return piece1+piece2+piece3+piece4;
    }

    CustomLinearLayout makeUnitSelector(final int i, final String unit, final double[] factor, final String[] SunitChoice)
    {
        final double d[]={1,1};
        final String s[]={"",""};

        final CustomLinearLayout L=new CustomLinearLayout(this,120,-2,0);
        final CustomSpinner SL=new CustomSpinner(this,unitPrefix,"SI Prefix ("+unitType[unitStoN(unit)]+"):",0,40,50);
        SL.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> parent) {}
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                d[0]=unitFactorPrefix[pos];
                factor[i]=d[0]*d[1];
                s[0]=unitPrefix[pos];
                SunitChoice[i]=s[0]+s[1];
            }
        });
        SL.setSelection(defaultUnitPrefix[unitStoN(unit)]);

        final CustomSpinner SR=new CustomSpinner(this,unitText[unitStoN(unit)],"Unit ("+unit+"):");
        SR.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> parent) {}
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                d[1]=unitFactor[unitStoN(unit)][pos];
                factor[i]=d[0]*d[1];
                s[1]=unitText[unitStoN(unit)][pos];
                SunitChoice[i]=s[0]+s[1];
            }
        });
        SR.setSelection(defaultUnit[unitStoN(unit)]);

        factor[i]=unitFactorPrefix[defaultUnitPrefix[unitStoN(unit)]]*unitFactor[unitStoN(unit)][defaultUnit[unitStoN(unit)]];
        SunitChoice[i]=unitPrefix[defaultUnitPrefix[unitStoN(unit)]]+unitText[unitStoN(unit)][defaultUnit[unitStoN(unit)]];

        L.addView(SL);
        L.addView(SR);
        return L;
    }

    int unitStoN(final String s)
    {
        if ("length".equals(s)){return 0;}
        else if ("time".equals(s)){return 1;}
        else if ("velocity".equals(s)){return 2;}
        else if ("angle".equals(s)){return 3;}
        else if ("mass".equals(s)){return 4;}
        else if ("force".equals(s)){return 5;}
        else if ("energy".equals(s)){return 6;}
        else if ("frequency".equals(s)){return 7;}
        else if ("acceleration".equals(s)){return 8;}
        else if ("springconstant".equals(s)){return 9;}
        else if ("voltage".equals(s)){return 10;}
        else if ("resistance".equals(s)){return 11;}
        else if ("capacitance".equals(s)){return 12;}
        else if ("inductance".equals(s)){return 13;}
        else if ("charge".equals(s)){return 14;}
        else if ("current".equals(s)){return 15;}
        else if ("current_slope".equals(s)){return 16;}
        return -1;
    }

    int typeStoN(final String s)
    {
        if ("PROJECTILE".equals(s)){return 0;}
        else if ("VECTORS".equals(s)){return 1;}
        else if ("INCLINE_SIMPLE".equals(s)){return 2;}
        else if ("INCLINE".equals(s)){return 3;}
        else if ("SPRING_SIMPLE".equals(s)){return 4;}
        else if ("SPRING".equals(s)){return 5;}
        else if ("CIRCUIT_LRC".equals(s)){return 6;}
        return -1;
    }
}