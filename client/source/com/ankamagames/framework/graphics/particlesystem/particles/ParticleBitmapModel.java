/*    */ package com.ankamagames.framework.graphics.particlesystem.particles;
/*    */ 
/*    */ import com.ankamagames.framework.graphics.image.AlphaBitmapData;
/*    */ import com.ankamagames.framework.graphics.opengl.TextureManager;
/*    */ import com.ankamagames.framework.graphics.opengl.base.BaseTexture;
/*    */ import com.ankamagames.framework.graphics.particlesystem.Particle;
/*    */ import com.ankamagames.framework.graphics.particlesystem.ParticleModel;
/*    */ import com.ankamagames.framework.graphics.particlesystem.ParticleSystem;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ParticleBitmapModel
/*    */   extends ParticleModel
/*    */ {
/*    */   public BaseTexture m_texture;
/*    */   public boolean m_textureNeedUpdate;
/*    */   private int m_bitmapId;
/*    */   
/*    */   public int getBitmapId() {
/* 28 */     return this.m_bitmapId;
/*    */   }
/*    */   
/*    */   public void setBitmapId(int bitmapId) {
/* 32 */     this.m_bitmapId = bitmapId;
/* 33 */     this.m_textureNeedUpdate = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public Particle generateParticle(ParticleSystem particleSystem) {
/*    */     Particle particle;
/* 39 */     if (this.m_textureNeedUpdate || this.m_texture == null) {
/*    */       
/* 41 */       AlphaBitmapData image = particleSystem.getBitmap(getBitmapId());
/*    */       
/* 43 */       if (image == null) {
/* 44 */         return null;
/*    */       }
/* 46 */       this.m_texture = TextureManager.createTexture(image.getWidth(), image.getHeight(), image.getDatas(), 6408);
/* 47 */       this.m_textureNeedUpdate = false;
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 54 */       particle = ParticleBitmap.checkOut();
/* 55 */     } catch (Exception e) {
/* 56 */       e.printStackTrace();
/* 57 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 61 */     particle.getMesh().setTexture(this.m_texture);
/* 62 */     particle.getMesh().computeTextureCoordinate();
/*    */ 
/*    */     
/* 65 */     intializeParticleMesh(particle.getMesh());
/*    */     
/* 67 */     return particle;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void release() {
/* 73 */     this.m_texture = null;
/* 74 */     super.release();
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\particlesystem\particles\ParticleBitmapModel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */