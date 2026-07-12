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
/*    */ public class FrictionalForce
/*    */   extends BaseAffector
/*    */ {
/* 16 */   private float m_friction = 1.0F;
/*    */   
/*    */   public void affect(Particle mesh, ParticleSystem particleSystem)
/*    */   {
/* 20 */     mesh.setVelocityX(mesh.getVelocityX() * this.m_friction);
/* 21 */     mesh.setVelocityY(mesh.getVelocityY() * this.m_friction);
/* 22 */     mesh.setVelocityZ(mesh.getVelocityZ() * this.m_friction);
/*    */   }
/*    */   
/*    */   public float getFriction() {
/* 26 */     return this.m_friction;
/*    */   }
/*    */   
/*    */   public void setFriction(float friction) {
/* 30 */     this.m_friction = friction;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 35 */     return "Frictionnal Force";
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\particlesystem\affectors\FrictionalForce.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */