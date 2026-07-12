/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.teamManagement;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.InputOnlyProxyMessage;
/*    */ import com.ankamagames.dofusarena.common.game.team.TeamPreset;
/*    */ import java.nio.BufferUnderflowException;
/*    */ import java.nio.ByteBuffer;
/*    */ import org.apache.log4j.Logger;
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
/*    */ public class SaveTeamPresetMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private byte m_errorCode;
/*    */   private TeamPreset m_teamPreset;
/*    */   
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 32 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/*    */     
/* 34 */     this.m_errorCode = buffer.get();
/*    */     
/* 36 */     if (this.m_errorCode == 0) {
/* 37 */       TeamPreset teamPreset = new TeamPreset();
/*    */       try {
/* 39 */         teamPreset.unserialize(buffer);
/* 40 */         this.m_teamPreset = teamPreset;
/*    */       } catch (BufferUnderflowException e) {
/* 42 */         m_logger.error("decode error : BufferUnderflow ");
/*    */       }
/*    */     }
/*    */     
/* 46 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 56 */     return 6020;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public byte getErrorCode()
/*    */   {
/* 63 */     return this.m_errorCode;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public TeamPreset getTeamPreset()
/*    */   {
/* 70 */     return this.m_teamPreset;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\teamManagement\SaveTeamPresetMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */