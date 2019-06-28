package com.jewel.libx;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author jewel
 * @email jewelbao88@gmail.com
 * @gitsite https://github.com/jewelbao
 * @since 2019/5/8
 */
public class SampleActivity extends Activity {
    /**
     * 静态内部类的实例不包含对其外部类的隐式引用。
     */
    private static class MyHandler extends Handler {
        private final WeakReference<SampleActivity> mActivity;

        public MyHandler(SampleActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            SampleActivity activity = mActivity.get();
            if (activity != null) {  /*...*/ }
        }
    }

    private final MyHandler mHandler = new MyHandler(this);
    /**
     * 当匿名类为static时，它们不会对其外部类进行隐式引用。
     */
    private static final Runnable sRunnable = new Runnable() {
        @Override
        public void run() { /* ... */ }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Post a message and delay its execution for 10 minutes.
        mHandler.postDelayed(sRunnable, 1000 * 60 * 10);
        // Go back to the previous Activity.
        finish();
    }
}
