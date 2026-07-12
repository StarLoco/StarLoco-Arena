/*     */ package com.ankamagames.dofusarena.client.network.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.world.serverToClient.OpponentFoundMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.world.serverToClient.OpponentSearchErrorMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.Dialogs;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UIRandomFightCreationFrame;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UIRandomFightTeamManagementFrame;
/*     */ import com.ankamagames.dofusarena.common.game.fight.FightDefinitionManager;
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
/*     */ public class NetRandomFightFrame
/*     */   implements MessageFrame
/*     */ {
/*  31 */   protected static final Logger m_logger = Logger.getLogger(NetRandomFightFrame.class);
/*     */   
/*  33 */   private static NetRandomFightFrame m_instance = new NetRandomFightFrame();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NetRandomFightFrame getInstance() {
/*  39 */     return m_instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onMessage(Message message) {
/*     */     OpponentFoundMessage opponentFoundMessage;
/*     */     OpponentSearchErrorMessage msg;
/*  48 */     switch (message.getId()) {
/*     */       
/*     */       case 2300:
/*  51 */         opponentFoundMessage = (OpponentFoundMessage)message;
/*     */ 
/*     */         
/*  54 */         NetFightCreationFrame.getInstance().setFightId(opponentFoundMessage.getFightId());
/*  55 */         NetFightCreationFrame.getInstance().setFightDefinition(FightDefinitionManager.getInstance().getDefinitionFromFightTypeId((byte)1));
/*  56 */         NetFightCreationFrame.getInstance().setCancelableFight(false);
/*     */ 
/*     */         
/*  59 */         DofusArenaGameEntity.getInstance().removeFrame(this);
/*     */ 
/*     */         
/*  62 */         DofusArenaGameEntity.getInstance().pushFrame(NetFightCreationFrame.getInstance());
/*  63 */         DofusArenaGameEntity.getInstance().pushFrame((MessageFrame)UIRandomFightTeamManagementFrame.getInstance());
/*     */         
/*  65 */         return false;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 2304:
/*  71 */         DofusArenaGameEntity.getInstance().setRandomFightSearchInProgress(true);
/*     */ 
/*     */         
/*  74 */         Xulor.getInstance().unload("randomFightCreationDialog");
/*     */ 
/*     */         
/*  77 */         Xulor.getInstance().load("randomFightSearchStatusDialog", Dialogs.getDialogPath("randomFightSearchStatusDialog"), (short)12000);
/*     */         
/*  79 */         return false;
/*     */ 
/*     */       
/*     */       case 2302:
/*  83 */         msg = (OpponentSearchErrorMessage)message;
/*     */ 
/*     */         
/*  86 */         Xulor.getInstance().msgBox("Erreur lors de la recherche d'un combat aléatoire " + msg.getErrorCode() + ")", 66);
/*     */ 
/*     */         
/*  89 */         DofusArenaGameEntity.getInstance().removeFrame(this);
/*     */         
/*  91 */         return false;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 2306:
/*  97 */         DofusArenaGameEntity.getInstance().removeFrame(this);
/*     */         
/*  99 */         return false;
/*     */     } 
/*     */     
/* 102 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getId() {
/* 111 */     return 0L;
/*     */   }
/*     */ 
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
/*     */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved) {
/* 138 */     if (!isAboutToBeRemoved)
/*     */     {
/*     */       
/* 141 */       DofusArenaGameEntity.getInstance().removeFrame((MessageFrame)UIRandomFightCreationFrame.getInstance());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 146 */     DofusArenaGameEntity.getInstance().setRandomFightSearchInProgress(false);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\frame\NetRandomFightFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */