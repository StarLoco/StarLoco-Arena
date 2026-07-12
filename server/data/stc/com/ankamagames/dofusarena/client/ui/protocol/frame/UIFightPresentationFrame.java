/*     */ package com.ankamagames.dofusarena.client.ui.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.NetworkEntity;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.CoachCard;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.fight.TeamMateSetReadyForPlacementRequestMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.Dialogs;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.coach.UICoachEquipmentMessage;
/*     */ import com.ankamagames.framework.kernel.FrameHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.events.MessageFrame;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.property.PropertiesProvider;
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
/*     */ public class UIFightPresentationFrame
/*     */   implements MessageFrame
/*     */ {
/*  28 */   private static UIFightPresentationFrame m_instance = new UIFightPresentationFrame();
/*     */   
/*     */ 
/*     */ 
/*     */   public static UIFightPresentationFrame getInstance()
/*     */   {
/*  34 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onMessage(Message message)
/*     */   {
/*  43 */     switch (message.getId())
/*     */     {
/*     */ 
/*     */ 
/*     */     case 18009: 
/*  48 */       TeamMateSetReadyForPlacementRequestMessage netMessage = new TeamMateSetReadyForPlacementRequestMessage();
/*  49 */       DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage(netMessage);
/*     */       
/*  51 */       return false;
/*     */     
/*     */ 
/*     */     case 16700: 
/*  55 */       UICoachEquipmentMessage msg = (UICoachEquipmentMessage)message;
/*     */       
/*  57 */       CoachCard equipment = msg.getEquipment();
/*  58 */       if (equipment != null)
/*     */       {
/*  60 */         Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("singleCardData", equipment);
/*     */       }
/*  62 */       return false;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     case 16701: 
/*  68 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("singleCardData", null);
/*     */       
/*  70 */       return false;
/*     */     }
/*     */     
/*     */     
/*  74 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getId()
/*     */   {
/*  83 */     return 0L;
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
/* 101 */     if (!isAboutToBeAdded)
/*     */     {
/*     */ 
/* 104 */       Xulor.getInstance().load("fightPresentationDialog", Dialogs.getDialogPath("fightPresentationDialog"), 1L, (short)10100);
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
/* 116 */     if (!isAboutToBeRemoved)
/*     */     {
/*     */ 
/* 119 */       Xulor.getInstance().unload("fightPresentationDialog");
/*     */       
/*     */ 
/* 122 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("singleCardData", null);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\protocol\frame\UIFightPresentationFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */