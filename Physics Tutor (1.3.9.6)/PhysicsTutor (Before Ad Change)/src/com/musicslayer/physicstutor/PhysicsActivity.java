package com.musicslayer.physicstutor;

import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.localytics.android.*;
import java.util.Date;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
//TODO email with only relevant inputs
public class PhysicsActivity extends Activity {
	final public static boolean test=true;
    final public static boolean alwaysShowAds=false;
    final public static int adFreeTimeSec=604800;//1 week
    final public static String CRITTERCISM="512a5eaf4f633a0cc0001255";//test
    //final public static String CRITTERCISM="512a91ccf716963ade0011d4";//real
    final public static String LOCALYTICS="0cf7118ce64f63d3f7fd5fe-83b51a88-93d1-11e0-ffa7-007f58cb3154";//test
    //final public static String LOCALYTICS = "f37443489ebdbb7d1b29908-2ddd9af0-a398-11e0-01b7-007f58cb3154";//real
	private LocalyticsSession localyticsSession;

    static Date adTimer=new Date();
    static String type="?";

    static int surveyChoice[]={0,0,0,0}, option[]={1,1,1,1}, selection_g=0, answerChoice=0, defaultLength=0, defaultTime=0, defaultVelocity=0, defaultAngle=0, defaultMass=0, defaultForce=0, defaultEnergy=0, defaultFrequency=0, defaultAcceleration=0;
    static double u1=0, u2=0, u3=0, u4=0, u5=0, u6=0, u7=0, u8=0, u9=0, u10=0, u11=0, u12=0, var[]={0,0,0,0,0,0,0,0,0,0,0,0}, constant_g=9.8;
    static float scale=1;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.localyticsSession = new LocalyticsSession(this.getApplicationContext(),LOCALYTICS);
		this.localyticsSession.open();
		this.localyticsSession.upload();
		loadPrefs();

        if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE)
        {
            scale=1.67f;
        }
        else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4)
        {
            scale=1.67f;
        }
    }

    public void savePrefs() {
    	SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putFloat("scale", scale);
        editor.putLong("timer", adTimer.getTime());
        editor.putString("type", type);

        editor.putInt("sc1", surveyChoice[0]);
        editor.putInt("sc2", surveyChoice[1]);
        editor.putInt("sc3", surveyChoice[2]);
        editor.putInt("sc4", surveyChoice[3]);

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
        editor.putInt("un1", defaultLength);
        editor.putInt("un2", defaultTime);
        editor.putInt("un3", defaultVelocity);
        editor.putInt("un4", defaultAngle);
        editor.putInt("un5", defaultMass);
        editor.putInt("un6", defaultForce);
        editor.putInt("un7", defaultEnergy);
        editor.putInt("un8", defaultFrequency);
        editor.putInt("un9", defaultAcceleration);
        editor.commit();
    }

    public void loadPrefs() {
    	SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);
        if (!"PROJECTILE".equals(type) && !"VECTORS".equals(type) && !"INCLINE_SIMPLE".equals(type) && !"INCLINE".equals(type) && !"SPRING_SIMPLE".equals(type) && !"SPRING".equals(type)&& !"CHARGE".equals(type))
        {
            scale=preferences.getFloat("scale", scale);
            adTimer.setTime(preferences.getLong("timer", adTimer.getTime()));
		    type=preferences.getString("type", "?");

            surveyChoice[0]=preferences.getInt("sc1", surveyChoice[0]);
            surveyChoice[1]=preferences.getInt("sc2", surveyChoice[1]);
            surveyChoice[2]=preferences.getInt("sc3", surveyChoice[2]);
            surveyChoice[3]=preferences.getInt("sc4", surveyChoice[3]);

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
		    defaultLength=preferences.getInt("un1", defaultLength);
		    defaultTime=preferences.getInt("un2", defaultTime);
		    defaultVelocity=preferences.getInt("un3", defaultVelocity);
		    defaultAngle=preferences.getInt("un4", defaultAngle);
		    defaultMass=preferences.getInt("un5", defaultMass);
		    defaultForce=preferences.getInt("un6", defaultForce);
            defaultForce=preferences.getInt("un7", defaultEnergy);
            defaultForce=preferences.getInt("un8", defaultFrequency);
            defaultAcceleration=preferences.getInt("un9", defaultAcceleration);
        }
    }

    public int dpToPx(int dp)
    {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }
}

class CustomAdapter<T> extends ArrayAdapter<T> {
    public CustomAdapter(Context context, int textViewResourceId, T[] objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        if (view instanceof TextView) {
            ((TextView) view).setTextSize(1,14*PhysicsActivity.scale);
        }
        return view;
    }
}