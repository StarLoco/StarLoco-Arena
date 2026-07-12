/*    */ package com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message;
/*    */ 
/*    */ import java.nio.ByteBuffer;
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
/*    */ public class QueueNotificationMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/* 17 */   private int m_position = -1;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean decode(byte[] rawDatas) {
/* 26 */     if (!checkMessageSize(rawDatas.length, 4, true)) {
/* 27 */       return false;
/*    */     }
/* 29 */     ByteBuffer bb = ByteBuffer.wrap(rawDatas);
/* 30 */     this.m_position = bb.getInt();
/*    */     
/* 32 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 41 */     return 8192;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getClientId() {
/* 50 */     return this.m_clientId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getPosition() {
/* 57 */     return this.m_position;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setPosition(int position) {
/* 64 */     this.m_position = position;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\QueueNotificationMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */