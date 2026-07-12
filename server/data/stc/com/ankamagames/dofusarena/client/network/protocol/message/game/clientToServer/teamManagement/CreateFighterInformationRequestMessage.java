/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.teamManagement;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.OutputOnlyProxyMessage;
/*    */ import com.ankamagames.dofusarena.common.game.fighter.FighterInformation;
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
/*    */ public class CreateFighterInformationRequestMessage
/*    */   extends OutputOnlyProxyMessage
/*    */ {
/*    */   private FighterInformation m_fighterInformation;
/*    */   
/*    */   public byte[] encode()
/*    */   {
/* 31 */     byte[] serializedFighterInformation = new byte[0];
/* 32 */     if (this.m_fighterInformation != null) {
/* 33 */       serializedFighterInformation = this.m_fighterInformation.serialize();
/*    */     }
/*    */     
/*    */ 
/* 37 */     ByteBuffer buffer = ByteBuffer.allocate(2 + serializedFighterInformation.length);
/*    */     
/* 39 */     buffer.putShort((short)serializedFighterInformation.length);
/* 40 */     buffer.put(serializedFighterInformation);
/*    */     
/* 42 */     return addClientHeader((byte)3, buffer.array());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 52 */     return 6001;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setFighterInformation(FighterInformation fighterInformation)
/*    */   {
/* 59 */     this.m_fighterInformation = fighterInformation;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\clientToServer\teamManagement\CreateFighterInformationRequestMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */