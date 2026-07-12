/*     */ package com.ankamagames.dofusarena.client.core.game.fight;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.TeamMate;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*     */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.frame.UIFightInvitationFrame;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.fight.UIFightInvitationAcceptRequestMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.fight.UIFightInvitationRejectRequestMessage;
/*     */ import com.ankamagames.dofusarena.common.game.fight.FightDefinition;
/*     */ import com.ankamagames.dofusarena.common.game.fight.NamedFightingTeam;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Worker;
/*     */ import com.ankamagames.framework.kernel.events.MessageFrame;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.core.messagebox.IMessageBoxEventListener;
/*     */ import com.ankamagames.xulor.core.messagebox.MessageBoxControler;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
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
/*     */ public class FightInvitationManager
/*     */ {
/*  32 */   private static FightInvitationManager m_instance = new FightInvitationManager();
/*     */   
/*  34 */   private final HashMap<Long, FightInvitation> m_invitations = new HashMap<Long, FightInvitation>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FightInvitationManager getInstance() {
/*  40 */     return m_instance;
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
/*     */ 
/*     */   
/*     */   public void addInvitation(final long invitationId, Iterable<NamedFightingTeam> opponentTeams, FightDefinition fightDefinition, boolean localInviter, int bet) {
/*  54 */     FightInvitation invitation = new FightInvitation(invitationId, opponentTeams, fightDefinition);
/*     */ 
/*     */     
/*  57 */     this.m_invitations.put(Long.valueOf(invitationId), invitation);
/*     */ 
/*     */     
/*  60 */     DofusArenaGameEntity.getInstance().pushFrame((MessageFrame)UIFightInvitationFrame.getInstance());
/*     */ 
/*     */     
/*  63 */     int messageBoxOptions = localInviter ? 4 : 24;
/*     */ 
/*     */     
/*  66 */     ArrayList<String> opponents = new ArrayList<String>();
/*  67 */     for (NamedFightingTeam<Fighter> team : opponentTeams) {
/*  68 */       String teamMessage = "";
/*  69 */       for (TeamMate<Fighter> teamMate : (Iterable<TeamMate<Fighter>>)team.getTeamMates()) {
/*  70 */         teamMessage = String.valueOf(teamMessage) + teamMate.getName();
/*     */       }
/*  72 */       opponents.add(teamMessage);
/*     */     } 
/*     */     
/*  75 */     String message = "";
/*  76 */     if (localInviter) {
/*  77 */       message = DofusArenaTranslator.getInstance().getString("fightInvitation.messageOut", opponents.toArray());
/*     */     } else {
/*  79 */       message = DofusArenaTranslator.getInstance().getString("fightInvitation.messageIn", opponents.toArray());
/*  80 */       if (bet != 0) {
/*  81 */         message = String.valueOf(message) + "( " + DofusArenaTranslator.getInstance().getString("fightInvitation.withBet", new Object[0]) + ")";
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  86 */     MessageBoxControler messageBoxControler = Xulor.getInstance().msgBox(message, messageBoxOptions | 0x80);
/*  87 */     invitation.setMessageBoxControler(messageBoxControler);
/*  88 */     messageBoxControler.addEventListener(new IMessageBoxEventListener() {
/*     */           public void messageBoxClosed(int type) {
/*  90 */             if (type == 8) {
/*     */ 
/*     */               
/*  93 */               UIFightInvitationAcceptRequestMessage message = UIFightInvitationAcceptRequestMessage.checkOut();
/*  94 */               message.setInvitationId(invitationId);
/*  95 */               Worker.getInstance().pushMessage((Message)message);
/*     */             
/*     */             }
/*     */             else {
/*     */               
/* 100 */               UIFightInvitationRejectRequestMessage message = UIFightInvitationRejectRequestMessage.checkOut();
/* 101 */               message.setInvitationId(invitationId);
/* 102 */               Worker.getInstance().pushMessage((Message)message);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeInvitation(long invitationId) {
/* 116 */     FightInvitation invitation = this.m_invitations.get(Long.valueOf(invitationId));
/* 117 */     closeInvitation(invitation);
/* 118 */     this.m_invitations.remove(Long.valueOf(invitationId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FightInvitation getInvitation(long invitationId) {
/* 126 */     return this.m_invitations.get(Long.valueOf(invitationId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeInvitation(FightInvitation invitation) {
/* 135 */     if (invitation != null)
/*     */     {
/* 137 */       Xulor.getInstance().unload(invitation.getMessageBoxControler().getMessageBoxId());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 145 */     return this.m_invitations.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 152 */     for (FightInvitation invitation : this.m_invitations.values()) {
/* 153 */       closeInvitation(invitation);
/*     */     }
/* 155 */     this.m_invitations.clear();
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\fight\FightInvitationManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */