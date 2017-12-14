package com.nirvana.code.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nirvana.code.R;

/**
 * Created by kriszhang on 16/5/28.
 */
public class TestDefinedView extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_defined_view_activity);
    }
}
