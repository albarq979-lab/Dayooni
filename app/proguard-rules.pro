# Dayooni application ProGuard/R8 rules.
# Gson uses reflection for JSON import/export; keep model fields.
-keep class com.dayooni.app.data.** { *; }
-keep class com.google.gson.** { *; }
