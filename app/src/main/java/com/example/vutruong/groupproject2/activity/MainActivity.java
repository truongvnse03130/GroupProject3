package com.example.vutruong.groupproject2.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.vutruong.groupproject2.R;
import com.example.vutruong.groupproject2.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "AddFromMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG);
        if (fragment == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new MainFragment(), TAG).commit();
        }

    }
}
