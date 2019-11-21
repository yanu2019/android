package com.yin.hy.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

//活动管理
public class ActivityManager {
    public static List<Activity> activityList = new ArrayList<>();

    public static void addActivity(Activity activity){
        if (!activityList.contains(activity)){
            activityList.add(activity);
        }
    }

    public static void removeActivity(Activity activity){
        if(activityList.contains(activity)){
            activityList.remove(activity);
        }
    }

    public static void finishAll(){
        for(Activity activity:activityList){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
        activityList.clear();
    }
}
