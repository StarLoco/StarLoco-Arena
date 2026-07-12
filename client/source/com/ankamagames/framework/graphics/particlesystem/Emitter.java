/*     */ package com.ankamagames.framework.graphics.particlesystem;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.particlesystem.affectors.BaseAffector;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
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
/*     */ public class Emitter
/*     */   extends LifeObject
/*     */ {
/*     */   private ParticleSystem m_particleSystem;
/*     */   protected ArrayList<ParticleModel> m_particlesModel;
/*     */   protected ArrayList<Particle> m_particles;
/*     */   protected ArrayList<BaseAffector> m_particleAffector;
/*     */   private int m_startSpawnTime;
/*     */   private int m_endSpawnTime;
/*     */   private long m_lastSpawnTime;
/*     */   private long m_remainTimeForSpawn;
/*     */   private int m_maxParticlesCount;
/*     */   private int m_maxParticlesPerSpawn;
/*     */   private int m_spawnFrequency;
/*     */   private int m_particleLifeTime;
/*     */   private float m_particleOffsetX;
/*     */   private float m_particleOffsetRandomX;
/*     */   private float m_particleOffsetY;
/*     */   private float m_particleOffsetRandomY;
/*     */   private float m_particleOffsetZ;
/*     */   private float m_particleOffsetRandomZ;
/*     */   private float m_particleVelocityX;
/*     */   private float m_particleVelocityRandomX;
/*     */   private float m_particleVelocityY;
/*     */   private float m_particleVelocityRandomY;
/*     */   private float m_particleVelocityZ;
/*     */   private float m_particleVelocityRandomZ;
/*  50 */   private Random m_random = new Random();
/*     */   
/*     */   private boolean m_visible = false;
/*     */ 
/*     */   
/*     */   public Emitter(ParticleSystem particleSystem) {
/*  56 */     this.m_particleSystem = particleSystem;
/*     */     
/*  58 */     this.m_remainTimeForSpawn = 0L;
/*  59 */     this.m_lastSpawnTime = -1L;
/*     */     
/*  61 */     this.m_particles = new ArrayList<Particle>();
/*  62 */     this.m_particlesModel = new ArrayList<ParticleModel>();
/*  63 */     this.m_particleAffector = new ArrayList<BaseAffector>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canSpawnParticles(long systemDuration) {
/*  73 */     if (isVisible()) {
/*  74 */       return false;
/*     */     }
/*  76 */     if (this.m_startSpawnTime == 0 && this.m_endSpawnTime == 0) {
/*  77 */       return true;
/*     */     }
/*  79 */     if (systemDuration < this.m_startSpawnTime) {
/*  80 */       return false;
/*     */     }
/*  82 */     if (systemDuration > this.m_endSpawnTime) {
/*  83 */       return false;
/*     */     }
/*  85 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processAffectors(long realTime, int frameCount, ParticleSystem particleSystem) {
/*  96 */     for (BaseAffector affector : this.m_particleAffector) {
/*     */       
/*  98 */       for (Particle particle : this.m_particles) {
/*     */         
/* 100 */         if (particle.isDead()) {
/*     */           continue;
/*     */         }
/* 103 */         affector.affectWithCondition(particle, particleSystem);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void spawnParticles(long realTime, int frameCount, ParticleSystem particleSystem) {
/* 115 */     long spawnDifference = this.m_remainTimeForSpawn;
/* 116 */     int particleSpawned = 0;
/*     */     
/* 118 */     if (this.m_spawnFrequency > 0 && this.m_lastSpawnTime != -1L) {
/*     */       
/* 120 */       spawnDifference += realTime - this.m_lastSpawnTime;
/*     */       
/* 122 */       while (spawnDifference - this.m_spawnFrequency > 0L) {
/*     */         
/* 124 */         if (particleSpawned > this.m_maxParticlesPerSpawn) {
/*     */           break;
/*     */         }
/* 127 */         if (this.m_particles.size() < this.m_maxParticlesCount) {
/*     */           
/* 129 */           ParticleModel model = getRandomParticleModel();
/*     */           
/* 131 */           if (model == null) {
/*     */             break;
/*     */           }
/* 134 */           Particle particle = model.generateParticle(particleSystem);
/*     */           
/* 136 */           if (particle != null) {
/*     */             
/* 138 */             initializeParticle(particle);
/* 139 */             this.m_particles.add(particle);
/*     */           } 
/*     */         } 
/*     */         
/* 143 */         spawnDifference -= this.m_spawnFrequency;
/*     */         
/* 145 */         particleSpawned++;
/*     */       } 
/*     */     } 
/*     */     
/* 149 */     this.m_remainTimeForSpawn = spawnDifference;
/* 150 */     this.m_lastSpawnTime = realTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initializeParticle(Particle particle) {
/* 159 */     float offsetX = this.m_particleOffsetX;
/* 160 */     float offsetY = this.m_particleOffsetY;
/* 161 */     float offsetZ = this.m_particleOffsetZ;
/* 162 */     float velocityX = this.m_particleVelocityX;
/* 163 */     float velocityY = this.m_particleVelocityY;
/* 164 */     float velocityZ = this.m_particleVelocityZ;
/*     */     
/* 166 */     if (this.m_particleOffsetRandomX != 0.0F) {
/* 167 */       offsetX += (this.m_random.nextFloat() - 0.5F) * this.m_particleOffsetRandomX;
/*     */     }
/* 169 */     if (this.m_particleOffsetRandomY != 0.0F) {
/* 170 */       offsetY += (this.m_random.nextFloat() - 0.5F) * this.m_particleOffsetRandomY;
/*     */     }
/* 172 */     if (this.m_particleOffsetRandomZ != 0.0F) {
/* 173 */       offsetZ += (this.m_random.nextFloat() - 0.5F) * this.m_particleOffsetRandomZ;
/*     */     }
/* 175 */     if (this.m_particleVelocityRandomX != 0.0F) {
/* 176 */       velocityX += (this.m_random.nextFloat() - 0.5F) * this.m_particleVelocityRandomX;
/*     */     }
/* 178 */     if (this.m_particleVelocityRandomY != 0.0F) {
/* 179 */       velocityY += (this.m_random.nextFloat() - 0.5F) * this.m_particleVelocityRandomY;
/*     */     }
/* 181 */     if (this.m_particleVelocityRandomZ != 0.0F) {
/* 182 */       velocityZ += (this.m_random.nextFloat() - 0.5F) * this.m_particleVelocityRandomZ;
/*     */     }
/* 184 */     particle.setX(this.m_particleSystem.getXFromSystemCenter() + offsetX);
/* 185 */     particle.setY(this.m_particleSystem.getYFromSystemCenter() + offsetY);
/* 186 */     particle.setZ(this.m_particleSystem.getZFromSystemCenter() + offsetZ);
/*     */     
/* 188 */     particle.setVelocityX(velocityX);
/* 189 */     particle.setVelocityY(velocityY);
/* 190 */     particle.setVelocityZ(velocityZ);
/*     */     
/* 192 */     particle.setLifeTime(this.m_particleLifeTime);
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
/*     */   public boolean isVisible() {
/* 218 */     return this.m_visible;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVisible(boolean visible) {
/* 226 */     this.m_visible = visible;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParticleSystem(ParticleSystem particleSystem) {
/* 234 */     this.m_particleSystem = particleSystem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAffector(BaseAffector a) {
/* 244 */     this.m_particleAffector.add(a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAffector(BaseAffector a) {
/* 253 */     this.m_particleAffector.remove(a);
/*     */   }
/*     */ 
/*     */   
/*     */   public ArrayList<BaseAffector> getAffectors() {
/* 258 */     return this.m_particleAffector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addParticleModel(ParticleModel p) {
/* 267 */     this.m_particlesModel.add(p);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeParticleModel(ParticleModel p) {
/* 276 */     this.m_particlesModel.remove(p);
/*     */   }
/*     */ 
/*     */   
/*     */   public ArrayList<ParticleModel> getParticlesModel() {
/* 281 */     return this.m_particlesModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParticleModel getRandomParticleModel() {
/* 290 */     if (this.m_particlesModel.size() == 0) {
/* 291 */       return null;
/*     */     }
/* 293 */     int id = this.m_random.nextInt(this.m_particlesModel.size());
/*     */     
/* 295 */     return this.m_particlesModel.get(id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<Particle> getParticles() {
/* 303 */     return this.m_particles;
/*     */   }
/*     */   
/*     */   public int getStartSpawnTime() {
/* 307 */     return this.m_startSpawnTime;
/*     */   }
/*     */   
/*     */   public void setStartSpawnTime(int startSpawnTime) {
/* 311 */     this.m_startSpawnTime = startSpawnTime;
/*     */   }
/*     */   
/*     */   public int getEndSpawnTime() {
/* 315 */     return this.m_endSpawnTime;
/*     */   }
/*     */   
/*     */   public void setEndSpawnTime(int endSpawnTime) {
/* 319 */     this.m_endSpawnTime = endSpawnTime;
/*     */   }
/*     */   
/*     */   public void setParticleLifeTime(int lifeTime) {
/* 323 */     this.m_particleLifeTime = lifeTime;
/*     */   }
/*     */   
/*     */   public void setSpawnFrequency(int spawnFrequency) {
/* 327 */     this.m_spawnFrequency = spawnFrequency;
/*     */   }
/*     */   
/*     */   public void setMaxParticlesCount(int maxParticlesCount) {
/* 331 */     this.m_maxParticlesCount = maxParticlesCount;
/*     */   }
/*     */   
/*     */   public void setMaxParticlesPerSpawn(int maxParticlesPerSpawn) {
/* 335 */     this.m_maxParticlesPerSpawn = maxParticlesPerSpawn;
/*     */   }
/*     */   
/*     */   public void setParticleVelocityX(float particleVelocityX) {
/* 339 */     this.m_particleVelocityX = particleVelocityX;
/*     */   }
/*     */   
/*     */   public void setParticleVelocityRandomX(float particleVelocityRandomX) {
/* 343 */     this.m_particleVelocityRandomX = particleVelocityRandomX;
/*     */   }
/*     */   
/*     */   public void setParticleVelocityY(float particleVelocityY) {
/* 347 */     this.m_particleVelocityY = particleVelocityY;
/*     */   }
/*     */   
/*     */   public void setParticleVelocityRandomY(float particleVelocityRandomY) {
/* 351 */     this.m_particleVelocityRandomY = particleVelocityRandomY;
/*     */   }
/*     */   
/*     */   public void setParticleVelocityZ(float particleVelocityZ) {
/* 355 */     this.m_particleVelocityZ = particleVelocityZ;
/*     */   }
/*     */   
/*     */   public void setParticleVelocityRandomZ(float particleVelocityRandomZ) {
/* 359 */     this.m_particleVelocityRandomZ = particleVelocityRandomZ;
/*     */   }
/*     */   
/*     */   public void setParticleOffsetX(float particleOffsetX) {
/* 363 */     this.m_particleOffsetX = particleOffsetX;
/*     */   }
/*     */   
/*     */   public void setParticleOffsetRandomX(float particleOffsetRandomX) {
/* 367 */     this.m_particleOffsetRandomX = particleOffsetRandomX;
/*     */   }
/*     */   
/*     */   public void setParticleOffsetY(float particleOffsetY) {
/* 371 */     this.m_particleOffsetY = particleOffsetY;
/*     */   }
/*     */   
/*     */   public void setParticleOffsetRandomY(float particleOffsetRandomY) {
/* 375 */     this.m_particleOffsetRandomY = particleOffsetRandomY;
/*     */   }
/*     */   
/*     */   public void setParticleOffsetZ(float particleOffsetZ) {
/* 379 */     this.m_particleOffsetZ = particleOffsetZ;
/*     */   }
/*     */   
/*     */   public void setParticleOffsetRandomZ(float particleOffsetRandomZ) {
/* 383 */     this.m_particleOffsetRandomZ = particleOffsetRandomZ;
/*     */   }
/*     */   
/*     */   public long getRemainTimeForSpawn() {
/* 387 */     return this.m_remainTimeForSpawn;
/*     */   }
/*     */   
/*     */   public int getSpawnFrequency() {
/* 391 */     return this.m_spawnFrequency;
/*     */   }
/*     */   
/*     */   public int getMaxParticlesCount() {
/* 395 */     return this.m_maxParticlesCount;
/*     */   }
/*     */   
/*     */   public int getMaxParticlesPerSpawn() {
/* 399 */     return this.m_maxParticlesPerSpawn;
/*     */   }
/*     */   
/*     */   public int getParticleLifeTime() {
/* 403 */     return this.m_particleLifeTime;
/*     */   }
/*     */   
/*     */   public float getParticleOffsetX() {
/* 407 */     return this.m_particleOffsetX;
/*     */   }
/*     */   
/*     */   public float getParticleOffsetRandomX() {
/* 411 */     return this.m_particleOffsetRandomX;
/*     */   }
/*     */   
/*     */   public float getParticleOffsetY() {
/* 415 */     return this.m_particleOffsetY;
/*     */   }
/*     */   
/*     */   public float getParticleOffsetRandomY() {
/* 419 */     return this.m_particleOffsetRandomY;
/*     */   }
/*     */   
/*     */   public float getParticleOffsetZ() {
/* 423 */     return this.m_particleOffsetZ;
/*     */   }
/*     */   
/*     */   public float getParticleOffsetRandomZ() {
/* 427 */     return this.m_particleOffsetRandomZ;
/*     */   }
/*     */   
/*     */   public float getParticleVelocityX() {
/* 431 */     return this.m_particleVelocityX;
/*     */   }
/*     */   
/*     */   public float getParticleVelocityRandomX() {
/* 435 */     return this.m_particleVelocityRandomX;
/*     */   }
/*     */   
/*     */   public float getParticleVelocityY() {
/* 439 */     return this.m_particleVelocityY;
/*     */   }
/*     */   
/*     */   public float getParticleVelocityRandomY() {
/* 443 */     return this.m_particleVelocityRandomY;
/*     */   }
/*     */   
/*     */   public float getParticleVelocityZ() {
/* 447 */     return this.m_particleVelocityZ;
/*     */   }
/*     */   
/*     */   public float getParticleVelocityRandomZ() {
/* 451 */     return this.m_particleVelocityRandomZ;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void release() {
/* 458 */     for (Particle p : this.m_particles)
/* 459 */       p.release(); 
/* 460 */     this.m_particles.clear();
/*     */ 
/*     */     
/* 463 */     if (!isLocked()) {
/*     */       
/* 465 */       for (ParticleModel m : this.m_particlesModel)
/* 466 */         m.release(); 
/* 467 */       this.m_particlesModel.clear();
/*     */       
/* 469 */       this.m_particleAffector.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void resetLastSpawnTime() {
/* 474 */     this.m_lastSpawnTime = -1L;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\particlesystem\Emitter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */