package com.musicslayer.physicstutor;

import android.content.Intent;
import android.graphics.PorterDuff;
import com.localytics.android.*;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PhysicsProblemsActivity extends PhysicsActivity {
	private LocalyticsSession localyticsSession;
    private Button projectile, vectors, inclineSimple, incline, springSimple, spring;
	
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
        vectors = (Button) findViewById(R.id.Button_Vectors);
        inclineSimple = (Button) findViewById(R.id.Button_Incline_Simple);
        incline = (Button) findViewById(R.id.Button_Incline);
        springSimple = (Button) findViewById(R.id.Button_Spring_Simple);
        spring = (Button) findViewById(R.id.Button_Spring);

        projectile.getBackground().setColorFilter(0xFFFF3366, PorterDuff.Mode.MULTIPLY);
        vectors.getBackground().setColorFilter(0xFFFF3366, PorterDuff.Mode.MULTIPLY);
        inclineSimple.getBackground().setColorFilter(0xFFFF3366, PorterDuff.Mode.MULTIPLY);
        incline.getBackground().setColorFilter(0xFFFF3366, PorterDuff.Mode.MULTIPLY);
        springSimple.getBackground().setColorFilter(0xFFFF3366, PorterDuff.Mode.MULTIPLY);
        spring.getBackground().setColorFilter(0xFFFF3366, PorterDuff.Mode.MULTIPLY);

        projectile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            type="PROJECTILE";
            startActivity(new Intent(PhysicsProblemsActivity.this, PhysicsInfoActivity.class));    
            }
        });

        vectors.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            type="VECTORS";
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