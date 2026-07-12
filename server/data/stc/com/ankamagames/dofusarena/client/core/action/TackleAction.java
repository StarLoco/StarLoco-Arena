/*    */ package com.ankamagames.dofusarena.client.core.action;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*    */ import com.ankamagames.dofusarena.client.core.game.actor.FighterActor;
/*    */ import com.ankamagames.dofusarena.client.core.game.fight.Fight;
/*    */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*    */ import com.ankamagames.framework.script.action.TimedAction;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TackleAction
/*    */   extends TimedAction
/*    */ {
/* 19 */   private static FightLogger m_fightLogger = new FightLogger();
/*    */   
/* 21 */   private static String TACKLE_ANIMATION = "AnimTacle";
/* 22 */   private static long TACKLE_ANIMATION_DURATION = 1000L;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public TackleAction(int uniqueId, int actionType, int actionId)
/*    */   {
/* 30 */     super(uniqueId, actionType, actionId);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public long onRun()
/*    */   {
/* 41 */     Fighter fighter = (Fighter)DofusArenaGameEntity.getInstance().getFight().getFighterById(getTargetId());
/*    */     
/* 43 */     if (fighter != null) {
/* 44 */       m_fightLogger.info(DofusArenaTranslator.getInstance().getString("fight.tackled", new Object[] { fighter.getName() }));
/*    */       
/* 46 */       fighter.getActor().setAnimation(TACKLE_ANIMATION);
/* 47 */       return TACKLE_ANIMATION_DURATION;
/*    */     }
/*    */     
/* 50 */     return 0L;
/*    */   }
/*    */   
/*    */   protected void onActionFinished() {}
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\action\TackleAction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */