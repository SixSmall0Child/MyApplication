package com.example.taoying.testapplication.bean;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.taoying.testapplication.utils.CalculateUtils;
import com.example.taoying.testapplication.utils.DisplayUtils;

public class MockMotionEvent implements Parcelable {
    public float x;
    public float y;
    public float pressure;
    public int action;
    public float xPercent;//x相对值，取小数点后五位
    public float yPercent;//y相对值，取小数点后五位


    public MockMotionEvent(Context context, float x, float y, float pressure, int action) {
        this.x = x;
        this.y = y;
        this.pressure = pressure;
        this.action = action;
        this.xPercent = CalculateUtils.div(x, (float) DisplayUtils.getScreenWidth(context), 5);
        this.yPercent = CalculateUtils.div(y, (float) DisplayUtils.getScreenHeight(context), 5);
    }

    @Override
    public String toString() {
        return "MockMotionEvent{" +
                "x=" + x +
                ", y=" + y +
                ", pressure=" + pressure +
                ", action=" + action +
                ", xPercent=" + xPercent +
                ", yPercent=" + yPercent +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.x);
        dest.writeFloat(this.y);
        dest.writeFloat(this.pressure);
        dest.writeInt(this.action);
        dest.writeFloat(this.xPercent);
        dest.writeFloat(this.yPercent);
    }

    protected MockMotionEvent(Parcel in) {
        this.x = in.readFloat();
        this.y = in.readFloat();
        this.pressure = in.readFloat();
        this.action = in.readInt();
        this.xPercent = in.readFloat();
        this.yPercent = in.readFloat();
    }

    public static final Parcelable.Creator<MockMotionEvent> CREATOR = new Parcelable.Creator<MockMotionEvent>() {
        @Override
        public MockMotionEvent createFromParcel(Parcel source) {
            return new MockMotionEvent(source);
        }

        @Override
        public MockMotionEvent[] newArray(int size) {
            return new MockMotionEvent[size];
        }
    };
}
