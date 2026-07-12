/*     */ package com.ankamagames.dofusarena.client.core.game.coach;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.InventoryContentProvider;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event.InventoryEvent;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event.InventoryObserver;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event.ItemExchangerEndEvent;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event.ItemExchangerEvent;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event.ItemExchangerModifiedEvent;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.CoachCard;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.CoachCardProvider;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.EquipedCoachCard;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.EquipedCoachCardProvider;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.filter.CoachCardFilter;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.filter.CostFilter;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.filter.EmoteFilter;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.filter.PetFilter;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.filter.SetFilter;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.filter.TypeFilter;
/*     */ import com.ankamagames.dofusarena.client.core.game.exchange.CardTrade;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.frame.NetExchangeFrame;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UIExchangeFrame;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UIExchangeInvitationFrame;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.exchange.UIExchangeInvitationAcceptRequestMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.exchange.UIExchangeInvitationRejectRequestMessage;
/*     */ import com.ankamagames.dofusarena.common.game.card.AbstractCoachCard;
/*     */ import com.ankamagames.dofusarena.common.game.card.CoachCardInventories;
/*     */ import com.ankamagames.dofusarena.common.game.card.CoachCardType;
/*     */ import com.ankamagames.dofusarena.common.game.statistics.PlayerStatisticsReport;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Worker;
/*     */ import com.ankamagames.framework.kernel.events.MessageFrame;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.core.messagebox.IMessageBoxEventListener;
/*     */ import com.ankamagames.xulor.core.messagebox.MessageBoxControler;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LocalCoach
/*     */   extends Coach
/*     */ {
/*     */   public static final String CARD_INVENTORY_FIELD = "cardInventory";
/*     */   public static final String FILTRED_EQUIPMENT_CARD_INVENTORY_FIELD = "filtredEquipmentCardInventory";
/*     */   public static final String CARD_SETS_FIELD = "cardSets";
/*     */   public static final String FILTRED_SET_CARD_INVENTORY_FIELD = "filtredSetCardInventory";
/*     */   public static final String CARD_PETS_FIELD = "cardPets";
/*     */   public static final String FILTRED_PET_CARD_INVENTORY_FIELD = "filtredPetCardInventory";
/*     */   public static final String CARD_COST_FILTER_LIST_FIELD = "cardCostFilterList";
/*     */   public static final String SELECTED_COST_FILTER_FIELD = "selectedCostFilter";
/*     */   public static final String EMOTE_INVENTORY_FIELD = "emoteInventory";
/*     */   public static final String FILTRED_EMOTE_CARD_INVENTORY_FIELD = "filtredEquipmentCardInventory";
/*     */   public static final String EQUIPED_EMOTES_FIELD = "equipedEmotes";
/*     */   public static final String LOCKED_CARDS_COUNT_FIELD = "lockedCardsCount";
/*     */   public static final String LOCKED_CARDS_MAX_COUNT_FIELD = "lockedCardsMaxCount";
/*     */   public static final String INVENTORY_SYNCHRONIZED_FIELD = "inventorySynchronized";
/*     */   public static final String PANT_EQUIPMENT_FIELD = "pantEquipment";
/*     */   public static final String HAIRS_EQUIPMENT_FIELD = "hairsEquipment";
/*     */   public static final String TATOO_EQUIPMENT_FIELD = "tatooEquipment";
/*     */   public static final String ARMBAND_LEFT_EQUIPMENT_FIELD = "armbandLeftEquipment";
/*     */   public static final String ARMBAND_RIGHT_EQUIPMENT_FIELD = "armbandRightEquipment";
/*     */   public static final String SHOES_EQUIPMENT_FIELD = "shoesEquipment";
/*     */   public static final String SHOULDERPAD_LEFT_EQUIPMENT_FIELD = "shoulderpadLeftEquipment";
/*     */   public static final String SHOULDERPAD_RIGHT_EQUIPMENT_FIELD = "shoulderpadRightEquipment";
/*     */   public static final String CLOAK_EQUIPMENT_FIELD = "cloakEquipment";
/*     */   public static final String TROUSERS_EQUIPMENT_FIELD = "trousersEquipment";
/*     */   public static final String SHIR_EQUIPMENT_FIELD = "shirEquipment";
/*     */   public static final String HAT_EQUIPMENT_FIELD = "hatEquipment";
/*     */   public static final String STAFF_EQUIPMENT_FIELD = "staffEquipment";
/*     */   public static final String PET_EQUIPMENT_FIELD = "petEquipment";
/*     */   public static final String CURSE_EQUIPMENT_FIELD = "cureEquipment";
/*  81 */   public static final String[] FIELDS = new String[] { 
/*  82 */       "cardInventory", "filtredEquipmentCardInventory", 
/*     */       
/*  84 */       "cardSets", "filtredSetCardInventory", 
/*     */       
/*  86 */       "cardPets", "filtredPetCardInventory", 
/*     */       
/*  88 */       "cardCostFilterList", "selectedCostFilter", 
/*     */       
/*  90 */       "emoteInventory", 
/*  91 */       "equipedEmotes",
/*     */       
/*  93 */       "lockedCardsCount", 
/*  94 */       "lockedCardsMaxCount", 
/*     */       
/*  96 */       "inventorySynchronized", 
/*     */       
/*  98 */       "pantEquipment", 
/*  99 */       "hairsEquipment", 
/* 100 */       "tatooEquipment", 
/* 101 */       "armbandLeftEquipment", 
/* 102 */       "armbandRightEquipment", 
/* 103 */       "shoesEquipment", 
/* 104 */       "shoulderpadLeftEquipment", 
/* 105 */       "shoulderpadRightEquipment", 
/* 106 */       "cloakEquipment", 
/* 107 */       "trousersEquipment", 
/* 108 */       "shirEquipment", 
/* 109 */       "hatEquipment", 
/* 110 */       "staffEquipment", 
/* 111 */       "petEquipment", 
/* 112 */       "cureEquipment" };
/*     */ 
/*     */ 
/*     */   
/* 116 */   public static final String[] ALL_FIELDS = new String[FIELDS.length + Coach.FIELDS.length]; static {
/* 117 */     System.arraycopy(FIELDS, 0, ALL_FIELDS, 0, FIELDS.length);
/* 118 */     System.arraycopy(Coach.FIELDS, 0, ALL_FIELDS, FIELDS.length, Coach.FIELDS.length);
/*     */   }
/*     */   
/* 121 */   private static final String[] UPDATE_EQUIPMENT_PROPERTIES = new String[] { 
/* 122 */       "pantEquipment", 
/* 123 */       "hairsEquipment", 
/* 124 */       "tatooEquipment", 
/* 125 */       "armbandLeftEquipment", 
/* 126 */       "armbandRightEquipment", 
/* 127 */       "shoesEquipment", 
/* 128 */       "shoulderpadLeftEquipment", 
/* 129 */       "shoulderpadRightEquipment", 
/* 130 */       "cloakEquipment", 
/* 131 */       "trousersEquipment", 
/* 132 */       "shirEquipment", 
/* 133 */       "hatEquipment", 
/* 134 */       "staffEquipment", 
/* 135 */       "petEquipment", 
/* 136 */       "cureEquipment", 
/* 137 */       "actorDescriptorLibrary" };
/*     */ 
/*     */   
/* 140 */   private static final String[] UPDATE_STATISTICS_PROPERTIES = new String[] {
/* 141 */       "statisticsTotalFights", 
/* 142 */       "statisticsTotalFightsWon", 
/* 143 */       "statisticsTotalFightsLost", 
/* 144 */       "statisticsConsecutiveWins", 
/* 145 */       "statisticsConsecutiveLosses", 
/* 146 */       "statisticsTotalFightsTime", 
/* 147 */       "statisticsTotalPlayTime"
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final CoachCardInventories<CoachCard> m_editableCoachCardInventories;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean m_editableEquipmentChanged = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean m_editableInventoryChanged = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 168 */   private Coach m_fightingCoach = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 173 */   private TypeFilter m_cardTypeFilter = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 178 */   private ArrayList<CostFilter> m_costFilterList = new ArrayList<CostFilter>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 183 */   private CostFilter m_selectedCostFilter = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 188 */   private SetFilter m_setCardFilter = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 193 */   private PetFilter m_petCardFilter = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 198 */   private final EmoteFilter m_emoteCardFilter = null;
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
/*     */   private boolean m_inventorySynchronized = true;
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
/*     */   private boolean m_cardSetInitialized;
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
/*     */   public Coach getFightingCoach() {
/* 234 */     return this.m_fightingCoach;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFightingCoach(Coach fightingCoach) {
/* 241 */     this.m_fightingCoach = fightingCoach;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStatisticsReport(PlayerStatisticsReport statisticsReport) {
/* 251 */     super.setStatisticsReport(statisticsReport);
/*     */ 
/*     */     
/* 254 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, UPDATE_STATISTICS_PROPERTIES);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CoachCardFilter getCardTypeFilter() {
/* 261 */     return (CoachCardFilter)this.m_cardTypeFilter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCardTypeFilter(TypeFilter cardTypeFilter) {
/* 268 */     this.m_cardTypeFilter = cardTypeFilter;
/*     */ 
/*     */     
/* 271 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("coachManagement.inventoryCardTypeFilter", cardTypeFilter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CoachCardFilter getSetCardFilter() {
/* 278 */     return (CoachCardFilter)this.m_setCardFilter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSetCardFilter(SetFilter setCardFilter) {
/* 285 */     this.m_setCardFilter = setCardFilter;
/*     */ 
/*     */     
/* 288 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("coachManagement.inventoryCardSetFilter", setCardFilter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CoachCardFilter getPetCardFilter() {
/* 295 */     return (CoachCardFilter)this.m_petCardFilter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPetCardFilter(PetFilter petCardFilter) {
/* 302 */     this.m_petCardFilter = petCardFilter;
/*     */ 
/*     */     
/* 305 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("coachManagement.inventoryCardPetFilter", petCardFilter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CoachCardInventories<CoachCard> getEditableCoachCardInventories() {
/* 312 */     return this.m_editableCoachCardInventories;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEditableEquipmentChanged() {
/* 319 */     return this.m_editableEquipmentChanged;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEditableInventoryChanged() {
/* 326 */     return this.m_editableInventoryChanged;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEquipment(CoachCard inventoryEquipment, short position) {
/*     */     try {
/* 339 */       if (!getEditableCoachCardInventories().isEquipmentPositionFree(position)) {
/* 340 */         EquipedCoachCard oldEquipment = (EquipedCoachCard)getEditableCoachCardInventories().getEquipmentAt(position);
/* 341 */         if (oldEquipment != null && oldEquipment.getReferenceId() != inventoryEquipment.getReferenceId()) {
/* 342 */           removeEquipment((CoachCard)oldEquipment);
/*     */         } else {
/*     */           return;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 350 */       EquipedCoachCard equipedCoachCard = EquipedCoachCardProvider.getInstance().unserializeContent(ByteBuffer.wrap(inventoryEquipment.serialize()));
/*     */ 
/*     */       
/* 353 */       if (getEditableCoachCardInventories().addEquipmentAt((AbstractCoachCard)equipedCoachCard, position))
/*     */       {
/* 355 */         inventoryEquipment.updateQuantity((short)-1);
/*     */ 
/*     */         
/* 358 */         Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, UPDATE_EQUIPMENT_PROPERTIES);
/* 359 */         Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, "filtredEquipmentCardInventory");
/* 360 */         Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, "filtredSetCardInventory");
/* 361 */         Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, "filtredPetCardInventory");
/* 362 */         Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, "filtredEquipmentCardInventory");
/* 363 */         Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, "cardSets");
/*     */ 
/*     */         
/* 366 */         this.m_editableEquipmentChanged = true;
/*     */       }
/*     */     
/* 369 */     } catch (Exception exception) {}
/*     */   }
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
/*     */   public void removeEquipment(CoachCard equipment) {
/* 382 */     CoachCard coachCard = CoachCardProvider.getInstance().unserializeContent(ByteBuffer.wrap(equipment.serialize()));
/* 383 */     coachCard.setQuantity(equipment.getQuantity());
/*     */ 
/*     */     
/* 386 */     getEditableCoachCardInventories().removeEquipment((AbstractCoachCard)equipment);
/*     */ 
/*     */     
/*     */     try {
/* 390 */       getEditableCoachCardInventories().addToInventory((AbstractCoachCard)coachCard);
/* 391 */     } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */     
/* 395 */     this.m_editableEquipmentChanged = true;
/*     */ 
/*     */     
/* 398 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, "cardSets");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteEquipment(CoachCard equipment) {
/* 407 */     getEditableCoachCardInventories().removeFromInventory((AbstractCoachCard)equipment);
/*     */ 
/*     */     
/* 410 */     updateFiltredInventoryProperty();
/*     */ 
/*     */     
/* 413 */     this.m_editableInventoryChanged = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void lockEquipment(CoachCard equipment) {
/* 422 */     if (getEditableCoachCardInventories().lockCard((AbstractCoachCard)equipment)) {
/*     */ 
/*     */       
/* 425 */       this.m_editableInventoryChanged = true;
/*     */     }
/*     */     else {
/*     */       
/* 429 */       Xulor.getInstance().msgBox(DofusArenaTranslator.getInstance().getString("error.coachManagement.tooManyLocks", new Object[] { Short.valueOf((short)10) }), 66);
/*     */     } 
/*     */ 
/*     */     
/* 433 */     updateFiltredInventoryProperty();
/* 434 */     updateLockProperty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unlockEquipment(CoachCard equipment) {
/* 443 */     if (getEditableCoachCardInventories().unlockCard((AbstractCoachCard)equipment)) {
/*     */ 
/*     */       
/* 446 */       updateFiltredInventoryProperty();
/* 447 */       updateLockProperty();
/*     */ 
/*     */       
/* 450 */       this.m_editableInventoryChanged = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEmote(CoachCard emote) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeEmote(EquipedCoachCard emote) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void populateEditableInventories() {
/* 477 */     cleanEditableInventories();
/* 478 */     this.m_editableCoachCardInventories.addEquipmentObserver(this);
/* 479 */     this.m_editableCoachCardInventories.unserializeEquipment(getCardInventories().serializeEquipment());
/* 480 */     this.m_editableCoachCardInventories.unserializeInventory(getCardInventories().serializeInventory());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cleanEditableInventories() {
/* 487 */     this.m_editableCoachCardInventories.removeEquipmentObserver(this);
/* 488 */     this.m_editableCoachCardInventories.removeAll();
/* 489 */     this.m_editableEquipmentChanged = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getFields() {
/* 499 */     return ALL_FIELDS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getFieldValue(String fieldName) {
/* 509 */     if (fieldName.equals("cardInventory")) {
/* 510 */       ArrayList<CoachCard> inventory = new ArrayList<CoachCard>();
/* 511 */       for (CoachCard equipment : getEditableCoachCardInventories().getInventoryCards()) {
/* 512 */         inventory.add(equipment);
/*     */       }
/* 514 */       return inventory.toArray();
/*     */     } 
/* 516 */     if (fieldName.equals("filtredEquipmentCardInventory")) {
/* 517 */       ArrayList<CoachCard> filtredInventory = new ArrayList<CoachCard>();
/* 518 */       for (CoachCard equipment : getEditableCoachCardInventories().getInventoryCards()) {
/* 519 */         if (this.m_cardTypeFilter == null || (this.m_selectedCostFilter != null && this.m_selectedCostFilter.accept(equipment) && this.m_cardTypeFilter != null && this.m_cardTypeFilter.accept(equipment) && equipment.getQuantity() > 0)) {
/* 520 */           filtredInventory.add(equipment);
/*     */         }
/*     */       } 
/* 523 */       Collections.sort(filtredInventory);
/* 524 */       return filtredInventory.toArray();
/*     */     } 
/*     */     
/* 527 */     if (fieldName.equals("cardSets")) {
/* 528 */       ArrayList<String> sets = new ArrayList<String>();
/* 529 */       for (CoachCard equipment : getEditableCoachCardInventories().getInventoryCards()) {
/* 530 */         String setName = (String)equipment.getFieldValue("cardSetName");
/* 531 */         if (setName != null && !setName.equals("") && equipment.getQuantity() > 0 && !sets.contains(setName)) {
/* 532 */           sets.add(setName);
/*     */         }
/*     */       } 
/* 535 */       Collections.sort(sets);
/* 536 */       String[] setsArray = new String[sets.size()];
/* 537 */       sets.toArray(setsArray);
/* 538 */       return setsArray;
/*     */     } 
/* 540 */     if (fieldName.equals("filtredSetCardInventory")) {
/* 541 */       ArrayList<CoachCard> filtredInventory = new ArrayList<CoachCard>();
/* 542 */       for (CoachCard equipment : getEditableCoachCardInventories().getInventoryCards()) {
/* 543 */         if (this.m_setCardFilter == null || (this.m_setCardFilter != null && this.m_setCardFilter.accept(equipment) && equipment.getQuantity() > 0)) {
/* 544 */           filtredInventory.add(equipment);
/*     */         }
/*     */       } 
/* 547 */       Collections.sort(filtredInventory);
/* 548 */       return filtredInventory.toArray();
/*     */     } 
/*     */     
/* 551 */     fieldName.equals("cardPets");
/*     */ 
/*     */     
/* 554 */     if (fieldName.equals("filtredPetCardInventory")) {
/* 555 */       ArrayList<CoachCard> filtredInventory = new ArrayList<CoachCard>();
/* 556 */       for (CoachCard equipment : getEditableCoachCardInventories().getInventoryCards()) {
/* 557 */         if (this.m_petCardFilter == null || (this.m_petCardFilter != null && this.m_petCardFilter.accept(equipment) && equipment.getQuantity() > 0)) {
/* 558 */           filtredInventory.add(equipment);
/*     */         }
/*     */       } 
/* 561 */       Collections.sort(filtredInventory);
/* 562 */       return filtredInventory.toArray();
/*     */     } 
/*     */     
/* 565 */     if (fieldName.equals("emoteInventory")) {
/* 566 */       ArrayList<CoachCard> emotes = new ArrayList<CoachCard>();
/* 567 */       for (CoachCard equipment : getEditableCoachCardInventories().getInventoryCards()) {
/* 568 */         if (equipment.getType() == CoachCardType.EMOTE) {
/* 569 */           emotes.add(equipment);
/*     */         }
/*     */       } 
/* 572 */       return emotes.toArray();
/*     */     } 
/*     */     
/* 575 */     if (fieldName.equals("filtredEquipmentCardInventory")) {
/* 576 */       ArrayList<CoachCard> emotes = new ArrayList<CoachCard>();
/* 577 */       for (CoachCard equipment : getEditableCoachCardInventories().getInventoryCards()) {
/* 578 */         if (this.m_emoteCardFilter == null || (this.m_emoteCardFilter != null && this.m_emoteCardFilter.accept(equipment) && equipment.getQuantity() > 0))
/*     */         {
/* 580 */           emotes.add(equipment);
/*     */         }
/*     */       } 
/* 583 */       return emotes.toArray();
/*     */     } 
/*     */     
/* 586 */     if (fieldName.equals("cardCostFilterList")) {
/* 587 */       return this.m_costFilterList.toArray();
/*     */     }
/* 589 */     if (fieldName.equals("selectedCostFilter")) {
/* 590 */       if (this.m_costFilterList != null) {
/* 591 */         return this.m_selectedCostFilter;
/*     */       }
/* 593 */       return null;
/*     */     } 
/*     */     
/* 596 */     if (fieldName.equals("lockedCardsCount")) {
/* 597 */       return Integer.valueOf(getEditableCoachCardInventories().getLockedItemsCount());
/*     */     }
/* 599 */     if (fieldName.equals("lockedCardsMaxCount")) {
/* 600 */       return Short.valueOf((short)10);
/*     */     }
/*     */     
/* 603 */     if (fieldName.equals("pantEquipment")) {
/* 604 */       return getEditableCoachCardInventories().getEquipmentAt(CoachCardType.PANT.getInventoryPosition()[0]);
/*     */     }
/* 606 */     if (fieldName.equals("hairsEquipment")) {
/* 607 */       return getEditableCoachCardInventories().getEquipmentAt(CoachCardType.HAIRS.getInventoryPosition()[0]);
/*     */     }
/* 609 */     if (fieldName.equals("tatooEquipment")) {
/* 610 */       return getEditableCoachCardInventories().getEquipmentAt(CoachCardType.TATOO.getInventoryPosition()[0]);
/*     */     }
/* 612 */     if (fieldName.equals("armbandLeftEquipment")) {
/* 613 */       return getEditableCoachCardInventories().getEquipmentAt(CoachCardType.ARMBAND.getInventoryPosition()[1]);
/*     */     }
/* 615 */     if (fieldName.equals("armbandRightEquipment")) {
/* 616 */       return getEditableCoachCardInventories().getEquipmentAt(CoachCardType.ARMBAND.getInventoryPosition()[0]);
/*     */     }
/* 618 */     if (fieldName.equals("shoesEquipment")) {
/* 619 */       return getEditableCoachCardInventories().getEquipmentAt(CoachCardType.SHOES.getInventoryPosition()[0]);
/*     */     }
/* 621 */     if (fieldName.equals("shoulderpadLeftEquipment")) {
/* 622 */       return getEditableCoachCardInventories().getEquipmentAt(CoachCardType.SHOULDERPAD.getInventoryPosition()[1]);
/*     */     }
/* 624 */     if (fieldName.equals("shoulderpadRightEquipment")) {
/* 625 */       return getEditableCoachCardInventories().getEquipmentAt(CoachCardType.SHOULDERPAD.getInventoryPosition()[0]);
/*     */     }
/* 627 */     if (fieldName.equals("cloakEquipment")) {
/* 628 */       return getEditableCoachCardInventories().getEquipmentAt(CoachCardType.CLOAK.getInventoryPosition()[0]);
/*     */     }
/* 630 */     if (fieldName.equals("trousersEquipment")) {
/* 631 */       return getEditableCoachCardInventories().getEquipmentAt(CoachCardType.TROUSERS.getInventoryPosition()[0]);
/*     */     }
/* 633 */     if (fieldName.equals("shirEquipment")) {
/* 634 */       return getEditableCoachCardInventories().getEquipmentAt(CoachCardType.SHIR.getInventoryPosition()[0]);
/*     */     }
/* 636 */     if (fieldName.equals("hatEquipment")) {
/* 637 */       return getEditableCoachCardInventories().getEquipmentAt(CoachCardType.HAT.getInventoryPosition()[0]);
/*     */     }
/* 639 */     if (fieldName.equals("staffEquipment")) {
/* 640 */       return getEditableCoachCardInventories().getEquipmentAt(CoachCardType.STAFF.getInventoryPosition()[0]);
/*     */     }
/* 642 */     if (fieldName.equals("petEquipment")) {
/* 643 */       return getEditableCoachCardInventories().getEquipmentAt(CoachCardType.PET.getInventoryPosition()[0]);
/*     */     }
/* 645 */     if (fieldName.equals("cureEquipment")) {
/* 646 */       return getEditableCoachCardInventories().getEquipmentAt(CoachCardType.CURSE.getInventoryPosition()[0]);
/*     */     }
/*     */     
/* 649 */     return super.getFieldValue(fieldName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFieldSynchronisable(String fieldName) {
/* 659 */     return fieldName.equals("name");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFieldValue(String fieldName, Object value) {
/* 670 */     if (fieldName.equals("name")) {
/* 671 */       setName((String)value);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateLibraryDescriptorProperty() {
/* 679 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, "actorDescriptorLibrary");
/*     */   }
/*     */   
/* 682 */   public LocalCoach() { this.m_cardSetInitialized = false; this.m_editableCoachCardInventories = new CoachCardInventories((InventoryContentProvider)CoachCardProvider.getInstance(), (InventoryContentProvider)EquipedCoachCardProvider.getInstance()); this.m_editableCoachCardInventories.addInventoryObserver(new InventoryObserver() { public void onInventoryEvent(InventoryEvent event) { LocalCoach.this.updateFiltredInventoryProperty(); } }
/*     */       ); setCardTypeFilter(new TypeFilter()); setSetCardFilter(new SetFilter()); setPetCardFilter(new PetFilter()); this.m_costFilterList.add(new CostFilter(0, 2147483647)); this.m_costFilterList.add(new CostFilter(0, 199));
/*     */     this.m_costFilterList.add(new CostFilter(200, 4999));
/*     */     this.m_costFilterList.add(new CostFilter(5000, 29999));
/*     */     this.m_costFilterList.add(new CostFilter(30000, 39999));
/*     */     this.m_costFilterList.add(new CostFilter(40000, 2147483647));
/* 688 */     this.m_selectedCostFilter = this.m_costFilterList.get(0); } public void updateFiltredInventoryProperty() { Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, "filtredEquipmentCardInventory");
/* 689 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, "filtredSetCardInventory");
/* 690 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, "cardSets");
/* 691 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, "filtredEquipmentCardInventory");
/* 692 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, "filtredPetCardInventory");
/* 693 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, "selectedCostFilter");
/* 694 */     if (!this.m_cardSetInitialized) {
/* 695 */       String[] sets = (String[])getFieldValue("cardSets");
/* 696 */       if (sets != null && sets.length > 0) {
/* 697 */         this.m_setCardFilter.setCurrentSetName(sets[0]);
/* 698 */         this.m_cardSetInitialized = true;
/*     */       } 
/*     */     }  }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateLockProperty() {
/* 707 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, "lockedCardsCount");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateEquipmentProperties() {
/* 714 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, UPDATE_EQUIPMENT_PROPERTIES);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onInventoryEvent(InventoryEvent event) {
/* 724 */     super.onInventoryEvent(event);
/* 725 */     updateEquipmentProperties(); } public void onItemExchangerEvent(ItemExchangerEvent event) {
/*     */     ItemExchangerEndEvent endEvent;
/*     */     final CardTrade trade;
/*     */     ItemExchangerModifiedEvent modifiedEvent;
/*     */     CardTrade cardTrade2;
/*     */     byte localIndex;
/*     */     int remoteUserIndex;
/*     */     String message;
/*     */     CardTrade cardTrade1;
/*     */     MessageBoxControler controler, messageBoxControler;
/*     */     byte b1;
/* 736 */     switch (event.getAction()) {
/*     */       
/*     */       case EXCHANGE_END:
/* 739 */         endEvent = (ItemExchangerEndEvent)event;
/*     */         
/* 741 */         switch (endEvent.getReason()) {
/*     */           case INVITATION_LOCALLY_CANCELED:
/*     */           case INVITATION_REMOTELY_CANCELED:
/* 744 */             cardTrade2 = (CardTrade)event.getItemExchanger();
/* 745 */             controler = cardTrade2.getInvitationMessageBoxControler();
/*     */             
/* 747 */             if (controler != null) {
/* 748 */               Xulor.getInstance().unload(controler.getMessageBoxId());
/*     */             }
/*     */             break;
/*     */ 
/*     */           
/*     */           case REMOTELY_CANCELED:
/*     */           case LOCALLY_CANCELED:
/* 755 */             DofusArenaGameEntity.getInstance().removeFrame((MessageFrame)UIExchangeFrame.getInstance());
/* 756 */             DofusArenaGameEntity.getInstance().removeFrame((MessageFrame)NetExchangeFrame.getInstance());
/*     */             break;
/*     */           
/*     */           case null:
/* 760 */             DofusArenaGameEntity.getInstance().removeFrame((MessageFrame)UIExchangeFrame.getInstance());
/* 761 */             DofusArenaGameEntity.getInstance().removeFrame((MessageFrame)NetExchangeFrame.getInstance());
/*     */             break;
/*     */         } 
/*     */         
/*     */         break;
/*     */       
/*     */       case EXCHANGE_USER_READY:
/* 768 */         trade = (CardTrade)event.getItemExchanger();
/* 769 */         localIndex = trade.getIndexByUser(this);
/*     */         
/* 771 */         if (event.getUserIndex() == localIndex) {
/* 772 */           trade.updateLocalReadyProperties(); break;
/*     */         } 
/* 774 */         trade.updateRemoteReadyProperties();
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case EXCHANGE_STARTED:
/* 782 */         trade = (CardTrade)event.getItemExchanger();
/* 783 */         Xulor.getInstance().unload(trade.getInvitationMessageBoxControler().getMessageBoxId());
/*     */ 
/*     */         
/* 786 */         DofusArenaGameEntity.getInstance().removeFrame((MessageFrame)UIExchangeInvitationFrame.getInstance());
/*     */ 
/*     */         
/* 789 */         Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("exchange.cardTrade", trade);
/*     */         
/* 791 */         remoteUserIndex = trade.isRequesterLocal() ? 1 : 0;
/* 792 */         Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("exchange.remoteCoach", trade.getUser(remoteUserIndex));
/*     */ 
/*     */         
/* 795 */         DofusArenaGameEntity.getInstance().pushFrame((MessageFrame)UIExchangeFrame.getInstance());
/* 796 */         DofusArenaGameEntity.getInstance().pushFrame((MessageFrame)NetExchangeFrame.getInstance());
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case EXCHANGE_PROPOSED:
/* 803 */         DofusArenaGameEntity.getInstance().pushFrame((MessageFrame)UIExchangeInvitationFrame.getInstance());
/*     */         
/* 805 */         trade = (CardTrade)event.getItemExchanger();
/* 806 */         message = DofusArenaTranslator.getInstance().getString("exchangeInvitation.messageOut", new Object[] { trade.getUser(1).getName() });
/*     */ 
/*     */         
/* 809 */         messageBoxControler = Xulor.getInstance().msgBox(message, 132);
/* 810 */         trade.setInvitationMessageBoxControler(messageBoxControler);
/* 811 */         messageBoxControler.addEventListener(new IMessageBoxEventListener()
/*     */             {
/*     */               public void messageBoxClosed(int type) {
/* 814 */                 UIExchangeInvitationRejectRequestMessage message = UIExchangeInvitationRejectRequestMessage.checkOut();
/* 815 */                 message.setInvitationId(trade.getId());
/* 816 */                 Worker.getInstance().pushMessage((Message)message);
/*     */               }
/*     */             });
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case EXCHANGE_REQUESTED:
/* 824 */         DofusArenaGameEntity.getInstance().pushFrame((MessageFrame)UIExchangeInvitationFrame.getInstance());
/*     */         
/* 826 */         trade = (CardTrade)event.getItemExchanger();
/* 827 */         message = DofusArenaTranslator.getInstance().getString("exchangeInvitation.messageIn", new Object[] { trade.getUser(0).getName() });
/*     */ 
/*     */         
/* 830 */         messageBoxControler = Xulor.getInstance().msgBox(message, 152);
/* 831 */         trade.setInvitationMessageBoxControler(messageBoxControler);
/* 832 */         messageBoxControler.addEventListener(new IMessageBoxEventListener() {
/*     */               public void messageBoxClosed(int type) {
/* 834 */                 if (type == 8) {
/*     */ 
/*     */                   
/* 837 */                   UIExchangeInvitationAcceptRequestMessage message = UIExchangeInvitationAcceptRequestMessage.checkOut();
/* 838 */                   message.setInvitationId(trade.getId());
/* 839 */                   Worker.getInstance().pushMessage((Message)message);
/*     */                 
/*     */                 }
/*     */                 else {
/*     */                   
/* 844 */                   UIExchangeInvitationRejectRequestMessage message = UIExchangeInvitationRejectRequestMessage.checkOut();
/* 845 */                   message.setInvitationId(trade.getId());
/* 846 */                   Worker.getInstance().pushMessage((Message)message);
/*     */                 } 
/*     */               }
/*     */             });
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case null:
/* 855 */         modifiedEvent = (ItemExchangerModifiedEvent)event;
/*     */         
/* 857 */         cardTrade1 = (CardTrade)modifiedEvent.getItemExchanger();
/* 858 */         b1 = cardTrade1.getIndexByUser(this);
/*     */         
/* 860 */         if (modifiedEvent.getUserIndex() == b1) {
/* 861 */           short updateQuantity = 0;
/* 862 */           switch (modifiedEvent.getModification()) {
/*     */             case null:
/* 864 */               updateQuantity = (short)-modifiedEvent.getContentQuantity();
/*     */               break;
/*     */             case CONTENT_REMOVED:
/* 867 */               updateQuantity = modifiedEvent.getContentQuantity();
/*     */               break;
/*     */           } 
/*     */           
/* 871 */           getEditableCoachCardInventories().updateInventoryQuantity(modifiedEvent.getContent().getUniqueId(), updateQuantity);
/* 872 */           updateFiltredInventoryProperty();
/* 873 */           cardTrade1.updateLocalCardProperties();
/*     */         }
/*     */         else {
/*     */           
/* 877 */           cardTrade1.updateRemoteCardProperties();
/*     */         } 
/*     */ 
/*     */         
/* 881 */         cardTrade1.updateRemoteReadyProperties();
/* 882 */         cardTrade1.updateLocalReadyProperties();
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 889 */     super.onItemExchangerEvent(event);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInventorySynchronized() {
/* 894 */     return this.m_inventorySynchronized;
/*     */   }
/*     */   
/*     */   public void setInventorySynchronized(boolean inventorySynchronized) {
/* 898 */     this.m_inventorySynchronized = inventorySynchronized;
/*     */     
/* 900 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("menuBar.coachInventoryButton", Boolean.valueOf(inventorySynchronized));
/*     */   }
/*     */ 
/*     */   
/*     */   public CostFilter getSelectedCostFilter() {
/* 905 */     return this.m_selectedCostFilter;
/*     */   }
/*     */   
/*     */   public void setSelectedCostFilter(String selectedCostFilter) {
/* 909 */     for (CostFilter filter : this.m_costFilterList) {
/* 910 */       if (filter.toString().equals(selectedCostFilter))
/* 911 */         this.m_selectedCostFilter = filter; 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\coach\LocalCoach.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */