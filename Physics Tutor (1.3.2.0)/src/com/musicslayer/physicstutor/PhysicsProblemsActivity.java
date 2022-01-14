package com.musicslayer.physicstutor;

import android.content.Intent;
import com.Localytics.android.*;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PhysicsProblemsActivity extends PhysicsActivity {
	private LocalyticsSession localyticsSession;
	int i;
	
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
	public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ;
    }
	
	@Override
	public void onBackPressed() {
		startActivity(new Intent(PhysicsProblemsActivity.this, PhysicsIntroActivity.class));
	return;
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.problems);
        setTitle("Physics Tutor - Problems");
        this.localyticsSession = new LocalyticsSession(this.getApplicationContext(),LOCALYTICS);
		this.localyticsSession.open();
		this.localyticsSession.upload();
        loadPrefs();
        
        Button projectile = (Button) findViewById(R.id.Button_Projectile);
        projectile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            type="PROJECTILE";
            startActivity(new Intent(PhysicsProblemsActivity.this, PhysicsInfoActivity.class));    
            }
        });
        
        Button incline = (Button) findViewById(R.id.Button_Incline);
        incline.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            type="INCLINE";
            startActivity(new Intent(PhysicsProblemsActivity.this, PhysicsInfoActivity.class));    
            }
        });
        
        Button inclineSimple = (Button) findViewById(R.id.Button_Incline_Simple);
        inclineSimple.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            type="INCLINE_SIMPLE";
            startActivity(new Intent(PhysicsProblemsActivity.this, PhysicsInfoActivity.class));    
            }
        });
        
        
    }
}