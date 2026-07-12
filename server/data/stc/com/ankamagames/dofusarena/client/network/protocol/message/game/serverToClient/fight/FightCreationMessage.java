/*     */ package com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.fight;
/*     */ 
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.InputOnlyProxyMessage;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effectArea.BasicEffectArea;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effectArea.EffectAreaManager;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.statistics.StatisticsReportManager;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.BetCoachCard;
/*     */ import com.ankamagames.dofusarena.client.core.game.coach.Coach;
/*     */ import com.ankamagames.dofusarena.client.core.game.fight.Fight;
/*     */ import com.ankamagames.dofusarena.client.core.game.fight.Timeline;
/*     */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*     */ import com.ankamagames.dofusarena.common.game.effectArea.AbstractEffectArea;
/*     */ import com.ankamagames.dofusarena.common.game.effectArea.StaticEffectAreaManager;
/*     */ import com.ankamagames.dofusarena.common.game.event.AbstractEvent;
/*     */ import com.ankamagames.dofusarena.common.game.event.AbstractEventManager;
/*     */ import com.ankamagames.dofusarena.common.game.fight.FightDefinition;
/*     */ import com.ankamagames.dofusarena.common.game.fight.FightDefinitionManager;
/*     */ import com.ankamagames.dofusarena.common.game.fight.NamedFightingTeam;
/*     */ import com.ankamagames.dofusarena.common.game.statistics.PlayerStatisticsReport;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
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
/*     */ public class FightCreationMessage
/*     */   extends InputOnlyProxyMessage
/*     */ {
/*     */   private byte m_errorCode;
/*     */   private Fight m_fight;
/*     */   
/*     */   public boolean decode(byte[] rawDatas)
/*     */   {
/*  47 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/*     */     
/*  49 */     this.m_errorCode = buffer.get();
/*  50 */     if (this.m_errorCode == 0)
/*     */     {
/*  52 */       byte[] serializedCoachCards = new byte[buffer.getShort()];
/*  53 */       buffer.get(serializedCoachCards);
/*     */       
/*  55 */       int fightTypeId = buffer.getInt();
/*  56 */       FightDefinition fightDefinition = FightDefinitionManager.getInstance().getDefinitionFromFightTypeId((byte)fightTypeId);
/*  57 */       if (fightDefinition == null) {
/*  58 */         m_logger.error("Type de fight " + fightTypeId + " inconnu !");
/*  59 */         return false;
/*     */       }
/*  61 */       this.m_fight = new Fight(fightDefinition);
/*  62 */       this.m_fight.setBet(buffer.getInt());
/*     */       
/*  64 */       int teamCount = buffer.get();
/*  65 */       for (int i = 0; i < teamCount; i++)
/*     */       {
/*     */ 
/*  68 */         byte id = buffer.get();
/*  69 */         byte length = buffer.get();
/*  70 */         byte[] name = new byte[length];
/*  71 */         buffer.get(name);
/*     */         
/*  73 */         NamedFightingTeam<Fighter> team = this.m_fight.newTeam();
/*  74 */         team.setId(id);
/*  75 */         team.setName(new String(name));
/*     */         
/*     */ 
/*  78 */         int coachCount = buffer.get();
/*  79 */         for (int j = 0; j < coachCount; j++) {
/*  80 */           Coach coach = new Coach();
/*  81 */           if (coach.unserialize(buffer, 2))
/*     */           {
/*     */ 
/*  84 */             coach.initializeCoachSpellInventory(serializedCoachCards);
/*     */             
/*     */ 
/*  87 */             int fighterCount = buffer.get();
/*  88 */             for (int k = 0; k < fighterCount; k++) {
/*  89 */               Fighter fighter = new Fighter();
/*  90 */               if (fighter.unserialize(buffer)) {
/*  91 */                 coach.addFighter(fighter);
/*     */               } else {
/*  93 */                 m_logger.error("La désérialisation d'un fighter a échouée !");
/*     */               }
/*     */             }
/*     */             
/*     */ 
/*  98 */             team.addTeamMate(coach);
/*     */             
/*     */ 
/* 101 */             int reportSize = buffer.getShort() & 0xFFFF;
/* 102 */             if (reportSize > 0) {
/* 103 */               byte[] serializedReport = new byte[reportSize];
/* 104 */               buffer.get(serializedReport);
/* 105 */               PlayerStatisticsReport report = (PlayerStatisticsReport)StatisticsReportManager.getInstance().createReport(serializedReport);
/* 106 */               if (report != null)
/*     */               {
/*     */ 
/* 109 */                 coach.setStatisticsReport(report);
/*     */               }
/*     */             }
/*     */             
/*     */ 
/* 114 */             int cardsCount = buffer.get();
/* 115 */             for (int k = 0; k < cardsCount; k++) {
/* 116 */               int referenceCardId = buffer.getInt();
/* 117 */               coach.addBetCoachCard(new BetCoachCard(referenceCardId));
/*     */             }
/*     */           }
/*     */           else {
/* 121 */             m_logger.error("La désérialisation d'un coach a échouée !");
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 126 */         this.m_fight.addTeam(team);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 131 */       Timeline timeline = new Timeline(this.m_fight);
/* 132 */       this.m_fight.setTimeline(timeline);
/*     */       
/* 134 */       int timelineCount = buffer.get();
/* 135 */       for (int j = 0; j < timelineCount; j++) {
/* 136 */         long fighterId = buffer.getLong();
/* 137 */         this.m_fight.addFighterToTimeline(fighterId, false, false);
/*     */       }
/*     */       
/*     */ 
/* 141 */       ArrayList<AbstractEvent> events = new ArrayList();
/* 142 */       int eventCount = buffer.get();
/* 143 */       for (int k = 0; k < eventCount; k++) {
/* 144 */         int eventId = buffer.getInt();
/* 145 */         events.add(AbstractEventManager.getInstance().getAbstractEventFromId(eventId));
/*     */       }
/* 147 */       this.m_fight.setEvents(events);
/*     */       
/*     */ 
/* 150 */       int specialCellsCount = buffer.get();
/* 151 */       for (int l = 0; l < specialCellsCount; l++) {
/* 152 */         long cellBaseId = buffer.getLong();
/* 153 */         long cellId = buffer.getLong();
/* 154 */         int x = buffer.getInt();
/* 155 */         int y = buffer.getInt();
/* 156 */         short z = buffer.getShort();
/* 157 */         AbstractEffectArea cell = StaticEffectAreaManager.getInstance().getSpecialCell(cellBaseId);
/* 158 */         if (cell != null) {
/* 159 */           BasicEffectArea area = cell.instanceAnother(cellId, x, y, z, this.m_fight.getContext(), null);
/* 160 */           this.m_fight.getEffectAreaManager().addEffectArea(area);
/*     */         } else {
/* 162 */           m_logger.error("Impossible de trouver la cellule spéciale " + cellBaseId);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 168 */       this.m_fight.onFightCreatedAndInitialized();
/*     */     }
/*     */     
/*     */ 
/* 172 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getId()
/*     */   {
/* 182 */     return 8000;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public byte getErrorCode()
/*     */   {
/* 189 */     return this.m_errorCode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Fight getFight()
/*     */   {
/* 196 */     return this.m_fight;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\fight\FightCreationMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */