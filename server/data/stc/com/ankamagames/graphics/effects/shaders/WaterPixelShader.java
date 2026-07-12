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
/*    */ public class WaterPixelShader
/*    */   extends ShaderProgram
/*    */ {
/*    */   public WaterPixelShader(boolean textureRectSupported, int numTextureUnits)
/*    */   {
/* 21 */     super("water", "", 9);
/*    */     
/* 23 */     String fileName = ShaderManager.getInstance().getShadersBaseDirectory() + "water/ps_";
/* 24 */     if (textureRectSupported) {
/* 25 */       fileName = fileName + "rect.cg";
/*    */     } else {
/* 27 */       fileName = fileName + "2D.cg";
/*    */     }
/* 29 */     setFileName(fileName);
/*    */   }
/*    */   
/*    */   public void bindParameters() {}
/*    */   
/*    */   public void unbindParameters() {}
/*    */   
/*    */   public void setup(GL gl) {}
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\effects\shaders\WaterPixelShader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */