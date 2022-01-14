package com.musicslayer.physicstutor;

import android.content.res.Configuration;
import android.os.Build;
import android.view.*;
import android.widget.*;
import com.localytics.android.*;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

class PhysicsActivity extends Activity{
    final static String SunitChoice[][][]=new String[4][12][12];
    final static String Sunit[]={"","","","","","","","","","","",""};
    final static String SunitA1[]={"","","","","","","","","","","",""};
    final static String SunitA2[]={"","","","","","","","","","","",""};
    final static String SunitA3[]={"","","","","","","","","","","",""};

    final static boolean SWITCH=true;
    final static String CRITTERCISM="512a5eaf4f633a0cc0001255";//test
    final static private String LOCALYTICS="0cf7118ce64f63d3f7fd5fe-83b51a88-93d1-11e0-ffa7-007f58cb3154";//test
    //final public static String CRITTERCISM="512a91ccf716963ade0011d4";//real
    //final private static String LOCALYTICS = "28f315436b14eb32f74145e-af72d1fe-c1cc-11e2-33a6-00a426b17dd8";//real

    //TODO Can this be final?
    static LocalyticsSession localyticsSession;
    static String type="?";
    static CustomLinearLayout MAIN;

    final static int surveyChoice[]={-1,-1,-1,-1,-1,-1}, option[]={1,1,1,1};
    final static double var[]={0,0,0,0,0,0,0,0,0,0,0,0};
    final static double varUnit[]={0,0,0,0,0,0,0,0,0,0,0,0};
    static int selection_g=0, answerChoice=0, keyboardChoice=0, defaultLength=0, defaultTime=0, defaultVelocity=0, defaultAngle=0, defaultMass=0, defaultForce=0, defaultEnergy=0, defaultFrequency=0, defaultAcceleration=0, defaultSpringConstant=0;
    static double u1=0, u2=0, u3=0, u4=0, u5=0, u6=0, u7=0, u8=0, u9=0, u10=0, u11=0, u12=0, constant_g=9.8;
    static float scale=1;

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
    public void onBackPressed() {
        this.myOnBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setScale();
        customRedrawView(MAIN);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    scale+=0.1f;
                    savePrefs();
                }
                customRedrawView(MAIN);
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    scale-=0.1f;
                    savePrefs();
                }
                customRedrawView(MAIN);
                return true;
            case KeyEvent.KEYCODE_BACK:
                if(Build.VERSION.SDK_INT<5)
                {
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        this.myOnBackPressed();
                    }
                return true;
                }
            default:
                return super.dispatchKeyEvent(event);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO can I do this above, to make this final
        localyticsSession = new LocalyticsSession(getApplicationContext(),LOCALYTICS);
		localyticsSession.open();
		localyticsSession.upload();
		loadPrefs();
        setScale();
    }

    void myOnBackPressed()
    {
    }

    void savePrefs() {
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

        editor.putInt("s1", answerChoice);
        editor.putInt("s2", keyboardChoice);
        editor.putInt("un1", defaultLength);
        editor.putInt("un2", defaultTime);
        editor.putInt("un3", defaultVelocity);
        editor.putInt("un4", defaultAngle);
        editor.putInt("un5", defaultMass);
        editor.putInt("un6", defaultForce);
        editor.putInt("un7", defaultEnergy);
        editor.putInt("un8", defaultFrequency);
        editor.putInt("un9", defaultAcceleration);
        editor.putInt("un10", defaultSpringConstant);
        editor.commit();
    }

    private void loadPrefs() {
    	SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);
        if (!"PROJECTILE".equals(type) && !"VECTORS".equals(type) && !"INCLINE_SIMPLE".equals(type) && !"INCLINE".equals(type) && !"SPRING_SIMPLE".equals(type) && !"SPRING".equals(type)&& !"CHARGE".equals(type))
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

            answerChoice=preferences.getInt("s1", answerChoice);
            keyboardChoice=preferences.getInt("s2", keyboardChoice);
		    defaultLength=preferences.getInt("un1", defaultLength);
		    defaultTime=preferences.getInt("un2", defaultTime);
		    defaultVelocity=preferences.getInt("un3", defaultVelocity);
		    defaultAngle=preferences.getInt("un4", defaultAngle);
		    defaultMass=preferences.getInt("un5", defaultMass);
		    defaultForce=preferences.getInt("un6", defaultForce);
            defaultForce=preferences.getInt("un7", defaultEnergy);
            defaultForce=preferences.getInt("un8", defaultFrequency);
            defaultAcceleration=preferences.getInt("un9", defaultAcceleration);
            defaultSpringConstant=preferences.getInt("un10", defaultSpringConstant);
        }
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

    void enableDisableView(View view, boolean enabled) {
        view.setEnabled(enabled);
        if (view instanceof ViewGroup) {
            ViewGroup group=(ViewGroup) view;
            for (int idx=0;idx<group.getChildCount();idx++) {
                enableDisableView(group.getChildAt(idx), enabled);
            }
        }
    }

    private void customRedrawView(View view) {
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

    String getHumanReadableText(final String[] Sunit) {
        String piece1="", piece2="", piece3="", piece4="";
        if ("PROJECTILE".equals(type))
        {
            piece1="An object undergoes projectile motion.\nIt is known that at t="+Double.toString(var[1])+" "+Sunit[1]+", x="+Double.toString(var[0])+" "+Sunit[0]+", and at t="+Double.toString(var[3])+" "+Sunit[3]+", y="+Double.toString(var[2])+" "+Sunit[2]+".";
            if (option[1]==1)
            {
                piece2="\nFurthermore, at t="+Double.toString(var[6])+" "+Sunit[6]+", the magnitude of the velocity ="+Double.toString(var[4])+" "+Sunit[4]+" and the angle ="+Double.toString(var[5])+" "+Sunit[5]+".";
            }
            else if (option[1]==2)
            {
                piece2="\nFurthermore, X-velocity is constant and known to be ="+Double.toString(var[4])+" "+Sunit[4]+", and at t="+Double.toString(var[6])+" "+Sunit[6]+", the angle ="+Double.toString(var[5])+" "+Sunit[5]+".";
            }
            else if (option[1]==3)
            {
                piece2="\nFurthermore, at t="+Double.toString(var[5])+" "+Sunit[5]+", the Y-velocity ="+Double.toString(var[4])+" "+Sunit[4]+" and at t="+Double.toString(var[7])+" "+Sunit[7]+", the angle ="+Double.toString(var[6])+" "+Sunit[6]+".";
            }
            else if (option[1]==4)
            {
                piece2="\nFurthermore, X-velocity is constant and known to be ="+Double.toString(var[4])+" "+Sunit[4]+", and at t="+Double.toString(var[6])+" "+Sunit[6]+", the Y-velocity ="+Double.toString(var[5])+" "+Sunit[5]+".";
            }
            else if (option[1]==5)
            {
                piece2="\nFurthermore, at t="+Double.toString(var[5])+" "+Sunit[5]+", x="+Double.toString(var[4])+" "+Sunit[4]+", and at t="+Double.toString(var[7])+" "+Sunit[7]+", y="+Double.toString(var[6])+" "+Sunit[6]+".";
            }
        }
        else if ("VECTORS".equals(type))
        {
            if (option[0]==1)
            {
                piece1="We wish to resolve a vector with magnitude "+Double.toString(var[0])+" "+Sunit[0]+" and angle "+Double.toString(var[1])+" "+Sunit[1]+".";
            }
            else if (option[0]==2)
            {
                piece1="We have "+Integer.toString(option[0])+" vectors that we wish to resolve.";
                piece2="\n\nFirst: Magnitude "+Double.toString(var[0])+" "+Sunit[0]+" and angle "+Double.toString(var[1])+" "+Sunit[1]+"." +
                    "\nSecond: Magnitude "+Double.toString(var[2])+" "+Sunit[2]+" and angle "+Double.toString(var[3])+" "+Sunit[3]+".";
            }
            else if (option[0]==3)
            {
                piece1="We have "+Integer.toString(option[0])+" vectors that we wish to resolve.";
                piece2="\n\nFirst: Magnitude "+Double.toString(var[0])+" "+Sunit[0]+" and angle "+Double.toString(var[1])+" "+Sunit[1]+"." +
                    "\nSecond: Magnitude "+Double.toString(var[2])+" "+Sunit[2]+" and angle "+Double.toString(var[3])+" "+Sunit[3]+"." +
                    "\nThird: Magnitude "+Double.toString(var[4])+" "+Sunit[4]+" and angle "+Double.toString(var[5])+" "+Sunit[5]+".";
            }
            else if (option[0]==4)
            {
                piece1="We have "+Integer.toString(option[0])+" vectors that we wish to resolve.";
                piece2="\n\nFirst: Magnitude "+Double.toString(var[0])+" "+Sunit[0]+" and angle "+Double.toString(var[1])+" "+Sunit[1]+"." +
                    "\nSecond: Magnitude "+Double.toString(var[2])+" "+Sunit[2]+" and angle "+Double.toString(var[3])+" "+Sunit[3]+"." +
                    "\nThird: Magnitude "+Double.toString(var[4])+" "+Sunit[4]+" and angle "+Double.toString(var[5])+" "+Sunit[5]+"." +
                    "\nFourth: Magnitude "+Double.toString(var[6])+" "+Sunit[6]+" and angle "+Double.toString(var[7])+" "+Sunit[7]+".";
            }
            else if (option[0]==5)
            {
                piece1="We have "+Integer.toString(option[0])+" vectors that we wish to resolve.";
                piece2="\n\nFirst: Magnitude "+Double.toString(var[0])+" "+Sunit[0]+" and angle "+Double.toString(var[1])+" "+Sunit[1]+"." +
                    "\nSecond: Magnitude "+Double.toString(var[2])+" "+Sunit[2]+" and angle "+Double.toString(var[3])+" "+Sunit[3]+"." +
                    "\nThird: Magnitude "+Double.toString(var[4])+" "+Sunit[4]+" and angle "+Double.toString(var[5])+" "+Sunit[5]+"." +
                    "\nFourth: Magnitude "+Double.toString(var[6])+" "+Sunit[6]+" and angle "+Double.toString(var[7])+" "+Sunit[7]+"." +
                    "\nFifth: Magnitude "+Double.toString(var[8])+" "+Sunit[8]+" and angle "+Double.toString(var[9])+" "+Sunit[9]+".";
            }
            else if (option[0]==6)
            {
                piece1="We have "+Integer.toString(option[0])+" vectors that we wish to resolve.";
                piece2="\n\nFirst: Magnitude "+Double.toString(var[0])+" "+Sunit[0]+" and angle "+Double.toString(var[1])+" "+Sunit[1]+"." +
                    "\nSecond: Magnitude "+Double.toString(var[2])+" "+Sunit[2]+" and angle "+Double.toString(var[3])+" "+Sunit[3]+"." +
                    "\nThird: Magnitude "+Double.toString(var[4])+" "+Sunit[4]+" and angle "+Double.toString(var[5])+" "+Sunit[5]+"." +
                    "\nFourth: Magnitude "+Double.toString(var[6])+" "+Sunit[6]+" and angle "+Double.toString(var[7])+" "+Sunit[7]+"." +
                    "\nFifth: Magnitude "+Double.toString(var[8])+" "+Sunit[8]+" and angle "+Double.toString(var[9])+" "+Sunit[9]+"." +
                    "\nSixth: Magnitude "+Double.toString(var[10])+" "+Sunit[10]+" and angle "+Double.toString(var[11])+" "+Sunit[11]+".";
            }
        }
        else if ("INCLINE_SIMPLE".equals(type))
        {
            piece1="An object is on an incline. It either sits still or is sliding downward (not upward).";
            if (option[0]==1)
            {
                piece2="\nThe angle of the incline ="+Double.toString(var[0])+" "+Sunit[0]+", and the mass of the object ="+Double.toString(var[1])+" "+Sunit[1]+".";
            }
            else if (option[0]==2)
            {
                piece2="\nThe angle of the incline ="+Double.toString(var[0])+" "+Sunit[0]+", and the force parallel to the incline ="+Double.toString(var[1])+" "+Sunit[1]+".";
            }
            else if (option[0]==3)
            {
                piece2="\nThe angle of the incline ="+Double.toString(var[0])+" "+Sunit[0]+", and the force normal to the incline ="+Double.toString(var[1])+" "+Sunit[1]+".";
            }
            else if (option[0]==4)
            {
                piece2="\nThe mass of the object ="+Double.toString(var[0])+" "+Sunit[0]+", and the force parallel to the incline ="+Double.toString(var[1])+" "+Sunit[1]+".";
            }
            else if (option[0]==5)
            {
                piece2="\nThe mass of the object ="+Double.toString(var[0])+" kg, and the force normal to the incline ="+Double.toString(var[1])+" N.";
            }
            else if (option[0]==6)
            {
                piece2="\nThe force parallel to the incline ="+Double.toString(var[0])+" "+Sunit[0]+", and the force normal to the incline ="+Double.toString(var[1])+" "+Sunit[1]+".";
            }
            if (option[1]==1)
            {
                piece3="\nThe static coefficient of friction is "+Double.toString(var[2])+".";
            }
            else if (option[1]==2)
            {
                piece3="\nA force of "+Double.toString(var[2])+" "+Sunit[2]+" is required to start moving the object if it sits still.";
            }
            if (option[2]==1)
            {
                piece4="\nThe kinetic coefficient of friction is "+Double.toString(var[3])+".";
            }
            else if (option[2]==2)
            {
                piece4="\nA force of "+Double.toString(var[3])+" "+Sunit[3]+" is required to keep moving the object if it is already moving.";
            }
        }
        else if ("INCLINE".equals(type))
        {
            piece1="An object is on an incline.\nThe kinetic coefficient of friction is "+Double.toString(var[0])+".";
            if (option[1]==1)
            {
                piece2="\nThe angle of the incline ="+Double.toString(var[3])+" "+Sunit[3]+", and at t="+Double.toString(var[2])+" "+Sunit[2]+", the position along the incline ="+Double.toString(var[1])+" "+Sunit[1]+".";
            }
            else if (option[1]==2)
            {
                piece2="\nThe angle of the incline ="+Double.toString(var[3])+" "+Sunit[3]+", and at t="+Double.toString(var[2])+" "+Sunit[2]+", x="+Double.toString(var[1])+" "+Sunit[1]+".";
            }
            else if (option[1]==3)
            {
                piece2="\nThe angle of the incline ="+Double.toString(var[3])+" "+Sunit[3]+", and at t="+Double.toString(var[2])+" "+Sunit[2]+", y="+Double.toString(var[1])+" "+Sunit[1]+".";
            }
            else if (option[1]==4)
            {
                piece2="\nAt t="+Double.toString(var[3])+" "+Sunit[3]+", x="+Double.toString(var[1])+" "+Sunit[1]+", and y="+Double.toString(var[2])+" "+Sunit[2]+".";
            }
            piece3="\nAt t="+Double.toString(var[5])+" "+Sunit[5]+", the velocity along the incline ="+Double.toString(var[4])+" "+Sunit[4]+".";
        }
        else if ("SPRING_SIMPLE".equals(type))
        {
            piece1="A mass on a frictionless table is attached to an ideal spring.";
            if (option[0]==1)
            {
                piece2="\nThe spring constant k="+Double.toString(var[0])+" "+Sunit[0]+".";
                piece3="\nThe equilibrium position of the mass ="+Double.toString(var[1])+" "+Sunit[1]+".";
            }
            else if (option[0]==2)
            {
                piece2="\nThe mass sits at x="+Double.toString(var[0])+" "+Sunit[0]+", and experiences a force with magnitude ="+Double.toString(var[1])+" "+Sunit[1]+".";
                piece3="\nThe equilibrium position of the mass ="+Double.toString(var[2])+" "+Sunit[2]+".";
            }
        }
        else if ("SPRING".equals(type))
        {
            piece1="A mass on a frictionless table is attached to an ideal spring.";
            if (option[0]==1)
            {
                piece2="\nThe mass m="+Double.toString(var[0])+" "+Sunit[0]+", and the spring constant k="+Double.toString(var[1])+" "+Sunit[1]+".";
            }
            else if (option[0]==2)
            {
                piece2="\nThe mass m="+Double.toString(var[0])+" "+Sunit[0]+", and the angular frequency of oscillation ="+Double.toString(var[1])+" "+Sunit[1]+".";
            }
            else if (option[0]==3)
            {
                piece2="\nThe spring constant k="+Double.toString(var[0])+" "+Sunit[0]+", and the angular frequency of oscillation ="+Double.toString(var[1])+" "+Sunit[1]+".";
            }
            if (option[2]==1)
            {
                piece3="\nThe mass has initial displacement x="+Double.toString(var[2])+" "+Sunit[2]+" and initial velocity v="+Double.toString(var[3])+" "+Sunit[3]+".";
            }
            else if (option[2]==2)
            {
                piece3="\nThe amplitude of oscillation is A="+Double.toString(var[2])+" "+Sunit[2]+", and the time offset ="+Double.toString(var[3])+" "+Sunit[3]+".";
            }
            else if (option[2]==3)
            {
                piece3="\nThe amplitude of oscillation is A="+Double.toString(var[2])+" "+Sunit[2]+", and the phase offset ="+Double.toString(var[3])+" "+Sunit[3]+".";
            }
            else if (option[2]==4)
            {
                piece3="\nThe total energy of the system is E="+Double.toString(var[2])+" "+Sunit[2]+", and the time offset ="+Double.toString(var[3])+" "+Sunit[3]+".";
            }
            else if (option[2]==5)
            {
                piece3="\nThe total energy of the system is E="+Double.toString(var[2])+" "+Sunit[2]+", and the phase offset ="+Double.toString(var[3])+" "+Sunit[3]+".";
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
        return piece1+piece2+piece3+piece4;
    }

    CustomSpinner makeSpinner(final int i, final String unit, final double[] factor, final String[] SunitChoice) {
        final String unitText[][]={{"m","cm","mm","km","in","ft"},{"s","ms"},{"m/s","cm/s","mm/s","km/s","in/s","ft/s"},{"radians","degrees"},{"kg","g"},{"N","lb"},{"J"},{"Hz"},{"m/s^2"},{"N/m"}};
        final double unitFactor[][]={{1,0.01,0.001,1000,0.0254,0.3048},{1,0.001},{1,0.01,0.001,1000,0.0254,0.3048},{1,0.01745329},{1,0.001},{1,4.44822162},{1},{1},{1},{1}};
        final int unitDefault[]={defaultLength,defaultTime,defaultVelocity,defaultAngle,defaultMass,defaultForce,defaultEnergy,defaultFrequency,defaultAcceleration,defaultSpringConstant};

        final CustomSpinner S=new CustomSpinner(this,unitText[unitStoN(unit)],"Unit ("+unit+"):");
        S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> parent) {}
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                factor[i]=unitFactor[unitStoN(unit)][pos];
                SunitChoice[i]=unitText[unitStoN(unit)][pos];
            }
        });
        S.setSelection(unitDefault[unitStoN(unit)]);

        return S;
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
        return -1;
    }
}