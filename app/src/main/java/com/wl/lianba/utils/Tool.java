package com.wl.lianba.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.wl.lianba.user.been.MyContacts;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class Tool {

    /**
     * 获取库Phon表字段
     **/
    private static final String[] PHONES_PROJECTION = new String[]{Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID, Phone.CONTACT_ID};

    /**
     * 联系人显示名称
     **/
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;

    /**
     * 电话号码
     **/
    private static final int PHONES_NUMBER_INDEX = 1;

    /**
     * 头像ID
     **/
    private static final int PHONES_PHOTO_ID_INDEX = 2;

    /**
     * 联系人的ID
     **/
    private static final int PHONES_CONTACT_ID_INDEX = 3;

    public Tool() {

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
     * 检测sd卡是否有效
     *
     * @return
     */
    public static boolean checkSdAvailable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    /**
     * 检测sd卡是否可读
     *
     * @return
     */
    public static boolean checkSdReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /**
     * 读取 assets 目录中图片
     *
     * @param context
     * @param fileName 图片名称
     * @return
     */
    public static Bitmap getAssetsImage(Context context, String fileName) {
        Bitmap image = null;
        AssetManager am = context.getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * Wifi是否可用
     */
    public static boolean isWifiEnable(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wifiManager.isWifiEnabled();
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return  activeNetwork != null &&  activeNetwork.isConnectedOrConnecting();
    }

    /**
     * Gps是否可用
     */
    public static boolean isGpsEnable(Context context) {
        LocationManager locationManager = ((LocationManager) context.getSystemService(Context.LOCATION_SERVICE));
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static int randomInt(int scope) {
        return (int) (Math.random() * 1000 % scope);
    }

    /**
     * 保存图片到sd卡
     */
    public static void saveCahceBitmapToFile(Bitmap bitmap, String _file, CompressFormat compressFormat) throws IOException {
        BufferedOutputStream os = null;
        try {

            File filePath = new File(Environment.getExternalStorageDirectory() + "/quickjob");

            if (!filePath.exists()) {
                filePath.mkdir();
            }
            File file = new File(filePath + File.separator + _file);
            if (!file.exists()) {
                file.createNewFile();
                os = new BufferedOutputStream(new FileOutputStream(file));
                bitmap.compress(compressFormat, 100, os);
            }
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    Log.e("Error", e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 从sd卡 加载图片
     */
    public static Bitmap loadBitmapToFile(String imgName) throws IOException {
        Bitmap bm = null;
        // String filePath = Environment.getExternalStorageDirectory() +
        // "/quickjob";
        // String filename = filePath + File.separator + imgName;
        File mfile = new File(imgName);

        if (mfile.exists()) {
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inPreferredConfig = Bitmap.Config.RGB_565;
            opt.inPurgeable = true;
            opt.inInputShareable = true;
            opt.inSampleSize = 5;
            bm = BitmapFactory.decodeFile(imgName, opt);

        }
        return bm;
    }

    /**
     * 从sd卡 加载图片
     *
     * @author hu
     */

    public static Bitmap decodeSampledBitmapFromResource(String imgName, int reqWidth, int reqHeight) {
        File mfile = new File(imgName);
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        BitmapFactory.Options options = null;
        if (mfile.exists()) {
            options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(imgName, options);
            // 调用下面定义的方法计算inSampleSize值
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
            // 使用获取到的inSampleSize值再次解析图片

            options.inJustDecodeBounds = false;
        }
        return BitmapFactory.decodeFile(imgName, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    private static int readPictureDegree(String path, Context context) {
        // String[] proj = { MediaStore.Images.Media.DATA };
        // ContentResolver cr = context.getContentResolver();
        // Cursor cursor = cr.query(uri, proj, null, null, null);
        // int column_index =
        // cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        // cursor.moveToFirst();
        // String path = cursor.getString(column_index);

        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
            Log.i("info", "---------------------------1");
        } catch (IOException e) {
            Log.i("info", "---------------------------2");
            e.printStackTrace();
        }
        return degree;
    }

    /*
     * 旋转图片
     *
     * @param angle
     *
     * @param bitmap
     *
     * @return Bitmap
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    /**
     * 从sd卡 加载图片
     */
    public static Bitmap loadCacheBitmapToFile(String imgName) {
        Bitmap bm = null;
        String filePath = Environment.getExternalStorageDirectory() + "/quickjob";
        String filename = filePath + File.separator + imgName;
        File mfile = new File(filename);

        if (mfile.exists()) {
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inPreferredConfig = Bitmap.Config.RGB_565;
            opt.inPurgeable = true;
            opt.inInputShareable = true;
            // opt.inSampleSize = 4;
            bm = BitmapFactory.decodeFile(filename, opt);
        }
        return bm;
    }

    /**
     * 从sd卡 加载图片
     */
    public static Bitmap loadCacheBitmapToFile(String imgName, int scale, Context context) throws IOException {
        Bitmap bm = null;
        try {
            String filePath = Environment.getExternalStorageDirectory() + "/quickjob";
            String filename = filePath + File.separator + imgName;
            File mfile = new File(filename);

            if (mfile.exists()) {
                BitmapFactory.Options opt = new BitmapFactory.Options();
                opt.inPreferredConfig = Bitmap.Config.RGB_565;
                opt.inPurgeable = true;
                opt.inInputShareable = true;
                opt.inSampleSize = scale;
                bm = BitmapFactory.decodeFile(filename, opt);
            }

            int degree = readPictureDegree(filename, context);
            bm = rotaingImageView(degree, bm);

        } catch (Exception e) {
            bm = null;
        }

        return bm;
    }

    /**
     * @param localImgPath //图片所在sd卡路径
     * @param scale
     * @return
     * @throws IOException
     */
    public static Bitmap loadBitmapBySD(String localImgPath, int scale, Context context) throws IOException {
        Bitmap bm = null;
        try {
            File mfile = new File(localImgPath);

            if (mfile.exists()) {
                BitmapFactory.Options opt = new BitmapFactory.Options();
                opt.inPreferredConfig = Bitmap.Config.RGB_565;
                opt.inPurgeable = true;
                opt.inInputShareable = true;
                opt.inSampleSize = scale;
                bm = BitmapFactory.decodeFile(localImgPath, opt);

                Log.i("info", "调用3333");
                int degree = readPictureDegree(localImgPath, context);
                Log.i("info", "degree:" + degree);
                bm = rotaingImageView(degree, bm);
            }
        } catch (Exception e) {
            bm = null;
        }

        return bm;
    }

    public static int GetMinValue(int[] array) {
        int m = 0;
        int length = array.length;
        for (int i = 0; i < length; ++i) {

            if (array[i] < array[m]) {
                m = i;
            }
        }
        return m;
    }

    public static void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    public static String mapToString(Map<String, String> map) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            sb.append(key + "_" + value + ",");
        }
        if (sb.toString().length() > 1) {
            sb.deleteCharAt(sb.toString().length() - 1);
        }
        return sb.toString();
    }

    public static Map<String, String> stringToMap(String s) {
        Map<String, String> map = new HashMap<String, String>();
        String[] keyAndValue = s.split(",");
        for (int i = 0; i < keyAndValue.length; i++) {
            String[] tempArray = keyAndValue[i].split("_");
            map.put(tempArray[0], tempArray[1]);
        }
        return map;
    }

    public static String getIdFromMap(Map<String, String> map) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey().toString();
            sb.append(key + ",");
        }
        if (sb.toString().length() > 1) {
            sb.deleteCharAt(sb.toString().length() - 1);
        }
        return sb.toString();
    }

    public static String getIdFromList(List<String> list) {
        StringBuffer sb = new StringBuffer();
        for (String s : list) {
            sb.append(s + ",");
        }
        if (sb.toString().length() > 1) {
            sb.deleteCharAt(sb.toString().length() - 1);
        }
        return sb.toString();
    }

    public static String getIdFromSet(Set<String> set) {
        StringBuffer sb = new StringBuffer();
        for (String s : set) {
            sb.append(s + ",");
        }
        if (sb.toString().length() > 1) {
            sb.deleteCharAt(sb.toString().length() - 1);
        }
        return sb.toString();
    }

    public static String getContentFromMap(Map<String, String> map) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String value = entry.getValue().toString();
            sb.append(value + ",");
        }
        if (sb.toString().length() > 1) {
            sb.deleteCharAt(sb.toString().length() - 1);
        }
        return sb.toString();
    }

    /**
     * * 获取视频的缩略图 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
     * 91. * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。 92. * @param
     * videoPath 视频的路径 93. * @param width 指定输出视频缩略图的宽度 94. * @param height
     * 指定输出视频缩略图的高度度 95. * @param kind
     * 参照MediaStore.Images.Thumbnails类中的常量MINI_KIND和MICRO_KIND。 96. *
     * 其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96 97. * @return 指定大小的视频缩略图 98.
     */
    public static Bitmap getVideoThumbnail(String videoPath, int width, int height, int kind) {
        Bitmap bitmap = null;
        // 获取视频的缩略图
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        System.out.println("w" + bitmap.getWidth());
        System.out.println("h" + bitmap.getHeight());
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    /**
     * 将"yyyy-mm-dd HH:MM:SS"格式化为"yyyy.mm.dd"
     *
     * @param date
     * @return
     */
    public static String getSimpleDate(String date) {
        if (TextUtils.isEmpty(date)) {
            return null;
        } else {
            return date.split(" ")[0];
        }
    }

    /**
     * 将"yyyy-mm-dd HH:MM:SS"格式化为"yyyy-mm-dd"
     *
     * @param date
     * @return
     */
    public static String getDateString(String date) {
        if (null == date)
            return null;
        if (date.equals("")) {
            return "";
        } else {
            String yms = date.split(" ")[0];
            return yms;
        }
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public static int getScreenWidth(Activity act) {
        DisplayMetrics dm = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @return （像素）
     */
    public static int getScreenHeight(Activity act) {
        DisplayMetrics dm = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 获取屏幕密度
     *
     * @return （0.75 / 1.0 / 1.5）
     */
    public static float getScreenDensity(Activity act) {
        DisplayMetrics dm = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.density;
    }

    /**
     * 获取屏幕密度DPI
     *
     * @return （120 / 160 / 240）
     */
    public static int getScreenDensityDPI(Activity act) {
        DisplayMetrics dm = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.densityDpi;
    }

    /**
     * 调用电话
     */
    public static void call(Context ctx, String phoneno) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneno));
        ctx.startActivity(intent);
    }

    /**
     * 将drawable转化为bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static String calculateDate(int days) {
        String resultDate = null;
        GregorianCalendar calendar = new GregorianCalendar();
        Date date = calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println("Today: " + df.format(date));

        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - days);
        date = calendar.getTime();
        resultDate = (df.format(date)).split(" ")[0];
        return resultDate;
    }

    /**
     * 得到手机通讯录联系人信息
     **/
    public static List<MyContacts> getPhoneContacts(Context mContext) {
        List<MyContacts> data = new ArrayList<MyContacts>();
        ContentResolver resolver = mContext.getContentResolver();

        // 获取手机联系人
        Cursor phoneCursor = resolver.query(Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);

        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {

                // 得到联系人ID
                Long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);
                // 得到联系人名称
                String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
                // 得到手机号码
                String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);

                phoneNumber = phoneNumber.replaceAll("^(\\+86)", "");
                phoneNumber = phoneNumber.replaceAll("^(86)", "");
                phoneNumber = phoneNumber.replaceAll("-", "");
                phoneNumber = phoneNumber.replaceAll(" ", "");
                phoneNumber = phoneNumber.trim();

                // 当手机号码为空的或者为空字段 跳过当前循环
                if (TextUtils.isEmpty(phoneNumber))
                    continue;
                // 得到联系人头像ID
                Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);

                MyContacts mc = new MyContacts();
                mc.setId(contactid);
                mc.setName(contactName);
                mc.setPhoneNo(phoneNumber);
                mc.setAvatarId(photoid);
                data.add(mc);
            }

            phoneCursor.close();
        }

        return data;
    }

    /**
     * 得到手机SIM卡联系人人信息【SIM卡中的联系人无头像】
     **/
    public static List<MyContacts> getSIMContacts(Context mContext) {
        List<MyContacts> data = new ArrayList<MyContacts>();
        ContentResolver resolver = mContext.getContentResolver();
        // 获取Sims卡联系人
        Uri uri = Uri.parse("content://icc/adn");
        Cursor phoneCursor = resolver.query(uri, PHONES_PROJECTION, null, null, null);

        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {

                // 得到手机号码
                String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
                // 当手机号码为空的或者为空字段 跳过当前循环
                if (TextUtils.isEmpty(phoneNumber))
                    continue;
                // 得到联系人名称
                String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);

                MyContacts mc = new MyContacts();
                mc.setName(contactName);
                mc.setPhoneNo(phoneNumber);
                data.add(mc);
            }

            phoneCursor.close();
        }

        return data;
    }

    /**
     * 为TextView控件设置日期
     *
     * @param context
     * @param showView
     */
    public static void selectDateDialog(Context context, final TextView showView) {
        final Calendar currentDate = Calendar.getInstance();
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH);
        int day = currentDate.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                MyLog.ii(year + " , " + monthOfYear + " , " + dayOfMonth);
                monthOfYear = monthOfYear + 1;
                String date = year + "-";
                if (monthOfYear >= 10) {
                    date = date + monthOfYear + "-";
                } else {
                    date = date + "0" + monthOfYear + "-";
                }

                if (dayOfMonth >= 10) {
                    date = date + dayOfMonth;
                } else {
                    date = date + "0" + dayOfMonth;
                }
                showView.setText(date);
            }
        }, year, month, day).show();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static Bitmap drawableToBitmap1(Drawable drawable) {
        int width = drawable.getIntrinsicWidth(); // 取 drawable 的长宽
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565; // 取
        // drawable
        // 的颜色格式
        Bitmap bitmap = Bitmap.createBitmap(width, height, config); // 建立对应
        // bitmap
        Canvas canvas = new Canvas(bitmap); // 建立对应 bitmap 的画布
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas); // 把 drawable 内容画到画布中
        return bitmap;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    /**
     * 字符串转换到时间格式
     *
     * @param dateStr   需要转换的字符串
     * @param formatStr 需要格式的目标字符串 举例 yyyy-MM-dd
     * @return Date 返回转换后的时间
     * @throws ParseException 转换异常
     */
    public static Date StringToDate(String dateStr, String formatStr) {
        DateFormat sdf = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 压缩bitMap图片，大于maxSize大小的图片不断压缩到小于为止
     */
    public static Bitmap compressImage(Bitmap image, long maxSize) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;
        System.out.println("压缩前图片大小为 : " + baos.toByteArray().length / 1024 + "KB");
        while (baos.toByteArray().length > maxSize) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            MyLog.ee("options : " + options + "baos.toByteArray().length : " + baos.toByteArray().length + ", " + maxSize);
            options -= 10;// 每次都减少10
            MyLog.ee("options : " + options + "baos.toByteArray().length : " + baos.toByteArray().length);
            if (options < 0) {
                // options = 90;
                break;
            }
        }
        System.out.println("压缩后图片大小为 : " + baos.toByteArray().length / 1024 + "KB");
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 获得图片大小,大于指定值则需要压缩
     */
    public static boolean isNeedCompress(Bitmap image, long maxSize) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中

        // 需要压缩
        return baos.toByteArray().length > maxSize;

    }

    /**
     * 压缩图片到指定大小
     */

    public static Bitmap compressHeadPortraitImage(Bitmap image, int x, int y) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 1;
        int height = image.getHeight();
        int width = image.getWidth();

        opts.inSampleSize = height / y > width / x ? height / y : width / y;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 100;
        image.compress(CompressFormat.JPEG, options, baos);
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, opts);
        return bitmap;
    }

    /**
     * 通过路劲获得BITMAP图片
     */
    public static Bitmap getBitmapFromPath(String path) {

        BitmapFactory.Options ops = new BitmapFactory.Options();
        ops.inJustDecodeBounds = true;
        Bitmap bm = BitmapFactory.decodeFile(path, ops);
        ops.inSampleSize = 1;
        ops.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(path, ops);
        return bm;
    }

    /**
     * 把包含html标记的字符串 去掉 html 标记
     *
     * @param inputString
     * @return
     */
    public static String Html2Text(String inputString) {
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        Pattern p_script;
        java.util.regex.Matcher m_script;
        Pattern p_style;
        java.util.regex.Matcher m_style;
        Pattern p_html;
        java.util.regex.Matcher m_html;

        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            // }
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            // }
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签

            textStr = htmlStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return textStr;// 返回文本字符串
    }

    /**
     * 比较时间大小
     *
     * @return
     */

    public static boolean compareAWithB(String A, String B, int size) {
        if (TextUtils.isEmpty(A) || TextUtils.isEmpty(B)) {
            return false;
        }
        long aTime = 0;
        long bTime = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (size == 0) {
                aTime = sdf.parse(A).getTime();
                bTime = sdf.parse(B).getTime();
            } else {
                aTime = sdf1.parse(A).getTime();
                bTime = sdf1.parse(B).getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }// 毫秒
        return aTime > bTime;
    }

    /**
     * 判断数组中的最大数
     *
     * @param args
     * @return
     */
    public static int getMaxNum(int... args) {
        int max = 0;
        for (int i = 0; i < args.length; i++) {
            if (args[i] > args[max])
                max = i;
        }
        return args[max];
    }

    /**
     * 判断数组中的最小数
     *
     * @param args
     * @return
     */
    public static int getMinNum(int... args) {
        int min = 0;
        for (int i = 0; i < args.length; i++) {
            if (args[i] < args[min])
                min = i;
        }
        return args[min];
    }

    /**
     * 获取今天日期
     *
     * @return
     */
    public static String getTodayDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(new Date());
    }

    /**
     * 毛玻璃特效
     *
     * @param radius 模糊率
     * @return Bitmap
     */
    public Bitmap fastblur(Context context, Bitmap sentBitmap, int radius) {

        Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

        if (radius < 1) {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int temp = 256 * divsum;
        int dv[] = new int[temp];
        for (i = 0; i < temp; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

        bitmap.setPixels(pix, 0, w, 0, 0, w, h);
        return (bitmap);
    }


    /**
     * 取百分比
     */
    public static String getPercent(int x, int total) {
        if (total == 0) {
            return "";
        }

        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();

        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(1);
        String result = numberFormat.format((float) x / (float) total * 100);
        return result + "%";
    }



}
