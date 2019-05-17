package com.example.taoying.testapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class SubButton extends android.support.v7.widget.AppCompatButton {
    private static final String TAG = "SubButton";

    public SubButton(Context context) {
        super(context);
    }

    public SubButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public SubButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
    
}
