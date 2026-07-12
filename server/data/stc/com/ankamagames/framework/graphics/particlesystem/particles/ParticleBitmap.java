/*    */ package com.ankamagames.framework.graphics.particlesystem.particles;
/*    */ 
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
/*    */ 
/*    */ public class ParticleBitmap
/*    */   extends Particle
/*    */ {
/* 20 */   private static final ObjectPool m_pool = new MonitoredPool(new ObjectFactory() {
/* 21 */     public ParticleBitmap makeObject() { return new ParticleBitmap(null); }
/* 20 */   });
/*    */   
/*    */ 
/*    */   private Mesh2D m_mesh;
/*    */   
/*    */ 
/*    */   private ParticleBitmap()
/*    */   {
/* 28 */     this.m_mesh = new Mesh2D();
/*    */   }
/*    */   
/*    */   public Mesh2D getMesh() {
/* 32 */     return this.m_mesh;
/*    */   }
/*    */   
/*    */   public static ParticleBitmap checkOut() {
/*    */     try {
/* 37 */       return (ParticleBitmap)m_pool.borrowObject();
/*    */     }
/*    */     catch (Exception e) {
/* 40 */       e.printStackTrace(); }
/* 41 */     return new ParticleBitmap();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void onCheckOut()
/*    */   {
/* 49 */     this.m_mesh.initialize();
/*    */     
/* 51 */     super.onCheckOut();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void onCheckIn()
/*    */   {
/* 58 */     this.m_mesh.uninitialize();
/*    */     
/* 60 */     super.onCheckIn();
/*    */   }
/*    */   
/*    */   public void release()
/*    */   {
/* 65 */     if (m_pool != null) {
/*    */       try {
/* 67 */         m_pool.returnObject(this);
/*    */       } catch (Exception e) {
/* 69 */         e.printStackTrace();
/*    */       }
/*    */     } else {
/*    */       try {
/* 73 */         onCheckIn();
/*    */       } catch (Exception e) {
/* 75 */         e.printStackTrace();
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\particlesystem\particles\ParticleBitmap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */