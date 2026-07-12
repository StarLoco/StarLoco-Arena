/*     */ package com.ankamagames.dofusarena.client.ui.actions;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.utils.WordsModerator;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.CoachCard;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.EquipedCoachCard;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.filter.CostFilter;
/*     */ import com.ankamagames.dofusarena.client.core.game.coach.LocalCoach;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.UIMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.coach.UICoachEquipmentMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.coach.UILocalCoachMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.connection.UICoachCreationMessage;
/*     */ import com.ankamagames.dofusarena.common.constants.DofusArenaConstants;
/*     */ import com.ankamagames.dofusarena.common.game.card.CoachCardInventories;
/*     */ import com.ankamagames.dofusarena.common.game.card.CoachCardType;
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
/*     */ import com.ankamagames.xulor.event.SelectionChangedEvent;
/*     */ import com.ankamagames.xulor.property.Property;
/*     */ import com.ankamagames.xulor.template.IDragNDropable;
/*     */ import com.ankamagames.xulor.util.Item;
/*     */ import java.io.PrintStream;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CoachManagementActions
/*     */ {
/*     */   public static final String PACKAGE = "dofusarena.coachManagement";
/*     */   
/*     */   public static void setCoachSex(Event event, String sex)
/*     */   {
/*  45 */     UIMessage message = new UIMessage();
/*  46 */     message.setByteValue(Byte.valueOf(sex).byteValue());
/*  47 */     message.setId(16401);
/*  48 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void setCoachHairColorIndex(Event event, String hairColorIndex)
/*     */   {
/*  58 */     UIMessage message = new UIMessage();
/*  59 */     message.setByteValue(Byte.valueOf(hairColorIndex).byteValue());
/*  60 */     message.setId(16402);
/*  61 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void setCoachSkinColorIndex(Event event, String skinColorIndex)
/*     */   {
/*  71 */     UIMessage message = new UIMessage();
/*  72 */     message.setByteValue(Byte.valueOf(skinColorIndex).byteValue());
/*  73 */     message.setId(16403);
/*  74 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void setNextCoachDirection(Event event)
/*     */   {
/*  83 */     UIMessage message = new UIMessage();
/*  84 */     message.setId(16405);
/*  85 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void setPreviousCoachDirection(Event event)
/*     */   {
/*  94 */     UIMessage message = new UIMessage();
/*  95 */     message.setId(16406);
/*  96 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void createRandomCoach(Event event)
/*     */   {
/* 105 */     UIMessage message = new UIMessage();
/* 106 */     message.setId(16404);
/* 107 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean validateCoachCreationForm(Form form)
/*     */   {
/* 117 */     form.synchronizeProperties();
/* 118 */     Property localCoachProperty = form.getProperty("localCoach");
/* 119 */     if (localCoachProperty != null) {
/* 120 */       String coachName = localCoachProperty.getFieldStringValue("name");
/* 121 */       if ((coachName != null) && (coachName.length() <= 20) && (DofusArenaConstants.COACH_NAME_PATTERN.matcher(coachName).matches()) && 
/* 122 */         (WordsModerator.validateName(coachName))) {
/* 123 */         return true;
/*     */       }
/* 125 */       Xulor.getInstance().msgBox(DofusArenaTranslator.getInstance().getString("error.coachCreation.invalidName", new Object[0]));
/* 126 */       return false;
/*     */     }
/*     */     
/* 129 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void createCoach(Event event, Form form)
/*     */   {
/* 139 */     if (((event instanceof MouseClickEvent)) || (((event instanceof KeyPressedEvent)) && (((KeyPressedEvent)event).getKeyClass().equals(Integer.valueOf(10))))) {
/* 140 */       if (form.isValid())
/*     */       {
/* 142 */         Property localCoachProperty = form.getProperty("localCoach");
/* 143 */         if (localCoachProperty != null)
/*     */         {
/* 145 */           Object propertyValue = localCoachProperty.getValue();
/* 146 */           if ((propertyValue instanceof LocalCoach))
/*     */           {
/*     */ 
/* 149 */             UICoachCreationMessage message = new UICoachCreationMessage();
/* 150 */             message.setLocalCoach((LocalCoach)propertyValue);
/* 151 */             Worker.getInstance().pushMessage(message);
/*     */           }
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 157 */         System.out.println("Formulaire invalide");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void showCoachCardInfos(ItemOverEvent event)
/*     */   {
/* 168 */     Object value = event.getItemValue();
/* 169 */     if ((value != null) && ((value instanceof CoachCard))) {
/* 170 */       UICoachEquipmentMessage message = new UICoachEquipmentMessage();
/* 171 */       message.setEquipment((CoachCard)value);
/* 172 */       message.setId(16700);
/* 173 */       Worker.getInstance().pushMessage(message);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void hideCoachCardInfos(ItemOutEvent event)
/*     */   {
/* 183 */     UIMessage message = new UIMessage();
/* 184 */     message.setId(16701);
/* 185 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void selectEquipmentTypeFilter(Event event, LocalCoach localCoach, String typeId)
/*     */   {
/* 197 */     UILocalCoachMessage message = new UILocalCoachMessage();
/* 198 */     message.setCoach(localCoach);
/* 199 */     message.setIntValue(Integer.valueOf(typeId).intValue());
/* 200 */     message.setId(16704);
/* 201 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void selectSetFilter(Event event, LocalCoach localCoach)
/*     */   {
/* 211 */     if ((event instanceof SelectionChangedEvent)) {
/* 212 */       SelectionChangedEvent sce = (SelectionChangedEvent)event;
/*     */       
/* 214 */       UILocalCoachMessage message = new UILocalCoachMessage();
/* 215 */       message.setCoach(localCoach);
/* 216 */       message.setStringValue((String)sce.getItem().getValue());
/* 217 */       message.setId(16711);
/* 218 */       Worker.getInstance().pushMessage(message);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void selectCostFilter(Event event, LocalCoach localCoach)
/*     */   {
/* 229 */     if ((event instanceof SelectionChangedEvent)) {
/* 230 */       SelectionChangedEvent sce = (SelectionChangedEvent)event;
/* 231 */       if ((sce.getItem().getValue() instanceof CostFilter))
/*     */       {
/* 233 */         UILocalCoachMessage message = new UILocalCoachMessage();
/* 234 */         message.setCoach(localCoach);
/* 235 */         message.setStringValue(sce.getItem().getValue().toString());
/* 236 */         message.setId(16714);
/* 237 */         Worker.getInstance().pushMessage(message);
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
/*     */   public static void selectPetTypeFilter(Event event, LocalCoach localCoach, String typeId)
/*     */   {
/* 250 */     UILocalCoachMessage message = new UILocalCoachMessage();
/* 251 */     message.setCoach(localCoach);
/* 252 */     message.setIntValue(Integer.valueOf(typeId).intValue());
/* 253 */     message.setId(16712);
/* 254 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void selectAllEquipmentTypeFilter(Event event, LocalCoach localCoach)
/*     */   {
/* 264 */     UILocalCoachMessage message = new UILocalCoachMessage();
/* 265 */     message.setCoach(localCoach);
/* 266 */     message.setId(16705);
/* 267 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void selectAllPetTypeFilter(Event event, LocalCoach localCoach)
/*     */   {
/* 279 */     UILocalCoachMessage message = new UILocalCoachMessage();
/* 280 */     message.setCoach(localCoach);
/* 281 */     message.setId(16713);
/* 282 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean validateEquipmentDrop(IDragNDropable src, Object srcValue, IDragNDropable dest, Object destValue, Object value, LocalCoach localCoach, String position)
/*     */   {
/* 291 */     if ((value != null) && ((value instanceof CoachCard))) {
/* 292 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 298 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void dropEquipment(DropEvent event, LocalCoach localCoach, String position)
/*     */   {
/* 309 */     Object value = event.getValue();
/* 310 */     if ((value != null) && ((value instanceof CoachCard)))
/*     */     {
/* 312 */       CoachCard equipment = null;
/* 313 */       if ((value instanceof EquipedCoachCard))
/*     */       {
/* 315 */         EquipedCoachCard equipedCoachCard = (EquipedCoachCard)value;
/* 316 */         equipment = (CoachCard)localCoach.getEditableCoachCardInventories().getFromInventory(equipedCoachCard.getReferenceUniqueId());
/*     */       } else {
/* 318 */         equipment = (CoachCard)value;
/*     */       }
/*     */       
/* 321 */       if (equipment != null)
/*     */       {
/* 323 */         UICoachEquipmentMessage message = new UICoachEquipmentMessage();
/* 324 */         message.setCoach(localCoach);
/* 325 */         message.setEquipment(equipment);
/*     */         
/* 327 */         if (((equipment.getType() == CoachCardType.SHOULDERPAD) && ((position.equals("3")) || (position.equals("13")))) || (
/* 328 */           (equipment.getType() == CoachCardType.ARMBAND) && ((position.equals("4")) || (position.equals("12"))))) {
/* 329 */           message.setPosition(Short.valueOf(position).shortValue());
/*     */         } else {
/* 331 */           message.setPosition((short)-1);
/*     */         }
/* 333 */         message.setId(16702);
/* 334 */         Worker.getInstance().pushMessage(message);
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
/*     */   public static void dragEquipment(DragEvent event, LocalCoach localCoach, String position)
/*     */   {
/* 347 */     Object value = event.getValue();
/* 348 */     if ((value != null) && ((value instanceof CoachCard))) {
/* 349 */       CoachCard equipment = (CoachCard)value;
/*     */       
/* 351 */       if (event.getElement() != null)
/*     */       {
/* 353 */         UICoachEquipmentMessage message = new UICoachEquipmentMessage();
/* 354 */         message.setCoach(localCoach);
/* 355 */         message.setEquipment(equipment);
/* 356 */         message.setPosition(Short.valueOf(position).shortValue());
/* 357 */         message.setId(16703);
/* 358 */         Worker.getInstance().pushMessage(message);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void equip(ItemDoubleClickEvent event, LocalCoach localCoach)
/*     */   {
/* 369 */     Object value = event.getItemValue();
/* 370 */     if ((value instanceof CoachCard)) {
/* 371 */       CoachCard equipment = (CoachCard)value;
/*     */       
/*     */ 
/* 374 */       UICoachEquipmentMessage message = new UICoachEquipmentMessage();
/* 375 */       message.setCoach(localCoach);
/* 376 */       message.setEquipment(equipment);
/* 377 */       message.setPosition((short)-1);
/* 378 */       message.setId(16702);
/* 379 */       Worker.getInstance().pushMessage(message);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void unequip(ItemDoubleClickEvent event, LocalCoach localCoach, String position)
/*     */   {
/* 389 */     Object value = event.getItemValue();
/* 390 */     if ((value instanceof CoachCard)) {
/* 391 */       CoachCard equipment = (CoachCard)value;
/*     */       
/*     */ 
/* 394 */       UICoachEquipmentMessage message = new UICoachEquipmentMessage();
/* 395 */       message.setCoach(localCoach);
/* 396 */       message.setEquipment(equipment);
/* 397 */       message.setPosition(Short.valueOf(position).shortValue());
/* 398 */       message.setId(16703);
/* 399 */       Worker.getInstance().pushMessage(message);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void deleteEquipment(DropEvent event, LocalCoach localCoach)
/*     */   {
/* 410 */     Object value = event.getValue();
/* 411 */     if ((value != null) && ((value instanceof CoachCard))) {
/* 412 */       CoachCard equipment = (CoachCard)value;
/*     */       
/* 414 */       if (event.getElement() != null)
/*     */       {
/* 416 */         UICoachEquipmentMessage message = new UICoachEquipmentMessage();
/* 417 */         message.setCoach(localCoach);
/* 418 */         message.setEquipment(equipment);
/* 419 */         message.setId(16706);
/* 420 */         Worker.getInstance().pushMessage(message);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean validateEmoteDrop(IDragNDropable src, Object srcValue, IDragNDropable dest, Object destValue, Object value, LocalCoach localCoach)
/*     */   {
/* 432 */     if ((value != null) && ((value instanceof CoachCard))) {
/* 433 */       CoachCard emote = (CoachCard)value;
/* 434 */       if (localCoach != null)
/*     */       {
/* 436 */         return false;
/*     */       }
/*     */     }
/*     */     
/* 440 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void dropEquipment(DropEvent event, LocalCoach localCoach)
/*     */   {
/* 450 */     Object value = event.getValue();
/* 451 */     if ((value != null) && ((value instanceof CoachCard))) {
/* 452 */       CoachCard emote = (CoachCard)value;
/*     */       
/*     */ 
/* 455 */       UICoachEquipmentMessage message = new UICoachEquipmentMessage();
/* 456 */       message.setCoach(localCoach);
/* 457 */       message.setEquipment(emote);
/* 458 */       message.setId(16702);
/* 459 */       Worker.getInstance().pushMessage(message);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void dragEquipment(DragEvent event, LocalCoach localCoach)
/*     */   {
/* 470 */     Object value = event.getValue();
/* 471 */     if ((value != null) && ((value instanceof CoachCard))) {
/* 472 */       CoachCard emote = (CoachCard)value;
/*     */       
/* 474 */       if (event.getElement() != null)
/*     */       {
/* 476 */         UICoachEquipmentMessage message = new UICoachEquipmentMessage();
/* 477 */         message.setCoach(localCoach);
/* 478 */         message.setEquipment(emote);
/* 479 */         message.setId(16703);
/* 480 */         Worker.getInstance().pushMessage(message);
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
/*     */   public static void lockUnlockEquipment(Event event, LocalCoach localCoach, CoachCard equipment)
/*     */   {
/* 493 */     UICoachEquipmentMessage message = new UICoachEquipmentMessage();
/* 494 */     message.setCoach(localCoach);
/* 495 */     message.setEquipment(equipment);
/* 496 */     message.setId(16708);
/* 497 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void equipEmote(ItemDoubleClickEvent event, LocalCoach localCoach)
/*     */   {
/* 506 */     Object value = event.getItemValue();
/* 507 */     if ((value instanceof CoachCard)) {
/* 508 */       CoachCard equipment = (CoachCard)value;
/*     */       
/*     */ 
/* 511 */       UICoachEquipmentMessage message = new UICoachEquipmentMessage();
/* 512 */       message.setCoach(localCoach);
/* 513 */       message.setEquipment(equipment);
/* 514 */       message.setPosition((short)-1);
/* 515 */       message.setId(16709);
/* 516 */       Worker.getInstance().pushMessage(message);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void unequipEmote(ItemDoubleClickEvent event, LocalCoach localCoach)
/*     */   {
/* 526 */     Object value = event.getItemValue();
/* 527 */     if ((value instanceof CoachCard)) {
/* 528 */       CoachCard emote = (CoachCard)value;
/*     */       
/*     */ 
/* 531 */       UICoachEquipmentMessage message = new UICoachEquipmentMessage();
/* 532 */       message.setCoach(localCoach);
/* 533 */       message.setEquipment(emote);
/* 534 */       message.setPosition((short)-1);
/* 535 */       message.setId(16710);
/* 536 */       Worker.getInstance().pushMessage(message);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void openCloseCoachEquipmentDialog(Event event)
/*     */   {
/* 547 */     UIMessage message = new UIMessage();
/* 548 */     message.setId(20008);
/* 549 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void openCloseFusionLaboratoryDialog(Event event)
/*     */   {
/* 559 */     UIMessage message = new UIMessage();
/* 560 */     message.setId(20010);
/* 561 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void openCloseInventoryStatisticsDialog(Event event)
/*     */   {
/* 571 */     UIMessage message = new UIMessage();
/* 572 */     message.setId(20010);
/* 573 */     Worker.getInstance().pushMessage(message);
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\actions\CoachManagementActions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */