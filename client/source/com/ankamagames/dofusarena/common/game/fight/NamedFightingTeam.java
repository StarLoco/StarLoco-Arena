/*    */ package com.ankamagames.dofusarena.common.game.fight;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.FightingTeam;
/*    */ import com.ankamagames.dofusarena.common.game.fighter.AbstractFighter;
/*    */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*    */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*    */ import org.apache.commons.pool.ObjectPool;
/*    */ import org.apache.commons.pool.PoolableObjectFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NamedFightingTeam<F extends AbstractFighter>
/*    */   extends FightingTeam<F>
/*    */ {
/* 19 */   private static final ObjectPool m_staticPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<NamedFightingTeam>() {
/*    */         public NamedFightingTeam makeObject() {
/* 21 */           return new NamedFightingTeam<AbstractFighter>();
/*    */         }
/*    */       });
/*    */ 
/*    */   
/*    */   public static NamedFightingTeam checkOut() {
/*    */     NamedFightingTeam<AbstractFighter> team;
/*    */     try {
/* 29 */       team = (NamedFightingTeam)m_staticPool.borrowObject();
/* 30 */       team.m_pool = m_staticPool;
/*    */     }
/* 32 */     catch (Exception e) {
/* 33 */       team = new NamedFightingTeam<AbstractFighter>();
/* 34 */       team.m_pool = null;
/* 35 */       m_logger.error("Erreur lors d'un checkOut sur une FightingTeam : " + e.getMessage());
/*    */     } 
/*    */     
/* 38 */     return team;
/*    */   }
/*    */   private String m_name;
/*    */   
/*    */   public void onCheckOut() {
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
/*    */   
/*    */   public String getName() {
/* 56 */     return this.m_name;
/*    */   }
/*    */   
/*    */   public void addPartOfName(String partOfName) {
/* 60 */     this.m_name = String.valueOf(this.m_name) + partOfName;
/*    */   }
/*    */   
/*    */   public void setName(String name) {
/* 64 */     this.m_name = name;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\fight\NamedFightingTeam.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */