package com.musicslayer.physicstutor;

import android.widget.*;
import com.localytics.android.*;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

public class PhysicsSettingsActivity extends PhysicsActivity {
	private LocalyticsSession localyticsSession;
	private Spinner s1, u1, u2, u3, u4, u5, u6, u7, u8, u9;
	private TextView answerText;
	
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
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
        	((LinearLayout) findViewById(R.id.LinearLayout01)).setOrientation(0);
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
        	((LinearLayout) findViewById(R.id.LinearLayout01)).setOrientation(1);
        }
    }

	@Override
	public void onBackPressed() {
		startActivity(new Intent(PhysicsSettingsActivity.this, PhysicsIntroActivity.class));
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

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
        	((LinearLayout) findViewById(R.id.LinearLayout01)).setOrientation(0);
        }
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
        	((LinearLayout) findViewById(R.id.LinearLayout01)).setOrientation(1);
        }

        answerText = (TextView) findViewById(R.id.TextView_AnswerText);
        
        s1 = (Spinner) findViewById(R.id.Spinner_AnswerLayout);
        ArrayAdapter<?> adapter1 = ArrayAdapter.createFromResource(this, R.array.answer, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(adapter1);
        s1.setSelection(answerChoice);
        
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            	switch (pos)
            	{
            	    case 0:
                        answerText.setText("Answer Layout A: The explanation will be on a separate screen than the answer fields.");
                        break;
            	    case 1:
                        answerText.setText("Answer Layout B: The explanation will be on the same screen as the answer fields.");
                        break;
            	}
            	answerChoice=pos;
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        
        u1 = (Spinner) findViewById(R.id.Spinner_Default_Length);
        ArrayAdapter<?> adapteru1 = ArrayAdapter.createFromResource(this, R.array.unit_length, android.R.layout.simple_spinner_item);
        adapteru1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        u1.setAdapter(adapteru1);
        u1.setSelection(defaultLength);
        
        u1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            	defaultLength=pos;
            }
			public void onNothingSelected(AdapterView<?> arg0) {
			}
        });
        
        u2 = (Spinner) findViewById(R.id.Spinner_Default_Time);
        ArrayAdapter<?> adapteru2 = ArrayAdapter.createFromResource(this, R.array.unit_time, android.R.layout.simple_spinner_item);
        adapteru2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        u2.setAdapter(adapteru2);
        u2.setSelection(defaultTime);
        
        u2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            	defaultTime=pos;
            }
			public void onNothingSelected(AdapterView<?> arg0) {
			}
        });
        
        u3 = (Spinner) findViewById(R.id.Spinner_Default_Velocity);
        ArrayAdapter<?> adapteru3 = ArrayAdapter.createFromResource(this, R.array.unit_velocity, android.R.layout.simple_spinner_item);
        adapteru3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        u3.setAdapter(adapteru3);
        u3.setSelection(defaultVelocity);
        
        u3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            	defaultVelocity=pos;
            }
			public void onNothingSelected(AdapterView<?> arg0) {
			}
        });
        
        u4 = (Spinner) findViewById(R.id.Spinner_Default_Angle);
        ArrayAdapter<?> adapteru4 = ArrayAdapter.createFromResource(this, R.array.unit_angle, android.R.layout.simple_spinner_item);
        adapteru4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        u4.setAdapter(adapteru4);
        u4.setSelection(defaultAngle);
        
        u4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            	defaultAngle=pos;
            }
			public void onNothingSelected(AdapterView<?> arg0) {
			}
        });
        
        u5 = (Spinner) findViewById(R.id.Spinner_Default_Mass);
        ArrayAdapter<?> adapteru5 = ArrayAdapter.createFromResource(this, R.array.unit_mass, android.R.layout.simple_spinner_item);
        adapteru5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        u5.setAdapter(adapteru5);
        u5.setSelection(defaultMass);
        
        u5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            	defaultMass=pos;
            }
			public void onNothingSelected(AdapterView<?> arg0) {
			}
        });
        
        u6 = (Spinner) findViewById(R.id.Spinner_Default_Force);
        ArrayAdapter<?> adapteru6 = ArrayAdapter.createFromResource(this, R.array.unit_force, android.R.layout.simple_spinner_item);
        adapteru6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        u6.setAdapter(adapteru6);
        u6.setSelection(defaultForce);
        
        u6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            	defaultForce=pos;
            }
			public void onNothingSelected(AdapterView<?> arg0) {
			}
        });

        u7 = (Spinner) findViewById(R.id.Spinner_Default_Energy);
        ArrayAdapter<?> adapteru7 = ArrayAdapter.createFromResource(this, R.array.unit_energy, android.R.layout.simple_spinner_item);
        adapteru7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        u7.setAdapter(adapteru7);
        u7.setSelection(defaultEnergy);

        u7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            	defaultEnergy=pos;
            }
			public void onNothingSelected(AdapterView<?> arg0) {
			}
        });

        u8 = (Spinner) findViewById(R.id.Spinner_Default_Frequency);
        ArrayAdapter<?> adapteru8 = ArrayAdapter.createFromResource(this, R.array.unit_frequency, android.R.layout.simple_spinner_item);
        adapteru8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        u8.setAdapter(adapteru8);
        u8.setSelection(defaultFrequency);

        u8.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            	defaultFrequency=pos;
            }
			public void onNothingSelected(AdapterView<?> arg0) {
			}
        });

        u9 = (Spinner) findViewById(R.id.Spinner_Default_Acceleration);
        ArrayAdapter<?> adapteru9 = ArrayAdapter.createFromResource(this, R.array.unit_acceleration, android.R.layout.simple_spinner_item);
        adapteru9.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        u9.setAdapter(adapteru9);
        u9.setSelection(defaultAcceleration);

        u9.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            	defaultAcceleration=pos;
            }
			public void onNothingSelected(AdapterView<?> arg0) {
			}
        });
    }
}