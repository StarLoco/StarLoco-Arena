/*    */ package com.ankamagames.dofusarena.client.ui.protocol.frame;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*    */ import com.ankamagames.dofusarena.client.network.protocol.message.world.clientToServer.FightCreationCancelMessage;
/*    */ import com.ankamagames.dofusarena.client.ui.Dialogs;
/*    */ import com.ankamagames.dofusarena.client.ui.protocol.message.teamManagement.UIFightMessage;
/*    */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*    */ import com.ankamagames.xulor.Xulor;
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
/*    */ public class UIFightTeamManagementFrame
/*    */   extends UIRandomFightTeamManagementFrame
/*    */ {
/* 22 */   private static UIFightTeamManagementFrame m_instance = new UIFightTeamManagementFrame();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static UIFightTeamManagementFrame getInstance() {
/* 28 */     return m_instance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onMessage(Message message) {
/*    */     UIFightMessage msg;
/*    */     FightCreationCancelMessage netMessage;
/* 38 */     switch (message.getId()) {
/*    */       
/*    */       case 16601:
/* 41 */         msg = (UIFightMessage)message;
/*    */ 
/*    */         
/* 44 */         netMessage = new FightCreationCancelMessage();
/* 45 */         netMessage.setFightId(msg.getFightId());
/* 46 */         DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage((Message)netMessage);
/*    */         
/* 48 */         return false;
/*    */     } 
/*    */ 
/*    */     
/* 52 */     return super.onMessage(message);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void closeDialog() {
/* 63 */     Xulor.getInstance().unload("fightTeamManagementDialog");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void openDialog() {
/* 74 */     Xulor.getInstance().load("fightTeamManagementDialog", Dialogs.getDialogPath("fightTeamManagementDialog"), 128L, (short)10001);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\clien\\ui\protocol\frame\UIFightTeamManagementFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */