/*    */ package com.ankamagames.framework.graphics.particlesystem.affectors;
/*    */ 
/*    */ import com.ankamagames.framework.graphics.opengl.base.impl.Mesh2D;
/*    */ import com.ankamagames.framework.graphics.opengl.base.matrices.transformation2D.Scaling2D;
/*    */ import com.ankamagames.framework.graphics.particlesystem.Particle;
/*    */ import com.ankamagames.framework.graphics.particlesystem.ParticleSystem;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Deformer
/*    */   extends BaseAffector
/*    */ {
/* 17 */   private float m_growthX = 0.0F;
/* 18 */   private float m_growthY = 0.0F;
/* 19 */   private float m_rotate = 0.0F;
/*    */   
/*    */   public void affect(Particle particle, ParticleSystem particleSystem)
/*    */   {
/* 23 */     Mesh2D mesh = particle.getMesh();
/*    */     
/* 25 */     mesh.rotate(this.m_rotate);
/*    */     
/* 27 */     mesh.setScale(mesh.getScaleMatrix().getX() + this.m_growthX, mesh.getScaleMatrix().getY() + this.m_growthY);
/*    */   }
/*    */   
/*    */   public float getGrowthX() {
/* 31 */     return this.m_growthX;
/*    */   }
/*    */   
/*    */   public void setGrowthX(float growthX) {
/* 35 */     this.m_growthX = growthX;
/*    */   }
/*    */   
/*    */   public float getGrowthY() {
/* 39 */     return this.m_growthY;
/*    */   }
/*    */   
/*    */   public void setGrowthY(float growthY) {
/* 43 */     this.m_growthY = growthY;
/*    */   }
/*    */   
/*    */   public float getRotate() {
/* 47 */     return this.m_rotate;
/*    */   }
/*    */   
/*    */   public void setRotate(float rotate) {
/* 51 */     this.m_rotate = rotate;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 56 */     return "Deformer";
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\particlesystem\affectors\Deformer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */