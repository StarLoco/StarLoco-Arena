/*    */ package com.ankamagames.xulor.binding.fenggui;
/*    */ 
/*    */ import com.ankamagames.xulor.core.ConverterLibrary;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FengguiConverterLibrary
/*    */   extends ConverterLibrary
/*    */ {
/* 14 */   private static FengguiConverterLibrary instance = new FengguiConverterLibrary();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized ConverterLibrary getInstance() {
/* 20 */     return instance;
/*    */   }
/*    */   
/*    */   protected void registerConverters() {}
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\FengguiConverterLibrary.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */