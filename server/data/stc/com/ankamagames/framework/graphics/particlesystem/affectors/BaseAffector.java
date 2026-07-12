/*    */ package com.ankamagames.framework.graphics.particlesystem.affectors;
/*    */ 
/*    */ import com.ankamagames.framework.graphics.particlesystem.Particle;
/*    */ import com.ankamagames.framework.graphics.particlesystem.ParticleSystem;
/*    */ import com.ankamagames.framework.graphics.particlesystem.conditions.AffectorCondition;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BaseAffector
/*    */ {
/*    */   private ArrayList<AffectorCondition> m_conditions;
/*    */   
/*    */   public ArrayList<AffectorCondition> getConditions()
/*    */   {
/* 22 */     return this.m_conditions;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void addCondition(AffectorCondition c)
/*    */   {
/* 31 */     if (this.m_conditions == null) {
/* 32 */       this.m_conditions = new ArrayList();
/*    */     }
/* 34 */     this.m_conditions.add(c);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void removeCondition(AffectorCondition c)
/*    */   {
/* 43 */     this.m_conditions.remove(c);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void affectWithCondition(Particle particle, ParticleSystem particleSystem)
/*    */   {
/* 53 */     if (this.m_conditions != null) {
/* 54 */       for (AffectorCondition condition : this.m_conditions)
/*    */       {
/* 56 */         if (!condition.validateCondition(particle))
/* 57 */           return;
/*    */       }
/*    */     }
/* 60 */     affect(particle, particleSystem);
/*    */   }
/*    */   
/*    */   public abstract void affect(Particle paramParticle, ParticleSystem paramParticleSystem);
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\particlesystem\affectors\BaseAffector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */