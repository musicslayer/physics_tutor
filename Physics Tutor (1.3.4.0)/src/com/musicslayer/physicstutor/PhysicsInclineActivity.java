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

public class PhysicsInclineActivity extends PhysicsActivity{
	private final static String SOLVE_INCLINE_CLICK = "solve_incline_click";
	private final static String AD_SKIP = "ad_skip";
	private LocalyticsSession localyticsSession;
	Spinner S[][][];
	ArrayAdapter<?> adapter[][][];
    TextView TEST, constants;
    Button solve, test;
    Button category1, category2, category3, category4, P1_1, P1_2, P1_3, P1_4, P2_1, P2_2, P2_3, P2_4, P3_1, P3_2, P3_3, P3_4, P4_1;
    ScrollView cat1_1, cat1_2, cat1_3, cat1_4, cat2_1, cat2_2, cat2_3, cat2_4, cat3_1, cat3_2, cat3_3, cat3_4, cat4_1;
    LinearLayout page1, page2, page3, page4, inputs;
    LightingColorFilter fGreen, fRed, fYellow;
    EditText E1_1_1, E1_1_2, E1_1_3, E1_1_4, E1_2_1, E1_2_2, E1_2_3, E1_2_4, E1_3_1, E1_3_2, E1_3_3, E1_3_4, E1_4_1, E1_4_2, E1_4_3, E1_4_4, E2_1_1, E2_1_2, E2_1_3, E2_2_1, E2_2_2, E2_2_3, E2_3_1, E2_3_2, E2_3_3, E2_3_4, E2_4_1, E2_4_2, E2_4_3, E3_1_1, E3_1_2, E3_1_3, E3_2_1, E3_2_2, E3_2_3, E3_3_1, E3_3_2, E3_3_3, E3_4_1, E3_4_2, E3_4_3, E4_1_1, E4_1_2;
    
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
		startActivity(new Intent(PhysicsInclineActivity.this, PhysicsInfoActivity.class));
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
        setContentView(R.layout.solve_incline);
        setTitle("Physics Tutor - Incline Problem");
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
        //category2=(Button) findViewById(R.id.Button_catagory2);
        category3=(Button) findViewById(R.id.Button_catagory3);
        category4=(Button) findViewById(R.id.Button_catagory4);
        cat1_1=(ScrollView) findViewById(R.id.ScrollView01_1);
        cat2_1=(ScrollView) findViewById(R.id.ScrollView02_1);
        cat2_2=(ScrollView) findViewById(R.id.ScrollView02_2);
        cat3_1=(ScrollView) findViewById(R.id.ScrollView03_1);
        cat3_2=(ScrollView) findViewById(R.id.ScrollView03_2);
        cat3_3=(ScrollView) findViewById(R.id.ScrollView03_3);
        cat3_4=(ScrollView) findViewById(R.id.ScrollView03_4);
        cat4_1=(ScrollView) findViewById(R.id.ScrollView04_1);
        page1=(LinearLayout) findViewById(R.id.LinearLayoutW);
        page2=(LinearLayout) findViewById(R.id.LinearLayoutX);
        page3=(LinearLayout) findViewById(R.id.LinearLayoutY);
        page4=(LinearLayout) findViewById(R.id.LinearLayoutZ);
        inputs=(LinearLayout) findViewById(R.id.LinearLayout_Inputs);
        P1_1=(Button) findViewById(R.id.P1_1);
        P2_1=(Button) findViewById(R.id.P2_1);
        P2_2=(Button) findViewById(R.id.P2_2);
        P3_1=(Button) findViewById(R.id.P3_1);
        P3_2=(Button) findViewById(R.id.P3_2);
        P3_3=(Button) findViewById(R.id.P3_3);
        P3_4=(Button) findViewById(R.id.P3_4);
        P4_1=(Button) findViewById(R.id.P4_1);
        E1_1_1=(EditText) findViewById(R.id.EditText_1_1_1);  
        E2_1_1=(EditText) findViewById(R.id.EditText_2_1_1);
        E2_2_1=(EditText) findViewById(R.id.EditText_2_2_1);
        E3_1_1=(EditText) findViewById(R.id.EditText_3_1_1);
        E3_1_2=(EditText) findViewById(R.id.EditText_3_1_2);
        E3_1_3=(EditText) findViewById(R.id.EditText_3_1_3);
        E3_2_1=(EditText) findViewById(R.id.EditText_3_2_1);
        E3_2_2=(EditText) findViewById(R.id.EditText_3_2_2);
        E3_2_3=(EditText) findViewById(R.id.EditText_3_2_3);
        E3_3_1=(EditText) findViewById(R.id.EditText_3_3_1);
        E3_3_2=(EditText) findViewById(R.id.EditText_3_3_2);
        E3_3_3=(EditText) findViewById(R.id.EditText_3_3_3);
        E3_4_1=(EditText) findViewById(R.id.EditText_3_4_1);
        E3_4_2=(EditText) findViewById(R.id.EditText_3_4_2);
        E3_4_3=(EditText) findViewById(R.id.EditText_3_4_3);
        E4_1_1=(EditText) findViewById(R.id.EditText_4_1_1);
        E4_1_2=(EditText) findViewById(R.id.EditText_4_1_2);
        fGreen = new LightingColorFilter(0xFFFFFF, 0x00FF00);
        fRed = new LightingColorFilter(0xFFFFFF, 0xFF0000);
        fYellow = new LightingColorFilter(0xFFFFFF, 0xFFFF00);
        solve.getBackground().setColorFilter(fYellow);
        test.getBackground().setColorFilter(fYellow);
        instructions = new String[21];
        helperImage = new int[21];
        //constants.setText("Constant g = "+String.format("%.4f", constant_g)+" m/s^2");
        constants.setVisibility(8);
        factor = new double[4][4][4];
	    selection = new int[4][4][4];
        
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
        S = new Spinner[4][4][4];
        adapter = new ArrayAdapter<?>[4][4][4];
        for (i=0;i<4;i++)
        {
        for (j=0;j<4;j++)
        {
        for (k=0;k<4;k++)
        {
        	S[i][j][k] = (Spinner) findViewById(getResources().getIdentifier("Spinner_"+Integer.toString(i)+"_"+Integer.toString(j)+"_"+Integer.toString(k), "id", getPackageName()));
        }
        }
        }
        
        factor[0][0][0]=1;
        
        adapter[2][0][0] = ArrayAdapter.createFromResource(this, R.array.unit_length, android.R.layout.simple_spinner_item);
        adapter[2][0][1] = ArrayAdapter.createFromResource(this, R.array.unit_time, android.R.layout.simple_spinner_item);
        adapter[2][0][2] = ArrayAdapter.createFromResource(this, R.array.unit_angle, android.R.layout.simple_spinner_item);
        makeSpinner(2, 0, 0, "length");
        makeSpinner(2, 0, 1, "time");
        makeSpinner(2, 0, 2, "angle");
        S[2][0][0].setSelection(defaultLength);
        S[2][0][1].setSelection(defaultTime);
        S[2][0][2].setSelection(defaultAngle);
        
        adapter[2][1][0] = ArrayAdapter.createFromResource(this, R.array.unit_length, android.R.layout.simple_spinner_item);
        adapter[2][1][1] = ArrayAdapter.createFromResource(this, R.array.unit_time, android.R.layout.simple_spinner_item);
        adapter[2][1][2] = ArrayAdapter.createFromResource(this, R.array.unit_angle, android.R.layout.simple_spinner_item);
        makeSpinner(2, 1, 0, "length");
        makeSpinner(2, 1, 1, "time");
        makeSpinner(2, 1, 2, "angle");
        S[2][1][0].setSelection(defaultLength);
        S[2][1][1].setSelection(defaultTime);
        S[2][1][2].setSelection(defaultAngle);
        
        adapter[2][2][0] = ArrayAdapter.createFromResource(this, R.array.unit_length, android.R.layout.simple_spinner_item);
        adapter[2][2][1] = ArrayAdapter.createFromResource(this, R.array.unit_time, android.R.layout.simple_spinner_item);
        adapter[2][2][2] = ArrayAdapter.createFromResource(this, R.array.unit_angle, android.R.layout.simple_spinner_item);
        makeSpinner(2, 2, 0, "length");
        makeSpinner(2, 2, 1, "time");
        makeSpinner(2, 2, 2, "angle");
        S[2][2][0].setSelection(defaultLength);
        S[2][2][1].setSelection(defaultTime);
        S[2][2][2].setSelection(defaultAngle);
        
        adapter[2][3][0] = ArrayAdapter.createFromResource(this, R.array.unit_length, android.R.layout.simple_spinner_item);
        adapter[2][3][1] = ArrayAdapter.createFromResource(this, R.array.unit_length, android.R.layout.simple_spinner_item);
        adapter[2][3][2] = ArrayAdapter.createFromResource(this, R.array.unit_time, android.R.layout.simple_spinner_item);
        makeSpinner(2, 3, 0, "length");
        makeSpinner(2, 3, 1, "length");
        makeSpinner(2, 3, 2, "time");
        S[2][3][0].setSelection(defaultLength);
        S[2][3][1].setSelection(defaultLength);
        S[2][3][2].setSelection(defaultTime);
        
        adapter[3][0][0] = ArrayAdapter.createFromResource(this, R.array.unit_velocity, android.R.layout.simple_spinner_item);
        adapter[3][0][1] = ArrayAdapter.createFromResource(this, R.array.unit_time, android.R.layout.simple_spinner_item);
        makeSpinner(3, 0, 0, "velocity");
        makeSpinner(3, 0, 1, "time");
        S[3][0][0].setSelection(defaultVelocity);
        S[3][0][1].setSelection(defaultTime);
        
        category1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	page1.setVisibility(0);
            	page2.setVisibility(8);
            	page3.setVisibility(8);
            	page4.setVisibility(8);
            }
        });  
        
        /*
        category2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	page1.setVisibility(8);
            	page2.setVisibility(0);
            	page3.setVisibility(8);
            	page4.setVisibility(8);
            }
        });
        */  
        
        category3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	page1.setVisibility(8);
            	page2.setVisibility(8);
            	page3.setVisibility(0);
            	page4.setVisibility(8);
            }
        });  
        
        category4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	page1.setVisibility(8);
            	page2.setVisibility(8);
            	page3.setVisibility(8);
            	page4.setVisibility(0);
            }
        });  
        
        P1_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	option1=1;
            	cat1_1.setVisibility(0);
            	P1_1.invalidate();
            	P1_1.getBackground().setColorFilter(fGreen);
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
            	cat3_3.setVisibility(8);
            	cat3_4.setVisibility(8);
            	P3_1.invalidate();
            	P3_2.invalidate();
            	P3_3.invalidate();
            	P3_4.invalidate();
            	P3_1.getBackground().setColorFilter(fGreen);
            	P3_2.getBackground().clearColorFilter();
            	P3_3.getBackground().clearColorFilter();
            	P3_4.getBackground().clearColorFilter();
            	solve.setEnabled(false);
            }
        }); 
        
        P3_2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	option3=2;
            	cat3_1.setVisibility(8);
            	cat3_2.setVisibility(0);
            	cat3_3.setVisibility(8);
            	cat3_4.setVisibility(8);
            	P3_1.invalidate();
            	P3_2.invalidate();
            	P3_3.invalidate();
            	P3_4.invalidate();
            	P3_1.getBackground().clearColorFilter();
            	P3_2.getBackground().setColorFilter(fGreen);
            	P3_3.getBackground().clearColorFilter();
            	P3_4.getBackground().clearColorFilter();
            	solve.setEnabled(false);
            }
        }); 
        
        P3_3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	option3=3;
            	cat3_1.setVisibility(8);
            	cat3_2.setVisibility(8);
            	cat3_3.setVisibility(0);
            	cat3_4.setVisibility(8);
            	P3_1.invalidate();
            	P3_2.invalidate();
            	P3_3.invalidate();
            	P3_4.invalidate();
            	P3_1.getBackground().clearColorFilter();
            	P3_2.getBackground().clearColorFilter();
            	P3_3.getBackground().setColorFilter(fGreen);
            	P3_4.getBackground().clearColorFilter();
            	solve.setEnabled(false);
            }
        });  
        
        P3_4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	option3=4;
            	cat3_1.setVisibility(8);
            	cat3_2.setVisibility(8);
            	cat3_3.setVisibility(8);
            	cat3_4.setVisibility(0);
            	P3_1.invalidate();
            	P3_2.invalidate();
            	P3_3.invalidate();
            	P3_4.invalidate();
            	P3_1.getBackground().clearColorFilter();
            	P3_2.getBackground().clearColorFilter();
            	P3_3.getBackground().clearColorFilter();
            	P3_4.getBackground().setColorFilter(fGreen);
            	solve.setEnabled(false);
            }
        });  
        
        P4_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	option4=1;
            	cat4_1.setVisibility(0);
            	P4_1.invalidate();
            	P4_1.getBackground().setColorFilter(fGreen);
            	solve.setEnabled(false);
            }
        });  
     
        solve.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            localyticsSession.tagEvent(PhysicsInclineActivity.SOLVE_INCLINE_CLICK);
            //type="INCLINE";
            savePrefs();
            try
    		{
    		if ((new Date().getTime()-PhysicsActivity.adTimer.getTime())/1000<adFreeTimeSec)
    		{
    			localyticsSession.tagEvent(PhysicsInclineActivity.AD_SKIP);
    			if (answerChoice==0)
    			{
    			startActivity(new Intent(PhysicsInclineActivity.this, PhysicsSolverSplitActivity.class));
    			}
    			else
    			{
    			startActivity(new Intent(PhysicsInclineActivity.this, PhysicsSolverActivity.class));	
    			}
    		}
    		else
    		{
    			startActivity(new Intent(PhysicsInclineActivity.this, PhysicsAdActivity.class));
    		}
    		}
    		catch(RuntimeException e)
    		{
    			;
    			startActivity(new Intent(PhysicsInclineActivity.this, PhysicsAdActivity.class));
    		}
            }
        });    
        
        test.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	if (isReady==0)
            	{
	            	isError=0;
	        		category1.invalidate();
	        		//category2.invalidate();
	        		category3.invalidate();
	        		category4.invalidate();
	            	category1.getBackground().setColorFilter(fRed);
	            	//category2.getBackground().setColorFilter(fRed);
	            	category3.getBackground().setColorFilter(fRed);
	            	category4.getBackground().setColorFilter(fRed);
	            	if(option1==0)
	            	{
	            		isError=1;
	            		solve.setEnabled(false);
	            	}
	            	else if(option1==1)
	            	{
	            	try {
	            		TEST.setText(Double.valueOf(E1_1_1.getText().toString()).toString());
	            		//TEST.setText(Double.valueOf(E1_1_2.getText().toString()).toString());
	            		//TEST.setText(Double.valueOf(E1_1_3.getText().toString()).toString());
	            		//TEST.setText(Double.valueOf(E1_1_4.getText().toString()).toString());
	            		category1.invalidate();
	            		//category1.getBackground().clearColorFilter();
	            		category1.getBackground().setColorFilter(fGreen);
	            		u1=factor[0][0][0]*Double.valueOf(E1_1_1.getText().toString());
	            		//u2=factor[0][0][1]*Double.valueOf(E1_1_2.getText().toString());
	            		//u3=factor[0][0][2]*Double.valueOf(E1_1_3.getText().toString());
	            		//u4=factor[0][0][3]*Double.valueOf(E1_1_4.getText().toString());
	            	}
	            	catch (NumberFormatException e) {
	            		isError=1;
	            		solve.setEnabled(false);
	            	}
	            	}
	            	/*
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
	            		category2.getBackground().clearColorFilter();
	            		u2=factor[1][0][0]*Double.valueOf(E2_1_1.getText().toString());
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
	            		category2.getBackground().clearColorFilter();
	            		u2=factor[1][1][0]*Double.valueOf(E2_2_1.getText().toString());
	            	}
	            	catch (NumberFormatException e) {
	            		isError=1;
	            		solve.setEnabled(false);
	            	}
	            	}
	            	*/
	            	if(option3==0)
	            	{
	            		isError=1;
	            		solve.setEnabled(false);
	            	}
	            	else if(option3==1)
	            	{
	            	try {
	            		TEST.setText(Double.valueOf(E3_1_1.getText().toString()).toString());
	            		TEST.setText(Double.valueOf(E3_1_2.getText().toString()).toString());
	            		TEST.setText(Double.valueOf(E3_1_3.getText().toString()).toString());
	            		category3.invalidate();
	            		//category3.getBackground().clearColorFilter();
	            		category3.getBackground().setColorFilter(fGreen);
	            		u3=factor[2][0][0]*Double.valueOf(E3_1_1.getText().toString());
	            		u4=factor[2][0][1]*Double.valueOf(E3_1_2.getText().toString());
	            		u5=factor[2][0][2]*Double.valueOf(E3_1_3.getText().toString());
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
	            		TEST.setText(Double.valueOf(E3_2_2.getText().toString()).toString());
	            		TEST.setText(Double.valueOf(E3_2_3.getText().toString()).toString());
	            		category3.invalidate();
	            		//category3.getBackground().clearColorFilter();
	            		category3.getBackground().setColorFilter(fGreen);
	            		u3=factor[2][1][0]*Double.valueOf(E3_2_1.getText().toString());
	            		u4=factor[2][1][1]*Double.valueOf(E3_2_2.getText().toString());
	            		u5=factor[2][1][2]*Double.valueOf(E3_2_3.getText().toString());
	            	}
	            	catch (NumberFormatException e) {
	            		isError=1;
	            		solve.setEnabled(false);
	            	}
	            	}
	            	else if(option3==3)
	            	{
	            	try {
	            		TEST.setText(Double.valueOf(E3_3_1.getText().toString()).toString());
	            		TEST.setText(Double.valueOf(E3_3_2.getText().toString()).toString());
	            		TEST.setText(Double.valueOf(E3_3_3.getText().toString()).toString());
	            		category3.invalidate();
	            		//category3.getBackground().clearColorFilter();
	            		category3.getBackground().setColorFilter(fGreen);
	            		u3=factor[2][2][0]*Double.valueOf(E3_3_1.getText().toString());
	            		u4=factor[2][2][1]*Double.valueOf(E3_3_2.getText().toString());
	            		u5=factor[2][2][2]*Double.valueOf(E3_3_3.getText().toString());
	            	}
	            	catch (NumberFormatException e) {
	            		isError=1;
	            		solve.setEnabled(false);
	            	}
	            	}
	            	else if(option3==4)
	            	{
	            	try {
	            		TEST.setText(Double.valueOf(E3_4_1.getText().toString()).toString());
	            		TEST.setText(Double.valueOf(E3_4_2.getText().toString()).toString());
	            		TEST.setText(Double.valueOf(E3_4_3.getText().toString()).toString());
	            		category3.invalidate();
	            		//category3.getBackground().clearColorFilter();
	            		category3.getBackground().setColorFilter(fGreen);
	            		u3=factor[2][3][0]*Double.valueOf(E3_4_1.getText().toString());
	            		u5=factor[2][3][1]*Double.valueOf(E3_4_2.getText().toString());
	            		u4=factor[2][3][2]*Double.valueOf(E3_4_3.getText().toString());
	            	}
	            	catch (NumberFormatException e) {
	            		isError=1;
	            		solve.setEnabled(false);
	            	}
	            	}
	            	if(option4==0)
	            	{
	            		isError=1;
	            		solve.setEnabled(false);
	            	}
	            	else if(option4==1)
	            	{
	            	try {
	            		TEST.setText(Double.valueOf(E4_1_1.getText().toString()).toString());
	            		TEST.setText(Double.valueOf(E4_1_2.getText().toString()).toString());
	            		//TEST.setText(Double.valueOf(E1_1_3.getText().toString()).toString());
	            		//TEST.setText(Double.valueOf(E1_1_4.getText().toString()).toString());
	            		category4.invalidate();
	            		//category4.getBackground().clearColorFilter();
	            		category4.getBackground().setColorFilter(fGreen);
	            		u6=factor[3][0][0]*Double.valueOf(E4_1_1.getText().toString());
	            		u7=factor[3][0][1]*Double.valueOf(E4_1_2.getText().toString());
	            		u8=0;
	            		u9=0;
	            		u10=0;
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
	    				/*
	    				double angle = 0, k, peakTime;
	    				switch (option3) {
	    				case 1:
	    				angle=u5;
	    				instructions[1]="The first piece of information we should know about an incline problem is the angle of the incline itself. We are given this in the problem.";
	    				break;
	    				case 2:
	    				angle=u5;
	    				instructions[1]="The first piece of information we should know about an incline problem is the angle of the incline itself. We are given this in the problem.";
	    				break;
	    				case 3:
	    				angle=u5;
	    				instructions[1]="The first piece of information we should know about an incline problem is the angle of the incline itself. We are given this in the problem.";
	    				break;
	    				case 4:
	    				angle=Math.atan(u5/u3);
	        			instructions[1]="The first piece of information we should know about an incline problem is the angle of the incline itself. We are given x-position and y-position at the same time, and can use them to solve for the angle of the incline.";
	        			break;
	    				}
	    				instructions[2]="For an incline problem, it is easier to consider a different coordinate system than the standard x,y system. We want one basis vector to run along the incline, and the other perpendicular to the incline (i.e. rotate the x,y system by the incline angle). Since the object does not leave the incline, we only need to worry about one coordinate, s.";
	    				instructions[3]="While the force of friction points along the incline, the force of gravity points straight down. We can project the force of gravity onto our new basis vectors.";
	    				instructions[4]="The projections allow us to find the force pushing the object down the incline, and the normal force. The force of friction will be proportional to the normal force, with proportionality constant = static coefficient of friction if the object is stationary, and kinetic coefficient of friction if the object is sliding.";
	    				instructions[5]="With Newton's 2nd law, we can find the s-acceleration of the object. Note that friction points in the opposite direction of velocity, and thus will point in a different direction depending on whether the object is sliding uphill or downhill. Thus, there are two different equations for acceleration (one for uphill motion and one for downhill motion).";
	    				if (u6>0) {
	    				k=u6+(constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))*u7;
	    				peakTime=k/(constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle));
	    				instructions[6]="We want an equation for s-velocity. We can get it from s-acceleration. Since the given velocity is positive, the object is sliding uphill at that time. Thus, we will start with the acceleration equation for uphill motion, and integrate to get velocity. Note that there is an unknown integration constant K1.";
	    				instructions[7]="Using our velocity, we can solve for the integration constant K1.";
	    				instructions[8]="The equation we have is valid for all uphill motion, but in addition, is also valid for the peak position. We can use this equation for velocity to find when the object reaches its peak position. If tp is the time the object is at its peak, then v(tp) = 0, so we can solve for the peak time, tp.";
	    				instructions[9]="Now, we wish to find the velocity for all times the object is sliding downhill. We can get this from the acceleration equation for downhill motion, but there is an integration constant K2.";
	    				instructions[10]="Since this equation for downhill velocity is also valid at the peak, we can use v(tp)=0 to solve for the integration constant K2.";
	    				}
	    				else if(u6<0) {
	    				k=u6+(constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))*u7;
	        			peakTime=k/(constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle));
	    				instructions[6]="We want an equation for s-velocity. We can get it from s-acceleration. Since the given velocity is negative, the object is sliding downhill at that time. Thus, we will start with the acceleration equation that is valid when the object slides downhill, and integrate to get velocity. Note that there is an unknown integration constant K1.";
	    				instructions[7]="Using our velocity, we can solve for the integration constant K1.";
	    				instructions[8]="The equation we have is valid for all downhill motion, but in addition, is also valid for the peak position. We can use this equation for velocity to find when the object reaches its peak position. If tp is the time the object is at its peak, then v(tp) = 0, so we can solve for the peak time, tp.";
	    				instructions[9]="Now, we wish to find the velocity for all times the object is sliding uphill. We can get this from the acceleration equation for uphill motion, but there is an integration constant K2.";
	    				instructions[10]="Since this equation for uphill velocity is also valid at the peak, we can use v(tp)=0 to solve for the integration constant K2.";
	    				}
	    				else {
	    				peakTime=u7;
	    				instructions[6]="We want an equation for s-velocity. We can get it from s-acceleration. Since the given velocity is 0, the object is exactly at the peak of its motion at that time. Because velocity must be a continuous quantity, both the uphill and downhill regions intersect at the peak, and have v(t*)=0 as a condition.";
	    				instructions[7]="First, let's deal with uphill motion. We will start with the acceleration equation for uphill motion, and integrate to get velocity. Note that there is an unknown integration constant K1.";
	    				instructions[8]="Plug in v(t*)=0 to solve for K1";
	    				instructions[9]="Next, let's deal with downhill motion. We will start with the acceleration equation for downhill motion, and integrate to get velocity. Note that there is an unknown integration constant K2.";
	    				instructions[10]="Plug in v(t*)=0 to solve for K2";
	    				}
	    				instructions[11]="We now know the velocity of the object at all times.";
	    				
	    				switch (option3) {
	    				case 1:
	    				instructions[12]="We are given the s-position at a certain time.";
	        			break;	
	    				case 2:
	    				instructions[12]="We know the x-position at a certain time, and the angle of the incline. We can use this to get the s-position at that same time.";
	    				break;
	    				case 3:
	    				instructions[12]="We know the y-position at a certain time, and the angle of the incline. We can use this to get the s-position at that same time.";
	    				break;
	    				case 4:
	    				instructions[12]="We know the x-position and y-position at a certain time. We can use this to get the s-position at that same time.";
	    				break;
	    				}	
	    				if (u4<peakTime)
	    				{
	    				instructions[13]="Next, we want an equation for s-position. We can get it from s-velocity. Since the position is given at a time before the peak time (the uphill motion region), we will start with the uphill velocity and integrate to get position. Note that there is an unknown integration constant K3.";
	    				instructions[14]="Using our s-position, we can solve for the integration constant K3.";
	    				instructions[15]="The equation we have is valid for all uphill motion, but in addition, is also valid for the peak position. We can use this equation for position and plug in the peak time to find the peak position sp.";
	    				instructions[16]="Now, we wish to find the s-position for all times the object is sliding downhill. We can get this from the s-velocity equation for downhill motion, but there is an integration constant K4.";
	    				instructions[17]="Since this equation for downhill position is also valid at the peak, we can use s(tp)=sp to solve for the integration constant K4.";
	    				}
	    				else if (u4>peakTime)
	    				{
	    				instructions[13]="Next, we want an equation for s-position. We can get it from s-velocity. Since the position is given at a time after the peak time (the downhill motion region), we will start with the downhill velocity and integrate to get position. Note that there is an unknown integration constant K3.";
	    				instructions[14]="Using our s-position, we can solve for the integration constant K3.";
	    				instructions[15]="The equation we have is valid for all downhill motion, but in addition, is also valid for the peak position. We can use this equation for position and plug in the peak time to find the peak position sp.";
	    				instructions[16]="Now, we wish to find the s-position for all times the object is sliding uphill. We can get this from the s-velocity equation for uphill motion, but there is an integration constant K4.";
	    				instructions[17]="Since this equation for uphill position is also valid at the peak, we can use s(tp)=sp to solve for the integration constant K4.";
	    				}
	    				else {
	    				instructions[13]="Next, we want an equation for s-position. We can get it from s-velocity. Since the position is given at the peak time, and position must be a continuous quantity, both the uphill and downhill regions intersect at the peak, and have s(t*)=s* as a condition.";
	    				instructions[14]="First, let's deal with uphill motion. We will start with the velocity equation for uphill motion, and integrate to get position. Note that there is an unknown integration constant K3.";
	    				instructions[15]="Plug in s(tp)=sp to solve for K3";
	    				instructions[16]="Next, let's deal with downhill motion. We will start with the velocity equation for downhill motion, and integrate to get position. Note that there is an unknown integration constant K4.";
	    				instructions[17]="Plug in s(tp)=sp to solve for K4";
	    				}
	    				instructions[18]="We now know the s-position of the object at all times";
	    				instructions[19]="Use the following equations to solve for what you need. You may also enter the value of one variable below and hit 'Solve' to get the rest of them.";
	    				*/
	    				/*
	        			helperImage[1]=R.raw.helper_proj_1;
	        			helperImage[2]=R.raw.helper_proj_2;
	        			switch (option2) {
	    	        	case 1:
	        			helperImage[3]=R.raw.helper_proj_3_1;
	        			break;
	    	        	case 2:
	            		helperImage[3]=R.raw.helper_proj_3_2;
	            		break;
	    	        	case 3:
	            		helperImage[3]=R.raw.helper_proj_3_3;
	            		break;
	    	        	case 4:
	            		helperImage[3]=R.raw.helper_proj_3_4;
	            		break;
	        			}
	        			helperImage[4]=R.raw.helper_proj_4;
	        			helperImage[5]=R.raw.helper_proj_5;
	        			helperImage[6]=R.raw.helper_proj_6;
	        			helperImage[7]=R.raw.helper_proj_7;
	        			helperImage[8]=R.raw.helper_proj_8;
	        			helperImage[9]=R.raw.helper_proj_9;
	        			switch (option2) {
	    	        	case 1:
	        			helperImage[10]=R.raw.helper_proj_10_1;
	        			break;
	    	        	case 2:
	            		helperImage[10]=R.raw.helper_proj_10_2;
	            		break;
	    	        	case 3:
	            		helperImage[10]=R.raw.helper_proj_10_3;
	            		break;
	    	        	case 4:
	            		helperImage[10]=R.raw.helper_proj_10_4;
	            		break;
	        			}
	        			helperImage[11]=R.raw.helper_proj_11;
	        			helperImage[12]=R.raw.helper_proj_12;
	        			helperImage[13]=R.raw.helper_proj_13;
	        			helperImage[14]=R.raw.helper_proj_14;
	        			*/
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
    }
}
