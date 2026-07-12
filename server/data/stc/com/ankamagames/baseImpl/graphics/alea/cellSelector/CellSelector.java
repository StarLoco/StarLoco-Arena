/*     */ package com.ankamagames.baseImpl.graphics.alea.cellSelector;
/*     */ 
/*     */ import com.ankamagames.baseImpl.graphics.alea.WorldCell;
/*     */ import com.ankamagames.baseImpl.graphics.alea.display.AleaWorldScene;
/*     */ import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;
/*     */ import com.ankamagames.framework.graphics.opengl.base.material.Material;
/*     */ import com.ankamagames.framework.graphics.painting.brushes.BaseBrush;
/*     */ import com.ankamagames.framework.kernel.core.maths.Direction8;
/*     */ import com.ankamagames.graphics.isometric.highlight.HighLightLayer;
/*     */ import com.ankamagames.graphics.isometric.highlight.HighLightManager;
/*     */ import com.ankamagames.graphics.isometric.highlight.UniqueHandleReference;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CellSelector
/*     */ {
/*  22 */   protected static final Logger m_logger = Logger.getLogger(CellSelector.class);
/*     */   private String m_name;
/*     */   private ElementSelector m_elementSelector;
/*     */   private BaseBrush m_brush;
/*     */   private List<WorldElement> m_lastSelected;
/*     */   
/*     */   public CellSelector(String name, float[] color, ElementSelector elementSelector)
/*     */   {
/*  30 */     this.m_name = name;
/*  31 */     this.m_elementSelector = elementSelector;
/*     */     try
/*     */     {
/*  34 */       HighLightManager.getInstance().createLayer(this.m_name);
/*  35 */       Material highlightColor = HighLightManager.getInstance().getLayer(this.m_name).getMaterial();
/*  36 */       highlightColor.setDiffuse(color);
/*  37 */       highlightColor.setUseDiffuse(true);
/*     */     } catch (Exception e) {
/*  39 */       e.printStackTrace();
/*     */     }
/*  41 */     Material highlightColor = HighLightManager.getInstance().getLayer(this.m_name).getMaterial();
/*  42 */     highlightColor.setDiffuse(color);
/*  43 */     highlightColor.setUseDiffuse(true);
/*     */   }
/*     */   
/*     */   public BaseBrush getBrush() {
/*  47 */     return this.m_brush;
/*     */   }
/*     */   
/*     */   public void setBrush(BaseBrush brush) {
/*  51 */     this.m_brush = brush;
/*     */   }
/*     */   
/*     */   public ElementSelector getElementSelector() {
/*  55 */     return this.m_elementSelector;
/*     */   }
/*     */   
/*     */   public void setElementSelector(ElementSelector elementSelector) {
/*  59 */     this.m_elementSelector = elementSelector;
/*     */   }
/*     */   
/*     */   private List<WorldElement> getWorldElements(WorldCell originCell, AleaWorldScene scene, Direction8 orientation) {
/*  63 */     return getWorldElements(originCell, scene, orientation, this.m_elementSelector, this.m_brush);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static List<WorldElement> getWorldElements(WorldCell originCell, AleaWorldScene scene, Direction8 orientation, ElementSelector elementSelector, BaseBrush brush)
/*     */   {
/*  75 */     elementSelector.reset(originCell);
/*     */     
/*  77 */     int brushSizeX = brush.getSizeX();
/*  78 */     int brushSizeY = brush.getSizeY();
/*     */     
/*     */ 
/*     */ 
/*  82 */     switch (orientation) {
/*     */     case EAST: 
/*  84 */       int originX = originCell.getX() - brush.getCenterX();
/*  85 */       int originY = originCell.getY() - brush.getCenterY();
/*     */       
/*  87 */       for (int x = 0; x < brushSizeX; x++) {
/*  88 */         int posX = originX + x;
/*  89 */         for (int y = 0; y < brushSizeY; y++) {
/*  90 */           if (brush.getData(x, y)) {
/*  91 */             WorldCell cell = (WorldCell)scene.getWorldCell(posX, originY + y);
/*  92 */             if (cell != null)
/*  93 */               elementSelector.addElementFromCell(cell);
/*     */           }
/*     */         }
/*     */       }
/*  97 */       break;
/*     */     
/*     */     case NORTH: 
/* 100 */       int originX = originCell.getX() - brush.getCenterY() + brush.getSizeY() - 1;
/* 101 */       int originY = originCell.getY() - brush.getCenterX();
/*     */       
/* 103 */       for (int x = 0; x < brushSizeX; x++) {
/* 104 */         int posX = originY + x;
/* 105 */         for (int y = 0; y < brushSizeY; y++) {
/* 106 */           if (brush.getData(x, y)) {
/* 107 */             WorldCell cell = (WorldCell)scene.getWorldCell(originX - y, posX);
/* 108 */             if (cell != null)
/* 109 */               elementSelector.addElementFromCell(cell);
/*     */           }
/*     */         }
/*     */       }
/* 113 */       break;
/*     */     
/*     */     case NORTH_WEST: 
/* 116 */       int originX = originCell.getX() - brush.getCenterX() + brush.getSizeX() - 1;
/* 117 */       int originY = originCell.getY() - brush.getCenterY() + brush.getSizeY() - 1;
/*     */       
/* 119 */       for (int x = 0; x < brushSizeX; x++) {
/* 120 */         int posX = originX - x;
/* 121 */         for (int y = 0; y < brushSizeY; y++) {
/* 122 */           if (brush.getData(x, y)) {
/* 123 */             WorldCell cell = (WorldCell)scene.getWorldCell(posX, originY - y);
/* 124 */             if (cell != null)
/* 125 */               elementSelector.addElementFromCell(cell);
/*     */           }
/*     */         }
/*     */       }
/* 129 */       break;
/*     */     
/*     */     case SOUTH_EAST: 
/* 132 */       int originX = originCell.getX() - brush.getCenterY();
/* 133 */       int originY = originCell.getY() + brush.getCenterX();
/*     */       
/* 135 */       for (int x = 0; x < brushSizeX; x++) {
/* 136 */         int posX = originY - x;
/* 137 */         for (int y = 0; y < brushSizeY; y++) {
/* 138 */           if (brush.getData(x, y)) {
/* 139 */             WorldCell cell = (WorldCell)scene.getWorldCell(originX + y, posX);
/* 140 */             if (cell != null) {
/* 141 */               elementSelector.addElementFromCell(cell);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */     
/* 149 */     return elementSelector.getListElement();
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
/*     */   public static List<WorldElement> getWorldElements(WorldCell originCell, AleaWorldScene scene, Direction8 orientation, ElementSelector elementSelector, List<int[]> pattern)
/*     */   {
/* 162 */     elementSelector.reset(originCell);
/*     */     
/* 164 */     int originX = originCell.getX();
/* 165 */     int originY = originCell.getY();
/*     */     
/* 167 */     switch (orientation) {
/*     */     case EAST: 
/*     */     case WEST: 
/* 170 */       for (int[] offset : pattern) {
/* 171 */         WorldCell cell = (WorldCell)scene.getWorldCell(originX + offset[0], originY + offset[1]);
/* 172 */         if (cell != null)
/* 173 */           elementSelector.addElementFromCell(cell);
/*     */       }
/* 175 */       break;
/*     */     
/*     */     case NORTH: 
/* 178 */       for (int[] offset : pattern) {
/* 179 */         WorldCell cell = (WorldCell)scene.getWorldCell(originX - offset[1], originY + offset[0]);
/* 180 */         if (cell != null)
/* 181 */           elementSelector.addElementFromCell(cell);
/*     */       }
/* 183 */       break;
/*     */     
/*     */     case NORTH_WEST: 
/* 186 */       for (int[] offset : pattern) {
/* 187 */         WorldCell cell = (WorldCell)scene.getWorldCell(originX - offset[0], originY - offset[1]);
/* 188 */         if (cell != null)
/* 189 */           elementSelector.addElementFromCell(cell);
/*     */       }
/* 191 */       break;
/*     */     
/*     */     case SOUTH_EAST: 
/* 194 */       for (int[] offset : pattern) {
/* 195 */         WorldCell cell = (WorldCell)scene.getWorldCell(originX + offset[1], originY - offset[0]);
/* 196 */         if (cell != null)
/* 197 */           elementSelector.addElementFromCell(cell);
/*     */       }
/* 199 */       break;
/*     */     case NONE: case NORTH_EAST: case SOUTH: 
/*     */     case SOUTH_WEST: case TOP: default: 
/* 202 */       m_logger.error("Impossible de selectionner des cellules dans cette direction :" + orientation);
/*     */     }
/*     */     
/*     */     
/* 206 */     return elementSelector.getListElement();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void unHighLightAll()
/*     */   {
/* 213 */     HighLightManager.getInstance().clearLayer(this.m_name);
/* 214 */     this.m_lastSelected = null;
/*     */   }
/*     */   
/*     */   public void highlight(WorldCell originCell, AleaWorldScene scene, Direction8 orientation)
/*     */   {
/* 219 */     this.m_lastSelected = getWorldElements(originCell, scene, orientation);
/* 220 */     if (this.m_lastSelected != null) {
/* 221 */       int size = this.m_lastSelected.size();
/* 222 */       for (int i = 0; i < size; i++) {
/* 223 */         if (!HighLightManager.getInstance().add((UniqueHandleReference)this.m_lastSelected.get(i), this.m_name)) {
/* 224 */           this.m_lastSelected.remove(i);
/* 225 */           i--;
/* 226 */           size--;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void highlight(List<WorldCell> cells) {
/* 233 */     this.m_lastSelected = getWorldElements(cells);
/* 234 */     if (this.m_lastSelected != null) {
/* 235 */       int size = this.m_lastSelected.size();
/* 236 */       for (int i = 0; i < size; i++) {
/* 237 */         if (!HighLightManager.getInstance().add((UniqueHandleReference)this.m_lastSelected.get(i), this.m_name)) {
/* 238 */           this.m_lastSelected.remove(i);
/* 239 */           i--;
/* 240 */           size--;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected List<WorldElement> getWorldElements(List<WorldCell> cells) {
/* 247 */     this.m_elementSelector.clear();
/*     */     
/* 249 */     for (WorldCell cell : cells) {
/* 250 */       this.m_elementSelector.addElementFromCell(cell);
/*     */     }
/*     */     
/* 253 */     return this.m_elementSelector.getListElement();
/*     */   }
/*     */   
/*     */   public void unHighLightLastSelection()
/*     */   {
/* 258 */     if (this.m_lastSelected != null) {
/* 259 */       for (WorldElement element : this.m_lastSelected) {
/* 260 */         HighLightManager.getInstance().remove(element, this.m_name);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void setColor(float[] color) {
/* 266 */     Material highlightColor = HighLightManager.getInstance().getLayer(this.m_name).getMaterial();
/* 267 */     highlightColor.setDiffuse(color);
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\cellSelector\CellSelector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */