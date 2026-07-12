/*     */ package com.ankamagames.framework.graphics.opengl.base.impl;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.opengl.base.BaseTexture;
/*     */ import com.ankamagames.framework.graphics.opengl.base.Mesh;
/*     */ import com.ankamagames.framework.graphics.opengl.base.material.Material;
/*     */ import com.ankamagames.framework.graphics.opengl.base.matrices.transformation2D.Matrix2D;
/*     */ import com.ankamagames.framework.graphics.opengl.base.matrices.transformation2D.RotateSkew2D;
/*     */ import com.ankamagames.framework.graphics.opengl.base.matrices.transformation2D.Rotation2D;
/*     */ import com.ankamagames.framework.graphics.opengl.base.matrices.transformation2D.Scaling2D;
/*     */ import com.ankamagames.framework.graphics.opengl.base.matrices.transformation3D.HotSpot3D;
/*     */ import com.ankamagames.framework.graphics.opengl.base.matrices.transformation3D.Position3D;
/*     */ import com.ankamagames.framework.graphics.opengl.base.render.GLObject;
/*     */ import com.ankamagames.framework.kernel.core.resource.ResourceContext;
/*     */ import com.sun.opengl.util.BufferUtil;
/*     */ import com.sun.opengl.util.texture.TextureCoords;
/*     */ import java.io.PrintStream;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.ShortBuffer;
/*     */ import java.util.ArrayList;
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
/*     */ public class Mesh2D
/*     */   extends Mesh
/*     */ {
/*     */   protected float m_width;
/*     */   protected float m_height;
/*     */   protected float m_hotX;
/*     */   protected float m_hotY;
/*     */   protected float m_posZ;
/*     */   protected boolean m_flip;
/*     */   protected boolean m_flipChanged;
/*     */   protected HotSpot3D m_hotCenter;
/*     */   protected Position3D m_offset;
/*     */   protected Position3D m_position;
/*     */   protected RotateSkew2D m_rotateSkew;
/*     */   protected Rotation2D m_rotation;
/*     */   protected Scaling2D m_scaling;
/*     */   protected Matrix2D m_transformation;
/*  50 */   private static int m_mesh2Dcount = 0;
/*     */   
/*     */   public Mesh2D()
/*     */   {
/*  54 */     m_mesh2Dcount += 1;
/*     */     
/*  56 */     this.m_hotCenter = new HotSpot3D();
/*  57 */     this.m_offset = new Position3D();
/*  58 */     this.m_position = new Position3D();
/*  59 */     this.m_rotateSkew = new RotateSkew2D();
/*  60 */     this.m_rotation = new Rotation2D();
/*  61 */     this.m_scaling = new Scaling2D();
/*     */     
/*  63 */     initialize();
/*     */   }
/*     */   
/*     */   public Material getMaterial() {
/*  67 */     return (Material)this.m_material;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int getMesh2Dcount()
/*     */   {
/*  75 */     return m_mesh2Dcount;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void initialize()
/*     */   {
/*  84 */     if (isInitialized()) {
/*  85 */       return;
/*     */     }
/*     */     
/*  88 */     super.initialize();
/*     */     
/*  90 */     this.m_width = 10.0F;
/*  91 */     this.m_height = 10.0F;
/*     */     
/*  93 */     pushMatrixBack(this.m_position, 5888);
/*  94 */     pushMatrixBack(this.m_rotateSkew, 5888);
/*  95 */     pushMatrixBack(this.m_rotation, 5888);
/*  96 */     pushMatrixBack(this.m_scaling, 5888);
/*  97 */     pushMatrixBack(this.m_hotCenter, 5888);
/*     */     
/*     */ 
/* 100 */     this.m_glType = 5;
/*     */     
/* 102 */     if (this.m_vertexBuffer == null)
/* 103 */       this.m_vertexBuffer = BufferUtil.newFloatBuffer(16);
/* 104 */     if (this.m_vertexColorBuffer == null)
/* 105 */       this.m_vertexColorBuffer = BufferUtil.newFloatBuffer(16);
/* 106 */     if (this.m_texCoordBuffer == null)
/* 107 */       this.m_texCoordBuffer = BufferUtil.newFloatBuffer(8);
/* 108 */     if (this.m_vertexIndexBuffer == null) {
/* 109 */       this.m_vertexIndexBuffer = BufferUtil.newShortBuffer(6);
/*     */     }
/*     */     try {
/* 112 */       this.m_vertexBuffer.rewind();
/* 113 */       this.m_vertexColorBuffer.rewind();
/* 114 */       this.m_texCoordBuffer.rewind();
/* 115 */       this.m_vertexIndexBuffer.rewind();
/*     */       
/* 117 */       this.m_vertexBuffer.put(new float[] { 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, 1.0F });
/* 118 */       this.m_vertexColorBuffer.put(new float[] { 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F });
/* 119 */       this.m_texCoordBuffer.put(new float[] { 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, 1.0F, 1.0F });
/* 120 */       this.m_vertexIndexBuffer.put(new short[] { 0, 1, 2, 2, 3, 1 });
/*     */     } catch (Exception e) {
/* 122 */       System.err.println("Exception : " + e.getMessage() + "\n\t+ Deux buffers ont la même addresse");
/*     */     }
/*     */     
/* 125 */     this.m_vertexBuffer.flip();
/* 126 */     this.m_vertexColorBuffer.flip();
/* 127 */     this.m_texCoordBuffer.flip();
/* 128 */     this.m_vertexIndexBuffer.flip();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void uninitialize()
/*     */   {
/* 137 */     if (!isInitialized()) {
/* 138 */       return;
/*     */     }
/* 140 */     super.uninitialize();
/*     */     
/* 142 */     this.m_flip = false;
/* 143 */     this.m_flipChanged = false;
/*     */     
/* 145 */     this.m_effectEnabled = false;
/* 146 */     this.m_position.reset();
/* 147 */     this.m_rotateSkew.reset();
/* 148 */     this.m_rotation.reset();
/* 149 */     this.m_scaling.reset();
/* 150 */     this.m_hotCenter.reset();
/* 151 */     this.m_offset.reset();
/* 152 */     this.m_transformation = null;
/*     */     
/* 154 */     this.m_width = 0.0F;
/* 155 */     this.m_height = 0.0F;
/* 156 */     this.m_hotX = 0.0F;
/* 157 */     this.m_hotY = 0.0F;
/* 158 */     this.m_posZ = 0.0F;
/* 159 */     this.m_flip = false;
/* 160 */     this.m_flipChanged = false;
/*     */     
/* 162 */     clearMatrices(5888);
/* 163 */     clearMatrices(5889);
/* 164 */     clearMatrices(5890);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void computeTextureCoordinate()
/*     */   {
/* 171 */     BaseTexture texture = getTexture();
/*     */     
/* 173 */     if (texture != null)
/*     */     {
/* 175 */       TextureCoords coords = texture.getImageTexCoords();
/* 176 */       if (coords != null) {
/* 177 */         if (this.m_flip) {
/* 178 */           this.m_texCoordBuffer.put(0, coords.right());
/* 179 */           this.m_texCoordBuffer.put(1, coords.bottom());
/* 180 */           this.m_texCoordBuffer.put(2, coords.right());
/* 181 */           this.m_texCoordBuffer.put(3, coords.top());
/* 182 */           this.m_texCoordBuffer.put(4, coords.left());
/* 183 */           this.m_texCoordBuffer.put(5, coords.bottom());
/* 184 */           this.m_texCoordBuffer.put(6, coords.left());
/* 185 */           this.m_texCoordBuffer.put(7, coords.top());
/*     */         } else {
/* 187 */           this.m_texCoordBuffer.put(0, coords.left());
/* 188 */           this.m_texCoordBuffer.put(1, coords.bottom());
/* 189 */           this.m_texCoordBuffer.put(2, coords.left());
/* 190 */           this.m_texCoordBuffer.put(3, coords.top());
/* 191 */           this.m_texCoordBuffer.put(4, coords.right());
/* 192 */           this.m_texCoordBuffer.put(5, coords.bottom());
/* 193 */           this.m_texCoordBuffer.put(6, coords.right());
/* 194 */           this.m_texCoordBuffer.put(7, coords.top());
/*     */         }
/*     */       }
/*     */       
/* 198 */       setWidth(texture.getImageWidth());
/* 199 */       setHeight(texture.getImageHeight());
/*     */     }
/*     */   }
/*     */   
/*     */   public void setColor(float r, float g, float b, float a) {
/* 204 */     float[] diffuse = getMaterial().getDiffuse();
/* 205 */     getMaterial().setUseDiffuse(true);
/* 206 */     if ((diffuse[0] != r) || (diffuse[1] != g) || (diffuse[2] != b) || (diffuse[3] != a)) {
/* 207 */       getMaterial().setDiffuse(r, g, b, a);
/* 208 */       this.m_materialChanged = true;
/* 209 */       applyMaterial();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void applyMaterial()
/*     */   {
/* 217 */     if ((this.m_materialChanged) || ((this.m_material != null) && (getMaterial().isDiffuseChanged())))
/*     */     {
/* 219 */       this.m_vertexColorBuffer.rewind();
/*     */       
/* 221 */       if ((this.m_material == null) || (!getMaterial().useDiffuse()))
/*     */       {
/* 223 */         this.m_vertexColorBuffer.put(Material.WhiteColor);
/* 224 */         this.m_vertexColorBuffer.put(Material.WhiteColor);
/* 225 */         this.m_vertexColorBuffer.put(Material.WhiteColor);
/* 226 */         this.m_vertexColorBuffer.put(Material.WhiteColor);
/*     */       }
/*     */       else
/*     */       {
/* 230 */         this.m_vertexColorBuffer.put(getMaterial().getDiffuseBottomLeft());
/* 231 */         this.m_vertexColorBuffer.put(getMaterial().getDiffuseTopLeft());
/* 232 */         this.m_vertexColorBuffer.put(getMaterial().getDiffuseBottomRight());
/* 233 */         this.m_vertexColorBuffer.put(getMaterial().getDiffuseTopRight());
/*     */       }
/*     */       
/* 236 */       this.m_vertexColorBuffer.rewind();
/*     */       
/* 238 */       this.m_materialChanged = false;
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
/*     */   public void process(long realTime, int frameCount)
/*     */   {
/* 252 */     super.process(realTime, frameCount);
/* 253 */     Mesh2DManager.getInstance().tagResourceInUse(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isHitTestable()
/*     */   {
/* 262 */     return false;
/*     */   }
/*     */   
/*     */   public float getPosZ() {
/* 266 */     return this.m_posZ;
/*     */   }
/*     */   
/*     */   public void setZOrder(float posZ) {
/* 270 */     this.m_posZ = posZ;
/* 271 */     this.m_position.setZ(this.m_posZ);
/*     */   }
/*     */   
/*     */   public float getPosX() {
/* 275 */     return this.m_position.getX();
/*     */   }
/*     */   
/*     */   public float getPosY() {
/* 279 */     return this.m_position.getY();
/*     */   }
/*     */   
/*     */   public float getWidth() {
/* 283 */     return this.m_width;
/*     */   }
/*     */   
/*     */   public void setWidth(float width) {
/* 287 */     this.m_width = width;
/*     */     
/* 289 */     this.m_vertexBuffer.put(8, this.m_width);
/* 290 */     this.m_vertexBuffer.put(12, this.m_width);
/*     */   }
/*     */   
/*     */   public float getHeight() {
/* 294 */     return this.m_height;
/*     */   }
/*     */   
/*     */   public void setHeight(float height) {
/* 298 */     this.m_height = height;
/*     */     
/* 300 */     this.m_vertexBuffer.put(5, this.m_height);
/* 301 */     this.m_vertexBuffer.put(13, this.m_height);
/*     */     
/* 303 */     if (this.m_transformation != null) {
/* 304 */       this.m_offset.set(0.0F, -this.m_height, 0.0F);
/*     */     } else
/* 306 */       this.m_hotCenter.set(this.m_hotX, this.m_hotY - this.m_height, 0.0F);
/*     */   }
/*     */   
/*     */   public float getHotX() {
/* 310 */     return this.m_hotCenter.getX();
/*     */   }
/*     */   
/*     */   public float getHotY() {
/* 314 */     return this.m_hotCenter.getY() + this.m_height;
/*     */   }
/*     */   
/*     */   public void setHotPoint(float hotX, float hotY) {
/* 318 */     this.m_hotX = hotX;
/* 319 */     this.m_hotY = hotY;
/*     */     
/* 321 */     if (this.m_transformation == null) {
/* 322 */       this.m_hotCenter.set(hotX, hotY - this.m_height, 0.0F);
/*     */     } else
/* 324 */       this.m_hotCenter.set(hotX, hotY, 0.0F);
/*     */   }
/*     */   
/*     */   public void setGeometry(float x, float y, float w, float h) {
/* 328 */     this.m_position.set(x, y, this.m_posZ);
/* 329 */     setWidth(w);
/* 330 */     setHeight(h);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getSortPosition()
/*     */   {
/* 339 */     return this.m_posZ;
/*     */   }
/*     */   
/*     */   public boolean isFlip() {
/* 343 */     return this.m_flip;
/*     */   }
/*     */   
/*     */   public void setFlip(boolean flip) {
/* 347 */     if (this.m_flip != flip) {
/* 348 */       this.m_flip = flip;
/* 349 */       this.m_flipChanged = true;
/*     */     }
/*     */   }
/*     */   
/*     */   public void setScreenPosition(float posX, float posY) {
/* 354 */     this.m_position.set(posX, posY, this.m_posZ);
/*     */   }
/*     */   
/*     */   public void translate(float x, float y) {
/* 358 */     this.m_position.add(x, y, 0.0F);
/*     */   }
/*     */   
/*     */   public void rotate(float angleDeg) {
/* 362 */     this.m_rotation.add(angleDeg);
/*     */   }
/*     */   
/*     */   public void setRotation(float angleDeg) {
/* 366 */     this.m_rotation.setAngleDeg(angleDeg);
/*     */   }
/*     */   
/*     */   public Rotation2D getRotation() {
/* 370 */     return this.m_rotation;
/*     */   }
/*     */   
/*     */   public void scale(float x, float y) {
/* 374 */     this.m_scaling.mult(x, y);
/*     */   }
/*     */   
/*     */   public void setScale(float x, float y) {
/* 378 */     this.m_scaling.set(x, y);
/*     */   }
/*     */   
/*     */   public void rotateSkew(float x, float y) {
/* 382 */     this.m_rotateSkew.add(x, y);
/*     */   }
/*     */   
/*     */   public void setRotateSkew(float x, float y) {
/* 386 */     this.m_rotateSkew.set(x, y);
/*     */   }
/*     */   
/*     */   public Position3D getPositionMatrix() {
/* 390 */     return this.m_position;
/*     */   }
/*     */   
/*     */   public RotateSkew2D getRotateSkewMatrix() {
/* 394 */     return this.m_rotateSkew;
/*     */   }
/*     */   
/*     */   public Scaling2D getScaleMatrix() {
/* 398 */     return this.m_scaling;
/*     */   }
/*     */   
/*     */   public void setTransformation(Matrix2D matrix)
/*     */   {
/* 403 */     clearMatrices(5888);
/* 404 */     this.m_transformation = matrix;
/*     */     
/* 406 */     pushMatrixBack(this.m_transformation, 5888);
/* 407 */     pushMatrixBack(this.m_scaling, 5888);
/* 408 */     pushMatrixBack(this.m_hotCenter, 5888);
/* 409 */     pushMatrixBack(this.m_offset, 5888);
/*     */   }
/*     */   
/*     */   public Matrix2D getTransformation() {
/* 413 */     return this.m_transformation;
/*     */   }
/*     */   
/*     */   public void combineHotCenterAndOffset()
/*     */   {
/* 418 */     this.m_hotCenter.setX(this.m_hotCenter.getX() + this.m_offset.getX());
/* 419 */     this.m_hotCenter.setY(this.m_hotCenter.getY() + this.m_offset.getY());
/* 420 */     this.m_hotCenter.setZ(this.m_hotCenter.getZ() + this.m_offset.getZ());
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
/*     */   public String toString()
/*     */   {
/* 473 */     StringBuffer buffer = new StringBuffer();
/*     */     
/* 475 */     buffer.append("Mesh2D[").append(getPosX()).append(";").append(getPosY()).append(";").append(getPosZ()).append(" - ").append(this.m_width).append(";").append(this.m_height).append("] (").append(this.m_children.size()).append(" childs)\n");
/* 476 */     for (GLObject child : this.m_children) {
/* 477 */       buffer.append("\t").append(child.toString());
/*     */     }
/* 479 */     return buffer.toString();
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
/*     */   public void reloadResource(ResourceContext resourceContext)
/*     */   {
/* 492 */     super.reloadResource(resourceContext);
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
/*     */   public void unloadResource(ResourceContext resourceContext)
/*     */   {
/* 506 */     super.unloadResource(resourceContext);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long estimateMemoryUsageInBytes()
/*     */   {
/* 515 */     return super.estimateMemoryUsageInBytes() + 64L + 64L + 32L + 12L;
/*     */   }
/*     */   
/*     */   public static class Mesh2DResourceContext
/*     */     extends ResourceContext
/*     */   {}
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\impl\Mesh2D.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */