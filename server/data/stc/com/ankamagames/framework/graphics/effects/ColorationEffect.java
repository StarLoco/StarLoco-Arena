/*     */ package com.ankamagames.framework.graphics.effects;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.effects.shaders.ColorationPixelShader;
/*     */ import com.ankamagames.framework.graphics.opengl.base.BaseTexture;
/*     */ import com.ankamagames.framework.graphics.opengl.base.Mesh;
/*     */ import com.ankamagames.framework.graphics.opengl.base.Texture;
/*     */ import com.ankamagames.framework.graphics.opengl.base.effects.Effect;
/*     */ import com.ankamagames.framework.graphics.opengl.base.effects.EffectContext;
/*     */ import com.ankamagames.framework.graphics.opengl.base.effects.EffectRequirement;
/*     */ import com.ankamagames.framework.graphics.opengl.base.effects.ShaderManager;
/*     */ import com.ankamagames.framework.graphics.opengl.base.material.Material;
/*     */ import com.ankamagames.framework.graphics.opengl.base.render.GLObject;
/*     */ import javax.media.opengl.GL;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ColorationEffect
/*     */   extends Effect
/*     */ {
/*  26 */   protected static final Logger m_logger = Logger.getLogger(ColorationEffect.class);
/*     */   public static final String NAME = "coloration";
/*     */   private ColorationPixelShader m_ps2D;
/*     */   private ColorationPixelShader m_psRect;
/*     */   
/*     */   public static class ColorationEffectContext extends EffectContext {
/*     */     private Mesh m_mesh;
/*     */     
/*     */     public ColorationEffectContext(Effect effect) {
/*  35 */       super();
/*     */     }
/*     */     
/*     */     public Mesh getMesh() {
/*  39 */       return this.m_mesh;
/*     */     }
/*     */     
/*     */     public void setMesh(Mesh mesh) {
/*  43 */       this.m_mesh = mesh;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ColorationEffect()
/*     */   {
/*  52 */     this.m_name = "coloration";
/*  53 */     this.m_requirements = new EffectRequirement(6153, 6159, 1);
/*     */     
/*  55 */     int numTextureUnits = this.m_requirements.getMaxTextureUnits();
/*     */     
/*  57 */     this.m_ps2D = new ColorationPixelShader(true, numTextureUnits);
/*  58 */     this.m_psRect = new ColorationPixelShader(false, numTextureUnits);
/*     */     try
/*     */     {
/*  61 */       ShaderManager.getInstance().enablePixelShader(this.m_psRect);
/*  62 */       this.m_psRect.unbind();
/*  63 */       ShaderManager.getInstance().enablePixelShader(this.m_ps2D);
/*  64 */       this.m_ps2D.unbind();
/*     */     }
/*     */     catch (Exception e) {
/*  67 */       e.printStackTrace();
/*     */     }
/*     */   }
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
/*     */   public int process(Mesh mesh, long realTime, int frameCount)
/*     */   {
/*  83 */     return 4;
/*     */   }
/*     */   
/*     */   public void draw(GL gl, EffectContext context)
/*     */   {
/*  88 */     Mesh mesh = ((ColorationEffectContext)context).getMesh();
/*  89 */     if (mesh == null) {
/*  90 */       m_logger.trace("mesh null");
/*  91 */       return;
/*     */     }
/*  93 */     BaseTexture texture = mesh.getTexture();
/*     */     
/*  95 */     ColorationPixelShader colorPS = null;
/*  96 */     if ((texture != null) && (texture.getTexture() != null)) {
/*  97 */       boolean rectTexture = texture.getTexture().getTarget() == 34037;
/*     */       
/*  99 */       colorPS = rectTexture ? this.m_psRect : this.m_ps2D;
/* 100 */       if (colorPS != null)
/*     */       {
/* 102 */         Material material = (Material)mesh.getMaterial();
/* 103 */         colorPS.bind();
/*     */         
/* 105 */         if ((material != null) && (material.useDiffuse())) {
/* 106 */           colorPS.setMultTermColor(material.getDiffuse());
/*     */         } else {
/* 108 */           colorPS.setMultTermColor(Material.WhiteColor);
/*     */         }
/* 110 */         if ((material != null) && (material.useSpecular())) {
/* 111 */           colorPS.setAddTermColor(material.getSpecular());
/*     */         } else
/* 113 */           colorPS.setAddTermColor(Material.BlackColor);
/*     */       } else {
/* 115 */         m_logger.trace("colorPS null");
/*     */       }
/*     */     } else {
/* 118 */       m_logger.trace("texture= " + texture + " texture.getTexture()= " + texture != null ? texture.getTexture() : null);
/*     */     }
/*     */     
/*     */ 
/* 122 */     mesh.drawPrimitives(gl);
/*     */     
/* 124 */     if (colorPS != null) {
/* 125 */       colorPS.unbind();
/*     */     }
/*     */   }
/*     */   
/*     */   public EffectContext getNewContext() {
/* 130 */     return new ColorationEffectContext(this);
/*     */   }
/*     */   
/*     */ 
/*     */   public void bind(GLObject object, EffectContext context)
/*     */   {
/* 136 */     ((ColorationEffectContext)context).setMesh((Mesh)object);
/*     */   }
/*     */   
/*     */ 
/*     */   public void unbind(GLObject object, EffectContext context)
/*     */   {
/* 142 */     ((ColorationEffectContext)context).setMesh(null);
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\effects\ColorationEffect.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */