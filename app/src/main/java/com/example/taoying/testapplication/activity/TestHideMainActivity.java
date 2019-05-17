package com.example.taoying.testapplication.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.taoying.testapplication.R;
import com.example.taoying.testapplication.view.SubImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestHideMainActivity extends Activity {
    private static final String TAG = "TestHideMainActivity";
    private Thread mThread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_hide_main);
        TextView tv = (TextView) findViewById(R.id.tv);
        tv.setText("aa");
        Log.e(TAG, "onCreate: ");
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(1000);
                        Log.e(TAG, "I'm Running!");
                    }
                } catch (InterruptedException e) {
                    Log.e(TAG, "catch InterruptedException");
                    e.printStackTrace();
                }

            }
        });
        mThread.start();


        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestHideMainActivity.this, TestHideSecondAcitivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

}
