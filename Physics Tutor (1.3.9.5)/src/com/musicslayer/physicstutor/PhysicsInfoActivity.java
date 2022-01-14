package com.musicslayer.physicstutor;

import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.*;
import com.localytics.android.*;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

public class PhysicsInfoActivity extends PhysicsActivity {
	private LocalyticsSession localyticsSession;
	private Button proceed;
	private TextView description, constants, notes;
	private ImageView helper;
    private WebView myWebView;
    private LinearLayout L, L1, LA, LAA;
    private ScrollView S_Info, S_Image;
    private HorizontalScrollView H_Image;

    boolean seeImage=false;
	
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
	public void onBackPressed() {
        if (seeImage)
        {
            setContentView(L);
            seeImage=false;
        }
        else
        {
            startActivity(new Intent(PhysicsInfoActivity.this, PhysicsProblemsActivity.class));
        }
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L=makeInfo();
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
	}

    LinearLayout makeInfo () {
        myWebView=new WebView(this);
        myWebView.getSettings().setBuiltInZoomControls(true);

        L1=new LinearLayout(this);
        L1.setOrientation(1);
        L1.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));

        LA=new LinearLayout(this);
        LA.setOrientation(1);
        LA.setLayoutParams(new ViewGroup.LayoutParams(dpToPx((int)(300*scale)), dpToPx((int)(250*scale))));

        proceed=new Button(this);
        proceed.setText("PROCEED");
        proceed.setTextSize(1,22*scale);
        proceed.setLayoutParams(new ViewGroup.LayoutParams(dpToPx((int)(120*scale)), dpToPx((int)(50*scale))));
        proceed.getBackground().setColorFilter(0xFF0A85FF, PorterDuff.Mode.MULTIPLY);
        proceed.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		startActivity(new Intent(PhysicsInfoActivity.this, PhysicsInputActivity.class));
            }
        });

        S_Info=new ScrollView(this);
        S_Info.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));

        LAA=new LinearLayout(this);
        LAA.setOrientation(1);
        LAA.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));

        fillInfo();

        LAA.addView(description);
        LAA.addView(constants);
        LAA.addView(notes);
        S_Info.addView(LAA);

        LA.addView(proceed);
        LA.addView(S_Info);

        S_Image=new ScrollView(this);
        S_Image.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        H_Image=new HorizontalScrollView(this);
        H_Image.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));

        H_Image.addView(helper);
        S_Image.addView(H_Image);

        L1.addView(LA);
        L1.addView(S_Image);

        return L1;
    }

    private void fillInfo()
    {
        description = new TextView(this);
        description.setTextSize(1,14*scale);
        constants = new TextView(this);
        constants.setTextSize(1,14*scale);
        notes = new TextView(this);
        notes.setTextSize(1,14*scale);
        helper=new ImageView(this);
        helper.setLayoutParams(new ViewGroup.LayoutParams(dpToPx((int)(260*scale)), dpToPx((int)(260*scale))));
        helper.setAdjustViewBounds(true);
        helper.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(myWebView);
                seeImage=true;
            }
        });
        if ("PROJECTILE".equals(type))
        {
            myWebView.loadUrl("file:///android_res/raw/info_projectile.jpg");
        	setTitle("Physics Tutor - Projectile Info");
        	helper.setImageResource(R.raw.info_projectile);
            description.setText("A simple projectile motion problem. An object is launched and is under constant gravitational pull downward. There are no drag forces.");
        	constants.setText("\nConstants used in this problem:\nConstant g = "+String.format("%.4f", constant_g)+" m/s^2");
        	notes.setText("\nNotes:\nThe object is assumed to follow the flight path for all time. You must check to see if the object would crash into something yourself.");
        }
        else if ("VECTORS".equals(type))
        {
            myWebView.loadUrl("file:///android_res/raw/info_vectors.jpg");
        	setTitle("Physics Tutor - Vectors Info");
        	helper.setImageResource(R.raw.info_vectors);
        	description.setText("Vectors of various magnitude and direction are given, and we wish to resolve them into one resultant vector. Also, an equilibrant vector that cancels the resultant vector can be found.");
        	constants.setText("\nConstants used in this problem:\nNone");
        	notes.setText("\nNotes:\nAlthough the solver uses force vectors, this problem can apply to other vectors that obey superposition.");
        }
        else if ("INCLINE".equals(type))
        {
            myWebView.loadUrl("file:///android_res/raw/info_incline.jpg");
        	setTitle("Physics Tutor - Incline Info");
        	helper.setImageResource(R.raw.info_incline);
        	description.setText("An object slides along an incline under the influence of gravity, the normal force, and friction.");
        	constants.setText("\nConstants used in this problem:\nConstant g = "+String.format("%.4f", constant_g)+" m/s^2");
        	notes.setText("\nNotes:\nThe incline is assumed to extend forever, and the object never leaves the surface of the incline. Also, currently it is explicitly assumed that friction is small enough to allow motion. If this is not the case, then the solver may give wrong answers.");
        }
        else if ("INCLINE_SIMPLE".equals(type))
        {
            myWebView.loadUrl("file:///android_res/raw/info_incline_simple.jpg");
        	setTitle("Physics Tutor - Simple Incline Info");
        	helper.setImageResource(R.raw.info_incline_simple);
        	description.setText("An object slides along an incline under the influence of gravity, the normal force, and friction.");
        	constants.setText("\nConstants used in this problem:\nConstant g = "+String.format("%.4f", constant_g)+" m/s^2");
        	notes.setText("\nNotes:\nThe incline is assumed to extend forever, and the object never leaves the surface of the incline.");
        }
        else if ("SPRING_SIMPLE".equals(type))
        {
            myWebView.loadUrl("file:///android_res/raw/info_spring_simple.jpg");
        	setTitle("Physics Tutor - Simple Spring Info");
        	helper.setImageResource(R.raw.info_spring_simple);
        	description.setText("A mass is attached to a spring. The mass oscillates back and forth only under the influence of the spring's elastic force.");
        	constants.setText("\nConstants used in this problem:\nNone");
        	notes.setText("\nNotes:\nGravity and friction on the mass are neglected. Also, the spring is ideal (it has no mass, can stretch and compress without limit, never breaks, and obeys Hooke's Law).");
        }
        else if ("SPRING".equals(type))
        {
            myWebView.loadUrl("file:///android_res/raw/info_spring.jpg");
        	setTitle("Physics Tutor - Spring Info");
        	helper.setImageResource(R.raw.info_spring);
        	description.setText("A mass is attached to a spring. The mass oscillates back and forth only under the influence of the spring's elastic force.");
        	constants.setText("\nConstants used in this problem:\nNone");
        	notes.setText("\nNotes:\nThe equilibrium position is assumed to be the origin.\nGravity and friction on the mass are neglected. Also, the spring is ideal (it has no mass, can stretch and compress without limit, never breaks, and obeys Hooke's Law).");
        }
    }

    private int dpToPx(int dp)
    {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }
}