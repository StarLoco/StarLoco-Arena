/*     */ package com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.fight;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.statistics.StatisticsReportManager;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.BetCoachCard;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.action.FightActionMessage;
/*     */ import com.ankamagames.dofusarena.common.game.fight.FightActionType;
/*     */ import com.ankamagames.dofusarena.common.game.statistics.PlayerStatisticsReport;
/*     */ import java.nio.ByteBuffer;
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
/*     */ public class EndFightMessage
/*     */   extends FightActionMessage
/*     */ {
/*     */   public class TeamMateResultInformations
/*     */   {
/*     */     private final long m_id;
/*     */     private final int m_strength;
/*     */     private final PlayerStatisticsReport m_statisticsReport;
/*     */     
/*     */     public TeamMateResultInformations(long id, int strength, PlayerStatisticsReport statisticsReport)
/*     */     {
/*  38 */       this.m_id = id;
/*  39 */       this.m_strength = strength;
/*  40 */       this.m_statisticsReport = statisticsReport;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public long getId()
/*     */     {
/*  47 */       return this.m_id;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public int getStrength()
/*     */     {
/*  54 */       return this.m_strength;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public PlayerStatisticsReport getStatisticsReport()
/*     */     {
/*  61 */       return this.m_statisticsReport;
/*     */     }
/*     */   }
/*     */   
/*  65 */   private boolean m_flee = false;
/*     */   
/*  67 */   private final ArrayList<TeamMateResultInformations> m_winnerTeamMatesResultInformations = new ArrayList();
/*  68 */   private final ArrayList<TeamMateResultInformations> m_looserTeamMatesResultInformations = new ArrayList();
/*     */   
/*  70 */   private final ArrayList<BetCoachCard> m_lostCards = new ArrayList();
/*  71 */   private final ArrayList<BetCoachCard> m_wonCards = new ArrayList();
/*  72 */   private final ArrayList<BetCoachCard> m_bonusCards = new ArrayList();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean decode(byte[] rawDatas)
/*     */   {
/*  82 */     if (!checkMessageSize(rawDatas.length, 9, false)) {
/*  83 */       return false;
/*     */     }
/*     */     
/*  86 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/*     */     
/*  88 */     decodeFightActionHeader(buffer);
/*     */     
/*  90 */     this.m_flee = (buffer.get() == 1);
/*     */     
/*  92 */     if (this.m_flee)
/*     */     {
/*  94 */       int size = buffer.getShort();
/*  95 */       if (size > 0) {
/*  96 */         byte[] serializedLostCards = new byte[size];
/*  97 */         buffer.get(serializedLostCards);
/*  98 */         unserializeCards(serializedLostCards, true);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 103 */       int count = buffer.get();
/* 104 */       for (int i = 0; i < count; i++) {
/* 105 */         long playerId = buffer.getLong();
/* 106 */         short strength = buffer.getShort();
/*     */         
/* 108 */         short size = buffer.getShort();
/* 109 */         PlayerStatisticsReport report = null;
/* 110 */         if (size > 0) {
/* 111 */           byte[] serial = new byte[size];
/* 112 */           buffer.get(serial);
/* 113 */           report = (PlayerStatisticsReport)StatisticsReportManager.getInstance().createReport(serial);
/*     */         }
/* 115 */         this.m_winnerTeamMatesResultInformations.add(new TeamMateResultInformations(playerId, strength, report));
/*     */       }
/*     */       
/* 118 */       count = buffer.get();
/* 119 */       for (int i = 0; i < count; i++) {
/* 120 */         long playerId = buffer.getLong();
/* 121 */         short strength = buffer.getShort();
/*     */         
/* 123 */         short size = buffer.getShort();
/* 124 */         PlayerStatisticsReport report = null;
/* 125 */         if (size > 0) {
/* 126 */           byte[] serial = new byte[size];
/* 127 */           buffer.get(serial);
/* 128 */           report = (PlayerStatisticsReport)StatisticsReportManager.getInstance().createReport(serial);
/*     */         }
/*     */         
/* 131 */         this.m_looserTeamMatesResultInformations.add(new TeamMateResultInformations(playerId, strength, report));
/*     */       }
/*     */       
/* 134 */       int size = buffer.getShort();
/* 135 */       if (size > 0) {
/* 136 */         byte[] serializedLostCards = new byte[size];
/* 137 */         buffer.get(serializedLostCards);
/* 138 */         unserializeCards(serializedLostCards, false);
/*     */       }
/*     */       
/* 141 */       size = buffer.getShort();
/* 142 */       if (size > 0) {
/* 143 */         byte[] serializedLostCards = new byte[size];
/* 144 */         buffer.get(serializedLostCards);
/* 145 */         unserializeCards(serializedLostCards, true);
/*     */       }
/*     */     }
/*     */     
/* 149 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getId()
/*     */   {
/* 160 */     return 8300;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isFlee()
/*     */   {
/* 167 */     return this.m_flee;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ArrayList<TeamMateResultInformations> getWinnerTeamMatesResultInformations()
/*     */   {
/* 174 */     return this.m_winnerTeamMatesResultInformations;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ArrayList<TeamMateResultInformations> getLooserTeamMatesResultInformations()
/*     */   {
/* 181 */     return this.m_looserTeamMatesResultInformations;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayList<BetCoachCard> getLostCards()
/*     */   {
/* 189 */     return this.m_lostCards;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ArrayList<BetCoachCard> getWonCards()
/*     */   {
/* 196 */     return this.m_wonCards;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ArrayList<BetCoachCard> getBonusCards()
/*     */   {
/* 203 */     return this.m_bonusCards;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getActionId()
/*     */   {
/* 213 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FightActionType getFightActionType()
/*     */   {
/* 223 */     return FightActionType.FIGHT_END;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void unserializeCards(byte[] serializedData, boolean bLostCards)
/*     */   {
/* 234 */     ByteBuffer buffer = ByteBuffer.wrap(serializedData);
/*     */     
/*     */ 
/* 237 */     int entryCount = buffer.get();
/* 238 */     for (int i = 0; i < entryCount; i++)
/*     */     {
/*     */ 
/* 241 */       long playerId = buffer.getLong();
/* 242 */       ArrayList<BetCoachCard> cards; ArrayList<BetCoachCard> cards; if (playerId == -1L)
/*     */       {
/* 244 */         cards = this.m_bonusCards; } else { ArrayList<BetCoachCard> cards;
/* 245 */         if (bLostCards) {
/* 246 */           cards = this.m_lostCards;
/*     */         } else {
/* 248 */           cards = this.m_wonCards;
/*     */         }
/*     */       }
/*     */       
/* 252 */       int count = buffer.get();
/* 253 */       for (int j = 0; j < count; j++) {
/* 254 */         BetCoachCard card = new BetCoachCard(buffer.getInt(), buffer.get() > 0);
/* 255 */         card.setOwnerId(playerId);
/* 256 */         cards.add(card);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\fight\EndFightMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */