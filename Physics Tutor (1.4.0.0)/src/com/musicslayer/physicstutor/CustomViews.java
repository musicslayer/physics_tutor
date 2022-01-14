package com.musicslayer.physicstutor;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.*;

import java.lang.reflect.Method;

class CustomTableRow extends TableRow {
    public CustomTableRow(Context context) {
        super(context);
    }

    public void addView(View view) {
        view.setLayoutParams(new TableRow.LayoutParams(view.getLayoutParams().width,view.getLayoutParams().height));
        super.addView(view);
    }
}

class CustomSpinner extends Spinner {
    final private int originalWidth, originalHeight;
    final private Context context;
    final private CharSequence Sstuff[];

    public CustomSpinner(Context context, CharSequence[] Sstuff, CharSequence prompt, int selection, int originalWidth, int originalHeight) {
        super(context);
        this.context=context;
        this.originalWidth=originalWidth;
        this.originalHeight=originalHeight;
        this.Sstuff=Sstuff;
        setPadding(getPaddingLeft(),0,getPaddingRight(),0);
        setPrompt(prompt);
        setBackgroundResource(android.R.drawable.btn_default);
        setColorFilter(R.color.myBlue2);
        update();
        setSelection(selection);
    }

    public CustomSpinner(Context context, int stuff, CharSequence prompt, int selection, int originalWidth, int originalHeight) {
        this(context,context.getResources().getTextArray(stuff),prompt,selection,originalWidth,originalHeight);
    }

    public CustomSpinner(Context context, int stuff, CharSequence prompt, int selection) {
        this(context,context.getResources().getTextArray(stuff),prompt,selection,80,50);
    }

    public CustomSpinner(Context context, int stuff, CharSequence prompt) {
        this(context,context.getResources().getTextArray(stuff),prompt,0,80,50);
    }

    public CustomSpinner(Context context, CharSequence[] Sstuff, CharSequence prompt) {
        this(context,Sstuff,prompt,0,80,50);
    }

    public void setColorFilter(int color)
    {
        invalidate();
        getBackground().setColorFilter(getResources().getColor(color), PorterDuff.Mode.MULTIPLY);
    }

    public void update() {
        final int w, h, selection=getSelectedItemPosition();

        if (originalWidth<0){w=originalWidth;}
        else{w=dpToPx((int)(originalWidth*PhysicsActivity.scale));}
        if (originalHeight<0){h=originalHeight;}
        else{h=dpToPx((int)(originalHeight*PhysicsActivity.scale));}

        if ((getParent() != null) && (CustomTableRow.class.equals(getParent().getClass())))
        {setLayoutParams(new TableRow.LayoutParams(w,h));}
        else
        {setLayoutParams(new FrameLayout.LayoutParams(w,h));}

        CustomAdapter<CharSequence> adapter = new CustomAdapter<CharSequence>(context, Sstuff);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setAdapter(adapter);
        setSelection(selection);
    }

    private int dpToPx(int dp)
    {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }
}

class CustomAdapter<CharSequence> extends ArrayAdapter<CharSequence> {
    public CustomAdapter(Context context, CharSequence[] objects) {
        super(context, android.R.layout.simple_spinner_item, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        if (view instanceof TextView) {
            ((TextView) view).setGravity(17);
            ((TextView) view).setTextSize(1,14*PhysicsActivity.scale);
        }
        return view;
    }
}

class CustomLinearLayout extends LinearLayout{
    final private int originalWidth, originalHeight;

    public CustomLinearLayout(Context context, int originalWidth, int originalHeight, int orientation) {
        super(context);
        this.originalWidth=originalWidth;
        this.originalHeight=originalHeight;
        update();
        setBaselineAligned(false);
        setOrientation(orientation);
    }

    public CustomLinearLayout(Context context, int originalWidth, int originalHeight) {
        this(context,originalWidth,originalHeight,0);
    }

    public CustomLinearLayout(Context context) {
        this(context,-2,-2,0);
    }

    public void update() {
        final int w, h;

        if (originalWidth<0){w=originalWidth;}
        else{w=dpToPx((int)(originalWidth*PhysicsActivity.scale));}
        if (originalHeight<0){h=originalHeight;}
        else{h=dpToPx((int)(originalHeight*PhysicsActivity.scale));}

        if ((getParent() != null) && (CustomTableRow.class.equals(getParent().getClass())))
        {setLayoutParams(new TableRow.LayoutParams(w,h));}
        else
        {setLayoutParams(new FrameLayout.LayoutParams(w,h));}
    }

    private int dpToPx(int dp)
    {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }
}

class CustomScrollView extends ScrollView{
    public CustomScrollView(Context context, int width, int height) {
        super(context);
        //TODO Scrollbar still covers up text
        setLayoutParams(new ViewGroup.LayoutParams(width,height));
        //setPadding(0,0,dpToPx(40),0);
        //setScrollBarStyle(16777216);
        disableScrollbarFading(this);
    }

    private void disableScrollbarFading(View view) {
        try
        {
            Method setScrollbarFadingEnabled = View.class.getDeclaredMethod("setScrollbarFadingEnabled", boolean.class);
            setScrollbarFadingEnabled.setAccessible(true);
            setScrollbarFadingEnabled.invoke(view, false);
        }
        catch (Exception e) {}
    }
    /*
    private int dpToPx(int dp)
    {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }
    */
}

class CustomHorizontalScrollView extends HorizontalScrollView{
    public CustomHorizontalScrollView(Context context, int width, int height) {
        super(context);
        setScrollBarStyle(16777216);
        setLayoutParams(new ViewGroup.LayoutParams(width,height));
        disableScrollbarFading(this);
    }

    private void disableScrollbarFading(View view) {
        try
        {
            Method setScrollbarFadingEnabled = View.class.getDeclaredMethod("setScrollbarFadingEnabled", boolean.class);
            setScrollbarFadingEnabled.setAccessible(true);
            setScrollbarFadingEnabled.invoke(view, false);
        }
        catch (Exception e) {}
    }
}

class CustomButton extends Button {
    final private float originalTextSize;
    final private int originalWidth, originalHeight;

    public CustomButton(Context context, int originalWidth, int originalHeight, String text, float originalTextSize, int color) {
        super(context);
        this.originalTextSize=originalTextSize;
        this.originalWidth=originalWidth;
        this.originalHeight=originalHeight;
        setText(text);
        update();
        setPadding(0,0,0,0);
        setColorFilter(color);
    }

    public CustomButton(Context context, int originalWidth, int originalHeight, String text, float originalTextSize) {
        super(context);
        this.originalTextSize=originalTextSize;
        this.originalWidth=originalWidth;
        this.originalHeight=originalHeight;
        setText(text);
        update();
        setPadding(0,0,0,0);
    }

    public void setColorFilter(int color)
    {
        invalidate();
        getBackground().setColorFilter(getResources().getColor(color), PorterDuff.Mode.MULTIPLY);
    }

    public void clearColorFilter()
    {
        invalidate();
        getBackground().clearColorFilter();
    }

    public void setText(String text) {
        super.setText(text,BufferType.SPANNABLE);
        setIncludeFontPadding(false);
    }

    public void setTextSize(int unit, float size) {
        super.setTextSize(unit, size);
        setText(getText(), BufferType.SPANNABLE);
        setIncludeFontPadding(false);
    }

    public void update() {
        final int w, h;

        if (originalWidth<0){w=originalWidth;}
        else{w=dpToPx((int)(originalWidth*PhysicsActivity.scale));}
        if (originalHeight<0){h=originalHeight;}
        else{h=dpToPx((int)(originalHeight*PhysicsActivity.scale));}

        if ((getParent() != null) && (CustomTableRow.class.equals(getParent().getClass())))
        {setLayoutParams(new TableRow.LayoutParams(w,h));}
        else
        {setLayoutParams(new FrameLayout.LayoutParams(w,h));}

        setTextSize(1,originalTextSize*PhysicsActivity.scale);
    }

    private int dpToPx(int dp)
    {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }
}

class CustomTextView extends TextView {
    final private float originalTextSize;
    final private int originalWidth, originalHeight;

    public CustomTextView(Context context, int originalWidth, int originalHeight, float originalTextSize, String text, int color) {
        super(context);
        this.originalWidth=originalWidth;
        this.originalHeight=originalHeight;
        this.originalTextSize=originalTextSize;
        update();
        setText(text);
        setTextColor(color);
    }

    public CustomTextView(Context context, int originalWidth, int originalHeight, float originalTextSize, String text) {
        super(context);
        this.originalWidth=originalWidth;
        this.originalHeight=originalHeight;
        this.originalTextSize=originalTextSize;
        update();
        setText(text);
    }

    public CustomTextView(Context context, int originalWidth, int originalHeight, float originalTextSize) {
        super(context);
        this.originalWidth=originalWidth;
        this.originalHeight=originalHeight;
        this.originalTextSize=originalTextSize;
        update();
    }

    public CustomTextView(Context context, float originalTextSize) {
        this(context,-2,-2,originalTextSize);
    }

    public CustomTextView(Context context) {
        this(context,-2,-2,14);
    }

    public void setTextColor(int color) {
        invalidate();
        super.setTextColor(color);
    }

    public void setText(String text) {
        super.setText(text, BufferType.SPANNABLE);
    }

    public void setTextSize(int unit, float size) {
        super.setTextSize(unit, size);
        setText(getText(), BufferType.SPANNABLE);
    }

    public void update() {
        final int w, h;

        if (originalWidth<0){w=originalWidth;}
        else{w=dpToPx((int)(originalWidth*PhysicsActivity.scale));}
        if (originalHeight<0){h=originalHeight;}
        else{h=dpToPx((int)(originalHeight*PhysicsActivity.scale));}

        if ((getParent() != null) && (CustomTableRow.class.equals(getParent().getClass())))
        {setLayoutParams(new TableRow.LayoutParams(w,h));}
        else
        {setLayoutParams(new FrameLayout.LayoutParams(w,h));}

        setTextSize(1,originalTextSize*PhysicsActivity.scale);
    }

    private int dpToPx(int dp)
    {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }
}

class CustomEditText extends EditText {
    final private float originalTextSize;
    final private int originalWidth, originalHeight;

    public CustomEditText(Context context, int originalWidth, int originalHeight, float originalTextSize) {
        super(context);
        this.originalWidth=originalWidth;
        this.originalHeight=originalHeight;
        this.originalTextSize=originalTextSize;
        setPadding(getPaddingLeft(),0,getPaddingRight(),0);
        if(PhysicsActivity.prefs[1]==0){setInputType(3);}
        update();
    }

    public CustomEditText(Context context) {
        this(context,130,50,14);
    }

    public void setColorFilter(int color)
    {
        invalidate();
        getBackground().setColorFilter(getResources().getColor(color), PorterDuff.Mode.MULTIPLY);
    }

    public void clearColorFilter()
    {
        invalidate();
        getBackground().clearColorFilter();
    }

    public void setTextSize(int unit, float size) {
        super.setTextSize(unit, size);
        setText(getText(), BufferType.SPANNABLE);
    }

    public void update() {
        final int w, h;

        if (originalWidth<0){w=originalWidth;}
        else{w=dpToPx((int)(originalWidth*PhysicsActivity.scale));}
        if (originalHeight<0){h=originalHeight;}
        else{h=dpToPx((int)(originalHeight*PhysicsActivity.scale));}

        if ((getParent() != null) && (CustomTableRow.class.equals(getParent().getClass())))
        {setLayoutParams(new TableRow.LayoutParams(w,h));}
        else
        {setLayoutParams(new FrameLayout.LayoutParams(w,h));}

        setTextSize(1,originalTextSize*PhysicsActivity.scale);
    }

    private int dpToPx(int dp)
    {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }
}

class CustomImageView extends ImageView {
    final private int originalWidth, originalHeight;

    public CustomImageView(Context context, int originalWidth, int originalHeight, int source) {
        super(context);
        setAdjustViewBounds(true);
        this.originalWidth=originalWidth;
        this.originalHeight=originalHeight;
        setImageResource(source);
        update();
    }

    public void update() {
        final int w, h;

        if (originalWidth<0){w=originalWidth;}
        else{w=dpToPx((int)(originalWidth*PhysicsActivity.scale));}
        if (originalHeight<0){h=originalHeight;}
        else{h=dpToPx((int)(originalHeight*PhysicsActivity.scale));}

        setLayoutParams(new FrameLayout.LayoutParams(w,h));
    }

    private int dpToPx(int dp)
    {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }
}

class ZoomCustomImageView extends CustomImageView {
    private int source;

    public ZoomCustomImageView(Context context, int originalWidth, int originalHeight, int source) {
        super(context, originalWidth, originalHeight, source);
        this.source=source;
    }

    //TODO we shouldn't need this case
    public ZoomCustomImageView(Context context, int originalWidth, int originalHeight) {
        this(context,originalWidth,originalHeight,0);
    }

    public void setImageResource(int source) {
        super.setImageResource(source);
        this.source=source;
    }

    public View fullScreen() {
        if(Build.VERSION.SDK_INT>=8)
        {
            final WebView myWebView=new WebView(getContext());
            myWebView.getSettings().setBuiltInZoomControls(true);
            myWebView.loadUrl("file:///android_"+getResources().getString(source));
            return myWebView;
        }
        else
        {
            final TwoDScrollView T=new TwoDScrollView(getContext());
            final CustomImageView I=new CustomImageView(getContext(),-2,-2,source);
            T.addView(I);
            return T;
        }
    }
}