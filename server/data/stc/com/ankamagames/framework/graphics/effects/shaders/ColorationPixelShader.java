/*    */ package com.ankamagames.framework.graphics.effects.shaders;
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
/*    */ public class ColorationPixelShader
/*    */   extends ShaderProgram
/*    */ {
/*    */   private CGparameter m_multTerm;
/*    */   private CGparameter m_addTerm;
/*    */   
/*    */   public ColorationPixelShader(boolean texture2D, int numTextureUnits)
/*    */   {
/* 23 */     super("coloration", "", 9);
/*    */     
/* 25 */     String fileName = ShaderManager.getInstance().getShadersBaseDirectory() + "coloration/ps_";
/*    */     
/* 27 */     if (texture2D) {
/* 28 */       fileName = fileName + "2D.cg";
/*    */     } else {
/* 30 */       fileName = fileName + "rect.cg";
/*    */     }
/* 32 */     setFileName(fileName);
/*    */   }
/*    */   
/*    */ 
/*    */   public void bindParameters()
/*    */   {
/* 38 */     this.m_multTerm = CgGL.cgGetNamedParameter(this.m_program, "multTerm");
/* 39 */     CgGL.cgGLEnableClientState(this.m_multTerm);
/* 40 */     this.m_addTerm = CgGL.cgGetNamedParameter(this.m_program, "addTerm");
/* 41 */     CgGL.cgGLEnableClientState(this.m_addTerm);
/*    */   }
/*    */   
/*    */   public void unbindParameters()
/*    */   {
/* 46 */     CgGL.cgGLDisableClientState(this.m_multTerm);
/* 47 */     CgGL.cgGLDisableClientState(this.m_addTerm);
/*    */   }
/*    */   
/*    */ 
/*    */   public void setup(GL gl) {}
/*    */   
/*    */   public void setMultTermColor(float[] multTerm)
/*    */   {
/* 55 */     CgGL.cgGLSetParameter4fv(this.m_multTerm, multTerm, 0);
/*    */   }
/*    */   
/* 58 */   public void setAddTermColor(float[] addTerm) { CgGL.cgGLSetParameter4fv(this.m_addTerm, addTerm, 0); }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\effects\shaders\ColorationPixelShader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */