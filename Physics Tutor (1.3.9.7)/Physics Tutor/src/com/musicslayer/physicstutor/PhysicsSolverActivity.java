package com.musicslayer.physicstutor;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.*;
import android.widget.*;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

public class PhysicsSolverActivity extends PhysicsActivity {
    private Toast toastSMS, toastEmail, toastExit;
    private CustomLinearLayout L1, L1A, L1B;
    final private CustomEditText E_varInput[]=new CustomEditText[10];
    private String instructions[]=new String[20];
    private String overallnames[];

    private double k1, k2, k3, k4, input, inputRaw, peak, angle, peakTime, peakS, S;

    private double factorA1[]={1.0,1.0,1.0,1.0,1.0,1.0,1.0};
    private double factorA2[]={1.0,1.0,1.0,1.0,1.0,1.0,1.0};
    private double factorA3[]={1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0};

    private double v1=0, v2=0, v3=0, v4=0, v5=0, v6=0, v7=0, v8=0;
    private int stotal, s=1, helperImage[]=new int[20], solveChoice;
    private boolean seeImage=false, split=false;

    private CustomButton B_Prev, B_Next, B_Answers, B_Constants, B_Constants1, B_Pictures1;
    
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
        if (seeImage)
        {
            setContentView(MAIN);
            seeImage=false;
        }
        else
        {
            if (split)
            {
                split=false;
                MAIN=L1;
                setContentView(MAIN);

                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                {
                    MAIN.setOrientation(0);
                }
                else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                {
                    MAIN.setOrientation(1);
                }
            }
            else
            {
                toastExit.show();
            }
        }
	}
    
    @Override 
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 1, Menu.NONE, "QUIT");
        menu.add(0, 2, Menu.NONE, "EDIT INPUTS");
        menu.add(0, 3, Menu.NONE, "EMAIL DEVELOPER");
        menu.findItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem m) {
                finish();
                startActivity(new Intent(PhysicsSolverActivity.this, PhysicsIntroActivity.class));
                return true;
            }
        });
        menu.findItem(2).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem m) {
                finish();
                startActivity(new Intent(PhysicsSolverActivity.this, PhysicsInputActivity.class));
                return true;
            }
        });
        menu.findItem(3).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem m) {
                final Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","musicslayer@gmail.com", null));
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Physics Tutor - Bug Report/Feedback");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "\n\n~~~~~~~~~~~~~~~~~~~~\nType of Problem:\n"+type+"\n"+getHumanReadableText(Sunit)+"\n\nProblem Constants:\n"+assignConstants()+"\n\nOptions:\n"+option[0]+"\n"+option[1]+"\n"+option[2]+"\n"+option[3]+"\n\nUser inputs:\n"+u1+"\n"+u2+"\n"+u3+"\n"+u4+"\n"+u5+"\n"+u6+"\n"+u7+"\n"+u8+"\n"+u9+"\n"+u10+"\n"+u11+"\n"+u12+"\n~~~~~~~~~~~~~~~~~~~~");
                try
                {
                    startActivity(emailIntent);
                }
                catch (ActivityNotFoundException e)
                {
                    toastEmail.show();
                }
                return true;
            }
        });
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if ("PROJECTILE".equals(type))
        {
            setTitle("Physics Tutor - Projectile Solver");
        }
        else if ("VECTORS".equals(type))
        {
            setTitle("Physics Tutor - Vectors Solver");
        }
        else if ("INCLINE_SIMPLE".equals(type))
        {
            setTitle("Physics Tutor - Simple Incline Solver");
        }
        else if ("INCLINE".equals(type))
        {
            setTitle("Physics Tutor - Incline Solver");
        }
        else if ("SPRING_SIMPLE".equals(type))
        {
            setTitle("Physics Tutor - Simple Spring Solver");
        }
        else if ("SPRING".equals(type))
        {
            setTitle("Physics Tutor - Spring Solver");
        }
        assignVariables();
        assignExplanation();
        makeSolver();
        MAIN=L1;
        setContentView(MAIN);
        
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) 
        {
            MAIN.setOrientation(0);
        } 
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            MAIN.setOrientation(1);
        }

        toastSMS=Toast.makeText(getApplicationContext(), "Your device does not have a text messaging application.", 0);
        toastEmail=Toast.makeText(getApplicationContext(), "Your device does not have an email application.", 0);
        toastExit=Toast.makeText(getApplicationContext(), "Press MENU and select QUIT to exit, or EDIT INPUTS to change the inputs.", 0);
    }

    private void makeSolver()
    {
        final CustomLinearLayout LA, LB, LAA, LBA, LBA2, LBAA, LBAA2, L_Exp;
        final CustomButton B_ViewExplanation, B_ViewAnswers;
        final CustomTextView T_Step, T_Display, T_Display1;
        final CustomScrollView S_Exp, S_Pictures;
        final TableLayout Table_Answers, Table_Constants;
        final ZoomCustomImageView I_Helper;
        final TwoDScrollView Dual_Answers, Dual_Constants, Dual_Constants1;

        //final CustomButton B_ShareConstants=new CustomButton(this,70,50,"SHARE",14,R.color.myBlue);
        //final CustomButton B_ShareConstants1=new CustomButton(this,70,50,"SHARE",14,R.color.myBlue);

        /*
        B_ShareConstants.setVisibility(8);
        B_ShareConstants.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alert.setTitle("Share Your Solution!");
                alert.setMessage("Choose a method of sharing your answer:");
                alert.setPositiveButton("SMS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        final Intent shareSMSIntent = new Intent(Intent.ACTION_VIEW);

                        shareSMSIntent.putExtra("sms_body", "Problem:\n\n"+getHumanReadableText(Sunit)+"\n\nProblem Constants:\n"+assignConstants());
                        shareSMSIntent.setType("vnd.android-dir/mms-sms");
                        try
                        {
                            startActivity(shareSMSIntent);
                        }
                        catch (ActivityNotFoundException e)
                        {
                            toastSMS.show();
                        }
                    }
                });
                alert.setNeutralButton("EMAIL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        final Intent shareEmailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","", null));

                        shareEmailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Physics Tutor - Your friend has solved a physics problem for you!");
                        shareEmailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Problem:\n\n"+getHumanReadableText(Sunit)+"\n\nProblem Constants:\n"+assignConstants());
                        try
                        {
                            startActivity(shareEmailIntent);
                        }
                        catch (ActivityNotFoundException e)
                        {
                            toastEmail.show();
                        }
                    }
                });
                alert.setNegativeButton("(Cancel)", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });
                alert.create().show();
            }
        });

        B_ShareConstants1.setVisibility(8);
        B_ShareConstants1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alert.setTitle("Share Your Solution!");
                alert.setMessage("Choose a method of sharing your answer:");
                alert.setPositiveButton("SMS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        final Intent shareSMSIntent = new Intent(Intent.ACTION_VIEW);

                        shareSMSIntent.putExtra("sms_body", "Problem:\n\n"+getHumanReadableText(Sunit)+"\n\nProblem Constants:\n"+assignConstants());
                        shareSMSIntent.setType("vnd.android-dir/mms-sms");
                        try
                        {
                            startActivity(shareSMSIntent);
                        }
                        catch (ActivityNotFoundException e)
                        {
                            toastSMS.show();
                        }
                    }
                });
                alert.setNeutralButton("EMAIL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        final Intent shareEmailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","", null));

                        shareEmailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Physics Tutor - Your friend has solved a physics problem for you!");
                        shareEmailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Problem:\n\n"+getHumanReadableText(Sunit)+"\n\nProblem Constants:\n"+assignConstants());
                        try
                        {
                            startActivity(shareEmailIntent);
                        }
                        catch (ActivityNotFoundException e)
                        {
                            toastEmail.show();
                        }
                    }
                });
                alert.setNegativeButton("(Cancel)", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });
                alert.create().show();
            }
        });
        */

        S_Pictures=new CustomScrollView(this,-1,-1);
        T_Display=new CustomTextView(this,14);

        Dual_Answers=new TwoDScrollView(this);

        Table_Answers=new TableLayout(this);
        Table_Answers.setPadding(5,5,5,5);
        Table_Answers.setBackgroundResource(R.drawable.border);

        Dual_Constants=new TwoDScrollView(this);
        Dual_Constants.setVisibility(8);

        Table_Constants=new TableLayout(this);
        Table_Constants.setPadding(5,5,5,5);
        Table_Constants.setBackgroundResource(R.drawable.border);

        Dual_Constants.addView(Table_Constants);

        LAA=new CustomLinearLayout(this,-2,-2,0);

        T_Step=new CustomTextView(this,14);
        T_Step.setText("Step 1:\n" + instructions[1]);

        I_Helper=new ZoomCustomImageView(this,-2,-2,helperImage[1]);
        I_Helper.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(I_Helper.fullScreen());
                seeImage=true;
            }
        });

        B_Prev=new CustomButton(this,120,50,"PREVIOUS",20);
        B_Prev.setEnabled(false);
        B_Prev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (s>1)
                {
                    if (--s==1){B_Prev.setEnabled(false);}
                    B_Next.setEnabled(true);
                    T_Step.setText("Step "+Integer.toString(s)+":\n"+instructions[s]);
                    I_Helper.setImageResource(helperImage[s]);
                }
            }
        });

        B_Next=new CustomButton(this,120,50,"NEXT",20);
        if (stotal==1){B_Next.setEnabled(false);}
        B_Next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (s<stotal)
                {
                    if (++s==stotal){B_Next.setEnabled(false);}
                    B_Prev.setEnabled(true);
                    T_Step.setText("Step "+Integer.toString(s)+":\n"+instructions[s]);
                    I_Helper.setImageResource(helperImage[s]);
                }
            }
        });

        LAA.addView(B_Prev);
        LAA.addView(B_Next);

        LBA=new CustomLinearLayout(this,-1,-2,1);
        LBAA=new CustomLinearLayout(this,-1,-2,0);

        B_Answers=new CustomButton(this,150,50,"ANSWER FIELDS",14);
        B_Answers.setEnabled(false);
        B_Answers.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                B_Answers.setEnabled(false);
                B_Constants.setEnabled(true);
                if ("VECTORS".equals(type) || "INCLINE_SIMPLE".equals(type))
                {
                    T_Display.setText("Answer Fields:\nThis problem does not have any answer fields.");
                }
                else
                {
                    T_Display.setText("Answer Fields:\nEnter the value of any variable to get the rest.");
                    Dual_Answers.setVisibility(0);
                }
                Dual_Constants.setVisibility(8);
                //B_ShareConstants.setVisibility(8);
            }
        });

        B_Constants=new CustomButton(this,150,50,"CONSTANT VALUES",14);
        B_Constants.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                B_Answers.setEnabled(true);
                B_Constants.setEnabled(false);
                T_Display.setText("Constant Values:");
                Dual_Answers.setVisibility(8);
                Dual_Constants.setVisibility(0);
                //B_ShareConstants.setVisibility(0);
            }
        });

        LBAA.addView(B_Answers);
        LBAA.addView(B_Constants);

        T_Display1=new CustomTextView(this,-2,-2,14,"Constant Values:");
        T_Display1.setVisibility(8);

        LBA.addView(LBAA);
        LBA.addView(T_Display);
        //LBA.addView(B_ShareConstants);
        LBA.addView(Dual_Answers);
        LBA.addView(Dual_Constants);

        Dual_Answers.addView(Table_Answers);

        Dual_Constants1=new TwoDScrollView(this);
        Dual_Constants1.setVisibility(8);

        //Table_Constants1=new TableLayout(this);
        //Table_Constants1.setPadding(5,5,5,5);
        //Table_Constants1.setBackgroundResource(R.drawable.border);

        //Dual_Constants1.addView(Table_Constants1);

        if ("PROJECTILE".equals(type))
        {
            makeAnswersTable(Table_Answers,new String[]{"Vy(t)","y(t)","x(t)","t","angle(t)","V(t)"},new String[]{"velocity","length","length","time","angle","velocity"});
            makeConstantsTable(Table_Constants, new String[]{"Constant g","Peak y-position","x-velocity"}, new String[]{"acceleration","length","velocity"});
            //makeConstantsTable(Table_Constants1, new String[]{"Constant g","Peak y-position","x-velocity"}, new String[]{"acceleration","length","velocity"});
        }
        else if ("VECTORS".equals(type))
        {
            makeConstantsTable(Table_Constants, new String[]{"Resultant Vector: Magnitude","Resultant Vector: Angle","Resultant Vector: X-component","Resultant Vector: Y-component","Equilibrant Vector: Magnitude","Equilibrant Vector: Angle","Equilibrant Vector: X-component","Equilibrant Vector: Y-component"}, new String[]{"force","angle","force","force","force","angle","force","force"});
            //makeConstantsTable(Table_Constants1, new String[]{"Resultant Vector: Magnitude","Resultant Vector: Angle","Resultant Vector: X-component","Resultant Vector: Y-component","Equilibrant Vector: Magnitude","Equilibrant Vector: Angle","Equilibrant Vector: X-component","Equilibrant Vector: Y-component"}, new String[]{"force","angle","force","force","force","angle","force","force"});
        }
        else if ("INCLINE_SIMPLE".equals(type))
        {
            makeConstantsTable(Table_Constants, new String[]{"Constant g","Angle of incline","Mass of object","Force of gravity parallel to incline","Normal force = -force of gravity perpendicular to incline","Maximum force of static friction","Coefficient of static friction","Force of kinetic friction","Coefficient of kinetic friction","Net acceleration along incline (if sliding downward)"}, new String[]{"acceleration","angle","mass","force","force","force","unitless","force","unitless","acceleration"});
            //makeConstantsTable(Table_Constants1, new String[]{"Constant g","Angle of incline","Mass of object","Force of gravity parallel to incline","Normal force = -force of gravity perpendicular to incline","Maximum force of static friction","Coefficient of static friction","Force of kinetic friction","Coefficient of kinetic friction","Net acceleration along incline (if sliding downward)"}, new String[]{"acceleration","angle","mass","force","force","force","unitless","force","unitless","acceleration"});
        }
        else if ("INCLINE".equals(type))
        {
            makeAnswersTable(Table_Answers,new String[]{"Vs(t)","s(t)","t"},new String[]{"velocity","length","time"});
            makeConstantsTable(Table_Constants, new String[]{"Constant g","Peak s-position","Peak time","Angle of incline"}, new String[]{"acceleration","length","time","angle"});
            //makeConstantsTable(Table_Constants1, new String[]{"Constant g","Peak s-position","Peak time","Angle of incline"}, new String[]{"acceleration","length","time","angle"});
        }
        else if ("SPRING_SIMPLE".equals(type))
        {
            makeAnswersTable(Table_Answers,new String[]{"x","F"},new String[]{"length","force"});
            makeConstantsTable(Table_Constants, new String[]{"Spring Constant","Equilibrium position"}, new String[]{"springconstant","length"});
            //makeConstantsTable(Table_Constants1, new String[]{"Spring Constant","Equilibrium position"}, new String[]{"springconstant","length"});
        }
        else if ("SPRING".equals(type))
        {
            makeAnswersTable(Table_Answers,new String[]{"t","x(t)","v(t)","a(t)","F(t)","E-Kinetic(t)","E-Potential(t)"},new String[]{"time","length","velocity","acceleration","force","energy","energy"});
            makeConstantsTable(Table_Constants, new String[]{"Mass","Spring constant","Angular frequency","Amplitude","Time shift","Phase shift","Total energy"}, new String[]{"mass","springconstant","frequency","length","time","angle","energy"});
            //makeConstantsTable(Table_Constants1, new String[]{"Mass","Spring constant","Angular frequency","Amplitude","Time shift","Phase shift","Total energy"}, new String[]{"mass","springconstant","frequency","length","time","angle","energy"});
        }



        if ("VECTORS".equals(type) || "INCLINE_SIMPLE".equals(type)) {
            T_Display.setText("Answer Fields:\nThis problem does not have any answer fields.");
            Dual_Answers.setVisibility(8);
        }
        else
        {
            T_Display.setText("Answer Fields:\nEnter the value of any variable to get the rest.");
            Dual_Answers.setVisibility(0);
        }

        B_ViewExplanation=new CustomButton(this,250,200,"EXPLANATION",32);
        B_ViewExplanation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                split=true;
                s=1;
                MAIN=L1A;
                setContentView(MAIN);
                Dual_Constants.removeAllViews();
                Dual_Constants1.removeAllViews();
                Dual_Constants1.addView(Table_Constants);

                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                {
                    MAIN.setOrientation(0);
                }
                else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                {
                    MAIN.setOrientation(1);
                }
            }
        });

        B_ViewAnswers=new CustomButton(this,250,200,"ANSWERS",32);
        B_ViewAnswers.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                split=true;
                MAIN=L1B;
                setContentView(MAIN);
                Dual_Constants.removeAllViews();
                Dual_Constants1.removeAllViews();
                Dual_Constants.addView(Table_Constants);

                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                {
                    MAIN.setOrientation(0);
                }
                else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                {
                    MAIN.setOrientation(1);
                }
            }
        });

        B_Pictures1=new CustomButton(this,150,50,"PICTURES",14);
        B_Pictures1.setEnabled(false);
        B_Pictures1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                B_Pictures1.setEnabled(false);
                B_Constants1.setEnabled(true);
                T_Display1.setVisibility(8);
                S_Pictures.setVisibility(0);
                Dual_Constants1.setVisibility(8);
                //B_ShareConstants1.setVisibility(8);
            }
        });

        B_Constants1=new CustomButton(this,150,50,"CONSTANT VALUES",14);
        B_Constants1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                B_Pictures1.setEnabled(true);
                B_Constants1.setEnabled(false);
                T_Display1.setVisibility(0);
                S_Pictures.setVisibility(8);
                Dual_Constants1.setVisibility(0);
                //B_ShareConstants1.setVisibility(0);
            }
        });

        if (answerChoice==0)
        {
            L1=new CustomLinearLayout(this,-1,-1,1);
            L1.addView(B_ViewExplanation);
            L1.addView(B_ViewAnswers);

            L1A=new CustomLinearLayout(this,-1,-1,1);
            LA=new CustomLinearLayout(this,260,260,1);

            S_Exp=new CustomScrollView(this,-1,-1);
            S_Exp.addView(T_Step);

            LA.addView(LAA);
            LA.addView(S_Exp);

            LB=new CustomLinearLayout(this,-1,-1,1);
            LBA2=new CustomLinearLayout(this,-1,-2,1);
            LBAA2=new CustomLinearLayout(this,-1,-2,0);

            LBAA2.addView(B_Pictures1);
            LBAA2.addView(B_Constants1);
            LBA2.addView(LBAA2);
            LBA2.addView(T_Display1);
            //LBA2.addView(B_ShareConstants1);

            S_Pictures.addView(I_Helper);

            LB.addView(LBA2);
            LB.addView(S_Pictures);
            LB.addView(Dual_Constants1);
            L1A.addView(LA);
            L1A.addView(LB);

            L1B=new CustomLinearLayout(this,-1,-1,1);
            L1B.addView(LBA);
        }
        else
        {
            L1=new CustomLinearLayout(this,-1,-1,1);
            LA=new CustomLinearLayout(this,260,260,1);

            S_Exp=new CustomScrollView(this,-1,-1);
            L_Exp=new CustomLinearLayout(this,-1,-1,1);

            L_Exp.addView(T_Step);
            L_Exp.addView(I_Helper);

            S_Exp.addView(L_Exp);

            LA.addView(LAA);
            LA.addView(S_Exp);

            LB=new CustomLinearLayout(this,-1,-1,1);

            LB.addView(LBA);

            L1.addView(LA);
            L1.addView(LB);
        }
    }

    private void assignVariables()
    {
        if ("PROJECTILE".equals(type))
        {
            //double xPos=var[0], xTime=var[1], yPos=var[2], yTime=var[3];
            u1=varUnit[0];
            u2=varUnit[1];
            u3=varUnit[2];
            u4=varUnit[3];
            u5=varUnit[4];
            u6=varUnit[5];
            u7=varUnit[6];
            u8=varUnit[7];
            switch (option[1]) {
                case 1:
                    //double velocity=var[4], angle=var[5], time=var[6];
                    k1=u5*Math.sin(u6)+constant_g*u7;
                    k2=u3+constant_g*u4*u4/2-k1*u4;
                    k3=u5*Math.cos(u6);
                    k4=u1-k3*u2;
                    break;
                case 2:
                    k1=u5*Math.tan(u6)+constant_g*u7;
                    k2=u3+constant_g*u4*u4/2-k1*u4;
                    k3=u5;
                    k4=u1-k3*u2;
                    break;
                case 3:
                    k1=u5+constant_g*u6;
                    k2=u3+constant_g*u4*u4/2-k1*u4;
                    k3=(-constant_g*u8+k1)/Math.tan(u7);
                    k4=u1-k3*u2;
                    break;
                case 4:
                    k1=u6+constant_g*u7;
                    k2=u3+constant_g*u4*u4/2-k1*u4;
                    k3=u5;
                    k4=u1-k3*u2;
                    break;
                case 5:
                    k1=(u7-u3+(constant_g/2)*(u8*u8-u4*u4))/(u8-u4);
                    k2=u3+constant_g*u4*u4/2-k1*u4;
                    k3=(u5-u1)/(u6-u2);
                    k4=u1-k3*u2;
                    break;
        	}
            peak=-(constant_g*(k1/constant_g)*(k1/constant_g))/2+k1*(k1/constant_g)+k2;
        }
        else if ("VECTORS".equals(type))
        {
            u1=varUnit[0];
            u2=varUnit[1];
            u3=varUnit[2];
            u4=varUnit[3];
            u5=varUnit[4];
            u6=varUnit[5];
            u7=varUnit[6];
            u8=varUnit[7];
            u9=varUnit[8];
            u10=varUnit[9];
            u11=varUnit[10];
            u12=varUnit[11];
            switch (option[0]) {
                case 1:
                    v1=u1*Math.cos(u2);
                    v2=u1*Math.sin(u2);
                    break;
                case 2:
                    v1=u1*Math.cos(u2)+u3*Math.cos(u4);
                    v2=u1*Math.sin(u2)+u3*Math.sin(u4);
                    break;
                case 3:
                    v1=u1*Math.cos(u2)+u3*Math.cos(u4)+u5*Math.cos(u6);
                    v2=u1*Math.sin(u2)+u3*Math.sin(u4)+u5*Math.sin(u6);
                    break;
                case 4:
                    v1=u1*Math.cos(u2)+u3*Math.cos(u4)+u5*Math.cos(u6)+u7*Math.cos(u8);
                    v2=u1*Math.sin(u2)+u3*Math.sin(u4)+u5*Math.sin(u6)+u7*Math.sin(u8);
                    break;
                case 5:
                    v1=u1*Math.cos(u2)+u3*Math.cos(u4)+u5*Math.cos(u6)+u7*Math.cos(u8)+u9*Math.cos(u10);
                    v2=u1*Math.sin(u2)+u3*Math.sin(u4)+u5*Math.sin(u6)+u7*Math.sin(u8)+u9*Math.sin(u10);
                    break;
                case 6:
                    v1=u1*Math.cos(u2)+u3*Math.cos(u4)+u5*Math.cos(u6)+u7*Math.cos(u8)+u9*Math.cos(u10)+u11*Math.cos(u12);
                    v2=u1*Math.sin(u2)+u3*Math.sin(u4)+u5*Math.sin(u6)+u7*Math.sin(u8)+u9*Math.sin(u10)+u11*Math.sin(u12);
                    break;
        	}
        }
        else if ("INCLINE_SIMPLE".equals(type))
        {
            u1=varUnit[0];
            u2=varUnit[1];
            u3=varUnit[2];
            u4=varUnit[3];
            switch (option[0])
            {
                case 1:
                    v1=u1;
                    v2=u2;
                    v3=u2*constant_g*Math.sin(u1);
                    v4=u2*constant_g*Math.cos(u1);
                    break;
                case 2:
                    v1=u1;
                    v2=u2/(constant_g*Math.sin(u1));
                    v3=u2;
                    v4=v2*constant_g*Math.cos(u1);
                    break;
                case 3:
                    v1=u1;
                    v2=u2/(constant_g*Math.cos(u1));
                    v3=v2*constant_g*Math.sin(u1);
                    v4=u2;
                    break;
                case 4:
                    v1=Math.asin(u2/(u1*constant_g));
                    v2=u1;
                    v3=u2;
                    v4=u1*constant_g*Math.cos(v1);
                    break;
                case 5:
                    v1=Math.acos(u2/(u1*constant_g));
                    v2=u1;
                    v3=u1*constant_g*Math.sin(v1);
                    v4=u2;
                    break;
                case 6:
                    v1=Math.atan(u1/u2);
                    v2=u1/(constant_g*Math.sin(v1));
                    v3=u1;
                    v4=u2;
                    break;
			}
        	switch (option[1])
            {
                case 1:
                    v5=v4*u3;
                    v6=u3;
                    break;
                case 2:
                    v5=u3;
                    v6=u3/v4;
                    break;
        	}
        	switch (option[2])
            {
                case 1:
                    v7=v4*u4;
                    v8=u4;
                    break;
                case 2:
                    v7=u4;
                    v8=u4/v4;
                    break;
        	}
        }
        else if ("INCLINE".equals(type))
        {
            u1=varUnit[0];
            u3=varUnit[1];
            u4=varUnit[2];
            u5=varUnit[3];
            u6=varUnit[4];
            u7=varUnit[5];
            if (option[1]==4)
            {
                u4=varUnit[3];
                u5=varUnit[2];
            }
            switch (option[1]) {
                case 1:
                    angle=u5;
                    S=u3;
                    break;
                case 2:
                    angle=u5;
                    S=u3/Math.cos(angle);
                    break;
                case 3:
                    angle=u5;
                    S=u3/Math.sin(angle);
                    break;
                case 4:
                    angle=Math.atan(u5/u3);
                    S=Math.sqrt(u3*u3+u5*u5);
                    break;
			}
        	if (u6>0)
            {
        		k1=u6+(constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))*u7;
        		peakTime=k1/(constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle));
        		k2=(constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))*peakTime;
        	}
        	else if(u6<0)
            {
        		k2=u6+(constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))*u7;
        		peakTime=k2/(constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle));
        		k1=(constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))*peakTime;
        	}
        	else
            {
        		peakTime=u7;
        		k1=(constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))*peakTime;
        		k2=(constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))*peakTime;
        	}
        	if (u4<peakTime)
            {
        		k3=S+(constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))*(u4*u4/2)-k1*u4;
        		peakS=((-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))*(peakTime*peakTime/2))+k1*peakTime+k3;
        		k4=peakS+(constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))*(peakTime*peakTime/2)-k2*peakTime;
			}
        	else if (u4>peakTime)
			{
        		k4=S+(constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))*(u4*u4/2)-k2*u4;
        		peakS=((-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))*(peakTime*peakTime/2))+k2*peakTime+k4;
        		k3=peakS+(constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))*(peakTime*peakTime/2)-k1*peakTime;
			}
        	else {
        		peakS=S;
        		k3=peakS+(constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))*(peakTime*peakTime/2)-k1*peakTime;
        		k4=peakS+(constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))*(peakTime*peakTime/2)-k2*peakTime;
        	}
        }
        else if ("SPRING_SIMPLE".equals(type))
        {
            u1=varUnit[0];
            u2=varUnit[1];
            u3=varUnit[2];
            switch (option[0])
            {
                case 1:
                    v1=u1;
                    v2=u2;
                    break;
                case 2:
                    v1=Math.abs(u2/(u3-u1));
                    v2=u3;
                    break;
            }
        }
        else if ("SPRING".equals(type))
        {
            u1=varUnit[0];
            u2=varUnit[1];
            u3=varUnit[2];
            u4=varUnit[3];
            switch (option[0])
            {
                case 1:
                    v1=u1;
                    v2=u2;
                    v3=Math.sqrt(u2/u1);
                    break;
                case 2:
                    v1=u1;
                    v2=u1*u2*u2;
                    v3=u2;
                    break;
                case 3:
                    v1=u1/(u2*u2);
                    v2=u1;
                    v3=u2;
                    break;
            }
            switch (option[2])
            {
                case 1:
                    v4=Math.sqrt(u3*u3+(u4/v3)*(u4/v3));
                    switch (option[1])
                    {
                        case 1:
                        v5=(Math.atan(u4/(v3*u3))/v3)%(2*Math.PI/v3);
                            break;
                        case 2:
                        v5=(-Math.atan((v3*u3)/u4)/v3)%(2*Math.PI/v3);
                            break;
                    }
                    v6=(v2*v4*v4)/2;
                    v7=v5*v3;
                    break;
                case 2:
                    v4=u3;
                    v5=u4%(2*Math.PI/v3);
                    v6=(v2*v4*v4)/2;
                    v7=v5*v3;
                    break;
                case 3:
                    v4=u3;
                    v7=u4%(2*Math.PI);
                    v6=(v2*v4*v4)/2;
                    v5=v7/v3;
                    break;
                case 4:
                    v4=Math.sqrt((2*u3)/v2);
                    v5=u4%(2*Math.PI/v3);
                    v6=u3;
                    v7=v5*v3;
                    break;
                case 5:
                    v4=Math.sqrt((2*u3)/v2);
                    v7=u4%(2*Math.PI);
                    v6=u3;
                    v5=v7/v3;
                    break;
            }
        }
    }

    private void assignExplanation()
    {
        if ("PROJECTILE".equals(type))
        {
            stotal=14;
        	instructions[1]="First, we will deal with the 'y' direction. We can use the y-acceleration for a typical projectile problem.";
			instructions[2]="We want an equation for y-velocity. We can get it from y-acceleration, but there is an unknown integration constant K1.";
            helperImage[1]=R.raw.helper_projectile_1;
			helperImage[2]=R.raw.helper_projectile_2;
			switch (option[1]) {
        	case 1:
			instructions[3]="We know the magnitude of the overall velocity and the angle at a certain (same) time. We can use this knowledge to obtain the y-velocity at that same time.";
			instructions[4]="Plug in values and solve for the integration constant K1.";
			instructions[5]="Next, we want an equation for y-position. We can get it from y-velocity, but there is an unknown integration constant K2.";
			instructions[6]="We know the y-position at a certain time, because we are given this in the problem.";
			instructions[7]="Plug in values and solve for the integration constant K2.";
            helperImage[3]=R.raw.helper_projectile_3_1;
			helperImage[4]=R.raw.helper_projectile_4;
			helperImage[5]=R.raw.helper_projectile_5;
			helperImage[6]=R.raw.helper_projectile_6;
			helperImage[7]=R.raw.helper_projectile_7;
			break;
        	case 2:
			instructions[3]="We know the x-velocity and the angle at a certain time. We can use this knowledge to obtain the y-velocity at that same time.";
			instructions[4]="Plug in values and solve for the integration constant K1.";
			instructions[5]="Next, we want an equation for y-position. We can get it from y-velocity, but there is an unknown integration constant K2.";
			instructions[6]="We know the y-position at a certain time, because we are given this in the problem.";
			instructions[7]="Plug in values and solve for the integration constant K2.";
            helperImage[3]=R.raw.helper_projectile_3_2;
    		helperImage[4]=R.raw.helper_projectile_4;
    		helperImage[5]=R.raw.helper_projectile_5;
			helperImage[6]=R.raw.helper_projectile_6;
			helperImage[7]=R.raw.helper_projectile_7;
			break;
        	case 3:
			instructions[3]="We know the y-velocity at a certain time, because we are given this in the problem.";
			instructions[4]="Plug in values and solve for the integration constant K1.";
			instructions[5]="Next, we want an equation for y-position. We can get it from y-velocity, but there is an unknown integration constant K2.";
			instructions[6]="We know the y-position at a certain time, because we are given this in the problem.";
			instructions[7]="Plug in values and solve for the integration constant K2.";
            helperImage[3]=R.raw.helper_projectile_3_3;
    		helperImage[4]=R.raw.helper_projectile_4;
    		helperImage[5]=R.raw.helper_projectile_5;
			helperImage[6]=R.raw.helper_projectile_6;
			helperImage[7]=R.raw.helper_projectile_7;
			break;
        	case 4:
			instructions[3]="We know the y-velocity at a certain time, because we are given this in the problem.";
			instructions[4]="Plug in values and solve for the integration constant K1.";
			instructions[5]="Next, we want an equation for y-position. We can get it from y-velocity, but there is an unknown integration constant K2.";
			instructions[6]="We know the y-position at a certain time, because we are given this in the problem.";
			instructions[7]="Plug in values and solve for the integration constant K2.";
            helperImage[3]=R.raw.helper_projectile_3_4;
    		helperImage[4]=R.raw.helper_projectile_4;
    		helperImage[5]=R.raw.helper_projectile_5;
			helperImage[6]=R.raw.helper_projectile_6;
			helperImage[7]=R.raw.helper_projectile_7;
			break;
        	case 5:
        	instructions[3]="Unfortunately, we do not have any information regarding velocity given to us in the problem.";
        	instructions[4]="To solve for K1 now, we would need to plug in the y-velocity and the time and solve for K1. However, we cannot do this because we have no way of finding the y-velocity at a certain time yet.";
        	instructions[5]="Keeping the unknown K1 in mind, we next want an equation for y-position. We can get it from y-velocity, but there is another unknown integration constant K2. Note that at this point there are two unknowns, K1 and K2.";
        	instructions[6]="We know the y-position at two times, because we are given this in the problem. As long as both times are different, we have two different pieces of information.";
        	instructions[7]="For each y-position and time we are given, plug them in and write the resulting equations. This gives us two equations for two unknowns. There are many ways to solve these equations, but perhaps the easiest is shown in the helper illustration. This will give us both K1 and K2.";
            helperImage[3]=R.raw.helper_projectile_3_5;
        	helperImage[4]=R.raw.helper_projectile_4_5;
        	helperImage[5]=R.raw.helper_projectile_5;
			helperImage[6]=R.raw.helper_projectile_6_5;
			helperImage[7]=R.raw.helper_projectile_7_5;
        	break;
			}
			instructions[8]="Now, we will deal with the 'x' direction. We can use the x-acceleration for a typical projectile problem.";
			instructions[9]="We want an equation for x-velocity. We can get it from x-acceleration, but there is an unknown integration constant K3. Still, we can tell x-velocity is constant";
            helperImage[8]=R.raw.helper_projectile_8;
			helperImage[9]=R.raw.helper_projectile_9;
			switch (option[1]) {
        	case 1:
			instructions[10]="Since x-velocity is a constant, we only need to know it at one time. We know the magnitude of the overall velocity and the angle at a certain (same) time. We can use this knowledge to obtain the x-velocity.";
			instructions[11]="Next, we want an equation for x-position. We can get it from x-velocity, but there is an unknown integration constant K4.";
			instructions[12]="We know the x-position at a certain time, because we are given this in the problem.";
			instructions[13]="Plug in values and solve for the integration constant K4.";
            helperImage[10]=R.raw.helper_projectile_10_1;
			helperImage[11]=R.raw.helper_projectile_11;
			helperImage[12]=R.raw.helper_projectile_12;
			helperImage[13]=R.raw.helper_projectile_13;
			break;
        	case 2:
			instructions[10]="Since x-velocity is a constant, we only need to know it at one time. We are given this in the problem.";
			instructions[11]="Next, we want an equation for x-position. We can get it from x-velocity, but there is an unknown integration constant K4.";
			instructions[12]="We know the x-position at a certain time, because we are given this in the problem.";
			instructions[13]="Plug in values and solve for the integration constant K4.";
            helperImage[10]=R.raw.helper_projectile_10_2;
    		helperImage[11]=R.raw.helper_projectile_11;
			helperImage[12]=R.raw.helper_projectile_12;
			helperImage[13]=R.raw.helper_projectile_13;
			break;
        	case 3:
			instructions[10]="Since x-velocity is a constant, we only need to know it at one time. We know the angle at a certain time, and can use our equation for y-velocity to find y-velocity at that same time. We can then use this knowledge to obtain the x-velocity.";
			instructions[11]="Next, we want an equation for x-position. We can get it from x-velocity, but there is an unknown integration constant K4.";
			instructions[12]="We know the x-position at a certain time, because we are given this in the problem.";
			instructions[13]="Plug in values and solve for the integration constant K4.";
            helperImage[10]=R.raw.helper_projectile_10_3;
    		helperImage[11]=R.raw.helper_projectile_11;
			helperImage[12]=R.raw.helper_projectile_12;
			helperImage[13]=R.raw.helper_projectile_13;
			break;
        	case 4:
			instructions[10]="Since x-velocity is a constant, we only need to know it at one time. We are given this in the problem.";
			instructions[11]="Next, we want an equation for x-position. We can get it from x-velocity, but there is an unknown integration constant K4.";
			instructions[12]="We know the x-position at a certain time, because we are given this in the problem.";
			instructions[13]="Plug in values and solve for the integration constant K4.";
			helperImage[10]=R.raw.helper_projectile_10_4;
    		helperImage[11]=R.raw.helper_projectile_11;
			helperImage[12]=R.raw.helper_projectile_12;
			helperImage[13]=R.raw.helper_projectile_13;
            break;
        	case 5:
            instructions[10]="Unfortunately, we do not have any information regarding velocity given to us in the problem. We could proceed as we did with y, but there is also a trick we can take advantage of. Because x-velocity is a constant, we can find it directly from the two x-position values given to us, as long as the times are different.";
            instructions[11]="Next, we want an equation for x-position. We can get it from x-velocity, but there is an unknown integration constant K4.";
			instructions[12]="We know the x-position at two times, because we are given this in the problem.";
			instructions[13]="Pick one set of corresponding x-position and time values (it doesn't matter which), and plug the values in and solve for the integration constant K4.";
            helperImage[10]=R.raw.helper_projectile_10_5;
        	helperImage[11]=R.raw.helper_projectile_11;
			helperImage[12]=R.raw.helper_projectile_12_5;
			helperImage[13]=R.raw.helper_projectile_13_5;
            break;
			}
			instructions[14]="Use the following equations to solve for what you need. You may also enter the value of one variable in the answer fields and hit 'SOLVE' to get the rest of them.";
            helperImage[14]=R.raw.helper_projectile_14;
        }
        else if ("VECTORS".equals(type))
        {
            stotal=6;
            instructions[1]="We are given a list of vectors (that obey superposition), all potentially pointing in different directions. We wish to combine them into a resultant vector. Also, we wish to find the equilibrant vector that cancels the resultant vector.";
            instructions[2]="Although it is easy to visually add vectors, mathematically it is hard if they all point in different directions.";
            instructions[3]="To help us, we can break each vector into an x-component and a y-component. All the x-components can be added into one vector, and all the y-components can be added into another vector. Then, we can add these two vectors to get the resultant vector.";
            instructions[4]="To obtain the x-component of a vector, we can simply multiply its magnitude by the cosine of the angle. Similarly, to obtain the y-component of a vector, we can simply multiply its magnitude by the sine of the angle.";
            instructions[5]="The equilibrant vector is simply the negative of the resultant vector. Adding these two vectors gives the zero vector.";
            instructions[6]="Hit 'CONSTANT VALUES' for info about the resultant vector and the equilibrant vector.";
            helperImage[1]=R.raw.helper_vectors_1;
            helperImage[2]=R.raw.helper_vectors_2;
            helperImage[3]=R.raw.helper_vectors_3;
            helperImage[4]=R.raw.helper_vectors_4;
            helperImage[5]=R.raw.helper_vectors_5;
            helperImage[6]=R.raw.helper_vectors_6;
        }
        else if ("INCLINE_SIMPLE".equals(type))
        {
        	stotal=8;
        	instructions[1]="For the simple incline problem, we wish to know the forces acting on the object (static and kinetic friction, gravity, normal force) as well as the angle of the incline and the mass of the object. The object either sits still, or slides downhill (not uphill). Using coordinates that are parallel and perpendicular (normal) to the incline makes things easier. the s,n system is simply the x,y system rotated by the angle of the incline.";
        	helperImage[1]=R.raw.helper_incline_simple_1;
        	switch (option[0]) {
        	case 1:
        	instructions[2]="We are given the angle of the incline and the mass of the object. The mass allows us to figure out the force of gravity, which acts straight down, while the angle allows us to break this force into components parallel and perpendicular (normal) to the surface of the incline.";
        	helperImage[2]=R.raw.helper_incline_simple_2_1;
        	break;
        	case 2:
            instructions[2]="We are given the angle of the incline and the force of gravity parallel to the incline. These two pieces of information allow us to find the mass of the object and the force of gravity perpendicular (normal) to the surface of the incline.";
            helperImage[2]=R.raw.helper_incline_simple_2_2;
            break;
        	case 3:
            instructions[2]="We are given the angle of the incline and the force of gravity perpendicular (normal) to the incline. These two pieces of information allow us to find the mass of the object and the force of gravity parallel to the surface of the incline.";
            helperImage[2]=R.raw.helper_incline_simple_2_3;
            break;
        	case 4:
            instructions[2]="We are given the mass of the object and the force of gravity parallel to the incline. These two pieces of information allow us to find the angle of the incline and the force of gravity perpendicular (normal) to the surface of the incline.";
            helperImage[2]=R.raw.helper_incline_simple_2_4;
            break;
        	case 5:
            instructions[2]="We are given the mass of the object and the force of gravity perpendicular (normal) to the incline. These two pieces of information allow us to find the angle of the incline and the force of gravity parallel to the surface of the incline.";
            helperImage[2]=R.raw.helper_incline_simple_2_5;
            break;
        	case 6:
            instructions[2]="We are given the force of gravity parallel and perpendicular (normal) to the incline. These two pieces of information allow us to find the angle of the incline and the mass of the object.";
            helperImage[2]=R.raw.helper_incline_simple_2_6;
            break;
        	}
        	instructions[3]="We now wish to find the normal force. We can find this using Newton's 2nd Law, and the fact that the object never leaves the surface of the incline.";
        	helperImage[3]=R.raw.helper_incline_simple_3;
        	switch (option[1]) {
        	case 1:
        	instructions[4]="Next, we would like to know the static coefficient of friction, and the maximum force that static friction can exert. We are given the static coefficient of friction, and can use this, along with the normal force, to get the maximum force of static friction.";
        	helperImage[4]=R.raw.helper_incline_simple_4_1;
            break;
        	case 2:
            instructions[4]="Next, we would like to know the static coefficient of friction, and the maximum force that static friction can exert. We are given the maximum force of static friction, and can use this, along with the normal force, to get the coefficient of static friction.";
            helperImage[4]=R.raw.helper_incline_simple_4_2;
            break;
        	}
        	switch (option[2]) {
        	case 1:
        	instructions[5]="Next, we would like to know the kinetic coefficient of friction, and the force that kinetic friction can exert. We are given the kinetic coefficient of friction, and can use this, along with the normal force, to get the force of kinetic friction.";
        	helperImage[5]=R.raw.helper_incline_simple_5_1;
            break;
        	case 2:
            instructions[5]="Next, we would like to know the kinetic coefficient of friction, and the force that kinetic friction can exert. We are given the force of kinetic friction, and can use this, along with the normal force, to get the coefficient of kinetic friction.";
            helperImage[5]=R.raw.helper_incline_simple_5_2;
            break;
        	}
        	instructions[6]="Finally, with all of the forces known to us, we can get the net force on the object. Net force and acceleration in the n direction is always 0 (since the object never leaves the ramp). In the s direction, if the object is held in place by friction, the force is also 0. If the object is sliding down the incline, then we can figure out the net force by considering gravity (parallel to incline) and kinetic friction (which points in +s, opposite of the motion (remember we do not consider the possibility of uphill motion)).";
        	instructions[7]="With this net force, we can find the acceleration of the object (if it is sliding down the incline) by using its mass.";
        	instructions[8]="The following equations summarize what you need to obtain all the information used in the simple incline problem.";
        	helperImage[6]=R.raw.helper_incline_simple_6;
        	helperImage[7]=R.raw.helper_incline_simple_7;
        	helperImage[8]=R.raw.helper_incline_simple_8;
        }
        else if ("INCLINE".equals(type))
        {
            stotal=19;
			switch (option[1]) {
			case 1:
			instructions[1]="The first piece of information we should know about an incline problem is the angle of the incline itself. We are given this in the problem.";
			helperImage[1]=R.raw.helper_incline_1;
			break;
			case 2:
			instructions[1]="The first piece of information we should know about an incline problem is the angle of the incline itself. We are given this in the problem.";
			helperImage[1]=R.raw.helper_incline_1;
			break;
			case 3:
			instructions[1]="The first piece of information we should know about an incline problem is the angle of the incline itself. We are given this in the problem.";
			helperImage[1]=R.raw.helper_incline_1;
			break;
			case 4:
			instructions[1]="The first piece of information we should know about an incline problem is the angle of the incline itself. We are given x-position and y-position at the same time, and can use them to solve for the angle of the incline.";
			helperImage[1]=R.raw.helper_incline_1_4;
			break;
			}
			instructions[2]="For an incline problem, it is easier to consider a different coordinate system than the standard x,y system. We want one basis vector to run along the incline, and the other perpendicular to the incline (i.e. rotate the x,y system by the incline angle). Since the object does not leave the incline, we only need to worry about one coordinate, s.";
			instructions[3]="While the force of friction points along the incline, the force of gravity points straight down. We can project the force of gravity onto our new basis vectors.";
			instructions[4]="The projections allow us to find the force pushing the object down the incline, and the normal force. The force of friction will be proportional to the normal force, with proportionality constant = static coefficient of friction if the object is stationary, and kinetic coefficient of friction if the object is sliding.";
			instructions[5]="With Newton's 2nd law, we can find the s-acceleration of the object. Note that friction points in the opposite direction of velocity, and thus will point in a different direction depending on whether the object is sliding uphill or downhill. Thus, there are two different equations for acceleration (one for uphill motion and one for downhill motion).";
			helperImage[2]=R.raw.helper_incline_2;
			helperImage[3]=R.raw.helper_incline_3;
			helperImage[4]=R.raw.helper_incline_4;
			helperImage[5]=R.raw.helper_incline_5;
			if (u6>0) {
			instructions[6]="We want an equation for s-velocity. We can get it from s-acceleration. Since the given velocity is positive, the object is sliding uphill at that time. Thus, we will start with the acceleration equation for uphill motion, and integrate to get velocity. Note that there is an unknown integration constant K1.";
			instructions[7]="Using our velocity, we can solve for the integration constant K1.";
			instructions[8]="The equation we have is valid for all uphill motion, but in addition, is also valid for the peak position. We can use this equation for velocity to find when the object reaches its peak position. If tp is the time the object is at its peak, then v(tp) = 0, so we can solve for the peak time, tp.";
			instructions[9]="Now, we wish to find the velocity for all times the object is sliding downhill. We can get this from the acceleration equation for downhill motion, but there is an integration constant K2.";
			instructions[10]="Since this equation for downhill velocity is also valid at the peak, we can use v(tp)=0 to solve for the integration constant K2.";
			helperImage[6]=R.raw.helper_incline_6_up;
			helperImage[7]=R.raw.helper_incline_7_up;
			helperImage[8]=R.raw.helper_incline_8_up;
			helperImage[9]=R.raw.helper_incline_9_up;
			helperImage[10]=R.raw.helper_incline_10_up;
			}
			else if(u6<0) {
			instructions[6]="We want an equation for s-velocity. We can get it from s-acceleration. Since the given velocity is negative, the object is sliding downhill at that time. Thus, we will start with the acceleration equation that is valid when the object slides downhill, and integrate to get velocity. Note that there is an unknown integration constant K2.";
			instructions[7]="Using our velocity, we can solve for the integration constant K2.";
			instructions[8]="The equation we have is valid for all downhill motion, but in addition, is also valid for the peak position. We can use this equation for velocity to find when the object reaches its peak position. If tp is the time the object is at its peak, then v(tp) = 0, so we can solve for the peak time, tp.";
			instructions[9]="Now, we wish to find the velocity for all times the object is sliding uphill. We can get this from the acceleration equation for uphill motion, but there is an integration constant K1.";
			instructions[10]="Since this equation for uphill velocity is also valid at the peak, we can use v(tp)=0 to solve for the integration constant K1.";
			helperImage[6]=R.raw.helper_incline_6_down;
			helperImage[7]=R.raw.helper_incline_7_down;
			helperImage[8]=R.raw.helper_incline_8_down;
			helperImage[9]=R.raw.helper_incline_9_down;
			helperImage[10]=R.raw.helper_incline_10_down;
			}
			else {
			instructions[6]="We want an equation for s-velocity. We can get it from s-acceleration. Since the given velocity is 0, the object is exactly at the peak of its motion at that time. Because velocity must be a continuous quantity, both the uphill and downhill regions intersect at the peak, and have v(t*)=0 as a condition.";
			instructions[7]="First, let's deal with uphill motion. We will start with the acceleration equation for uphill motion, and integrate to get velocity. Note that there is an unknown integration constant K1.";
			instructions[8]="Plug in v(t*)=0 to solve for K1";
			instructions[9]="Next, let's deal with downhill motion. We will start with the acceleration equation for downhill motion, and integrate to get velocity. Note that there is an unknown integration constant K2.";
			instructions[10]="Plug in v(t*)=0 to solve for K2";
			helperImage[6]=R.raw.helper_incline_6_peak;
			helperImage[7]=R.raw.helper_incline_7_peak;
			helperImage[8]=R.raw.helper_incline_8_peak;
			helperImage[9]=R.raw.helper_incline_9_peak;
			helperImage[10]=R.raw.helper_incline_10_peak;
			}
			instructions[11]="We now know the velocity of the object at all times.";
			helperImage[11]=R.raw.helper_incline_11;
			switch (option[1]) {
			case 1:
			instructions[12]="We are given the s-position at a certain time.";
			helperImage[12]=R.raw.helper_incline_12_1;
			break;
			case 2:
			instructions[12]="We know the x-position at a certain time, and the angle of the incline. We can use this to get the s-position at that same time.";
			helperImage[12]=R.raw.helper_incline_12_2;
			break;
			case 3:
			instructions[12]="We know the y-position at a certain time, and the angle of the incline. We can use this to get the s-position at that same time.";
			helperImage[12]=R.raw.helper_incline_12_3;
			break;
			case 4:
			instructions[12]="We know the x-position and y-position at a certain time. We can use this to get the s-position at that same time.";
			helperImage[12]=R.raw.helper_incline_12_4;
			break;
			}
			if (u4<peakTime)
			{
			instructions[13]="Next, we want an equation for s-position. We can get it from s-velocity. Since the position is given at a time before the peak time (the uphill motion region), we will start with the uphill velocity and integrate to get position. Note that there is an unknown integration constant K3.";
			instructions[14]="Using our s-position, we can solve for the integration constant K3.";
			instructions[15]="The equation we have is valid for all uphill motion, but in addition, is also valid for the peak position. We can use this equation for position and plug in the peak time to find the peak position sp.";
			instructions[16]="Now, we wish to find the s-position for all times the object is sliding downhill. We can get this from the s-velocity equation for downhill motion, but there is an integration constant K4.";
			instructions[17]="Since this equation for downhill position is also valid at the peak, we can use s(tp)=sp to solve for the integration constant K4.";
			helperImage[13]=R.raw.helper_incline_13_up;
			helperImage[14]=R.raw.helper_incline_14_up;
			helperImage[15]=R.raw.helper_incline_15_up;
			helperImage[16]=R.raw.helper_incline_16_up;
			helperImage[17]=R.raw.helper_incline_17_up;
			}
			else if (u4>peakTime)
			{
			instructions[13]="Next, we want an equation for s-position. We can get it from s-velocity. Since the position is given at a time after the peak time (the downhill motion region), we will start with the downhill velocity and integrate to get position. Note that there is an unknown integration constant K4.";
			instructions[14]="Using our s-position, we can solve for the integration constant K4.";
			instructions[15]="The equation we have is valid for all downhill motion, but in addition, is also valid for the peak position. We can use this equation for position and plug in the peak time to find the peak position sp.";
			instructions[16]="Now, we wish to find the s-position for all times the object is sliding uphill. We can get this from the s-velocity equation for uphill motion, but there is an integration constant K3.";
			instructions[17]="Since this equation for uphill position is also valid at the peak, we can use s(tp)=sp to solve for the integration constant K3.";
			helperImage[13]=R.raw.helper_incline_13_down;
			helperImage[14]=R.raw.helper_incline_14_down;
			helperImage[15]=R.raw.helper_incline_15_down;
			helperImage[16]=R.raw.helper_incline_16_down;
			helperImage[17]=R.raw.helper_incline_17_down;
			}
			else {
			instructions[13]="Next, we want an equation for s-position. We can get it from s-velocity. Since the position is given at the peak time, and position must be a continuous quantity, both the uphill and downhill regions intersect at the peak, and have s(tp)=sp as a condition.";
			instructions[14]="First, let's deal with uphill motion. We will start with the velocity equation for uphill motion, and integrate to get position. Note that there is an unknown integration constant K3.";
			instructions[15]="Plug in s(tp)=sp to solve for K3";
			instructions[16]="Next, let's deal with downhill motion. We will start with the velocity equation for downhill motion, and integrate to get position. Note that there is an unknown integration constant K4.";
			instructions[17]="Plug in s(tp)=sp to solve for K4";
			helperImage[13]=R.raw.helper_incline_13_peak;
			helperImage[14]=R.raw.helper_incline_14_peak;
			helperImage[15]=R.raw.helper_incline_15_peak;
			helperImage[16]=R.raw.helper_incline_16_peak;
			helperImage[17]=R.raw.helper_incline_17_peak;
			}
			instructions[18]="We now know the s-position of the object at all times";
			instructions[19]="Use the following equations to solve for what you need. You may also enter the value of one variable in the answer fields and hit 'SOLVE' to get the rest of them.";
			helperImage[18]=R.raw.helper_incline_18;
			helperImage[19]=R.raw.helper_incline_19;
        }
        else if ("SPRING_SIMPLE".equals(type))
        {
            stotal=4;
        	instructions[1]="We are given the equilibrium position of the spring. This is the position of the mass at which there is no force acting on the mass. We define displacement d in terms of the actual position x and the equilibrium position xe.";
            helperImage[1]=R.raw.helper_spring_simple_1;
			switch (option[0]) {
        	case 1:
            instructions[2]="We are given the spring constant of our spring, which is a material property of the spring and assumed not to change as the spring stretches or compresses.";
            helperImage[2]=R.raw.helper_spring_simple_2_1;
            break;
            case 2:
            instructions[2]="We are given the magnitude of the force on the mass while it is at a particular position. We can use this information along with Hooke's law to solve for the spring constant. The spring constant is a material property of the spring and assumed not to change as the spring stretches or compresses.";
            helperImage[2]=R.raw.helper_spring_simple_2_2;
            break;
            }
            instructions[3]="With the help of the spring constant, we can use Hooke's law to relate the displacement of the mass to the force acting on it.";
            instructions[4]="Use the following equations to solve for what you need. You may also enter the value of one variable in the answer fields and hit 'SOLVE' to get the rest of them.";
            helperImage[3]=R.raw.helper_spring_simple_3;
            helperImage[4]=R.raw.helper_spring_simple_4;
        }
        else if ("SPRING".equals(type))
        {
            stotal=8;
            instructions[1]="We wish to solve for the motion of a mass attached to an ideal spring. We can use Newton's 2nd law and Hooke's law to find a differential equation we can use to solve for the displacement x.";
            helperImage[1]=R.raw.helper_spring_1;
            switch (option[1]) {
        	case 1:
            instructions[2]="There are a few ways to write the general solution to this equation. Based on preference, we will use the cosine function. The displacement can be written in terms of the amplitude A, a phase shift Phi, the angular frequency w, and time t.";
            helperImage[2]=R.raw.helper_spring_2_1;
            break;
            case 2:
            instructions[2]="There are a few ways to write the general solution to this equation. Based on preference, we will use the sine function. The displacement can be written in terms of the amplitude A, a phase shift Phi, the angular frequency w, and time t.";
            helperImage[2]=R.raw.helper_spring_2_2;
            break;
            }
            switch (option[0]) {
            case 1:
            instructions[3]="We are given the mass and the spring constant. We can use those two values to solve for the angular frequency.";
            helperImage[3]=R.raw.helper_spring_3_1;
            break;
        	case 2:
            instructions[3]="We are given the angular frequency of the oscillation. Also, we have the mass, so we can solve for the spring constant.";
            helperImage[3]=R.raw.helper_spring_3_2;
            break;
            case 3:
            instructions[3]="We are given the angular frequency of the oscillation. Also, we have the spring constant, so we can solve for the mass.";
            helperImage[3]=R.raw.helper_spring_3_3;
            break;
            }
            switch (option[2]) {
            case 1:
            instructions[4]="We are given the displacement and velocity of the mass at t=0. We can differentiate displacement to get velocity. Then, we plug in the initial conditions to obtain two equations. We can solve these equations to get the amplitude and phase shift. Also, we can solve for the time offset.";
                switch (option[1]) {
                case 1:
                helperImage[4]=R.raw.helper_spring_4_1_1;
                break;
                case 2:
                helperImage[4]=R.raw.helper_spring_4_1_2;
                break;
                }
            break;
            case 2:
            instructions[4]="We are given the amplitude and the time offset of the oscillation. We can solve for the phase shift.";
            helperImage[4]=R.raw.helper_spring_4_2;
            break;
            case 3:
            instructions[4]="We are given the amplitude and the phase shift of the oscillation. We can solve for the time offset.";
            helperImage[4]=R.raw.helper_spring_4_3;
            break;
            case 4:
            instructions[4]="We are given the total energy and the time offset of the oscillation (from which we can get the phase shift), but we still need the amplitude. The potential energy of a spring will be the most (and equal to the total energy) when the spring is stretched the most. We can use this knowledge to solve for the amplitude.";
                switch (option[1]) {
                case 1:
                helperImage[4]=R.raw.helper_spring_4_4_1;
                break;
                case 2:
                helperImage[4]=R.raw.helper_spring_4_4_2;
                break;
                }
            break;
            case 5:
            instructions[4]="We are given the total energy and the phase shift of the oscillation (from which we can get the time offset), but we still need the amplitude. The potential energy of a spring will be the most (and equal to the total energy) when the spring is stretched the most. We can use this knowledge to solve for the amplitude.";
                switch (option[1]) {
                case 1:
                helperImage[4]=R.raw.helper_spring_4_5_1;
                break;
                case 2:
                helperImage[4]=R.raw.helper_spring_4_5_2;
                break;
                }
            break;
            }
            instructions[5]="We now know everything to write the displacement. We can differentiate displacement to get a function for velocity and for acceleration.";
            instructions[6]="We can use the acceleration to write down the force in terms of time.";
            instructions[7]="We can get equations for the kinetic, potential, and total energy of the system. Note that because there is no friction, the total energy is constant";
            instructions[8]="Use the following equations to solve for what you need. You may also enter the value of one variable in the answer fields and hit 'SOLVE' to get the rest of them.";
            switch (option[1]) {
            case 1:
            helperImage[5]=R.raw.helper_spring_5_1;
            helperImage[6]=R.raw.helper_spring_6_1;
            helperImage[7]=R.raw.helper_spring_7_1;
            helperImage[8]=R.raw.helper_spring_8_1;
            break;
            case 2:
            helperImage[5]=R.raw.helper_spring_5_2;
            helperImage[6]=R.raw.helper_spring_6_2;
            helperImage[7]=R.raw.helper_spring_7_2;
            helperImage[8]=R.raw.helper_spring_8_2;
            break;
            }
        }
    }

    private String assignConstants()
    {
        String s="";
        for (int i=0;i<overallnames.length;i++)
        {
            s+="\n"+overallnames[i]+"=["+constantSolver(i)+"] "+SunitA3[i];
        }
        return s;
    }

    private void makeConstantsTable(final TableLayout Table_Constants, final String names[], final String units[])
    {
        final CustomTextView T_Constant[]=new CustomTextView[names.length];
        final CustomButton update[]=new CustomButton[names.length];
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final CustomButton B_ShareConstants=new CustomButton(this,70,50,"SHARE",14,R.color.myBlue);

        B_ShareConstants.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alert.setTitle("Share Your Solution!");
                alert.setMessage("Choose a method of sharing your answer:");
                alert.setPositiveButton("SMS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        final Intent shareSMSIntent = new Intent(Intent.ACTION_VIEW);

                        shareSMSIntent.putExtra("sms_body", "Problem:\n\n"+getHumanReadableText(Sunit)+"\n\nProblem Constants:\n"+assignConstants());
                        shareSMSIntent.setType("vnd.android-dir/mms-sms");
                        try
                        {
                            startActivity(shareSMSIntent);
                        }
                        catch (ActivityNotFoundException e)
                        {
                            toastSMS.show();
                        }
                    }
                });
                alert.setNeutralButton("EMAIL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        final Intent shareEmailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","", null));

                        shareEmailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Physics Tutor - Your friend has solved a physics problem for you!");
                        shareEmailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Problem:\n\n"+getHumanReadableText(Sunit)+"\n\nProblem Constants:\n"+assignConstants());
                        try
                        {
                            startActivity(shareEmailIntent);
                        }
                        catch (ActivityNotFoundException e)
                        {
                            toastEmail.show();
                        }
                    }
                });
                alert.setNegativeButton("(Cancel)", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });
                alert.create().show();
            }
        });
        overallnames=names;
        for(int c=-1;c<names.length;c++)
        {
            final CustomTableRow TR=new CustomTableRow(this);
            if (c==-1)
            {
                TR.addView(B_ShareConstants);
            }
            else
            {
                final int i=c;
                T_Constant[i]=new CustomTextView(this,-2,50,14,names[i]+"="+constantSolver(i));
                T_Constant[i].setGravity(3|16);

                TR.addView(T_Constant[i]);

                if ("unitless".equals(units[i]))
                {
                    TR.addView(new CustomTextView(this,-2,-2,14,"(unitless)"));
                }
                else
                {
                    //final CustomSpinner SC=makeSpinner2(i, units[i], factorA3, SunitA3,T_Constant[i]);


                    final String unitText[][]={{"m","cm","mm","km","in","ft"},{"s","ms"},{"m/s","cm/s","mm/s","km/s","in/s","ft/s"},{"radians","degrees"},{"kg","g"},{"N","lb"},{"J"},{"Hz"},{"m/s^2"},{"N/m"}};
                    final double unitFactor[][]={{1,0.01,0.001,1000,0.0254,0.3048},{1,0.001},{1,0.01,0.001,1000,0.0254,0.3048},{1,0.01745329},{1,0.001},{1,4.44822162},{1},{1},{1},{1}};
                    final int unitDefault[]={defaultLength,defaultTime,defaultVelocity,defaultAngle,defaultMass,defaultForce,defaultEnergy,defaultFrequency,defaultAcceleration,defaultSpringConstant};


                    final CustomSpinner SC=new CustomSpinner(this,unitText[unitStoN(units[i])],"Unit ("+units[i]+"):");
                    SC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        public void onNothingSelected(AdapterView<?> parent) {}
                        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                            factorA3[i]=unitFactor[unitStoN(units[i])][pos];
                            SunitA3[i]=unitText[unitStoN(units[i])][pos];
                            T_Constant[i].setText(names[i]+"="+constantSolver(i));
                        }
                    });
                    SC.setSelection(unitDefault[unitStoN(units[i])]);










                    //update[i]=new CustomButton(this,70,50,"UPDATE",14,R.color.myYellow);
                    //update[i].setOnClickListener(new View.OnClickListener() {
                        //public void onClick(View v) {
                            //T_Constant[i].setText(names[i]+"="+constantSolver(i));
                        //}
                    //});
                    TR.addView(SC);
                    //TR.addView(update[i]);
                }
            }
            Table_Constants.addView(TR);
        }
    }

    private void makeAnswersTable(final TableLayout Table_Answers, final String names[], final String units[])
    {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        if ("VECTORS".equals(type) || "INCLINE_SIMPLE".equals(type))
        {
            final CustomTableRow TR=new CustomTableRow(this);
            final CustomButton B_ShareConstants=new CustomButton(this,70,50,"SHARE",14,R.color.myBlue);

            B_ShareConstants.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    alert.setTitle("Share Your Solution!");
                    alert.setMessage("Choose a method of sharing your answer:");
                    alert.setPositiveButton("SMS", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            final Intent shareSMSIntent = new Intent(Intent.ACTION_VIEW);

                            shareSMSIntent.putExtra("sms_body", "Problem:\n\n"+getHumanReadableText(Sunit)+"\n\nProblem Constants:\n"+assignConstants());
                            shareSMSIntent.setType("vnd.android-dir/mms-sms");
                            try
                            {
                                startActivity(shareSMSIntent);
                            }
                            catch (ActivityNotFoundException e)
                            {
                                toastSMS.show();
                            }
                        }
                    });
                    alert.setNeutralButton("EMAIL", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            final Intent shareEmailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","", null));

                            shareEmailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Physics Tutor - Your friend has solved a physics problem for you!");
                            shareEmailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Problem:\n\n"+getHumanReadableText(Sunit)+"\n\nProblem Constants:\n"+assignConstants());
                            try
                            {
                                startActivity(shareEmailIntent);
                            }
                            catch (ActivityNotFoundException e)
                            {
                                toastEmail.show();
                            }
                        }
                    });
                    alert.setNegativeButton("(Cancel)", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                        }
                    });
                    alert.create().show();
                }
            });

            TR.addView(B_ShareConstants);
            Table_Answers.addView(TR);
        }
        else
        {
            final CustomButton RB=new CustomButton(this,70,50,"RESET",14,R.color.myRed);
            final CustomButton B_ShareAll=new CustomButton(this,70,50,"SHARE ALL",12,R.color.myBlue);
            final CustomButton B_Solve[]=new CustomButton[names.length], B_Share[]=new CustomButton[names.length];
            final CustomTextView T_varAnswer[]=new CustomTextView[names.length];
            //final String SunitA1[]=new String[names.length], SunitA2[]=new String[names.length];
            //final double factorA1[]={1.0,1.0,1.0,1.0,1.0,1.0,1.0}, factorA2[]={1.0,1.0,1.0,1.0,1.0,1.0,1.0};
            //final double factorA1[]={1.0,1.0,1.0,1.0,1.0,1.0,1.0};

            for(int c=-1;c<names.length;c++)
            {
                final int i=c;

                final CustomTableRow TR=new CustomTableRow(this);

                if (i<0)
                {
                    final CustomTextView TV1=new CustomTextView(this,-2,-2,14,"Variable  ");
                    final CustomTextView TV2=new CustomTextView(this,-2,-2,14,"Input Value  ");
                    final CustomTextView TV3=new CustomTextView(this,-2,-2,14,"Input Units  ");

                    RB.setEnabled(false);
                    RB.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            enableDisableView(Table_Answers,true);
                            v.setEnabled(false);
                            B_ShareAll.setEnabled(false);
                            B_Solve[solveChoice].getBackground().clearColorFilter();
                            for (int c=0;c<names.length;c++)
                            {
                                T_varAnswer[c].setText("");
                                B_Share[c].setEnabled(false);
                            }
                        }
                    });

                    final CustomTextView TV5=new CustomTextView(this,-2,-2,14,"Output Value  ");
                    final CustomTextView TV6=new CustomTextView(this,-2,-2,14,"Output Units");

                    B_ShareAll.setEnabled(false);
                    B_ShareAll.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            alert.setTitle("Share Your Solution!");
                            alert.setMessage("Choose a method of sharing your answer:");
                            alert.setPositiveButton("SMS", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    final Intent shareSMSIntent = new Intent(Intent.ACTION_VIEW);
                                    String info="";

                                    for (int c=0;c<names.length;c++)
                                    {
                                        if(c!=solveChoice){info+="\n"+names[c]+"=["+equationSolver(input,solveChoice,c)+"] "+SunitA2[c];}
                                    }
                                    shareSMSIntent.putExtra("sms_body", "Problem:\n\n"+getHumanReadableText(Sunit)+"\n\nIf "+names[solveChoice]+"="+inputRaw+" "+SunitA1[solveChoice]+", what are the other variables?\n\nSolution:\n\nIf "+names[solveChoice]+"="+inputRaw+" "+SunitA1[solveChoice]+":\n"+info+"\n\nProblem Constants:\n"+assignConstants());
                                    shareSMSIntent.setType("vnd.android-dir/mms-sms");
                                    try
                                    {
                                        startActivity(shareSMSIntent);
                                    }
                                    catch (ActivityNotFoundException e)
                                    {
                                        toastSMS.show();
                                    }
                                }
                            });
                            alert.setNeutralButton("EMAIL", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    final Intent shareEmailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","", null));
                                    String info="";

                                    for (int c=0;c<names.length;c++)
                                    {
                                        if(c!=solveChoice){info+="\n"+names[c]+"=["+equationSolver(input,solveChoice,c)+"] "+SunitA2[c];}
                                    }
                                    shareEmailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Physics Tutor - Your friend has solved a physics problem for you!");
                                    shareEmailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Problem:\n\n"+getHumanReadableText(Sunit)+"\n\nIf "+names[solveChoice]+"="+inputRaw+" "+SunitA1[solveChoice]+", what are the other variables?\n\nSolution:\n\nIf "+names[solveChoice]+"="+inputRaw+" "+SunitA1[solveChoice]+":\n"+info+"\n\nProblem Constants:\n"+assignConstants());
                                    try
                                    {
                                        startActivity(shareEmailIntent);
                                    }
                                    catch (ActivityNotFoundException e)
                                    {
                                        toastEmail.show();
                                    }
                                }
                            });
                            alert.setNegativeButton("(Cancel)", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.dismiss();
                                }
                            });
                            alert.create().show();
                        }
                    });

                    TR.addView(TV1);
                    TR.addView(TV2);
                    TR.addView(TV3);
                    TR.addView(RB);
                    TR.addView(TV5);
                    TR.addView(TV6);
                    TR.addView(B_ShareAll);
                }
                else
                {
                    final CustomTextView varName=new CustomTextView(this,-2,-2,14,names[i]+":");

                    E_varInput[i]=new CustomEditText(this,80,48,14);

                    if(SWITCH)
                    {
                    E_varInput[i].setOnTouchListener(new View.OnTouchListener() {
                        public boolean onTouch(View v, MotionEvent event) {
                            for (int r=0;r<names.length;r++)
                            {
                                E_varInput[r].setFocusableInTouchMode(true);
                            }
                            return false;
                        }
                    });
                    }

                    //final CustomSpinner SL=makeSpinner(i, units[i], factorA1, SunitChoiceA1);
                    final CustomSpinner SL=makeSpinner(i, units[i], factorA1, SunitA1);
                    if(SWITCH)
                    {
                    SL.setOnTouchListener(new View.OnTouchListener() {
                        public boolean onTouch(View v, MotionEvent event) {
                            for (int r=0;r<names.length;r++)
                            {
                                E_varInput[r].setFocusableInTouchMode(false);
                            }
                            for (int r=0;r<names.length;r++)
                            {
                                E_varInput[r].clearFocus();
                            }
                            return false;
                        }
                    });
                    }

                    B_Solve[i]=new CustomButton(this,70,50,"SOLVE",14);
                    if(SWITCH)
                    {
                    B_Solve[i].setOnTouchListener(new View.OnTouchListener() {
                        public boolean onTouch(View v, MotionEvent event) {
                            for (int r=0;r<names.length;r++)
                            {
                                E_varInput[r].setFocusableInTouchMode(false);
                            }
                            for (int r=0;r<names.length;r++)
                            {
                                E_varInput[r].clearFocus();
                            }
                            return false;
                        }
                    });
                    }
                    B_Solve[i].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            try
                            {
                                input=factorA1[i]*Double.valueOf(E_varInput[i].getText().toString());
                                inputRaw=Double.valueOf(E_varInput[i].getText().toString());
                                solveChoice=i;
                                B_Solve[i].setColorFilter(R.color.myYellow);
                                enableDisableView(Table_Answers,false);
                                RB.setEnabled(true);
                                B_ShareAll.setEnabled(true);
                                for(int j=0;j<names.length;j++)
                                {
                                    T_varAnswer[j].setText(equationSolver(input,i,j));
                                    if(i!=j){B_Share[j].setEnabled(true);}
                                }
                            }
                            catch (NumberFormatException e)
                            {
                            }
                        }
                    });

                    T_varAnswer[i]=new CustomTextView(this);

                    //CustomSpinner SR=makeSpinner(i, units[i], factorA2, SunitChoiceA2);
                    CustomSpinner SR=makeSpinner(i, units[i], factorA2, SunitA2);
                    if(SWITCH)
                    {
                    SR.setOnTouchListener(new View.OnTouchListener() {
                        public boolean onTouch(View v, MotionEvent event) {
                            for (int r=0;r<names.length;r++)
                            {
                                E_varInput[r].setFocusableInTouchMode(false);
                            }
                            for (int r=0;r<names.length;r++)
                            {
                                E_varInput[r].clearFocus();
                            }
                            return false;
                        }
                    });
                    }

                    B_Share[i]=new CustomButton(this,70,50,"SHARE",14,R.color.myBlue);
                    B_Share[i].setEnabled(false);
                    B_Share[i].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            alert.setTitle("Share Your Solution!");
                            alert.setMessage("Choose a method of sharing your answer:");
                            alert.setPositiveButton("SMS", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    final Intent shareSMSIntent = new Intent(Intent.ACTION_VIEW);
                                    shareSMSIntent.putExtra("sms_body", "Problem:\n\n"+getHumanReadableText(Sunit)+"\n\nIf "+names[solveChoice]+"="+inputRaw+" "+SunitA1[solveChoice]+", what is "+names[i]+"?\n\nSolution:\n\nIf "+names[solveChoice]+"="+inputRaw+" "+SunitA1[solveChoice]+", then "+names[i]+"=["+equationSolver(input,solveChoice,i)+"] "+SunitA2[i]+"\n\nProblem Constants:\n"+assignConstants());
                                    shareSMSIntent.setType("vnd.android-dir/mms-sms");
                                    try
                                    {
                                        startActivity(shareSMSIntent);
                                    }
                                    catch (ActivityNotFoundException e)
                                    {
                                        toastSMS.show();
                                    }
                                }
                            });
                            alert.setNeutralButton("EMAIL", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    final Intent shareEmailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","", null));
                                    shareEmailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Physics Tutor - Your friend has solved a physics problem for you!");
                                    shareEmailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Problem:\n\n"+getHumanReadableText(Sunit)+"\n\nIf "+names[solveChoice]+"="+inputRaw+" "+SunitA1[solveChoice]+", what is "+names[i]+"?\n\nSolution:\n\nIf "+names[solveChoice]+"="+inputRaw+" "+SunitA1[solveChoice]+", then "+names[i]+"=["+equationSolver(input,solveChoice,i)+"] "+SunitA2[i]+"\n\nProblem Constants:\n"+assignConstants());
                                    try
                                    {
                                        startActivity(shareEmailIntent);
                                    }
                                    catch (ActivityNotFoundException e)
                                    {
                                        toastEmail.show();
                                    }
                                }
                            });
                            alert.setNegativeButton("(Cancel)", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.dismiss();
                                }
                            });
                            alert.create().show();
                        }
                    });

                    TR.addView(varName);
                    TR.addView(E_varInput[i]);
                    TR.addView(SL);
                    TR.addView(B_Solve[i]);
                    TR.addView(T_varAnswer[i]);
                    TR.addView(SR);
                    TR.addView(B_Share[i]);
                }
                Table_Answers.addView(TR);
            }
        }
    }

    private String constantSolver(final int i)
    {
        if ("PROJECTILE".equals(type))
        {
                if (i==0){return makeString2(0,constant_g);}
                else if (i==1){return makeString2(1,peak);}
                else if (i==2){return makeString2(2,k3);}
        }
        else if ("VECTORS".equals(type))
        {
            if (i==0){return makeString2(0,Math.sqrt(v1*v1+v2*v2));}
            else if (i==1){return makeString2(1,Math.atan2(v2,v1));}
            else if (i==2){return makeString2(2,v1);}
            else if (i==3){return makeString2(3,v2);}
            else if (i==4){return makeString2(4,Math.sqrt(v1*v1+v2*v2));}
            else if (i==5){return makeString2(5,Math.atan2(-v2,-v1));}
            else if (i==6){return makeString2(6,-v1);}
            else if (i==7){return makeString2(7,-v2);}
        }
        else if ("INCLINE_SIMPLE".equals(type))
        {
            if (i==0){return makeString2(0,constant_g);}
            else if (i==1){return makeString2(1,v1);}
            else if (i==2){return makeString2(2,v2);}
            else if (i==3){return makeString2(3,-v3);}
            else if (i==4){return makeString2(4,v4);}
            else if (i==5){return makeString2(5,v5);}
            else if (i==6){return makeString2(6,v6);}
            else if (i==7){return makeString2(7,v7);}
            else if (i==8){return makeString2(8,v8);}
            else if (i==9){return makeString2(9,v7-v3);}
            else if (i==10){return makeString2(10,(v7-v3)/v2);}
        }
        else if ("INCLINE".equals(type))
        {
            if (i==0){return makeString2(0,constant_g);}
            else if (i==1){return makeString2(1,peakS);}
            else if (i==2){return makeString2(2,peakTime);}
            else if (i==3){return makeString2(3,angle);}
        }
        else if ("SPRING_SIMPLE".equals(type))
        {
            if (i==0){return makeString2(0,v1);}
            else if (i==1){return makeString2(1,v2);}
        }
        else if ("SPRING".equals(type))
        {
            if (i==0){return makeString2(0,v1);}
            else if (i==1){return makeString2(1,v2);}
            else if (i==2){return makeString2(2,v3);}
            else if (i==3){return makeString2(3,v4);}
            else if (i==4){return makeString2(4,v5)+" + "+makeString2(4,2*Math.PI/v3)+"N, N is any integer";}
            else if (i==5){return makeString2(5,v7)+" + "+makeString2(5,2*Math.PI)+"N, N is any integer";}
            else if (i==6){return makeString2(6,v6);}
        }
        return "???";
    }

    private String makeString2(final int j, final double answer)
    {
        return String.format("%.4f",(1/factorA3[j])*answer);
    }

    private String equationSolver(final double input, final int i, final int j)
    {
        if ("PROJECTILE".equals(type))
        {
            if (i==0)
            {
                if (j==0){return makeString(0,input);}
                else if (j==1){return makeString(1,(-(constant_g*((k1-input)/constant_g)*((k1-input)/constant_g))/2+k1*((k1-input)/constant_g))+k2);}
                else if (j==2){return makeString(2,k3*((k1-input)/constant_g)+k4);}
                else if (j==3){return makeString(3,(k1-input)/constant_g);}
                else if (j==4){return makeString(4,Math.atan(input/k3));}
                else if (j==5){return makeString(5,Math.sqrt(k3*k3+input*input));}
            }
            else if (i==1)
            {
                if (input<=peak)
                {
                    final double temp1=(-k1+Math.sqrt((k1*k1)-4*(-constant_g/2)*(k2-input)))/(-constant_g);
                    final double temp2=(-k1-Math.sqrt((k1*k1)-4*(-constant_g/2)*(k2-input)))/(-constant_g);
                    if (j==0){return makeString(0,-constant_g*temp1+k1)+" or\n"+makeString(0,-constant_g*temp2+k1);}
                    else if (j==1){return makeString(1,input);}
                    else if (j==2){return makeString(2,k3*temp1+k4)+" or\n"+makeString(2,k3*temp2+k4);}
                    else if (j==3){return makeString(3,temp1)+" or\n"+makeString(3,temp2);}
                    else if (j==4){return makeString(4,Math.atan((-constant_g*temp1+k1)/k3))+" or\n"+makeString(4,Math.atan((-constant_g*temp2+k1)/k3));}
                    else if (j==5){return makeString(5,Math.sqrt(k3*k3+(-constant_g*temp1+k1)*(-constant_g*temp1+k1)));}
                }
                else
                {
                    if (j==0){return "";}
                    else if (j==1){return "y(t) cannot exceed peak value.";}
                    else if (j==2){return "";}
                    else if (j==3){return "";}
                    else if (j==4){return "";}
                    else if (j==5){return "";}
                }
            }
            else if (i==2)
            {
                if (j==0){return makeString(0,-constant_g*((input-k4)/k3)+k1);}
                else if (j==1){return makeString(1,-(constant_g*((input-k4)/k3)*((input-k4)/k3))/2+k1*((input-k4)/k3)+k2);}
                else if (j==2){return makeString(2,input);}
                else if (j==3){return makeString(3,(input-k4)/k3);}
                else if (j==4){return makeString(4,Math.atan((-constant_g*((input-k4)/k3)+k1)/k3));}
                else if (j==5){return makeString(5,Math.sqrt(k3*k3+(-constant_g*((input-k4)/k3)+k1)*(-constant_g*((input-k4)/k3)+k1)));}
            }
            else if (i==3)
            {
                if (j==0){return makeString(0,-constant_g*input+k1);}
                else if (j==1){return makeString(1,-(constant_g*input*input)/2+k1*input+k2);}
                else if (j==2){return makeString(2,k3*input+k4);}
                else if (j==3){return makeString(3,input);}
                else if (j==4){return makeString(4,Math.atan((-constant_g*input+k1)/k3));}
                else if (j==5){return makeString(5,Math.sqrt(k3*k3+(-constant_g*input+k1)*(-constant_g*input+k1)));}
            }
            else if (i==4)
            {
                if (j==0){return makeString(0,k3*Math.tan(input));}
                else if (j==1){return makeString(1,-(constant_g*((k1-(k3*Math.tan(input)))/constant_g)*((k1-(k3*Math.tan(input)))/constant_g))/2+k1*((k1-(k3*Math.tan(input)))/constant_g)+k2);}
                else if (j==2){return makeString(2,k3*((k1-(k3*Math.tan(input)))/constant_g)+k4);}
                else if (j==3){return makeString(3,(k1-(k3*Math.tan(input)))/constant_g);}
                else if (j==4){return makeString(4,input);}
                else if (j==5){return makeString(5,Math.sqrt(k3*k3+(k3*Math.tan(input))*(k3*Math.tan(input))));}
            }
            else if (i==5)
            {
                if (j==0){return makeString(0,Math.sqrt(input*input-k3*k3));}
                else if (j==1){return makeString(1,-(constant_g*((k1-(Math.sqrt(input*input-k3*k3)))/constant_g)*((k1-(Math.sqrt(input*input-k3*k3)))/constant_g))/2+k1*((k1-(Math.sqrt(input*input-k3*k3)))/constant_g)+k2);}
                else if (j==2){return makeString(2,k3*((k1-(Math.sqrt(input*input-k3*k3)))/constant_g)+k4);}
                else if (j==3){return makeString(3,(k1-(Math.sqrt(input*input-k3*k3)))/constant_g);}
                else if (j==4){return makeString(4,Math.acos(k3/input));}
                else if (j==5){return makeString(5,input);}
            }
        }
        else if ("INCLINE".equals(PhysicsActivity.type))
        {
            if (i==0)
            {
                if (input>0)
                {
                    final double temp1=(input-k1)/(-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle));
                    if (j==0){return makeString(0,input);}
                    else if (j==1){return makeString(1,(-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))*(temp1*temp1/2)+k1*temp1+k3);}
                    else if (j==2){return makeString(2,temp1);}
                }
                else if (input<0)
                {
                    final double temp1=(input-k2)/(-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle));
                    if (j==0){return makeString(0,input);}
                    else if (j==1){return makeString(1,(-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))*(temp1*temp1/2)+k2*temp1+k4);}
                    else if (j==2){return makeString(2,temp1);}
                }
                else
                {
                    if (j==0){return makeString(0,input);}
                    else if (j==1){return makeString(1,peakS);}
                    else if (j==2){return makeString(2,peakTime);}
                }
            }
            else if (i==1)
            {
                if (input<peakS)
                {
                    final double a1= (-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))/2;
                    final double a2= (-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))/2;
                    final double temp1=(-k1+Math.sqrt((k1*k1)-4*(a1)*(k3-input)))/(2*a1);
                    final double temp2=(-k2-Math.sqrt((k2*k2)-4*(a2)*(k4-input)))/(2*a2);
                    if (j==0){return makeString(0,2*a1*temp1+k1)+" or\n"+makeString(0,2*a2*temp2+k2);}
                    else if (j==1){return makeString(1,input);}
                    else if (j==2){return makeString(2,temp1)+" or\n"+makeString(2,temp2);}
                }
                else if (input==peakS)
                {
                    if (j==0){return "0";}
                    else if (j==1){return makeString(1,input);}
                    else if (j==2){return makeString(2,peakTime);}
                }
                else
                {
                    if (j==0){return "";}
                    else if (j==1){return "s(t) cannot exceed peak value.";}
                    else if (j==2){return "";}
                }
            }
            else if (i==2)
            {
                if (input<peakTime)
                {
                    if (j==0){return makeString(0,(-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))*input+k1);}
                    else if (j==1){return makeString(1,(-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))*(input*input/2)+k1*input+k3);}
                    else if (j==2){return makeString(2,input);}
                }
                else if (input>peakTime)
                {
                    if (j==0){return makeString(0,(-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))*input+k2);}
                    else if (j==1){return makeString(1,(-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))*(input*input/2)+k2*input+k4);}
                    else if (j==2){return makeString(2,input);}
                }
                else
                {
                    if (j==0){return "0";}
                    else if (j==1){return makeString(1,peakS);}
                    else if (j==2){return makeString(2,input);}
                }
            }
        }
        else if ("SPRING_SIMPLE".equals(PhysicsActivity.type))
        {
            if (i==0)
            {
                if (j==0){return makeString(0,input);}
                else if (j==1){return makeString(1,v1*(v2-input));}
            }
            else if (i==1)
            {
                if (j==0){return makeString(0,v2-(input/v1));}
                else if (j==1){return makeString(1,input);}
            }
        }
        else if ("SPRING".equals(PhysicsActivity.type))
        {
            if (i==0)
            {
                if (option[1]==1)
                {
                    if (j==0){return makeString(0,input)+"\n+"+makeString(0,2*Math.PI/v3)+"N, N is any integer";}
                    else if (j==1){return makeString(1,v4*Math.cos(v3*(input-v5)));}
                    else if (j==2){return makeString(2,-v4*v3*Math.sin(v3*(input-v5)));}
                    else if (j==3){return makeString(3,-v4*v3*v3*Math.cos(v3*(input-v5)));}
                    else if (j==4){return makeString(4,-v1*v4*v3*v3*Math.cos(v3*(input-v5)));}
                    else if (j==5){return makeString(5,0.5*v1*v4*v3*Math.sin(v3*(input-v5))*v4*v3*Math.sin(v3*(input-v5)));}
                    else if (j==6){return makeString(6,0.5*v2*v4*Math.cos(v3*(input-v5))*v4*Math.cos(v3*(input-v5)));}
                }
                else if (option[1]==2)
                {
                    if (j==0){return makeString(0,input)+"\n+"+makeString(0,2*Math.PI/v3)+"N, N is any integer";}
                    else if (j==1){return makeString(1,v4*Math.sin(v3*(input-v5)));}
                    else if (j==2){return makeString(2,v4*v3*Math.cos(v3*(input-v5)));}
                    else if (j==3){return makeString(3,-v4*v3*v3*Math.sin(v3*(input-v5)));}
                    else if (j==4){return makeString(4,-v1*v4*v3*v3*Math.sin(v3*(input-v5)));}
                    else if (j==5){return makeString(5,0.5*v1*v4*v3*Math.cos(v3*(input-v5))*v4*v3*Math.cos(v3*(input-v5)));}
                    else if (j==6){return makeString(6,0.5*v2*v4*Math.sin(v3*(input-v5))*v4*Math.sin(v3*(input-v5)));}
                }
            }
            else if (i==1)
            {
                if (option[1]==1)
                {
                    final double temp1=v5-Math.acos(input/v4)/v3;
                    final double temp2=v5+Math.acos(input/v4)/v3;
                    if (j==0){return "("+makeString(0,temp1)+" or "+makeString(0,temp2)+")\n+"+makeString(0,2*Math.PI/v3)+"N, N is any integer";}
                    else if (j==1){return makeString(1,input);}
                    else if (j==2){return makeString(2,-v4*v3*Math.sin(v3*(temp1-v5)))+" or "+makeString(2,-v4*v3*Math.sin(v3*(temp2-v5)));}
                    else if (j==3){return makeString(3,-v3*v3*input);}
                    else if (j==4){return makeString(4,-v2*input);}
                    else if (j==5){return makeString(5,0.5*v1*v4*v3*Math.sin(v3*(temp1-v5))*v4*v3*Math.sin(v3*(temp1-v5)));}
                    else if (j==6){return makeString(6,0.5*v2*input*input);}
                }
                else if (option[1]==2)
                {
                    final double temp1=v5+Math.asin(input/v4)/v3;
                    final double temp2=v5-Math.asin(input/v4)/v3+Math.PI/v3;
                    if (j==0){return "("+makeString(0,temp1)+" or "+makeString(0,temp2)+")\n+"+makeString(0,2*Math.PI/v3)+"N, N is any integer";}
                    else if (j==1){return makeString(1,input);}
                    else if (j==2){return makeString(2,v4*v3*Math.cos(v3*(temp1-v5)))+" or "+makeString(2,v4*v3*Math.cos(v3*(temp2-v5)));}
                    else if (j==3){return makeString(3,-v3*v3*input);}
                    else if (j==4){return makeString(4,-v2*input);}
                    else if (j==5){return makeString(5,0.5*v1*v4*v3*Math.cos(v3*(temp1-v5))*v4*v3*Math.cos(v3*(temp1-v5)));}
                    else if (j==6){return makeString(6,0.5*v2*input*input);}
                }
            }
            else if (i==2)
            {
                if (option[1]==1)
                {
                    final double temp1=v5-Math.asin(input/(v4*v3))/v3;
                    final double temp2=v5+Math.asin(input/(v4*v3))/v3+Math.PI/v3;
                    if (j==0){return "("+makeString(0,temp1)+" or "+makeString(0,temp2)+")\n+"+makeString(0,2*Math.PI/v3)+"N, N is any integer";}
                    else if (j==1){return makeString(1,v4*Math.cos(v3*(temp1-v5)))+" or "+makeString(1,v4*Math.cos(v3*(temp2-v5)));}
                    else if (j==2){return makeString(2,input);}
                    else if (j==3){return makeString(3,-v3*v3*v4*Math.cos(v3*(temp1-v5)))+" or "+makeString(3,-v3*v3*v4*Math.cos(v3*(temp2-v5)));}
                    else if (j==4){return makeString(4,-v1*v3*v3*v4*Math.cos(v3*(temp1-v5)))+" or "+makeString(4,-v1*v3*v3*v4*Math.cos(v3*(temp2-v5)));}
                    else if (j==5){return makeString(5,0.5*v1*input*input);}
                    else if (j==6){return makeString(6,0.5*v2*v4*Math.cos(v3*(temp1-v5))*v4*Math.cos(v3*(temp1-v5)));}
                }
                else if (option[1]==2)
                {
                    final double temp1=v5-Math.acos(input/(v4*v3))/v3;
                    final double temp2=v5+Math.acos(input/(v4*v3))/v3;
                    if (j==0){return "("+makeString(0,temp1)+" or "+makeString(0,temp2)+")\n+"+makeString(0,2*Math.PI/v3)+"N, N is any integer";}
                    else if (j==1){return makeString(1,v4*Math.sin(v3*(temp1-v5)))+" or "+makeString(1,v4*Math.sin(v3*(temp2-v5)));}
                    else if (j==2){return makeString(2,input);}
                    else if (j==3){return makeString(3,-v3*v3*v4*Math.sin(v3*(temp1-v5)))+" or "+makeString(3,-v3*v3*v4*Math.sin(v3*(temp2-v5)));}
                    else if (j==4){return makeString(4,-v1*v3*v3*v4*Math.sin(v3*(temp1-v5)))+" or "+makeString(4,-v1*v3*v3*v4*Math.sin(v3*(temp2-v5)));}
                    else if (j==5){return makeString(5,0.5*v1*input*input);}
                    else if (j==6){return makeString(6,0.5*v2*v4*Math.sin(v3*(temp1-v5))*v4*Math.sin(v3*(temp1-v5)));}
                }
            }
            else if (i==3)
            {
                if (option[1]==1)
                {
                    final double temp1=v5-Math.acos(-input/(v4*v3*v3))/v3;
                    final double temp2=v5+Math.acos(-input/(v4*v3*v3))/v3;
                    if (j==0){return "("+makeString(0,temp1)+" or "+makeString(0,temp2)+")\n+"+makeString(0,2*Math.PI/v3)+"N, N is any integer";}
                    else if (j==1){return makeString(1,-input/(v3*v3));}
                    else if (j==2){return makeString(2,-v4*v3*Math.sin(v3*(temp1-v5)))+" or "+makeString(2,-v4*v3*Math.sin(v3*(temp2-v5)));}
                    else if (j==3){return makeString(3,input);}
                    else if (j==4){return makeString(4,v1*input);}
                    else if (j==5){return makeString(5,0.5*v1*v4*v3*Math.sin(v3*(temp1-v5))*v4*v3*Math.sin(v3*(temp1-v5)));}
                    else if (j==6){return makeString(6,0.5*v2*(input/(v3*v3))*(input/(v3*v3)));}
                }
                else if (option[1]==2)
                {
                    final double temp1=v5-Math.asin(input/(v4*v3*v3))/v3;
                    final double temp2=v5+Math.asin(input/(v4*v3*v3))/v3+Math.PI/v3;
                    if (j==0){return "("+makeString(0,temp1)+" or "+makeString(0,temp2)+")\n+"+makeString(0,2*Math.PI/v3)+"N, N is any integer";}
                    else if (j==1){return makeString(1,-input/(v3*v3));}
                    else if (j==2){return makeString(2,v4*v3*Math.cos(v3*(temp1-v5)))+" or "+makeString(2,v4*v3*Math.cos(v3*(temp2-v5)));}
                    else if (j==3){return makeString(3,input);}
                    else if (j==4){return makeString(4,v1*input);}
                    else if (j==5){return makeString(5,0.5*v1*v4*v3*Math.cos(v3*(temp1-v5))*v4*v3*Math.cos(v3*(temp1-v5)));}
                    else if (j==6){return makeString(6,0.5*v2*(input/(v3*v3))*(input/(v3*v3)));}
                }
            }
            else if (i==4)
            {
                if (option[1]==1)
                {
                    final double temp1=v5-Math.acos(-input/(v1*v4*v3*v3))/v3;
                    final double temp2=v5+Math.acos(-input/(v1*v4*v3*v3))/v3;
                    if (j==0){return "("+makeString(0,temp1)+" or "+makeString(0,temp2)+")\n+"+makeString(0,2*Math.PI/v3)+"N, N is any integer";}
                    else if (j==1){return makeString(1,-input/(v1*v3*v3));}
                    else if (j==2){return makeString(2,-v4*v3*Math.sin(v3*(temp1-v5)))+" or "+makeString(2,-v4*v3*Math.sin(v3*(temp2-v5)));}
                    else if (j==3){return makeString(3,input/v1);}
                    else if (j==4){return makeString(4,input);}
                    else if (j==5){return makeString(5,0.5*v1*v4*v3*Math.sin(v3*(temp1-v5))*v4*v3*Math.sin(v3*(temp1-v5)));}
                    else if (j==6){return makeString(6,0.5*v2*(input/(v1*v3*v3))*(input/(v1*v3*v3)));}
                }
                else if (option[1]==2)
                {
                    final double temp1=v5-Math.asin(input/(v1*v4*v3*v3))/v3;
                    final double temp2=v5+Math.asin(input/(v1*v4*v3*v3))/v3+Math.PI/v3;
                    if (j==0){return "("+makeString(0,temp1)+" or "+makeString(0,temp2)+")\n+"+makeString(0,2*Math.PI/v3)+"N, N is any integer";}
                    else if (j==1){return makeString(1,-input/(v1*v3*v3));}
                    else if (j==2){return makeString(2,v4*v3*Math.cos(v3*(temp1-v5)))+" or "+makeString(2,v4*v3*Math.cos(v3*(temp2-v5)));}
                    else if (j==3){return makeString(3,input/v1);}
                    else if (j==4){return makeString(4,input);}
                    else if (j==5){return makeString(5,0.5*v1*v4*v3*Math.cos(v3*(temp1-v5))*v4*v3*Math.cos(v3*(temp1-v5)));}
                    else if (j==6){return makeString(6,0.5*v2*(input/(v1*v3*v3))*(input/(v1*v3*v3)));}
                }
            }
            else if (i==5)
            {
                if (option[1]==1)
                {
                    final double temp1=v5-Math.asin((Math.sqrt(2*input/v1))/(v4*v3))/v3;
                    final double temp2=v5+Math.asin((Math.sqrt(2*input/v1))/(v4*v3))/v3;
                    final double temp3=v5+Math.PI/v3-Math.asin((Math.sqrt(2*input/v1))/(v4*v3))/v3;
                    final double temp4=v5+Math.PI/v3+Math.asin((Math.sqrt(2*input/v1))/(v4*v3))/v3;
                    if (j==0){return "("+makeString(0,temp1)+" or "+makeString(0,temp2)+" or "+makeString(0,temp3)+" or "+makeString(0,temp4)+")\n+"+makeString(0,2*Math.PI/v3)+"N, N is any integer";}
                    else if (j==1){return makeString(1,v4*Math.cos(v3*(temp1-v5)))+" or "+makeString(1,v4*Math.cos(v3*(temp2-v5)))+" or "+makeString(1,v4*Math.cos(v3*(temp3-v5)))+" or "+makeString(1,v4*Math.cos(v3*(temp4-v5)));}
                    else if (j==2){return makeString(2,-v4*v3*Math.sin(v3*(temp1-v5)))+" or "+makeString(2,-v4*v3*Math.sin(v3*(temp2-v5)))+" or "+makeString(2,-v4*v3*Math.sin(v3*(temp3-v5)))+" or "+makeString(2,-v4*v3*Math.sin(v3*(temp4-v5)));}
                    else if (j==3){return makeString(3,-v3*v3*v4*Math.cos(v3*(temp1-v5)))+" or "+makeString(3,-v3*v3*v4*Math.cos(v3*(temp2-v5)))+" or "+makeString(3,-v3*v3*v4*Math.cos(v3*(temp3-v5)))+" or "+makeString(3,-v3*v3*v4*Math.cos(v3*(temp4-v5)));}
                    else if (j==4){return makeString(4,-v1*v3*v3*v4*Math.cos(v3*(temp1-v5)))+" or "+makeString(4,-v1*v3*v3*v4*Math.cos(v3*(temp2-v5)))+" or "+makeString(4,-v1*v3*v3*v4*Math.cos(v3*(temp3-v5)))+" or "+makeString(4,-v1*v3*v3*v4*Math.cos(v3*(temp4-v5)));}
                    else if (j==5){return makeString(5,input);}
                    else if (j==6){return makeString(6,v6-input);}
                }
                else if (option[1]==2)
                {
                    final double temp1=v5-Math.acos(Math.sqrt(2*input/v2)/v4)/v3;
                    final double temp2=v5+Math.acos(Math.sqrt(2*input/v2)/v4)/v3;
                    final double temp3=v5+Math.PI/v3-Math.acos(Math.sqrt(2*input/v2)/v4)/v3;
                    final double temp4=v5+Math.PI/v3+Math.acos(Math.sqrt(2*input/v2)/v4)/v3;
                    if (j==0){return "("+makeString(0,temp1)+" or "+makeString(0,temp2)+" or "+makeString(0,temp3)+" or "+makeString(0,temp4)+")\n+"+makeString(0,2*Math.PI/v3)+"N, N is any integer";}
                    else if (j==1){return makeString(1,v4*Math.sin(v3 * (temp1 - v5)))+" or "+makeString(1,v4*Math.sin(v3 * (temp2 - v5)))+" or "+makeString(1,v4*Math.sin(v3 * (temp3 - v5)))+" or "+makeString(1,v4*Math.sin(v3 * (temp4 - v5)));}
                    else if (j==2){return makeString(2,v4*v3*Math.cos(v3 * (temp1 - v5)))+" or "+makeString(2,v4*v3*Math.cos(v3 * (temp2 - v5)))+" or "+makeString(2,v4*v3*Math.cos(v3 * (temp3 - v5)))+" or "+makeString(2,v4*v3*Math.cos(v3 * (temp4 - v5)));}
                    else if (j==3){return makeString(3,-v3*v3*v4*Math.sin(v3 * (temp1 - v5)))+" or "+makeString(3,-v3*v3*v4*Math.sin(v3 * (temp2 - v5)))+" or "+makeString(3,-v3*v3*v4*Math.sin(v3 * (temp3 - v5)))+" or "+makeString(3,-v3*v3*v4*Math.sin(v3 * (temp4 - v5)));}
                    else if (j==4){return makeString(4,-v1*v3*v3*v4*Math.sin(v3 * (temp1 - v5)))+" or "+makeString(4,-v1*v3*v3*v4*Math.sin(v3 * (temp2 - v5)))+" or "+makeString(4,-v1*v3*v3*v4*Math.sin(v3 * (temp3 - v5)))+" or "+makeString(4,-v1*v3*v3*v4*Math.sin(v3 * (temp4 - v5)));}
                    else if (j==5){return makeString(5,input);}
                    else if (j==6){return makeString(6,v6-input);}
                }
            }
            else if (i==6)
            {
                if (option[1]==1)
                {
                    final double temp1=v5-Math.acos(Math.sqrt(2*input/v2)/v4)/v3;
                    final double temp2=v5+Math.acos(Math.sqrt(2*input/v2)/v4)/v3;
                    final double temp3=v5+Math.PI/v3-Math.acos(Math.sqrt(2*input/v2)/v4)/v3;
                    final double temp4=v5+Math.PI/v3+Math.acos(Math.sqrt(2*input/v2)/v4)/v3;
                    if (j==0){return "("+makeString(0,temp1)+" or "+makeString(0,temp2)+" or "+makeString(0,temp3)+" or "+makeString(0,temp4)+")\n+"+makeString(0,2*Math.PI/v3)+"N, N is any integer";}
                    else if (j==1){return makeString(1,v4*Math.cos(v3*(temp1-v5)))+" or "+makeString(1,v4*Math.cos(v3*(temp2-v5)))+" or "+makeString(1,v4*Math.cos(v3*(temp3-v5)))+" or "+makeString(1,v4*Math.cos(v3*(temp4-v5)));}
                    else if (j==2){return makeString(2,-v4*v3*Math.sin(v3*(temp1-v5)))+" or "+makeString(2,-v4*v3*Math.sin(v3*(temp2-v5)))+" or "+makeString(2,-v4*v3*Math.sin(v3*(temp3-v5)))+" or "+makeString(2,-v4*v3*Math.sin(v3*(temp4-v5)));}
                    else if (j==3){return makeString(3,-v3*v3*v4*Math.cos(v3*(temp1-v5)))+" or "+makeString(3,-v3*v3*v4*Math.cos(v3*(temp2-v5)))+" or "+makeString(3,-v3*v3*v4*Math.cos(v3*(temp3-v5)))+" or "+makeString(3,-v3*v3*v4*Math.cos(v3*(temp4-v5)));}
                    else if (j==4){return makeString(4,-v1*v3*v3*v4*Math.cos(v3*(temp1-v5)))+" or "+makeString(4,-v1*v3*v3*v4*Math.cos(v3*(temp2-v5)))+" or "+makeString(4,-v1*v3*v3*v4*Math.cos(v3*(temp3-v5)))+" or "+makeString(4,-v1*v3*v3*v4*Math.cos(v3*(temp4-v5)));}
                    else if (j==5){return makeString(5,v6-input);}
                    else if (j==6){return makeString(6,input);}
                }
                else if (option[1]==2)
                {
                    final double temp1=v5-Math.asin((Math.sqrt(2*input/v1))/(v4*v3))/v3;
                    final double temp2=v5+Math.asin((Math.sqrt(2*input/v1))/(v4*v3))/v3;
                    final double temp3=v5+Math.PI/v3-Math.asin((Math.sqrt(2*input/v1))/(v4*v3))/v3;
                    final double temp4=v5+Math.PI/v3+Math.asin((Math.sqrt(2*input/v1))/(v4*v3))/v3;
                    if (j==0){return "("+makeString(0,temp1)+" or "+makeString(0,temp2)+" or "+makeString(0,temp3)+" or "+makeString(0,temp4)+")\n+"+makeString(0,2*Math.PI/v3)+"N, N is any integer";}
                    else if (j==1){return makeString(1,v4*Math.sin(v3*(temp1-v5)))+" or "+makeString(1,v4*Math.sin(v3*(temp2-v5)))+" or "+makeString(1,v4*Math.sin(v3*(temp3-v5)))+" or "+makeString(1,v4*Math.sin(v3*(temp4-v5)));}
                    else if (j==2){return makeString(2,v4*v3*Math.cos(v3*(temp1-v5)))+" or "+makeString(2,v4*v3*Math.cos(v3*(temp2-v5)))+" or "+makeString(2,v4*v3*Math.cos(v3*(temp3-v5)))+" or "+makeString(2,v4*v3*Math.cos(v3*(temp4-v5)));}
                    else if (j==3){return makeString(3,-v3*v3*v4*Math.sin(v3*(temp1-v5)))+" or "+makeString(3,-v3*v3*v4*Math.sin(v3*(temp2-v5)))+" or "+makeString(3,-v3*v3*v4*Math.sin(v3*(temp3-v5)))+" or "+makeString(3,-v3*v3*v4*Math.sin(v3*(temp4-v5)));}
                    else if (j==4){return makeString(4,-v1*v3*v3*v4*Math.sin(v3*(temp1-v5)))+" or "+makeString(4,-v1*v3*v3*v4*Math.sin(v3*(temp2-v5)))+" or "+makeString(4,-v1*v3*v3*v4*Math.sin(v3*(temp3-v5)))+" or "+makeString(4,-v1*v3*v3*v4*Math.sin(v3*(temp4-v5)));}
                    else if (j==5){return makeString(5,v6-input);}
                    else if (j==6){return makeString(6,input);}
                }
            }
        }
        return "???";
    }

    private String makeString(final int j, final double answer)
    {
        return String.format("%.4f",(1/factorA2[j])*answer);
    }
}