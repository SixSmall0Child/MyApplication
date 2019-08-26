package com.example.taoying.testapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.taoying.testapplication.MyAdapter;
import com.example.taoying.testapplication.R;
import com.example.taoying.testapplication.bean.CustomMotionEvent;
import com.example.taoying.testapplication.bean.MockMotionEvent;
import com.example.taoying.testapplication.utils.CustomMotionEventParser;
import com.example.taoying.testapplication.utils.DisplayUtils;

import java.util.ArrayList;

public class TestSlideMainActivity extends Activity {

    private static final String TAG = "TestSlideMainActivity";
    private ArrayList<CustomMotionEvent> motionEventArrayList = new ArrayList<>();
    private Button mBtn;
    private Button mBtn1;
    private RecyclerView mRlv;


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

        mRlv = (RecyclerView) findViewById(R.id.rlv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRlv.setLayoutManager(linearLayoutManager);
        mRlv.setAdapter(new MyAdapter(this));



        mRlv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "onTouch: " + event.toString());
                if (motionEventArrayList == null) {
                    motionEventArrayList = new ArrayList<CustomMotionEvent>();
                }

//                MockMotionEvent mockMotionEvent = new MockMotionEvent(TestSlideMainActivity.this, event.getRawX(), event.getRawY(), event.getPressure(), event.getAction());
//                motionEventArrayList.add(mockMotionEvent);
                CustomMotionEvent customMotionEvent = CustomMotionEventParser.parse(event, 1, 1);
                motionEventArrayList.add(customMotionEvent);
                return false;
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
