/*     */ package com.ankamagames.dofusarena.client.network.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.world.clientToServer.FightCreationCancelMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.world.serverToClient.FightCreationCanceledMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.world.serverToClient.ReadyForFightMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UICoachInventoryManagementFrame;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UICoachStatisticsFrame;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UIFightTeamManagementFrame;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UIRandomFightTeamManagementFrame;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UITeamManagementFrame;
/*     */ import com.ankamagames.dofusarena.common.game.fight.FightDefinition;
/*     */ import com.ankamagames.framework.kernel.FrameHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.events.MessageFrame;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.core.messagebox.IMessageBoxEventListener;
/*     */ import com.ankamagames.xulor.core.messagebox.MessageBoxControler;
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
/*     */ 
/*     */ 
/*     */ public class NetFightCreationFrame
/*     */   implements MessageFrame
/*     */ {
/*  40 */   protected static final Logger m_logger = Logger.getLogger(NetFightCreationFrame.class);
/*     */   
/*  42 */   private static NetFightCreationFrame m_instance = new NetFightCreationFrame();
/*     */   
/*     */   private long m_fightId;
/*     */   private boolean m_cancelableFight = true;
/*  46 */   private MessageBoxControler m_messageBoxControler = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NetFightCreationFrame getInstance() {
/*  52 */     return m_instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFightId(long fightId) {
/*  59 */     this.m_fightId = fightId;
/*     */ 
/*     */     
/*  62 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("fight.id", Long.valueOf(fightId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFightDefinition(FightDefinition fightDefinition) {
/*  70 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("fight.budget", Integer.valueOf(fightDefinition.getBudget()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCancelableFight(boolean cancelableFight) {
/*  77 */     this.m_cancelableFight = cancelableFight;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onMessage(Message message) {
/*     */     ReadyForFightMessage readyForFightMessage;
/*     */     FightCreationCanceledMessage msg;
/*     */     String readableMsg;
/*  86 */     switch (message.getId()) {
/*     */       
/*     */       case 4306:
/*  89 */         readyForFightMessage = (ReadyForFightMessage)message;
/*     */         
/*  91 */         if (readyForFightMessage.getCoachId() == DofusArenaGameEntity.getInstance().getLocalCoach().getId()) {
/*     */ 
/*     */ 
/*     */           
/*  95 */           int options = 4;
/*  96 */           this.m_messageBoxControler = Xulor.getInstance().msgBox(DofusArenaTranslator.getInstance().getString("waitingForOpponents", new Object[0]), options | 0x20 | 0x1);
/*  97 */           this.m_messageBoxControler.addEventListener(new IMessageBoxEventListener()
/*     */               {
/*     */                 public void messageBoxClosed(int type) {
/* 100 */                   if (type == 4) {
/*     */ 
/*     */                     
/* 103 */                     FightCreationCancelMessage netMessage = new FightCreationCancelMessage();
/* 104 */                     netMessage.setFightId(NetFightCreationFrame.this.m_fightId);
/* 105 */                     DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage((Message)netMessage);
/*     */                   } 
/*     */                 }
/*     */               });
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 113 */         return false;
/*     */ 
/*     */       
/*     */       case 4310:
/* 117 */         msg = (FightCreationCanceledMessage)message;
/*     */ 
/*     */         
/* 120 */         DofusArenaGameEntity.getInstance().pushFrame(NetFightInvitationFrame.getInstance());
/*     */ 
/*     */ 
/*     */         
/* 124 */         DofusArenaGameEntity.getInstance().removeFrame(NetFightFrame.getInstance());
/* 125 */         DofusArenaGameEntity.getInstance().removeFrame((MessageFrame)UIFightTeamManagementFrame.getInstance());
/* 126 */         DofusArenaGameEntity.getInstance().removeFrame((MessageFrame)UIRandomFightTeamManagementFrame.getInstance());
/* 127 */         DofusArenaGameEntity.getInstance().removeFrame(this);
/*     */         
/* 129 */         readableMsg = "";
/* 130 */         switch (msg.getCancelReason())
/*     */         
/*     */         {
                    case 34: readableMsg = DofusArenaTranslator.getInstance().getString("error.fight.creation.unableToCreateFight", new Object[0]);  Xulor.getInstance().msgBox(readableMsg, 66); return false;
                    case 35: readableMsg = DofusArenaTranslator.getInstance().getString("error.fight.creation.targetDisconnected", new Object[0]);Xulor.getInstance().msgBox(readableMsg, 66);return false;
                    case 36: readableMsg = DofusArenaTranslator.getInstance().getString("error.fight.creation.noSelectedTeam", new Object[0]); Xulor.getInstance().msgBox(readableMsg, 66);return false;
                    case 37: readableMsg = DofusArenaTranslator.getInstance().getString("error.fight.creation.noPendingFight", new Object[0]);Xulor.getInstance().msgBox(readableMsg, 66);return false;
                    case 38: readableMsg = DofusArenaTranslator.getInstance().getString("error.fight.creation.internalErrorDuringCreation", new Object[0]); Xulor.getInstance().msgBox(readableMsg, 66); return false;
                    case 39: readableMsg = DofusArenaTranslator.getInstance().getString("error.fight.creation.noInstanceServer", new Object[0]); Xulor.getInstance().msgBox(readableMsg, 66); return false;
                    case 40: readableMsg = DofusArenaTranslator.getInstance().getString("error.fight.creation.canceledByOpponent", new Object[0]); Xulor.getInstance().msgBox(readableMsg, 66); return false;
                    case 41: readableMsg = DofusArenaTranslator.getInstance().getString("error.fight.creation.badFightParameters", new Object[0]); Xulor.getInstance().msgBox(readableMsg, 66); return false;
                    case 42: readableMsg = DofusArenaTranslator.getInstance().getString("error.fight.creation.noSelectedFighter", new Object[0]); Xulor.getInstance().msgBox(readableMsg, 66); return false;
                    case 43: readableMsg = DofusArenaTranslator.getInstance().getString("error.fight.creation.notEnoughFighters", new Object[0]); Xulor.getInstance().msgBox(readableMsg, 66); return false;
                    case 44: readableMsg = DofusArenaTranslator.getInstance().getString("error.fight.creation.notEnoughCoach", new Object[0]); Xulor.getInstance().msgBox(readableMsg, 66); return false;
                    case 45: readableMsg = DofusArenaTranslator.getInstance().getString("error.fight.creation.invalidFightersCount", new Object[0]); Xulor.getInstance().msgBox(readableMsg, 66); return false;
                    case 46: readableMsg = DofusArenaTranslator.getInstance().getString("error.fight.creation.invalidTeamBudget", new Object[0]); Xulor.getInstance().msgBox(readableMsg, 66); return false;
                    case 47: readableMsg = DofusArenaTranslator.getInstance().getString("error.fight.creation.cantHoldTheBet", new Object[0]); Xulor.getInstance().msgBox(readableMsg, 66); return false;
                }
            readableMsg = "Erreur lors de la création du combat (" + msg.getCancelReason() + ")"; Xulor.getInstance().msgBox(readableMsg, 66); return false;
/*     */     } 
/*     */ 
/*     */     
/* 185 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getId() {
/* 194 */     return 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(long id) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded) {
/* 212 */     if (!isAboutToBeAdded) {
/*     */ 
/*     */       
/* 215 */       DofusArenaGameEntity.getInstance().removeFrame((MessageFrame)UITeamManagementFrame.getInstance());
/* 216 */       DofusArenaGameEntity.getInstance().removeFrame((MessageFrame)UICoachInventoryManagementFrame.getInstance());
/* 217 */       DofusArenaGameEntity.getInstance().removeFrame((MessageFrame)UICoachStatisticsFrame.getInstance());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved) {
/* 229 */     if (!isAboutToBeRemoved) {
/* 230 */       this.m_fightId = 0L;
/*     */       
/* 232 */       if (this.m_messageBoxControler != null) {
/* 233 */         Xulor.getInstance().unload(this.m_messageBoxControler.getMessageBoxId());
/*     */       }
/*     */ 
/*     */       
/* 237 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().removeProperty("fight.id");
/* 238 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().removeProperty("fight.budget");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\frame\NetFightCreationFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */