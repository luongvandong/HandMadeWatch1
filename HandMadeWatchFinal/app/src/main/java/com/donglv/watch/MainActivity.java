package com.donglv.watch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.donglv.watch.common.HMW_Constant;
import com.donglv.watch.fragment.FragmentLogIn;
import com.nam.customlibrary.Libs_System;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String signOut = getIntent().getStringExtra("sign out");
        if (signOut == null) {
            if (Libs_System.getStringData(MainActivity.this, HMW_Constant.TOKEN).equals("")) {
                getSupportFragmentManager().beginTransaction().replace(R.id.main, new FragmentLogIn()).commit();
            } else {
                Intent intent = new Intent(getApplicationContext(), ActivityHome.class);
                startActivity(intent);
                finish();
            }
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.main, new FragmentLogIn()).commit();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
