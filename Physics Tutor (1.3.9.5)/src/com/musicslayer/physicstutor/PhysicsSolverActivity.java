package com.musicslayer.physicstutor;

import android.content.ActivityNotFoundException;
import android.view.*;
import android.webkit.WebView;
import android.widget.*;
import com.localytics.android.*;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

public class PhysicsSolverActivity extends PhysicsActivity {
	private LocalyticsSession localyticsSession;
    Toast toastEmail, toastExit;
    Intent emailIntent;
    private WebView myWebView;

    LinearLayout L, L1, LA, LB, LAA, LBA, LBAA, L_Exp, L_Main, L_Explanation, L_Answers;
    Button B_Prev, B_Next, B_Answers, B_Answers2, B_Constants, B_Constants1, B_Constants2, B_Pictures1, B_ViewExplanation, B_ViewAnswers, B_Solve[]=new Button[10];
    ScrollView S_Exp, S_Answers, S_Answers2, S_Constants, S_Constants1, S_Constants2, S_Pictures1;
    HorizontalScrollView H_Answers, H_Constants;
    TextView T_Step, T_Display, T_Display1, T_Display2, T_Constants, T_varAnswer[]=new TextView[10];
    ImageView I_Helper;
    TableLayout Table_Answers;
    EditText E_varInput[]=new EditText[10];
    String instructions[]=new String[20], ShelperImage[]=new String[20];
    
    double k1, k2, k3, k4, temp, temp1, temp2, temp3, temp4, peak, angle, peakTime, peakS, S;
    double v1=0, v2=0, v3=0, v4=0, v5=0, v6=0, v7=0, v8=0;
    double factorA1[]={1.0,1.0,1.0,1.0,1.0,1.0,1.0}, factorA2[]={1.0,1.0,1.0,1.0,1.0,1.0,1.0};
    int stotal, s, j, r, numR=0, helperImage[]=new int[20];
    boolean seeImage=false, split, reset[]={false,false,false,false,false,false,false,false,false,false};
    
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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) 
        {
            L.setOrientation(0);
        } 
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            L.setOrientation(1);
        }
    }

    @Override
	public void onBackPressed() {
        if (seeImage)
        {
            setContentView(L);
            seeImage=false;
        }
        else
        {
            if (split)
            {
                split=false;
                L=L_Main;

                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                {
                    L.setOrientation(0);
                }
                else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                {
                    L.setOrientation(1);
                }
                setContentView(L);
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
    getMenuInflater().inflate(R.menu.menu_solver, menu);
    menu.findItem(R.id.quit_menu_item).setIntent(new Intent(this, PhysicsIntroActivity.class));
    menu.findItem(R.id.edit_inputs_menu_item).setIntent(new Intent(this, PhysicsInputActivity.class));
    menu.findItem(R.id.email_menu_item).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
        public boolean onMenuItemClick(MenuItem m) {
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ("PROJECTILE".equals(type))
        {
            setTitle("Physics Tutor - Projectile Solver");
        }
        else if ("VECTORS".equals(type))
        {
            setTitle("Physics Tutor - Vectors Solver");
        }
        else if ("INCLINE".equals(type))
        {
            setTitle("Physics Tutor - Incline Solver");
        }
        else if ("INCLINE_SIMPLE".equals(type))
        {
            setTitle("Physics Tutor - Simple Incline Solver");
        }
        else if ("SPRING_SIMPLE".equals(type))
        {
            setTitle("Physics Tutor - Simple Spring Solver");
        }
        else if ("SPRING".equals(type))
        {
            setTitle("Physics Tutor - Spring Solver");
        }

        s=1;
        assignVariables();
        assignExplanation();
        myWebView=new WebView(this);
        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.loadUrl("file:///android_res/raw/"+ShelperImage[1]+".jpg");
        if (answerChoice==0)
        {
            L_Main=makeSplitSolver();
            L_Explanation=makeSplitSolverExplanation();
            L_Answers=makeSplitSolverAnswers();
            L=L_Main;
        }
        else
        {
            L=makeSolver();
        }
        split=false;
        setContentView(L);
        this.localyticsSession = new LocalyticsSession(this.getApplicationContext(),LOCALYTICS);
		this.localyticsSession.open();
		this.localyticsSession.upload();
        loadPrefs();
        
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) 
        {
            L.setOrientation(0);
        } 
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            L.setOrientation(1);
        }

        toastEmail=Toast.makeText(getApplicationContext(), "Your device does not have an email application.", 0);
        toastExit=Toast.makeText(getApplicationContext(), "Press MENU and select QUIT to exit, or EDIT INPUTS to change the inputs.", 0);
        emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"musicslayer@gmail.com", ""});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Physics Tutor - Bug Report");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "\n\n~~~~~~~~~~~~~~~~~~~~\nType of Problem:\n"+type+"\n\nOptions:\n"+option[0]+"\n"+option[1]+"\n"+option[2]+"\n"+option[3]+"\n\nUser inputs:\n"+u1+"\n"+u2+"\n"+u3+"\n"+u4+"\n"+u5+"\n"+u6+"\n"+u7+"\n"+u8+"\n"+u9+"\n"+u10+"\n"+u11+"\n"+u12+"\n~~~~~~~~~~~~~~~~~~~~");
    }

    public LinearLayout makeSplitSolver() {
        L1=new LinearLayout(this);
        L1.setOrientation(1);
        L1.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));

        B_ViewExplanation=makeViewExplanationButton();
        B_ViewAnswers=makeViewAnswersButton();

        L1.addView(B_ViewExplanation);
        L1.addView(B_ViewAnswers);

        return L1;
    }

    public LinearLayout makeSplitSolverExplanation() {
        L1=new LinearLayout(this);
        L1.setOrientation(1);
        L1.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));

        LA=new LinearLayout(this);
        LA.setOrientation(1);
        LA.setLayoutParams(new ViewGroup.LayoutParams(dpToPx(260), dpToPx(260)));

        LAA=new LinearLayout(this);
        LAA.setOrientation(0);
        LAA.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        B_Prev=makePrevButton();
        B_Next=makeNextButton();

        LAA.addView(B_Prev);
        LAA.addView(B_Next);

        S_Exp=new ScrollView(this);

        T_Step=new TextView(this);
        T_Step.setText("Step 1:\n" + instructions[1]);
        T_Step.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        S_Exp.addView(T_Step);

        LA.addView(LAA);
        LA.addView(S_Exp);

        LB=new LinearLayout(this);
        LB.setOrientation(1);
        LB.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));

        LBA=new LinearLayout(this);
        LBA.setOrientation(1);
        LBA.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));

        LBAA=new LinearLayout(this);
        LBAA.setOrientation(0);
        LBAA.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));

        B_Pictures1=makePictures1Button();
        B_Constants1=makeConstants1Button();

        LBAA.addView(B_Pictures1);
        LBAA.addView(B_Constants1);

        T_Display1=new TextView(this);
        T_Display1.setVisibility(8);

        LBA.addView(LBAA);
        LBA.addView(T_Display1);

        S_Pictures1=new ScrollView(this);

        I_Helper=new ImageView(this);
        I_Helper.setImageResource(helperImage[1]);
        I_Helper.setAdjustViewBounds(true);
        I_Helper.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        I_Helper.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(myWebView);
                seeImage=true;
            }
        });

        S_Pictures1.addView(I_Helper);

        S_Constants1=new ScrollView(this);
        S_Constants1.setVisibility(8);
        H_Constants=new HorizontalScrollView(this);

        T_Constants=new TextView(this);
        T_Constants.setPadding(5,5,5,5);
        T_Constants.setBackgroundResource(R.drawable.border);
        assignConstants();

        H_Constants.addView(T_Constants);
        S_Constants1.addView(H_Constants);

        LB.addView(LBA);
        LB.addView(S_Pictures1);
        LB.addView(S_Constants1);

        L1.addView(LA);
        L1.addView(LB);

        return L1;
    }

    public LinearLayout makeSplitSolverAnswers() {
        L1=new LinearLayout(this);
        L1.setOrientation(1);
        L1.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));

        LB=new LinearLayout(this);
        LB.setOrientation(1);
        LB.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));

        LBA=new LinearLayout(this);
        LBA.setOrientation(1);
        LBA.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));

        LBAA=new LinearLayout(this);
        LBAA.setOrientation(0);
        LBAA.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));

        B_Answers2=makeAnswers2Button();
        B_Constants2=makeConstants2Button();

        LBAA.addView(B_Answers2);
        LBAA.addView(B_Constants2);

        T_Display2=new TextView(this);

        LBA.addView(LBAA);
        LBA.addView(T_Display2);

        S_Answers2=new ScrollView(this);
        H_Answers=new HorizontalScrollView(this);
        Table_Answers=new TableLayout(this);
        makeAnswersTable();
        Table_Answers.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
                for (r=0;r<numR;r++)
                {
				    E_varInput[r].setFocusableInTouchMode(false);
                }
                for (r=0;r<numR;r++)
                {
                    E_varInput[r].clearFocus();
                }
				return false;
			}
        });

        H_Answers.addView(Table_Answers);
        S_Answers2.addView(H_Answers);

        S_Constants2=new ScrollView(this);
        S_Constants2.setVisibility(8);
        H_Constants=new HorizontalScrollView(this);

        T_Constants=new TextView(this);
        T_Constants.setPadding(5,5,5,5);
        T_Constants.setBackgroundResource(R.drawable.border);
        assignConstants();

        H_Constants.addView(T_Constants);
        S_Constants2.addView(H_Constants);

        LB.addView(LBA);
        LB.addView(S_Answers2);
        LB.addView(S_Constants2);

        L1.addView(LB);

        if ("VECTORS".equals(type) || "INCLINE_SIMPLE".equals(type)) {
        	T_Display2.setText("Answer Fields:\nThis problem does not have any answer fields.");
        	S_Answers2.setVisibility(8);
    	}
    	else
    	{
    		T_Display2.setText("Answer Fields:\nEnter the value of any variable to get the rest.");
    		S_Answers2.setVisibility(0);
    	}

        return L1;
    }

    public Button makeAnswersButton() {
        final Button b=new Button(this);
        b.setText("ANSWER FIELDS");
        b.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if ("VECTORS".equals(type) || "INCLINE_SIMPLE".equals(type))
                {
                    T_Display.setText("Answer Fields:\nThis problem does not have any answer fields.");
                }
                else
                {
                    T_Display.setText("Answer Fields:\nEnter the value of any variable to get the rest.");
                    S_Answers.setVisibility(0);
                }
                S_Constants.setVisibility(8);
            }
        });
        return b;
    }

    public Button makeAnswers2Button() {
        final Button b=new Button(this);
        b.setText("ANSWER FIELDS");
        b.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if ("VECTORS".equals(type) || "INCLINE_SIMPLE".equals(type))
                {
                    T_Display2.setText("Answer Fields:\nThis problem does not have any answer fields.");
                }
                else
                {
                    T_Display2.setText("Answer Fields:\nEnter the value of any variable to get the rest.");
                    S_Answers2.setVisibility(0);
                }
                S_Constants2.setVisibility(8);
            }
        });
        return b;
    }

    public Button makeConstantsButton() {
        final Button b=new Button(this);
        b.setText("CONSTANT VALUES");
        b.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	T_Display.setText("Constant Values:");
            	S_Answers.setVisibility(8);
            	S_Constants.setVisibility(0);
            }
        });
        return b;
    }

    public Button makeConstants1Button() {
        final Button b=new Button(this);
        b.setText("CONSTANT VALUES");
        b.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	T_Display1.setVisibility(0);
                T_Display1.setText("Constant Values:");
            	S_Pictures1.setVisibility(8);
            	S_Constants1.setVisibility(0);
            }
        });
        return b;
    }

    public Button makeConstants2Button() {
        final Button b=new Button(this);
        b.setText("CONSTANT VALUES");
        b.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	T_Display2.setText("Constant Values:");
            	S_Answers2.setVisibility(8);
            	S_Constants2.setVisibility(0);
            }
        });
        return b;
    }

    public Button makePictures1Button() {
        final Button b=new Button(this);
        b.setText("PICTURES");
        b.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	T_Display1.setVisibility(8);
            	S_Pictures1.setVisibility(0);
            	S_Constants1.setVisibility(8);
            }
        });
        return b;
    }

    public Button makeViewExplanationButton() {
        final Button b=new Button(this);
        b.setText("EXPLANATION");
        b.setTextSize(3,14);
        b.setLayoutParams(new ViewGroup.LayoutParams(dpToPx(250), dpToPx(200)));
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                split=true;
                L=L_Explanation;

                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                {
                    L.setOrientation(0);
                }
                else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                {
                    L.setOrientation(1);
                }

                setContentView(L);
            }
        });
        return b;
    }

    public Button makeViewAnswersButton() {
        final Button b=new Button(this);
        b.setText("ANSWERS");
        b.setTextSize(3,14);
        b.setLayoutParams(new ViewGroup.LayoutParams(dpToPx(250), dpToPx(200)));
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                split=true;
                setContentView(L_Answers);
            }
        });
        return b;
    }

    public LinearLayout makeSolver() {
        L1=new LinearLayout(this);
        L1.setOrientation(1);
        L1.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));

        LA=new LinearLayout(this);
        LA.setOrientation(1);
        LA.setLayoutParams(new ViewGroup.LayoutParams(dpToPx(260), dpToPx(260)));

        LAA=new LinearLayout(this);
        LAA.setOrientation(0);
        LAA.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        B_Prev=makePrevButton();
        B_Next=makeNextButton();

        LAA.addView(B_Prev);
        LAA.addView(B_Next);

        S_Exp=new ScrollView(this);

        L_Exp=new LinearLayout(this);
        L_Exp.setOrientation(1);
        L_Exp.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));

        T_Step=new TextView(this);
        T_Step.setText("Step 1:\n" + instructions[1]);
        T_Step.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        I_Helper=new ImageView(this);
        I_Helper.setImageResource(helperImage[1]);
        I_Helper.setAdjustViewBounds(true);
        I_Helper.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        I_Helper.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(myWebView);
                seeImage=true;
            }
        });

        L_Exp.addView(T_Step);
        L_Exp.addView(I_Helper);

        S_Exp.addView(L_Exp);

        LA.addView(LAA);
        LA.addView(S_Exp);

        LB=new LinearLayout(this);
        LB.setOrientation(1);
        LB.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));

        LBA=new LinearLayout(this);
        LBA.setOrientation(1);
        LBA.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));

        LBAA=new LinearLayout(this);
        LBAA.setOrientation(0);
        LBAA.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));

        B_Answers=makeAnswersButton();
        B_Constants=makeConstantsButton();

        LBAA.addView(B_Answers);
        LBAA.addView(B_Constants);

        T_Display=new TextView(this);

        LBA.addView(LBAA);
        LBA.addView(T_Display);

        S_Answers=new ScrollView(this);
        H_Answers=new HorizontalScrollView(this);
        Table_Answers=new TableLayout(this);
        makeAnswersTable();
        Table_Answers.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
                for (r=0;r<numR;r++)
                {
				    E_varInput[r].setFocusableInTouchMode(false);
                }
                for (r=0;r<numR;r++)
                {
                    E_varInput[r].clearFocus();
                }
				return false;
			}
        });

        H_Answers.addView(Table_Answers);
        S_Answers.addView(H_Answers);

        S_Constants=new ScrollView(this);
        S_Constants.setVisibility(8);
        H_Constants=new HorizontalScrollView(this);

        T_Constants=new TextView(this);
        T_Constants.setPadding(5,5,5,5);
        T_Constants.setBackgroundResource(R.drawable.border);
        assignConstants();

        H_Constants.addView(T_Constants);
        S_Constants.addView(H_Constants);

        LB.addView(LBA);
        LB.addView(S_Answers);
        LB.addView(S_Constants);

        L1.addView(LA);
        L1.addView(LB);

        if ("VECTORS".equals(type) || "INCLINE_SIMPLE".equals(type)) {
        	T_Display.setText("Answer Fields:\nThis problem does not have any answer fields.");
        	S_Answers.setVisibility(8);
    	}
    	else
    	{
    		T_Display.setText("Answer Fields:\nEnter the value of any variable to get the rest.");
    		S_Answers.setVisibility(0);
    	}

        return L1;
    }

    public void makeAnswersTable() {
        if ("PROJECTILE".equals(type))
        {
            numR=6;
            makeAnswersRow(0,"Vy(t)","velocity");
            makeAnswersRow(1,"y(t)","length");
            makeAnswersRow(2,"x(t)","length");
            makeAnswersRow(3,"t","time");
            makeAnswersRow(4,"angle(t)","angle");
            makeAnswersRow(5,"V(t)","velocity");
            B_Solve[0].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (!reset[0])
                    {
                        j=0;
                        try
                        {
                            temp=factorA1[0]*Double.valueOf(E_varInput[0].getText().toString());
                            T_varAnswer[0].setText(String.format("%.4f",(1/factorA2[0])*temp));
                            T_varAnswer[1].setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*((k1-temp)/constant_g)*((k1-temp)/constant_g))/2+k1*((k1-temp)/constant_g))+k2));
                            T_varAnswer[2].setText(String.format("%.4f",(1/factorA2[2])*(k3*((k1-temp)/constant_g)+k4)));
                            T_varAnswer[3].setText(String.format("%.4f",(1/factorA2[3])*((k1-temp)/constant_g)));
                            T_varAnswer[4].setText(String.format("%.4f",(1/factorA2[4])*(Math.atan(temp/k3))));
                            T_varAnswer[5].setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+temp*temp))));
                            enableDisableView(Table_Answers,false);
                            enableDisableView(B_Solve[0],true);
                            B_Solve[0].setText("RESET");
                            reset[0]=true;
                        }
                        catch (NumberFormatException e)
                        {
                        }
                    }
                    else
                    {
                        enableDisableView(Table_Answers,true);
                        B_Solve[0].setText("SOLVE");
                        T_varAnswer[0].setText("");
                        T_varAnswer[1].setText("");
                        T_varAnswer[2].setText("");
                        T_varAnswer[3].setText("");
                        T_varAnswer[4].setText("");
                        T_varAnswer[5].setText("");
                        reset[0]=false;
                    }
                }
            });
            B_Solve[1].setOnClickListener(new View.OnClickListener() {
        		public void onClick(View v) {
                    if (!reset[1])
                    {
                        j=1;
                        try
                        {
                            temp=factorA1[1]*Double.valueOf(E_varInput[1].getText().toString());
                            temp1=(-k1+Math.sqrt((k1*k1)-4*(-constant_g/2)*(k2-temp)))/(-constant_g);
                            temp2=(-k1-Math.sqrt((k1*k1)-4*(-constant_g/2)*(k2-temp)))/(-constant_g);
                            if (temp<=peak)
                            {
                                T_varAnswer[0].setText(String.format("%.4f",(1/factorA2[0])*(-constant_g*temp1+k1))+" or\n"+String.format("%.4f",(1/factorA2[0])*(-constant_g*temp2+k1)));
                                T_varAnswer[1].setText(String.format("%.4f",(1/factorA2[1])*temp));
                                T_varAnswer[2].setText(String.format("%.4f",(1/factorA2[2])*(k3*temp1+k4))+" or\n"+String.format("%.4f",(1/factorA2[2])*(k3*temp2+k4)));
                                T_varAnswer[3].setText(String.format("%.4f",(1/factorA2[3])*temp1)+" or\n"+String.format("%.4f",(1/factorA2[3])*temp2));
                                T_varAnswer[4].setText(String.format("%.4f",(1/factorA2[4])*(Math.atan((-constant_g*temp1+k1)/k3)))+" or\n"+String.format("%.4f",(1/factorA2[4])*(Math.atan((-constant_g*temp2+k1)/k3))));
                                T_varAnswer[5].setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+(-constant_g*temp1+k1)*(-constant_g*temp1+k1)))));
                            }
                            else
                            {
                                T_varAnswer[0].setText("");
                                T_varAnswer[1].setText("y(t) cannot exceed peak value.");
                                T_varAnswer[2].setText("");
                                T_varAnswer[3].setText("");
                                T_varAnswer[4].setText("");
                                T_varAnswer[5].setText("");
                            }
                            enableDisableView(Table_Answers,false);
                            enableDisableView(B_Solve[1],true);
                            B_Solve[1].setText("RESET");
                            reset[1]=true;
                        }
                        catch (NumberFormatException e)
                        {
                        }
                    }
                    else
                    {
                        enableDisableView(Table_Answers,true);
                        B_Solve[1].setText("SOLVE");
                        T_varAnswer[0].setText("");
                        T_varAnswer[1].setText("");
                        T_varAnswer[2].setText("");
                        T_varAnswer[3].setText("");
                        T_varAnswer[4].setText("");
                        T_varAnswer[5].setText("");
                        reset[1]=false;
                    }
        		}
            });
        	B_Solve[2].setOnClickListener(new View.OnClickListener() {
        		public void onClick(View v) {
                    if (!reset[2])
                    {
                        j=2;
                        try
                        {
                            temp=factorA1[2]*Double.valueOf(E_varInput[2].getText().toString());
                            T_varAnswer[0].setText(String.format("%.4f",(1/factorA2[0])*(-constant_g*((temp-k4)/k3)+k1)));
                            T_varAnswer[1].setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*((temp-k4)/k3)*((temp-k4)/k3))/2+k1*((temp-k4)/k3)+k2)));
                            T_varAnswer[2].setText(String.format("%.4f",(1/factorA2[2])*temp));
                            T_varAnswer[3].setText(String.format("%.4f",(1/factorA2[3])*((temp-k4)/k3)));
                            T_varAnswer[4].setText(String.format("%.4f",(1/factorA2[4])*(Math.atan((-constant_g*((temp-k4)/k3)+k1)/k3))));
                            T_varAnswer[5].setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+(-constant_g*((temp-k4)/k3)+k1)*(-constant_g*((temp-k4)/k3)+k1)))));
                            enableDisableView(Table_Answers,false);
                            enableDisableView(B_Solve[2],true);
                            B_Solve[2].setText("RESET");
                            reset[2]=true;
                        }
                        catch (NumberFormatException e)
                        {
                        }
                    }
                    else
                    {
                        enableDisableView(Table_Answers,true);
                        B_Solve[2].setText("SOLVE");
                        T_varAnswer[0].setText("");
                        T_varAnswer[1].setText("");
                        T_varAnswer[2].setText("");
                        T_varAnswer[3].setText("");
                        T_varAnswer[4].setText("");
                        T_varAnswer[5].setText("");
                        reset[2]=false;
                    }
        		}
            });
        	B_Solve[3].setOnClickListener(new View.OnClickListener() {
        		public void onClick(View v) {
                    if (!reset[3])
                    {
                        j=3;
                        try
                        {
                            temp=factorA1[3]*Double.valueOf(E_varInput[3].getText().toString());
                            T_varAnswer[0].setText(String.format("%.4f",(1/factorA2[0])*(-constant_g*temp+k1)));
                            T_varAnswer[1].setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*temp*temp)/2+k1*temp+k2)));
                            T_varAnswer[2].setText(String.format("%.4f",(1/factorA2[2])*(k3*temp+k4)));
                            T_varAnswer[3].setText(String.format("%.4f",(1/factorA2[3])*temp));
                            T_varAnswer[4].setText(String.format("%.4f",(1/factorA2[4])*(Math.atan((-constant_g*temp+k1)/k3))));
                            T_varAnswer[5].setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+(-constant_g*temp+k1)*(-constant_g*temp+k1)))));
                            enableDisableView(Table_Answers,false);
                            enableDisableView(B_Solve[3],true);
                            B_Solve[3].setText("RESET");
                            reset[3]=true;
                        }
                        catch (NumberFormatException e)
                        {
                        }
                    }
                    else
                    {
                        enableDisableView(Table_Answers,true);
                        B_Solve[3].setText("SOLVE");
                        T_varAnswer[0].setText("");
                        T_varAnswer[1].setText("");
                        T_varAnswer[2].setText("");
                        T_varAnswer[3].setText("");
                        T_varAnswer[4].setText("");
                        T_varAnswer[5].setText("");
                        reset[3]=false;
                    }
        		}
            });
        	B_Solve[4].setOnClickListener(new View.OnClickListener() {
        		public void onClick(View v) {
                    if (!reset[4])
                    {
                        j=4;
                        try
                        {
                            temp=factorA1[4]*Double.valueOf(E_varInput[4].getText().toString());
                            T_varAnswer[0].setText(String.format("%.4f",(1/factorA2[0])*(k3*Math.tan(temp))));
                            T_varAnswer[1].setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*((k1-(k3*Math.tan(temp)))/constant_g)*((k1-(k3*Math.tan(temp)))/constant_g))/2+k1*((k1-(k3*Math.tan(temp)))/constant_g)+k2)));
                            T_varAnswer[2].setText(String.format("%.4f",(1/factorA2[2])*(k3*((k1-(k3*Math.tan(temp)))/constant_g)+k4)));
                            T_varAnswer[3].setText(String.format("%.4f",(1/factorA2[3])*((k1-(k3*Math.tan(temp)))/constant_g)));
                            T_varAnswer[4].setText(String.format("%.4f",(1/factorA2[4])*temp));
                            T_varAnswer[5].setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+(k3*Math.tan(temp))*(k3*Math.tan(temp))))));
                            enableDisableView(Table_Answers,false);
                            enableDisableView(B_Solve[4],true);
                            B_Solve[4].setText("RESET");
                            reset[4]=true;
                        }
                        catch (NumberFormatException e)
                        {
                        }
                    }
                    else
                    {
                        enableDisableView(Table_Answers,true);
                        B_Solve[4].setText("SOLVE");
                        T_varAnswer[0].setText("");
                        T_varAnswer[1].setText("");
                        T_varAnswer[2].setText("");
                        T_varAnswer[3].setText("");
                        T_varAnswer[4].setText("");
                        T_varAnswer[5].setText("");
                        reset[4]=false;
                    }
        		}
            });
        	B_Solve[5].setOnClickListener(new View.OnClickListener() {
        		public void onClick(View v) {
                    if (!reset[5])
                    {
                        j=5;
                        try
                        {
                            temp=factorA1[5]*Double.valueOf(E_varInput[5].getText().toString());
                            T_varAnswer[0].setText(String.format("%.4f",(1/factorA2[0])*(Math.sqrt(temp*temp-k3*k3))));
                            T_varAnswer[1].setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g)*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g))/2+k1*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g)+k2)));
                            T_varAnswer[2].setText(String.format("%.4f",(1/factorA2[2])*(k3*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g)+k4)));
                            T_varAnswer[3].setText(String.format("%.4f",(1/factorA2[3])*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g)));
                            T_varAnswer[4].setText(String.format("%.4f",(1/factorA2[4])*(Math.acos(k3/temp))));
                            T_varAnswer[5].setText(String.format("%.4f",(1/factorA2[5])*temp));
                            enableDisableView(Table_Answers,false);
                            enableDisableView(B_Solve[5],true);
                            B_Solve[5].setText("RESET");
                            reset[5]=true;
                        }
                        catch (NumberFormatException e)
                        {
                        }
                    }
                    else
                    {
                        enableDisableView(Table_Answers,true);
                        B_Solve[5].setText("SOLVE");
                        T_varAnswer[0].setText("");
                        T_varAnswer[1].setText("");
                        T_varAnswer[2].setText("");
                        T_varAnswer[3].setText("");
                        T_varAnswer[4].setText("");
                        T_varAnswer[5].setText("");
                        reset[5]=false;
                    }
        		}
            });
        }
        else if ("INCLINE".equals(type))
        {
            numR=3;
            makeAnswersRow(0,"Vs(t)","velocity");
            makeAnswersRow(1,"s(t)","length");
            makeAnswersRow(2,"t","time");
            B_Solve[0].setOnClickListener(new View.OnClickListener() {
        		public void onClick(View v) {
                    if (!reset[0])
                    {
                        j=0;
                        try
                        {
                            temp=factorA1[0]*Double.valueOf(E_varInput[0].getText().toString());
                            T_varAnswer[0].setText(String.format("%.4f",(1/factorA2[0])*temp));
                            if (temp>0)
                            {
                                temp1=(temp-k1)/(-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle));
                                T_varAnswer[1].setText(String.format("%.4f",(1/factorA2[1])*((-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))*(temp1*temp1/2)+k1*temp1+k3)));
                                T_varAnswer[2].setText(String.format("%.4f",(1/factorA2[2])*temp1));
                            }
                            else if (temp<0)
                            {
                                temp1=(temp-k2)/(-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle));
                                T_varAnswer[1].setText(String.format("%.4f",(1/factorA2[1])*((-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))*(temp1*temp1/2)+k2*temp1+k4)));
                                T_varAnswer[2].setText(String.format("%.4f",(1/factorA2[2])*temp1));
                            }
                            else
                            {
                                T_varAnswer[1].setText(String.format("%.4f",(1/factorA2[1])*peakS));
                                T_varAnswer[2].setText(String.format("%.4f",(1/factorA2[2])*peakTime));
                            }
                            enableDisableView(Table_Answers,false);
                            enableDisableView(B_Solve[0],true);
                            B_Solve[0].setText("RESET");
                            reset[0]=true;
                        }
                        catch (NumberFormatException e)
                        {
                        }
                    }
                    else
                    {
                        enableDisableView(Table_Answers,true);
                        B_Solve[0].setText("SOLVE");
                        T_varAnswer[0].setText("");
                        T_varAnswer[1].setText("");
                        T_varAnswer[2].setText("");
                        reset[0]=false;
                    }
        		}
            });
        	B_Solve[1].setOnClickListener(new View.OnClickListener() {
        		public void onClick(View v) {
                    if (!reset[1])
                    {
                        j=1;
                        try
                        {
                            temp=factorA1[1]*Double.valueOf(E_varInput[1].getText().toString());
                            double a1, a2;
                            a1= (-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))/2;
                            temp1=(-k1+Math.sqrt((k1*k1)-4*(a1)*(k3-temp)))/(2*a1);
                            a2= (-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))/2;
                            temp2=(-k2-Math.sqrt((k2*k2)-4*(a2)*(k4-temp)))/(2*a2);
                            if (temp<peakS)
                            {
                                T_varAnswer[0].setText(String.format("%.4f",(1/factorA2[0])*(2*a1*temp1+k1))+" or\n"+String.format("%.4f",(1/factorA2[0])*(2*a2*temp2+k2)));
                                T_varAnswer[1].setText(String.format("%.4f",(1/factorA2[1])*temp));
                                T_varAnswer[2].setText(String.format("%.4f",(1/factorA2[2])*temp1)+" or\n"+String.format("%.4f",(1/factorA2[2])*temp2));
                            }
                            else if (temp==peakS)
                            {
                                T_varAnswer[0].setText("0");
                                T_varAnswer[1].setText(String.format("%.4f",(1/factorA2[1])*temp));
                                T_varAnswer[2].setText(String.format("%.4f",(1/factorA2[2])*peakTime));
                            }
                            else
                            {
                                T_varAnswer[0].setText("");
                                T_varAnswer[1].setText("s(t) cannot exceed peak value.");
                                T_varAnswer[2].setText("");
                            }
                            enableDisableView(Table_Answers,false);
                            enableDisableView(B_Solve[1],true);
                            B_Solve[1].setText("RESET");
                            reset[1]=true;
                        }
                        catch (NumberFormatException e)
                        {
                        }
                    }
                    else
                    {
                        enableDisableView(Table_Answers,true);
                        B_Solve[1].setText("SOLVE");
                        T_varAnswer[0].setText("");
                        T_varAnswer[1].setText("");
                        T_varAnswer[2].setText("");
                        reset[1]=false;
                    }
        		}
            });
        	B_Solve[2].setOnClickListener(new View.OnClickListener() {
        		public void onClick(View v) {
                    if (!reset[2])
                    {
                        j=2;
                        try
                        {
                            temp=factorA1[2]*Double.valueOf(E_varInput[2].getText().toString());
                            if (temp<peakTime)
                            {
                                T_varAnswer[0].setText(String.format("%.4f",(1/factorA2[0])*((-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))*temp+k1)));
                                T_varAnswer[1].setText(String.format("%.4f",(1/factorA2[1])*((-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))*(temp*temp/2)+k1*temp+k3)));
                            }
                            else if (temp>peakTime)
                            {
                                T_varAnswer[0].setText(String.format("%.4f",(1/factorA2[0])*((-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))*temp+k2)));
                                T_varAnswer[1].setText(String.format("%.4f",(1/factorA2[1])*((-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))*(temp*temp/2)+k2*temp+k4)));
                            }
                            else
                            {
                                T_varAnswer[0].setText("0");
                                T_varAnswer[1].setText(String.format("%.4f",(1/factorA2[1])*peakS));
                            }
                            T_varAnswer[2].setText(String.format("%.4f",(1/factorA2[2])*temp));
                            enableDisableView(Table_Answers,false);
                            enableDisableView(B_Solve[2],true);
                            B_Solve[2].setText("RESET");
                            reset[2]=true;
                        }
                        catch (NumberFormatException e)
                        {
                        }
                    }
                    else
                    {
                        enableDisableView(Table_Answers,true);
                        B_Solve[2].setText("SOLVE");
                        T_varAnswer[0].setText("");
                        T_varAnswer[1].setText("");
                        T_varAnswer[2].setText("");
                        reset[2]=false;
                    }
        		}
            });
        }
        else if ("SPRING_SIMPLE".equals(type))
        {
            numR=2;
            makeAnswersRow(0,"x","length");
            makeAnswersRow(1,"F","force");
            B_Solve[0].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (!reset[0])
                    {
                        j=0;
                        try
                        {
                            temp=factorA1[0]*Double.valueOf(E_varInput[0].getText().toString());
                            T_varAnswer[0].setText(String.format("%.4f",(1/factorA2[0])*temp));
                            T_varAnswer[1].setText(String.format("%.4f",(1/factorA2[1])*v1*(v2-temp)));
                            enableDisableView(Table_Answers,false);
                            enableDisableView(B_Solve[0],true);
                            B_Solve[0].setText("RESET");
                            reset[0]=true;
                        }
                        catch (NumberFormatException e)
                        {
                        }
                    }
                    else
                    {
                        enableDisableView(Table_Answers,true);
                        B_Solve[0].setText("SOLVE");
                        T_varAnswer[0].setText("");
                        T_varAnswer[1].setText("");
                        reset[0]=false;
                    }
                }
            });
            B_Solve[1].setOnClickListener(new View.OnClickListener() {
        		public void onClick(View v) {
                    if (!reset[1])
                    {
                        j=1;
                        try
                        {
                            temp=factorA1[1]*Double.valueOf(E_varInput[1].getText().toString());
                            T_varAnswer[0].setText(String.format("%.4f",(1/factorA2[0])*(v2-(temp/v1))));
                            T_varAnswer[1].setText(String.format("%.4f",(1/factorA2[1])*temp));
                            enableDisableView(Table_Answers,false);
                            enableDisableView(B_Solve[1],true);
                            B_Solve[1].setText("RESET");
                            reset[1]=true;
                        }
                        catch (NumberFormatException e)
                        {
                        }
                    }
                    else
                    {
                        enableDisableView(Table_Answers,true);
                        B_Solve[1].setText("SOLVE");
                        T_varAnswer[0].setText("");
                        T_varAnswer[1].setText("");
                        reset[1]=false;
                    }
        		}
            });
        }
        else if ("SPRING".equals(type))
        {
            numR=7;
            makeAnswersRow(0,"t","time");
            makeAnswersRow(1,"x(t)","length");
            makeAnswersRow(2,"v(t)","velocity");
            makeAnswersRow(3,"a(t)","acceleration");
            makeAnswersRow(4,"F(t)","force");
            makeAnswersRow(5,"E-Kinetic(t)","energy");
            makeAnswersRow(6,"E-Potential(t)","energy");
            B_Solve[0].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (!reset[0])
                    {
                        j=0;
                        try
                        {
                            temp=factorA1[0]*Double.valueOf(E_varInput[0].getText().toString());
                            T_varAnswer[0].setText(String.format("%.4f",(1/factorA2[0])*temp)+"\n+"+String.format("%.4f",(1/factorA2[0])*2*Math.PI/v3)+"N, N is any integer");
                            T_varAnswer[1].setText(String.format("%.4f",(1/factorA2[1])*v4*Math.cos(v3*(temp-v5))));
                            T_varAnswer[2].setText(String.format("%.4f",(1/factorA2[2])*-v4*v3*Math.sin(v3*(temp-v5))));
                            T_varAnswer[3].setText(String.format("%.4f",(1/factorA2[3])*-v4*v3*v3*Math.cos(v3*(temp-v5))));
                            T_varAnswer[4].setText(String.format("%.4f",(1/factorA2[4])*-v1*v4*v3*v3*Math.cos(v3*(temp-v5))));
                            T_varAnswer[5].setText(String.format("%.4f",(1/factorA2[5])*0.5*v1*v4*v3*Math.sin(v3*(temp-v5))*v4*v3*Math.sin(v3*(temp-v5))));
                            T_varAnswer[6].setText(String.format("%.4f",(1/factorA2[6])*0.5*v2*v4*Math.cos(v3*(temp-v5))*v4*Math.cos(v3*(temp-v5))));
                            enableDisableView(Table_Answers,false);
                            enableDisableView(B_Solve[0],true);
                            B_Solve[0].setText("RESET");
                            reset[0]=true;
                        }
                        catch (NumberFormatException e)
                        {
                        }
                    }
                    else
                    {
                        enableDisableView(Table_Answers,true);
                        B_Solve[0].setText("SOLVE");
                        T_varAnswer[0].setText("");
                        T_varAnswer[1].setText("");
                        T_varAnswer[2].setText("");
                        T_varAnswer[3].setText("");
                        T_varAnswer[4].setText("");
                        T_varAnswer[5].setText("");
                        T_varAnswer[6].setText("");
                        reset[0]=false;
                    }
                }
            });
            B_Solve[1].setOnClickListener(new View.OnClickListener() {
        		public void onClick(View v) {
                    if (!reset[1])
                    {
                        j=1;
                        try
                        {
                            temp=factorA1[1]*Double.valueOf(E_varInput[1].getText().toString());
                            temp1=v5+Math.acos(temp/v4)/v3;
                            temp2=v5-Math.acos(temp/v4)/v3;
                            T_varAnswer[0].setText("("+String.format("%.4f",(1/factorA2[0])*temp1)+" or "+String.format("%.4f",(1/factorA2[0])*temp2)+")\n+"+String.format("%.4f",(1/factorA2[0])*2*Math.PI/v3)+"N, N is any integer");
                            T_varAnswer[1].setText(String.format("%.4f",(1/factorA2[1])*temp));
                            T_varAnswer[2].setText(String.format("%.4f",(1/factorA2[2])*-v4*v3*Math.sin(v3*(temp1-v5)))+" or "+String.format("%.4f",(1/factorA2[2])*-v4*v3*Math.sin(v3*(temp2-v5))));
                            T_varAnswer[3].setText(String.format("%.4f",(1/factorA2[3])*-v3*v3*temp));
                            T_varAnswer[4].setText(String.format("%.4f",(1/factorA2[4])*-v2*temp));
                            T_varAnswer[5].setText(String.format("%.4f",(1/factorA2[5])*0.5*v1*v4*v3*Math.sin(v3*(temp1-v5))*v4*v3*Math.sin(v3*(temp1-v5))));
                            T_varAnswer[6].setText(String.format("%.4f",(1/factorA2[6])*0.5*v2*temp*temp));
                            enableDisableView(Table_Answers,false);
                            enableDisableView(B_Solve[1],true);
                            B_Solve[1].setText("RESET");
                            reset[1]=true;
                        }
                        catch (NumberFormatException e)
                        {
                        }
                    }
                    else
                    {
                        enableDisableView(Table_Answers,true);
                        B_Solve[1].setText("SOLVE");
                        T_varAnswer[0].setText("");
                        T_varAnswer[1].setText("");
                        T_varAnswer[2].setText("");
                        T_varAnswer[3].setText("");
                        T_varAnswer[4].setText("");
                        T_varAnswer[5].setText("");
                        T_varAnswer[6].setText("");
                        reset[1]=false;
                    }
        		}
            });
            B_Solve[2].setOnClickListener(new View.OnClickListener() {
        		public void onClick(View v) {
                    if (!reset[2])
                    {
                        j=2;
                        try
                        {
                            temp=factorA1[2]*Double.valueOf(E_varInput[2].getText().toString());
                            temp1=v5+Math.asin(-temp/(v4*v3))/v3;
                            temp2=v5+Math.asin(temp/(v4*v3))/v3+Math.PI/v3;
                            T_varAnswer[0].setText("("+String.format("%.4f",(1/factorA2[0])*temp1)+" or "+String.format("%.4f",(1/factorA2[0])*temp2)+")\n+"+String.format("%.4f",(1/factorA2[0])*2*Math.PI/v3)+"N, N is any integer");
                            T_varAnswer[1].setText(String.format("%.4f",(1/factorA2[1])*v4*Math.cos(v3*(temp1-v5)))+" or "+String.format("%.4f",(1/factorA2[1])*v4*Math.cos(v3*(temp2-v5))));
                            T_varAnswer[2].setText(String.format("%.4f",(1/factorA2[2])*temp));
                            T_varAnswer[3].setText(String.format("%.4f",(1/factorA2[3])*-v3*v3*v4*Math.cos(v3*(temp1-v5)))+" or "+String.format("%.4f",(1/factorA2[3])*-v3*v3*v4*Math.cos(v3*(temp2-v5))));
                            T_varAnswer[4].setText(String.format("%.4f",(1/factorA2[4])*-v1*v3*v3*v4*Math.cos(v3*(temp1-v5)))+" or "+String.format("%.4f",(1/factorA2[4])*-v1*v3*v3*v4*Math.cos(v3*(temp2-v5))));
                            T_varAnswer[5].setText(String.format("%.4f",(1/factorA2[5])*0.5*v1*temp*temp));
                            T_varAnswer[6].setText(String.format("%.4f",(1/factorA2[6])*0.5*v2*v4*Math.cos(v3*(temp1-v5))*v4*Math.cos(v3*(temp1-v5))));
                            enableDisableView(Table_Answers,false);
                            enableDisableView(B_Solve[2],true);
                            B_Solve[2].setText("RESET");
                            reset[2]=true;
                        }
                        catch (NumberFormatException e)
                        {
                        }
                    }
                    else
                    {
                        enableDisableView(Table_Answers,true);
                        B_Solve[2].setText("SOLVE");
                        T_varAnswer[0].setText("");
                        T_varAnswer[1].setText("");
                        T_varAnswer[2].setText("");
                        T_varAnswer[3].setText("");
                        T_varAnswer[4].setText("");
                        T_varAnswer[5].setText("");
                        T_varAnswer[6].setText("");
                        reset[2]=false;
                    }
        		}
            });
            B_Solve[3].setOnClickListener(new View.OnClickListener() {
        		public void onClick(View v) {
                    if (!reset[3])
                    {
                        j=3;
                        try
                        {
                            temp=factorA1[3]*Double.valueOf(E_varInput[3].getText().toString());
                            temp1=v5+Math.acos(-temp/(v4*v3*v3))/v3;
                            temp2=v5-Math.acos(-temp/(v4*v3*v3))/v3;
                            T_varAnswer[0].setText("("+String.format("%.4f",(1/factorA2[0])*temp1)+" or "+String.format("%.4f",(1/factorA2[0])*temp2)+")\n+"+String.format("%.4f",(1/factorA2[0])*2*Math.PI/v3)+"N, N is any integer");
                            T_varAnswer[1].setText(String.format("%.4f",(1/factorA2[1])*(-temp/(v3*v3))));
                            T_varAnswer[2].setText(String.format("%.4f",(1/factorA2[2])*-v4*v3*Math.sin(v3*(temp1-v5)))+" or "+String.format("%.4f",(1/factorA2[2])*-v4*v3*Math.sin(v3*(temp2-v5))));
                            T_varAnswer[3].setText(String.format("%.4f",(1/factorA2[3])*temp));
                            T_varAnswer[4].setText(String.format("%.4f",(1/factorA2[4])*v1*temp));
                            T_varAnswer[5].setText(String.format("%.4f",(1/factorA2[5])*0.5*v1*v4*v3*Math.sin(v3*(temp1-v5))*v4*v3*Math.sin(v3*(temp1-v5))));
                            T_varAnswer[6].setText(String.format("%.4f",(1/factorA2[6])*0.5*v2*(temp/(v3*v3))*(temp/(v3*v3))));
                            enableDisableView(Table_Answers,false);
                            enableDisableView(B_Solve[3],true);
                            B_Solve[3].setText("RESET");
                            reset[3]=true;
                        }
                        catch (NumberFormatException e)
                        {
                        }
                    }
                    else
                    {
                        enableDisableView(Table_Answers,true);
                        B_Solve[3].setText("SOLVE");
                        T_varAnswer[0].setText("");
                        T_varAnswer[1].setText("");
                        T_varAnswer[2].setText("");
                        T_varAnswer[3].setText("");
                        T_varAnswer[4].setText("");
                        T_varAnswer[5].setText("");
                        T_varAnswer[6].setText("");
                        reset[3]=false;
                    }
        		}
            });
            B_Solve[4].setOnClickListener(new View.OnClickListener() {
        		public void onClick(View v) {
                    if (!reset[4])
                    {
                        j=4;
                        try
                        {
                            temp=factorA1[4]*Double.valueOf(E_varInput[4].getText().toString());
                            temp1=v5+Math.acos(-temp/(v1*v4*v3*v3))/v3;
                            temp2=v5-Math.acos(-temp/(v1*v4*v3*v3))/v3;
                            T_varAnswer[0].setText(String.format("("+"%.4f",(1/factorA2[0])*temp1)+" or "+String.format("%.4f",(1/factorA2[0])*temp2)+")\n+"+String.format("%.4f",(1/factorA2[0])*2*Math.PI/v3)+"N, N is any integer");
                            T_varAnswer[1].setText(String.format("%.4f",(1/factorA2[1])*(-temp/(v1*v3*v3))));
                            T_varAnswer[2].setText(String.format("%.4f",(1/factorA2[2])*-v4*v3*Math.sin(v3*(temp1-v5)))+" or "+String.format("%.4f",(1/factorA2[2])*-v4*v3*Math.sin(v3*(temp2-v5))));
                            T_varAnswer[3].setText(String.format("%.4f",(1/factorA2[3])*temp/v1));
                            T_varAnswer[4].setText(String.format("%.4f",(1/factorA2[4])*temp));
                            T_varAnswer[5].setText(String.format("%.4f",(1/factorA2[5])*0.5*v1*v4*v3*Math.sin(v3*(temp1-v5))*v4*v3*Math.sin(v3*(temp1-v5))));
                            T_varAnswer[6].setText(String.format("%.4f",(1/factorA2[6])*0.5*v2*(temp/(v1*v3*v3))*(temp/(v1*v3*v3))));
                            enableDisableView(Table_Answers,false);
                            enableDisableView(B_Solve[4],true);
                            B_Solve[4].setText("RESET");
                            reset[4]=true;
                        }
                        catch (NumberFormatException e)
                        {
                        }
                    }
                    else
                    {
                        enableDisableView(Table_Answers,true);
                        B_Solve[4].setText("SOLVE");
                        T_varAnswer[0].setText("");
                        T_varAnswer[1].setText("");
                        T_varAnswer[2].setText("");
                        T_varAnswer[3].setText("");
                        T_varAnswer[4].setText("");
                        T_varAnswer[5].setText("");
                        T_varAnswer[6].setText("");
                        reset[4]=false;
                    }
        		}
            });
            B_Solve[5].setOnClickListener(new View.OnClickListener() {
        		public void onClick(View v) {
                    if (!reset[5])
                    {
                        j=5;
                        try
                        {
                            temp=factorA1[5]*Double.valueOf(E_varInput[5].getText().toString());
                            temp1=v5+Math.asin(-(Math.sqrt(2*temp/v1))/(v4*v3))/v3;
                            temp2=v5-Math.asin(-(Math.sqrt(2*temp/v1))/(v4*v3))/v3;
                            temp3=v5+Math.PI/v3+Math.asin(-(Math.sqrt(2*temp/v1))/(v4*v3))/v3;
                            temp4=v5+Math.PI/v3-Math.asin(-(Math.sqrt(2*temp/v1))/(v4*v3))/v3;
                            T_varAnswer[0].setText("("+String.format("%.4f",(1/factorA2[0])*temp1)+" or "+String.format("%.4f",(1/factorA2[0])*temp2)+" or "+String.format("%.4f",(1/factorA2[0])*temp3)+" or "+String.format("%.4f",(1/factorA2[0])*temp4)+")\n+"+String.format("%.4f",(1/factorA2[0])*2*Math.PI/v3)+"N, N is any integer");
                            T_varAnswer[1].setText(String.format("%.4f",(1/factorA2[1])*v4*Math.cos(v3*(temp1-v5)))+" or "+String.format("%.4f",(1/factorA2[1])*v4*Math.cos(v3*(temp2-v5)))+" or "+String.format("%.4f",(1/factorA2[1])*v4*Math.cos(v3*(temp3-v5)))+" or "+String.format("%.4f",(1/factorA2[1])*v4*Math.cos(v3*(temp4-v5))));
                            T_varAnswer[2].setText(String.format("%.4f",(1/factorA2[2])*-v4*v3*Math.sin(v3*(temp1-v5)))+" or "+String.format("%.4f",(1/factorA2[2])*-v4*v3*Math.sin(v3*(temp2-v5)))+" or "+String.format("%.4f",(1/factorA2[2])*-v4*v3*Math.sin(v3*(temp3-v5)))+" or "+String.format("%.4f",(1/factorA2[2])*-v4*v3*Math.sin(v3*(temp4-v5))));
                            T_varAnswer[3].setText(String.format("%.4f",(1/factorA2[3])*-v3*v3*v4*Math.cos(v3*(temp1-v5)))+" or "+String.format("%.4f",(1/factorA2[3])*-v3*v3*v4*Math.cos(v3*(temp2-v5)))+" or "+String.format("%.4f",(1/factorA2[3])*-v3*v3*v4*Math.cos(v3*(temp3-v5)))+" or "+String.format("%.4f",(1/factorA2[3])*-v3*v3*v4*Math.cos(v3*(temp4-v5))));
                            T_varAnswer[4].setText(String.format("%.4f",(1/factorA2[4])*-v1*v3*v3*v4*Math.cos(v3*(temp1-v5)))+" or "+String.format("%.4f",(1/factorA2[4])*-v1*v3*v3*v4*Math.cos(v3*(temp2-v5)))+" or "+String.format("%.4f",(1/factorA2[4])*-v1*v3*v3*v4*Math.cos(v3*(temp3-v5)))+" or "+String.format("%.4f",(1/factorA2[4])*-v1*v3*v3*v4*Math.cos(v3*(temp4-v5))));
                            T_varAnswer[5].setText(String.format("%.4f",(1/factorA2[5])*temp));
                            T_varAnswer[6].setText(String.format("%.4f",(1/factorA2[6])*v6-temp));
                            enableDisableView(Table_Answers,false);
                            enableDisableView(B_Solve[5],true);
                            B_Solve[5].setText("RESET");
                            reset[5]=true;
                        }
                        catch (NumberFormatException e)
                        {
                        }
                    }
                    else
                    {
                        enableDisableView(Table_Answers,true);
                        B_Solve[5].setText("SOLVE");
                        T_varAnswer[0].setText("");
                        T_varAnswer[1].setText("");
                        T_varAnswer[2].setText("");
                        T_varAnswer[3].setText("");
                        T_varAnswer[4].setText("");
                        T_varAnswer[5].setText("");
                        T_varAnswer[6].setText("");
                        reset[5]=false;
                    }
        		}
            });
            B_Solve[6].setOnClickListener(new View.OnClickListener() {
        		public void onClick(View v) {
                    if (!reset[6])
                    {
                        j=6;
                        try
                        {
                            temp=factorA1[6]*Double.valueOf(E_varInput[6].getText().toString());
                            temp1=v5+Math.acos(Math.sqrt(2*temp/v2)/v4)/v3;
                            temp2=v5-Math.acos(Math.sqrt(2*temp/v2)/v4)/v3;
                            temp3=v5+Math.PI/v3+Math.acos(Math.sqrt(2*temp/v2)/v4)/v3;
                            temp4=v5+Math.PI/v3-Math.acos(Math.sqrt(2*temp/v2)/v4)/v3;
                            T_varAnswer[0].setText(String.format("("+"%.4f",(1/factorA2[0])*temp1)+" or "+String.format("%.4f",(1/factorA2[0])*temp2)+" or "+String.format("%.4f",(1/factorA2[0])*temp3)+" or "+String.format("%.4f",(1/factorA2[0])*temp4)+")\n+"+String.format("%.4f",(1/factorA2[0])*2*Math.PI/v3)+"N, N is any integer");
                            T_varAnswer[1].setText(String.format("%.4f",(1/factorA2[1])*v4*Math.cos(v3*(temp1-v5)))+" or "+String.format("%.4f",(1/factorA2[1])*v4*Math.cos(v3*(temp2-v5)))+" or "+String.format("%.4f",(1/factorA2[1])*v4*Math.cos(v3*(temp3-v5)))+" or "+String.format("%.4f",(1/factorA2[1])*v4*Math.cos(v3*(temp4-v5))));
                            T_varAnswer[2].setText(String.format("%.4f",(1/factorA2[2])*-v4*v3*Math.sin(v3*(temp1-v5)))+" or "+String.format("%.4f",(1/factorA2[2])*-v4*v3*Math.sin(v3*(temp2-v5)))+" or "+String.format("%.4f",(1/factorA2[2])*-v4*v3*Math.sin(v3*(temp3-v5)))+" or "+String.format("%.4f",(1/factorA2[2])*-v4*v3*Math.sin(v3*(temp4-v5))));
                            T_varAnswer[3].setText(String.format("%.4f",(1/factorA2[3])*-v4*v3*v3*Math.cos(v3*(temp1-v5)))+" or "+String.format("%.4f",(1/factorA2[3])*-v4*v3*v3*Math.cos(v3*(temp2-v5)))+" or "+String.format("%.4f",(1/factorA2[3])*-v4*v3*v3*Math.cos(v3*(temp3-v5)))+" or "+String.format("%.4f",(1/factorA2[3])*-v4*v3*v3*Math.cos(v3*(temp4-v5))));
                            T_varAnswer[4].setText(String.format("%.4f",(1/factorA2[4])*-v1*v4*v3*v3*Math.cos(v3*(temp1-v5)))+" or "+String.format("%.4f",(1/factorA2[4])*-v1*v4*v3*v3*Math.cos(v3*(temp2-v5)))+" or "+String.format("%.4f",(1/factorA2[4])*-v1*v4*v3*v3*Math.cos(v3*(temp3-v5)))+" or "+String.format("%.4f",(1/factorA2[4])*-v1*v4*v3*v3*Math.cos(v3*(temp4-v5))));
                            T_varAnswer[5].setText(String.format("%.4f",(1/factorA2[5])*v6-temp));
                            T_varAnswer[6].setText(String.format("%.4f",(1/factorA2[6])*temp));
                            enableDisableView(Table_Answers,false);
                            enableDisableView(B_Solve[6],true);
                            B_Solve[6].setText("RESET");
                            reset[6]=true;
                        }
                        catch (NumberFormatException e)
                        {
                        }
                    }
                    else
                    {
                        enableDisableView(Table_Answers,true);
                        B_Solve[6].setText("SOLVE");
                        T_varAnswer[0].setText("");
                        T_varAnswer[1].setText("");
                        T_varAnswer[2].setText("");
                        T_varAnswer[3].setText("");
                        T_varAnswer[4].setText("");
                        T_varAnswer[5].setText("");
                        T_varAnswer[6].setText("");
                        reset[6]=false;
                    }
        		}
            });
        }
        Table_Answers.setPadding(5,5,5,5);
        Table_Answers.setBackgroundResource(R.drawable.border);
    }

    public void makeAnswersRow(int i, String name, String unit) {
        TableRow TR=new TableRow(this);

        TextView varName=new TextView(this);
        varName.setText(name+":");

        E_varInput[i]=new EditText(this);
        E_varInput[i].setWidth(dpToPx(80));
        E_varInput[i].setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
                for (r=0;r<numR;r++)
                {
				    E_varInput[r].setFocusableInTouchMode(true);
                }
				return false;
			}
        });

        Spinner SL=makeLeftSpinner(i, unit);
        SL.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
                for (r=0;r<numR;r++)
                {
				    E_varInput[r].setFocusableInTouchMode(false);
                }
                for (r=0;r<numR;r++)
                {
                    E_varInput[r].clearFocus();
                }
				return false;
			}
        });

        B_Solve[i]=new Button(this);
        B_Solve[i].setText("SOLVE");
        B_Solve[i].setLayoutParams(new TableRow.LayoutParams(-2, -2));
        B_Solve[i].setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
                for (r=0;r<numR;r++)
                {
				    E_varInput[r].setFocusableInTouchMode(false);
                }
                for (r=0;r<numR;r++)
                {
                    E_varInput[r].clearFocus();
                }
				return false;
			}
        });

        T_varAnswer[i]=new TextView(this);

        Spinner SR=makeRightSpinner(i, unit);
        SR.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
                for (r=0;r<numR;r++)
                {
				    E_varInput[r].setFocusableInTouchMode(false);
                }
                for (r=0;r<numR;r++)
                {
                    E_varInput[r].clearFocus();
                }
				return false;
			}
        });

        TR.addView(varName);
        TR.addView(E_varInput[i]);
        TR.addView(SL);
        TR.addView(B_Solve[i]);
        TR.addView(T_varAnswer[i]);
        TR.addView(SR);

        Table_Answers.addView(TR);
    }

    public Button makePrevButton() {
        final Button b=new Button(this);
        b.setWidth(dpToPx(120));
        b.setHeight(dpToPx(50));
        b.setText("PREVIOUS");
        b.setTextSize(3,8);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (s>1)
                {
                    s--;
                    T_Step.setText("Step ".concat(Integer.toString(s)).concat(":\n").concat(instructions[s]));
                    //I_Helper.setImageResource(helperImage[s]);
                    I_Helper.setImageResource(getResources().getIdentifier(ShelperImage[s], "raw", getPackageName()));
                    myWebView.loadUrl("file:///android_res/raw/"+ShelperImage[s]+".jpg");
                }
            }
        });
        return b;
    }

    public Button makeNextButton() {
        final Button b=new Button(this);
        b.setWidth(dpToPx(120));
        b.setHeight(dpToPx(50));
        b.setText("NEXT");
        b.setTextSize(3,8);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (s<stotal)
                {
                    s++;
                    T_Step.setText("Step ".concat(Integer.toString(s)).concat(":\n").concat(instructions[s]));
                    //I_Helper.setImageResource(helperImage[s]);
                    I_Helper.setImageResource(getResources().getIdentifier(ShelperImage[s], "raw", getPackageName()));
                    myWebView.loadUrl("file:///android_res/raw/"+ShelperImage[s]+".jpg");
                }
            }
        });
        return b;
    }

    public void assignVariables() {
        if ("PROJECTILE".equals(type))
        {
            u1=var[0];
            u2=var[1];
            u3=var[2];
            u4=var[3];
            u5=var[4];
            u6=var[5];
            u7=var[6];
            u8=var[7];
            switch (option[1]) {
                case 1:
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
        if ("VECTORS".equals(type))
        {
            u1=var[0];
            u2=var[1];
            u3=var[2];
            u4=var[3];
            u5=var[4];
            u6=var[5];
            u7=var[6];
            u8=var[7];
            u9=var[8];
            u10=var[9];
            u11=var[10];
            u12=var[11];
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
            u1=var[0];
            u2=var[1];
            u3=var[2];
            u4=var[3];
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
            u1=var[0];
            u3=var[1];
            u4=var[2];
            u5=var[3];
            u6=var[4];
            u7=var[5];
            if (option[1]==4)
            {
                u4=var[3];
                u5=var[2];
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
            u1=var[0];
            u2=var[1];
            u3=var[2];
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
            u1=var[0];
            u2=var[1];
            u3=var[2];
            u4=var[3];
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
                        v5=(-Math.atan(u4/(v3*u3))/v3)%(2*Math.PI/v3);
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

    public void assignExplanation() {
        if ("PROJECTILE".equals(type))
        {
            stotal=14;
        	instructions[1]="First, we will deal with the 'y' direction. We can use the y-acceleration for a typical projectile problem.";
			instructions[2]="We want an equation for y-velocity. We can get it from y-acceleration, but there is an unknown integration constant K1.";
            helperImage[1]=R.raw.helper_proj_1;
			helperImage[2]=R.raw.helper_proj_2;
            ShelperImage[1]="helper_proj_1";
			ShelperImage[2]="helper_proj_2";
			switch (option[1]) {
        	case 1:
			instructions[3]="We know the magnitude of the overall velocity and the angle at a certain (same) time. We can use this knowledge to obtain the y-velocity at that same time.";
			instructions[4]="Plug in values and solve for the integration constant K1.";
			instructions[5]="Next, we want an equation for y-position. We can get it from y-velocity, but there is an unknown integration constant K2.";
			instructions[6]="We know the y-position at a certain time, because we are given this in the problem.";
			instructions[7]="Plug in values and solve for the integration constant K2.";
            helperImage[3]=R.raw.helper_proj_3_1;
			helperImage[4]=R.raw.helper_proj_4;
			helperImage[5]=R.raw.helper_proj_5;
			helperImage[6]=R.raw.helper_proj_6;
			helperImage[7]=R.raw.helper_proj_7;
            ShelperImage[3]="helper_proj_3_1";
			ShelperImage[4]="helper_proj_4";
			ShelperImage[5]="helper_proj_5";
			ShelperImage[6]="helper_proj_6";
			ShelperImage[7]="helper_proj_7";
			break;
        	case 2:
			instructions[3]="We know the x-velocity and the angle at a certain time. We can use this knowledge to obtain the y-velocity at that same time.";
			instructions[4]="Plug in values and solve for the integration constant K1.";
			instructions[5]="Next, we want an equation for y-position. We can get it from y-velocity, but there is an unknown integration constant K2.";
			instructions[6]="We know the y-position at a certain time, because we are given this in the problem.";
			instructions[7]="Plug in values and solve for the integration constant K2.";
            helperImage[3]=R.raw.helper_proj_3_2;
    		helperImage[4]=R.raw.helper_proj_4;
    		helperImage[5]=R.raw.helper_proj_5;
			helperImage[6]=R.raw.helper_proj_6;
			helperImage[7]=R.raw.helper_proj_7;
            ShelperImage[3]="helper_proj_3_2";
			ShelperImage[4]="helper_proj_4";
			ShelperImage[5]="helper_proj_5";
			ShelperImage[6]="helper_proj_6";
			ShelperImage[7]="helper_proj_7";
			break;
        	case 3:
			instructions[3]="We know the y-velocity at a certain time, because we are given this in the problem.";
			instructions[4]="Plug in values and solve for the integration constant K1.";
			instructions[5]="Next, we want an equation for y-position. We can get it from y-velocity, but there is an unknown integration constant K2.";
			instructions[6]="We know the y-position at a certain time, because we are given this in the problem.";
			instructions[7]="Plug in values and solve for the integration constant K2.";
            helperImage[3]=R.raw.helper_proj_3_3;
    		helperImage[4]=R.raw.helper_proj_4;
    		helperImage[5]=R.raw.helper_proj_5;
			helperImage[6]=R.raw.helper_proj_6;
			helperImage[7]=R.raw.helper_proj_7;
            ShelperImage[3]="helper_proj_3_3";
			ShelperImage[4]="helper_proj_4";
			ShelperImage[5]="helper_proj_5";
			ShelperImage[6]="helper_proj_6";
			ShelperImage[7]="helper_proj_7";
			break;
        	case 4:
			instructions[3]="We know the y-velocity at a certain time, because we are given this in the problem.";
			instructions[4]="Plug in values and solve for the integration constant K1.";
			instructions[5]="Next, we want an equation for y-position. We can get it from y-velocity, but there is an unknown integration constant K2.";
			instructions[6]="We know the y-position at a certain time, because we are given this in the problem.";
			instructions[7]="Plug in values and solve for the integration constant K2.";
            helperImage[3]=R.raw.helper_proj_3_4;
    		helperImage[4]=R.raw.helper_proj_4;
    		helperImage[5]=R.raw.helper_proj_5;
			helperImage[6]=R.raw.helper_proj_6;
			helperImage[7]=R.raw.helper_proj_7;
            ShelperImage[3]="helper_proj_3_4";
			ShelperImage[4]="helper_proj_4";
			ShelperImage[5]="helper_proj_5";
			ShelperImage[6]="helper_proj_6";
			ShelperImage[7]="helper_proj_7";
			break;
        	case 5:
        	instructions[3]="Unfortunately, we do not have any information regarding velocity given to us in the problem.";
        	instructions[4]="To solve for K1 now, we would need to plug in the y-velocity and the time and solve for K1. However, we cannot do this because we have no way of finding the y-velocity at a certain time yet.";
        	instructions[5]="Keeping the unknown K1 in mind, we next want an equation for y-position. We can get it from y-velocity, but there is another unknown integration constant K2. Note that at this point there are two unknowns, K1 and K2.";
        	instructions[6]="We know the y-position at two times, because we are given this in the problem. As long as both times are different, we have two different pieces of information.";
        	instructions[7]="For each y-position and time we are given, plug them in and write the resulting equations. This gives us two equations for two unknowns. There are many ways to solve these equations, but perhaps the easiest is shown in the helper illustration. This will give us both K1 and K2.";
            helperImage[3]=R.raw.helper_proj_3_5;
        	helperImage[4]=R.raw.helper_proj_4_5;
        	helperImage[5]=R.raw.helper_proj_5;
			helperImage[6]=R.raw.helper_proj_6_5;
			helperImage[7]=R.raw.helper_proj_7_5;
            ShelperImage[3]="helper_proj_3_5";
			ShelperImage[4]="helper_proj_4_5";
			ShelperImage[5]="helper_proj_5";
			ShelperImage[6]="helper_proj_6_5";
			ShelperImage[7]="helper_proj_7_5";
        	break;
			}
			instructions[8]="Now, we will deal with the 'x' direction. We can use the x-acceleration for a typical projectile problem.";
			instructions[9]="We want an equation for x-velocity. We can get it from x-acceleration, but there is an unknown integration constant K3. Still, we can tell x-velocity is constant";
            helperImage[8]=R.raw.helper_proj_8;
			helperImage[9]=R.raw.helper_proj_9;
            ShelperImage[8]="helper_proj_8";
			ShelperImage[9]="helper_proj_9";
			switch (option[1]) {
        	case 1:
			instructions[10]="Since x-velocity is a constant, we only need to know it at one time. We know the magnitude of the overall velocity and the angle at a certain (same) time. We can use this knowledge to obtain the x-velocity.";
			instructions[11]="Next, we want an equation for x-position. We can get it from x-velocity, but there is an unknown integration constant K4.";
			instructions[12]="We know the x-position at a certain time, because we are given this in the problem.";
			instructions[13]="Plug in values and solve for the integration constant K4.";
            helperImage[10]=R.raw.helper_proj_10_1;
			helperImage[11]=R.raw.helper_proj_11;
			helperImage[12]=R.raw.helper_proj_12;
			helperImage[13]=R.raw.helper_proj_13;
            ShelperImage[10]="helper_proj_10_1";
			ShelperImage[11]="helper_proj_11";
			ShelperImage[12]="helper_proj_12";
			ShelperImage[13]="helper_proj_13";
			break;
        	case 2:
			instructions[10]="Since x-velocity is a constant, we only need to know it at one time. We are given this in the problem.";
			instructions[11]="Next, we want an equation for x-position. We can get it from x-velocity, but there is an unknown integration constant K4.";
			instructions[12]="We know the x-position at a certain time, because we are given this in the problem.";
			instructions[13]="Plug in values and solve for the integration constant K4.";
            helperImage[10]=R.raw.helper_proj_10_2;
    		helperImage[11]=R.raw.helper_proj_11;
			helperImage[12]=R.raw.helper_proj_12;
			helperImage[13]=R.raw.helper_proj_13;
            ShelperImage[10]="helper_proj_10_2";
			ShelperImage[11]="helper_proj_11";
			ShelperImage[12]="helper_proj_12";
			ShelperImage[13]="helper_proj_13";
			break;
        	case 3:
			instructions[10]="Since x-velocity is a constant, we only need to know it at one time. We know the angle at a certain time, and can use our equation for y-velocity to find y-velocity at that same time. We can then use this knowledge to obtain the x-velocity.";
			instructions[11]="Next, we want an equation for x-position. We can get it from x-velocity, but there is an unknown integration constant K4.";
			instructions[12]="We know the x-position at a certain time, because we are given this in the problem.";
			instructions[13]="Plug in values and solve for the integration constant K4.";
            helperImage[10]=R.raw.helper_proj_10_3;
    		helperImage[11]=R.raw.helper_proj_11;
			helperImage[12]=R.raw.helper_proj_12;
			helperImage[13]=R.raw.helper_proj_13;
            ShelperImage[10]="helper_proj_10_3";
			ShelperImage[11]="helper_proj_11";
			ShelperImage[12]="helper_proj_12";
			ShelperImage[13]="helper_proj_13";
			break;
        	case 4:
			instructions[10]="Since x-velocity is a constant, we only need to know it at one time. We are given this in the problem.";
			instructions[11]="Next, we want an equation for x-position. We can get it from x-velocity, but there is an unknown integration constant K4.";
			instructions[12]="We know the x-position at a certain time, because we are given this in the problem.";
			instructions[13]="Plug in values and solve for the integration constant K4.";
			helperImage[10]=R.raw.helper_proj_10_4;
    		helperImage[11]=R.raw.helper_proj_11;
			helperImage[12]=R.raw.helper_proj_12;
			helperImage[13]=R.raw.helper_proj_13;
            ShelperImage[10]="helper_proj_10_4";
			ShelperImage[11]="helper_proj_11";
			ShelperImage[12]="helper_proj_12";
			ShelperImage[13]="helper_proj_13";
            break;
        	case 5:
            instructions[10]="Unfortunately, we do not have any information regarding velocity given to us in the problem. We could proceed as we did with y, but there is also a trick we can take advantage of. Because x-velocity is a constant, we can find it directly from the two x-position values given to us, as long as the times are different.";
            instructions[11]="Next, we want an equation for x-position. We can get it from x-velocity, but there is an unknown integration constant K4.";
			instructions[12]="We know the x-position at two times, because we are given this in the problem.";
			instructions[13]="Pick one set of corresponding x-position and time values (it doesn't matter which), and plug the values in and solve for the integration constant K4.";
            helperImage[10]=R.raw.helper_proj_10_5;
        	helperImage[11]=R.raw.helper_proj_11;
			helperImage[12]=R.raw.helper_proj_12_5;
			helperImage[13]=R.raw.helper_proj_13_5;
            ShelperImage[10]="helper_proj_10_5";
			ShelperImage[11]="helper_proj_11";
			ShelperImage[12]="helper_proj_12_5";
			ShelperImage[13]="helper_proj_13_5";
            break;
			}
			instructions[14]="Use the following equations to solve for what you need. You may also enter the value of one variable in the answer fields and hit 'SOLVE' to get the rest of them.";
            helperImage[14]=R.raw.helper_proj_14;
            ShelperImage[14]="helper_proj_14";
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
            ShelperImage[1]="helper_vectors_1";
            ShelperImage[2]="helper_vectors_2";
            ShelperImage[3]="helper_vectors_3";
            ShelperImage[4]="helper_vectors_4";
            ShelperImage[5]="helper_vectors_5";
            ShelperImage[6]="helper_vectors_6";
        }
        else if ("INCLINE_SIMPLE".equals(type))
        {
        	stotal=8;
        	instructions[1]="For the simple incline problem, we wish to know the forces acting on the object (static and kinetic friction, gravity, normal force) as well as the angle of the incline and the mass of the object. Using coordinates that are parallel and perpendicular (normal) to the incline makes things easier. the s,n system is simply the x,y system rotated by the angle of the incline.";
        	helperImage[1]=R.raw.helper_simp_1;
            ShelperImage[1]="helper_simp_1";
        	switch (option[0]) {
        	case 1:
        	instructions[2]="We are given the angle of the incline and the mass of the object. The mass allows us to figure out the force of gravity, which acts straight down, while the angle allows us to break this force into components parallel and perpendicular (normal) to the surface of the incline.";
        	helperImage[2]=R.raw.helper_simp_2_1;
            ShelperImage[2]="helper_simp_2_1";
        	break;
        	case 2:
            instructions[2]="We are given the angle of the incline and the force of gravity parallel to the incline. These two pieces of information allow us to find the mass of the object and the force of gravity perpendicular (normal) to the surface of the incline.";
            helperImage[2]=R.raw.helper_simp_2_2;
            ShelperImage[2]="helper_simp_2_2";
            break;
        	case 3:
            instructions[2]="We are given the angle of the incline and the force of gravity perpendicular (normal) to the incline. These two pieces of information allow us to find the mass of the object and the force of gravity parallel to the surface of the incline.";
            helperImage[2]=R.raw.helper_simp_2_3;
            ShelperImage[2]="helper_simp_2_3";
            break;
        	case 4:
            instructions[2]="We are given the mass of the object and the force of gravity parallel to the incline. These two pieces of information allow us to find the angle of the incline and the force of gravity perpendicular (normal) to the surface of the incline.";
            helperImage[2]=R.raw.helper_simp_2_4;
            ShelperImage[2]="helper_simp_2_4";
            break;
        	case 5:
            instructions[2]="We are given the mass of the object and the force of gravity perpendicular (normal) to the incline. These two pieces of information allow us to find the angle of the incline and the force of gravity parallel to the surface of the incline.";
            helperImage[2]=R.raw.helper_simp_2_5;
            ShelperImage[2]="helper_simp_2_5";
            break;
        	case 6:
            instructions[2]="We are given the force of gravity parallel and perpendicular (normal) to the incline. These two pieces of information allow us to find the angle of the incline and the mass of the object.";
            helperImage[2]=R.raw.helper_simp_2_6;
            ShelperImage[2]="helper_simp_2_6";
            break;
        	}
        	instructions[3]="We now wish to find the normal force. We can find this using Newton's 2nd Law, and the fact that the object never leaves the surface of the incline.";
        	helperImage[3]=R.raw.helper_simp_3;
            ShelperImage[3]="helper_simp_3";
        	switch (option[1]) {
        	case 1:
        	instructions[4]="Next, we would like to know the static coefficient of friction, and the maximum force that static friction can exert. We are given the static coefficient of friction, and can use this, along with the normal force, to get the maximum force of static friction.";
        	helperImage[4]=R.raw.helper_simp_4_1;
            ShelperImage[4]="helper_simp_4_1";
            break;
        	case 2:
            instructions[4]="Next, we would like to know the static coefficient of friction, and the maximum force that static friction can exert. We are given the maximum force of static friction, and can use this, along with the normal force, to get the coefficient of static friction.";
            helperImage[4]=R.raw.helper_simp_4_2;
            ShelperImage[4]="helper_simp_4_2";
            break;
        	}
        	switch (option[2]) {
        	case 1:
        	instructions[5]="Next, we would like to know the kinetic coefficient of friction, and the force that kinetic friction can exert. We are given the kinetic coefficient of friction, and can use this, along with the normal force, to get the force of kinetic friction.";
        	helperImage[5]=R.raw.helper_simp_5_1;
            ShelperImage[5]="helper_simp_5_1";
            break;
        	case 2:
            instructions[5]="Next, we would like to know the kinetic coefficient of friction, and the force that kinetic friction can exert. We are given the force of kinetic friction, and can use this, along with the normal force, to get the coefficient of kinetic friction.";
            helperImage[5]=R.raw.helper_simp_5_2;
            ShelperImage[5]="helper_simp_5_2";
            break;
        	}
        	instructions[6]="Finally, with all of the forces known to us, we can get the net force on the object. Net force and acceleration in the n direction is always 0 (since the object never leaves the ramp). In the s direction, if the object is held in place by friction, the force is also 0. If the object is sliding down the incline, then we can figure out the net force by considering gravity (parallel to incline) and kinetic friction (which points in +s, opposite of the motion).";
        	instructions[7]="With this net force, we can find the acceleration of the object (if it is sliding down the incline) by using its mass.";
        	instructions[8]="The following equations summarize what you need to obtain all the information used in the simple incline problem.";
        	helperImage[6]=R.raw.helper_simp_6;
        	helperImage[7]=R.raw.helper_simp_7;
        	helperImage[8]=R.raw.helper_simp_8;
            ShelperImage[6]="helper_simp_6";
            ShelperImage[7]="helper_simp_7";
            ShelperImage[8]="helper_simp_8";
        }
        else if ("INCLINE".equals(type))
        {
            stotal=19;
			switch (option[1]) {
			case 1:
			instructions[1]="The first piece of information we should know about an incline problem is the angle of the incline itself. We are given this in the problem.";
			helperImage[1]=R.raw.helper_incl_1;
            ShelperImage[1]="helper_incl_1";
			break;
			case 2:
			instructions[1]="The first piece of information we should know about an incline problem is the angle of the incline itself. We are given this in the problem.";
			helperImage[1]=R.raw.helper_incl_1;
            ShelperImage[1]="helper_incl_1";
			break;
			case 3:
			instructions[1]="The first piece of information we should know about an incline problem is the angle of the incline itself. We are given this in the problem.";
			helperImage[1]=R.raw.helper_incl_1;
            ShelperImage[1]="helper_incl_1";
			break;
			case 4:
			instructions[1]="The first piece of information we should know about an incline problem is the angle of the incline itself. We are given x-position and y-position at the same time, and can use them to solve for the angle of the incline.";
			helperImage[1]=R.raw.helper_incl_1_4;
            ShelperImage[1]="helper_incl_1_4";
			break;
			}
			instructions[2]="For an incline problem, it is easier to consider a different coordinate system than the standard x,y system. We want one basis vector to run along the incline, and the other perpendicular to the incline (i.e. rotate the x,y system by the incline angle). Since the object does not leave the incline, we only need to worry about one coordinate, s.";
			instructions[3]="While the force of friction points along the incline, the force of gravity points straight down. We can project the force of gravity onto our new basis vectors.";
			instructions[4]="The projections allow us to find the force pushing the object down the incline, and the normal force. The force of friction will be proportional to the normal force, with proportionality constant = static coefficient of friction if the object is stationary, and kinetic coefficient of friction if the object is sliding.";
			instructions[5]="With Newton's 2nd law, we can find the s-acceleration of the object. Note that friction points in the opposite direction of velocity, and thus will point in a different direction depending on whether the object is sliding uphill or downhill. Thus, there are two different equations for acceleration (one for uphill motion and one for downhill motion).";
			helperImage[2]=R.raw.helper_incl_2;
			helperImage[3]=R.raw.helper_incl_3;
			helperImage[4]=R.raw.helper_incl_4;
			helperImage[5]=R.raw.helper_incl_5;
            ShelperImage[2]="helper_incl_2";
			ShelperImage[3]="helper_incl_3";
			ShelperImage[4]="helper_incl_4";
			ShelperImage[5]="helper_incl_5";
			if (u6>0) {
			instructions[6]="We want an equation for s-velocity. We can get it from s-acceleration. Since the given velocity is positive, the object is sliding uphill at that time. Thus, we will start with the acceleration equation for uphill motion, and integrate to get velocity. Note that there is an unknown integration constant K1.";
			instructions[7]="Using our velocity, we can solve for the integration constant K1.";
			instructions[8]="The equation we have is valid for all uphill motion, but in addition, is also valid for the peak position. We can use this equation for velocity to find when the object reaches its peak position. If tp is the time the object is at its peak, then v(tp) = 0, so we can solve for the peak time, tp.";
			instructions[9]="Now, we wish to find the velocity for all times the object is sliding downhill. We can get this from the acceleration equation for downhill motion, but there is an integration constant K2.";
			instructions[10]="Since this equation for downhill velocity is also valid at the peak, we can use v(tp)=0 to solve for the integration constant K2.";
			helperImage[6]=R.raw.helper_incl_6_up;
			helperImage[7]=R.raw.helper_incl_7_up;
			helperImage[8]=R.raw.helper_incl_8_up;
			helperImage[9]=R.raw.helper_incl_9_up;
			helperImage[10]=R.raw.helper_incl_10_up;
            ShelperImage[6]="helper_incl_6_up";
			ShelperImage[7]="helper_incl_7_up";
			ShelperImage[8]="helper_incl_8_up";
			ShelperImage[9]="helper_incl_9_up";
            ShelperImage[10]="helper_incl_10_up";
			}
			else if(u6<0) {
			instructions[6]="We want an equation for s-velocity. We can get it from s-acceleration. Since the given velocity is negative, the object is sliding downhill at that time. Thus, we will start with the acceleration equation that is valid when the object slides downhill, and integrate to get velocity. Note that there is an unknown integration constant K2.";
			instructions[7]="Using our velocity, we can solve for the integration constant K2.";
			instructions[8]="The equation we have is valid for all downhill motion, but in addition, is also valid for the peak position. We can use this equation for velocity to find when the object reaches its peak position. If tp is the time the object is at its peak, then v(tp) = 0, so we can solve for the peak time, tp.";
			instructions[9]="Now, we wish to find the velocity for all times the object is sliding uphill. We can get this from the acceleration equation for uphill motion, but there is an integration constant K1.";
			instructions[10]="Since this equation for uphill velocity is also valid at the peak, we can use v(tp)=0 to solve for the integration constant K1.";
			helperImage[6]=R.raw.helper_incl_6_down;
			helperImage[7]=R.raw.helper_incl_7_down;
			helperImage[8]=R.raw.helper_incl_8_down;
			helperImage[9]=R.raw.helper_incl_9_down;
			helperImage[10]=R.raw.helper_incl_10_down;
            ShelperImage[6]="helper_incl_6_down";
			ShelperImage[7]="helper_incl_7_down";
			ShelperImage[8]="helper_incl_8_down";
			ShelperImage[9]="helper_incl_9_down";
            ShelperImage[10]="helper_incl_10_down";
			}
			else {
			instructions[6]="We want an equation for s-velocity. We can get it from s-acceleration. Since the given velocity is 0, the object is exactly at the peak of its motion at that time. Because velocity must be a continuous quantity, both the uphill and downhill regions intersect at the peak, and have v(t*)=0 as a condition.";
			instructions[7]="First, let's deal with uphill motion. We will start with the acceleration equation for uphill motion, and integrate to get velocity. Note that there is an unknown integration constant K1.";
			instructions[8]="Plug in v(t*)=0 to solve for K1";
			instructions[9]="Next, let's deal with downhill motion. We will start with the acceleration equation for downhill motion, and integrate to get velocity. Note that there is an unknown integration constant K2.";
			instructions[10]="Plug in v(t*)=0 to solve for K2";
			helperImage[6]=R.raw.helper_incl_6_peak;
			helperImage[7]=R.raw.helper_incl_7_peak;
			helperImage[8]=R.raw.helper_incl_8_peak;
			helperImage[9]=R.raw.helper_incl_9_peak;
			helperImage[10]=R.raw.helper_incl_10_peak;
            ShelperImage[6]="helper_incl_6_peak";
			ShelperImage[7]="helper_incl_7_peak";
			ShelperImage[8]="helper_incl_8_peak";
			ShelperImage[9]="helper_incl_9_peak";
            ShelperImage[10]="helper_incl_10_peak";
			}
			instructions[11]="We now know the velocity of the object at all times.";
			helperImage[11]=R.raw.helper_incl_11;
            ShelperImage[11]="helper_incl_11";
			switch (option[1]) {
			case 1:
			instructions[12]="We are given the s-position at a certain time.";
			helperImage[12]=R.raw.helper_incl_12_1;
            ShelperImage[12]="helper_incl_12_1";
			break;
			case 2:
			instructions[12]="We know the x-position at a certain time, and the angle of the incline. We can use this to get the s-position at that same time.";
			helperImage[12]=R.raw.helper_incl_12_2;
            ShelperImage[12]="helper_incl_12_2";
			break;
			case 3:
			instructions[12]="We know the y-position at a certain time, and the angle of the incline. We can use this to get the s-position at that same time.";
			helperImage[12]=R.raw.helper_incl_12_3;
            ShelperImage[12]="helper_incl_12_3";
			break;
			case 4:
			instructions[12]="We know the x-position and y-position at a certain time. We can use this to get the s-position at that same time.";
			helperImage[12]=R.raw.helper_incl_12_4;
            ShelperImage[12]="helper_incl_12_4";
			break;
			}
			if (u4<peakTime)
			{
			instructions[13]="Next, we want an equation for s-position. We can get it from s-velocity. Since the position is given at a time before the peak time (the uphill motion region), we will start with the uphill velocity and integrate to get position. Note that there is an unknown integration constant K3.";
			instructions[14]="Using our s-position, we can solve for the integration constant K3.";
			instructions[15]="The equation we have is valid for all uphill motion, but in addition, is also valid for the peak position. We can use this equation for position and plug in the peak time to find the peak position sp.";
			instructions[16]="Now, we wish to find the s-position for all times the object is sliding downhill. We can get this from the s-velocity equation for downhill motion, but there is an integration constant K4.";
			instructions[17]="Since this equation for downhill position is also valid at the peak, we can use s(tp)=sp to solve for the integration constant K4.";
			helperImage[13]=R.raw.helper_incl_13_up;
			helperImage[14]=R.raw.helper_incl_14_up;
			helperImage[15]=R.raw.helper_incl_15_up;
			helperImage[16]=R.raw.helper_incl_16_up;
			helperImage[17]=R.raw.helper_incl_17_up;
            ShelperImage[13]="helper_incl_13_up";
			ShelperImage[14]="helper_incl_14_up";
			ShelperImage[15]="helper_incl_15_up";
			ShelperImage[16]="helper_incl_16_up";
            ShelperImage[17]="helper_incl_17_up";
			}
			else if (u4>peakTime)
			{
			instructions[13]="Next, we want an equation for s-position. We can get it from s-velocity. Since the position is given at a time after the peak time (the downhill motion region), we will start with the downhill velocity and integrate to get position. Note that there is an unknown integration constant K4.";
			instructions[14]="Using our s-position, we can solve for the integration constant K4.";
			instructions[15]="The equation we have is valid for all downhill motion, but in addition, is also valid for the peak position. We can use this equation for position and plug in the peak time to find the peak position sp.";
			instructions[16]="Now, we wish to find the s-position for all times the object is sliding uphill. We can get this from the s-velocity equation for uphill motion, but there is an integration constant K3.";
			instructions[17]="Since this equation for uphill position is also valid at the peak, we can use s(tp)=sp to solve for the integration constant K3.";
			helperImage[13]=R.raw.helper_incl_13_down;
			helperImage[14]=R.raw.helper_incl_14_down;
			helperImage[15]=R.raw.helper_incl_15_down;
			helperImage[16]=R.raw.helper_incl_16_down;
			helperImage[17]=R.raw.helper_incl_17_down;
            ShelperImage[13]="helper_incl_13_down";
			ShelperImage[14]="helper_incl_14_down";
			ShelperImage[15]="helper_incl_15_down";
			ShelperImage[16]="helper_incl_16_down";
            ShelperImage[17]="helper_incl_17_down";
			}
			else {
			instructions[13]="Next, we want an equation for s-position. We can get it from s-velocity. Since the position is given at the peak time, and position must be a continuous quantity, both the uphill and downhill regions intersect at the peak, and have s(tp)=sp as a condition.";
			instructions[14]="First, let's deal with uphill motion. We will start with the velocity equation for uphill motion, and integrate to get position. Note that there is an unknown integration constant K3.";
			instructions[15]="Plug in s(tp)=sp to solve for K3";
			instructions[16]="Next, let's deal with downhill motion. We will start with the velocity equation for downhill motion, and integrate to get position. Note that there is an unknown integration constant K4.";
			instructions[17]="Plug in s(tp)=sp to solve for K4";
			helperImage[13]=R.raw.helper_incl_13_peak;
			helperImage[14]=R.raw.helper_incl_14_peak;
			helperImage[15]=R.raw.helper_incl_15_peak;
			helperImage[16]=R.raw.helper_incl_16_peak;
			helperImage[17]=R.raw.helper_incl_17_peak;
            ShelperImage[13]="helper_incl_13_peak";
			ShelperImage[14]="helper_incl_14_peak";
			ShelperImage[15]="helper_incl_15_peak";
			ShelperImage[16]="helper_incl_16_peak";
            ShelperImage[17]="helper_incl_17_peak";
			}
			instructions[18]="We now know the s-position of the object at all times";
			instructions[19]="Use the following equations to solve for what you need. You may also enter the value of one variable in the answer fields and hit 'SOLVE' to get the rest of them.";
			helperImage[18]=R.raw.helper_incl_18;
			helperImage[19]=R.raw.helper_incl_19;
            ShelperImage[18]="helper_incl_18";
            ShelperImage[19]="helper_incl_19";
        }
        else if ("SPRING_SIMPLE".equals(type))
        {
            stotal=4;
        	instructions[1]="We are given the equilibrium position of the spring. This is the position of the mass at which there is no force acting on the mass. We define displacement d in terms of the actual position x and the equilibrium position xe.";
            helperImage[1]=R.raw.helper_spri_simp_1;
            ShelperImage[1]="helper_spri_simp_1";
			switch (option[0]) {
        	case 1:
            instructions[2]="We are given the spring constant of our spring, which is a material property of the spring and assumed not to change as the spring stretches or compresses.";
            helperImage[2]=R.raw.helper_spri_simp_2_1;
            ShelperImage[2]="helper_spri_simp_2_1";
            break;
            case 2:
            instructions[2]="We are given the magnitude of the force on the mass while it is at a particular position. We can use this information along with Hooke's law to solve for the spring constant. The spring constant is a material property of the spring and assumed not to change as the spring stretches or compresses.";
            helperImage[2]=R.raw.helper_spri_simp_2_2;
            ShelperImage[2]="helper_spri_simp_2_2";
            break;
            }
            instructions[3]="With the help of the spring constant, we can use Hooke's law to relate the displacement of the mass to the force acting on it.";
            instructions[4]="Use the following equations to solve for what you need. You may also enter the value of one variable in the answer fields and hit 'SOLVE' to get the rest of them.";
            helperImage[3]=R.raw.helper_spri_simp_3;
            helperImage[4]=R.raw.helper_spri_simp_4;
            ShelperImage[3]="helper_spri_simp_3";
            ShelperImage[4]="helper_spri_simp_4";
        }
        else if ("SPRING".equals(type))
        {
            stotal=8;
            instructions[1]="We wish to solve for the motion of a mass attached to an ideal spring. We can use Newton's 2nd law and Hooke's law to find a differential equation we can use to solve for x";
            helperImage[1]=R.raw.helper_spri_1;
            ShelperImage[1]="helper_spri_1";
            switch (option[1]) {
        	case 1:
            instructions[2]="There are a few ways to write the general solution to this equation. Based on preference, we will use a cosine wave. The position can be written in terms of the amplitude A, a phase shift Phi, the angular frequency w, and time t.";
            helperImage[2]=R.raw.helper_spri_2;
            ShelperImage[2]="helper_spri_2";
            break;
            case 2:
            instructions[2]="There are a few ways to write the general solution to this equation. Based on preference, we will use a sine wave. The position can be written in terms of the amplitude A, a phase shift Phi, the angular frequency w, and time t.";
            break;
            }
            switch (option[0]) {
            case 1:
            instructions[3]="We are given the mass and the spring constant. We can use those two values to solve for the angular frequency.";
            helperImage[3]=R.raw.helper_spri_3_1;
            ShelperImage[3]="helper_spri_3_1";
            break;
        	case 2:
            instructions[3]="We are given the angular frequency of the oscillation. Also, we have the mass, so we can solve for the spring constant.";
            helperImage[3]=R.raw.helper_spri_3_2;
            ShelperImage[3]="helper_spri_3_2";
            break;
            case 3:
            instructions[3]="We are given the angular frequency of the oscillation. Also, we have the spring constant, so we can solve for the mass.";
            helperImage[3]=R.raw.helper_spri_3_3;
            ShelperImage[3]="helper_spri_3_3";
            break;
            }
            switch (option[2]) {
            case 1:
            instructions[4]="We are given the position and velocity of the mass at t=0. We can differentiate position to get velocity. Then, we plug in the initial conditions to obtain two equations. We can solve these equations to get the amplitude and phase shift. Also, we can solve for the time offset.";
            helperImage[4]=R.raw.helper_spri_4_1;
            ShelperImage[4]="helper_spri_4_1";
            break;
            case 2:
            instructions[4]="We are given the amplitude and the time offset of the oscillation. We can solve for the phase shift.";
            helperImage[4]=R.raw.helper_spri_4_2;
            ShelperImage[4]="helper_spri_4_2";
            break;
            case 3:
            instructions[4]="We are given the amplitude and the phase shift of the oscillation. We can solve for the time offset.";
            helperImage[4]=R.raw.helper_spri_4_3;
            ShelperImage[4]="helper_spri_4_3";
            break;
            case 4:
            instructions[4]="We are given the total energy and the time offset of the oscillation (from which we can get the phase shift), but we still need the amplitude. The potential energy of a spring will be the most (and equal to the total energy) when the spring is stretched the most. We can use this knowledge to solve for the amplitude.";
            helperImage[4]=R.raw.helper_spri_4_4;
            ShelperImage[4]="helper_spri_4_4";
            break;
            case 5:
            instructions[4]="We are given the total energy and the phase shift of the oscillation (from which we can get the time offset), but we still need the amplitude. The potential energy of a spring will be the most (and equal to the total energy) when the spring is stretched the most. We can use this knowledge to solve for the amplitude.";
            helperImage[4]=R.raw.helper_spri_4_5;
            ShelperImage[4]="helper_spri_4_5";
            break;
            }
            instructions[5]="We now know everything to write the position. We can differentiate position to get a function for velocity and for acceleration.";
            instructions[6]="We can use the acceleration to write down the force in terms of time.";
            instructions[7]="We can get equations for the kinetic, potential, and total energy of the system. Note that because there is no friction, the total energy is constant";
            instructions[8]="Use the following equations to solve for what you need. You may also enter the value of one variable in the answer fields and hit 'SOLVE' to get the rest of them.";
            helperImage[5]=R.raw.helper_spri_5;
            helperImage[6]=R.raw.helper_spri_6;
            helperImage[7]=R.raw.helper_spri_7;
            helperImage[8]=R.raw.helper_spri_8;
            ShelperImage[5]="helper_spri_5";
            ShelperImage[6]="helper_spri_6";
            ShelperImage[7]="helper_spri_7";
            ShelperImage[8]="helper_spri_8";
        }
    }

    public void assignConstants()
    {
        if ("PROJECTILE".equals(type)) {
            T_Constants.setText("Constant g = "+String.format("%.4f", constant_g)+" m/s^2" +
        	"\nPeak y-position = "+String.format("%.4f",peak)+ " m" +
        	"\nx-velocity = "+String.format("%.4f",k3)+ " m/s");
        }
        else if ("VECTORS".equals(type)) {
            T_Constants.setText("Resultant Vector:\n    Magnitude = "+String.format("%.4f",Math.sqrt(v1*v1+v2*v2))+" N\n    Angle = "+String.format("%.4f",Math.atan2(v2,v1))+" Radians\n    X-component = "+String.format("%.4f",v1)+" N\n    Y-component = "+String.format("%.4f",v2)+" N" +
        	"\nEquilibrant Vector:\n    Magnitude = "+String.format("%.4f",Math.sqrt(v1*v1+v2*v2))+" N\n    Angle = "+String.format("%.4f",Math.atan2(-v2,-v1))+" Radians\n    X-component = "+String.format("%.4f",-v1)+" N\n    Y-component = "+String.format("%.4f",-v2)+" N");
        }
        else if ("INCLINE_SIMPLE".equals(type)) {
            T_Constants.setText("Constant g = "+String.format("%.4f", constant_g)+" m/s^2" +
        	"\nAngle of incline = "+String.format("%.4f",v1)+ " radians or "+String.format("%.4f",v1*57.295779513082320876798154814105)+" degrees" +
        	"\nMass of object = "+String.format("%.4f",v2)+ " kg" +
        	"\nForce of gravity parallel to incline = "+String.format("%.4f",-v3)+ " N" +
        	"\nNormal force = -force of gravity perpendicular to incline = "+String.format("%.4f",v4)+ " N" +
        	"\nMaximum force of static friction = "+String.format("%.4f",v5)+ " N" +
        	"\nCoefficient of static friction = "+String.format("%.4f",v6)+
        	"\nForce of kinetic friction = "+String.format("%.4f",v7)+ " N" +
        	"\nCoefficient of kinetic friction = "+String.format("%.4f",v8) +
        	"\nNet force along incline (if sliding downward) = "+String.format("%.4f",-(v3-v7))+ " N" +
        	"\nNet acceleration along incline (if sliding downward) = "+String.format("%.4f",-(v3-v7)/v2)+ " m/s^2");
        }
        else if ("INCLINE".equals(type)) {
            T_Constants.setText("Constant g = "+String.format("%.4f", constant_g)+" m/s^2" +
        	"\nPeak s-position = "+String.format("%.4f",peakS)+ " m at time = "+String.format("%.4f",peakTime)+" s" +
            "\nAngle of incline = "+String.format("%.4f",angle)+ " radians or "+String.format("%.4f",angle*57.295779513082320876798154814105)+" degrees");
        }
        else if ("SPRING_SIMPLE".equals(type)) {
            T_Constants.setText("Spring constant = "+String.format("%.4f", v1)+" N/m" +
        	"\nEquilibrium position = "+String.format("%.4f",v2)+ " m");
        }
        else if ("SPRING".equals(type)) {
            T_Constants.setText("Mass = "+String.format("%.4f", v1)+" kg" +
            "\nSpring constant = "+String.format("%.4f", v2)+" N/m" +
            "\nAngular frequency = "+String.format("%.4f",v3)+ " Hz" +
        	"\nAmplitude = "+String.format("%.4f",v4)+ " m" +
            "\nTime shift = "+String.format("%.4f",v5)+" + "+String.format("%.4f",2*Math.PI/v3)+"N seconds, N is any integer" +
            "\nPhase shift = "+String.format("%.4f",v7)+" + "+String.format("%.4f",2*Math.PI)+"N radians, N is any integer" +
            "\nTotal energy = "+String.format("%.4f",v6)+ " J");
        }
    }

    public Spinner makeLeftSpinner(final int i, final String unit) {
        final Spinner S=new Spinner(this);
        final ArrayAdapter A=ArrayAdapter.createFromResource(this, getResources().getIdentifier("unit_"+unit, "array", getPackageName()), android.R.layout.simple_spinner_item);
        A.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        S.setPrompt("Unit ("+unit+"):");
        S.setAdapter(A);
        if ("length".equals(unit)){
	        S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
	            	switch (pos)
	            	{
	            	case 0: factorA1[i]=1; break;
	            	case 1: factorA1[i]=0.01; break;
	            	case 2: factorA1[i]=0.001; break;
                	case 3: factorA1[i]=1000; break;
                	case 4: factorA1[i]=0.0254; break;
                	case 5: factorA1[i]=0.3048; break;
	            	}
	            }
	            public void onNothingSelected(AdapterView<?> parent) {
	            	factorA1[i]=1;
	            }
	        });
            S.setSelection(defaultLength);
        }
        else if ("time".equals(unit)){
        	S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                	switch (pos)
                	{
                	case 0: factorA1[i]=1; break;
                	case 1: factorA1[i]=0.001; break;
                	}
                }
                public void onNothingSelected(AdapterView<?> parent) {
                	factorA1[i]=1;
                }
            });
            S.setSelection(defaultTime);
        }
        else if ("velocity".equals(unit)){
        	S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                	switch (pos)
                	{
                	case 0: factorA1[i]=1; break;
                	case 1: factorA1[i]=0.01; break;
                	case 2: factorA1[i]=0.001; break;
                	case 3: factorA1[i]=1000; break;
                	case 4: factorA1[i]=0.0254; break;
                	case 5: factorA1[i]=0.3048; break;
                	}
                }
                public void onNothingSelected(AdapterView<?> parent) {
                	factorA1[i]=1;
                }
            });
            S.setSelection(defaultVelocity);
        }
        else if ("angle".equals(unit)){
        	S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                	switch (pos)
                	{
                	case 0: factorA1[i]=1; break;
                	case 1: factorA1[i]=0.017453292519943295769236907684886; break;
                	}
                }
                public void onNothingSelected(AdapterView<?> parent) {
                	factorA1[i]=1;
                }
            });
            S.setSelection(defaultAngle);
        }
        else if ("mass".equals(unit)){
        	S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                	switch (pos)
                	{
                	case 0: factorA1[i]=1; break;
                	case 1: factorA1[i]=0.001; break;
                	}
                }
                public void onNothingSelected(AdapterView<?> parent) {
                	factorA1[i]=1;
                }
            });
            S.setSelection(defaultMass);
        }
        else if ("force".equals(unit)){
        	S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                	switch (pos)
                	{
                	case 0: factorA1[i]=1; break;
                	case 1: factorA1[i]=4.44822162; break;
                	}
                }
                public void onNothingSelected(AdapterView<?> parent) {
                	factorA1[i]=1;
                }
            });
            S.setSelection(defaultForce);
        }
        else if ("energy".equals(unit)){
        	S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    switch (pos) {
                        case 0:
                            factorA1[i] = 1; break;
                    }
                }

                public void onNothingSelected(AdapterView<?> parent) {
                    factorA1[i] = 1;
                }
            });
            S.setSelection(defaultEnergy);
        }
        else if ("frequency".equals(unit)){
        	S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    switch (pos) {
                        case 0:
                            factorA1[i] = 1; break;
                    }
                }

                public void onNothingSelected(AdapterView<?> parent) {
                    factorA1[i] = 1;
                }
            });
            S.setSelection(defaultFrequency);
        }
        else if ("acceleration".equals(unit)){
        	S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    switch (pos) {
                        case 0:
                            factorA1[i] = 1; break;
                    }
                }

                public void onNothingSelected(AdapterView<?> parent) {
                    factorA1[i] = 1;
                }
            });
            S.setSelection(defaultAcceleration);
        }
        return S;
    }

    public Spinner makeRightSpinner(final int i, final String unit) {
        final Spinner S=new Spinner(this);
        final ArrayAdapter A=ArrayAdapter.createFromResource(this, getResources().getIdentifier("unit_"+unit, "array", getPackageName()), android.R.layout.simple_spinner_item);
        A.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        S.setPrompt("Unit ("+unit+"):");
        S.setAdapter(A);
        if ("length".equals(unit)){
	        S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
	            	switch (pos)
	            	{
	            	case 0: factorA2[i]=1; break;
	            	case 1: factorA2[i]=0.01; break;
	            	case 2: factorA2[i]=0.001; break;
                	case 3: factorA2[i]=1000; break;
                	case 4: factorA2[i]=0.0254; break;
                	case 5: factorA2[i]=0.3048; break;
	            	}
	            }
	            public void onNothingSelected(AdapterView<?> parent) {
	            	factorA2[i]=1;
	            }
	        });
            S.setSelection(defaultLength);
        }
        else if ("time".equals(unit)){
        	S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                	switch (pos)
                	{
                	case 0: factorA2[i]=1; break;
                	case 1: factorA2[i]=0.001; break;
                	}
                }
                public void onNothingSelected(AdapterView<?> parent) {
                	factorA2[i]=1;
                }
            });
            S.setSelection(defaultTime);
        }
        else if ("velocity".equals(unit)){
        	S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                	switch (pos)
                	{
                	case 0: factorA2[i]=1; break;
                	case 1: factorA2[i]=0.01; break;
                	case 2: factorA2[i]=0.001; break;
                	case 3: factorA2[i]=1000; break;
                	case 4: factorA2[i]=0.0254; break;
                	case 5: factorA2[i]=0.3048; break;
                	}
                }
                public void onNothingSelected(AdapterView<?> parent) {
                	factorA2[i]=1;
                }
            });
            S.setSelection(defaultVelocity);
        }
        else if ("angle".equals(unit)){
        	S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                	switch (pos)
                	{
                	case 0: factorA2[i]=1; break;
                	case 1: factorA2[i]=0.017453292519943295769236907684886; break;
                	}
                }
                public void onNothingSelected(AdapterView<?> parent) {
                	factorA2[i]=1;
                }
            });
            S.setSelection(defaultAngle);
        }
        else if ("mass".equals(unit)){
        	S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                	switch (pos)
                	{
                	case 0: factorA2[i]=1; break;
                	case 1: factorA2[i]=0.001; break;
                	}
                }
                public void onNothingSelected(AdapterView<?> parent) {
                	factorA2[i]=1;
                }
            });
            S.setSelection(defaultMass);
        }
        else if ("force".equals(unit)){
        	S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                	switch (pos)
                	{
                	case 0: factorA2[i]=1; break;
                	case 1: factorA2[i]=4.44822162; break;
                	}
                }
                public void onNothingSelected(AdapterView<?> parent) {
                	factorA2[i]=1;
                }
            });
            S.setSelection(defaultForce);
        }
        else if ("energy".equals(unit)){
        	S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    switch (pos) {
                        case 0:
                            factorA2[i] = 1; break;
                    }
                }

                public void onNothingSelected(AdapterView<?> parent) {
                    factorA2[i] = 1;
                }
            });
            S.setSelection(defaultEnergy);
        }
        else if ("frequency".equals(unit)){
        	S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    switch (pos) {
                        case 0:
                            factorA2[i] = 1; break;
                    }
                }

                public void onNothingSelected(AdapterView<?> parent) {
                    factorA2[i] = 1;
                }
            });
            S.setSelection(defaultFrequency);
        }
        else if ("acceleration".equals(unit)){
        	S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    switch (pos) {
                        case 0:
                            factorA2[i] = 1; break;
                    }
                }

                public void onNothingSelected(AdapterView<?> parent) {
                    factorA2[i] = 1;
                }
            });
            S.setSelection(defaultAcceleration);
        }
        return S;
    }

    private int dpToPx(int dp)
    {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }

    public void enableDisableView(View view, boolean enabled) {
        view.setEnabled(enabled);
        if (view instanceof ViewGroup) {
            ViewGroup group=(ViewGroup) view;
            for (int idx=0;idx<group.getChildCount();idx++) {
                enableDisableView(group.getChildAt(idx), enabled);
            }
        }
    }
}