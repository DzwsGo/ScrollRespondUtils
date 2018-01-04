package cn.dzws.android.scrollrespondUtils.utils;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import cn.dzws.android.scrollrespondUtils.activity.RecyclerViewActivity;
import cn.dzws.android.scrollrespondUtils.activity.ScrollViewActivity;

/**
 * @author Lwang
 * @time 2018/1/4.
 */

public class DIntent {
    public static void toRecyclerView(AppCompatActivity appCompatActivity) {
        Intent intent = new Intent(appCompatActivity,RecyclerViewActivity.class);
        appCompatActivity.startActivity(intent);
    }
    public static void toScrollView(AppCompatActivity appCompatActivity) {
        Intent intent = new Intent(appCompatActivity,ScrollViewActivity.class);
        appCompatActivity.startActivity(intent);
    }
}
