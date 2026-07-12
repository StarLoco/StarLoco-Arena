/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.world.serverToClient;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.InputOnlyProxyMessage;
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
/*    */ public class OpponentSearchErrorMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private byte m_errorCode;
/*    */   
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 25 */     this.m_errorCode = rawDatas[0];
/* 26 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 35 */     return 2302;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public byte getErrorCode()
/*    */   {
/* 42 */     return this.m_errorCode;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\world\serverToClient\OpponentSearchErrorMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */