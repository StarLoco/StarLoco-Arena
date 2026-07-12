/*    */ package com.ankamagames.graphics.effects;
/*    */ 
/*    */ import com.ankamagames.framework.graphics.opengl.base.Mesh;
/*    */ import com.ankamagames.framework.graphics.opengl.base.effects.Effect;
/*    */ import com.ankamagames.framework.graphics.opengl.base.effects.EffectContext;
/*    */ import com.ankamagames.framework.graphics.opengl.base.effects.EffectRequirement;
/*    */ import com.ankamagames.framework.graphics.opengl.base.effects.ShaderManager;
/*    */ import com.ankamagames.framework.graphics.opengl.base.effects.shaders.ShaderProgram;
/*    */ import com.ankamagames.graphics.effects.shaders.BlurPixelShader;
/*    */ import com.ankamagames.graphics.effects.shaders.BlurVertexShader;
/*    */ import javax.media.opengl.GL;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlurEffect
/*    */   extends Effect
/*    */ {
/*    */   public static final String NAME = "blur";
/*    */   private ShaderProgram m_vs;
/*    */   private ShaderProgram m_ps;
/*    */   private Mesh m_mesh;
/*    */   
/*    */   public BlurEffect()
/*    */   {
/* 29 */     this.m_name = "blur";
/* 30 */     this.m_requirements = new EffectRequirement(6153, 6162, 8);
/*    */     
/* 32 */     boolean textureRectSupported = this.m_requirements.isTextureRectangleSupported();
/* 33 */     int numTextureUnits = this.m_requirements.getMaxTextureUnits();
/*    */     
/* 35 */     this.m_vs = new BlurVertexShader(textureRectSupported, numTextureUnits);
/* 36 */     this.m_ps = new BlurPixelShader(textureRectSupported, numTextureUnits);
/*    */     try
/*    */     {
/* 39 */       ShaderManager.getInstance().enableVertexShader(this.m_vs);
/* 40 */       this.m_vs.unbind();
/*    */       
/* 42 */       ShaderManager.getInstance().enablePixelShader(this.m_ps);
/* 43 */       this.m_ps.unbind();
/*    */     }
/*    */     catch (Exception e) {
/* 46 */       e.printStackTrace();
/*    */     }
/*    */   }
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
/*    */   public int process(Mesh mesh, long realTime, int frameCount)
/*    */   {
/* 62 */     this.m_mesh = mesh;
/* 63 */     return 2;
/*    */   }
/*    */   
/*    */   public void draw(GL gl, EffectContext context)
/*    */   {
/* 68 */     this.m_ps.bind();
/* 69 */     this.m_vs.bind();
/*    */     
/* 71 */     this.m_mesh.drawPrimitives(gl);
/*    */     
/* 73 */     this.m_vs.unbind();
/* 74 */     this.m_ps.unbind();
/*    */   }
/*    */   
/*    */   public EffectContext getNewContext()
/*    */   {
/* 79 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\effects\BlurEffect.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */