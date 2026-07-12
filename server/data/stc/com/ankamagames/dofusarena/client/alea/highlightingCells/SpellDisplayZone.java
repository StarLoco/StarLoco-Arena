/*    */ package com.ankamagames.dofusarena.client.alea.highlightingCells;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;
/*    */ import com.ankamagames.dofusarena.client.DofusArenaClientConstants;
/*    */ import com.ankamagames.dofusarena.client.alea.DofusArenaWorldScene;
/*    */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*    */ import com.ankamagames.dofusarena.client.core.game.spell.Spell;
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
/*    */ public class SpellDisplayZone
/*    */   extends RangeAndEffectDisplayer
/*    */ {
/*    */   private static final String ZONE_EFFECT_NAME = "SpellZoneEffect";
/*    */   private static final String RANGE_NAME = "SpellRange";
/*    */   private static final String RANGE_WITH_CONSTRAINT_NAME = "SpellRangeWithConstraint";
/*    */   private Spell m_selectedSpell;
/*    */   
/*    */   public SpellDisplayZone()
/*    */   {
/* 28 */     super("SpellRange", DofusArenaClientConstants.RANGE_COLOR, "SpellZoneEffect", DofusArenaClientConstants.ZONE_EFFECT_COLOR, "SpellRangeWithConstraint", DofusArenaClientConstants.RANGE_COLOR_WITH_CONSTRAINTS);
/*    */   }
/*    */   
/*    */   public void selectSpellRange(Spell selectedSpell, Fighter fighter, DofusArenaWorldScene scene) {
/* 32 */     this.m_selectedSpell = selectedSpell;
/*    */     
/* 34 */     selectRange(fighter, scene);
/*    */     
/* 36 */     this.m_selectedSpell = null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected void selectRange(Fighter fighter, DofusArenaWorldScene scene)
/*    */   {
/* 45 */     super.selectRange(fighter, scene);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected RangeAndEffectDisplayer.RangeValidity checkValidity(WorldElement element)
/*    */   {
/* 54 */     switch (this.m_fight.getSpellCastValidity(this.m_fighter, this.m_selectedSpell, element.getCoordinates())) {
/*    */     case CAST_CRITERIONS_NOT_VALID: 
/* 56 */       return RangeAndEffectDisplayer.RangeValidity.OK;
/*    */     case CELLS_NOT_ALIGNED: 
/* 58 */       return RangeAndEffectDisplayer.RangeValidity.OK_WITH_CONSTRAINTS;
/*    */     }
/* 60 */     return RangeAndEffectDisplayer.RangeValidity.INVALID;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\alea\highlightingCells\SpellDisplayZone.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */