/*     */ package com.ankamagames.dofusarena.client.network.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*     */ import com.ankamagames.dofusarena.client.core.game.fight.FightInvitation;
/*     */ import com.ankamagames.dofusarena.client.core.game.fight.FightInvitationManager;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.FightInvitationAcceptedMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.FightInvitationErrorMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.FightInvitationMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.FightInvitationRejectedMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UIFightInvitationFrame;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UIFightResultFrame;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UIFightTeamManagementFrame;
/*     */ import com.ankamagames.framework.kernel.FrameHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.events.MessageFrame;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NetFightInvitationFrame
/*     */   implements MessageFrame
/*     */ {
/*  36 */   protected static final Logger m_logger = Logger.getLogger(NetFightInvitationFrame.class);
/*     */   
/*  38 */   private static NetFightInvitationFrame m_instance = new NetFightInvitationFrame();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NetFightInvitationFrame getInstance() {
/*  44 */     return m_instance;
/*     */   }
/*     */   public boolean onMessage(Message message) {
/*     */     FightInvitationMessage fightInvitationMessage;
/*     */     FightInvitationAcceptedMessage fightInvitationAcceptedMessage;
/*     */     FightInvitationRejectedMessage fightInvitationRejectedMessage;
/*     */     FightInvitationErrorMessage msg;
/*     */     FightInvitation fightInvitation;
/*     */     String readableMsg;
/*  53 */     switch (message.getId()) {
/*     */       
/*     */       case 4300:
/*  56 */         fightInvitationMessage = (FightInvitationMessage)message;
/*     */ 
/*     */         
/*  59 */         DofusArenaGameEntity.getInstance().removeFrame((MessageFrame)UIFightResultFrame.getInstance());
/*     */ 
/*     */         
/*  62 */         FightInvitationManager.getInstance().addInvitation(fightInvitationMessage.getInvitationId(), fightInvitationMessage.getOpponentTeams(), fightInvitationMessage.getFightDefinition(), fightInvitationMessage.isInviter(), fightInvitationMessage.getBet());
/*     */         
/*  64 */         return false;
/*     */ 
/*     */       
/*     */       case 4302:
/*  68 */         fightInvitationAcceptedMessage = (FightInvitationAcceptedMessage)message;
/*     */ 
/*     */         
/*  71 */         fightInvitation = FightInvitationManager.getInstance().getInvitation(fightInvitationAcceptedMessage.getInvitationId());
/*     */ 
/*     */         
/*  74 */         FightInvitationManager.getInstance().clear();
/*     */ 
/*     */         
/*  77 */         NetFightCreationFrame.getInstance().setFightId(fightInvitationAcceptedMessage.getFightId());
/*  78 */         NetFightCreationFrame.getInstance().setFightDefinition((fightInvitation != null) ? fightInvitation.getFightDefinition() : null);
/*  79 */         NetFightCreationFrame.getInstance().setCancelableFight(true);
/*     */ 
/*     */         
/*  82 */         DofusArenaGameEntity.getInstance().removeFrame(this);
/*  83 */         DofusArenaGameEntity.getInstance().removeFrame((MessageFrame)UIFightInvitationFrame.getInstance());
/*     */ 
/*     */         
/*  86 */         DofusArenaGameEntity.getInstance().pushFrame(NetFightCreationFrame.getInstance());
/*  87 */         DofusArenaGameEntity.getInstance().pushFrame((MessageFrame)UIFightTeamManagementFrame.getInstance());
/*     */         
/*  89 */         return false;
/*     */ 
/*     */       
/*     */       case 4304:
/*  93 */         fightInvitationRejectedMessage = (FightInvitationRejectedMessage)message;
/*     */ 
/*     */         
/*  96 */         FightInvitationManager.getInstance().removeInvitation(fightInvitationRejectedMessage.getInvitationId());
/*     */         
/*  98 */         return false;
/*     */ 
/*     */       
/*     */       case 4309:
/* 102 */         msg = (FightInvitationErrorMessage)message;
/*     */         
/* 104 */         readableMsg = "";
/* 105 */         switch (msg.getErrorCode())
/*     */         { case 30:
/* 107 */             readableMsg = DofusArenaTranslator.getInstance().getString("error.fight.creation.targetNotFound", new Object[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 123 */             Xulor.getInstance().msgBox(readableMsg, 66);
/*     */             
/* 125 */             return false;case 31: readableMsg = DofusArenaTranslator.getInstance().getString("error.fight.creation.targetBusy", new Object[0]); Xulor.getInstance().msgBox(readableMsg, 66); return false;case 32: readableMsg = DofusArenaTranslator.getInstance().getString("error.fight.creation.youreBusy", new Object[0]); Xulor.getInstance().msgBox(readableMsg, 66); return false;case 33: readableMsg = DofusArenaTranslator.getInstance().getString("error.fight.creation.targetIsYourself", new Object[0]); Xulor.getInstance().msgBox(readableMsg, 66); return false; }  readableMsg = "Erreur lors de l'invitation (" + msg.getErrorCode() + ")"; Xulor.getInstance().msgBox(readableMsg, 66); return false;
/*     */     } 
/*     */ 
/*     */     
/* 129 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getId() {
/* 138 */     return 0L;
/*     */   }
/*     */   
/*     */   public void setId(long id) {}
/*     */   
/*     */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded) {}
/*     */   
/*     */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved) {}
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\frame\NetFightInvitationFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */