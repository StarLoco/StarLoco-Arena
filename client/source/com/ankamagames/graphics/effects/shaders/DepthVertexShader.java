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
/*    */ 
/*    */ public class DepthVertexShader
/*    */   extends ShaderProgram
/*    */ {
/*    */   private CGparameter m_depth;
/*    */   
/*    */   public DepthVertexShader(boolean textureRectSupported, int numTextureUnits) {
/* 24 */     super("depth", "", 8);
/*    */     
/* 26 */     String fileName = String.valueOf(ShaderManager.getInstance().getShadersBaseDirectory()) + "depth/vs_";
/* 27 */     if (textureRectSupported) {
/* 28 */       fileName = String.valueOf(fileName) + "rect.cg";
/*    */     } else {
/* 30 */       fileName = String.valueOf(fileName) + "2D.cg";
/*    */     } 
/* 32 */     setFileName(fileName);
/*    */   }
/*    */   
/*    */   public void bindParameters() {
/* 36 */     this.m_depth = CgGL.cgGetNamedParameter(this.m_program, "depth");
/* 37 */     CgGL.cgGLEnableClientState(this.m_depth);
/*    */   }
/*    */ 
/*    */   
/*    */   public void unbindParameters() {}
/*    */   
/*    */   public void setup(GL gl) {
/* 44 */     CgGL.cgGLSetStateMatrixParameter(this.m_projectionMatrix, 5, 0);
/* 45 */     CgGL.cgGLSetStateMatrixParameter(this.m_modelViewMatrix, 4, 0);
/*    */   }
/*    */   
/*    */   public void setDepthParameter(float depth) {
/* 49 */     CgGL.cgGLSetParameter1f(this.m_depth, depth);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\effects\shaders\DepthVertexShader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */