/*    */ package com.ankamagames.dofusarena.client.core.action;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*    */ import com.ankamagames.dofusarena.client.core.game.fight.Fight;
/*    */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*    */ import com.ankamagames.dofusarena.client.core.game.spell.Spell;
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
/*    */ public class SpellAction
/*    */   extends AbstractFightCastAction
/*    */ {
/* 21 */   protected static Logger m_logger = Logger.getLogger(SpellAction.class);
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private final Spell m_spell;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   private final boolean m_display;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public SpellAction(int uniqueId, int actionType, int actionId, Spell spell, boolean criticalHit, boolean criticalMiss, long casterId, int x, int y, short z, boolean display)
/*    */   {
/* 40 */     super(uniqueId, actionType, actionId, criticalHit, criticalMiss, casterId, x, y, z);
/*    */     
/* 42 */     this.m_spell = spell;
/* 43 */     this.m_display = display;
/*    */     
/* 45 */     setScriptFileId(this.m_spell.getScriptId());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void run()
/*    */   {
/* 57 */     Fighter fighter = (Fighter)DofusArenaGameEntity.getInstance().getFight().getFighterById(getInstigatorId());
/*    */     
/* 59 */     if (fighter != null) {
/* 60 */       m_fightLogger.info(DofusArenaTranslator.getInstance().getString("fight.spellCast", new Object[] { fighter.getName(), this.m_spell.getName() }));
/*    */     }
/*    */     
/* 63 */     if (this.m_display) {
/* 64 */       super.run();
/*    */     } else {
/* 66 */       fireActionFinishedEvent();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\action\SpellAction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */