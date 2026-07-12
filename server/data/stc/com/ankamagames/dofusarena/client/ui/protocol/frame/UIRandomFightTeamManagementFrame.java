/*     */ package com.ankamagames.dofusarena.client.ui.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.NetworkEntity;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*     */ import com.ankamagames.dofusarena.client.core.game.team.EditableTeamPreset;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.frame.NetFightFrame;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.world.clientToServer.FightCreationCancelMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.world.clientToServer.SetReadyForFightMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.Dialogs;
/*     */ import com.ankamagames.dofusarena.client.ui.actions.FightCreationActions;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.teamManagement.UIFightMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.teamManagement.UIReadyForFightRequestMessage;
/*     */ import com.ankamagames.framework.kernel.FrameHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.xulor.Xulor;
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
/*     */ public class UIRandomFightTeamManagementFrame
/*     */   extends UITeamManagementFrame
/*     */ {
/*  30 */   private static UIRandomFightTeamManagementFrame m_instance = new UIRandomFightTeamManagementFrame();
/*     */   
/*     */ 
/*     */ 
/*     */   public static UIRandomFightTeamManagementFrame getInstance()
/*     */   {
/*  36 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onMessage(Message message)
/*     */   {
/*  46 */     switch (message.getId()) {
/*     */     case 16601: 
/*  48 */       UIFightMessage msg = (UIFightMessage)message;
/*     */       
/*     */ 
/*  51 */       FightCreationCancelMessage netMessage = new FightCreationCancelMessage();
/*  52 */       netMessage.setFightId(msg.getFightId());
/*  53 */       DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage(netMessage);
/*     */       
/*  55 */       return false;
/*     */     
/*     */ 
/*     */     case 16600: 
/*  59 */       UIReadyForFightRequestMessage msg = (UIReadyForFightRequestMessage)message;
/*     */       
/*  61 */       if (msg.getTeamPreset() == null)
/*     */       {
/*  63 */         Xulor.getInstance().msgBox(DofusArenaTranslator.getInstance().getString("error.teamManagement.teamEmpty", new Object[0]), 66);
/*  64 */         return false;
/*     */       }
/*     */       
/*  67 */       if (msg.getTeamPreset().isEmpty())
/*     */       {
/*  69 */         Xulor.getInstance().msgBox(DofusArenaTranslator.getInstance().getString("error.teamManagement.teamEmpty", new Object[0]), 66);
/*  70 */         return false;
/*     */       }
/*     */       
/*  73 */       int teamValue = msg.getTeamPreset().getValue();
/*  74 */       if (teamValue > 5000) {
/*  75 */         Xulor.getInstance().msgBox(DofusArenaTranslator.getInstance().getString("error.teamManagement.budgetExploded", new Object[] { Integer.valueOf(teamValue), Integer.valueOf(5000) }), 
/*  76 */           66);
/*  77 */         return false;
/*     */       }
/*     */       
/*     */ 
/*  81 */       DofusArenaGameEntity.getInstance().pushFrame(NetFightFrame.getInstance());
/*     */       
/*     */ 
/*  84 */       SetReadyForFightMessage netMessage = new SetReadyForFightMessage();
/*  85 */       netMessage.setFightId(msg.getFightId());
/*  86 */       netMessage.setTeamPreset(msg.getTeamPreset());
/*  87 */       DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage(netMessage);
/*     */       
/*  89 */       return false;
/*     */     }
/*     */     
/*     */     
/*  93 */     return super.onMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded)
/*     */   {
/* 104 */     super.onFrameAdd(frameHandler, isAboutToBeAdded);
/* 105 */     if (!isAboutToBeAdded)
/*     */     {
/*     */ 
/* 108 */       Xulor.getInstance().putActionClass("dofusarena.fightCreation", FightCreationActions.class);
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
/*     */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved)
/*     */   {
/* 121 */     super.onFrameRemove(frameHandler, isAboutToBeRemoved);
/* 122 */     if (!isAboutToBeRemoved)
/*     */     {
/*     */ 
/* 125 */       Xulor.getInstance().removeActionClass("dofusarena.fightCreation");
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
/*     */   protected void closeDialog()
/*     */   {
/* 138 */     Xulor.getInstance().unload("randomFightTeamManagementDialog");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void openDialog()
/*     */   {
/* 149 */     Xulor.getInstance().load("randomFightTeamManagementDialog", Dialogs.getDialogPath("randomFightTeamManagementDialog"), 128L, (short)10001);
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\protocol\frame\UIRandomFightTeamManagementFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */