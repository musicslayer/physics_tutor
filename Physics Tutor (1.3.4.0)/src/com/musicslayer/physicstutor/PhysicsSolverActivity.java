package com.musicslayer.physicstutor;

import com.Localytics.android.*;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class PhysicsSolverActivity extends PhysicsActivity {
	private LocalyticsSession localyticsSession;
    Toast toastExit, toastInfo;
    Menu menu;
    //TextView case1, step, constants;
    TextView step, display, constantsText;
    Button constants, answers, variable1B, variable2B, variable3B, variable4B, variable5B, variable6B;
    TextView answer1, answer2, answer3, answer4, answer5, answer6, variable1T, variable2T, variable3T, variable4T, variable5T, variable6T;
    EditText variable1E, variable2E, variable3E, variable4E, variable5E, variable6E;
    ImageView helper;
    Button previous, next;
    TableLayout table;
    TableRow variable1, variable2, variable3, variable4, variable5, variable6;
    String infoText;
    Spinner SA1[], SA2[];
    ArrayAdapter<?> adapterA1[], adapterA2[];
    ScrollView answersBox, constantsBox;
    Intent emailIntent;
    
    Double k1, k2, k3, k4, temp, temp1, temp2, peak, angle, k, peakTime, peakS, S;
    int s, i, j;
    
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
		toastExit.show();
	return;
	}
    
    @Override 
    public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu); 
    getMenuInflater().inflate(R.menu.menu_solver, menu); 
    
    		menu.findItem(R.id.quit_menu_item).setIntent(
    		new Intent(this, PhysicsIntroActivity.class)); 
    		
    		if ("PROJECTILE"==type)
			{
				menu.findItem(R.id.edit_inputs_menu_item).setIntent(
				new Intent(this, PhysicsProjectileActivity.class));
			}
			else if ("INCLINE"==type)
			{
				menu.findItem(R.id.edit_inputs_menu_item).setIntent(
				new Intent(this, PhysicsInclineActivity.class));
			}
			else if ("INCLINE_SIMPLE"==type)
			{
				menu.findItem(R.id.edit_inputs_menu_item).setIntent(
				new Intent(this, PhysicsInclineSimpleActivity.class));
			}
            menu.findItem(R.id.email_menu_item).setIntent(
    		new Intent(emailIntent));
    return true;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.solver);
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
        answersBox=(ScrollView) findViewById(R.id.ScrollView_Answers);
        constantsBox=(ScrollView) findViewById(R.id.ScrollView_Constants);
        table=(TableLayout) findViewById(R.id.Table);
        variable1=(TableRow) findViewById(R.id.Row1);
        variable2=(TableRow) findViewById(R.id.Row2);
        variable3=(TableRow) findViewById(R.id.Row3);
        variable4=(TableRow) findViewById(R.id.Row4);
        variable5=(TableRow) findViewById(R.id.Row5);
        variable6=(TableRow) findViewById(R.id.Row6);
        step = (TextView) findViewById(R.id.TextView_Step);
        //constants=(TextView) findViewById(R.id.Constants);
        constants=(Button) findViewById(R.id.Button_Constants);
        answers=(Button) findViewById(R.id.Button_Answers);
        variable1B=(Button) findViewById(R.id.Button_Variable1);
        variable2B=(Button) findViewById(R.id.Button_Variable2);
        variable3B=(Button) findViewById(R.id.Button_Variable3);
        variable4B=(Button) findViewById(R.id.Button_Variable4);
        variable5B=(Button) findViewById(R.id.Button_Variable5);
        variable6B=(Button) findViewById(R.id.Button_Variable6);
        variable1T=(TextView) findViewById(R.id.TextView_Variable1);
        variable2T=(TextView) findViewById(R.id.TextView_Variable2);
        variable3T=(TextView) findViewById(R.id.TextView_Variable3);
        variable4T=(TextView) findViewById(R.id.TextView_Variable4);
        variable5T=(TextView) findViewById(R.id.TextView_Variable5);
        variable6T=(TextView) findViewById(R.id.TextView_Variable6);
        variable1E=(EditText) findViewById(R.id.EditText_Variable1);
        variable2E=(EditText) findViewById(R.id.EditText_Variable2);
        variable3E=(EditText) findViewById(R.id.EditText_Variable3);
        variable4E=(EditText) findViewById(R.id.EditText_Variable4);
        variable5E=(EditText) findViewById(R.id.EditText_Variable5);
        variable6E=(EditText) findViewById(R.id.EditText_Variable6);
        answer1=(TextView) findViewById(R.id.TextView_Answer1);
        answer2=(TextView) findViewById(R.id.TextView_Answer2);
        answer3=(TextView) findViewById(R.id.TextView_Answer3);
        answer4=(TextView) findViewById(R.id.TextView_Answer4);   
        answer5=(TextView) findViewById(R.id.TextView_Answer5);
        answer6=(TextView) findViewById(R.id.TextView_Answer6);
        display=(TextView) findViewById(R.id.TextView_Display);
        constantsText=(TextView) findViewById(R.id.TextView_Constants);
        helper=(ImageView) findViewById(R.id.ImageView_Helper);
        previous = (Button) findViewById(R.id.Button_Previous);
        next = (Button) findViewById(R.id.Button_Next);
        toastExit=Toast.makeText(getApplicationContext(), "Press MENU and select QUIT to exit, or EDIT INPUTS to change the inputs.", 0);
        emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"musicslayer@gmail.com", ""});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Physics Tutor - Bug Report");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "\n\n~~~~~~~~~~~~~~~~~~~~\nType of Problem:\n"+type+"\n\nOptions:\n"+option1+"\n"+option2+"\n"+option3+"\n"+option4+"\n\nUser inputs:\n"+u1+"\n"+u2+"\n"+u3+"\n"+u4+"\n"+u5+"\n"+u6+"\n"+u7+"\n"+u8+"\n"+u9+"\n"+u10+"\n~~~~~~~~~~~~~~~~~~~~");

        if ("INCLINE_SIMPLE".equals(type)) {
        	display.setText("Answer Fields:\nThis problem does not have any answer fields.");
        	answersBox.setVisibility(8);
    	}
    	else
    	{
    		display.setText("Answer Fields:");
    		answersBox.setVisibility(0);
    	}
        constantsBox.setVisibility(8);
        
        factorA1 = new double[6];
	    selectionA1 = new int[6];
	    factorA2 = new double[6];
	    selectionA2 = new int[6];
        
        SA1 = new Spinner[6];
        adapterA1 = new ArrayAdapter<?>[6];
        SA2 = new Spinner[6];
        adapterA2 = new ArrayAdapter<?>[6];
        for (i=0;i<6;i++)
        {
        	SA1[i] = (Spinner) findViewById(getResources().getIdentifier("SpinnerA1_"+Integer.toString(i), "id", getPackageName()));
        	SA2[i] = (Spinner) findViewById(getResources().getIdentifier("SpinnerA2_"+Integer.toString(i), "id", getPackageName()));
        	SA1[i].setOnTouchListener(new OnTouchListener() {
    			public boolean onTouch(View v, MotionEvent event) {
    				variable1E.setFocusableInTouchMode(false);
        			variable2E.setFocusableInTouchMode(false);
        			variable3E.setFocusableInTouchMode(false);
        			variable4E.setFocusableInTouchMode(false);
        			variable5E.setFocusableInTouchMode(false);
        			variable6E.setFocusableInTouchMode(false);
    				variable1E.clearFocus();
        			variable2E.clearFocus();
        			variable3E.clearFocus();
        			variable4E.clearFocus();
        			variable5E.clearFocus();
        			variable6E.clearFocus();
    				return false;
    			}
            });
        	SA2[i].setOnTouchListener(new OnTouchListener() {
    			public boolean onTouch(View v, MotionEvent event) {
    				variable1E.setFocusableInTouchMode(false);
        			variable2E.setFocusableInTouchMode(false);
        			variable3E.setFocusableInTouchMode(false);
        			variable4E.setFocusableInTouchMode(false);
        			variable5E.setFocusableInTouchMode(false);
        			variable6E.setFocusableInTouchMode(false);
    				variable1E.clearFocus();
        			variable2E.clearFocus();
        			variable3E.clearFocus();
        			variable4E.clearFocus();
        			variable5E.clearFocus();
        			variable6E.clearFocus();
    				return false;
    			}
            });
        }
        
        table.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				variable1E.setFocusableInTouchMode(false);
    			variable2E.setFocusableInTouchMode(false);
    			variable3E.setFocusableInTouchMode(false);
    			variable4E.setFocusableInTouchMode(false);
    			variable5E.setFocusableInTouchMode(false);
    			variable6E.setFocusableInTouchMode(false);
				variable1E.clearFocus();
    			variable2E.clearFocus();
    			variable3E.clearFocus();
    			variable4E.clearFocus();
    			variable5E.clearFocus();
    			variable6E.clearFocus();
				return false;
			}
        });
        
        variable1B.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				variable1E.setFocusableInTouchMode(false);
    			variable2E.setFocusableInTouchMode(false);
    			variable3E.setFocusableInTouchMode(false);
    			variable4E.setFocusableInTouchMode(false);
    			variable5E.setFocusableInTouchMode(false);
    			variable6E.setFocusableInTouchMode(false);
				variable1E.clearFocus();
    			variable2E.clearFocus();
    			variable3E.clearFocus();
    			variable4E.clearFocus();
    			variable5E.clearFocus();
    			variable6E.clearFocus();
				return false;
			}
        });
        
        variable2B.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				variable1E.setFocusableInTouchMode(false);
    			variable2E.setFocusableInTouchMode(false);
    			variable3E.setFocusableInTouchMode(false);
    			variable4E.setFocusableInTouchMode(false);
    			variable5E.setFocusableInTouchMode(false);
    			variable6E.setFocusableInTouchMode(false);
				variable1E.clearFocus();
    			variable2E.clearFocus();
    			variable3E.clearFocus();
    			variable4E.clearFocus();
    			variable5E.clearFocus();
    			variable6E.clearFocus();
				return false;
			}
        });
        
        variable3B.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				variable1E.setFocusableInTouchMode(false);
    			variable2E.setFocusableInTouchMode(false);
    			variable3E.setFocusableInTouchMode(false);
    			variable4E.setFocusableInTouchMode(false);
    			variable5E.setFocusableInTouchMode(false);
    			variable6E.setFocusableInTouchMode(false);
				variable1E.clearFocus();
    			variable2E.clearFocus();
    			variable3E.clearFocus();
    			variable4E.clearFocus();
    			variable5E.clearFocus();
    			variable6E.clearFocus();
				return false;
			}
        });
        
        variable4B.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				variable1E.setFocusableInTouchMode(false);
    			variable2E.setFocusableInTouchMode(false);
    			variable3E.setFocusableInTouchMode(false);
    			variable4E.setFocusableInTouchMode(false);
    			variable5E.setFocusableInTouchMode(false);
    			variable6E.setFocusableInTouchMode(false);
				variable1E.clearFocus();
    			variable2E.clearFocus();
    			variable3E.clearFocus();
    			variable4E.clearFocus();
    			variable5E.clearFocus();
    			variable6E.clearFocus();
				return false;
			}
        });
        
        variable5B.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				variable1E.setFocusableInTouchMode(false);
    			variable2E.setFocusableInTouchMode(false);
    			variable3E.setFocusableInTouchMode(false);
    			variable4E.setFocusableInTouchMode(false);
    			variable5E.setFocusableInTouchMode(false);
    			variable6E.setFocusableInTouchMode(false);
				variable1E.clearFocus();
    			variable2E.clearFocus();
    			variable3E.clearFocus();
    			variable4E.clearFocus();
    			variable5E.clearFocus();
    			variable6E.clearFocus();
				return false;
			}
        });
        
        variable6B.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				variable1E.setFocusableInTouchMode(false);
    			variable2E.setFocusableInTouchMode(false);
    			variable3E.setFocusableInTouchMode(false);
    			variable4E.setFocusableInTouchMode(false);
    			variable5E.setFocusableInTouchMode(false);
    			variable6E.setFocusableInTouchMode(false);
				variable1E.clearFocus();
    			variable2E.clearFocus();
    			variable3E.clearFocus();
    			variable4E.clearFocus();
    			variable5E.clearFocus();
    			variable6E.clearFocus();
				return false;
			}
        });
        
        variable1E.setOnTouchListener(new View.OnTouchListener() {
        	public boolean onTouch(View v, MotionEvent event) {
        		variable1E.setFocusableInTouchMode(true);
    			variable2E.setFocusableInTouchMode(true);
    			variable3E.setFocusableInTouchMode(true);
    			variable4E.setFocusableInTouchMode(true);
    			variable5E.setFocusableInTouchMode(true);
    			variable6E.setFocusableInTouchMode(true);
    			return false;
			}
        });
        variable2E.setOnTouchListener(new View.OnTouchListener() {
        	public boolean onTouch(View v, MotionEvent event) {
        		variable1E.setFocusableInTouchMode(true);
    			variable2E.setFocusableInTouchMode(true);
    			variable3E.setFocusableInTouchMode(true);
    			variable4E.setFocusableInTouchMode(true);
    			variable5E.setFocusableInTouchMode(true);
    			variable6E.setFocusableInTouchMode(true);
    			return false;
			}
        });
        variable3E.setOnTouchListener(new View.OnTouchListener() {
        	public boolean onTouch(View v, MotionEvent event) {
        		variable1E.setFocusableInTouchMode(true);
    			variable2E.setFocusableInTouchMode(true);
    			variable3E.setFocusableInTouchMode(true);
    			variable4E.setFocusableInTouchMode(true);
    			variable5E.setFocusableInTouchMode(true);
    			variable6E.setFocusableInTouchMode(true);
    			return false;
			}
        });
        variable4E.setOnTouchListener(new View.OnTouchListener() {
        	public boolean onTouch(View v, MotionEvent event) {
        		variable1E.setFocusableInTouchMode(true);
    			variable2E.setFocusableInTouchMode(true);
    			variable3E.setFocusableInTouchMode(true);
    			variable4E.setFocusableInTouchMode(true);
    			variable5E.setFocusableInTouchMode(true);
    			variable6E.setFocusableInTouchMode(true);
    			return false;
			}
        });
        variable5E.setOnTouchListener(new View.OnTouchListener() {
        	public boolean onTouch(View v, MotionEvent event) {
        		variable1E.setFocusableInTouchMode(true);
    			variable2E.setFocusableInTouchMode(true);
    			variable3E.setFocusableInTouchMode(true);
    			variable4E.setFocusableInTouchMode(true);
    			variable5E.setFocusableInTouchMode(true);
    			variable6E.setFocusableInTouchMode(true);
    			return false;
			}
        });
        variable6E.setOnTouchListener(new View.OnTouchListener() {
        	public boolean onTouch(View v, MotionEvent event) {
        		variable1E.setFocusableInTouchMode(true);
    			variable2E.setFocusableInTouchMode(true);
    			variable3E.setFocusableInTouchMode(true);
    			variable4E.setFocusableInTouchMode(true);
    			variable5E.setFocusableInTouchMode(true);
    			variable6E.setFocusableInTouchMode(true);
    			return false;
			}
        });
        
        instructions = new String[21];
	    helperImage = new int[21];
        
        for (i=0;i<=20;i++)
        {
        	helperImage[i]=R.raw.default_image;
        	instructions[i]="?";
        }
        s=1;
        if ("PROJECTILE".equals(type))
        {
        	stotal=14;
        	instructions[1]="First, we will deal with the 'y' direction. We can use the y-acceleration for a typical projectile problem."; 
			instructions[2]="We want an equation for y-velocity. We can get it from y-acceleration, but there is an unknown integration constant K1.";
			switch (option2) {
        	case 1:
			instructions[3]="We know the magnitude of the overall velocity and the angle at a certain (same) time. We can use this knowledge to obtain the y-velocity at that same time.";
			instructions[4]="Plug in values and solve for the integration constant K1.";
			instructions[5]="Next, we want an equation for y-position. We can get it from y-velocity, but there is an unknown integration constant K2.";
			instructions[6]="We know the y-position at a certain time, because we are given this in the problem.";
			instructions[7]="Plug in values and solve for the integration constant K2.";
			break;
        	case 2:
			instructions[3]="We know the x-velocity and the angle at a certain time. We can use this knowledge to obtain the y-velocity at that same time.";
			instructions[4]="Plug in values and solve for the integration constant K1.";
			instructions[5]="Next, we want an equation for y-position. We can get it from y-velocity, but there is an unknown integration constant K2.";
			instructions[6]="We know the y-position at a certain time, because we are given this in the problem.";
			instructions[7]="Plug in values and solve for the integration constant K2.";
			break;
        	case 3:
			instructions[3]="We know the y-velocity at a certain time, because we are given this in the problem.";
			instructions[4]="Plug in values and solve for the integration constant K1.";
			instructions[5]="Next, we want an equation for y-position. We can get it from y-velocity, but there is an unknown integration constant K2.";
			instructions[6]="We know the y-position at a certain time, because we are given this in the problem.";
			instructions[7]="Plug in values and solve for the integration constant K2.";
			break;
        	case 4:
			instructions[3]="We know the y-velocity at a certain time, because we are given this in the problem.";
			instructions[4]="Plug in values and solve for the integration constant K1.";
			instructions[5]="Next, we want an equation for y-position. We can get it from y-velocity, but there is an unknown integration constant K2.";
			instructions[6]="We know the y-position at a certain time, because we are given this in the problem.";
			instructions[7]="Plug in values and solve for the integration constant K2.";
			break;
        	case 5:
        	instructions[3]="Unfortunately, we do not have any information regarding velocity given to us in the problem.";
        	instructions[4]="To solve for K1 now, we would need to plug in the y-velocity and the time and solve for K1. However, we cannot do this because we have no way of finding the y-velocity at a certain time yet.";
        	instructions[5]="Keeping the unknown K1 in mind, we next want an equation for y-position. We can get it from y-velocity, but there is another unknown integration constant K2. Note that at this point there are two unknowns, K1 and K2.";
        	instructions[6]="We know the y-position at two times, because we are given this in the problem. As long as both times are different, we have two different pieces of information.";
        	instructions[7]="For each y-position and time we are given, plug them in and write the resulting equations. This gives us two equations for two unknowns. There are many ways to solve these equations, but perhaps the easiest is shown in the helper illustration. This will give us both K1 and K2.";
        	break;
			}
			
			instructions[8]="Now, we will deal with the 'x' direction. We can use the x-acceleration for a typical projectile problem.";
			instructions[9]="We want an equation for x-velocity. We can get it from x-acceleration, but there is an unknown integration constant K3. Still, we can tell x-velocity is constant";
			switch (option2) {
        	case 1:
			instructions[10]="Since x-velocity is a constant, we only need to know it at one time. We know the magnitude of the overall velocity and the angle at a certain (same) time. We can use this knowledge to obtain the x-velocity.";
			instructions[11]="Next, we want an equation for x-position. We can get it from x-velocity, but there is an unknown integration constant K4.";
			instructions[12]="We know the x-position at a certain time, because we are given this in the problem.";
			instructions[13]="Plug in values and solve for the integration constant K4.";
			break;
        	case 2:
			instructions[10]="Since x-velocity is a constant, we only need to know it at one time. We are given this in the problem.";
			instructions[11]="Next, we want an equation for x-position. We can get it from x-velocity, but there is an unknown integration constant K4.";
			instructions[12]="We know the x-position at a certain time, because we are given this in the problem.";
			instructions[13]="Plug in values and solve for the integration constant K4.";
			break;
        	case 3:
			instructions[10]="Since x-velocity is a constant, we only need to know it at one time. We know the angle at a certain time, and can use our equation for y-velocity to find y-velocity at that same time. We can then use this knowledge to obtain the x-velocity.";
			instructions[11]="Next, we want an equation for x-position. We can get it from x-velocity, but there is an unknown integration constant K4.";
			instructions[12]="We know the x-position at a certain time, because we are given this in the problem.";
			instructions[13]="Plug in values and solve for the integration constant K4.";
			break;
        	case 4:
			instructions[10]="Since x-velocity is a constant, we only need to know it at one time. We are given this in the problem.";
			instructions[11]="Next, we want an equation for x-position. We can get it from x-velocity, but there is an unknown integration constant K4.";
			instructions[12]="We know the x-position at a certain time, because we are given this in the problem.";
			instructions[13]="Plug in values and solve for the integration constant K4.";
			break;
        	case 5:
            instructions[10]="Unfortunately, we do not have any information regarding velocity given to us in the problem. We could proceed as we did with y, but there is also a trick we can take advantage of. Because x-velocity is a constant, we can find it directly from the two x-position values given to us, as long as the times are different.";
            instructions[11]="Next, we want an equation for x-position. We can get it from x-velocity, but there is an unknown integration constant K4.";
			instructions[12]="We know the x-position at two times, because we are given this in the problem.";
			instructions[13]="Pick one set of corresponding x-position and time values (it doesn't matter which), and plug the values in and solve for the integration constant K4.";
            break;
			}
			instructions[14]="Use the following equations to solve for what you need. You may also enter the value of one variable below and hit 'Solve' to get the rest of them.";
			helperImage[1]=R.raw.helper_proj_1;
			helperImage[2]=R.raw.helper_proj_2;
			switch (option2) {
        	case 1:
			helperImage[3]=R.raw.helper_proj_3_1;
			helperImage[4]=R.raw.helper_proj_4;
			helperImage[5]=R.raw.helper_proj_5;
			helperImage[6]=R.raw.helper_proj_6;
			helperImage[7]=R.raw.helper_proj_7;
			break;
        	case 2:
    		helperImage[3]=R.raw.helper_proj_3_2;
    		helperImage[4]=R.raw.helper_proj_4;
    		helperImage[5]=R.raw.helper_proj_5;
			helperImage[6]=R.raw.helper_proj_6;
			helperImage[7]=R.raw.helper_proj_7;
    		break;
        	case 3:
    		helperImage[3]=R.raw.helper_proj_3_3;
    		helperImage[4]=R.raw.helper_proj_4;
    		helperImage[5]=R.raw.helper_proj_5;
			helperImage[6]=R.raw.helper_proj_6;
			helperImage[7]=R.raw.helper_proj_7;
    		break;
        	case 4:
    		helperImage[3]=R.raw.helper_proj_3_4;
    		helperImage[4]=R.raw.helper_proj_4;
    		helperImage[5]=R.raw.helper_proj_5;
			helperImage[6]=R.raw.helper_proj_6;
			helperImage[7]=R.raw.helper_proj_7;
    		break;
        	case 5:
        	helperImage[3]=R.raw.helper_proj_3_5;
        	helperImage[4]=R.raw.helper_proj_4_5;
        	helperImage[5]=R.raw.helper_proj_5;
			helperImage[6]=R.raw.helper_proj_6_5;
			helperImage[7]=R.raw.helper_proj_7_5;
            break;
			}
			helperImage[8]=R.raw.helper_proj_8;
			helperImage[9]=R.raw.helper_proj_9;
			switch (option2) {
        	case 1:
			helperImage[10]=R.raw.helper_proj_10_1;
			helperImage[11]=R.raw.helper_proj_11;
			helperImage[12]=R.raw.helper_proj_12;
			helperImage[13]=R.raw.helper_proj_13;
			helperImage[14]=R.raw.helper_proj_14;
			break;
        	case 2:
    		helperImage[10]=R.raw.helper_proj_10_2;
    		helperImage[11]=R.raw.helper_proj_11;
			helperImage[12]=R.raw.helper_proj_12;
			helperImage[13]=R.raw.helper_proj_13;
			helperImage[14]=R.raw.helper_proj_14;
    		break;
        	case 3:
    		helperImage[10]=R.raw.helper_proj_10_3;
    		helperImage[11]=R.raw.helper_proj_11;
			helperImage[12]=R.raw.helper_proj_12;
			helperImage[13]=R.raw.helper_proj_13;
			helperImage[14]=R.raw.helper_proj_14;
    		break;
        	case 4:
    		helperImage[10]=R.raw.helper_proj_10_4;
    		helperImage[11]=R.raw.helper_proj_11;
			helperImage[12]=R.raw.helper_proj_12;
			helperImage[13]=R.raw.helper_proj_13;
			helperImage[14]=R.raw.helper_proj_14;
    		break;
        	case 5:
        	helperImage[10]=R.raw.helper_proj_10_5;
        	helperImage[11]=R.raw.helper_proj_11;
			helperImage[12]=R.raw.helper_proj_12_5;
			helperImage[13]=R.raw.helper_proj_13_5;
			helperImage[14]=R.raw.helper_proj_14;
            break;
			}
			
			adapterA1[0] = ArrayAdapter.createFromResource(this, R.array.unit_velocity, android.R.layout.simple_spinner_item);
            adapterA1[1] = ArrayAdapter.createFromResource(this, R.array.unit_length, android.R.layout.simple_spinner_item);
            adapterA1[2] = ArrayAdapter.createFromResource(this, R.array.unit_length, android.R.layout.simple_spinner_item);
            adapterA1[3] = ArrayAdapter.createFromResource(this, R.array.unit_time, android.R.layout.simple_spinner_item);
            adapterA1[4] = ArrayAdapter.createFromResource(this, R.array.unit_angle, android.R.layout.simple_spinner_item);
            adapterA1[5] = ArrayAdapter.createFromResource(this, R.array.unit_velocity, android.R.layout.simple_spinner_item);
            adapterA2[0] = ArrayAdapter.createFromResource(this, R.array.unit_velocity, android.R.layout.simple_spinner_item);
            adapterA2[1] = ArrayAdapter.createFromResource(this, R.array.unit_length, android.R.layout.simple_spinner_item);
            adapterA2[2] = ArrayAdapter.createFromResource(this, R.array.unit_length, android.R.layout.simple_spinner_item);
            adapterA2[3] = ArrayAdapter.createFromResource(this, R.array.unit_time, android.R.layout.simple_spinner_item);
            adapterA2[4] = ArrayAdapter.createFromResource(this, R.array.unit_angle, android.R.layout.simple_spinner_item);
            adapterA2[5] = ArrayAdapter.createFromResource(this, R.array.unit_velocity, android.R.layout.simple_spinner_item);
            makeSpinner(0, "velocity");
            makeSpinner(1, "length");
            makeSpinner(2, "length");
            makeSpinner(3, "time");
            makeSpinner(4, "angle");
            makeSpinner(5, "velocity");
            SA1[0].setPromptId(R.string.prompt_velocity);
            SA1[1].setPromptId(R.string.prompt_length);
            SA1[2].setPromptId(R.string.prompt_length);
            SA1[3].setPromptId(R.string.prompt_time);
            SA1[4].setPromptId(R.string.prompt_angle);
            SA1[5].setPromptId(R.string.prompt_velocity);
            SA2[0].setPromptId(R.string.prompt_velocity);
            SA2[1].setPromptId(R.string.prompt_length);
            SA2[2].setPromptId(R.string.prompt_length);
            SA2[3].setPromptId(R.string.prompt_time);
            SA2[4].setPromptId(R.string.prompt_angle);
            SA2[5].setPromptId(R.string.prompt_velocity);
            SA1[0].setSelection(defaultVelocity);
            SA1[1].setSelection(defaultLength);
            SA1[2].setSelection(defaultLength);
            SA1[3].setSelection(defaultTime);
            SA1[4].setSelection(defaultAngle);
            SA1[5].setSelection(defaultVelocity);
            SA2[0].setSelection(defaultVelocity);
            SA2[1].setSelection(defaultLength);
            SA2[2].setSelection(defaultLength);
            SA2[3].setSelection(defaultTime);
            SA2[4].setSelection(defaultAngle);
            SA2[5].setSelection(defaultVelocity);
            setTitle("Physics Tutor - Projectile Explanation and Answers");
            variable1.setVisibility(0);
        	variable2.setVisibility(0);
        	variable3.setVisibility(0);
            variable4.setVisibility(0);
        	variable5.setVisibility(0);
        	variable6.setVisibility(0);
        	variable1T.setText("Vy(t):");
        	variable2T.setText("y(t):");
        	variable3T.setText("x(t):");
        	variable4T.setText("t:");
        	variable5T.setText("angle(t):");
        	variable6T.setText("V(t):");
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
        	case 5:
        		k1=(u7-u3+(constant_g/2)*(u8*u8-u4*u4))/(u8-u4);
        		k2=u3+constant_g*u4*u4/2-k1*u4;
        		k3=(u5-u1)/(u6-u2);
        		k4=u1-k3*u2;
        		break;
        	}
        	peak=-(constant_g*(k1/constant_g)*(k1/constant_g))/2+k1*(k1/constant_g)+k2;
        	//constants.setText("Constant g = "+String.format("%.4f", constant_g)+" m/s^2");
            //info.setText("Peak y-position = "+String.format("%.4f",peak)+" m\nx-velocity = "+String.format("%.4f",k3)+ " m/s");
        	infoText="Constant g = "+String.format("%.4f", constant_g)+" m/s^2" +
        	"\nPeak y-position = "+String.format("%.4f",peak)+ " m" +
        	"\nx-velocity = "+String.format("%.4f",k3)+ " m/s";
        	variable1B.setOnClickListener(new View.OnClickListener() {
        		public void onClick(View v) {
        			j=0;
        			try {
        				temp=factorA1[0]*Double.valueOf(variable1E.getText().toString());
        				answer1.setText(String.format("%.4f",(1/factorA2[0])*temp));
            			answer2.setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*((k1-temp)/constant_g)*((k1-temp)/constant_g))/2+k1*((k1-temp)/constant_g))+k2));
            			answer3.setText(String.format("%.4f",(1/factorA2[2])*(k3*((k1-temp)/constant_g)+k4)));
            			answer4.setText(String.format("%.4f",(1/factorA2[3])*((k1-temp)/constant_g)));
            			answer5.setText(String.format("%.4f",(1/factorA2[4])*(Math.atan(temp/k3))));
            			answer6.setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+temp*temp))));
        				}
        			catch (NumberFormatException e) {
                		;
                		}
        		}});    
        	variable2B.setOnClickListener(new View.OnClickListener() {
        		public void onClick(View v) {
        			j=1;
        			try {
        				temp=factorA1[1]*Double.valueOf(variable2E.getText().toString());
        				temp1=(-k1+Math.sqrt((k1*k1)-4*(-constant_g/2)*(k2-temp)))/(-constant_g);
        				temp2=(-k1-Math.sqrt((k1*k1)-4*(-constant_g/2)*(k2-temp)))/(-constant_g);
        				if (temp<=peak)
        				{
        				answer1.setText(String.format("%.4f",(1/factorA2[0])*(-constant_g*temp1+k1))+" or\n"+String.format("%.4f",(1/factorA2[0])*(-constant_g*temp2+k1)));
            			answer2.setText(String.format("%.4f",(1/factorA2[1])*temp));
            			answer3.setText(String.format("%.4f",(1/factorA2[2])*(k3*temp1+k4))+" or\n"+String.format("%.4f",(1/factorA2[2])*(k3*temp2+k4)));
            			answer4.setText(String.format("%.4f",(1/factorA2[3])*temp1)+" or\n"+String.format("%.4f",(1/factorA2[3])*temp2));
            			answer5.setText(String.format("%.4f",(1/factorA2[4])*(Math.atan((-constant_g*temp1+k1)/k3)))+" or\n"+String.format("%.4f",(1/factorA2[4])*(Math.atan((-constant_g*temp2+k1)/k3))));
            			answer6.setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+(-constant_g*temp1+k1)*(-constant_g*temp1+k1)))));
        				}
        				else
        				{
        					answer1.setText("");
        					answer2.setText("y(t) cannot exceed peak value.");
        					answer3.setText("");
        					answer4.setText("");
        					answer5.setText("");
                			answer6.setText("");
        				}
        				}
        			catch (NumberFormatException e) {
                		;
                		}
        		}});
        	variable3B.setOnClickListener(new View.OnClickListener() {
        		public void onClick(View v) {
        			j=2;
        			try {
        				temp=factorA1[2]*Double.valueOf(variable3E.getText().toString());
        				answer1.setText(String.format("%.4f",(1/factorA2[0])*(-constant_g*((temp-k4)/k3)+k1)));
            			answer2.setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*((temp-k4)/k3)*((temp-k4)/k3))/2+k1*((temp-k4)/k3)+k2)));
            			answer3.setText(String.format("%.4f",(1/factorA2[2])*temp));
            			answer4.setText(String.format("%.4f",(1/factorA2[3])*((temp-k4)/k3)));
            			answer5.setText(String.format("%.4f",(1/factorA2[4])*(Math.atan((-constant_g*((temp-k4)/k3)+k1)/k3))));
            			answer6.setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+(-constant_g*((temp-k4)/k3)+k1)*(-constant_g*((temp-k4)/k3)+k1)))));
        				}
        			catch (NumberFormatException e) {
                		;
                		}
        		}});
        	variable4B.setOnClickListener(new View.OnClickListener() {
        		public void onClick(View v) {
        			j=3;
        			try {
        				temp=factorA1[3]*Double.valueOf(variable4E.getText().toString());
        				answer1.setText(String.format("%.4f",(1/factorA2[0])*(-constant_g*temp+k1)));
            			answer2.setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*temp*temp)/2+k1*temp+k2)));
            			answer3.setText(String.format("%.4f",(1/factorA2[2])*(k3*temp+k4)));
            			answer4.setText(String.format("%.4f",(1/factorA2[3])*temp));
            			answer5.setText(String.format("%.4f",(1/factorA2[4])*(Math.atan((-constant_g*temp+k1)/k3))));
            			answer6.setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+(-constant_g*temp+k1)*(-constant_g*temp+k1)))));
        				}
        			catch (NumberFormatException e) {
                		;
                		}
        		}});
        	variable5B.setOnClickListener(new View.OnClickListener() {
        		public void onClick(View v) {
        			j=4;
        			try {
        				temp=factorA1[4]*Double.valueOf(variable5E.getText().toString());
        				answer1.setText(String.format("%.4f",(1/factorA2[0])*(k3*Math.tan(temp))));
            			answer2.setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*((k1-(k3*Math.tan(temp)))/constant_g)*((k1-(k3*Math.tan(temp)))/constant_g))/2+k1*((k1-(k3*Math.tan(temp)))/constant_g)+k2)));
            			answer3.setText(String.format("%.4f",(1/factorA2[2])*(k3*((k1-(k3*Math.tan(temp)))/constant_g)+k4)));
            			answer4.setText(String.format("%.4f",(1/factorA2[3])*((k1-(k3*Math.tan(temp)))/constant_g)));
            			answer5.setText(String.format("%.4f",(1/factorA2[4])*temp));
            			answer6.setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+(k3*Math.tan(temp))*(k3*Math.tan(temp))))));
        				}
        			catch (NumberFormatException e) {
                		;
                		}
        		}});
        	variable6B.setOnClickListener(new View.OnClickListener() {
        		public void onClick(View v) {
        			j=5;
        			try {
        				temp=factorA1[5]*Double.valueOf(variable6E.getText().toString());
        				answer1.setText(String.format("%.4f",(1/factorA2[0])*(Math.sqrt(temp*temp-k3*k3))));
            			answer2.setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g)*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g))/2+k1*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g)+k2)));
            			answer3.setText(String.format("%.4f",(1/factorA2[2])*(k3*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g)+k4)));
            			answer4.setText(String.format("%.4f",(1/factorA2[3])*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g)));
            			answer5.setText(String.format("%.4f",(1/factorA2[4])*(Math.acos(k3/temp))));
            			answer6.setText(String.format("%.4f",(1/factorA2[5])*temp));
        				}
        			catch (NumberFormatException e) {
                		;
                		}
        		}});
        }
        else if ("INCLINE".equals(type))
        {
        	stotal=19;
        	adapterA1[0] = ArrayAdapter.createFromResource(this, R.array.unit_velocity, android.R.layout.simple_spinner_item);
            adapterA1[1] = ArrayAdapter.createFromResource(this, R.array.unit_length, android.R.layout.simple_spinner_item);
            adapterA1[2] = ArrayAdapter.createFromResource(this, R.array.unit_time, android.R.layout.simple_spinner_item);
            adapterA2[0] = ArrayAdapter.createFromResource(this, R.array.unit_velocity, android.R.layout.simple_spinner_item);
            adapterA2[1] = ArrayAdapter.createFromResource(this, R.array.unit_length, android.R.layout.simple_spinner_item);
            adapterA2[2] = ArrayAdapter.createFromResource(this, R.array.unit_time, android.R.layout.simple_spinner_item);
            makeSpinner(0, "velocity");
            makeSpinner(1, "length");
            makeSpinner(2, "time");
            SA1[0].setPromptId(R.string.prompt_velocity);
            SA1[1].setPromptId(R.string.prompt_length);
            SA1[2].setPromptId(R.string.prompt_time);
            SA2[0].setPromptId(R.string.prompt_velocity);
            SA2[1].setPromptId(R.string.prompt_length);
            SA2[2].setPromptId(R.string.prompt_time);
            SA1[0].setSelection(defaultVelocity);
            SA1[1].setSelection(defaultLength);
            SA1[2].setSelection(defaultTime);
            SA2[0].setSelection(defaultVelocity);
            SA2[1].setSelection(defaultLength);
            SA2[2].setSelection(defaultTime);
            setTitle("Physics Tutor - Incline Explanation and Answers");
        	variable1T.setText("Vs(t):");
        	variable2T.setText("s(t):");
        	variable3T.setText("t:");
        	variable4.setVisibility(8);
        	variable5.setVisibility(8);
        	variable6.setVisibility(8);
        	switch (option3) {
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
			S=Math.sqrt(u3*u3+u4*u4);
			break;
			}
        	if (u6>0) {
        		k1=u6+(constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))*u7;
        		peakTime=k1/(constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle));
        		k2=(constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))*peakTime;
        	}
        	else if(u6<0) {
        		k2=u6+(constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))*u7;
        		peakTime=k2/(constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle));
        		k1=(constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))*peakTime;
        	}
        	else {
        		peakTime=u7;
        		k1=(constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))*peakTime;
        		k2=(constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))*peakTime;
        	}
        	if (u4<peakTime){
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
        	
        	//double angle = 0, k, peakTime;
			switch (option3) {
			case 1:
			//angle=u5;
			instructions[1]="The first piece of information we should know about an incline problem is the angle of the incline itself. We are given this in the problem.";
			helperImage[1]=R.raw.helper_incl_1;
			break;
			case 2:
			//angle=u5;
			instructions[1]="The first piece of information we should know about an incline problem is the angle of the incline itself. We are given this in the problem.";
			helperImage[1]=R.raw.helper_incl_1;
			break;
			case 3:
			//angle=u5;
			instructions[1]="The first piece of information we should know about an incline problem is the angle of the incline itself. We are given this in the problem.";
			helperImage[1]=R.raw.helper_incl_1;
			break;
			case 4:
			//angle=Math.atan(u5/u3);
			instructions[1]="The first piece of information we should know about an incline problem is the angle of the incline itself. We are given x-position and y-position at the same time, and can use them to solve for the angle of the incline.";
			helperImage[1]=R.raw.helper_incl_1_4;
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
			if (u6>0) {
			//k=u6+(constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))*u7;
			//peakTime=k/(constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle));
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
			}
			else if(u6<0) {
			//k=u6+(constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))*u7;
			//peakTime=k/(constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle));
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
			}
			else {
			//peakTime=u7;
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
			}
			instructions[11]="We now know the velocity of the object at all times.";
			helperImage[11]=R.raw.helper_incl_11;
			switch (option3) {
			case 1:
			instructions[12]="We are given the s-position at a certain time.";
			helperImage[12]=R.raw.helper_incl_12_1;
			break;	
			case 2:
			instructions[12]="We know the x-position at a certain time, and the angle of the incline. We can use this to get the s-position at that same time.";
			helperImage[12]=R.raw.helper_incl_12_2;
			break;
			case 3:
			instructions[12]="We know the y-position at a certain time, and the angle of the incline. We can use this to get the s-position at that same time.";
			helperImage[12]=R.raw.helper_incl_12_3;
			break;
			case 4:
			instructions[12]="We know the x-position and y-position at a certain time. We can use this to get the s-position at that same time.";
			helperImage[12]=R.raw.helper_incl_12_4;
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
			}
			instructions[18]="We now know the s-position of the object at all times";
			instructions[19]="Use the following equations to solve for what you need. You may also enter the value of one variable below and hit 'Solve' to get the rest of them.";
			helperImage[18]=R.raw.helper_incl_18;
			helperImage[19]=R.raw.helper_incl_19;
			
        	
        	//constants.setText("Constant g = "+String.format("%.4f", constant_g)+" m/s^2");
            //info.setText("Peak s-position = "+String.format("%.4f",peakS)+" m at time = "+String.format("%.4f",peakTime)+" s");
        	infoText="Constant g = "+String.format("%.4f", constant_g)+" m/s^2" +
        	"\nPeak s-position = "+String.format("%.4f",peakS)+ " m at time = "+String.format("%.4f",peakTime)+" s";
        	variable1B.setOnClickListener(new View.OnClickListener() {
        		public void onClick(View v) {
        			j=0;
        			try {
        				temp=factorA1[0]*Double.valueOf(variable1E.getText().toString());
        				answer1.setText(String.format("%.4f",(1/factorA2[0])*temp));
        				if (temp>0)
        				{
        				temp1=(temp-k1)/(-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle));
            			answer2.setText(String.format("%.4f",(1/factorA2[1])*((-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))*(temp1*temp1/2)+k1*temp1+k3)));
            			answer3.setText(String.format("%.4f",(1/factorA2[2])*temp1));
        				}
        				else if (temp<0)
        				{
        				temp1=(temp-k2)/(-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle));
            			answer2.setText(String.format("%.4f",(1/factorA2[1])*((-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))*(temp1*temp1/2)+k2*temp1+k4)));
            			answer3.setText(String.format("%.4f",(1/factorA2[2])*temp1));
        				}
        				else
        				{
        				answer2.setText(String.format("%.4f",(1/factorA2[1])*peakS));
                		answer3.setText(String.format("%.4f",(1/factorA2[2])*peakTime));	
        				}
        				}
        			catch (NumberFormatException e) {
                		;
                		}
        		}});    
        	variable2B.setOnClickListener(new View.OnClickListener() {
        		public void onClick(View v) {
        			j=1;
        			try {
        				temp=factorA1[1]*Double.valueOf(variable2E.getText().toString());
        				double a1, a2;
        				a1= (-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))/2;
        				temp1=(-k1+Math.sqrt((k1*k1)-4*(a1)*(k3-temp)))/(2*a1);
        				a2= (-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))/2;
        				temp2=(-k2-Math.sqrt((k2*k2)-4*(a2)*(k4-temp)))/(2*a2);
        				if (temp<peakS)
        				{
        				answer1.setText(String.format("%.4f",(1/factorA2[0])*(2*a1*temp1+k1))+" or\n"+String.format("%.4f",(1/factorA2[0])*(2*a2*temp2+k2)));
            			answer2.setText(String.format("%.4f",(1/factorA2[1])*temp));
            			answer3.setText(String.format("%.4f",(1/factorA2[2])*temp1)+" or\n"+String.format("%.4f",(1/factorA2[2])*temp2));
        				}
        				else if (temp==peakS)
        				{
        				answer1.setText("0");
            			answer2.setText(String.format("%.4f",(1/factorA2[1])*temp));
            			answer3.setText(String.format("%.4f",(1/factorA2[2])*peakTime));
        				}
        				else
        				{
        					answer1.setText("");
        					answer2.setText("s(t) cannot exceed peak value.");
        					answer3.setText("");
        				}
        				}
        			catch (NumberFormatException e) {
                		;
                		}
        		}});
        	variable3B.setOnClickListener(new View.OnClickListener() {
        		public void onClick(View v) {
        			j=2;
        			try {
        				temp=factorA1[2]*Double.valueOf(variable3E.getText().toString());
        				if (temp<peakTime)
        				{
        					answer1.setText(String.format("%.4f",(1/factorA2[0])*((-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))*temp+k1)));
        					answer2.setText(String.format("%.4f",(1/factorA2[1])*((-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))*(temp*temp/2)+k1*temp+k3)));
        				}
        				else if (temp>peakTime)
        				{
            				answer1.setText(String.format("%.4f",(1/factorA2[0])*((-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))*temp+k2)));
                			answer2.setText(String.format("%.4f",(1/factorA2[1])*((-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))*(temp*temp/2)+k2*temp+k4)));
        				}
        				else {
        					answer1.setText("0");
                			answer2.setText(String.format("%.4f",(1/factorA2[1])*peakS));
        				}
            			answer3.setText(String.format("%.4f",(1/factorA2[2])*temp));
        				}
        			catch (NumberFormatException e) {
                		;
                		}
        		}});
        }
        else if ("INCLINE_SIMPLE".equals(type))
        {
        	stotal=8;
        	instructions[1]="For the simple incline problem, we wish to know the forces acting on the object (static and kinetic friction, gravity, normal force) as well as the angle of the incline and the mass of the object. Using coordinates that are parallel and perpendicular (normal) to the incline makes things easier. the s,n system is simply the x,y system rotated by the angle of the incline.";
        	helperImage[1]=R.raw.helper_simp_1;
        	switch (option1) {
        	case 1:
        	instructions[2]="We are given the angle of the incline and the mass of the object. The mass allows us to figure out the force of gravity, which acts straight down, while the angle allows us to break this force into components parallel and perpendicular (normal) to the surface of the incline.";
        	helperImage[2]=R.raw.helper_simp_2_1;
        	break;
        	case 2:
            instructions[2]="We are given the angle of the incline and the force of gravity parallel to the incline. These two pieces of information allow us to find the mass of the object and the force of gravity perpendicular (normal) to the surface of the incline.";
            helperImage[2]=R.raw.helper_simp_2_2;
            break;
        	case 3:
            instructions[2]="We are given the angle of the incline and the force of gravity perpendicular (normal) to the incline. These two pieces of information allow us to find the mass of the object and the force of gravity parallel to the surface of the incline.";
            helperImage[2]=R.raw.helper_simp_2_3;
            break;
        	case 4:
            instructions[2]="We are given the mass of the object and the force of gravity parallel to the incline. These two pieces of information allow us to find the angle of the incline and the force of gravity perpendicular (normal) to the surface of the incline.";
            helperImage[2]=R.raw.helper_simp_2_4;
            break;
        	case 5:
            instructions[2]="We are given the mass of the object and the force of gravity perpendicular (normal) to the incline. These two pieces of information allow us to find the angle of the incline and the force of gravity parallel to the surface of the incline.";
            helperImage[2]=R.raw.helper_simp_2_5;
            break;
        	case 6:
            instructions[2]="We are given the force of gravity parallel and perpendicular (normal) to the incline. These two pieces of information allow us to find the angle of the incline and the mass of the object.";
            helperImage[2]=R.raw.helper_simp_2_6;
            break;
        	}
        	instructions[3]="We now wish to find the normal force. We can find this using Newton's 2nd Law, and the fact that the object never leaves the surface of the incline.";
        	helperImage[3]=R.raw.helper_simp_3;
        	switch (option2) {
        	case 1:
        	instructions[4]="Next, we would like to know the static coefficient of friction, and the maximum force that static friction can exert. We are given the static coefficient of friction, and can use this, along with the normal force, to get the maximum force of static friction.";
        	helperImage[4]=R.raw.helper_simp_4_1;
            break;
        	case 2:
            instructions[4]="Next, we would like to know the static coefficient of friction, and the maximum force that static friction can exert. We are given the maximum force of static friction, and can use this, along with the normal force, to get the coefficient of static friction.";
            helperImage[4]=R.raw.helper_simp_4_2;
            break;
        	}
        	switch (option3) {
        	case 1:
        	instructions[5]="Next, we would like to know the kinetic coefficient of friction, and the force that kinetic friction can exert. We are given the kinetic coefficient of friction, and can use this, along with the normal force, to get the force of kinetic friction.";
        	helperImage[5]=R.raw.helper_simp_5_1;
            break;
        	case 2:
            instructions[5]="Next, we would like to know the kinetic coefficient of friction, and the force that kinetic friction can exert. We are given the force of kinetic friction, and can use this, along with the normal force, to get the coefficient of kinetic friction.";
            helperImage[5]=R.raw.helper_simp_5_2;
            break;
        	}
        	instructions[6]="Finally, with all of the forces known to us, we can get the net force on the object. Net force and acceleration in the n direction is always 0 (since the object never leaves the ramp). In the s direction, if the object is held in place by friction, the force is also 0. If the object is sliding down the incline, then we can figure out the net force by considering gravity (parallel to incline) and kinetic friction (which points in +s, opposite of the motion).";
        	instructions[7]="With this net force, we can find the acceleration of the object (if it is sliding down the incline) by using its mass.";
        	instructions[8]="The following equations summarize what you need to obtain all the information used in the simple incline problem.";
        	helperImage[6]=R.raw.helper_simp_6;
        	helperImage[7]=R.raw.helper_simp_7;
        	helperImage[8]=R.raw.helper_simp_8;
            setTitle("Physics Tutor - Simple Incline Explanation and Answers");
            variable1.setVisibility(8);
            variable2.setVisibility(8);
            variable3.setVisibility(8);
            variable4.setVisibility(8);
        	variable5.setVisibility(8);
        	variable6.setVisibility(8);
        	double v1=0, v2=0, v3=0, v4=0, v5=0, v6=0, v7=0, v8=0;
        	switch (option1) {
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
        	switch (option2) {
        	case 1:
        		v5=v4*u3;
        		v6=u3;
            	break;
        	case 2:
        		v5=u3;
        		v6=u3/v4;
            	break;
        	}
        	switch (option3) {
        	case 1:
        		v7=v4*u4;
        		v8=u4;
            	break;
        	case 2:
        		v7=u4;
        		v8=u4/v4;
            	break;
        	}
        	infoText="Constant g = "+String.format("%.4f", constant_g)+" m/s^2" +
        	"\nAngle of incline = "+String.format("%.4f",v1)+ " radians or "+String.format("%.4f",v1*57.295779513082320876798154814105)+" degrees" +
        	"\nMass of object = "+String.format("%.4f",v2)+ " kg" +
        	"\nForce of gravity parallel to incline = "+String.format("%.4f",-v3)+ " N" +
        	"\nNormal force = -force of gravity perpendicular to incline = "+String.format("%.4f",v4)+ " N" +
        	"\nMaximum force of static friction = "+String.format("%.4f",v5)+ " N" +
        	"\nCoefficient of static friction = "+String.format("%.4f",v6)+
        	"\nForce of kinetic friction = "+String.format("%.4f",v7)+ " N" +
        	"\nCoefficient of kinetic friction = "+String.format("%.4f",v8) +
        	"\nNet force along incline (if sliding downward) = "+String.format("%.4f",-(v3-v7))+ " N" +
        	"\nNet acceleration along incline (if sliding downward) = "+String.format("%.4f",-(v3-v7)/v2)+ " m/s^2";
        }
        else
        {
        	variable4.setVisibility(0);
        	variable5.setVisibility(0);
        	variable6.setVisibility(0);
        	variable1T.setText("?");
        	variable2T.setText("?");
        	variable3T.setText("?");
        	variable4T.setText("?");
        }
        
        step.setText("Step 1:\n"+instructions[1]);
        helper.setImageResource(helperImage[1]);
        //helper.setAdjustViewBounds(true);
        toastInfo=Toast.makeText(getApplicationContext(), infoText, 1);
        constantsText.setText(infoText);
        
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
        
        answers.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	if ("INCLINE_SIMPLE".equals(type)) {
            		display.setText("Answer Fields:\nThis problem does not have any answer fields.");
            	}
            	else
            	{
            		display.setText("Answer Fields:");
            		answersBox.setVisibility(0);
            	}
            	constantsBox.setVisibility(8);
        }});
        
        constants.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	display.setText("Constant Values:");
            	answersBox.setVisibility(8);
            	constantsBox.setVisibility(0);
        }});     
    }
    
    public void makeSpinner(final int i, final String unit) {
        adapterA1[i].setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterA2[i].setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SA1[i].setAdapter(adapterA1[i]);
        SA1[i].setSelection(PhysicsActivity.selectionA1[i]);
        SA2[i].setAdapter(adapterA2[i]);
        SA2[i].setSelection(PhysicsActivity.selectionA2[i]);
        
        if ("length".equals(unit)){
	        SA1[i].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
	            	selectionA1[i]=pos;
	            }
	            public void onNothingSelected(AdapterView<?> parent) {
	            	factorA1[i]=1;
	            }
	        });
	        
	        SA2[i].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
	            	selectionA2[i]=pos;
	            	if ("PROJECTILE".equals(type))
	                {
	            	if (j==0)
	            	{
	            		try {
	        				//temp=factorA1[0]*Double.valueOf(variable1E.getText().toString());
	        				answer1.setText(String.format("%.4f",(1/factorA2[0])*temp));
	            			answer2.setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*((k1-temp)/constant_g)*((k1-temp)/constant_g))/2+k1*((k1-temp)/constant_g))+k2));
	            			answer3.setText(String.format("%.4f",(1/factorA2[2])*(k3*((k1-temp)/constant_g)+k4)));
	            			answer4.setText(String.format("%.4f",(1/factorA2[3])*((k1-temp)/constant_g)));
	            			answer5.setText(String.format("%.4f",(1/factorA2[4])*(Math.atan(temp/k3))));
	            			answer6.setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+temp*temp))));
	        				}
	        			catch (NumberFormatException e) {
	                		;
	                		}
	        			catch (NullPointerException e) {
	                		;
	                		}
	            	}
	            	else if (j==1)
	            	{
	            		try {
	        				//temp=factorA1[1]*Double.valueOf(variable2E.getText().toString());
	        				temp1=(-k1+Math.sqrt((k1*k1)-4*(-constant_g/2)*(k2-temp)))/(-constant_g);
	        				temp2=(-k1-Math.sqrt((k1*k1)-4*(-constant_g/2)*(k2-temp)))/(-constant_g);
	        				if (temp<=peak)
	        				{
	        				answer1.setText(String.format("%.4f",(1/factorA2[0])*(-constant_g*temp1+k1))+" or\n"+String.format("%.4f",(1/factorA2[0])*(-constant_g*temp2+k1)));
	            			answer2.setText(String.format("%.4f",(1/factorA2[1])*temp));
	            			answer3.setText(String.format("%.4f",(1/factorA2[2])*(k3*temp1+k4))+" or\n"+String.format("%.4f",(1/factorA2[2])*(k3*temp2+k4)));
	            			answer4.setText(String.format("%.4f",(1/factorA2[3])*temp1)+" or\n"+String.format("%.4f",(1/factorA2[3])*temp2));
	            			answer5.setText(String.format("%.4f",(1/factorA2[4])*(Math.atan((-constant_g*temp1+k1)/k3)))+" or\n"+String.format("%.4f",(1/factorA2[4])*(Math.atan((-constant_g*temp2+k1)/k3))));
	            			answer6.setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+(-constant_g*temp1+k1)*(-constant_g*temp1+k1)))));
	        				}
	        				else
	        				{
	        					answer1.setText("");
	        					answer2.setText("y(t) cannot exceed peak value.");
	        					answer3.setText("");
	        					answer4.setText("");
	        					answer5.setText("");
	        					answer6.setText("");
	        				}
	        				}
	        			catch (NumberFormatException e) {
	                		;
	                		}
	        			catch (NullPointerException e) {
	                		;
	                		}
	            	}
	            	else if (j==2)
	            	{
	            		try {
	        				//temp=factorA1[2]*Double.valueOf(variable3E.getText().toString());
	        				answer1.setText(String.format("%.4f",(1/factorA2[0])*(-constant_g*((temp-k4)/k3)+k1)));
	            			answer2.setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*((temp-k4)/k3)*((temp-k4)/k3))/2+k1*((temp-k4)/k3)+k2)));
	            			answer3.setText(String.format("%.4f",(1/factorA2[2])*temp));
	            			answer4.setText(String.format("%.4f",(1/factorA2[3])*((temp-k4)/k3)));
	            			answer5.setText(String.format("%.4f",(1/factorA2[4])*(Math.atan((-constant_g*((temp-k4)/k3)+k1)/k3))));
	            			answer6.setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+(-constant_g*((temp-k4)/k3)+k1)*(-constant_g*((temp-k4)/k3)+k1)))));
	        				}
	        			catch (NumberFormatException e) {
	                		;
	                		}
	        			catch (NullPointerException e) {
	                		;
	                		}
	            	}
	            	else if (j==3)
	            	{
	            		try {
	        				//temp=factorA1[3]*Double.valueOf(variable4E.getText().toString());
	        				answer1.setText(String.format("%.4f",(1/factorA2[0])*(-constant_g*temp+k1)));
	            			answer2.setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*temp*temp)/2+k1*temp+k2)));
	            			answer3.setText(String.format("%.4f",(1/factorA2[2])*(k3*temp+k4)));
	            			answer4.setText(String.format("%.4f",(1/factorA2[3])*temp));
	            			answer5.setText(String.format("%.4f",(1/factorA2[4])*(Math.atan((-constant_g*temp+k1)/k3))));
	            			answer6.setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+(-constant_g*temp+k1)*(-constant_g*temp+k1)))));
	        				}
	        			catch (NumberFormatException e) {
	                		;
	                		}
	        			catch (NullPointerException e) {
	                		;
	                		}
	            	}
	            	else if (j==4)
	            	{
        			try {
        				//temp=factorA1[4]*Double.valueOf(variable5E.getText().toString());
        				answer1.setText(String.format("%.4f",(1/factorA2[0])*(k3*Math.tan(temp))));
            			answer2.setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*((k1-(k3*Math.tan(temp)))/constant_g)*((k1-(k3*Math.tan(temp)))/constant_g))/2+k1*((k1-(k3*Math.tan(temp)))/constant_g)+k2)));
            			answer3.setText(String.format("%.4f",(1/factorA2[2])*(k3*((k1-(k3*Math.tan(temp)))/constant_g)+k4)));
            			answer4.setText(String.format("%.4f",(1/factorA2[3])*((k1-(k3*Math.tan(temp)))/constant_g)));
            			answer5.setText(String.format("%.4f",(1/factorA2[4])*temp));
            			answer6.setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+(k3*Math.tan(temp))*(k3*Math.tan(temp))))));
        				}
        			catch (NumberFormatException e) {
                		;
                		}
        			catch (NullPointerException e) {
                		;
                		}
	            	}
	            	else if (j==5)
	            	{
        			try {
        				//temp=factorA1[5]*Double.valueOf(variable6E.getText().toString());
        				answer1.setText(String.format("%.4f",(1/factorA2[0])*(Math.sqrt(temp*temp-k3*k3))));
            			answer2.setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g)*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g))/2+k1*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g)+k2)));
            			answer3.setText(String.format("%.4f",(1/factorA2[2])*(k3*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g)+k4)));
            			answer4.setText(String.format("%.4f",(1/factorA2[3])*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g)));
            			answer5.setText(String.format("%.4f",(1/factorA2[4])*(Math.acos(k3/temp))));
            			answer6.setText(String.format("%.4f",(1/factorA2[5])*temp));
        				}
        			catch (NumberFormatException e) {
                		;
                		}
	            	catch (NullPointerException e) {
                		;
                		}
	                }
	                }
	            	else if ("INCLINE".equals(type))
	                {
	            		if (j==0)
		            	{
	            			try {
	            				//temp=factorA1[0]*Double.valueOf(variable1E.getText().toString());
	            				answer1.setText(String.format("%.4f",(1/factorA2[0])*temp));
	            				if (temp>0)
	            				{
	            				temp1=(temp-k1)/(-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle));
	                			answer2.setText(String.format("%.4f",(1/factorA2[1])*((-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))*(temp1*temp1/2)+k1*temp1+k3)));
	                			answer3.setText(String.format("%.4f",(1/factorA2[2])*temp1));
	            				}
	            				else if (temp<0)
	            				{
	            				temp1=(temp-k2)/(-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle));
	                			answer2.setText(String.format("%.4f",(1/factorA2[1])*((-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))*(temp1*temp1/2)+k2*temp1+k4)));
	                			answer3.setText(String.format("%.4f",(1/factorA2[2])*temp1));
	            				}
	            				else
	            				{
	            				answer2.setText(String.format("%.4f",(1/factorA2[1])*peakS));
	                    		answer3.setText(String.format("%.4f",(1/factorA2[2])*peakTime));	
	            				}
	            				}
	            			catch (NumberFormatException e) {
	                    		;
	                    		}
	            			catch (NullPointerException e) {
		                		;
		                		}
		            	}
	            		else if (j==1)
		            	{
	            			try {
	            				//temp=factorA1[1]*Double.valueOf(variable2E.getText().toString());
	            				double a1, a2;
	            				a1= (-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))/2;
	            				temp1=(-k1+Math.sqrt((k1*k1)-4*(a1)*(k3-temp)))/(2*a1);
	            				a2= (-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))/2;
	            				temp2=(-k2-Math.sqrt((k2*k2)-4*(a2)*(k4-temp)))/(2*a2);
	            				if (temp<peakS)
	            				{
	            				answer1.setText(String.format("%.4f",(1/factorA2[0])*(2*a1*temp1+k1))+" or\n"+String.format("%.4f",(1/factorA2[0])*(2*a2*temp2+k2)));
	                			answer2.setText(String.format("%.4f",(1/factorA2[1])*temp));
	                			answer3.setText(String.format("%.4f",(1/factorA2[2])*temp1)+" or\n"+String.format("%.4f",(1/factorA2[2])*temp2));
	            				}
	            				else if (temp==peakS)
	            				{
	            				answer1.setText("0");
	                			answer2.setText(String.format("%.4f",(1/factorA2[1])*temp));
	                			answer3.setText(String.format("%.4f",(1/factorA2[2])*peakTime));
	            				}
	            				else
	            				{
	            					answer1.setText("");
	            					answer2.setText("s(t) cannot exceed peak value.");
	            					answer3.setText("");
	            				}
	            				}
	            			catch (NumberFormatException e) {
	                    		;
	                    		}
	            			catch (NullPointerException e) {
		                		;
		                		}
		            	}
	            		else if (j==2)
		            	{
	            			try {
	            				//temp=factorA1[2]*Double.valueOf(variable3E.getText().toString());
	            				if (temp<peakTime)
	            				{
	            					answer1.setText(String.format("%.4f",(1/factorA2[0])*((-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))*temp+k1)));
	            					answer2.setText(String.format("%.4f",(1/factorA2[1])*((-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))*(temp*temp/2)+k1*temp+k3)));
	            				}
	            				else if (temp>peakTime)
	            				{
	                				answer1.setText(String.format("%.4f",(1/factorA2[0])*((-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))*temp+k2)));
	                    			answer2.setText(String.format("%.4f",(1/factorA2[1])*((-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))*(temp*temp/2)+k2*temp+k4)));
	            				}
	            				else {
	            					answer1.setText("0");
	                    			answer2.setText(String.format("%.4f",(1/factorA2[1])*peakS));
	            				}
	                			answer3.setText(String.format("%.4f",(1/factorA2[2])*temp));
	            				}
	            			catch (NumberFormatException e) {
	                    		;
	                    		}
	            			catch (NullPointerException e) {
		                		;
		                		}
		            	}
	                }
	            }
	            public void onNothingSelected(AdapterView<?> parent) {
	            	factorA2[i]=1;
	            }
	        });
        }
        else if ("time".equals(unit)){
        	SA1[i].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                	switch (pos)
                	{
                	case 0: factorA1[i]=1; break;
                	case 1: factorA1[i]=0.001; break;
                	}
                	selectionA1[i]=pos;
                }
                public void onNothingSelected(AdapterView<?> parent) {
                	factorA1[i]=1;
                }
            });
        	SA2[i].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                	switch (pos)
                	{
                	case 0: factorA2[i]=1; break;
                	case 1: factorA2[i]=0.001; break;
                	}
                	selectionA2[i]=pos;
                	if ("PROJECTILE".equals(type))
	                {
	            	if (j==0)
	            	{
	            		try {
	        				//temp=factorA1[0]*Double.valueOf(variable1E.getText().toString());
	        				answer1.setText(String.format("%.4f",(1/factorA2[0])*temp));
	            			answer2.setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*((k1-temp)/constant_g)*((k1-temp)/constant_g))/2+k1*((k1-temp)/constant_g))+k2));
	            			answer3.setText(String.format("%.4f",(1/factorA2[2])*(k3*((k1-temp)/constant_g)+k4)));
	            			answer4.setText(String.format("%.4f",(1/factorA2[3])*((k1-temp)/constant_g)));
	            			answer5.setText(String.format("%.4f",(1/factorA2[4])*(Math.atan(temp/k3))));
	            			answer6.setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+temp*temp))));
	        				}
	        			catch (NumberFormatException e) {
	                		;
	                		}
	        			catch (NullPointerException e) {
	                		;
	                		}
	            	}
	            	else if (j==1)
	            	{
	            		try {
	        				//temp=factorA1[1]*Double.valueOf(variable2E.getText().toString());
	        				temp1=(-k1+Math.sqrt((k1*k1)-4*(-constant_g/2)*(k2-temp)))/(-constant_g);
	        				temp2=(-k1-Math.sqrt((k1*k1)-4*(-constant_g/2)*(k2-temp)))/(-constant_g);
	        				if (temp<=peak)
	        				{
	        				answer1.setText(String.format("%.4f",(1/factorA2[0])*(-constant_g*temp1+k1))+" or\n"+String.format("%.4f",(1/factorA2[0])*(-constant_g*temp2+k1)));
	            			answer2.setText(String.format("%.4f",(1/factorA2[1])*temp));
	            			answer3.setText(String.format("%.4f",(1/factorA2[2])*(k3*temp1+k4))+" or\n"+String.format("%.4f",(1/factorA2[2])*(k3*temp2+k4)));
	            			answer4.setText(String.format("%.4f",(1/factorA2[3])*temp1)+" or\n"+String.format("%.4f",(1/factorA2[3])*temp2));
	            			answer5.setText(String.format("%.4f",(1/factorA2[4])*(Math.atan((-constant_g*temp1+k1)/k3)))+" or\n"+String.format("%.4f",(1/factorA2[4])*(Math.atan((-constant_g*temp2+k1)/k3))));
	            			answer6.setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+(-constant_g*temp1+k1)*(-constant_g*temp1+k1)))));
	        				}
	        				else
	        				{
	        					answer1.setText("");
	        					answer2.setText("y(t) cannot exceed peak value.");
	        					answer3.setText("");
	        					answer4.setText("");
	        					answer5.setText("");
	        					answer6.setText("");
	        				}
	        				}
	        			catch (NumberFormatException e) {
	                		;
	                		}
	        			catch (NullPointerException e) {
	                		;
	                		}
	            	}
	            	else if (j==2)
	            	{
	            		try {
	        				//temp=factorA1[2]*Double.valueOf(variable3E.getText().toString());
	        				answer1.setText(String.format("%.4f",(1/factorA2[0])*(-constant_g*((temp-k4)/k3)+k1)));
	            			answer2.setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*((temp-k4)/k3)*((temp-k4)/k3))/2+k1*((temp-k4)/k3)+k2)));
	            			answer3.setText(String.format("%.4f",(1/factorA2[2])*temp));
	            			answer4.setText(String.format("%.4f",(1/factorA2[3])*((temp-k4)/k3)));
	            			answer5.setText(String.format("%.4f",(1/factorA2[4])*(Math.atan((-constant_g*((temp-k4)/k3)+k1)/k3))));
	            			answer6.setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+(-constant_g*((temp-k4)/k3)+k1)*(-constant_g*((temp-k4)/k3)+k1)))));
	        				}
	        			catch (NumberFormatException e) {
	                		;
	                		}
	        			catch (NullPointerException e) {
	                		;
	                		}
	            	}
	            	else if (j==3)
	            	{
	            		try {
	        				//temp=factorA1[3]*Double.valueOf(variable4E.getText().toString());
	        				answer1.setText(String.format("%.4f",(1/factorA2[0])*(-constant_g*temp+k1)));
	            			answer2.setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*temp*temp)/2+k1*temp+k2)));
	            			answer3.setText(String.format("%.4f",(1/factorA2[2])*(k3*temp+k4)));
	            			answer4.setText(String.format("%.4f",(1/factorA2[3])*temp));
	            			answer5.setText(String.format("%.4f",(1/factorA2[4])*(Math.atan((-constant_g*temp+k1)/k3))));
	            			answer6.setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+(-constant_g*temp+k1)*(-constant_g*temp+k1)))));
	        				}
	        			catch (NumberFormatException e) {
	                		;
	                		}
	        			catch (NullPointerException e) {
	                		;
	                		}
	            	}
	            	else if (j==4)
	            	{
        			try {
        				//temp=factorA1[4]*Double.valueOf(variable5E.getText().toString());
        				answer1.setText(String.format("%.4f",(1/factorA2[0])*(k3*Math.tan(temp))));
            			answer2.setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*((k1-(k3*Math.tan(temp)))/constant_g)*((k1-(k3*Math.tan(temp)))/constant_g))/2+k1*((k1-(k3*Math.tan(temp)))/constant_g)+k2)));
            			answer3.setText(String.format("%.4f",(1/factorA2[2])*(k3*((k1-(k3*Math.tan(temp)))/constant_g)+k4)));
            			answer4.setText(String.format("%.4f",(1/factorA2[3])*((k1-(k3*Math.tan(temp)))/constant_g)));
            			answer5.setText(String.format("%.4f",(1/factorA2[4])*temp));
            			answer6.setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+(k3*Math.tan(temp))*(k3*Math.tan(temp))))));
        				}
        			catch (NumberFormatException e) {
                		;
                		}
        			catch (NullPointerException e) {
                		;
                		}
	            	}
	            	else if (j==5)
	            	{
        			try {
        				//temp=factorA1[5]*Double.valueOf(variable6E.getText().toString());
        				answer1.setText(String.format("%.4f",(1/factorA2[0])*(Math.sqrt(temp*temp-k3*k3))));
            			answer2.setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g)*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g))/2+k1*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g)+k2)));
            			answer3.setText(String.format("%.4f",(1/factorA2[2])*(k3*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g)+k4)));
            			answer4.setText(String.format("%.4f",(1/factorA2[3])*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g)));
            			answer5.setText(String.format("%.4f",(1/factorA2[4])*(Math.acos(k3/temp))));
            			answer6.setText(String.format("%.4f",(1/factorA2[5])*temp));
        				}
        			catch (NumberFormatException e) {
                		;
                		}
	            	catch (NullPointerException e) {
                		;
                		}
	                }
	                }
	            	else if ("INCLINE".equals(type))
	                {
	            		if (j==0)
		            	{
	            			try {
	            				//temp=factorA1[0]*Double.valueOf(variable1E.getText().toString());
	            				answer1.setText(String.format("%.4f",(1/factorA2[0])*temp));
	            				if (temp>0)
	            				{
	            				temp1=(temp-k1)/(-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle));
	                			answer2.setText(String.format("%.4f",(1/factorA2[1])*((-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))*(temp1*temp1/2)+k1*temp1+k3)));
	                			answer3.setText(String.format("%.4f",(1/factorA2[2])*temp1));
	            				}
	            				else if (temp<0)
	            				{
	            				temp1=(temp-k2)/(-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle));
	                			answer2.setText(String.format("%.4f",(1/factorA2[1])*((-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))*(temp1*temp1/2)+k2*temp1+k4)));
	                			answer3.setText(String.format("%.4f",(1/factorA2[2])*temp1));
	            				}
	            				else
	            				{
	            				answer2.setText(String.format("%.4f",(1/factorA2[1])*peakS));
	                    		answer3.setText(String.format("%.4f",(1/factorA2[2])*peakTime));	
	            				}
	            				}
	            			catch (NumberFormatException e) {
	                    		;
	                    		}
	            			catch (NullPointerException e) {
		                		;
		                		}
		            	}
	            		else if (j==1)
		            	{
	            			try {
	            				//temp=factorA1[1]*Double.valueOf(variable2E.getText().toString());
	            				double a1, a2;
	            				a1= (-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))/2;
	            				temp1=(-k1+Math.sqrt((k1*k1)-4*(a1)*(k3-temp)))/(2*a1);
	            				a2= (-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))/2;
	            				temp2=(-k2-Math.sqrt((k2*k2)-4*(a2)*(k4-temp)))/(2*a2);
	            				if (temp<peakS)
	            				{
	            				answer1.setText(String.format("%.4f",(1/factorA2[0])*(2*a1*temp1+k1))+" or\n"+String.format("%.4f",(1/factorA2[0])*(2*a2*temp2+k2)));
	                			answer2.setText(String.format("%.4f",(1/factorA2[1])*temp));
	                			answer3.setText(String.format("%.4f",(1/factorA2[2])*temp1)+" or\n"+String.format("%.4f",(1/factorA2[2])*temp2));
	            				}
	            				else if (temp==peakS)
	            				{
	            				answer1.setText("0");
	                			answer2.setText(String.format("%.4f",(1/factorA2[1])*temp));
	                			answer3.setText(String.format("%.4f",(1/factorA2[2])*peakTime));
	            				}
	            				else
	            				{
	            					answer1.setText("");
	            					answer2.setText("s(t) cannot exceed peak value.");
	            					answer3.setText("");
	            				}
	            				}
	            			catch (NumberFormatException e) {
	                    		;
	                    		}
	            			catch (NullPointerException e) {
		                		;
		                		}
		            	}
	            		else if (j==2)
		            	{
	            			try {
	            				//temp=factorA1[2]*Double.valueOf(variable3E.getText().toString());
	            				if (temp<peakTime)
	            				{
	            					answer1.setText(String.format("%.4f",(1/factorA2[0])*((-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))*temp+k1)));
	            					answer2.setText(String.format("%.4f",(1/factorA2[1])*((-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))*(temp*temp/2)+k1*temp+k3)));
	            				}
	            				else if (temp>peakTime)
	            				{
	                				answer1.setText(String.format("%.4f",(1/factorA2[0])*((-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))*temp+k2)));
	                    			answer2.setText(String.format("%.4f",(1/factorA2[1])*((-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))*(temp*temp/2)+k2*temp+k4)));
	            				}
	            				else {
	            					answer1.setText("0");
	                    			answer2.setText(String.format("%.4f",(1/factorA2[1])*peakS));
	            				}
	                			answer3.setText(String.format("%.4f",(1/factorA2[2])*temp));
	            				}
	            			catch (NumberFormatException e) {
	                    		;
	                    		}
	            			catch (NullPointerException e) {
		                		;
		                		}
		            	}
	                }
                }
                public void onNothingSelected(AdapterView<?> parent) {
                	factorA2[i]=1;
                }
            });
        }
        else if ("velocity".equals(unit)){
        	SA1[i].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                	selectionA1[i]=pos;
                }
                public void onNothingSelected(AdapterView<?> parent) {
                	factorA1[i]=1;
                }
            });
        	SA2[i].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                	selectionA2[i]=pos;
                	if ("PROJECTILE".equals(type))
	                {
	            	if (j==0)
	            	{
	            		try {
	        				//temp=factorA1[0]*Double.valueOf(variable1E.getText().toString());
	        				answer1.setText(String.format("%.4f",(1/factorA2[0])*temp));
	            			answer2.setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*((k1-temp)/constant_g)*((k1-temp)/constant_g))/2+k1*((k1-temp)/constant_g))+k2));
	            			answer3.setText(String.format("%.4f",(1/factorA2[2])*(k3*((k1-temp)/constant_g)+k4)));
	            			answer4.setText(String.format("%.4f",(1/factorA2[3])*((k1-temp)/constant_g)));
	            			answer5.setText(String.format("%.4f",(1/factorA2[4])*(Math.atan(temp/k3))));
	            			answer6.setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+temp*temp))));
	        				}
	        			catch (NumberFormatException e) {
	                		;
	                		}
	        			catch (NullPointerException e) {
	                		;
	                		}
	            	}
	            	else if (j==1)
	            	{
	            		try {
	        				//temp=factorA1[1]*Double.valueOf(variable2E.getText().toString());
	        				temp1=(-k1+Math.sqrt((k1*k1)-4*(-constant_g/2)*(k2-temp)))/(-constant_g);
	        				temp2=(-k1-Math.sqrt((k1*k1)-4*(-constant_g/2)*(k2-temp)))/(-constant_g);
	        				if (temp<=peak)
	        				{
	        				answer1.setText(String.format("%.4f",(1/factorA2[0])*(-constant_g*temp1+k1))+" or\n"+String.format("%.4f",(1/factorA2[0])*(-constant_g*temp2+k1)));
	            			answer2.setText(String.format("%.4f",(1/factorA2[1])*temp));
	            			answer3.setText(String.format("%.4f",(1/factorA2[2])*(k3*temp1+k4))+" or\n"+String.format("%.4f",(1/factorA2[2])*(k3*temp2+k4)));
	            			answer4.setText(String.format("%.4f",(1/factorA2[3])*temp1)+" or\n"+String.format("%.4f",(1/factorA2[3])*temp2));
	            			answer5.setText(String.format("%.4f",(1/factorA2[4])*(Math.atan((-constant_g*temp1+k1)/k3)))+" or\n"+String.format("%.4f",(1/factorA2[4])*(Math.atan((-constant_g*temp2+k1)/k3))));
	            			answer6.setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+(-constant_g*temp1+k1)*(-constant_g*temp1+k1)))));
	        				}
	        				else
	        				{
	        					answer1.setText("");
	        					answer2.setText("y(t) cannot exceed peak value.");
	        					answer3.setText("");
	        					answer4.setText("");
	        					answer5.setText("");
	        					answer6.setText("");
	        				}
	        				}
	        			catch (NumberFormatException e) {
	                		;
	                		}
	        			catch (NullPointerException e) {
	                		;
	                		}
	            	}
	            	else if (j==2)
	            	{
	            		try {
	        				//temp=factorA1[2]*Double.valueOf(variable3E.getText().toString());
	        				answer1.setText(String.format("%.4f",(1/factorA2[0])*(-constant_g*((temp-k4)/k3)+k1)));
	            			answer2.setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*((temp-k4)/k3)*((temp-k4)/k3))/2+k1*((temp-k4)/k3)+k2)));
	            			answer3.setText(String.format("%.4f",(1/factorA2[2])*temp));
	            			answer4.setText(String.format("%.4f",(1/factorA2[3])*((temp-k4)/k3)));
	            			answer5.setText(String.format("%.4f",(1/factorA2[4])*(Math.atan((-constant_g*((temp-k4)/k3)+k1)/k3))));
	            			answer6.setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+(-constant_g*((temp-k4)/k3)+k1)*(-constant_g*((temp-k4)/k3)+k1)))));
	        				}
	        			catch (NumberFormatException e) {
	                		;
	                		}
	        			catch (NullPointerException e) {
	                		;
	                		}
	            	}
	            	else if (j==3)
	            	{
	            		try {
	        				//temp=factorA1[3]*Double.valueOf(variable4E.getText().toString());
	        				answer1.setText(String.format("%.4f",(1/factorA2[0])*(-constant_g*temp+k1)));
	            			answer2.setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*temp*temp)/2+k1*temp+k2)));
	            			answer3.setText(String.format("%.4f",(1/factorA2[2])*(k3*temp+k4)));
	            			answer4.setText(String.format("%.4f",(1/factorA2[3])*temp));
	            			answer5.setText(String.format("%.4f",(1/factorA2[4])*(Math.atan((-constant_g*temp+k1)/k3))));
	            			answer6.setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+(-constant_g*temp+k1)*(-constant_g*temp+k1)))));
	        				}
	        			catch (NumberFormatException e) {
	                		;
	                		}
	        			catch (NullPointerException e) {
	                		;
	                		}
	            	}
	            	else if (j==4)
	            	{
        			try {
        				//temp=factorA1[4]*Double.valueOf(variable5E.getText().toString());
        				answer1.setText(String.format("%.4f",(1/factorA2[0])*(k3*Math.tan(temp))));
            			answer2.setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*((k1-(k3*Math.tan(temp)))/constant_g)*((k1-(k3*Math.tan(temp)))/constant_g))/2+k1*((k1-(k3*Math.tan(temp)))/constant_g)+k2)));
            			answer3.setText(String.format("%.4f",(1/factorA2[2])*(k3*((k1-(k3*Math.tan(temp)))/constant_g)+k4)));
            			answer4.setText(String.format("%.4f",(1/factorA2[3])*((k1-(k3*Math.tan(temp)))/constant_g)));
            			answer5.setText(String.format("%.4f",(1/factorA2[4])*temp));
            			answer6.setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+(k3*Math.tan(temp))*(k3*Math.tan(temp))))));
        				}
        			catch (NumberFormatException e) {
                		;
                		}
        			catch (NullPointerException e) {
                		;
                		}
	            	}
	            	else if (j==5)
	            	{
        			try {
        				//temp=factorA1[5]*Double.valueOf(variable6E.getText().toString());
        				answer1.setText(String.format("%.4f",(1/factorA2[0])*(Math.sqrt(temp*temp-k3*k3))));
            			answer2.setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g)*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g))/2+k1*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g)+k2)));
            			answer3.setText(String.format("%.4f",(1/factorA2[2])*(k3*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g)+k4)));
            			answer4.setText(String.format("%.4f",(1/factorA2[3])*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g)));
            			answer5.setText(String.format("%.4f",(1/factorA2[4])*(Math.acos(k3/temp))));
            			answer6.setText(String.format("%.4f",(1/factorA2[5])*temp));
        				}
        			catch (NumberFormatException e) {
                		;
                		}
	            	catch (NullPointerException e) {
                		;
                		}
	                }
	                }
	            	else if ("INCLINE".equals(type))
	                {
	            		if (j==0)
		            	{
	            			try {
	            				//temp=factorA1[0]*Double.valueOf(variable1E.getText().toString());
	            				answer1.setText(String.format("%.4f",(1/factorA2[0])*temp));
	            				if (temp>0)
	            				{
	            				temp1=(temp-k1)/(-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle));
	                			answer2.setText(String.format("%.4f",(1/factorA2[1])*((-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))*(temp1*temp1/2)+k1*temp1+k3)));
	                			answer3.setText(String.format("%.4f",(1/factorA2[2])*temp1));
	            				}
	            				else if (temp<0)
	            				{
	            				temp1=(temp-k2)/(-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle));
	                			answer2.setText(String.format("%.4f",(1/factorA2[1])*((-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))*(temp1*temp1/2)+k2*temp1+k4)));
	                			answer3.setText(String.format("%.4f",(1/factorA2[2])*temp1));
	            				}
	            				else
	            				{
	            				answer2.setText(String.format("%.4f",(1/factorA2[1])*peakS));
	                    		answer3.setText(String.format("%.4f",(1/factorA2[2])*peakTime));	
	            				}
	            				}
	            			catch (NumberFormatException e) {
	                    		;
	                    		}
	            			catch (NullPointerException e) {
		                		;
		                		}
		            	}
	            		else if (j==1)
		            	{
	            			try {
	            				//temp=factorA1[1]*Double.valueOf(variable2E.getText().toString());
	            				double a1, a2;
	            				a1= (-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))/2;
	            				temp1=(-k1+Math.sqrt((k1*k1)-4*(a1)*(k3-temp)))/(2*a1);
	            				a2= (-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))/2;
	            				temp2=(-k2-Math.sqrt((k2*k2)-4*(a2)*(k4-temp)))/(2*a2);
	            				if (temp<peakS)
	            				{
	            				answer1.setText(String.format("%.4f",(1/factorA2[0])*(2*a1*temp1+k1))+" or\n"+String.format("%.4f",(1/factorA2[0])*(2*a2*temp2+k2)));
	                			answer2.setText(String.format("%.4f",(1/factorA2[1])*temp));
	                			answer3.setText(String.format("%.4f",(1/factorA2[2])*temp1)+" or\n"+String.format("%.4f",(1/factorA2[2])*temp2));
	            				}
	            				else if (temp==peakS)
	            				{
	            				answer1.setText("0");
	                			answer2.setText(String.format("%.4f",(1/factorA2[1])*temp));
	                			answer3.setText(String.format("%.4f",(1/factorA2[2])*peakTime));
	            				}
	            				else
	            				{
	            					answer1.setText("");
	            					answer2.setText("s(t) cannot exceed peak value.");
	            					answer3.setText("");
	            				}
	            				}
	            			catch (NumberFormatException e) {
	                    		;
	                    		}
	            			catch (NullPointerException e) {
		                		;
		                		}
		            	}
	            		else if (j==2)
		            	{
	            			try {
	            				//temp=factorA1[2]*Double.valueOf(variable3E.getText().toString());
	            				if (temp<peakTime)
	            				{
	            					answer1.setText(String.format("%.4f",(1/factorA2[0])*((-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))*temp+k1)));
	            					answer2.setText(String.format("%.4f",(1/factorA2[1])*((-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))*(temp*temp/2)+k1*temp+k3)));
	            				}
	            				else if (temp>peakTime)
	            				{
	                				answer1.setText(String.format("%.4f",(1/factorA2[0])*((-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))*temp+k2)));
	                    			answer2.setText(String.format("%.4f",(1/factorA2[1])*((-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))*(temp*temp/2)+k2*temp+k4)));
	            				}
	            				else {
	            					answer1.setText("0");
	                    			answer2.setText(String.format("%.4f",(1/factorA2[1])*peakS));
	            				}
	                			answer3.setText(String.format("%.4f",(1/factorA2[2])*temp));
	            				}
	            			catch (NumberFormatException e) {
	                    		;
	                    		}
	            			catch (NullPointerException e) {
		                		;
		                		}
		            	}
	                }
                }
                public void onNothingSelected(AdapterView<?> parent) {
                	factorA2[i]=1;
                }
            });
        }
        else if ("angle".equals(unit)){
        	SA1[i].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                	switch (pos)
                	{
                	case 0: factorA1[i]=1; break;
                	case 1: factorA1[i]=0.017453292519943295769236907684886; break;
                	}
                	selectionA1[i]=pos;
                }
                public void onNothingSelected(AdapterView<?> parent) {
                	factorA1[i]=1;
                }
            });
        	
        	SA2[i].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                	switch (pos)
                	{
                	case 0: factorA2[i]=1; break;
                	case 1: factorA2[i]=0.017453292519943295769236907684886; break;
                	}
                	selectionA2[i]=pos;
                	if ("PROJECTILE".equals(type))
	                {
	            	if (j==0)
	            	{
	            		try {
	        				//temp=factorA1[0]*Double.valueOf(variable1E.getText().toString());
	        				answer1.setText(String.format("%.4f",(1/factorA2[0])*temp));
	            			answer2.setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*((k1-temp)/constant_g)*((k1-temp)/constant_g))/2+k1*((k1-temp)/constant_g))+k2));
	            			answer3.setText(String.format("%.4f",(1/factorA2[2])*(k3*((k1-temp)/constant_g)+k4)));
	            			answer4.setText(String.format("%.4f",(1/factorA2[3])*((k1-temp)/constant_g)));
	            			answer5.setText(String.format("%.4f",(1/factorA2[4])*(Math.atan(temp/k3))));
	            			answer6.setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+temp*temp))));
	        				}
	        			catch (NumberFormatException e) {
	                		;
	                		}
	        			catch (NullPointerException e) {
	                		;
	                		}
	            	}
	            	else if (j==1)
	            	{
	            		try {
	        				//temp=factorA1[1]*Double.valueOf(variable2E.getText().toString());
	        				temp1=(-k1+Math.sqrt((k1*k1)-4*(-constant_g/2)*(k2-temp)))/(-constant_g);
	        				temp2=(-k1-Math.sqrt((k1*k1)-4*(-constant_g/2)*(k2-temp)))/(-constant_g);
	        				if (temp<=peak)
	        				{
	        				answer1.setText(String.format("%.4f",(1/factorA2[0])*(-constant_g*temp1+k1))+" or\n"+String.format("%.4f",(1/factorA2[0])*(-constant_g*temp2+k1)));
	            			answer2.setText(String.format("%.4f",(1/factorA2[1])*temp));
	            			answer3.setText(String.format("%.4f",(1/factorA2[2])*(k3*temp1+k4))+" or\n"+String.format("%.4f",(1/factorA2[2])*(k3*temp2+k4)));
	            			answer4.setText(String.format("%.4f",(1/factorA2[3])*temp1)+" or\n"+String.format("%.4f",(1/factorA2[3])*temp2));
	            			answer5.setText(String.format("%.4f",(1/factorA2[4])*(Math.atan((-constant_g*temp1+k1)/k3)))+" or\n"+String.format("%.4f",(1/factorA2[4])*(Math.atan((-constant_g*temp2+k1)/k3))));
	            			answer6.setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+(-constant_g*temp1+k1)*(-constant_g*temp1+k1)))));
	        				}
	        				else
	        				{
	        					answer1.setText("");
	        					answer2.setText("y(t) cannot exceed peak value.");
	        					answer3.setText("");
	        					answer4.setText("");
	        					answer5.setText("");
	        					answer6.setText("");
	        				}
	        				}
	        			catch (NumberFormatException e) {
	                		;
	                		}
	        			catch (NullPointerException e) {
	                		;
	                		}
	            	}
	            	else if (j==2)
	            	{
	            		try {
	        				//temp=factorA1[2]*Double.valueOf(variable3E.getText().toString());
	        				answer1.setText(String.format("%.4f",(1/factorA2[0])*(-constant_g*((temp-k4)/k3)+k1)));
	            			answer2.setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*((temp-k4)/k3)*((temp-k4)/k3))/2+k1*((temp-k4)/k3)+k2)));
	            			answer3.setText(String.format("%.4f",(1/factorA2[2])*temp));
	            			answer4.setText(String.format("%.4f",(1/factorA2[3])*((temp-k4)/k3)));
	            			answer5.setText(String.format("%.4f",(1/factorA2[4])*(Math.atan((-constant_g*((temp-k4)/k3)+k1)/k3))));
	            			answer6.setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+(-constant_g*((temp-k4)/k3)+k1)*(-constant_g*((temp-k4)/k3)+k1)))));
	        				}
	        			catch (NumberFormatException e) {
	                		;
	                		}
	        			catch (NullPointerException e) {
	                		;
	                		}
	            	}
	            	else if (j==3)
	            	{
	            		try {
	        				//temp=factorA1[3]*Double.valueOf(variable4E.getText().toString());
	        				answer1.setText(String.format("%.4f",(1/factorA2[0])*(-constant_g*temp+k1)));
	            			answer2.setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*temp*temp)/2+k1*temp+k2)));
	            			answer3.setText(String.format("%.4f",(1/factorA2[2])*(k3*temp+k4)));
	            			answer4.setText(String.format("%.4f",(1/factorA2[3])*temp));
	            			answer5.setText(String.format("%.4f",(1/factorA2[4])*(Math.atan((-constant_g*temp+k1)/k3))));
	            			answer6.setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+(-constant_g*temp+k1)*(-constant_g*temp+k1)))));
	        				}
	        			catch (NumberFormatException e) {
	                		;
	                		}
	        			catch (NullPointerException e) {
	                		;
	                		}
	            	}
	            	else if (j==4)
	            	{
        			try {
        				//temp=factorA1[4]*Double.valueOf(variable5E.getText().toString());
        				answer1.setText(String.format("%.4f",(1/factorA2[0])*(k3*Math.tan(temp))));
            			answer2.setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*((k1-(k3*Math.tan(temp)))/constant_g)*((k1-(k3*Math.tan(temp)))/constant_g))/2+k1*((k1-(k3*Math.tan(temp)))/constant_g)+k2)));
            			answer3.setText(String.format("%.4f",(1/factorA2[2])*(k3*((k1-(k3*Math.tan(temp)))/constant_g)+k4)));
            			answer4.setText(String.format("%.4f",(1/factorA2[3])*((k1-(k3*Math.tan(temp)))/constant_g)));
            			answer5.setText(String.format("%.4f",(1/factorA2[4])*temp));
            			answer6.setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+(k3*Math.tan(temp))*(k3*Math.tan(temp))))));
        				}
        			catch (NumberFormatException e) {
                		;
                		}
        			catch (NullPointerException e) {
                		;
                		}
	            	}
	            	else if (j==5)
	            	{
        			try {
        				//temp=factorA1[5]*Double.valueOf(variable6E.getText().toString());
        				answer1.setText(String.format("%.4f",(1/factorA2[0])*(Math.sqrt(temp*temp-k3*k3))));
            			answer2.setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g)*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g))/2+k1*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g)+k2)));
            			answer3.setText(String.format("%.4f",(1/factorA2[2])*(k3*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g)+k4)));
            			answer4.setText(String.format("%.4f",(1/factorA2[3])*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g)));
            			answer5.setText(String.format("%.4f",(1/factorA2[4])*(Math.acos(k3/temp))));
            			answer6.setText(String.format("%.4f",(1/factorA2[5])*temp));
        				}
        			catch (NumberFormatException e) {
                		;
                		}
	            	catch (NullPointerException e) {
                		;
                		}
	                }
	                }
	            	else if ("INCLINE".equals(type))
	                {
	            		if (j==0)
		            	{
	            			try {
	            				//temp=factorA1[0]*Double.valueOf(variable1E.getText().toString());
	            				answer1.setText(String.format("%.4f",(1/factorA2[0])*temp));
	            				if (temp>0)
	            				{
	            				temp1=(temp-k1)/(-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle));
	                			answer2.setText(String.format("%.4f",(1/factorA2[1])*((-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))*(temp1*temp1/2)+k1*temp1+k3)));
	                			answer3.setText(String.format("%.4f",(1/factorA2[2])*temp1));
	            				}
	            				else if (temp<0)
	            				{
	            				temp1=(temp-k2)/(-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle));
	                			answer2.setText(String.format("%.4f",(1/factorA2[1])*((-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))*(temp1*temp1/2)+k2*temp1+k4)));
	                			answer3.setText(String.format("%.4f",(1/factorA2[2])*temp1));
	            				}
	            				else
	            				{
	            				answer2.setText(String.format("%.4f",(1/factorA2[1])*peakS));
	                    		answer3.setText(String.format("%.4f",(1/factorA2[2])*peakTime));	
	            				}
	            				}
	            			catch (NumberFormatException e) {
	                    		;
	                    		}
	            			catch (NullPointerException e) {
		                		;
		                		}
		            	}
	            		else if (j==1)
		            	{
	            			try {
	            				//temp=factorA1[1]*Double.valueOf(variable2E.getText().toString());
	            				double a1, a2;
	            				a1= (-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))/2;
	            				temp1=(-k1+Math.sqrt((k1*k1)-4*(a1)*(k3-temp)))/(2*a1);
	            				a2= (-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))/2;
	            				temp2=(-k2-Math.sqrt((k2*k2)-4*(a2)*(k4-temp)))/(2*a2);
	            				if (temp<peakS)
	            				{
	            				answer1.setText(String.format("%.4f",(1/factorA2[0])*(2*a1*temp1+k1))+" or\n"+String.format("%.4f",(1/factorA2[0])*(2*a2*temp2+k2)));
	                			answer2.setText(String.format("%.4f",(1/factorA2[1])*temp));
	                			answer3.setText(String.format("%.4f",(1/factorA2[2])*temp1)+" or\n"+String.format("%.4f",(1/factorA2[2])*temp2));
	            				}
	            				else if (temp==peakS)
	            				{
	            				answer1.setText("0");
	                			answer2.setText(String.format("%.4f",(1/factorA2[1])*temp));
	                			answer3.setText(String.format("%.4f",(1/factorA2[2])*peakTime));
	            				}
	            				else
	            				{
	            					answer1.setText("");
	            					answer2.setText("s(t) cannot exceed peak value.");
	            					answer3.setText("");
	            				}
	            				}
	            			catch (NumberFormatException e) {
	                    		;
	                    		}
	            			catch (NullPointerException e) {
		                		;
		                		}
		            	}
	            		else if (j==2)
		            	{
	            			try {
	            				//temp=factorA1[2]*Double.valueOf(variable3E.getText().toString());
	            				if (temp<peakTime)
	            				{
	            					answer1.setText(String.format("%.4f",(1/factorA2[0])*((-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))*temp+k1)));
	            					answer2.setText(String.format("%.4f",(1/factorA2[1])*((-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))*(temp*temp/2)+k1*temp+k3)));
	            				}
	            				else if (temp>peakTime)
	            				{
	                				answer1.setText(String.format("%.4f",(1/factorA2[0])*((-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))*temp+k2)));
	                    			answer2.setText(String.format("%.4f",(1/factorA2[1])*((-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))*(temp*temp/2)+k2*temp+k4)));
	            				}
	            				else {
	            					answer1.setText("0");
	                    			answer2.setText(String.format("%.4f",(1/factorA2[1])*peakS));
	            				}
	                			answer3.setText(String.format("%.4f",(1/factorA2[2])*temp));
	            				}
	            			catch (NumberFormatException e) {
	                    		;
	                    		}
	            			catch (NullPointerException e) {
		                		;
		                		}
		            	}
	                }
                }
                public void onNothingSelected(AdapterView<?> parent) {
                	factorA2[i]=1;
                }
            });
        }
        else if ("mass".equals(unit)){
        	SA1[i].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                	switch (pos)
                	{
                	case 0: factorA1[i]=1; break;
                	case 1: factorA1[i]=0.001; break;
                	}
                	selectionA1[i]=pos;
                }
                public void onNothingSelected(AdapterView<?> parent) {
                	factorA1[i]=1;
                }
            });
        	SA2[i].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                	switch (pos)
                	{
                	case 0: factorA2[i]=1; break;
                	case 1: factorA2[i]=0.001; break;
                	}
                	selectionA2[i]=pos;
                	if ("PROJECTILE".equals(type))
	                {
	            	if (j==0)
	            	{
	            		try {
	        				//temp=factorA1[0]*Double.valueOf(variable1E.getText().toString());
	        				answer1.setText(String.format("%.4f",(1/factorA2[0])*temp));
	            			answer2.setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*((k1-temp)/constant_g)*((k1-temp)/constant_g))/2+k1*((k1-temp)/constant_g))+k2));
	            			answer3.setText(String.format("%.4f",(1/factorA2[2])*(k3*((k1-temp)/constant_g)+k4)));
	            			answer4.setText(String.format("%.4f",(1/factorA2[3])*((k1-temp)/constant_g)));
	            			answer5.setText(String.format("%.4f",(1/factorA2[4])*(Math.atan(temp/k3))));
	            			answer6.setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+temp*temp))));
	        				}
	        			catch (NumberFormatException e) {
	                		;
	                		}
	        			catch (NullPointerException e) {
	                		;
	                		}
	            	}
	            	else if (j==1)
	            	{
	            		try {
	        				//temp=factorA1[1]*Double.valueOf(variable2E.getText().toString());
	        				temp1=(-k1+Math.sqrt((k1*k1)-4*(-constant_g/2)*(k2-temp)))/(-constant_g);
	        				temp2=(-k1-Math.sqrt((k1*k1)-4*(-constant_g/2)*(k2-temp)))/(-constant_g);
	        				if (temp<=peak)
	        				{
	        				answer1.setText(String.format("%.4f",(1/factorA2[0])*(-constant_g*temp1+k1))+" or\n"+String.format("%.4f",(1/factorA2[0])*(-constant_g*temp2+k1)));
	            			answer2.setText(String.format("%.4f",(1/factorA2[1])*temp));
	            			answer3.setText(String.format("%.4f",(1/factorA2[2])*(k3*temp1+k4))+" or\n"+String.format("%.4f",(1/factorA2[2])*(k3*temp2+k4)));
	            			answer4.setText(String.format("%.4f",(1/factorA2[3])*temp1)+" or\n"+String.format("%.4f",(1/factorA2[3])*temp2));
	            			answer5.setText(String.format("%.4f",(1/factorA2[4])*(Math.atan((-constant_g*temp1+k1)/k3)))+" or\n"+String.format("%.4f",(1/factorA2[4])*(Math.atan((-constant_g*temp2+k1)/k3))));
	            			answer6.setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+(-constant_g*temp1+k1)*(-constant_g*temp1+k1)))));
	        				}
	        				else
	        				{
	        					answer1.setText("");
	        					answer2.setText("y(t) cannot exceed peak value.");
	        					answer3.setText("");
	        					answer4.setText("");
	        					answer5.setText("");
	        					answer6.setText("");
	        				}
	        				}
	        			catch (NumberFormatException e) {
	                		;
	                		}
	        			catch (NullPointerException e) {
	                		;
	                		}
	            	}
	            	else if (j==2)
	            	{
	            		try {
	        				//temp=factorA1[2]*Double.valueOf(variable3E.getText().toString());
	        				answer1.setText(String.format("%.4f",(1/factorA2[0])*(-constant_g*((temp-k4)/k3)+k1)));
	            			answer2.setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*((temp-k4)/k3)*((temp-k4)/k3))/2+k1*((temp-k4)/k3)+k2)));
	            			answer3.setText(String.format("%.4f",(1/factorA2[2])*temp));
	            			answer4.setText(String.format("%.4f",(1/factorA2[3])*((temp-k4)/k3)));
	            			answer5.setText(String.format("%.4f",(1/factorA2[4])*(Math.atan((-constant_g*((temp-k4)/k3)+k1)/k3))));
	            			answer6.setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+(-constant_g*((temp-k4)/k3)+k1)*(-constant_g*((temp-k4)/k3)+k1)))));
	        				}
	        			catch (NumberFormatException e) {
	                		;
	                		}
	        			catch (NullPointerException e) {
	                		;
	                		}
	            	}
	            	else if (j==3)
	            	{
	            		try {
	        				//temp=factorA1[3]*Double.valueOf(variable4E.getText().toString());
	        				answer1.setText(String.format("%.4f",(1/factorA2[0])*(-constant_g*temp+k1)));
	            			answer2.setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*temp*temp)/2+k1*temp+k2)));
	            			answer3.setText(String.format("%.4f",(1/factorA2[2])*(k3*temp+k4)));
	            			answer4.setText(String.format("%.4f",(1/factorA2[3])*temp));
	            			answer5.setText(String.format("%.4f",(1/factorA2[4])*(Math.atan((-constant_g*temp+k1)/k3))));
	            			answer6.setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+(-constant_g*temp+k1)*(-constant_g*temp+k1)))));
	        				}
	        			catch (NumberFormatException e) {
	                		;
	                		}
	        			catch (NullPointerException e) {
	                		;
	                		}
	            	}
	            	else if (j==4)
	            	{
        			try {
        				//temp=factorA1[4]*Double.valueOf(variable5E.getText().toString());
        				answer1.setText(String.format("%.4f",(1/factorA2[0])*(k3*Math.tan(temp))));
            			answer2.setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*((k1-(k3*Math.tan(temp)))/constant_g)*((k1-(k3*Math.tan(temp)))/constant_g))/2+k1*((k1-(k3*Math.tan(temp)))/constant_g)+k2)));
            			answer3.setText(String.format("%.4f",(1/factorA2[2])*(k3*((k1-(k3*Math.tan(temp)))/constant_g)+k4)));
            			answer4.setText(String.format("%.4f",(1/factorA2[3])*((k1-(k3*Math.tan(temp)))/constant_g)));
            			answer5.setText(String.format("%.4f",(1/factorA2[4])*temp));
            			answer6.setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+(k3*Math.tan(temp))*(k3*Math.tan(temp))))));
        				}
        			catch (NumberFormatException e) {
                		;
                		}
        			catch (NullPointerException e) {
                		;
                		}
	            	}
	            	else if (j==5)
	            	{
        			try {
        				//temp=factorA1[5]*Double.valueOf(variable6E.getText().toString());
        				answer1.setText(String.format("%.4f",(1/factorA2[0])*(Math.sqrt(temp*temp-k3*k3))));
            			answer2.setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g)*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g))/2+k1*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g)+k2)));
            			answer3.setText(String.format("%.4f",(1/factorA2[2])*(k3*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g)+k4)));
            			answer4.setText(String.format("%.4f",(1/factorA2[3])*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g)));
            			answer5.setText(String.format("%.4f",(1/factorA2[4])*(Math.acos(k3/temp))));
            			answer6.setText(String.format("%.4f",(1/factorA2[5])*temp));
        				}
        			catch (NumberFormatException e) {
                		;
                		}
	            	catch (NullPointerException e) {
                		;
                		}
	                }
	                }
	            	else if ("INCLINE".equals(type))
	                {
	            		if (j==0)
		            	{
	            			try {
	            				//temp=factorA1[0]*Double.valueOf(variable1E.getText().toString());
	            				answer1.setText(String.format("%.4f",(1/factorA2[0])*temp));
	            				if (temp>0)
	            				{
	            				temp1=(temp-k1)/(-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle));
	                			answer2.setText(String.format("%.4f",(1/factorA2[1])*((-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))*(temp1*temp1/2)+k1*temp1+k3)));
	                			answer3.setText(String.format("%.4f",(1/factorA2[2])*temp1));
	            				}
	            				else if (temp<0)
	            				{
	            				temp1=(temp-k2)/(-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle));
	                			answer2.setText(String.format("%.4f",(1/factorA2[1])*((-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))*(temp1*temp1/2)+k2*temp1+k4)));
	                			answer3.setText(String.format("%.4f",(1/factorA2[2])*temp1));
	            				}
	            				else
	            				{
	            				answer2.setText(String.format("%.4f",(1/factorA2[1])*peakS));
	                    		answer3.setText(String.format("%.4f",(1/factorA2[2])*peakTime));	
	            				}
	            				}
	            			catch (NumberFormatException e) {
	                    		;
	                    		}
	            			catch (NullPointerException e) {
		                		;
		                		}
		            	}
	            		else if (j==1)
		            	{
	            			try {
	            				//temp=factorA1[1]*Double.valueOf(variable2E.getText().toString());
	            				double a1, a2;
	            				a1= (-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))/2;
	            				temp1=(-k1+Math.sqrt((k1*k1)-4*(a1)*(k3-temp)))/(2*a1);
	            				a2= (-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))/2;
	            				temp2=(-k2-Math.sqrt((k2*k2)-4*(a2)*(k4-temp)))/(2*a2);
	            				if (temp<peakS)
	            				{
	            				answer1.setText(String.format("%.4f",(1/factorA2[0])*(2*a1*temp1+k1))+" or\n"+String.format("%.4f",(1/factorA2[0])*(2*a2*temp2+k2)));
	                			answer2.setText(String.format("%.4f",(1/factorA2[1])*temp));
	                			answer3.setText(String.format("%.4f",(1/factorA2[2])*temp1)+" or\n"+String.format("%.4f",(1/factorA2[2])*temp2));
	            				}
	            				else if (temp==peakS)
	            				{
	            				answer1.setText("0");
	                			answer2.setText(String.format("%.4f",(1/factorA2[1])*temp));
	                			answer3.setText(String.format("%.4f",(1/factorA2[2])*peakTime));
	            				}
	            				else
	            				{
	            					answer1.setText("");
	            					answer2.setText("s(t) cannot exceed peak value.");
	            					answer3.setText("");
	            				}
	            				}
	            			catch (NumberFormatException e) {
	                    		;
	                    		}
	            			catch (NullPointerException e) {
		                		;
		                		}
		            	}
	            		else if (j==2)
		            	{
	            			try {
	            				//temp=factorA1[2]*Double.valueOf(variable3E.getText().toString());
	            				if (temp<peakTime)
	            				{
	            					answer1.setText(String.format("%.4f",(1/factorA2[0])*((-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))*temp+k1)));
	            					answer2.setText(String.format("%.4f",(1/factorA2[1])*((-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))*(temp*temp/2)+k1*temp+k3)));
	            				}
	            				else if (temp>peakTime)
	            				{
	                				answer1.setText(String.format("%.4f",(1/factorA2[0])*((-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))*temp+k2)));
	                    			answer2.setText(String.format("%.4f",(1/factorA2[1])*((-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))*(temp*temp/2)+k2*temp+k4)));
	            				}
	            				else {
	            					answer1.setText("0");
	                    			answer2.setText(String.format("%.4f",(1/factorA2[1])*peakS));
	            				}
	                			answer3.setText(String.format("%.4f",(1/factorA2[2])*temp));
	            				}
	            			catch (NumberFormatException e) {
	                    		;
	                    		}
	            			catch (NullPointerException e) {
		                		;
		                		}
		            	}
	                }
                }
                public void onNothingSelected(AdapterView<?> parent) {
                	factorA2[i]=1;
                }
            });
        }
        else if ("force".equals(unit)){
        	SA1[i].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                	switch (pos)
                	{
                	case 0: factorA1[i]=1; break;
                	case 1: factorA1[i]=4.44822162; break;
                	}
                	selectionA1[i]=pos;
                }
                public void onNothingSelected(AdapterView<?> parent) {
                	factorA1[i]=1;
                }
            });
        	SA2[i].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                	switch (pos)
                	{
                	case 0: factorA2[i]=1; break;
                	case 1: factorA2[i]=4.44822162; break;
                	}
                	selectionA2[i]=pos;
                	if ("PROJECTILE".equals(type))
	                {
	            	if (j==0)
	            	{
	            		try {
	        				//temp=factorA1[0]*Double.valueOf(variable1E.getText().toString());
	        				answer1.setText(String.format("%.4f",(1/factorA2[0])*temp));
	            			answer2.setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*((k1-temp)/constant_g)*((k1-temp)/constant_g))/2+k1*((k1-temp)/constant_g))+k2));
	            			answer3.setText(String.format("%.4f",(1/factorA2[2])*(k3*((k1-temp)/constant_g)+k4)));
	            			answer4.setText(String.format("%.4f",(1/factorA2[3])*((k1-temp)/constant_g)));
	            			answer5.setText(String.format("%.4f",(1/factorA2[4])*(Math.atan(temp/k3))));
	            			answer6.setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+temp*temp))));
	        				}
	        			catch (NumberFormatException e) {
	                		;
	                		}
	        			catch (NullPointerException e) {
	                		;
	                		}
	            	}
	            	else if (j==1)
	            	{
	            		try {
	        				//temp=factorA1[1]*Double.valueOf(variable2E.getText().toString());
	        				temp1=(-k1+Math.sqrt((k1*k1)-4*(-constant_g/2)*(k2-temp)))/(-constant_g);
	        				temp2=(-k1-Math.sqrt((k1*k1)-4*(-constant_g/2)*(k2-temp)))/(-constant_g);
	        				if (temp<=peak)
	        				{
	        				answer1.setText(String.format("%.4f",(1/factorA2[0])*(-constant_g*temp1+k1))+" or\n"+String.format("%.4f",(1/factorA2[0])*(-constant_g*temp2+k1)));
	            			answer2.setText(String.format("%.4f",(1/factorA2[1])*temp));
	            			answer3.setText(String.format("%.4f",(1/factorA2[2])*(k3*temp1+k4))+" or\n"+String.format("%.4f",(1/factorA2[2])*(k3*temp2+k4)));
	            			answer4.setText(String.format("%.4f",(1/factorA2[3])*temp1)+" or\n"+String.format("%.4f",(1/factorA2[3])*temp2));
	            			answer5.setText(String.format("%.4f",(1/factorA2[4])*(Math.atan((-constant_g*temp1+k1)/k3)))+" or\n"+String.format("%.4f",(1/factorA2[4])*(Math.atan((-constant_g*temp2+k1)/k3))));
	            			answer6.setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+(-constant_g*temp1+k1)*(-constant_g*temp1+k1)))));
	        				}
	        				else
	        				{
	        					answer1.setText("");
	        					answer2.setText("y(t) cannot exceed peak value.");
	        					answer3.setText("");
	        					answer4.setText("");
	        					answer5.setText("");
	        					answer6.setText("");
	        				}
	        				}
	        			catch (NumberFormatException e) {
	                		;
	                		}
	        			catch (NullPointerException e) {
	                		;
	                		}
	            	}
	            	else if (j==2)
	            	{
	            		try {
	        				//temp=factorA1[2]*Double.valueOf(variable3E.getText().toString());
	        				answer1.setText(String.format("%.4f",(1/factorA2[0])*(-constant_g*((temp-k4)/k3)+k1)));
	            			answer2.setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*((temp-k4)/k3)*((temp-k4)/k3))/2+k1*((temp-k4)/k3)+k2)));
	            			answer3.setText(String.format("%.4f",(1/factorA2[2])*temp));
	            			answer4.setText(String.format("%.4f",(1/factorA2[3])*((temp-k4)/k3)));
	            			answer5.setText(String.format("%.4f",(1/factorA2[4])*(Math.atan((-constant_g*((temp-k4)/k3)+k1)/k3))));
	            			answer6.setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+(-constant_g*((temp-k4)/k3)+k1)*(-constant_g*((temp-k4)/k3)+k1)))));
	        				}
	        			catch (NumberFormatException e) {
	                		;
	                		}
	        			catch (NullPointerException e) {
	                		;
	                		}
	            	}
	            	else if (j==3)
	            	{
	            		try {
	        				//temp=factorA1[3]*Double.valueOf(variable4E.getText().toString());
	        				answer1.setText(String.format("%.4f",(1/factorA2[0])*(-constant_g*temp+k1)));
	            			answer2.setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*temp*temp)/2+k1*temp+k2)));
	            			answer3.setText(String.format("%.4f",(1/factorA2[2])*(k3*temp+k4)));
	            			answer4.setText(String.format("%.4f",(1/factorA2[3])*temp));
	            			answer5.setText(String.format("%.4f",(1/factorA2[4])*(Math.atan((-constant_g*temp+k1)/k3))));
	            			answer6.setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+(-constant_g*temp+k1)*(-constant_g*temp+k1)))));
	        				}
	        			catch (NumberFormatException e) {
	                		;
	                		}
	        			catch (NullPointerException e) {
	                		;
	                		}
	            	}
	            	else if (j==4)
	            	{
        			try {
        				//temp=factorA1[4]*Double.valueOf(variable5E.getText().toString());
        				answer1.setText(String.format("%.4f",(1/factorA2[0])*(k3*Math.tan(temp))));
            			answer2.setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*((k1-(k3*Math.tan(temp)))/constant_g)*((k1-(k3*Math.tan(temp)))/constant_g))/2+k1*((k1-(k3*Math.tan(temp)))/constant_g)+k2)));
            			answer3.setText(String.format("%.4f",(1/factorA2[2])*(k3*((k1-(k3*Math.tan(temp)))/constant_g)+k4)));
            			answer4.setText(String.format("%.4f",(1/factorA2[3])*((k1-(k3*Math.tan(temp)))/constant_g)));
            			answer5.setText(String.format("%.4f",(1/factorA2[4])*temp));
            			answer6.setText(String.format("%.4f",(1/factorA2[5])*(Math.sqrt(k3*k3+(k3*Math.tan(temp))*(k3*Math.tan(temp))))));
        				}
        			catch (NumberFormatException e) {
                		;
                		}
        			catch (NullPointerException e) {
                		;
                		}
	            	}
	            	else if (j==5)
	            	{
        			try {
        				//temp=factorA1[5]*Double.valueOf(variable6E.getText().toString());
        				answer1.setText(String.format("%.4f",(1/factorA2[0])*(Math.sqrt(temp*temp-k3*k3))));
            			answer2.setText(String.format("%.4f",(1/factorA2[1])*(-(constant_g*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g)*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g))/2+k1*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g)+k2)));
            			answer3.setText(String.format("%.4f",(1/factorA2[2])*(k3*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g)+k4)));
            			answer4.setText(String.format("%.4f",(1/factorA2[3])*((k1-(Math.sqrt(temp*temp-k3*k3)))/constant_g)));
            			answer5.setText(String.format("%.4f",(1/factorA2[4])*(Math.acos(k3/temp))));
            			answer6.setText(String.format("%.4f",(1/factorA2[5])*temp));
        				}
        			catch (NumberFormatException e) {
                		;
                		}
	            	catch (NullPointerException e) {
                		;
                		}
	                }
	                }
	            	else if ("INCLINE".equals(type))
	                {
	            		if (j==0)
		            	{
	            			try {
	            				//temp=factorA1[0]*Double.valueOf(variable1E.getText().toString());
	            				answer1.setText(String.format("%.4f",(1/factorA2[0])*temp));
	            				if (temp>0)
	            				{
	            				temp1=(temp-k1)/(-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle));
	                			answer2.setText(String.format("%.4f",(1/factorA2[1])*((-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))*(temp1*temp1/2)+k1*temp1+k3)));
	                			answer3.setText(String.format("%.4f",(1/factorA2[2])*temp1));
	            				}
	            				else if (temp<0)
	            				{
	            				temp1=(temp-k2)/(-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle));
	                			answer2.setText(String.format("%.4f",(1/factorA2[1])*((-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))*(temp1*temp1/2)+k2*temp1+k4)));
	                			answer3.setText(String.format("%.4f",(1/factorA2[2])*temp1));
	            				}
	            				else
	            				{
	            				answer2.setText(String.format("%.4f",(1/factorA2[1])*peakS));
	                    		answer3.setText(String.format("%.4f",(1/factorA2[2])*peakTime));	
	            				}
	            				}
	            			catch (NumberFormatException e) {
	                    		;
	                    		}
	            			catch (NullPointerException e) {
		                		;
		                		}
		            	}
	            		else if (j==1)
		            	{
	            			try {
	            				//temp=factorA1[1]*Double.valueOf(variable2E.getText().toString());
	            				double a1, a2;
	            				a1= (-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))/2;
	            				temp1=(-k1+Math.sqrt((k1*k1)-4*(a1)*(k3-temp)))/(2*a1);
	            				a2= (-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))/2;
	            				temp2=(-k2-Math.sqrt((k2*k2)-4*(a2)*(k4-temp)))/(2*a2);
	            				if (temp<peakS)
	            				{
	            				answer1.setText(String.format("%.4f",(1/factorA2[0])*(2*a1*temp1+k1))+" or\n"+String.format("%.4f",(1/factorA2[0])*(2*a2*temp2+k2)));
	                			answer2.setText(String.format("%.4f",(1/factorA2[1])*temp));
	                			answer3.setText(String.format("%.4f",(1/factorA2[2])*temp1)+" or\n"+String.format("%.4f",(1/factorA2[2])*temp2));
	            				}
	            				else if (temp==peakS)
	            				{
	            				answer1.setText("0");
	                			answer2.setText(String.format("%.4f",(1/factorA2[1])*temp));
	                			answer3.setText(String.format("%.4f",(1/factorA2[2])*peakTime));
	            				}
	            				else
	            				{
	            					answer1.setText("");
	            					answer2.setText("s(t) cannot exceed peak value.");
	            					answer3.setText("");
	            				}
	            				}
	            			catch (NumberFormatException e) {
	                    		;
	                    		}
	            			catch (NullPointerException e) {
		                		;
		                		}
		            	}
	            		else if (j==2)
		            	{
	            			try {
	            				//temp=factorA1[2]*Double.valueOf(variable3E.getText().toString());
	            				if (temp<peakTime)
	            				{
	            					answer1.setText(String.format("%.4f",(1/factorA2[0])*((-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))*temp+k1)));
	            					answer2.setText(String.format("%.4f",(1/factorA2[1])*((-constant_g*Math.sin(angle)-u1*constant_g*Math.cos(angle))*(temp*temp/2)+k1*temp+k3)));
	            				}
	            				else if (temp>peakTime)
	            				{
	                				answer1.setText(String.format("%.4f",(1/factorA2[0])*((-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))*temp+k2)));
	                    			answer2.setText(String.format("%.4f",(1/factorA2[1])*((-constant_g*Math.sin(angle)+u1*constant_g*Math.cos(angle))*(temp*temp/2)+k2*temp+k4)));
	            				}
	            				else {
	            					answer1.setText("0");
	                    			answer2.setText(String.format("%.4f",(1/factorA2[1])*peakS));
	            				}
	                			answer3.setText(String.format("%.4f",(1/factorA2[2])*temp));
	            				}
	            			catch (NumberFormatException e) {
	                    		;
	                    		}
	            			catch (NullPointerException e) {
		                		;
		                		}
		            	}
	                }
                }
                public void onNothingSelected(AdapterView<?> parent) {
                	factorA2[i]=1;
                }
            });
        }
    }
}