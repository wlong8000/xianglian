# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/wanglong/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#eventbus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

#七牛
-keep class com.qiniu.**{*;}
-keep class com.qiniu.**{public <init>();}
-dontwarn okio.**
-ignorewarnings

#友盟
-keep class com.umeng.** {*;}

-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class dalvik.system.DexFile{*;}

-keep class qalsdk.**{*;}
-dontwarn qalsdk.**

#保留系统中继承v4/v7包的类，不被混淆
-keep class android.support.** { *; }
-keep class android.support.v4.** { *; }
-keep public class * extends android.support.v4.**
-keep interface android.support.v4.app.** { *; }
-keep class android.support.v7.** { *; }
-keep public class * extends android.support.v7.**
-keep interface android.support.v7.app.** { *; }
-dontwarn android.support.**

#保留系统中继实现v4/v7包的接口，不被混淆
-keep public class * implements android.support.v4.**
-dontwarn android.support.v4.**

#所有的native方法不被混淆
-keepclasseswithmembers class * {
    native <methods>;
}

#自定义View构造方法不混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context,android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context,android.util.AttributeSet,int);
}

#枚举不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#release版不打印log
#-assumenosideeffects class android.util.Log {
#    public static *** d(...);
#    public static *** v(...);
#    public static *** i(...);
#    public static *** e(...);
#    public static *** w(...);
#}

#四大组件不能混淆
-dontwarn android.support.v4.**
-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep class android.support.v4.** { *; }
-keep public class * extends android.app.Application

-dontwarn android.support.v7.**
-keep class android.support.v7.internal.** { *; }
-keep interface android.support.v7.internal.** { *; }
-keep class android.support.v7.** { *; }

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference

#Design包不混淆
-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }

#不混淆资源类
-keepclassmembers class **.R$* {
    public static <fields>;
}

#确保JavaBean不被混淆-否则Gson将无法将数据解析成具体对象
-keep class com.xianglian.love.model.** { *; }
-keep class com.wl.appcore.entity.** { *; }
-keep class com.xianglian.love.user.been.** { *; }
-keep class com.xianglian.love.main.home.been.** { *; }
-keep class com.xianglian.love.main.meet.model.** { *; }
-keep class com.xianglian.love.main.special.model.** { *; }