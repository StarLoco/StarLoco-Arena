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
/*    */ public class SeaVertexShader
/*    */   extends ShaderProgram
/*    */ {
/*    */   private CGparameter m_stretch;
/*    */   private CGparameter m_width;
/*    */   
/*    */   public SeaVertexShader(boolean textureRectSupported, int numTextureUnits)
/*    */   {
/* 25 */     super("sea", ShaderManager.getInstance().getShadersBaseDirectory() + "sea/vs.cg", 8);
/*    */   }
/*    */   
/*    */   public void bindParameters() {
/* 29 */     this.m_stretch = CgGL.cgGetNamedParameter(this.m_program, "stretch");
/* 30 */     CgGL.cgGLEnableClientState(this.m_stretch);
/*    */     
/* 32 */     this.m_width = CgGL.cgGetNamedParameter(this.m_program, "width");
/* 33 */     CgGL.cgGLEnableClientState(this.m_width);
/*    */   }
/*    */   
/*    */   public void unbindParameters()
/*    */   {
/* 38 */     CgGL.cgGLDisableClientState(this.m_stretch);
/* 39 */     CgGL.cgGLDisableClientState(this.m_width);
/*    */   }
/*    */   
/*    */   public void setup(GL gl) {
/* 43 */     CgGL.cgGLSetStateMatrixParameter(this.m_projectionMatrix, 5, 0);
/* 44 */     CgGL.cgGLSetStateMatrixParameter(this.m_modelViewMatrix, 4, 0);
/*    */   }
/*    */   
/*    */   public void setStretchParameter(float stretch) {
/* 48 */     CgGL.cgGLSetParameter1f(this.m_stretch, stretch);
/*    */   }
/*    */   
/*    */   public void setWidthParameter(float width) {
/* 52 */     CgGL.cgGLSetParameter1f(this.m_width, width);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\effects\shaders\SeaVertexShader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */