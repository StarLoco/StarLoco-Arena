/*     */ package com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient;
/*     */ 
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.InputOnlyProxyMessage;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.TeamMate;
/*     */ import com.ankamagames.dofusarena.client.core.game.coach.Coach;
/*     */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*     */ import com.ankamagames.dofusarena.common.game.fight.FightDefinition;
/*     */ import com.ankamagames.dofusarena.common.game.fight.FightDefinitionManager;
/*     */ import com.ankamagames.dofusarena.common.game.fight.NamedFightingTeam;
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
/*     */ public class FightInvitationMessage
/*     */   extends InputOnlyProxyMessage
/*     */ {
/*     */   private long m_invitationId;
/*     */   private boolean m_inviter;
/*     */   private int m_bet;
/*     */   private FightDefinition m_fightDefinition;
/*  30 */   private final ArrayList<NamedFightingTeam> m_opponentTeams = new ArrayList<NamedFightingTeam>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean decode(byte[] rawDatas) {
/*  39 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/*     */     
/*  41 */     this.m_invitationId = buffer.getLong();
/*  42 */     this.m_inviter = (buffer.get() == 1);
/*  43 */     byte fightTypeId = buffer.get();
/*  44 */     this.m_bet = buffer.getInt();
/*     */     
/*  46 */     this.m_fightDefinition = FightDefinitionManager.getInstance().getDefinitionFromFightTypeId(fightTypeId);
/*     */     
/*  48 */     int opponentCount = buffer.get();
/*  49 */     for (int i = 0; i < opponentCount; i++) {
/*  50 */       NamedFightingTeam<Fighter> team = new NamedFightingTeam();
/*  51 */       team.setId(buffer.get());
/*  52 */       long leaderId = buffer.getLong();
/*  53 */       team.setLeader(leaderId);
/*  54 */       int count = buffer.get();
/*     */       
/*  56 */       for (int j = 0; j < count; j++) {
/*  57 */         Coach teammate = new Coach();
/*  58 */         teammate.setId(buffer.getLong());
/*  59 */         byte[] name = new byte[buffer.get()];
/*  60 */         buffer.get(name);
/*  61 */         teammate.setName(new String(name));
/*  62 */         team.addTeamMate((TeamMate)teammate);
/*     */       } 
/*     */ 
/*     */       
/*  66 */       this.m_opponentTeams.add(team);
/*     */     } 
/*  68 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getId() {
/*  78 */     return 4300;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getInvitationId() {
/*  85 */     return this.m_invitationId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FightDefinition getFightDefinition() {
/*  92 */     return this.m_fightDefinition;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterable<NamedFightingTeam> getOpponentTeams() {
/*  99 */     return this.m_opponentTeams;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInviter() {
/* 106 */     return this.m_inviter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBet() {
/* 113 */     return this.m_bet;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\FightInvitationMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */