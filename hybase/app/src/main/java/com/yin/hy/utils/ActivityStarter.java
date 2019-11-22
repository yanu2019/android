package com.yin.hy.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


//启动活动,工具类-显示启动
public class ActivityStarter {
    /**
     *
     * @param activity 当前活动 对象
     * @param cls   启动活动 类对象
     * @param bundle 传递的数据
     */
    public static void startAction(Activity activity, Class<?> cls, Bundle bundle){
        Intent intent = new Intent(activity,cls);
        if(!bundle.isEmpty()){
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
    }

    public static void startAction(Activity activity, Class<?> cls){
        Intent intent = new Intent(activity,cls);
        activity.startActivity(intent);
    }

    /**
     *
     * @param action 响应action
     * @param category  响应category数组
     * @param bundle 传递的数据
     */
    public static void startAction(Activity activity,String action , @NonNull Bundle bundle, String ... category){
        Intent intent = new Intent(action);
        if(category.length != 0){
            for(String str : category){
                intent.addCategory(str);
            }
        }
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    public static void startAction(Activity activity,String action , String ... category){
        Intent intent = new Intent(action);
        if(category.length != 0){
            for(String str : category){
                intent.addCategory(str);
            }
        }
        activity.startActivity(intent);
    }

    /**
     *
     * @param action  Intent 内置action
     * @param uri
     */
    public static void startAction(Activity activity,String action ,String uri){
        Intent intent = new Intent(action);
        intent.setData(Uri.parse(uri));
        activity.startActivity(intent);
    }

}
