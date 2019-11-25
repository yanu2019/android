package com.yin.hy.personal;


import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.yin.hy.BaseActivity;
import com.yin.hy.Entity.LoginInfo;
import com.yin.hy.HomeActivity;
import com.yin.hy.R;
import com.yin.hy.utils.ActivityStarter;
import com.yin.hy.utils.LogManager;


import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.text.SimpleDateFormat;
import java.util.Date;


public class LoginActivity extends BaseActivity {

    private EditText txtAcct ;
    private EditText txtPwd ;
    private ProgressBar proAfterYes;
    private Bundle bundle ;
    private CheckBox cbxAutoLogin;
    //自动登录复选框 状态变化
    public static Boolean cbxCheckedChange = false;
    public static Boolean autoLoginFlag = false;
    public static Boolean isNextAutoLoginAcct = false;
    public static String msg = "";

    private static final String TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        txtAcct = findViewById(R.id.edt_login_account);
        txtPwd = findViewById(R.id.edt_login_pwd);
        cbxAutoLogin = findViewById(R.id.cbx_login_remember);
        proAfterYes = findViewById(R.id.pro_login_afteryes);
        proAfterYes.setVisibility(View.INVISIBLE);
        btnInit(R.id.btn_login_no,R.id.btn_login_regist,R.id.btn_login_yes);
        cbxInit(R.id.cbx_login_remember);
        edtLoginAccountInit();
        edtLoginPwdInit();
        cbxCheckedChange = false;
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.btn_login_yes://登录
                    //显示进度条,圆形
                    proAfterYes.setVisibility(View.VISIBLE);
                    //前台输入的账号,密码
                    String username = txtAcct.getText().toString();
                    String pwd = txtPwd.getText().toString();
                    //check账号密码
                    int result = checkAcctAndPwd(username, pwd);
                    if (result == 2) {//账号密码正确
                        //回传username
                        bundle = new Bundle();
                        bundle.putString("account", username);
                        setResult(RESULT_OK, new Intent().putExtras(bundle));
                        //登录成功-广播
                        sendBroadcast(new Intent("com.yin.hy.personal.LOGIN_SUCCESS"));
                        //登录日志
                        new LogManager().saveLog(LoginActivity.this,
                                "user" + username + "login successfully at " +
                                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                                .format(new Date(System.currentTimeMillis())) + "\r\n",
                                "logforlogin", Context.MODE_APPEND
                        );
                        /*checkAcctAndPwd无本地记录
                         * 本地记录SharedReference: username-password
                         * litepal本地数据库
                         * for 1.自动登录;2本地验证
                         */
                        if (msg.equals("NOLOCALRECORD")) {
                            autoLoginDeal();
                            savePassword(username, pwd, autoLoginFlag,isNextAutoLoginAcct);
                        }else{//账号有本地记录
                            //自动登录复选框,无变更动作不处理
                            if(cbxCheckedChange){//有变更动作
                                //改变账号自动登录标志
                                checkedChange();
                            }                        }
                        //关闭进度条
                        proAfterYes.setVisibility(View.GONE);
                        //销毁登录活动
                        finish();
                    } else {//登录失败,前台提示
                        proAfterYes.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.btn_login_no://返回
                    ActivityStarter.startAction(LoginActivity.this, HomeActivity.class);
                    break;
                case R.id.btn_login_regist://注册
                    ActivityStarter.startAction(LoginActivity.this, RegistActivity.class);
                    break;
                default:
                    super.onClick(v);
            }
        }catch(Exception ex){
            Log.e(TAG, "onClick: " + ex.getMessage() );
        }
    }
    //SharedPreFerences文件储存
//    private int checkAcctAndPwd(String acct, String pwd){
//        int result = 3;
//        String password = null;
//        try{

//            SharedPreferences sp = getSharedPreferences(acct + "-logincheck", Context.MODE_PRIVATE);
//            if(sp==null){//本地无记录
//                //数据库check账号,
//                password = selectPwd(acct);
//                if(password==null){//账号不存在
//                    msg="账号"+acct+"不存在";
//                    return 0;
//                }
//            }else{//有本地记录
//                //获取本地记录的密码
//                password = sp.getString(acct,"");
//            }
//            //验证密码
//            if(!pwd.equals(password)){
//                msg = "密码错误";
//                return 1;
//            }else if(sp==null){//密码正确,无本地记录
//                msg = "NOLOCALRECORD";
//            }
//            result = 2;
//        }catch (Exception ex){
//            Log.e(TAG,"checkAcctAndPwd: "+ex.getMessage());
//        }
//        return result;
//    }

    //litepal本地数据库存储
    private int checkAcctAndPwd(String username,String pwd){
        String password = null;
        //check异常
        int result = 3;
        try {
            Cursor c = LitePal.findBySQL("select password from LoginInfo where username = ?", username);
            if (c.moveToFirst()) {
                password = c.getString(c.getColumnIndex("password"));
            } else {//本地无记录
                password = selectPwd(username);
                if (password == null) {
                    msg = "账号" + username + "不存在";
                    return 0;
                }
            }
            //验证密码
            if (!pwd.equals(password)) {
                msg = "密码错误";
                return 1;
            } else if (!c.moveToFirst()) {//密码正确,无本地记录
                msg = "NOLOCALRECORD";
            }
            result = 2;
        }catch (Exception e){
            Log.e(TAG,"checkAcctAndPwd: "+e.getMessage());
        }
        return result;
    }
    //连接远程数据库获取密码
    private String selectPwd(String acct){
        //select pwd from ...
        String password = null;
        return password;
    }

    //无本地记录账号自动登录处理
    private void autoLoginDeal(){
        LoginInfo info = new LoginInfo();
        if(cbxAutoLogin.isChecked()){//选择自动登录
            autoLoginFlag = true;
            //下次启动app自动登录此账号
            isNextAutoLoginAcct = true;
            info.setIsNextAutoLoginAcct(false);
            info.updateAll();
        }
    }

    //帐号,密码编辑框焦点变更监听
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


    //自动登录cbx  checkedChange
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.cbx_login_remember:
                //自动登录复选框状态有变更
                cbxCheckedChange = true;
        }
    }

    //有本地记录账号自动登录状态更新
    private void checkedChange(){
        LoginInfo info = new LoginInfo();
        info.setAutoLogin(cbxAutoLogin.isChecked());
    }
}
