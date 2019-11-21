package com.yin.hy.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yin.hy.MainActivity;
import com.yin.hy.utils.ActivityManager;
import com.yin.hy.utils.ActivityStarter;
//开机自启动
public class BootCompletedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startActivity(new Intent(""));
    }
}
