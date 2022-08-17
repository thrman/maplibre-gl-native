/*     */ package com.mapbox.geojson.internal.typeadapters;
/*     */ 
/*     */ import androidx.annotation.Keep;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonPrimitive;
/*     */ import com.google.gson.TypeAdapter;
/*     */ import com.google.gson.TypeAdapterFactory;
/*     */ import com.google.gson.internal.Streams;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.google.gson.stream.JsonWriter;
/*     */ import java.io.IOException;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Keep
/*     */ public final class RuntimeTypeAdapterFactory<T>
/*     */   implements TypeAdapterFactory
/*     */ {
/*     */   private final Class<?> baseType;
/*     */   private final String typeFieldName;
/* 134 */   private final Map<String, Class<?>> labelToSubtype = new LinkedHashMap<>();
/* 135 */   private final Map<Class<?>, String> subtypeToLabel = new LinkedHashMap<>();
/*     */   private final boolean maintainType;
/*     */   
/*     */   private RuntimeTypeAdapterFactory(Class<?> baseType, String typeFieldName, boolean maintainType) {
/* 139 */     if (typeFieldName == null || baseType == null) {
/* 140 */       throw new NullPointerException();
/*     */     }
/* 142 */     this.baseType = baseType;
/* 143 */     this.typeFieldName = typeFieldName;
/* 144 */     this.maintainType = maintainType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> RuntimeTypeAdapterFactory<T> of(Class<T> baseType, String typeFieldName, boolean maintainType) {
/* 160 */     return new RuntimeTypeAdapterFactory<>(baseType, typeFieldName, maintainType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> RuntimeTypeAdapterFactory<T> of(Class<T> baseType, String typeFieldName) {
/* 172 */     return new RuntimeTypeAdapterFactory<>(baseType, typeFieldName, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> RuntimeTypeAdapterFactory<T> of(Class<T> baseType) {
/* 183 */     return new RuntimeTypeAdapterFactory<>(baseType, "type", false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RuntimeTypeAdapterFactory<T> registerSubtype(Class<? extends T> type, String label) {
/* 196 */     if (type == null || label == null) {
/* 197 */       throw new NullPointerException();
/*     */     }
/* 199 */     if (this.subtypeToLabel.containsKey(type) || this.labelToSubtype.containsKey(label)) {
/* 200 */       throw new IllegalArgumentException("types and labels must be unique");
/*     */     }
/* 202 */     this.labelToSubtype.put(label, type);
/* 203 */     this.subtypeToLabel.put(type, label);
/* 204 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RuntimeTypeAdapterFactory<T> registerSubtype(Class<? extends T> type) {
/* 216 */     return registerSubtype(type, type.getSimpleName());
/*     */   }
/*     */ 
/*     */   
/*     */   public <R> TypeAdapter<R> create(Gson gson, TypeToken<R> type) {
/* 221 */     if (type.getRawType() != this.baseType) {
/* 222 */       return null;
/*     */     }
/*     */     
/* 225 */     final Map<String, TypeAdapter<?>> labelToDelegate = new LinkedHashMap<>();
/*     */     
/* 227 */     final Map<Class<?>, TypeAdapter<?>> subtypeToDelegate = new LinkedHashMap<>();
/*     */     
/* 229 */     for (Map.Entry<String, Class<?>> entry : this.labelToSubtype.entrySet()) {
/*     */       
/* 231 */       TypeAdapter<?> delegate = gson.getDelegateAdapter(this, TypeToken.get(entry.getValue()));
/* 232 */       labelToDelegate.put(entry.getKey(), delegate);
/* 233 */       subtypeToDelegate.put(entry.getValue(), delegate);
/*     */     } 
/*     */     
/* 236 */     return (new TypeAdapter<R>() {
/*     */         public R read(JsonReader in) throws IOException {
/* 238 */           JsonElement labelJsonElement, jsonElement = Streams.parse(in);
/*     */           
/* 240 */           if (RuntimeTypeAdapterFactory.this.maintainType) {
/* 241 */             labelJsonElement = jsonElement.getAsJsonObject().get(RuntimeTypeAdapterFactory.this.typeFieldName);
/*     */           } else {
/* 243 */             labelJsonElement = jsonElement.getAsJsonObject().remove(RuntimeTypeAdapterFactory.this.typeFieldName);
/*     */           } 
/*     */           
/* 246 */           if (labelJsonElement == null) {
/* 247 */             throw new JsonParseException("cannot deserialize " + RuntimeTypeAdapterFactory.this.baseType + " because it does not define a field named " + RuntimeTypeAdapterFactory.this
/* 248 */                 .typeFieldName);
/*     */           }
/* 250 */           String label = labelJsonElement.getAsString();
/*     */           
/* 252 */           TypeAdapter<R> delegate = (TypeAdapter<R>)labelToDelegate.get(label);
/* 253 */           if (delegate == null) {
/* 254 */             throw new JsonParseException("cannot deserialize " + RuntimeTypeAdapterFactory.this.baseType + " subtype named " + label + "; did you forget to register a subtype?");
/*     */           }
/*     */           
/* 257 */           return (R)delegate.fromJsonTree(jsonElement);
/*     */         }
/*     */         
/*     */         public void write(JsonWriter out, R value) throws IOException {
/* 261 */           Class<?> srcType = value.getClass();
/*     */           
/* 263 */           TypeAdapter<R> delegate = (TypeAdapter<R>)subtypeToDelegate.get(srcType);
/* 264 */           if (delegate == null) {
/* 265 */             throw new JsonParseException("cannot serialize " + srcType.getName() + "; did you forget to register a subtype?");
/*     */           }
/*     */           
/* 268 */           JsonObject jsonObject = delegate.toJsonTree(value).getAsJsonObject();
/*     */           
/* 270 */           if (RuntimeTypeAdapterFactory.this.maintainType) {
/* 271 */             Streams.write((JsonElement)jsonObject, out);
/*     */             
/*     */             return;
/*     */           } 
/* 275 */           JsonObject clone = new JsonObject();
/*     */           
/* 277 */           if (jsonObject.has(RuntimeTypeAdapterFactory.this.typeFieldName)) {
/* 278 */             throw new JsonParseException("cannot serialize " + srcType.getName() + " because it already defines a field named " + RuntimeTypeAdapterFactory.this
/* 279 */                 .typeFieldName);
/*     */           }
/* 281 */           String label = (String)RuntimeTypeAdapterFactory.this.subtypeToLabel.get(srcType);
/* 282 */           clone.add(RuntimeTypeAdapterFactory.this.typeFieldName, (JsonElement)new JsonPrimitive(label));
/*     */           
/* 284 */           for (Map.Entry<String, JsonElement> e : (Iterable<Map.Entry<String, JsonElement>>)jsonObject.entrySet()) {
/* 285 */             clone.add(e.getKey(), e.getValue());
/*     */           }
/* 287 */           Streams.write((JsonElement)clone, out);
/*     */         }
/* 289 */       }).nullSafe();
/*     */   }
/*     */ }


/* Location:              C:\Users\jd\Desktop\android-sdk-geojson-5.9.0.jar!\com\mapbox\geojson\internal\typeadapters\RuntimeTypeAdapterFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */