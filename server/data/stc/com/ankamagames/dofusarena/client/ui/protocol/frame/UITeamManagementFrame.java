/*     */ package com.ankamagames.dofusarena.client.ui.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.NetworkEntity;
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
/*     */ import com.ankamagames.dofusarena.common.game.fighter.Breed;
/*     */ import com.ankamagames.dofusarena.common.game.team.TeamPreset;
/*     */ import com.ankamagames.framework.kernel.FrameHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Worker;
/*     */ import com.ankamagames.framework.kernel.events.MessageFrame;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.core.messagebox.IMessageBoxEventListener;
/*     */ import com.ankamagames.xulor.core.messagebox.MessageBoxControler;
/*     */ import com.ankamagames.xulor.event.MouseClickEvent;
/*     */ import com.ankamagames.xulor.event.listener.MouseClickListener;
/*     */ import com.ankamagames.xulor.property.PropertiesProvider;
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
/*     */ public class UITeamManagementFrame
/*     */   implements MessageFrame
/*     */ {
/*  60 */   private static UITeamManagementFrame m_instance = new UITeamManagementFrame();
/*     */   
/*     */ 
/*     */ 
/*     */   public static UITeamManagementFrame getInstance()
/*     */   {
/*  66 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onMessage(Message message)
/*     */   {
/*  75 */     switch (message.getId())
/*     */     {
/*     */ 
/*     */ 
/*     */     case 16605: 
/*  80 */       EditableFighter newFighter = FighterManager.getInstance().createEmptyEditableFighter();
/*  81 */       newFighter.setBreedAndSex((byte)1, (byte)0);
/*     */       
/*     */ 
/*  84 */       FighterManager.getInstance().setEditableFighter(newFighter);
/*     */       
/*     */ 
/*  87 */       Xulor.getInstance().load("fighterCreationDialog", Dialogs.getDialogPath("fighterCreationDialog"), 129L, (short)10003);
/*     */       
/*  89 */       return false;
/*     */     
/*     */ 
/*     */     case 16607: 
/*  93 */       UIFighterMessage msg = (UIFighterMessage)message;
/*     */       
/*  95 */       EditableFighter fighter = (EditableFighter)msg.getFighter();
/*  96 */       if (fighter != null) {
/*  97 */         fighter.setPreviousSkinIndex();
/*     */       }
/*     */       
/* 100 */       return false;
/*     */     
/*     */ 
/*     */     case 16608: 
/* 104 */       UIFighterMessage msg = (UIFighterMessage)message;
/*     */       
/* 106 */       EditableFighter fighter = (EditableFighter)msg.getFighter();
/* 107 */       if (fighter != null) {
/* 108 */         fighter.setNextSkinIndex();
/*     */       }
/*     */       
/* 111 */       return false;
/*     */     
/*     */ 
/*     */     case 16609: 
/* 115 */       UIFighterBreedMessage msg = (UIFighterBreedMessage)message;
/*     */       
/* 117 */       EditableFighter fighter = (EditableFighter)msg.getFighter();
/* 118 */       if ((fighter != null) && 
/* 119 */         (fighter.getBreed().getId() != msg.getBreedId())) {
/* 120 */         fighter.setBreedFromId(msg.getBreedId());
/* 121 */         fighter.setSkinIndex(fighter.getSkinIndex());
/*     */       }
/*     */       
/*     */ 
/* 125 */       return false;
/*     */     
/*     */ 
/*     */     case 16610: 
/* 129 */       UIFighterSexMessage msg = (UIFighterSexMessage)message;
/*     */       
/* 131 */       EditableFighter fighter = (EditableFighter)msg.getFighter();
/* 132 */       if ((fighter != null) && 
/* 133 */         (fighter.getSex() != msg.getSex())) {
/* 134 */         fighter.setSex(msg.getSex());
/* 135 */         fighter.setSkinIndex(fighter.getSkinIndex());
/*     */       }
/*     */       
/*     */ 
/* 139 */       return false;
/*     */     
/*     */ 
/*     */     case 16627: 
/* 143 */       UIFighterMessage msg = (UIFighterMessage)message;
/*     */       
/* 145 */       EditableFighter fighter = (EditableFighter)msg.getFighter();
/* 146 */       if (fighter != null) {
/* 147 */         fighter.setNextDirection();
/*     */       }
/*     */       
/* 150 */       return false;
/*     */     
/*     */ 
/*     */     case 16626: 
/* 154 */       UIFighterMessage msg = (UIFighterMessage)message;
/*     */       
/* 156 */       EditableFighter fighter = (EditableFighter)msg.getFighter();
/* 157 */       if (fighter != null) {
/* 158 */         fighter.setPreviousDirection();
/*     */       }
/*     */       
/* 161 */       return false;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     case 16606: 
/* 167 */       FighterManager.getInstance().releaseEditableFighter();
/*     */       
/*     */ 
/* 170 */       Xulor.getInstance().unload("fighterCreationDialog");
/*     */       
/* 172 */       return false;
/*     */     
/*     */ 
/*     */     case 16614: 
/* 176 */       UIFighterMessage msg = (UIFighterMessage)message;
/*     */       
/*     */ 
/* 179 */       Fighter fighter = msg.getFighter();
/* 180 */       if (fighter != null)
/*     */       {
/* 182 */         FighterManager.getInstance().setEditableFighter(fighter.getEditableFighter());
/*     */         
/*     */ 
/* 185 */         Xulor.getInstance().load("fighterEditionDialog", Dialogs.getDialogPath("fighterEditionDialog"), 129L, (short)10002);
/*     */       }
/*     */       
/* 188 */       return false;
/*     */     
/*     */ 
/*     */ 
/*     */     case 16615: 
/* 193 */       Xulor.getInstance().unload("fighterEditionDialog");
/*     */       
/* 195 */       FighterManager.getInstance().releaseEditableFighter();
/*     */       
/* 197 */       return false;
/*     */     
/*     */ 
/*     */     case 16616: 
/* 201 */       UIFighterMessage msg = (UIFighterMessage)message;
/*     */       
/*     */ 
/* 204 */       final Fighter fighter = msg.getFighter();
/* 205 */       if (fighter != null)
/*     */       {
/*     */ 
/* 208 */         MessageBoxControler messageBoxControler = Xulor.getInstance().msgBox(
/* 209 */           DofusArenaTranslator.getInstance().getString("question.saveEditableFighter", new Object[] { fighter.getName() }), 
/* 210 */           153);
/* 211 */         messageBoxControler.addEventListener(new IMessageBoxEventListener() {
/*     */           public void messageBoxClosed(int type) {
/* 213 */             if (type == 8)
/*     */             {
/*     */ 
/* 216 */               UpdateFighterInventoryRequestMessage netMessage = new UpdateFighterInventoryRequestMessage();
/* 217 */               netMessage.setFighter(fighter);
/* 218 */               DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage(netMessage);
/*     */             }
/*     */           }
/*     */         });
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 226 */       return false;
/*     */     
/*     */ 
/*     */     case 16618: 
/* 230 */       UIFighterSpellMessage msg = (UIFighterSpellMessage)message;
/*     */       
/* 232 */       EditableFighter fighter = (EditableFighter)msg.getFighter();
/* 233 */       Spell spell = msg.getSpell();
/* 234 */       if ((fighter != null) && (spell != null)) {
/* 235 */         fighter.addSpell(spell);
/*     */       }
/*     */       
/* 238 */       return false;
/*     */     
/*     */ 
/*     */     case 16619: 
/* 242 */       UIFighterSpellMessage msg = (UIFighterSpellMessage)message;
/*     */       
/* 244 */       EditableFighter fighter = (EditableFighter)msg.getFighter();
/* 245 */       Spell spell = msg.getSpell();
/* 246 */       if ((fighter != null) && (spell != null)) {
/* 247 */         fighter.removeSpell(spell);
/*     */       }
/*     */       
/* 250 */       return false;
/*     */     
/*     */ 
/*     */     case 16624: 
/* 254 */       UIFighterSpellMessage msg = (UIFighterSpellMessage)message;
/*     */       
/* 256 */       Spell spell = msg.getSpell();
/* 257 */       if (spell != null)
/*     */       {
/* 259 */         Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("teamManagement.selectedCard", spell);
/*     */       }
/*     */       
/* 262 */       return false;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     case 16625: 
/* 268 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("teamManagement.selectedCard", null);
/*     */       
/* 270 */       return false;
/*     */     
/*     */ 
/*     */     case 16620: 
/* 274 */       UIFighterEquipmentMessage msg = (UIFighterEquipmentMessage)message;
/*     */       
/* 276 */       EditableFighter editableFighter = (EditableFighter)msg.getFighter();
/* 277 */       FighterCard equipment = msg.getEquipment();
/* 278 */       if ((editableFighter != null) && (equipment != null)) {
/* 279 */         editableFighter.addEquipment(equipment, msg.getPosition());
/*     */       }
/*     */       
/* 282 */       return false;
/*     */     
/*     */ 
/*     */     case 16621: 
/* 286 */       UIFighterEquipmentMessage msg = (UIFighterEquipmentMessage)message;
/*     */       
/* 288 */       EditableFighter editableFighter = (EditableFighter)msg.getFighter();
/* 289 */       FighterCard equipment = msg.getEquipment();
/* 290 */       if ((editableFighter != null) && (equipment != null)) {
/* 291 */         editableFighter.removeEquipment(equipment);
/*     */       }
/*     */       
/* 294 */       return false;
/*     */     
/*     */ 
/*     */     case 16622: 
/* 298 */       UIFighterEquipmentMessage msg = (UIFighterEquipmentMessage)message;
/*     */       
/* 300 */       FighterCard equipment = msg.getEquipment();
/* 301 */       if (equipment != null)
/*     */       {
/* 303 */         Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("teamManagement.selectedCard", equipment);
/*     */       }
/*     */       
/* 306 */       return false;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     case 16623: 
/* 312 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("teamManagement.selectedCard", null);
/*     */       
/* 314 */       return false;
/*     */     
/*     */ 
/*     */     case 16629: 
/* 318 */       UIFighterMessage msg = (UIFighterMessage)message;
/*     */       
/* 320 */       Fighter fighter = msg.getFighter();
/* 321 */       if (fighter != null)
/*     */       {
/* 323 */         Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("teamManagement.selectedCard", fighter);
/*     */       }
/*     */       
/* 326 */       return false;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     case 16630: 
/* 332 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("teamManagement.selectedCard", null);
/*     */       
/* 334 */       return false;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     case 16611: 
/* 340 */       EditableFighter editableFighter = FighterManager.getInstance().getEditableFighter();
/* 341 */       if (editableFighter != null)
/*     */       {
/* 343 */         CreateFighterInformationRequestMessage netMessage = new CreateFighterInformationRequestMessage();
/* 344 */         netMessage.setFighterInformation(editableFighter.getFighterInformation());
/* 345 */         DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage(netMessage);
/* 346 */         FighterManager.getInstance().releaseEditableFighter();
/*     */       }
/*     */       
/* 349 */       return false;
/*     */     
/*     */ 
/*     */     case 16612: 
/* 353 */       UIFighterMessage msg = (UIFighterMessage)message;
/*     */       
/*     */ 
/* 356 */       final Fighter fighter = msg.getFighter();
/* 357 */       if (fighter != null)
/*     */       {
/*     */ 
/* 360 */         MessageBoxControler messageBoxControler = Xulor.getInstance().msgBox(DofusArenaTranslator.getInstance().getString("question.removeFighter", new Object[] { fighter.getName() }), 
/* 361 */           153);
/* 362 */         messageBoxControler.addEventListener(new IMessageBoxEventListener() {
/*     */           public void messageBoxClosed(int type) {
/* 364 */             if (type == 8)
/*     */             {
/*     */ 
/* 367 */               DeleteFighterInformationRequestMessage netMessage = new DeleteFighterInformationRequestMessage();
/* 368 */               netMessage.setFighterId(fighter.getId());
/* 369 */               DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage(netMessage);
/*     */             }
/*     */           }
/*     */         });
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 377 */       return false;
/*     */     
/*     */ 
/*     */ 
/*     */     case 16628: 
/* 382 */       EditableTeamPreset editableTeamPreset = TeamPresetManager.getInstance().getEditableTeamPreset();
/* 383 */       IPopupMenu popupMenu = Xulor.getInstance().popupMenu();
/* 384 */       popupMenu.addLabel(DofusArenaTranslator.getInstance().getString("teamManagement.teamPresetList", new Object[0]), null);
/* 385 */       Collection<TeamPreset> teamPresets = TeamPresetManager.getInstance().getTeamPresets();
/* 386 */       for (final TeamPreset teamPreset : teamPresets) {
/* 387 */         popupMenu.addButton(teamPreset.getName(), null, new MouseClickListener()
/*     */         {
/*     */           public void run(MouseClickEvent event) {
/* 390 */             UISelectTeamPresetRequestMessage message = new UISelectTeamPresetRequestMessage();
/* 391 */             message.setTeamPresetId(teamPreset.getId());
/* 392 */             Worker.getInstance().pushMessage(message);
/*     */           }
/*     */           
/* 395 */         }, (editableTeamPreset != null) && (teamPreset.getId() != editableTeamPreset.getId()));
/*     */       }
/* 397 */       Xulor.getInstance().showPopupMenu(popupMenu);
/*     */       
/* 399 */       return false;
/*     */     
/*     */ 
/*     */     case 16617: 
/* 403 */       UISelectTeamPresetRequestMessage msg = (UISelectTeamPresetRequestMessage)message;
/*     */       
/*     */ 
/* 406 */       TeamPreset teamPreset = TeamPresetManager.getInstance().getTeamPreset(Short.valueOf(msg.getTeamPresetId()));
/* 407 */       if (teamPreset != null)
/*     */       {
/* 409 */         TeamPresetManager.getInstance().setEditableTeamPreset(EditableTeamPreset.createEditableTeamPreset(teamPreset));
/*     */         
/*     */ 
/* 412 */         DofusArenaConfiguration.getInstance().setLastSelectedTeamPresetId(teamPreset.getId());
/*     */         
/*     */ 
/* 415 */         FighterManager.getInstance().updateFighterListProperty();
/*     */       }
/*     */       
/* 418 */       return false;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     case 16602: 
/* 424 */       TeamPresetManager.getInstance().setEditableTeamPreset(new EditableTeamPreset());
/*     */       
/* 426 */       return false;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     case 16603: 
/* 432 */       TeamPreset teamPreset = TeamPresetManager.getInstance().getEditableTeamPreset();
/* 433 */       if (teamPreset != null)
/*     */       {
/* 435 */         SaveTeamPresetRequestMessage netMessage = new SaveTeamPresetRequestMessage();
/* 436 */         netMessage.setTeamPreset(teamPreset);
/* 437 */         DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage(netMessage);
/*     */       }
/*     */       
/* 440 */       return false;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     case 16604: 
/* 446 */       final TeamPreset teamPreset = TeamPresetManager.getInstance().getEditableTeamPreset();
/* 447 */       if (teamPreset != null)
/*     */       {
/*     */ 
/* 450 */         MessageBoxControler messageBoxControler = Xulor.getInstance().msgBox(
/* 451 */           DofusArenaTranslator.getInstance().getString("question.removeTeamPreset", new Object[] { teamPreset.getName() }), 
/* 452 */           153);
/* 453 */         messageBoxControler.addEventListener(new IMessageBoxEventListener() {
/*     */           public void messageBoxClosed(int type) {
/* 455 */             if (type == 8)
/*     */             {
/*     */ 
/* 458 */               DeleteTeamPresetRequestMessage netMessage = new DeleteTeamPresetRequestMessage();
/* 459 */               netMessage.setTeamPresetId(teamPreset.getId());
/* 460 */               DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage(netMessage);
/*     */             }
/*     */           }
/*     */         });
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 468 */       return false;
/*     */     
/*     */ 
/*     */     case 16613: 
/* 472 */       UIFighterMessage msg = (UIFighterMessage)message;
/*     */       
/*     */ 
/* 475 */       TeamPreset teamPreset = TeamPresetManager.getInstance().getEditableTeamPreset();
/* 476 */       if (teamPreset != null)
/*     */       {
/*     */ 
/* 479 */         Fighter fighter = msg.getFighter();
/* 480 */         if (fighter != null)
/*     */         {
/* 482 */           if (teamPreset.contains(fighter.getId()))
/*     */           {
/* 484 */             teamPreset.remove(fighter.getId());
/*     */           }
/*     */           else {
/* 487 */             teamPreset.add(fighter.getId());
/*     */           }
/*     */           
/*     */ 
/* 491 */           Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged("teamManagement.editableTeamPreset", 
/* 492 */             "fighters");
/* 493 */           Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged("teamManagement.editableTeamPreset", 
/* 494 */             "value");
/* 495 */           FighterManager.getInstance().updateFighterListProperty();
/*     */         }
/*     */       }
/*     */       
/* 499 */       return false;
/*     */     }
/*     */     
/*     */     
/* 503 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getId()
/*     */   {
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
/*     */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded)
/*     */   {
/* 530 */     if (!isAboutToBeAdded)
/*     */     {
/*     */ 
/* 533 */       DofusArenaGameEntity.getInstance().pushFrame(NetTeamManagementFrame.getInstance());
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
/*     */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved)
/*     */   {
/* 554 */     if (!isAboutToBeRemoved)
/*     */     {
/*     */ 
/* 557 */       DofusArenaGameEntity.getInstance().removeFrame(NetTeamManagementFrame.getInstance());
/*     */       
/*     */ 
/* 560 */       Xulor.getInstance().removeActionClass("dofusarena.teamManagement");
/*     */       
/*     */ 
/* 563 */       ShortcutManager.getInstance().enableGroup("world", DofusArenaGameEntity.getInstance().getFight() == null);
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
/*     */   protected void openDialog()
/*     */   {
/* 576 */     Xulor.getInstance().load("teamManagementDialog", Dialogs.getDialogPath("teamManagementDialog"), 129L, (short)10001);
/*     */     
/* 578 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("menuBar.coachStatisticsButton", Boolean.valueOf(false));
/* 579 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("menuBar.coachInventoryButton", Boolean.valueOf(false));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void closeDialog()
/*     */   {
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


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\protocol\frame\UITeamManagementFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */