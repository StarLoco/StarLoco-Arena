/*     */ package com.ankamagames.dofusarena.client.network.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.NetworkEntity;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*     */ import com.ankamagames.dofusarena.client.core.game.coach.LocalCoach;
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
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.core.messagebox.IMessageBoxEventListener;
/*     */ import com.ankamagames.xulor.core.messagebox.MessageBoxControler;
/*     */ import com.ankamagames.xulor.property.PropertiesProvider;
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
/*     */ public class NetFightCreationFrame
/*     */   implements MessageFrame
/*     */ {
/*  40 */   protected static final Logger m_logger = Logger.getLogger(NetFightCreationFrame.class);
/*     */   
/*  42 */   private static NetFightCreationFrame m_instance = new NetFightCreationFrame();
/*     */   
/*     */   private long m_fightId;
/*  45 */   private boolean m_cancelableFight = true;
/*  46 */   private MessageBoxControler m_messageBoxControler = null;
/*     */   
/*     */ 
/*     */ 
/*     */   public static NetFightCreationFrame getInstance()
/*     */   {
/*  52 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setFightId(long fightId)
/*     */   {
/*  59 */     this.m_fightId = fightId;
/*     */     
/*     */ 
/*  62 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("fight.id", Long.valueOf(fightId));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFightDefinition(FightDefinition fightDefinition)
/*     */   {
/*  70 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("fight.budget", Integer.valueOf(fightDefinition.getBudget()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setCancelableFight(boolean cancelableFight)
/*     */   {
/*  77 */     this.m_cancelableFight = cancelableFight;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onMessage(Message message)
/*     */   {
/*  86 */     switch (message.getId())
/*     */     {
/*     */     case 4306: 
/*  89 */       ReadyForFightMessage msg = (ReadyForFightMessage)message;
/*     */       
/*  91 */       if (msg.getCoachId() == DofusArenaGameEntity.getInstance().getLocalCoach().getId())
/*     */       {
/*     */ 
/*     */ 
/*  95 */         int options = 4;
/*  96 */         this.m_messageBoxControler = Xulor.getInstance().msgBox(DofusArenaTranslator.getInstance().getString("waitingForOpponents", new Object[0]), options | 0x20 | 0x1);
/*  97 */         this.m_messageBoxControler.addEventListener(new IMessageBoxEventListener()
/*     */         {
/*     */           public void messageBoxClosed(int type) {
/* 100 */             if (type == 4)
/*     */             {
/*     */ 
/* 103 */               FightCreationCancelMessage netMessage = new FightCreationCancelMessage();
/* 104 */               netMessage.setFightId(NetFightCreationFrame.this.m_fightId);
/* 105 */               DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage(netMessage);
/*     */             }
/*     */           }
/*     */         });
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 113 */       return false;
/*     */     
/*     */ 
/*     */     case 4310: 
/* 117 */       FightCreationCanceledMessage msg = (FightCreationCanceledMessage)message;
/*     */       
/*     */ 
/* 120 */       DofusArenaGameEntity.getInstance().pushFrame(NetFightInvitationFrame.getInstance());
/*     */       
/*     */ 
/*     */ 
/* 124 */       DofusArenaGameEntity.getInstance().removeFrame(NetFightFrame.getInstance());
/* 125 */       DofusArenaGameEntity.getInstance().removeFrame(UIFightTeamManagementFrame.getInstance());
/* 126 */       DofusArenaGameEntity.getInstance().removeFrame(UIRandomFightTeamManagementFrame.getInstance());
/* 127 */       DofusArenaGameEntity.getInstance().removeFrame(this);
/*     */       
/* 129 */       String readableMsg = "";
/* 130 */       switch (msg.getCancelReason())
/*     */       {
/*     */       case 34: 
/* 133 */         readableMsg = DofusArenaTranslator.getInstance().getString("error.fight.creation.unableToCreateFight", new Object[0]);
/* 134 */         break;
/*     */       case 35: 
/* 136 */         readableMsg = DofusArenaTranslator.getInstance().getString("error.fight.creation.targetDisconnected", new Object[0]);
/* 137 */         break;
/*     */       case 36: 
/* 139 */         readableMsg = DofusArenaTranslator.getInstance().getString("error.fight.creation.noSelectedTeam", new Object[0]);
/* 140 */         break;
/*     */       case 37: 
/* 142 */         readableMsg = DofusArenaTranslator.getInstance().getString("error.fight.creation.noPendingFight", new Object[0]);
/* 143 */         break;
/*     */       case 38: 
/* 145 */         readableMsg = DofusArenaTranslator.getInstance().getString("error.fight.creation.internalErrorDuringCreation", new Object[0]);
/* 146 */         break;
/*     */       case 39: 
/* 148 */         readableMsg = DofusArenaTranslator.getInstance().getString("error.fight.creation.noInstanceServer", new Object[0]);
/* 149 */         break;
/*     */       case 40: 
/* 151 */         readableMsg = DofusArenaTranslator.getInstance().getString("error.fight.creation.canceledByOpponent", new Object[0]);
/* 152 */         break;
/*     */       case 41: 
/* 154 */         readableMsg = DofusArenaTranslator.getInstance().getString("error.fight.creation.badFightParameters", new Object[0]);
/* 155 */         break;
/*     */       case 42: 
/* 157 */         readableMsg = DofusArenaTranslator.getInstance().getString("error.fight.creation.noSelectedFighter", new Object[0]);
/* 158 */         break;
/*     */       case 43: 
/* 160 */         readableMsg = DofusArenaTranslator.getInstance().getString("error.fight.creation.notEnoughFighters", new Object[0]);
/* 161 */         break;
/*     */       case 44: 
/* 163 */         readableMsg = DofusArenaTranslator.getInstance().getString("error.fight.creation.notEnoughCoach", new Object[0]);
/* 164 */         break;
/*     */       case 45: 
/* 166 */         readableMsg = DofusArenaTranslator.getInstance().getString("error.fight.creation.invalidFightersCount", new Object[0]);
/* 167 */         break;
/*     */       case 46: 
/* 169 */         readableMsg = DofusArenaTranslator.getInstance().getString("error.fight.creation.invalidTeamBudget", new Object[0]);
/* 170 */         break;
/*     */       case 47: 
/* 172 */         readableMsg = DofusArenaTranslator.getInstance().getString("error.fight.creation.cantHoldTheBet", new Object[0]);
/* 173 */         break;
/*     */       
/*     */       default: 
/* 176 */         readableMsg = "Erreur lors de la création du combat (" + msg.getCancelReason() + ")";
/*     */       }
/*     */       
/* 179 */       Xulor.getInstance().msgBox(readableMsg, 66);
/*     */       
/* 181 */       return false;
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
/*     */   public long getId()
/*     */   {
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
/*     */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded)
/*     */   {
/* 212 */     if (!isAboutToBeAdded)
/*     */     {
/*     */ 
/* 215 */       DofusArenaGameEntity.getInstance().removeFrame(UITeamManagementFrame.getInstance());
/* 216 */       DofusArenaGameEntity.getInstance().removeFrame(UICoachInventoryManagementFrame.getInstance());
/* 217 */       DofusArenaGameEntity.getInstance().removeFrame(UICoachStatisticsFrame.getInstance());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved)
/*     */   {
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


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\frame\NetFightCreationFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */