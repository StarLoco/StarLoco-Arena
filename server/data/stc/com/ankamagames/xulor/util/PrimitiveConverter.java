/*     */ package com.ankamagames.xulor.util;
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
/*     */ public class PrimitiveConverter
/*     */ {
/*     */   public static String getString(Object value)
/*     */   {
/*  20 */     if ((value instanceof String)) {
/*  21 */       return (String)value;
/*     */     }
/*  23 */     return String.valueOf(value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static boolean getBoolean(Object value)
/*     */   {
/*  30 */     if ((value instanceof Boolean))
/*  31 */       return ((Boolean)value).booleanValue();
/*  32 */     if ((value instanceof String)) {
/*  33 */       return Boolean.valueOf((String)value).booleanValue();
/*     */     }
/*  35 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static int getInteger(Object value)
/*     */   {
/*  42 */     if ((value instanceof Number))
/*  43 */       return ((Number)value).intValue();
/*  44 */     if ((value instanceof String)) {
/*  45 */       return Integer.valueOf((String)value).intValue();
/*     */     }
/*  47 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public static double getDouble(Object value)
/*     */   {
/*     */     try
/*     */     {
/*  55 */       return ((Number)value).doubleValue();
/*     */     } catch (ClassCastException exception) {
/*  57 */       if ((value instanceof String))
/*  58 */         return Double.valueOf((String)value).doubleValue();
/*     */     }
/*  60 */     return 0.0D;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static float getFloat(Object value)
/*     */   {
/*  68 */     if ((value instanceof Number))
/*  69 */       return ((Number)value).floatValue();
/*  70 */     if ((value instanceof String)) {
/*  71 */       return Float.valueOf((String)value).floatValue();
/*     */     }
/*  73 */     return 0.0F;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static long getLong(Object value)
/*     */   {
/*  80 */     if ((value instanceof Number))
/*  81 */       return ((Number)value).longValue();
/*  82 */     if ((value instanceof String)) {
/*  83 */       return Long.valueOf((String)value).longValue();
/*     */     }
/*  85 */     return 0L;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static byte getByte(Object value)
/*     */   {
/*  92 */     if ((value instanceof Number))
/*  93 */       return ((Number)value).byteValue();
/*  94 */     if ((value instanceof String)) {
/*  95 */       return Byte.valueOf((String)value).byteValue();
/*     */     }
/*  97 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static short getShort(Object value)
/*     */   {
/* 104 */     if ((value instanceof Number))
/* 105 */       return ((Number)value).shortValue();
/* 106 */     if ((value instanceof String)) {
/* 107 */       return Short.valueOf((String)value).shortValue();
/*     */     }
/* 109 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Class getClassFromPrimitive(Class primitive)
/*     */   {
/* 119 */     if (primitive.equals(Boolean.TYPE))
/* 120 */       return Boolean.class;
/* 121 */     if (primitive.equals(Double.TYPE))
/* 122 */       return Double.class;
/* 123 */     if (primitive.equals(Float.TYPE))
/* 124 */       return Float.class;
/* 125 */     if (primitive.equals(Short.TYPE))
/* 126 */       return Short.class;
/* 127 */     if (primitive.equals(Integer.TYPE))
/* 128 */       return Integer.class;
/* 129 */     if (primitive.equals(Long.TYPE))
/* 130 */       return Long.class;
/* 131 */     if (primitive.equals(Character.TYPE))
/* 132 */       return Character.class;
/* 133 */     if (primitive.equals(Byte.TYPE))
/* 134 */       return Byte.class;
/* 135 */     if (primitive.equals(Void.TYPE)) {
/* 136 */       return Void.class;
/*     */     }
/* 138 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\util\PrimitiveConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */