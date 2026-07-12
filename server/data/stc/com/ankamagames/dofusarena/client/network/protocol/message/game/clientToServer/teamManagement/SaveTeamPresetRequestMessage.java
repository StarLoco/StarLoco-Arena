/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.teamManagement;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.OutputOnlyProxyMessage;
/*    */ import com.ankamagames.dofusarena.common.game.team.TeamPreset;
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
/*    */ public class SaveTeamPresetRequestMessage
/*    */   extends OutputOnlyProxyMessage
/*    */ {
/*    */   private TeamPreset m_teamPreset;
/*    */   
/*    */   public byte[] encode()
/*    */   {
/* 28 */     byte[] serializedTeamPreset = new byte[0];
/* 29 */     if (this.m_teamPreset != null) {
/* 30 */       serializedTeamPreset = this.m_teamPreset.serialize();
/*    */     }
/* 32 */     return addClientHeader((byte)3, serializedTeamPreset);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 42 */     return 6021;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setTeamPreset(TeamPreset teamPreset)
/*    */   {
/* 49 */     this.m_teamPreset = teamPreset;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\clientToServer\teamManagement\SaveTeamPresetRequestMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */