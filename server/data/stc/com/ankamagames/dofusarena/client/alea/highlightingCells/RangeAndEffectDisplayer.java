/*     */ package com.ankamagames.dofusarena.client.alea.highlightingCells;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.Effect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContainer;
/*     */ import com.ankamagames.baseImpl.graphics.alea.WorldCell;
/*     */ import com.ankamagames.baseImpl.graphics.alea.WorldManager;
/*     */ import com.ankamagames.baseImpl.graphics.alea.WorldMap;
/*     */ import com.ankamagames.baseImpl.graphics.alea.cellSelector.ElementSelection;
/*     */ import com.ankamagames.baseImpl.graphics.alea.cellSelector.ElementSelector;
/*     */ import com.ankamagames.baseImpl.graphics.alea.cellSelector.elementSelector.ElementSelectorGround;
/*     */ import com.ankamagames.baseImpl.graphics.alea.display.AleaWorldScene;
/*     */ import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;
/*     */ import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
/*     */ import com.ankamagames.dofusarena.client.alea.DofusArenaWorldScene;
/*     */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*     */ import com.ankamagames.framework.ai.targetfinder.aoe.AreaOfEffect;
/*     */ import com.ankamagames.framework.ai.targetfinder.aoe.AreaOfEffectEnum;
/*     */ import com.ankamagames.framework.kernel.core.maths.Direction8;
/*     */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*     */ import com.ankamagames.framework.kernel.core.maths.Vector3i;
/*     */ import gnu.trove.TLongObjectHashMap;
/*     */ import java.util.List;
/*     */ 
/*     */ public abstract class RangeAndEffectDisplayer
/*     */ {
/*     */   protected Fighter m_fighter;
/*     */   protected com.ankamagames.dofusarena.common.game.fight.AbstractFight m_fight;
/*     */   protected DofusArenaWorldScene m_scene;
/*     */   protected boolean m_prepared;
/*     */   protected ElementSelection m_zoneEffect;
/*     */   protected ElementSelection m_range;
/*     */   protected ElementSelection m_rangeWithConstraint;
/*     */   
/*     */   public static enum RangeValidity
/*     */   {
/*  36 */     OK, 
/*  37 */     OK_WITH_CONSTRAINTS, 
/*  38 */     INVALID;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  54 */   protected ElementSelector m_elementSelector = new ElementSelectorGround();
/*     */   
/*     */   public RangeAndEffectDisplayer(String rangeName, float[] rangeColor, String zoneEffectName, float[] zoneEffectColor, String rangeWithConstraintName, float[] rangeWithConstraintColor) {
/*  57 */     this.m_range = new ElementSelection(rangeName, rangeColor);
/*  58 */     this.m_rangeWithConstraint = new ElementSelection(rangeWithConstraintName, rangeWithConstraintColor);
/*  59 */     this.m_zoneEffect = new ElementSelection(zoneEffectName, zoneEffectColor);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void clearRange()
/*     */   {
/*  66 */     clearZoneEffect();
/*  67 */     this.m_range.clear();
/*  68 */     this.m_rangeWithConstraint.clear();
/*  69 */     this.m_range.refreshDisplay(DofusArenaClientInstance.getInstance().getWorldScene());
/*  70 */     this.m_rangeWithConstraint.refreshDisplay(DofusArenaClientInstance.getInstance().getWorldScene());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void clearZoneEffect()
/*     */   {
/*  77 */     this.m_zoneEffect.clear();
/*  78 */     this.m_zoneEffect.refreshDisplay(DofusArenaClientInstance.getInstance().getWorldScene());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void selectZoneEffect(EffectContainer effects, Fighter fighter, Point3 target, AleaWorldScene scene)
/*     */   {
/*  87 */     this.m_zoneEffect.clear();
/*     */     
/*  89 */     if (effects != null)
/*     */     {
/*  91 */       WorldCell centerCell = (WorldCell)scene.getWorldCell(target.getX(), target.getY());
/*     */       
/*  93 */       Direction8 direction = Vector3i.getDirection4FromVector(target.getX() - fighter.getPosition().getX(), target.getY() - fighter.getPosition().getY());
/*     */       
/*  95 */       for (Effect effect : effects) {
/*  96 */         AreaOfEffect aoe = effect.getAreaOfEffect();
/*  97 */         if (aoe.getType() == AreaOfEffectEnum.EMPTY) {
/*  98 */           if (!this.m_zoneEffect.contains(target)) {
/*  99 */             this.m_zoneEffect.add(target);
/*     */           }
/*     */         } else {
/* 102 */           List<int[]> pattern = aoe.getPattern();
/* 103 */           List<WorldElement> elements = com.ankamagames.baseImpl.graphics.alea.cellSelector.CellSelector.getWorldElements(centerCell, scene, direction, this.m_elementSelector, pattern);
/* 104 */           for (WorldElement elt : elements) {
/* 105 */             if (!this.m_zoneEffect.contains(elt.getCoordinates())) {
/* 106 */               this.m_zoneEffect.add(elt.getCoordinates());
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     } else {
/* 112 */       this.m_zoneEffect.add(target);
/*     */     }
/*     */     
/* 115 */     this.m_zoneEffect.refreshDisplay(scene);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean rangeContains(Point3 target)
/*     */   {
/* 123 */     return (this.m_range.contains(target)) || (this.m_rangeWithConstraint.contains(target));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void selectRange(Fighter fighter, DofusArenaWorldScene scene)
/*     */   {
/* 133 */     clearRange();
/*     */     
/* 135 */     this.m_fighter = fighter;
/* 136 */     this.m_fight = fighter.getCurrentFight();
/* 137 */     this.m_scene = scene;
/*     */     
/*     */ 
/*     */ 
/* 141 */     Object[] worldMaps = WorldManager.getInstance().getWorldMaps().getValues();
/* 142 */     Object[] arrayOfObject1; int j = (arrayOfObject1 = worldMaps).length; for (int i = 0; i < j; i++) { Object objectMap = arrayOfObject1[i];
/* 143 */       WorldMap map = (WorldMap)objectMap;
/* 144 */       WorldCell[][] cells = map.getCells();
/*     */       
/* 146 */       if (cells != null)
/*     */       {
/*     */ 
/* 149 */         for (int x = 0; x < cells.length; x++) {
/* 150 */           for (int y = 0; y < cells.length; y++) {
/* 151 */             WorldCell cell = cells[x][y];
/* 152 */             if (cell != null)
/*     */             {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 160 */               List<WorldElement> elements = cell.getElementsAtTop();
/* 161 */               for (WorldElement element : elements) {
/* 162 */                 switch (checkValidity(element)) {
/*     */                 case INVALID: 
/* 164 */                   this.m_range.add(element.getCoordinates());
/* 165 */                   break;
/*     */                 case OK: 
/* 167 */                   this.m_rangeWithConstraint.add(element.getCoordinates());
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 175 */     this.m_range.refreshDisplay(scene);
/* 176 */     this.m_rangeWithConstraint.refreshDisplay(scene);
/*     */     
/* 178 */     this.m_fighter = null;
/* 179 */     this.m_fight = null;
/* 180 */     this.m_scene = null;
/*     */   }
/*     */   
/*     */   protected abstract RangeValidity checkValidity(WorldElement paramWorldElement);
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\alea\highlightingCells\RangeAndEffectDisplayer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */