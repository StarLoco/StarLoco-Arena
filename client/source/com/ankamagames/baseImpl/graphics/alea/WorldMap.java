/*     */ package com.ankamagames.baseImpl.graphics.alea;
/*     */ 
/*     */ import com.ankamagames.alea.AleaWorldMap;
/*     */ import com.ankamagames.framework.fileFormat.document.DocumentAccessor;
/*     */ import com.ankamagames.framework.fileFormat.document.DocumentContainer;
/*     */ import com.ankamagames.framework.fileFormat.document.DocumentContainerEventsHandler;
/*     */ import com.ankamagames.framework.fileFormat.document.DocumentEntry;
/*     */ import com.ankamagames.framework.kernel.core.resource.ResourceContext;
/*     */ import com.ankamagames.framework.struct.space.Partition;
/*     */ import java.util.ArrayList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldMap
/*     */   implements AleaWorldMap
/*     */ {
/*     */   private int m_worldX;
/*     */   private int m_worldY;
/*     */   private int m_mapWidth;
/*     */   private int m_mapHeight;
/*     */   private WorldCell[][] m_cells;
/*     */   
/*     */   public void allocateGeometry(int mapX, int mapY, int mapWidth, int mapHeight) {
/*  42 */     releaseGeometry();
/*     */     
/*  44 */     this.m_worldX = mapX;
/*  45 */     this.m_worldY = mapY;
/*  46 */     this.m_mapWidth = mapWidth;
/*  47 */     this.m_mapHeight = mapHeight;
/*     */     
/*  49 */     this.m_cells = new WorldCell[this.m_mapHeight][this.m_mapWidth];
/*     */     
/*  51 */     for (int iy = 0; iy < this.m_mapHeight; iy++) {
/*  52 */       for (int ix = 0; ix < this.m_mapWidth; ix++)
/*  53 */         this.m_cells[ix][iy] = new WorldCell(ix + this.m_worldX * this.m_mapWidth, iy + this.m_worldY * this.m_mapHeight); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getWorldX() {
/*  58 */     return this.m_worldX;
/*     */   }
/*     */   
/*     */   public void setWorldX(int worldX) {
/*  62 */     this.m_worldX = worldX;
/*     */   }
/*     */   
/*     */   public int getWorldY() {
/*  66 */     return this.m_worldY;
/*     */   }
/*     */   
/*     */   public void setWorldY(int worldY) {
/*  70 */     this.m_worldY = worldY;
/*     */   }
/*     */   
/*     */   public int getMapWidth() {
/*  74 */     return this.m_mapWidth;
/*     */   }
/*     */   
/*     */   public void setMapWidth(int mapWidth) {
/*  78 */     this.m_mapWidth = mapWidth;
/*     */   }
/*     */   
/*     */   public int getMapHeight() {
/*  82 */     return this.m_mapHeight;
/*     */   }
/*     */   
/*     */   public void setMapHeight(int mapHeight) {
/*  86 */     this.m_mapHeight = mapHeight;
/*     */   }
/*     */   
/*     */   public WorldCell[][] getCells() {
/*  90 */     return this.m_cells;
/*     */   }
/*     */   
/*     */   public void setCells(WorldCell[][] cells) {
/*  94 */     this.m_cells = cells;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void releaseGeometry() {
/* 101 */     if (this.m_cells != null) {
/* 102 */       for (int iy = 0; iy < this.m_mapHeight; iy++) {
/* 103 */         for (int ix = 0; ix < this.m_mapWidth; ix++)
/* 104 */           this.m_cells[ix][iy] = null; 
/*     */       } 
/* 106 */       this.m_cells = null;
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
/*     */   public Partition getPartitionFromPoint(float x, float y, float z) {
/* 121 */     if (this.m_cells == null) {
/* 122 */       return null;
/*     */     }
/* 124 */     if (this.m_mapWidth == 0 || this.m_mapHeight == 0) {
/* 125 */       return null;
/*     */     }
/* 127 */     int cellX = (int)x - this.m_worldX * this.m_mapWidth;
/* 128 */     int cellY = (int)y - this.m_worldY * this.m_mapHeight;
/*     */     
/* 130 */     if (cellX < 0 || cellX >= this.m_mapWidth || cellY < 0 || cellY >= this.m_mapHeight)
/*     */     {
/* 132 */       return null;
/*     */     }
/*     */     
/* 135 */     return (Partition)this.m_cells[cellX][cellY];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAllPartitions() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPartition(Partition subPartition) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removePartition(Partition subPartition) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reloadResource(ResourceContext resourceContext) {
/* 170 */     WorldMapContext wmContext = (WorldMapContext)resourceContext;
/*     */     
/*     */     try {
/* 173 */       DocumentAccessor accessor = wmContext.getDocumentAccessor();
/*     */ 
/*     */ 
/*     */       
/* 177 */       accessor.open(wmContext.getSourceFileName());
/* 178 */       accessor.read((DocumentContainer)this);
/* 179 */       accessor.close();
/*     */     }
/* 181 */     catch (Exception e) {
/* 182 */       e.printStackTrace();
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
/*     */   
/*     */   public void unloadResource(ResourceContext resourceContext) {
/* 198 */     WorldMapContext wmContext = (WorldMapContext)resourceContext;
/*     */ 
/*     */ 
/*     */     
/* 202 */     this.m_mapWidth = 0;
/* 203 */     this.m_mapHeight = 0;
/* 204 */     releaseGeometry();
/* 205 */     WorldManager.getInstance().releaseMap(wmContext);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long estimateMemoryUsageInBytes() {
/* 214 */     return 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCheckOut() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCheckIn() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DocumentEntry getEntryByName(String name) {
/* 238 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<DocumentEntry> getEntriesByName(String name) {
/* 248 */     return null;
/*     */   }
/*     */   
/*     */   public void addEventsHandler(DocumentContainerEventsHandler handler) {}
/*     */   
/*     */   public void notifyOnLoadBegin() {}
/*     */   
/*     */   public void notifyOnLoadComplete() {}
/*     */   
/*     */   public void notifyOnLoadError(String errorMessage) {}
/*     */   
/*     */   public void notifyOnSaveBegin() {}
/*     */   
/*     */   public void notifyOnSaveComplete() {}
/*     */   
/*     */   public void notifyOnSaveError(String errorMessage) {}
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\WorldMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */