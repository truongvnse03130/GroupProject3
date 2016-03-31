package com.example.vutruong.groupproject2.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vutruong.groupproject2.R;
import com.example.vutruong.groupproject2.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag("AddToMain");
        if (fragment == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new MainFragment(),"AddToMain").commit();
        }

    }
}
