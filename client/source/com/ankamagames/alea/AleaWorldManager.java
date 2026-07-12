/*     */ package com.ankamagames.alea;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.core.resource.ContextFactory;
/*     */ import com.ankamagames.framework.kernel.core.resource.ManageableResource;
/*     */ import com.ankamagames.framework.kernel.core.resource.ResourceContext;
/*     */ import com.ankamagames.framework.kernel.core.resource.ResourceFactory;
/*     */ import com.ankamagames.framework.kernel.core.resource.SingleResourceManager;
/*     */ import com.ankamagames.framework.struct.space.Partition;
/*     */ import gnu.trove.TLongObjectHashMap;
/*     */ import java.util.ArrayList;
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
/*     */ public abstract class AleaWorldManager
/*     */   extends SingleResourceManager
/*     */   implements Partition
/*     */ {
/*  26 */   protected int m_mapSize = 18;
/*  27 */   protected TLongObjectHashMap<AleaWorldMap> m_worldMaps = new TLongObjectHashMap();
/*     */   
/*     */   protected AleaDocumentAccessor m_documentAccessor;
/*     */   
/*  31 */   protected List<AleaWorldMap> m_toTag = new ArrayList<AleaWorldMap>();
/*     */ 
/*     */   
/*     */   protected boolean m_updateBegun = false;
/*     */ 
/*     */ 
/*     */   
/*     */   protected AleaWorldManager(ResourceFactory<? extends AleaWorldMap> partitionFactory, ContextFactory<? extends AleaWorldMapContext> contextFactory, boolean bAutoRelease, AleaDocumentAccessor accessor) {
/*  39 */     super(partitionFactory, contextFactory, bAutoRelease);
/*  40 */     this.m_documentAccessor = accessor;
/*     */   }
/*     */   
/*     */   public int getMapSize() {
/*  44 */     return this.m_mapSize;
/*     */   }
/*     */   
/*     */   public void setMapSize(int mapSize) {
/*  48 */     this.m_mapSize = mapSize;
/*     */   }
/*     */   
/*     */   public TLongObjectHashMap<AleaWorldMap> getWorldMaps() {
/*  52 */     return this.m_worldMaps;
/*     */   }
/*     */   
/*     */   public AleaDocumentAccessor getDocumentAccessor() {
/*  56 */     return this.m_documentAccessor;
/*     */   }
/*     */   
/*     */   public void setDocumentAccessor(AleaDocumentAccessor documentAccessor) {
/*  60 */     this.m_documentAccessor = documentAccessor;
/*     */   }
/*     */   
/*     */   public void beginUpdate() {
/*  64 */     this.m_updateBegun = true;
/*  65 */     this.m_toTag.clear();
/*     */   }
/*     */   
/*     */   public void endUpdate() {
/*  69 */     this.m_updateBegun = false;
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
/*     */   public long getHashIndexFromCellCoord(int cellX, int cellY) {
/*  82 */     int mapX = (int)Math.floor(cellX / this.m_mapSize);
/*  83 */     int mapY = (int)Math.floor(cellY / this.m_mapSize);
/*     */     
/*  85 */     return getHashIndex(mapX, mapY);
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
/*     */   public long getHashIndex(int mapX, int mapY) {
/*  97 */     long ux = mapX & 0xFFFFFFFFL;
/*  98 */     long uy = mapY & 0xFFFFFFFFL;
/*  99 */     return ux << 32L | uy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void tagLoadedMapsInUse() {
/* 105 */     for (AleaWorldMap map : this.m_toTag) {
/* 106 */       tagResourceInUse((ManageableResource)map);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public AleaWorldMap getMapFromHashIndex(long hashIndex) {
/* 112 */     return (AleaWorldMap)this.m_worldMaps.get(hashIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AleaWorldMap getMap(int mapX, int mapY) {
/* 122 */     long hashIndex = getHashIndex(mapX, mapY);
/*     */     
/* 124 */     if (this.m_worldMaps.containsKey(hashIndex)) {
/* 125 */       AleaWorldMap map = (AleaWorldMap)this.m_worldMaps.get(hashIndex);
/* 126 */       if (this.m_updateBegun) {
/* 127 */         if (!this.m_toTag.contains(map)) {
/* 128 */           this.m_toTag.add(map);
/*     */         }
/*     */       } else {
/* 131 */         tagResourceInUse((ManageableResource)map);
/*     */       } 
/* 133 */       return map;
/*     */     } 
/* 135 */     ResourceContext context = getNewResource();
/*     */     
/* 137 */     String mapPath = this.m_documentAccessor.getBasePath();
/* 138 */     String ext = this.m_documentAccessor.getDocumentExtension();
/*     */     
/* 140 */     synchronized (context.getMutex()) {
/* 141 */       AleaWorldMap map = (AleaWorldMap)context.getResource();
/*     */       
/* 143 */       AleaWorldMapContext wmContext = (AleaWorldMapContext)context;
/* 144 */       wmContext.setMapGeometry(mapX, mapY, this.m_mapSize, this.m_mapSize);
/* 145 */       wmContext.setDocumentAccessor(this.m_documentAccessor);
/* 146 */       wmContext.setSourceFileName(String.valueOf(mapPath) + "map_" + mapX + "_" + mapY + ext);
/* 147 */       context.setLastUseFrame(getFrameCounter());
/*     */       
/* 149 */       if (context.isResourceUnloaded()) {
/* 150 */         map.reloadResource(context);
/* 151 */         context.setResourceUnloaded(false);
/* 152 */         onResourceReloaded(context);
/*     */       } 
/* 154 */       this.m_toTag.add(map);
/* 155 */       this.m_worldMaps.put(hashIndex, map);
/* 156 */       return map;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AleaWorldMap getMap(int mapX, int mapY, int width, int height) {
/* 163 */     long hashIndex = getHashIndex(mapX, mapY);
/*     */     
/* 165 */     if (this.m_worldMaps.containsKey(hashIndex)) {
/* 166 */       AleaWorldMap map = (AleaWorldMap)this.m_worldMaps.get(hashIndex);
/* 167 */       if (this.m_updateBegun) {
/* 168 */         if (!this.m_toTag.contains(map)) {
/* 169 */           this.m_toTag.add(map);
/*     */         }
/*     */       } else {
/* 172 */         tagResourceInUse((ManageableResource)map);
/*     */       } 
/* 174 */       return map;
/*     */     } 
/* 176 */     ResourceContext context = getNewResource();
/*     */     
/* 178 */     String mapPath = this.m_documentAccessor.getBasePath();
/* 179 */     String ext = this.m_documentAccessor.getDocumentExtension();
/*     */     
/* 181 */     synchronized (context.getMutex()) {
/* 182 */       AleaWorldMap map = (AleaWorldMap)context.getResource();
/*     */       
/* 184 */       AleaWorldMapContext wmContext = (AleaWorldMapContext)context;
/* 185 */       wmContext.setMapGeometry(mapX, mapY, width, height);
/* 186 */       wmContext.setDocumentAccessor(this.m_documentAccessor);
/* 187 */       wmContext.setSourceFileName(String.valueOf(mapPath) + "map_" + mapX + "_" + mapY + ext);
/* 188 */       context.setLastUseFrame(getFrameCounter());
/*     */       
/* 190 */       if (context.isResourceUnloaded()) {
/* 191 */         map.reloadResource(context);
/* 192 */         context.setResourceUnloaded(false);
/* 193 */         onResourceReloaded(context);
/*     */       } 
/*     */       
/* 196 */       this.m_worldMaps.put(hashIndex, map);
/* 197 */       return map;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void releaseMap(AleaWorldMapContext mapContext) {
/* 203 */     long hashIndex = getHashIndex(mapContext.getX(), mapContext.getY());
/*     */     
/* 205 */     AleaWorldMap map = (AleaWorldMap)this.m_worldMaps.remove(hashIndex);
/* 206 */     releaseResource(mapContext);
/*     */   }
/*     */   
/*     */   public void releaseAllResources() {
/* 210 */     super.releaseAllResources();
/* 211 */     this.m_toTag.clear();
/* 212 */     this.m_worldMaps.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AleaWorldMap getMapFromCellCoordinates(int cellX, int cellY) {
/* 222 */     return getMap((int)Math.floor(cellX / this.m_mapSize), 
/* 223 */         (int)Math.floor(cellY / this.m_mapSize));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AleaWorldCell getWorldCell(int x, int y) {
/* 234 */     AleaWorldMap map = getMapFromCellCoordinates(x, y);
/*     */     
/* 236 */     if (map != null) {
/* 237 */       return (AleaWorldCell)map.getPartitionFromPoint(x, y, 0.0F);
/*     */     }
/* 239 */     return null;
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
/* 253 */     return getWorldCell((int)x, (int)y);
/*     */   }
/*     */   
/*     */   public void removeAllPartitions() {}
/*     */   
/*     */   public void addPartition(Partition subPartition) {}
/*     */   
/*     */   public void removePartition(Partition subPartition) {}
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\alea\AleaWorldManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */