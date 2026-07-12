/*     */ package com.ankamagames.dofusarena.client.ui.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.baseImpl.graphicalClient.ui.shortcuts.ShortcutManager;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.CoachCard;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.filter.CoachCardFilter;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.filter.PetFilter;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.filter.SetFilter;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.filter.TypeFilter;
/*     */ import com.ankamagames.dofusarena.client.core.game.coach.LocalCoach;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.coach.UICoachEquipmentMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.coach.UILocalCoachMessage;
/*     */ import com.ankamagames.dofusarena.common.game.card.CoachCardType;
/*     */ import com.ankamagames.framework.kernel.FrameHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Worker;
/*     */ import com.ankamagames.framework.kernel.events.MessageFrame;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.core.messagebox.IMessageBoxEventListener;
/*     */ import com.ankamagames.xulor.core.messagebox.MessageBoxControler;
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
/*     */ 
/*     */ 
/*     */ public abstract class UIAbstractCoachInventoryManagementFrame
/*     */   extends UIAbstractCoachManagementFrame
/*     */ {
/*  39 */   private MessageBoxControler m_removeEquipmentMessageBoxControler = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onMessage(Message message)
/*     */   {
/*  48 */     switch (message.getId())
/*     */     {
/*     */     case 16700: 
/*  51 */       UICoachEquipmentMessage msg = (UICoachEquipmentMessage)message;
/*     */       
/*  53 */       CoachCard equipment = msg.getEquipment();
/*  54 */       if (equipment != null)
/*     */       {
/*  56 */         Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("coachManagement.selectedCard", equipment);
/*     */       }
/*  58 */       return false;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     case 16701: 
/*  64 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("coachManagement.selectedCard", null);
/*     */       
/*  66 */       return false;
/*     */     
/*     */ 
/*     */     case 16704: 
/*  70 */       UILocalCoachMessage msg = (UILocalCoachMessage)message;
/*     */       
/*  72 */       LocalCoach localCoach = msg.getLocalCoach();
/*  73 */       CoachCardType type = CoachCardType.getFromId(msg.getIntValue());
/*  74 */       if (localCoach != null) {
/*  75 */         CoachCardFilter filter = localCoach.getCardTypeFilter();
/*  76 */         if ((filter instanceof TypeFilter)) {
/*  77 */           TypeFilter typeFilter = (TypeFilter)filter;
/*  78 */           if (typeFilter.contains(type)) {
/*  79 */             typeFilter.removeType(type);
/*     */           } else {
/*  81 */             typeFilter.addType(type);
/*     */           }
/*     */         }
/*  84 */         localCoach.updateFiltredInventoryProperty();
/*     */       }
/*  86 */       return false;
/*     */     
/*     */ 
/*     */     case 16712: 
/*  90 */       UILocalCoachMessage msg = (UILocalCoachMessage)message;
/*     */       
/*  92 */       LocalCoach localCoach = msg.getLocalCoach();
/*  93 */       CoachCardType type = CoachCardType.getFromId(msg.getIntValue());
/*  94 */       if (localCoach != null) {
/*  95 */         CoachCardFilter filter = localCoach.getPetCardFilter();
/*  96 */         if ((filter instanceof PetFilter)) {
/*  97 */           PetFilter petFilter = (PetFilter)filter;
/*  98 */           if (petFilter.contains(type)) {
/*  99 */             petFilter.removeType(type);
/*     */           } else {
/* 101 */             petFilter.addType(type);
/*     */           }
/*     */         }
/* 104 */         localCoach.updateFiltredInventoryProperty();
/*     */       }
/* 106 */       return false;
/*     */     
/*     */ 
/*     */     case 16705: 
/* 110 */       UILocalCoachMessage msg = (UILocalCoachMessage)message;
/*     */       
/* 112 */       LocalCoach localCoach = msg.getLocalCoach();
/* 113 */       if (localCoach != null) {
/* 114 */         CoachCardFilter filter = localCoach.getCardTypeFilter();
/* 115 */         if ((filter instanceof TypeFilter)) {
/* 116 */           TypeFilter typeFilter = (TypeFilter)filter;
/* 117 */           if (typeFilter.isEmpty()) {
/* 118 */             typeFilter.addAll();
/*     */           } else {
/* 120 */             typeFilter.removeAll();
/*     */           }
/*     */         }
/* 123 */         localCoach.updateFiltredInventoryProperty();
/*     */       }
/* 125 */       return false;
/*     */     
/*     */ 
/*     */     case 16713: 
/* 129 */       UILocalCoachMessage msg = (UILocalCoachMessage)message;
/*     */       
/* 131 */       LocalCoach localCoach = msg.getLocalCoach();
/* 132 */       if (localCoach != null) {
/* 133 */         CoachCardFilter filter = localCoach.getPetCardFilter();
/* 134 */         if ((filter instanceof PetFilter)) {
/* 135 */           PetFilter petFilter = (PetFilter)filter;
/* 136 */           if (petFilter.isEmpty()) {
/* 137 */             petFilter.addAll();
/*     */           } else {
/* 139 */             petFilter.removeAll();
/*     */           }
/*     */         }
/* 142 */         localCoach.updateFiltredInventoryProperty();
/*     */       }
/* 144 */       return false;
/*     */     
/*     */ 
/*     */     case 16711: 
/* 148 */       UILocalCoachMessage msg = (UILocalCoachMessage)message;
/*     */       
/* 150 */       LocalCoach localCoach = msg.getLocalCoach();
/* 151 */       if (localCoach != null) {
/* 152 */         CoachCardFilter filter = localCoach.getSetCardFilter();
/* 153 */         if ((filter instanceof SetFilter)) {
/* 154 */           SetFilter setFilter = (SetFilter)filter;
/* 155 */           setFilter.setCurrentSetName(msg.getStringValue());
/* 156 */           localCoach.updateFiltredInventoryProperty();
/*     */         }
/*     */       }
/* 159 */       return false;
/*     */     
/*     */ 
/*     */     case 16714: 
/* 163 */       UILocalCoachMessage msg = (UILocalCoachMessage)message;
/*     */       
/* 165 */       LocalCoach localCoach = msg.getLocalCoach();
/* 166 */       if (localCoach != null) {
/* 167 */         localCoach.setSelectedCostFilter(msg.getStringValue());
/* 168 */         localCoach.updateFiltredInventoryProperty();
/*     */       }
/* 170 */       return false;
/*     */     
/*     */ 
/*     */     case 16706: 
/* 174 */       UICoachEquipmentMessage msg = (UICoachEquipmentMessage)message;
/*     */       
/* 176 */       final LocalCoach localCoach = msg.getLocalCoach();
/* 177 */       final CoachCard equipment = msg.getEquipment();
/* 178 */       if ((localCoach != null) && (equipment != null))
/*     */       {
/*     */ 
/* 181 */         String question = DofusArenaTranslator.getInstance().getString("question.deleteCoachEquipment", new Object[] { Short.valueOf(equipment.getQuantity()), equipment.getName() });
/*     */         
/*     */ 
/*     */ 
/* 185 */         this.m_removeEquipmentMessageBoxControler = Xulor.getInstance().msgBox(question, 152);
/* 186 */         this.m_removeEquipmentMessageBoxControler.addEventListener(new IMessageBoxEventListener() {
/*     */           public void messageBoxClosed(int type) {
/* 188 */             if (type == 8)
/*     */             {
/*     */ 
/* 191 */               UICoachEquipmentMessage uiMessage = new UICoachEquipmentMessage();
/* 192 */               uiMessage.setCoach(localCoach);
/* 193 */               uiMessage.setEquipment(equipment);
/* 194 */               uiMessage.setId(16707);
/* 195 */               Worker.getInstance().pushMessage(uiMessage);
/*     */               
/* 197 */               UIAbstractCoachInventoryManagementFrame.this.m_removeEquipmentMessageBoxControler = null;
/*     */             }
/*     */           }
/*     */         });
/*     */       }
/*     */       
/*     */ 
/* 204 */       return false;
/*     */     
/*     */ 
/*     */     case 16707: 
/* 208 */       UICoachEquipmentMessage msg = (UICoachEquipmentMessage)message;
/*     */       
/* 210 */       LocalCoach localCoach = msg.getLocalCoach();
/* 211 */       CoachCard equipment = msg.getEquipment();
/* 212 */       if ((localCoach != null) && (equipment != null)) {
/* 213 */         localCoach.deleteEquipment(equipment);
/*     */       }
/*     */       
/* 216 */       return false;
/*     */     
/*     */ 
/*     */     case 16708: 
/* 220 */       UICoachEquipmentMessage msg = (UICoachEquipmentMessage)message;
/*     */       
/* 222 */       LocalCoach localCoach = msg.getLocalCoach();
/* 223 */       CoachCard equipment = msg.getEquipment();
/* 224 */       if ((localCoach != null) && (equipment != null)) {
/* 225 */         if (equipment.isLocked()) {
/* 226 */           localCoach.unlockEquipment(equipment);
/*     */         } else {
/* 228 */           localCoach.lockEquipment(equipment);
/*     */         }
/*     */       }
/*     */       
/* 232 */       return false;
/*     */     }
/*     */     
/*     */     
/* 236 */     return super.onMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded)
/*     */   {
/* 248 */     if (!isAboutToBeAdded)
/*     */     {
/*     */ 
/* 251 */       DofusArenaGameEntity.getInstance().getLocalCoach().populateEditableInventories();
/*     */       
/*     */ 
/* 254 */       ShortcutManager.getInstance().enableGroup("world", false);
/*     */     }
/*     */     
/*     */ 
/* 258 */     super.onFrameAdd(frameHandler, isAboutToBeAdded);
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
/* 269 */     if (!isAboutToBeRemoved)
/*     */     {
/*     */ 
/* 272 */       if (this.m_removeEquipmentMessageBoxControler != null) {
/* 273 */         Xulor.getInstance().unload(this.m_removeEquipmentMessageBoxControler.getMessageBoxId());
/* 274 */         this.m_removeEquipmentMessageBoxControler = null;
/*     */       }
/*     */       
/*     */ 
/* 278 */       int countInventoryManagementFrame = 0;
/* 279 */       for (MessageFrame frame : DofusArenaGameEntity.getInstance().getFrames()) {
/* 280 */         if ((frame instanceof UIAbstractCoachInventoryManagementFrame)) {
/* 281 */           countInventoryManagementFrame++;
/*     */         }
/*     */       }
/*     */       
/* 285 */       if (countInventoryManagementFrame == 0) {
/* 286 */         DofusArenaGameEntity.getInstance().getLocalCoach().cleanEditableInventories();
/*     */       }
/*     */       
/* 289 */       ShortcutManager.getInstance().enableGroup("world", DofusArenaGameEntity.getInstance().getFight() == null);
/*     */     }
/*     */     
/* 292 */     super.onFrameRemove(frameHandler, isAboutToBeRemoved);
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\protocol\frame\UIAbstractCoachInventoryManagementFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */