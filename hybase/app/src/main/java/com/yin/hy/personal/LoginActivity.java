package com.yin.hy.personal;


import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yin.hy.BaseActivity;
import com.yin.hy.HomeActivity;
import com.yin.hy.R;
import com.yin.hy.broadcast.LoginSuccessReceiver;
import com.yin.hy.utils.ActivityStarter;
import com.yin.hy.utils.IoStreamUtl;
import com.yin.hy.utils.LogManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;


public class LoginActivity extends BaseActivity {

    private EditText txtAcct ;
    private EditText txtPwd ;
    private ProgressBar proAfterYes;
    private Bundle bundle ;
    private LoginSuccessReceiver loginReceiver;
    private LogManager logManager;
    public static String msg = "";

    private static final String TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        txtAcct = findViewById(R.id.edt_login_account);
        txtPwd = findViewById(R.id.edt_login_pwd);
        proAfterYes = findViewById(R.id.pro_login_afteryes);
        proAfterYes.setVisibility(View.INVISIBLE);
        btnInit(R.id.btn_login_no,R.id.btn_login_regist,R.id.btn_login_yes);
        edtLoginAccountInit();
        edtLoginPwdInit();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_login_yes://登录
                //显示进度条,圆形
                proAfterYes.setVisibility(View.VISIBLE);
                //前台输入的账号,密码
                String username = txtAcct.getText().toString();
                String pwd = txtPwd.getText().toString();
                //check账号密码
                int result = checkAcctAndPwd(username,pwd);
                if(result == 2){//账号密码正确
                    //回传username
                    bundle = new Bundle();
                    bundle.putString("account",username);
                    setResult(RESULT_OK,new Intent().putExtras(bundle));
                    //登录成功-广播
                    sendBroadcast(new Intent("com.yin.hy.personal.LOGIN_SUCCESS"));
                    //登录日志
                    new LogManager().saveLog(LoginActivity.this,
                            "user" + username + "login successfully at " +
                                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                            .format(new Date(System.currentTimeMillis())) + "\r\n",
                            "logforlogin",Context.MODE_APPEND
                    );
                    //关闭进度条
                    proAfterYes.setVisibility(View.GONE);
                    /*checkAcctAndPwd无本地记录
                    * 本地记录SharedReference: username-password
                    * for 1.自动登录;2本地验证
                    */
                    if(msg.equals("NOLOCALRECORD")){
                        savePassword(username,pwd);
                    }
                    //销毁登录活动
                    finish();
                }else{//登录失败,前台提示
                    proAfterYes.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_login_no://返回
                ActivityStarter.startAction(LoginActivity.this, HomeActivity.class);
                break;
            case R.id.btn_login_regist://注册
                ActivityStarter.startAction(LoginActivity.this,RegistActivity.class);
                break;
            default:
                super.onClick(v);
        }
    }

    private int checkAcctAndPwd(String acct, String pwd){
        int result = 3;
        String password = null;
        try{
            SharedPreferences sp = getSharedPreferences(acct + "-logincheck", Context.MODE_PRIVATE);
            if(sp==null){//本地无记录
                //数据库check账号,
                password = selectPwd(acct);
                if(password==null){//账号不存在
                    msg="账号"+acct+"不存在";
                    return 0;
                }
            }else{//有本地记录
                //获取本地记录的密码
                password = sp.getString(acct,"");
            }
            //验证密码
            if(!pwd.equals(password)){
                msg = "密码错误";
                return 1;
            }else if(sp==null){//密码正确,无本地记录
                msg = "NOLOCALRECORD";
            }
            result = 2;
        }catch (Exception ex){
            Log.e(TAG,"checkAcctAndPwd: "+ex.getMessage());
        }
        return result;
    }
    //连接数据库获取密码
    private String selectPwd(String acct){
        //select pwd from ...
        String password = null;
        return password;
    }
    //帐号编辑框焦点变更监听
    private void edtLoginAccountInit(){
        txtAcct.hasFocus();
        txtAcct.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && txtAcct.getText().toString().trim().equals("")){//获取焦点后失去焦点
                    Toast.makeText(LoginActivity.this,"账号不能为空",Toast.LENGTH_SHORT).show();
                    txtAcct.hasFocus();
                }
            }
        });
    }
    private void edtLoginPwdInit(){
        txtPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && txtPwd.getText().toString().trim().equals("")){//获取焦点后失去焦点
                    Toast.makeText(LoginActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
