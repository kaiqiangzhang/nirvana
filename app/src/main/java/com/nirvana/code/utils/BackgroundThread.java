package com.nirvana.code.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;

/**
 * It executes tasks at background.
 * All tasks are put in a queue and executed one by one.
 */

public class BackgroundThread extends Thread {
    private static BackgroundThread instance;
    private static final String TAG = "BackgroundThread";
    private static final int STOP = 1001;
    public static BackgroundThread getInstance() {
        if(instance==null){
            instance=new BackgroundThread();
        }
        return instance;
    }

    private BackgroundThread() {
        init();
    }

    private static Handler mHandler;

    private ArrayList<Runnable> mPendingRunnables = new ArrayList<Runnable>();

    @Override
    public void run() {
        Log.d(TAG, "begin thread run");
        Looper.prepare();

        synchronized (this) {

            mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what){
                        case STOP:
                            Runnable runnable = (Runnable) msg.obj;
                            mHandler.removeCallbacks(runnable);
                            break;
                    }
                }

            };
        }

        Looper.loop();

        Log.d(TAG, "end thread run");
    }

    public void post(Runnable r) {
        synchronized (this) {

            if (mHandler == null) {
                mPendingRunnables.add(r);
            } else {
                if (mPendingRunnables.size() > 0) {
                    for (Runnable pendingR : mPendingRunnables) {
                        mHandler.post(pendingR);
                    }
                    mPendingRunnables.clear();
                }

                mHandler.post(r);
            }
        }
    }

    public void remove(Runnable r){
        Message msg = Message.obtain();
        msg.what = STOP;
        msg.obj = r;
        mHandler.sendMessage(msg);
    }

    public void postDelayed(Runnable r, long delayMillis) {
        if (mHandler != null) {
            mHandler.postDelayed(r, delayMillis);
        }
    }

    private void init() {
        if (getState() == State.NEW) {
            start();
        }
    }
}

