package com.musicslayer.physicstutor;

import android.view.ViewGroup;
import android.widget.*;
import com.localytics.android.*;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PhysicsConstantsActivity extends PhysicsActivity {
	private LocalyticsSession localyticsSession;
    private LinearLayout L, L1;
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
        this.localyticsSession = new LocalyticsSession(this.getApplicationContext(),LOCALYTICS);
		this.localyticsSession.open();
		this.localyticsSession.upload();
        L=makeConstants();
        setContentView(L);
        setTitle("Physics Tutor - Constants");
        loadPrefs();
    }

    LinearLayout makeConstants()
    {
        L1=new LinearLayout(this);
        L1.setOrientation(1);
        L1.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));

        T_gravity = new TextView(this);
        T_gravity.setTextSize(1,14*scale);

        E_gravity = new EditText(this);
        E_gravity.setTextSize(1,14*scale);
        E_gravity.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
        E_gravity.setVisibility(4);

        B_gravity = new Button(this);
        B_gravity.setTextSize(1,22*scale);
        B_gravity.setLayoutParams(new ViewGroup.LayoutParams(dpToPx((int)(70*scale)), dpToPx((int)(50*scale))));
        B_gravity.setVisibility(4);
        B_gravity.setText("SET");
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

        S_gravity = new Spinner(this);
        S_gravity.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
        S_gravity.setPromptId(R.string.prompt_constant);
        CustomAdapter<CharSequence> adapter = new CustomAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, this.getResources().getTextArray(R.array.gravity));
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

        L1.addView(T_gravity);
        L1.addView(S_gravity);
        L1.addView(E_gravity);
        L1.addView(B_gravity);

        return L1;
    }
}