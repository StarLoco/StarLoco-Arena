/*    */ package com.sun.gluegen.runtime;
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
/*    */ public class UnixDynamicLinkerImpl
/*    */   implements DynamicLinker
/*    */ {
/*    */   public static final int RTLD_LAZY = 1;
/*    */   public static final int RTLD_NOW = 2;
/*    */   public static final int RTLD_NOLOAD = 4;
/*    */   public static final int RTLD_GLOBAL = 256;
/*    */   public static final int RTLD_LOCAL = 0;
/*    */   public static final int RTLD_PARENT = 512;
/*    */   public static final int RTLD_GROUP = 1024;
/*    */   public static final int RTLD_WORLD = 2048;
/*    */   public static final int RTLD_NODELETE = 4096;
/*    */   public static final int RTLD_FIRST = 8192;
/*    */   
/*    */   private static native int dlclose(long paramLong);
/*    */   
/*    */   private static native String dlerror();
/*    */   
/*    */   private static native long dlopen(String paramString, int paramInt);
/*    */   
/*    */   private static native long dlsym(long paramLong, String paramString);
/*    */   
/*    */   public long openLibrary(String paramString) {
/* 43 */     return dlopen(paramString, 257);
/*    */   }
/*    */   
/*    */   public long lookupSymbol(long paramLong, String paramString) {
/* 47 */     return dlsym(paramLong, paramString);
/*    */   }
/*    */   
/*    */   public void closeLibrary(long paramLong) {
/* 51 */     dlclose(paramLong);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\sun\gluegen\runtime\UnixDynamicLinkerImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */