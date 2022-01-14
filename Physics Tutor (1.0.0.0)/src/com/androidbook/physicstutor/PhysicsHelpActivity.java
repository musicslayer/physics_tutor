package com.androidbook.physicstutor;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PhysicsHelpActivity extends PhysicsActivity {

	TextView help;
	Button b1, b2, b3;
	
	int i;
	
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
        ;
    }
	
	@Override
	public void onBackPressed() {
		startActivity(new Intent(PhysicsHelpActivity.this, PhysicsIntroActivity.class));
	return;
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
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
        
        help=(TextView) findViewById(R.id.help);
        b1=(Button) findViewById(R.id.problems);
        b2=(Button) findViewById(R.id.assumptions);
        b3=(Button) findViewById(R.id.settings);
        
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	help.setText("Problems:" +
                		"\nUse this section to choose a type of physics problem." +
                		"\nEach problem will have one or more categories of information." +
                		"\nEach category contains one or more pages – groups of values that are to be provided by the user." +
                		"\nThe user must choose exactly one page in each category, based on what information the user already knows." +
                		"\nThe pages are designed so that only one page is necessary to provide enough information for that category." +
                		"\n\nAfter filling out one page in each category, the user must hit ‘TEST’ to ensure that all inputs are valid, and that each category has a page chosen and completely filled out." +
                		"\nIf everything is OK, then the user can hit ‘SOLVE’ to proceed to the solution." +
                		"\nNote that, if the user changes any of the values, or switches a category’s page, those changes will not be counted unless the user hits ‘TEST’ again." +
                		"\n\nThe solution caters to both those who want a quick answer, and those who want an explanation of how to reach the answer." +
                		"\nIf the user wants answers, then enter the value of one variable into the answer fields, and hit ‘Solve’, and the other variables will be computed." +
                		"\nIf the user wants an explanation, then there are instructions and helper illustrations that will guide the user through the problem, step by step." +
                		"\nNote that the step by step instructions end by finding all the terms to write all the equations for that problem." +
                		"\nThe user must still plug in information and/or solve for what they want (At this point, the answer fields can be used).");
            }
        });  
        
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	help.setText("Assumptions:\nPhysics problems often rely on certain constants." +
                		"\nFor example, the projectile problem depends on the value of ‘g’, the (magnitude of the) gravitational acceleration of an object." +
                		"\nThese constants are set to a default value (for example ‘g’=9.8 m/s^2)." +
                		"\nHowever, the user can change these values in this section.");
            }
        });  
        
        b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	help.setText("Settings:\nThis section is coming soon.");
            }
        });  
    }
}