/*     */ package com.ankamagames.dofusarena.client.ui.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.CoachCard;
/*     */ import com.ankamagames.dofusarena.client.core.game.coach.LocalCoach;
/*     */ import com.ankamagames.dofusarena.client.core.game.exchange.CardTrade;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.exchange.ItemExchangeAddCardMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.exchange.ItemExchangeCancelMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.exchange.ItemExchangeRemoveCardMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.exchange.ItemExchangeSetReadyMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.Dialogs;
/*     */ import com.ankamagames.dofusarena.client.ui.actions.ExchangeActions;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.UIMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.coach.UICoachEquipmentMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.exchange.UIExchangeMoveCardMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.exchange.UIReadyForExchangeRequestMessage;
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
/*     */ public class UIExchangeFrame
/*     */   extends UIAbstractCoachInventoryManagementFrame
/*     */ {
/*  33 */   private static UIExchangeFrame m_instance = new UIExchangeFrame();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static UIExchangeFrame getInstance() {
/*  39 */     return m_instance; } public boolean onMessage(Message message) { UIReadyForExchangeRequestMessage exchangeRequestMessage; UIExchangeMoveCardMessage uIExchangeMoveCardMessage1; UICoachEquipmentMessage msg;
/*     */     UIExchangeMoveCardMessage exchangeMoveCardMessage;
/*     */     UIMessage uiMessage;
/*     */     ItemExchangeSetReadyMessage itemExchangeSetReadyMessage;
/*     */     ItemExchangeAddCardMessage itemExchangeAddCardMessage;
/*     */     LocalCoach localCoach;
/*     */     ItemExchangeRemoveCardMessage netMessage;
/*     */     long exchangeId;
/*     */     CoachCard equipment;
/*     */     ItemExchangeCancelMessage itemExchangeCancelMessage;
/*  49 */     switch (message.getId()) {
/*     */       
/*     */       case 16803:
/*  52 */         exchangeRequestMessage = (UIReadyForExchangeRequestMessage)message;
/*     */ 
/*     */         
/*  55 */         itemExchangeSetReadyMessage = new ItemExchangeSetReadyMessage();
/*  56 */         itemExchangeSetReadyMessage.setExchangeId(exchangeRequestMessage.getExchangeId());
/*     */         
/*  58 */         DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage((Message)itemExchangeSetReadyMessage);
/*     */         
/*  60 */         return false;
/*     */ 
/*     */       
/*     */       case 16807:
/*  64 */         uIExchangeMoveCardMessage1 = (UIExchangeMoveCardMessage)message;
/*     */ 
/*     */         
/*  67 */         itemExchangeAddCardMessage = new ItemExchangeAddCardMessage();
/*  68 */         itemExchangeAddCardMessage.setExchangeId(uIExchangeMoveCardMessage1.getExchangeId());
/*  69 */         itemExchangeAddCardMessage.setCardUniqueId(uIExchangeMoveCardMessage1.getCoachCard().getUniqueId());
/*  70 */         itemExchangeAddCardMessage.setCardQuantity((short)1);
/*     */         
/*  72 */         DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage((Message)itemExchangeAddCardMessage);
/*     */         
/*  74 */         return false;
/*     */ 
/*     */       
/*     */       case 16702:
/*  78 */         msg = (UICoachEquipmentMessage)message;
/*     */         
/*  80 */         localCoach = msg.getLocalCoach();
/*  81 */         equipment = msg.getEquipment();
/*  82 */         if (localCoach != null && equipment != null) {
/*     */           
/*  84 */           ItemExchangeAddCardMessage itemExchangeAddCardMessage1 = new ItemExchangeAddCardMessage();
/*  85 */           CardTrade exchange = (CardTrade)localCoach.getCurrentExchanger();
/*  86 */           if (exchange != null) {
/*  87 */             itemExchangeAddCardMessage1.setExchangeId(((Long)exchange.getFieldValue("exchangeId")).longValue());
/*  88 */             itemExchangeAddCardMessage1.setCardUniqueId(equipment.getUniqueId());
/*  89 */             itemExchangeAddCardMessage1.setCardQuantity((short)1);
/*     */             
/*  91 */             DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage((Message)itemExchangeAddCardMessage1);
/*     */           } 
/*     */         } 
/*     */         
/*  95 */         return false;
/*     */ 
/*     */       
/*     */       case 16808:
/*  99 */         exchangeMoveCardMessage = (UIExchangeMoveCardMessage)message;
/*     */ 
/*     */         
/* 102 */         netMessage = new ItemExchangeRemoveCardMessage();
/* 103 */         netMessage.setExchangeId(exchangeMoveCardMessage.getExchangeId());
/* 104 */         netMessage.setCardUniqueId(exchangeMoveCardMessage.getCoachCard().getUniqueId());
/* 105 */         netMessage.setCardQuantity((short)1);
/*     */         
/* 107 */         DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage((Message)netMessage);
/*     */         
/* 109 */         return false;
/*     */ 
/*     */       
/*     */       case 16809:
/* 113 */         uiMessage = (UIMessage)message;
/* 114 */         exchangeId = uiMessage.getLongValue();
/*     */ 
/*     */         
/* 117 */         itemExchangeCancelMessage = new ItemExchangeCancelMessage();
/* 118 */         itemExchangeCancelMessage.setExchangeId(exchangeId);
/*     */         
/* 120 */         DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage((Message)itemExchangeCancelMessage);
/*     */         
/* 122 */         return false;
/*     */     } 
/* 124 */     return super.onMessage(message); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getId() {
/* 134 */     return 0L;
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
/*     */   
/*     */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded) {
/* 154 */     if (!isAboutToBeAdded)
/*     */     {
/*     */       
/* 157 */       Xulor.getInstance().putActionClass("dofusarena.exchange", ExchangeActions.class);
/*     */     }
/* 159 */     super.onFrameAdd(frameHandler, isAboutToBeAdded);
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
/* 170 */     if (!isAboutToBeRemoved)
/*     */     {
/*     */       
/* 173 */       Xulor.getInstance().removeActionClass("dofusarena.exchange");
/*     */     }
/*     */     
/* 176 */     super.onFrameRemove(frameHandler, isAboutToBeRemoved);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void closeDialog() {
/* 186 */     Xulor.getInstance().unload("exchangeDialog");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void openDialog() {
/* 196 */     Xulor.getInstance().load("exchangeDialog", Dialogs.getDialogPath("exchangeDialog"), 129L, (short)10001);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\clien\\ui\protocol\frame\UIExchangeFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */