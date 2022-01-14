package com.androidbook.physicstutor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PhysicsSolverActivity extends PhysicsActivity {
    Toast toast;
    Menu menu;
    TextView case1, step, constants, info;
    Button variable1B, variable2B, variable3B, variable4B;
    TextView answer1, answer2, answer3, answer4, variable1T, variable2T, variable3T, variable4T;
    ImageView helper;
    EditText variable1E, variable2E, variable3E, variable4E;
    Button previous, next;
    
    Double k1, k2, k3, k4, temp, temp1, temp2, peak;
    int i, s;
    
    /*
    @Override
    public void onDestroy() 
    {
      super.onDestroy();
      SharedPreferences preferences = getPreferences(MODE_PRIVATE);
      SharedPreferences.Editor editor = preferences.edit();
      editor.clear();
      editor.commit();
    }
    */
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
		toast.show();
	return;
	}
    
    @Override 
    public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu); 
    getMenuInflater().inflate(R.menu.menu_solver, menu); 
    		menu.findItem(R.id.exit_menu_item).setIntent(
    		new Intent(this, PhysicsProblemsActivity.class)); 
    return true;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.solver);
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
        
        step = (TextView) findViewById(R.id.TextView_Step);
        case1=(TextView) findViewById(R.id.TextView_Case);
        constants=(TextView) findViewById(R.id.Constants);
        info=(TextView) findViewById(R.id.Info);
        variable1B=(Button) findViewById(R.id.Button_Variable1);
        variable2B=(Button) findViewById(R.id.Button_Variable2);
        variable3B=(Button) findViewById(R.id.Button_Variable3);
        variable4B=(Button) findViewById(R.id.Button_Variable4);
        variable1T=(TextView) findViewById(R.id.TextView_Variable1);
        variable2T=(TextView) findViewById(R.id.TextView_Variable2);
        variable3T=(TextView) findViewById(R.id.TextView_Variable3);
        variable4T=(TextView) findViewById(R.id.TextView_Variable4);
        variable1E=(EditText) findViewById(R.id.EditText_Variable1);
        variable2E=(EditText) findViewById(R.id.EditText_Variable2);
        variable3E=(EditText) findViewById(R.id.EditText_Variable3);
        variable4E=(EditText) findViewById(R.id.EditText_Variable4);
        answer1=(TextView) findViewById(R.id.TextView_Answer1);
        answer2=(TextView) findViewById(R.id.TextView_Answer2);
        answer3=(TextView) findViewById(R.id.TextView_Answer3);
        answer4=(TextView) findViewById(R.id.TextView_Answer4);        
        helper=(ImageView) findViewById(R.id.ImageView_Helper);
        previous = (Button) findViewById(R.id.Button_Previous);
        next = (Button) findViewById(R.id.Button_Next);
        toast=Toast.makeText(getApplicationContext(), "Press MENU and select QUIT to exit this problem.", 0);
        case1.setText(type);
        //case1.setText(Double.toString(factor2_3_3));
        step.setText("Step 1:\n"+instructions[1]);
        helper.setImageResource(helperImage[1]);
        
        s=1;
        if ("PROJECTILE".equals(type))
        {
        	variable1T.setText("Vy(t) (m/s):");
        	variable2T.setText("y(t) (m):");
        	variable3T.setText("x(t) (m):");
        	variable4T.setText("t (s):");
        	switch (option2) {
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
        	}
        	peak=-(constant_g*(k1/constant_g)*(k1/constant_g))/2+k1*(k1/constant_g)+k2;
        	constants.setText("Constant g = "+String.format("%.4f", constant_g)+" m/s^2");
            info.setText("Peak y-position = "+String.format("%.4f",peak)+" m\nx-velocity = "+String.format("%.4f",k3)+ " m/s");
        	variable1B.setOnClickListener(new View.OnClickListener() {
        		public void onClick(View v) {
        			try {
        				temp=Double.valueOf(variable1E.getText().toString());
        				answer1.setText(String.format("%.4f",temp)+" m/s");
            			answer2.setText(String.format("%.4f",-(constant_g*((k1-temp)/constant_g)*((k1-temp)/constant_g))/2+k1*((k1-temp)/constant_g)+k2)+" m");
            			answer3.setText(String.format("%.4f",k3*((k1-temp)/constant_g)+k4)+" m");
            			answer4.setText(String.format("%.4f",(k1-temp)/constant_g)+" s");
        				}
        			catch (NumberFormatException e) {
                		;
                		}
        		}});    
        	variable2B.setOnClickListener(new View.OnClickListener() {
        		public void onClick(View v) {
        			try {
        				temp=Double.valueOf(variable2E.getText().toString());
        				temp1=(-k1+Math.sqrt((k1*k1)-4*(-constant_g/2)*(k2-temp)))/(-constant_g);
        				temp2=(-k1-Math.sqrt((k1*k1)-4*(-constant_g/2)*(k2-temp)))/(-constant_g);
        				if (temp<=peak)
        				{
        				answer1.setText(String.format("%.4f",-constant_g*temp1+k1)+" m/s or "+String.format("%.4f",-constant_g*temp2+k1)+" m/s");
            			answer2.setText(String.format("%.4f",temp)+" m");
            			answer3.setText(String.format("%.4f",k3*temp1+k4)+" m or "+String.format("%.4f",k3*temp2+k4)+" m");
            			answer4.setText(String.format("%.4f",temp1)+" s or "+String.format("%.4f",temp2)+" s");
        				}
        				else
        				{
        					answer1.setText("");
        					answer2.setText("y(t) cannot exceed peak value.");
        					answer3.setText("");
        					answer4.setText("");
        				}
        				}
        			catch (NumberFormatException e) {
                		;
                		}
        		}});
        	variable3B.setOnClickListener(new View.OnClickListener() {
        		public void onClick(View v) {
        			try {
        				temp=Double.valueOf(variable3E.getText().toString());
        				answer1.setText(String.format("%.4f",-constant_g*((temp-k4)/k3)+k1)+" m/s");
            			answer2.setText(String.format("%.4f",-(constant_g*((temp-k4)/k3)*((temp-k4)/k3))/2+k1*((temp-k4)/k3)+k2)+" m");
            			answer3.setText(String.format("%.4f",temp)+" m");
            			answer4.setText(String.format("%.4f",(temp-k4)/k3)+" s");
        				}
        			catch (NumberFormatException e) {
                		;
                		}
        		}});
        	variable4B.setOnClickListener(new View.OnClickListener() {
        		public void onClick(View v) {
        			try {
        				temp=Double.valueOf(variable4E.getText().toString());
        				answer1.setText(String.format("%.4f",-constant_g*temp+k1)+" m/s");
            			answer2.setText(String.format("%.4f",-(constant_g*temp*temp)/2+k1*temp+k2)+" m");
            			answer3.setText(String.format("%.4f",k3*temp+k4)+" m");
            			answer4.setText(String.format("%.4f",temp)+" s");
        				}
        			catch (NumberFormatException e) {
                		;
                		}
        		}});
        }
        else
        {
        	variable1T.setText("?");
        	variable2T.setText("?");
        	variable3T.setText("?");
        	variable4T.setText("?");
        }
        
        previous.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            if (s>1)
            {
            s--;
            step.setText("Step ".concat(Integer.toString(s)).concat(":\n").concat(instructions[s]));
            helper.setImageResource(helperImage[s]);
            }    
        }});    
        
        
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            if (s<stotal)
            {
            s++;
            step.setText("Step ".concat(Integer.toString(s)).concat(":\n").concat(instructions[s]));
            helper.setImageResource(helperImage[s]);
            }    
        }});     
    }
}