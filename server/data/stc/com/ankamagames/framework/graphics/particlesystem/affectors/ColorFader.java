/*    */ package com.ankamagames.framework.graphics.particlesystem.affectors;
/*    */ 
/*    */ import com.ankamagames.framework.graphics.opengl.base.impl.Mesh2D;
/*    */ import com.ankamagames.framework.graphics.opengl.base.material.Material;
/*    */ import com.ankamagames.framework.graphics.particlesystem.Particle;
/*    */ import com.ankamagames.framework.graphics.particlesystem.ParticleSystem;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ColorFader
/*    */   extends BaseAffector
/*    */ {
/* 17 */   private float m_red = 0.0F;
/* 18 */   private float m_green = 0.0F;
/* 19 */   private float m_blue = 0.0F;
/* 20 */   private float m_alpha = 0.0F;
/*    */   
/* 22 */   private float m_speed = 0.1F;
/*    */   
/*    */ 
/*    */   public void affect(Particle particle, ParticleSystem particleSystem)
/*    */   {
/* 27 */     float[] oldColor = particle.getMesh().getMaterial().getDiffuse();
/* 28 */     float newR = oldColor[0] - (oldColor[0] - this.m_red) * this.m_speed;
/* 29 */     float newG = oldColor[1] - (oldColor[1] - this.m_green) * this.m_speed;
/* 30 */     float newB = oldColor[2] - (oldColor[2] - this.m_blue) * this.m_speed;
/* 31 */     float newA = oldColor[3] - (oldColor[3] - this.m_alpha) * this.m_speed;
/*    */     
/* 33 */     particle.getMesh().setColor(newR, newG, newB, newA);
/*    */   }
/*    */   
/*    */   public float getRed() {
/* 37 */     return this.m_red;
/*    */   }
/*    */   
/*    */   public float getGreen() {
/* 41 */     return this.m_green;
/*    */   }
/*    */   
/*    */   public float getBlue() {
/* 45 */     return this.m_blue;
/*    */   }
/*    */   
/*    */   public float getAlpha() {
/* 49 */     return this.m_alpha;
/*    */   }
/*    */   
/*    */   public float getSpeed() {
/* 53 */     return this.m_speed;
/*    */   }
/*    */   
/*    */   public void setRed(float red) {
/* 57 */     this.m_red = red;
/*    */   }
/*    */   
/*    */   public void setGreen(float green) {
/* 61 */     this.m_green = green;
/*    */   }
/*    */   
/*    */   public void setBlue(float blue) {
/* 65 */     this.m_blue = blue;
/*    */   }
/*    */   
/*    */   public void setAlpha(float alpha) {
/* 69 */     this.m_alpha = alpha;
/*    */   }
/*    */   
/*    */   public void setSpeed(float speed) {
/* 73 */     this.m_speed = speed;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 77 */     return "Color Fader";
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\particlesystem\affectors\ColorFader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */