package com.example.diabetes.name;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by behnam_b on 7/24/2016.
 */
public class MyEditText extends EditText {

    private Context context;
    private AttributeSet attrs;
    private int defStyle;

    public MyEditText(Context context) {
        super(context);
        this.context=context;
        init();
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        this.attrs=attrs;
        init();
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context=context;
        this.attrs=attrs;
        this.defStyle=defStyle;
        init();
    }

    private void init() {
       Typeface font=Typeface.createFromAsset(getContext().getAssets(), "nazaninn.TTF");
        this.setTypeface(font);

    }
    @Override
    public void setTypeface(Typeface tf, int style) {
        tf=Typeface.createFromAsset(getContext().getAssets(), "nazaninn.TTF");
        super.setTypeface(tf, style);
    }

    @Override
    public void setTypeface(Typeface tf) {
        tf=Typeface.createFromAsset(getContext().getAssets(), "nazaninn.TTF");
       super.setTypeface(tf);
    }}