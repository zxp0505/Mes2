# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#-------------------------------------------基本不用动区域--------------------------------------------
#---------------------------------基本指令区----------------------------------
#指定压缩级别
-optimizationpasses 5
#不跳过非公共的库的类成员
-dontskipnonpubliclibraryclassmembers

-printmapping proguardMapping.txt
#混淆时采用的算法
-optimizations !code/simplification/cast,!field/*,!class/merging/*
-keepattributes *Annotation*,InnerClasses
#保持泛型
-keepattributes Signature
#保留行号
-keepattributes SourceFile,LineNumberTable
-ignorewarnings



#把混淆类中的方法名也混淆了
-useuniqueclassmembernames

#优化时允许访问并修改有修饰符的类和类的成员
-allowaccessmodification

#将文件来源重命名为“SourceFile”字符串
#-renamesourcefileattribute SourceFile

#----------------------------------------------------------------------------

#---------------------------------默认保留区---------------------------------
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}
-keep public class * extends android.os.IInterface

#Fragment不需要在AndroidManifest.xml中注册，需要额外保护下
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.app.Fragment

# 保持测试相关的代码
-dontnote junit.framework.**
-dontnote junit.runner.**
-dontwarn android.test.**
-dontwarn android.support.test.**
-dontwarn org.junit.**




-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
#-keepclassmembers class * implements java.io.Serializable {
#    static final long serialVersionUID;
#    private static final java.io.ObjectStreamField[] serialPersistentFields;
#    private void writeObject(java.io.ObjectOutputStream);
#    private void readObject(java.io.ObjectInputStream);
#    java.lang.Object writeReplace();
#    java.lang.Object readResolve();
#}

#保持 Serializable 不被混淆
-keepnames class * implements java.io.Serializable

#保持 Serializable 不被混淆并且enum 类也不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class **.R$* {
 *;
}
-keepclassmembers class * {
    void *(OnEvent**);
}





-keepclassmembers class ** {
    public void onEvent*(**);
    void onEvent*(**);
}


-keepclasseswithmembernames class * { # 保持 native 方法不被混淆
 native <methods>;
}

-keepclasseswithmembers class * { # 保持自定义控件类不被混淆
 public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {# 保持自定义控件类不被混淆
 public <init>(android.content.Context, android.util.AttributeSet, int);
}

#不混淆Activity中参数是View的方法，因为有这样一种用法，在XML中配置android:onClick=”buttonClick”属性，
#当用户点击该按钮时就会调用Activity中的buttonClick(View view)方法，如果这个方法被混淆的话就找不到了。
-keepclassmembers class * extends android.app.Activity {
 public void *(android.view.View);
}

-keepclassmembers enum * { # 保持枚举 enum 类不被混淆
 public static **[] values();
 public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable { # 保持 Parcelable 不被混淆
 public static final android.os.Parcelable$Creator *;
}

#----------------------------------------基本不用动区域-------------------------------------


#--------------------------------------------------三方混淆------------------------------------
-keep class com.bumptech.** { *; }
-keepnames class com.bumptech.** { *; }
-keep class com.squareup.** { *; }
-keepnames class com.squareup.** { *; }
-keep class com.google.** { *; }
-keepnames class com.google.** { *; }
-keep class com.jakewharton.** { *; }
-keepnames class com.jakewharton.** { *; }
-keep class com.alibaba.** { *; }
-keepnames class com.alibaba.** { *; }
-keep class com.chad.** { *; }
-keepnames class com.chad.** { *; }
-keep class com.github.barteksc.** { *; }
-keepnames class com.github.barteksc.** { *; }
-keep class com.wang.avi.** { *; }
-keepnames class com.wang.avi.** { *; }
-keep class com.zhy.http.okhttp.** { *; }
-keepnames class com.zhy.http.okhttp.** { *; }
-keep class com.trello.rxlifecycle2.** { *; }
-keepnames class com.trello.rxlifecycle2.** { *; }
-keep class com.bigkoo.convenientbanner.** { *; }
-keepnames class com.bigkoo.convenientbanner.** { *; }
-keep class com.orhanobut.logger.** { *; }
-keepnames class com.orhanobut.logger.** { *; }
-keep class com.shockwave.pdfium.** { *; }
-keepnames class com.shockwave.pdfium.** { *; }
-keep class com.tbruyelle.** { *; }
-keepnames class com.tbruyelle.** { *; }
-keep class kotlin.** { *; }
-keepnames class kotlin.** { *; }
-keep class rx.** { *; }
-keepnames class rx.** { *; }
-keep class java.** { *; }
-keepnames class java.** { *; }
-keep class okhttp3.** { *; }
-keepnames class okhttp3.** { *; }
-keep class cn.com.ethank.** { *; }
-keepnames class cn.com.ethank.** { *; }
-keep class me.yokeyword.** { *; }
-keepnames class me.yokeyword.** { *; }
-keep class okio.** { *; }
-keepnames class okio.** { *; }
-keep class retrofit2.** { *; }
-keepnames class retrofit2.** { *; }
-keep class org.** { *; }
-keepnames class org.** { *; }
-keep class pub.** { *; }
-keepnames class pub.** { *; }
-keep class butterknife.** { *; }
-keepnames class butterknife.** { *; }
-keep class javax.** { *; }
-keepnames class javax.** { *; }
-keep class sun.** { *; }
-keepnames class sun.** { *; }

#--------------------------------------------------三方混淆------------------------------------


