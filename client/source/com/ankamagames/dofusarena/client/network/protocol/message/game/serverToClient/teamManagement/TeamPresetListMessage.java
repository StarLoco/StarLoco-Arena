/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.teamManagement;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.InputOnlyProxyMessage;
/*    */ import com.ankamagames.dofusarena.common.game.team.TeamPreset;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.ArrayList;
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
/*    */ public class TeamPresetListMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/* 21 */   private final ArrayList<TeamPreset> m_teamPresets = new ArrayList<TeamPreset>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean decode(byte[] rawDatas) {
/* 30 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/*    */     
/* 32 */     this.m_teamPresets.clear();
/*    */     
/* 34 */     int teamPresetCount = buffer.get();
/* 35 */     for (int i = 0; i < teamPresetCount; i++) {
/* 36 */       TeamPreset teamPreset = new TeamPreset();
/* 37 */       teamPreset.unserialize(buffer);
/* 38 */       this.m_teamPresets.add(teamPreset);
/*    */     } 
/* 40 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 50 */     return 6030;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Iterable<TeamPreset> getTeamPresets() {
/* 57 */     return this.m_teamPresets;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getTeamPresetsCount() {
/* 64 */     return this.m_teamPresets.size();
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\teamManagement\TeamPresetListMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */