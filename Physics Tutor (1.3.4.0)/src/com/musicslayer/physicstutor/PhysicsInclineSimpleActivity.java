package com.musicslayer.physicstutor;

import java.util.Date;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.Localytics.android.LocalyticsSession;

public class PhysicsInclineSimpleActivity extends PhysicsActivity{
	private final static String SOLVE_SIMPLE_INCLINE_CLICK = "solve_simple_incline_click";
	private final static String AD_SKIP = "ad_skip";
	private LocalyticsSession localyticsSession;
	Spinner S[][][];
	ArrayAdapter<?> adapter[][][];
    TextView TEST, constants;
    Button solve, test;
    Button category1, category2, category3, P1_1, P1_2, P1_3, P1_4, P1_5, P1_6, P2_1, P2_2, P3_1, P3_2;
    ScrollView cat1_1, cat1_2, cat1_3, cat1_4, cat1_5, cat1_6, cat2_1, cat2_2, cat3_1, cat3_2;
    LinearLayout page1, page2, page3, inputs;
    LightingColorFilter fGreen, fRed, fYellow;
    EditText E1_1_1, E1_1_2, E1_2_1, E1_2_2, E1_3_1, E1_3_2, E1_4_1, E1_4_2, E1_5_1, E1_5_2, E1_6_1, E1_6_2, E2_1_1, E2_2_1, E3_1_1, E3_2_1; 
    
    int isError, isReady=0, i, j, k;
    
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
    	super.onPause();
    	savePrefs();
    }

    @Override
	public void onBackPressed() {
		startActivity(new Intent(PhysicsInclineSimpleActivity.this, PhysicsInfoActivity.class));
	return;
	}
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) 
        {
        	((LinearLayout) findViewById(R.id.LinearLayout01)).setOrientation(0);
        } 
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
        	((LinearLayout) findViewById(R.id.LinearLayout01)).setOrientation(1);
        }
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.solve_incline_simple);
        setTitle("Physics Tutor - Simple Incline Problem");
        this.localyticsSession = new LocalyticsSession(this.getApplicationContext(),LOCALYTICS);
		this.localyticsSession.open();
		this.localyticsSession.upload();
        loadPrefs();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) 
        {
        	((LinearLayout) findViewById(R.id.LinearLayout01)).setOrientation(0);
        } 
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
        	((LinearLayout) findViewById(R.id.LinearLayout01)).setOrientation(1);
        }      
        
        solve = (Button) findViewById(R.id.Button_Solve);
        test = (Button) findViewById(R.id.TEST2);
        TEST=(TextView) findViewById(R.id.TEST);
        constants=(TextView) findViewById(R.id.Constants);
        category1=(Button) findViewById(R.id.Button_catagory1);
        category2=(Button) findViewById(R.id.Button_catagory2);
        category3=(Button) findViewById(R.id.Button_catagory3);
        cat1_1=(ScrollView) findViewById(R.id.ScrollView01_1);
        cat1_2=(ScrollView) findViewById(R.id.ScrollView01_2);
        cat1_3=(ScrollView) findViewById(R.id.ScrollView01_3);
        cat1_4=(ScrollView) findViewById(R.id.ScrollView01_4);
        cat1_5=(ScrollView) findViewById(R.id.ScrollView01_5);
        cat1_6=(ScrollView) findViewById(R.id.ScrollView01_6);
        cat2_1=(ScrollView) findViewById(R.id.ScrollView02_1);
        cat2_2=(ScrollView) findViewById(R.id.ScrollView02_2);
        cat3_1=(ScrollView) findViewById(R.id.ScrollView03_1);
        cat3_2=(ScrollView) findViewById(R.id.ScrollView03_2);
        page1=(LinearLayout) findViewById(R.id.LinearLayoutW);
        page2=(LinearLayout) findViewById(R.id.LinearLayoutX);
        page3=(LinearLayout) findViewById(R.id.LinearLayoutY);
        inputs=(LinearLayout) findViewById(R.id.LinearLayout_Inputs);
        P1_1=(Button) findViewById(R.id.P1_1);
        P1_2=(Button) findViewById(R.id.P1_2);
        P1_3=(Button) findViewById(R.id.P1_3);
        P1_4=(Button) findViewById(R.id.P1_4);
        P1_5=(Button) findViewById(R.id.P1_5);
        P1_6=(Button) findViewById(R.id.P1_6);
        P2_1=(Button) findViewById(R.id.P2_1);
        P2_2=(Button) findViewById(R.id.P2_2);
        P3_1=(Button) findViewById(R.id.P3_1);
        P3_2=(Button) findViewById(R.id.P3_2);
        E1_1_1=(EditText) findViewById(R.id.EditText_1_1_1);
        E1_1_2=(EditText) findViewById(R.id.EditText_1_1_2);
        E1_2_1=(EditText) findViewById(R.id.EditText_1_2_1);
        E1_2_2=(EditText) findViewById(R.id.EditText_1_2_2);
        E1_3_1=(EditText) findViewById(R.id.EditText_1_3_1);
        E1_3_2=(EditText) findViewById(R.id.EditText_1_3_2);
        E1_4_1=(EditText) findViewById(R.id.EditText_1_4_1);
        E1_4_2=(EditText) findViewById(R.id.EditText_1_4_2);
        E1_5_1=(EditText) findViewById(R.id.EditText_1_5_1);
        E1_5_2=(EditText) findViewById(R.id.EditText_1_5_2);
        E1_6_1=(EditText) findViewById(R.id.EditText_1_6_1);
        E1_6_2=(EditText) findViewById(R.id.EditText_1_6_2);
        E2_1_1=(EditText) findViewById(R.id.EditText_2_1_1);
        E2_2_1=(EditText) findViewById(R.id.EditText_2_2_1);
        E3_1_1=(EditText) findViewById(R.id.EditText_3_1_1);
        E3_2_1=(EditText) findViewById(R.id.EditText_3_2_1);
        fGreen = new LightingColorFilter(0xFFFFFF, 0x00FF00);
        fRed = new LightingColorFilter(0xFFFFFF, 0xFF0000);
        fYellow = new LightingColorFilter(0xFFFFFF, 0xFFFF00);
        solve.getBackground().setColorFilter(fYellow);
        test.getBackground().setColorFilter(fYellow);
        instructions = new String[21];
        helperImage = new int[21];
        //constants.setText("Constant g = "+String.format("%.4f", constant_g)+" m/s^2");
        constants.setVisibility(8);
        factor = new double[4][6][4];
	    selection = new int[4][6][4];
        
        option1=0;
        option2=0;
        option3=0;
        option4=0;
        //stotal=19;
        /*
        for (i=0;i<=20;i++)
        {
        	helperImage[i]=R.raw.default_image;
        	instructions[i]="?";
        }
        */
        S = new Spinner[4][6][4];
        adapter = new ArrayAdapter<?>[4][6][4];
        for (i=0;i<4;i++)
        {
        for (j=0;j<6;j++)
        {
        for (k=0;k<4;k++)
        {
        	S[i][j][k] = (Spinner) findViewById(getResources().getIdentifier("Spinner_"+Integer.toString(i)+"_"+Integer.toString(j)+"_"+Integer.toString(k), "id", getPackageName()));
        }
        }
        }
        adapter[0][0][0] = ArrayAdapter.createFromResource(this, R.array.unit_angle, android.R.layout.simple_spinner_item);
        adapter[0][0][1] = ArrayAdapter.createFromResource(this, R.array.unit_mass, android.R.layout.simple_spinner_item);
        makeSpinner(0, 0, 0, "angle");
        makeSpinner(0, 0, 1, "mass");
        S[0][0][0].setSelection(defaultAngle);
        S[0][0][1].setSelection(defaultMass);
        
        adapter[0][1][0] = ArrayAdapter.createFromResource(this, R.array.unit_angle, android.R.layout.simple_spinner_item);
        adapter[0][1][1] = ArrayAdapter.createFromResource(this, R.array.unit_force, android.R.layout.simple_spinner_item);
        makeSpinner(0, 1, 0, "angle");
        makeSpinner(0, 1, 1, "force");
        S[0][1][0].setSelection(defaultAngle);
        S[0][1][1].setSelection(defaultForce);
        
        adapter[0][2][0] = ArrayAdapter.createFromResource(this, R.array.unit_angle, android.R.layout.simple_spinner_item);
        adapter[0][2][1] = ArrayAdapter.createFromResource(this, R.array.unit_force, android.R.layout.simple_spinner_item);
        makeSpinner(0, 2, 0, "angle");
        makeSpinner(0, 2, 1, "force");
        S[0][2][0].setSelection(defaultAngle);
        S[0][2][1].setSelection(defaultForce);
        
        adapter[0][3][0] = ArrayAdapter.createFromResource(this, R.array.unit_mass, android.R.layout.simple_spinner_item);
        adapter[0][3][1] = ArrayAdapter.createFromResource(this, R.array.unit_force, android.R.layout.simple_spinner_item);
        makeSpinner(0, 3, 0, "mass");
        makeSpinner(0, 3, 1, "force");
        S[0][3][0].setSelection(defaultMass);
        S[0][3][1].setSelection(defaultForce);
        
        adapter[0][4][0] = ArrayAdapter.createFromResource(this, R.array.unit_mass, android.R.layout.simple_spinner_item);
        adapter[0][4][1] = ArrayAdapter.createFromResource(this, R.array.unit_force, android.R.layout.simple_spinner_item);
        makeSpinner(0, 4, 0, "mass");
        makeSpinner(0, 4, 1, "force");
        S[0][4][0].setSelection(defaultMass);
        S[0][4][1].setSelection(defaultForce);
        
        adapter[0][5][0] = ArrayAdapter.createFromResource(this, R.array.unit_force, android.R.layout.simple_spinner_item);
        adapter[0][5][1] = ArrayAdapter.createFromResource(this, R.array.unit_force, android.R.layout.simple_spinner_item);
        makeSpinner(0, 5, 0, "force");
        makeSpinner(0, 5, 1, "force");
        S[0][5][0].setSelection(defaultForce);
        S[0][5][1].setSelection(defaultForce);
        
        factor[1][0][0]=1;
        adapter[1][1][0] = ArrayAdapter.createFromResource(this, R.array.unit_force, android.R.layout.simple_spinner_item);
        makeSpinner(1, 1, 0, "force");
        S[1][1][0].setSelection(defaultForce);
        
        factor[2][0][0]=1;
        factor[2][0][0]=1;
        adapter[2][1][0] = ArrayAdapter.createFromResource(this, R.array.unit_force, android.R.layout.simple_spinner_item);
        makeSpinner(2, 1, 0, "force");
        S[2][1][0].setSelection(defaultForce);
        /*
        adapter[2][0][0] = ArrayAdapter.createFromResource(this, R.array.unit_length, android.R.layout.simple_spinner_item);
        adapter[2][0][1] = ArrayAdapter.createFromResource(this, R.array.unit_time, android.R.layout.simple_spinner_item);
        adapter[2][0][2] = ArrayAdapter.createFromResource(this, R.array.unit_angle, android.R.layout.simple_spinner_item);
        makeSpinner(2, 0, 0, "length");
        makeSpinner(2, 0, 1, "time");
        makeSpinner(2, 0, 2, "angle");
        
        adapter[2][1][0] = ArrayAdapter.createFromResource(this, R.array.unit_length, android.R.layout.simple_spinner_item);
        adapter[2][1][1] = ArrayAdapter.createFromResource(this, R.array.unit_time, android.R.layout.simple_spinner_item);
        adapter[2][1][2] = ArrayAdapter.createFromResource(this, R.array.unit_angle, android.R.layout.simple_spinner_item);
        makeSpinner(2, 1, 0, "length");
        makeSpinner(2, 1, 1, "time");
        makeSpinner(2, 1, 2, "angle");
        
        adapter[2][2][0] = ArrayAdapter.createFromResource(this, R.array.unit_length, android.R.layout.simple_spinner_item);
        adapter[2][2][1] = ArrayAdapter.createFromResource(this, R.array.unit_time, android.R.layout.simple_spinner_item);
        adapter[2][2][2] = ArrayAdapter.createFromResource(this, R.array.unit_angle, android.R.layout.simple_spinner_item);
        makeSpinner(2, 2, 0, "length");
        makeSpinner(2, 2, 1, "time");
        makeSpinner(2, 2, 2, "angle");
        
        adapter[2][3][0] = ArrayAdapter.createFromResource(this, R.array.unit_length, android.R.layout.simple_spinner_item);
        adapter[2][3][1] = ArrayAdapter.createFromResource(this, R.array.unit_length, android.R.layout.simple_spinner_item);
        adapter[2][3][2] = ArrayAdapter.createFromResource(this, R.array.unit_time, android.R.layout.simple_spinner_item);
        makeSpinner(2, 3, 0, "length");
        makeSpinner(2, 3, 1, "length");
        makeSpinner(2, 3, 2, "time");
        
        adapter[3][0][0] = ArrayAdapter.createFromResource(this, R.array.unit_velocity, android.R.layout.simple_spinner_item);
        adapter[3][0][1] = ArrayAdapter.createFromResource(this, R.array.unit_time, android.R.layout.simple_spinner_item);
        makeSpinner(3, 0, 0, "velocity");
        makeSpinner(3, 0, 1, "time");
        */
        category1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	page1.setVisibility(0);
            	page2.setVisibility(8);
            	page3.setVisibility(8);
            }
        });  
        
        category2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	page1.setVisibility(8);
            	page2.setVisibility(0);
            	page3.setVisibility(8);
            }
        });  
        
        category3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	page1.setVisibility(8);
            	page2.setVisibility(8);
            	page3.setVisibility(0);
            }
        });  
 
        P1_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	option1=1;
            	cat1_1.setVisibility(0);
            	cat1_2.setVisibility(8);
            	cat1_3.setVisibility(8);
            	cat1_4.setVisibility(8);
            	cat1_5.setVisibility(8);
            	cat1_6.setVisibility(8);
            	P1_1.invalidate();
            	P1_2.invalidate();
            	P1_3.invalidate();
            	P1_4.invalidate();
            	P1_5.invalidate();
            	P1_6.invalidate();
            	P1_1.getBackground().setColorFilter(fGreen);
            	P1_2.getBackground().clearColorFilter();
            	P1_3.getBackground().clearColorFilter();
            	P1_4.getBackground().clearColorFilter();
            	P1_5.getBackground().clearColorFilter();
            	P1_6.getBackground().clearColorFilter();
            	solve.setEnabled(false);
            }
        });  
        
        P1_2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	option1=2;
            	cat1_1.setVisibility(8);
            	cat1_2.setVisibility(0);
            	cat1_3.setVisibility(8);
            	cat1_4.setVisibility(8);
            	cat1_5.setVisibility(8);
            	cat1_6.setVisibility(8);
            	P1_1.invalidate();
            	P1_2.invalidate();
            	P1_3.invalidate();
            	P1_4.invalidate();
            	P1_5.invalidate();
            	P1_6.invalidate();
            	P1_1.getBackground().clearColorFilter();
            	P1_2.getBackground().setColorFilter(fGreen);
            	P1_3.getBackground().clearColorFilter();
            	P1_4.getBackground().clearColorFilter();
            	P1_5.getBackground().clearColorFilter();
            	P1_6.getBackground().clearColorFilter();
            	solve.setEnabled(false);
            }
        });
        
        P1_3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	option1=3;
            	cat1_1.setVisibility(8);
            	cat1_2.setVisibility(8);
            	cat1_3.setVisibility(0);
            	cat1_4.setVisibility(8);
            	cat1_5.setVisibility(8);
            	cat1_6.setVisibility(8);
            	P1_1.invalidate();
            	P1_2.invalidate();
            	P1_3.invalidate();
            	P1_4.invalidate();
            	P1_5.invalidate();
            	P1_6.invalidate();
            	P1_1.getBackground().clearColorFilter();
            	P1_2.getBackground().clearColorFilter();
            	P1_3.getBackground().setColorFilter(fGreen);
            	P1_4.getBackground().clearColorFilter();
            	P1_5.getBackground().clearColorFilter();
            	P1_6.getBackground().clearColorFilter();
            	solve.setEnabled(false);
            }
        }); 
        
        P1_4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	option1=4;
            	cat1_1.setVisibility(8);
            	cat1_2.setVisibility(8);
            	cat1_3.setVisibility(8);
            	cat1_4.setVisibility(0);
            	cat1_5.setVisibility(8);
            	cat1_6.setVisibility(8);
            	P1_1.invalidate();
            	P1_2.invalidate();
            	P1_3.invalidate();
            	P1_4.invalidate();
            	P1_5.invalidate();
            	P1_6.invalidate();
            	P1_1.getBackground().clearColorFilter();
            	P1_2.getBackground().clearColorFilter();
            	P1_3.getBackground().clearColorFilter();
            	P1_4.getBackground().setColorFilter(fGreen);
            	P1_5.getBackground().clearColorFilter();
            	P1_6.getBackground().clearColorFilter();
            	solve.setEnabled(false);
            }
        }); 
        
        P1_5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	option1=5;
            	cat1_1.setVisibility(8);
            	cat1_2.setVisibility(8);
            	cat1_3.setVisibility(8);
            	cat1_4.setVisibility(8);
            	cat1_5.setVisibility(0);
            	cat1_6.setVisibility(8);
            	P1_1.invalidate();
            	P1_2.invalidate();
            	P1_3.invalidate();
            	P1_4.invalidate();
            	P1_5.invalidate();
            	P1_6.invalidate();
            	P1_1.getBackground().clearColorFilter();
            	P1_2.getBackground().clearColorFilter();
            	P1_3.getBackground().clearColorFilter();
            	P1_4.getBackground().clearColorFilter();
            	P1_5.getBackground().setColorFilter(fGreen);
            	P1_6.getBackground().clearColorFilter();
            	solve.setEnabled(false);
            }
        });  
        
        P1_6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	option1=6;
            	cat1_1.setVisibility(8);
            	cat1_2.setVisibility(8);
            	cat1_3.setVisibility(8);
            	cat1_4.setVisibility(8);
            	cat1_5.setVisibility(8);
            	cat1_6.setVisibility(0);
            	P1_1.invalidate();
            	P1_2.invalidate();
            	P1_3.invalidate();
            	P1_4.invalidate();
            	P1_5.invalidate();
            	P1_6.invalidate();
            	P1_1.getBackground().clearColorFilter();
            	P1_2.getBackground().clearColorFilter();
            	P1_3.getBackground().clearColorFilter();
            	P1_4.getBackground().clearColorFilter();
            	P1_5.getBackground().clearColorFilter();
            	P1_6.getBackground().setColorFilter(fGreen);
            	solve.setEnabled(false);
            }
        });  
        
        P2_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	option2=1;
            	cat2_1.setVisibility(0);
            	cat2_2.setVisibility(8);
            	P2_1.invalidate();
            	P2_2.invalidate();
            	P2_1.getBackground().setColorFilter(fGreen);
            	P2_2.getBackground().clearColorFilter();
            	solve.setEnabled(false);
            }
        });  
        
        P2_2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	option2=2;
            	cat2_1.setVisibility(8);
            	cat2_2.setVisibility(0);
            	P2_1.invalidate();
            	P2_2.invalidate();
            	P2_1.getBackground().clearColorFilter();
            	P2_2.getBackground().setColorFilter(fGreen);
            	solve.setEnabled(false);
            }
        });  
        
        P3_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	option3=1;
            	cat3_1.setVisibility(0);
            	cat3_2.setVisibility(8);
            	P3_1.invalidate();
            	P3_2.invalidate();
            	P3_1.getBackground().setColorFilter(fGreen);
            	P3_2.getBackground().clearColorFilter();
            	solve.setEnabled(false);
            }
        });  
        
        P3_2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	option3=2;
            	cat3_1.setVisibility(8);
            	cat3_2.setVisibility(0);
            	P3_1.invalidate();
            	P3_2.invalidate();
            	P3_1.getBackground().clearColorFilter();
            	P3_2.getBackground().setColorFilter(fGreen);
            	solve.setEnabled(false);
            }
        });  
     
        solve.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            localyticsSession.tagEvent(PhysicsInclineSimpleActivity.SOLVE_SIMPLE_INCLINE_CLICK);
            //type="INCLINE";
            savePrefs();
            try
    		{
    		if ((new Date().getTime()-PhysicsActivity.adTimer.getTime())/1000<adFreeTimeSec)
    		{
    			localyticsSession.tagEvent(PhysicsInclineSimpleActivity.AD_SKIP);
    			if (answerChoice==0)
    			{
    			startActivity(new Intent(PhysicsInclineSimpleActivity.this, PhysicsSolverSplitActivity.class));
    			}
    			else
    			{
    			startActivity(new Intent(PhysicsInclineSimpleActivity.this, PhysicsSolverActivity.class));	
    			}
    		}
    		else
    		{
    			startActivity(new Intent(PhysicsInclineSimpleActivity.this, PhysicsAdActivity.class));
    		}
    		}
    		catch(RuntimeException e)
    		{
    			;
    			startActivity(new Intent(PhysicsInclineSimpleActivity.this, PhysicsAdActivity.class));
    		}
            }
        });    
        
        test.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	if (isReady==0)
            	{
	            	isError=0;
	        		category1.invalidate();
	        		category2.invalidate();
	        		category3.invalidate();
	            	category1.getBackground().setColorFilter(fRed);
	            	category2.getBackground().setColorFilter(fRed);
	            	category3.getBackground().setColorFilter(fRed);
	            	if(option1==0)
	            	{
	            		isError=1;
	            		solve.setEnabled(false);
	            	}
	            	else if(option1==1)
	            	{
	            	try {
	            		TEST.setText(Double.valueOf(E1_1_1.getText().toString()).toString());
	            		TEST.setText(Double.valueOf(E1_1_2.getText().toString()).toString());
	            		category1.invalidate();
	            		category1.getBackground().setColorFilter(fGreen);
	            		u1=factor[0][0][0]*Double.valueOf(E1_1_1.getText().toString());
	            		u2=factor[0][0][1]*Double.valueOf(E1_1_2.getText().toString());
	            	}
	            	catch (NumberFormatException e) {
	            		isError=1;
	            		solve.setEnabled(false);
	            	}
	            	}
	            	else if(option1==2)
	            	{
	            	try {
	            		TEST.setText(Double.valueOf(E1_2_1.getText().toString()).toString());
	            		TEST.setText(Double.valueOf(E1_2_2.getText().toString()).toString());
	            		category1.invalidate();
	            		category1.getBackground().setColorFilter(fGreen);
	            		u1=factor[0][1][0]*Double.valueOf(E1_2_1.getText().toString());
	            		u2=factor[0][1][1]*Double.valueOf(E1_2_2.getText().toString());
	            	}
	            	catch (NumberFormatException e) {
	            		isError=1;
	            		solve.setEnabled(false);
	            	}
	            	}
	            	else if(option1==3)
	            	{
	            	try {
	            		TEST.setText(Double.valueOf(E1_3_1.getText().toString()).toString());
	            		TEST.setText(Double.valueOf(E1_3_2.getText().toString()).toString());
	            		category1.invalidate();
	            		category1.getBackground().setColorFilter(fGreen);
	            		u1=factor[0][2][0]*Double.valueOf(E1_3_1.getText().toString());
	            		u2=factor[0][2][1]*Double.valueOf(E1_3_2.getText().toString());
	            	}
	            	catch (NumberFormatException e) {
	            		isError=1;
	            		solve.setEnabled(false);
	            	}
	            	}
	            	else if(option1==4)
	            	{
	            	try {
	            		TEST.setText(Double.valueOf(E1_4_1.getText().toString()).toString());
	            		TEST.setText(Double.valueOf(E1_4_2.getText().toString()).toString());
	            		category1.invalidate();
	            		category1.getBackground().setColorFilter(fGreen);
	            		u1=factor[0][3][0]*Double.valueOf(E1_4_1.getText().toString());
	            		u2=factor[0][3][1]*Double.valueOf(E1_4_2.getText().toString());
	            	}
	            	catch (NumberFormatException e) {
	            		isError=1;
	            		solve.setEnabled(false);
	            	}
	            	}
	            	else if(option1==5)
	            	{
	            	try {
	            		TEST.setText(Double.valueOf(E1_5_1.getText().toString()).toString());
	            		TEST.setText(Double.valueOf(E1_5_2.getText().toString()).toString());
	            		category1.invalidate();
	            		category1.getBackground().setColorFilter(fGreen);
	            		u1=factor[0][4][0]*Double.valueOf(E1_5_1.getText().toString());
	            		u2=factor[0][4][1]*Double.valueOf(E1_5_2.getText().toString());
	            	}
	            	catch (NumberFormatException e) {
	            		isError=1;
	            		solve.setEnabled(false);
	            	}
	            	}
	            	else if(option1==6)
	            	{
	            	try {
	            		TEST.setText(Double.valueOf(E1_6_1.getText().toString()).toString());
	            		TEST.setText(Double.valueOf(E1_6_2.getText().toString()).toString());
	            		category1.invalidate();
	            		category1.getBackground().setColorFilter(fGreen);
	            		u1=factor[0][5][0]*Double.valueOf(E1_6_1.getText().toString());
	            		u2=factor[0][5][1]*Double.valueOf(E1_6_2.getText().toString());
	            	}
	            	catch (NumberFormatException e) {
	            		isError=1;
	            		solve.setEnabled(false);
	            	}
	            	}
	            	if(option2==0)
	            	{
	            		isError=1;
	            		solve.setEnabled(false);
	            	}
	            	else if(option2==1)
	            	{
	            	try {
	            		TEST.setText(Double.valueOf(E2_1_1.getText().toString()).toString());
	            		category2.invalidate();
	            		category2.getBackground().setColorFilter(fGreen);
	            		u3=factor[1][0][0]*Double.valueOf(E2_1_1.getText().toString());
	            	}
	            	catch (NumberFormatException e) {
	            		isError=1;
	            		solve.setEnabled(false);
	            	}
	            	}
	            	else if(option2==2)
	            	{
	            	try {
	            		TEST.setText(Double.valueOf(E2_2_1.getText().toString()).toString());
	            		category2.invalidate();
	            		category2.getBackground().setColorFilter(fGreen);
	            		u3=factor[1][1][0]*Double.valueOf(E2_2_1.getText().toString());
	            	}
	            	catch (NumberFormatException e) {
	            		isError=1;
	            		solve.setEnabled(false);
	            	}
	            	}
	            	if(option3==0)
	            	{
	            		isError=1;
	            		solve.setEnabled(false);
	            	}
	            	else if(option3==1)
	            	{
	            	try {
	            		TEST.setText(Double.valueOf(E3_1_1.getText().toString()).toString());
	            		category3.invalidate();
	            		category3.getBackground().setColorFilter(fGreen);
	            		u4=factor[2][0][0]*Double.valueOf(E3_1_1.getText().toString());
	            	}
	            	catch (NumberFormatException e) {
	            		isError=1;
	            		solve.setEnabled(false);
	            	}
	            	}
	            	else if(option3==2)
	            	{
	            	try {
	            		TEST.setText(Double.valueOf(E3_2_1.getText().toString()).toString());
	            		category3.invalidate();
	            		category3.getBackground().setColorFilter(fGreen);
	            		u4=factor[2][1][0]*Double.valueOf(E3_2_1.getText().toString());
	            	}
	            	catch (NumberFormatException e) {
	            		isError=1;
	            		solve.setEnabled(false);
	            	}
	            	}
	            	TEST.setText("Missing/invalid input in red categories.");
	            	if (isError==0)
	            	{           
	            		isReady=1;
	            		test.setText("EDIT INPUTS");
	            		TEST.setText("You may proceed by clicking 'SOLVE'.");
	            		enableDisableView(inputs,false);
	    				solve.setEnabled(true);
	    				
	            	}
            }
            else
        	{
        		isReady=0;
        		solve.setEnabled(false);
        		test.setText("TEST");
        		TEST.setText("Complete one page in every catagory.");
        		//inputs.setEnabled(true);
        		enableDisableView(inputs,true);
        	}
            }
        });            
    }
    
    public void enableDisableView(View view, boolean enabled) {
        view.setEnabled(enabled);

        if ( view instanceof ViewGroup ) {
            ViewGroup group = (ViewGroup)view;

            for ( int idx = 0 ; idx < group.getChildCount() ; idx++ ) {
                enableDisableView(group.getChildAt(idx), enabled);
            }
        }
    }
    
    public void makeSpinner(final int i, final int j, final int k, final String unit) {
        adapter[i][j][k].setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        S[i][j][k].setAdapter(adapter[i][j][k]);
        S[i][j][k].setSelection(PhysicsActivity.selection[i][j][k]);
        
        if ("length".equals(unit)){
	        S[i][j][k].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
	            	switch (pos)
	            	{
	            	case 0: factor[i][j][k]=1; break;
	            	case 1: factor[i][j][k]=0.01; break;
	            	case 2: factor[i][j][k]=0.001; break;
                	case 3: factor[i][j][k]=1000; break;
                	case 4: factor[i][j][k]=0.0254; break;
                	case 5: factor[i][j][k]=0.3048; break;
	            	}
	            	selection[i][j][k]=pos;
	            }
	            public void onNothingSelected(AdapterView<?> parent) {
	            	factor[i][j][k]=1;
	            }
	        });
        }
        else if ("time".equals(unit)){
        	S[i][j][k].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                	switch (pos)
                	{
                	case 0: factor[i][j][k]=1; break;
                	case 1: factor[i][j][k]=0.001; break;
                	}
                	selection[i][j][k]=pos;
                }
                public void onNothingSelected(AdapterView<?> parent) {
                	factor[i][j][k]=1;
                }
            });
        }
        else if ("velocity".equals(unit)){
        	S[i][j][k].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                	switch (pos)
                	{
                	case 0: factor[i][j][k]=1; break;
                	case 1: factor[i][j][k]=0.01; break;
                	case 2: factor[i][j][k]=0.001; break;
                	case 3: factor[i][j][k]=1000; break;
                	case 4: factor[i][j][k]=0.0254; break;
                	case 5: factor[i][j][k]=0.3048; break;
                	}
                	selection[i][j][k]=pos;
                }
                public void onNothingSelected(AdapterView<?> parent) {
                	factor[i][j][k]=1;
                }
            });
        }
        else if ("angle".equals(unit)){
        	S[i][j][k].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                	switch (pos)
                	{
                	case 0: factor[i][j][k]=1; break;
                	case 1: factor[i][j][k]=0.017453292519943295769236907684886; break;
                	}
                	selection[i][j][k]=pos;
                }
                public void onNothingSelected(AdapterView<?> parent) {
                	factor[i][j][k]=1;
                }
            });
        }
        else if ("mass".equals(unit)){
        	S[i][j][k].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                	switch (pos)
                	{
                	case 0: factor[i][j][k]=1; break;
                	case 1: factor[i][j][k]=0.001; break;
                	}
                	selection[i][j][k]=pos;
                }
                public void onNothingSelected(AdapterView<?> parent) {
                	factor[i][j][k]=1;
                }
            });
        }
        else if ("force".equals(unit)){
        	S[i][j][k].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                	switch (pos)
                	{
                	case 0: factor[i][j][k]=1; break;
                	case 1: factor[i][j][k]=4.44822162; break;
                	}
                	selection[i][j][k]=pos;
                }
                public void onNothingSelected(AdapterView<?> parent) {
                	factor[i][j][k]=1;
                }
            });
        }
    }
}
