/*    */ package com.ankamagames.framework.graphics.particlesystem.affectors;
/*    */ 
/*    */ import com.ankamagames.framework.graphics.particlesystem.Particle;
/*    */ import com.ankamagames.framework.graphics.particlesystem.ParticleSystem;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LinearForce
/*    */   extends BaseAffector
/*    */ {
/*    */   private float m_forceX;
/*    */   private float m_forceY;
/*    */   private float m_forceZ;
/*    */   
/*    */   public void affect(Particle particle, ParticleSystem particleSystem) {
/* 22 */     particle.setVelocityX(particle.getVelocityX() + this.m_forceX);
/* 23 */     particle.setVelocityY(particle.getVelocityY() + this.m_forceY);
/* 24 */     particle.setVelocityZ(particle.getVelocityZ() + this.m_forceZ);
/*    */   }
/*    */   
/*    */   public float getForceX() {
/* 28 */     return this.m_forceX;
/*    */   }
/*    */   
/*    */   public void setForceX(float forceX) {
/* 32 */     this.m_forceX = forceX;
/*    */   }
/*    */   
/*    */   public float getForceY() {
/* 36 */     return this.m_forceY;
/*    */   }
/*    */   
/*    */   public void setForceY(float forceY) {
/* 40 */     this.m_forceY = forceY;
/*    */   }
/*    */   
/*    */   public float getForceZ() {
/* 44 */     return this.m_forceZ;
/*    */   }
/*    */   
/*    */   public void setForceZ(float forceZ) {
/* 48 */     this.m_forceZ = forceZ;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 53 */     return "Linear Force";
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\particlesystem\affectors\LinearForce.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */