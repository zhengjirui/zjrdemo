# To enable ProGuard in your project, edit project.properties
# to define the proguard.config property as described in that file.
#
# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in ${sdk.dir}/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the ProGuard
# include property in project.properties.
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

#代码混淆的压缩比例，值在0-7之间
-optimizationpasses 5

#混淆后类名都为小写
-dontusemixedcaseclassnames

#指定不去忽略非公共的库的类
-dontskipnonpubliclibraryclasses

#指定不去忽略非公共的库的类的成员
-dontskipnonpubliclibraryclassmembers

#不做预校验的操作
-dontpreverify

#生成原类名和混淆后的类名的映射文件
-verbose

-printmapping proguardMapping.txt

#指定混淆是采用的算法
-optimizations !code/simplification/cast,!field/*,!class/merging/*

#不混淆Annotation
-keepattributes *Annotation*,InnerClasses

#不混淆泛型
-keepattributes Signature

#抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable

# 保持哪些类不被混淆
-keep public class * extends com.metshow.bz.view.activity.base.BaseActivity
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

#如果有引用v4包可以添加下面这行
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends com.metshow.bz.model.bean.POJO

#忽略警告
-ignorewarning
#####################记录生成的日志数据,gradle build时在本项目根目录输出################
#apk 包内所有 class 的内部结构
-dump class_files.txt
#未混淆的类和成员
-printseeds seeds.txt
#列出从 apk 中删除的代码
-printusage unused.txt
#混淆前后的映射
-printmapping mapping.txt

-keep class com.tencent.**{*;}
-keep class com.umeng.**{*;}
-keep class com.sina.** {*;}
-keep class com.baidu.** {*;}
-keep class rx.** {*;}
-keep class retrofit2.** {*;}
-keep class okio.** {*;}
-keep class okhttp3.** {*;}
-keep class com.github.**{*;}
-keep class com.daimajia.**{*;}
-keep class com.aspsine.**{*;}
-keep class com.yongchun.**{*;}
-keep class com.kogitune.**{*;}

-keep class com.android.**{*;}
-keep class android.support.** {*;}
-keep class vi.com.gdi.bgl.android.**{*;}


#如果引用了v4或者v7包
-dontwarn android.support.**

#-dontwarn cn.sharesdk.**
##混淆保护自己项目的部分代码以及引用的第三方jar包library-end
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}
#保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

# Keep names - Native method names. Keep all native class/method names.
-keepclasseswithmembers,allowshrinking class * {
     native <methods>;
 }

#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
#保持自定义控件类不被混淆
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
#保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
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
#保持枚举 enum 类不被混淆 如果混淆报错，建议直接使用上面的 -keepclassmembers class * implements java.io.Serializable即可
#-keepclassmembers enum * {
#  public static **[] values();
#  public static ** valueOf(java.lang.String);
#}
-keepclassmembers class * {
    public void *ButtonClicked(android.view.View);
}
#不混淆资源类
-keepclassmembers class **.R$* {
    public static <fields>;
}
#避免混淆泛型 如果混淆报错建议关掉
#–keepattributes Signature

#不混淆H5交互
-keepattributes *JavascriptInterface*
 #ClassName是类名，H5_Object是与javascript相交互的object，建议以内部类形式书写
-keepclassmembers   class **.ClassName$H5_Object{*;}

#如果使用有Gson，则添加以下配置
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }

#友盟推送
-dontwarn com.taobao.**
-dontwarn anet.channel.**
-dontwarn anetwork.channel.**
-dontwarn org.android.**
-dontwarn org.apache.thrift.**
-dontwarn com.xiaomi.**
-dontwarn com.huawei.**

-keep class com.taobao.** {*;}
-keep class org.android.** {*;}
-keep class anet.channel.** {*;}
-keep class com.umeng.** {*;}
-keep class com.xiaomi.** {*;}
-keep class com.huawei.** {*;}
-keep class org.apache.thrift.** {*;}

-keep public class **.R$*{
   public static final int *;
}