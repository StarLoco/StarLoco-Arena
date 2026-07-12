/*    */ package net.java.games.joal;
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
/*    */ class NativeLibLoader
/*    */ {
/*    */   static {
/* 45 */     AccessController.doPrivileged(new PrivilegedAction()
/*    */         {
/*    */ 
/*    */ 
/*    */           
/*    */           public Object run()
/*    */           {
/* 52 */             System.loadLibrary("joal_native");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */             
/* 61 */             return null;
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public static void load() {}
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\net\java\games\joal\NativeLibLoader.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */