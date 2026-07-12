/*    */ package com.ankamagames.graphics.effects;
/*    */ 
/*    */ import com.ankamagames.framework.graphics.opengl.base.Mesh;
/*    */ import com.ankamagames.framework.graphics.opengl.base.effects.Effect;
/*    */ import com.ankamagames.framework.graphics.opengl.base.effects.EffectContext;
/*    */ import com.ankamagames.framework.graphics.opengl.base.effects.ShaderManager;
/*    */ import com.ankamagames.framework.graphics.opengl.base.effects.shaders.ShaderProgram;
/*    */ import com.ankamagames.graphics.effects.shaders.GreyScalePixelShader;
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
/*    */ 
/*    */ public class GreyScaleEffect
/*    */   extends Effect
/*    */ {
/*    */   public static final String NAME = "greyscale";
/*    */   private ShaderProgram m_ps;
/*    */   private Mesh m_mesh;
/*    */   
/*    */   public GreyScaleEffect() {
/* 30 */     boolean textureRectSupported = this.m_requirements.isTextureRectangleSupported();
/* 31 */     int numTextureUnits = this.m_requirements.getMaxTextureUnits();
/*    */     
/* 33 */     this.m_ps = (ShaderProgram)new GreyScalePixelShader(textureRectSupported, numTextureUnits);
/*    */     
/*    */     try {
/* 36 */       ShaderManager.getInstance().enablePixelShader(this.m_ps);
/* 37 */       this.m_ps.unbind();
/*    */     }
/* 39 */     catch (Exception e) {
/* 40 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void preProcess(long realTime, int frameCount) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int process(Mesh mesh, long realTime, int frameCount) {
/* 56 */     this.m_mesh = mesh;
/* 57 */     return 2;
/*    */   }
/*    */   
/*    */   public void draw(GL gl, EffectContext context) {
/* 61 */     this.m_ps.bind();
/* 62 */     this.m_mesh.drawPrimitives(gl);
/* 63 */     this.m_ps.unbind();
/*    */   }
/*    */   
/*    */   public EffectContext getNewContext() {
/* 67 */     return null;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\effects\GreyScaleEffect.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */