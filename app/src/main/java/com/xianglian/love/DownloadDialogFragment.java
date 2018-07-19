
package com.xianglian.love;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;
import com.xianglian.love.model.DownloadModel;
import com.xianglian.love.utils.Trace;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DownloadDialogFragment extends DialogFragment implements View.OnClickListener {
    private static final String TAG = "DownloadActivity";

    public static final String PARAMS_DOWN_LORD_MODEL = "download_model";

    private DownloadModel mModel;

    private boolean isForceUp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_download, container, false);
        setupView(v);
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        Bundle mBundle = getArguments();
        if (mBundle == null) {
            Toast.makeText(getActivity(), getResources().getString(R.string.download_fail) + "！", Toast.LENGTH_SHORT).show();
            dismiss();
            return;
        }
        mModel = mBundle.getParcelable(PARAMS_DOWN_LORD_MODEL);
        isForceUp = mModel != null && mModel.getIs_mandatory() == 1;
        Trace.d(TAG, "isForceUp = " + isForceUp);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            if (dialog.getWindow() == null) return;
            dialog.getWindow().setLayout(dm.widthPixels * 8 / 9, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    private void setupView(View view) {
        TextView title = view.findViewById(R.id.download_title);
        TextView content = view.findViewById(R.id.download_content);
        Button confirm = view.findViewById(R.id.confirm);
        Button cancel = view.findViewById(R.id.cancel);
        View bottomLine = view.findViewById(R.id.bottom_line);


        title.setText(mModel.title);
        content.setText(mModel.desc);

        if (!TextUtils.isEmpty(mModel.title)) {
            title.setVisibility(View.VISIBLE);
            title.setText(mModel.title);
        } else {
            title.setVisibility(View.GONE);
        }

        setContent(content);

        if (!TextUtils.isEmpty(mModel.confirm)) {
            confirm.setText(mModel.confirm);
        }
        if (!TextUtils.isEmpty(mModel.cancel)) {
            cancel.setText(mModel.cancel);
        }

        if (isForceUp) {
            cancel.setVisibility(View.GONE);
            bottomLine.setVisibility(View.VISIBLE);
        }

        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    private void setContent(TextView content) {
        StringBuilder buffer = new StringBuilder();
        if (!TextUtils.isEmpty(mModel.getFile_size())) {
            buffer.append(mModel.getFile_size());
            if (!TextUtils.isEmpty(mModel.getDownloads())) {
                buffer.append(",   ");
            }
        }

        if (!TextUtils.isEmpty(mModel.getDownloads())) {
            buffer.append(Html.fromHtml("<font color='#ff0000'>" + mModel.downloads + "</font>" + "下载"));
        }
        if (!TextUtils.isEmpty(buffer.toString())) {
            content.setVisibility(View.VISIBLE);
            content.setText(buffer.toString());
        }
    }


    @Override
    public void onClick(View v) {
        if (mModel == null) return;
        if (v.getId() == R.id.confirm) {
            if (!TextUtils.isEmpty(mModel.url)) {
                downloadApk(getActivity(), mModel.url,
                        mModel.notificationTitle, mModel.notificationDesc, mModel.filename, true);
                Toast.makeText(getActivity(), getResources().getString(R.string.download_now), Toast.LENGTH_SHORT).show();
            }
        }

        dismiss();
    }

    private void downloadApk(Context context, String url, String title, String desc,
                             String fileName, boolean isApk) {

        // 判断是否能用系统下载
        int state = context.getPackageManager()
                .getApplicationEnabledSetting("com.android.providers.downloads");
        if (state != PackageManager.COMPONENT_ENABLED_STATE_DEFAULT
                && state != PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW);
            webIntent.setData(Uri.parse(url));
            context.startActivity(webIntent);
            return;
        }

        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        // 创建下载请求
        DownloadManager.Request down = new DownloadManager.Request(Uri.parse(url));
        // 设置允许使用的网络类型，这里是移动网络和wifi都可以
        down.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        down.setVisibleInDownloadsUi(true);
        down.allowScanningByMediaScanner();
        down.setNotificationVisibility(
                DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        // 设置下载后文件存放的位置
        if (isApk && !url.endsWith(".apk")) {
            String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date());
            fileName = timeStamp + ".apk";
        } else if (TextUtils.isEmpty(fileName)) {
            fileName = url.substring(url.lastIndexOf("/") + 1);
        }
        down.setTitle(title == null ? context.getString(R.string.download_apk) : title);
        down.setDescription(desc == null ? fileName : desc);
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            down.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
        }
        // 将下载请求放入队列
        if (manager != null) {
            long id = manager.enqueue(down);
            Hawk.put("down_id", String.valueOf(id));
        }
    }

    public static DownloadDialogFragment newInstance(DownloadModel model) {
        DownloadDialogFragment dialogFragment = new DownloadDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(PARAMS_DOWN_LORD_MODEL, model);
        dialogFragment.setArguments(args);
        return dialogFragment;
    }
}
