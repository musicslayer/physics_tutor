package com.musicslayer.physicstutor;

import com.Localytics.android.*;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class PhysicsSettingsActivity extends PhysicsActivity {
	private LocalyticsSession localyticsSession;
	Spinner s1, u1, u2, u3, u4, u5, u6;
	TextView adText, answerText;
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
		startActivity(new Intent(PhysicsSettingsActivity.this, PhysicsIntroActivity.class));
	return;
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        setTitle("Physics Tutor - Settings");
        this.localyticsSession = new LocalyticsSession(this.getApplicationContext(),LOCALYTICS);
		this.localyticsSession.open();
		this.localyticsSession.upload();
        loadPrefs();
        
        //adText = (TextView) findViewById(R.id.TextView_AdText);
        answerText = (TextView) findViewById(R.id.TextView_AnswerText);
        
        s1 = (Spinner) findViewById(R.id.Spinner_AnswerLayout);
        ArrayAdapter<?> adapter1 = ArrayAdapter.createFromResource(this, R.array.answer, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(adapter1);
        s1.setSelection(PhysicsActivity.answerChoice);
        
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            	switch (pos)
            	{
            	case 0: answerText.setText("Answer Layout A: The explanation will be on a separate screen than the answer fields.");break;
            	case 1: answerText.setText("Answer Layout B: The explanation will be on the same screen as the answer fields."); break;
            	}
            	answerChoice=pos;
            }
            public void onNothingSelected(AdapterView<?> parent) {
            	//constant_g=9.8;
            }
        });
        
        u1 = (Spinner) findViewById(R.id.Spinner_Default_Length);
        ArrayAdapter<?> adapteru1 = ArrayAdapter.createFromResource(this, R.array.unit_length, android.R.layout.simple_spinner_item);
        adapteru1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        u1.setAdapter(adapteru1);
        u1.setSelection(PhysicsActivity.defaultLength);
        
        u1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            	defaultLength=pos;
            }

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			;
			}
        });
        
        u2 = (Spinner) findViewById(R.id.Spinner_Default_Time);
        ArrayAdapter<?> adapteru2 = ArrayAdapter.createFromResource(this, R.array.unit_time, android.R.layout.simple_spinner_item);
        adapteru2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        u2.setAdapter(adapteru2);
        u2.setSelection(PhysicsActivity.defaultTime);
        
        u2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            	defaultTime=pos;
            }

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			;
			}
        });
        
        u3 = (Spinner) findViewById(R.id.Spinner_Default_Velocity);
        ArrayAdapter<?> adapteru3 = ArrayAdapter.createFromResource(this, R.array.unit_velocity, android.R.layout.simple_spinner_item);
        adapteru3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        u3.setAdapter(adapteru3);
        u3.setSelection(PhysicsActivity.defaultVelocity);
        
        u3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            	defaultVelocity=pos;
            }

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			;
			}
        });
        
        u4 = (Spinner) findViewById(R.id.Spinner_Default_Angle);
        ArrayAdapter<?> adapteru4 = ArrayAdapter.createFromResource(this, R.array.unit_angle, android.R.layout.simple_spinner_item);
        adapteru4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        u4.setAdapter(adapteru4);
        u4.setSelection(PhysicsActivity.defaultAngle);
        
        u4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            	defaultAngle=pos;
            }

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			;
			}
        });
        
        u5 = (Spinner) findViewById(R.id.Spinner_Default_Mass);
        ArrayAdapter<?> adapteru5 = ArrayAdapter.createFromResource(this, R.array.unit_mass, android.R.layout.simple_spinner_item);
        adapteru5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        u5.setAdapter(adapteru5);
        u5.setSelection(PhysicsActivity.defaultMass);
        
        u5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            	defaultMass=pos;
            }

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			;
			}
        });
        
        u6 = (Spinner) findViewById(R.id.Spinner_Default_Force);
        ArrayAdapter<?> adapteru6 = ArrayAdapter.createFromResource(this, R.array.unit_force, android.R.layout.simple_spinner_item);
        adapteru6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        u6.setAdapter(adapteru6);
        u6.setSelection(PhysicsActivity.defaultForce);
        
        u6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            	defaultForce=pos;
            }

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			;
			}
        });
    }
}