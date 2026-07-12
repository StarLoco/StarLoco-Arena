/*    */ package com.ankamagames.framework.graphics.particlesystem.particles;
/*    */ 
/*    */ import com.ankamagames.framework.graphics.animation.instances.DisplayObject;
/*    */ import com.ankamagames.framework.graphics.opengl.base.impl.Mesh2D;
/*    */ import com.ankamagames.framework.graphics.particlesystem.Particle;
/*    */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*    */ import org.apache.commons.pool.ObjectPool;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ParticleSequence
/*    */   extends Particle
/*    */ {
/* 20 */   public static final ObjectPool m_pool = new MonitoredPool(new ObjectFactory() {
/* 21 */     public ParticleSequence makeObject() { return new ParticleSequence(); }
/* 20 */   });
/*    */   
/*    */ 
/*    */   private DisplayObject m_displayObject;
/*    */   
/*    */ 
/* 26 */   private boolean m_alreadyOnScene = false;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setDisplayObject(DisplayObject displayObject)
/*    */   {
/* 33 */     this.m_displayObject = displayObject;
/*    */   }
/*    */   
/*    */   public DisplayObject getDisplayObject() {
/* 37 */     return this.m_displayObject;
/*    */   }
/*    */   
/*    */   public Mesh2D getMesh() {
/* 41 */     return this.m_displayObject.getMesh();
/*    */   }
/*    */   
/*    */   public boolean isAlreadyOnScene() {
/* 45 */     return this.m_alreadyOnScene;
/*    */   }
/*    */   
/*    */   public void setAlreadyOnScene(boolean alreadyOnScene) {
/* 49 */     this.m_alreadyOnScene = alreadyOnScene;
/*    */   }
/*    */   
/*    */   public void release() {
/* 53 */     if (m_pool != null) {
/*    */       try {
/* 55 */         m_pool.returnObject(this);
/*    */       } catch (Exception e) {
/* 57 */         e.printStackTrace();
/*    */       }
/*    */     } else {
/* 60 */       onCheckIn();
/*    */     }
/*    */   }
/*    */   
/*    */   public void onCheckOut() {
/* 65 */     this.m_alreadyOnScene = false;
/*    */     
/* 67 */     super.onCheckOut();
/*    */   }
/*    */   
/*    */   public void onCheckIn() {
/* 71 */     this.m_displayObject.invalidate();
/*    */     
/* 73 */     super.onCheckIn();
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\particlesystem\particles\ParticleSequence.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */