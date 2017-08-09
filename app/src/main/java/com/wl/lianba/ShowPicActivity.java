
package com.wl.lianba;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.media.MediaScannerConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.wl.lianba.bottomttabs.StatusbarUtil;
import com.wl.lianba.fresco.zoomable.ZoomableDraweeView;
import com.wl.lianba.utils.AppUtils;
import com.wl.lianba.view.MyViewPager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * 图片查看器
 */
public class ShowPicActivity extends BaseActivity implements View.OnClickListener {
    private static final String tag = "ShowPicActivity";

    private final boolean showThumb = false;// 显示缩略图

    private MyViewPager pager;

    private String[] pics;

    private String[] thumbs;

    private Map<String, Boolean> isGif;

    private int localPosition;

    private ImagePagerAdapter adapter;

    private View saveView;

    private Map<String, Boolean> cache;

    private Map<Integer, SoftReference<ZoomableDraweeView>> viewMap = new HashMap<>();

    private LinearLayout title;

    public static void showPictures(Activity context, String url) {
        Intent imageIntent = new Intent(context, ShowPicActivity.class);
        String[] urls = {
                url
        };
        imageIntent.putExtra("fileName", urls);
        context.startActivity(imageIntent);
        context.overridePendingTransition(R.anim.show_picture_anim_in, 0);
    }


    public static void showPictures(Context context, String[] urls, String[] thumb, int num) {
        Intent imageIntent = new Intent(context, ShowPicActivity.class);
        imageIntent.putExtra("fileName", urls);
        imageIntent.putExtra("thumbs", thumb);
        imageIntent.putExtra("num", num);
        context.startActivity(imageIntent);
        if (context instanceof Activity) {
            ((Activity) context).overridePendingTransition(R.anim.show_picture_anim_in, 0);
        }
    }

    public static void showPictures(Context context, String[] urls, int num) {
        Intent imageIntent = new Intent(context, ShowPicActivity.class);
        imageIntent.putExtra("fileName", urls);
        imageIntent.putExtra("num", num);
        context.startActivity(imageIntent);
        if (context instanceof Activity) {
            ((Activity) context).overridePendingTransition(R.anim.show_picture_anim_in, 0);
        }
    }


    // private int screenWidth;
    // private int height[];
    // private int width[];
    private MyViewPager.OnViewPagerTouchUpListener touchUpListener = new MyViewPager.OnViewPagerTouchUpListener() {
        @Override
        public void onTouchUp() {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showpic);
        saveView = findViewById(R.id.saveBtn);
        title = (LinearLayout) findViewById(R.id.title);
        findViewById(R.id.back).setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            title.setPadding(0, StatusbarUtil.getStatusBarHeight(getApplicationContext()), 0, 0);
        }
        isGif = new HashMap();
        // screenWidth = getResources().getDisplayMetrics().widthPixels;
        if (getIntent().getStringArrayExtra("fileName") != null) {
            pics = getIntent().getStringArrayExtra("fileName");
            // height=getIntent ().getIntArrayExtra ("height");
            // width=getIntent ().getIntArrayExtra ("width");
            if (getIntent().hasExtra("thumbs")) {
                thumbs = getIntent().getStringArrayExtra("thumbs");
                if (pics.length != thumbs.length)
                    thumbs = null;
            }
            cache = new HashMap<String, Boolean>() {
                {
                    for (int i = 0, j = pics.length; i < j; i++) {
                        put(pics[i], false);
                    }
                }
            };
            adapter = new ImagePagerAdapter(pics);
            pager = (MyViewPager) findViewById(R.id.pager);
            pager.setPageMargin(5);
            pager.setAdapter(adapter);
            pager.setCurrentItem(getIntent().getIntExtra("num", 0));
            pager.setOnViewPagerTouchUpListener(touchUpListener);
            pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset,
                                           int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    int currentIndex = pager.getCurrentItem();

                    if (cache.get(pics[localPosition])
                            && viewMap.get(localPosition) != null
                            && viewMap.get(localPosition).get() != null
                            && viewMap.get(localPosition).get().getController().getAnimatable() != null) {
                        viewMap.get(localPosition).get().getController().getAnimatable().stop();
                    }
                    localPosition = currentIndex;
                    getTextView(R.id.pageNum).setText((currentIndex + 1) + "/" + pics.length);
                    if (cache.get(pics[currentIndex])) {
                        saveView.setVisibility(View.VISIBLE);
                    } else
                        saveView.setVisibility(View.GONE);

                    if (cache.get(pics[localPosition])
                            && viewMap.get(localPosition) != null
                            && viewMap.get(localPosition).get() != null
                            && viewMap.get(localPosition).get().getController().getAnimatable() != null) {
                        viewMap.get(localPosition).get().getController().getAnimatable().start();
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
            pager.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            localPosition = (getIntent().getIntExtra("num", 0));
            if (localPosition >= pics.length)
                localPosition = 0;
            getTextView(R.id.pageNum).setText((localPosition + 1) + "/" + pics.length);
        } else {
            AppUtils.showToast(this, getString(R.string.parameter_error_retry));
            finish();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
            SystemBarTintManager mTintManager = new SystemBarTintManager(this);
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setNavigationBarTintEnabled(true);
            mTintManager.setTintColor(Color.TRANSPARENT);
            // mTintManager.setTintDrawable(UIElementsHelper
            // .getGeneralActionBarBackground(activity));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        cache.clear();
        super.onDestroy();
    }

    public void run(View target) {
        switch (target.getId()) {
            case R.id.saveBtn:
                if (pics.length > 0) {
                    if (cache.containsKey(pics[localPosition]) && !cache.get(pics[localPosition])) {
                        AppUtils.showToast(getApplicationContext(),
                                getString(R.string.pic_not_ready_to_save));
                        return;
                    }
                    saveImage(pics[localPosition]);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void saveImage(String url) {
        if (TextUtils.isEmpty(url)) {
            AppUtils.showToast(this, getString(R.string.image_url_empty),
                    false);
            return;
        }
        File picFile = AppUtils.getFrescoCachedImageOnDisk(AppUtils.parse(url));
        String msg = getString(R.string.save_pic_error);
        if (picFile != null && picFile.exists()) {
            FileOutputStream os = null;
            int fileSize = 0;
            FileInputStream is = null;
            try {
                is = new FileInputStream(picFile);
                final String rootPath = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES).getAbsolutePath()
                        + "/dongqiudi/";
                File file = new File(rootPath);
                if (!file.exists())
                    file.mkdirs();
                String suffix;
                if (isGif.get(url) == null) {
                    // // TODO: 16/6/12
                    suffix = AppUtils.isGifImage(url) ? ".gif" : ".jpg";
                } else {
                    suffix = isGif.get(url) ? ".gif" : ".jpg";
                }
                final String saveFilePath = rootPath + System.currentTimeMillis() + suffix;
                file = new File(saveFilePath);
                if (!file.exists())
                    file.createNewFile();
                os = new FileOutputStream(saveFilePath);
                int len;

                byte[] bytes = new byte[1024];
                while ((len = is.read(bytes)) != -1) {
                    fileSize += len;
                    os.write(bytes, 0, len);
                }
                msg = getString(R.string.savepic_to) + saveFilePath;
                AppUtils.showToast(this, msg, false);
                MediaScannerConnection.scanFile(this, new String[]{
                        saveFilePath
                }, null, null);
                return;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                // 保存图片失败，存储空间不足时给出提示
                if (AppUtils.getSDFreeSize() < fileSize) {
                    msg = getString(R.string.no_enough_space);
                }
            } finally {
                if (os != null)
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if (is != null)
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        } else {
            msg = getString(R.string.pic_not_ready_to_save);
        }
        AppUtils.showToast(this, msg, false);
    }

    @Override
    public boolean needTitleTransparent() {
        return false;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.show_picture_anim_out);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    class ImagePagerAdapter extends PagerAdapter {
        private String[] images;

        private LayoutInflater inflater;

        ImagePagerAdapter(String[] images) {
            this.images = images;
            inflater = getLayoutInflater();
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object instantiateItem(ViewGroup view, final int position) {
            String default_pic = "asset://com.dongqiudi.news/images/pic_load_failed.png";
            if (thumbs != null && position < thumbs.length) {
                default_pic = TextUtils.isEmpty(thumbs[position]) ? default_pic : thumbs[position];
            }
            final String path = TextUtils.isEmpty(images[position]) ? default_pic
                    : images[position];
            final ZoomableDraweeView zoomView = (ZoomableDraweeView) inflater.inflate(R.layout.pic,
                    null, false);

            PipelineDraweeControllerBuilder builder = Fresco.newDraweeControllerBuilder();
            ImageRequestBuilder imageRequest = ImageRequestBuilder.newBuilderWithSource(AppUtils
                    .parse(path));
            builder.setOldController(zoomView.getController()).setTapToRetryEnabled(true)
                    .setImageRequest(imageRequest.build()).setAutoPlayAnimations(false)
                    .setControllerListener(new BaseControllerListener<ImageInfo>() {
                        @Override
                        public void onFinalImageSet(String id, ImageInfo imageInfo,
                                                    Animatable animatable) {
                            viewMap.put(position, new SoftReference<>(zoomView));
                            super.onFinalImageSet(id, imageInfo, animatable);
                            cache.put(path, true);
                            if (pager.getCurrentItem() == position) {
                                saveView.setVisibility(View.VISIBLE);
                                if (animatable != null)
                                    animatable.start();
                            }
                            if (animatable != null) {
                                isGif.put(path, true);
                            } else {
                                isGif.put(path, false);
                            }
                        }
                    });
            if (showThumb) {
                if (thumbs != null)
                    builder.setLowResImageRequest(ImageRequestBuilder.newBuilderWithSource(
                            AppUtils.parse(thumbs[position])).build());
            }
            DraweeController controller = builder.build();

            zoomView.setController(controller);
            GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(
                    view.getResources()).setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                    .setProgressBarImage(AppUtils.getImageLoadProgress(getApplicationContext()))
                    .setRetryImage(getResources().getDrawable(R.drawable.load_failed_retry))
                    .setFailureImage(getResources().getDrawable(R.drawable.load_failed)).build();

            zoomView.setHierarchy(hierarchy);
            zoomView.setOnSingleClickListener(new ZoomableDraweeView.onSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    ShowPicActivity.this.finish();
                }
            });
            view.addView(zoomView, 0);
            return zoomView;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View container) {
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public void finishUpdate(View container) {
        }

    }
}
