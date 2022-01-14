package com.musicslayer.physicstutor;

import com.Localytics.android.*;
import java.util.Date;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

public class PhysicsActivity extends Activity {
	public static final boolean test=true;
	private LocalyticsSession localyticsSession;
    public static final String GAME_PREFERENCES = "GamePrefs";
    public static String LOCALYTICS;
    //debug
    
    public static final int adFreeTimeSec=604800;//1 week
    //public static final int adFreeTimeSec=1200;//20 min
    //public static final int adFreeTimeSec=60; //1min
    static String type;
    static double constant_g=9.8;
    static int selection_g, /*adChoice,*/ answerChoice /*bypasses=0*/;
    static Date adTimer=new Date();
    static double u1, u2, u3, u4, u5, u6, u7, u8, u9, u10;
    static int stotal;
    static String instructions[];
    static int helperImage[], option1, option2, option3, option4;
    static int selection[][][], selectionA1[], selectionA2[];
    //static double factor[][][]={{{1.0,1.0,1.0,1.0},{1.0,1.0,1.0,1.0},{1.0,1.0,1.0,1.0},{1.0,1.0,1.0,1.0}},{{1.0,1.0,1.0,1.0},{1.0,1.0,1.0,1.0},{1.0,1.0,1.0,1.0},{1.0,1.0,1.0,1.0}},{{1.0,1.0,1.0,1.0},{1.0,1.0,1.0,1.0},{1.0,1.0,1.0,1.0},{1.0,1.0,1.0,1.0}},{{1.0,1.0,1.0,1.0},{1.0,1.0,1.0,1.0},{1.0,1.0,1.0,1.0},{1.0,1.0,1.0,1.0}}};
    static double factor[][][];
    static double factorA1[]={1.0,1.0,1.0,1.0};
    static double factorA2[]={1.0,1.0,1.0,1.0};
    static int defaultLength=0, defaultTime=0, defaultVelocity=0, defaultAngle=0, defaultMass=0, defaultForce=0;
    
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
        if (test==true)
        {
        LOCALYTICS = "0cf7118ce64f63d3f7fd5fe-83b51a88-93d1-11e0-ffa7-007f58cb3154";
        }
        else if (test==false)
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
    
        editor.putInt("un1", defaultLength);
        editor.putInt("un2", defaultTime);
        editor.putInt("un3", defaultVelocity);
        editor.putInt("un4", defaultAngle);
        editor.putInt("un5", defaultMass);
        editor.putInt("un6", defaultForce);
        editor.putLong("t1", adTimer.getTime());
        //editor.putInt("t2", adChoice);
        editor.putInt("t3", answerChoice);
        editor.putString("v1", type);
        editor.putFloat("v2", (float)constant_g);
        editor.putInt("v3", selection_g);
        //editor.putInt("v4", bypasses);
        editor.putInt("v5", option1);
        editor.putInt("v6", option2);
        editor.putInt("v6.1", option3);
        editor.putInt("v6.2", option4);
        editor.putFloat("v7", (float)u1);
        editor.putFloat("v8", (float)u2);
        editor.putFloat("v9", (float)u3);
        editor.putFloat("v10", (float)u4);
        editor.putFloat("v11", (float)u5);
        editor.putFloat("v12", (float)u6);
        editor.putFloat("v13", (float)u7);
        editor.putFloat("v14", (float)u8);
        editor.putFloat("v15", (float)u9);
        editor.putFloat("v16", (float)u10);
        editor.commit();
    }
    public void loadPrefs() {
    	SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);
    	
        if ("PROJECTILE".equals(type)==false&&"INCLINE".equals(type)==false&&"INCLINE_SIMPLE".equals(type)==false)
        {
		    adTimer.setTime(preferences.getLong("t1", adTimer.getTime()-adFreeTimeSec*1000));
		    //adChoice=preferences.getInt("t2", adChoice);
		    answerChoice=preferences.getInt("t3", answerChoice);
		    type=preferences.getString("v1", "?");
		    constant_g=preferences.getFloat("v2", (float)constant_g);
		    selection_g=preferences.getInt("v3", selection_g);
		    //bypasses=preferences.getInt("v4", bypasses);
		    option1=preferences.getInt("v5", option1);
		    option2=preferences.getInt("v6", option2);
		    option3=preferences.getInt("v6.1", option3);
		    option4=preferences.getInt("v6.2", option4);
		    u1=preferences.getFloat("v7", (float)u1);
		    u2=preferences.getFloat("v8", (float)u2);
		    u3=preferences.getFloat("v9", (float)u3);
		    u4=preferences.getFloat("v10", (float)u4);
		    u5=preferences.getFloat("v11", (float)u5);
		    u6=preferences.getFloat("v12", (float)u6);
		    u7=preferences.getFloat("v13", (float)u7);
		    u8=preferences.getFloat("v14", (float)u8);
		    u9=preferences.getFloat("v15", (float)u9);
		    u10=preferences.getFloat("v16", (float)u10);
		    defaultLength=preferences.getInt("un1", defaultLength);
		    defaultTime=preferences.getInt("un2", defaultTime);
		    defaultVelocity=preferences.getInt("un3", defaultVelocity);
		    defaultAngle=preferences.getInt("un4", defaultAngle);
		    defaultMass=preferences.getInt("un5", defaultMass);
		    defaultForce=preferences.getInt("un6", defaultForce);
        }
    }
}