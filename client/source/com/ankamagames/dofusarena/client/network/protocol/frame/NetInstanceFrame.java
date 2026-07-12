/*     */ package com.ankamagames.dofusarena.client.network.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.baseImpl.graphicalClient.ui.shortcuts.ShortcutManager;
/*     */ import com.ankamagames.baseImpl.graphics.alea.WorldManager;
/*     */ import com.ankamagames.baseImpl.graphics.alea.mobile.Mobile;
/*     */ import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaConfiguration;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.game.coach.LocalCoach;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.EnterInstanceMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UIChatFrame;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UICoachInventoryManagementFrame;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UIFightTeamManagementFrame;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UIIslandWorldSceneInteractionFrame;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UIMenuBarFrame;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UITeamManagementFrame;
/*     */ import com.ankamagames.dofusarena.common.game.fight.FightActionType;
/*     */ import com.ankamagames.framework.fileFormat.properties.PropertyException;
/*     */ import com.ankamagames.framework.kernel.FrameHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.events.MessageFrame;
/*     */ import com.ankamagames.framework.script.action.Action;
/*     */ import com.ankamagames.framework.script.action.ActionGroup;
/*     */ import com.ankamagames.framework.script.action.QueueActionGroupManager;
/*     */ import com.ankamagames.framework.sounds.SoundManager;
/*     */ import com.ankamagames.framework.sounds.group.MusicGroup;
/*     */ import com.ankamagames.graphics.isometric.DefaultIsoWorldTarget;
/*     */ import com.ankamagames.graphics.isometric.IsoWorldTarget;
/*     */ import java.util.LinkedList;
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
/*     */ public class NetInstanceFrame
/*     */   implements MessageFrame
/*     */ {
/*  45 */   protected static final Logger m_logger = Logger.getLogger(NetInstanceFrame.class);
/*     */   
/*  47 */   private static NetInstanceFrame m_instance = new NetInstanceFrame();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NetInstanceFrame getInstance() {
/*  54 */     return m_instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onMessage(Message message) {
/*     */     EnterInstanceMessage msg;
/*     */     MusicGroup musics;
/*  63 */     switch (message.getId()) {
/*     */       
/*     */       case 4600:
/*  66 */         msg = (EnterInstanceMessage)message;
/*     */ 
/*     */         
/*  69 */         musics = (MusicGroup)SoundManager.getInstance().getGroupByName("musics");
/*  70 */         if (musics != null) {
/*  71 */           musics.playRandomMusic(true);
/*     */         }
/*     */ 
/*     */         
/*  75 */         DofusArenaClientInstance.getInstance().getWorldScene().clean(false);
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/*  80 */           String mapsPath = DofusArenaConfiguration.getInstance().getString("mapsPath");
/*  81 */           mapsPath = String.format(mapsPath, new Object[] { Short.valueOf(msg.getInstanceID()) });
/*  82 */           WorldManager.getInstance().getDocumentAccessor().setBasePath(mapsPath);
/*  83 */         } catch (PropertyException e) {
/*  84 */           m_logger.error("Impossible de déterminer le chemin des fichiers de map (configuration incorreste) !");
/*  85 */           return false;
/*     */         } 
/*     */         
/*  88 */         ShortcutManager.getInstance().enableGroup("common", true);
/*     */         
/*  90 */         if (msg.isDynamic()) {
/*     */ 
/*     */ 
/*     */           
/*  94 */           DefaultIsoWorldTarget defaultIsoWorldTarget = new DefaultIsoWorldTarget(msg.getWorldX(), msg.getWorldY(), msg.getAltitude());
/*  95 */           DofusArenaClientInstance.getInstance().getWorldScene().setCameraTarget((IsoWorldTarget)defaultIsoWorldTarget);
/*  96 */           DofusArenaClientInstance.getInstance().getWorldScene().alignCameraOnTrackingTarget();
/*     */ 
/*     */           
/*  99 */           DofusArenaGameEntity.getInstance().removeFrame((MessageFrame)UIMenuBarFrame.getInstance());
/* 100 */           DofusArenaGameEntity.getInstance().removeFrame((MessageFrame)UIIslandWorldSceneInteractionFrame.getInstance());
/* 101 */           DofusArenaGameEntity.getInstance().removeFrame(NetActorsFrame.getInstance());
/* 102 */           DofusArenaGameEntity.getInstance().removeFrame(NetFightInvitationFrame.getInstance());
/* 103 */           DofusArenaGameEntity.getInstance().removeFrame((MessageFrame)UITeamManagementFrame.getInstance());
/* 104 */           DofusArenaGameEntity.getInstance().removeFrame((MessageFrame)UIFightTeamManagementFrame.getInstance());
/* 105 */           DofusArenaGameEntity.getInstance().removeFrame(NetTeamManagementFrame.getInstance());
/* 106 */           DofusArenaGameEntity.getInstance().removeFrame(NetExchangeInvitationFrame.getInstance());
/* 107 */           DofusArenaGameEntity.getInstance().removeFrame((MessageFrame)UICoachInventoryManagementFrame.getInstance());
/*     */ 
/*     */           
/* 110 */           ShortcutManager.getInstance().enableGroup("world", false);
/* 111 */           ShortcutManager.getInstance().enableGroup("fight", true);
/*     */ 
/*     */           
/* 114 */           DofusArenaClientInstance.getInstance().getWorldScene().setDesiredZoomFactor(0.8999999761581421D);
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */           
/* 120 */           LinkedList<ActionGroup> groups = QueueActionGroupManager.getInstance().getExecutingActionGroups();
/* 121 */           if (groups != null && !groups.isEmpty()) {
/* 122 */             ActionGroup actionGroup = QueueActionGroupManager.getInstance().getExecutingActionGroups().getFirst();
/* 123 */             if (actionGroup != null) {
/* 124 */               Action fightEndAction = actionGroup.getActionByType(FightActionType.FIGHT_END.getId());
/*     */               
/* 126 */               actionGroup.kill();
/*     */               
/* 128 */               if (fightEndAction != null) {
/* 129 */                 fightEndAction.run();
/*     */               }
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 135 */           LocalCoach localCoach = DofusArenaGameEntity.getInstance().getLocalCoach();
/* 136 */           if (localCoach == null) {
/* 137 */             m_logger.error("Impossible d'entrer dans le monde si aucun localCoach n'est défini !");
/* 138 */             return false;
/*     */           } 
/* 140 */           localCoach.setWorldPosition(msg.getWorldX(), msg.getWorldY(), msg.getAltitude());
/* 141 */           localCoach.forceReloadAnimation();
/*     */ 
/*     */           
/* 144 */           NetActorsFrame.addMobile((Mobile)localCoach);
/*     */ 
/*     */           
/* 147 */           ShortcutManager.getInstance().enableGroup("world", true);
/* 148 */           ShortcutManager.getInstance().enableGroup("fight", false);
/*     */ 
/*     */ 
/*     */           
/* 152 */           DofusArenaClientInstance.getInstance().getWorldScene().setCameraTarget((IsoWorldTarget)localCoach);
/* 153 */           DofusArenaClientInstance.getInstance().getWorldScene().alignCameraOnTrackingTarget();
/*     */ 
/*     */           
/* 156 */           DofusArenaGameEntity.getInstance().pushFrame((MessageFrame)UIMenuBarFrame.getInstance());
/* 157 */           DofusArenaGameEntity.getInstance().pushFrame((MessageFrame)UIIslandWorldSceneInteractionFrame.getInstance());
/* 158 */           DofusArenaGameEntity.getInstance().pushFrame(NetActorsFrame.getInstance());
/* 159 */           DofusArenaGameEntity.getInstance().pushFrame(NetFightInvitationFrame.getInstance());
/* 160 */           DofusArenaGameEntity.getInstance().pushFrame((MessageFrame)UIChatFrame.getInstance());
/* 161 */           DofusArenaGameEntity.getInstance().pushFrame(NetExchangeInvitationFrame.getInstance());
/*     */ 
/*     */           
/* 164 */           DofusArenaGameEntity.getInstance().removeFrame(NetCoachFrame.getInstance());
/*     */ 
/*     */           
/* 167 */           DofusArenaClientInstance.getInstance().getWorldScene().setDesiredZoomFactor(1.0D);
/*     */         } 
/*     */         
/* 170 */         DofusArenaClientInstance.getInstance().getWorldScene().setForceUpdateDisplayCell(true);
/*     */         
/* 172 */         return false;
/*     */     } 
/*     */ 
/*     */     
/* 176 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getId() {
/* 185 */     return 0L;
/*     */   }
/*     */   
/*     */   public void setId(long id) {}
/*     */   
/*     */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded) {}
/*     */   
/*     */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved) {}
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\frame\NetInstanceFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */