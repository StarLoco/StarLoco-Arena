/*     */ package com.ankamagames.framework.graphics.opengl.base.effects;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.opengl.base.Mesh;
/*     */ import com.ankamagames.framework.graphics.opengl.base.matrices.GLMatrix;
/*     */ import com.ankamagames.framework.graphics.opengl.base.render.GLObject;
/*     */ import com.ankamagames.framework.graphics.opengl.base.states.GLRenderStates;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Effect
/*     */ {
/*  30 */   protected EffectRequirement m_requirements = new EffectRequirement(); protected GLRenderStates m_preRenderStates; protected GLRenderStates m_postRenderStates; protected GLMatrix m_modelViewMatrix;
/*  31 */   protected String m_name = "<undefined>"; public static final int ORIGINAL_MESH_BEFORE = 0;
/*     */   public static final int ORIGINAL_MESH_AFTER = 1;
/*     */   
/*     */   public EffectRequirement getRequirements() {
/*  35 */     return this.m_requirements;
/*     */   }
/*     */   public static final int ORIGINAL_MESH_NONE = 2; public static final int EFFECT_DONT_DISPLAY = 3; public static final int CHILDREN_ONLY_AFTER = 4;
/*     */   public boolean isConfigurationMatchRequirements(GL gl) {
/*  39 */     if (this.m_requirements != null) {
/*  40 */       return this.m_requirements.matchCurrentConfiguration(gl);
/*     */     }
/*  42 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void preProcess(long paramLong, int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int process(Mesh paramMesh, long paramLong, int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void draw(GL paramGL, EffectContext paramEffectContext);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void display(GL gl, EffectContext context) {
/*  76 */     if (this.m_modelViewMatrix != null) {
/*  77 */       gl.glMatrixMode(5888);
/*  78 */       gl.glPushMatrix();
/*  79 */       this.m_modelViewMatrix.setup(gl);
/*     */     } 
/*     */     
/*  82 */     if (this.m_preRenderStates != null) {
/*  83 */       this.m_preRenderStates.setup(gl);
/*     */     }
/*  85 */     draw(gl, context);
/*     */     
/*  87 */     if (this.m_postRenderStates != null) {
/*  88 */       this.m_postRenderStates.setup(gl);
/*     */     }
/*  90 */     if (this.m_modelViewMatrix != null) {
/*  91 */       gl.glPopMatrix();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bind(GLObject object, EffectContext context) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unbind(GLObject object, EffectContext context) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GLRenderStates getPreRenderStates() {
/* 114 */     return this.m_preRenderStates;
/*     */   }
/*     */   
/*     */   public void setPreRenderStates(GLRenderStates preRenderStates) {
/* 118 */     this.m_preRenderStates = preRenderStates;
/*     */   }
/*     */   
/*     */   public GLRenderStates getPostRenderStates() {
/* 122 */     return this.m_postRenderStates;
/*     */   }
/*     */   
/*     */   public void setPostRenderStates(GLRenderStates postRenderStates) {
/* 126 */     this.m_postRenderStates = postRenderStates;
/*     */   }
/*     */   
/*     */   public GLMatrix getModelViewMatrix() {
/* 130 */     return this.m_modelViewMatrix;
/*     */   }
/*     */   
/*     */   public void setModelViewMatrix(GLMatrix modelViewMatrix) {
/* 134 */     this.m_modelViewMatrix = modelViewMatrix;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 138 */     return this.m_name;
/*     */   }
/*     */   
/*     */   public abstract EffectContext getNewContext();
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\effects\Effect.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */