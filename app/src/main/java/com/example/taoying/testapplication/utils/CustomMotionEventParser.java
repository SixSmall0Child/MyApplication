package com.example.taoying.testapplication.utils;

import android.util.Log;
import android.view.MotionEvent;

import com.example.taoying.testapplication.bean.CustomMotionEvent;

public class CustomMotionEventParser {
    private static final String TAG = "CustomMotionEventParser";

    public static CustomMotionEvent parse(MotionEvent event, int width, int height) {
        Log.d(TAG, "parse: event.getEventTime() = " + event.getEventTime() + " event.getDownTime() = " + event.getDownTime());
        CustomMotionEvent fakeMotionEvent = new CustomMotionEvent();
        fakeMotionEvent.setEventType(event.getAction());
        fakeMotionEvent.setDeviceId(event.getDeviceId());
        fakeMotionEvent.setFlags(event.getFlags());
        fakeMotionEvent.setButtonState(event.getButtonState());
        fakeMotionEvent.setEdgeFlags(event.getEdgeFlags());
        fakeMotionEvent.setMetaState(event.getMetaState());
        fakeMotionEvent.setPointerId(event.getPointerId(event.getActionIndex()));
        fakeMotionEvent.setEventTime(event.getEventTime());
        fakeMotionEvent.setDownTime(event.getDownTime());
        fakeMotionEvent.setPointerCount(event.getPointerCount());
        fakeMotionEvent.setOrientation(event.getOrientation());
        fakeMotionEvent.setPressure(event.getPressure());
        fakeMotionEvent.setSize(event.getSize());
        fakeMotionEvent.setSource(event.getSource());
        fakeMotionEvent.setToolMajor(event.getToolMajor());
        fakeMotionEvent.setToolMinor(event.getToolMinor());
        fakeMotionEvent.setTouchMajor(event.getTouchMajor());
        fakeMotionEvent.setTouchMinor(event.getTouchMinor());
        float x = event.getX() / width;
        float y = event.getY() / height;
        fakeMotionEvent.setX(x);
        fakeMotionEvent.setY(y);
        fakeMotionEvent.setxPrecision(event.getXPrecision());
        fakeMotionEvent.setyPrecision(event.getYPrecision());

        fakeMotionEvent.setAbX(event.getX());
        fakeMotionEvent.setAbY(event.getY());
        fakeMotionEvent.setParentX(width);
        fakeMotionEvent.setParentY(height);
        return fakeMotionEvent;
    }
}
