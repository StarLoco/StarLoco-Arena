/*    */ package com.ankamagames.dofusarena.common.game.fight;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.FightingTeam;
/*    */ import com.ankamagames.dofusarena.common.game.fighter.AbstractFighter;
/*    */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*    */ import org.apache.commons.pool.ObjectPool;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NamedFightingTeam<F extends AbstractFighter>
/*    */   extends FightingTeam<F>
/*    */ {
/* 19 */   private static final ObjectPool m_staticPool = new MonitoredPool(new ObjectFactory() {
/*    */     public NamedFightingTeam makeObject() {
/* 21 */       return new NamedFightingTeam();
/*    */     }
/* 19 */   });
/*    */   
/*    */   private String m_name;
/*    */   
/*    */ 
/*    */   public static NamedFightingTeam checkOut()
/*    */   {
/*    */     NamedFightingTeam team;
/*    */     try
/*    */     {
/* 29 */       NamedFightingTeam team = (NamedFightingTeam)m_staticPool.borrowObject();
/* 30 */       team.m_pool = m_staticPool;
/*    */     }
/*    */     catch (Exception e) {
/* 33 */       team = new NamedFightingTeam();
/* 34 */       team.m_pool = null;
/* 35 */       m_logger.error("Erreur lors d'un checkOut sur une FightingTeam : " + e.getMessage());
/*    */     }
/*    */     
/* 38 */     return team;
/*    */   }
/*    */   
/*    */   public void onCheckOut()
/*    */   {
/* 43 */     super.onCheckOut();
/* 44 */     this.m_name = "";
/*    */   }
/*    */   
/*    */   public void onCheckIn() {
/* 48 */     super.onCheckIn();
/* 49 */     this.m_name = "";
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getName()
/*    */   {
/* 56 */     return this.m_name;
/*    */   }
/*    */   
/*    */   public void addPartOfName(String partOfName) {
/* 60 */     this.m_name += partOfName;
/*    */   }
/*    */   
/*    */   public void setName(String name) {
/* 64 */     this.m_name = name;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\fight\NamedFightingTeam.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */