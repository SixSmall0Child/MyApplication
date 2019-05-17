package com.example.taoying.testapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.taoying.testapplication.R;
import com.example.taoying.testapplication.bean.MockMotionEvent;
import com.example.taoying.testapplication.utils.DisplayUtils;

import java.util.ArrayList;

public class TestSlideMainActivity extends Activity {

    private static final String TAG = "TestSlideMainActivity";
    private ArrayList<MockMotionEvent> motionEventArrayList = new ArrayList<>();
    private Button mBtn;
    private Button mBtn1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        moveTaskToBack(true);


        Log.d(TAG, "onCreate: screenWidth = " + DisplayUtils.getScreenWidth(this) + " screenHeight = " + DisplayUtils.getScreenHeight(this));
        mBtn = (Button) findViewById(R.id.btn);//跳转
        mBtn1 = (Button) findViewById(R.id.btn1);//清除
        mBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "motionEventArrayList to clear is " + motionEventArrayList.toString());
                motionEventArrayList.clear();
            }
        });
        TextView tv = (TextView) findViewById(R.id.tv);
        tv.setText("aa");
        Log.e(TAG, "onCreate: ");

        tv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "onTouch: " + event.toString());
                if (motionEventArrayList == null) {
                    motionEventArrayList = new ArrayList<MockMotionEvent>();
                }

                MockMotionEvent mockMotionEvent = new MockMotionEvent(TestSlideMainActivity.this, event.getRawX(), event.getRawY(), event.getPressure(), event.getAction());
                motionEventArrayList.add(mockMotionEvent);
                return true;
            }
        });


        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "motionEventArrayList to deliver is " + motionEventArrayList.toString());
                Intent intent = new Intent(TestSlideMainActivity.this, TestSlideSecondActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("_motion_event_list", motionEventArrayList);
                intent.putExtras(bundle);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                TestSlideMainActivity.this.startActivity(intent);
            }
        });


    }

}
