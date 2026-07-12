/*    */ package com.ankamagames.baseImpl.graphics.alea.cellSelector;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphics.alea.display.AleaWorldScene;
/*    */ import com.ankamagames.framework.graphics.opengl.base.BaseTexture;
/*    */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ElementSelection
/*    */ {
/*    */   private boolean m_needRefresh;
/* 18 */   private CellTargetSelection m_selection = new CellTargetSelection();
/*    */   
/*    */   private CellTargetSelectionDisplayer m_displayer;
/*    */   
/*    */   public ElementSelection(String name, float[] color, BaseTexture texture) {
/* 23 */     this.m_displayer = new CellTargetSelectionDisplayer(name, color, texture);
/*    */   }
/*    */   
/*    */   public ElementSelection(String name, float[] color) {
/* 27 */     this(name, color, null);
/*    */   }
/*    */ 
/*    */   
/*    */   public void add(Point3 target) {
/* 32 */     this.m_selection.addTarget(target);
/* 33 */     this.m_needRefresh = true;
/*    */   }
/*    */   
/*    */   public void remove(Point3 target) {
/* 37 */     this.m_selection.remove(target);
/* 38 */     this.m_needRefresh = true;
/*    */   }
/*    */   
/*    */   public boolean contains(Point3 target) {
/* 42 */     return this.m_selection.contains(target);
/*    */   }
/*    */   
/*    */   public void removeAt(int x, int y) {
/* 46 */     List<Point3> targets = this.m_selection.removeAllAt(x, y);
/* 47 */     this.m_needRefresh = true;
/*    */   }
/*    */   
/*    */   public void clear() {
/* 51 */     this.m_selection.clear();
/* 52 */     this.m_displayer.clear();
/* 53 */     this.m_needRefresh = true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void refreshDisplay(AleaWorldScene scene) {
/* 61 */     if (this.m_needRefresh) {
/* 62 */       this.m_displayer.setTargets(scene, this.m_selection);
/* 63 */       this.m_needRefresh = false;
/*    */     } 
/*    */   }
/*    */   
/*    */   public int getSize() {
/* 68 */     return this.m_selection.count();
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\cellSelector\ElementSelection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */