/*    */ package com.ankamagames.graphics.effects.shaders;
/*    */ 
/*    */ import com.ankamagames.framework.graphics.opengl.base.effects.ShaderManager;
/*    */ import com.ankamagames.framework.graphics.opengl.base.effects.shaders.ShaderProgram;
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
/*    */ 
/*    */ public class GreyScalePixelShader
/*    */   extends ShaderProgram
/*    */ {
/*    */   public GreyScalePixelShader(boolean textureRectSupported, int numTextureUnits)
/*    */   {
/* 22 */     super("greyscale", "", 9);
/*    */     
/* 24 */     String fileName = ShaderManager.getInstance().getShadersBaseDirectory() + "greyscale/ps_";
/*    */     
/* 26 */     if (textureRectSupported) {
/* 27 */       fileName = fileName + "rect.cg";
/*    */     } else {
/* 29 */       fileName = fileName + "2D.cg";
/*    */     }
/* 31 */     setFileName(fileName);
/*    */   }
/*    */   
/*    */   public void bindParameters() {}
/*    */   
/*    */   public void unbindParameters() {}
/*    */   
/*    */   public void setup(GL gl) {}
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\effects\shaders\GreyScalePixelShader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */