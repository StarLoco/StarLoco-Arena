/*    */ package com.ankamagames.graphics.isometric.highlight;
/*    */ 
/*    */ import com.ankamagames.framework.graphics.opengl.base.impl.ModifiableMesh2D;
/*    */ import com.ankamagames.framework.graphics.opengl.base.states.GLRenderStates;
/*    */ import com.ankamagames.framework.kernel.core.common.Poolable;
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
/*    */ 
/*    */ public class HighLightMesh
/*    */   extends ModifiableMesh2D
/*    */   implements Poolable
/*    */ {
/* 21 */   private static final GLRenderStates m_preRenderState = new HighLightMeshPreRenderStates();
/* 22 */   private static final GLRenderStates m_postRenderState = new HighLightMeshPostRenderStates();
/*    */   
/*    */   private static final float DEFAULT_SIZE = 43.0F;
/*    */   
/* 26 */   private float m_size = 43.0F;
/*    */   
/*    */ 
/*    */   private ObjectPool m_pool;
/*    */   
/*    */ 
/*    */   public HighLightMesh()
/*    */   {
/* 34 */     setPreRenderStates(m_preRenderState);
/* 35 */     setPostRenderStates(m_postRenderState);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setPool(ObjectPool pool)
/*    */   {
/* 43 */     this.m_pool = pool;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void release()
/*    */   {
/* 50 */     if (this.m_pool != null) {
/*    */       try {
/* 52 */         this.m_pool.returnObject(this);
/*    */       } catch (Exception e) {
/* 54 */         e.printStackTrace();
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public void onCheckIn()
/*    */   {
/* 62 */     super.onCheckIn();
/*    */   }
/*    */   
/*    */   public void onCheckOut()
/*    */   {
/* 67 */     super.onCheckOut();
/*    */     
/* 69 */     setPreRenderStates(m_preRenderState);
/* 70 */     setPostRenderStates(m_postRenderState);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public float getSize()
/*    */   {
/* 77 */     return this.m_size;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setSize(float size)
/*    */   {
/* 85 */     this.m_size = size;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\isometric\highlight\HighLightMesh.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */