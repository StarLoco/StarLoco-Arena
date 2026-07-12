/*    */ package com.ankamagames.framework.graphics.opengl.base.impl;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModifiableMesh2D
/*    */   extends Mesh2D
/*    */ {
/*    */   public void setVertexPosition(int index, float x, float y) {
/* 36 */     if (index >= 4) {
/*    */       return;
/*    */     }
/*    */     
/* 40 */     int offset = index * 4;
/* 41 */     this.m_vertexBuffer.put(offset, x);
/* 42 */     this.m_vertexBuffer.put(offset + 1, y);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setVertices(float[] x, float[] y) {
/* 47 */     if (x.length < 4 || y.length < 4) {
/*    */       return;
/*    */     }
/*    */     
/* 51 */     int offset = 0;
/* 52 */     for (int i = 0; i < 4; i++) {
/* 53 */       this.m_vertexBuffer.put(offset, x[i]);
/* 54 */       this.m_vertexBuffer.put(offset + 1, y[i]);
/* 55 */       offset += 4;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setWidth(float width) {
/* 65 */     this.m_width = width;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setHeight(float height) {
/* 74 */     this.m_height = height;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\impl\ModifiableMesh2D.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */