/*    */ package com.ankamagames.graphics.effects.shaders;
/*    */ 
/*    */ import com.ankamagames.framework.graphics.opengl.base.effects.ShaderManager;
/*    */ import com.ankamagames.framework.graphics.opengl.base.effects.shaders.ShaderProgram;
/*    */ import com.sun.opengl.cg.CGparameter;
/*    */ import com.sun.opengl.cg.CgGL;
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
/*    */ public class WindVertexShader
/*    */   extends ShaderProgram
/*    */ {
/*    */   private CGparameter m_bend;
/*    */   
/*    */   public WindVertexShader(boolean textureRectSupported, int numTextureUnits)
/*    */   {
/* 24 */     super("wind", ShaderManager.getInstance().getShadersBaseDirectory() + "wind/vs.cg", 8);
/*    */   }
/*    */   
/*    */   public void bindParameters() {
/* 28 */     this.m_bend = CgGL.cgGetNamedParameter(this.m_program, "bend");
/* 29 */     CgGL.cgGLEnableClientState(this.m_bend);
/*    */   }
/*    */   
/*    */   public void unbindParameters()
/*    */   {
/* 34 */     CgGL.cgGLDisableClientState(this.m_bend);
/*    */   }
/*    */   
/*    */   public void setup(GL gl) {
/* 38 */     CgGL.cgGLSetStateMatrixParameter(this.m_projectionMatrix, 5, 0);
/* 39 */     CgGL.cgGLSetStateMatrixParameter(this.m_modelViewMatrix, 4, 0);
/*    */   }
/*    */   
/*    */   public void setBendParameter(float bend) {
/* 43 */     CgGL.cgGLSetParameter1f(this.m_bend, bend);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\effects\shaders\WindVertexShader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */