/*    */ package com.ankamagames.baseImpl.graphics.alea.worldElement;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphics.alea.WorldElementManager;
/*    */ import com.ankamagames.baseImpl.graphics.alea.element.GraphicalElement;
/*    */ import com.ankamagames.baseImpl.graphics.alea.element.properties.GraphicalElementProperties;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GraphicalWorldElement
/*    */   extends WorldElement
/*    */ {
/*    */   public GraphicalWorldElement(int elementId, int paramsCount, byte[] params, int state, int groupId)
/*    */   {
/* 15 */     super(elementId, paramsCount, params, state, groupId);
/*    */     
/* 17 */     this.m_element = WorldElementManager.getInstance().getGraphicalElement(elementId);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public GraphicalElement getElement()
/*    */   {
/* 25 */     return (GraphicalElement)this.m_element;
/*    */   }
/*    */   
/*    */   public double getWeight() {
/* 29 */     return getElement().getStateProperties(this.m_state).getWeight();
/*    */   }
/*    */   
/*    */   public double getHeight() {
/* 33 */     return getElement().getStateProperties(this.m_state).getHeight();
/*    */   }
/*    */   
/*    */   public double getVisualHeight() {
/* 37 */     return getElement().getStateProperties(this.m_state).getVisualHeight();
/*    */   }
/*    */   
/*    */   public byte getSlope() {
/* 41 */     return getElement().getStateProperties(this.m_state).getSlope();
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\worldElement\GraphicalWorldElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */