/*     */ package com.ankamagames.dofusarena.client.ui.actions;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.ArrayInventory;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.InventoryContentChecker;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.StackInventory;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.utils.WordsModerator;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.fighter.FighterCard;
/*     */ import com.ankamagames.dofusarena.client.core.game.fighter.EditableFighter;
/*     */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*     */ import com.ankamagames.dofusarena.client.core.game.spell.Spell;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.UIMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.teamManagement.UIFighterBreedMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.teamManagement.UIFighterEquipmentMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.teamManagement.UIFighterMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.teamManagement.UIFighterSexMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.teamManagement.UIFighterSpellMessage;
/*     */ import com.ankamagames.dofusarena.common.constants.DofusArenaConstants;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Worker;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.core.form.Form;
/*     */ import com.ankamagames.xulor.event.DragEvent;
/*     */ import com.ankamagames.xulor.event.DropEvent;
/*     */ import com.ankamagames.xulor.event.Event;
/*     */ import com.ankamagames.xulor.event.ItemDoubleClickEvent;
/*     */ import com.ankamagames.xulor.event.ItemOutEvent;
/*     */ import com.ankamagames.xulor.event.ItemOverEvent;
/*     */ import com.ankamagames.xulor.event.Key;
/*     */ import com.ankamagames.xulor.event.KeyPressedEvent;
/*     */ import com.ankamagames.xulor.event.MouseClickEvent;
/*     */ import com.ankamagames.xulor.event.MouseDoubleClickEvent;
/*     */ import com.ankamagames.xulor.property.Property;
/*     */ import com.ankamagames.xulor.template.IDragNDropable;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
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
/*     */ public class TeamManagementActions
/*     */ {
/*     */   public static final String PACKAGE = "dofusarena.teamManagement";
/*     */   
/*     */   public static void createNewFighter(Event event)
/*     */   {
/*  58 */     UIMessage message = new UIMessage();
/*  59 */     message.setId(16605);
/*  60 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void closeFighterCreationDialog(Event event)
/*     */   {
/*  70 */     UIMessage message = new UIMessage();
/*  71 */     message.setId(16606);
/*  72 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void setFighterBreedId(Event event, EditableFighter fighter, String breedId)
/*     */   {
/*  82 */     UIFighterBreedMessage message = new UIFighterBreedMessage();
/*  83 */     message.setFighter(fighter);
/*  84 */     message.setBreedId(Byte.valueOf(breedId).byteValue());
/*  85 */     message.setId(16609);
/*  86 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void setFighterSex(Event event, EditableFighter fighter, String sex)
/*     */   {
/*  97 */     UIFighterSexMessage message = new UIFighterSexMessage();
/*  98 */     message.setFighter(fighter);
/*  99 */     message.setSex(Byte.valueOf(sex).byteValue());
/* 100 */     message.setId(16610);
/* 101 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void setPreviousFighterSkinIndex(Event event, EditableFighter fighter)
/*     */   {
/* 111 */     UIFighterMessage message = new UIFighterMessage();
/* 112 */     message.setFighter(fighter);
/* 113 */     message.setId(16607);
/* 114 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void setNextFighterSkinIndex(Event event, EditableFighter fighter)
/*     */   {
/* 124 */     UIFighterMessage message = new UIFighterMessage();
/* 125 */     message.setFighter(fighter);
/* 126 */     message.setId(16608);
/* 127 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void createFighter(Event event, Form form)
/*     */   {
/* 136 */     if (((event instanceof MouseClickEvent)) || (((event instanceof KeyPressedEvent)) && (((KeyPressedEvent)event).getKeyClass().equals(Integer.valueOf(10))))) {
/* 137 */       if (form.isValid())
/*     */       {
/* 139 */         UIMessage message = new UIMessage();
/* 140 */         message.setId(16611);
/* 141 */         Worker.getInstance().pushMessage(message);
/*     */       } else {
/* 143 */         Xulor.getInstance().msgBox(DofusArenaTranslator.getInstance().getString("error.fighterCreation.invalidName", new Object[0]), 3);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean validateCreateFighterForm(Form form)
/*     */   {
/* 155 */     form.synchronizeProperties();
/* 156 */     Property p = form.getProperty("teamManagement.editableFighter");
/* 157 */     if (p == null)
/* 158 */       return false;
/* 159 */     if (!(p.getValue() instanceof Fighter))
/* 160 */       return false;
/* 161 */     Fighter fighter = (Fighter)p.getValue();
/*     */     
/*     */ 
/* 164 */     String name = fighter.getName();
/* 165 */     return (name != null) && (name.length() <= 16) && (DofusArenaConstants.FIGHTER_NAME_PATTERN.matcher(name).matches()) && (WordsModerator.validateName(name));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void deleteFighter(Event event, Fighter fighter)
/*     */   {
/* 176 */     UIFighterMessage message = new UIFighterMessage();
/* 177 */     message.setId(16612);
/* 178 */     message.setFighter(fighter);
/*     */     
/*     */ 
/* 181 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void addRemoveFighterFromEditableTeamPreset(Event event, Fighter fighter)
/*     */   {
/* 193 */     UIFighterMessage message = new UIFighterMessage();
/* 194 */     message.setId(16613);
/* 195 */     message.setFighter(fighter);
/*     */     
/*     */ 
/* 198 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void editFighter(Event event, Fighter fighter)
/*     */   {
/* 210 */     UIFighterMessage message = new UIFighterMessage();
/* 211 */     message.setId(16614);
/* 212 */     message.setFighter(fighter);
/*     */     
/*     */ 
/* 215 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void setNextFighterDirection(Event event, Fighter fighter)
/*     */   {
/* 226 */     UIFighterMessage message = new UIFighterMessage();
/* 227 */     message.setFighter(fighter);
/* 228 */     message.setId(16627);
/* 229 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void setPreviousFighterDirection(Event event, Fighter fighter)
/*     */   {
/* 239 */     UIFighterMessage message = new UIFighterMessage();
/* 240 */     message.setFighter(fighter);
/* 241 */     message.setId(16626);
/* 242 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void closeFighterEditionDialog(Event event)
/*     */   {
/* 252 */     UIMessage message = new UIMessage();
/* 253 */     message.setId(16615);
/* 254 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void saveEditableFighter(Event event, EditableFighter fighter)
/*     */   {
/* 265 */     UIFighterMessage message = new UIFighterMessage();
/* 266 */     message.setId(16616);
/* 267 */     message.setFighter(fighter);
/*     */     
/*     */ 
/* 270 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void newEditableTeamPreset(Event event)
/*     */   {
/* 281 */     UIMessage message = new UIMessage();
/* 282 */     message.setId(16602);
/* 283 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void saveEditableTeamPreset(Event event, Form form)
/*     */   {
/* 292 */     form.synchronizeProperties();
/*     */     
/* 294 */     UIMessage message = new UIMessage();
/* 295 */     message.setId(16603);
/* 296 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void deleteEditableTeamPreset(Event event)
/*     */   {
/* 306 */     UIMessage message = new UIMessage();
/* 307 */     message.setId(16604);
/* 308 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void showTeamPresetList(Event event)
/*     */   {
/* 317 */     UIMessage message = new UIMessage();
/* 318 */     message.setId(16628);
/* 319 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean validateSpellDrop(IDragNDropable src, Object srcValue, IDragNDropable dest, Object destValue, Object value, EditableFighter fighter)
/*     */   {
/* 329 */     if ((value != null) && ((value instanceof Spell))) {
/* 330 */       Spell spell = (Spell)value;
/* 331 */       if (fighter != null) {
/* 332 */         StackInventory<Spell> spellInventory = fighter.getSpellInventory();
/* 333 */         return spellInventory.getContentChecker().canAddItem(spellInventory, spell) == 0;
/*     */       }
/*     */     }
/* 336 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void dropSpell(DropEvent event, EditableFighter fighter)
/*     */   {
/* 345 */     Object value = event.getValue();
/* 346 */     if ((value != null) && ((value instanceof Spell))) {
/* 347 */       Spell spell = (Spell)value;
/*     */       
/*     */ 
/* 350 */       UIFighterSpellMessage message = new UIFighterSpellMessage();
/* 351 */       message.setFighter(fighter);
/* 352 */       message.setSpell(spell);
/* 353 */       message.setId(16618);
/* 354 */       Worker.getInstance().pushMessage(message);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void dragSpell(DragEvent event, EditableFighter fighter)
/*     */   {
/* 364 */     Object value = event.getValue();
/* 365 */     if ((value != null) && ((value instanceof Spell))) {
/* 366 */       Spell spell = (Spell)value;
/*     */       
/*     */ 
/* 369 */       UIFighterSpellMessage message = new UIFighterSpellMessage();
/* 370 */       message.setFighter(fighter);
/* 371 */       message.setSpell(spell);
/* 372 */       message.setId(16619);
/* 373 */       Worker.getInstance().pushMessage(message);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean validateEquipmentDrop(IDragNDropable src, Object srcValue, IDragNDropable dest, Object destValue, Object value, EditableFighter fighter, String position)
/*     */   {
/* 384 */     if ((value != null) && ((value instanceof FighterCard))) {
/* 385 */       FighterCard equipment = (FighterCard)value;
/* 386 */       if (fighter != null) {
/* 387 */         ArrayInventory<FighterCard> equipmentInventory = fighter.getEquipmentInventory();
/* 388 */         return equipmentInventory.getContentChecker().canAddItem(equipmentInventory, equipment, Short.valueOf(position).shortValue()) == 0;
/*     */       }
/*     */     }
/* 391 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void dropEquipment(DropEvent event, EditableFighter fighter, String position)
/*     */   {
/* 400 */     Object value = event.getValue();
/* 401 */     if ((value != null) && ((value instanceof FighterCard))) {
/* 402 */       FighterCard equipment = (FighterCard)value;
/*     */       
/*     */ 
/* 405 */       UIFighterEquipmentMessage message = new UIFighterEquipmentMessage();
/* 406 */       message.setFighter(fighter);
/* 407 */       message.setEquipment(equipment);
/* 408 */       message.setPosition(Short.valueOf(position).shortValue());
/* 409 */       message.setId(16620);
/* 410 */       Worker.getInstance().pushMessage(message);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void dragEquipment(DragEvent event, Fighter fighter, String position)
/*     */   {
/* 422 */     Object value = event.getValue();
/* 423 */     if ((value != null) && ((value instanceof FighterCard))) {
/* 424 */       FighterCard equipment = (FighterCard)value;
/* 425 */       if (event.getElement() != null)
/*     */       {
/* 427 */         UIFighterEquipmentMessage message = new UIFighterEquipmentMessage();
/* 428 */         message.setFighter(fighter);
/* 429 */         message.setEquipment(equipment);
/* 430 */         message.setPosition(Short.valueOf(position).shortValue());
/* 431 */         message.setId(16621);
/* 432 */         Worker.getInstance().pushMessage(message);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void addEquipment(ItemDoubleClickEvent event, EditableFighter fighter, String position)
/*     */   {
/* 445 */     Object value = event.getItemValue();
/* 446 */     if ((value != null) && ((value instanceof FighterCard))) {
/* 447 */       FighterCard equipment = (FighterCard)value;
/*     */       
/*     */ 
/* 450 */       UIFighterEquipmentMessage message = new UIFighterEquipmentMessage();
/* 451 */       message.setFighter(fighter);
/* 452 */       message.setEquipment(equipment);
/* 453 */       message.setPosition(Short.valueOf(position).shortValue());
/* 454 */       message.setId(16620);
/* 455 */       Worker.getInstance().pushMessage(message);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void removeEquipment(MouseDoubleClickEvent event, EditableFighter fighter, String position)
/*     */   {
/* 467 */     if (event.getElement() != null)
/*     */     {
/* 469 */       UIFighterEquipmentMessage message = new UIFighterEquipmentMessage();
/* 470 */       message.setFighter(fighter);
/* 471 */       message.setEquipment((FighterCard)fighter.getEquipmentInventory().getFromPosition(Short.valueOf(position).shortValue()));
/* 472 */       message.setPosition(Short.valueOf(position).shortValue());
/* 473 */       message.setId(16621);
/* 474 */       Worker.getInstance().pushMessage(message);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void addSpell(ItemDoubleClickEvent event, EditableFighter fighter)
/*     */   {
/* 484 */     Object value = event.getItemValue();
/* 485 */     if ((value != null) && ((value instanceof Spell))) {
/* 486 */       Spell spell = (Spell)value;
/*     */       
/*     */ 
/* 489 */       UIFighterSpellMessage message = new UIFighterSpellMessage();
/* 490 */       message.setFighter(fighter);
/* 491 */       message.setSpell(spell);
/* 492 */       message.setId(16618);
/* 493 */       Worker.getInstance().pushMessage(message);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void removeSpell(ItemDoubleClickEvent event, EditableFighter fighter)
/*     */   {
/* 503 */     Object value = event.getItemValue();
/* 504 */     if ((value != null) && ((value instanceof Spell))) {
/* 505 */       Spell spell = (Spell)value;
/*     */       
/* 507 */       UIFighterSpellMessage message = new UIFighterSpellMessage();
/* 508 */       message.setFighter(fighter);
/* 509 */       message.setSpell(spell);
/* 510 */       message.setId(16619);
/* 511 */       Worker.getInstance().pushMessage(message);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void showEquipmentInfos(ItemOverEvent event)
/*     */   {
/* 521 */     Object value = event.getItemValue();
/* 522 */     if ((value != null) && ((value instanceof FighterCard))) {
/* 523 */       UIFighterEquipmentMessage message = new UIFighterEquipmentMessage();
/* 524 */       message.setEquipment((FighterCard)value);
/* 525 */       message.setId(16622);
/* 526 */       Worker.getInstance().pushMessage(message);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void hideEquipmentInfos(ItemOutEvent event)
/*     */   {
/* 536 */     UIMessage message = new UIMessage();
/* 537 */     message.setId(16623);
/* 538 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void showStatisticsInfos(ItemOverEvent event)
/*     */   {
/* 547 */     Object value = event.getItemValue();
/* 548 */     if ((value != null) && ((value instanceof Fighter))) {
/* 549 */       UIFighterMessage message = new UIFighterMessage();
/* 550 */       message.setFighter((Fighter)value);
/* 551 */       message.setId(16629);
/* 552 */       Worker.getInstance().pushMessage(message);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void hideStatisticsInfos(ItemOutEvent event)
/*     */   {
/* 562 */     UIMessage message = new UIMessage();
/* 563 */     message.setId(16630);
/* 564 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void showSpellInfos(ItemOverEvent event)
/*     */   {
/* 573 */     Object value = event.getItemValue();
/* 574 */     if ((value != null) && ((value instanceof Spell))) {
/* 575 */       UIFighterSpellMessage message = new UIFighterSpellMessage();
/* 576 */       message.setSpell((Spell)value);
/* 577 */       message.setId(16624);
/* 578 */       Worker.getInstance().pushMessage(message);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void hideSpellInfos(ItemOutEvent event)
/*     */   {
/* 588 */     UIMessage message = new UIMessage();
/* 589 */     message.setId(16625);
/* 590 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\actions\TeamManagementActions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */