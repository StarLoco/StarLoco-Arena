/*     */ package com.ankamagames.baseImpl.graphics.alea.utils;
/*     */ 
/*     */ import com.ankamagames.baseImpl.graphics.alea.WorldCell;
/*     */ import com.ankamagames.baseImpl.graphics.alea.WorldManager;
/*     */ import com.ankamagames.baseImpl.graphics.alea.display.AleaWorldScene;
/*     */ import com.ankamagames.baseImpl.graphics.alea.display.DisplayedElement;
/*     */ import com.ankamagames.baseImpl.graphics.alea.display.DisplayedElementComparator;
/*     */ import com.ankamagames.baseImpl.graphics.alea.mobile.Mobile;
/*     */ import com.ankamagames.baseImpl.graphics.alea.mobile.PathMobile;
/*     */ import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;
/*     */ import com.ankamagames.framework.ai.dataProvider.CellInformationProvider;
/*     */ import com.ankamagames.framework.ai.pathfinder.PathFindMover;
/*     */ import com.ankamagames.framework.ai.pathfinder.PathFindParameters;
/*     */ import com.ankamagames.framework.ai.pathfinder.PathFindResult;
/*     */ import com.ankamagames.framework.ai.pathfinder.PathFinder;
/*     */ import com.ankamagames.framework.kernel.core.maths.Point3;
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
/*     */ public class WorldSceneInteractionUtils
/*     */ {
/*  30 */   public static int DEFAULT_PATHFIND_MAX_ITERATION = 2000;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum PathSolutionCriterions
/*     */   {
/*  37 */     SMALLEST,
/*     */     
/*  39 */     NEAREST_UP_FACE;
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
/*     */   public static PathFindResult getPathSolutionFromMouseCoordinates(AleaWorldScene worldScene, PathMobile pathMobile, int mouseX, int mouseY, PathSolutionCriterions critrions, PathFindParameters pathFindParameters) {
/*     */     ArrayList<DisplayedElement> hitElements;
/*  54 */     switch (critrions) {
/*     */ 
/*     */ 
/*     */       
/*     */       case SMALLEST:
/*  59 */         hitElements = worldScene.getDisplayedElementsUnderMousePoint(mouseX, mouseY, null);
/*     */         
/*  61 */         hitElements.size();
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case null:
/*  71 */         hitElements = worldScene.getDisplayedElementsUnderMousePoint(mouseX, mouseY, DisplayedElementComparator.MOUSE_DISTANCE_COMPARATOR);
/*     */         
/*  73 */         if (hitElements != null && hitElements.size() != 0) {
/*     */           
/*  75 */           int displayElementIndex = 0;
/*  76 */           while (displayElementIndex < hitElements.size()) {
/*     */             
/*  78 */             DisplayedElement displayedElement = hitElements.get(displayElementIndex);
/*  79 */             WorldElement worldElement = displayedElement.getWorldElement();
/*  80 */             WorldCell worldCell = displayedElement.getDisplayedCell().getWorldCell();
/*     */ 
/*     */             
/*  83 */             int destX = worldCell.getX();
/*  84 */             int destY = worldCell.getY();
/*  85 */             short destAltitude = (short)(int)(displayedElement.getAltitude() + worldElement.getHeight());
/*     */ 
/*     */             
/*  88 */             PathFinder pathFinder = PathFinder.checkOut();
/*     */             
/*  90 */             Point3 from = new Point3(pathMobile.getDestinationWorldX(), pathMobile.getDestinationWorldY(), (short)(int)pathMobile.getAltitude());
/*  91 */             Point3 to = new Point3(destX, destY, destAltitude);
/*  92 */             PathFindResult currentPathResult = pathFinder.compute((PathFindMover)pathMobile, (CellInformationProvider)WorldManager.getInstance(), from, to, pathFindParameters);
/*  93 */             pathFinder.release();
/*     */             
/*  95 */             if (currentPathResult.isPathFound()) {
/*  96 */               return currentPathResult;
/*     */             }
/*  98 */             displayElementIndex++;
/*     */           } 
/*     */         } 
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 105 */     return null;
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
/*     */   public static Point3 getNearestPoint3(AleaWorldScene worldScene, int mouseX, int mouseY, boolean mobileSelectable) {
/* 121 */     if (mobileSelectable) {
/*     */       
/* 123 */       ArrayList<Mobile> hitMobiles = worldScene.getMobilesUnderMousePoint(mouseX, mouseY);
/* 124 */       if (hitMobiles.size() != 0) {
/* 125 */         Mobile mobile = hitMobiles.get(0);
/* 126 */         if (mobile != null) {
/* 127 */           return mobile.getWorldCoordinates();
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 134 */     ArrayList<DisplayedElement> hitElements = worldScene.getDisplayedElementsUnderMousePoint(mouseX, mouseY, DisplayedElementComparator.MOUSE_DISTANCE_COMPARATOR);
/*     */     
/* 136 */     DisplayedElement elt = (hitElements != null && hitElements.size() != 0) ? hitElements.get(0) : null;
/* 137 */     return (elt != null) ? elt.getCoordinates() : null;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\ale\\utils\WorldSceneInteractionUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */