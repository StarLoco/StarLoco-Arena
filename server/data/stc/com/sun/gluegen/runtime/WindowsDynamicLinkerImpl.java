/*    */ package com.sun.gluegen.runtime;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WindowsDynamicLinkerImpl
/*    */   implements DynamicLinker
/*    */ {
/*    */   private static native int FreeLibrary(long paramLong);
/*    */   
/*    */ 
/*    */ 
/*    */   private static native int GetLastError();
/*    */   
/*    */ 
/*    */ 
/*    */   private static native long GetProcAddress(long paramLong, String paramString);
/*    */   
/*    */ 
/*    */ 
/*    */   private static native long LoadLibraryA(String paramString);
/*    */   
/*    */ 
/*    */ 
/*    */   public long openLibrary(String paramString)
/*    */   {
/* 26 */     return LoadLibraryA(paramString);
/*    */   }
/*    */   
/*    */   public long lookupSymbol(long paramLong, String paramString) {
/* 30 */     return GetProcAddress(paramLong, paramString);
/*    */   }
/*    */   
/*    */   public void closeLibrary(long paramLong) {
/* 34 */     FreeLibrary(paramLong);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\sun\gluegen\runtime\WindowsDynamicLinkerImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       0.7.1
 */