package com.yin.hy;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yin.hy.personal.LoginActivity;
import com.yin.hy.utils.ActivityManager;
import com.yin.hy.utils.ActivityStarter;

public class HomeActivity extends BaseActivity implements View.OnClickListener{

    private AlertDialog.Builder tempDialog;
    public static final int EXIT_DIALOG_ICON = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //怎么获取当前Activity所有按钮.
        btnInit(R.id.btn_home_search,R.id.btn_home_login);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_home_search:
                ActivityStarter.startAction(HomeActivity.this,Intent.ACTION_VIEW,"http://www.baidu.com");
                break;
            case R.id.btn_home_login:
                startActivityForResult(new Intent(HomeActivity.this, LoginActivity.class),1);
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch(requestCode){
            case 1:
                if(resultCode==RESULT_OK){
                    String returnData = data.getExtras().getString("account");
                    TextView textView = findViewById(R.id.txt_home_acct);
                    textView.setText(returnData);
                    textView.setVisibility(View.VISIBLE);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    //主菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_main_help:
                break;
            case R.id.menu_main_exit:
                tempDialog = new AlertDialog.Builder(HomeActivity.this);
                createDialog("退出系统","确定退出吗?",true,EXIT_DIALOG_ICON,tempDialog);
                break;
            default:
        }
        return true;
    }
    //菜单选择监听

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which){
            case DialogInterface.BUTTON_POSITIVE:
                ActivityManager.finishAll();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                dialog.dismiss();
            case DialogInterface.BUTTON_NEUTRAL:
                dialog.dismiss();
        }
    }
}
