package com.yin.hy.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
//登录监听
public class LoginSuccessReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
    }
}
