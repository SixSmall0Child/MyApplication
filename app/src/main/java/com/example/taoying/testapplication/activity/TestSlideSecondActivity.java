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
    private ArrayList<MockMotionEvent> mMontionEventsCopy = new ArrayList<>();
    private RecyclerView mRlv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_second);

        mRlv = (RecyclerView) findViewById(R.id.rlv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRlv.setLayoutManager(linearLayoutManager);
        mRlv.setAdapter(new MyAdapter(this));
        mRlv.scrollToPosition(4);

        //获取滑动事件数据
        Bundle extras = getIntent().getExtras();
        mMontionEventsCopy = extras.getParcelableArrayList("_motion_event_list");
        Log.d(TAG, "onCreate: mMontionEventsCopy = "+mMontionEventsCopy.toString());

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

    private void generateMotionEventAndSend(ArrayList<MockMotionEvent> list) {
        if(list == null || list.isEmpty()){
            Log.e(TAG, "generateMotionEventAndSend: list is empty or null");
            return;
        }
        if (list.get(0).action != MotionEvent.ACTION_DOWN){
            Log.e(TAG, "generateMotionEventAndSend: list error");
            return;
        }else if (list.get(list.size() - 1).action != MotionEvent.ACTION_UP){
            Log.e(TAG, "generateMotionEventAndSend: list error");
            return;
        }
        for (MockMotionEvent event : list) {
            MotionEvent mockMotionEvent = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), event.action,
                    event.x, event.y, event.pressure, 1.0f,0,1.0f, 1.0f, 0, 0);
            mockMotionEvent.setSource(InputDevice.SOURCE_TOUCHSCREEN);
            getWindow().getDecorView().dispatchTouchEvent(mockMotionEvent);
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
            Log.d(TAG, "generateKeyEvent: x = "+mX+" Y = "+mY);
            MotionEvent motionEvent = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, mX+5, mY+5, 0);
            motionEvent.setSource(InputDevice.SOURCE_TOUCHSCREEN);


            Method injectInputEvent = InputManager.class.getMethod("injectInputEvent", InputEvent.class, int.class);
//            Field inject_input_event_mode_wait_for_finish = clazz.getField("INJECT_INPUT_EVENT_MODE_WAIT_FOR_FINISH");
            Boolean res = (Boolean)injectInputEvent.invoke(im, motionEvent, 2);
            Log.d(TAG, "injectInputEvent res: "+res);
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
        Log.d(TAG, "dispatchTouchEvent: "+ev.toString());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: "+event.toString());
        return super.onTouchEvent(event);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.VH>{

        private final Context mContext;
        private ArrayList<String> mDatas  = new ArrayList<>();//Recyclerview数据

        public MyAdapter(Context context) {
            this.mContext = context;
            generateData();
        }

        private void generateData() {
            for (int i = 0; i < 50; i++) {
                mDatas.add(i+"");
            }
        }

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_test_eventmotion, parent, false);
            return new VH(inflate);
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            holder.mTv.setText(mDatas.get(position));
        }


        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class VH extends RecyclerView.ViewHolder{
            TextView mTv;

            public VH(View itemView) {
                super(itemView);
                mTv = (TextView) itemView.findViewById(R.id.tv);
            }
        }

    }
}
