/*    */ package com.ankamagames.graphics.isometric.lines;
/*    */ 
/*    */ import com.ankamagames.framework.graphics.opengl.base.impl.Mesh2D;
/*    */ import com.ankamagames.framework.graphics.opengl.base.states.GLRenderStates;
/*    */ import javax.media.opengl.GL;
/*    */ 
/*    */ public class SegmentMesh
/*    */   extends Mesh2D
/*    */ {
/*    */   private float m_startX;
/*    */   private float m_startY;
/*    */   private float m_endX;
/*    */   private float m_endY;
/*    */   
/*    */   private class SegmentPreRenderState
/*    */     implements GLRenderStates {
/*    */     public void setup(GL gl) {
/* 18 */       gl.glEnable(2848);
/*    */       
/* 20 */       gl.glEnable(2852);
/* 21 */       gl.glLineStipple(4, (short)-21846);
/*    */     }
/*    */     
/*    */     private SegmentPreRenderState() {} }
/*    */   
/*    */   private class SegmentPostRenderState implements GLRenderStates { public void setup(GL gl) {
/* 27 */       gl.glDisable(2852);
/* 28 */       gl.glDisable(2848);
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     private SegmentPostRenderState() {} }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 38 */   private float m_alpha = 0.75F;
/*    */ 
/*    */ 
/*    */   
/*    */   public SegmentMesh() {
/* 43 */     setPreRenderStates(new SegmentPreRenderState(null));
/* 44 */     setPostRenderStates(new SegmentPostRenderState(null));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawPrimitives(GL gl) {
/* 51 */     if (!this.m_visible) {
/*    */       return;
/*    */     }
/*    */     
/* 55 */     gl.glColor4f(this.m_alpha, this.m_alpha, this.m_alpha, this.m_alpha);
/*    */     
/* 57 */     gl.glLineWidth(4.0F);
/*    */     
/* 59 */     gl.glBegin(1);
/* 60 */     gl.glVertex3f(this.m_startX, this.m_startY, getPosZ());
/* 61 */     gl.glVertex3f(this.m_endX, this.m_endY, getPosZ());
/* 62 */     gl.glEnd();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setStart(float startX, float startY) {
/* 68 */     this.m_startX = startX;
/* 69 */     this.m_startY = startY;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setEnd(float endX, float endY) {
/* 75 */     this.m_endX = endX;
/* 76 */     this.m_endY = endY;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setAlpha(float alpha) {
/* 82 */     this.m_alpha = alpha;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\isometric\lines\SegmentMesh.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */