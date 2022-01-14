package com.musicslayer.physicstutor;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.view.ViewGroup;
import android.widget.*;
import com.localytics.android.*;
import android.os.Bundle;
import android.view.View;

public class PhysicsProblemsActivity extends PhysicsActivity {
	private LocalyticsSession localyticsSession;
    private ScrollView S1;
    private HorizontalScrollView H1;
    private LinearLayout L1, L_Triplet1, L_Triplet2, LA, LB, LC, LD, LE, LF;
    private Button projectile, vectors, inclineSimple, incline, springSimple, spring, charge;
    private ImageView IA, IB, IC, ID, IE, IF;
	
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
        startActivity(new Intent(PhysicsProblemsActivity.this, PhysicsIntroActivity.class));
	}

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            L1.setOrientation(0);
            S1.removeView(L1);
            H1.addView(L1);
            setContentView(H1);
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            L1.setOrientation(1);
            H1.removeView(L1);
            S1.addView(L1);
            setContentView(S1);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.localyticsSession = new LocalyticsSession(this.getApplicationContext(),LOCALYTICS);
		this.localyticsSession.open();
		this.localyticsSession.upload();
        makeProblems();
        setTitle("Physics Tutor - Problems");
        loadPrefs();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            L1.setOrientation(0);
            S1.removeView(L1);
            H1.addView(L1);
            setContentView(H1);
        }
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            L1.setOrientation(1);
            H1.removeView(L1);
            S1.addView(L1);
            setContentView(S1);
        }
    }

    void makeProblems () {
        S1=new ScrollView(this);
        S1.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        H1=new HorizontalScrollView(this);
        H1.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        L1=new LinearLayout(this);
        L1.setOrientation(1);
        L1.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));

        L_Triplet1=new LinearLayout(this);
        L_Triplet1.setOrientation(1);
        L_Triplet1.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        LA=new LinearLayout(this);
        LA.setOrientation(0);
        LA.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        projectile=new Button(this);
        projectile.setText("PROJECTILE");
        projectile.setTextSize(1,22*scale);
        projectile.setLayoutParams(new ViewGroup.LayoutParams(dpToPx((int)(150*scale)), dpToPx((int)(98*scale))));
        projectile.invalidate();
        projectile.getBackground().setColorFilter(0xFFFF3366, PorterDuff.Mode.MULTIPLY);
        projectile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                type="PROJECTILE";
                startActivity(new Intent(PhysicsProblemsActivity.this, PhysicsInfoActivity.class));
            }
        });

        IA=new ImageView(this);
        IA.setLayoutParams(new ViewGroup.LayoutParams(dpToPx((int)(150*scale)), dpToPx((int)(96*scale))));
        IA.setAdjustViewBounds(true);
        IA.setImageResource(R.raw.problem_projectile);

        LA.addView(projectile);
        LA.addView(IA);

        LB=new LinearLayout(this);
        LB.setOrientation(0);
        LB.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        vectors=new Button(this);
        vectors.setText("RESULTANT VECTORS");
        vectors.setTextSize(1,22*scale);
        vectors.setLayoutParams(new ViewGroup.LayoutParams(dpToPx((int)(150*scale)), dpToPx((int)(98*scale))));
        vectors.invalidate();
        vectors.getBackground().setColorFilter(0xFFFF3366, PorterDuff.Mode.MULTIPLY);
        vectors.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                type="VECTORS";
                startActivity(new Intent(PhysicsProblemsActivity.this, PhysicsInfoActivity.class));
            }
        });

        IB=new ImageView(this);
        IB.setLayoutParams(new ViewGroup.LayoutParams(dpToPx((int)(150*scale)), dpToPx((int)(96*scale))));
        IB.setAdjustViewBounds(true);
        IB.setImageResource(R.raw.problem_vectors);

        LB.addView(vectors);
        LB.addView(IB);

        LC=new LinearLayout(this);
        LC.setOrientation(0);
        LC.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        inclineSimple=new Button(this);
        inclineSimple.setText("INCLINE (SIMPLE)");
        inclineSimple.setTextSize(1,22*scale);
        inclineSimple.setLayoutParams(new ViewGroup.LayoutParams(dpToPx((int)(150*scale)), dpToPx((int)(98*scale))));
        inclineSimple.invalidate();
        inclineSimple.getBackground().setColorFilter(0xFFFF3366, PorterDuff.Mode.MULTIPLY);
        inclineSimple.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                type="INCLINE_SIMPLE";
                startActivity(new Intent(PhysicsProblemsActivity.this, PhysicsInfoActivity.class));
            }
        });

        IC=new ImageView(this);
        IC.setLayoutParams(new ViewGroup.LayoutParams(dpToPx((int)(150*scale)), dpToPx((int)(96*scale))));
        IC.setAdjustViewBounds(true);
        IC.setImageResource(R.raw.problem_incline);

        LC.addView(inclineSimple);
        LC.addView(IC);

        L_Triplet1.addView(LA);
        L_Triplet1.addView(LB);
        L_Triplet1.addView(LC);

        L_Triplet2=new LinearLayout(this);
        L_Triplet2.setOrientation(1);
        L_Triplet2.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        LD=new LinearLayout(this);
        LD.setOrientation(0);
        LD.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        incline=new Button(this);
        incline.setText("INCLINE");
        incline.setTextSize(1,22*scale);
        incline.setLayoutParams(new ViewGroup.LayoutParams(dpToPx((int)(150*scale)), dpToPx((int)(98*scale))));
        incline.invalidate();
        incline.getBackground().setColorFilter(0xFFFF3366, PorterDuff.Mode.MULTIPLY);
        incline.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                type="INCLINE";
                startActivity(new Intent(PhysicsProblemsActivity.this, PhysicsInfoActivity.class));
            }
        });

        ID=new ImageView(this);
        ID.setLayoutParams(new ViewGroup.LayoutParams(dpToPx((int)(150*scale)), dpToPx((int)(96*scale))));
        ID.setAdjustViewBounds(true);
        ID.setImageResource(R.raw.problem_incline);

        LD.addView(incline);
        LD.addView(ID);

        LE=new LinearLayout(this);
        LE.setOrientation(0);
        LE.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        springSimple=new Button(this);
        springSimple.setText("SPRING (SIMPLE)");
        springSimple.setTextSize(1,22*scale);
        springSimple.setLayoutParams(new ViewGroup.LayoutParams(dpToPx((int)(150*scale)), dpToPx((int)(98*scale))));
        springSimple.invalidate();
        springSimple.getBackground().setColorFilter(0xFFFF3366, PorterDuff.Mode.MULTIPLY);
        springSimple.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                type="SPRING_SIMPLE";
                startActivity(new Intent(PhysicsProblemsActivity.this, PhysicsInfoActivity.class));
            }
        });

        IE=new ImageView(this);
        IE.setLayoutParams(new ViewGroup.LayoutParams(dpToPx((int)(150*scale)), dpToPx((int)(96*scale))));
        IE.setAdjustViewBounds(true);
        IE.setImageResource(R.raw.problem_spring);

        LE.addView(springSimple);
        LE.addView(IE);

        LF=new LinearLayout(this);
        LF.setOrientation(0);
        LF.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        spring=new Button(this);
        spring.setText("SPRING");
        spring.setTextSize(1,22*scale);
        spring.setLayoutParams(new ViewGroup.LayoutParams(dpToPx((int)(150*scale)), dpToPx((int)(98*scale))));
        spring.invalidate();
        spring.getBackground().setColorFilter(0xFFFF3366, PorterDuff.Mode.MULTIPLY);
        spring.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                type="SPRING";
                startActivity(new Intent(PhysicsProblemsActivity.this, PhysicsInfoActivity.class));
            }
        });

        IF=new ImageView(this);
        IF.setLayoutParams(new ViewGroup.LayoutParams(dpToPx((int)(150*scale)), dpToPx((int)(96*scale))));
        IF.setAdjustViewBounds(true);
        IF.setImageResource(R.raw.problem_spring);

        //charge = new Button(this);

        LF.addView(spring);
        LF.addView(IF);

        L_Triplet2.addView(LD);
        L_Triplet2.addView(LE);
        L_Triplet2.addView(LF);
        L1.addView(L_Triplet1);
        L1.addView(L_Triplet2);
    }
}