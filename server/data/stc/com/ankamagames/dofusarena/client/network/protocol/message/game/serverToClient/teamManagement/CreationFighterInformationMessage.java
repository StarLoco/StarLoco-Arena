/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.teamManagement;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.InputOnlyProxyMessage;
/*    */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
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
/*    */ public class CreationFighterInformationMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private byte m_errorCode;
/*    */   private Fighter m_fighter;
/*    */   
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 31 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/*    */     
/* 33 */     this.m_errorCode = buffer.get();
/*    */     
/* 35 */     if (this.m_errorCode == 0) {
/* 36 */       long id = buffer.getLong();
/*    */       
/* 38 */       byte[] bArray = new byte[buffer.getShort()];
/* 39 */       buffer.get(bArray);
/* 40 */       FighterInformation fighterInformation = new FighterInformation();
/* 41 */       fighterInformation.unserialize(bArray);
/*    */       
/* 43 */       this.m_fighter = new Fighter();
/* 44 */       this.m_fighter.setId(id);
/* 45 */       this.m_fighter.initWithFighterInformation(fighterInformation);
/*    */     }
/*    */     
/* 48 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 57 */     return 6000;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public byte getErrorCode()
/*    */   {
/* 64 */     return this.m_errorCode;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Fighter getFighter()
/*    */   {
/* 71 */     return this.m_fighter;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\teamManagement\CreationFighterInformationMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */