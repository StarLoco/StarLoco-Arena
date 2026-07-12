/*     */ package com.ankamagames.framework.graphics.particlesystem;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.image.AlphaBitmapData;
/*     */ import gnu.trove.TIntObjectHashMap;
/*     */ import java.util.ArrayList;
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
/*     */ public abstract class ParticleSystem
/*     */   extends LifeObject
/*     */ {
/*     */   protected TIntObjectHashMap<AlphaBitmapData> m_bitmapLibrary;
/*     */   protected TIntObjectHashMap<byte[]> m_sequenceLibrary;
/*     */   private ArrayList<Emitter> m_emitters;
/*  24 */   protected String m_systemName = "";
/*     */   
/*     */   protected boolean m_geocentric;
/*     */   
/*     */   protected int m_duration;
/*     */   protected int m_currentDuration;
/*     */   protected double m_x;
/*     */   protected double m_y;
/*     */   protected double m_z;
/*     */   
/*     */   public ParticleSystem() {
/*  35 */     this.m_bitmapLibrary = new TIntObjectHashMap();
/*  36 */     this.m_sequenceLibrary = new TIntObjectHashMap();
/*  37 */     this.m_emitters = new ArrayList<Emitter>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(long realTime, int frameCount) {
/*  46 */     if (getSystemDuration() > 0) {
/*  47 */       this.m_currentDuration++;
/*     */     }
/*     */     
/*  50 */     boolean atLeastOneEmitterAlive = false;
/*     */     
/*  52 */     for (Emitter emitter : getEmitters()) {
/*  53 */       emitter.processAffectors(realTime, frameCount, this);
/*     */       
/*  55 */       boolean atLeastOneAliveParticle = false;
/*     */       
/*  57 */       for (Particle particle : emitter.getParticles()) {
/*  58 */         particle.process(realTime, frameCount);
/*     */         
/*  60 */         if (!particle.isDead()) {
/*  61 */           atLeastOneAliveParticle = true;
/*     */         }
/*     */       } 
/*     */       
/*  65 */       if (this.m_currentDuration <= getSystemDuration()) {
/*  66 */         if (emitter.canSpawnParticles(this.m_currentDuration)) {
/*  67 */           emitter.spawnParticles(realTime, frameCount, this);
/*     */         }
/*     */         continue;
/*     */       } 
/*  71 */       if (!atLeastOneAliveParticle) {
/*  72 */         emitter.kill();
/*     */         continue;
/*     */       } 
/*  75 */       atLeastOneEmitterAlive = true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  80 */     if (!atLeastOneEmitterAlive && this.m_currentDuration > getSystemDuration()) {
/*  81 */       kill();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void release() {
/*  90 */     for (Emitter e : this.m_emitters)
/*  91 */       e.release(); 
/*  92 */     this.m_emitters.clear();
/*     */     
/*  94 */     this.m_bitmapLibrary.clear();
/*     */     
/*  96 */     this.m_sequenceLibrary.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEmitter(Emitter emitter) {
/* 104 */     this.m_emitters.add(emitter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeEmitter(Emitter emitter) {
/* 112 */     this.m_emitters.remove(emitter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearEmitters() {
/* 120 */     this.m_emitters.clear();
/*     */   }
/*     */   
/*     */   public TIntObjectHashMap<AlphaBitmapData> getBitmapLibrary() {
/* 124 */     return this.m_bitmapLibrary;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearBitmapLibrary() {
/* 129 */     this.m_bitmapLibrary.clear();
/*     */   }
/*     */   
/*     */   public AlphaBitmapData getBitmap(int id) {
/* 133 */     return (AlphaBitmapData)this.m_bitmapLibrary.get(id);
/*     */   }
/*     */   
/*     */   public void addBitmap(int id, AlphaBitmapData image) {
/* 137 */     this.m_bitmapLibrary.put(id, image);
/*     */   }
/*     */   
/*     */   public void removeBitmap(int id) {
/* 141 */     this.m_bitmapLibrary.remove(id);
/*     */   }
/*     */   
/*     */   public TIntObjectHashMap<byte[]> getSequenceLibrary() {
/* 145 */     return this.m_sequenceLibrary;
/*     */   }
/*     */   
/*     */   public byte[] getSequence(int id) {
/* 149 */     return (byte[])this.m_sequenceLibrary.get(id);
/*     */   }
/*     */   
/*     */   public void addSequence(int id, byte[] sequenceBuffer) {
/* 153 */     this.m_sequenceLibrary.put(id, sequenceBuffer);
/*     */   }
/*     */   
/*     */   public void removeSequence(int id) {
/* 157 */     this.m_sequenceLibrary.remove(id);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearSequenceLibrary() {
/* 162 */     this.m_sequenceLibrary.clear();
/*     */   }
/*     */   
/*     */   public int getSystemDuration() {
/* 166 */     return this.m_duration;
/*     */   }
/*     */   
/*     */   public void setDuration(int duration) {
/* 170 */     this.m_duration = duration;
/*     */   }
/*     */   
/*     */   public void resetCurrentDuration() {
/* 174 */     this.m_currentDuration = 0;
/* 175 */     for (Emitter emitter : this.m_emitters) {
/* 176 */       emitter.resetLastSpawnTime();
/*     */     }
/*     */   }
/*     */   
/*     */   public String getSystemName() {
/* 181 */     return this.m_systemName;
/*     */   }
/*     */   
/*     */   public void setSystemName(String systemName) {
/* 185 */     this.m_systemName = systemName;
/*     */   }
/*     */   
/*     */   public void setX(double x) {
/* 189 */     this.m_x = x;
/*     */   }
/*     */   
/*     */   public void setY(double y) {
/* 193 */     this.m_y = y;
/*     */   }
/*     */   
/*     */   public void setZ(double z) {
/* 197 */     this.m_z = z;
/*     */   }
/*     */   
/*     */   public boolean isGeocentric() {
/* 201 */     return this.m_geocentric;
/*     */   }
/*     */   
/*     */   public void setGeocentric(boolean geocentric) {
/* 205 */     this.m_geocentric = geocentric;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getXFromSystemCenter() {
/* 214 */     if (this.m_geocentric) {
/* 215 */       return 0.0D;
/*     */     }
/* 217 */     return getX();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getYFromSystemCenter() {
/* 226 */     if (this.m_geocentric) {
/* 227 */       return 0.0D;
/*     */     }
/* 229 */     return getY();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getZFromSystemCenter() {
/* 238 */     if (this.m_geocentric) {
/* 239 */       return 0.0D;
/*     */     }
/* 241 */     return getZ();
/*     */   }
/*     */   
/*     */   public double getX() {
/* 245 */     return this.m_x;
/*     */   }
/*     */   
/*     */   public double getY() {
/* 249 */     return this.m_y;
/*     */   }
/*     */   
/*     */   public double getZ() {
/* 253 */     return this.m_z;
/*     */   }
/*     */   
/*     */   public ArrayList<Emitter> getEmitters() {
/* 257 */     return this.m_emitters;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\particlesystem\ParticleSystem.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */