package com.androidbook.physicstutor;


//import android.graphics.Color;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class PhysicsAssumptionsActivity extends PhysicsActivity {
    
	Spinner s;
	EditText gravity;
	Button gravity2;
	TextView gravity3;
	
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
		startActivity(new Intent(PhysicsAssumptionsActivity.this, PhysicsIntroActivity.class));
	return;
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assumptions);
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
        
        gravity = (EditText) findViewById(R.id.EditText_Constant_g);
        gravity2 = (Button) findViewById(R.id.Button_Constant_g);
        gravity3 = (TextView) findViewById(R.id.TextView_Constant_g);
        if (PhysicsActivity.selection_g==2)
        {
        	gravity.setVisibility(0);
        	gravity2.setVisibility(0);
        }
        else
        {
        	gravity.setVisibility(4);
        	gravity2.setVisibility(4);
        }
        
        s = (Spinner) findViewById(R.id.Spinner_Constant_g);
        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.gravity, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        s.setSelection(PhysicsActivity.selection_g);
        
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                //Object item = parent.getItemAtPosition(pos);
                //gravity.setText(Integer.toString(pos));
            	switch (pos)
            	{
            	case 0: PhysicsActivity.constant_g=9.8; gravity.setVisibility(4); gravity2.setVisibility(4); break;
            	case 1: PhysicsActivity.constant_g=10; gravity.setVisibility(4); gravity2.setVisibility(4); break;
            	case 2: gravity.setVisibility(0); gravity2.setVisibility(0); break;
            	}
            	gravity3.setText("Constant g = "+String.format("%.4f", constant_g)+" m/s^2");
            	PhysicsActivity.selection_g=pos;
            }
            public void onNothingSelected(AdapterView<?> parent) {
            	PhysicsActivity.constant_g=9.8;
            	;
            }
        });
        
        gravity2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	try {
            		//gravity3.setText("Constant g (meters/(seconds^2)) = ".concat(Double.valueOf(gravity.getText().toString()).toString()));
            		PhysicsActivity.constant_g=Double.valueOf(gravity.getText().toString());
            		gravity3.setText("Constant g = "+String.format("%.4f", constant_g)+" m/s^2");
            	}
            	catch (NumberFormatException e) {
            		;
            	}
        }});    
        
    }
}