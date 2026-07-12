/*     */ package net.java.games.sound3d;
/*     */ 
/*     */ import net.java.games.joal.AL;
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
/*     */ public class Listener
/*     */ {
/*     */   private final AL al;
/*     */   
/*     */   Listener(AL paramAL) {
/*  50 */     this.al = paramAL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGain(float paramFloat) {
/*  60 */     this.al.alListenerf(4106, paramFloat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getGain() {
/*  70 */     float[] arrayOfFloat = new float[1];
/*  71 */     this.al.alGetListenerf(4106, arrayOfFloat, 0);
/*     */     
/*  73 */     return arrayOfFloat[0];
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
/*     */ 
/*     */   
/*     */   public void setPosition(float paramFloat1, float paramFloat2, float paramFloat3) {
/*  88 */     this.al.alListener3f(4100, paramFloat1, paramFloat2, paramFloat3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPosition(Vec3f paramVec3f) {
/*  99 */     this.al.alListener3f(4100, paramVec3f.v1, paramVec3f.v2, paramVec3f.v3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3f getPosition() {
/* 110 */     Vec3f vec3f = null;
/* 111 */     float[] arrayOfFloat = new float[3];
/* 112 */     this.al.alGetListenerfv(4100, arrayOfFloat, 0);
/* 113 */     vec3f = new Vec3f(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2]);
/*     */     
/* 115 */     return vec3f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVelocity(Vec3f paramVec3f) {
/* 126 */     this.al.alListener3f(4102, paramVec3f.v1, paramVec3f.v2, paramVec3f.v3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3f getVelocity() {
/* 137 */     Vec3f vec3f = null;
/* 138 */     float[] arrayOfFloat = new float[3];
/* 139 */     this.al.alGetListenerfv(4102, arrayOfFloat, 0);
/* 140 */     vec3f = new Vec3f(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2]);
/*     */     
/* 142 */     return vec3f;
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
/*     */   public void setOrientation(float[] paramArrayOffloat) {
/* 154 */     this.al.alListenerfv(4111, paramArrayOffloat, 0);
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
/*     */   public float[] getOrientation() {
/* 167 */     float[] arrayOfFloat = new float[6];
/* 168 */     this.al.alGetListenerfv(4111, arrayOfFloat, 0);
/* 169 */     return arrayOfFloat;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\net\java\games\sound3d\Listener.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */