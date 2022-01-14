package com.musicslayer.physicstutor;

import android.widget.*;
import com.localytics.android.*;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PhysicsConstantsActivity extends PhysicsActivity {
	private LocalyticsSession localyticsSession;
	private Spinner S_gravity;
	private EditText E_gravity;
	private Button B_gravity;
	private TextView T_gravity;
	
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
		startActivity(new Intent(PhysicsConstantsActivity.this, PhysicsIntroActivity.class));
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.constants);
        setTitle("Physics Tutor - Constants");
        this.localyticsSession = new LocalyticsSession(this.getApplicationContext(),LOCALYTICS);
		this.localyticsSession.open();
		this.localyticsSession.upload();
        loadPrefs();

        E_gravity = (EditText) findViewById(R.id.EditText_Constant_g);
        B_gravity = (Button) findViewById(R.id.Button_Constant_g);
        T_gravity = (TextView) findViewById(R.id.TextView_Constant_g);
        S_gravity = (Spinner) findViewById(R.id.Spinner_Constant_g);
        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.gravity, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        S_gravity.setAdapter(adapter);
        S_gravity.setSelection(selection_g);
        
        S_gravity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            	switch (pos) {
                    case 0:
                        constant_g=9.8;
                        E_gravity.setVisibility(8);
                        B_gravity.setVisibility(8);
                        break;
                    case 1:
                        constant_g=10;
                        E_gravity.setVisibility(8);
                        B_gravity.setVisibility(8);
                        break;
                    case 2:
                        E_gravity.setVisibility(0);
                        B_gravity.setVisibility(0);
                        break;
            	}
            	T_gravity.setText("Constant g = "+String.format("%.4f", constant_g)+" m/s^2");
                selection_g=pos;
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        B_gravity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	try
                {
            		constant_g=Double.valueOf(E_gravity.getText().toString());
            		T_gravity.setText("Constant g = "+String.format("%.4f", constant_g)+" m/s^2");
            	}
            	catch (NumberFormatException e)
                {
            	}
            }
        });
    }
}