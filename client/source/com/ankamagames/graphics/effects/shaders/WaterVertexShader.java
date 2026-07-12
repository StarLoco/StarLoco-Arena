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
/*    */ public class WaterVertexShader
/*    */   extends ShaderProgram
/*    */ {
/*    */   private CGparameter m_phase;
/*    */   private CGparameter m_depth;
/*    */   
/*    */   public WaterVertexShader(boolean textureRectSupported, int numTextureUnits) {
/* 25 */     super("mirror", "", 8);
/*    */     
/* 27 */     String fileName = String.valueOf(ShaderManager.getInstance().getShadersBaseDirectory()) + "water/vs_";
/* 28 */     if (textureRectSupported) {
/* 29 */       fileName = String.valueOf(fileName) + "rect.cg";
/*    */     } else {
/* 31 */       fileName = String.valueOf(fileName) + "2D.cg";
/*    */     } 
/* 33 */     setFileName(fileName);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void bindParameters() {
/* 41 */     this.m_phase = CgGL.cgGetNamedParameter(this.m_program, "phase");
/* 42 */     CgGL.cgGLEnableClientState(this.m_phase);
/*    */     
/* 44 */     this.m_depth = CgGL.cgGetNamedParameter(this.m_program, "depth");
/* 45 */     CgGL.cgGLEnableClientState(this.m_depth);
/*    */   }
/*    */ 
/*    */   
/*    */   public void unbindParameters() {
/* 50 */     CgGL.cgGLDisableClientState(this.m_phase);
/* 51 */     CgGL.cgGLDisableClientState(this.m_depth);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setup(GL gl) {
/* 61 */     CgGL.cgGLSetStateMatrixParameter(this.m_projectionMatrix, 5, 0);
/* 62 */     CgGL.cgGLSetStateMatrixParameter(this.m_modelViewMatrix, 4, 0);
/*    */   }
/*    */   
/*    */   public void setPhaseParameter(float phase) {
/* 66 */     CgGL.cgGLSetParameter1f(this.m_phase, phase);
/*    */   }
/*    */   
/*    */   public void setDepthParameter(float depth) {
/* 70 */     CgGL.cgGLSetParameter1f(this.m_depth, depth);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\effects\shaders\WaterVertexShader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */