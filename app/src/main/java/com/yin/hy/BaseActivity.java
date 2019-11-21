package com.yin.hy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yin.hy.utils.ActivityManager;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener,DialogInterface.OnClickListener{
    protected Button button;
    protected Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(getClass().getSimpleName(),"onCreate");
        ActivityManager.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.removeActivity(this);
    }
    //button
    @Override
    public void onClick(View v) {
        //星星
    }
    //dialog
    @Override
    public void onClick(DialogInterface dialog, int which) {

    }

    //按钮初始化
    protected void btnInit(int... btn){
        for(int i:btn){
            button = (Button)findViewById(i);
            button.setOnClickListener(this);
        }
    }
    //对话框
    protected void createDialog(String title, String message, Boolean cancelable,int icon,AlertDialog.Builder dialog){
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setIcon(icon);
        dialog.setCancelable(cancelable);
        dialog.setPositiveButton("OK",this);
        dialog.setNegativeButton("CANCEL",this);
        dialog.show();
    }

}
