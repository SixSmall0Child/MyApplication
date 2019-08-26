package com.example.taoying.testapplication.activity;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.InputDevice;
import android.view.InputEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.taoying.testapplication.MyAdapter;
import com.example.taoying.testapplication.bean.CustomMotionEvent;
import com.example.taoying.testapplication.bean.MockMotionEvent;
import com.example.taoying.testapplication.R;
import com.example.taoying.testapplication.view.SubButton;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class TestSlideSecondActivity extends Activity {
    private static final String TAG = "TestSlideSecondActivity";
    private float mX;
    private float mY;
    private ArrayList<CustomMotionEvent> mMontionEventsCopy = new ArrayList<>();
    private RecyclerView mRlv;

    long newDownTime;
    long spaceValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_second);

        mRlv = (RecyclerView) findViewById(R.id.rlv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRlv.setLayoutManager(linearLayoutManager);
        mRlv.setAdapter(new MyAdapter(this));
//        mRlv.scrollToPosition(4);

        //获取滑动事件数据
        Bundle extras = getIntent().getExtras();
        mMontionEventsCopy = extras.getParcelableArrayList("_motion_event_list");
        Log.d(TAG, "onCreate: mMontionEventsCopy = " + mMontionEventsCopy.toString());

        SubButton btn = (SubButton) findViewById(R.id.btn);
        mX = btn.getX();
        mY = btn.getY();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateMotionEventAndSend(mMontionEventsCopy);
            }
        });


        //Decorview的onTouchListener无法触发回调
        /*getWindow().getDecorView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();
                float y = event.getY();
                float pressure = event.getPressure();
                Log.d(TAG, "aa: "+event.toString());
                return true;
            }
        });*/


        //
        btn.post(new Runnable() {
            @Override
            public void run() {
                //生成滑动模拟事件
//                generateMotionEventAndSend(mMontionEventsCopy);

                //生成模拟点击事件
                /*MotionEvent motionEventdown = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                        MotionEvent.ACTION_DOWN, 100, 500, 0);
                motionEventdown.setSource(InputDevice.SOURCE_TOUCHSCREEN);
                getWindow().getDecorView().dispatchTouchEvent(motionEventdown);
                MotionEvent motionEventup = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                        MotionEvent.ACTION_UP, 100, 500, 0);
                motionEventup.setSource(InputDevice.SOURCE_TOUCHSCREEN);
                getWindow().getDecorView().dispatchTouchEvent(motionEventup);

                motionEventdown.recycle();
                motionEventup.recycle();*/
            }
        });
    }

    private void generateMotionEventAndSend(ArrayList<CustomMotionEvent> list) {
        if (list == null || list.isEmpty()) {
            Log.e(TAG, "generateMotionEventAndSend: list is empty or null");
            return;
        }
//        if (list.get(0).action != MotionEvent.ACTION_DOWN) {
//            Log.e(TAG, "generateMotionEventAndSend: list error");
//            return;
//        } else if (list.get(list.size() - 1).action != MotionEvent.ACTION_UP) {
//            Log.e(TAG, "generateMotionEventAndSend: list error");
//            return;
//        }

        ArrayList<MotionEvent> motionEvents = new ArrayList<>();

        boolean isFirst = true;
        for (CustomMotionEvent event : list) {
            if (isFirst) {
                newDownTime = SystemClock.uptimeMillis();
                spaceValue = newDownTime - event.getDownTime();
                isFirst = false;
            }
//            MotionEvent mockMotionEvent = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), event.action,
//                    event.x, event.y, event.pressure, 1.0f, 0, 1.0f, 1.0f, 0, 0);
//            mockMotionEvent.setSource(InputDevice.SOURCE_TOUCHSCREEN);
//            getWindow().getDecorView().dispatchTouchEvent(mockMotionEvent);
            sendMotionEvent(event, this);
//            motionEvents.add(mockMotionEvent);
        }
//        Log.d(TAG, "generateMotionEventAndSend: " + motionEvents.toString());
//        for (MotionEvent e : motionEvents) {
//            getWindow().getDecorView().dispatchTouchEvent(e);
//        }

    }

    long l1 = 0;
    public void sendMotionEvent(CustomMotionEvent mEvent, Activity mActivity) {

        Log.d(TAG, "sendMotionEvent() called with FakeMotionEvent = " + mEvent.getEventType() +
                " x = " + mEvent.getX() + " y = " + mEvent.getY());
        try {
            /*Class c = Class.forName("android.hardware.input.InputManager");
            Method m = c.getMethod("getInstance");
            InputManager mInputMgr = (InputManager) m.invoke(null);
            Class paramTypes[] = getParamTypes(c, "injectInputEvent");
            Method mInjectInputEvent = c.getMethod("injectInputEvent", paramTypes);*/

            MotionEvent.PointerProperties[] props = new MotionEvent.PointerProperties[1];
            MotionEvent.PointerCoords[] coordses = new MotionEvent.PointerCoords[1];

            MotionEvent.PointerProperties prop = new MotionEvent.PointerProperties();
            prop.id = mEvent.getPointerId();
            prop.toolType = MotionEvent.TOOL_TYPE_FINGER;
            props[0] = prop;
            MotionEvent.PointerCoords coords = new MotionEvent.PointerCoords();
            coords.x = mEvent.getX();
            coords.y = mEvent.getY();
            coords.orientation = mEvent.getOrientation();
            coords.size = mEvent.getSize();
            coords.pressure = mEvent.getPressure();
            coords.touchMinor = mEvent.getTouchMinor();
            coords.touchMajor = mEvent.getTouchMajor();
            coords.toolMinor = mEvent.getToolMinor();
            coords.toolMajor = mEvent.getToolMajor();
            coordses[0] = coords;

            long eventTime = mEvent.getEventTime() + spaceValue;

            MotionEvent obtEvent;
            if (mEvent.getEventType() == MotionEvent.ACTION_DOWN){
                long temp = SystemClock.uptimeMillis();
                spaceValue = spaceValue + temp - newDownTime;
                newDownTime = temp;
                obtEvent = MotionEvent.obtain(newDownTime, eventTime,
                        mEvent.getEventType(), mEvent.getPointerCount(), props,
                        coordses, mEvent.getMetaState(), mEvent.getButtonState(), mEvent.getxPrecision(),
                        mEvent.getyPrecision(), mEvent.getDeviceId(), mEvent.getEdgeFlags(), mEvent.getSource(), mEvent.getFlags());
            }else {
                obtEvent = MotionEvent.obtain(newDownTime, eventTime,
                        mEvent.getEventType(), mEvent.getPointerCount(), props,
                        coordses, mEvent.getMetaState(), mEvent.getButtonState(), mEvent.getxPrecision(),
                        mEvent.getyPrecision(), mEvent.getDeviceId(), mEvent.getEdgeFlags(), mEvent.getSource(), mEvent.getFlags());
            }

//            MotionEvent obtEvent = MotionEvent.obtain(newDownTime, eventTime,
//                    mEvent.getEventType(), mEvent.getPointerCount(), props,
//                    coordses, mEvent.getMetaState(), mEvent.getButtonState(), mEvent.getxPrecision(),
//                    mEvent.getyPrecision(), mEvent.getDeviceId(), mEvent.getEdgeFlags(), mEvent.getSource(), mEvent.getFlags());

            mActivity.getWindow().getDecorView().dispatchTouchEvent(obtEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void mockTouchEvent1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Instrumentation mInst = new Instrumentation();
                mInst.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(),
                        SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 100, 100, 0));    //x,y 即是事件的坐标
                mInst.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(),
                        SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 100, 100, 0));
            }
        }).start();

    }


    private void mockTouchEvent2() {

        InputManager im = (InputManager) getSystemService(Context.INPUT_SERVICE);

        try {
            Class<?> clazz = MotionEvent.class;
//            MotionEvent motionEvent= (MotionEvent)clazz.newInstance();
            Log.d(TAG, "generateKeyEvent: x = " + mX + " Y = " + mY);
            MotionEvent motionEvent = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, mX + 5, mY + 5, 0);
            motionEvent.setSource(InputDevice.SOURCE_TOUCHSCREEN);


            Method injectInputEvent = InputManager.class.getMethod("injectInputEvent", InputEvent.class, int.class);
//            Field inject_input_event_mode_wait_for_finish = clazz.getField("INJECT_INPUT_EVENT_MODE_WAIT_FOR_FINISH");
            Boolean res = (Boolean) injectInputEvent.invoke(im, motionEvent, 2);
            Log.d(TAG, "injectInputEvent res: " + res);
//            im.injectInputEvent(motionEvent, InputManager.INJECT_INPUT_EVENT_MODE_WAIT_FOR_FINISH);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "dispatchTouchEvent: " + ev.toString());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: " + event.toString());
        return super.onTouchEvent(event);
    }


}
