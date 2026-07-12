/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.world.serverToClient;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.InputOnlyProxyMessage;
/*    */ import com.ankamagames.dofusarena.client.core.game.coach.LocalCoach;
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
/*    */ 
/*    */ 
/*    */ public class CoachInformationsMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/* 21 */   private LocalCoach m_localCoach = null;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 30 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/*    */     
/* 32 */     LocalCoach localCoach = new LocalCoach();
/* 33 */     if (localCoach.unserialize(buffer, 14)) {
/* 34 */       this.m_localCoach = localCoach;
/*    */     }
/* 36 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 45 */     return 2052;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public LocalCoach getLocalCoach()
/*    */   {
/* 52 */     return this.m_localCoach;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\world\serverToClient\CoachInformationsMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */