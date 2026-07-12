/*    */ package com.ankamagames.graphics.isometric.lights;
/*    */ 
/*    */ import com.ankamagames.graphics.isometric.IsoWorldTarget;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LightSpot
/*    */ {
/*    */   private IsoWorldTarget m_target;
/* 16 */   private double m_intensityR = 0.6000000238418579D;
/* 17 */   private double m_intensityG = 0.5D;
/* 18 */   private double m_intensityB = 0.4000000059604645D;
/*    */   
/*    */   public LightSpot(IsoWorldTarget target)
/*    */   {
/* 22 */     this.m_target = target;
/*    */   }
/*    */   
/*    */   public void setTarget(IsoWorldTarget target) {
/* 26 */     this.m_target = target;
/*    */   }
/*    */   
/*    */   public IsoWorldTarget getTarget() {
/* 30 */     return this.m_target;
/*    */   }
/*    */   
/*    */   public double getIntensityR() {
/* 34 */     return this.m_intensityR;
/*    */   }
/*    */   
/*    */   public void setIntensityR(double intensityR) {
/* 38 */     this.m_intensityR = intensityR;
/*    */   }
/*    */   
/*    */   public double getIntensityG() {
/* 42 */     return this.m_intensityG;
/*    */   }
/*    */   
/*    */   public void setIntensityG(double intensityG) {
/* 46 */     this.m_intensityG = intensityG;
/*    */   }
/*    */   
/*    */   public double getIntensityB() {
/* 50 */     return this.m_intensityB;
/*    */   }
/*    */   
/*    */   public void setIntensityB(double intensityB) {
/* 54 */     this.m_intensityB = intensityB;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\isometric\lights\LightSpot.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */