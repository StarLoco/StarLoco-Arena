/*     */ package com.ankamagames.graphics.effects;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.opengl.base.Mesh;
/*     */ import com.ankamagames.framework.graphics.opengl.base.Scene;
/*     */ import com.ankamagames.framework.graphics.opengl.base.effects.Effect;
/*     */ import com.ankamagames.framework.graphics.opengl.base.effects.EffectContext;
/*     */ import com.ankamagames.framework.graphics.opengl.base.effects.EffectManager;
/*     */ import com.ankamagames.framework.graphics.opengl.base.effects.EffectRequirement;
/*     */ import com.ankamagames.framework.graphics.opengl.base.effects.ShaderManager;
/*     */ import com.ankamagames.framework.graphics.opengl.base.effects.shaders.ShaderProgram;
/*     */ import com.ankamagames.framework.graphics.opengl.base.impl.Mesh2D;
/*     */ import com.ankamagames.framework.graphics.opengl.base.render.GLObject;
/*     */ import com.ankamagames.graphics.effects.shaders.SeaVertexShader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import javax.media.opengl.GL;
/*     */ 
/*     */ 
/*     */ public class SeaEffect
/*     */   extends Effect
/*     */ {
/*     */   public static final String NAME = "sea";
/*     */   private SeaVertexShader m_vs;
/*     */   
/*     */   class SeaEffectContext
/*     */     extends EffectContext
/*     */   {
/*     */     private float m_stretch;
/*     */     private Mesh2D m_mesh;
/*     */     private boolean m_hasAdjency;
/*     */     private ArrayList<Mesh2D> m_adjency;
/*     */     
/*     */     public SeaEffectContext(Effect effect) {
/*  34 */       super(effect);
/*  35 */       this.m_adjency = new ArrayList<Mesh2D>();
/*     */     }
/*     */     
/*     */     public float getStretch() {
/*  39 */       return this.m_stretch;
/*     */     }
/*     */     
/*     */     public void setStretch(float stretch) {
/*  43 */       this.m_stretch = stretch;
/*     */     }
/*     */     
/*     */     public Mesh2D getMesh() {
/*  47 */       return this.m_mesh;
/*     */     }
/*     */     
/*     */     public void setMesh(Mesh2D mesh) {
/*  51 */       this.m_mesh = mesh;
/*     */     }
/*     */     
/*     */     public boolean hasAdjency() {
/*  55 */       return this.m_hasAdjency;
/*     */     }
/*     */     
/*     */     public void setHasAdjency(boolean hasAdjency) {
/*  59 */       this.m_hasAdjency = hasAdjency;
/*     */     }
/*     */     
/*     */     public void addAdjency(Mesh2D mesh) {
/*  63 */       this.m_adjency.add(mesh);
/*     */     }
/*     */     
/*     */     public void clearAdjency() {
/*  67 */       this.m_adjency.clear();
/*     */     }
/*     */     
/*     */     public ArrayList<Mesh2D> getAdjency() {
/*  71 */       return this.m_adjency;
/*     */     }
/*     */ 
/*     */     
/*     */     public void sortAdjency() {
/*  76 */       if (!this.m_adjency.isEmpty()) {
/*  77 */         Collections.sort(this.m_adjency);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   private double m_wMax = 0.25D;
/*     */   private ArrayList<Double> m_waves;
/*     */   private SceneMirrorEffect m_mirror;
/*     */   
/*     */   public SeaEffect() {
/*  90 */     this.m_name = "sea";
/*  91 */     this.m_waves = new ArrayList<Double>(256);
/*  92 */     this.m_requirements = new EffectRequirement(6153, 6162, 4);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 114 */     this.m_requirements = new EffectRequirement(6153, 0, 1);
/* 115 */     boolean textureRectSupported = this.m_requirements.isTextureRectangleSupported();
/* 116 */     int numTextureUnits = this.m_requirements.getMaxTextureUnits();
/* 117 */     this.m_vs = new SeaVertexShader(textureRectSupported, numTextureUnits);
/*     */     
/*     */     try {
/* 120 */       ShaderManager.getInstance().enableVertexShader((ShaderProgram)this.m_vs);
/* 121 */       this.m_vs.unbind();
/*     */     }
/* 123 */     catch (Exception e) {
/* 124 */       e.printStackTrace();
/*     */     } 
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
/*     */   
/*     */   public void preProcess(long realTime, int frameCount) {
/* 139 */     if (this.m_mirror == null) {
/* 140 */       this.m_mirror = (SceneMirrorEffect)EffectManager.getInstance().getEffect("sceneMirror");
/*     */     }
/* 142 */     this.m_waves.add(Double.valueOf(Math.sin((frameCount / 30.0F)) * this.m_wMax));
/* 143 */     if (this.m_waves.size() > 256) {
/* 144 */       this.m_waves.remove(0);
/*     */     }
/*     */   }
/*     */   
/*     */   public int process(Mesh mesh, long realTime, int frameCount) {
/* 149 */     Mesh2D m = (Mesh2D)mesh;
/*     */     
/* 151 */     int x = 800 + (int)Math.floor(m.getPosX());
/* 152 */     int y = 800 + (int)Math.floor(m.getPosY());
/*     */     
/* 154 */     int max = this.m_waves.size();
/* 155 */     int idx = (x + y) / 2 / 6;
/*     */     
/* 157 */     int index = (idx >= max) ? (max - 1) : idx;
/* 158 */     index = (index < 0) ? 0 : index;
/*     */     
/* 160 */     double wave = ((Double)this.m_waves.get(index)).doubleValue();
/* 161 */     float stretch = (float)wave;
/*     */     
/* 163 */     SeaEffectContext ctx = (SeaEffectContext)mesh.getEffectContext();
/* 164 */     ctx.setMesh(m);
/* 165 */     ctx.setStretch(stretch);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 170 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(GL gl, EffectContext context) {
/* 175 */     SeaEffectContext ctx = (SeaEffectContext)context;
/* 176 */     Mesh2D mesh = ctx.getMesh();
/*     */     
/* 178 */     this.m_vs.bind();
/*     */     
/* 180 */     this.m_vs.setStretchParameter(ctx.getStretch());
/* 181 */     this.m_vs.setWidthParameter(mesh.getWidth());
/*     */     
/* 183 */     mesh.drawPrimitives(gl);
/*     */     
/* 185 */     this.m_vs.unbind();
/*     */   }
/*     */   
/*     */   public EffectContext getNewContext() {
/* 189 */     return new SeaEffectContext(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void getAdjency(GLObject object, EffectContext context) {
/* 195 */     SeaEffectContext ctx = (SeaEffectContext)context;
/*     */     
/* 197 */     if (this.m_mirror != null) {
/* 198 */       Scene scene = this.m_mirror.getScene();
/* 199 */       if (scene != null) {
/* 200 */         Mesh2D m = (Mesh2D)object;
/*     */ 
/*     */         
/* 203 */         float h = m.getHeight();
/* 204 */         float x = m.getPosX();
/*     */         
/* 206 */         float z = m.getPosZ();
/*     */         
/* 208 */         float lz = Float.MAX_VALUE;
/* 209 */         float rz = Float.MAX_VALUE;
/*     */         
/* 211 */         Mesh2D lnear = null;
/* 212 */         Mesh2D rnear = null;
/*     */         
/* 214 */         Mesh2D r = (Mesh2D)scene.getFirstChild();
/* 215 */         while (r != null && 
/* 216 */           r != m) {
/*     */ 
/*     */           
/* 219 */           if (r.getEffect() != this) {
/*     */             
/* 221 */             float ph = r.getHeight();
/* 222 */             float px = r.getPosX();
/*     */             
/* 224 */             float pz = r.getPosZ();
/*     */             
/* 226 */             if (ph > h) {
/* 227 */               if (px < x) {
/* 228 */                 if (pz > z && pz < lz) {
/* 229 */                   lz = pz;
/* 230 */                   lnear = r;
/*     */                 } 
/* 232 */               } else if (px > x && 
/* 233 */                 pz > z && pz < rz) {
/* 234 */                 rz = pz;
/* 235 */                 rnear = r;
/*     */               } 
/*     */             }
/*     */           } 
/*     */           
/* 240 */           r = (Mesh2D)scene.getNextChild();
/*     */         } 
/*     */         
/* 243 */         if (rnear != null) {
/* 244 */           ctx.addAdjency(rnear);
/* 245 */           ctx.setHasAdjency(true);
/*     */         } 
/*     */         
/* 248 */         if (lnear != null) {
/* 249 */           ctx.addAdjency(lnear);
/* 250 */           ctx.setHasAdjency(true);
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/* 257 */     ctx.setHasAdjency(false);
/*     */   }
/*     */   
/*     */   public void bind(GLObject object, EffectContext context) {}
/*     */   
/*     */   public void unbind(GLObject object, EffectContext context) {}
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\effects\SeaEffect.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */