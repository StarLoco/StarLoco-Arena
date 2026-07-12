/*    */ package com.ankamagames.dofusarena.client.alea.highlightingCells;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;
/*    */ import com.ankamagames.dofusarena.client.DofusArenaClientConstants;
/*    */ import com.ankamagames.dofusarena.client.alea.DofusArenaWorldScene;
/*    */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*    */ import com.ankamagames.dofusarena.common.game.card.AbstractFighterCard;
/*    */ import com.ankamagames.dofusarena.common.game.fight.CardUseValidity;
/*    */ import com.ankamagames.dofusarena.common.game.fighter.AbstractFighter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FighterCardUseDisplayZone
/*    */   extends RangeAndEffectDisplayer
/*    */ {
/*    */   private static final String ZONE_EFFECT_NAME = "FighterCardUseZoneEffect";
/*    */   private static final String RANGE_NAME = "FighterCardUseRange";
/*    */   private static final String RANGE_WITH_CONSTRAINT_NAME = "FighterCardUseRangeWithConstraint";
/*    */   private AbstractFighterCard m_fighterCard;
/*    */   
/*    */   public FighterCardUseDisplayZone() {
/* 28 */     super("FighterCardUseRange", DofusArenaClientConstants.RANGE_COLOR, "FighterCardUseZoneEffect", DofusArenaClientConstants.ZONE_EFFECT_COLOR, "FighterCardUseRangeWithConstraint", DofusArenaClientConstants.RANGE_COLOR_WITH_CONSTRAINTS);
/*    */   }
/*    */   
/*    */   public void selectCardUseRange(AbstractFighterCard fighterCard, Fighter fighter, DofusArenaWorldScene scene) {
/* 32 */     this.m_fighterCard = fighterCard;
/* 33 */     selectRange(fighter, scene);
/* 34 */     this.m_fighterCard = null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected RangeAndEffectDisplayer.RangeValidity checkValidity(WorldElement element) {
/* 43 */     switch (this.m_fight.getCardUseValidity((AbstractFighter)this.m_fighter, this.m_fighterCard, element.getCoordinates())) {
/*    */       case OK:
/* 45 */         return RangeAndEffectDisplayer.RangeValidity.OK;
/*    */     } 
/* 47 */     return RangeAndEffectDisplayer.RangeValidity.INVALID;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\alea\highlightingCells\FighterCardUseDisplayZone.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */