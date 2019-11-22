package com.yin.hy;


import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.yin.hy.utils.ActivityStarter;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnMainBeginInit();
        Log.e(TAG,Environment.getExternalStorageDirectory().getPath());
    }


    private void btnMainBeginInit(){
        Button btn = (Button) findViewById(R.id.btn_main_begin);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityStarter.startAction(MainActivity.this,HomeActivity.class);
            }
        });
    }
}
