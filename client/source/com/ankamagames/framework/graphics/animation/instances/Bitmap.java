/*     */ package com.ankamagames.framework.graphics.animation.instances;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.BitmapDescriptor;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.DisplayObjectDescriptor;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.library.AbstractDescriptorLibrary;
/*     */ import com.ankamagames.framework.graphics.opengl.base.BaseTexture;
/*     */ import com.ankamagames.framework.graphics.opengl.base.Mesh;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public class Bitmap
/*     */   extends DisplayObject
/*     */ {
/*  23 */   protected static final Logger m_logger = Logger.getLogger(Bitmap.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Bitmap() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Bitmap(AbstractDescriptorLibrary descriptorLibrary, int descriptorId) {
/*  36 */     super(descriptorLibrary, descriptorId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BitmapDescriptor getDescriptor() {
/*  44 */     DisplayObjectDescriptor descriptor = this.m_descriptorLibrary.getDescriptor(this.m_descriptorId);
/*  45 */     if (descriptor != null && descriptor.getType() == DisplayObjectDescriptor.DescriptorType.BITMAP) {
/*  46 */       return (BitmapDescriptor)descriptor;
/*     */     }
/*     */     
/*  49 */     m_logger.trace("getBitmapDescritpor ne devrait pas arriver " + descriptor);
/*  50 */     invalidate();
/*  51 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(Mesh parentMesh, int deltaTime, int recurs) {
/*  61 */     BitmapDescriptor descriptor = getDescriptor();
/*     */     
/*  63 */     if (descriptor != null) {
/*  64 */       float inverScalingValue = descriptor.getInvertScalingValue();
/*  65 */       this.m_mesh.setScale(inverScalingValue, inverScalingValue);
/*  66 */       this.m_mesh.setHotPoint(descriptor.getHotX(), -descriptor.getHotY());
/*     */ 
/*     */       
/*  69 */       BaseTexture texture = descriptor.getTexture();
/*  70 */       if (texture != null) {
/*  71 */         this.m_mesh.setVisible(true);
/*  72 */         this.m_mesh.setTexture(texture);
/*  73 */         this.m_mesh.computeTextureCoordinate();
/*     */       } else {
/*     */         
/*  76 */         this.m_mesh.setVisible(false);
/*     */       } 
/*  78 */       attachMeshTo(parentMesh);
/*     */     } else {
/*  80 */       removeMeshFrom(parentMesh);
/*     */     } 
/*     */     
/*  83 */     notifyProcessed();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DisplayObject.DisplayObjectType getType() {
/* 100 */     return DisplayObject.DisplayObjectType.BITMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAnimated() {
/* 109 */     return false;
/*     */   }
/*     */   
/*     */   public float getAnimationSpeed() {
/* 113 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void incTime(int time) {}
/*     */   
/*     */   public boolean isPaused() {
/* 120 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAnimationSpeed(float speed) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCurrentTime(long time) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPause(boolean pause) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hitTest(float x, float y) {
/* 140 */     return this.m_mesh.hitTest(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getLeftBound() {
/* 150 */     return this.m_mesh.getPosX() - this.m_mesh.getHotX();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getBottomBound() {
/* 160 */     return this.m_mesh.getPosY() + this.m_mesh.getHotY() - getHeight();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getHeight() {
/* 170 */     BitmapDescriptor descriptor = getDescriptor();
/* 171 */     if (descriptor != null) {
/* 172 */       return descriptor.getHeight();
/*     */     }
/* 174 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getWidth() {
/* 184 */     BitmapDescriptor descriptor = getDescriptor();
/* 185 */     if (descriptor != null) {
/* 186 */       return descriptor.getWidth();
/*     */     }
/* 188 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public void refresh() {}
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\animation\instances\Bitmap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */