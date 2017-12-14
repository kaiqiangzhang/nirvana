package com.nirvana.code.utils;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by kris.zhang on 2017-08-08 01:44:16.
 */

public class SelectToolUtil {

    public static void setAlphaSelector(View view) {
        view.setAlpha(1f);
        view.setOnTouchListener(new View.OnTouchListener() {
            float lastPosX = -1;
            float lastPosY = -1;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                float posX = motionEvent.getX();
                float posY = motionEvent.getY();
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    lastPosX = posX;
                    lastPosY = posY;
                    if (view.isClickable()){
                        view.setAlpha(0.5f);
                    }
                } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    if (lastPosY == posY && lastPosX == posX) {
                        view.setAlpha(0.5f);
                    } else {
                        view.setAlpha(1f);
                    }
                } else {
                    view.setAlpha(1f);
                }
                return false;
            }
        });
    }
}
