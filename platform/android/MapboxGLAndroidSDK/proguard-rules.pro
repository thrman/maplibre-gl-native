# By default, the flags in this file are appended to flags specified
# in ../sdk/tools/proguard/proguard-android.txt,
# contents of this file will be appended into proguard-android.txt
-keepattributes Signature, *Annotation*, EnclosingMethod

# Reflection on classes from native code
-keep class com.google.gson.JsonArray { *; }
-keep class com.google.gson.JsonElement { *; }
-keep class com.google.gson.JsonObject { *; }
-keep class com.google.gson.JsonPrimitive { *; }
-dontnote com.google.gson.**

# dontnote for keeps the entry point x but not the descriptor class y
-dontnote com.gomap.sdk.maps.MapboxMap$OnFpsChangedListener
-dontnote com.gomap.sdk.style.layers.PropertyValue
-dontnote com.gomap.sdk.maps.MapboxMap
-dontnote com.gomap.sdk.maps.MapboxMapOptions
-dontnote com.gomap.sdk.log.LoggerDefinition
-dontnote com.gomap.sdk.location.engine.LocationEnginePriority

# config for okhttp 3.11.0, https://github.com/square/okhttp/pull/3354
-dontwarn javax.annotation.**
-dontnote okhttp3.internal.**
-dontwarn org.codehaus.**

# config for mapbox-sdk-geojson:3.0.1
-keep class com.gomap.geojson.** { *; }
-dontwarn com.google.auto.value.**

# config for additional notes
-dontnote org.robolectric.Robolectric
-dontnote libcore.io.Memory
-dontnote com.google.protobuf.**
-dontnote android.net.**
-dontnote org.apache.http.**

# config for mapbox-sdk-services
# while we don't include this dependency directly
# a large amount of users combine it with our SDK
# we aren't able to provide a proguard config in that project (jar vs aar)
-dontwarn com.sun.xml.internal.ws.spi.db.*