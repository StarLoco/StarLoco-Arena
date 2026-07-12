/*    */ package com.ankamagames.framework.graphics.particlesystem.states;
/*    */ 
/*    */ import com.ankamagames.framework.graphics.opengl.base.states.GLRenderStates;
/*    */ import javax.media.opengl.GL;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ParticlePreRenderStates
/*    */   implements GLRenderStates
/*    */ {
/*    */   private int m_glTexEnv;
/*    */   private int m_glBlendSrc;
/*    */   private int m_glBlendDst;
/*    */   
/*    */   public void setup(GL gl) {
/* 22 */     gl.glTexEnvi(8960, 8704, this.m_glTexEnv);
/*    */     
/* 24 */     gl.glBlendFunc(this.m_glBlendSrc, this.m_glBlendDst);
/*    */     
/* 26 */     gl.glTexEnvf(8960, 34163, 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setTextureEnvMode(int glParam) {
/* 31 */     this.m_glTexEnv = glParam;
/*    */   }
/*    */   
/*    */   public void setBlendSource(int glBlendSrc) {
/* 35 */     this.m_glBlendSrc = glBlendSrc;
/*    */   }
/*    */   
/*    */   public void setBlendDestination(int glBlendDst) {
/* 39 */     this.m_glBlendDst = glBlendDst;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\particlesystem\states\ParticlePreRenderStates.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */