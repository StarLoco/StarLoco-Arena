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
/*    */ public class ProcAddressHelper
/*    */ {
/*    */   public static final String PROCADDRESS_VAR_PREFIX = "_addressof_";
/*    */   
/*    */   public static void resetProcAddressTable(Object paramObject, DynamicLookupHelper paramDynamicLookupHelper)
/*    */     throws RuntimeException
/*    */   {
/* 50 */     Class localClass = paramObject.getClass();
/* 51 */     Field[] arrayOfField = localClass.getFields();
/*    */     
/* 53 */     for (int i = 0; i < arrayOfField.length; i++) {
/* 54 */       String str1 = arrayOfField[i].getName();
/* 55 */       if (str1.startsWith("_addressof_"))
/*    */       {
/*    */ 
/*    */ 
/* 59 */         int j = "_addressof_".length();
/* 60 */         String str2 = str1.substring(j);
/*    */         try {
/* 62 */           Field localField = arrayOfField[i];
/* 63 */           assert (localField.getType() == Long.TYPE);
/* 64 */           long l = paramDynamicLookupHelper.dynamicLookupFunction(str2);
/*    */           
/* 66 */           localField.setLong(paramObject, l);
/*    */         } catch (Exception localException) {
/* 68 */           throw new RuntimeException("Can not get proc address for method \"" + str2 + "\": Couldn't set value of field \"" + str1 + "\" in class " + localClass.getName(), localException);
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\sun\gluegen\runtime\ProcAddressHelper.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       0.7.1
 */