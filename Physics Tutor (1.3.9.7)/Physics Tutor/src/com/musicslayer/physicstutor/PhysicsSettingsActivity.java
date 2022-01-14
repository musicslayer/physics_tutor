package com.musicslayer.physicstutor;

import android.widget.*;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

public class PhysicsSettingsActivity extends PhysicsActivity {
	@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            MAIN.setOrientation(0);
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            MAIN.setOrientation(1);
        }
    }

    public void myOnBackPressed()
    {
        finish();
		startActivity(new Intent(this, PhysicsIntroActivity.class));
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MAIN=makeSettings();
        setContentView(MAIN);
        setTitle("Physics Tutor - Settings");

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            MAIN.setOrientation(0);
        }
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            MAIN.setOrientation(1);
        }
    }

    private CustomLinearLayout makeSettings () {
        final CustomLinearLayout L1, LA, L_Units;
        final TableLayout T_Units;
        final CustomTableRow TR_1, TR_2, TR_3, TR_4, TR_5, TR_6, TR_7, TR_8, TR_9, TR_10;
        final CustomScrollView S_Units;
        final CustomTextView TA, TB, T_1, T_2, T_3, T_4, T_5, T_6, T_7, T_8, T_9, T_10, answerText, keyboardText, defaultText;
        final CustomSpinner s1, s2, U_1, U_2, U_3, U_4, U_5, U_6, U_7, U_8, U_9, U_10;

        L1=new CustomLinearLayout(this,-1,-1,1);

        LA=new CustomLinearLayout(this,300,-2,1);
        LA.setPadding(0,0,dpToPx((int)(50*scale)),0);

        TA=new CustomTextView(this,-2,-2,14,"Answer Layout:");

        answerText=new CustomTextView(this);
        answerText.setPadding(0,0,0,dpToPx((int)(30*scale)));

        s1=new CustomSpinner(this, R.array.answer, "Answer Layout:", answerChoice);
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
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        TB=new CustomTextView(this,-2,-2,14,"Keyboard Choice:");

        keyboardText=new CustomTextView(this);
        keyboardText.setPadding(0,0,0,dpToPx((int)(30*scale)));

        s2=new CustomSpinner(this, R.array.keyboard, "Keyboard Choice:", keyboardChoice);
        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                switch (pos)
                {
                    case 0:
                        keyboardText.setText("Keyboard Choice A: The popup keyboard will start with numbers.");
                        break;
                    case 1:
                        keyboardText.setText("Keyboard Choice B: The popup keyboard will start as normal.");
                        break;
                }
                keyboardChoice=pos;
            }
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        LA.addView(TA);
        LA.addView(s1);
        LA.addView(answerText);
        LA.addView(TB);
        LA.addView(s2);
        LA.addView(keyboardText);

        L_Units=new CustomLinearLayout(this,-1,-1,1);

        defaultText=new CustomTextView(this,-2,-2,14,"Default Units:");

        S_Units=new CustomScrollView(this,-1,-1);

        T_Units=new TableLayout(this);

        TR_1=new CustomTableRow(this);

        T_1=new CustomTextView(this,-2,-2,14,"Length");

        U_1=new CustomSpinner(this, R.array.unit_length, "Unit (length):", defaultLength);
        U_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                defaultLength=pos;
            }
            public void onNothingSelected(AdapterView<?> arg0) {}
        });

        TR_1.addView(T_1);
        TR_1.addView(U_1);

        TR_2=new CustomTableRow(this);

        T_2=new CustomTextView(this,-2,-2,14,"Time");

        U_2=new CustomSpinner(this, R.array.unit_time, "Unit (time):", defaultTime);
        U_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                defaultTime=pos;
            }
            public void onNothingSelected(AdapterView<?> arg0) {}
        });

        TR_2.addView(T_2);
        TR_2.addView(U_2);

        TR_3=new CustomTableRow(this);

        T_3=new CustomTextView(this,-2,-2,14,"Velocity");

        U_3=new CustomSpinner(this, R.array.unit_velocity, "Unit (velocity):", defaultVelocity);
        U_3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                defaultVelocity=pos;
            }
            public void onNothingSelected(AdapterView<?> arg0) {}
        });

        TR_3.addView(T_3);
        TR_3.addView(U_3);

        TR_4=new CustomTableRow(this);

        T_4=new CustomTextView(this,-2,-2,14,"Angle");

        U_4=new CustomSpinner(this, R.array.unit_angle, "Unit (angle):", defaultAngle);
        U_4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                defaultAngle=pos;
            }
            public void onNothingSelected(AdapterView<?> arg0) {}
        });

        TR_4.addView(T_4);
        TR_4.addView(U_4);

        TR_5=new CustomTableRow(this);

        T_5=new CustomTextView(this,-2,-2,14,"Mass");

        U_5=new CustomSpinner(this, R.array.unit_mass, "Unit (mass):", defaultMass);
        U_5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                defaultMass=pos;
            }
            public void onNothingSelected(AdapterView<?> arg0) {}
        });

        TR_5.addView(T_5);
        TR_5.addView(U_5);

        TR_6=new CustomTableRow(this);

        T_6=new CustomTextView(this,-2,-2,14,"Force");

        U_6=new CustomSpinner(this, R.array.unit_force, "Unit (force):", defaultForce);
        U_6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                defaultForce=pos;
            }
            public void onNothingSelected(AdapterView<?> arg0) {}
        });

        TR_6.addView(T_6);
        TR_6.addView(U_6);

        TR_7=new CustomTableRow(this);

        T_7=new CustomTextView(this,-2,-2,14,"Energy");

        U_7=new CustomSpinner(this, R.array.unit_energy, "Unit (energy):", defaultEnergy);
        U_7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                defaultEnergy=pos;
            }
            public void onNothingSelected(AdapterView<?> arg0) {}
        });

        TR_7.addView(T_7);
        TR_7.addView(U_7);

        TR_8=new CustomTableRow(this);

        T_8=new CustomTextView(this,-2,-2,14,"Frequency");

        U_8=new CustomSpinner(this, R.array.unit_frequency, "Unit (frequency):", defaultFrequency);
        U_8.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                defaultFrequency=pos;
            }
            public void onNothingSelected(AdapterView<?> arg0) {}
        });

        TR_8.addView(T_8);
        TR_8.addView(U_8);

        TR_9=new CustomTableRow(this);

        T_9=new CustomTextView(this,-2,-2,14,"Acceleration");

        U_9=new CustomSpinner(this, R.array.unit_acceleration, "Unit (acceleration):", defaultAcceleration);
        U_9.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                defaultAcceleration=pos;
            }
            public void onNothingSelected(AdapterView<?> arg0) {}
        });

        TR_9.addView(T_9);
        TR_9.addView(U_9);

        TR_10=new CustomTableRow(this);

        T_10=new CustomTextView(this,-2,-2,14,"Spring Constant");

        U_10=new CustomSpinner(this, R.array.unit_springconstant, "Unit (spring constant):", defaultSpringConstant);
        U_10.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                defaultSpringConstant=pos;
            }
            public void onNothingSelected(AdapterView<?> arg0) {}
        });

        TR_10.addView(T_10);
        TR_10.addView(U_10);

        T_Units.addView(TR_1);
        T_Units.addView(TR_2);
        T_Units.addView(TR_3);
        T_Units.addView(TR_4);
        T_Units.addView(TR_5);
        T_Units.addView(TR_6);
        T_Units.addView(TR_7);
        T_Units.addView(TR_8);
        T_Units.addView(TR_9);
        T_Units.addView(TR_10);
        S_Units.addView(T_Units);
        L_Units.addView(defaultText);
        L_Units.addView(S_Units);
        L1.addView(LA);
        L1.addView(L_Units);

        return L1;
    }
}