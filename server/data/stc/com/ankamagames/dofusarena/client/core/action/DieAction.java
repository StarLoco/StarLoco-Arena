/*    */ package com.ankamagames.dofusarena.client.core.action;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*    */ import com.ankamagames.dofusarena.client.core.game.actor.FighterActor;
/*    */ import com.ankamagames.dofusarena.client.core.game.fight.Fight;
/*    */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*    */ import com.ankamagames.framework.script.action.TimedAction;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DieAction
/*    */   extends TimedAction
/*    */ {
/* 18 */   private static FightLogger m_fightLogger = new FightLogger();
/*    */   
/* 20 */   private static String DIE_ANIMATION = "AnimMort";
/* 21 */   private static long DIE_ANIMATION_DURATION = 1500L;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public DieAction(int uniqueId, int actionType, int actionId)
/*    */   {
/* 30 */     super(uniqueId, actionType, actionId);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public long onRun()
/*    */   {
/* 40 */     Fighter fighter = (Fighter)DofusArenaGameEntity.getInstance().getFight().getFighterById(getTargetId());
/*    */     
/* 42 */     if (fighter != null) {
/* 43 */       m_fightLogger.info(DofusArenaTranslator.getInstance().getString("fight.die", new Object[] { fighter.getName() }));
/*    */       
/* 45 */       fighter.getActor().hideTeamParticleSystem();
/* 46 */       fighter.getActor().hideActiveParticleSystem();
/* 47 */       fighter.getActor().hideRootParticleSystem();
/* 48 */       fighter.uncarry();
/*    */       
/*    */ 
/* 51 */       fighter.getActor().setAnimation(DIE_ANIMATION);
/*    */       
/* 53 */       return DIE_ANIMATION_DURATION;
/*    */     }
/*    */     
/* 56 */     return 0L;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected void onActionFinished()
/*    */   {
/* 66 */     m_logger.info("onActionFinished DieAction");
/* 67 */     Fighter fighter = (Fighter)DofusArenaGameEntity.getInstance().getFight().getFighterById(getTargetId());
/* 68 */     if (fighter != null) {
/* 69 */       DofusArenaGameEntity.getInstance().getFight().killFighter(fighter);
/*    */     } else {
/* 71 */       m_logger.error("on demande de tuer un personnage qui n'est pas (encore ?) dans le combat");
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\action\DieAction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */