/*    */ package org.xml.sax.helpers;
/*    */ 
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.lang.reflect.Method;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class NewInstance
/*    */ {
/*    */   static Object newInstance(ClassLoader paramClassLoader, String paramString) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
/*    */     Class<?> clazz;
/* 46 */     if (paramClassLoader == null) {
/* 47 */       clazz = Class.forName(paramString);
/*    */     } else {
/* 49 */       clazz = paramClassLoader.loadClass(paramString);
/*    */     } 
/* 51 */     return clazz.newInstance();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static ClassLoader getClassLoader() {
/* 60 */     Method method = null;
/*    */     
/*    */     try {
/* 63 */       method = Thread.class.getMethod("getContextClassLoader", (Class[])null);
/* 64 */     } catch (NoSuchMethodException noSuchMethodException) {
/*    */       
/* 66 */       return NewInstance.class.getClassLoader();
/*    */     } 
/*    */     
/*    */     try {
/* 70 */       return (ClassLoader)method.invoke(Thread.currentThread(), (Object[])null);
/* 71 */     } catch (IllegalAccessException illegalAccessException) {
/*    */       
/* 73 */       throw new UnknownError(illegalAccessException.getMessage());
/* 74 */     } catch (InvocationTargetException invocationTargetException) {
/*    */       
/* 76 */       throw new UnknownError(invocationTargetException.getMessage());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\xml\sax\helpers\NewInstance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */