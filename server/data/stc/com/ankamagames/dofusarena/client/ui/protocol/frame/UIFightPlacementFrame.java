/*     */ package com.ankamagames.dofusarena.client.ui.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.NetworkEntity;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.FightingTeam;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.TeamMate;
/*     */ import com.ankamagames.baseImpl.graphics.alea.display.AleaWorldScene;
/*     */ import com.ankamagames.baseImpl.graphics.alea.display.DisplayedElement;
/*     */ import com.ankamagames.baseImpl.graphics.alea.display.DisplayedElementComparator;
/*     */ import com.ankamagames.baseImpl.graphics.alea.mobile.Mobile;
/*     */ import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
/*     */ import com.ankamagames.dofusarena.client.alea.highlightingCells.StartPointManager;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.game.actor.FighterActor;
/*     */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.fight.MoveToFreePlacementRequestMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.fight.TeamMateSetReadyForObservationRequestMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.Dialogs;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.worldScene.UIWorldSceneMouseReleasedMessage;
/*     */ import com.ankamagames.framework.kernel.FrameHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*     */ import com.ankamagames.framework.kernel.events.MessageFrame;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import java.util.ArrayList;
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
/*     */ public class UIFightPlacementFrame
/*     */   implements MessageFrame
/*     */ {
/*  40 */   private static UIFightPlacementFrame m_instance = new UIFightPlacementFrame();
/*     */   
/*  42 */   private Fighter m_selectedFighter = null;
/*     */   
/*     */ 
/*     */ 
/*     */   public static UIFightPlacementFrame getInstance()
/*     */   {
/*  48 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onMessage(Message message)
/*     */   {
/*  57 */     switch (message.getId())
/*     */     {
/*     */ 
/*     */ 
/*     */     case 18010: 
/*  62 */       TeamMateSetReadyForObservationRequestMessage netMessage = new TeamMateSetReadyForObservationRequestMessage();
/*  63 */       DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage(netMessage);
/*     */       
/*  65 */       return false;
/*     */     
/*     */ 
/*     */     case 30000: 
/*  69 */       UIWorldSceneMouseReleasedMessage msg = (UIWorldSceneMouseReleasedMessage)message;
/*     */       
/*  71 */       if (msg.getMouseButton() == 1)
/*     */       {
/*  73 */         AleaWorldScene worldScene = DofusArenaClientInstance.getInstance().getWorldScene();
/*     */         
/*  75 */         if (this.m_selectedFighter == null)
/*     */         {
/*     */ 
/*  78 */           ArrayList<Mobile> hitMobiles = worldScene.getMobilesUnderMousePoint(msg.getMouseX(), msg.getMouseY());
/*  79 */           if (hitMobiles.size() != 0) {
/*  80 */             Mobile mobile = (Mobile)hitMobiles.get(0);
/*  81 */             if ((mobile != null) && ((mobile instanceof FighterActor))) {
/*  82 */               FighterActor fighterActor = (FighterActor)mobile;
/*  83 */               this.m_selectedFighter = fighterActor.getFighter();
/*  84 */               fighterActor.showActiveParticleSystem();
/*  85 */               fighterActor.highlight();
/*  86 */               return false;
/*     */             }
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*  92 */         if (this.m_selectedFighter != null)
/*     */         {
/*     */ 
/*  95 */           ArrayList<DisplayedElement> hitElements = worldScene
/*  96 */             .getDisplayedElementsUnderMousePoint(msg.getMouseX(), msg.getMouseY(), DisplayedElementComparator.MOUSE_DISTANCE_COMPARATOR);
/*     */           
/*  98 */           boolean changePlace = false;
/*  99 */           Point3 target = null;
/* 100 */           int index = 0;
/*     */           
/*     */ 
/* 103 */           while ((!changePlace) && (index < hitElements.size())) {
/* 104 */             DisplayedElement displayedElement = (DisplayedElement)hitElements.get(index);
/*     */             
/* 106 */             byte teamId = this.m_selectedFighter.getTeamMate().getTeam().getId();
/* 107 */             target = displayedElement.getCoordinates();
/*     */             
/* 109 */             changePlace = StartPointManager.getInstance().containsTarget(teamId, target);
/* 110 */             index++;
/*     */           }
/*     */           
/*     */ 
/* 114 */           if (!changePlace) {
/* 115 */             ArrayList<Mobile> hitMobiles = worldScene.getMobilesUnderMousePoint(msg.getMouseX(), msg.getMouseY());
/* 116 */             if (hitMobiles.size() != 0) {
/* 117 */               Mobile mobile = (Mobile)hitMobiles.get(0);
/* 118 */               target = mobile.getWorldCoordinates();
/* 119 */               changePlace = true;
/*     */             }
/*     */           }
/*     */           
/* 123 */           if (changePlace)
/*     */           {
/*     */ 
/* 126 */             MoveToFreePlacementRequestMessage netMessage = new MoveToFreePlacementRequestMessage();
/* 127 */             netMessage.setFighterId(this.m_selectedFighter.getId());
/* 128 */             netMessage.setWorldX(target.getX());
/* 129 */             netMessage.setWorldY(target.getY());
/* 130 */             netMessage.setAltitude(target.getZ());
/* 131 */             DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage(netMessage);
/*     */           }
/*     */           
/*     */ 
/* 135 */           this.m_selectedFighter.getActor().hideActiveParticleSystem();
/* 136 */           this.m_selectedFighter.getActor().unhighlight();
/* 137 */           this.m_selectedFighter = null;
/*     */         }
/*     */       }
/* 140 */       return false; }
/*     */     
/* 142 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getId()
/*     */   {
/* 151 */     return 0L;
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
/* 169 */     if (!isAboutToBeAdded)
/*     */     {
/*     */ 
/* 172 */       Xulor.getInstance().load("fightPlacementDialog", Dialogs.getDialogPath("fightPlacementDialog"), 1L, (short)10000);
/* 173 */       Xulor.getInstance().load("fightCountdownDialog", Dialogs.getDialogPath("fightCountdownDialog"), 1L, (short)10000);
/* 174 */       Xulor.getInstance().load("fightEventCardsDialog", Dialogs.getDialogPath("fightEventCardsDialog"), 1L, (short)10000);
/* 175 */       Xulor.getInstance().load("timelineDialog", Dialogs.getDialogPath("timelineDialog"), 1L, (short)10000);
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
/* 187 */     if (!isAboutToBeRemoved)
/*     */     {
/*     */ 
/* 190 */       Xulor.getInstance().unload("fightPlacementDialog");
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\protocol\frame\UIFightPlacementFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */