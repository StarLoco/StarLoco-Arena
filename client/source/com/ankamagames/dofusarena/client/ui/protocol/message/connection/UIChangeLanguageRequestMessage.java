/*    */ package com.ankamagames.dofusarena.client.ui.protocol.message.connection;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.ui.protocol.message.UIMessage;
/*    */ import com.ankamagames.framework.kernel.core.translator.Language;
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
/*    */ public class UIChangeLanguageRequestMessage
/*    */   extends UIMessage
/*    */ {
/*    */   private Language m_language;
/*    */   
/*    */   public int getId() {
/* 29 */     return 16384;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Language getLanguage() {
/* 36 */     return this.m_language;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setLanguage(Language language) {
/* 43 */     this.m_language = language;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\clien\\ui\protocol\message\connection\UIChangeLanguageRequestMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */