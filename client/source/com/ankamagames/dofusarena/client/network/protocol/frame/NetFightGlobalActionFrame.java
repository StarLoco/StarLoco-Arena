/*    */ package com.ankamagames.dofusarena.client.network.protocol.frame;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*    */ import com.ankamagames.dofusarena.client.core.action.FightEndAction;
/*    */ import com.ankamagames.dofusarena.client.core.game.fight.Fight;
/*    */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.fight.EndFightMessage;
/*    */ import com.ankamagames.framework.kernel.FrameHandler;
/*    */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*    */ import com.ankamagames.framework.kernel.events.MessageFrame;
/*    */ import com.ankamagames.framework.script.action.Action;
/*    */ import com.ankamagames.framework.script.action.QueueActionGroupManager;
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
/*    */ public class NetFightGlobalActionFrame
/*    */   implements MessageFrame
/*    */ {
/* 29 */   protected static final Logger m_logger = Logger.getLogger(NetFightGlobalActionFrame.class);
/*    */   
/* 31 */   private static NetFightGlobalActionFrame m_instance = new NetFightGlobalActionFrame();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static NetFightGlobalActionFrame getInstance() {
/* 37 */     return m_instance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onMessage(Message message) {
/*    */     EndFightMessage msg;
/*    */     Fight fight;
/* 46 */     switch (message.getId()) {
/*    */       
/*    */       case 8300:
/* 49 */         msg = (EndFightMessage)message;
/*    */         
/* 51 */         fight = DofusArenaGameEntity.getInstance().getFight();
/* 52 */         if (fight != null) {
/*    */           
/* 54 */           int actionTypeId = msg.getFightActionType().getId();
/*    */           
/* 56 */           FightEndAction action = new FightEndAction(msg.getUniqueId(), actionTypeId, msg.getActionId(), fight, msg.getWinnerTeamMatesResultInformations(), msg.getLooserTeamMatesResultInformations(), msg.getLostCards(), msg.getWonCards(), msg.getBonusCards());
/*    */           
/* 58 */           QueueActionGroupManager.getInstance().addActionToPendingGroup((Action)action);
/* 59 */           QueueActionGroupManager.getInstance().executePendingGroup();
/*    */         } else {
/*    */           
/* 62 */           m_logger.error("Il n'existe aucun combat à terminer !");
/*    */         } 
/*    */         
/* 65 */         return false;
/*    */     } 
/*    */ 
/*    */     
/* 69 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getId() {
/* 78 */     return 0L;
/*    */   }
/*    */   
/*    */   public void setId(long id) {}
/*    */   
/*    */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded) {}
/*    */   
/*    */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved) {}
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\frame\NetFightGlobalActionFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */