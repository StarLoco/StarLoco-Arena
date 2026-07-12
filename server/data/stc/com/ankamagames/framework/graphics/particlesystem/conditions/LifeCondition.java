/*    */ package com.ankamagames.framework.graphics.particlesystem.conditions;
/*    */ 
/*    */ import com.ankamagames.framework.graphics.particlesystem.Particle;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LifeCondition
/*    */   extends AffectorCondition
/*    */ {
/* 15 */   private int m_lifeMax = 0;
/* 16 */   private int m_lifeMin = 0;
/*    */   
/*    */   public boolean validateCondition(Particle m)
/*    */   {
/* 20 */     if (m.getCurrentLife() < this.m_lifeMin) {
/* 21 */       return false;
/*    */     }
/* 23 */     if (m.getCurrentLife() > this.m_lifeMax) {
/* 24 */       return false;
/*    */     }
/* 26 */     return true;
/*    */   }
/*    */   
/*    */   public void setLifeMax(int lifeMax) {
/* 30 */     this.m_lifeMax = lifeMax;
/*    */   }
/*    */   
/*    */   public void setLifeMin(int lifeMin) {
/* 34 */     this.m_lifeMin = lifeMin;
/*    */   }
/*    */   
/*    */   public int getLifeMax() {
/* 38 */     return this.m_lifeMax;
/*    */   }
/*    */   
/*    */   public int getLifeMin() {
/* 42 */     return this.m_lifeMin;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 47 */     return "Life Condition";
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\particlesystem\conditions\LifeCondition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */