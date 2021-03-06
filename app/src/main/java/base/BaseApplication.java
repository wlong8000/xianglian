package base;

import android.os.Environment;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.crashlytics.android.Crashlytics;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.lzy.okgo.OkGo;
import com.okhttplib.OkHttpUtil;
import com.okhttplib.annotation.CacheType;
import com.okhttplib.annotation.Encoding;
import com.okhttplib.cookie.PersistentCookieJar;
import com.okhttplib.cookie.cache.SetCookieCache;
import com.okhttplib.cookie.persistence.SharedPrefsCookiePersistor;
import com.orhanobut.hawk.Hawk;
import com.umeng.commonsdk.UMConfigure;
import com.xianglian.love.AppService;
import com.wl.appcore.entity.UserEntity;
import com.xianglian.love.BuildConfig;
import com.xianglian.love.utils.AppUtils;
import com.xianglian.love.utils.Trace;
import com.xianglian.love.utils.UserUtils;

import java.io.File;

import io.fabric.sdk.android.Fabric;

/**
 * Application
 * 1、初始化全局OkHttpUtil
 *
 * @author wl
 */
public class BaseApplication extends MultiDexApplication {

    private static final String TAG = "BaseApplication";
    public static BaseApplication baseApplication;

    public static BaseApplication getApplication() {
        return baseApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
        Fabric.with(this, new Crashlytics());
        logUser();
        Fresco.initialize(this);
        Stetho.initializeWithDefaults(this);
        Hawk.init(this).build();
        MultiDex.install(this);
        if (BuildConfig.DEBUG) {           // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog();     // Print log
            ARouter.openDebug();   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(this);

        UserEntity userEntity = UserUtils.getUserEntity();
        Trace.d(TAG, "userEntity = " + userEntity);

        if (userEntity != null) {
            Trace.d(TAG, "username = " + userEntity.getUsername());
        }

        if (userEntity != null && !TextUtils.isEmpty(userEntity.getUsername())) {
            String username = AppUtils.getTimName(userEntity.getUsername(), String.valueOf(userEntity.getId()));
            AppService.startUpdateTimSign(this, username);
        }

        /*
            注意: 即使您已经在AndroidManifest.xml中配置过appkey和channel值，
            也需要在App代码中调用初始化接口（如需要使用AndroidManifest.xml中配置好的appkey和channel值，
            UMConfigure.init调用中appkey和channel参数请置为null）。
        */
        UMConfigure.init(this, "59815caaaed17933e9001575", "website", UMConfigure.DEVICE_TYPE_PHONE, null);

        String downloadFileDir = Environment.getExternalStorageDirectory().getPath() + "/okHttp_download/";
        String cacheDir = Environment.getExternalStorageDirectory().getPath();
        if (getExternalCacheDir() != null) {
            //缓存目录，APP卸载后会自动删除缓存数据
            cacheDir = getExternalCacheDir().getPath();
        }
        OkGo.getInstance().init(this).addCommonHeaders(AppUtils.getHeaders(this));
        OkHttpUtil.init(this)
                .setConnectTimeout(15)//连接超时时间
                .setWriteTimeout(15)//写超时时间
                .setReadTimeout(15)//读超时时间
                .setMaxCacheSize(10 * 1024 * 1024)//缓存空间大小
                .setCacheType(CacheType.FORCE_NETWORK)//缓存类型
                .setHttpLogTAG("HttpLog")//设置请求日志标识
                .setIsGzip(false)//Gzip压缩，需要服务端支持
                .setShowHttpLog(true)//显示请求日志
                .setShowLifecycleLog(true)//显示Activity销毁日志
                .setRetryOnConnectionFailure(false)//失败后不自动重连
                .setCachedDir(new File(cacheDir, "okHttp_cache"))//缓存目录
                .setDownloadFileDir(downloadFileDir)//文件下载保存目录
                .setResponseEncoding(Encoding.UTF_8)//设置全局的服务器响应编码
                .setRequestEncoding(Encoding.UTF_8)//设置全局的请求参数编码
                .addResultInterceptor(HttpInterceptor.ResultInterceptor)//请求结果拦截器
                .addExceptionInterceptor(HttpInterceptor.ExceptionInterceptor)//请求链路异常拦截器
                .setCookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(this)))//持久化cookie
                .build();
    }

    private void logUser() {
        // TODO: Use the current user's information
        // You can call any combination of these three methods
        Crashlytics.setUserIdentifier("com.xianglian.love");
        Crashlytics.setUserEmail("1615474873@qq.com");
        Crashlytics.setUserName("wlong");
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }


}
