/*    */ package com.ankamagames.dofusarena.client.core.action;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*    */ import com.ankamagames.dofusarena.client.core.game.fight.Fight;
/*    */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CloseCombatAction
/*    */   extends AbstractFightCastAction
/*    */ {
/* 18 */   private static int DEFAULT_CLOSE_COMBAT_SCRIPT_ID = 8000;
/*    */   
/* 20 */   protected static Logger m_logger = Logger.getLogger(SpellAction.class);
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public CloseCombatAction(int uniqueId, int actionType, int actionId, boolean criticalHit, boolean criticalMiss, long casterId, int x, int y, short z)
/*    */   {
/* 29 */     super(uniqueId, actionType, actionId, criticalHit, criticalMiss, casterId, x, y, z);
/*    */     
/* 31 */     setScriptFileId(DEFAULT_CLOSE_COMBAT_SCRIPT_ID);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void run()
/*    */   {
/* 41 */     Fighter fighter = (Fighter)DofusArenaGameEntity.getInstance().getFight().getFighterById(getInstigatorId());
/*    */     
/* 43 */     if (fighter != null) {
/* 44 */       m_fightLogger.info(DofusArenaTranslator.getInstance().getString("fight.closeCombat", new Object[] { fighter.getName() }));
/*    */     }
/* 46 */     super.run();
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\action\CloseCombatAction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */