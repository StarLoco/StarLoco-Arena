/*    */ package com.ankamagames.dofusarena.client.ui.protocol.message.teamManagement;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.core.game.team.EditableTeamPreset;
/*    */ import com.ankamagames.dofusarena.client.ui.protocol.message.UIMessage;
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
/*    */ public class UIReadyForFightRequestMessage
/*    */   extends UIMessage
/*    */ {
/*    */   private long m_fightId;
/*    */   private EditableTeamPreset m_teamPreset;
/*    */   
/*    */   public int getId() {
/* 30 */     return 16600;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getFightId() {
/* 37 */     return this.m_fightId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setFightId(long fightId) {
/* 44 */     this.m_fightId = fightId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EditableTeamPreset getTeamPreset() {
/* 51 */     return this.m_teamPreset;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setTeamPreset(EditableTeamPreset teamPreset) {
/* 58 */     this.m_teamPreset = teamPreset;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\clien\\ui\protocol\message\teamManagement\UIReadyForFightRequestMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */