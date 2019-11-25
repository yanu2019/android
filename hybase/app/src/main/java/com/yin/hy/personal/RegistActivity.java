package com.yin.hy.personal;

import android.content.Context;
import android.os.Bundle;

import com.yin.hy.BaseActivity;
import com.yin.hy.Entity.RegistInfo;
import com.yin.hy.R;
import com.yin.hy.utils.IoStreamUtl;


public class RegistActivity extends BaseActivity {
    private static final String TAG = "RegistActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
    }
    //存储注册信息,修改个人信息
    private void saveRegistInfo(RegistInfo info){
        IoStreamUtl.inObject(info.getUsername()+"-info",Context.MODE_PRIVATE,RegistActivity.this,info);
    }
    
}
