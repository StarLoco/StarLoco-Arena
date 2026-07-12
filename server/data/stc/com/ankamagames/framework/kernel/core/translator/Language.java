/*    */ package com.ankamagames.framework.kernel.core.translator;
/*    */ 
/*    */ import java.util.Locale;
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
/*    */ public enum Language
/*    */ {
/* 18 */   FR(new Locale("fr")), 
/* 19 */   EN(new Locale("en")), 
/* 20 */   DE(new Locale("de")), 
/* 21 */   ES(new Locale("es"));
/*    */   
/*    */ 
/*    */ 
/*    */   private Locale m_locale;
/*    */   
/*    */ 
/*    */ 
/*    */   private Language(Locale locale)
/*    */   {
/* 31 */     this.m_locale = locale;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Locale getLocale()
/*    */   {
/* 38 */     return this.m_locale;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public static Language getLanguage(String languageCode)
/*    */   {
/* 46 */     if (languageCode.equals("fr"))
/* 47 */       return FR;
/* 48 */     if (languageCode.equals("en"))
/* 49 */       return EN;
/* 50 */     if (languageCode.equals("de"))
/* 51 */       return DE;
/* 52 */     if (languageCode.equals("es")) {
/* 53 */       return ES;
/*    */     }
/* 55 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\translator\Language.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */