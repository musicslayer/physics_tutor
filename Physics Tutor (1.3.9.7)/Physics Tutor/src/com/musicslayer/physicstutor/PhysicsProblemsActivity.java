package com.musicslayer.physicstutor;

import android.content.Intent;
import android.content.res.Configuration;
import android.widget.*;
import android.os.Bundle;
import android.view.View;

public class PhysicsProblemsActivity extends PhysicsActivity {
    private CustomScrollView S1;
    private CustomHorizontalScrollView H1;

    public void myOnBackPressed()
    {
        finish();
        startActivity(new Intent(this, PhysicsIntroActivity.class));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            MAIN.setOrientation(0);
            S1.removeView(MAIN);
            H1.addView(MAIN);
            setContentView(H1);
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            MAIN.setOrientation(1);
            H1.removeView(MAIN);
            S1.addView(MAIN);
            setContentView(S1);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        S1=new CustomScrollView(this,-2,-2);

        H1=new CustomHorizontalScrollView(this,-2,-2);

        MAIN=makeProblems();
        setTitle("Physics Tutor - Problems");

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            MAIN.setOrientation(0);
            S1.removeView(MAIN);
            H1.addView(MAIN);
            setContentView(H1);
        }
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            MAIN.setOrientation(1);
            H1.removeView(MAIN);
            S1.addView(MAIN);
            setContentView(S1);
        }
    }

    private CustomLinearLayout makeProblems () {
        final CustomLinearLayout L1, L_Triplet1, L_Triplet2, LA, LB, LC, LD, LE, LF;
        final CustomButton projectile, vectors, inclineSimple, incline, springSimple, spring, charge;
        final ImageView IA, IB, IC, ID, IE, IF;

        L1=new CustomLinearLayout(this,-1,-1,1);
        L_Triplet1=new CustomLinearLayout(this,-2,-2,1);
        LA=new CustomLinearLayout(this,-2,-2,0);

        projectile=new CustomButton(this,150,98,"PROJECTILE",22,R.color.myRed2);
        projectile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                type="PROJECTILE";
                finish();
                startActivity(new Intent(PhysicsProblemsActivity.this, PhysicsInfoActivity.class));
            }
        });

        IA=new CustomImageView(this,150,96,R.raw.problem_projectile);

        LA.addView(projectile);
        LA.addView(IA);

        LB=new CustomLinearLayout(this,-2,-2,0);

        vectors=new CustomButton(this,150,98,"RESULTANT VECTORS",22,R.color.myRed2);
        vectors.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                type="VECTORS";
                finish();
                startActivity(new Intent(PhysicsProblemsActivity.this, PhysicsInfoActivity.class));
            }
        });

        IB=new CustomImageView(this,150,96,R.raw.problem_vectors);

        LB.addView(vectors);
        LB.addView(IB);

        LC=new CustomLinearLayout(this,-2,-2,0);

        inclineSimple=new CustomButton(this,150,98,"INCLINE (SIMPLE)",22,R.color.myRed2);
        inclineSimple.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                type="INCLINE_SIMPLE";
                finish();
                startActivity(new Intent(PhysicsProblemsActivity.this, PhysicsInfoActivity.class));
            }
        });

        IC=new CustomImageView(this,150,96,R.raw.problem_incline);

        LC.addView(inclineSimple);
        LC.addView(IC);

        L_Triplet1.addView(LA);
        L_Triplet1.addView(LB);
        L_Triplet1.addView(LC);

        L_Triplet2=new CustomLinearLayout(this,-2,-2,1);
        LD=new CustomLinearLayout(this,-2,-2,0);

        incline=new CustomButton(this,150,98,"INCLINE",22,R.color.myRed2);
        incline.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                type="INCLINE";
                finish();
                startActivity(new Intent(PhysicsProblemsActivity.this, PhysicsInfoActivity.class));
            }
        });

        ID=new CustomImageView(this,150,96,R.raw.problem_incline);

        LD.addView(incline);
        LD.addView(ID);

        LE=new CustomLinearLayout(this,-2,-2,0);

        springSimple=new CustomButton(this,150,98,"SPRING (SIMPLE)",22,R.color.myRed2);
        springSimple.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                type="SPRING_SIMPLE";
                finish();
                startActivity(new Intent(PhysicsProblemsActivity.this, PhysicsInfoActivity.class));
            }
        });

        IE=new CustomImageView(this,150,96,R.raw.problem_spring);

        LE.addView(springSimple);
        LE.addView(IE);

        LF=new CustomLinearLayout(this,-2,-2,0);

        spring=new CustomButton(this,150,98,"SPRING",22,R.color.myRed2);
        spring.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                type="SPRING";
                finish();
                startActivity(new Intent(PhysicsProblemsActivity.this, PhysicsInfoActivity.class));
            }
        });

        IF=new CustomImageView(this,150,96,R.raw.problem_spring);

        //charge = new Button(this);

        LF.addView(spring);
        LF.addView(IF);

        L_Triplet2.addView(LD);
        L_Triplet2.addView(LE);
        L_Triplet2.addView(LF);
        L1.addView(L_Triplet1);
        L1.addView(L_Triplet2);

        return L1;
    }
}