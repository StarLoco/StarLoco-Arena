/*     */ package com.ankamagames.graphics.effects;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.opengl.base.Mesh;
/*     */ import com.ankamagames.framework.graphics.opengl.base.effects.Effect;
/*     */ import com.ankamagames.framework.graphics.opengl.base.effects.EffectContext;
/*     */ import com.ankamagames.framework.graphics.opengl.base.effects.EffectRequirement;
/*     */ import com.ankamagames.framework.graphics.opengl.base.effects.ShaderManager;
/*     */ import com.ankamagames.framework.graphics.opengl.base.effects.shaders.ShaderProgram;
/*     */ import com.ankamagames.framework.graphics.opengl.base.impl.Mesh2D;
/*     */ import com.ankamagames.graphics.effects.shaders.WindVertexShader;
/*     */ import java.util.ArrayList;
/*     */ import javax.media.opengl.GL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WindEffect
/*     */   extends Effect
/*     */ {
/*     */   public static final String NAME = "wind";
/*     */   private ShaderProgram m_vs;
/*     */   private double m_w;
/*     */   
/*     */   class WindEffectContext
/*     */     extends EffectContext
/*     */   {
/*     */     private float m_bend;
/*     */     private Mesh m_mesh;
/*     */     
/*     */     public WindEffectContext(Effect effect) {
/*  31 */       super(effect);
/*     */     }
/*     */     
/*     */     public float getBend() {
/*  35 */       return this.m_bend;
/*     */     }
/*     */     
/*     */     public void setBend(float bend) {
/*  39 */       this.m_bend = bend;
/*     */     }
/*     */     
/*     */     public Mesh getMesh() {
/*  43 */       return this.m_mesh;
/*     */     }
/*     */     
/*     */     public void setMesh(Mesh mesh) {
/*  47 */       this.m_mesh = mesh;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   private double m_wMax = 0.04D;
/*     */   
/*     */   private ArrayList<Double> m_winds;
/*     */   private double m_angle;
/*     */   
/*     */   public WindEffect() {
/*  60 */     this.m_name = "wind";
/*  61 */     this.m_requirements = new EffectRequirement(6153, 0, 1);
/*  62 */     this.m_winds = new ArrayList<Double>(256);
/*     */     
/*  64 */     boolean textureRectSupported = this.m_requirements.isTextureRectangleSupported();
/*  65 */     int numTextureUnits = this.m_requirements.getMaxTextureUnits();
/*     */     
/*  67 */     this.m_vs = (ShaderProgram)new WindVertexShader(textureRectSupported, numTextureUnits);
/*     */ 
/*     */     
/*     */     try {
/*  71 */       ShaderManager.getInstance().enableVertexShader(this.m_vs);
/*  72 */       this.m_vs.unbind();
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*  77 */     catch (Exception e) {
/*  78 */       e.printStackTrace();
/*     */     } 
/*     */     
/*  81 */     this.m_w = 0.0D;
/*     */   }
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
/*     */   public void preProcess(long realTime, int frameCount) {
/*  94 */     double vmax = 0.157D;
/*  95 */     this.m_angle += Math.random() * vmax - vmax * 0.7D;
/*  96 */     this.m_w = Math.sin(this.m_angle) * this.m_wMax;
/*     */     
/*  98 */     this.m_winds.add(Double.valueOf(this.m_w));
/*  99 */     if (this.m_winds.size() > 256) {
/* 100 */       this.m_winds.remove(0);
/*     */     }
/*     */   }
/*     */   
/*     */   public int process(Mesh mesh, long realTime, int frameCount) {
/* 105 */     Mesh2D m = (Mesh2D)mesh;
/*     */     
/* 107 */     int x = 640 + (int)Math.floor(m.getPosX());
/* 108 */     int y = 640 + (int)Math.floor(m.getPosY());
/*     */     
/* 110 */     int max = this.m_winds.size();
/* 111 */     int idx = (x + y) / 2 / 6;
/*     */     
/* 113 */     double wind = ((Double)this.m_winds.get((idx >= max) ? (max - 1) : idx)).doubleValue();
/* 114 */     float bend = (float)(wind - this.m_wMax * 0.9D * wind);
/*     */     
/* 116 */     WindEffectContext ctx = (WindEffectContext)mesh.getEffectContext();
/* 117 */     ctx.setMesh(mesh);
/* 118 */     ctx.setBend(bend);
/*     */     
/* 120 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(GL gl, EffectContext context) {
/* 125 */     this.m_vs.bind();
/*     */ 
/*     */     
/* 128 */     WindEffectContext ctx = (WindEffectContext)context;
/*     */     
/* 130 */     ((WindVertexShader)this.m_vs).setBendParameter(ctx.getBend());
/* 131 */     ctx.getMesh().drawPrimitives(gl);
/*     */ 
/*     */     
/* 134 */     this.m_vs.unbind();
/*     */   }
/*     */   
/*     */   public EffectContext getNewContext() {
/* 138 */     return new WindEffectContext(this);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\effects\WindEffect.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */