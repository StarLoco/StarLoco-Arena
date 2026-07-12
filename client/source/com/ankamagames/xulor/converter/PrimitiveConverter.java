/*    */ package com.ankamagames.xulor.converter;
/*    */ 
/*    */ import com.ankamagames.xulor.core.Converter;
/*    */ import org.jdom.Attribute;
/*    */ import org.jdom.DataConversionException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PrimitiveConverter
/*    */   implements Converter
/*    */ {
/* 18 */   public static final Class TEMPLATE = Object.class;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object convert(Class type, String value) {
/* 27 */     if (boolean.class.equals(type) || Boolean.class.equals(type))
/* 28 */       return Boolean.valueOf(com.ankamagames.xulor.util.PrimitiveConverter.getBoolean(value)); 
/* 29 */     if (int.class.equals(type) || Integer.class.equals(type))
/* 30 */       return Integer.valueOf(com.ankamagames.xulor.util.PrimitiveConverter.getInteger(value)); 
/* 31 */     if (long.class.equals(type) || Long.class.equals(type))
/* 32 */       return Long.valueOf(com.ankamagames.xulor.util.PrimitiveConverter.getLong(value)); 
/* 33 */     if (float.class.equals(type) || Float.class.equals(type))
/* 34 */       return Float.valueOf(com.ankamagames.xulor.util.PrimitiveConverter.getFloat(value)); 
/* 35 */     if (double.class.equals(type) || Double.class.equals(type))
/* 36 */       return Double.valueOf(com.ankamagames.xulor.util.PrimitiveConverter.getDouble(value)); 
/* 37 */     if (byte.class.equals(type) || Byte.class.equals(type))
/* 38 */       return Byte.valueOf(com.ankamagames.xulor.util.PrimitiveConverter.getByte(value)); 
/* 39 */     if (short.class.equals(type) || Short.class.equals(type)) {
/* 40 */       return Short.valueOf(com.ankamagames.xulor.util.PrimitiveConverter.getShort(value));
/*    */     }
/* 42 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Class convertsTo() {
/* 51 */     return TEMPLATE;
/*    */   }
/*    */   
/*    */   public static Object convertJDOMAttribute(Class type, Attribute value) {
/* 55 */     Attribute a = (Attribute)value.clone();
/* 56 */     Object obj = null;
/*    */     
/*    */     try {
/* 59 */       if (boolean.class.equals(type)) {
/* 60 */         obj = new Boolean(a.getBooleanValue());
/* 61 */       } else if (int.class.equals(type)) {
/* 62 */         obj = new Integer(a.getIntValue());
/* 63 */       } else if (long.class.equals(type)) {
/* 64 */         obj = new Long(a.getLongValue());
/* 65 */       } else if (float.class.equals(type)) {
/* 66 */         obj = new Float(a.getFloatValue());
/* 67 */       } else if (double.class.equals(type)) {
/* 68 */         obj = new Double(a.getDoubleValue());
/*    */       } 
/* 70 */     } catch (DataConversionException e) {
/* 71 */       e.printStackTrace();
/*    */     } 
/*    */     
/* 74 */     return obj;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\converter\PrimitiveConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */