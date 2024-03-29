package com.jiandanbaoxian.util;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.constant.StringConstant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * @author coolszy
 * @date 2012-4-26
 * @blog http://blog.92coding.com
 */

public class UpdateVersionUtil {
    private static final String packageName = "com.jiandanbaoxian";
    private static final String apkName = StringConstant.app_name + File.pathSeparator + "anxin.apk";
    private String apkUrl = "";
    private String versionCodeFromServer = "";


    /* 下载保存路径 */
    private String mSavePath;
    /* 记录进度条数量 */
    private float progress;
    /* 是否取消更新 */
    private boolean cancelUpdate = false;

    private Context mContext;
    /* 更新进度条 */
    private ProgressBar mProgress;
    private TextView progressTextView;

    private Dialog mDownloadDialog;

    public UpdateVersionUtil(Context context, String apkUrl, String versionCodeFromServer) {
        this.mContext = context;
        this.apkUrl = apkUrl;
        this.versionCodeFromServer = versionCodeFromServer;
    }

    /**
     * 版本更新检测
     */
    public void checkVerisonUpdate() {
        if (!versionCodeFromServer.equals(getVersionCode(mContext) + "")) {
            showNoticeDialog();
        }

    }

    /**
     * 获取软件版本号
     *
     * @param context
     * @return
     */
    private int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = context.getPackageManager().getPackageInfo(packageName, 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 显示软件更新对话框
     */
    private void showNoticeDialog() {
        // 构造对话框
        AlertDialog.Builder builder = new Builder(mContext);
        builder.setTitle("软件更新");
        builder.setMessage("检测到新版本，立即更新吗?");
        // 更新
        builder.setPositiveButton("更新", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // 显示下载对话框
                showDownloadDialog();
            }
        });
        // 稍后更新
        builder.setNegativeButton("暂不更新", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog noticeDialog = builder.create();
        noticeDialog.show();
    }

    /**
     * 显示软件下载对话框
     */
    private void showDownloadDialog() {
        // 构造软件下载对话框
        AlertDialog.Builder builder = new Builder(mContext);
        builder.setTitle("正在更新");
        // 给下载对话框增加进度条
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.view_update_version, null);
        mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
        progressTextView = (TextView) v.findViewById(R.id.progressTextView);
        builder.setView(v);
        // 取消更新
        builder.setNegativeButton("取消", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // 设置取消状态
                cancelUpdate = true;
            }
        });
        mDownloadDialog = builder.create();
        mDownloadDialog.show();
        // 现在文件
        downloadApk();
    }

    /**
     * 下载apk文件
     */
    private void downloadApk() {
        // 启动新线程下载软件
        new AsyncTask<Integer, Float, Boolean>() {
            @Override
            protected void onProgressUpdate(Float... values) {
//                mProgress.setProgress((int) progress);

                progressTextView.setText(progress + "%");
                super.onProgressUpdate(values);

            }

            @Override
            protected Boolean doInBackground(Integer... params) {
                try {
                    // 判断SD卡是否存在，并且是否具有读写权限
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        // 获得存储卡的路径
                        String sdpath = Environment.getExternalStorageDirectory() + File.separator;
                        mSavePath = sdpath + "download";
                        URL url = new URL(apkUrl);
                        // 创建连接
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.connect();
                        // 获取文件大小
                        int length = conn.getContentLength();
                        // 创建输入流
                        InputStream is = conn.getInputStream();

                        File file = new File(mSavePath);
                        // 判断文件目录是否存在
                        if (!file.exists()) {
                            file.mkdir();
                        }
                        File apkFile = new File(mSavePath, apkName);
                        FileOutputStream fos = new FileOutputStream(apkFile);
                        int count = 0;
                        // 缓存
                        byte buf[] = new byte[1024];
                        // 写入到文件中
                        do {
                            int numread = is.read(buf);
                            count += numread;
                            // 计算进度条位置
                            progress = (count / length) * 100;
                            // 更新进度
                            publishProgress(progress);

                            // 写入文件
                            fos.write(buf, 0, numread);
                        } while (count < length && !cancelUpdate);// 点击取消就停止下载.
                        fos.close();
                        is.close();
                    } else {
                        Log.e("jwjw", "not exist fils ");
                    }
                } catch (MalformedURLException e) {
                    Log.e("jwjw", "MalformedURLException" + e.toString());
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.e("jwjw", "IOException" + e.toString());
                    e.printStackTrace();
                }
                return cancelUpdate;

            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Boolean aVoid) {
                super.onPostExecute(aVoid);
                // 取消下载对话框显示
                mDownloadDialog.dismiss();
                if (!aVoid) {
                    installApk();
                }
            }
        }.execute();
    }

    /**
     * 安装APK文件
     */
    private void installApk() {
        File apkfile = new File(mSavePath, apkName);
        if (!apkfile.exists()) {
            return;
        }
        // 通过Intent安装APK文件
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        mContext.startActivity(i);
    }
}
