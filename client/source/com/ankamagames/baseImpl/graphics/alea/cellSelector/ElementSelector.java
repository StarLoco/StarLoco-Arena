/*    */ package com.ankamagames.baseImpl.graphics.alea.cellSelector;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphics.alea.WorldCell;
/*    */ import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ElementSelector
/*    */ {
/* 18 */   protected List<WorldElement> m_selectedElements = new ArrayList<WorldElement>();
/*    */ 
/*    */   
/*    */   protected WorldCell m_originCell;
/*    */ 
/*    */   
/*    */   public void clear() {
/* 25 */     this.m_selectedElements.clear();
/*    */   }
/*    */   
/*    */   public void reset(WorldCell originCell) {
/* 29 */     this.m_originCell = originCell;
/* 30 */     this.m_selectedElements.clear();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<WorldElement> getListElement() {
/* 39 */     return this.m_selectedElements;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected abstract void addElementFromCell(WorldCell paramWorldCell);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<WorldElement> getElements(WorldCell cell) {
/* 51 */     this.m_selectedElements.clear();
/* 52 */     addElementFromCell(cell);
/* 53 */     return this.m_selectedElements;
/*    */   }
/*    */   
/*    */   public List<WorldElement> getElements(List<WorldCell> cells) {
/* 57 */     this.m_selectedElements.clear();
/* 58 */     for (WorldCell cell : cells) {
/* 59 */       addElementFromCell(cell);
/*    */     }
/* 61 */     return this.m_selectedElements;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\cellSelector\ElementSelector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */