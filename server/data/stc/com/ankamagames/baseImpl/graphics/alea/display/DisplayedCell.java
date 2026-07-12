/*     */ package com.ankamagames.baseImpl.graphics.alea.display;
/*     */ 
/*     */ import com.ankamagames.baseImpl.graphics.alea.WorldCell;
/*     */ import com.ankamagames.baseImpl.graphics.alea.WorldGroup;
/*     */ import com.ankamagames.baseImpl.graphics.alea.WorldGroupManager;
/*     */ import com.ankamagames.baseImpl.graphics.alea.element.GraphicalElement;
/*     */ import com.ankamagames.baseImpl.graphics.alea.element.properties.GraphicalElementProperties;
/*     */ import com.ankamagames.baseImpl.graphics.alea.worldElement.GraphicalWorldElement;
/*     */ import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;
/*     */ import com.ankamagames.framework.graphics.opengl.base.impl.Mesh2D;
/*     */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*     */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*     */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*     */ import com.ankamagames.graphics.isometric.highlight.HighLightManager;
/*     */ import com.ankamagames.graphics.isometric.lights.LightManager;
/*     */ import com.ankamagames.graphics.isometric.lines.LinesManager;
/*     */ import gnu.trove.TIntArrayList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DisplayedCell
/*     */   implements Poolable
/*     */ {
/*  31 */   private static final ObjectPool m_staticPool = new MonitoredPool(new ObjectFactory() {
/*     */     public DisplayedCell makeObject() {
/*  33 */       return new DisplayedCell();
/*     */     }
/*  31 */   });
/*     */   
/*     */ 
/*     */   private ArrayList<DisplayedElement> m_hitElements;
/*     */   
/*     */ 
/*     */   private WorldCell m_worldCell;
/*     */   
/*     */ 
/*     */   private double m_screenX;
/*     */   
/*     */ 
/*     */   private double m_screenY;
/*     */   
/*     */   private ArrayList<DisplayedElement> m_displayedElements;
/*     */   
/*     */ 
/*     */   public DisplayedCell()
/*     */   {
/*  50 */     this.m_displayedElements = new ArrayList();
/*  51 */     this.m_hitElements = new ArrayList();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setWorldCell(WorldCell worldCell)
/*     */   {
/*  59 */     this.m_worldCell = worldCell;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public WorldCell getWorldCell()
/*     */   {
/*  66 */     return this.m_worldCell;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getScreenX()
/*     */   {
/*  74 */     return this.m_screenX;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getScreenY()
/*     */   {
/*  82 */     return this.m_screenY;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setScreenX(double screenX)
/*     */   {
/*  91 */     this.m_screenX = screenX;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setScreenY(double screenY)
/*     */   {
/* 100 */     this.m_screenY = screenY;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ArrayList<DisplayedElement> getDisplayedElements()
/*     */   {
/* 107 */     return this.m_displayedElements;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void updateDisplayedElements(AleaWorldScene scene, int row, int heightCellCount, int centerScreenIsoWorldX, int centerScreenIsoWorldY)
/*     */   {
/* 119 */     int elevationUnit = (int)Math.floor(scene.getElevationUnit());
/*     */     
/*     */ 
/* 122 */     for (DisplayedElement displayedElement : this.m_displayedElements) {
/* 123 */       displayedElement.release();
/*     */     }
/*     */     
/* 126 */     this.m_displayedElements.clear();
/*     */     
/* 128 */     if (this.m_worldCell == null) {
/* 129 */       return;
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 134 */       for (??? = this.m_worldCell.getVisualElements().iterator(); ???.hasNext();) { GraphicalWorldElement element = (GraphicalWorldElement)???.next();
/*     */         
/* 136 */         GraphicalElement graphicalElement = element.getElement();
/*     */         
/* 138 */         float elementAltitudePx = element.getAltitude() * elevationUnit;
/* 139 */         float elementHeightPx = graphicalElement.getStateProperties(element.getState()).getHeight() * elevationUnit;
/*     */         
/* 141 */         if (isElementVisibleOnScreen(scene, (int)(elementAltitudePx + elementHeightPx), heightCellCount, row)) {
/* 142 */           float displayedElementScreenY = (float)this.m_screenY + elementAltitudePx;
/* 143 */           float displayedElementScreenTopY = displayedElementScreenY + elementHeightPx;
/*     */           
/* 145 */           double zValue = (-this.m_screenY + element.getAltitudeOrder()) / scene.getFrustumHeight();
/*     */           
/* 147 */           DisplayedElement displayedElement = DisplayedElement.checkOut();
/* 148 */           displayedElement.setDisplayedCell(this);
/* 149 */           displayedElement.setWorldElement(element);
/* 150 */           displayedElement.setScreenPosition((float)this.m_screenX, displayedElementScreenY, displayedElementScreenTopY);
/* 151 */           displayedElement.setZOrder((float)zValue);
/* 152 */           displayedElement.setAltitude(elementAltitudePx / elevationUnit);
/* 153 */           displayedElement.setBrightness(element.getBrightness());
/* 154 */           displayedElement.setTeint(element.getTeint());
/* 155 */           displayedElement.setState(element.getState());
/* 156 */           displayedElement.setLevel(element.getLevel());
/*     */           
/*     */ 
/* 159 */           Mesh2D mesh = CellMeshFactory.getInstance().getMeshFromGraphicalElement(graphicalElement, displayedElement.getState());
/* 160 */           mesh.setScreenPosition(displayedElement.getScreenX(), displayedElement.getScreenY());
/* 161 */           mesh.setZOrder(displayedElement.getZOrder());
/*     */           
/* 163 */           int gfxId = graphicalElement.getStateProperties(element.getState()).getGfxId();
/* 164 */           scene.addSpecialEffectToMesh(gfxId, mesh);
/*     */           
/* 166 */           displayedElement.setMesh(mesh);
/* 167 */           this.m_displayedElements.add(displayedElement);
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 171 */       e.printStackTrace();
/*     */     }
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
/*     */   private boolean isElementVisibleOnScreen(AleaWorldScene scene, int elementTopAltitudePx, int heightCellCount, int screenRow)
/*     */   {
/* 186 */     int screenBottomRow = heightCellCount * 2 + scene.getIsoCamera().getTopScreenCellHeightBonus();
/*     */     
/*     */ 
/* 189 */     if (screenRow > screenBottomRow)
/*     */     {
/* 191 */       if (elementTopAltitudePx < (screenRow - screenBottomRow - 3) * 21.5D) {
/* 192 */         return false;
/*     */       }
/*     */     }
/* 195 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void prepareCellBeforeRendering(AleaWorldScene scene)
/*     */   {
/* 205 */     if (this.m_worldCell == null) {
/* 206 */       return;
/*     */     }
/*     */     
/*     */ 
/* 210 */     for (DisplayedElement displayedElement : this.m_displayedElements)
/*     */     {
/* 212 */       Mesh2D elementMesh = displayedElement.getMesh();
/*     */       
/* 214 */       float brightnessValue = 0.5F + displayedElement.getBrightness() * LightManager.getInstance().getLightContrast();
/* 215 */       float[] teint = displayedElement.getTeint();
/*     */       
/*     */ 
/* 218 */       FadeManager.getInstance().apply(displayedElement, brightnessValue, teint);
/*     */       
/* 220 */       int groupInstanceId = displayedElement.getWorldElement().getGroupInstanceId();
/*     */       
/*     */ 
/*     */ 
/* 224 */       if (groupInstanceId > 0) {
/* 225 */         WorldGroup worldGroup = WorldGroupManager.getInstance().getGroupFromInstance(groupInstanceId);
/* 226 */         int instanceLevel = WorldGroupManager.getInstance().getLevelFromInstance(groupInstanceId);
/*     */         
/* 228 */         if (worldGroup != null) {
/* 229 */           TIntArrayList maskedLayers = null;
/*     */           
/*     */ 
/* 232 */           if (scene.getIsoCamera().getCameraGroupInstanceId() == 0) {
/* 233 */             maskedLayers = worldGroup.getMaskedLayers(-1);
/*     */           } else {
/* 235 */             WorldGroup cameraWorldGroup = WorldGroupManager.getInstance().getGroupFromInstance(scene.getIsoCamera().getCameraGroupInstanceId());
/* 236 */             TIntArrayList groupMaskedLayers = cameraWorldGroup.getMaskedLayers(displayedElement.getLevel() - instanceLevel);
/*     */             
/*     */ 
/*     */ 
/*     */ 
/* 241 */             if (groupMaskedLayers == null) {
/* 242 */               maskedLayers = worldGroup.getMaskedLayers(-1);
/*     */             }
/*     */           }
/*     */           
/* 246 */           if ((maskedLayers != null) && (maskedLayers.contains(displayedElement.getLevel() - instanceLevel))) {
/* 247 */             displayedElement.setVisible(false);
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 253 */       if (displayedElement.isVisible()) {
/* 254 */         HighLightManager.getInstance().prepareElementBeforeRendering(scene, displayedElement);
/* 255 */         LinesManager.getInstance().prepareElementBeforeRendering(scene, displayedElement);
/* 256 */         LightManager.getInstance().applyLightToMesh(elementMesh, displayedElement.getDisplayedCell().getWorldCell().getX(), displayedElement.getDisplayedCell().getWorldCell().getY(), 
/* 257 */           displayedElement.getAltitude());
/*     */         
/* 259 */         scene.addChild(elementMesh);
/*     */       }
/*     */     }
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
/*     */   public ArrayList<DisplayedElement> getDisplayedElementsUnderPoint(double x, double y)
/*     */   {
/* 276 */     this.m_hitElements.clear();
/* 277 */     for (DisplayedElement displayedElement : this.m_displayedElements) {
/* 278 */       if ((displayedElement.isVisible()) && (displayedElement.rectHitTest(x, y)) && 
/* 279 */         (displayedElement.fineHitTest(x, y, 0.2D))) {
/* 280 */         this.m_hitElements.add(displayedElement);
/*     */       }
/*     */     }
/*     */     
/* 284 */     return this.m_hitElements;
/*     */   }
/*     */   
/*     */   public static DisplayedCell checkOut() {
/*     */     try {
/* 289 */       return (DisplayedCell)m_staticPool.borrowObject();
/*     */     } catch (Exception e) {
/* 291 */       e.printStackTrace();
/*     */     }
/*     */     
/* 294 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public void release()
/*     */   {
/*     */     try
/*     */     {
/* 302 */       m_staticPool.returnObject(this);
/*     */     } catch (Exception e) {
/* 304 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public void onCheckOut() {}
/*     */   
/*     */   public void onCheckIn() {}
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\display\DisplayedCell.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */