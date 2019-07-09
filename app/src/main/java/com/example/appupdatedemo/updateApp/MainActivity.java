package com.example.appupdatedemo.updateApp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.appupdatedemo.R;
import com.example.appupdatedemo.updateApp.update.UpdateAppManager;
import com.example.appupdatedemo.updateApp.update.listener.ExceptionHandler;
import com.example.appupdatedemo.updateApp.update.util.UpdateAppHttpUtil;
/**
 * 由于作者处理的是json作为apk下载地址，而后台提供的是apk的url。
 * 两者不同，但是需要做成这样的更新弹框的话就需要对作者的代码进行修改;
 *
 * 8.0下载没有通知栏问题配置;
 *
 */
public class MainActivity extends AppCompatActivity {

    private String mUpdateUrl = "https://raw.githubusercontent.com/WVector/AppUpdateDemo/master/json/json.txt";
    private String mApkFileUrl = "https://raw.githubusercontent.com/WVector/AppUpdateDemo/master/apk/sample-debug.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 最简方式
     *
     * @param view
     */
    public void updateApp(View view) {
        new UpdateAppManager
                .Builder()
                //当前Activity
                .setActivity(this)
                //更新地址
                .setUpdateUrl(mUpdateUrl)
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {
                        e.printStackTrace();
                    }
                })
                //实现httpManager接口的对象
                .setHttpManager(new UpdateAppHttpUtil())
                .build()
                .update();
    }

}
