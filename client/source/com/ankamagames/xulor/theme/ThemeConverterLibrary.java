/*    */ package com.ankamagames.xulor.theme;
/*    */ 
/*    */ import com.ankamagames.xulor.core.ConverterLibrary;
/*    */ 
/*    */ public class ThemeConverterLibrary
/*    */   extends ConverterLibrary {
/*  7 */   private static ThemeConverterLibrary instance = new ThemeConverterLibrary();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized ConverterLibrary getInstance() {
/* 13 */     return instance;
/*    */   }
/*    */   
/*    */   protected void registerConverters() {}
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemeConverterLibrary.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */