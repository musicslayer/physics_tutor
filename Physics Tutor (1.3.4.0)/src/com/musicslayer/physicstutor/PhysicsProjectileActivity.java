package com.musicslayer.physicstutor;

import java.util.Date;

import com.Localytics.android.*;

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

public class PhysicsProjectileActivity extends PhysicsActivity {
	private final static String SOLVE_PROJECTILE_CLICK = "solve_projectile_click";
	private final static String AD_SKIP = "ad_skip";
	private LocalyticsSession localyticsSession;
	Spinner S[][][];
	ArrayAdapter<?> adapter[][][];
    TextView TEST, constants;
    Button solve, test;
    Button category1, category2, P1_1, P2_1, P2_2, P2_3, P2_4, P2_5;
    ScrollView cat1_1, cat2_1, cat2_2, cat2_3, cat2_4, cat2_5;
    LinearLayout page1, page2, inputs;
    LightingColorFilter fGreen, fRed, fYellow;
    EditText E1_1_1, E1_1_2, E1_1_3, E1_1_4, E2_1_1, E2_1_2, E2_1_3, E2_2_1, E2_2_2, E2_2_3, E2_3_1, E2_3_2, E2_3_3, E2_3_4, E2_4_1, E2_4_2, E2_4_3, E2_5_1, E2_5_2, E2_5_3, E2_5_4;
    
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
	public void onBackPressed() {
		startActivity(new Intent(PhysicsProjectileActivity.this, PhysicsInfoActivity.class));
	return;
	}
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.solve_projectile);
        setTitle("Physics Tutor - Projectile Problem");
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
        cat1_1=(ScrollView) findViewById(R.id.ScrollView01);
        cat2_1=(ScrollView) findViewById(R.id.ScrollView02_1);
        cat2_2=(ScrollView) findViewById(R.id.ScrollView02_2);
        cat2_3=(ScrollView) findViewById(R.id.ScrollView02_3);
        cat2_4=(ScrollView) findViewById(R.id.ScrollView02_4);
        cat2_5=(ScrollView) findViewById(R.id.ScrollView02_5);
        page1=(LinearLayout) findViewById(R.id.LinearLayoutY);
        page2=(LinearLayout) findViewById(R.id.LinearLayoutZ);
        inputs=(LinearLayout) findViewById(R.id.LinearLayout_Inputs);
        P1_1=(Button) findViewById(R.id.P1_1);
        P2_1=(Button) findViewById(R.id.P2_1);
        P2_2=(Button) findViewById(R.id.P2_2);
        P2_3=(Button) findViewById(R.id.P2_3);
        P2_4=(Button) findViewById(R.id.P2_4);  
        P2_5=(Button) findViewById(R.id.P2_5);
        E1_1_1=(EditText) findViewById(R.id.EditText_1_1_1);  
        E1_1_2=(EditText) findViewById(R.id.EditText_1_1_2);
        E1_1_3=(EditText) findViewById(R.id.EditText_1_1_3);  
        E1_1_4=(EditText) findViewById(R.id.EditText_1_1_4);
        E2_1_1=(EditText) findViewById(R.id.EditText_2_1_1);
        E2_1_2=(EditText) findViewById(R.id.EditText_2_1_2);
        E2_1_3=(EditText) findViewById(R.id.EditText_2_1_3);
        E2_2_1=(EditText) findViewById(R.id.EditText_2_2_1);
        E2_2_2=(EditText) findViewById(R.id.EditText_2_2_2);
        E2_2_3=(EditText) findViewById(R.id.EditText_2_2_3);
        E2_3_1=(EditText) findViewById(R.id.EditText_2_3_1);
        E2_3_2=(EditText) findViewById(R.id.EditText_2_3_2);
        E2_3_3=(EditText) findViewById(R.id.EditText_2_3_3);
        E2_3_4=(EditText) findViewById(R.id.EditText_2_3_4);
        E2_4_1=(EditText) findViewById(R.id.EditText_2_4_1);
        E2_4_2=(EditText) findViewById(R.id.EditText_2_4_2);
        E2_4_3=(EditText) findViewById(R.id.EditText_2_4_3);
        E2_5_1=(EditText) findViewById(R.id.EditText_2_5_1);
        E2_5_2=(EditText) findViewById(R.id.EditText_2_5_2);
        E2_5_3=(EditText) findViewById(R.id.EditText_2_5_3);
        E2_5_4=(EditText) findViewById(R.id.EditText_2_5_4);
        //TB2_1_2=(ToggleButton) findViewById(R.id.ToggleButton_2_1_2);
        //TB2_2_2=(ToggleButton) findViewById(R.id.ToggleButton_2_2_2);
        //TB2_3_3=(ToggleButton) findViewById(R.id.ToggleButton_2_3_3);
        fGreen = new LightingColorFilter(0xFFFFFF, 0x00FF00);
        fRed = new LightingColorFilter(0xFFFFFF, 0xFF0000);
        fYellow = new LightingColorFilter(0xFFFFFF, 0xFFFF00);
        solve.getBackground().setColorFilter(fYellow);
        test.getBackground().setColorFilter(fYellow);
        instructions = new String[21];
        helperImage = new int[21];
        factor = new double[4][5][4];
	    selection = new int[4][5][4];
	    constants.setVisibility(8);
        
        option1=0;
        option2=0;
        option3=0;
        option4=0;
        //stotal=14;
        /*
        for (i=0;i<=20;i++)
        {
        	helperImage[i]=R.raw.default_image;
        	instructions[i]="?";
        }
        */
        S = new Spinner[4][5][4];
        adapter = new ArrayAdapter<?>[4][5][4];
        for (i=0;i<4;i++)
        {
        for (j=0;j<5;j++)
        {
        for (k=0;k<4;k++)
        {
        	S[i][j][k] = (Spinner) findViewById(getResources().getIdentifier("Spinner_"+Integer.toString(i)+"_"+Integer.toString(j)+"_"+Integer.toString(k), "id", getPackageName()));
        }
        }
        }
        j=0;
        adapter[0][0][0] = ArrayAdapter.createFromResource(this, R.array.unit_length, android.R.layout.simple_spinner_item);
        adapter[0][0][1] = ArrayAdapter.createFromResource(this, R.array.unit_time, android.R.layout.simple_spinner_item);
        adapter[0][0][2] = ArrayAdapter.createFromResource(this, R.array.unit_length, android.R.layout.simple_spinner_item);
        adapter[0][0][3] = ArrayAdapter.createFromResource(this, R.array.unit_time, android.R.layout.simple_spinner_item);
        makeSpinner(0, 0, 0, "length");
        makeSpinner(0, 0, 1, "time");
        makeSpinner(0, 0, 2, "length");
        makeSpinner(0, 0, 3, "time");
        S[0][0][0].setSelection(defaultLength);
        S[0][0][1].setSelection(defaultTime);
        S[0][0][2].setSelection(defaultLength);
        S[0][0][3].setSelection(defaultTime);
        
        adapter[1][0][0] = ArrayAdapter.createFromResource(this, R.array.unit_velocity, android.R.layout.simple_spinner_item);
        adapter[1][0][1] = ArrayAdapter.createFromResource(this, R.array.unit_angle, android.R.layout.simple_spinner_item);
        adapter[1][0][2] = ArrayAdapter.createFromResource(this, R.array.unit_time, android.R.layout.simple_spinner_item);
        makeSpinner(1, 0, 0, "velocity");
        makeSpinner(1, 0, 1, "angle");
        makeSpinner(1, 0, 2, "time");
        S[1][0][0].setSelection(defaultVelocity);
        S[1][0][1].setSelection(defaultAngle);
        S[1][0][2].setSelection(defaultTime);
        
        adapter[1][1][0] = ArrayAdapter.createFromResource(this, R.array.unit_velocity, android.R.layout.simple_spinner_item);
        adapter[1][1][1] = ArrayAdapter.createFromResource(this, R.array.unit_angle, android.R.layout.simple_spinner_item);
        adapter[1][1][2] = ArrayAdapter.createFromResource(this, R.array.unit_time, android.R.layout.simple_spinner_item);
        makeSpinner(1, 1, 0, "velocity");
        makeSpinner(1, 1, 1, "angle");
        makeSpinner(1, 1, 2, "time");
        S[1][1][0].setSelection(defaultVelocity);
        S[1][1][1].setSelection(defaultAngle);
        S[1][1][2].setSelection(defaultTime);
        
        adapter[1][2][0] = ArrayAdapter.createFromResource(this, R.array.unit_velocity, android.R.layout.simple_spinner_item);
        adapter[1][2][1] = ArrayAdapter.createFromResource(this, R.array.unit_time, android.R.layout.simple_spinner_item);
        adapter[1][2][2] = ArrayAdapter.createFromResource(this, R.array.unit_angle, android.R.layout.simple_spinner_item);
        adapter[1][2][3] = ArrayAdapter.createFromResource(this, R.array.unit_time, android.R.layout.simple_spinner_item);
        makeSpinner(1, 2, 0, "velocity");
        makeSpinner(1, 2, 1, "time");
        makeSpinner(1, 2, 2, "angle");
        makeSpinner(1, 2, 3, "time");
        S[1][2][0].setSelection(defaultVelocity);
        S[1][2][1].setSelection(defaultTime);
        S[1][2][2].setSelection(defaultAngle);
        S[1][2][3].setSelection(defaultTime);
        
        adapter[1][3][0] = ArrayAdapter.createFromResource(this, R.array.unit_velocity, android.R.layout.simple_spinner_item);
        adapter[1][3][1] = ArrayAdapter.createFromResource(this, R.array.unit_velocity, android.R.layout.simple_spinner_item);
        adapter[1][3][2] = ArrayAdapter.createFromResource(this, R.array.unit_time, android.R.layout.simple_spinner_item);
        makeSpinner(1, 3, 0, "velocity");
        makeSpinner(1, 3, 1, "velocity");
        makeSpinner(1, 3, 2, "time");
        S[1][3][0].setSelection(defaultVelocity);
        S[1][3][1].setSelection(defaultVelocity);
        S[1][3][2].setSelection(defaultTime);
        
        adapter[1][4][0] = ArrayAdapter.createFromResource(this, R.array.unit_length, android.R.layout.simple_spinner_item);
        adapter[1][4][1] = ArrayAdapter.createFromResource(this, R.array.unit_time, android.R.layout.simple_spinner_item);
        adapter[1][4][2] = ArrayAdapter.createFromResource(this, R.array.unit_length, android.R.layout.simple_spinner_item);
        adapter[1][4][3] = ArrayAdapter.createFromResource(this, R.array.unit_time, android.R.layout.simple_spinner_item);
        makeSpinner(1, 4, 0, "length");
        makeSpinner(1, 4, 1, "time");
        makeSpinner(1, 4, 2, "length");
        makeSpinner(1, 4, 3, "time");
        S[1][4][0].setSelection(defaultLength);
        S[1][4][1].setSelection(defaultTime);
        S[1][4][2].setSelection(defaultLength);
        S[1][4][3].setSelection(defaultTime);

        category1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	page1.setVisibility(0);
            	page2.setVisibility(8);
            }
        });  
        
        category2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	page1.setVisibility(8);
            	page2.setVisibility(0);
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
            	cat2_3.setVisibility(8);
            	cat2_4.setVisibility(8);
            	cat2_5.setVisibility(8);
            	P2_1.invalidate();
            	P2_2.invalidate();
            	P2_3.invalidate();
            	P2_4.invalidate();
            	P2_5.invalidate();
            	P2_1.getBackground().setColorFilter(fGreen);
            	P2_2.getBackground().clearColorFilter();
            	P2_3.getBackground().clearColorFilter();
            	P2_4.getBackground().clearColorFilter();
            	P2_5.getBackground().clearColorFilter();
            	solve.setEnabled(false);
            }
        });  
        
        P2_2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	option2=2;
            	cat2_1.setVisibility(8);
            	cat2_2.setVisibility(0);
            	cat2_3.setVisibility(8);
            	cat2_4.setVisibility(8);
            	cat2_5.setVisibility(8);
            	P2_1.invalidate();
            	P2_2.invalidate();
            	P2_3.invalidate();
            	P2_4.invalidate();
            	P2_5.invalidate();
               	P2_1.getBackground().clearColorFilter();
            	P2_2.getBackground().setColorFilter(fGreen);
            	P2_3.getBackground().clearColorFilter();
            	P2_4.getBackground().clearColorFilter();
            	P2_5.getBackground().clearColorFilter();
            	solve.setEnabled(false);
            }
        });
        
        P2_3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	option2=3;
            	cat2_1.setVisibility(8);
            	cat2_2.setVisibility(8);
            	cat2_3.setVisibility(0);
            	cat2_4.setVisibility(8);
            	cat2_5.setVisibility(8);
            	P2_1.invalidate();
            	P2_2.invalidate();
            	P2_3.invalidate();
            	P2_4.invalidate();
            	P2_5.invalidate();
               	P2_1.getBackground().clearColorFilter();
            	P2_2.getBackground().clearColorFilter();
            	P2_3.getBackground().setColorFilter(fGreen);
            	P2_4.getBackground().clearColorFilter();
            	P2_5.getBackground().clearColorFilter();
            	solve.setEnabled(false);
            }
        });
        
        P2_4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	option2=4;
            	cat2_1.setVisibility(8);
            	cat2_2.setVisibility(8);
            	cat2_3.setVisibility(8);
            	cat2_4.setVisibility(0);
            	cat2_5.setVisibility(8);
            	P2_1.invalidate();
            	P2_2.invalidate();
            	P2_3.invalidate();
            	P2_4.invalidate();
            	P2_5.invalidate();
               	P2_1.getBackground().clearColorFilter();
            	P2_2.getBackground().clearColorFilter();
            	P2_3.getBackground().clearColorFilter();
            	P2_4.getBackground().setColorFilter(fGreen);
            	P2_5.getBackground().clearColorFilter();
            	solve.setEnabled(false);
            }
        });
        
        P2_5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	option2=5;
            	cat2_1.setVisibility(8);
            	cat2_2.setVisibility(8);
            	cat2_3.setVisibility(8);
            	cat2_4.setVisibility(8);
            	cat2_5.setVisibility(0);
            	P2_1.invalidate();
            	P2_2.invalidate();
            	P2_3.invalidate();
            	P2_4.invalidate();
            	P2_5.invalidate();
               	P2_1.getBackground().clearColorFilter();
            	P2_2.getBackground().clearColorFilter();
            	P2_3.getBackground().clearColorFilter();
            	P2_4.getBackground().clearColorFilter();
            	P2_5.getBackground().setColorFilter(fGreen);
            	solve.setEnabled(false);
            }
        });
        
        solve.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            localyticsSession.tagEvent(PhysicsProjectileActivity.SOLVE_PROJECTILE_CLICK);
            //type="PROJECTILE";
            savePrefs();
            try
    		{
    		if ((new Date().getTime()-PhysicsActivity.adTimer.getTime())/1000<adFreeTimeSec)
    		{
    			localyticsSession.tagEvent(PhysicsProjectileActivity.AD_SKIP);
    			if (answerChoice==0)
    			{
    			startActivity(new Intent(PhysicsProjectileActivity.this, PhysicsSolverSplitActivity.class));
    			}
    			else
    			{
    			startActivity(new Intent(PhysicsProjectileActivity.this, PhysicsSolverActivity.class));	
    			}
    		}
    		else
    		{
    			startActivity(new Intent(PhysicsProjectileActivity.this, PhysicsAdActivity.class));
    		}
    		}
    		catch(RuntimeException e)
    		{
    			startActivity(new Intent(PhysicsProjectileActivity.this, PhysicsAdActivity.class));
    			;
    			//Toast toast2;
    			//toast2=Toast.makeText(getApplicationContext(), "ERROR", 0);
    			//toast2.show();
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
	            	category1.getBackground().setColorFilter(fRed);
	            	category2.getBackground().setColorFilter(fRed);
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
	            		TEST.setText(Double.valueOf(E1_1_3.getText().toString()).toString());
	            		TEST.setText(Double.valueOf(E1_1_4.getText().toString()).toString());
	            		category1.invalidate();
	            		//category1.getBackground().clearColorFilter()
	            		category1.getBackground().setColorFilter(fGreen);
	            		u1=factor[0][0][0]*Double.valueOf(E1_1_1.getText().toString());
	            		u2=factor[0][0][1]*Double.valueOf(E1_1_2.getText().toString());
	            		u3=factor[0][0][2]*Double.valueOf(E1_1_3.getText().toString());
	            		u4=factor[0][0][3]*Double.valueOf(E1_1_4.getText().toString());
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
	            		TEST.setText(Double.valueOf(E2_1_2.getText().toString()).toString());
	            		TEST.setText(Double.valueOf(E2_1_3.getText().toString()).toString());
	            		if (E2_1_2.isEnabled()==false)
	            		{
	            			throw new NumberFormatException();
	            		}
	            		category2.invalidate();
	            		//category2.getBackground().clearColorFilter();
	            		category2.getBackground().setColorFilter(fGreen);
	            		u5=factor[1][0][0]*Double.valueOf(E2_1_1.getText().toString());
	            		u6=factor[1][0][1]*Double.valueOf(E2_1_2.getText().toString());
	            		u7=factor[1][0][2]*Double.valueOf(E2_1_3.getText().toString());
	            		u8=0;
	            		u9=0;
	            		u10=0;
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
	            		TEST.setText(Double.valueOf(E2_2_2.getText().toString()).toString());
	            		TEST.setText(Double.valueOf(E2_2_3.getText().toString()).toString());
	            		if (E2_2_2.isEnabled()==false)
	            		{
	            			throw new NumberFormatException();
	            		}
	            		category2.invalidate();
	            		//category2.getBackground().clearColorFilter();
	            		category2.getBackground().setColorFilter(fGreen);
	            		u5=factor[1][1][0]*Double.valueOf(E2_2_1.getText().toString());
	            		u6=factor[1][1][1]*Double.valueOf(E2_2_2.getText().toString());
	            		u7=factor[1][1][2]*Double.valueOf(E2_2_3.getText().toString());
	            		u8=0;
	            		u9=0;
	            		u10=0;
	            	}
	            	catch (NumberFormatException e) {
	            		isError=1;
	            		solve.setEnabled(false);
	            	}
	            	}
	            	else if(option2==3)
	            	{
	            	try {
	            		TEST.setText(Double.valueOf(E2_3_1.getText().toString()).toString());
	            		TEST.setText(Double.valueOf(E2_3_2.getText().toString()).toString());
	            		TEST.setText(Double.valueOf(E2_3_3.getText().toString()).toString());
	            		TEST.setText(Double.valueOf(E2_3_4.getText().toString()).toString());
	            		if (E2_3_3.isEnabled()==false)
	            		{
	            			throw new NumberFormatException();
	            		}
	            		category2.invalidate();
	            		//category2.getBackground().clearColorFilter();
	            		category2.getBackground().setColorFilter(fGreen);
	            		u5=factor[1][2][0]*Double.valueOf(E2_3_1.getText().toString());
	            		u6=factor[1][2][1]*Double.valueOf(E2_3_2.getText().toString());
	            		u7=factor[1][2][2]*Double.valueOf(E2_3_3.getText().toString());
	            		u8=factor[1][2][3]*Double.valueOf(E2_3_4.getText().toString());
	            		u9=0;
	            		u10=0;
	            	}
	            	catch (NumberFormatException e) {
	            		isError=1;
	            		solve.setEnabled(false);
	            	}
	            	}
	            	else if(option2==4)
	            	{
	            	try {
	            		TEST.setText(Double.valueOf(E2_4_1.getText().toString()).toString());
	            		TEST.setText(Double.valueOf(E2_4_2.getText().toString()).toString());
	            		TEST.setText(Double.valueOf(E2_4_3.getText().toString()).toString());
	            		category2.invalidate();
	            		//category2.getBackground().clearColorFilter();
	            		category2.getBackground().setColorFilter(fGreen);
	            		u5=factor[1][3][0]*Double.valueOf(E2_4_1.getText().toString());
	            		u6=factor[1][3][1]*Double.valueOf(E2_4_2.getText().toString());
	            		u7=factor[1][3][2]*Double.valueOf(E2_4_3.getText().toString());
	            		u8=0;
	            		u9=0;
	            		u10=0;
	            	}
	            	catch (NumberFormatException e) {
	            		isError=1;
	            		solve.setEnabled(false);
	            	}
	            	}
	            	else if(option2==5)
	            	{
	            	try {
	            		TEST.setText(Double.valueOf(E2_5_1.getText().toString()).toString());
	            		TEST.setText(Double.valueOf(E2_5_2.getText().toString()).toString());
	            		TEST.setText(Double.valueOf(E2_5_3.getText().toString()).toString());
	            		TEST.setText(Double.valueOf(E2_5_4.getText().toString()).toString());
	            		category2.invalidate();
	            		//category2.getBackground().clearColorFilter();
	            		category2.getBackground().setColorFilter(fGreen);
	            		u5=factor[1][4][0]*Double.valueOf(E2_5_1.getText().toString());
	            		u6=factor[1][4][1]*Double.valueOf(E2_5_2.getText().toString());
	            		u7=factor[1][4][2]*Double.valueOf(E2_5_3.getText().toString());
	            		u8=factor[1][4][3]*Double.valueOf(E2_5_4.getText().toString());
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
	    				instructions[1]="First, we will deal with the 'y' direction. We can use the y-acceleration for a typical projectile problem."; 
	    				instructions[2]="We want an equation for y-velocity. We can get it from y-acceleration, but there is an unknown integration constant K1.";
	    				switch (option2) {
	    	        	case 1:
	    				instructions[3]="We know the magnitude of the overall velocity and the angle at a certain (same) time. We can use this knowledge to obtain the y-velocity at that same time.";
	    				break;
	    	        	case 2:
	        			instructions[3]="We know the x-velocity and the angle at a certain time. We can use this knowledge to obtain the y-velocity at that same time.";
	        			break;
	    	        	case 3:
	        			instructions[3]="We know the y-velocity at a certain time, because we are given this in the problem.";
	        			break;
	    	        	case 4:
	        			instructions[3]="We know the y-velocity at a certain time, because we are given this in the problem.";
	        			break;
	    				}
	    				instructions[4]="Plug in values and solve for the integration constant K1.";
	    				instructions[5]="Next, we want an equation for y-position. We can get it from y-velocity, but there is an unknown integration constant K2.";
	    				instructions[6]="We know the y-position at a certain time, because we are given this in the problem.";
	    				instructions[7]="Plug in values and solve for the integration constant K2.";
	    				instructions[8]="Now, we will deal with the 'x' direction. We can use the x-acceleration for a typical projectile problem.";
	    				instructions[9]="We want an equation for x-velocity. We can get it from x-acceleration, but there is an unknown integration constant K3. Still, we can tell x-velocity is constant";
	    				switch (option2) {
	    	        	case 1:
	    				instructions[10]="Since x-velocity is a constant, we only need to know it at one time. We know the magnitude of the overall velocity and the angle at a certain (same) time. We can use this knowledge to obtain the x-velocity.";
	    				break;
	    	        	case 2:
	        			instructions[10]="Since x-velocity is a constant, we only need to know it at one time. We are given this in the problem.";
	        			break;
	    	        	case 3:
	        			instructions[10]="Since x-velocity is a constant, we only need to know it at one time. We know the angle at a certain time, and can use our equation for y-velocity to find y-velocity at that same time. We can then use this knowledge to obtain the x-velocity.";
	        			break;
	    	        	case 4:
	        			instructions[10]="Since x-velocity is a constant, we only need to know it at one time. We are given this in the problem.";
	        			break;
	    				}
	    				instructions[11]="Next, we want an equation for x-position. We can get it from x-velocity, but there is an unknown integration constant K4.";
	    				instructions[12]="We know the x-position at a certain time, because we are given this in the problem.";
	    				instructions[13]="Plug in values and solve for the integration constant K4.";
	    				instructions[14]="Use the following equations to solve for what you need. You may also enter the value of one variable below and hit 'Solve' to get the rest of them.";
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