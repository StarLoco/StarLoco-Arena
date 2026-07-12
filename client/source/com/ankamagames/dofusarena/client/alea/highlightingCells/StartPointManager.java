/*     */ package com.ankamagames.dofusarena.client.alea.highlightingCells;
/*     */ 
/*     */ import com.ankamagames.baseImpl.graphics.alea.WorldCell;
/*     */ import com.ankamagames.baseImpl.graphics.alea.WorldManager;
/*     */ import com.ankamagames.baseImpl.graphics.alea.WorldMap;
/*     */ import com.ankamagames.baseImpl.graphics.alea.cellSelector.ElementSelection;
/*     */ import com.ankamagames.baseImpl.graphics.alea.display.AleaWorldScene;
/*     */ import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;
/*     */ import com.ankamagames.dofusarena.client.DofusArenaClientConstants;
/*     */ import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
/*     */ import com.ankamagames.dofusarena.client.alea.element.FightStartPointElement;
/*     */ import com.ankamagames.framework.graphics.opengl.base.BaseTexture;
/*     */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*     */ import com.ankamagames.framework.kernel.core.resource.ResourceContext;
/*     */ import com.ankamagames.framework.kernel.core.resource.ResourceListener;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StartPointManager
/*     */   implements ResourceListener
/*     */ {
/*  30 */   private static final Logger m_logger = Logger.getLogger(StartPointManager.class);
/*     */   
/*  32 */   private static final BaseTexture TEXTURE = null;
/*     */   
/*     */   private boolean m_activated;
/*     */   
/*     */   private List<ElementSelection> m_teamStartPoints;
/*     */   
/*  38 */   private static StartPointManager m_instance = new StartPointManager();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StartPointManager() {
/*  44 */     int TEAM_COUNT = 2;
/*  45 */     this.m_teamStartPoints = new ArrayList<ElementSelection>(2);
/*  46 */     for (int i = 0; i < 2; i++) {
/*  47 */       this.m_teamStartPoints.add(new ElementSelection(getLayerName(i), DofusArenaClientConstants.TEAM_COLOR[i], TEXTURE));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StartPointManager getInstance() {
/*  55 */     return m_instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void activate(AleaWorldScene scene) {
/*  62 */     if (!this.m_activated) {
/*  63 */       this.m_activated = true;
/*  64 */       WorldManager.getInstance().addListener(this);
/*     */       
/*  66 */       Object[] objectMaps = WorldManager.getInstance().getWorldMaps().getValues();
/*  67 */       int count = 0; byte b; int i;
/*     */       Object[] arrayOfObject1;
/*  69 */       for (i = (arrayOfObject1 = objectMaps).length, b = 0; b < i; ) { Object map = arrayOfObject1[b];
/*  70 */         WorldMap worldMap = (WorldMap)map;
/*  71 */         if (map != null) {
/*  72 */           addCells(worldMap.getCells());
/*  73 */           count++;
/*     */         } 
/*     */         b++; }
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void desactivate() {
/*  84 */     if (this.m_activated) {
/*  85 */       this.m_activated = false;
/*     */       
/*  87 */       WorldManager.getInstance().removeListener(this);
/*  88 */       for (ElementSelection teamStartPointDisplayer : this.m_teamStartPoints) {
/*  89 */         teamStartPointDisplayer.clear();
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
/*     */   public boolean containsTarget(byte teamId, Point3 target) {
/* 104 */     if (this.m_teamStartPoints.size() > teamId && teamId >= 0) {
/* 105 */       return ((ElementSelection)this.m_teamStartPoints.get(teamId)).contains(target);
/*     */     }
/* 107 */     m_logger.trace("teamId invalid " + teamId);
/* 108 */     return false;
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
/*     */   public void add(byte teamId, Point3 target) {
/* 120 */     if (this.m_teamStartPoints.size() > teamId && teamId >= 0) {
/* 121 */       ((ElementSelection)this.m_teamStartPoints.get(teamId)).add(target);
/*     */     } else {
/* 123 */       m_logger.trace("teamId invalid " + teamId);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCells(WorldCell[][] cells) {
/* 134 */     if (cells != null) {
/* 135 */       for (int i = 0; i < cells.length; i++) {
/* 136 */         for (int j = 0; j < (cells[i]).length; j++) {
/*     */           
/* 138 */           ArrayList<WorldElement> customCellElements = cells[i][j].getCustomElement();
/* 139 */           int cellX = cells[i][j].getX();
/* 140 */           int cellY = cells[i][j].getY();
/*     */           
/* 142 */           for (ElementSelection teamStartPointDisplayer : this.m_teamStartPoints) {
/* 143 */             teamStartPointDisplayer.removeAt(cellX, cellY);
/*     */           }
/*     */           
/* 146 */           for (WorldElement element : customCellElements) {
/* 147 */             if (element.getElement().getType() == 1000) {
/* 148 */               byte teamId = FightStartPointElement.getTeamId(element);
/* 149 */               add(teamId, new Point3(cellX, cellY, (short)(int)(element.getAltitude() + element.getHeight())));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 154 */       refreshHighlight();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeCells(WorldCell[][] cells) {
/* 165 */     if (cells != null) {
/* 166 */       for (ElementSelection teamStartPointDisplayer : this.m_teamStartPoints) {
/*     */         
/* 168 */         for (int i = 0; i < cells.length; i++) {
/* 169 */           for (int j = 0; j < (cells[i]).length; j++) {
/* 170 */             WorldCell cell = cells[i][j];
/* 171 */             if (cell != null)
/* 172 */               teamStartPointDisplayer.removeAt(cell.getX(), cell.getY()); 
/*     */           } 
/*     */         } 
/*     */       } 
/* 176 */       refreshHighlight();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onResourceContextReloaded(ResourceContext resourceContexts) {
/* 186 */     WorldMap map = (WorldMap)resourceContexts.getResource();
/* 187 */     addCells(map.getCells());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUnloadResourceContext(ResourceContext resourceContexts) {
/* 196 */     WorldMap map = (WorldMap)resourceContexts.getResource();
/* 197 */     removeCells(map.getCells());
/*     */   }
/*     */   
/*     */   private void refreshHighlight() {
/* 201 */     AleaWorldScene scene = DofusArenaClientInstance.getInstance().getWorldScene();
/*     */     
/* 203 */     for (ElementSelection teamStartPointDisplayer : this.m_teamStartPoints) {
/* 204 */       teamStartPointDisplayer.refreshDisplay(scene);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLayerName(int id) {
/* 215 */     return "startPoint" + id;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\alea\highlightingCells\StartPointManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */