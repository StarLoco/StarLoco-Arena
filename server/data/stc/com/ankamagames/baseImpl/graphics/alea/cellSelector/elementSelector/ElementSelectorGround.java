/*    */ package com.ankamagames.baseImpl.graphics.alea.cellSelector.elementSelector;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphics.alea.WorldCell;
/*    */ import com.ankamagames.baseImpl.graphics.alea.cellSelector.ElementSelector;
/*    */ import com.ankamagames.baseImpl.graphics.alea.element.GraphicalElement;
/*    */ import com.ankamagames.baseImpl.graphics.alea.element.properties.GraphicalElementProperties;
/*    */ import com.ankamagames.baseImpl.graphics.alea.worldElement.GraphicalWorldElement;
/*    */ import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ElementSelectorGround
/*    */   extends ElementSelector
/*    */ {
/*    */   protected void addElementFromCell(WorldCell cell)
/*    */   {
/* 23 */     List<WorldElement> elements = cell.getElementsAtTop();
/* 24 */     for (WorldElement element : elements) {
/* 25 */       if ((element != null) && 
/* 26 */         ((element instanceof GraphicalWorldElement))) {
/* 27 */         GraphicalWorldElement graphicalElt = (GraphicalWorldElement)element;
/* 28 */         if (graphicalElt.getElement().getStateProperties(0).isWalkable()) {
/* 29 */           this.m_selectedElements.add(graphicalElt);
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\cellSelector\elementSelector\ElementSelectorGround.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */