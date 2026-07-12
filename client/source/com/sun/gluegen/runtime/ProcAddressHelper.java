/*    */ package com.sun.gluegen.runtime;
/*    */ 
/*    */ import java.lang.reflect.Field;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProcAddressHelper
/*    */ {
/*    */   public static final String PROCADDRESS_VAR_PREFIX = "_addressof_";
/*    */   static final boolean $assertionsDisabled;
/*    */   
/*    */   public static void resetProcAddressTable(Object paramObject, DynamicLookupHelper paramDynamicLookupHelper) throws RuntimeException {
/* 50 */     Class clazz = paramObject.getClass();
/* 51 */     Field[] arrayOfField = clazz.getFields();
/*    */     
/* 53 */     for (byte b = 0; b < arrayOfField.length; b++) {
/* 54 */       String str = arrayOfField[b].getName();
/* 55 */       if (str.startsWith("_addressof_")) {
/*    */ 
/*    */ 
/*    */         
/* 59 */         int i = "_addressof_".length();
/* 60 */         String str1 = str.substring(i);
/*    */         try {
/* 62 */           Field field = arrayOfField[b];
/* 63 */           assert field.getType() == long.class;
/* 64 */           long l = paramDynamicLookupHelper.dynamicLookupFunction(str1);
/*    */           
/* 66 */           field.setLong(paramObject, l);
/* 67 */         } catch (Exception exception) {
/* 68 */           throw new RuntimeException("Can not get proc address for method \"" + str1 + "\": Couldn't set value of field \"" + str + "\" in class " + clazz.getName(), exception);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\sun\gluegen\runtime\ProcAddressHelper.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */