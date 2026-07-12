/*    */ package com.ankamagames.dofusarena.client.core.game.fight;
/*    */ 
/*    */ import com.ankamagames.dofusarena.common.game.fight.FightDefinition;
/*    */ import com.ankamagames.dofusarena.common.game.fight.NamedFightingTeam;
/*    */ import com.ankamagames.xulor.core.messagebox.MessageBoxControler;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FightInvitation
/*    */ {
/* 18 */   private long m_id = 0L;
/*    */   private Iterable<NamedFightingTeam> m_opponentTeams;
/*    */   private FightDefinition m_fightDefinition;
/* 21 */   private MessageBoxControler m_messageBoxControler = null;
/*    */   
/*    */ 
/*    */ 
/*    */   public FightInvitation(long id, Iterable<NamedFightingTeam> opponentTeams, FightDefinition fightDefinition)
/*    */   {
/* 27 */     this.m_id = id;
/* 28 */     this.m_opponentTeams = opponentTeams;
/* 29 */     this.m_fightDefinition = fightDefinition;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public long getId()
/*    */   {
/* 36 */     return this.m_id;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Iterable<NamedFightingTeam> getOpponents()
/*    */   {
/* 43 */     return this.m_opponentTeams;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public FightDefinition getFightDefinition()
/*    */   {
/* 50 */     return this.m_fightDefinition;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setMessageBoxControler(MessageBoxControler messageBoxControler)
/*    */   {
/* 57 */     this.m_messageBoxControler = messageBoxControler;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public MessageBoxControler getMessageBoxControler()
/*    */   {
/* 64 */     return this.m_messageBoxControler;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\fight\FightInvitation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */