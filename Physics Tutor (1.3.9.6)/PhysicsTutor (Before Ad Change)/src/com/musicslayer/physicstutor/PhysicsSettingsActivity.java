package com.musicslayer.physicstutor;

import android.view.ViewGroup;
import android.widget.*;
import com.localytics.android.*;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

public class PhysicsSettingsActivity extends PhysicsActivity {
	private LocalyticsSession localyticsSession;
    private LinearLayout L, L1, LA, L_Units, L_1, L_2, L_3, L_4, L_5, L_6, L_7, L_8, L_9;
    private ScrollView S_Units;
	private Spinner s1, U_1, U_2, U_3, U_4, U_5, U_6, U_7, U_8, U_9;
	private TextView TA, T_1, T_2, T_3, T_4, T_5, T_6, T_7, T_8, T_9, answerText, defaultText;
	
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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
        	L.setOrientation(0);
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
        	L.setOrientation(1);
        }
    }

	@Override
	public void onBackPressed() {
		startActivity(new Intent(PhysicsSettingsActivity.this, PhysicsIntroActivity.class));
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.localyticsSession = new LocalyticsSession(this.getApplicationContext(),LOCALYTICS);
		this.localyticsSession.open();
		this.localyticsSession.upload();
        L=makeSettings();
        setContentView(L);
        setTitle("Physics Tutor - Settings");
        loadPrefs();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
        	L.setOrientation(0);
        }
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
        	L.setOrientation(1);
        }
    }

    LinearLayout makeSettings () {
        L1=new LinearLayout(this);
        L1.setOrientation(1);
        L1.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));

        LA=new LinearLayout(this);
        LA.setOrientation(1);
        LA.setLayoutParams(new ViewGroup.LayoutParams(dpToPx((int)(300*scale)), -2));

        TA=new TextView(this);
        TA.setTextSize(1,14*scale);
        TA.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        TA.setText("Answer Layout:");

        answerText=new TextView(this);
        answerText.setTextSize(1,14*scale);
        answerText.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        answerText.setPadding(0,0,0,dpToPx((int)(10*scale)));

        S_Units=new ScrollView(this);
        S_Units.setLayoutParams(new ViewGroup.LayoutParams(dpToPx((int)(200*scale)), -2));

        L_Units=new LinearLayout(this);
        L_Units.setOrientation(1);
        L_Units.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        defaultText=new TextView(this);
        defaultText.setTextSize(1,14*scale);
        defaultText.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        defaultText.setText("Default Units:");

        L_1=new LinearLayout(this);
        L_1.setOrientation(0);
        L_1.setLayoutParams(new ViewGroup.LayoutParams(dpToPx((int)(200*scale)), -2));

        T_1=new TextView(this);
        T_1.setTextSize(1,14*scale);
        T_1.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        T_1.setText("Length");

        L_2=new LinearLayout(this);
        L_2.setOrientation(0);
        L_2.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        T_2=new TextView(this);
        T_2.setTextSize(1,14*scale);
        T_2.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        T_2.setText("Time");

        L_3=new LinearLayout(this);
        L_3.setOrientation(0);
        L_3.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        T_3=new TextView(this);
        T_3.setTextSize(1,14*scale);
        T_3.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        T_3.setText("Velocity");

        L_4=new LinearLayout(this);
        L_4.setOrientation(0);
        L_4.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        T_4=new TextView(this);
        T_4.setTextSize(1,14*scale);
        T_4.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        T_4.setText("Angle");

        L_5=new LinearLayout(this);
        L_5.setOrientation(0);
        L_5.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        T_5=new TextView(this);
        T_5.setTextSize(1,14*scale);
        T_5.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        T_5.setText("Mass");

        L_6=new LinearLayout(this);
        L_6.setOrientation(0);
        L_6.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        T_6=new TextView(this);
        T_6.setTextSize(1,14*scale);
        T_6.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        T_6.setText("Force");

        L_7=new LinearLayout(this);
        L_7.setOrientation(0);
        L_7.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        T_7=new TextView(this);
        T_7.setTextSize(1,14*scale);
        T_7.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        T_7.setText("Energy");

        L_8=new LinearLayout(this);
        L_8.setOrientation(0);
        L_8.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        T_8=new TextView(this);
        T_8.setTextSize(1,14*scale);
        T_8.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        T_8.setText("Frequency");

        L_9=new LinearLayout(this);
        L_9.setOrientation(0);
        L_9.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        T_9=new TextView(this);
        T_9.setTextSize(1,14*scale);
        T_9.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        T_9.setText("Acceleration");

        fillSettingsSpinners();

        LA.addView(TA);
        LA.addView(s1);
        LA.addView(answerText);

        L_1.addView(T_1);
        L_1.addView(U_1);
        L_2.addView(T_2);
        L_2.addView(U_2);
        L_3.addView(T_3);
        L_3.addView(U_3);
        L_4.addView(T_4);
        L_4.addView(U_4);
        L_5.addView(T_5);
        L_5.addView(U_5);
        L_6.addView(T_6);
        L_6.addView(U_6);
        L_7.addView(T_7);
        L_7.addView(U_7);
        L_8.addView(T_8);
        L_8.addView(U_8);
        L_9.addView(T_9);
        L_9.addView(U_9);
        L_Units.addView(defaultText);
        L_Units.addView(L_1);
        L_Units.addView(L_2);
        L_Units.addView(L_3);
        L_Units.addView(L_4);
        L_Units.addView(L_5);
        L_Units.addView(L_6);
        L_Units.addView(L_7);
        L_Units.addView(L_8);
        L_Units.addView(L_9);
        S_Units.addView(L_Units);
        L1.addView(LA);
        L1.addView(S_Units);

        return L1;
    }

    private void fillSettingsSpinners()
    {
        s1=new Spinner(this);
        s1.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        s1.setPromptId(R.string.prompt_answer);
        //ArrayAdapter<?> adapter1 = ArrayAdapter.createFromResource(this, R.array.answer, android.R.layout.simple_spinner_item);
        CustomAdapter<CharSequence> adapter1 = new CustomAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, this.getResources().getTextArray(R.array.answer));
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

        U_1=new Spinner(this);
        U_1.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        U_1.setPromptId(R.string.prompt_length);
        //ArrayAdapter<?> adapteru1 = ArrayAdapter.createFromResource(this, R.array.unit_length, android.R.layout.simple_spinner_item);
        CustomAdapter<CharSequence> adapteru1 = new CustomAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, this.getResources().getTextArray(R.array.unit_length));
        adapteru1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        U_1.setAdapter(adapteru1);
        U_1.setSelection(defaultLength);

        U_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                defaultLength=pos;
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        U_2=new Spinner(this);
        U_2.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        U_2.setPromptId(R.string.prompt_time);
        //ArrayAdapter<?> adapteru2 = ArrayAdapter.createFromResource(this, R.array.unit_time, android.R.layout.simple_spinner_item);
        CustomAdapter<CharSequence> adapteru2 = new CustomAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, this.getResources().getTextArray(R.array.unit_time));
        adapteru2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        U_2.setAdapter(adapteru2);
        U_2.setSelection(defaultTime);

        U_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                defaultTime=pos;
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        U_3=new Spinner(this);
        U_3.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        U_3.setPromptId(R.string.prompt_velocity);
        //ArrayAdapter<?> adapteru3 = ArrayAdapter.createFromResource(this, R.array.unit_velocity, android.R.layout.simple_spinner_item);
        CustomAdapter<CharSequence> adapteru3 = new CustomAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, this.getResources().getTextArray(R.array.unit_velocity));
        adapteru3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        U_3.setAdapter(adapteru3);
        U_3.setSelection(defaultVelocity);

        U_3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                defaultVelocity=pos;
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        U_4=new Spinner(this);
        U_4.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        U_4.setPromptId(R.string.prompt_angle);
        //ArrayAdapter<?> adapteru4 = ArrayAdapter.createFromResource(this, R.array.unit_angle, android.R.layout.simple_spinner_item);
        CustomAdapter<CharSequence> adapteru4 = new CustomAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, this.getResources().getTextArray(R.array.unit_angle));
        adapteru4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        U_4.setAdapter(adapteru4);
        U_4.setSelection(defaultAngle);

        U_4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                defaultAngle=pos;
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        U_5=new Spinner(this);
        U_5.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        U_5.setPromptId(R.string.prompt_mass);
        //ArrayAdapter<?> adapteru5 = ArrayAdapter.createFromResource(this, R.array.unit_mass, android.R.layout.simple_spinner_item);
        CustomAdapter<CharSequence> adapteru5 = new CustomAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, this.getResources().getTextArray(R.array.unit_mass));
        adapteru5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        U_5.setAdapter(adapteru5);
        U_5.setSelection(defaultMass);

        U_5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                defaultMass=pos;
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        U_6=new Spinner(this);
        U_6.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        U_6.setPromptId(R.string.prompt_force);
        //ArrayAdapter<?> adapteru6 = ArrayAdapter.createFromResource(this, R.array.unit_force, android.R.layout.simple_spinner_item);
        CustomAdapter<CharSequence> adapteru6 = new CustomAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, this.getResources().getTextArray(R.array.unit_force));
        adapteru6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        U_6.setAdapter(adapteru6);
        U_6.setSelection(defaultForce);

        U_6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                defaultForce=pos;
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        U_7=new Spinner(this);
        U_7.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        U_7.setPromptId(R.string.prompt_energy);
        //ArrayAdapter<?> adapteru7 = ArrayAdapter.createFromResource(this, R.array.unit_energy, android.R.layout.simple_spinner_item);
        CustomAdapter<CharSequence> adapteru7 = new CustomAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, this.getResources().getTextArray(R.array.unit_energy));
        adapteru7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        U_7.setAdapter(adapteru7);
        U_7.setSelection(defaultEnergy);

        U_7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                defaultEnergy=pos;
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        U_8=new Spinner(this);
        U_8.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        U_8.setPromptId(R.string.prompt_frequency);
        //ArrayAdapter<?> adapteru8 = ArrayAdapter.createFromResource(this, R.array.unit_frequency, android.R.layout.simple_spinner_item);
        CustomAdapter<CharSequence> adapteru8 = new CustomAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, this.getResources().getTextArray(R.array.unit_frequency));
        adapteru8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        U_8.setAdapter(adapteru8);
        U_8.setSelection(defaultFrequency);

        U_8.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                defaultFrequency=pos;
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        U_9=new Spinner(this);
        U_9.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        U_9.setPromptId(R.string.prompt_acceleration);
        //ArrayAdapter<?> adapteru9 = ArrayAdapter.createFromResource(this, R.array.unit_acceleration, android.R.layout.simple_spinner_item);
        CustomAdapter<CharSequence> adapteru9 = new CustomAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, this.getResources().getTextArray(R.array.unit_acceleration));
        adapteru9.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        U_9.setAdapter(adapteru9);
        U_9.setSelection(defaultAcceleration);

        U_9.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                defaultAcceleration=pos;
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }
}