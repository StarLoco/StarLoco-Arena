/*     */ package com.ankamagames.framework.graphics.animation.instances;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.BitmapDescriptor;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.BitmapSequenceDescriptor;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.DisplayObjectDescriptor;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.DisplayObjectDescriptor.DescriptorType;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.library.AbstractDescriptorLibrary;
/*     */ import com.ankamagames.framework.graphics.opengl.base.BaseTexture;
/*     */ import com.ankamagames.framework.graphics.opengl.base.Mesh;
/*     */ import com.ankamagames.framework.graphics.opengl.base.impl.HitTestableMesh2D;
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
/*     */ public class BitmapSequence
/*     */   extends SequenceObject
/*     */ {
/*  24 */   protected static final Logger m_logger = Logger.getLogger(BitmapSequence.class);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BitmapSequence() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BitmapSequence(AbstractDescriptorLibrary descriptorLibrary, int descriptorId)
/*     */   {
/*  39 */     super(descriptorLibrary, descriptorId);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BitmapSequenceDescriptor getDescriptor()
/*     */   {
/*  48 */     DisplayObjectDescriptor descriptor = this.m_descriptorLibrary.getDescriptor(this.m_descriptorId);
/*  49 */     if ((descriptor != null) && (descriptor.getType() == DisplayObjectDescriptor.DescriptorType.BITMAP_SEQUENCE)) {
/*  50 */       return (BitmapSequenceDescriptor)descriptor;
/*     */     }
/*  52 */     m_logger.trace("getBitmapSequenceDescritpor ne devrait pas arriver " + descriptor);
/*  53 */     invalidate();
/*  54 */     return null;
/*     */   }
/*     */   
/*     */   public void process(Mesh parentMesh, int deltaTime, int recurs) {
/*  58 */     if (!this.m_terminated) {
/*  59 */       super.process(parentMesh, deltaTime, recurs);
/*     */       
/*  61 */       BitmapSequenceDescriptor descriptor = getDescriptor();
/*  62 */       if (descriptor != null) {
/*  63 */         BitmapDescriptor frameDescriptor = descriptor.getFrame(this.m_currentFrameIndex);
/*  64 */         if (frameDescriptor != null) {
/*  65 */           float inverScalingValue = descriptor.getInvertScalingValue();
/*  66 */           this.m_mesh.setScale(inverScalingValue, inverScalingValue);
/*  67 */           this.m_mesh.setHotPoint(frameDescriptor.getHotX(), -frameDescriptor.getHotY());
/*     */           
/*  69 */           BaseTexture texture = frameDescriptor.getTexture();
/*  70 */           if (texture != null) {
/*  71 */             this.m_mesh.setTexture(frameDescriptor.getTexture());
/*  72 */             this.m_mesh.computeTextureCoordinate();
/*  73 */             this.m_mesh.setVisible(true);
/*     */           } else {
/*  75 */             this.m_mesh.setVisible(false);
/*     */           }
/*  77 */           attachMeshTo(parentMesh);
/*     */         }
/*     */       }
/*     */     } else {
/*  81 */       removeMeshFrom(parentMesh);
/*     */     }
/*     */     
/*  84 */     notifyProcessed();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DisplayObject.DisplayObjectType getType()
/*     */   {
/*  93 */     return DisplayObject.DisplayObjectType.BITMAP_SEQUENCE;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hitTest(float x, float y)
/*     */   {
/* 104 */     return this.m_mesh.hitTest(x, y);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getLeftBound()
/*     */   {
/* 114 */     return this.m_mesh.getPosX() - this.m_mesh.getHotX();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getBottomBound()
/*     */   {
/* 124 */     return this.m_mesh.getPosY() + this.m_mesh.getHotY() - getHeight();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getHeight()
/*     */   {
/* 134 */     return this.m_mesh.getHeight();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getWidth()
/*     */   {
/* 144 */     return this.m_mesh.getWidth();
/*     */   }
/*     */   
/*     */   public void refresh() {}
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\animation\instances\BitmapSequence.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */