/*     */ package com.ankamagames.dofusarena.client.ui.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.NetworkEntity;
/*     */ import com.ankamagames.baseImpl.graphics.alea.utils.WorldSceneInteractionUtils;
/*     */ import com.ankamagames.baseImpl.graphics.alea.utils.WorldSceneInteractionUtils.PathSolutionCriterions;
/*     */ import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
/*     */ import com.ankamagames.dofusarena.client.alea.highlightingCells.PathDisplaySelection;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.game.actor.FighterActor;
/*     */ import com.ankamagames.dofusarena.client.core.game.fight.Fight;
/*     */ import com.ankamagames.dofusarena.client.core.game.fight.Timeline;
/*     */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.FighterActorMovementRequestMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.worldScene.UIWorldSceneMouseMovedMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.worldScene.UIWorldSceneMouseReleasedMessage;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.characteristic.FighterCharacteristicType;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.characteristic.FighterPropertyType;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.characteristic.FighterPropertymanager;
/*     */ import com.ankamagames.framework.ai.pathfinder.PathFindParameters;
/*     */ import com.ankamagames.framework.ai.pathfinder.PathFindResult;
/*     */ import com.ankamagames.framework.kernel.FrameHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*     */ import com.ankamagames.framework.kernel.events.MessageFrame;
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
/*     */ public class UIFightMoveWorldSceneInteractionFrame
/*     */   implements MessageFrame
/*     */ {
/*  39 */   private static final Logger m_logger = Logger.getLogger(UIFightMoveWorldSceneInteractionFrame.class);
/*     */   
/*  41 */   private static UIFightMoveWorldSceneInteractionFrame m_instance = new UIFightMoveWorldSceneInteractionFrame();
/*     */   
/*  43 */   private PathDisplaySelection m_pathSelection = new PathDisplaySelection();
/*  44 */   private PathFindParameters m_defaultParameters = new PathFindParameters();
/*     */   
/*     */ 
/*     */ 
/*  48 */   private int m_pathLength = 0;
/*     */   
/*     */ 
/*     */ 
/*  52 */   private int m_lastMP = -1;
/*     */   
/*     */ 
/*     */   private int m_mouseX;
/*     */   
/*     */ 
/*     */   private int m_mouseY;
/*     */   
/*  60 */   private Point3 m_lastTarget = null;
/*     */   
/*     */ 
/*     */ 
/*     */   public UIFightMoveWorldSceneInteractionFrame()
/*     */   {
/*  66 */     this.m_defaultParameters.m_searchLimit = 1000;
/*  67 */     this.m_defaultParameters.m_includeStartCell = false;
/*  68 */     this.m_defaultParameters.m_useDiagonals = false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static UIFightMoveWorldSceneInteractionFrame getInstance()
/*     */   {
/*  75 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onMessage(Message message)
/*     */   {
/*  84 */     switch (message.getId())
/*     */     {
/*     */     case 30001: 
/*  87 */       UIWorldSceneMouseMovedMessage msg = (UIWorldSceneMouseMovedMessage)message;
/*     */       
/*  89 */       this.m_mouseX = msg.getMouseX();
/*  90 */       this.m_mouseY = msg.getMouseY();
/*  91 */       refreshPathSelection();
/*     */       
/*  93 */       return false;
/*     */     
/*     */ 
/*     */ 
/*     */     case 30000: 
/*  98 */       UIWorldSceneMouseReleasedMessage msg = (UIWorldSceneMouseReleasedMessage)message;
/*  99 */       this.m_mouseX = msg.getMouseX();
/* 100 */       this.m_mouseY = msg.getMouseY();
/*     */       
/* 102 */       if (msg.getMouseButton() == 1) {
/* 103 */         this.m_pathLength = 0;
/*     */         
/* 105 */         this.m_pathSelection.clear();
/* 106 */         this.m_pathSelection.refreshDisplay(DofusArenaClientInstance.getInstance().getWorldScene());
/*     */         
/* 108 */         if ((this.m_lastTarget != null) && (this.m_lastTarget.equals(getNearestPoint3(this.m_mouseX, this.m_mouseY))))
/*     */         {
/* 110 */           Fight fight = DofusArenaGameEntity.getInstance().getFight();
/* 111 */           Fighter currentFighter = (Fighter)fight.getTimeline().getCurrentFighter();
/* 112 */           if (currentFighter != null) {
/* 113 */             FighterActor actor = currentFighter.getActor();
/* 114 */             if (actor != null)
/*     */             {
/* 116 */               int movementPoints = currentFighter.getCharacteristicValue(FighterCharacteristicType.MP);
/*     */               
/* 118 */               if (movementPoints > 0) {
/* 119 */                 this.m_defaultParameters.m_obstacleInformationProvider = fight;
/* 120 */                 this.m_defaultParameters.m_maxNodes = movementPoints;
/*     */                 
/*     */ 
/* 123 */                 PathFindResult pathResult = WorldSceneInteractionUtils.getPathSolutionFromMouseCoordinates(DofusArenaClientInstance.getInstance().getWorldScene(), actor, msg.getMouseX(), msg.getMouseY(), WorldSceneInteractionUtils.PathSolutionCriterions.NEAREST_UP_FACE, this.m_defaultParameters);
/* 124 */                 if ((pathResult != null) && (pathResult.getPathLength() > 0)) {
/* 125 */                   this.m_pathLength = pathResult.getPathLength();
/*     */                   
/* 127 */                   FighterActorMovementRequestMessage netMessage = new FighterActorMovementRequestMessage();
/* 128 */                   netMessage.setPathResult(pathResult);
/* 129 */                   netMessage.setFighterId(currentFighter.getId());
/* 130 */                   DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage(netMessage);
/*     */                 } else {
/* 132 */                   m_logger.error("Aucun chemin trouvé");
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 140 */       return false;
/*     */     }
/*     */     
/* 143 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getId()
/*     */   {
/* 152 */     return 0L;
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
/* 170 */     clear();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved)
/*     */   {
/* 180 */     clear();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void clearPathSelection()
/*     */   {
/* 187 */     this.m_pathSelection.clear();
/* 188 */     this.m_pathSelection.refreshDisplay(DofusArenaClientInstance.getInstance().getWorldScene());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void clear()
/*     */   {
/* 196 */     this.m_lastTarget = null;
/* 197 */     this.m_pathLength = 0;
/* 198 */     this.m_lastMP = -1;
/* 199 */     clearPathSelection();
/*     */   }
/*     */   
/*     */ 
/*     */   public void setMouse(int mouseX, int mouseY)
/*     */   {
/* 205 */     this.m_lastTarget = null;
/* 206 */     this.m_mouseX = mouseX;
/* 207 */     this.m_mouseY = mouseY;
/*     */   }
/*     */   
/*     */   public int getMouseX() {
/* 211 */     return this.m_mouseX;
/*     */   }
/*     */   
/*     */   public int getMouseY() {
/* 215 */     return this.m_mouseY;
/*     */   }
/*     */   
/*     */ 
/*     */   public void refreshPathSelection()
/*     */   {
/* 221 */     Fight fight = DofusArenaGameEntity.getInstance().getFight();
/* 222 */     Fighter currentFighter = (Fighter)fight.getTimeline().getCurrentFighter();
/*     */     
/* 224 */     if (currentFighter != null) {
/* 225 */       Point3 target = getNearestPoint3(this.m_mouseX, this.m_mouseY);
/* 226 */       if (target == null) {
/* 227 */         this.m_pathSelection.clear();
/* 228 */         this.m_pathSelection.refreshDisplay(DofusArenaClientInstance.getInstance().getWorldScene());
/*     */       }
/* 230 */       else if ((this.m_lastTarget == null) || (!this.m_lastTarget.equals(target))) {
/* 231 */         int movementPoints = currentFighter.getCharacteristicValue(FighterCharacteristicType.MP);
/*     */         
/* 233 */         if (this.m_lastMP != movementPoints) {
/* 234 */           this.m_lastMP = movementPoints;
/* 235 */           this.m_pathLength = 0;
/*     */         }
/*     */         else {
/* 238 */           movementPoints -= this.m_pathLength;
/*     */         }
/* 240 */         if ((movementPoints > 0) && (!currentFighter.getProperties().isActiveProperty(FighterPropertyType.PETRIFIED))) {
/* 241 */           FighterActor actor = currentFighter.getActor();
/* 242 */           if (actor != null) {
/* 243 */             this.m_defaultParameters.m_obstacleInformationProvider = fight;
/* 244 */             this.m_defaultParameters.m_maxNodes = movementPoints;
/*     */             
/*     */ 
/* 247 */             PathFindResult pathResult = WorldSceneInteractionUtils.getPathSolutionFromMouseCoordinates(DofusArenaClientInstance.getInstance().getWorldScene(), actor, this.m_mouseX, this.m_mouseY, WorldSceneInteractionUtils.PathSolutionCriterions.NEAREST_UP_FACE, this.m_defaultParameters);
/*     */             
/* 249 */             if ((pathResult != null) && (pathResult.getPathLength() > 0)) {
/* 250 */               this.m_pathSelection.setPath(pathResult);
/*     */             } else {
/* 252 */               this.m_pathSelection.clear();
/*     */             }
/*     */             
/* 255 */             this.m_lastTarget = target;
/*     */           }
/*     */         } else {
/* 258 */           this.m_pathSelection.clear();
/*     */         }
/* 260 */         this.m_pathSelection.refreshDisplay(DofusArenaClientInstance.getInstance().getWorldScene());
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
/*     */ 
/*     */ 
/*     */   protected Point3 getNearestPoint3(int mouseX, int mouseY)
/*     */   {
/* 275 */     return WorldSceneInteractionUtils.getNearestPoint3(DofusArenaClientInstance.getInstance().getWorldScene(), mouseX, mouseY, false);
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\protocol\frame\UIFightMoveWorldSceneInteractionFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */