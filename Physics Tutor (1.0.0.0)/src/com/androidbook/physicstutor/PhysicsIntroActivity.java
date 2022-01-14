package com.androidbook.physicstutor;

import java.util.Date;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class PhysicsIntroActivity extends PhysicsActivity {

	Toast toast;
	TextView adFreeTimer;
	Date nag1, nag2;
	
	int i;
	long sec, min, secSinceAd;
	
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
	public void onBackPressed() {
		toast.show();
	return;
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro); 
        
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

        toast=Toast.makeText(getApplicationContext(), "Press HOME to exit this application.", 0);
        Button problems = (Button) findViewById(R.id.Button_Problems);
        adFreeTimer=(TextView) findViewById(R.id.AdFreeTimer);
        
        nag1=new Date();
        secSinceAd=(nag1.getTime()-adTimer.getTime())/1000;
        sec=(adFreeTimeSec-secSinceAd)%60;
        min=(adFreeTimeSec-secSinceAd)/60;
        if (adFreeTimeSec-secSinceAd>0)
        {
        adFreeTimer.setText("Ad free time (As of this activity loading):\n"+Long.toString(min)+" minutes and "+Long.toString(sec)+" seconds.");
        }
        else
        {
        adFreeTimer.setText("Ad free time (As of this activity loading):\nNone.");
        }
        problems.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startActivity(new Intent(PhysicsIntroActivity.this, PhysicsProblemsActivity.class));    
            }
        });
            
        Button assumptions = (Button) findViewById(R.id.Button_Assumptions);
        assumptions.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startActivity(new Intent(PhysicsIntroActivity.this, PhysicsAssumptionsActivity.class));    
            }
        });
        
        Button settings = (Button) findViewById(R.id.Button_Settings);
        settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startActivity(new Intent(PhysicsIntroActivity.this, PhysicsSettingsActivity.class));    
            }
        });
        
        Button help = (Button) findViewById(R.id.Button_Help);
        help.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startActivity(new Intent(PhysicsIntroActivity.this, PhysicsHelpActivity.class));    
            }
        });
        
        
        
        
        
        
        
        
        
        
    }
}