package com.musicslayer.physicstutor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class PhysicsProblemsActivity extends PhysicsActivity {
    private boolean seeImage=false;
    private int visType1=0;
    final static private String types[][]=new String[][] {{"PROJECTILE"},{"VECTORS"},{"INCLINE_SIMPLE","INCLINE"},{"SPRING_SIMPLE","SPRING"},{"CIRCUIT_LRC"}};
    final static private String typeText[]=new String[] {"PROJECTILE","RESULTANT VECTORS","INCLINE","SPRING","CIRCUIT"};
    final static private String subTypeText[][]=new String[][] {{"STANDARD"},{"STANDARD"},{"SIMPLE","STANDARD"},{"SIMPLE","STANDARD"},{"LRC"}};

    public void myOnBackPressed()
    {
        if (seeImage)
        {
            setContentView(MAIN);
            seeImage=false;
        }
        else
        {
            finish();
            startActivity(new Intent(this, PhysicsIntroActivity.class));
        }
    }
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        MAIN=makeProblems();
        setContentView(MAIN);
        setTitle("Physics Tutor - Problems");
        super.onCreate(savedInstanceState);
	}

    private CustomLinearLayout makeProblems () {
        final CustomLinearLayout L=new CustomLinearLayout(this,-1,-1,1);
        final CustomLinearLayout LA=new CustomLinearLayout(this,300,300,1);
        final CustomLinearLayout L_Type1List=new CustomLinearLayout(this,-1,-2,0);
        final CustomButton B_PrevType1=new CustomButton(this,50,50,"<=",20);
        final CustomTextView type1Text=new CustomTextView(this,200,50,20,typeText[0]);
        final CustomButton B_NextType1=new CustomButton(this,50,50,"=>",20);
        final CustomLinearLayout L_Type2List=new CustomLinearLayout(this,-1,-2,0);
        final CustomButton B_PrevType2=new CustomButton(this,50,50,"<=",20);
        final CustomTextView type2Text=new CustomTextView(this,80,50,15,subTypeText[0][0]);
        final CustomButton B_NextType2=new CustomButton(this,50,50,"=>",20);
        final CustomButton proceed=new CustomButton(this,120,50,"PROCEED",22,R.color.myBlue);
        final CustomScrollView S_Info=new CustomScrollView(this,-1,-1);
        final CustomLinearLayout L_Info=new CustomLinearLayout(this,-1,-1,1);
        final CustomTextView description=new CustomTextView(this,14);
        final CustomTextView variables=new CustomTextView(this,14);
        final CustomTextView constants=new CustomTextView(this,14);
        final CustomTextView notes=new CustomTextView(this,14);
        final ZoomCustomImageView helper=new ZoomCustomImageView(this,-2,-2);

        final int numTypes1=types.length;
        final int numTypes2[]=new int[numTypes1];
        final int visType2[]=new int[numTypes1];

        for (int i=0;i<numTypes1;i++)
        {
            numTypes2[i]=types[i].length;
            visType2[i]=0;
        }

        B_PrevType1.setEnabled(false);
        B_PrevType1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (visType1>0)
                {
                    if (--visType1==0){B_PrevType1.setEnabled(false);}
                    B_NextType1.setEnabled(true);
                    type1Text.setText(typeText[visType1]);
                    type2Text.setText(subTypeText[visType1][visType2[visType1]]);
                    B_PrevType2.setEnabled(visType2[visType1]!=0);
                    B_NextType2.setEnabled(visType2[visType1]!=numTypes2[visType1]-1);
                    fillInfo(types[visType1][visType2[visType1]],description,variables,constants,notes,helper);
                }
            }
        });

        type1Text.setGravity(17);

        if (numTypes1==1){B_NextType1.setEnabled(false);}
        B_NextType1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (visType1<numTypes1-1)
                {
                    if (++visType1==numTypes1-1){B_NextType1.setEnabled(false);}
                    B_PrevType1.setEnabled(true);
                    type1Text.setText(typeText[visType1]);
                    type2Text.setText(subTypeText[visType1][visType2[visType1]]);
                    B_PrevType2.setEnabled(visType2[visType1]!=0);
                    B_NextType2.setEnabled(visType2[visType1]!=numTypes2[visType1]-1);
                    fillInfo(types[visType1][visType2[visType1]],description,variables,constants,notes,helper);
                }
            }
        });

        L_Type1List.addView(B_PrevType1);
        L_Type1List.addView(type1Text);
        L_Type1List.addView(B_NextType1);

        B_PrevType2.setEnabled(false);
        B_PrevType2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (visType2[visType1]>0)
                {
                    if (--visType2[visType1]==0){B_PrevType2.setEnabled(false);}
                    B_NextType2.setEnabled(true);
                    type2Text.setText(subTypeText[visType1][visType2[visType1]]);
                    fillInfo(types[visType1][visType2[visType1]],description,variables,constants,notes,helper);
                }
            }
        });

        type2Text.setGravity(17);

        if (numTypes2[0]==1){B_NextType2.setEnabled(false);}
        B_NextType2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (visType2[visType1]<numTypes2[visType1]-1)
                {
                    if (++visType2[visType1]==numTypes2[visType1]-1){B_NextType2.setEnabled(false);}
                    B_PrevType2.setEnabled(true);
                    type2Text.setText(subTypeText[visType1][visType2[visType1]]);
                    fillInfo(types[visType1][visType2[visType1]],description,variables,constants,notes,helper);
                }
            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                type=types[visType1][visType2[visType1]];
                finish();
                startActivity(new Intent(PhysicsProblemsActivity.this, PhysicsInputActivity.class));
                localyticsSession.tagEvent("input_"+type.toLowerCase());
            }
        });

        L_Type2List.addView(B_PrevType2);
        L_Type2List.addView(type2Text);
        L_Type2List.addView(B_NextType2);
        L_Type2List.addView(proceed);
        L_Info.addView(description);
        L_Info.addView(variables);
        L_Info.addView(constants);
        L_Info.addView(notes);
        S_Info.addView(L_Info);
        LA.addView(L_Type1List);
        LA.addView(L_Type2List);
        LA.addView(S_Info);

        helper.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(helper.fullScreen());
                seeImage=true;
            }
        });

        L.addView(LA);
        L.addView(helper);
        fillInfo("PROJECTILE",description,variables,constants,notes,helper);

        return L;
    }

    private void fillInfo(final String type, CustomTextView description, CustomTextView variables, CustomTextView constants, CustomTextView notes, ImageView helper)
    {
        if ("PROJECTILE".equals(type))
        {
            description.setText("A simple projectile motion problem. An object is launched and is under constant gravitational pull downward. There are no drag forces.");
            if(prefs[2]==0){variables.setText("\nVariables used in this problem:\nTime, X-position, Y-position, Y-velocity, Overall Velocity, Angle");}
            else{variables.setText("\nVariables used in this problem:\nt, x(t), y(t), Vy(t), V(t), \u03B8(t)");}
            constants.setText("\nConstants used in this problem:\nConstant g = "+String.format("%.4f", constant_g)+" m/s^2");
            notes.setText("\nNotes:\nThe object is assumed to follow the flight path for all time. You must check to see if the object would crash into something yourself.");
            helper.setImageResource(R.raw.info_projectile);
        }
        else if ("VECTORS".equals(type))
        {
            description.setText("Vectors of various magnitude and direction are given, and we wish to resolve them into one resultant vector. Also, an equilibrant vector that cancels the resultant vector can be found.");
            variables.setText("\nVariables used in this problem:\nNone");
            constants.setText("\nConstants used in this problem:\nNone");
            notes.setText("\nNotes:\nAlthough the solver uses force vectors, this problem can apply to other vectors that obey superposition.");
            helper.setImageResource(R.raw.info_vectors);
        }
        else if ("INCLINE_SIMPLE".equals(type))
        {
            description.setText("An object is on an incline under the influence of gravity, the normal force, and friction.\n\nThis problem deals with finding the angle of the incline, the mass, and the forces on the object, NOT the position of the object on the incline.");
            variables.setText("\nVariables used in this problem:\nNone");
            constants.setText("\nConstants used in this problem:\nConstant g = "+String.format("%.4f", constant_g)+" m/s^2");
            notes.setText("\nNotes:\nThe incline is assumed to extend forever, and the object never leaves the surface of the incline.");
            helper.setImageResource(R.raw.info_incline);
        }
        else if ("INCLINE".equals(type))
        {
            description.setText("An object slides along an incline under the influence of gravity, the normal force, and friction.\n\nThis problem deals with the position and velocity of the object on the incline.");
            if(prefs[2]==0){variables.setText("\nVariables used in this problem:\nTime, Position along incline, Velocity along incline");}
            else{variables.setText("\nVariables used in this problem:\nt, s(t), Vs(t)");}
            constants.setText("\nConstants used in this problem:\nConstant g = "+String.format("%.4f", constant_g)+" m/s^2");
            notes.setText("\nNotes:\nThe incline is assumed to extend forever, and the object never leaves the surface of the incline. Also, currently it is explicitly assumed that friction is small enough to allow motion. If this is not the case, then the solver may give wrong answers.");
            helper.setImageResource(R.raw.info_incline);
        }
        else if ("SPRING_SIMPLE".equals(type))
        {
            description.setText("A mass is attached to a spring, only affected by the spring's elastic force.\n\nThis problem deals with the position and restoring force of the mass, NOT the overall sinusoidal motion.");
            if(prefs[2]==0){variables.setText("\nVariables used in this problem:\nPosition, Force");}
            else{variables.setText("\nVariables used in this problem:\nx, F");}
            constants.setText("\nConstants used in this problem:\nNone");
            notes.setText("\nNotes:\nGravity and friction on the mass are neglected. Also, the spring is ideal (it has no mass, can stretch and compress without limit, never breaks, and obeys Hooke's Law).");
            helper.setImageResource(R.raw.info_spring_simple);
        }
        else if ("SPRING".equals(type))
        {
            description.setText("A mass is attached to a spring. The mass oscillates back and forth only under the influence of the spring's elastic force.\n\nThis problem deals with the sinusoidal motion of the mass.");
            if(prefs[2]==0){variables.setText("\nVariables used in this problem:\nTime, Position, Velocity, Acceleration, Force, Kinetic Energy, Potential Energy");}
            else{variables.setText("\nVariables used in this problem:\nt, x(t), v(t), a(t), F(t), E-Kinetic(t), E-Potential(t)");}
            constants.setText("\nConstants used in this problem:\nNone");
            notes.setText("\nNotes:\nGravity and friction on the mass are neglected. Also, the spring is ideal (it has no mass, can stretch and compress without limit, never breaks, and obeys Hooke's Law).");
            helper.setImageResource(R.raw.info_spring);
        }
        else if ("CIRCUIT_LRC".equals(type))
        {
            description.setText("A series circuit possibly has a voltage source, a resistor, a capacitor, and an inductor. Any combination of the circuit elements can be removed.");
            if(prefs[2]==0){variables.setText("\nVariables used in this problem:\nTime, Capacitor Charge, Current, Slope Of Current, Capacitor Voltage, Resistor Voltage, Inductor Voltage");}
            else{variables.setText("\nVariables used in this problem:\nt, q(t), i(t), di(t)/dt, VC(t), VR(t), VL(t)");}
            constants.setText("\nConstants used in this problem:\nNone");
            notes.setText("\nNotes:\nSome circuits, such as one that only has a voltage source, produce nonsensical results. You can exclude the voltage source by setting the voltage to zero. If you wish to exclude one of the other elements from the circuit, explicitly choose the page for that option. Setting the inductance to zero, for example, may not produce all of the correct results of an RC circuit.");
            helper.setImageResource(R.raw.default2);
        }
    }
}