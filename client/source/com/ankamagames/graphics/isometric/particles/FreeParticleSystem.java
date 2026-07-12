/*     */ package com.ankamagames.graphics.isometric.particles;
/*     */ 
/*     */ import com.ankamagames.graphics.isometric.IsoWorldTarget;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FreeParticleSystem
/*     */   extends IsoParticleSystem
/*     */   implements IsoWorldTarget
/*     */ {
/*     */   private IsoWorldTarget m_target;
/*     */   
/*     */   public double getX() {
/*  26 */     if (this.m_target != null) {
/*  27 */       return this.m_target.getWorldX();
/*     */     }
/*  29 */     return super.getX();
/*     */   }
/*     */ 
/*     */   
/*     */   public double getY() {
/*  34 */     if (this.m_target != null) {
/*  35 */       return this.m_target.getWorldY();
/*     */     }
/*  37 */     return super.getY();
/*     */   }
/*     */ 
/*     */   
/*     */   public double getZ() {
/*  42 */     if (this.m_target != null) {
/*  43 */       return this.m_target.getAltitude();
/*     */     }
/*  45 */     return super.getZ();
/*     */   }
/*     */   
/*     */   public IsoWorldTarget getTarget() {
/*  49 */     return this.m_target;
/*     */   }
/*     */   
/*     */   public void setTarget(IsoWorldTarget target) {
/*  53 */     this.m_target = target;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getAltitude() {
/*  62 */     return getZ();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWorldCellX() {
/*  71 */     return (int)getX();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWorldCellY() {
/*  80 */     return (int)getY();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getWorldX() {
/*  89 */     return getX();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getWorldY() {
/*  98 */     return getY();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAltitude(double altitude) {
/* 107 */     setZ(altitude);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWorldX(double worldX) {
/* 116 */     setX(worldX);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWorldY(double worldY) {
/* 125 */     setY(worldY);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\isometric\particles\FreeParticleSystem.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */