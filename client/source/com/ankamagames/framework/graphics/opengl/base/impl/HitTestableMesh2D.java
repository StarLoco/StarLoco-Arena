/*     */ package com.ankamagames.framework.graphics.opengl.base.impl;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.opengl.base.render.GLObject;
/*     */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*     */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*     */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*     */ import javax.media.opengl.GL;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.commons.pool.PoolableObjectFactory;
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
/*     */ public class HitTestableMesh2D
/*     */   extends Mesh2D
/*     */ {
/*     */   public HitTestableMesh2D() {
/*  29 */     this.m_screenBounds = new float[16];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHitTestable() {
/*  38 */     return true;
/*     */   }
/*     */   private static final ObjectPool m_pool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<HitTestableMesh2D>() { public HitTestableMesh2D makeObject() {
/*     */           return new HitTestableMesh2D();
/*     */         } }
/*     */     );
/*     */   private static final boolean USE_POOL = true;
/*     */   private float[] m_screenBounds;
/*     */   
/*     */   public void setHeight(float height) {
/*  48 */     super.setHeight(height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWidth(float width) {
/*  59 */     super.setWidth(width);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void calculateOnScreenCoordinates(GL gl) {
/*  69 */     if (this.m_screenBounds != null) {
/*  70 */       gl.glGetFloatv(2982, this.m_screenBounds, 0);
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
/*     */   public boolean hitTest(float x, float y) {
/*  82 */     boolean hit = false;
/*  83 */     if (this.m_screenBounds != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  88 */       float ux = this.m_screenBounds[0] * this.m_width;
/*  89 */       float uy = this.m_screenBounds[1] * this.m_width;
/*     */       
/*  91 */       float p1x = x - this.m_screenBounds[12];
/*  92 */       float p1y = y - this.m_screenBounds[13];
/*     */ 
/*     */       
/*  95 */       if (p1x * ux + p1y * uy > 0.0F) {
/*  96 */         float vx = this.m_screenBounds[4] * this.m_height;
/*  97 */         float vy = this.m_screenBounds[5] * this.m_height;
/*  98 */         float p2x = p1x - ux - vx;
/*  99 */         float p2y = p1y - uy - vy;
/*     */ 
/*     */         
/* 102 */         if (p2x * -ux + p2y * -uy > 0.0F)
/*     */         {
/* 104 */           if (p1x * vx + p1y * vy > 0.0F)
/*     */           {
/* 106 */             if (p2x * -vx + p2y * -vy > 0.0F) {
/* 107 */               hit = true;
/*     */             }
/*     */           }
/*     */         }
/*     */       } 
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
/* 127 */       if (!hit && this.m_children.size() != 0) {
/* 128 */         for (GLObject mesh : getChildren()) {
/* 129 */           if (((HitTestableMesh2D)mesh).hitTest(x, y)) {
/* 130 */             return true;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/* 135 */     return hit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCheckIn() {
/* 145 */     super.onCheckIn();
/* 146 */     this.m_screenBounds = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCheckOut() {
/* 156 */     super.onCheckOut();
/* 157 */     this.m_screenBounds = new float[16];
/*     */   }
/*     */ 
/*     */   
/*     */   public void release() {
/*     */     try {
/* 163 */       m_pool.returnObject(this);
/* 164 */     } catch (Exception ex) {
/* 165 */       m_logger.error("HitTestableMesh2D exception raised : ", ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HitTestableMesh2D getNewHitTestableMesh2D() {
/*     */     HitTestableMesh2D mesh;
/*     */     try {
/* 174 */       mesh = (HitTestableMesh2D)m_pool.borrowObject();
/* 175 */     } catch (Exception ex) {
/* 176 */       mesh = new HitTestableMesh2D();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 181 */     return mesh;
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 185 */     int count = 3000;
/* 186 */     long time = System.nanoTime(); int i;
/* 187 */     for (i = 0; i < count; i++) {
/* 188 */       HitTestableMesh2D mesh = new HitTestableMesh2D();
/* 189 */       float f = mesh.getHotX();
/*     */     } 
/* 191 */     time = System.nanoTime() - time;
/* 192 */     System.out.println("new " + ((float)time / 1000000.0F) + " ms");
/*     */     
/* 194 */     time = System.nanoTime();
/* 195 */     for (i = 0; i < count; i++) {
/* 196 */       HitTestableMesh2D mesh = getNewHitTestableMesh2D();
/* 197 */       float k = mesh.getHotX();
/* 198 */       mesh.release();
/*     */     } 
/* 200 */     time = System.nanoTime() - time;
/* 201 */     System.out.println("ppol " + ((float)time / 1000000.0F) + " ms");
/*     */     
/* 203 */     time = System.nanoTime();
/* 204 */     for (i = 0; i < count; i++) {
/* 205 */       HitTestableMesh2D mesh = new HitTestableMesh2D();
/* 206 */       float f = mesh.getHotX();
/*     */     } 
/* 208 */     time = System.nanoTime() - time;
/* 209 */     System.out.println("new " + ((float)time / 1000000.0F) + " ms");
/*     */     
/* 211 */     time = System.nanoTime();
/* 212 */     for (i = 0; i < count; i++) {
/* 213 */       HitTestableMesh2D mesh = getNewHitTestableMesh2D();
/* 214 */       float k = mesh.getHotX();
/* 215 */       mesh.release();
/*     */     } 
/* 217 */     time = System.nanoTime() - time;
/* 218 */     System.out.println("pool" + ((float)time / 1000000.0F) + " ms");
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\impl\HitTestableMesh2D.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */