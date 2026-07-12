/*     */ package com.ankamagames.baseImpl.graphics.alea;
/*     */ 
/*     */ import com.ankamagames.alea.AleaDocumentAccessor;
/*     */ import com.ankamagames.alea.AleaWorldMap;
/*     */ import com.ankamagames.baseImpl.graphics.alea.element.BasicElement;
/*     */ import com.ankamagames.baseImpl.graphics.alea.element.BrightnessElement;
/*     */ import com.ankamagames.baseImpl.graphics.alea.element.GraphicalElement;
/*     */ import com.ankamagames.baseImpl.graphics.alea.element.OffsetElement;
/*     */ import com.ankamagames.baseImpl.graphics.alea.element.ParticleElement;
/*     */ import com.ankamagames.baseImpl.graphics.alea.element.TeintElement;
/*     */ import com.ankamagames.baseImpl.graphics.alea.worldElement.GraphicalWorldElement;
/*     */ import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;
/*     */ import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElementFactory;
/*     */ import com.ankamagames.framework.fileFormat.document.DocumentContainer;
/*     */ import com.ankamagames.graphics.isometric.particles.CellParticleSystem;
/*     */ import com.ankamagames.graphics.isometric.particles.IsoParticleSystemFactory;
/*     */ import com.ankamagames.graphics.isometric.particles.IsoParticleSystemManager;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldMapDocumentAccessor
/*     */   extends AleaDocumentAccessor
/*     */ {
/*     */   private boolean m_particleActivated;
/*     */   private CustomElementProcessor m_customElementHandler;
/*     */   
/*     */   public boolean getParticleActivated() {
/*  36 */     return this.m_particleActivated;
/*     */   }
/*     */   
/*     */   public void setParticleActivated(boolean particleActivated) {
/*  40 */     this.m_particleActivated = particleActivated;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldMapDocumentAccessor() {
/*  47 */     setBasePath("contents/data/maps");
/*  48 */     setDocumentExtension(".amw");
/*  49 */     setAleaDocumentTypeCode((byte)77);
/*  50 */     setAleaDocumentVersion((byte)1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void open(String fileName) throws Exception {
/*     */     try {
/*  62 */       super.open(fileName);
/*  63 */     } catch (Exception exception) {}
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
/*     */   public void read(DocumentContainer container) {
/*  75 */     super.read(container);
/*     */     
/*  77 */     if (this.m_streamBuffer == null || container == null) {
/*     */       return;
/*     */     }
/*  80 */     AleaWorldMap map = (AleaWorldMap)container;
/*     */     
/*  82 */     int coordX = this.m_streamBuffer.getInt();
/*  83 */     int coordY = this.m_streamBuffer.getInt();
/*  84 */     int size = this.m_streamBuffer.get();
/*  85 */     int cellsCount = size * size;
/*  86 */     int coordCellX = coordX * size;
/*  87 */     int coordCellY = coordY * size;
/*     */     
/*  89 */     map.allocateGeometry(coordX, coordY, size, size);
/*     */     
/*  91 */     for (int j = 0; j < cellsCount; j++) {
/*  92 */       int worldCellX = coordCellX + j % size;
/*  93 */       int worldCellY = coordCellY + j / size;
/*     */       
/*  95 */       WorldCell cell = (WorldCell)map.getPartitionFromPoint(worldCellX, worldCellY, 0.0F);
/*     */       
/*  97 */       if (cell != null) {
/*  98 */         readCellDatas(cell);
/*     */       } else {
/* 100 */         map.notifyOnLoadError("Bad cell coordinates (" + worldCellX + "; " + worldCellY + ")");
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 105 */     map.notifyOnLoadComplete();
/*     */   }
/*     */ 
/*     */   
/*     */   private void readCellDatas(WorldCell cell) {
/* 110 */     int levelCount = this.m_streamBuffer.get();
/* 111 */     ArrayList[] levels = new ArrayList[levelCount];
/*     */     
/* 113 */     for (int levelIndex = 0; levelIndex < levelCount; levelIndex++) {
/* 114 */       int elementCount = this.m_streamBuffer.get();
/*     */       
/* 116 */       if (elementCount > 0) {
/* 117 */         levels[levelIndex] = new ArrayList();
/*     */       }
/* 119 */       for (int elementIndex = 0; elementIndex < elementCount; elementIndex++) {
/* 120 */         byte[] savedParam; int elementId = this.m_streamBuffer.getInt();
/* 121 */         byte state = this.m_streamBuffer.get();
/*     */         
/* 123 */         int cellInstanceGroupId = this.m_streamBuffer.getInt();
/*     */         
/* 125 */         byte paramCount = this.m_streamBuffer.get();
/*     */ 
/*     */ 
/*     */         
/* 129 */         if (paramCount > 0) {
/* 130 */           ByteBuffer byteParam = ByteBuffer.allocate(32);
/*     */           
/* 132 */           for (int m = 0; m < paramCount; m++) {
/* 133 */             byte type = this.m_streamBuffer.get();
/* 134 */             byteParam.put(type);
/*     */             
/* 136 */             int typeSize = m_typesSize[type];
/*     */             
/* 138 */             for (int t = 0; t < typeSize; t++) {
/* 139 */               byte param = this.m_streamBuffer.get();
/* 140 */               byteParam.put(param);
/*     */             } 
/*     */           } 
/*     */           
/* 144 */           savedParam = new byte[byteParam.position()];
/* 145 */           System.arraycopy(byteParam.array(), 0, savedParam, 0, byteParam.position());
/*     */         } else {
/*     */           
/* 148 */           savedParam = new byte[0];
/*     */         } 
/*     */         
/* 151 */         BasicElement element = WorldElementManager.getInstance().getElement(elementId);
/*     */ 
/*     */ 
/*     */         
/* 155 */         if (element != null) {
/* 156 */           if (element.getType() == 6) {
/* 157 */             WorldGroupManager.getInstance().registerInstanceGroupInformation(cellInstanceGroupId, savedParam, levelIndex);
/*     */           } else {
/* 159 */             levels[levelIndex].add(WorldElementFactory.create(element, paramCount, savedParam, state, cellInstanceGroupId));
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 167 */     if (cell == null || levels == null) {
/*     */       return;
/*     */     }
/*     */     
/* 171 */     float minOrderPossibleAltitude = 0.0F;
/*     */ 
/*     */     
/* 174 */     byte currentAltitude = 0;
/* 175 */     byte oldLevelAltitude = 0;
/* 176 */     float possibleAltitudePad = 1.0E-4F;
/*     */     
/* 178 */     for (int level = 0; level < levels.length; level++) {
/* 179 */       byte levelAltitude = currentAltitude;
/*     */       
/* 181 */       ArrayList<WorldElement> levelElements = levels[level];
/*     */       
/* 183 */       if (levelElements == null) {
/* 184 */         oldLevelAltitude = levelAltitude;
/*     */       }
/*     */       else {
/*     */         
/* 188 */         boolean isLevelPiled = true;
/*     */         
/* 190 */         for (WorldElement element : levelElements) {
/* 191 */           if (element.getElement().getType() == 8) {
/* 192 */             isLevelPiled = false;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 197 */         if (!isLevelPiled) {
/* 198 */           levelAltitude = currentAltitude = oldLevelAltitude;
/*     */         }
/* 200 */         float currentBrightness = 0.0F;
/* 201 */         float[] currentTeint = (float[])null;
/*     */         
/* 203 */         for (WorldElement element : levelElements) {
/*     */           GraphicalElement graphicalElement; boolean elementPiled; byte elementAltitude; float elementHeight, f1, f2;
/* 205 */           switch (element.getElement().getType()) {
/*     */             
/*     */             case 4:
/* 208 */               if (OffsetElement.isAbsolute(element)) {
/* 209 */                 currentAltitude = OffsetElement.getOffset(element);
/*     */               } else {
/* 211 */                 currentAltitude = (byte)(currentAltitude + OffsetElement.getOffset(element));
/*     */               } 
/* 213 */               levelAltitude = currentAltitude;
/*     */               continue;
/*     */             
/*     */             case 3:
/* 217 */               currentTeint = TeintElement.getTeint(element);
/*     */               continue;
/*     */             
/*     */             case 10:
/* 221 */               currentBrightness = (float)BrightnessElement.getBrightness(element);
/*     */               continue;
/*     */             
/*     */             case 2:
/* 225 */               graphicalElement = (GraphicalElement)element.getElement();
/*     */               
/* 227 */               elementPiled = graphicalElement.getStateProperties(element.getState()).isPiled();
/* 228 */               elementAltitude = elementPiled ? currentAltitude : levelAltitude;
/* 229 */               elementHeight = graphicalElement.getStateProperties(element.getState()).getHeight();
/*     */               
/* 231 */               f1 = elementAltitude + elementHeight;
/* 232 */               f2 = (minOrderPossibleAltitude > f1) ? (minOrderPossibleAltitude + possibleAltitudePad) : f1;
/*     */               
/* 234 */               element.addPrecalculatedInformations(level, elementAltitude, currentBrightness, currentTeint, f2);
/* 235 */               element.setCoordinates(cell.getX(), cell.getY(), elementAltitude);
/* 236 */               cell.addVisualElement((GraphicalWorldElement)element);
/*     */               
/* 238 */               if (elementPiled) {
/* 239 */                 currentAltitude = (byte)(int)(currentAltitude + elementHeight);
/*     */               }
/* 241 */               minOrderPossibleAltitude = f2;
/*     */               continue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             case 6:
/*     */             case 8:
/*     */             case 1:
/*     */               continue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             case 9:
/* 258 */               if (this.m_particleActivated) {
/* 259 */                 int id = ParticleElement.getParticleFileId(element);
/* 260 */                 CellParticleSystem particleSystem = IsoParticleSystemFactory.getInstance().getCellParticleSystem(id);
/*     */                 
/* 262 */                 if (particleSystem != null) {
/*     */                   
/* 264 */                   particleSystem.setX(cell.getX());
/* 265 */                   particleSystem.setY(cell.getY());
/* 266 */                   particleSystem.setZ(currentAltitude);
/* 267 */                   particleSystem.setLevel(level);
/*     */                   
/* 269 */                   IsoParticleSystemManager.getInstance().addCellParticleSystem(particleSystem);
/*     */                 } 
/*     */               } 
/*     */               continue;
/*     */           } 
/*     */ 
/*     */           
/* 276 */           float elementTopAltitude = currentAltitude + (float)element.getHeight();
/* 277 */           float zCellOrderAltitude = (minOrderPossibleAltitude > elementTopAltitude) ? (minOrderPossibleAltitude + possibleAltitudePad) : elementTopAltitude;
/*     */           
/* 279 */           element.addPrecalculatedInformations(level, currentAltitude, currentBrightness, currentTeint, zCellOrderAltitude);
/* 280 */           element.setCoordinates(cell.getX(), cell.getY(), currentAltitude);
/* 281 */           cell.addCustomElement(element);
/* 282 */           if (this.m_customElementHandler != null)
/*     */           {
/* 284 */             this.m_customElementHandler.onReadCell(cell, element, (ArrayList<WorldElement>[])levels);
/*     */           }
/*     */           
/* 287 */           minOrderPossibleAltitude = zCellOrderAltitude;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 293 */         if (isLevelPiled)
/* 294 */           oldLevelAltitude = levelAltitude; 
/*     */       } 
/*     */     } 
/* 297 */     Collections.sort(cell.getVisualElements());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DocumentContainer getNewDocumentContainer() {
/* 308 */     return (DocumentContainer)new WorldMap();
/*     */   }
/*     */ 
/*     */   
/*     */   public CustomElementProcessor getCustomElementHandler() {
/* 313 */     return this.m_customElementHandler;
/*     */   }
/*     */   
/*     */   public void setCustomElementHandler(CustomElementProcessor customElementHandler) {
/* 317 */     this.m_customElementHandler = customElementHandler;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\WorldMapDocumentAccessor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */