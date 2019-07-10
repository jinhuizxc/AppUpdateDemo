package com.example.appupdatedemo.app_updater;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.appupdatedemo.R;
import com.example.appupdatedemo.app_updater.callback.UpdateCallback;
import com.example.appupdatedemo.app_updater.constant.Constants;
import com.example.appupdatedemo.updateApp.update.view.NumberProgressBar;
import com.example.appupdatedemo.utils.NotificationUtils;
import com.king.app.dialog.AppDialog;
import com.king.app.dialog.AppDialogConfig;
import com.mylhyl.circledialog.CircleDialog;

import java.io.File;

/**
 * 下载的url = http://192.168.1.169:7820/faas/fileapi/download/zxfile/b4e6049b5fd44f09aa9b6cdeb2893cdd?access_token=c0b315e5-221a-4cd3-953d-69a28309e084
 * <p>
 * 关于打电话权限是因为作者的库，引入了电话状态权限，只能修改他的源代码了;
 * <p>
 * Android 8.0更新适配和通知栏适配
 * https://www.jianshu.com/p/27ad5e773df0
 */
public class TestActivity extends AppCompatActivity {

    private String mApkFileUrl = "https://github.com/jinhuizxc/AppUpdateDemo/raw/master/app/apk/release/app-release.apk";
//    private String mApkFileUrl = "https://github.com/jenly1314/AppUpdater/raw/master/app/release/app-release.apk";
//    private String mApkFileUrl = "https://raw.githubusercontent.com/WVector/AppUpdateDemo/master/apk/sample-debug.apk";
//    private String mApkFileUrl = "http://192.168.1.169:7820/faas/fileapi/download/zxfile/b4e6049b5fd44f09aa9b6cdeb2893cdd?access_token=df61cf45-07ec-4bf4-ad90-709230b854f0";
    // 下载失败，不知道为啥

    private TestActivity mActivity;

    private boolean isDownload;
//    ProgressBar progressBar;

    // 自定义数字进度条
    private NumberProgressBar mNumberProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mActivity = this;

//        progressBar = findViewById(R.id.progressbar);
        //进度条
        mNumberProgressBar = findViewById(R.id.progressbar);

        // 检查是否打开了通知, 必须开启，否则可能8.0, 9.0手机收不到通知；
        checkOpenNotification();

    }

    private void checkOpenNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (!NotificationUtils.isNotificationEnabled(this)) {
                new CircleDialog.Builder()
                        .setTitle("您还未开启系统通知，可能会影响消息的接收，要去开启吗？")
                        .setTitleColor(getResources().getColor(R.color.black))
                        .setWidth(0.8f)

                        .setCancelable(false)
                        .setPositive("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // 跳转权限设置
                                NotificationUtils.gotoSet(TestActivity.this);
                            }
                        })
                        .setNegative("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .show(getSupportFragmentManager());
            } else {
                LogUtils.e("onNext: " + "已开启通知权限");
            }
        }


    }



    public void btn_page(View view) {
        //代码实现跳转
        Intent intent = new Intent();
//        intent.setAction("android.intent.action.VIEW");
        intent.setAction(Intent.ACTION_VIEW);
        Uri content_url = Uri.parse("https://www.baidu.com/");//此处填链接
        intent.setData(content_url);
        startActivity(intent);
    }

    /**
     * 最简方式
     *
     * @param view
     */
    public void updateApp(View view) {
        // 显示弹框，ok更新
        AppDialogConfig config = new AppDialogConfig();
        config.setOk("立即更新")
                .setTitle("发现新版本")
                .setContent("\r\n1.测试 \r\n2.优化xx问题")
                .setOnClickOk(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startUpdate();
                        AppDialog.INSTANCE.dismissDialog();
                    }
                })
                .setOnClickCancel(null);
        AppDialog.INSTANCE.showDialog(mActivity, config);
    }

    private void startUpdate() {
        /*new AppUpdater.Builder()
                .serUrl(mApkFileUrl)
                .setReDownload(true)
                .setInstallApk(true)
                .setChannelId(Constants.Notification.CHANNEL_ID)
                .setChannelName(Constants.Notification.CHANNEL_NAME)
                .setNotificationIcon(R.mipmap.ic_launcher)
                .setShowNotification(true)
                .setShowPercentage(true)
                .build(mActivity)
                .setUpdateCallback(new UpdateCallback() {
                    @Override
                    public void onDownloading(boolean isDownloading) {
                        if (isDownloading) {
                            ToastUtils.showShort("已经在下载中,请勿重复下载。");
                        }
                    }

                    @Override
                    public void onStart(String url) {
                        ToastUtils.showShort("开始下载");
                        isDownload = true;
//                        progressBar.setProgress(0);
//                        progressBar.setVisibility(View.VISIBLE);

                        mNumberProgressBar.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onProgress(int progress, int total, boolean isChange) {
                        if (isChange) {
//                            progressBar.setMax(total);
//                            progressBar.setProgress(progress);

                            mNumberProgressBar.setProgress(progress);
                            mNumberProgressBar.setMax(total);

                        }
                    }

                    @Override
                    public void onFinish(File file) {
                        isDownload = false;
//                        progressBar.setVisibility(View.INVISIBLE);
                        mNumberProgressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {
                        isDownload = false;
//                        progressBar.setVisibility(View.INVISIBLE);
                        mNumberProgressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onCancel() {
//                        progressBar.setVisibility(View.INVISIBLE);
                        mNumberProgressBar.setVisibility(View.INVISIBLE);
                    }
                }).start();*/


        new AppUpdater.Builder().serUrl(mApkFileUrl)
                .setReDownload(true)
                .setInstallApk(true)
                .setChannelId(Constants.Notification.CHANNEL_ID)
                .setChannelName(Constants.Notification.CHANNEL_NAME)
                .setNotificationIcon(R.mipmap.ic_launcher)
                .setShowNotification(true)
                .setShowPercentage(true)
                .build(mActivity)
                .setUpdateCallback(new UpdateCallback() {
                    @Override
                    public void onDownloading(boolean isDownloading) {
                        if (isDownloading) {
                            ToastUtils.showShort("已经在下载中,请勿重复下载。");
                        }
                    }

                    @Override
                    public void onStart(String url) {
                        ToastUtils.showShort("开始下载");
                        isDownload = true;
                        mNumberProgressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onProgress(int progress, int total, boolean isChange) {
                        if (isChange) {
                            mNumberProgressBar.setProgress(progress);
                            mNumberProgressBar.setMax(total);
                        }
                    }

                    @Override
                    public void onFinish(File file) {
                        isDownload = false;
                        mNumberProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        isDownload = false;
                        mNumberProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancel() {
                        mNumberProgressBar.setVisibility(View.GONE);
                    }
                }).start();


    }
}
