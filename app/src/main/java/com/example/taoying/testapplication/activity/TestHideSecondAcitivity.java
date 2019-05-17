package com.example.taoying.testapplication.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.taoying.testapplication.R;
import com.example.taoying.testapplication.view.SubImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestHideSecondAcitivity extends Activity {
    private static final String TAG = "TestHideSecondAcitivity";
    private Thread mThread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_hide_second);
        moveTaskToBack(true);
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "run: TestHideSecondAcitivity is running!");
                }
            }
        });
        mThread.start();

        final SubImageView iv = (SubImageView) findViewById(R.id.iv);
        iv.setImageResource(R.drawable.ic_launcher);


        iv.post(new Runnable() {
            @Override
            public void run() {
                iv.setDrawingCacheEnabled(true);
                iv.buildDrawingCache();
                Bitmap drawingCache = iv.getDrawingCache(true);
                Log.d(TAG, "onClick: " + (drawingCache != null));

                saveBitmap(drawingCache, System.currentTimeMillis() + "_pic");
            }
        });
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        moveTaskToBack(true);
        super.onResume();

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    public void saveBitmap(Bitmap bm, String picName) {
        Log.e(TAG, "保存图片");
        File f = new File(getExternalCacheDir(), picName);
        if (!f.exists()) {
            f.getParentFile().mkdirs();
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            f.delete();
        }

        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            Log.i(TAG, "已经保存");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
