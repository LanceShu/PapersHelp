package com.example.race.papershelp;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.FragmentManager;

/**
 * Created by Lance on 2017/10/15.
 */

public class Content {

    //用户名
    public static String uName;
    //用户密码
    public static String uPass;

    public static Handler handler = new Handler();

    public static Context context;

    public static FragmentManager fragmentManager;

    public static boolean isNightMode = false;
}
