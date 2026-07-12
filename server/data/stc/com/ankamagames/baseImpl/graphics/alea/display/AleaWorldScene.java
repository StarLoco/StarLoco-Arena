/*     */ package com.ankamagames.baseImpl.graphics.alea.display;
/*     */ 
/*     */ import com.ankamagames.alea.AleaDocumentAccessor;
/*     */ import com.ankamagames.alea.AleaWorldCell;
/*     */ import com.ankamagames.alea.AleaWorldMap;
/*     */ import com.ankamagames.baseImpl.graphics.alea.WorldCell;
/*     */ import com.ankamagames.baseImpl.graphics.alea.WorldGroup;
/*     */ import com.ankamagames.baseImpl.graphics.alea.WorldGroupManager;
/*     */ import com.ankamagames.baseImpl.graphics.alea.WorldManager;
/*     */ import com.ankamagames.baseImpl.graphics.alea.adviser.AdviserManager;
/*     */ import com.ankamagames.baseImpl.graphics.alea.element.GraphicalElement;
/*     */ import com.ankamagames.baseImpl.graphics.alea.element.properties.GraphicalElementProperties;
/*     */ import com.ankamagames.baseImpl.graphics.alea.mobile.Mobile;
/*     */ import com.ankamagames.baseImpl.graphics.alea.mobile.MobileManager;
/*     */ import com.ankamagames.baseImpl.graphics.alea.worldElement.GraphicalWorldElement;
/*     */ import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;
/*     */ import com.ankamagames.framework.graphics.animation.instances.AnimatedObjectControler;
/*     */ import com.ankamagames.framework.graphics.opengl.base.animation.AnimatedObject;
/*     */ import com.ankamagames.framework.graphics.opengl.base.animation.AnimationManager;
/*     */ import com.ankamagames.framework.graphics.opengl.base.animation.AnimationManager.ProcessType;
/*     */ import com.ankamagames.framework.graphics.opengl.base.impl.Mesh2D;
/*     */ import com.ankamagames.framework.graphics.opengl.base.impl.Mesh2DManager;
/*     */ import com.ankamagames.framework.graphics.opengl.base.render.GLObject;
/*     */ import com.ankamagames.framework.graphics.opengl.base.states.DefaultScenePostRenderStates;
/*     */ import com.ankamagames.framework.graphics.opengl.base.states.DefaultScenePreRenderStates;
/*     */ import com.ankamagames.framework.kernel.core.controllers.KeyboardController;
/*     */ import com.ankamagames.framework.kernel.core.controllers.MouseController;
/*     */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*     */ import com.ankamagames.framework.script.JavaFunctionsLibrary;
/*     */ import com.ankamagames.framework.script.LuaManager;
/*     */ import com.ankamagames.framework.sounds.SoundManager;
/*     */ import com.ankamagames.framework.sounds.group.field.FieldGroup;
/*     */ import com.ankamagames.framework.sounds.group.field.FieldSourceController;
/*     */ import com.ankamagames.framework.struct.space.Partition;
/*     */ import com.ankamagames.graphics.isometric.IsoCamera;
/*     */ import com.ankamagames.graphics.isometric.IsoWorldScene;
/*     */ import com.ankamagames.graphics.isometric.IsoWorldTarget;
/*     */ import com.ankamagames.graphics.isometric.RenderProcessHandler;
/*     */ import com.ankamagames.graphics.isometric.highlight.HighLightManager;
/*     */ import com.ankamagames.graphics.isometric.lights.LightManager;
/*     */ import com.ankamagames.graphics.isometric.lines.LinesManager;
/*     */ import com.ankamagames.graphics.isometric.particles.IsoParticleSystemManager;
/*     */ import com.ankamagames.graphics.isometric.tween.TweenManager;
/*     */ import gnu.trove.TIntArrayList;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseWheelEvent;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.media.opengl.GL;
/*     */ import javax.media.opengl.GLAutoDrawable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AleaWorldScene
/*     */   extends IsoWorldScene
/*     */   implements MouseController, KeyboardController, Partition, AnimatedObjectControler
/*     */ {
/*     */   public static final String DEFAULT_GFX_PATH = "contents/gfx";
/*     */   public static final String DEFAULT_SND_PATH = "contents/snd";
/*  68 */   protected String m_gfxPath = "contents/gfx";
/*  69 */   protected String m_sndPath = "contents/snd";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public DisplayedCell[] m_displayedCells;
/*     */   
/*     */ 
/*     */ 
/*     */   private int m_mouseX;
/*     */   
/*     */ 
/*     */ 
/*     */   private int m_mouseY;
/*     */   
/*     */ 
/*     */ 
/*  86 */   private ArrayList<RenderProcessHandler> m_renderProcessHandlers = new ArrayList();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  91 */   private boolean m_forceUpdateDisplayCell = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  97 */   private JavaFunctionsLibrary[] m_animatedObjectActionsFunctionLibraries = null;
/*     */   
/*     */ 
/*     */ 
/*     */   public AleaWorldScene()
/*     */   {
/* 103 */     addRenderProcessHandler(MobileManager.getInstance());
/* 104 */     addRenderProcessHandler(IsoParticleSystemManager.getInstance());
/* 105 */     addRenderProcessHandler(LightManager.getInstance());
/* 106 */     addRenderProcessHandler(HighLightManager.getInstance());
/* 107 */     addRenderProcessHandler(LinesManager.getInstance());
/* 108 */     addRenderProcessHandler(FadeManager.getInstance());
/* 109 */     addRenderProcessHandler(TweenManager.getInstance());
/* 110 */     addRenderProcessHandler(AdviserManager.getInstance());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void initializeCamera()
/*     */   {
/* 120 */     this.m_isoCamera = new AleaIsoCamera(0.0D, 0.0D, 0.0D, this);
/* 121 */     setCamera(this.m_isoCamera);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AleaIsoCamera getIsoCamera()
/*     */   {
/* 131 */     return (AleaIsoCamera)super.getIsoCamera();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setForceUpdateDisplayCell(boolean forceUpdateDisplayCell)
/*     */   {
/* 138 */     this.m_forceUpdateDisplayCell = forceUpdateDisplayCell;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public DisplayedCell[] getDisplayedCells()
/*     */   {
/* 146 */     return this.m_displayedCells;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public DisplayedElement getDisplayedElementAt(Point3 pos)
/*     */   {
/* 154 */     DisplayedElement element = null;
/* 155 */     DisplayedCell[] arrayOfDisplayedCell; int j = (arrayOfDisplayedCell = this.m_displayedCells).length; for (int i = 0; i < j; i++) { DisplayedCell cell = arrayOfDisplayedCell[i];
/* 156 */       if ((cell != null) && (cell.getWorldCell() != null) && (cell.getWorldCell().getX() == pos.getX()) && (cell.getWorldCell().getY() == pos.getY())) {
/* 157 */         for (DisplayedElement displayedElement : cell.getDisplayedElements()) {
/* 158 */           if (displayedElement.getWorldElement().getCoordinates().getZ() == pos.getZ()) {
/* 159 */             element = displayedElement;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 164 */     return element;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setAnimatedObjectActionsFunctionLibraries(JavaFunctionsLibrary[] animatedObjectActionsFunctionLibraries)
/*     */   {
/* 172 */     this.m_animatedObjectActionsFunctionLibraries = animatedObjectActionsFunctionLibraries;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setCellCharacteristics(double cellWidth, double cellHeight, int elevationUnit, int screenCellHeightBonus)
/*     */   {
/* 183 */     this.m_cellWidth = cellWidth;
/* 184 */     this.m_cellHeight = cellHeight;
/* 185 */     this.m_elevationUnit = elevationUnit;
/*     */     
/* 187 */     if (this.m_isoCamera != null) {
/* 188 */       this.m_isoCamera.setBottomScreenCellHeightBonus(screenCellHeightBonus);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void setGfxPath(String gfxPath)
/*     */   {
/* 195 */     this.m_gfxPath = gfxPath;
/* 196 */     AleaTextureManager.getInstance().setGfxPath(gfxPath);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setSndPath(String sndPath)
/*     */   {
/* 203 */     this.m_sndPath = sndPath;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getMouseX()
/*     */   {
/* 210 */     return this.m_mouseX;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getMouseY()
/*     */   {
/* 217 */     return this.m_mouseY;
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
/*     */   public void addSound(String fileName, float x, float y, float range, float rolloff)
/*     */   {
/* 230 */     FieldGroup field = (FieldGroup)SoundManager.getInstance().getGroupByName(fileName);
/*     */     
/* 232 */     if (field == null) {
/* 233 */       field = new FieldGroup(fileName);
/* 234 */       SoundManager.getInstance().addGroup(field);
/* 235 */       field.setMaxGain(0.4F);
/* 236 */       field.createReferences(this.m_sndPath + fileName, true, false, range, rolloff);
/*     */     }
/*     */     
/* 239 */     FieldSourceController controller = new FieldSourceController(x, y, 0.0D);
/* 240 */     field.addEmitter(controller);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addRenderProcessHandler(RenderProcessHandler renderProcessHandler)
/*     */   {
/* 248 */     this.m_renderProcessHandlers.add(renderProcessHandler);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void removeRenderProcessHandler(RenderProcessHandler renderProcessHandler)
/*     */   {
/* 255 */     this.m_renderProcessHandlers.remove(renderProcessHandler);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void uninitialize()
/*     */   {
/* 263 */     clean(true);
/* 264 */     super.uninitialize();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void clean(boolean forceUpdate)
/*     */   {
/* 272 */     MobileManager.getInstance().removeAllMobiles();
/*     */     
/*     */ 
/* 275 */     IsoParticleSystemManager.getInstance().clearParticleSystems();
/*     */     
/*     */ 
/* 278 */     AnimationManager.getInstance().invalidateAllDisplayObjects(this);
/*     */     
/*     */ 
/* 281 */     AdviserManager.getInstance().clear();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 286 */     WorldManager.getInstance().releaseAllResources();
/* 287 */     WorldManager.getInstance().update();
/* 288 */     WorldManager.getInstance().getDocumentAccessor().setBasePath("");
/* 289 */     Mesh2DManager.getInstance().releaseAllResources();
/* 290 */     Mesh2DManager.getInstance().update();
/*     */     
/* 292 */     uninitializeDisplayedCell();
/* 293 */     setForceUpdateDisplayCell(forceUpdate);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void init(GLAutoDrawable glAutoDrawable)
/*     */   {
/* 304 */     super.init(glAutoDrawable);
/*     */     
/*     */ 
/* 307 */     setPreRenderStates(new DefaultScenePreRenderStates());
/* 308 */     setPostRenderStates(new DefaultScenePostRenderStates());
/*     */     
/*     */ 
/*     */ 
/* 312 */     MaterialFactory.getInstance().setFileExtension(".png");
/* 313 */     MaterialFactory.getInstance().setGfxPath(this.m_gfxPath);
/*     */     
/* 315 */     if (this.m_isoCamera != null) {
/* 316 */       this.m_isoCamera.setWidthCellCount((int)Math.ceil(this.m_frustumWidth / getCellWidth()) + 2);
/* 317 */       this.m_isoCamera.setHeightCellCount((int)Math.ceil(this.m_frustumHeight / getCellHeight()));
/*     */       
/* 319 */       initializeDisplayedCell(this.m_isoCamera.getCellCountInView());
/*     */     }
/*     */     
/* 322 */     AnimationManager.getInstance().registerScene(this, this, AnimationManager.ProcessType.AUTO_PROCESS);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void process(long realTime, int frameCount)
/*     */   {
/* 334 */     if (this.m_isoCamera == null) {
/* 335 */       return;
/*     */     }
/*     */     
/* 338 */     for (RenderProcessHandler processHandler : this.m_renderProcessHandlers) {
/* 339 */       processHandler.process(this, realTime, frameCount);
/*     */     }
/*     */     
/*     */ 
/* 343 */     this.m_isoCamera.process(realTime, frameCount);
/*     */     
/* 345 */     int centerScreenIsoWorldX = this.m_isoCamera.getCenterScreenIsoWorldX();
/* 346 */     int centerScreenIsoWorldY = this.m_isoCamera.getCenterScreenIsoWorldY();
/*     */     
/*     */ 
/*     */ 
/* 350 */     updateDisplayedCell(centerScreenIsoWorldX, centerScreenIsoWorldY, this.m_forceUpdateDisplayCell);
/*     */     
/*     */ 
/* 353 */     removeAllChilds();
/*     */     
/*     */ 
/*     */ 
/* 357 */     prepareSceneBeforeRendering(centerScreenIsoWorldX, centerScreenIsoWorldY);
/*     */     
/*     */ 
/* 360 */     sort();
/*     */     
/*     */ 
/* 363 */     super.process(realTime, frameCount);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void display(GL gl)
/*     */   {
/* 372 */     super.display(gl);
/* 373 */     CellMeshFactory.getInstance().rewind();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void initializeDisplayedCell(int numCellsInView)
/*     */   {
/* 380 */     if (numCellsInView > 0)
/*     */     {
/* 382 */       if (this.m_displayedCells != null) { DisplayedCell[] arrayOfDisplayedCell;
/* 383 */         int j = (arrayOfDisplayedCell = this.m_displayedCells).length; for (int i = 0; i < j; i++) { DisplayedCell m_displayedCell = arrayOfDisplayedCell[i];
/* 384 */           if (m_displayedCell != null)
/* 385 */             m_displayedCell.release();
/*     */         }
/*     */       }
/* 388 */       this.m_displayedCells = new DisplayedCell[numCellsInView];
/* 389 */       for (int i = 0; i < this.m_displayedCells.length; i++) {
/* 390 */         this.m_displayedCells[i] = DisplayedCell.checkOut();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void uninitializeDisplayedCell()
/*     */   {
/* 399 */     if (this.m_displayedCells != null) { DisplayedCell[] arrayOfDisplayedCell;
/* 400 */       int j = (arrayOfDisplayedCell = this.m_displayedCells).length; for (int i = 0; i < j; i++) { DisplayedCell m_displayedCell = arrayOfDisplayedCell[i];
/* 401 */         if (m_displayedCell != null)
/* 402 */           m_displayedCell.release();
/*     */       } }
/* 404 */     this.m_displayedCells = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void updateDisplayedCell(int centerScreenIsoWorldX, int centerScreenIsoWorldY, boolean bForceUpdate)
/*     */   {
/* 414 */     AleaIsoCamera isoCamera = getIsoCamera();
/*     */     
/* 416 */     if ((isoCamera.isCameraParametersChanged()) || (bForceUpdate)) {
/* 417 */       this.m_forceUpdateDisplayCell = false;
/*     */       
/* 419 */       int cameraX = 0;
/* 420 */       int cameraY = 0;
/* 421 */       if (getCameraTarget() != null) {
/* 422 */         cameraX = (int)Math.round(getCameraTarget().getWorldX());
/* 423 */         cameraY = (int)Math.round(getCameraTarget().getWorldY());
/*     */       }
/*     */       
/*     */ 
/* 427 */       AleaWorldMap cameraMap = getWorldMapFromCellCoordinates(cameraX, cameraY);
/* 428 */       WorldCell cameraCell = (WorldCell)cameraMap.getPartitionFromPoint(cameraX, cameraY, 0.0F);
/*     */       
/* 430 */       if (cameraCell != null)
/*     */       {
/* 432 */         isoCamera.setCameraGroupInstanceId(0);
/* 433 */         for (GraphicalWorldElement element : cameraCell.getVisualElements())
/*     */         {
/* 435 */           GraphicalElement graphicalElement = element.getElement();
/*     */           
/* 437 */           if ((!graphicalElement.getStateProperties(element.getState()).isMoveBottom()) && (element.getGroupInstanceId() != 0) && (getCameraTarget().getAltitude() >= element.getAltitude())) {
/* 438 */             isoCamera.setCameraGroupInstanceId(element.getGroupInstanceId());
/* 439 */             isoCamera.setCameraGroupLevel(element.getLevel());
/*     */           }
/*     */         }
/*     */         
/* 443 */         FadeManager.getInstance().clearMaskedLayers();
/*     */         
/*     */ 
/*     */ 
/* 447 */         if (isoCamera.getCameraGroupInstanceId() > 0) {
/* 448 */           WorldGroup group = WorldGroupManager.getInstance().getGroupFromInstance(isoCamera.getCameraGroupInstanceId());
/*     */           
/* 450 */           if (group != null) {
/* 451 */             int instanceLevel = WorldGroupManager.getInstance().getLevelFromInstance(isoCamera.getCameraGroupInstanceId());
/*     */             
/* 453 */             TIntArrayList maskedLayers = group.getMaskedLayers(isoCamera.getCameraGroupLevel() - instanceLevel);
/*     */             
/* 455 */             if (maskedLayers != null)
/*     */             {
/*     */ 
/* 458 */               if (maskedLayers.contains(-1)) {
/* 459 */                 resetToDefaultIndoorZoomFactor();
/*     */               }
/* 461 */               for (int i = 0; i < maskedLayers.size(); i++) {
/* 462 */                 int layer = maskedLayers.get(i);
/*     */                 
/* 464 */                 FadeManager.getInstance().addMaskedLayers(layer, isoCamera.getCameraGroupInstanceId());
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 471 */       int isoLocalX = this.m_isoCamera.getIsoLocalX();
/* 472 */       int isoLocalY = this.m_isoCamera.getIsoLocalY();
/* 473 */       int isoOffsetX = this.m_isoCamera.getIsoOffsetX();
/* 474 */       int isoOffsetY = this.m_isoCamera.getIsoOffsetY();
/* 475 */       int heightCellCount = this.m_isoCamera.getHeightCellCount();
/* 476 */       int widthCellCount = this.m_isoCamera.getWidthCellCount();
/* 477 */       int bottomScreenCellHeightBonus = this.m_isoCamera.getBottomScreenCellHeightBonus();
/* 478 */       int topScreenCellHeightBonus = this.m_isoCamera.getTopScreenCellHeightBonus();
/*     */       
/* 480 */       initializeDisplayedCell(this.m_isoCamera.getCellCountInView());
/*     */       
/* 482 */       int cellCount = 0;
/* 483 */       int rowCount = 0;
/* 484 */       int rowPair = 0;
/*     */       
/* 486 */       WorldManager.getInstance().beginUpdate();
/* 487 */       for (int row = -topScreenCellHeightBonus; row < heightCellCount * 2 + bottomScreenCellHeightBonus; row++) {
/* 488 */         for (int col = 0; col < widthCellCount; col++)
/*     */         {
/*     */ 
/* 491 */           int wx = centerScreenIsoWorldX + isoLocalX;
/* 492 */           int wy = centerScreenIsoWorldY + isoLocalY;
/*     */           
/* 494 */           DisplayedCell displayedCell = this.m_displayedCells[cellCount];
/*     */           
/* 496 */           AleaWorldMap map = getWorldMapFromCellCoordinates(wx, wy);
/*     */           
/* 498 */           displayedCell.setWorldCell((WorldCell)map.getPartitionFromPoint(wx, wy, 0.0F));
/*     */           
/* 500 */           displayedCell.setScreenX(isoToScreenX(isoLocalX, isoLocalY));
/* 501 */           displayedCell.setScreenY(isoToScreenY(isoLocalX, isoLocalY));
/*     */           
/* 503 */           displayedCell.updateDisplayedElements(this, row, heightCellCount, centerScreenIsoWorldX, centerScreenIsoWorldY);
/*     */           
/* 505 */           isoLocalX++;
/* 506 */           isoLocalY--;
/*     */           
/* 508 */           cellCount++;
/*     */         }
/*     */         
/* 511 */         rowPair = rowPair == 0 ? 1 : 0;
/* 512 */         rowCount++;
/*     */         
/* 514 */         isoLocalY = isoOffsetY + rowCount / 2;
/* 515 */         isoLocalX = isoOffsetX + rowCount / 2 + rowPair;
/*     */       }
/* 517 */       WorldManager.getInstance().endUpdate();
/*     */     }
/*     */     
/*     */ 
/* 521 */     WorldManager.getInstance().tagLoadedMapsInUse();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract void addSpecialEffectToMesh(int paramInt, Mesh2D paramMesh2D);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void prepareSceneBeforeRendering(int centerScreenIsoWorldX, int centerScreenIsoWorldY)
/*     */   {
/* 540 */     for (RenderProcessHandler processHandler : this.m_renderProcessHandlers) {
/* 541 */       processHandler.prepareBeforeRendering(this, centerScreenIsoWorldX, centerScreenIsoWorldY);
/*     */     }
/*     */     
/*     */ 
/* 545 */     if (this.m_displayedCells != null) { DisplayedCell[] arrayOfDisplayedCell;
/* 546 */       int j = (arrayOfDisplayedCell = this.m_displayedCells).length; for (int i = 0; i < j; i++) { DisplayedCell cell = arrayOfDisplayedCell[i];
/* 547 */         cell.prepareCellBeforeRendering(this);
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
/*     */ 
/*     */   public ArrayList<DisplayedElement> getDisplayedElementsUnderMousePoint(double mouseX, double mouseY, DisplayedElementComparator comparator)
/*     */   {
/* 565 */     if ((this.m_isoCamera == null) || (this.m_displayedCells == null)) {
/* 566 */       return null;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 571 */     double adjustedMouseX = (mouseX - this.m_frustumWidth / 2.0D) / this.m_isoCamera.getZoomFactor() + this.m_isoCamera.getCameraDeltaScreenX();
/* 572 */     double adjustedMouseY = (this.m_frustumHeight / 2.0D - mouseY) / this.m_isoCamera.getZoomFactor() + this.m_isoCamera.getCameraDeltaScreenY();
/*     */     
/*     */ 
/* 575 */     ArrayList<DisplayedElement> hitElements = new ArrayList();
/*     */     DisplayedCell[] arrayOfDisplayedCell;
/* 577 */     int j = (arrayOfDisplayedCell = this.m_displayedCells).length; for (int i = 0; i < j; i++) { DisplayedCell cell = arrayOfDisplayedCell[i];
/* 578 */       ArrayList<DisplayedElement> cellHitElements = cell.getDisplayedElementsUnderPoint(adjustedMouseX, adjustedMouseY);
/* 579 */       if (cellHitElements.size() != 0) {
/* 580 */         for (DisplayedElement displayedElement : cellHitElements)
/*     */         {
/*     */ 
/* 583 */           displayedElement.calculateDistanceFromTopToMouse(adjustedMouseX, adjustedMouseY);
/*     */           
/* 585 */           hitElements.add(displayedElement);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 591 */     if (comparator.getComparator() != null) {
/* 592 */       Collections.sort(hitElements, comparator.getComparator());
/*     */     }
/*     */     
/* 595 */     return hitElements;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayList<Mobile> getMobilesUnderMousePoint(double mouseX, double mouseY)
/*     */   {
/* 607 */     if (this.m_isoCamera == null) {
/* 608 */       return null;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 613 */     float adjustedMouseX = (float)(mouseX - this.m_frustumWidth / 2.0F) / (float)this.m_isoCamera.getZoomFactor();
/* 614 */     float adjustedMouseY = (float)(this.m_frustumHeight / 2.0F - mouseY) / (float)this.m_isoCamera.getZoomFactor();
/*     */     
/* 616 */     return MobileManager.getInstance().getMobilesUnderPoint(adjustedMouseX, adjustedMouseY);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int selectMobilesUnderMousePoint(double mouseX, double mouseY)
/*     */   {
/* 628 */     if (this.m_isoCamera == null) {
/* 629 */       return 0;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 635 */     float adjustedMouseX = (float)(mouseX - this.m_frustumWidth / 2.0F) / (float)this.m_isoCamera.getZoomFactor();
/* 636 */     float adjustedMouseY = (float)(this.m_frustumHeight / 2.0F - mouseY) / (float)this.m_isoCamera.getZoomFactor();
/*     */     
/* 638 */     return MobileManager.getInstance().selectMobilesUnderPoint(adjustedMouseX, adjustedMouseY);
/*     */   }
/*     */   
/*     */ 
/* 642 */   private static float DEFAULT_OUTDOOR_ZOOM_FACTOR = 1.1F;
/* 643 */   protected static float DEFAULT_INDOOR_ZOOM_FACTOR = 1.4F;
/*     */   
/* 645 */   private float m_outdoorZoomFactor = DEFAULT_OUTDOOR_ZOOM_FACTOR;
/* 646 */   private float m_indoorZoomFactor = DEFAULT_INDOOR_ZOOM_FACTOR;
/*     */   
/*     */   public float getOutdoorZoomFactor() {
/* 649 */     return this.m_outdoorZoomFactor;
/*     */   }
/*     */   
/*     */   public float getIndoorZoomFactor() {
/* 653 */     return this.m_indoorZoomFactor;
/*     */   }
/*     */   
/*     */   public void setOutdoorZoomFactor(float outdoorZoomFactor) {
/* 657 */     this.m_outdoorZoomFactor = outdoorZoomFactor;
/*     */   }
/*     */   
/*     */   public void setIndoorZoomFactor(float indoorZoomFactor) {
/* 661 */     this.m_indoorZoomFactor = indoorZoomFactor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void resetToDefaultOutdoorZoomFactor()
/*     */   {
/* 668 */     if (this.m_isoCamera != null) {
/* 669 */       this.m_isoCamera.setDesiredZoomFactor(this.m_outdoorZoomFactor);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void resetToDefaultIndoorZoomFactor()
/*     */   {
/* 676 */     if (this.m_isoCamera != null) {
/* 677 */       this.m_isoCamera.setDesiredZoomFactor(this.m_indoorZoomFactor);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onAnimatedObjectActionFlag(List<String> actions)
/*     */   {
/* 687 */     for (String action : actions) {
/* 688 */       LuaManager.getInstance().runCommand(action, this.m_animatedObjectActionsFunctionLibraries, true);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onAnimatedObjectInvalidate(AnimatedObject displayObject) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract boolean mouseClicked(MouseEvent paramMouseEvent);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract boolean mousePressed(MouseEvent paramMouseEvent);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract boolean mouseReleased(MouseEvent paramMouseEvent);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract boolean mouseEntered(MouseEvent paramMouseEvent);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract boolean mouseExited(MouseEvent paramMouseEvent);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract boolean mouseDragged(MouseEvent paramMouseEvent);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean mouseMoved(MouseEvent mouseEvent)
/*     */   {
/* 749 */     this.m_mouseX = mouseEvent.getX();
/* 750 */     this.m_mouseY = mouseEvent.getY();
/* 751 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract boolean mouseWheelMoved(MouseWheelEvent paramMouseWheelEvent);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract boolean keyTyped(KeyEvent paramKeyEvent);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract boolean keyPressed(KeyEvent paramKeyEvent);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract boolean keyReleased(KeyEvent paramKeyEvent);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AleaWorldMap getWorldMapFromCellCoordinates(int x, int y)
/*     */   {
/* 789 */     return WorldManager.getInstance().getMapFromCellCoordinates(x, y);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AleaWorldCell getWorldCell(int x, int y)
/*     */   {
/* 799 */     AleaWorldMap map = getWorldMapFromCellCoordinates(x, y);
/* 800 */     return (AleaWorldCell)map.getPartitionFromPoint(x, y, 0.0F);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Partition getPartitionFromPoint(float x, float y, float z)
/*     */   {
/* 812 */     return getWorldCell((int)x, (int)y);
/*     */   }
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
/*     */   public void addPartition(Partition subPartition) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removePartition(Partition subPartition) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Partition getWorld()
/*     */   {
/* 836 */     return WorldManager.getInstance();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 847 */     int surface = 0;
/* 848 */     for (GLObject mesh : this.m_children) {
/* 849 */       if ((mesh instanceof Mesh2D)) {
/* 850 */         surface = (int)(surface + ((Mesh2D)mesh).getWidth() * ((Mesh2D)mesh).getHeight());
/*     */       }
/*     */     }
/*     */     
/* 854 */     float screenCount = surface / 786432.0F;
/*     */     
/* 856 */     return surface + " px, " + screenCount + " écrans, " + "zoom=" + this.m_isoCamera.getZoomFactor() + ", " + super.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\display\AleaWorldScene.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */