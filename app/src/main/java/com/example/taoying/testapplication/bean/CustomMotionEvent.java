package com.example.taoying.testapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class CustomMotionEvent implements Parcelable {
    private int actionType; //0 标识为Down 1表示为Move，2 表示为Up
    private long downTime;
    private long eventTime;
    private float orientation;
    private float pressure;
    private float size;
    private float toolMajor;
    private float toolMinor;
    private float touchMajor;
    private float touchMinor;
    private float x;
    private float y;
    private int pointerId;
    private int deviceId;
    private int source;
    private int buttonState;
    private int pointerCount;
    private int metaState;
    private float xPrecision;
    private float yPrecision;
    private int edgeFlags;
    private int flags;

    private float abX;//触摸事件的宽高
    private float abY;
    private float parentX;//容器View宽高
    private float parentY;

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public float getAbX() {
        return abX;
    }

    public void setAbX(float abX) {
        this.abX = abX;
    }

    public float getAbY() {
        return abY;
    }

    public void setAbY(float abY) {
        this.abY = abY;
    }

    public float getParentX() {
        return parentX;
    }

    public void setParentX(float parentX) {
        this.parentX = parentX;
    }

    public float getParentY() {
        return parentY;
    }

    public void setParentY(float parentY) {
        this.parentY = parentY;
    }

    public int getEventType() {
        return actionType;
    }

    public void setEventType(int eventType) {
        this.actionType = eventType;
    }

    public long getDownTime() {
        return downTime;
    }

    public void setDownTime(long downTime) {
        this.downTime = downTime;
    }

    public long getEventTime() {
        return eventTime;
    }

    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;
    }

    public float getOrientation() {
        return orientation;
    }

    public void setOrientation(float orientation) {
        this.orientation = orientation;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public float getToolMajor() {
        return toolMajor;
    }

    public void setToolMajor(float toolMajor) {
        this.toolMajor = toolMajor;
    }

    public float getToolMinor() {
        return toolMinor;
    }

    public void setToolMinor(float toolMinor) {
        this.toolMinor = toolMinor;
    }

    public float getTouchMajor() {
        return touchMajor;
    }

    public void setTouchMajor(float touchMajor) {
        this.touchMajor = touchMajor;
    }

    public float getTouchMinor() {
        return touchMinor;
    }

    public void setTouchMinor(float touchMinor) {
        this.touchMinor = touchMinor;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getPointerId() {
        return pointerId;
    }

    public void setPointerId(int pointerId) {
        this.pointerId = pointerId;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getButtonState() {
        return buttonState;
    }

    public void setButtonState(int buttonState) {
        this.buttonState = buttonState;
    }

    public int getPointerCount() {
        return pointerCount;
    }

    public void setPointerCount(int pointerCount) {
        this.pointerCount = pointerCount;
    }

    public int getMetaState() {
        return metaState;
    }

    public void setMetaState(int metaState) {
        this.metaState = metaState;
    }

    public float getxPrecision() {
        return xPrecision;
    }

    public void setxPrecision(float xPrecision) {
        this.xPrecision = xPrecision;
    }

    public float getyPrecision() {
        return yPrecision;
    }

    public void setyPrecision(float yPrecision) {
        this.yPrecision = yPrecision;
    }

    public int getEdgeFlags() {
        return edgeFlags;
    }

    public void setEdgeFlags(int edgeFlags) {
        this.edgeFlags = edgeFlags;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.actionType);
        dest.writeLong(this.downTime);
        dest.writeLong(this.eventTime);
        dest.writeFloat(this.orientation);
        dest.writeFloat(this.pressure);
        dest.writeFloat(this.size);
        dest.writeFloat(this.toolMajor);
        dest.writeFloat(this.toolMinor);
        dest.writeFloat(this.touchMajor);
        dest.writeFloat(this.touchMinor);
        dest.writeFloat(this.x);
        dest.writeFloat(this.y);
        dest.writeInt(this.pointerId);
        dest.writeInt(this.deviceId);
        dest.writeInt(this.source);
        dest.writeInt(this.buttonState);
        dest.writeInt(this.pointerCount);
        dest.writeInt(this.metaState);
        dest.writeFloat(this.xPrecision);
        dest.writeFloat(this.yPrecision);
        dest.writeInt(this.edgeFlags);
        dest.writeInt(this.flags);
        dest.writeFloat(this.abX);
        dest.writeFloat(this.abY);
        dest.writeFloat(this.parentX);
        dest.writeFloat(this.parentY);
    }

    public CustomMotionEvent() {
    }

    protected CustomMotionEvent(Parcel in) {
        this.actionType = in.readInt();
        this.downTime = in.readLong();
        this.eventTime = in.readLong();
        this.orientation = in.readFloat();
        this.pressure = in.readFloat();
        this.size = in.readFloat();
        this.toolMajor = in.readFloat();
        this.toolMinor = in.readFloat();
        this.touchMajor = in.readFloat();
        this.touchMinor = in.readFloat();
        this.x = in.readFloat();
        this.y = in.readFloat();
        this.pointerId = in.readInt();
        this.deviceId = in.readInt();
        this.source = in.readInt();
        this.buttonState = in.readInt();
        this.pointerCount = in.readInt();
        this.metaState = in.readInt();
        this.xPrecision = in.readFloat();
        this.yPrecision = in.readFloat();
        this.edgeFlags = in.readInt();
        this.flags = in.readInt();
        this.abX = in.readFloat();
        this.abY = in.readFloat();
        this.parentX = in.readFloat();
        this.parentY = in.readFloat();
    }

    public static final Parcelable.Creator<CustomMotionEvent> CREATOR = new Parcelable.Creator<CustomMotionEvent>() {
        @Override
        public CustomMotionEvent createFromParcel(Parcel source) {
            return new CustomMotionEvent(source);
        }

        @Override
        public CustomMotionEvent[] newArray(int size) {
            return new CustomMotionEvent[size];
        }
    };
}
