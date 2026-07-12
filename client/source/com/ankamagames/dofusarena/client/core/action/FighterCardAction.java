/*    */ package com.ankamagames.dofusarena.client.core.action;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*    */ import com.ankamagames.dofusarena.client.core.game.card.fighter.FighterCard;
/*    */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FighterCardAction
/*    */   extends AbstractFightCastAction
/*    */ {
/* 20 */   protected static Logger m_logger = Logger.getLogger(SpellAction.class);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private FighterCard m_fightCard;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FighterCardAction(int uniqueId, int actionType, int actionId, FighterCard fighterCard, boolean criticalHit, boolean criticalMiss, long casterId, int x, int y, short z) {
/* 31 */     super(uniqueId, actionType, actionId, criticalHit, criticalMiss, casterId, x, y, z);
/*    */     
/* 33 */     this.m_fightCard = fighterCard;
/* 34 */     setScriptFileId(this.m_fightCard.getScriptId());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/* 44 */     Fighter fighter = (Fighter)DofusArenaGameEntity.getInstance().getFight().getFighterById(getInstigatorId());
/*    */     
/* 46 */     if (fighter != null)
/*    */     {
/* 48 */       m_fightLogger.info(DofusArenaTranslator.getInstance().getString("fight.cardUse", new Object[] { fighter.getName(), this.m_fightCard.getName() }));
/*    */     }
/*    */     
/* 51 */     super.run();
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\action\FighterCardAction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */