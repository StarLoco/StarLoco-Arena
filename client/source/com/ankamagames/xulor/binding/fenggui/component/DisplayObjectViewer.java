/*     */ package com.ankamagames.xulor.binding.fenggui.component;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.DisplayObjectDescriptor;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.library.AbstractDescriptorLibrary;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.library.DescriptorLibraryManager;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.library.ModifiableDescriptorLibrary;
/*     */ import com.ankamagames.framework.graphics.animation.instances.DisplayObject;
/*     */ import com.ankamagames.framework.graphics.opengl.base.Scene;
/*     */ import com.ankamagames.framework.graphics.opengl.base.animation.AnimatedObject;
/*     */ import com.ankamagames.framework.graphics.opengl.base.animation.AnimationManager;
/*     */ import com.ankamagames.framework.graphics.opengl.base.matrices.GLMatrix;
/*     */ import com.ankamagames.framework.graphics.opengl.base.matrices.transformation2D.Ortho2DScrollCamera;
/*     */ import com.ankamagames.framework.graphics.opengl.base.render.GLObject;
/*     */ import com.ankamagames.framework.graphics.opengl.base.states.DefaultScenePostRenderStates;
/*     */ import com.ankamagames.framework.graphics.opengl.base.states.DefaultScenePreRenderStates;
/*     */ import com.ankamagames.framework.graphics.opengl.base.states.GLRenderStates;
/*     */ import com.ankamagames.framework.kernel.core.resource.ManageableResource;
/*     */ import javax.media.opengl.GL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DisplayObjectViewer
/*     */   extends SceneCanvas
/*     */ {
/*     */   private class ScenePreRenderStates
/*     */     extends DefaultScenePreRenderStates
/*     */   {
/*     */     private ScenePreRenderStates() {}
/*     */     
/*     */     public void setup(GL gl) {
/*  34 */       super.setup(gl);
/*  35 */       gl.glTexEnvf(8960, 34163, 1.0F);
/*     */     } }
/*     */   
/*     */   private class ScenePostRenderStates extends DefaultScenePostRenderStates {
/*     */     private ScenePostRenderStates() {}
/*     */     
/*     */     public void setup(GL gl) {
/*  42 */       gl.glBlendFunc(770, 771);
/*  43 */       gl.glEnable(3042);
/*     */     }
/*     */   }
/*     */   
/*  47 */   private ModifiableDescriptorLibrary m_descriptorLibrary = null;
/*  48 */   private String m_linkage = null;
/*     */   
/*  50 */   private DisplayObject m_displayObject = null;
/*     */   private int m_xOffset;
/*     */   private int m_yOffset;
/*  53 */   private float m_scale = 1.0F;
/*     */   
/*     */   private boolean m_linkageChanged = false;
/*     */   
/*     */   private boolean m_descriptorLibraryChanged = false;
/*  58 */   private int m_lastDescriptorLibraryChangeRevision = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DisplayObjectViewer() {
/*  66 */     Scene scene = new Scene()
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void display(GL gl)
/*     */         {
/*  75 */           removeAllChilds();
/*  76 */           if (DisplayObjectViewer.this.m_displayObject != null && DisplayObjectViewer.this.m_displayObject.getMesh() != null) {
/*  77 */             addChild((GLObject)DisplayObjectViewer.this.m_displayObject.getMesh());
/*     */           }
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
/*  99 */           super.display(gl);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void process(long realTime, int frameCount) {
/* 111 */           if (DisplayObjectViewer.this.m_descriptorLibrary != null) {
/* 112 */             DisplayObjectViewer.this.m_descriptorLibrary.ensureParentAvailability();
/* 113 */             if (DisplayObjectViewer.this.m_descriptorLibrary.getParent() != null) {
/* 114 */               DescriptorLibraryManager.getInstance().tagResourceInUse((ManageableResource)DisplayObjectViewer.this.m_descriptorLibrary.getParent());
/*     */             }
/* 116 */             if (DisplayObjectViewer.this.m_displayObject != null && DisplayObjectViewer.this.m_descriptorLibrary.getChangeRevision() != DisplayObjectViewer.this.m_lastDescriptorLibraryChangeRevision) {
/* 117 */               DisplayObjectViewer.this.m_displayObject.refresh();
/* 118 */               DisplayObjectViewer.this.m_lastDescriptorLibraryChangeRevision = DisplayObjectViewer.this.m_descriptorLibrary.getChangeRevision();
/*     */             } 
/*     */           } 
/*     */           
/* 122 */           if (DisplayObjectViewer.this.m_linkageChanged || DisplayObjectViewer.this.m_descriptorLibraryChanged) {
/*     */ 
/*     */             
/* 125 */             if (DisplayObjectViewer.this.m_displayObject != null) {
/* 126 */               DisplayObjectViewer.this.m_displayObject.invalidate();
/*     */             }
/*     */ 
/*     */             
/* 130 */             if (DisplayObjectViewer.this.m_descriptorLibrary != null && DisplayObjectViewer.this.m_linkage != null) {
/* 131 */               DisplayObjectDescriptor descriptor = DisplayObjectViewer.this.m_descriptorLibrary.getDescriptor(DisplayObjectViewer.this.m_linkage);
/*     */               
/* 133 */               String libraryName = DescriptorLibraryManager.getInstance().getDescriptorLibraryName(DisplayObjectViewer.this.m_descriptorLibrary.getParent());
/*     */ 
/*     */               
/* 136 */               if (descriptor != null) {
/* 137 */                 DisplayObjectViewer.this.m_displayObject = descriptor.createInstance((AbstractDescriptorLibrary)DisplayObjectViewer.this.m_descriptorLibrary);
/*     */ 
/*     */                 
/* 140 */                 DisplayObjectViewer.this.setScale(DisplayObjectViewer.this.m_scale);
/*     */ 
/*     */                 
/* 143 */                 DisplayObjectViewer.this.m_displayObject.getMesh().setScreenPosition(DisplayObjectViewer.this.m_xOffset, DisplayObjectViewer.this.m_yOffset);
/*     */ 
/*     */                 
/* 146 */                 AnimationManager.getInstance().addAnimatedObject(this, (AnimatedObject)DisplayObjectViewer.this.m_displayObject);
/*     */               } else {
/* 148 */                 System.err.println("L'animation " + DisplayObjectViewer.this.m_linkage + " n'existe pas dans la bibliothèque (" + libraryName + ")!");
/*     */ 
/*     */                 
/* 151 */                 if (DisplayObjectViewer.this.m_descriptorLibrary.getParent() == null) {
/* 152 */                   m_logger.error("DisplayObjectViewer : m_descriptorLibrary.getParent() = null");
/*     */                 }
/*     */                 
/* 155 */                 DisplayObjectViewer.this.m_displayObject = null;
/*     */               } 
/*     */             } else {
/* 158 */               DisplayObjectViewer.this.m_displayObject = null;
/*     */             } 
/*     */ 
/*     */             
/* 162 */             DisplayObjectViewer.this.m_linkageChanged = false;
/* 163 */             DisplayObjectViewer.this.m_descriptorLibraryChanged = false;
/*     */           } 
/* 165 */           super.process(realTime, frameCount);
/*     */         }
/*     */       };
/*     */     
/* 169 */     scene.setInstancesInitialized(true);
/* 170 */     scene.setLoaded(true);
/* 171 */     scene.setCamera((GLMatrix)new Ortho2DScrollCamera());
/*     */ 
/*     */     
/* 174 */     scene.setPreRenderStates((GLRenderStates)new ScenePreRenderStates(null));
/* 175 */     scene.setPostRenderStates((GLRenderStates)new ScenePostRenderStates(null));
/*     */ 
/*     */     
/* 178 */     setScene(scene);
/*     */ 
/*     */     
/* 181 */     AnimationManager.getInstance().registerScene(getScene(), null, AnimationManager.ProcessType.USER_PROCESS);
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
/*     */   public void removedFromWidgetTree() {
/* 193 */     if (this.m_displayObject != null) {
/* 194 */       this.m_displayObject.release();
/* 195 */       this.m_displayObject = null;
/*     */     } 
/*     */     
/* 198 */     this.m_descriptorLibrary = null;
/* 199 */     this.m_linkage = null;
/*     */ 
/*     */     
/* 202 */     AnimationManager.getInstance().unregisterScene(getScene());
/* 203 */     setScene((Scene)null);
/* 204 */     super.removedFromWidgetTree();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModifiableDescriptorLibrary getDescriptorLibrary() {
/* 211 */     return this.m_descriptorLibrary;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDescriptorLibrary(ModifiableDescriptorLibrary descriptorLibrary) {
/* 221 */     this.m_descriptorLibrary = descriptorLibrary;
/* 222 */     this.m_descriptorLibraryChanged = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLinkage() {
/* 229 */     return this.m_linkage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLinkage(String linkage) {
/* 236 */     this.m_linkage = linkage;
/* 237 */     this.m_linkageChanged = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getXOffset() {
/* 244 */     return this.m_xOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXOffset(int offset) {
/* 251 */     this.m_xOffset = offset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getYOffset() {
/* 258 */     return this.m_yOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setYOffset(int offset) {
/* 265 */     this.m_yOffset = offset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setScale(float scale) {
/* 272 */     this.m_scale = scale;
/* 273 */     if (this.m_displayObject != null) {
/* 274 */       this.m_displayObject.setScale(this.m_scale, this.m_scale);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getScale() {
/* 282 */     return this.m_scale;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\component\DisplayObjectViewer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */