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

public class PhysicsAssumptionsActivity extends PhysicsActivity {
	private LocalyticsSession localyticsSession;
	Spinner s;
	EditText gravity;
	Button gravity2;
	TextView gravity3;
	
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
		startActivity(new Intent(PhysicsAssumptionsActivity.this, PhysicsIntroActivity.class));
	return;
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
        
        gravity = (EditText) findViewById(R.id.EditText_Constant_g);
        gravity2 = (Button) findViewById(R.id.Button_Constant_g);
        gravity3 = (TextView) findViewById(R.id.TextView_Constant_g);
        if (PhysicsActivity.selection_g==2)
        {
        	gravity.setVisibility(0);
        	gravity2.setVisibility(0);
        }
        else
        {
        	gravity.setVisibility(4);
        	gravity2.setVisibility(4);
        }
        
        s = (Spinner) findViewById(R.id.Spinner_Constant_g);
        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.gravity, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        s.setSelection(PhysicsActivity.selection_g);
        
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                //Object item = parent.getItemAtPosition(pos);
                //gravity.setText(Integer.toString(pos));
            	switch (pos)
            	{
            	case 0: PhysicsActivity.constant_g=9.8; gravity.setVisibility(4); gravity2.setVisibility(4); break;
            	case 1: PhysicsActivity.constant_g=10; gravity.setVisibility(4); gravity2.setVisibility(4); break;
            	case 2: gravity.setVisibility(0); gravity2.setVisibility(0); break;
            	}
            	gravity3.setText("Constant g = "+String.format("%.4f", constant_g)+" m/s^2");
            	PhysicsActivity.selection_g=pos;
            }
            public void onNothingSelected(AdapterView<?> parent) {
            	PhysicsActivity.constant_g=9.8;
            	;
            }
        });
        
        gravity2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	try {
            		//gravity3.setText("Constant g (meters/(seconds^2)) = ".concat(Double.valueOf(gravity.getText().toString()).toString()));
            		PhysicsActivity.constant_g=Double.valueOf(gravity.getText().toString());
            		gravity3.setText("Constant g = "+String.format("%.4f", constant_g)+" m/s^2");
            	}
            	catch (NumberFormatException e) {
            		;
            	}
        }});    
        
    }
}