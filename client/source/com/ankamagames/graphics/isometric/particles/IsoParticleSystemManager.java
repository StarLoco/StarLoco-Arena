/*     */ package com.ankamagames.graphics.isometric.particles;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.particlesystem.ParticleSystem;
/*     */ import com.ankamagames.graphics.isometric.IsoWorldScene;
/*     */ import com.ankamagames.graphics.isometric.RenderProcessHandler;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IsoParticleSystemManager
/*     */   implements RenderProcessHandler
/*     */ {
/*  20 */   private static int PARTICLE_SYSTEM_MAX_CELL_COUNT_VISIBILITY = 20;
/*  21 */   private static int PARTICLE_SYSTEM_MIN_CELL_COUNT_FOR_DESTROY = 100;
/*     */   
/*  23 */   private static IsoParticleSystemManager m_instance = new IsoParticleSystemManager();
/*     */   
/*     */   public static IsoParticleSystemManager getInstance() {
/*  26 */     return m_instance;
/*     */   }
/*     */   
/*  29 */   private Map<Integer, IsoParticleSystem> m_particleSystems = new ConcurrentHashMap<Integer, IsoParticleSystem>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCellParticleSystem(CellParticleSystem particleSystem) {
/*  39 */     if (!containCellParticleSystem(particleSystem)) {
/*  40 */       this.m_particleSystems.put(Integer.valueOf(particleSystem.getId()), particleSystem);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containCellParticleSystem(CellParticleSystem particleSystem) {
/*  51 */     for (IsoParticleSystem system : this.m_particleSystems.values()) {
/*  52 */       if (system.getX() == particleSystem.getX() && system.getY() == particleSystem.getY())
/*     */       {
/*  54 */         if (system instanceof CellParticleSystem)
/*     */         {
/*  56 */           if (((CellParticleSystem)system).getLevel() == particleSystem.getLevel()) {
/*  57 */             return true;
/*     */           }
/*     */         }
/*     */       }
/*     */     } 
/*     */     
/*  63 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addParticleSystem(IsoParticleSystem particleSystem) {
/*  73 */     this.m_particleSystems.put(Integer.valueOf(particleSystem.getId()), particleSystem);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeParticleSystem(int id) {
/*  84 */     IsoParticleSystem particleSystem = this.m_particleSystems.get(Integer.valueOf(id));
/*     */     
/*  86 */     if (particleSystem != null) {
/*  87 */       particleSystem.kill();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearParticleSystems() {
/*  95 */     for (ParticleSystem p : this.m_particleSystems.values()) {
/*  96 */       p.kill();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(IsoWorldScene scene, long realTime, int frameCount) {
/* 107 */     for (IsoParticleSystem isoParticleSystem : this.m_particleSystems.values()) {
/* 108 */       isoParticleSystem.process(realTime, frameCount);
/*     */     }
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
/*     */   public void prepareBeforeRendering(IsoWorldScene scene, int centerScreenIsoWorldX, int centerScreenIsoWorldY) {
/* 121 */     Iterator<IsoParticleSystem> it = this.m_particleSystems.values().iterator();
/*     */     
/* 123 */     while (it.hasNext()) {
/*     */       
/* 125 */       IsoParticleSystem system = it.next();
/*     */       
/* 127 */       if (!system.isDead()) {
/*     */         
/* 129 */         double distanceX = system.getX() - centerScreenIsoWorldX;
/* 130 */         double distanceY = system.getY() - centerScreenIsoWorldY;
/* 131 */         double distance = Math.sqrt(Math.pow(distanceX, 2.0D) + Math.pow(distanceY, 2.0D));
/*     */         
/* 133 */         if (distance < PARTICLE_SYSTEM_MAX_CELL_COUNT_VISIBILITY) {
/* 134 */           system.prepareParticlesBeforeRendering(scene, centerScreenIsoWorldX, centerScreenIsoWorldY); continue;
/* 135 */         }  if (distance > PARTICLE_SYSTEM_MIN_CELL_COUNT_FOR_DESTROY)
/*     */         {
/* 137 */           system.kill();
/*     */         }
/*     */         
/*     */         continue;
/*     */       } 
/* 142 */       system.release();
/* 143 */       it.remove();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\isometric\particles\IsoParticleSystemManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */