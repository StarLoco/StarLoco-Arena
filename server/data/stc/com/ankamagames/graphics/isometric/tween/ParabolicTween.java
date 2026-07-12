/*     */ package com.ankamagames.graphics.isometric.tween;
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
/*     */ public class ParabolicTween
/*     */   extends Tween
/*     */ {
/*  15 */   private double ISOMETRIC_Z_TO_ALTITUDE_COEF = 4.8D;
/*     */   
/*  17 */   private static double DEFAULT_G = 9.81D;
/*     */   
/*  19 */   private static double DEFAULT_TIME_PER_SECOND = 1.0D;
/*     */   
/*  21 */   private double m_timePersecond = DEFAULT_TIME_PER_SECOND;
/*     */   
/*  23 */   private double m_g = DEFAULT_G;
/*     */   
/*     */   private double m_startX;
/*     */   
/*     */   private double m_startY;
/*     */   
/*     */   private double m_startZ;
/*     */   private double m_angle;
/*     */   private double m_linearAngle;
/*     */   private double m_initialSpeed;
/*     */   private double m_tweenEstimatedTime;
/*  34 */   private long m_startTime = 0L;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ParabolicTween(IsoWorldTarget target, double destX, double destY, double destZ, double angle)
/*     */   {
/*  46 */     super(target);
/*  47 */     this.m_startX = this.m_target.getWorldX();
/*  48 */     this.m_startY = this.m_target.getWorldY();
/*  49 */     this.m_startZ = this.m_target.getAltitude();
/*  50 */     this.m_angle = Math.toRadians(angle);
/*     */     
/*     */ 
/*  53 */     double distance = Math.sqrt(Math.pow(destX - this.m_startX, 2.0D) + Math.pow(destY - this.m_startY, 2.0D));
/*  54 */     this.m_initialSpeed = Math.sqrt(this.m_g * distance / Math.sin(2.0D * this.m_angle));
/*     */     
/*     */ 
/*     */ 
/*  58 */     this.m_linearAngle = Math.atan((destY - this.m_startY) / (destX - this.m_startX));
/*  59 */     if (destX - this.m_startX < 0.0D) {
/*  60 */       this.m_linearAngle += 3.141592653589793D;
/*     */     }
/*     */     
/*  63 */     this.m_tweenEstimatedTime = (2.0D * this.m_initialSpeed * Math.sin(this.m_angle) / this.m_g);
/*     */   }
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
/*     */   public ParabolicTween(IsoWorldTarget target, double destX, double destY, double destZ, double angle, double timePerSecond)
/*     */   {
/*  77 */     this(target, destX, destY, 0.0D, angle);
/*     */     
/*  79 */     this.m_timePersecond = timePerSecond;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void process(long realTime, int frameCount)
/*     */   {
/*  89 */     if (this.m_startTime == 0L) {
/*  90 */       this.m_startTime = realTime;
/*     */     }
/*  92 */     double m_elapsedTime = (realTime - this.m_startTime) * (this.m_timePersecond / 1000.0D);
/*     */     
/*  94 */     if ((this.m_target == null) || (m_elapsedTime > this.m_tweenEstimatedTime)) {
/*  95 */       endTween();
/*  96 */       return;
/*     */     }
/*     */     
/*  99 */     double x = Math.cos(this.m_linearAngle) * this.m_initialSpeed * Math.cos(this.m_angle) * m_elapsedTime + this.m_startX;
/* 100 */     double y = Math.sin(this.m_linearAngle) * this.m_initialSpeed * Math.cos(this.m_angle) * m_elapsedTime + this.m_startY;
/* 101 */     double z = -this.m_g / 2.0D * Math.pow(m_elapsedTime, 2.0D) + this.m_initialSpeed * Math.sin(this.m_angle) * m_elapsedTime;
/*     */     
/* 103 */     double altitude = this.ISOMETRIC_Z_TO_ALTITUDE_COEF * z + this.m_startZ;
/*     */     
/* 105 */     this.m_target.setWorldX(x);
/* 106 */     this.m_target.setWorldY(y);
/* 107 */     this.m_target.setAltitude(altitude);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getTweenDuration()
/*     */   {
/* 116 */     return this.m_tweenEstimatedTime * 1000.0D / this.m_timePersecond;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\isometric\tween\ParabolicTween.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */