/*    */ package com.ankamagames.framework.graphics.particlesystem.particles;
/*    */ 
/*    */ import com.ankamagames.framework.graphics.opengl.base.impl.Mesh2D;
/*    */ import com.ankamagames.framework.graphics.particlesystem.Particle;
/*    */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*    */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*    */ import org.apache.commons.pool.ObjectPool;
/*    */ import org.apache.commons.pool.PoolableObjectFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ParticleBitmap
/*    */   extends Particle
/*    */ {
/* 20 */   private static final ObjectPool m_pool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<ParticleBitmap>() { public ParticleBitmap makeObject() {
/* 21 */           return new ParticleBitmap(null);
/*    */         } }
/*    */     );
/*    */   
/*    */   private Mesh2D m_mesh;
/*    */   
/*    */   private ParticleBitmap() {
/* 28 */     this.m_mesh = new Mesh2D();
/*    */   }
/*    */   
/*    */   public Mesh2D getMesh() {
/* 32 */     return this.m_mesh;
/*    */   }
/*    */   
/*    */   public static ParticleBitmap checkOut() {
/*    */     try {
/* 37 */       ParticleBitmap particleBitmap = (ParticleBitmap)m_pool.borrowObject();
/* 38 */       return particleBitmap;
/* 39 */     } catch (Exception e) {
/* 40 */       e.printStackTrace();
/* 41 */       return new ParticleBitmap();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onCheckOut() {
/* 49 */     this.m_mesh.initialize();
/*    */     
/* 51 */     super.onCheckOut();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onCheckIn() {
/* 58 */     this.m_mesh.uninitialize();
/*    */     
/* 60 */     super.onCheckIn();
/*    */   }
/*    */ 
/*    */   
/*    */   public void release() {
/* 65 */     if (m_pool != null) {
/*    */       try {
/* 67 */         m_pool.returnObject(this);
/* 68 */       } catch (Exception e) {
/* 69 */         e.printStackTrace();
/*    */       } 
/*    */     } else {
/*    */       try {
/* 73 */         onCheckIn();
/* 74 */       } catch (Exception e) {
/* 75 */         e.printStackTrace();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\particlesystem\particles\ParticleBitmap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */