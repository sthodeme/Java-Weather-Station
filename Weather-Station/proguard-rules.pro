# Keep your application's main class
-keep public class com.itq25.finalproject.StartWS {
    public static void main(java.lang.String[]);
}

# Preserve logging classes
-keep class org.apache.logging.** { *; }

# Preserve Gson and JSON
-keep class com.google.gson.** { *; }
-keep class org.json.** { *; }

# Kotlin support (used by some dependencies)
-keep class kotlin.** { *; }

# Keep annotations
-keepattributes *Annotation*



