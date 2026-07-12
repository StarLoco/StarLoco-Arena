/*    */ package com.ankamagames.framework.graphics.opengl.base;
/*    */ 
/*    */ import com.ankamagames.framework.kernel.core.resource.direct.DirectBufferManager;
/*    */ import java.nio.IntBuffer;
/*    */ import javax.media.opengl.GL;
/*    */ import javax.media.opengl.glu.GLU;
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
/*    */ public class RenderTarget
/*    */ {
/* 20 */   private static IntBuffer m_int = DirectBufferManager.getInstance().getIntBuffer(16);
/*    */   private GL m_gl;
/*    */   private int m_renderTargetId;
/*    */   
/*    */   public RenderTarget()
/*    */   {
/* 26 */     this.m_gl = GLU.getCurrentGL();
/*    */     
/* 28 */     this.m_gl.glGenFramebuffersEXT(1, m_int);
/* 29 */     this.m_renderTargetId = m_int.get(0);
/*    */   }
/*    */   
/*    */   public void release()
/*    */   {
/* 34 */     if (this.m_renderTargetId != 0) {
/* 35 */       m_int.put(0, this.m_renderTargetId);
/* 36 */       m_int.rewind();
/* 37 */       this.m_gl.glDeleteFramebuffersEXT(1, m_int);
/* 38 */       this.m_renderTargetId = 0;
/*    */     }
/*    */   }
/*    */   
/*    */   public void bind() {
/* 43 */     if (this.m_renderTargetId != 0)
/* 44 */       this.m_gl.glBindFramebufferEXT(36160, this.m_renderTargetId);
/*    */   }
/*    */   
/*    */   public void unbind() {
/* 48 */     if (this.m_renderTargetId != 0)
/* 49 */       this.m_gl.glBindFramebufferEXT(36160, 0);
/*    */   }
/*    */   
/*    */   public void attachTexture(Texture texture) {
/* 53 */     bind();
/* 54 */     this.m_gl.glFramebufferTexture2DEXT(36160, 36064, 
/* 55 */       texture.getTarget(), texture.getTextureObject(), 0);
/* 56 */     unbind();
/*    */   }
/*    */   
/*    */   public void detachTexture(Texture texture) {
/* 60 */     bind();
/* 61 */     this.m_gl.glFramebufferTexture2DEXT(36160, 36064, 
/* 62 */       texture.getTarget(), 0, 0);
/* 63 */     unbind();
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\RenderTarget.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */