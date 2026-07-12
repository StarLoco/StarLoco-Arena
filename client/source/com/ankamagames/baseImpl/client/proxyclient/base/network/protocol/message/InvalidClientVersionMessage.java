/*    */ package com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message;
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
/*    */ public class InvalidClientVersionMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private byte[] m_neededVersion;
/*    */   
/*    */   public boolean decode(byte[] rawDatas) {
/* 24 */     this.m_neededVersion = rawDatas;
/* 25 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 34 */     return 8;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setId(int id) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] getNeededVersion() {
/* 49 */     return this.m_neededVersion;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\InvalidClientVersionMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */