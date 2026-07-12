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
/*    */   public Object convert(Class type, String value)
/*    */   {
/* 27 */     if ((Boolean.TYPE.equals(type)) || (Boolean.class.equals(type)))
/* 28 */       return Boolean.valueOf(com.ankamagames.xulor.util.PrimitiveConverter.getBoolean(value));
/* 29 */     if ((Integer.TYPE.equals(type)) || (Integer.class.equals(type)))
/* 30 */       return Integer.valueOf(com.ankamagames.xulor.util.PrimitiveConverter.getInteger(value));
/* 31 */     if ((Long.TYPE.equals(type)) || (Long.class.equals(type)))
/* 32 */       return Long.valueOf(com.ankamagames.xulor.util.PrimitiveConverter.getLong(value));
/* 33 */     if ((Float.TYPE.equals(type)) || (Float.class.equals(type)))
/* 34 */       return Float.valueOf(com.ankamagames.xulor.util.PrimitiveConverter.getFloat(value));
/* 35 */     if ((Double.TYPE.equals(type)) || (Double.class.equals(type)))
/* 36 */       return Double.valueOf(com.ankamagames.xulor.util.PrimitiveConverter.getDouble(value));
/* 37 */     if ((Byte.TYPE.equals(type)) || (Byte.class.equals(type)))
/* 38 */       return Byte.valueOf(com.ankamagames.xulor.util.PrimitiveConverter.getByte(value));
/* 39 */     if ((Short.TYPE.equals(type)) || (Short.class.equals(type))) {
/* 40 */       return Short.valueOf(com.ankamagames.xulor.util.PrimitiveConverter.getShort(value));
/*    */     }
/* 42 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Class convertsTo()
/*    */   {
/* 51 */     return TEMPLATE;
/*    */   }
/*    */   
/*    */   public static Object convertJDOMAttribute(Class type, Attribute value) {
/* 55 */     Attribute a = (Attribute)value.clone();
/* 56 */     Object obj = null;
/*    */     try
/*    */     {
/* 59 */       if (Boolean.TYPE.equals(type)) {
/* 60 */         obj = new Boolean(a.getBooleanValue());
/* 61 */       } else if (Integer.TYPE.equals(type)) {
/* 62 */         obj = new Integer(a.getIntValue());
/* 63 */       } else if (Long.TYPE.equals(type)) {
/* 64 */         obj = new Long(a.getLongValue());
/* 65 */       } else if (Float.TYPE.equals(type)) {
/* 66 */         obj = new Float(a.getFloatValue());
/* 67 */       } else if (Double.TYPE.equals(type)) {
/* 68 */         obj = new Double(a.getDoubleValue());
/*    */       }
/*    */     } catch (DataConversionException e) {
/* 71 */       e.printStackTrace();
/*    */     }
/*    */     
/* 74 */     return obj;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\converter\PrimitiveConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */