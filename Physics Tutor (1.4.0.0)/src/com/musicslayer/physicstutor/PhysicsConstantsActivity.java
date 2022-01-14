package com.musicslayer.physicstutor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

public class PhysicsConstantsActivity extends PhysicsActivity {
    public void myOnBackPressed()
    {
        finish();
		startActivity(new Intent(this, PhysicsIntroActivity.class));
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        MAIN=makeConstants();
        setContentView(MAIN);
        setTitle("Physics Tutor - Constants");
        super.onCreate(savedInstanceState);
    }

    private CustomLinearLayout makeConstants()
    {
        final CustomLinearLayout L=new CustomLinearLayout(this,-1,-1,1);
        final CustomTextView T_Gravity = new CustomTextView(this,14);
        final CustomSpinner S_Gravity = new CustomSpinner(this,gravity,"Change value to:",selection_g,80,50);
        final CustomEditText E_Gravity = new CustomEditText(this,180,50,14);
        final CustomButton B_Gravity = new CustomButton(this,70,50,"SET",22);

        S_Gravity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> parent){}
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                switch (pos) {
                    case 0:
                        constant_g=9.8;
                        E_Gravity.setVisibility(8);
                        B_Gravity.setVisibility(8);
                        break;
                    case 1:
                        constant_g=10;
                        E_Gravity.setVisibility(8);
                        B_Gravity.setVisibility(8);
                        break;
                    case 2:
                        E_Gravity.setVisibility(0);
                        B_Gravity.setVisibility(0);
                        break;
                }
                T_Gravity.setText("Constant g = "+String.format("%.4f", constant_g)+" m/s^2");
                selection_g=pos;
            }
        });

        B_Gravity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try{constant_g=Double.valueOf(E_Gravity.getText().toString());}
                catch (NumberFormatException e){}
                T_Gravity.setText("Constant g = "+String.format("%.4f", constant_g)+" m/s^2");
            }
        });

        L.addView(T_Gravity);
        L.addView(S_Gravity);
        L.addView(E_Gravity);
        L.addView(B_Gravity);

        return L;
    }
}