/*    */ package net.java.games.joal;
/*    */ 
/*    */ import net.java.games.joal.impl.ALCImpl;
/*    */ import net.java.games.joal.impl.ALImpl;
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
/*    */ public class ALFactory
/*    */ {
/*    */   private static boolean initialized = false;
/*    */   private static AL al;
/*    */   private static ALC alc;
/*    */   
/*    */   private static synchronized void initialize() throws ALException {
/*    */     try {
/* 55 */       if (!initialized) {
/* 56 */         NativeLibLoader.load();
/* 57 */         initialized = true;
/*    */       } 
/* 59 */     } catch (UnsatisfiedLinkError unsatisfiedLinkError) {
/* 60 */       throw new ALException(unsatisfiedLinkError);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static AL getAL() throws ALException {
/* 71 */     initialize();
/* 72 */     if (al == null) {
/* 73 */       al = (AL)new ALImpl();
/*    */     }
/* 75 */     return al;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ALC getALC() throws ALException {
/* 85 */     initialize();
/* 86 */     if (alc == null) {
/* 87 */       alc = (ALC)new ALCImpl();
/*    */     }
/* 89 */     return alc;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\net\java\games\joal\ALFactory.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */