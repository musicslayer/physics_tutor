package com.androidbook.physicstutor;

import java.util.Date;

//import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//import android.widget.EditText;
import android.widget.TextView;

//import com.admob.android.ads.AdManager;
import com.admob.android.ads.AdManager;
import com.admob.android.ads.AdView;
public class PhysicsAdActivity extends PhysicsActivity {
	Date nag1, nag2;
	TextView diff;
	AdView adr;
	Button date;
	Long timer;
	
	int i, secOfNag;
	
	@Override
    public void onPause() 
    {
      super.onPause();
      SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);
      SharedPreferences.Editor editor = preferences.edit();
  
      editor.putLong("t1", adTimer.getTime());
      editor.putFloat("f1", (float)factor2_1_2);
      editor.putFloat("f2", (float)factor2_2_2);
      editor.putFloat("f3", (float)factor2_3_3);
      editor.putString("v1", type);
      editor.putFloat("v2", (float)constant_g);
      editor.putInt("v3", selection_g);
      editor.putInt("v4", stotal);
      editor.putInt("v5", option1);
      editor.putInt("v6", option2);
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
      for (i=1;i<=stotal;i++)
      {
    	  editor.putString("i"+Integer.toString(i), instructions[i]);
    	  editor.putInt("h"+Integer.toString(i), helperImage[i]);
      }
      editor.commit();
    }
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ;
    }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(null);
		setContentView(R.layout.ad);
		SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);
	       
        if ("PROJECTILE".equals(type)==false)
        {
		    instructions = new String[21];
		    helperImage = new int[21];
		    adTimer.setTime(preferences.getLong("t1", adTimer.getTime()));
		    factor2_1_2=preferences.getFloat("f1", (float)factor2_1_2);
		    factor2_2_2=preferences.getFloat("f2", (float)factor2_2_2);
		    factor2_3_3=preferences.getFloat("f3", (float)factor2_3_3);
		    type=preferences.getString("v1", "?");
		    constant_g=preferences.getFloat("v2", (float)constant_g);
		    selection_g=preferences.getInt("v3", selection_g);
		    stotal=preferences.getInt("v4", stotal);
		    option1=preferences.getInt("v5", option1);
		    option2=preferences.getInt("v6", option2);
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
		    for (i=1;i<=stotal;i++)
		    {
		    	instructions[i]=preferences.getString("i"+Integer.toString(i), instructions[i]);
		    	helperImage[i]=preferences.getInt("h"+Integer.toString(i), helperImage[i]);
		    }
        }
		
		try
		{
		if ((new Date().getTime()-PhysicsActivity.adTimer.getTime())/1000<adFreeTimeSec)
		{startActivity(new Intent(PhysicsAdActivity.this, PhysicsSolverActivity.class));}
		}
		catch(RuntimeException e)
		{
			;
		}
		
		
		AdManager.setTestDevices(new String[]{  
			AdManager.TEST_EMULATOR,
	    	"242B06F88823F5C9643AA6E6F27CFE8A", 
	    	});
		
		nag1=new Date();
		diff=(TextView)findViewById(R.id.diff);
		adr = (AdView)findViewById(R.id.adr);
		date=(Button)findViewById(R.id.Button_date);
		secOfNag=40;
		
    	          
       
    adr.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
        	PhysicsActivity.adTimer=new Date();
        	startActivity(new Intent(PhysicsAdActivity.this, PhysicsSolverActivity.class)); 
        }    	
        }
    );
    
    date.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
        	nag2=new Date();
        	timer=(nag2.getTime()-nag1.getTime())/1000;  	
        	diff.setText((secOfNag-timer<0?Integer.toString(0):Long.toString(secOfNag-timer))+" seconds remaining.");
        	if (timer>=secOfNag)
        	{
        		startActivity(new Intent(PhysicsAdActivity.this, PhysicsSolverActivity.class));
        	}
        }
        }
    );
    
    
    
    }
}