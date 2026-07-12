/*    */ package com.ankamagames.dofusarena.client.ui.protocol.message.teamManagement;
/*    */ 
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
/*    */ public class UISelectTeamPresetRequestMessage
/*    */   extends UIMessage
/*    */ {
/*    */   private short m_teamPresetId;
/*    */   
/*    */   public int getId() {
/* 26 */     return 16617;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public short getTeamPresetId() {
/* 33 */     return this.m_teamPresetId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setTeamPresetId(short teamPresetId) {
/* 40 */     this.m_teamPresetId = teamPresetId;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\clien\\ui\protocol\message\teamManagement\UISelectTeamPresetRequestMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */