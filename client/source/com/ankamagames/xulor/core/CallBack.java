/*     */ package com.ankamagames.xulor.core;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
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
/*     */ public class CallBack
/*     */ {
/*  25 */   private static Pattern m_funcPattern = Pattern.compile("(^([a-zA-Z.]+):)?([a-zA-Z]+){1}(\\((([_a-zA-Z0-9]+([,]?[_a-zA-Z0-9]+)*)*)\\))?");
/*     */   
/*     */   public static final String FUNCTION_SEPARATOR = ";";
/*  28 */   protected static Logger m_logger = Logger.getLogger(CallBack.class);
/*     */   
/*     */   protected String[] m_funcs;
/*  31 */   protected String m_func = null;
/*  32 */   protected ElementMap m_elementMap = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCallBackFunc(String func, ElementMap elementMap) {
/*  42 */     this.m_func = func;
/*  43 */     this.m_funcs = func.split(";");
/*  44 */     this.m_elementMap = elementMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setElementMap(ElementMap elementMap) {
/*  51 */     this.m_elementMap = elementMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object invokeCallBack() {
/*  58 */     Object result = null; byte b; int i; String[] arrayOfString;
/*  59 */     for (i = (arrayOfString = this.m_funcs).length, b = 0; b < i; ) { String func = arrayOfString[b];
/*  60 */       Matcher matcher = m_funcPattern.matcher(func);
/*  61 */       if (matcher.matches()) {
/*     */ 
/*     */         
/*  64 */         String parameters[], packageName = matcher.group(2);
/*  65 */         String methodName = matcher.group(3);
/*  66 */         String parametersString = matcher.group(5);
/*     */         
/*  68 */         if (parametersString != null) {
/*  69 */           parameters = matcher.group(5).split(",");
/*     */         } else {
/*  71 */           parameters = new String[0];
/*     */         } 
/*     */         
/*  74 */         result = processCallBack(packageName, methodName, parameters);
/*     */       } else {
/*     */         
/*  77 */         m_logger.error("Erreur de syntaxe : '" + func + "' n'est pas du type 'package:method(param1,param2,...)'.");
/*     */       }  b++; }
/*     */     
/*  80 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object processCallBack(String packageName, String methodName, String[] parameters) {
/*  90 */     List<Class<?>> parameterTypes = new ArrayList<Class<?>>();
/*  91 */     List<Object> args = new ArrayList();
/*     */     
/*  93 */     fillParameters(parameters, parameterTypes, args);
/*     */ 
/*     */     
/*  96 */     Class<?> actionClass = Xulor.getInstance().getActionClass(packageName);
/*  97 */     if (actionClass != null) {
/*  98 */       Method[] methods = actionClass.getMethods(); byte b; int i; Method[] arrayOfMethod1;
/*  99 */       for (i = (arrayOfMethod1 = methods).length, b = 0; b < i; ) { Method method = arrayOfMethod1[b];
/* 100 */         if (method.getName().equals(methodName)) {
/* 101 */           boolean isValid = false;
/* 102 */           Class[] methodParameterTypes = method.getParameterTypes();
/* 103 */           if (methodParameterTypes.length == parameterTypes.size()) {
/* 104 */             for (int j = 0; j < methodParameterTypes.length; j++) {
/* 105 */               isValid = methodParameterTypes[j].isAssignableFrom(parameterTypes.get(j));
/* 106 */               if (!isValid) {
/*     */                 break;
/*     */               }
/*     */             } 
/*     */           }
/* 111 */           if (isValid)
/*     */             try {
/* 113 */               return method.invoke(null, args.toArray());
/* 114 */             } catch (Exception e) {
/* 115 */               m_logger.error("Erreur lors du invokeCallBack", e);
/*     */               
/* 117 */               return null;
/*     */             }  
/*     */         } 
/*     */         b++; }
/*     */       
/* 122 */       m_logger.error("La méthode '" + ((packageName != null) ? (String.valueOf(packageName) + ":") : "") + methodName + "(" + typeArrayToString(parameterTypes) + ")' est inconnue !");
/*     */     } 
/* 124 */     return null;
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
/*     */   protected void fillParameters(String[] parameters, List<Class<?>> parameterTypes, List<Object> args) {
/* 138 */     for (int i = 0; i < parameters.length; i++) {
/*     */       
/* 140 */       IElement element = this.m_elementMap.getElement(parameters[i]);
/* 141 */       if (element != null) {
/* 142 */         Object value = element.getElementValue();
/* 143 */         if (value != null) {
/* 144 */           parameterTypes.add(value.getClass());
/* 145 */           args.add(value);
/*     */         }
/*     */       
/* 148 */       } else if (parameters[i].length() != 0) {
/*     */ 
/*     */         
/* 151 */         parameterTypes.add(String.class);
/* 152 */         args.add(parameters[i]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String typeArrayToString(List<Class<?>> types) {
/* 164 */     StringBuilder builder = new StringBuilder();
/* 165 */     for (Class<?> type : types) {
/* 166 */       builder.append(",").append(type.getName());
/*     */     }
/* 168 */     return builder.toString().substring(1);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\CallBack.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */