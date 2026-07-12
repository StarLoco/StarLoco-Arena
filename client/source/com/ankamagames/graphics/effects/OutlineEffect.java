/*     */ package com.ankamagames.graphics.effects;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.opengl.TextureManager;
/*     */ import com.ankamagames.framework.graphics.opengl.base.Mesh;
/*     */ import com.ankamagames.framework.graphics.opengl.base.RenderTarget;
/*     */ import com.ankamagames.framework.graphics.opengl.base.Scene;
/*     */ import com.ankamagames.framework.graphics.opengl.base.Texture;
/*     */ import com.ankamagames.framework.graphics.opengl.base.effects.Effect;
/*     */ import com.ankamagames.framework.graphics.opengl.base.effects.EffectContext;
/*     */ import com.ankamagames.framework.graphics.opengl.base.effects.EffectRequirement;
/*     */ import com.ankamagames.framework.graphics.opengl.base.effects.ShaderManager;
/*     */ import com.ankamagames.framework.graphics.opengl.base.effects.shaders.ShaderProgram;
/*     */ import com.ankamagames.framework.graphics.opengl.base.impl.Mesh2D;
/*     */ import com.ankamagames.framework.graphics.opengl.base.render.GLObject;
/*     */ import com.ankamagames.framework.kernel.core.resource.direct.DirectBufferManager;
/*     */ import com.ankamagames.graphics.effects.shaders.OutlinePixelShader;
/*     */ import com.ankamagames.graphics.effects.shaders.OutlineVertexShader;
/*     */ import com.sun.opengl.util.texture.TextureCoords;
/*     */ import java.nio.IntBuffer;
/*     */ import javax.media.opengl.GL;
/*     */ import javax.media.opengl.glu.GLU;
/*     */ 
/*     */ 
/*     */ public class OutlineEffect
/*     */   extends Effect
/*     */ {
/*     */   public static final String NAME = "outline";
/*     */   private ShaderProgram m_vs;
/*     */   private ShaderProgram m_ps;
/*     */   private float m_thickness;
/*     */   private RenderTarget m_renderTarget;
/*     */   private Texture m_texture;
/*     */   
/*     */   public class OutlineEffectContext
/*     */     extends EffectContext
/*     */   {
/*     */     private Mesh2D m_mesh;
/*     */     
/*     */     public OutlineEffectContext(Effect effect) {
/*  40 */       super(effect);
/*     */     }
/*     */     
/*     */     public Mesh2D getMesh() {
/*  44 */       return this.m_mesh;
/*     */     }
/*     */     
/*     */     public void setMesh(Mesh2D mesh) {
/*  48 */       this.m_mesh = mesh;
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
/*  60 */   private static IntBuffer m_int = DirectBufferManager.getInstance().getIntBuffer(16);
/*     */   
/*  62 */   private int FRAME_BUFFER_WIDTH = 256;
/*  63 */   private int FRAME_BUFFER_HEIGHT = 256;
/*     */ 
/*     */   
/*     */   public OutlineEffect() {
/*  67 */     this.m_name = "outline";
/*  68 */     this.m_requirements = new EffectRequirement(6153, 6162, 4);
/*  69 */     this.m_thickness = 0.5F;
/*     */     
/*  71 */     boolean fboSupported = this.m_requirements.isFBOSupported();
/*     */     
/*  73 */     if (fboSupported) {
/*     */       
/*  75 */       this.m_renderTarget = new RenderTarget();
/*     */       
/*  77 */       this.m_texture = 
/*  78 */         TextureManager.createTexture(this.FRAME_BUFFER_WIDTH, this.FRAME_BUFFER_HEIGHT, true, 6408, false);
/*     */ 
/*     */       
/*  81 */       this.m_texture.bind();
/*  82 */       GL gl = GLU.getCurrentGL();
/*  83 */       gl.glTexParameterf(this.m_texture.getTarget(), 10241, 9729.0F);
/*  84 */       gl.glTexParameterf(this.m_texture.getTarget(), 10240, 9729.0F);
/*     */ 
/*     */       
/*  87 */       this.m_renderTarget.attachTexture(this.m_texture);
/*     */       
/*  89 */       boolean textureRectSupported = (this.m_texture.getTarget() == 34037);
/*  90 */       int numTextureUnits = this.m_requirements.getMaxTextureUnits();
/*     */       
/*  92 */       this.m_vs = (ShaderProgram)new OutlineVertexShader(textureRectSupported, numTextureUnits);
/*  93 */       this.m_ps = (ShaderProgram)new OutlinePixelShader(textureRectSupported, numTextureUnits);
/*     */       
/*     */       try {
/*  96 */         ShaderManager.getInstance().enableVertexShader(this.m_vs);
/*  97 */         ShaderManager.getInstance().enablePixelShader(this.m_ps);
/*     */       }
/*  99 */       catch (Exception e) {
/* 100 */         e.printStackTrace();
/*     */       } 
/*     */     } else {
/*     */       
/* 104 */       System.err.println("OutlineEffect : FBO not supported");
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
/*     */   
/*     */   public int process(Mesh mesh, long realTime, int frameCount) {
/* 121 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(GL gl, EffectContext context) {
/* 126 */     Mesh2D mesh = ((OutlineEffectContext)context).getMesh();
/*     */ 
/*     */     
/* 129 */     float z = mesh.getPosZ();
/* 130 */     float vw = 1024.0F;
/* 131 */     float vh = 768.0F;
/* 132 */     float scaleFactor = 1.0F;
/*     */     
/* 134 */     if (this.m_renderTarget != null) {
/*     */       
/* 136 */       GLObject parent = mesh.getParent();
/* 137 */       if (parent instanceof Scene) {
/* 138 */         scaleFactor = ((Scene)parent).getScaleFactor();
/*     */       }
/* 140 */       gl.glGetIntegerv(2978, m_int);
/* 141 */       vw = (m_int.get(2) - m_int.get(0));
/* 142 */       vh = (m_int.get(3) - m_int.get(1));
/*     */       
/* 144 */       float wt = this.m_texture.getWidth();
/* 145 */       float ht = this.m_texture.getHeight();
/*     */       
/* 147 */       gl.glViewport(0, 0, (int)wt, (int)ht);
/* 148 */       gl.glScissor(0, 0, (int)wt, (int)ht);
/*     */       
/* 150 */       float[] modelViewMatrix = new float[16];
/* 151 */       gl.glGetFloatv(2982, modelViewMatrix, 0);
/* 152 */       float tx = modelViewMatrix[12];
/* 153 */       float ty = modelViewMatrix[13];
/* 154 */       float tz = modelViewMatrix[14];
/*     */       
/* 156 */       gl.glMatrixMode(5889);
/* 157 */       gl.glPushMatrix();
/* 158 */       gl.glScalef(vw / wt, vh / ht, 1.0F);
/* 159 */       gl.glTranslatef(0.0F, -ht * 0.4F / scaleFactor, 0.0F);
/* 160 */       gl.glTranslatef(-tx, -ty, -tz);
/*     */       
/* 162 */       gl.glMatrixMode(5888);
/* 163 */       gl.glPushMatrix();
/* 164 */       gl.glLoadIdentity();
/* 165 */       gl.glTranslatef(tx, ty, tz);
/*     */       
/* 167 */       this.m_renderTarget.bind();
/*     */ 
/*     */       
/* 170 */       gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
/* 171 */       gl.glClear(16384);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 177 */     float[] diffuseColor = mesh.getMaterial().getDiffuse();
/*     */     
/* 179 */     boolean useDiffuse = mesh.getMaterial().useDiffuse();
/* 180 */     mesh.getMaterial().setUseDiffuse(false);
/*     */     
/* 182 */     mesh.drawPrimitives(gl);
/*     */     
/* 184 */     GLObject child = mesh.getFirstChild();
/* 185 */     while (child != null) {
/* 186 */       child.display(gl);
/* 187 */       child = mesh.getNextChild();
/*     */     } 
/*     */     
/* 190 */     mesh.getMaterial().setUseDiffuse(useDiffuse);
/*     */     
/* 192 */     if (this.m_renderTarget != null) {
/*     */       
/* 194 */       gl.glMatrixMode(5889);
/* 195 */       gl.glPopMatrix();
/*     */       
/* 197 */       gl.glMatrixMode(5888);
/* 198 */       gl.glPopMatrix();
/*     */       
/* 200 */       gl.glViewport(0, 0, (int)vw, (int)vh);
/* 201 */       gl.glScissor(0, 0, (int)vw, (int)vh);
/*     */       
/* 203 */       this.m_renderTarget.unbind();
/*     */       
/* 205 */       TextureCoords coords = this.m_texture.getImageTexCoords();
/* 206 */       float l = coords.left();
/* 207 */       float r = coords.right();
/* 208 */       float t = coords.top();
/* 209 */       float b = coords.bottom();
/*     */       
/* 211 */       float w = this.m_texture.getWidth() * 0.5F;
/* 212 */       float h = this.m_texture.getHeight();
/*     */       
/* 214 */       float h0 = h * 0.1F;
/* 215 */       float h1 = h * 0.9F;
/*     */       
/* 217 */       boolean texture2DEnabled = gl.glIsEnabled(3553);
/* 218 */       boolean textureRectEnabled = gl.glIsEnabled(34037);
/*     */       
/* 220 */       gl.glDisable(3553);
/* 221 */       gl.glDisable(34037);
/*     */       
/* 223 */       this.m_texture.enable();
/* 224 */       this.m_texture.bind();
/*     */       
/* 226 */       gl.glScalef(1.0F / scaleFactor, 1.0F / scaleFactor, 1.0F);
/*     */       
/* 228 */       ((OutlineVertexShader)this.m_vs).setThickNessParameter(this.m_thickness);
/*     */       
/* 230 */       for (int i = 0; i < 2; i++) {
/* 231 */         if (i == 0) {
/* 232 */           gl.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 233 */           this.m_ps.bind();
/* 234 */           this.m_vs.bind();
/*     */         } else {
/* 236 */           gl.glColor4fv(diffuseColor, 0);
/*     */         } 
/*     */         
/* 239 */         gl.glBegin(7);
/*     */         
/* 241 */         gl.glTexCoord2f(l, t);
/* 242 */         gl.glVertex4f(-w, -h0, z, 1.0F);
/*     */         
/* 244 */         gl.glTexCoord2f(r, t);
/* 245 */         gl.glVertex4f(w, -h0, z, 1.0F);
/*     */         
/* 247 */         gl.glTexCoord2f(r, b);
/* 248 */         gl.glVertex4f(w, h1, z, 1.0F);
/*     */         
/* 250 */         gl.glTexCoord2f(l, b);
/* 251 */         gl.glVertex4f(-w, h1, z, 1.0F);
/*     */         
/* 253 */         gl.glEnd();
/*     */         
/* 255 */         if (i == 0) {
/* 256 */           this.m_vs.unbind();
/* 257 */           this.m_ps.unbind();
/*     */         } 
/*     */       } 
/*     */       
/* 261 */       this.m_texture.disable();
/*     */       
/* 263 */       if (texture2DEnabled)
/* 264 */         gl.glEnable(3553); 
/* 265 */       if (textureRectEnabled) {
/* 266 */         gl.glEnable(34037);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EffectContext getNewContext() {
/* 273 */     return new OutlineEffectContext(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bind(GLObject object, EffectContext context) {
/* 284 */     ((OutlineEffectContext)context).setMesh((Mesh2D)object);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unbind(GLObject object, EffectContext context) {
/* 294 */     ((OutlineEffectContext)context).setMesh(null);
/*     */   }
/*     */   
/*     */   public float getThickness() {
/* 298 */     return this.m_thickness;
/*     */   }
/*     */   
/*     */   public void setThickness(float thickness) {
/* 302 */     this.m_thickness = thickness;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\effects\OutlineEffect.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */