/*     */ package com.ankamagames.dofusarena.client.core.action;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.FightingTeam;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.TeamMate;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.BetCoachCard;
/*     */ import com.ankamagames.dofusarena.client.core.game.coach.Coach;
/*     */ import com.ankamagames.dofusarena.client.core.game.coach.LocalCoach;
/*     */ import com.ankamagames.dofusarena.client.core.game.fight.Fight;
/*     */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.fight.EndFightDoneMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.fight.EndFightMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UIFightResultFrame;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.events.MessageFrame;
/*     */ import com.ankamagames.framework.script.action.Action;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FightEndAction
/*     */   extends Action
/*     */ {
/*     */   private final Fight m_fight;
/*     */   private final ArrayList<EndFightMessage.TeamMateResultInformations> m_winnerTeamMatesResultInformations;
/*     */   private final ArrayList<EndFightMessage.TeamMateResultInformations> m_looserTeamMatesResultInformations;
/*     */   private final ArrayList<BetCoachCard> m_lostCards;
/*     */   private final ArrayList<BetCoachCard> m_wonCards;
/*     */   private final ArrayList<BetCoachCard> m_bonusCards;
/*     */   
/*     */   public FightEndAction(int uniqueId, int actionType, int actionId, Fight fightEnding, ArrayList<EndFightMessage.TeamMateResultInformations> winnerTeamMatesResultinformations, ArrayList<EndFightMessage.TeamMateResultInformations> looserTeamMatesResultInformations, ArrayList<BetCoachCard> lostCards, ArrayList<BetCoachCard> wonCards, ArrayList<BetCoachCard> bonusCards) {
/*  47 */     super(uniqueId, actionType, actionId);
/*  48 */     this.m_fight = fightEnding;
/*  49 */     this.m_winnerTeamMatesResultInformations = winnerTeamMatesResultinformations;
/*  50 */     this.m_looserTeamMatesResultInformations = looserTeamMatesResultInformations;
/*  51 */     this.m_lostCards = lostCards;
/*  52 */     this.m_wonCards = wonCards;
/*  53 */     this.m_bonusCards = bonusCards;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*  64 */     if (this.m_fight != null) {
/*     */       
/*  66 */       LocalCoach localCoach = DofusArenaGameEntity.getInstance().getLocalCoach();
/*     */ 
/*     */       
/*  69 */       Iterable<FightingTeam<Fighter>> teams = this.m_fight.getTeams();
/*     */ 
/*     */       
/*  72 */       ArrayList<EndFightMessage.TeamMateResultInformations> looserTeamMateIds = this.m_looserTeamMatesResultInformations;
/*  73 */       for (EndFightMessage.TeamMateResultInformations teamMateResultInformations : looserTeamMateIds) {
/*  74 */         for (FightingTeam<Fighter> team : teams) {
/*  75 */           TeamMate teamMate = team.getTeamMateById(teamMateResultInformations.getId());
/*  76 */           if (teamMate != null) {
/*  77 */             Coach coach = (Coach)teamMate;
/*  78 */             coach.setLadderStrength((byte)1, (short)teamMateResultInformations.getStrength());
/*  79 */             coach.setStatisticsReport(teamMateResultInformations.getStatisticsReport());
/*  80 */             if (coach.getId() == localCoach.getId()) {
/*  81 */               localCoach.setStatisticsReport(teamMateResultInformations.getStatisticsReport());
/*     */             }
/*  83 */             UIFightResultFrame.getInstance().addLooserCoach(coach);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/*  89 */       ArrayList<EndFightMessage.TeamMateResultInformations> winnerTeamMateIds = this.m_winnerTeamMatesResultInformations;
/*  90 */       for (EndFightMessage.TeamMateResultInformations teamMateResultInformations : winnerTeamMateIds) {
/*  91 */         for (FightingTeam<Fighter> team : teams) {
/*  92 */           TeamMate teamMate = team.getTeamMateById(teamMateResultInformations.getId());
/*  93 */           if (teamMate != null) {
/*  94 */             Coach coach = (Coach)teamMate;
/*  95 */             coach.setLadderStrength((byte)1, (short)teamMateResultInformations.getStrength());
/*  96 */             coach.setStatisticsReport(teamMateResultInformations.getStatisticsReport());
/*  97 */             if (coach.getId() == localCoach.getId()) {
/*  98 */               localCoach.setLadderStrength((byte)1, (short)teamMateResultInformations.getStrength());
/*  99 */               localCoach.setStatisticsReport(teamMateResultInformations.getStatisticsReport());
/*     */             } 
/* 101 */             UIFightResultFrame.getInstance().addWinnerCoach(coach);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 107 */       UIFightResultFrame.getInstance().setLostCards(this.m_lostCards);
/* 108 */       UIFightResultFrame.getInstance().setWonCards(this.m_wonCards);
/* 109 */       UIFightResultFrame.getInstance().setBonusCards(this.m_bonusCards);
/*     */ 
/*     */       
/* 112 */       UIFightResultFrame.getInstance().setFightDuration(this.m_fight.getDuration());
/*     */ 
/*     */       
/* 115 */       DofusArenaGameEntity.getInstance().pushFrame((MessageFrame)UIFightResultFrame.getInstance());
/*     */ 
/*     */       
/* 118 */       this.m_fight.endFight();
/*     */     } else {
/*     */       
/* 121 */       m_logger.error("Erreur dans FIghtEndAction");
/*     */     } 
/*     */     
/* 124 */     fireActionFinishedEvent();
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
/*     */   protected void onActionFinished() {
/* 136 */     EndFightDoneMessage msg = new EndFightDoneMessage();
/* 137 */     DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage((Message)msg);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\action\FightEndAction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */