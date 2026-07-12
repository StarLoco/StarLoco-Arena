/*    */ package com.ankamagames.baseImpl.graphics.alea.cellSelector;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphics.alea.WorldCell;
/*    */ import com.ankamagames.baseImpl.graphics.alea.display.AleaWorldScene;
/*    */ import com.ankamagames.baseImpl.graphics.alea.display.DisplayedCell;
/*    */ import com.ankamagames.framework.graphics.opengl.base.BaseTexture;
/*    */ import com.ankamagames.framework.graphics.opengl.base.material.Material;
/*    */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*    */ import com.ankamagames.graphics.isometric.highlight.HighLightLayer;
/*    */ import com.ankamagames.graphics.isometric.highlight.HighLightManager;
/*    */ import com.ankamagames.graphics.isometric.highlight.UniqueHandleReference;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CellTargetSelectionDisplayer
/*    */ {
/*    */   private String m_name;
/*    */   
/*    */   public CellTargetSelectionDisplayer(String name, float[] color, BaseTexture texture)
/*    */   {
/* 27 */     this.m_name = name;
/*    */     try
/*    */     {
/* 30 */       if (texture == null) {
/* 31 */         HighLightManager.getInstance().createLayer(this.m_name);
/*    */       } else {
/* 33 */         HighLightManager.getInstance().createLayer(this.m_name, texture);
/*    */       }
/*    */       
/* 36 */       Material highlightColor = HighLightManager.getInstance().getLayer(this.m_name).getMaterial();
/* 37 */       highlightColor.setDiffuse(color);
/* 38 */       highlightColor.setUseDiffuse(true);
/*    */     } catch (Exception e) {
/* 40 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */   
/*    */   public CellTargetSelectionDisplayer(String name, float[] color) {
/* 45 */     this(name, color, null);
/*    */   }
/*    */   
/*    */   public void clear() {
/* 49 */     HighLightManager.getInstance().clearLayer(this.m_name);
/*    */   }
/*    */   
/*    */   public void setTargets(AleaWorldScene scene, CellTargetSelection cellTargetSelection) {
/* 53 */     HighLightManager.getInstance().clearLayer(this.m_name);
/*    */     
/* 55 */     DisplayedCell[] displayedCells = scene.getDisplayedCells();
/*    */     
/* 57 */     if (displayedCells != null) { DisplayedCell[] arrayOfDisplayedCell1;
/* 58 */       int j = (arrayOfDisplayedCell1 = displayedCells).length; for (int i = 0; i < j; i++) { DisplayedCell cell = arrayOfDisplayedCell1[i];
/* 59 */         WorldCell worldCell = cell.getWorldCell();
/* 60 */         if (worldCell != null) {
/* 61 */           List<Point3> targets = cellTargetSelection.getTargets(worldCell.getX(), worldCell.getY());
/* 62 */           if (targets != null) {
/* 63 */             for (Point3 target : targets) {
/* 64 */               UniqueHandleReference ref = worldCell.getElementWithTopAtAltitude(target.getZ());
/* 65 */               if (ref != null) {
/* 66 */                 HighLightManager.getInstance().add(ref, this.m_name);
/*    */               }
/*    */             }
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public void remove(AleaWorldScene scene, DisplayedCell cell, List<Point3> targets) {
/* 76 */     WorldCell worldCell = cell.getWorldCell();
/*    */     
/* 78 */     for (Point3 target : targets) {
/* 79 */       UniqueHandleReference ref = worldCell.getElementWithTopAtAltitude(target.getZ());
/* 80 */       if (ref != null) {
/* 81 */         HighLightManager.getInstance().remove(ref, this.m_name);
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\cellSelector\CellTargetSelectionDisplayer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */