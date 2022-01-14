package com.musicslayer.physicstutor;

import android.content.Intent;
import com.localytics.android.*;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PhysicsProblemsActivity extends PhysicsActivity {
	private LocalyticsSession localyticsSession;
    private Button projectile, incline, inclineSimple, springSimple, spring;
	
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
	public void onBackPressed() {
        startActivity(new Intent(PhysicsProblemsActivity.this, PhysicsIntroActivity.class));
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
        
        projectile = (Button) findViewById(R.id.Button_Projectile);
        incline = (Button) findViewById(R.id.Button_Incline);
        inclineSimple = (Button) findViewById(R.id.Button_Incline_Simple);
        springSimple = (Button) findViewById(R.id.Button_Spring_Simple);
        spring = (Button) findViewById(R.id.Button_Spring);

        projectile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            type="PROJECTILE";
            startActivity(new Intent(PhysicsProblemsActivity.this, PhysicsInfoActivity.class));    
            }
        });

        inclineSimple.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            type="INCLINE_SIMPLE";
            startActivity(new Intent(PhysicsProblemsActivity.this, PhysicsInfoActivity.class));
            }
        });

        incline.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            type="INCLINE";
            startActivity(new Intent(PhysicsProblemsActivity.this, PhysicsInfoActivity.class));    
            }
        });

        springSimple.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            type="SPRING_SIMPLE";
            startActivity(new Intent(PhysicsProblemsActivity.this, PhysicsInfoActivity.class));
            }
        });

        spring.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            type="SPRING";
            startActivity(new Intent(PhysicsProblemsActivity.this, PhysicsInfoActivity.class));
            }
        });
    }
}