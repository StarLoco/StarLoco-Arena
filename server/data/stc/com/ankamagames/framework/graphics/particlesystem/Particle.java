/*     */ package com.ankamagames.framework.graphics.particlesystem;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.opengl.base.impl.Mesh2D;
/*     */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Particle
/*     */   extends LifeObject
/*     */   implements Poolable
/*     */ {
/*     */   private double m_x;
/*     */   private double m_y;
/*     */   private double m_z;
/*     */   private float m_lifeTime;
/*     */   private float m_velocityX;
/*     */   private float m_velocityY;
/*     */   private float m_velocityZ;
/*     */   protected long m_currentLife;
/*     */   protected long m_lastProcessTime;
/*     */   private HashMap<Object, Object[]> m_stateRecorder;
/*     */   
/*     */   public abstract Mesh2D getMesh();
/*     */   
/*     */   public void process(long realTime, int frameCount)
/*     */   {
/*  33 */     if (this.m_lastProcessTime == 0L)
/*     */     {
/*  35 */       this.m_lastProcessTime = realTime;
/*  36 */       return;
/*     */     }
/*     */     
/*  39 */     if ((this.m_lifeTime >= 0.0F) && ((float)this.m_currentLife > this.m_lifeTime))
/*     */     {
/*  41 */       kill();
/*  42 */       return;
/*     */     }
/*     */     
/*  45 */     long elapsedTime = realTime - this.m_lastProcessTime;
/*     */     
/*  47 */     this.m_x += this.m_velocityX;
/*  48 */     this.m_y += this.m_velocityY;
/*  49 */     this.m_z += this.m_velocityZ;
/*     */     
/*  51 */     this.m_currentLife += elapsedTime;
/*  52 */     this.m_lastProcessTime = realTime;
/*     */   }
/*     */   
/*     */   public void setX(double x) {
/*  56 */     this.m_x = x;
/*     */   }
/*     */   
/*     */   public void setY(double y) {
/*  60 */     this.m_y = y;
/*     */   }
/*     */   
/*     */   public void setZ(double z) {
/*  64 */     this.m_z = z;
/*     */   }
/*     */   
/*     */   public double getX() {
/*  68 */     return this.m_x;
/*     */   }
/*     */   
/*     */   public double getY() {
/*  72 */     return this.m_y;
/*     */   }
/*     */   
/*     */   public double getZ() {
/*  76 */     return this.m_z;
/*     */   }
/*     */   
/*     */   public float getCurrentLife() {
/*  80 */     return (float)this.m_currentLife;
/*     */   }
/*     */   
/*     */   public float getLifeTime() {
/*  84 */     return this.m_lifeTime;
/*     */   }
/*     */   
/*     */   public void setLifeTime(float lifeTime) {
/*  88 */     this.m_lifeTime = lifeTime;
/*     */   }
/*     */   
/*     */   public float getVelocityX() {
/*  92 */     return this.m_velocityX;
/*     */   }
/*     */   
/*     */   public void setVelocityX(float velocityX) {
/*  96 */     this.m_velocityX = velocityX;
/*     */   }
/*     */   
/*     */   public float getVelocityY() {
/* 100 */     return this.m_velocityY;
/*     */   }
/*     */   
/*     */   public void setVelocityY(float velocityY) {
/* 104 */     this.m_velocityY = velocityY;
/*     */   }
/*     */   
/*     */   public float getVelocityZ() {
/* 108 */     return this.m_velocityZ;
/*     */   }
/*     */   
/*     */   public void setVelocityZ(float velocityZ) {
/* 112 */     this.m_velocityZ = velocityZ;
/*     */   }
/*     */   
/*     */   public Object[] getState(Object key) {
/* 116 */     if (this.m_stateRecorder == null) {
/* 117 */       return null;
/*     */     }
/* 119 */     return (Object[])this.m_stateRecorder.get(key);
/*     */   }
/*     */   
/*     */   public void saveState(Object key, Object[] states)
/*     */   {
/* 124 */     if (this.m_stateRecorder == null) {
/* 125 */       this.m_stateRecorder = new HashMap();
/*     */     }
/* 127 */     this.m_stateRecorder.put(key, states);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void onCheckIn()
/*     */   {
/* 134 */     if (this.m_stateRecorder != null) {
/* 135 */       this.m_stateRecorder.clear();
/*     */     }
/* 137 */     this.m_x = 0.0D;
/* 138 */     this.m_y = 0.0D;
/* 139 */     this.m_z = 0.0D;
/* 140 */     this.m_lifeTime = 0.0F;
/*     */     
/* 142 */     this.m_velocityX = 0.0F;
/* 143 */     this.m_velocityY = 0.0F;
/* 144 */     this.m_velocityZ = 0.0F;
/*     */     
/* 146 */     this.m_currentLife = 0L;
/* 147 */     this.m_lastProcessTime = 0L;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void onCheckOut()
/*     */   {
/* 154 */     setDead(false);
/*     */     
/* 156 */     this.m_stateRecorder = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\particlesystem\Particle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */