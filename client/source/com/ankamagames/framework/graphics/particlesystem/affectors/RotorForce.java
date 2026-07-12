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
/*    */ public class RotorForce
/*    */   extends BaseAffector
/*    */ {
/* 16 */   private float m_intensity = 0.5F;
/*    */ 
/*    */   
/*    */   public void affect(Particle particle, ParticleSystem particleSystem) {
/* 20 */     double difX = (particle.getY() - particleSystem.getYFromSystemCenter()) * this.m_intensity;
/* 21 */     double difY = -(particle.getX() - particleSystem.getXFromSystemCenter()) * this.m_intensity;
/*    */     
/* 23 */     particle.setX(particle.getX() + difX);
/* 24 */     particle.setY(particle.getY() + difY);
/*    */   }
/*    */   
/*    */   public float getIntensity() {
/* 28 */     return this.m_intensity;
/*    */   }
/*    */   
/*    */   public void setIntensity(float intensity) {
/* 32 */     this.m_intensity = intensity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 37 */     return "Rotor Force";
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\particlesystem\affectors\RotorForce.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */