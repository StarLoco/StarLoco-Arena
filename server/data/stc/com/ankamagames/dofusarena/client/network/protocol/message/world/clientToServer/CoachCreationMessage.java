/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.world.clientToServer;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.OutputOnlyProxyMessage;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CoachCreationMessage
/*    */   extends OutputOnlyProxyMessage
/*    */ {
/*    */   private LocalCoach m_localCoach;
/*    */   
/*    */   public byte[] encode()
/*    */   {
/* 31 */     byte[] name = this.m_localCoach.getName().getBytes();
/* 32 */     ByteBuffer buffer = ByteBuffer.allocate(4 + name.length);
/* 33 */     buffer.put((byte)name.length);
/* 34 */     buffer.put(name);
/* 35 */     buffer.put(this.m_localCoach.getSkinColorIndex());
/* 36 */     buffer.put(this.m_localCoach.getHairColorIndex());
/* 37 */     buffer.put(this.m_localCoach.getSex());
/*    */     
/* 39 */     return addClientHeader((byte)2, buffer.array());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 49 */     return 2049;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setLocalCoach(LocalCoach localCoach)
/*    */   {
/* 56 */     this.m_localCoach = localCoach;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\world\clientToServer\CoachCreationMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */