/*     */ package com.ankamagames.dofusarena.client.network.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.FightingTeam;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.TeamMate;
/*     */ import com.ankamagames.baseImpl.graphics.alea.adviser.AdviserManager;
/*     */ import com.ankamagames.baseImpl.graphics.alea.mobile.Mobile;
/*     */ import com.ankamagames.baseImpl.graphics.alea.mobile.MobileManager;
/*     */ import com.ankamagames.baseImpl.graphics.alea.mobile.MobileSelectionChangeListener;
/*     */ import com.ankamagames.baseImpl.graphics.alea.mobile.PathMobile;
/*     */ import com.ankamagames.dofusarena.client.alea.adviser.DofusArenaOverHeadInfos;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*     */ import com.ankamagames.dofusarena.client.core.game.actor.Actor;
/*     */ import com.ankamagames.dofusarena.client.core.game.actor.FighterActor;
/*     */ import com.ankamagames.dofusarena.client.core.game.coach.Coach;
/*     */ import com.ankamagames.dofusarena.client.core.game.fight.Fight;
/*     */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.actor.ActorAppearMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.actor.ActorAppearMessage.CharacterAppearInformation;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.actor.ActorDisapearMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.actor.ActorMovementMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.actor.ActorRepositionMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.actor.ActorRepositionMessage.CharacterAppearInformation;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.actor.ActorTeleportsMessage;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.characteristic.FighterCharacteristicType;
/*     */ import com.ankamagames.framework.ai.pathfinder.PathFindResult;
/*     */ import com.ankamagames.framework.kernel.FrameHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.events.MessageFrame;
/*     */ import java.util.Iterator;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NetFightActorsFrame
/*     */   implements MessageFrame
/*     */ {
/*  41 */   protected static final Logger m_logger = Logger.getLogger(NetFightActorsFrame.class);
/*     */   
/*  43 */   private static NetFightActorsFrame m_instance = new NetFightActorsFrame();
/*     */   
/*     */ 
/*     */ 
/*     */   public static NetFightActorsFrame getInstance()
/*     */   {
/*  49 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean onMessage(Message message)
/*     */   {
/*     */     Iterator localIterator1;
/*     */     
/*     */     ActorAppearMessage.CharacterAppearInformation actorInformations;
/*  58 */     switch (message.getId())
/*     */     {
/*     */     case 4102: 
/*  61 */       ActorAppearMessage msg = (ActorAppearMessage)message;
/*     */       
/*  63 */       Fight fight = DofusArenaGameEntity.getInstance().getFight();
/*  64 */       if (fight != null) {
/*  65 */         Iterable<FightingTeam<Fighter>> teams = fight.getTeams();
/*  66 */         Iterable<ActorAppearMessage.CharacterAppearInformation> actorsInformations = msg.getCharacterInformations();
/*  67 */         for (localIterator1 = actorsInformations.iterator(); localIterator1.hasNext();) { actorInformations = (ActorAppearMessage.CharacterAppearInformation)localIterator1.next();
/*  68 */           Actor actor = null;
/*  69 */           Fighter fighter = (Fighter)fight.getFighterById(actorInformations.getId());
/*  70 */           if (fighter != null) {
/*  71 */             fighter.setPosition(actorInformations.getWorldX(), actorInformations.getWorldY(), actorInformations.getAltitude());
/*  72 */             fighter.setDirection(actorInformations.getDirection());
/*  73 */             actor = fighter.getActor();
/*  74 */             fighter.getActor().showTeamParticleSystem();
/*     */           } else {
/*  76 */             for (FightingTeam<Fighter> team : teams) {
/*  77 */               TeamMate<Fighter> teamMate = team.getTeamMateById(actorInformations.getId());
/*  78 */               if (teamMate != null) {
/*  79 */                 Coach coach = (Coach)teamMate;
/*  80 */                 coach.setWorldPosition(actorInformations.getWorldX(), actorInformations.getWorldY(), actorInformations.getAltitude());
/*  81 */                 coach.setDirection(actorInformations.getDirection());
/*  82 */                 actor = coach;
/*  83 */                 break;
/*     */               }
/*     */             }
/*     */           }
/*     */           
/*     */ 
/*  89 */           if (actor != null) {
/*  90 */             addMobile(actor);
/*     */           } else {
/*  92 */             m_logger.error("L'acteur " + actorInformations.getId() + " est inconnu !");
/*     */           }
/*     */         }
/*     */       }
/*     */       else {
/*  97 */         m_logger.error("Aucun fight !");
/*     */       }
/*     */       
/* 100 */       return false;
/*     */     
/*     */ 
/*     */     case 4104: 
/* 104 */       ActorDisapearMessage msg = (ActorDisapearMessage)message;
/*     */       
/* 106 */       Fight fight = DofusArenaGameEntity.getInstance().getFight();
/* 107 */       if (fight != null) {
/* 108 */         Iterable<Long> actorIds = msg.getCharacterIds();
/* 109 */         for (localIterator1 = actorIds.iterator(); localIterator1.hasNext();) { long actorId = ((Long)localIterator1.next()).longValue();
/* 110 */           Fighter fighter = (Fighter)fight.getFighterById(actorId);
/* 111 */           if (fighter != null) {
/* 112 */             Actor actor = fighter.getActor();
/* 113 */             if (actor != null)
/*     */             {
/*     */ 
/* 116 */               MobileManager.getInstance().removeMobile(actor);
/*     */             }
/*     */             else {
/* 119 */               m_logger.error("L'acteur " + actorId + " est inconnu !");
/*     */             }
/*     */           }
/*     */         }
/*     */       } else {
/* 124 */         m_logger.error("Aucun fight !");
/*     */       }
/*     */       
/* 127 */       return false;
/*     */     
/*     */ 
/*     */     case 4106: 
/* 131 */       ActorRepositionMessage msg = (ActorRepositionMessage)message;
/*     */       
/* 133 */       Fight fight = DofusArenaGameEntity.getInstance().getFight();
/* 134 */       if (fight != null) {
/* 135 */         Iterable<ActorRepositionMessage.CharacterAppearInformation> actorsInformations = msg.getCharacterInformations();
/* 136 */         for (ActorRepositionMessage.CharacterAppearInformation actorInformations : actorsInformations) {
/* 137 */           Fighter fighter = (Fighter)fight.getFighterById(actorInformations.getId());
/* 138 */           if (fighter != null)
/*     */           {
/* 140 */             fighter.setPosition(actorInformations.getWorldX(), actorInformations.getWorldY(), actorInformations.getAltitude());
/*     */           } else {
/* 142 */             m_logger.error("L'acteur " + actorInformations.getId() + " est inconnu !");
/*     */           }
/*     */         }
/*     */       } else {
/* 146 */         m_logger.error("Aucun fight !");
/*     */       }
/*     */       
/* 149 */       return false;
/*     */     
/*     */ 
/*     */     case 4500: 
/* 153 */       ActorMovementMessage msg = (ActorMovementMessage)message;
/*     */       
/*     */ 
/* 156 */       PathMobile pathMobile = (PathMobile)MobileManager.getInstance().getMobile(msg.getActorId());
/* 157 */       if (pathMobile != null)
/*     */       {
/*     */ 
/* 160 */         PathFindResult node = msg.getPathResult();
/* 161 */         pathMobile.setPath(node, true);
/*     */       }
/*     */       else {
/* 164 */         m_logger.error("Impossible de déplacer le personnage " + msg.getActorId() + " car il n'existe pas !");
/*     */       }
/*     */       
/* 167 */       return false;
/*     */     
/*     */ 
/*     */     case 4510: 
/* 171 */       ActorTeleportsMessage msg = (ActorTeleportsMessage)message;
/*     */       
/* 173 */       Fight fight = DofusArenaGameEntity.getInstance().getFight();
/* 174 */       if (fight != null) {
/* 175 */         Fighter fighter = (Fighter)fight.getFighterById(msg.getActorId());
/* 176 */         if (fighter != null) {
/* 177 */           fighter.setPosition(msg.getWorldX(), msg.getWorldY(), msg.getAltitude());
/*     */         } else {
/* 179 */           m_logger.error("Impossible de téléporter le personnage " + msg.getActorId() + " car il n'existe pas !");
/*     */         }
/*     */       }
/*     */       
/* 183 */       return false;
/*     */     }
/*     */     
/* 186 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getId()
/*     */   {
/* 195 */     return 0L;
/*     */   }
/*     */   
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
/*     */ 
/*     */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void addMobile(Mobile mobile)
/*     */   {
/* 234 */     MobileManager.getInstance().addMobile(mobile);
/*     */     
/*     */ 
/* 237 */     mobile.setVisible(true);
/*     */     
/*     */ 
/* 240 */     mobile.addSelectionChangedListener(new MobileSelectionChangeListener() {
/*     */       DofusArenaOverHeadInfos m_overHeadInfos;
/*     */       
/* 243 */       public void selectionChanged(Mobile mobile, boolean selected) { if ((selected) && (mobile.isVisible())) {
/* 244 */           String infos = null;
/* 245 */           if ((mobile instanceof Coach)) {
/* 246 */             infos = ((Coach)mobile).getName();
/* 247 */           } else if ((mobile instanceof FighterActor)) {
/* 248 */             Fighter fighter = ((FighterActor)mobile).getFighter();
/* 249 */             String name = fighter.getName();
/* 250 */             int healthPoints = fighter.getCharacteristicValue(FighterCharacteristicType.HP);
/* 251 */             infos = String.format("%s (%d %s)", new Object[] { name, Integer.valueOf(healthPoints), DofusArenaTranslator.getInstance().getString("HP", new Object[0]) });
/*     */           }
/* 253 */           if (infos != null) {
/* 254 */             this.m_overHeadInfos = new DofusArenaOverHeadInfos(infos);
/* 255 */             this.m_overHeadInfos.setTarget(mobile);
/* 256 */             AdviserManager.getInstance().addAdviser(this.m_overHeadInfos);
/*     */           }
/*     */         } else {
/* 259 */           AdviserManager.getInstance().removeAdviser(this.m_overHeadInfos);
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\frame\NetFightActorsFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */