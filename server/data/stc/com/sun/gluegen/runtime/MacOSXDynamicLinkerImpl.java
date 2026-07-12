/*    */ package com.sun.gluegen.runtime;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MacOSXDynamicLinkerImpl
/*    */   implements DynamicLinker
/*    */ {
/*    */   public static final int RTLD_LAZY = 1;
/*    */   
/*    */ 
/*    */   public static final int RTLD_NOW = 2;
/*    */   
/*    */ 
/*    */   public static final int RTLD_LOCAL = 4;
/*    */   
/*    */ 
/*    */   public static final int RTLD_GLOBAL = 8;
/*    */   
/*    */ 
/*    */ 
/*    */   private static native int dlclose(long paramLong);
/*    */   
/*    */ 
/*    */ 
/*    */   private static native String dlerror();
/*    */   
/*    */ 
/*    */ 
/*    */   private static native long dlopen(String paramString, int paramInt);
/*    */   
/*    */ 
/*    */   private static native long dlsym(long paramLong, String paramString);
/*    */   
/*    */ 
/*    */   public long openLibrary(String paramString)
/*    */   {
/* 37 */     return dlopen(paramString, 9);
/*    */   }
/*    */   
/*    */   public long lookupSymbol(long paramLong, String paramString) {
/* 41 */     return dlsym(paramLong, paramString);
/*    */   }
/*    */   
/*    */   public void closeLibrary(long paramLong) {
/* 45 */     dlclose(paramLong);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\sun\gluegen\runtime\MacOSXDynamicLinkerImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       0.7.1
 */