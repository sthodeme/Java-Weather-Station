-injars target/weather-station-1.0-SNAPSHOT-shaded.jar
-outjars target/weather-station-obfuscated-sreeram.jar

-libraryjars <java.home>/lib/modules

-keep public class com.itq25.finalproject.StartWS {
    public static void main(java.lang.String[]);
}

-keepattributes Signature
-keepattributes *Annotation*
-keep class com.google.gson.** { *; }
-keep class org.json.** { *; }

-dontwarn
-overloadaggressively

-printmapping target/proguard_map.txt
