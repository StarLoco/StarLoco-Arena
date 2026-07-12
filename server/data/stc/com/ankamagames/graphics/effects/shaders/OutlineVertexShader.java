/*    */ package com.ankamagames.graphics.effects.shaders;
/*    */ 
/*    */ import com.ankamagames.framework.graphics.opengl.base.effects.ShaderManager;
/*    */ import com.ankamagames.framework.graphics.opengl.base.effects.shaders.ShaderProgram;
/*    */ import com.sun.opengl.cg.CGparameter;
/*    */ import com.sun.opengl.cg.CgGL;
/*    */ import com.sun.opengl.util.texture.Texture;
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
/*    */ public class OutlineVertexShader
/*    */   extends ShaderProgram
/*    */ {
/*    */   private CGparameter m_thickness;
/*    */   
/*    */   public OutlineVertexShader(boolean textureRectSupported, int numTextureUnits)
/*    */   {
/* 25 */     super("outline", "", 8);
/*    */     
/* 27 */     String fileName = ShaderManager.getInstance().getShadersBaseDirectory() + "outline/vs_";
/*    */     
/* 29 */     if (textureRectSupported) {
/* 30 */       fileName = fileName + "rect.cg";
/*    */     } else {
/* 32 */       fileName = fileName + "2D.cg";
/*    */     }
/* 34 */     setFileName(fileName);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void bindParameters()
/*    */   {
/* 42 */     this.m_thickness = CgGL.cgGetNamedParameter(this.m_program, "thickness");
/* 43 */     CgGL.cgGLEnableClientState(this.m_thickness);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void unbindParameters()
/*    */   {
/* 50 */     CgGL.cgGLDisableClientState(this.m_thickness);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setup(GL gl)
/*    */   {
/* 60 */     CgGL.cgGLSetStateMatrixParameter(this.m_projectionMatrix, 5, 0);
/* 61 */     CgGL.cgGLSetStateMatrixParameter(this.m_modelViewMatrix, 4, 0);
/*    */   }
/*    */   
/*    */   public void setTextureParameter(Texture texture) {}
/*    */   
/*    */   public void setThickNessParameter(float thickness)
/*    */   {
/* 68 */     CgGL.cgGLSetParameter1f(this.m_thickness, thickness);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\effects\shaders\OutlineVertexShader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */