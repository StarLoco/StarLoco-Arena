/*    */ package com.sun.gluegen.runtime;
/*    */ 
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NativeLibLoader
/*    */ {
/*    */   private static volatile boolean loadingEnabled = true;
/*    */   private static volatile boolean didLoading;
/*    */   
/*    */   public static void disableLoading() {
/* 54 */     loadingEnabled = false;
/*    */   }
/*    */   
/*    */   public static void enableLoading() {
/* 58 */     loadingEnabled = true;
/*    */   }
/*    */   
/*    */   public static void loadGlueGenRT() {
/* 62 */     if (!didLoading && loadingEnabled)
/* 63 */       synchronized (NativeLibLoader.class) {
/* 64 */         if (!didLoading && loadingEnabled) {
/* 65 */           didLoading = true;
/* 66 */           AccessController.doPrivileged(new PrivilegedAction() {
/*    */                 public Object run() {
/* 68 */                   System.loadLibrary("gluegen-rt");
/* 69 */                   return null;
/*    */                 }
/*    */               });
/*    */         } 
/*    */       }  
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\sun\gluegen\runtime\NativeLibLoader.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */