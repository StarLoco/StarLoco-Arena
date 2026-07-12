/*     */ package com.ankamagames.dofusarena.client.network.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaConfiguration;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*     */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*     */ import com.ankamagames.dofusarena.client.core.game.fighter.FighterManager;
/*     */ import com.ankamagames.dofusarena.client.core.game.team.EditableTeamPreset;
/*     */ import com.ankamagames.dofusarena.client.core.game.team.TeamPresetManager;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.teamManagement.FighterInformationListRequestMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.teamManagement.TeamPresetListRequestMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.teamManagement.CreationFighterInformationMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.teamManagement.DeletionFighterInformationMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.teamManagement.DeletionTeamPresetMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.teamManagement.FighterInformationListMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.teamManagement.SaveTeamPresetMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.teamManagement.TeamPresetListMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.teamManagement.UpdatedFighterInformationInventoryMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.progress.DofusArenaProgressMonitorManager;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.FighterInformation;
/*     */ import com.ankamagames.dofusarena.common.game.team.TeamPreset;
/*     */ import com.ankamagames.framework.kernel.FrameHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.events.MessageFrame;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
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
/*     */ 
/*     */ public class NetTeamManagementFrame
/*     */   implements MessageFrame
/*     */ {
/*  48 */   protected static final Logger m_logger = Logger.getLogger(NetTeamManagementFrame.class);
/*     */   
/*  50 */   private static NetTeamManagementFrame m_instance = new NetTeamManagementFrame();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NetTeamManagementFrame getInstance() {
/*  56 */     return m_instance; } public boolean onMessage(Message message) { FighterInformationListMessage fighterInformationListMessage; CreationFighterInformationMessage creationFighterInformationMessage; DeletionFighterInformationMessage deletionFighterInformationMessage;
/*     */     UpdatedFighterInformationInventoryMessage updatedFighterInformationInventoryMessage;
/*     */     TeamPresetListMessage teamPresetListMessage;
/*     */     SaveTeamPresetMessage saveTeamPresetMessage;
/*     */     DeletionTeamPresetMessage msg;
/*     */     Fighter fighter;
/*     */     Iterable<TeamPreset> teamPresets;
/*     */     short lastSelectedTeamPresetId;
/*     */     EditableTeamPreset editableTeamPreset;
/*  65 */     switch (message.getId()) {
/*     */       
/*     */       case 6006:
/*  68 */         fighterInformationListMessage = (FighterInformationListMessage)message;
/*     */         
/*  70 */         if (!fighterInformationListMessage.getFighterInformations().isEmpty()) {
/*     */           
/*  72 */           for (Map.Entry<Long, byte[]> entry : (Iterable<Map.Entry<Long, byte[]>>)fighterInformationListMessage.getFighterInformations().entrySet()) {
/*  73 */             FighterInformation fighterInformation = new FighterInformation();
/*  74 */             fighterInformation.unserialize(entry.getValue());
/*     */             
/*  76 */             Fighter fighter1 = new Fighter();
/*  77 */             fighter1.setId(((Long)entry.getKey()).longValue());
/*  78 */             fighter1.initWithFighterInformation(fighterInformation);
/*     */ 
/*     */             
/*  81 */             FighterManager.getInstance().addFighter(fighter1, false);
/*     */           } 
/*     */           
/*  84 */           FighterManager.getInstance().updateFighterListProperty();
/*     */         } 
/*     */         
/*  87 */         return false;
/*     */ 
/*     */       
/*     */       case 6000:
/*  91 */         creationFighterInformationMessage = (CreationFighterInformationMessage)message;
/*     */         
/*  93 */         switch (creationFighterInformationMessage.getErrorCode())
/*     */         
/*     */         { case 0:
/*  96 */             fighter = creationFighterInformationMessage.getFighter();
/*  97 */             if (fighter != null) {
/*     */               
/*  99 */               FighterManager.getInstance().addFighter(fighter);
/*     */ 
/*     */               
/* 102 */               Xulor.getInstance().unload("fighterCreationDialog");
/*     */             } 
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
/* 114 */             return false;case 20: Xulor.getInstance().msgBox(DofusArenaTranslator.getInstance().getString("error.teamManagement.noMoreRoom", new Object[0])); return false; }  Xulor.getInstance().msgBox(DofusArenaTranslator.getInstance().getString("error.teamManagement.fighterCreation", new Object[] { Byte.valueOf(creationFighterInformationMessage.getErrorCode()) })); return false;
/*     */ 
/*     */       
/*     */       case 6002:
/* 118 */         deletionFighterInformationMessage = (DeletionFighterInformationMessage)message;
/*     */         
/* 120 */         if (deletionFighterInformationMessage.getErrorCode() == 0) {
/*     */           
/* 122 */           long fighterDefinitionId = deletionFighterInformationMessage.getFighterInformationId();
/*     */           
/* 124 */           Fighter fighter1 = FighterManager.getInstance().getFighter(fighterDefinitionId);
/*     */           
/* 126 */           FighterManager.getInstance().removeFighter(fighter1);
/*     */ 
/*     */ 
/*     */           
/* 130 */           TeamPresetManager.getInstance().removeFighterDefinitionFromAllTeamPresets(fighterDefinitionId);
/*     */           
/* 132 */           fighter1.release();
/*     */         }
/*     */         else {
/*     */           
/* 136 */           Xulor.getInstance().msgBox(DofusArenaTranslator.getInstance().getString("error.teamManagement.fighterDeletion", new Object[] { Byte.valueOf(deletionFighterInformationMessage.getErrorCode()) }));
/*     */         } 
/*     */         
/* 139 */         return false;
/*     */ 
/*     */       
/*     */       case 6010:
/* 143 */         updatedFighterInformationInventoryMessage = (UpdatedFighterInformationInventoryMessage)message;
/*     */         
/* 145 */         if (updatedFighterInformationInventoryMessage.getErrorCode() == 0) {
/*     */           
/* 147 */           long fighterId = updatedFighterInformationInventoryMessage.getFighterId();
/* 148 */           Fighter fighter1 = FighterManager.getInstance().getFighter(fighterId);
/* 149 */           if (fighter1 != null) {
/*     */ 
/*     */             
/* 152 */             fighter1.getSpellInventory().unserialize(updatedFighterInformationInventoryMessage.getSerializedSpellInventory());
/* 153 */             fighter1.getEquipmentInventory().unserialize(updatedFighterInformationInventoryMessage.getSerializedEquipmentInventory());
/*     */ 
/*     */             
/* 156 */             fighter1.computeValue();
/*     */ 
/*     */             
/* 159 */             FighterManager.getInstance().updateFighterListProperty();
/*     */ 
/*     */             
/* 162 */             TeamPresetManager.getInstance().updateTeamPresetListProperty();
/*     */ 
/*     */             
/* 165 */             Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged("teamManagement.editableTeamPreset", "value");
/*     */ 
/*     */             
/* 168 */             FighterManager.getInstance().releaseEditableFighter();
/*     */ 
/*     */             
/* 171 */             Xulor.getInstance().unload("fighterEditionDialog");
/*     */           } else {
/*     */             
/* 174 */             m_logger.error("Le fighter " + fighterId + " est inconnu !");
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 179 */           Xulor.getInstance().msgBox(DofusArenaTranslator.getInstance().getString("error.teamManagement.fighterSave", new Object[] { Byte.valueOf(updatedFighterInformationInventoryMessage.getErrorCode()) }));
/*     */         } 
/*     */         
/* 182 */         return false;
/*     */ 
/*     */       
/*     */       case 6030:
/* 186 */         teamPresetListMessage = (TeamPresetListMessage)message;
/*     */ 
/*     */         
/* 189 */         DofusArenaProgressMonitorManager.getInstance().done();
/*     */ 
/*     */         
/* 192 */         teamPresets = teamPresetListMessage.getTeamPresets();
/*     */ 
/*     */         
/* 195 */         TeamPresetManager.getInstance().setTeamPresets(teamPresets);
/*     */ 
/*     */         
/* 198 */         lastSelectedTeamPresetId = DofusArenaConfiguration.getInstance().getLastSelectedTeamPresetId();
/* 199 */         editableTeamPreset = null;
/* 200 */         if (lastSelectedTeamPresetId != -1) {
/* 201 */           editableTeamPreset = EditableTeamPreset.createEditableTeamPreset(TeamPresetManager.getInstance().getTeamPreset(Short.valueOf(lastSelectedTeamPresetId)));
/*     */         } else {
/* 203 */           editableTeamPreset = TeamPresetManager.getInstance().createEmptyEditableTeamPreset();
/*     */         } 
/*     */ 
/*     */         
/* 207 */         TeamPresetManager.getInstance().setEditableTeamPreset(editableTeamPreset);
/*     */ 
/*     */         
/* 210 */         FighterManager.getInstance().updateFighterListProperty();
/*     */         
/* 212 */         return false;
/*     */ 
/*     */       
/*     */       case 6020:
/* 216 */         saveTeamPresetMessage = (SaveTeamPresetMessage)message;
/*     */         
/* 218 */         if (saveTeamPresetMessage.getErrorCode() == 0) {
/*     */ 
/*     */           
/* 221 */           TeamPreset teamPreset = saveTeamPresetMessage.getTeamPreset();
/* 222 */           if (teamPreset != null)
/*     */           {
/* 224 */             TeamPresetManager.getInstance().addTeamPreset(teamPreset);
/*     */ 
/*     */             
/* 227 */             TeamPresetManager.getInstance().setEditableTeamPreset(EditableTeamPreset.createEditableTeamPreset(teamPreset));
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 232 */           Xulor.getInstance().msgBox(DofusArenaTranslator.getInstance().getString("error.teamManagement.teamPresetSave", new Object[] { Byte.valueOf(saveTeamPresetMessage.getErrorCode()) }));
/*     */         } 
/*     */         
/* 235 */         return false;
/*     */ 
/*     */       
/*     */       case 6022:
/* 239 */         msg = (DeletionTeamPresetMessage)message;
/*     */         
/* 241 */         if (msg.getErrorCode() == 0) {
/*     */ 
/*     */           
/* 244 */           TeamPresetManager.getInstance().removeTeamPreset(msg.getTeamPresetId());
/*     */         }
/*     */         else {
/*     */           
/* 248 */           Xulor.getInstance().msgBox(DofusArenaTranslator.getInstance().getString("error.teamManagement.teamPresetDeletion", new Object[] { Byte.valueOf(msg.getErrorCode()) }));
/*     */         } 
/*     */         
/* 251 */         return false;
/*     */     } 
/*     */ 
/*     */     
/* 255 */     return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getId() {
/* 264 */     return 0L;
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
/* 282 */     if (!isAboutToBeAdded) {
/*     */ 
/*     */       
/* 285 */       if (FighterManager.getInstance().isEmpty()) {
/* 286 */         FighterInformationListRequestMessage fighterListRequestMessage = new FighterInformationListRequestMessage();
/* 287 */         DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage((Message)fighterListRequestMessage);
/*     */       } 
/*     */ 
/*     */       
/* 291 */       if (TeamPresetManager.getInstance().isEmpty()) {
/*     */         
/* 293 */         DofusArenaProgressMonitorManager.getInstance().getProgressMonitor(true).beginTask(DofusArenaTranslator.getInstance().getString("loading", new Object[0]), 0);
/*     */         
/* 295 */         TeamPresetListRequestMessage teamPresetListRequestMessage = new TeamPresetListRequestMessage();
/* 296 */         DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage((Message)teamPresetListRequestMessage);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved) {}
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\frame\NetTeamManagementFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */