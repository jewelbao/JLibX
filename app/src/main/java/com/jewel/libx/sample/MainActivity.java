package com.jewel.libx.sample;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.jewel.libx.android.StatusBarUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dimStatusBar(View view) {
        StatusBarUtil.dimStatusBar(this);
    }

    public void resetStatusBar(View view) {
        StatusBarUtil.resetStatusBar(this);
    }

    public void hideStatusBar(View view) {
        StatusBarUtil.hideStatusBar(this);
    }

    public void immersiveStatusBar(View view) {
        StatusBarUtil.immersiveStatusBar(this, findViewById(R.id.layout_root));
    }

}
