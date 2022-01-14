package com.musicslayer.physicstutor;

import com.Localytics.android.*;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class PhysicsConstantsActivity extends PhysicsActivity {
	private LocalyticsSession localyticsSession;
	Spinner s;
	EditText E_gravity;
	Button set_gravity;
	TextView T_gravity;
	
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
    }
	
	@Override
	public void onBackPressed() {
		startActivity(new Intent(PhysicsConstantsActivity.this, PhysicsIntroActivity.class));
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assumptions);
        setTitle("Physics Tutor - Constants");
        this.localyticsSession = new LocalyticsSession(this.getApplicationContext(),LOCALYTICS);
		this.localyticsSession.open();
		this.localyticsSession.upload();
        loadPrefs();
        
        E_gravity = (EditText) findViewById(R.id.EditText_Constant_g);
        set_gravity = (Button) findViewById(R.id.Set_Constant_g);
        T_gravity = (TextView) findViewById(R.id.TextView_Constant_g);

        s = (Spinner) findViewById(R.id.Spinner_Constant_g);
        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.gravity, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        s.setSelection(selection_g);
        
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            	switch (pos)
            	{
            	case 0: constant_g=9.8; E_gravity.setVisibility(8); set_gravity.setVisibility(8); break;
            	case 1: constant_g=10; E_gravity.setVisibility(8); set_gravity.setVisibility(8); break;
                case 2: E_gravity.setVisibility(0); set_gravity.setVisibility(0);
            	}
            	T_gravity.setText("Constant g = "+String.format("%.4f", constant_g)+" m/s^2");
                selection_g=pos;
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        set_gravity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	try {
            		constant_g=Double.valueOf(E_gravity.getText().toString());
            		T_gravity.setText("Constant g = "+String.format("%.4f", constant_g)+" m/s^2");
            	}
            	catch (NumberFormatException e) {
            	}
        }});
    }
}