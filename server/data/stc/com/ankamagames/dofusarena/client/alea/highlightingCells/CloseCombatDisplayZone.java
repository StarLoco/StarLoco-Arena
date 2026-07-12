/*    */ package com.ankamagames.dofusarena.client.alea.highlightingCells;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;
/*    */ import com.ankamagames.dofusarena.client.DofusArenaClientConstants;
/*    */ import com.ankamagames.dofusarena.client.alea.DofusArenaWorldScene;
/*    */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CloseCombatDisplayZone
/*    */   extends RangeAndEffectDisplayer
/*    */ {
/*    */   private static final String ZONE_EFFECT_NAME = "CloseCombatZoneEffect";
/*    */   private static final String RANGE_NAME = "CloseCombatRange";
/*    */   private static final String RANGE_WITH_CONSTRAINT = "CloseCombatRangeWithContraint";
/*    */   
/*    */   public CloseCombatDisplayZone()
/*    */   {
/* 24 */     super("CloseCombatRange", DofusArenaClientConstants.RANGE_COLOR, "CloseCombatZoneEffect", DofusArenaClientConstants.ZONE_EFFECT_COLOR, "CloseCombatRangeWithContraint", DofusArenaClientConstants.RANGE_COLOR_WITH_CONSTRAINTS);
/*    */   }
/*    */   
/*    */   public void selectCloseCombatRange(Fighter fighter, DofusArenaWorldScene scene) {
/* 28 */     selectRange(fighter, scene);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected RangeAndEffectDisplayer.RangeValidity checkValidity(WorldElement element)
/*    */   {
/* 37 */     switch (this.m_fight.getCloseCombatValidity(this.m_fighter, element.getCoordinates())) {
/*    */     case CRITERIONS_NOT_VALID: 
/* 39 */       return RangeAndEffectDisplayer.RangeValidity.OK;
/*    */     }
/* 41 */     return RangeAndEffectDisplayer.RangeValidity.INVALID;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\alea\highlightingCells\CloseCombatDisplayZone.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */