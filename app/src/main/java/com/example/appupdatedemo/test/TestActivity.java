package com.example.appupdatedemo.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.blankj.utilcode.util.ToastUtils;
import com.example.appupdatedemo.R;
import com.king.app.dialog.AppDialog;
import com.king.app.updater.AppUpdater;
import com.king.app.updater.callback.UpdateCallback;

import java.io.File;

/**
 * 下载的url = http://192.168.1.169:7820/faas/fileapi/download/zxfile/b4e6049b5fd44f09aa9b6cdeb2893cdd?access_token=c0b315e5-221a-4cd3-953d-69a28309e084
 *
 * 关于打电话权限是因为作者的库，引入了电话状态权限，只能修改他的源代码了;
 *
 */
public class TestActivity extends AppCompatActivity {

    private String mApkFileUrl = "https://raw.githubusercontent.com/WVector/AppUpdateDemo/master/apk/sample-debug.apk";
//    private String mApkFileUrl = "http://192.168.1.169:7820/faas/fileapi/download/zxfile/b4e6049b5fd44f09aa9b6cdeb2893cdd?access_token=c0b315e5-221a-4cd3-953d-69a28309e084";

    private TestActivity mActivity;

    private boolean isDownload;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivity = this;

        progressBar = findViewById(R.id.progressbar);

    }

    /**
     * 最简方式
     *
     * @param view
     */
    public void updateApp(View view) {
        new AppUpdater.Builder().serUrl(mApkFileUrl)
                .setReDownload(true)
                .setInstallApk(true)
//                .setChannelId(Constants.Notification.CHANNEL_ID)
//                .setChannelName(Constants.Notification.CHANNEL_NAME)
//                .setNotificationIcon(R.mipmap.icon)
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
                        progressBar.setProgress(0);
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onProgress(int progress, int total, boolean isChange) {
                        if (isChange) {
                            progressBar.setMax(total);
                            progressBar.setProgress(progress);
                        }
                    }

                    @Override
                    public void onFinish(File file) {
                        isDownload = false;
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {
                        isDownload = false;
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onCancel() {
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }).start();
                            /*new AppUpdater(getContext(), updateVersion.fileid + "?access_token=" + Shareds.getInstance().getToken())
//                                    .setHttpManager(new UpdateAppHttpUtil())
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
                                            Logger.d("App更新 onStart = " + url);
//                                            progressBar.setProgress(0);
//                                            progressBar.setVisibility(View.VISIBLE);
                                        }

                                        @Override
                                        public void onProgress(int progress, int total, boolean isChange) {
                                            Logger.d("App更新 onProgress = " + progress);
                                            if (isChange) {
//                                                progressBar.setMax(total);
//                                                progressBar.setProgress(progress);
                                            }
                                        }

                                        @Override
                                        public void onFinish(File file) {
                                            Logger.d("App更新 onFinish = " + file.getAbsolutePath());
                                            isDownload = false;
//                                            progressBar.setVisibility(View.INVISIBLE);
                                        }

                                        @Override
                                        public void onError(Exception e) {
                                            Logger.d("App更新 onError");
                                            isDownload = false;
//                                            progressBar.setVisibility(View.INVISIBLE);
                                        }

                                        @Override
                                        public void onCancel() {
                                            Logger.d("App更新 onCancel");
//                                            progressBar.setVisibility(View.INVISIBLE);
                                        }
                                    }).start();*/

        AppDialog.INSTANCE.dismissDialog();
    }
}
