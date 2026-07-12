/*     */ package com.ankamagames.dofusarena.client.ui.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.baseImpl.graphicalClient.ui.shortcuts.ShortcutManager;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaConfiguration;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.fighter.FighterCard;
/*     */ import com.ankamagames.dofusarena.client.core.game.fighter.EditableFighter;
/*     */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*     */ import com.ankamagames.dofusarena.client.core.game.fighter.FighterManager;
/*     */ import com.ankamagames.dofusarena.client.core.game.spell.Spell;
/*     */ import com.ankamagames.dofusarena.client.core.game.team.EditableTeamPreset;
/*     */ import com.ankamagames.dofusarena.client.core.game.team.TeamPresetManager;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.frame.NetTeamManagementFrame;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.teamManagement.CreateFighterInformationRequestMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.teamManagement.DeleteFighterInformationRequestMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.teamManagement.DeleteTeamPresetRequestMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.teamManagement.SaveTeamPresetRequestMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.teamManagement.UpdateFighterInventoryRequestMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.Dialogs;
/*     */ import com.ankamagames.dofusarena.client.ui.actions.TeamManagementActions;
/*     */ import com.ankamagames.dofusarena.client.ui.progress.DofusArenaProgressMonitorManager;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.teamManagement.UIFighterBreedMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.teamManagement.UIFighterEquipmentMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.teamManagement.UIFighterMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.teamManagement.UIFighterSexMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.teamManagement.UIFighterSpellMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.teamManagement.UISelectTeamPresetRequestMessage;
/*     */ import com.ankamagames.dofusarena.common.game.team.TeamPreset;
/*     */ import com.ankamagames.framework.kernel.FrameHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Worker;
/*     */ import com.ankamagames.framework.kernel.events.MessageFrame;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.core.messagebox.IMessageBoxEventListener;
/*     */ import com.ankamagames.xulor.core.messagebox.MessageBoxControler;
/*     */ import com.ankamagames.xulor.event.IMouseClickListener;
/*     */ import com.ankamagames.xulor.event.MouseClickEvent;
/*     */ import com.ankamagames.xulor.event.listener.MouseClickListener;
/*     */ import com.ankamagames.xulor.template.IPopupMenu;
/*     */ import java.util.Collection;
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
/*     */ public class UITeamManagementFrame
/*     */   implements MessageFrame
/*     */ {
/*  60 */   private static UITeamManagementFrame m_instance = new UITeamManagementFrame();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static UITeamManagementFrame getInstance() {
/*  66 */     return m_instance; } public boolean onMessage(Message message) { EditableFighter newFighter; UIFighterMessage uIFighterMessage4; UIFighterBreedMessage uIFighterBreedMessage; UIFighterSexMessage uIFighterSexMessage; UIFighterMessage uIFighterMessage3; UIFighterSpellMessage uIFighterSpellMessage; UIFighterEquipmentMessage uIFighterEquipmentMessage; UIFighterMessage uIFighterMessage2; EditableFighter editableFighter; UIFighterMessage uIFighterMessage1; EditableTeamPreset editableTeamPreset; UISelectTeamPresetRequestMessage uISelectTeamPresetRequestMessage; EditableTeamPreset editableTeamPreset2; final EditableTeamPreset teamPreset; UIFighterMessage msg; EditableFighter editableFighter3; final Fighter fighter; EditableFighter editableFighter2; Spell spell; EditableFighter editableFighter1;
/*     */     FighterCard equipment;
/*     */     final Fighter fighter;
/*     */     IPopupMenu popupMenu;
/*     */     TeamPreset teamPreset;
/*     */     EditableTeamPreset editableTeamPreset3;
/*     */     Spell spell1;
/*     */     FighterCard fighterCard1;
/*     */     Collection<TeamPreset> teamPresets;
/*  75 */     switch (message.getId()) {
/*     */ 
/*     */ 
/*     */       
/*     */       case 16605:
/*  80 */         newFighter = FighterManager.getInstance().createEmptyEditableFighter();
/*  81 */         newFighter.setBreedAndSex((byte)1, (byte)0);
/*     */ 
/*     */         
/*  84 */         FighterManager.getInstance().setEditableFighter(newFighter);
/*     */ 
/*     */         
/*  87 */         Xulor.getInstance().load("fighterCreationDialog", Dialogs.getDialogPath("fighterCreationDialog"), 129L, (short)10003);
/*     */         
/*  89 */         return false;
/*     */ 
/*     */       
/*     */       case 16607:
/*  93 */         uIFighterMessage4 = (UIFighterMessage)message;
/*     */         
/*  95 */         editableFighter3 = (EditableFighter)uIFighterMessage4.getFighter();
/*  96 */         if (editableFighter3 != null) {
/*  97 */           editableFighter3.setPreviousSkinIndex();
/*     */         }
/*     */         
/* 100 */         return false;
/*     */ 
/*     */       
/*     */       case 16608:
/* 104 */         uIFighterMessage4 = (UIFighterMessage)message;
/*     */         
/* 106 */         editableFighter3 = (EditableFighter)uIFighterMessage4.getFighter();
/* 107 */         if (editableFighter3 != null) {
/* 108 */           editableFighter3.setNextSkinIndex();
/*     */         }
/*     */         
/* 111 */         return false;
/*     */ 
/*     */       
/*     */       case 16609:
/* 115 */         uIFighterBreedMessage = (UIFighterBreedMessage)message;
/*     */         
/* 117 */         editableFighter3 = (EditableFighter)uIFighterBreedMessage.getFighter();
/* 118 */         if (editableFighter3 != null && 
/* 119 */           editableFighter3.getBreed().getId() != uIFighterBreedMessage.getBreedId()) {
/* 120 */           editableFighter3.setBreedFromId(uIFighterBreedMessage.getBreedId());
/* 121 */           editableFighter3.setSkinIndex(editableFighter3.getSkinIndex());
/*     */         } 
/*     */ 
/*     */         
/* 125 */         return false;
/*     */ 
/*     */       
/*     */       case 16610:
/* 129 */         uIFighterSexMessage = (UIFighterSexMessage)message;
/*     */         
/* 131 */         editableFighter3 = (EditableFighter)uIFighterSexMessage.getFighter();
/* 132 */         if (editableFighter3 != null && 
/* 133 */           editableFighter3.getSex() != uIFighterSexMessage.getSex()) {
/* 134 */           editableFighter3.setSex(uIFighterSexMessage.getSex());
/* 135 */           editableFighter3.setSkinIndex(editableFighter3.getSkinIndex());
/*     */         } 
/*     */ 
/*     */         
/* 139 */         return false;
/*     */ 
/*     */       
/*     */       case 16627:
/* 143 */         uIFighterMessage3 = (UIFighterMessage)message;
/*     */         
/* 145 */         editableFighter3 = (EditableFighter)uIFighterMessage3.getFighter();
/* 146 */         if (editableFighter3 != null) {
/* 147 */           editableFighter3.setNextDirection();
/*     */         }
/*     */         
/* 150 */         return false;
/*     */ 
/*     */       
/*     */       case 16626:
/* 154 */         uIFighterMessage3 = (UIFighterMessage)message;
/*     */         
/* 156 */         editableFighter3 = (EditableFighter)uIFighterMessage3.getFighter();
/* 157 */         if (editableFighter3 != null) {
/* 158 */           editableFighter3.setPreviousDirection();
/*     */         }
/*     */         
/* 161 */         return false;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 16606:
/* 167 */         FighterManager.getInstance().releaseEditableFighter();
/*     */ 
/*     */         
/* 170 */         Xulor.getInstance().unload("fighterCreationDialog");
/*     */         
/* 172 */         return false;
/*     */ 
/*     */       
/*     */       case 16614:
/* 176 */         uIFighterMessage3 = (UIFighterMessage)message;
/*     */ 
/*     */         
/* 179 */         fighter1 = uIFighterMessage3.getFighter();
/* 180 */         if (fighter1 != null) {
/*     */           
/* 182 */           FighterManager.getInstance().setEditableFighter(fighter1.getEditableFighter());
/*     */ 
/*     */           
/* 185 */           Xulor.getInstance().load("fighterEditionDialog", Dialogs.getDialogPath("fighterEditionDialog"), 129L, (short)10002);
/*     */         } 
/*     */         
/* 188 */         return false;
/*     */ 
/*     */ 
/*     */       
/*     */       case 16615:
/* 193 */         Xulor.getInstance().unload("fighterEditionDialog");
/*     */         
/* 195 */         FighterManager.getInstance().releaseEditableFighter();
/*     */         
/* 197 */         return false;
/*     */ 
/*     */       
/*     */       case 16616:
/* 201 */         uIFighterMessage3 = (UIFighterMessage)message;
/*     */ 
/*     */         
/* 204 */         fighter1 = uIFighterMessage3.getFighter();
/* 205 */         if (fighter1 != null) {
/*     */ 
/*     */           
/* 208 */           MessageBoxControler messageBoxControler = Xulor.getInstance().msgBox(
/* 209 */               DofusArenaTranslator.getInstance().getString("question.saveEditableFighter", new Object[] { fighter1.getName()
/* 210 */                 }), 153);
/* 211 */           messageBoxControler.addEventListener(new IMessageBoxEventListener() {
/*     */                 public void messageBoxClosed(int type) {
/* 213 */                   if (type == 8) {
/*     */ 
/*     */                     
/* 216 */                     UpdateFighterInventoryRequestMessage netMessage = new UpdateFighterInventoryRequestMessage();
/* 217 */                     netMessage.setFighter(fighter);
/* 218 */                     DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage((Message)netMessage);
/*     */                   } 
/*     */                 }
/*     */               });
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 226 */         return false;
/*     */ 
/*     */       
/*     */       case 16618:
/* 230 */         uIFighterSpellMessage = (UIFighterSpellMessage)message;
/*     */         
/* 232 */         editableFighter2 = (EditableFighter)uIFighterSpellMessage.getFighter();
/* 233 */         spell1 = uIFighterSpellMessage.getSpell();
/* 234 */         if (editableFighter2 != null && spell1 != null) {
/* 235 */           editableFighter2.addSpell(spell1);
/*     */         }
/*     */         
/* 238 */         return false;
/*     */ 
/*     */       
/*     */       case 16619:
/* 242 */         uIFighterSpellMessage = (UIFighterSpellMessage)message;
/*     */         
/* 244 */         editableFighter2 = (EditableFighter)uIFighterSpellMessage.getFighter();
/* 245 */         spell1 = uIFighterSpellMessage.getSpell();
/* 246 */         if (editableFighter2 != null && spell1 != null) {
/* 247 */           editableFighter2.removeSpell(spell1);
/*     */         }
/*     */         
/* 250 */         return false;
/*     */ 
/*     */       
/*     */       case 16624:
/* 254 */         uIFighterSpellMessage = (UIFighterSpellMessage)message;
/*     */         
/* 256 */         spell = uIFighterSpellMessage.getSpell();
/* 257 */         if (spell != null)
/*     */         {
/* 259 */           Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("teamManagement.selectedCard", spell);
/*     */         }
/*     */         
/* 262 */         return false;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 16625:
/* 268 */         Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("teamManagement.selectedCard", null);
/*     */         
/* 270 */         return false;
/*     */ 
/*     */       
/*     */       case 16620:
/* 274 */         uIFighterEquipmentMessage = (UIFighterEquipmentMessage)message;
/*     */         
/* 276 */         editableFighter1 = (EditableFighter)uIFighterEquipmentMessage.getFighter();
/* 277 */         fighterCard1 = uIFighterEquipmentMessage.getEquipment();
/* 278 */         if (editableFighter1 != null && fighterCard1 != null) {
/* 279 */           editableFighter1.addEquipment(fighterCard1, uIFighterEquipmentMessage.getPosition());
/*     */         }
/*     */         
/* 282 */         return false;
/*     */ 
/*     */       
/*     */       case 16621:
/* 286 */         uIFighterEquipmentMessage = (UIFighterEquipmentMessage)message;
/*     */         
/* 288 */         editableFighter1 = (EditableFighter)uIFighterEquipmentMessage.getFighter();
/* 289 */         fighterCard1 = uIFighterEquipmentMessage.getEquipment();
/* 290 */         if (editableFighter1 != null && fighterCard1 != null) {
/* 291 */           editableFighter1.removeEquipment(fighterCard1);
/*     */         }
/*     */         
/* 294 */         return false;
/*     */ 
/*     */       
/*     */       case 16622:
/* 298 */         uIFighterEquipmentMessage = (UIFighterEquipmentMessage)message;
/*     */         
/* 300 */         equipment = uIFighterEquipmentMessage.getEquipment();
/* 301 */         if (equipment != null)
/*     */         {
/* 303 */           Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("teamManagement.selectedCard", equipment);
/*     */         }
/*     */         
/* 306 */         return false;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 16623:
/* 312 */         Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("teamManagement.selectedCard", null);
/*     */         
/* 314 */         return false;
/*     */ 
/*     */       
/*     */       case 16629:
/* 318 */         uIFighterMessage2 = (UIFighterMessage)message;
/*     */         
/* 320 */         fighter = uIFighterMessage2.getFighter();
/* 321 */         if (fighter != null)
/*     */         {
/* 323 */           Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("teamManagement.selectedCard", fighter);
/*     */         }
/*     */         
/* 326 */         return false;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 16630:
/* 332 */         Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("teamManagement.selectedCard", null);
/*     */         
/* 334 */         return false;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 16611:
/* 340 */         editableFighter = FighterManager.getInstance().getEditableFighter();
/* 341 */         if (editableFighter != null) {
/*     */           
/* 343 */           CreateFighterInformationRequestMessage netMessage = new CreateFighterInformationRequestMessage();
/* 344 */           netMessage.setFighterInformation(editableFighter.getFighterInformation());
/* 345 */           DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage((Message)netMessage);
/* 346 */           FighterManager.getInstance().releaseEditableFighter();
/*     */         } 
/*     */         
/* 349 */         return false;
/*     */ 
/*     */       
/*     */       case 16612:
/* 353 */         uIFighterMessage1 = (UIFighterMessage)message;
/*     */ 
/*     */         
/* 356 */         fighter = uIFighterMessage1.getFighter();
/* 357 */         if (fighter != null) {
/*     */ 
/*     */           
/* 360 */           MessageBoxControler messageBoxControler = Xulor.getInstance().msgBox(DofusArenaTranslator.getInstance().getString("question.removeFighter", new Object[] { fighter.getName()
/* 361 */                 }), 153);
/* 362 */           messageBoxControler.addEventListener(new IMessageBoxEventListener() {
/*     */                 public void messageBoxClosed(int type) {
/* 364 */                   if (type == 8) {
/*     */ 
/*     */                     
/* 367 */                     DeleteFighterInformationRequestMessage netMessage = new DeleteFighterInformationRequestMessage();
/* 368 */                     netMessage.setFighterId(fighter.getId());
/* 369 */                     DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage((Message)netMessage);
/*     */                   } 
/*     */                 }
/*     */               });
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 377 */         return false;
/*     */ 
/*     */ 
/*     */       
/*     */       case 16628:
/* 382 */         editableTeamPreset = TeamPresetManager.getInstance().getEditableTeamPreset();
/* 383 */         popupMenu = Xulor.getInstance().popupMenu();
/* 384 */         popupMenu.addLabel(DofusArenaTranslator.getInstance().getString("teamManagement.teamPresetList", new Object[0]), null);
/* 385 */         teamPresets = TeamPresetManager.getInstance().getTeamPresets();
/* 386 */         for (TeamPreset teamPreset1 : teamPresets) {
/* 387 */           popupMenu.addButton(teamPreset1.getName(), null, (IMouseClickListener)new MouseClickListener()
/*     */               {
/*     */                 public void run(MouseClickEvent event) {
/* 390 */                   UISelectTeamPresetRequestMessage message = new UISelectTeamPresetRequestMessage();
/* 391 */                   message.setTeamPresetId(teamPreset.getId());
/* 392 */                   Worker.getInstance().pushMessage((Message)message);
/*     */                 }
/*     */               }, 
/* 395 */               (editableTeamPreset != null && teamPreset1.getId() != editableTeamPreset.getId()));
/*     */         } 
/* 397 */         Xulor.getInstance().showPopupMenu(popupMenu);
/*     */         
/* 399 */         return false;
/*     */ 
/*     */       
/*     */       case 16617:
/* 403 */         uISelectTeamPresetRequestMessage = (UISelectTeamPresetRequestMessage)message;
/*     */ 
/*     */         
/* 406 */         teamPreset = TeamPresetManager.getInstance().getTeamPreset(Short.valueOf(uISelectTeamPresetRequestMessage.getTeamPresetId()));
/* 407 */         if (teamPreset != null) {
/*     */           
/* 409 */           TeamPresetManager.getInstance().setEditableTeamPreset(EditableTeamPreset.createEditableTeamPreset(teamPreset));
/*     */ 
/*     */           
/* 412 */           DofusArenaConfiguration.getInstance().setLastSelectedTeamPresetId(teamPreset.getId());
/*     */ 
/*     */           
/* 415 */           FighterManager.getInstance().updateFighterListProperty();
/*     */         } 
/*     */         
/* 418 */         return false;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 16602:
/* 424 */         TeamPresetManager.getInstance().setEditableTeamPreset(new EditableTeamPreset());
/*     */         
/* 426 */         return false;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 16603:
/* 432 */         editableTeamPreset2 = TeamPresetManager.getInstance().getEditableTeamPreset();
/* 433 */         if (editableTeamPreset2 != null) {
/*     */           
/* 435 */           SaveTeamPresetRequestMessage netMessage = new SaveTeamPresetRequestMessage();
/* 436 */           netMessage.setTeamPreset((TeamPreset)editableTeamPreset2);
/* 437 */           DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage((Message)netMessage);
/*     */         } 
/*     */         
/* 440 */         return false;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 16604:
/* 446 */         editableTeamPreset1 = TeamPresetManager.getInstance().getEditableTeamPreset();
/* 447 */         if (editableTeamPreset1 != null) {
/*     */ 
/*     */           
/* 450 */           MessageBoxControler messageBoxControler = Xulor.getInstance().msgBox(
/* 451 */               DofusArenaTranslator.getInstance().getString("question.removeTeamPreset", new Object[] { editableTeamPreset1.getName()
/* 452 */                 }), 153);
/* 453 */           messageBoxControler.addEventListener(new IMessageBoxEventListener() {
/*     */                 public void messageBoxClosed(int type) {
/* 455 */                   if (type == 8) {
/*     */ 
/*     */                     
/* 458 */                     DeleteTeamPresetRequestMessage netMessage = new DeleteTeamPresetRequestMessage();
/* 459 */                     netMessage.setTeamPresetId(teamPreset.getId());
/* 460 */                     DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage((Message)netMessage);
/*     */                   } 
/*     */                 }
/*     */               });
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 468 */         return false;
/*     */ 
/*     */       
/*     */       case 16613:
/* 472 */         msg = (UIFighterMessage)message;
/*     */ 
/*     */         
/* 475 */         editableTeamPreset3 = TeamPresetManager.getInstance().getEditableTeamPreset();
/* 476 */         if (editableTeamPreset3 != null) {
/*     */ 
/*     */           
/* 479 */           Fighter fighter2 = msg.getFighter();
/* 480 */           if (fighter2 != null) {
/*     */             
/* 482 */             if (editableTeamPreset3.contains(fighter2.getId())) {
/*     */               
/* 484 */               editableTeamPreset3.remove(fighter2.getId());
/*     */             } else {
/*     */               
/* 487 */               editableTeamPreset3.add(fighter2.getId());
/*     */             } 
/*     */ 
/*     */             
/* 491 */             Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged("teamManagement.editableTeamPreset", 
/* 492 */                 "fighters");
/* 493 */             Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged("teamManagement.editableTeamPreset", 
/* 494 */                 "value");
/* 495 */             FighterManager.getInstance().updateFighterListProperty();
/*     */           } 
/*     */         } 
/*     */         
/* 499 */         return false;
/*     */     } 
/*     */ 
/*     */     
/* 503 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getId() {
/* 512 */     return 0L;
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
/*     */   
/*     */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded) {
/* 530 */     if (!isAboutToBeAdded) {
/*     */ 
/*     */       
/* 533 */       DofusArenaGameEntity.getInstance().pushFrame((MessageFrame)NetTeamManagementFrame.getInstance());
/*     */ 
/*     */       
/* 536 */       Xulor.getInstance().putActionClass("dofusarena.teamManagement", TeamManagementActions.class);
/*     */ 
/*     */       
/* 539 */       ShortcutManager.getInstance().enableGroup("world", false);
/*     */ 
/*     */       
/* 542 */       openDialog();
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
/*     */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved) {
/* 554 */     if (!isAboutToBeRemoved) {
/*     */ 
/*     */       
/* 557 */       DofusArenaGameEntity.getInstance().removeFrame((MessageFrame)NetTeamManagementFrame.getInstance());
/*     */ 
/*     */       
/* 560 */       Xulor.getInstance().removeActionClass("dofusarena.teamManagement");
/*     */ 
/*     */       
/* 563 */       ShortcutManager.getInstance().enableGroup("world", (DofusArenaGameEntity.getInstance().getFight() == null));
/*     */ 
/*     */       
/* 566 */       closeDialog();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void openDialog() {
/* 576 */     Xulor.getInstance().load("teamManagementDialog", Dialogs.getDialogPath("teamManagementDialog"), 129L, (short)10001);
/*     */     
/* 578 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("menuBar.coachStatisticsButton", Boolean.valueOf(false));
/* 579 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("menuBar.coachInventoryButton", Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void closeDialog() {
/* 587 */     FighterManager.getInstance().releaseEditableFighter();
/*     */ 
/*     */     
/* 590 */     Xulor.getInstance().unload("teamManagementDialog");
/* 591 */     Xulor.getInstance().unload("fighterCreationDialog");
/* 592 */     Xulor.getInstance().unload("fighterEditionDialog");
/*     */     
/* 594 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("menuBar.coachStatisticsButton", Boolean.valueOf(true));
/* 595 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("menuBar.coachInventoryButton", Boolean.valueOf(true));
/*     */ 
/*     */     
/* 598 */     Xulor.getInstance().hidePopupMenu();
/*     */ 
/*     */     
/* 601 */     DofusArenaProgressMonitorManager.getInstance().done();
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\clien\\ui\protocol\frame\UITeamManagementFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */