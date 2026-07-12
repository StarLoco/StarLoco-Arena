/*     */ package com.ankamagames.framework.graphics.particlesystem.particles;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.DisplayObjectDescriptor;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.library.AbstractDescriptorLibrary;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.library.BaseDescriptorLibrary;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.library.DescriptorLibraryManager;
/*     */ import com.ankamagames.framework.graphics.particlesystem.Particle;
/*     */ import com.ankamagames.framework.graphics.particlesystem.ParticleModel;
/*     */ import com.ankamagames.framework.graphics.particlesystem.ParticleSystem;
/*     */ import com.ankamagames.framework.graphics.sba.IndexedDefinitionTagBuffer;
/*     */ import com.ankamagames.framework.graphics.sba.IndexedDefinitionTagBufferManager;
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
/*     */ public class ParticleSequenceModel
/*     */   extends ParticleModel
/*     */ {
/*     */   private String m_linkage;
/*     */   private int m_sequenceId;
/*     */   private boolean m_sequenceNeedUpdate;
/*     */   private BaseDescriptorLibrary m_sequence;
/*     */   
/*     */   public Particle generateParticle(ParticleSystem particleSystem) {
/*     */     ParticleSequence particle;
/*  37 */     if (this.m_sequenceNeedUpdate || this.m_sequence == null) {
/*     */       
/*  39 */       byte[] sequenceBuffer = particleSystem.getSequence(this.m_sequenceId);
/*     */       
/*  41 */       if (sequenceBuffer == null) {
/*  42 */         return null;
/*     */       }
/*     */       try {
/*  45 */         IndexedDefinitionTagBuffer indexedBuffer = IndexedDefinitionTagBufferManager.getInstance().getIndexedBuffer(sequenceBuffer);
/*  46 */         this.m_sequence = DescriptorLibraryManager.getInstance().getDescriptorLibrary(indexedBuffer, createSequenceName(Integer.toHexString(particleSystem.hashCode())));
/*     */         
/*  48 */         this.m_sequenceNeedUpdate = false;
/*     */       }
/*  50 */       catch (Exception e) {
/*  51 */         e.printStackTrace();
/*  52 */         return null;
/*     */       } 
/*     */     } 
/*     */     
/*  56 */     DisplayObjectDescriptor descriptor = this.m_sequence.getDescriptor(this.m_linkage);
/*     */     
/*  58 */     if (descriptor == null) {
/*  59 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/*  64 */       particle = (ParticleSequence)ParticleSequence.m_pool.borrowObject();
/*  65 */     } catch (Exception e) {
/*  66 */       e.printStackTrace();
/*  67 */       return null;
/*     */     } 
/*     */ 
/*     */     
/*  71 */     particle.setDisplayObject(descriptor.createInstance((AbstractDescriptorLibrary)this.m_sequence));
/*     */ 
/*     */     
/*  74 */     intializeParticleMesh(particle.getMesh());
/*     */     
/*  76 */     return particle;
/*     */   }
/*     */   
/*     */   public int getSequenceId() {
/*  80 */     return this.m_sequenceId;
/*     */   }
/*     */   
/*     */   public void setSequenceId(int sequenceId) {
/*  84 */     this.m_sequenceId = sequenceId;
/*  85 */     this.m_sequenceNeedUpdate = true;
/*     */   }
/*     */   
/*     */   public String getLinkage() {
/*  89 */     return this.m_linkage;
/*     */   }
/*     */   
/*     */   public void setLinkage(String linkage) {
/*  93 */     this.m_linkage = linkage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void release() {
/* 100 */     if (this.m_sequence != null) {
/* 101 */       this.m_sequence.releaseAllResources();
/* 102 */       DescriptorLibraryManager.getInstance().removeLibrary(this.m_sequence.getName());
/*     */     } 
/*     */     
/* 105 */     super.release();
/*     */   }
/*     */   
/*     */   private String createSequenceName(String s) {
/* 109 */     return String.valueOf(Integer.toString(this.m_sequenceId)) + "@" + s;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\particlesystem\particles\ParticleSequenceModel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */