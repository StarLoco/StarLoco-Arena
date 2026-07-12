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
/*     */ public final class Source
/*     */ {
/*     */   private final AL al;
/*     */   private final int sourceID;
/*     */   private Buffer buffer;
/*     */   
/*     */   Source(AL paramAL, int paramInt) {
/*  52 */     this.al = paramAL;
/*  53 */     this.sourceID = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void play() {
/*  60 */     this.al.alSourcePlay(this.sourceID);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pause() {
/*  67 */     this.al.alSourcePause(this.sourceID);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stop() {
/*  74 */     this.al.alSourceStop(this.sourceID);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void rewind() {
/*  81 */     this.al.alSourceRewind(this.sourceID);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete() {
/*  88 */     this.al.alDeleteSources(1, new int[] { this.sourceID }, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPitch(float paramFloat) {
/*  98 */     this.al.alSourcef(this.sourceID, 4099, paramFloat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPitch() {
/* 108 */     float[] arrayOfFloat = new float[1];
/* 109 */     this.al.alGetSourcef(this.sourceID, 4099, arrayOfFloat, 0);
/*     */     
/* 111 */     return arrayOfFloat[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGain(float paramFloat) {
/* 121 */     this.al.alSourcef(this.sourceID, 4106, paramFloat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getGain() {
/* 131 */     float[] arrayOfFloat = new float[1];
/* 132 */     this.al.alGetSourcef(this.sourceID, 4106, arrayOfFloat, 0);
/*     */     
/* 134 */     return arrayOfFloat[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxDistance(float paramFloat) {
/* 144 */     this.al.alSourcef(this.sourceID, 4131, paramFloat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMaxDistance() {
/* 154 */     float[] arrayOfFloat = new float[1];
/* 155 */     this.al.alGetSourcef(this.sourceID, 4131, arrayOfFloat, 0);
/*     */     
/* 157 */     return arrayOfFloat[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRolloffFactor(float paramFloat) {
/* 166 */     this.al.alSourcef(this.sourceID, 4129, paramFloat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getRolloffFactor() {
/* 175 */     float[] arrayOfFloat = new float[1];
/* 176 */     this.al.alGetSourcef(this.sourceID, 4129, arrayOfFloat, 0);
/*     */     
/* 178 */     return arrayOfFloat[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReferenceDistance(float paramFloat) {
/* 188 */     this.al.alSourcef(this.sourceID, 4128, paramFloat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getReferenceDistance() {
/* 198 */     float[] arrayOfFloat = new float[1];
/* 199 */     this.al.alGetSourcef(this.sourceID, 4128, arrayOfFloat, 0);
/*     */     
/* 201 */     return arrayOfFloat[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMinGain(float paramFloat) {
/* 210 */     this.al.alSourcef(this.sourceID, 4109, paramFloat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMinGain() {
/* 219 */     float[] arrayOfFloat = new float[1];
/* 220 */     this.al.alGetSourcef(this.sourceID, 4109, arrayOfFloat, 0);
/*     */     
/* 222 */     return arrayOfFloat[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxGain(float paramFloat) {
/* 231 */     this.al.alSourcef(this.sourceID, 4110, paramFloat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMaxGain() {
/* 240 */     float[] arrayOfFloat = new float[1];
/* 241 */     this.al.alGetSourcef(this.sourceID, 4110, arrayOfFloat, 0);
/*     */     
/* 243 */     return arrayOfFloat[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setConeOuterGain(float paramFloat) {
/* 252 */     this.al.alSourcef(this.sourceID, 4130, paramFloat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getConeOuterGain() {
/* 261 */     float[] arrayOfFloat = new float[1];
/* 262 */     this.al.alGetSourcef(this.sourceID, 4130, arrayOfFloat, 0);
/*     */     
/* 264 */     return arrayOfFloat[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPosition(Vec3f paramVec3f) {
/* 274 */     this.al.alSource3f(this.sourceID, 4100, paramVec3f.v1, paramVec3f.v2, paramVec3f.v3);
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
/*     */   
/*     */   public void setPosition(float paramFloat1, float paramFloat2, float paramFloat3) {
/* 290 */     this.al.alSource3f(this.sourceID, 4100, paramFloat1, paramFloat2, paramFloat3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3f getPosition() {
/* 300 */     Vec3f vec3f = null;
/* 301 */     float[] arrayOfFloat = new float[3];
/* 302 */     this.al.alGetSourcefv(this.sourceID, 4100, arrayOfFloat, 0);
/* 303 */     vec3f = new Vec3f(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2]);
/*     */     
/* 305 */     return vec3f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVelocity(Vec3f paramVec3f) {
/* 314 */     this.al.alSource3f(this.sourceID, 4102, paramVec3f.v1, paramVec3f.v2, paramVec3f.v3);
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
/*     */   
/*     */   public void setVelocity(float paramFloat1, float paramFloat2, float paramFloat3) {
/* 330 */     this.al.alSource3f(this.sourceID, 4102, paramFloat1, paramFloat2, paramFloat3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3f getVelocity() {
/* 339 */     Vec3f vec3f = null;
/* 340 */     float[] arrayOfFloat = new float[3];
/* 341 */     this.al.alGetSourcefv(this.sourceID, 4102, arrayOfFloat, 0);
/* 342 */     vec3f = new Vec3f(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2]);
/*     */     
/* 344 */     return vec3f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDirection(Vec3f paramVec3f) {
/* 353 */     this.al.alSource3f(this.sourceID, 4101, paramVec3f.v1, paramVec3f.v2, paramVec3f.v3);
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
/*     */   
/*     */   public void setDirection(float paramFloat1, float paramFloat2, float paramFloat3) {
/* 369 */     this.al.alSource3f(this.sourceID, 4101, paramFloat1, paramFloat2, paramFloat3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3f getDirection() {
/* 378 */     Vec3f vec3f = null;
/* 379 */     float[] arrayOfFloat = new float[3];
/* 380 */     this.al.alGetSourcefv(this.sourceID, 4101, arrayOfFloat, 0);
/* 381 */     vec3f = new Vec3f(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2]);
/*     */     
/* 383 */     return vec3f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSourceRelative(boolean paramBoolean) {
/* 394 */     boolean bool = paramBoolean ? true : false;
/* 395 */     this.al.alSourcei(this.sourceID, 514, bool);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSourceRelative() {
/* 406 */     int[] arrayOfInt = new int[1];
/* 407 */     this.al.alGetSourcei(this.sourceID, 514, arrayOfInt, 0);
/*     */     
/* 409 */     return (arrayOfInt[0] == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLooping(boolean paramBoolean) {
/* 418 */     boolean bool = paramBoolean ? true : false;
/* 419 */     this.al.alSourcei(this.sourceID, 4103, bool);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getLooping() {
/* 428 */     boolean bool = false;
/* 429 */     int[] arrayOfInt = new int[1];
/* 430 */     this.al.alGetSourcei(this.sourceID, 4103, arrayOfInt, 0);
/* 431 */     return (arrayOfInt[0] == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBuffersQueued() {
/* 440 */     int[] arrayOfInt = new int[1];
/* 441 */     this.al.alGetSourcei(this.sourceID, 4117, arrayOfInt, 0);
/*     */     
/* 443 */     return arrayOfInt[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBuffersProcessed() {
/* 451 */     int[] arrayOfInt = new int[1];
/* 452 */     this.al.alGetSourcei(this.sourceID, 4118, arrayOfInt, 0);
/*     */     
/* 454 */     return arrayOfInt[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBuffer(Buffer paramBuffer) {
/* 463 */     this.al.alSourcei(this.sourceID, 4105, paramBuffer.bufferID);
/* 464 */     this.buffer = paramBuffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Buffer getBuffer() {
/* 473 */     return this.buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void queueBuffers(Buffer[] paramArrayOfBuffer) {
/* 483 */     int i = paramArrayOfBuffer.length;
/* 484 */     int[] arrayOfInt = new int[i];
/*     */     
/* 486 */     for (byte b = 0; b < i; b++) {
/* 487 */       arrayOfInt[b] = (paramArrayOfBuffer[b]).bufferID;
/*     */     }
/*     */     
/* 490 */     this.al.alSourceQueueBuffers(this.sourceID, i, arrayOfInt, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unqueueBuffers(Buffer[] paramArrayOfBuffer) {
/* 499 */     int i = paramArrayOfBuffer.length;
/* 500 */     int[] arrayOfInt = new int[i];
/*     */     
/* 502 */     for (byte b = 0; b < i; b++) {
/* 503 */       arrayOfInt[b] = (paramArrayOfBuffer[b]).bufferID;
/*     */     }
/*     */     
/* 506 */     this.al.alSourceUnqueueBuffers(this.sourceID, i, arrayOfInt, 0);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\net\java\games\sound3d\Source.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */