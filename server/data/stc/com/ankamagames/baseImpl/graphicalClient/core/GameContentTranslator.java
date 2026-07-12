/*    */ package com.ankamagames.baseImpl.graphicalClient.core;
/*    */ 
/*    */ import com.ankamagames.framework.kernel.core.translator.Translator;
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
/*    */ public class GameContentTranslator
/*    */   extends Translator
/*    */ {
/*    */   private static final String GAME_CONTENT_TRANSLATOR_PREFIX = "content.";
/*    */   private static final char GAME_CONTENT_TRANSLATOR_SEPARATOR = '.';
/*    */   
/*    */   public String getString(int contentType, int contentId)
/*    */   {
/* 26 */     String stringKey = "content." + contentType + '.' + contentId;
/* 27 */     return getString(stringKey, new Object[0]);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean containsContentKey(int contentType, int contentId)
/*    */   {
/* 35 */     String stringKey = "content." + contentType + '.' + contentId;
/* 36 */     return containsKey(stringKey);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphicalClient\core\GameContentTranslator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */