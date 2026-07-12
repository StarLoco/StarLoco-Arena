/*     */ package com.ankamagames.graphics.isometric.particles;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.opengl.base.Scene;
/*     */ import com.ankamagames.framework.graphics.opengl.base.animation.AnimatedObject;
/*     */ import com.ankamagames.framework.graphics.opengl.base.animation.AnimationManager;
/*     */ import com.ankamagames.framework.graphics.opengl.base.render.GLObject;
/*     */ import com.ankamagames.framework.graphics.particlesystem.Emitter;
/*     */ import com.ankamagames.framework.graphics.particlesystem.Particle;
/*     */ import com.ankamagames.framework.graphics.particlesystem.ParticleSystem;
/*     */ import com.ankamagames.framework.graphics.particlesystem.particles.ParticleSequence;
/*     */ import com.ankamagames.graphics.isometric.IsoWorldScene;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class IsoParticleSystem
/*     */   extends ParticleSystem
/*     */ {
/*  24 */   private static int CURRENT_ID = 1;
/*     */   
/*     */   private static int getNexFreeId() {
/*  27 */     return CURRENT_ID++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int m_id;
/*     */ 
/*     */ 
/*     */   
/*     */   public IsoParticleSystem() {
/*  38 */     this.m_id = getNexFreeId();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getId() {
/*  46 */     return this.m_id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void prepareParticlesBeforeRendering(IsoWorldScene scene, int centerScreenIsoWorldX, int centerScreenIsoWorldY) {
/*  57 */     for (Iterator<Emitter> itEmitter = getEmitters().iterator(); itEmitter.hasNext(); ) {
/*     */       
/*  59 */       Emitter emitter = itEmitter.next();
/*     */       
/*  61 */       if (!emitter.isDead()) {
/*     */         
/*  63 */         for (Iterator<Particle> itParticle = emitter.getParticles().iterator(); itParticle.hasNext(); ) {
/*     */           
/*  65 */           Particle particle = itParticle.next();
/*     */           
/*  67 */           if (particle instanceof ParticleSequence) {
/*     */             
/*  69 */             ParticleSequence s = (ParticleSequence)particle;
/*  70 */             if (!s.isAlreadyOnScene()) {
/*     */               
/*  72 */               AnimationManager.getInstance().addAnimatedObject((Scene)scene, (AnimatedObject)s.getDisplayObject());
/*  73 */               s.setAlreadyOnScene(true);
/*     */             } 
/*     */           } 
/*     */           
/*  77 */           if (!particle.isDead()) {
/*     */             
/*  79 */             double isoX = particle.getX() - centerScreenIsoWorldX;
/*  80 */             double isoY = particle.getY() - centerScreenIsoWorldY;
/*  81 */             double isoZ = particle.getZ();
/*     */ 
/*     */             
/*  84 */             if (this.m_geocentric) {
/*  85 */               isoX += getX();
/*  86 */               isoY += getY();
/*  87 */               isoZ += getZ();
/*     */             } 
/*     */             
/*  90 */             double rx = scene.isoToScreenX(isoX, isoY);
/*  91 */             double ry = scene.isoToScreenY(isoX, isoY);
/*     */             
/*  93 */             particle.getMesh().setScreenPosition((float)rx, (float)(ry + isoZ * scene.getElevationUnit()));
/*  94 */             particle.getMesh().setZOrder((float)(-Math.floor(ry) + isoZ + scene.getCellHeight() * 0.5D) / scene.getFrustumHeight());
/*     */             
/*  96 */             scene.addChild((GLObject)particle.getMesh());
/*     */             
/*     */             continue;
/*     */           } 
/* 100 */           particle.release();
/* 101 */           itParticle.remove();
/*     */         } 
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 107 */       emitter.release();
/* 108 */       itEmitter.remove();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\isometric\particles\IsoParticleSystem.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */