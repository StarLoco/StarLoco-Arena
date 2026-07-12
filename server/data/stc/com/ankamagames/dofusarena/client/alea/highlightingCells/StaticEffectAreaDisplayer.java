/*     */ package com.ankamagames.dofusarena.client.alea.highlightingCells;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effectArea.BasicEffectArea;
/*     */ import com.ankamagames.baseImpl.graphics.alea.WorldCell;
/*     */ import com.ankamagames.baseImpl.graphics.alea.cellSelector.CellSelector;
/*     */ import com.ankamagames.baseImpl.graphics.alea.cellSelector.ElementSelection;
/*     */ import com.ankamagames.baseImpl.graphics.alea.cellSelector.ElementSelector;
/*     */ import com.ankamagames.baseImpl.graphics.alea.cellSelector.elementSelector.ElementSelectorGround;
/*     */ import com.ankamagames.baseImpl.graphics.alea.display.AleaWorldScene;
/*     */ import com.ankamagames.baseImpl.graphics.alea.display.DisplayedElement;
/*     */ import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;
/*     */ import com.ankamagames.framework.ai.targetfinder.aoe.AreaOfEffect;
/*     */ import com.ankamagames.framework.graphics.opengl.base.material.Material;
/*     */ import com.ankamagames.framework.kernel.core.maths.Direction8;
/*     */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*     */ import com.ankamagames.graphics.isometric.highlight.HighLightLayer;
/*     */ import com.ankamagames.graphics.isometric.highlight.HighLightManager;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
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
/*     */ public class StaticEffectAreaDisplayer
/*     */ {
/*  34 */   private static int NEXT_SELECTION_ID = 1;
/*  35 */   private static String PREFIX_SELECTION_NAME = "STATIC_EFFECT";
/*     */   
/*     */   private static final int TEAM_COUNT = 2;
/*     */   
/*     */   private static final String PREFIX_ACTIVATE_LAYER = "ACTIVATE_LAYER";
/*     */   
/*     */   private boolean m_activated;
/*  42 */   private static final float[] STATIC_ZONE_EFFECT_COLOR = { 0.0F, 0.0F, 0.0F, 0.4F };
/*  43 */   private static StaticEffectAreaDisplayer m_instance = new StaticEffectAreaDisplayer();
/*     */   
/*     */   public static StaticEffectAreaDisplayer getInstance() {
/*  46 */     return m_instance;
/*     */   }
/*     */   
/*     */   private static int getNextSelectionId() {
/*  50 */     if (NEXT_SELECTION_ID == Integer.MAX_VALUE)
/*  51 */       return 1;
/*  52 */     return NEXT_SELECTION_ID++;
/*     */   }
/*     */   
/*  55 */   protected ElementSelector m_elementSelector = new ElementSelectorGround();
/*  56 */   private HashMap<BasicEffectArea, ElementSelection> m_areaSelections = new HashMap();
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
/*     */   public boolean isActivated()
/*     */   {
/*  69 */     return this.m_activated;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void activate()
/*     */   {
/*  76 */     if (!this.m_activated) {
/*     */       try {
/*  78 */         for (int i = 0; i < 2; i++) {
/*  79 */           HighLightLayer layer = HighLightManager.getInstance().createLayer(getActivateLayerName(i));
/*  80 */           Material material = new Material();
/*  81 */           float[] color = com.ankamagames.dofusarena.client.DofusArenaClientConstants.TEAM_COLOR[i];
/*  82 */           material.setDiffuse(color[0], color[1], color[2], 0.5F);
/*  83 */           material.setUseDiffuse(true);
/*  84 */           layer.setMaterial(material);
/*     */         }
/*     */       } catch (Exception e) {
/*  87 */         e.printStackTrace();
/*     */       }
/*  89 */       this.m_activated = true;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void deactivate()
/*     */   {
/*  97 */     if (this.m_activated) {
/*  98 */       clear();
/*  99 */       for (int i = 0; i < 2; i++) {
/* 100 */         HighLightManager.getInstance().removeLayer(getActivateLayerName(i));
/*     */       }
/* 102 */       this.m_activated = false;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void clear()
/*     */   {
/* 111 */     for (ElementSelection selection : this.m_areaSelections.values())
/*     */     {
/* 113 */       selection.clear();
/*     */     }
/* 115 */     this.m_areaSelections.clear();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addStaticEffectArea(BasicEffectArea area, AleaWorldScene scene)
/*     */   {
/* 125 */     if (isActivated()) {
/* 126 */       ElementSelection selection = new ElementSelection(PREFIX_SELECTION_NAME + getNextSelectionId(), STATIC_ZONE_EFFECT_COLOR);
/* 127 */       selection.add(area.getPosition());
/*     */       
/*     */ 
/* 130 */       List<int[]> pattern = area.getArea().getPattern();
/*     */       
/* 132 */       Point3 p = area.getPosition();
/*     */       
/* 134 */       WorldCell centerCell = (WorldCell)scene.getWorldCell(p.getX(), p.getY());
/* 135 */       if (centerCell != null)
/*     */       {
/* 137 */         List<WorldElement> elements = CellSelector.getWorldElements(centerCell, scene, Direction8.NONE, this.m_elementSelector, pattern);
/* 138 */         for (WorldElement element : elements) {
/* 139 */           selection.add(element.getCoordinates());
/*     */         }
/*     */       }
/*     */       
/* 143 */       selection.refreshDisplay(scene);
/*     */       
/* 145 */       this.m_areaSelections.put(area, selection);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeStaticEffectArea(BasicEffectArea area, AleaWorldScene scene)
/*     */   {
/* 156 */     if (isActivated()) {
/* 157 */       ElementSelection selection = (ElementSelection)this.m_areaSelections.get(area);
/*     */       
/* 159 */       if (selection != null)
/*     */       {
/* 161 */         selection.clear();
/* 162 */         this.m_areaSelections.remove(area);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private String getActivateLayerName(int teamId)
/*     */   {
/* 174 */     return "ACTIVATE_LAYER" + teamId;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void markElementUse(Point3 target, AleaWorldScene scene)
/*     */   {
/* 183 */     for (int i = 0; i < 2; i++) {
/* 184 */       markElementUse(i, target, scene);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void unmarkElement(Point3 target, AleaWorldScene scene)
/*     */   {
/* 194 */     for (int i = 0; i < 2; i++) {
/* 195 */       unmarkElement(i, target, scene);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void markElementUse(int teamId, Point3 target, AleaWorldScene scene)
/*     */   {
/* 206 */     if (isActivated()) {
/* 207 */       DisplayedElement element = scene.getDisplayedElementAt(target);
/* 208 */       if (element != null) {
/* 209 */         HighLightManager.getInstance().add(element.getLayerReference(), getActivateLayerName(teamId));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void unmarkElement(int teamId, Point3 target, AleaWorldScene scene)
/*     */   {
/* 221 */     if (isActivated()) {
/* 222 */       DisplayedElement element = scene.getDisplayedElementAt(target);
/* 223 */       if (element != null) {
/* 224 */         HighLightManager.getInstance().remove(element.getLayerReference(), getActivateLayerName(teamId));
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\alea\highlightingCells\StaticEffectAreaDisplayer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */