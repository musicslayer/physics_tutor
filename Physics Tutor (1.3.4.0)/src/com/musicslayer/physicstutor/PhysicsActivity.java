package com.musicslayer.physicstutor;

import com.Localytics.android.*;
import java.util.Date;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

public class PhysicsActivity extends Activity {
	public static final boolean test=true;
	private LocalyticsSession localyticsSession;
    static Date adTimer=new Date();
    public static final String GAME_PREFERENCES = "GamePrefs";
    public static String LOCALYTICS;
    static String type, instructions[];

    public static final int adFreeTimeSec=604800;//1 week
    static int stotal, selection_g, answerChoice;
    static int helperImage[], option1, option2, option3, option4, selection[][][], selectionA1[], selectionA2[];
    static int defaultLength=0, defaultTime=0, defaultVelocity=0, defaultAngle=0, defaultMass=0, defaultForce=0;
    static double constant_g=9.8, u1, u2, u3, u4, u5, u6, u7, u8, u9, u10;
    static double factor[][][], factorA1[]={1.0,1.0,1.0,1.0}, factorA2[]={1.0,1.0,1.0,1.0};

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
    	super.onPause();
    	savePrefs();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (test)
        {
            LOCALYTICS = "0cf7118ce64f63d3f7fd5fe-83b51a88-93d1-11e0-ffa7-007f58cb3154";
        }
        else
        {
            LOCALYTICS = "f37443489ebdbb7d1b29908-2ddd9af0-a398-11e0-01b7-007f58cb3154";
        }
        this.localyticsSession = new LocalyticsSession(this.getApplicationContext(),LOCALYTICS);
		this.localyticsSession.open();
		this.localyticsSession.upload();
		loadPrefs();
    }

    public void savePrefs() {
    	SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putLong("timer", adTimer.getTime());
        editor.putString("type", type);
        editor.putInt("op1", option1);
        editor.putInt("op2", option2);
        editor.putInt("op3", option3);
        editor.putInt("op4", option4);
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
        editor.putFloat("cv1", (float)constant_g);
        editor.putInt("cs1", selection_g);
        editor.putInt("s1", answerChoice);
        editor.putInt("un1", defaultLength);
        editor.putInt("un2", defaultTime);
        editor.putInt("un3", defaultVelocity);
        editor.putInt("un4", defaultAngle);
        editor.putInt("un5", defaultMass);
        editor.putInt("un6", defaultForce);
        editor.commit();
    }

    public void loadPrefs() {
    	SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);
    	
        if (!"PROJECTILE".equals(type) && !"INCLINE".equals(type) && !"INCLINE_SIMPLE".equals(type))
        {
		    adTimer.setTime(preferences.getLong("timer", adTimer.getTime()-adFreeTimeSec*1000));
		    type=preferences.getString("type", "?");
		    option1=preferences.getInt("op1", option1);
		    option2=preferences.getInt("op2", option2);
		    option3=preferences.getInt("op3", option3);
		    option4=preferences.getInt("op4", option4);
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
            constant_g=preferences.getFloat("cv1", (float)constant_g);
		    selection_g=preferences.getInt("cs1", selection_g);
            answerChoice=preferences.getInt("s1", answerChoice);
		    defaultLength=preferences.getInt("un1", defaultLength);
		    defaultTime=preferences.getInt("un2", defaultTime);
		    defaultVelocity=preferences.getInt("un3", defaultVelocity);
		    defaultAngle=preferences.getInt("un4", defaultAngle);
		    defaultMass=preferences.getInt("un5", defaultMass);
		    defaultForce=preferences.getInt("un6", defaultForce);
        }
    }
}