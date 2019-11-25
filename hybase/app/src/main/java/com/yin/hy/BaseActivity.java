package com.yin.hy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yin.hy.Entity.LoginInfo;
import com.yin.hy.utils.ActivityManager;

import org.litepal.LitePal;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener,DialogInterface.OnClickListener, CompoundButton.OnCheckedChangeListener{
    protected Button button;
    protected Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LitePal.getDatabase();
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
            button = findViewById(i);
            button.setOnClickListener(this);
        }
    }
    //复选框初始化
    protected void cbxInit(int... cbx){
        for(int i:cbx){
            CheckBox checkBox = findViewById(i);
            checkBox.setOnCheckedChangeListener(this);
        }
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    /**
     * 对话框
     * @param title
     * @param message
     * @param cancelable
     * @param dialog
     */
    protected void createDialog(String title, String message, Boolean cancelable,AlertDialog.Builder dialog){
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setCancelable(cancelable);
        dialog.setPositiveButton("OK",this);
        dialog.setNegativeButton("CANCEL",this);
        dialog.show();
    }


    /**
     * SharedReference文件存储
     * @param username
     * @param password
     * @param autoLogin 自动登录
     * @throws Exception
     */
    protected void savePassword(String username,String password,Boolean autoLogin)throws Exception{
        try{
            SharedPreferences.Editor editor = getSharedPreferences(username+"-logincheck", Context.MODE_PRIVATE).edit();
            editor.putString(username,password);
            editor.putBoolean("autoLogin",autoLogin);
            editor.apply();
        }catch (Exception ex){
            throw ex;
        }
    }

    /**
     * LitePal本地数据库存储
     * @param username
     * @param password
     * @throws Exception
     */
    protected void savePassword(String username,String password,Boolean autoLogin,Boolean isNextAutoLoginAcct)throws Exception{
        try{
            LoginInfo info = new LoginInfo(username,password,autoLogin,isNextAutoLoginAcct);
            info.save();
        }catch (Exception ex){
            throw ex;
        }
    }

}
