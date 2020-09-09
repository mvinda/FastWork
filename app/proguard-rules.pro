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
-dontwarn rx.**
-keepclasseswithmembers class * {
    ... *JNI*(...);
}
-keepclasseswithmembernames class * {
	... *JRI*(...);
}
-keep class **JNI* {*;}

#################### start region keep class config
-keep,allowobfuscation @interface com.example.fastwork.component.proguard.Keep
-keep,allowobfuscation @interface com.example.fastwork.component.proguard.KeepClassName
#仅保留类名
-keep @com.example.fastwork.component.proguard.KeepClassName class *
#如果Keep作用在“类”，则保留类名、属性、方法
-keep @com.example.fastwork.component.proguard.Keep class *{*;}
#如果Keep作用在“类的方法”，则保留指定的方法，其它不保留，类名如果没有配置Keep也不保留
-keepclassmembers class * {
    @com.example.fastwork.component.proguard.Keep *;
}
#################### end region keep class config

-assumenosideeffects class android.util.Log{
    public static *** v(...);
    public static *** i(...);
    public static *** d(...);
    public static *** w(...);
    public static *** e(...);
}

################### start butterknife
# Retain generated class which implement Unbinder.
-keep public class * implements butterknife.Unbinder { public <init>(**, android.view.View); }
# Prevent obfuscation of types which use ButterKnife annotations since the simple name
# is used to reflectively look up the generated ViewBinding.
-keep class butterknife.*
-keepclasseswithmembernames class * { @butterknife.* <methods>; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }
################### end butterknife

-keep class com.example.fastwork.model.** {*;}


-ignorewarnings