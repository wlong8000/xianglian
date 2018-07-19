
package com.xianglian.love.utils;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.support.annotation.DrawableRes;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.facebook.binaryresource.BinaryResource;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.request.ImageRequest;
import com.google.gson.Gson;
import com.lzy.okgo.model.HttpHeaders;
import com.orhanobut.hawk.Hawk;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.xianglian.love.BuildConfig;
import com.xianglian.love.R;
import com.xianglian.love.config.Config;
import com.xianglian.love.config.Keys;
import com.xianglian.love.main.home.been.PhotoInfo;
import com.xianglian.love.main.home.been.UserDetailEntity;
import com.xianglian.love.model.RegionGsonModel;
import com.xianglian.love.model.RegionsListModel;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AppUtils {
    private static final String TAG = "AppUtils";

    /**
     * 检测网络状态
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            showToast(context, context.getResources().getString(R.string.please_connect_network));
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        showToast(context, context.getResources().getString(R.string.please_connect_network));
        return false;
    }

    /**
     * Toast提示
     *
     * @param message
     */
    public static void showToast(Context context, String message) {
        showToast(context, message, true);
    }

    /**
     * Toast提示
     *
     * @param message
     */
    public static void showToast(Context context, String message, boolean isShort) {
        if (TextUtils.isEmpty(message)) return;
        Toast.makeText(context, message, isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG).show();
    }


    /**
     * 单独设置内部字体颜色
     *
     * @param text
     * @param keyText
     * @return
     */
    public static SpannableStringBuilder getSpannableTextColor(Context context, String text, String keyText, int color) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        if (text.contains(keyText)) {
            int spanStartIndex = text.indexOf(keyText);
            int spacEndIndex = spanStartIndex + keyText.length();
            spannableStringBuilder.setSpan(new ForegroundColorSpan(context.getResources().getColor(color)),
                    spanStartIndex, spacEndIndex, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        return spannableStringBuilder;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 去除字符串中的空格、回车、换行符、制表符
     *
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static Uri parse(String url) {
        if (TextUtils.isEmpty(url))
            return Uri.parse("");
        return Uri.parse(url);
    }

    /**
     * 判断用户是否登录
     */
    public static boolean isLogin(Context context) {
        return !TextUtils.isEmpty(AppUtils.getToken(context));
    }


    public static List<RegionGsonModel> getRegionFromCache(Context context) {
        try {
            InputStream is = context.getAssets().open("region.cache");
            int length;
            byte[] buffer = new byte[1024];
            StringBuilder sb = new StringBuilder();
            while ((length = is.read(buffer)) != -1) {
                sb.append(new String(buffer, 0, length));
            }
            Gson gson = new Gson();
            RegionsListModel models = gson.fromJson(sb.toString(), RegionsListModel.class);
//            RegionsListModel models = Gson.parseObject(sb.toString(), RegionsListModel.class);
            if (models != null)
                return models.regions;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean isIntentAvailable(Context context, Intent intent) {
        final PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.GET_ACTIVITIES);
        return list.size() > 0;
    }

    public static String getPicturePath(Context context) {
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
            return context.getFilesDir().getAbsolutePath();
        File file = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (file != null)
            return file.getAbsolutePath();
        file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (file != null)
            return file.getAbsolutePath();
        return context.getFilesDir().getAbsolutePath();
    }

    public static boolean isGifImage(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        String p = path.toLowerCase();
        return p.endsWith(".gif") || p.contains(".gif!") || p.contains(".gif-")
                || p.contains(".gif_");
    }

    /**
     * 解决小米手机上6.0获取图片路径返回cursor为null的情况
     *
     * @param intent
     * @return
     */
    public static Uri getUri(Context context, Intent intent) {
        Uri uri = intent.getData();
        String type = intent.getType();
        //修复华为手机(畅玩4X)url为null问题
        if (uri != null && uri.getScheme().equals("file")) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = context.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                        .append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{
                        MediaStore.Images.ImageColumns._ID
                }, buff.toString(), null, null);
                try {
                    int index = 0;
                    for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                        index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                        // set _id value
                        index = cur.getInt(index);
                    }
                    if (index == 0) {
                        // todo do nothing
                    } else {
                        Uri uri_temp = Uri.parse("content://media/external/images/media/" + index);
                        if (uri_temp != null) {
                            uri = uri_temp;
                        }
                    }
                } finally {
                    if (cur != null)
                        cur.close();
                }
            }
        }
        return uri;
    }

    public static void setImmersionType(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.transparent);
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            Window win = activity.getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
            SystemBarTintManager mTintManager = new SystemBarTintManager(activity);
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setNavigationBarTintEnabled(true);
            mTintManager.setTintColor(activity.getResources().getColor(R.color.transparent));
        }
    }

    public static CircularProgressDrawable getImageLoadProgress(Context context) {
        CircularProgressDrawable progressDrawable = new CircularProgressDrawable.Builder()
                .setRingWidth(AppUtils.dip2px(context, 6)).setOutlineColor(0x33FFFFFF)
                .setRingColor(0xffffffff)
                .setCenterColor(context.getResources().getColor(R.color.transparent))
                .setWidth(dip2px(context, 50)).setHeight(dip2px(context, 50))
                .setPadding(AppUtils.dip2px(context, 6)).create();
        progressDrawable.setIndeterminate(false);
        progressDrawable.setProgress(0);
        return progressDrawable;
    }

    /**
     * 获取fresco缓存中图片文件
     *
     * @param loadUri
     * @return
     */
    public static File getFrescoCachedImageOnDisk(Uri loadUri) {
        File localFile = null;
        if (loadUri != null) {
            CacheKey cacheKey = DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(
                    ImageRequest.fromUri(loadUri), null);
            if (ImagePipelineFactory.getInstance().getMainFileCache().hasKey(cacheKey)) {
                BinaryResource resource = ImagePipelineFactory.getInstance()
                        .getMainFileCache().getResource(cacheKey);
                localFile = ((FileBinaryResource) resource).getFile();
            } else if (ImagePipelineFactory.getInstance().getSmallImageFileCache()
                    .hasKey(cacheKey)) {
                BinaryResource resource = ImagePipelineFactory.getInstance()
                        .getSmallImageFileCache().getResource(cacheKey);
                localFile = ((FileBinaryResource) resource).getFile();
            }
        }
        return localFile;
    }


    /**
     * 获取SD卡空余空间
     *
     * @return 返回剩余空间 单位：byte （-1：表示失败）
     */
    public static long getSDFreeSize() {
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return -1;
        }
        // 取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        // 获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        // 空闲的数据块的数量
        long freeBlocks = sf.getAvailableBlocks();
        // 返回SD卡空闲大小
        return (freeBlocks * blockSize); // 单位byte
    }

//    public static OwnerEntity getOwnerInfo(Context context) {
//        String cache = ACache.get(context).getAsString(Config.KEY_USER);
//        if (TextUtils.isEmpty(cache)) return null;
//        try {
//            return JSON.parseObject(cache, OwnerEntity.class);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

//    public static String getUserId(Context context) {
//        OwnerEntity entity = getOwnerInfo(context);
//        if (entity != null && entity.getResult() != null && entity.getResult().getUser_info() != null) {
//            return entity.getResult().getUser_info().getUid();
//        }
//        return null;
//    }


    public static String getToken(Context context) {
        if (!TextUtils.isEmpty(Config.TOKEN)) return Config.TOKEN;
        String token = Hawk.get(Keys.TOKEN);
        if (!TextUtils.isEmpty(token)) {
            Config.TOKEN = "JWT " + token;
            return Config.TOKEN;
        }
        return null;
    }

    /**
     * 获取用户信息
     */
//    public static UserDetailEntity getUserInfo(Context context) {
//        OwnerEntity entity = getOwnerInfo(context);
//        if (entity != null && entity.getResult() != null && entity.getResult().getUser_info() != null
//                && entity.getResult().getUser_info().getProfile() != null) {
//            return entity.getResult().getUser_info().getProfile();
//        }
//        return null;
//    }

//    public static String getIntroduce(Context context) {
//        UserDetailEntity entity = getUserInfo(context);
//        if (entity == null) return null;
//        return entity.getPerson_intro();
//    }
//
//    public static String getExperience(Context context) {
//        UserDetailEntity entity = getUserInfo(context);
//        if (entity == null) return null;
//        return entity.getRelationship_desc();
//    }
//
//    public static String getChooseMarry(Context context) {
//        UserDetailEntity entity = getUserInfo(context);
//        if (entity == null) return null;
//        return entity.getMate_preference();
//    }


    /*
 * 获取手机信息
 */
    public static String getPhoneInfo(Context context, int type) throws PackageManager.NameNotFoundException {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        switch (type) {
            case 1:// 设备唯一标识
//				return telephonyManager.getDeviceId();
            case 2:// 系统版本号
                return Build.VERSION.RELEASE;
            case 3:// 设备型号
                return Build.MODEL;
            case 4:// 应用程序版本号
                return context.getPackageManager().getPackageInfo(context.getPackageName(),
                        0).versionName;
            default:
                return "";
        }
    }

    public static HttpHeaders getHeaders(Context context) {
        HttpHeaders  headers = new HttpHeaders();
        headers.put("platform", "android");
        headers.put("version_code", String.valueOf(BuildConfig.VERSION_CODE));
        headers.put("version_name", BuildConfig.VERSION_NAME);
        headers.put("channel", "website");
        headers.put("device_id", getUniquePsuedoID());
        if (!TextUtils.isEmpty(AppUtils.getToken(context))) {
            headers.put("Authorization", AppUtils.getToken(context));
        }
        return headers;
    }

    public static Map<String, String> getOAuthMap(Context context) {
        Map<String, String> headers = new HashMap<>();
        if (!TextUtils.isEmpty(AppUtils.getToken(context))) {
            headers.put("Authorization", AppUtils.getToken(context));
        }
        try {
            headers.put("device_id", getPhoneInfo(context, 1));
            headers.put("version_code", getPhoneInfo(context, 4));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        headers.put("platform", "android");
        return headers;
    }

    /**
     * 职业 (0, '未透露'), (1, "在校学生"), (2, "私营业主"), (3, "农业劳动者"), (4, "企业职工"), (5, "政府机关/事业单位"), (6, "自由职业")
     *
     * @param career
     * @return
     */
    public static String getCareer(Context context, int career) {
        Resources res = context.getResources();
        String[] arr = res.getStringArray(R.array.profession_array);
        if (arr.length <= career) return null;
        return arr[career];
    }

    public static int stringToInt(String num) {
        if (TextUtils.isEmpty(num)) return -1;
        try {
            return Integer.parseInt(num);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int stringToInt2(String num) {
        if (TextUtils.isEmpty(num)) return -1;
        try {
            return (int) Float.parseFloat(num);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int stringToFloat(String num) {
        if (TextUtils.isEmpty(num)) return 0;
        try {
            return (int) Float.parseFloat(num);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void loadLocalGif(Context context, SimpleDraweeView img, @DrawableRes int res) {
        Uri uri = Uri.parse("res://" + context.getPackageName() + "/" + R.drawable.loading);
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                .build();
        img.setController(draweeController);
    }

    public static String[] getUrls(List<PhotoInfo> list) {
        if (list == null || list.size() == 0) return null;
        String[] urls = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            urls[i] = list.get(i).getImage_url();
        }
        return urls;
    }

    public static List<String> getTags(List<UserDetailEntity> list) {
        if (list == null || list.size() == 0) return null;
        List<String> tags = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            tags.add(i, list.get(i).getTag_name());
        }
        return tags;
    }

    public static List<String> getInterests(List<UserDetailEntity> list) {
        if (list == null || list.size() == 0) return null;
        List<String> interests = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            interests.add(i, list.get(i).getInterest_name());
        }
        return interests;
    }

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return format.format(date);
    }

    /**
     * Return pseudo unique ID
     * @return ID
     */
    public static String getUniquePsuedoID() {
        // If all else fails, if the user does have lower than API 9 (lower
        // than Gingerbread), has reset their device or 'Secure.ANDROID_ID'
        // returns 'null', then simply the ID returned will be solely based
        // off their Android device information. This is where the collisions
        // can happen.
        // Thanks http://www.pocketmagic.net/?p=1662!
        // Try not to use DISPLAY, HOST or ID - these items could change.
        // If there are collisions, there will be overlapping data
        String m_szDevIDShort = "35" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10) + (Build.CPU_ABI.length() % 10) + (Build.DEVICE.length() % 10) + (Build.MANUFACTURER.length() % 10) + (Build.MODEL.length() % 10) + (Build.PRODUCT.length() % 10);

        // Thanks to @Roman SL!
        // http://stackoverflow.com/a/4789483/950427
        // Only devices with API >= 9 have android.os.Build.SERIAL
        // http://developer.android.com/reference/android/os/Build.html#SERIAL
        // If a user upgrades software or roots their device, there will be a duplicate entry
        String serial = null;
        try {
            serial = android.os.Build.class.getField("SERIAL").get(null).toString();

            // Go ahead and return the serial for api => 9
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            // String needs to be initialized
            serial = "serial"; // some value
        }

        // Thanks @Joe!
        // http://stackoverflow.com/a/2853253/950427
        // Finally, combine the values we have found by using the UUID class to create a unique identifier
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

    public static long string2Long(String text) {
        if (TextUtils.isEmpty(text)) return 0;
        try {
            return Long.parseLong(text);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
