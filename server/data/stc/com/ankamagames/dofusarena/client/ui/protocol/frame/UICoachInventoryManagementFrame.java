/*     */ package com.ankamagames.dofusarena.client.ui.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.NetworkEntity;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.CoachCard;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.EquipedCoachCard;
/*     */ import com.ankamagames.dofusarena.client.core.game.coach.LocalCoach;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.coachManagement.CoachEquipmentUpdateRequestMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.coachManagement.CoachInventoryUpdateRequestMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.Dialogs;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.coach.UICoachEquipmentMessage;
/*     */ import com.ankamagames.dofusarena.common.game.card.CoachCardInventories;
/*     */ import com.ankamagames.dofusarena.common.game.card.CoachCardType;
/*     */ import com.ankamagames.framework.kernel.FrameHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.property.PropertiesProvider;
/*     */ import java.util.ArrayList;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UICoachInventoryManagementFrame
/*     */   extends UIAbstractCoachInventoryManagementFrame
/*     */ {
/*  31 */   private static Logger m_logger = Logger.getLogger(UICoachInventoryManagementFrame.class);
/*     */   
/*  33 */   private static UICoachInventoryManagementFrame m_instance = new UICoachInventoryManagementFrame();
/*     */   
/*     */ 
/*     */ 
/*     */   public static UICoachInventoryManagementFrame getInstance()
/*     */   {
/*  39 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onMessage(Message message)
/*     */   {
/*  49 */     switch (message.getId())
/*     */     {
/*     */     case 20008: 
/*  52 */       if (Xulor.getInstance().isLoaded("coachEquipmentDialog")) {
/*  53 */         Xulor.getInstance().unload("coachEquipmentDialog");
/*     */       } else {
/*  55 */         Xulor.getInstance().load("coachEquipmentDialog", Dialogs.getDialogPath("coachEquipmentDialog"), 129L, (short)10001);
/*     */       }
/*     */       
/*  58 */       return false;
/*     */     
/*     */ 
/*     */     case 20010: 
/*  62 */       if (Xulor.getInstance().isLoaded("fusionLaboratoryDialog")) {
/*  63 */         Xulor.getInstance().unload("fusionLaboratoryDialog");
/*     */       } else {
/*  65 */         Xulor.getInstance().load("fusionLaboratoryDialog", Dialogs.getDialogPath("fusionLaboratoryDialog"), 129L, (short)10001);
/*     */       }
/*     */       
/*  68 */       return false;
/*     */     
/*     */ 
/*     */     case 20012: 
/*  72 */       if (Xulor.getInstance().isLoaded("fusionLaboratoryDialog")) {
/*  73 */         Xulor.getInstance().unload("fusionLaboratoryDialog");
/*     */       } else {
/*  75 */         Xulor.getInstance().load("fusionLaboratoryDialog", Dialogs.getDialogPath("fusionLaboratoryDialog"), 129L, (short)10001);
/*     */       }
/*     */       
/*  78 */       return false;
/*     */     
/*     */ 
/*     */     case 16702: 
/*  82 */       UICoachEquipmentMessage msg = (UICoachEquipmentMessage)message;
/*     */       
/*  84 */       LocalCoach localCoach = msg.getLocalCoach();
/*  85 */       CoachCard equipment = msg.getEquipment();
/*  86 */       if ((localCoach != null) && (equipment != null)) {
/*  87 */         short destPosition = -1;
/*  88 */         if (msg.getPosition() != -1) {
/*  89 */           destPosition = msg.getPosition();
/*     */         }
/*     */         else {
/*  92 */           short[] positions = equipment.getType().getInventoryPosition();
/*  93 */           if (positions != null) {
/*  94 */             if (positions.length > 1)
/*     */             {
/*     */               short[] arrayOfShort1;
/*  97 */               int j = (arrayOfShort1 = positions).length; for (int i = 0; i < j; i++) { short position = arrayOfShort1[i];
/*  98 */                 if (localCoach.getEditableCoachCardInventories().getEquipmentAt(position) == null) {
/*  99 */                   destPosition = position;
/* 100 */                   break;
/*     */                 }
/*     */               }
/* 103 */               if (destPosition == -1)
/*     */               {
/*     */ 
/* 106 */                 j = (arrayOfShort1 = positions).length; for (i = 0; i < j; i++) { short position = arrayOfShort1[i];
/* 107 */                   EquipedCoachCard equipedCoachCard = (EquipedCoachCard)localCoach.getEditableCoachCardInventories().getEquipmentAt(position);
/* 108 */                   if ((equipedCoachCard != null) && (equipedCoachCard.getReferenceId() != equipment.getReferenceId())) {
/* 109 */                     destPosition = position;
/* 110 */                     break;
/*     */                   }
/*     */                 }
/*     */               }
/* 114 */               if (destPosition == -1) {
/* 115 */                 destPosition = positions[0];
/*     */               }
/* 117 */             } else if (positions.length != 0)
/*     */             {
/*     */ 
/* 120 */               destPosition = positions[0];
/*     */             }
/*     */           }
/*     */         }
/* 124 */         if (destPosition != -1) {
/* 125 */           localCoach.addEquipment(equipment, destPosition);
/*     */         } else {
/* 127 */           m_logger.error("Position d'équipement invalide : " + destPosition);
/*     */         }
/*     */       }
/*     */       
/* 131 */       return false;
/*     */     
/*     */ 
/*     */     case 16703: 
/* 135 */       UICoachEquipmentMessage msg = (UICoachEquipmentMessage)message;
/*     */       
/* 137 */       LocalCoach localCoach = msg.getLocalCoach();
/* 138 */       EquipedCoachCard equipment = (EquipedCoachCard)msg.getEquipment();
/* 139 */       if ((localCoach != null) && (equipment != null)) {
/* 140 */         localCoach.removeEquipment(equipment);
/*     */       }
/*     */       
/* 143 */       return false;
/*     */     
/*     */ 
/*     */     case 16709: 
/* 147 */       UICoachEquipmentMessage msg = (UICoachEquipmentMessage)message;
/*     */       
/* 149 */       LocalCoach localCoach = msg.getLocalCoach();
/* 150 */       CoachCard emote = msg.getEquipment();
/* 151 */       if ((localCoach != null) && (emote != null)) {
/* 152 */         localCoach.addEmote(emote);
/*     */       }
/*     */       
/* 155 */       return false;
/*     */     
/*     */ 
/*     */     case 16710: 
/* 159 */       UICoachEquipmentMessage msg = (UICoachEquipmentMessage)message;
/*     */       
/* 161 */       LocalCoach localCoach = msg.getLocalCoach();
/* 162 */       EquipedCoachCard emote = (EquipedCoachCard)msg.getEquipment();
/* 163 */       if ((localCoach != null) && (emote != null)) {
/* 164 */         localCoach.removeEmote(emote);
/*     */       }
/*     */       
/* 167 */       return false;
/*     */     }
/*     */     
/*     */     
/* 171 */     return super.onMessage(message);
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
/* 182 */     if (!isAboutToBeRemoved)
/*     */     {
/*     */ 
/* 185 */       LocalCoach localCoach = DofusArenaGameEntity.getInstance().getLocalCoach();
/*     */       
/*     */ 
/* 188 */       if (localCoach.isEditableEquipmentChanged())
/*     */       {
/* 190 */         localCoach.setInventorySynchronized(false);
/* 191 */         long[] equipmentArray = new long[14];
/* 192 */         for (short i = 0; i < 14; i = (short)(i + 1)) {
/* 193 */           EquipedCoachCard equipment = (EquipedCoachCard)localCoach.getEditableCoachCardInventories().getEquipmentAt(i);
/* 194 */           if (equipment != null) {
/* 195 */             equipmentArray[i] = equipment.getReferenceUniqueId();
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 200 */         CoachEquipmentUpdateRequestMessage netMessage = new CoachEquipmentUpdateRequestMessage();
/* 201 */         netMessage.setEquipmentArray(equipmentArray);
/* 202 */         DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage(netMessage);
/*     */       }
/*     */       
/*     */ 
/* 206 */       if (localCoach.isEditableInventoryChanged()) {
/* 207 */         ArrayList<CoachCard> removedCards = new ArrayList();
/* 208 */         ArrayList<CoachCard> lockedCards = new ArrayList();
/* 209 */         ArrayList<CoachCard> unlockedCards = new ArrayList();
/* 210 */         for (CoachCard coachCard : localCoach.getCardInventories().getInventoryCards()) {
/* 211 */           CoachCard editableCoachCard = (CoachCard)localCoach.getEditableCoachCardInventories().getFromInventory(coachCard.getUniqueId());
/* 212 */           if (editableCoachCard == null) {
/* 213 */             removedCards.add(coachCard);
/*     */           }
/* 215 */           else if ((!editableCoachCard.isLocked()) && (coachCard.isLocked())) {
/* 216 */             unlockedCards.add(coachCard);
/* 217 */           } else if ((editableCoachCard.isLocked()) && (!coachCard.isLocked())) {
/* 218 */             lockedCards.add(coachCard);
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 224 */         CoachInventoryUpdateRequestMessage netMessage = new CoachInventoryUpdateRequestMessage();
/* 225 */         netMessage.setRemovedCardsArray(removedCards);
/* 226 */         netMessage.setLockedCardsArray(lockedCards);
/* 227 */         netMessage.setUnlockedCardsArray(unlockedCards);
/* 228 */         DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage(netMessage);
/*     */       }
/*     */     }
/*     */     
/* 232 */     super.onFrameRemove(frameHandler, isAboutToBeRemoved);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void openDialog()
/*     */   {
/* 242 */     Xulor.getInstance().load("coachInventoryDialog", Dialogs.getDialogPath("coachInventoryDialog"), 129L, (short)10001);
/* 243 */     Xulor.getInstance().load("coachEquipmentDialog", Dialogs.getDialogPath("coachEquipmentDialog"), 129L, (short)10001);
/*     */     
/* 245 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("menuBar.teamManagementButton", Boolean.valueOf(false));
/* 246 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("menuBar.coachStatisticsButton", Boolean.valueOf(false));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void closeDialog()
/*     */   {
/* 256 */     Xulor.getInstance().unload("coachInventoryDialog");
/* 257 */     Xulor.getInstance().unload("coachEquipmentDialog");
/* 258 */     Xulor.getInstance().unload("fusionLaboratoryDialog");
/*     */     
/* 260 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("menuBar.teamManagementButton", Boolean.valueOf(true));
/* 261 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("menuBar.coachStatisticsButton", Boolean.valueOf(true));
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\protocol\frame\UICoachInventoryManagementFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */