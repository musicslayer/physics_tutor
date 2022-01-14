package com.musicslayer.physicstutor;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

public class HScroll extends HorizontalScrollView {
	float mx, my;
    float curX, curY;

    public HScroll(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public HScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HScroll(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float curX, curY;

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                mx = event.getRawX();
                my = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                curX = event.getRawX();
                curY = event.getRawY();
                this.scrollBy((int) (mx - curX), (int) (my - curY));
                mx = curX;
                my = curY;
                break;
            case MotionEvent.ACTION_UP:
            	curX = event.getRawX();
                curY = event.getRawY();
                this.scrollBy((int) (mx - curX), (int) (my - curY));
                break;
        }

        return true;
    }
}