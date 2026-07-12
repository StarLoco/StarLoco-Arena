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
/*    */ public class OutlinePixelShader
/*    */   extends ShaderProgram
/*    */ {
/*    */   public OutlinePixelShader(boolean textureRectSupported, int numTextureUnits)
/*    */   {
/* 21 */     super("outline", "", 9);
/*    */     
/* 23 */     String fileName = ShaderManager.getInstance().getShadersBaseDirectory() + "outline/ps_";
/*    */     
/* 25 */     if (textureRectSupported) {
/* 26 */       fileName = fileName + "rect.cg";
/*    */     } else {
/* 28 */       fileName = fileName + "2D.cg";
/*    */     }
/* 30 */     setFileName(fileName);
/*    */   }
/*    */   
/*    */   public void bindParameters() {}
/*    */   
/*    */   public void unbindParameters() {}
/*    */   
/*    */   public void setup(GL gl) {}
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\effects\shaders\OutlinePixelShader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */