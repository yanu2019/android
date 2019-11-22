package com.yin.hy.personal;


import android.content.Intent;
import android.content.IntentFilter;
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


public class LoginActivity extends BaseActivity {

    private EditText txtAcct ;
    private EditText txtPwd ;
    private ProgressBar proAfterYes;
    private Bundle bundle ;
    private LoginSuccessReceiver loginReceiver;
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
                //进度条
                proAfterYes.setVisibility(View.VISIBLE);
                int result = checkAcctAndPwd(txtAcct.getText().toString(),txtPwd.getText().toString());
                if(result == 2){
                    bundle = new Bundle();
                    bundle.putString("account",txtAcct.getText().toString());
                    setResult(RESULT_OK,new Intent().putExtras(bundle));
//                    try {
//                        Thread.sleep(3000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    sendBroadcast(new Intent("com.yin.hy.personal.LOGIN_SUCCESS"));
                    proAfterYes.setVisibility(View.GONE);
                    finish();
                }else{
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
        try{
            if (!acct.equals("aaa")){//select account from...
                msg = "账号不存在";
                return 0;
            }else if(!pwd.equals("111")){
                msg = "密码错误";
                return 1;
            }else{
                return 2;
            }
        }catch (Exception ex){
            Log.e(TAG,ex.getMessage());
            return 3;
        }
    }
    //帐号编辑框焦点变更监听
    private void edtLoginAccountInit(){
        txtAcct.hasFocus();
        txtAcct.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && txtAcct.getText().toString().trim().equals("")){//获取焦点后失去焦点
                    txtAcct.hasFocus();
                    Toast.makeText(LoginActivity.this,"账号不能为空",Toast.LENGTH_SHORT).show();
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
