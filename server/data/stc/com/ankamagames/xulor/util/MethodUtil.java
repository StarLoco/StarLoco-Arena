/*     */ package com.ankamagames.xulor.util;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.Binding;
/*     */ import com.ankamagames.xulor.core.Converter;
/*     */ import com.ankamagames.xulor.core.ConverterLibrary;
/*     */ import com.ankamagames.xulor.core.Factory;
/*     */ import com.ankamagames.xulor.core.renderer.ResultProvider;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public class MethodUtil
/*     */ {
/*  26 */   private static Logger m_logger = Logger.getLogger(MethodUtil.class);
/*     */   
/*     */   public static void castInvoke(Method method, Object invoker, Object[] values) throws Exception {
/*  29 */     if ((method == null) || (values == null)) {
/*  30 */       return;
/*     */     }
/*  32 */     Class[] classes = method.getParameterTypes();
/*  33 */     if (classes.length != values.length) {
/*  34 */       throw new Exception("nombre de paramètres attendus : " + classes.length + ". Trouvés : " + values.length);
/*     */     }
/*     */     
/*  37 */     ArrayList<Object> parameters = new ArrayList();
/*  38 */     for (int i = 0; i < classes.length; i++) {
/*  39 */       Class type = classes[i];
/*  40 */       Object value = values[i];
/*     */       
/*  42 */       if (type.isPrimitive()) {
/*  43 */         type = PrimitiveConverter.getClassFromPrimitive(type);
/*     */       }
/*     */       
/*  46 */       if ((value == null) || ((value != null) && (type.isAssignableFrom(value.getClass())))) {
/*  47 */         parameters.add(value);
/*  48 */       } else if (type.equals(String.class)) {
/*  49 */         parameters.add(PrimitiveConverter.getString(value));
/*  50 */       } else if (type.equals(Boolean.class)) {
/*  51 */         parameters.add(Boolean.valueOf(PrimitiveConverter.getBoolean(value)));
/*  52 */       } else if (type.equals(Integer.class)) {
/*  53 */         parameters.add(Integer.valueOf(PrimitiveConverter.getInteger(value)));
/*  54 */       } else if (type.equals(Float.class)) {
/*  55 */         parameters.add(Float.valueOf(PrimitiveConverter.getFloat(value)));
/*  56 */       } else if (type.equals(Double.class)) {
/*  57 */         parameters.add(Double.valueOf(PrimitiveConverter.getDouble(value)));
/*  58 */       } else if (type.equals(Long.class)) {
/*  59 */         parameters.add(Long.valueOf(PrimitiveConverter.getLong(value)));
/*  60 */       } else if (value.getClass().equals(String.class)) {
/*  61 */         ConverterLibrary cvtlib = Xulor.getInstance().getBinding().getConverterLibrary();
/*  62 */         Converter cvt = cvtlib.getConverter(type);
/*  63 */         parameters.add(cvt.convert(type, (String)value));
/*     */       } else {
/*  65 */         throw new Exception("Impossible de convertir la valeur donnée");
/*     */       }
/*     */     }
/*     */     try
/*     */     {
/*  70 */       method.invoke(invoker, parameters.toArray());
/*     */     } catch (IllegalArgumentException e) {
/*  72 */       StringBuilder sb = new StringBuilder();
/*  73 */       sb.append("IllegalArgumentException : method=" + method + ", parametres=");
/*  74 */       for (int i = 0; i < parameters.size(); i++) {
/*  75 */         sb.append(parameters.get(i));
/*  76 */         if (i == parameters.size() - 1) break;
/*  77 */         sb.append(", ");
/*     */       }
/*  79 */       m_logger.error(sb);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void castInvokeWithItem(Method method, Object invoker, Item item, String field, ResultProvider resultProvider) throws Exception {
/*  84 */     if (method == null) {
/*  85 */       return;
/*     */     }
/*  87 */     Class[] classes = method.getParameterTypes();
/*  88 */     if (classes.length != 1) {
/*  89 */       throw new Exception("La méthode prend " + classes.length + " paramètres");
/*     */     }
/*     */     
/*  92 */     Object value = null;
/*     */     
/*  94 */     Object retValue = item != null ? item.getAssociatedValue(field) : null;
/*  95 */     if (((retValue == null) || (resultProvider != null)) && (item != null)) {
/*  96 */       if (field != null) {
/*  97 */         value = item.getFieldValue(field);
/*     */       } else {
/*  99 */         value = item.getValue();
/*     */       }
/*     */     }
/*     */     
/* 103 */     ArrayList<Object> parameters = new ArrayList();
/* 104 */     Class type = classes[0];
/*     */     
/* 106 */     if (resultProvider != null) {
/* 107 */       retValue = resultProvider.getResult(value);
/*     */     }
/*     */     
/* 110 */     if (type.isPrimitive()) {
/* 111 */       type = PrimitiveConverter.getClassFromPrimitive(type);
/*     */     }
/*     */     
/*     */ 
/* 115 */     if ((retValue != null) && (type.isAssignableFrom(retValue.getClass()))) {
/* 116 */       parameters.add(retValue);
/*     */     } else {
/* 118 */       if ((value == null) || ((value != null) && (type.isAssignableFrom(value.getClass())))) {
/* 119 */         parameters.add(value);
/* 120 */       } else if (type.equals(String.class)) {
/* 121 */         parameters.add(PrimitiveConverter.getString(value));
/* 122 */       } else if (type.equals(Boolean.class)) {
/* 123 */         parameters.add(Boolean.valueOf(PrimitiveConverter.getBoolean(value)));
/* 124 */       } else if (type.equals(Integer.class)) {
/* 125 */         parameters.add(Integer.valueOf(PrimitiveConverter.getInteger(value)));
/* 126 */       } else if (type.equals(Float.class)) {
/* 127 */         parameters.add(Float.valueOf(PrimitiveConverter.getFloat(value)));
/* 128 */       } else if (type.equals(Double.class)) {
/* 129 */         parameters.add(Double.valueOf(PrimitiveConverter.getDouble(value)));
/* 130 */       } else if (type.equals(Long.class)) {
/* 131 */         parameters.add(Long.valueOf(PrimitiveConverter.getLong(value)));
/* 132 */       } else if (value.getClass().equals(String.class)) {
/* 133 */         ConverterLibrary cvtlib = Xulor.getInstance().getBinding().getConverterLibrary();
/* 134 */         Converter cvt = cvtlib.getConverter(type);
/* 135 */         retValue = cvt.convert(type, (String)value);
/* 136 */         parameters.add(retValue);
/*     */       } else {
/* 138 */         throw new Exception("Impossible de convertir la valeur donnée (type = " + type + ")");
/*     */       }
/*     */       
/* 141 */       if (item != null) {
/* 142 */         item.setAssociatedValue(field, parameters.get(0));
/*     */       }
/*     */     }
/*     */     try
/*     */     {
/* 147 */       method.invoke(invoker, parameters.toArray());
/*     */     } catch (IllegalArgumentException e) {
/* 149 */       StringBuilder sb = new StringBuilder();
/* 150 */       sb.append("IllegalArgumentException : method=" + method + ", parametres=");
/* 151 */       for (int i = 0; i < parameters.size(); i++) {
/* 152 */         sb.append(parameters.get(i));
/* 153 */         if (i == parameters.size() - 1) break;
/* 154 */         sb.append(", ");
/*     */       }
/* 156 */       m_logger.error(sb);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void castInvokeWithItem(Factory factory, String attributeName, Object invoker, Item item, String field, ResultProvider resultProvider)
/*     */     throws Exception
/*     */   {
/* 163 */     Object value = null;
/* 164 */     Method method = null;
/*     */     
/* 166 */     Object retValue = item != null ? item.getAssociatedValue(field) : null;
/* 167 */     if ((retValue == null) && (item != null)) {
/* 168 */       if (field != null) {
/* 169 */         value = item.getFieldValue(field);
/*     */       } else {
/* 171 */         value = item.getValue();
/*     */       }
/*     */     }
/*     */     
/* 175 */     if (resultProvider != null) {
/* 176 */       retValue = resultProvider.getResult(value);
/*     */     }
/*     */     
/*     */ 
/* 180 */     if (retValue != null) {
/* 181 */       method = factory.guessSetter(attributeName, retValue.getClass());
/*     */     } else {
/* 183 */       method = factory.guessSetter(attributeName, value.getClass());
/*     */     }
/* 185 */     if (method == null) {
/* 186 */       return;
/*     */     }
/* 188 */     Class[] classes = method.getParameterTypes();
/* 189 */     if (classes.length != 1) {
/* 190 */       throw new Exception("La méthode prend " + classes.length + " paramètres");
/*     */     }
/*     */     
/* 193 */     ArrayList<Object> parameters = new ArrayList();
/* 194 */     Class type = classes[0];
/*     */     
/* 196 */     if (type.isPrimitive()) {
/* 197 */       type = PrimitiveConverter.getClassFromPrimitive(type);
/*     */     }
/*     */     
/*     */ 
/* 201 */     if ((retValue != null) && (type.isAssignableFrom(retValue.getClass()))) {
/* 202 */       parameters.add(retValue);
/*     */     } else {
/* 204 */       if ((value == null) || ((value != null) && (type.isAssignableFrom(value.getClass())))) {
/* 205 */         parameters.add(value);
/* 206 */       } else if (type.equals(String.class)) {
/* 207 */         parameters.add(PrimitiveConverter.getString(value));
/* 208 */       } else if (type.equals(Boolean.class)) {
/* 209 */         parameters.add(Boolean.valueOf(PrimitiveConverter.getBoolean(value)));
/* 210 */       } else if (type.equals(Integer.class)) {
/* 211 */         parameters.add(Integer.valueOf(PrimitiveConverter.getInteger(value)));
/* 212 */       } else if (type.equals(Float.class)) {
/* 213 */         parameters.add(Float.valueOf(PrimitiveConverter.getFloat(value)));
/* 214 */       } else if (type.equals(Double.class)) {
/* 215 */         parameters.add(Double.valueOf(PrimitiveConverter.getDouble(value)));
/* 216 */       } else if (type.equals(Long.class)) {
/* 217 */         parameters.add(Long.valueOf(PrimitiveConverter.getLong(value)));
/* 218 */       } else if (value.getClass().equals(String.class)) {
/* 219 */         ConverterLibrary cvtlib = Xulor.getInstance().getBinding().getConverterLibrary();
/* 220 */         Converter cvt = cvtlib.getConverter(type);
/* 221 */         retValue = cvt.convert(type, (String)value);
/* 222 */         parameters.add(retValue);
/*     */       } else {
/* 224 */         throw new Exception("Impossible de convertir la valeur donnée (type = " + type + ")");
/*     */       }
/*     */       
/* 227 */       if (item != null) {
/* 228 */         item.setAssociatedValue(field, parameters.get(0));
/*     */       }
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 234 */       method.invoke(invoker, parameters.toArray());
/*     */     } catch (IllegalArgumentException e) {
/* 236 */       StringBuilder sb = new StringBuilder();
/* 237 */       sb.append("IllegalArgumentException : method=" + method + ", parametres=");
/* 238 */       for (int i = 0; i < parameters.size(); i++) {
/* 239 */         sb.append(parameters.get(i));
/* 240 */         if (i == parameters.size() - 1) break;
/* 241 */         sb.append(", ");
/*     */       }
/* 243 */       m_logger.error(sb);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\util\MethodUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */