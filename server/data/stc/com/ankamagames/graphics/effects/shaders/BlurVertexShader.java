/*    */ package com.ankamagames.graphics.effects.shaders;
/*    */ 
/*    */ import com.ankamagames.framework.graphics.opengl.base.effects.ShaderManager;
/*    */ import com.ankamagames.framework.graphics.opengl.base.effects.shaders.ShaderProgram;
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
/*    */ public class BlurVertexShader
/*    */   extends ShaderProgram
/*    */ {
/*    */   public BlurVertexShader(boolean textureRectSupported, int numTextureUnits)
/*    */   {
/* 21 */     super("blur", "", 8);
/*    */     
/* 23 */     String fileName = ShaderManager.getInstance().getShadersBaseDirectory() + "blur/vs_8u_";
/*    */     
/* 25 */     if (textureRectSupported) {
/* 26 */       fileName = fileName + "rect.cg";
/*    */     } else {
/* 28 */       fileName = fileName + "2D.cg";
/*    */     }
/* 30 */     setFileName(fileName);
/*    */   }
/*    */   
/*    */ 
/*    */   public void bindParameters() {}
/*    */   
/*    */ 
/*    */   public void unbindParameters() {}
/*    */   
/*    */   public void setup(GL gl)
/*    */   {
/* 41 */     CgGL.cgGLSetStateMatrixParameter(this.m_projectionMatrix, 5, 0);
/* 42 */     CgGL.cgGLSetStateMatrixParameter(this.m_modelViewMatrix, 4, 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\effects\shaders\BlurVertexShader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */