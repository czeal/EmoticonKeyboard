package com.zeal.android.emoticonkeyboard;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private MainActivityFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed() {
        checkFragmentReference();
        if (mFragment.isEmoticonSheetLayoutShow()) {
            mFragment.setInputState(MainActivityFragment.INPUT_STATE_HIDE_ALL);
        } else {
            super.onBackPressed();
        }
    }

    public void checkFragmentReference() {
        if (mFragment == null) {
            FragmentManager fm = getSupportFragmentManager();
            mFragment = (MainActivityFragment) fm.findFragmentById(R.id.fragment);
        }
    }
}
