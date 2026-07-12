/*     */ package com.ankamagames.graphics.effects;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.opengl.base.Mesh;
/*     */ import com.ankamagames.framework.graphics.opengl.base.effects.Effect;
/*     */ import com.ankamagames.framework.graphics.opengl.base.effects.EffectContext;
/*     */ import com.ankamagames.framework.graphics.opengl.base.effects.ShaderManager;
/*     */ import com.ankamagames.framework.graphics.opengl.base.effects.shaders.ShaderProgram;
/*     */ import com.ankamagames.framework.graphics.opengl.base.impl.Mesh2D;
/*     */ import com.ankamagames.graphics.effects.shaders.DepthPixelShader;
/*     */ import com.ankamagames.graphics.effects.shaders.DepthVertexShader;
/*     */ import com.ankamagames.graphics.effects.shaders.WaterPixelShader;
/*     */ import com.ankamagames.graphics.effects.shaders.WaterVertexShader;
/*     */ import javax.media.opengl.GL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RippleEffect
/*     */   extends Effect
/*     */ {
/*     */   public static final String NAME = "water";
/*     */   private ShaderProgram m_waterVs;
/*     */   private ShaderProgram m_waterPs;
/*     */   private ShaderProgram m_depthVs;
/*     */   private ShaderProgram m_depthPs;
/*     */   private Mesh2D m_mesh;
/*     */   private float m_phase;
/*     */   private float m_depth;
/*     */   
/*     */   public RippleEffect() {
/*  39 */     boolean textureRectSupported = this.m_requirements.isTextureRectangleSupported();
/*  40 */     int numTextureUnits = this.m_requirements.getMaxTextureUnits();
/*     */     
/*  42 */     this.m_waterVs = (ShaderProgram)new WaterVertexShader(textureRectSupported, numTextureUnits);
/*  43 */     this.m_waterPs = (ShaderProgram)new WaterPixelShader(textureRectSupported, numTextureUnits);
/*  44 */     this.m_depthVs = (ShaderProgram)new DepthVertexShader(textureRectSupported, numTextureUnits);
/*  45 */     this.m_depthPs = (ShaderProgram)new DepthPixelShader(textureRectSupported, numTextureUnits);
/*     */     
/*  47 */     this.m_phase = 0.0F;
/*  48 */     this.m_depth = 30.0F;
/*     */     
/*     */     try {
/*  51 */       ShaderManager.getInstance().enableVertexShader(this.m_waterVs);
/*  52 */       this.m_waterVs.unbind();
/*     */       
/*  54 */       ShaderManager.getInstance().enablePixelShader(this.m_waterPs);
/*  55 */       this.m_waterPs.unbind();
/*     */       
/*  57 */       ShaderManager.getInstance().enableVertexShader(this.m_depthVs);
/*  58 */       this.m_depthVs.unbind();
/*     */       
/*  60 */       ShaderManager.getInstance().enablePixelShader(this.m_depthPs);
/*  61 */       this.m_depthPs.unbind();
/*     */     }
/*  63 */     catch (Exception e) {
/*  64 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void preProcess(long realTime, int frameCount) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int process(Mesh mesh, long realTime, int frameCount) {
/*  80 */     this.m_mesh = (Mesh2D)mesh;
/*     */     
/*  82 */     ((WaterVertexShader)this.m_waterVs).setPhaseParameter(this.m_phase);
/*  83 */     ((WaterVertexShader)this.m_waterVs).setDepthParameter(this.m_depth);
/*  84 */     ((DepthVertexShader)this.m_depthVs).setDepthParameter(this.m_depth);
/*  85 */     this.m_phase += 0.3925F;
/*     */     
/*  87 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(GL gl, EffectContext context) {
/*  92 */     gl.glBlendFunc(770, 771);
/*     */     
/*  94 */     this.m_depthVs.bind();
/*  95 */     this.m_depthPs.bind();
/*  96 */     this.m_mesh.drawPrimitives(gl);
/*  97 */     this.m_depthPs.unbind();
/*  98 */     this.m_depthVs.unbind();
/*     */     
/* 100 */     gl.glMatrixMode(5888);
/* 101 */     gl.glPushMatrix();
/* 102 */     gl.glScalef(1.0F, -0.5F, 1.0F);
/* 103 */     gl.glTranslatef(0.0F, -(this.m_depth / 50.0F) * 130.0F, 0.0F);
/*     */     
/* 105 */     this.m_waterVs.bind();
/* 106 */     this.m_waterPs.bind();
/* 107 */     this.m_mesh.drawPrimitives(gl);
/* 108 */     this.m_waterPs.unbind();
/* 109 */     this.m_waterVs.unbind();
/*     */     
/* 111 */     gl.glPopMatrix();
/* 112 */     gl.glBlendFunc(1, 771);
/*     */   }
/*     */   
/*     */   public float getDepth() {
/* 116 */     return this.m_depth;
/*     */   }
/*     */   
/*     */   public void setDepth(float depth) {
/* 120 */     this.m_depth = depth;
/*     */   }
/*     */ 
/*     */   
/*     */   public EffectContext getNewContext() {
/* 125 */     return null;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\effects\RippleEffect.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */