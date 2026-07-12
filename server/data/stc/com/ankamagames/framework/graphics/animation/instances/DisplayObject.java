/*     */ package com.ankamagames.framework.graphics.animation.instances;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.DisplayObjectDescriptor;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.library.AbstractDescriptorLibrary;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.library.DisplayObjectListener;
/*     */ import com.ankamagames.framework.graphics.opengl.base.Mesh;
/*     */ import com.ankamagames.framework.graphics.opengl.base.animation.AnimatedObject;
/*     */ import com.ankamagames.framework.graphics.opengl.base.impl.HitTestableMesh2D;
/*     */ import com.ankamagames.framework.graphics.opengl.base.material.Material;
/*     */ import com.ankamagames.framework.graphics.opengl.base.material.MaterialColorMultAdd;
/*     */ import com.ankamagames.framework.graphics.opengl.base.matrices.GLMatrix;
/*     */ import com.ankamagames.framework.graphics.opengl.base.matrices.transformation2D.Matrix2D;
/*     */ import com.ankamagames.framework.graphics.opengl.base.matrices.transformation2D.RotateSkew2D;
/*     */ import com.ankamagames.framework.graphics.opengl.base.matrices.transformation2D.Scaling2D;
/*     */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*     */ import java.awt.geom.Rectangle2D.Float;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DisplayObject
/*     */   extends AnimatedObject
/*     */   implements Poolable
/*     */ {
/*  30 */   private static Logger m_logger = Logger.getLogger(DisplayObject.class);
/*     */   private DisplayObject m_parent;
/*     */   private ObjectPool m_pool;
/*     */   
/*     */   public static enum DisplayObjectType
/*     */   {
/*  36 */     BITMAP,  BITMAP_SEQUENCE,  MOVIE_CLIP;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean m_released;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected HitTestableMesh2D m_mesh;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean m_meshAttached;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private ArrayList<AnimatedObjectControler> m_controlers;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected AbstractDescriptorLibrary m_descriptorLibrary;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected int m_descriptorId;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Rectangle2D.Float m_boundRect;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private DisplayObjectListener m_listener;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   DisplayObject()
/*     */   {
/*  89 */     onCheckOut();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   DisplayObject(AbstractDescriptorLibrary descriptorLibrary, int descriptorId)
/*     */   {
/*  99 */     this();
/* 100 */     initialize(descriptorLibrary, descriptorId);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public DisplayObject getParent()
/*     */   {
/* 108 */     return this.m_parent;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setParent(DisplayObject parent)
/*     */   {
/* 115 */     this.m_parent = parent;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setPool(ObjectPool pool)
/*     */   {
/* 122 */     this.m_pool = pool;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addControler(AnimatedObjectControler controler)
/*     */   {
/* 131 */     if (this.m_controlers == null) {
/* 132 */       this.m_controlers = new ArrayList();
/*     */     }
/* 134 */     this.m_controlers.add(controler);
/*     */   }
/*     */   
/*     */   public void addListener(DisplayObjectListener listener) {
/* 138 */     this.m_listener = listener;
/*     */   }
/*     */   
/* 141 */   public void removeListener() { this.m_listener = null; }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeControler(AnimatedObjectControler controler)
/*     */   {
/* 150 */     if (this.m_controlers != null) {
/* 151 */       this.m_controlers.remove(controler);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public HitTestableMesh2D getMesh()
/*     */   {
/* 159 */     return this.m_mesh;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract DisplayObjectDescriptor getDescriptor();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract float getLeftBound();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract float getBottomBound();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract float getWidth();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract float getHeight();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void release()
/*     */   {
/* 192 */     if (!isReleased()) {
/* 193 */       if (this.m_pool != null) {
/*     */         try {
/* 195 */           this.m_pool.returnObject(this);
/*     */         } catch (Exception e) {
/* 197 */           onCheckIn();
/* 198 */           e.printStackTrace();
/*     */         }
/*     */       } else {
/* 201 */         onCheckIn();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isReleased()
/*     */   {
/* 210 */     return this.m_released;
/*     */   }
/*     */   
/*     */   protected void applyDescriptorColor() {
/* 214 */     if (this.m_mesh != null) {
/* 215 */       DisplayObjectDescriptor descriptor = getDescriptor();
/* 216 */       Material currentMaterial = this.m_mesh.getMaterial();
/*     */       
/* 218 */       if (descriptor != null) {
/* 219 */         Material descriptorMaterial = descriptor.getMaterial();
/* 220 */         if (descriptorMaterial != null) {
/* 221 */           currentMaterial.set(descriptorMaterial);
/*     */         }
/* 223 */         else if ((currentMaterial.useDiffuse()) || (currentMaterial.useSpecular())) {
/* 224 */           currentMaterial.reset();
/*     */         }
/*     */         
/*     */       }
/* 228 */       else if ((currentMaterial.useDiffuse()) || (currentMaterial.useSpecular())) {
/* 229 */         resetColor();
/*     */       }
/*     */     }
/*     */     else {
/* 233 */       m_logger.error("L'objet n'a pas de mesh");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void resetColor()
/*     */   {
/* 241 */     if (this.m_mesh != null) {
/* 242 */       this.m_mesh.getMaterial().reset();
/*     */     } else {
/* 244 */       m_logger.error("L'objet n'a pas de mesh");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void colorize(Material color)
/*     */   {
/* 254 */     if (this.m_mesh != null) {
/* 255 */       MaterialColorMultAdd.composeMaterial(this.m_mesh.getMaterial(), color);
/*     */     } else
/* 257 */       m_logger.error("L'objet n'a pas de mesh");
/*     */   }
/*     */   
/*     */   public AbstractDescriptorLibrary getDescriptorLibrary() {
/* 261 */     return this.m_descriptorLibrary;
/*     */   }
/*     */   
/*     */   public void addTransformation(GLMatrix matrix) {
/* 265 */     this.m_mesh.pushMatrixBack(matrix, 5888);
/*     */   }
/*     */   
/*     */   public void setRotateSkew(RotateSkew2D rotateSkew) {
/* 269 */     this.m_mesh.setRotateSkew(rotateSkew.getX(), rotateSkew.getY());
/*     */   }
/*     */   
/*     */   public void setRotateSkew(float x, float y) {
/* 273 */     this.m_mesh.setRotateSkew(x, y);
/*     */   }
/*     */   
/*     */   public void setScale(Scaling2D scale) {
/* 277 */     this.m_mesh.setScale(scale.getX(), scale.getY());
/*     */   }
/*     */   
/*     */   public void setScale(float x, float y) {
/* 281 */     this.m_mesh.setScale(x, y);
/*     */   }
/*     */   
/*     */   public void setMatrix(Matrix2D matrix) {
/* 285 */     this.m_mesh.setTransformation(matrix);
/*     */   }
/*     */   
/*     */   protected void notifyProcessed() {
/* 289 */     if (this.m_listener != null)
/* 290 */       this.m_listener.onProcessed(this);
/*     */   }
/*     */   
/*     */   public void transformBy(DisplayObject transformer) {
/* 294 */     this.m_mesh.clearMatrices(5888);
/* 295 */     while (transformer != null) {
/* 296 */       if ((this.m_mesh != null) && (transformer.getMesh() != null)) {
/* 297 */         this.m_mesh.pushMatrixFront(transformer.getMesh().getPositionMatrix(), 5888);
/* 298 */         if (transformer.getMesh().getTransformation() != null) {
/* 299 */           this.m_mesh.pushMatrixFront(transformer.getMesh().getTransformation(), 5888);
/*     */         }
/*     */       } else {
/* 302 */         this.m_mesh.clearMatrices(5888);
/* 303 */         break;
/*     */       }
/* 305 */       transformer = transformer.getParent();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public abstract void reset();
/*     */   
/*     */ 
/*     */   public abstract boolean isAnimated();
/*     */   
/*     */ 
/*     */   public abstract DisplayObjectType getType();
/*     */   
/*     */ 
/*     */   public abstract boolean hitTest(float paramFloat1, float paramFloat2);
/*     */   
/*     */   public void doAction(List<String> actions)
/*     */   {
/* 323 */     if (this.m_controlers != null) {
/* 324 */       for (AnimatedObjectControler controler : this.m_controlers) {
/* 325 */         controler.onAnimatedObjectActionFlag(actions);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onCheckIn()
/*     */   {
/* 336 */     this.m_parent = null;
/* 337 */     this.m_released = true;
/* 338 */     this.m_mesh.release();
/* 339 */     this.m_mesh = null;
/* 340 */     this.m_invalidate = false;
/* 341 */     this.m_boundRect = null;
/* 342 */     this.m_descriptorLibrary = null;
/* 343 */     this.m_descriptorId = 0;
/* 344 */     this.m_controlers = null;
/* 345 */     this.m_listener = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onCheckOut()
/*     */   {
/* 354 */     this.m_mesh = HitTestableMesh2D.getNewHitTestableMesh2D();
/* 355 */     this.m_boundRect = new Rectangle2D.Float();
/* 356 */     this.m_released = false;
/* 357 */     this.m_meshAttached = false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void attachMeshTo(Mesh parent)
/*     */   {
/* 366 */     if ((!this.m_meshAttached) && (parent != null)) {
/* 367 */       parent.addChild(this.m_mesh);
/* 368 */       this.m_meshAttached = true;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void removeMeshFrom(Mesh parent)
/*     */   {
/* 378 */     if ((this.m_meshAttached) && (parent != null)) {
/* 379 */       parent.removeChild(this.m_mesh);
/* 380 */       this.m_meshAttached = false;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void initialize(AbstractDescriptorLibrary descriptorLibrary, int descriptorId)
/*     */   {
/* 390 */     this.m_descriptorId = descriptorId;
/* 391 */     this.m_descriptorLibrary = descriptorLibrary;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void initialize(ObjectPool pool, AbstractDescriptorLibrary descriptorLibrary, int descriptorId, String linkage)
/*     */   {
/* 401 */     initialize(descriptorLibrary, descriptorId);
/* 402 */     this.m_pool = pool;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract void refresh();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public DisplayObject getDisplayObject(String linkageName)
/*     */   {
/* 415 */     int id = getDescriptorLibrary().getIdFromLinkage(linkageName);
/* 416 */     return getDisplayObject(id);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public DisplayObject getDisplayObject(int id)
/*     */   {
/* 424 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\animation\instances\DisplayObject.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */