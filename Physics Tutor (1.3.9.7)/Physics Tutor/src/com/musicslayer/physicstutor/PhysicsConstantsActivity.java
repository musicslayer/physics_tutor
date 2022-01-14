package com.musicslayer.physicstutor;

import android.widget.*;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PhysicsConstantsActivity extends PhysicsActivity {
    public void myOnBackPressed()
    {
        finish();
		startActivity(new Intent(this, PhysicsIntroActivity.class));
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        MAIN=makeConstants();
        setContentView(MAIN);
        setTitle("Physics Tutor - Constants");
    }

    private CustomLinearLayout makeConstants()
    {
        final CustomLinearLayout L1;
        final CustomSpinner S_gravity;
        final CustomEditText E_gravity;
        final CustomButton B_gravity;
        final CustomTextView T_gravity;

        L1=new CustomLinearLayout(this,-1,-1,1);

        T_gravity = new CustomTextView(this,14);

        E_gravity = new CustomEditText(this,180,50,14);
        E_gravity.setVisibility(4);

        B_gravity = new CustomButton(this,70,50,"SET",22);
        B_gravity.setVisibility(4);
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

        S_gravity = new CustomSpinner(this, R.array.gravity, "Change value to:", selection_g);
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