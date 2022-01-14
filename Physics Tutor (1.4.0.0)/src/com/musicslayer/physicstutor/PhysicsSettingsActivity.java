package com.musicslayer.physicstutor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

public class PhysicsSettingsActivity extends PhysicsActivity {
	public void myOnBackPressed()
    {
        finish();
		startActivity(new Intent(this, PhysicsIntroActivity.class));
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        MAIN=makeSettings();
        setContentView(MAIN);
        setTitle("Physics Tutor - Settings");
        super.onCreate(savedInstanceState);
    }

    private CustomLinearLayout makeSettings () {
        final CustomLinearLayout L=new CustomLinearLayout(this,-1,-1,1);
        final CustomLinearLayout LA=new CustomLinearLayout(this,300,-2,1);
        final CustomScrollView S_Prefs=new CustomScrollView(this,-1,-1);
        final CustomLinearLayout L_Prefs=new CustomLinearLayout(this,-1,-1,1);
        final CustomLinearLayout LB=new CustomLinearLayout(this,-1,-1,1);
        final CustomScrollView S_Units=new CustomScrollView(this,-1,-1);
        final CustomLinearLayout L_Units=new CustomLinearLayout(this,-1,-1,1);

        LA.setPadding(0,0,dpToPx((int)(50*scale)),0);

        for (int c=0;c<prefName.length;c++)
        {
            final int i=c;
            final CustomLinearLayout L_Row=new CustomLinearLayout(this,-2,-2,0);
            final CustomTextView T=new CustomTextView(this,110,50,14,prefName[i]+":");
            final CustomSpinner S=new CustomSpinner(this, prefOptions[i], prefName[i]+":", prefs[i], 90, 50);
            final CustomTextView prefText=new CustomTextView(this);

            T.setGravity(17);

            S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onNothingSelected(AdapterView<?> parent){}
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    prefText.setText(prefString[i][pos]);
                    prefs[i]=pos;
                }
            });

            prefText.setPadding(0,0,0,dpToPx((int)(15*scale)));

            L_Row.addView(T);
            L_Row.addView(S);
            L_Prefs.addView(L_Row);
            L_Prefs.addView(prefText);
        }

        for (int c=0;c<unitType.length;c++)
        {
            final int i=c;
            final CustomLinearLayout L_Row=new CustomLinearLayout(this,-2,-2,0);
            final CustomTextView T=new CustomTextView(this,110,50,14,unitType[i]);
            final CustomSpinner SL=new CustomSpinner(this,unitPrefix,"SI Prefix ("+unitType[i]+"):",defaultUnitPrefix[i],40,50);
            final CustomSpinner SR=new CustomSpinner(this,unitText[i],"Unit ("+unitType[i]+"):",defaultUnit[i],80,50);

            T.setGravity(17);

            SL.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onNothingSelected(AdapterView<?> parent) {}
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    defaultUnitPrefix[i]=pos;
                }
            });

            SR.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onNothingSelected(AdapterView<?> parent) {}
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    defaultUnit[i]=pos;
                }
            });

            L_Row.addView(T);
            L_Row.addView(SL);
            L_Row.addView(SR);
            L_Units.addView(L_Row);
        }

        S_Prefs.addView(L_Prefs);
        LA.addView(new CustomTextView(this,-2,-2,14,"Preferences:",-16711936));
        LA.addView(S_Prefs);
        S_Units.addView(L_Units);
        LB.addView(new CustomTextView(this,-2,-2,14,"Default Units:",-16711936));
        LB.addView(S_Units);
        L.addView(LA);
        L.addView(LB);

        return L;
    }
}