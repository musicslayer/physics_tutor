package com.androidbook.physicstutor;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class PhysicsProjectileActivity extends PhysicsActivity {
    TextView TEST, constants;
    Button solve, test;
    Button category1, category2, P1_1, P2_1, P2_2, P2_3, P2_4;
    ScrollView cat1_1, cat2_1, cat2_2, cat2_3, cat2_4;
    LinearLayout page1, page2;
    LightingColorFilter fGreen, fRed;
    EditText E1_1_1, E1_1_2, E1_1_3, E1_1_4, E2_1_1, E2_1_2, E2_1_3, E2_2_1, E2_2_2, E2_2_3, E2_3_1, E2_3_2, E2_3_3, E2_3_4, E2_4_1, E2_4_2, E2_4_3;
    ToggleButton TB2_1_2, TB2_2_2, TB2_3_3;
    
    int isError, i;
    //double factor2_1_2=0.017453292519943295769236907684886, factor2_2_2=0.017453292519943295769236907684886, factor2_3_3=0.017453292519943295769236907684886;
    //double factor2_1_2, factor2_2_2, factor2_3_3;
    
    @Override
    public void onPause() 
    {
      super.onPause();
      SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);
      SharedPreferences.Editor editor = preferences.edit();
  
      editor.putLong("t1", adTimer.getTime());
      editor.putFloat("f1", (float)factor2_1_2);
      editor.putFloat("f2", (float)factor2_2_2);
      editor.putFloat("f3", (float)factor2_3_3);
      editor.putString("v1", type);
      editor.putFloat("v2", (float)constant_g);
      editor.putInt("v3", selection_g);
      editor.putInt("v4", stotal);
      editor.putInt("v5", option1);
      editor.putInt("v6", option2);
      editor.putFloat("v7", (float)u1);
      editor.putFloat("v8", (float)u2);
      editor.putFloat("v9", (float)u3);
      editor.putFloat("v10", (float)u4);
      editor.putFloat("v11", (float)u5);
      editor.putFloat("v12", (float)u6);
      editor.putFloat("v13", (float)u7);
      editor.putFloat("v14", (float)u8);
      editor.putFloat("v15", (float)u9);
      editor.putFloat("v16", (float)u10);
      for (i=1;i<=stotal;i++)
      {
    	  editor.putString("i"+Integer.toString(i), instructions[i]);
    	  editor.putInt("h"+Integer.toString(i), helperImage[i]);
      }
      editor.commit();
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
    	factor2_1_2=0.017453292519943295769236907684886; 
    	factor2_2_2=0.017453292519943295769236907684886; 
    	factor2_3_3=0.017453292519943295769236907684886;
		startActivity(new Intent(PhysicsProjectileActivity.this, PhysicsProblemsActivity.class));
	return;
	}
    
    @Override
	public void onResume() {
    	super.onResume();
    	TB2_1_2.setText("Units");
    	TB2_2_2.setText("Units");
    	TB2_3_3.setText("Units");
	return;
	}
    
    public void onRestart() {
    	super.onRestart();
    	TB2_1_2.setText("Units");
    	TB2_2_2.setText("Units");
    	TB2_3_3.setText("Units");
	return;
	}
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.solve_projectile);
        
        SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);
        
        if ("PROJECTILE".equals(type)==false)
        {
		    instructions = new String[21];
		    helperImage = new int[21];
		    adTimer.setTime(preferences.getLong("t1", adTimer.getTime()));
		    factor2_1_2=preferences.getFloat("f1", (float)factor2_1_2);
		    factor2_2_2=preferences.getFloat("f2", (float)factor2_2_2);
		    factor2_3_3=preferences.getFloat("f3", (float)factor2_3_3);
		    type=preferences.getString("v1", "?");
		    constant_g=preferences.getFloat("v2", (float)constant_g);
		    selection_g=preferences.getInt("v3", selection_g);
		    stotal=preferences.getInt("v4", stotal);
		    option1=preferences.getInt("v5", option1);
		    option2=preferences.getInt("v6", option2);
		    u1=preferences.getFloat("v7", (float)u1);
		    u2=preferences.getFloat("v8", (float)u2);
		    u3=preferences.getFloat("v9", (float)u3);
		    u4=preferences.getFloat("v10", (float)u4);
		    u5=preferences.getFloat("v11", (float)u5);
		    u6=preferences.getFloat("v12", (float)u6);
		    u7=preferences.getFloat("v13", (float)u7);
		    u8=preferences.getFloat("v14", (float)u8);
		    u9=preferences.getFloat("v15", (float)u9);
		    u10=preferences.getFloat("v16", (float)u10);
		    for (i=1;i<=stotal;i++)
		    {
		    	instructions[i]=preferences.getString("i"+Integer.toString(i), instructions[i]);
		    	helperImage[i]=preferences.getInt("h"+Integer.toString(i), helperImage[i]);
		    }
        }
        
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
        page1=(LinearLayout) findViewById(R.id.LinearLayoutY);
        page2=(LinearLayout) findViewById(R.id.LinearLayoutZ);
        P1_1=(Button) findViewById(R.id.P1_1);
        P2_1=(Button) findViewById(R.id.P2_1);
        P2_2=(Button) findViewById(R.id.P2_2);
        P2_3=(Button) findViewById(R.id.P2_3);
        P2_4=(Button) findViewById(R.id.P2_4);  
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
        TB2_1_2=(ToggleButton) findViewById(R.id.ToggleButton_2_1_2);
        TB2_2_2=(ToggleButton) findViewById(R.id.ToggleButton_2_2_2);
        TB2_3_3=(ToggleButton) findViewById(R.id.ToggleButton_2_3_3);
        fGreen = new LightingColorFilter(0xFFFFFF, 0x00FF00);
        fRed = new LightingColorFilter(0xFFFFFF, 0xFF0000);
        instructions = new String[21];
        helperImage = new int[21];
        constants.setText("Constant g = "+String.format("%.4f", constant_g)+" m/s^2");
        
        option1=0;
        option2=0;
        stotal=14;
        
        /*
        if (TB2_1_2.isChecked()) {//Radians
            factor2_1_2=1.0;
        } else {//Degrees
        	factor2_1_2=0.017453292519943295769236907684886;
        }
        if (TB2_2_2.isChecked()) {//Radians
            factor2_2_2=1.0;
        } else {//Degrees
        	factor2_2_2=0.017453292519943295769236907684886;
        }
        if (TB2_3_3.isChecked()) {//Radians
            factor2_3_3=1.0;
        } else {//Degrees
        	factor2_3_3=0.017453292519943295769236907684886;
        }
        */
        for (i=0;i<=20;i++)
        {
        	helperImage[i]=R.raw.default_image;
        	instructions[i]="?";
        }
        
        TB2_1_2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks
            	E2_1_2.setEnabled(true);
                if (TB2_1_2.isChecked()) {//Radians
                    factor2_1_2=1.0;
                    TB2_1_2.setText("Radians");
                } else {//Degrees
                	factor2_1_2=0.017453292519943295769236907684886;
                	TB2_1_2.setText("Degrees");
                }
            }
        });
        
        TB2_2_2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks
            	E2_2_2.setEnabled(true);
                if (TB2_2_2.isChecked()) {//Radians
                    factor2_2_2=1.0;
                    TB2_2_2.setText("Radians");
                } else {//Degrees
                	factor2_2_2=0.017453292519943295769236907684886;
                	TB2_2_2.setText("Degrees");
                }
            }
        });
        
        TB2_3_3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks
            	E2_3_3.setEnabled(true);
                if (TB2_3_3.isChecked()) {//Radians
                    factor2_3_3=1.0;
                    TB2_3_3.setText("Radians");
                } else {//Degrees
                	factor2_3_3=0.017453292519943295769236907684886;
                	TB2_3_3.setText("Degrees");
                }
            }
        });
        
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
            	P2_1.invalidate();
            	P2_2.invalidate();
            	P2_3.invalidate();
            	P2_4.invalidate();
            	P2_1.getBackground().setColorFilter(fGreen);
            	P2_2.getBackground().clearColorFilter();
            	P2_3.getBackground().clearColorFilter();
            	P2_4.getBackground().clearColorFilter();
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
            	P2_1.invalidate();
            	P2_2.invalidate();
            	P2_3.invalidate();
            	P2_4.invalidate();
               	P2_1.getBackground().clearColorFilter();
            	P2_2.getBackground().setColorFilter(fGreen);
            	P2_3.getBackground().clearColorFilter();
            	P2_4.getBackground().clearColorFilter();
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
            	P2_1.invalidate();
            	P2_2.invalidate();
            	P2_3.invalidate();
            	P2_4.invalidate();
               	P2_1.getBackground().clearColorFilter();
            	P2_2.getBackground().clearColorFilter();
            	P2_3.getBackground().setColorFilter(fGreen);
            	P2_4.getBackground().clearColorFilter();
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
            	P2_1.invalidate();
            	P2_2.invalidate();
            	P2_3.invalidate();
            	P2_4.invalidate();
               	P2_1.getBackground().clearColorFilter();
            	P2_2.getBackground().clearColorFilter();
            	P2_3.getBackground().clearColorFilter();
            	P2_4.getBackground().setColorFilter(fGreen);
            	solve.setEnabled(false);
            }
        });
        
        solve.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            type="PROJECTILE";
            
	            SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);
	            SharedPreferences.Editor editor = preferences.edit();
	        
	            editor.putString("v1", type);
	            editor.putFloat("v2", (float)constant_g);
	            editor.putInt("v3", selection_g);
	            editor.putInt("v4", stotal);
	            editor.putInt("v5", option1);
	            editor.putInt("v6", option2);
	            editor.putFloat("v7", (float)u1);
	            editor.putFloat("v8", (float)u2);
	            editor.putFloat("v9", (float)u3);
	            editor.putFloat("v10", (float)u4);
	            editor.putFloat("v11", (float)u5);
	            editor.putFloat("v12", (float)u6);
	            editor.putFloat("v13", (float)u7);
	            editor.putFloat("v14", (float)u8);
	            editor.putFloat("v15", (float)u9);
	            editor.putFloat("v16", (float)u10);
	            for (i=1;i<=stotal;i++)
	            {
	          	  editor.putString("i"+Integer.toString(i), instructions[i]);
	          	  editor.putInt("h"+Integer.toString(i), helperImage[i]);
	            }
	            editor.commit();
        
            startActivity(new Intent(PhysicsProjectileActivity.this, PhysicsAdActivity.class));    
            }
        });    
        
        test.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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
            		category1.getBackground().clearColorFilter();
            		u1=Double.valueOf(E1_1_1.getText().toString());
            		u2=Double.valueOf(E1_1_2.getText().toString());
            		u3=Double.valueOf(E1_1_3.getText().toString());
            		u4=Double.valueOf(E1_1_4.getText().toString());
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
            		category2.getBackground().clearColorFilter();
            		u5=Double.valueOf(E2_1_1.getText().toString());
            		u6=factor2_1_2*Double.valueOf(E2_1_2.getText().toString());
            		u7=Double.valueOf(E2_1_3.getText().toString());
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
            		category2.getBackground().clearColorFilter();
            		u5=Double.valueOf(E2_2_1.getText().toString());
            		u6=factor2_2_2*Double.valueOf(E2_2_2.getText().toString());
            		u7=Double.valueOf(E2_2_3.getText().toString());
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
            		category2.getBackground().clearColorFilter();
            		u5=Double.valueOf(E2_3_1.getText().toString());
            		u6=Double.valueOf(E2_3_2.getText().toString());
            		u7=factor2_3_3*Double.valueOf(E2_3_3.getText().toString());
            		u8=Double.valueOf(E2_3_4.getText().toString());
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
            		category2.getBackground().clearColorFilter();
            		u5=Double.valueOf(E2_4_1.getText().toString());
            		u6=Double.valueOf(E2_4_2.getText().toString());
            		u7=Double.valueOf(E2_4_3.getText().toString());
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
            		TEST.setText("You may proceed by clicking 'SOLVE'.\nIf you change any inputs\nor switch any pages,\nyou must hit 'TEST' again.");
    				solve.setEnabled(true);
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
            		helperImage[10]=R.raw.helper_proj_10_1;
            		break;
    	        	case 3:
            		helperImage[10]=R.raw.helper_proj_10_1;
            		break;
    	        	case 4:
            		helperImage[10]=R.raw.helper_proj_10_1;
            		break;
        			}
        			helperImage[11]=R.raw.helper_proj_11;
        			helperImage[12]=R.raw.helper_proj_12;
        			helperImage[13]=R.raw.helper_proj_13;
        			helperImage[14]=R.raw.helper_proj_14;
            	}
            }
        });            
    }
}