package com.musicslayer.physicstutor;

import com.Localytics.android.LocalyticsSession;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class PhysicsSolverSplitActivity extends PhysicsActivity {
	private LocalyticsSession localyticsSession;
	Toast toast;
	Button explanation, answers;
	
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
		toast.show();
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
    				
    return true;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.solver_split);
        //setTitle("Physics Tutor - "+type.charAt(0)+type.substring(1).toLowerCase()+" Solution");
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
        
        explanation=(Button) findViewById(R.id.Button_Explanation);
        answers=(Button) findViewById(R.id.Button_Answers);
        toast=Toast.makeText(getApplicationContext(), "Press MENU and select QUIT to exit, or EDIT INPUTS to change the inputs.", 0);
        
        if ("PROJECTILE".equals(type))
        {
        	setTitle("Physics Tutor - Projectile Solution");
        }
        else if ("INCLINE".equals(type))
        {
        	setTitle("Physics Tutor - Incline Solution");
        }
        else if ("INCLINE_SIMPLE".equals(type))
        {
        	setTitle("Physics Tutor - Simple Incline Solution");
        }
        
        explanation.setOnClickListener(new View.OnClickListener() {
    		public void onClick(View v) {
    			startActivity(new Intent(PhysicsSolverSplitActivity.this, PhysicsSolverSplitExplanationActivity.class));
    		}});    
        
        answers.setOnClickListener(new View.OnClickListener() {
    		public void onClick(View v) {
    			startActivity(new Intent(PhysicsSolverSplitActivity.this, PhysicsSolverSplitAnswersActivity.class));
    		}}); 
        
    }

}
