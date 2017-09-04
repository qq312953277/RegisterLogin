package com.example.yong04zhou.registerlogin.loginregister.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.support.multidex.MultiDex;

import com.example.yong04zhou.registerlogin.loginregister.data.DataRepository;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yong04.zhou on 2017/8/31.
 */

public class BaseApplication extends Application {

    private static Context context;
    private static BaseApplication baseApplication = null;

    /**
     * A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher.
     */
    public static final boolean ENCRYPTED = false;


    public static Context getContext() {
        return context;
    }

    public static BaseApplication getApplication() {
        if (baseApplication == null) {
            baseApplication = new BaseApplication();
        }
        return baseApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;


        initMultiDex();

        //init data repository
        DataRepository.getInstance(getApplicationContext());


    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    /**
     * init MultiDex
     */
    public void initMultiDex() {
        MultiDex.install(this);
    }


    //运用list来保存们每一个activity
    private List<Activity> mList = new LinkedList<Activity>();

    private HashMap<String, Activity> mHashMap = new HashMap<>();

    /**
     * add Activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        try {
            mList.add(activity);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * add Activity
     *
     * @param key
     * @param activity
     */
    public void addActivity(String key, Activity activity) {
        try {
            if (mHashMap.containsKey(key)) {
                mHashMap.remove(key);
            }
            mHashMap.put(key, activity);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * remove Activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        try {
            mList.remove(activity);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * remove Activity
     */
    public void removeActivity(String key) {
        if (mHashMap.containsKey(key)) {
            mHashMap.remove(key);
        }
    }

    /**
     * 销毁指定Activity
     */
    public void finishActivity(String key) {
        if (mHashMap.containsKey(key)) {
            if (!mHashMap.get(key).isFinishing()) {
                mHashMap.get(key).finish();
            }
        }
    }


    /**
     * exit App and finish all activities
     */
    public void finishAllActivity() {
        //关闭list内的每一个activity
        if (mList == null) {
            return;
        }
        try {
            for (Activity activity : mList) {
                if (activity != null && !activity.isFinishing())
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
